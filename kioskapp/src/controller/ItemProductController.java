package controller;

public class ItemProductController {

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
			productID = 10;
			break;
		case "Strawberry Sundae":
			productID = 10;
			break;
		case "Chocolate Sundae":
			productID = 10;
			break;
		}
		return productID;
	}

}
