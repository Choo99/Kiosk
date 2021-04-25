package tcp.server.transactionServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.AuthorizationdbConn;
import exception.creditCardException.InvalidCreditCardException;

public class CreditCardAuthorization {

	public void validateCreditCardNo(String creditCardNo)throws InvalidCreditCardException{
		
		boolean result = false;
		
		AuthorizationdbConn db = new AuthorizationdbConn();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT creditcardauthorization.card_verify(?);";
		
		try {
			
			conn = db.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, creditCardNo);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getBoolean(1);
			}
			
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
			

		if(!result) {
			throw new InvalidCreditCardException();
		}
	}
}
