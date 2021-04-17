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
	public int getProductID (String productName) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ItemProduct item = new ItemProduct();

		String sql = "SELECT ItemProduct FROM itemproduct WHERE Name = ?";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, productName);

		rs = ps.executeQuery();
		rs.next();
		item.setItemProduct(rs.getInt(1));
		
		ps.close();
		rs.close();
		conn.close();

		
		return item.getItemProduct();
	}


	public int getItemProductID(String text) {
		
		
		
		int productID = 0;
		switch(text)
		{
		case "McChicken":
			productID = 1;
			break;
		case "Ayam Goreng McD Spicy (2pcs)":
			productID = 2;
			break;
		case "Ayam Goreng McD Spicy (5pcs)":
			productID = 3;
			break;
		case "Spicy Chicken McDeluxe":
			productID = 4;
			break;
		case "Chicken McNuggets (6pcs)":
			productID = 5;
			break;
		case "Double Cheeseburger":
			productID = 6;
			break;
		case "Big Mac":
			productID = 7;
			break;
		case "Filet-O-Fish":
			productID = 8;
			break;
		case "McChicken Meal (Medium)":
			productID = 9;
			break;
		case "Chicken McNuggets 6pcs Meal (Medium)":
			productID = 10;
			break;
		case "Filet-O-Fish Meal (Medium)":
			productID = 11;
			break;
		case "Strawberry Sundae":
			productID = 13;
			break;
		case "Chocolate Sundae":
			productID = 14;
			break;
		}
		return productID;
	}

}
