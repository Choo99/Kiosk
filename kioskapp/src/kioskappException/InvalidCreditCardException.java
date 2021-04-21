package kioskappException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvalidCreditCardException extends Exception
{	

	private static final long serialVersionUID = 1L;

	public boolean validation() {
		return false;				
	}
	
}

