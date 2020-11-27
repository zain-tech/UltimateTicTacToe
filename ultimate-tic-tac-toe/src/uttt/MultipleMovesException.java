package uttt;

public class MultipleMovesException extends Exception {

	private static final long serialVersionUID = -6635595108031074784L;

	public MultipleMovesException() {
		super();
	}
	
	public MultipleMovesException(String message) {
		super(message);
	}
	
	public MultipleMovesException(String message, Throwable err) {
		super(message, err);
	}
	
}
