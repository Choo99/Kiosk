package controller;

import kioskapp.order.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController {

	private DatabaseConnection db;

	public OrderController(){
		db=new DatabaseConnection();
	}

	//insert order into database
	public Order insertOrder(Order order, int orderTransactionID){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int status = 0;
    
		String sql = "INSERT INTO order (OrderedItem,TotalAmount,OrderReferenceNumber,OrderTransaction) " +
                 "VALUES (?,?,?,?)";

        try {
			    conn = db.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setInt(1, order.getOrderedItems().size());
                ps.setFloat(2, order.getTotalAmount());
                ps.setInt(3, order.getOrderReferenceNumber());
                ps.setInt(4, orderTransactionID);

                status = ps.executeUpdate();
                ps.close();
                if(status!=0)
                {
                    sql ="SELECT OrderId FROM order;";
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    rs.last();
                    
                    order.setOrderId(rs.getInt(1));
                }
            } 
            catch (ClassNotFoundException | SQLException e) {
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
 
        
    return order;

	}
}
