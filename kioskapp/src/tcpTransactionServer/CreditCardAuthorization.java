package tcpTransactionServer;

import kioskappException.InvalidCreditCardException;

public class CreditCardAuthorization {

	public void validateCreditCardNo(String creditCardNo) 
			throws InvalidCreditCardException{
		if(creditCardNo.length() != 16) {
			throw new InvalidCreditCardException("Your credit card number must equal to 16!");
		}
	}
}
