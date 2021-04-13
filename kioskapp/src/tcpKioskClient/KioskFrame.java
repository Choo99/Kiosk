package tcpKioskClient;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;

public class KioskFrame extends JFrame {

private static final long serialVersionUID = 1L;
	private JTextField creditCardNo ;
	


	// Private attribute for frame size setting
	private int height =800;
	private int width = 1000;
	private JTable table;

	
	/**
	 * Create the frame.
	 */
	public KioskFrame() {
		
		//getContentPane().setLayout(new BorderLayout());
		getContentPane().setLayout(new GridLayout(2,1));
		this.setTitle("Kiosk Application");
		this.setSize(width, height);
		
		
		// Center the frame on the screen
        this.setLocationRelativeTo(null);		
		
		// Must close on X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		this.setResizable(false);
				
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
            
            ImageIcon icon = null;
            JPanel list = null;
            Image image;
			try {
				
				image = ImageIO.read(new File("src/image/McChicken.png"));
				Image newImage = image.getScaledInstance(100, 75, Image.SCALE_DEFAULT);
				icon = new ImageIcon(newImage);
				Object[][] rec2 = { 
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1},
						{1, "McChicken",icon, 8.1}
						};
				list = setMenuList(rec2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			JScrollPane panel = new JScrollPane(list);
			//JPanel panel = new JPanel();
			//panel.setPreferredSize(new Dimension(20,30));
			menuPanel.add(BorderLayout.CENTER,panel);

			return menuPanel;
	}
	
	private JPanel setMenuList(Object[][] menuList) {
		JPanel menu = new JPanel(new GridLayout(14,1));
		for(int counter = 0;counter<13;counter++) {
			JLabel noLabel = new JLabel(Integer.toString((int)menuList[counter][0]));
			noLabel.setSize(100,200);
			JLabel productLabel = new JLabel((String)menuList[counter][1]);
			JLabel productImgLabel = new JLabel();
			productImgLabel.setIcon((ImageIcon)menuList[counter][2]);
			JLabel productPriceLabel = new JLabel(Double.toString((double)menuList[counter][3]));
			JButton addCartBtn = new JButton("Add Cart");
			JPanel panel = new JPanel();
			panel.add(noLabel);
			panel.add(productLabel);
			panel.add(productImgLabel);
			panel.add(productPriceLabel);
			panel.add(addCartBtn);
			menu.add(panel);
		}
		return menu;
	}

	// To return chart panel
	private JPanel getChartPanel(Font font)
	{

		JPanel chartPanel = new JPanel();
        

		// Chart panel component object
		JLabel creditCardLbl = new JLabel("Credit Card Number : ");
		creditCardLbl.setBounds(55, 318, 292, 39);
		creditCardNo = new JTextField ();
		creditCardNo.setBounds(374, 315, 292, 45);
 		JLabel orderedList = new JLabel("Ordered List ");
 		orderedList.setBounds(55, 25, 172, 39);
		JButton processPayment = new JButton ("Process Payment");
		processPayment.setBounds(670, 314, 265, 47);

		// Style chart panel
		chartPanel.setBackground(new Color(255, 250, 240));
		chartPanel.setLayout(null);
		

		// Style credit card label
		creditCardLbl.setFont(font);

		// Style credit card textField
		creditCardNo.setFont(font);		

        // Style ordered list       
        orderedList.setFont(font);

		// Style the process payment button
		processPayment.setFont(font);
		
		
        
		// Add all components to panel
		chartPanel.add(orderedList);
		chartPanel.add(creditCardNo);
		chartPanel.add(processPayment);
		chartPanel.add(creditCardLbl);
		return chartPanel;
		
	}

	private void loadComponent()
	{

		// Get font
		Font font = this.getFontStyle();

		// Upper layer
		getContentPane().add(getMenuPanel(font));

		// Lower layer
		getContentPane().add(getChartPanel(font));

;	}
	

	// Define a generic font style
	private Font getFontStyle() {
		
		Font font = new Font ("Yu Mincho Light", Font.PLAIN, 30);
		
		return font;
		
	}
}
