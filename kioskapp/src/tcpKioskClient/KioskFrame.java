package tcpKioskClient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import kioskapp.itemproduct.ItemProduct;
import kioskapp.order.Order;
import kioskapp.ordereditem.OrderedItem;
import kioskapp.ordertransaction.OrderTransaction;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.ItemProductController;

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
	private JPanel paymentListPanel;
	private float totalPrice = 0;
	private JLabel totalPriceLabel;
	private JLabel priceLabel;
	private JTabbedPane tabbedPanel;
	private OrderTransaction orderTransaction;
	private String creditCardNumber;
	//private JPanel menuPanel;
	//private JScrollPane menuScrollPanel;
	
	/**
	 * Create the frame.
	 */
	public KioskFrame() {
		
		//getContentPane().setLayout(new BorderLayout());
		this.setTitle("Kiosk Application");
		this.setSize(width, height);
		
		cartListPanel = new JPanel(new GridLayout(10,1));
		paymentListPanel = new JPanel(new GridLayout(20,1));
		paymentListPanel.setBackground(new Color(255, 250, 240));
		
		totalPriceLabel = new JLabel("RM 0.00");
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
		JPanel menu = new JPanel(new GridLayout(10,3));

		
		for(int counter = 0;counter<productList.size();counter++) {
			ItemProduct itemProduct = productList.get(counter);
			ImageIcon image = images.get(counter);
			JLabel productLabel = new JLabel(itemProduct.getName());
			JLabel productImgLabel = new JLabel();
			productImgLabel.setIcon(image);
			JLabel productPriceLabel = new JLabel("RM" + format.format(itemProduct.getPrice()));
			JButton addCartBtn = new JButton("Add Cart");
			addCartBtn.setPreferredSize(new Dimension(100,40));	

			JPanel btnPanel = new JPanel();
			btnPanel.add(addCartBtn);
			btnPanel.setPreferredSize(new Dimension(100,50));	
			
			JPanel allInOnePanel = new JPanel();
			//GridLayout layout = new GridLayout();
			BorderLayout layout = new BorderLayout();
			allInOnePanel.setLayout(layout);
			
			Border border = BorderFactory.createLineBorder(new Color(70, 130, 180));
			allInOnePanel.setBorder(BorderFactory.createTitledBorder(border,productLabel.getText()));
			allInOnePanel.add(BorderLayout.CENTER,productImgLabel);
			productImgLabel.setHorizontalAlignment(JLabel.CENTER);
			
			JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			Border secondBorderLine = BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(70, 130, 180));
			secondBorderLine = BorderFactory.createTitledBorder(secondBorderLine);
			secondPanel.setBorder(secondBorderLine);
			secondPanel.add(productPriceLabel);
			secondPanel.add(Box.createRigidArea(new Dimension(200 - btnPanel.getPreferredSize().width,0)));
			secondPanel.add(btnPanel);
		
			allInOnePanel.add(BorderLayout.SOUTH,secondPanel);
			menu.add(allInOnePanel);
			
			KioskFrame frame = this;
			addCartBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					setCartListPanel(itemProduct,image,addCartBtn);
					tabbedPanel.setEnabledAt(1, true);
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
		creditCardNo = new JTextField ();
 		JLabel orderedList = new JLabel("Ordered List ");
 		orderedList.setHorizontalAlignment(JLabel.CENTER);
		JButton processPayment = new JButton ("Process Payment");


		// Style cart panel
		cartListPanel.setOpaque(true);
		cartListPanel.setBackground(new Color(255, 250, 240));
		//cartPanel.setBackground(new Color(255, 250, 240));
		

		// Style credit card textField
		creditCardNo.setFont(font);		

        // Style ordered list       
        orderedList.setFont(font);

		// Style the process payment button
		processPayment.setFont(font);
		
		processPayment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				setPaymentListPanel();
				priceLabel.setText("RM " + format.format(totalPrice));
				tabbedPanel.setEnabledAt(0, false);
				tabbedPanel.setEnabledAt(1, false);
				tabbedPanel.setSelectedIndex(2);
			}
			
		});
		
		JPanel labelPanel = new JPanel();
		labelPanel.add(Box.createRigidArea(new Dimension(0,50)));
		labelPanel.add(orderedList);
		
		JScrollPane scrollPanel = new JScrollPane(cartListPanel);

		JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel totalLabel = new JLabel("Total Price : ");
		totalPriceLabel.setFont(font);
		totalLabel.setFont(font);
		totalLabel.setVerticalTextPosition(1);
		creditCardNo.setPreferredSize(new Dimension(300,50));
		
		pricePanel.add(totalLabel);
		pricePanel.add(Box.createRigidArea(new Dimension(5,50)));
		pricePanel.add(totalPriceLabel);
		pricePanel.add(Box.createRigidArea(new Dimension(5,0)));
		pricePanel.add(processPayment);

		// Add all components to panel

		cartPanel.add(BorderLayout.NORTH,labelPanel);
		cartPanel.add(BorderLayout.CENTER,scrollPanel);
		cartPanel.add(BorderLayout.SOUTH,pricePanel);

		return cartPanel;
		
	}
	
	public void setCartListPanel(ItemProduct product,ImageIcon image,JButton addCartBtn) {
		JLabel noLabel = new JLabel(Integer.toString(++cartIndex));

		JLabel productLabel = new JLabel(product.getName());
		JLabel productImgLabel = new JLabel();
		//rescaled image
		ListContent listContent = new ListContent();
		image = listContent.setRescaledImages(image);
		
		productImgLabel.setIcon(image);
		SpinnerModel model = new SpinnerNumberModel(1,1,10,1);
		JSpinner quantity = new JSpinner(model);
		//set JSpinner not editable
		((DefaultEditor) quantity.getEditor()).getTextField().setEditable(false);
		quantity.setPreferredSize(new Dimension(50,20));
		JLabel productPriceLabel = new JLabel("RM " + format.format(product.getPrice()));
		float total = product.getPrice() * (Integer)quantity.getValue();
		totalPrice += total;
		totalPriceLabel.setText("RM " + format.format(totalPrice));
		JLabel subtotalPriceLabel = new JLabel(format.format(total));
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
		panel.add(Box.createRigidArea(new Dimension(100 - subtotalPriceLabel.getPreferredSize().width,0)));
		panel.add(subtotalPriceLabel);
		panel.add(Box.createRigidArea(new Dimension(200 - btnPanel.getPreferredSize().width,0)));
		panel.add(btnPanel);
		
		//add border to the panel
		Border border = BorderFactory.createTitledBorder(" ");
		panel.setBorder(border);
		cartListPanel.add(panel);
		
		addCartBtn.setEnabled(false);
		
		quantity.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				float newTotal = product.getPrice() * (Integer)quantity.getValue();
				float oldTotal = Float.parseFloat(subtotalPriceLabel.getText());
				subtotalPriceLabel.setText(format.format(newTotal));
				totalPrice += newTotal - oldTotal;
				totalPriceLabel.setText("RM " + format.format(totalPrice));
			}
		});
		
		KioskFrame frame = this;
		removeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cartListPanel.remove(panel);
				cartIndex--;
				float newTotal = product.getPrice() * (Integer)quantity.getValue();
				totalPrice -= newTotal ;
				totalPriceLabel.setText("RM " +format.format(totalPrice));
				refreshCartIndex();
				addCartBtn.setEnabled(true);
				frame.revalidate();
				frame.repaint();
			}
			
		});
	}
	
	private JPanel getPaymentPanel(Font font) {
		
		JPanel paymentPanel = new JPanel(new BorderLayout());
		paymentPanel.setBackground(new Color(255, 250, 240));
		
		//JPanel paymentDetailPanel = new JPanel();
		
		JLabel paymentLabel = new JLabel("Payment");
		paymentLabel.setFont(font);
		paymentLabel.setHorizontalAlignment(JLabel.CENTER);

		JPanel totalPanel = getTotalPanel(font);
		JPanel nestedTotalPanel = new JPanel();
		nestedTotalPanel.add(totalPanel);
		JScrollPane scrollPanel = new JScrollPane(paymentListPanel);
		//paymentDetailPanel.add(scrollPanel);
		//paymentDetailPanel.add(totalPanel);
		
		paymentPanel.add(BorderLayout.NORTH,paymentLabel);

		//paymentPanel.add(BorderLayout.CENTER,paymentDetailPanel);
		paymentPanel.add(BorderLayout.CENTER,scrollPanel);
		paymentPanel.add(BorderLayout.EAST,nestedTotalPanel);
		paymentPanel.add(BorderLayout.SOUTH,Box.createRigidArea(new Dimension (0,200)));
		return paymentPanel;
	}
	
	private JPanel getTotalPanel(Font font) {
		JPanel totalPanel = new JPanel();
		totalPanel.setLayout(new BoxLayout(totalPanel,BoxLayout.Y_AXIS));
		
		JLabel orderSummaryLabel = new JLabel("Order Summary");
		JLabel totalPriceLabel = new JLabel("Total: ");
		
		priceLabel = new JLabel("RM " + format.format(totalPrice));
		
		JLabel tax = new JLabel("(included GST)");
		tax.setHorizontalAlignment(JLabel.CENTER);
		
		JTextField creditCardTextField = new JTextField("Credit Card Number");
		
		JButton confirmBtn = new JButton("Confirm");
		JButton backBtn = new JButton("Back");
		
		//styling all component
		orderSummaryLabel.setFont(font);
		totalPriceLabel.setFont(font);
		priceLabel.setFont(font);
		tax.setFont(new Font("Yu Mincho Light", Font.ITALIC, 15));
		creditCardTextField.setFont(new Font("Yu Mincho Light", Font.ITALIC, 15));
		creditCardTextField.setForeground(Color.GRAY);
		confirmBtn.setFont(font);
		backBtn.setFont(font);
		
		
		JPanel orderSummary = new JPanel();
		orderSummary.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		orderSummary.add(orderSummaryLabel);
		
		JPanel pricePanel = new JPanel();
		pricePanel.add(totalPriceLabel);
		pricePanel.add(priceLabel);
		
		JPanel creditCardPanel = new JPanel();
		creditCardTextField.setPreferredSize(new Dimension(210,30));
		creditCardPanel.add(creditCardTextField);
		
		JPanel confirmBtnPanel = new JPanel();
		confirmBtn.setPreferredSize(new Dimension(210,40));
		confirmBtnPanel.add(confirmBtn);
		
		JPanel backBtnPanel = new JPanel();
		backBtn.setPreferredSize(new Dimension(210,40));
		backBtnPanel.add(backBtn);
		
		totalPanel.add(orderSummary);
		totalPanel.add(pricePanel);
		totalPanel.add(tax);
		totalPanel.add(creditCardPanel);
		totalPanel.add(confirmBtnPanel);
		totalPanel.add(backBtnPanel);
		
		totalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		creditCardTextField.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
		
				creditCardTextField.setText("");
			}

			public void focusLost(FocusEvent e) {
				creditCardTextField.setText("Credit Card Number");
			}
		});
		
		backBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				tabbedPanel.setEnabledAt(0, true);
				tabbedPanel.setEnabledAt(1, true);
				tabbedPanel.setEnabledAt(2, false);
				paymentListPanel.removeAll();
				tabbedPanel.setSelectedIndex(1);
			}
			
		});
		
		confirmBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getOrderedItem(creditCardTextField);
			}
			
		});
		return totalPanel;
	}
	
	private void setPaymentListPanel() {
		Component[] components = cartListPanel.getComponents();
		for(int count = 0; count<cartIndex;count++) {
			
			JPanel panel = (JPanel)components[count];
			JPanel newPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			
			JLabel noLabel = new JLabel(Integer.toString(count));
			
			JLabel productLabel = (JLabel)panel.getComponent(3);
			JLabel newProductLabel = new JLabel(productLabel.getText());
			
			JLabel productImage = (JLabel)panel.getComponent(5);
			JLabel newProductImage = new JLabel();
			newProductImage.setIcon(productImage.getIcon());
			
			JSpinner quantitySpinner = (JSpinner)panel.getComponent(7);
			int quantity = (Integer)quantitySpinner.getValue();
			JLabel newQuantityLabel = new JLabel(Integer.toString(quantity));
			
			JLabel subtotalLabel = (JLabel)panel.getComponent(11);
			JLabel newSubtotalLabel = new JLabel(subtotalLabel.getText());
			
			newPanel.add(Box.createRigidArea(new Dimension (3 - noLabel.getPreferredSize().width,0)));
			newPanel.add(noLabel);
			newPanel.add(Box.createRigidArea(new Dimension (280 - newProductLabel.getPreferredSize().width,0)));
			newPanel.add(newProductLabel);
			newPanel.add(Box.createRigidArea(new Dimension (200 - newProductImage.getPreferredSize().width,0)));
			newPanel.add(newProductImage);
			newPanel.add(Box.createRigidArea(new Dimension (70 - newQuantityLabel.getPreferredSize().width,0)));
			newPanel.add(newQuantityLabel);
			newPanel.add(Box.createRigidArea(new Dimension (100 - newSubtotalLabel.getPreferredSize().width,0)));
			newPanel.add(newSubtotalLabel);
			
			newPanel.setBorder(BorderFactory.createTitledBorder(" "));
			
			paymentListPanel.add(newPanel);
		}
	}

	private void getOrderedItem(JTextField creditNumber) {
		
		Component[] component = paymentListPanel.getComponents();
		ArrayList<OrderedItem> orderedItems = new ArrayList<OrderedItem>();
		for(int counter = 0; counter < component.length ; counter++) {
			
			OrderedItem orderedItem = new OrderedItem();
			JPanel panel = (JPanel)component[counter];
			
			JLabel productName = (JLabel)panel.getComponent(1);
			ItemProductController converter = new ItemProductController();
			orderedItem.setOrderedItem(converter.getItemProductID(productName.getText()));
			
			JLabel quantity = (JLabel)panel.getComponent(3);
			orderedItem.setQuantity(Integer.parseInt(quantity.getText()));
			
			JLabel subtotal = (JLabel)panel.getComponent(5);
			orderedItem.setSubTotalAmount(Float.parseFloat(subtotal.getText()));
			
			orderedItems.add(orderedItem);
		}
		Order order = new Order();
		order.setOrderedItems(orderedItems);
		order.setTotalAmount(totalPrice);
		
		OrderTransaction orderTransaction = new OrderTransaction();
		orderTransaction.setOrder(order);
		orderTransaction.setOrderMode("");
		this.creditCardNumber = creditNumber.getText();
		 
		this.orderTransaction = orderTransaction;
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

		tabbedPanel = new JTabbedPane();
		// Menu tab
		
		tabbedPanel.addTab("Menu List", getMenuPanel(font));

		// Cart tab
		tabbedPanel.addTab("Cart List", getCartPanel(font));
		//Payment tab
		tabbedPanel.addTab("Payment", getPaymentPanel(font));
		tabbedPanel.setEnabledAt(2, false);
		tabbedPanel.setEnabledAt(1, false);
		
		add(tabbedPanel);

;	}
	

	// Define a generic font style
	private Font getFontStyle() {
		
		Font font = new Font ("Yu Mincho Light", Font.PLAIN, 30);
		
		return font;
		
	}
}
