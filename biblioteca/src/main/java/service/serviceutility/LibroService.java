package service.serviceutility;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import exceptioncustom.ExceptionCustomLibro;
import model.Libro;
import repository.RepositoryBook;

@org.springframework.stereotype.Service
public class LibroService {
	@Autowired
	private RepositoryBook repositoryBook;
	public List<Libro> getLibriService() throws ExceptionCustomLibro {
		List<Libro> listaLibri = repositoryBook.findAll();
		if(listaLibri.isEmpty()) {
			throw new ExceptionCustomLibro("Non ci sono Libri nel DB!");
		}
		return listaLibri;
	}
	
	public Libro getLibroService(Long seriale) throws ExceptionCustomLibro {
		Optional<Libro>libro = repositoryBook.findById(seriale);
		if(libro.isPresent()) {
			return libro.get();
		} else {
			throw new ExceptionCustomLibro("Libro non trovato! Inserire ID Corretto!");
		}
	}
	
	public List<Libro> getLibriByAutoreService(String autore) throws ExceptionCustomLibro {
		List<Libro> listaLibri = repositoryBook.findAll();
		List<Libro> checkLibriByAutore = listaLibri.stream().filter(libro -> libro.getAutore().contains(autore)).collect(Collectors.toList());
		if(checkLibriByAutore.isEmpty()) {
			throw new ExceptionCustomLibro("Autore non trovato");
		}
		return checkLibriByAutore;
	}
	
	public Libro inserisciLibroService(Libro libro) {
		return repositoryBook.save(libro);
	}
	
	public Libro cancellaLibroService(Long id) throws ExceptionCustomLibro {
		Optional<Libro> libro = repositoryBook.findById(id);
		Libro libroEliminato;
		if(libro.isPresent()) {
			libroEliminato = libro.get();
			repositoryBook.delete(libroEliminato);
		} else {
			throw new ExceptionCustomLibro("Per la cancellazione di un libro inserire un ID esistente!");
		}
		return libroEliminato;
	}
	
	public Libro modificaLibro (Libro libro, Long id) {
		Optional <Libro> idlibro = repositoryBook.findById(id);
		if(idlibro.isPresent()) {
			return repositoryBook.save(libro);
		} else {
			throw new ExceptionCustomLibro("ID non trovato (modifica libro)");
		}
	}

}
