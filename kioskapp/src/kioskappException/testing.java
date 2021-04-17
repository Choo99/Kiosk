package kioskappException;

import tcpTransactionServer.CreditCardAuthorization;

public class testing {

	public static void main(String[] args) throws InvalidCreditCardException {
		
		CreditCardAuthorization check = new CreditCardAuthorization();
		check.validateCreditCardNo("666");
	}

}
