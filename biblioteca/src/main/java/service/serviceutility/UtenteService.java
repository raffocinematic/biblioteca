package service.serviceutility;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import exceptioncustom.ExceptionCustomLibro;
import exceptioncustom.ExceptionCustomUtente;
import model.Libro;
import model.Utente;
import repository.RepositoryBook;
import repository.RepositoryUser;

@org.springframework.stereotype.Service
public class UtenteService {
	@Autowired
	private RepositoryUser repositoryUser;
	@Autowired
	private RepositoryBook repositoryBook;
	@Autowired
	private UtilityComponent utilityComponent;

	public List<Utente> getUtentiService() throws ExceptionCustomUtente {
		List<Utente> listaUtenti = repositoryUser.findAll();
		if (listaUtenti.isEmpty()) {
			throw new ExceptionCustomUtente("Non ci sono Utenti nel DB!");
		}
		return listaUtenti;
	}

	public Utente getUtenteServiceById(Long id) throws ExceptionCustomUtente {
		Optional<Utente> utente = repositoryUser.findById(id);
		if (utente.isPresent()) {
			return utente.get();
		} else {
			throw new ExceptionCustomUtente("Utente non trovato! Inserire ID corretto!");
		}
	}

	public Utente inserisciUtenteService (Utente utente) {
		List<Libro> streamLibri = repositoryBook.findAll();
		List<Libro> libriUtente = utente.getLibri();
		
		List<Libro> libriEsistenti = utilityComponent.libriEsistentiSuDatabase(streamLibri, libriUtente);
		
		boolean controlloSullaQuantitaDiLibri = utilityComponent.controlloSullaQuantitaDiLibri(streamLibri, libriUtente);
		// per aver scritto libriNonESITENTI in utility component mi dava errore, fa attenzione sempre
		List<Libro> libriNonEsistenti = utilityComponent.libriNonEsistentiSuDatabase(streamLibri, libriEsistenti);
		
		int sommaDelleQuantita = utilityComponent.sommaDelleQuantitaDeiLibri(libriUtente);
		
		//CONTROLLO REQUEST - Verifica ParametroRequest LibriPresiInPrestito sia corrispondente alla somma 
		//delle quantità dei Libri ParametroRequest
		if(utente.getLibriPresiInPrestito() == sommaDelleQuantita) {
			//CONTROLLO ESISTENZA LIBRI DB
			if(libriEsistenti.size() == libriUtente.size()) {
				//CONTROLLO DISPONIBILITA' DB LIBRI
				if(controlloSullaQuantitaDiLibri) {
					throw new ExceptionCustomLibro("Quantità di libri esistente su database non corrispondente alla quantità di libri associata all'utente");
				}
				utilityComponent.decrementaQuantitaDiLibriDalDatabase(streamLibri, libriUtente);
				return repositoryUser.save(utente);
			} else {
				throw new ExceptionCustomLibro("Non ho trovato i seguenti libri: " + libriNonEsistenti);
			}
		} else {
			throw new ExceptionCustomLibro ("Libri presi in prestito non corrispondenti alle quantità di libri associata all'utente");
		}
	}

	public List<Utente> getUtenteByNomeService(String nome) throws ExceptionCustomUtente {
			List<Utente> listaUtenti = repositoryUser.findAll();
			List<Utente> checkUtentiByNome = listaUtenti.stream().filter(utente -> utente.getNome().contains(nome)).collect(Collectors.toList());
			if(checkUtentiByNome.isEmpty()) {
				throw new ExceptionCustomUtente("Utente non trovato!");
			}
			return checkUtentiByNome;
	}

	public Utente cancellaUtenteService(Long id) throws ExceptionCustomUtente {
		Optional<Utente> utente = repositoryUser.findById(id);
		Utente utenteEliminato;
		if (utente.isPresent()) {
			utenteEliminato = utente.get();
			repositoryUser.delete(utenteEliminato);
		} else {
			throw new ExceptionCustomUtente("Per la cancellazione dell'utente, inserire un ID esistente!");
		}
		return utenteEliminato;
	}

	public Utente modificaUtenteService(Utente utente) throws ExceptionCustomUtente {
		List<Libro> listaLibri = repositoryBook.findAll();
		List<Libro> libriPresiDallUtente = utente.getLibri();
		List<Utente> listUtentiRep = repositoryUser.findAll();

		if (utilityComponent.isUtenteEsistenteSuDatabase(utente, listUtentiRep)) {

			List<Libro> libriEsistenti = utilityComponent.libriEsistentiSuDatabase(listaLibri, libriPresiDallUtente);

			List<Libro> libriNonEsistenti = utilityComponent.libriNonEsistentiSuDatabase(libriPresiDallUtente,
					libriEsistenti);

			if (libriEsistenti.size() == libriPresiDallUtente.size()) {
				return repositoryUser.save(utente);
			} else {
				throw new ExceptionCustomUtente("Libro o libri non corretti " + libriNonEsistenti);
			}

		} else {
			throw new ExceptionCustomUtente("Utente inserito non correttamente " + utente);
		}

	}

}
