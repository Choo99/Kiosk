package tcpKioskClient;

import java.awt.Image;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.ItemProductController;
import kioskapp.itemproduct.ItemProduct;


public class ListContent {

	public ArrayList<ItemProduct> setProductList(){

	ArrayList<ItemProduct> productList= new ArrayList<ItemProduct>();
	ItemProductController controller = new ItemProductController();
	productList = controller.getALLProduct();
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
