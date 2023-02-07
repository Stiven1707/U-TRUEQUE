package co.unicauca.utrueque.domain.exception;

public class FailedTransaction {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FailedTransaction(String message) {
		super();
		this.message = message;
	}
	
	
}
