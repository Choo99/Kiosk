package tcpKioskClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import kioskapp.itemproduct.ItemProduct;

public class addCartButtonListener implements ActionListener{
	
	private ItemProduct itemProduct;
	private ImageIcon image;
	
	public addCartButtonListener(ItemProduct itemProduct,ImageIcon image) {
		this.itemProduct = itemProduct;
		this.image = image;
	}
	public void actionPerformed(ActionEvent e) {

		KioskFrame frame = new KioskFrame();
		frame.setVisible(false);
		//frame.setCartListPanel(itemProduct, image);
	}
}


