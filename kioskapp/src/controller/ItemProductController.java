package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			ps.setInt(1, itemProductId);
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

	public ArrayList<ItemProduct> getALLProduct ()
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<ItemProduct> products = new ArrayList<ItemProduct>();
		
		String sql = "SELECT ItemProduct ,Name, Price FROM itemproduct";
		
		try {
			conn = db.getConnection();
			ps= conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				ItemProduct product = new ItemProduct();
				product.setItemProduct(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setPrice(rs.getFloat(3));
				products.add(product);
			}			
			ps.close();
			rs.close();
			conn.close();

		} catch (SQLException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}

		return products;

	}
}
