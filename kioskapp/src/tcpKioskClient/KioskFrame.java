package tcpKioskClient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import kioskapp.itemproduct.ItemProduct;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class KioskFrame extends JFrame {

private static final long serialVersionUID = 1L;
	private JTextField creditCardNo ;
	private static DecimalFormat format = new DecimalFormat("0.00");


	// Private attribute for frame size setting
	private int height =800;
	private int width = 1000;
	private JTable table;
	private JPanel cartListPanel;
	private int cartIndex = 0;
	//private JPanel menuPanel;
	//private JScrollPane menuScrollPanel;
	
	/**
	 * Create the frame.
	 */
	public KioskFrame() {
		
		//getContentPane().setLayout(new BorderLayout());
		getContentPane().setLayout(new GridLayout(2,1));
		this.setTitle("Kiosk Application");
		this.setSize(width, height);
		
		cartListPanel = new JPanel(new GridLayout(10,1));
		
		//menuPanel = new JPanel(new BorderLayout());
		
		//menuScrollPanel = new JScrollPane();
		
		// Center the frame on the screen
        this.setLocationRelativeTo(null);		
		
		// Must close on X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		//this.setResizable(false);
				
		// Display component
		loadComponent();
		
		
		
		
	}
	
	// To return menu panel 
	private JPanel getMenuPanel(Font font)
	{
			JPanel menuPanel = new JPanel();
		
			// Menu panel component object
			JLabel listLabel = new JLabel("List Of Food");
			
			// Style the menu panel
            menuPanel.setBackground(new Color(255, 250, 240));
            menuPanel.setLayout(new BorderLayout());
			
			// Style list label
            listLabel.setFont(font);	
			//listLabel.setBounds(424, 11, 161, 39);
            listLabel.setHorizontalAlignment(JLabel.CENTER);

            ListContent content = new ListContent();
            ArrayList<ItemProduct> productList = new ArrayList<ItemProduct>();
            ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
            productList = content.setProductList();
            images = content.setImages();
			JPanel list = setMenuList(productList,images);

			// Style the table
           
				
           Object[][] rec = {

                    {1, "McChicken", 8.1},
					{2, "Ayam Goreng McD Spicy (2pcs)", 11.9},
					{3, "Ayam Goreng McD Spicy (5pcs)", 30.2},
                    {4, "Spicy Chicken McDeluxe", 11.9},
                    {5, "Chicken McNuggets (6pcs)", 9.4},
                    {6, "Double Cheeseburger", 9.45},
                    {7, "Big Mac", 10.4},
                    {8, "Filet-O-Fish", 8.45},
                    {9, "McChicken Meal (Medium)", 13.2},
                    {10, "Chicken McNuggets 6pcs Meal (Medium)", 13.2},
                    {11, "Filet-O-Fish Meal (Medium)", 13},
                    
                    // Miss u missed no. 12 
                    // GOTCHA
                    {13, "Strawberry Sundae", 4.15},
                    {14, "Chocolate Sundae", 4.15},
                         };
            String [] header = { "No.", "Item", "Price" };
			
			table = new JTable(rec,header);

			table.setFont(new Font("Tahoma", Font.PLAIN, 13));
			//table.setBounds(145, 48, 750, 326);
            table.setRowHeight(23);
            table.getColumnModel().getColumn(0).setPreferredWidth(35);
            table.getColumnModel().getColumn(1).setPreferredWidth(275);
            table.getColumnModel().getColumn(2).setPreferredWidth(35);
			
			// Add menu panel's components
			menuPanel.add(BorderLayout.NORTH,listLabel);
			JScrollPane menuScrollPanel = new JScrollPane(list);
			menuPanel.add(BorderLayout.CENTER,menuScrollPanel);

			return menuPanel;
	}
	
	private JPanel setMenuList(ArrayList<ItemProduct> productList,ArrayList<ImageIcon> images) {
		JPanel menu = new JPanel(new GridLayout(productList.size(),1));
		
		for(int counter = 0;counter<productList.size();counter++) {
			ItemProduct itemProduct = productList.get(counter);
			ImageIcon image = images.get(counter);
			JLabel noLabel = new JLabel(Integer.toString(counter+1));
			JLabel productLabel = new JLabel(itemProduct.getName());
			JLabel productImgLabel = new JLabel();
			productImgLabel.setIcon(image);
			JLabel productPriceLabel = new JLabel(format.format(itemProduct.getPrice()));
			JButton addCartBtn = new JButton("Add Cart");
			addCartBtn.setPreferredSize(new Dimension(100,40));	

			JPanel btnPanel = new JPanel();
			btnPanel.add(addCartBtn);
			btnPanel.setPreferredSize(new Dimension(100,50));	
			
			JPanel panel = new JPanel();
			//GridLayout layout = new GridLayout();
			FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
			panel.setLayout(layout);

			panel.add(Box.createRigidArea(new Dimension(3 - noLabel.getPreferredSize().width,0)));
			panel.add(noLabel);
			panel.add(Box.createRigidArea(new Dimension(300 - productLabel.getPreferredSize().width,0)));
			panel.add(productLabel);
			panel.add(Box.createRigidArea(new Dimension(200 - productImgLabel.getPreferredSize().width,0)));
			panel.add(productImgLabel);
			panel.add(Box.createRigidArea(new Dimension(200 - productPriceLabel.getPreferredSize().width,0)));
			panel.add(productPriceLabel);
			panel.add(Box.createRigidArea(new Dimension(200 - btnPanel.getPreferredSize().width,0)));
			panel.add(btnPanel);
			menu.add(panel);
			
			KioskFrame frame = this;
			addCartBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					setCartListPanel(itemProduct,image,addCartBtn);
					
					frame.revalidate();
					frame.repaint();
				}
				
			});
		}
		return menu;
	}

	// To return cart panel
	private JPanel getCartPanel(Font font)
	{

		JPanel cartPanel = new JPanel(new BorderLayout());
		cartPanel.setBackground(new Color(255, 250, 240));

		// cart panel component object
		JLabel creditCardLbl = new JLabel("Credit Card Number : ");
		//creditCardLbl.setBounds(55, 318, 292, 39);
		creditCardNo = new JTextField ();
		//creditCardNo.setBounds(374, 315, 292, 45);
 		JLabel orderedList = new JLabel("Ordered List ");
 		orderedList.setHorizontalAlignment(JLabel.CENTER);
 		//orderedList.setBounds(55, 25, 172, 39);
		JButton processPayment = new JButton ("Process Payment");
		//processPayment.setBounds(670, 314, 265, 47);

		// Style cart panel
		//cartPanel.setBackground(new Color(255, 250, 240));

		

		// Style credit card label
		creditCardLbl.setFont(font);

		// Style credit card textField
		creditCardNo.setFont(font);		

        // Style ordered list       
        orderedList.setFont(font);

		// Style the process payment button
		processPayment.setFont(font);
		
		JScrollPane scrollPanel = new JScrollPane(cartListPanel);

		JPanel paymentPanel = new JPanel();
		//paymentPanel.add(creditCardLbl);
		//paymentPanel.add(creditCardNo);
		creditCardNo.setPreferredSize(new Dimension(300,50));
		paymentPanel.add(processPayment);		
		// Add all components to panel

		cartPanel.add(BorderLayout.NORTH,orderedList);
		cartPanel.add(BorderLayout.CENTER,scrollPanel);
		cartPanel.add(BorderLayout.SOUTH,paymentPanel);

		return cartPanel;
		
	}
	
	public void setCartListPanel(ItemProduct product,ImageIcon image,JButton addCartBtn) {
		JLabel noLabel = new JLabel(Integer.toString(++cartIndex));

		JLabel productLabel = new JLabel(product.getName());
		JLabel productImgLabel = new JLabel();
		productImgLabel.setIcon(image);
		SpinnerModel model = new SpinnerNumberModel(1,1,10,1);
		JSpinner quantity = new JSpinner(model);
		//set JSpinner not editable
		((DefaultEditor) quantity.getEditor()).getTextField().setEditable(false);
		quantity.setPreferredSize(new Dimension(50,20));
		JLabel productPriceLabel = new JLabel(format.format(product.getPrice()));
		float total = product.getPrice() * (Integer)quantity.getValue();
		JLabel totalPriceLabel = new JLabel(format.format(total));
		JButton removeBtn = new JButton("Remove");
		removeBtn.setPreferredSize(new Dimension(100,40));	
		
		JPanel btnPanel = new JPanel();
		btnPanel.add(removeBtn);
		
		btnPanel.setPreferredSize(new Dimension(100,50));	
		JPanel panel = new JPanel();
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		panel.setLayout(layout);

		panel.add(Box.createRigidArea(new Dimension(3 - noLabel.getPreferredSize().width,0)));
		panel.add(noLabel);
		panel.add(Box.createRigidArea(new Dimension(300 - productLabel.getPreferredSize().width,0)));
		panel.add(productLabel);
		panel.add(Box.createRigidArea(new Dimension(200 - productImgLabel.getPreferredSize().width,0)));
		panel.add(productImgLabel);
		panel.add(Box.createRigidArea(new Dimension(100 - quantity.getPreferredSize().width,0)));
		panel.add(quantity);
		panel.add(Box.createRigidArea(new Dimension(100 - productPriceLabel.getPreferredSize().width,0)));
		panel.add(productPriceLabel);
		panel.add(Box.createRigidArea(new Dimension(100 - totalPriceLabel.getPreferredSize().width,0)));
		panel.add(totalPriceLabel);
		panel.add(Box.createRigidArea(new Dimension(200 - btnPanel.getPreferredSize().width,0)));
		panel.add(btnPanel);
		cartListPanel.add(panel);
		
		addCartBtn.setEnabled(false);
		
		quantity.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				float total = product.getPrice() * (Integer)quantity.getValue();
				totalPriceLabel.setText(format.format(total));
			}
		});
		
		KioskFrame frame = this;
		removeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cartListPanel.remove(panel);
				cartIndex--;
				refreshCartIndex();
				addCartBtn.setEnabled(true);
				frame.revalidate();
				frame.repaint();
				
			}
			
		});

		System.out.println("haha");
	}

	private void refreshCartIndex() {
		
		for(int currentPanel = 0;currentPanel < cartIndex; currentPanel++) {
			
			JPanel panel = (JPanel)cartListPanel.getComponent(currentPanel);
			JLabel label = (JLabel)panel.getComponent(1);
			label.setText(Integer.toString(currentPanel + 1 ));
			
		}
	}
	private void loadComponent()
	{

		// Get font
		Font font = this.getFontStyle();

		// Upper layer
		this.add(getMenuPanel(font));

		// Lower layer
		this.add(getCartPanel(font));

;	}
	

	// Define a generic font style
	private Font getFontStyle() {
		
		Font font = new Font ("Yu Mincho Light", Font.PLAIN, 30);
		
		return font;
		
	}
}
