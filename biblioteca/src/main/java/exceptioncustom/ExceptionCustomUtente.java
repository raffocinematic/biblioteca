package exceptioncustom;

public class ExceptionCustomUtente extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ExceptionCustomUtente(String msg) {
		super(msg);
	}
}
