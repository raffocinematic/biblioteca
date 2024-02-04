package controller;

import exceptioncustom.ExceptionCustomLibro;
import model.Libro;
import service.serviceutility.LibroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/controllerLibri")
@Api(tags="Controller Libro")
public class ControllerLibro {
    @Autowired
    private LibroService libroService;
    @GetMapping("/libri")
    @ApiOperation(value="Visualizzazione di tutti i libri")
    public ResponseEntity<List<Libro>> getLibri() throws ExceptionCustomLibro {
        List<Libro> listaLibri = libroService.getLibriService();
        return new ResponseEntity<>(listaLibri, HttpStatus.OK);
    }
    @GetMapping("/libro/{seriale}")
    @ApiOperation(value="Visualizzazione di un libro tramite il codice seriale")
    public ResponseEntity<Libro> getLibro(@PathVariable("seriale") Long seriale) throws ExceptionCustomLibro {
        Libro libro = libroService.getLibroService(seriale);
        return new ResponseEntity<>(libro, HttpStatus.OK);
    }
    @GetMapping("/libriByAutore/{autore}")
    @ApiOperation(value="Visualizzazione dei libri tramite autore")
    public ResponseEntity<List<Libro>> getLibriByAutore(@PathVariable("autore") String autore) throws ExceptionCustomLibro {
        List<Libro> listaLibriByAutori = libroService.getLibriByAutoreService(autore);
        return new ResponseEntity<>(listaLibriByAutori, HttpStatus.OK);
    }
    @PostMapping("/inserisciLibro")
    @ApiOperation(value="Inserisci un libro")
    public ResponseEntity<Libro> inserisciLibro(@RequestBody Libro libro) {
        Libro libroInserito = libroService.inserisciLibroService(libro);
        return new ResponseEntity<>(libroInserito, HttpStatus.CREATED);
    }
    @DeleteMapping("/cancellaLibro/{idLibro}")
    @ApiOperation(value="Eliminazione di un libro tramite id")
    public ResponseEntity<Libro> cancellaLibro(@PathVariable("idLibro") Long idLibro) throws ExceptionCustomLibro {
        return new ResponseEntity<>(libroService.cancellaLibroService(idLibro), HttpStatus.NO_CONTENT);
    }
    @PutMapping("/aggiornaLibro/{idLibro}")
    @ApiOperation(value="Aggiorna un libro. Note che, in caso di mancato id nel body, si effettua un inserimento!")
    public ResponseEntity<Libro> modificaLibro(@RequestBody Libro libro, @PathVariable("idLibro") Long idLibro) {
        Libro libroModificato = libroService.modificaLibro(libro, idLibro);
        return new ResponseEntity<>(libroModificato, HttpStatus.OK);
    }


}

