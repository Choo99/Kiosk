package tcpKioskClient;

import java.awt.Image;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import kioskapp.itemproduct.ItemProduct;


public class ListContent {

	public ArrayList<ItemProduct> setProductList(){
		 Object[][] rec = {
                {1, "McChicken", (float)8.1},
				{2, "Ayam Goreng McD Spicy (2pcs)", (float)11.9},
				{3, "Ayam Goreng McD Spicy (5pcs)", (float)30.2},
                {4, "Spicy Chicken McDeluxe", (float)11.9},
                {5, "Chicken McNuggets (6pcs)", (float)9.4},
                {6, "Double Cheeseburger", (float)9.45},
                {7, "Big Mac", (float)10.4},
                {8, "Filet-O-Fish", (float)8.45},
                {9, "McChicken Meal (Medium)", (float)13.2},
                {10, "Chicken McNuggets 6pcs Meal (Medium)", (float)13.2},
                {11, "Filet-O-Fish Meal (Medium)", (float)13},
                {13, "Strawberry Sundae", (float)4.15},
                {14, "Chocolate Sundae", (float)4.15},
                      };
		ArrayList<ItemProduct> productList= new ArrayList<ItemProduct>();
		for(int counter = 0;counter<13;counter++) {
			ItemProduct itemProduct = new ItemProduct();
			itemProduct.setName((String)rec[counter][1]);
			itemProduct.setPrice((float)(rec[counter][2]));
			productList.add(itemProduct);
		}
		return productList;
	}
	
	public ArrayList<ImageIcon> setImages(){
		String paths[] = {
				"src/image/McChicken.png",
				"src/image/AyamGoreng2pcs.png",
				"src/image/AyamGoreng5pcs.png",
				"src/image/SpicyChickenMcDeluxe.png",
				"src/image/ChickenMcNugggets6pcs.png",
				"src/image/DoubleCheeseBurger.png",
				"src/image/BigMac.png", 
				"src/image/Filet-O-Fish.png",
				"src/image/McChickenMeal.png",
				"src/image/ChickenMcNuggets6pcsMeal.png",
				"src/image/Filet-O-FishMeal.png",
				"src/image/StrawberrySundae.png",
				"src/image/ChocolateSundae.png",
		};
		
		ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
		try {
			for(int currentString = 0;currentString < 13;currentString++) {
				Image image = ImageIO.read(new File(paths[currentString]));
				Image newImage = image.getScaledInstance(120, 90, Image.SCALE_DEFAULT);
				ImageIcon icon = new ImageIcon(image);
				images.add(icon);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return images;
	}
	
	public ImageIcon setRescaledImages(ImageIcon image){
		Image oldImage = image.getImage();
		Image newImage = oldImage.getScaledInstance(120, 90, Image.SCALE_DEFAULT);
		ImageIcon icon = new ImageIcon(newImage);

		return icon;
	}
}
