package controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kioskapp.ordertransaction.OrderTransaction;

public class OrderTransactionController {

	private DatabaseConnection db;

	public OrderTransactionController(){
		db = new DatabaseConnection();
	}

	// To insert transaction detail into database and get the auto generate transaction id
	
	public OrderTransaction insertTransaction(OrderTransaction orderTransaction){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int status =0;
       
		String sql="INSERT INTO ordertransaction(TransactionDate,AmountCharged,TransactionStatus,Last4Digits,OrderMode)"+
                "VALUES (LOCALTIMESTAMP(),?, ?, ?, ?);";

		try{
			conn = db.getConnection();
			ps = conn.prepareStatement(sql);
          
			ps.setFloat(1, orderTransaction.getAmountCharged());
			if (orderTransaction.isTransactionStatus())
				ps.setInt(2, 1);
            else
                 ps.setInt(2, 0);
            ps.setInt(3, orderTransaction.getLast4Digits());
            ps.setString(4,orderTransaction.getOrderMode());
            status = ps.executeUpdate();
            

            if(status!=0){   
            	
            	// To get the order transaction id generated by database
                sql = "SELECT * FROM ordertransaction";
                
                // To allow cursor to move from front and back
                 ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
                 rs = ps.executeQuery();
                 // Move cursor to the last row of the table
                 rs.afterLast();
                 if(rs.previous())
                	 orderTransaction.setOrderTransactionId(rs.getInt(1));
                 
                 

            }         

        }catch(Exception e){
        	e.printStackTrace();
        }
        finally{
           
				try {
					 	if (ps!= null)
					 		ps.close();
					 	if (rs!=null)
		             	  rs.close();
					 	if(conn!=null)
					 		conn.close();  
					 	
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
                 	
        }
        return orderTransaction;
	}
}
