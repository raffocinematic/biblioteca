package exceptioncustom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import exceptioncustom.UtenteErrorResponse;
import lombok.*;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
	Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);
	@ExceptionHandler
	public ResponseEntity<UtenteErrorResponse> utenteException(ExceptionCustomUtente exc) {
		UtenteErrorResponse error = new UtenteErrorResponse();
		//ho dovuto generare getters and setters in UtenteErrorResponse e LibroErroResponse perchè lombok non funziona e devo capire perchè
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMsg(exc.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler
	public ResponseEntity<LibroErrorResponse> libroException(ExceptionCustomLibro exc) {
		LibroErrorResponse error = new LibroErrorResponse();
		//come sopra a riga 17
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMsg(exc.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		
	}

}
