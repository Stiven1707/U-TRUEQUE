package co.unicauca.utrueque.domain.exception;

public class FailedBlockChain {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FailedBlockChain(String message) {
		super();
		this.message = message;
	}
}