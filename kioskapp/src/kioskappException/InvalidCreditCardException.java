package kioskappException;

public class InvalidCreditCardException extends Exception
{	
	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	public InvalidCreditCardException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String toString() {
		return(errorMessage);				
	}
	
}

