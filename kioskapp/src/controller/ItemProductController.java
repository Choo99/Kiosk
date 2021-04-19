package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kioskapp.itemproduct.ItemProduct;

public class ItemProductController {

	private DatabaseConnection db;
	
	public ItemProductController (){
		db = new DatabaseConnection();
	}

	@SuppressWarnings("null")
	public int getItemProductID (String productName) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ItemProduct item = new ItemProduct();

		String sql = "SELECT ItemProduct FROM itemproduct WHERE Name = ?";
		
		try {
			conn = db.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, productName);
			rs = ps.executeQuery();
			if (rs.next())
				item.setItemProduct(rs.getInt(1));
			
		} catch (SQLException | ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}
		
		
		try {
			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		

		
		return item.getItemProduct();
	}

	
	public ItemProduct getProduct (int itemProductId)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ItemProduct item = new ItemProduct();
		
		String sql = "SELECT Name, Price FROM itemproduct WHERE ItemProduct =? ";
		
		try {
			conn = db.getConnection();
			ps= conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()){
				item.setName(rs.getString(1));
				item.setPrice(rs.getFloat(2));
			}
			
						
			ps.close();
			rs.close();
			conn.close();

		} catch (SQLException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}

		return item;

	}
}
