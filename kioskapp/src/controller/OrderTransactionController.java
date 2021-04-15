package controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kioskapp.ordertransaction.OrderTransaction;

public class OrderTransactionController {


private DatabaseConnection db;

public OrderTransactionController()
{
    db = new DatabaseConnection();
}

public int insertTransaction(OrderTransaction orderTransaction)
{
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int status =0;
       
    String sql="INSERT INTO ordertransaction(TransactionDate,AmountCharged,TransactionStatus,Last4Digits,OrderMode)"+
                "VALUES (localtime(),?, ?, ?, ?);";

    try{
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
          
            ps.setFloat(2, orderTransaction.getAmountCharged());
            if (orderTransaction.isTransactionStatus())
                ps.setInt(3, 1);
            else
                 ps.setInt(3, 0);
            ps.setInt(4, orderTransaction.getLast4Digits());
            ps.setString(5,orderTransaction.getOrderMode());
            status = ps.executeUpdate();

            
           
            return status;
           


    }catch(Exception e)
    {

    }
    finally
    {
        try
	           {
	                if (ps!=null)
	                    ps.close();
                    if(rs!=null)
                        rs.close();
	                if(conn!=null)
	                    conn.close();
	            }
	            catch(Exception ex){}
    }
	return status;








}






}
