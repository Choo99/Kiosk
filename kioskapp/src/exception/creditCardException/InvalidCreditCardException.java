package exception.creditCardException;

@SuppressWarnings("serial")
public class InvalidCreditCardException extends Exception
{	

	public boolean validation() {
		return false;				
	}
	
}

