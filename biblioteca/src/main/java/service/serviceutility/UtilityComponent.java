package service.serviceutility;

import lombok.*;
import model.Libro;
import model.Utente;
import repository.RepositoryBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import repository.RepositoryBook;

@Component
public class UtilityComponent {
    @Autowired
    private RepositoryBook repositoryBook;
    Logger logger = LoggerFactory.getLogger(UtilityComponent.class);
    public boolean isUtenteEsistenteSuDatabase(Utente utente, List<Utente> listaUtenti) {
       boolean utenteEsistente = false;
        for(Utente streamUtenti: listaUtenti) {
        	//anche qui in Utente ho dovuto generare i get perchè lombok non funziona
            if(streamUtenti.getIdUtente().equals(utente.getIdUtente()) &&
                    streamUtenti.getNome().equals(utente.getNome()) &&
                    streamUtenti.getEmail().equals(utente.getEmail()) &&
                    streamUtenti.getCognome().equals(utente.getCognome()) &&
                    streamUtenti.getTelefono().equals(utente.getTelefono()) &&
                    streamUtenti.getLibriPresiInPrestito() == utente.getLibriPresiInPrestito()) {
                utenteEsistente = true;
                break;
            }
        }
        return utenteEsistente;
    }
    public List<Libro> libriEsistentiSuDatabase(List<Libro> listaLibri, List<Libro> libriUtente) {
        List<Libro> libriEsistenti = new ArrayList<>();
        for(Libro libroUtente: libriUtente) {
            for(Libro libro : listaLibri) {
            	//come a riga 23, ho dovuto generare in getters in Libro perchè lombok non va
                if (libroUtente.getIdLibro().equals(libro.getIdLibro()) &&
                        libroUtente.getTitolo().equals(libro.getTitolo()) &&
                        libroUtente.getSeriale().equals(libro.getSeriale()) &&
                        libroUtente.getEditore().equals(libro.getEditore()) &&
                        libroUtente.getAutore().equals(libro.getAutore())) {
                    libriEsistenti.add(libro);
                }
            }
        }
        return libriEsistenti;
    }
    public List<Libro> libriNonEsistentiSuDatabase(List<Libro> libriUtente, List<Libro> libriEsistenti) {
        List<Libro> libriNonEsistenti = new ArrayList<>();
        for(Libro libro: libriUtente) {
            if(!libriEsistenti.contains(libro)) {
                libriNonEsistenti.add(libro);
            }
        }
        return libriNonEsistenti;
    }
    public int sommaDelleQuantitaDeiLibri(List<Libro> libriUtente) {
        int sommaDelleQuantita = 0;

        for(Libro libro: libriUtente) {
            sommaDelleQuantita += libro.getQuantita();
        }
        return sommaDelleQuantita;
    }
    public void decrementaQuantitaDiLibriDalDatabase(List<Libro> streamLibri, List<Libro> libriUtente) {

        for(Libro libro : streamLibri) {
            for(Libro libroUtente : libriUtente) {
                if (libroUtente.getIdLibro().equals(libro.getIdLibro()) &&
                        libroUtente.getTitolo().equals(libro.getTitolo()) &&
                        libroUtente.getSeriale().equals(libro.getSeriale()) &&
                        libroUtente.getEditore().equals(libro.getEditore()) &&
                        libroUtente.getAutore().equals(libro.getAutore())) {
                	//ho dovuto scrivere il setter di quantità in Libro sempre per via di lombok che non funziona
                        libro.setQuantita((libro.getQuantita()) - (libroUtente.getQuantita()));
                        //repositoryBook.save(libro); ?? PERCHè DOVREBBE FUNZIONARE ANCHE SENZA LA SAVE?
                }
            }
        }
    }
    public boolean controlloSullaQuantitaDiLibri(List<Libro> streamLibri, List<Libro> libriUtente) {
        boolean controlloQuantitaNonDisponibile = false;

        for(Libro libroUtente: libriUtente) {
            for(Libro libro : streamLibri) {
                if(libroUtente.getAutore().equals(libro.getAutore()) &&
                   libroUtente.getIdLibro().equals(libro.getIdLibro()) &&
                   libroUtente.getTitolo().equals(libro.getTitolo()) &&
                   libroUtente.getSeriale().equals(libro.getSeriale()) &&
                   libroUtente.getEditore().equals(libro.getEditore())) {
                   if(libro.getQuantita() < libroUtente.getQuantita()) {
                        controlloQuantitaNonDisponibile = true;
                        break;
                   }
                }
            }
        }
        return controlloQuantitaNonDisponibile;
    }
}
