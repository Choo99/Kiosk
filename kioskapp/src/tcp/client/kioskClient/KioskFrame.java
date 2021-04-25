package tcp.client.kioskClient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

import controller.ItemProductController;
import model.kioskapp.itemproduct.ItemProduct;
import model.kioskapp.order.Order;
import model.kioskapp.orderedItem.OrderedItem;
import model.kioskapp.ordertransaction.OrderTransaction;

public class KioskFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	// Attribute used to format the float to 2 decimal point
	private static DecimalFormat format = new DecimalFormat("0.00");


	// Private attribute for frame size setting
	private int height =800;
	private int width = 1000;
	
	private JTabbedPane tabbedPanel;
	
	private JPanel menu;
	private JPanel cartListPanel;
	private JPanel paymentListPanel;
	
	private JLabel totalPriceLabel;
	private JLabel priceLabel;
	
	private JFormattedTextField creditCardNo;
	//private JTextField creditCardNo;
	
	private OrderTransaction orderTransaction;
	
	private int cartIndex = 0;
	
	private float totalPrice = 0;
	private boolean transactionStatus;
	private String orderMode;
	private String printMessage;
	
	/**
	 * Create the frame	
	 */
	public KioskFrame() {
		
		// Style the frame
		setTitle("Kiosk Application");
		setSize(width, height);
		
		// Initial component
		cartListPanel = new JPanel(new GridLayout(3,1));
		paymentListPanel = new JPanel(new GridLayout(3,1));
		totalPriceLabel = new JLabel("RM 0.00");
		creditCardNo = new JFormattedTextField(createFormatter("################"));

		// Initial print message for detect error purpose
		printMessage = "I am default message";
		
		// Style the component
		paymentListPanel.setBackground(new Color(255, 250, 240));
		
		// Center the frame on the screen
        this.setLocationRelativeTo(null);		
		
		// Frame must close on X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Frame cannot be resize
		this.setResizable(false);
				
		// Display component
		loadComponent();
		
	}
	

	/**
	 * This method set the printed message to show the result
	 * @param message
	 */
	public void setPrintMessage(String message) {
		printMessage = message;
	}
	
	/**
	 *  This method set the status of validation result
	 * @param status
	 */
	public void setTransactionStatus(boolean status) {
		transactionStatus = status;
	}
	
	/**
	 *  This method get the credit card number from JTextField
	 * @return
	 */
	public String getCreditCardNumber() {
		return creditCardNo.getText();
	}
	
	
	/**
	 *  This method freeze the thread to wait for user input
	 * @throws InterruptedException
	 */
	public void waitForInput() throws InterruptedException {
		synchronized(this) {
			wait();
		}
	}
	
	/**
	 *  This method would release the freeze thread
	 * @throws InterruptedException
	 */
	public void release() throws InterruptedException {
		synchronized(this) {
			notifyAll();
		}
	}
	
	
	/**
	 *  This method will return order transaction result
	 * @return object orderTransaction
	 */
	public OrderTransaction getOrderTransaction() {
		return this.orderTransaction;
	}
	
	/**
	 *  This method will return menuPanel after initialize all the component 
	 * @param font
	 * @return menuPanel
	 */
	private JPanel getMenuPanel(Font font)
	{
			JPanel menuPanel = new JPanel();
			
		
			// Menu panel component object
			JLabel listLabel = new JLabel("List Of Food");
			ImageIcon icon = null;
			try {
				Image image = ImageIO.read(new File("src/image/Checklist.png"));
				icon = new ImageIcon(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			listLabel.setIcon(icon);
			
			// Style the menu panel
        	menuPanel.setBackground(Color.white);
            menuPanel.setLayout(new BorderLayout());
			
			// Style list label
            listLabel.setFont(font);	
            listLabel.setHorizontalAlignment(JLabel.CENTER);
            
            // Add list label to a panel is easier to manage
            JPanel lblPanel = new JPanel();
            lblPanel.add(listLabel);
            lblPanel.add(Box.createRigidArea(new Dimension(0,60)));
			lblPanel.setBackground(new Color(255, 248, 220));

			// Get the product list from database and load the corresponding images
            ListContent content = new ListContent();
            ArrayList<ItemProduct> productList = new ArrayList<ItemProduct>();
            ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
            productList = content.setProductList();
            images = content.setImages();
			setMenuList(productList,images);
			
			// Add menu panel's components
			menuPanel.add(BorderLayout.NORTH,lblPanel);
			menuPanel.add(Box.createRigidArea(new Dimension(0,100)));
			JScrollPane menuScrollPanel = new JScrollPane(menu);
			menuPanel.add(BorderLayout.CENTER,menuScrollPanel);

			return menuPanel;
	}
	
	/**
	 * This method will set the detail's of menuListPanel before adding into menuPanel
	 * @param productList
	 * @param images
	 */
	private void setMenuList(ArrayList<ItemProduct> productList,ArrayList<ImageIcon> images) {
	
		/**
		 * Set the gridLayout size of menuList
		 * In order to get the arrangement of gridLayout fill from left to right and top to down
		 * GridLayout x-axis should half the productList size
		 * Odd number will plus one to get the actual half of size
		 */
		int gridSize = 0;
		if(productList.size() % 2 == 0) {gridSize = productList.size() / 2;}
		else {gridSize = (productList.size() + 1) / 2;}

		// Set and style menu layout
		menu = new JPanel(new GridLayout(gridSize,1));
		menu.setBackground(Color.WHITE);

		for(int counter = 0;counter<productList.size();counter++) {
			
			// Get product details and image 
			ItemProduct itemProduct = productList.get(counter);
			ImageIcon image = images.get(counter);
			
			// Set product detail and image in JLabel 
			JLabel productLabel = new JLabel(itemProduct.getName());
			productLabel.setFont(new Font("Dialog", Font.BOLD, 30));
			
			JLabel productImgLabel = new JLabel(image);
			JLabel productPriceLabel = new JLabel("RM" + format.format(itemProduct.getPrice()));
			productPriceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
			
			JButton addCartBtn = new JButton("Add Cart");
			addCartBtn.setPreferredSize(new Dimension(120,40));	
			addCartBtn.setFont(new Font("Dialog", Font.BOLD, 16));
			
			JPanel allInOnePanel = new JPanel();
			allInOnePanel.setLayout(new BorderLayout());
			allInOnePanel.setBackground(Color.WHITE);
		
			
			/**
			* Setting border of all in one panel
			* This panel is used to display product image with product name at the border
			*/
			Border border = BorderFactory.createLineBorder(new Color(70, 130, 180));
			allInOnePanel.setBorder(BorderFactory.createTitledBorder
					(border, productLabel.getText(), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION,new Font("Dialog", Font.BOLD, 16)));
			allInOnePanel.add(BorderLayout.CENTER,productImgLabel);
			productImgLabel.setHorizontalAlignment(JLabel.CENTER);
			
			// Second panel is used to display product's price and addChart button
			JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			secondPanel.setBackground(Color.white);
			Border secondBorderLine = BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(70, 130, 180));
			secondBorderLine = BorderFactory.createTitledBorder(secondBorderLine);
			secondPanel.setBorder(secondBorderLine);
			secondPanel.add(productPriceLabel);
			secondPanel.add(Box.createRigidArea(new Dimension(200 - addCartBtn.getPreferredSize().width,0)));
			secondPanel.add(addCartBtn);
			
			// Add second panel into South of all in one panel
			allInOnePanel.add(BorderLayout.SOUTH,secondPanel);
			menu.add(allInOnePanel);
			
			KioskFrame frame = this;
			
			/**
			 * addCartBtn will add the detail's of that product panel to cartListPanel
			 * addCartBtn will disable when the selected item is in the cartListPanel
			 * repaint and revalidate to refresh the frame
			 */
			addCartBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					setCartListPanel(itemProduct,image,addCartBtn);
					tabbedPanel.setEnabledAt(2, true);
					frame.revalidate();
					frame.repaint();
				}
				
			});
		}
		
	}

	/**
	 *  This method will set cart panel
	 * @param font
	 * @return JPanel cartPanel
	 */
	private JPanel getCartPanel(Font font)
	{

		JPanel cartPanel = new JPanel(new BorderLayout());

		// Cart panel's component object
		JButton processPaymentBtn = new JButton ("Process Payment");
 		JLabel orderedList = new JLabel("Ordered List");
 		ImageIcon icon = null;
		try {
			Image image = ImageIO.read(new File("src/image/shopping-cart.png"));
			icon = new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		orderedList.setIcon(icon);
	
		// Style cart panel
		cartListPanel.setBackground(Color.white);
		
        // Style ordered list       
        orderedList.setFont(font);
		orderedList.setBackground(Color.white);
 		orderedList.setHorizontalAlignment(JLabel.CENTER);

		// Style the process payment button
		processPaymentBtn.setFont(font);
		
		// Create a panel to display the title of cart panel
		JPanel labelPanel = new JPanel();
		labelPanel.add(Box.createRigidArea(new Dimension(0,60)));
		labelPanel.setBackground(new Color(255, 248, 220));
		labelPanel.add(orderedList);
		
		JScrollPane scrollPanel = new JScrollPane(cartListPanel);
		
		/** 
		* This panel will display the total price 
		* and contain process payment button	
		*/	
		JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pricePanel.setBackground(Color.white);
		
		JLabel totalLabel = new JLabel("Total Price : ");

		// Style the component	
		totalPriceLabel.setFont(font);
		totalLabel.setFont(font);
			
		pricePanel.add(totalLabel);
		pricePanel.add(Box.createRigidArea(new Dimension(5,50)));
		pricePanel.add(totalPriceLabel);
		pricePanel.add(Box.createRigidArea(new Dimension(5,0)));
		pricePanel.add(processPaymentBtn);

		// Add all components to panel
		cartPanel.add(BorderLayout.NORTH,labelPanel);
		cartPanel.add(BorderLayout.CENTER,scrollPanel);
		cartPanel.add(BorderLayout.SOUTH,pricePanel);

		/**
		 * processPaymentBtn will add the component inside cartListPanel into paymentListPanel
		 * Disable all other tabs and jump to payment tab
		 */
		processPaymentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPaymentListPanel();
				priceLabel.setText("RM " + format.format(totalPrice));
				tabbedPanel.setEnabledAt(0, false);
				tabbedPanel.setEnabledAt(1, false);
				tabbedPanel.setEnabledAt(2, false);
				tabbedPanel.setEnabledAt(3, true);
				tabbedPanel.setSelectedIndex(3);
			}
		});
		
		return cartPanel;
	}
	
	/**
	 *  This method will add detail's of product into cartList
	 * @param product
	 * @param image
	 * @param addCartBtn
	 */
	public void setCartListPanel(ItemProduct product,ImageIcon image,JButton addCartBtn) {
		
		JLabel noLabel = new JLabel(Integer.toString(++cartIndex));
		JLabel productLabel = new JLabel(product.getName());
		JLabel productImgLabel = new JLabel();
		
		// Rescaled image
		ListContent listContent = new ListContent();
		image = listContent.setRescaledImages(image);
		
		productImgLabel.setIcon(image);
		SpinnerModel model = new SpinnerNumberModel(1,1,10,1);
		JSpinner quantitySpinner = new JSpinner(model);
		
		// Set JSpinner not editable
		((DefaultEditor) quantitySpinner.getEditor()).getTextField().setEditable(false);
		quantitySpinner.setPreferredSize(new Dimension(50,20));
		JLabel productPriceLabel = new JLabel("RM " + format.format(product.getPrice()));
		float total = product.getPrice() * (Integer)quantitySpinner.getValue();
		
		totalPrice += total;
		totalPriceLabel.setText("RM " + format.format(totalPrice));
		JLabel subtotalPriceLabel = new JLabel(format.format(total));
		
		/** Create remove button
		* This button will remove the repesctive row from the chart list
		*/
		JButton removeBtn = new JButton("Remove");
		removeBtn.setPreferredSize(new Dimension(100,40));	
		
		// This panel is to hold removeBtn
		JPanel btnPanel = new JPanel();
		btnPanel.add(removeBtn);
		btnPanel.setPreferredSize(new Dimension(100,50));	
		btnPanel.setOpaque(false);
		
		/** 
		* This panel is to display the ordered item's list
		* Each row of list includes numbering, product name, product image, quantity, original price
		* subtotal with a remove button
		*/
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createRigidArea(new Dimension(3 - noLabel.getPreferredSize().width,0)));
		panel.add(noLabel);
		panel.add(Box.createRigidArea(new Dimension(300 - productLabel.getPreferredSize().width,0)));
		panel.add(productLabel);
		panel.add(Box.createRigidArea(new Dimension(200 - productImgLabel.getPreferredSize().width,0)));
		panel.add(productImgLabel);
		panel.add(Box.createRigidArea(new Dimension(100 - quantitySpinner.getPreferredSize().width,0)));
		panel.add(quantitySpinner);
		panel.add(Box.createRigidArea(new Dimension(100 - productPriceLabel.getPreferredSize().width,0)));
		panel.add(productPriceLabel);
		panel.add(Box.createRigidArea(new Dimension(100 - subtotalPriceLabel.getPreferredSize().width,0)));
		panel.add(subtotalPriceLabel);
		panel.add(Box.createRigidArea(new Dimension(200 - btnPanel.getPreferredSize().width,0)));
		panel.add(btnPanel);
		
		// Add border to the panel
		Border border = BorderFactory.createTitledBorder(" ");
		panel.setBorder(border);
		
		// GridLayout size will grow as item in the cart list is more than three
		if(cartIndex > 3) {
			GridLayout grid = new GridLayout(cartIndex,1);
			cartListPanel.setLayout(grid);
		}
		cartListPanel.add(panel);
		
		// Disable the addCartBtn of the selected item at the menu panel 
		addCartBtn.setEnabled(false);
		
		/**
		 * As quantity of spinner changed, subtotal of item and total amount of order will change immediately
		 * Minus the previous total and add the current total to get the actual number
		 */
		quantitySpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				float newTotal = product.getPrice() * (Integer)quantitySpinner.getValue();
				float oldTotal = Float.parseFloat(subtotalPriceLabel.getText());
				subtotalPriceLabel.setText(format.format(newTotal));
				totalPrice += newTotal - oldTotal;
				totalPriceLabel.setText("RM " + format.format(totalPrice));
			}
		});
		
		KioskFrame frame = this;
		
		/**
		 * removeBtn will remove panel of the selected product from cartListPanel
		 * Minus counter of cartIndex 
		 * Calculate new amount of order after entire product remove from list
		 * Refresh counter of product in the list
		 * Set enable to corresponding addCartBtn in the menuList
		 * Cart tab will disable and jump to menu tab when all product in cartList has been removed
		 */
		removeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cartListPanel.remove(panel);
				cartIndex--;
				float newTotal = product.getPrice() * (Integer)quantitySpinner.getValue();
				totalPrice -= newTotal ;
				totalPriceLabel.setText("RM " +format.format(totalPrice));
				refreshCartIndex();
				addCartBtn.setEnabled(true);
				if(cartIndex > 3) {
					GridLayout grid = new GridLayout(cartIndex,1);
					cartListPanel.setLayout(grid);
				}
				if(cartIndex == 0) {
					tabbedPanel.setSelectedIndex(1);
					tabbedPanel.setEnabledAt(2, false);
				}
				frame.revalidate();
				frame.repaint();
			}
			
		});
	}
	
	/**
	 * This method will return paymentPanel
	 * @param font
	 * @return JPanel paymentPanel
	 */
	private JPanel getPaymentPanel(Font font) {
		
		JPanel paymentPanel = new JPanel(new BorderLayout());
		paymentPanel.setBackground(new Color(255, 248, 220));

		// Create paymentPanel components	
		JLabel paymentLabel = new JLabel("Payment");
		ImageIcon icon = null;
		try {
			Image image = ImageIO.read(new File("src/image/credit-card.png"));
			icon = new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		paymentLabel.setIcon(icon);
		
		// Style component
		paymentLabel.setFont(font);
		paymentLabel.setHorizontalAlignment(JLabel.CENTER);
		
		// This panel is to hold the title of this tab 
		JPanel labelPanel = new JPanel();
		labelPanel.setOpaque(false);
		labelPanel.add(paymentLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0,60)));
		
		/** nestedTotalPanel avoid the totalPanel fill itself to the whole East 
		*   of payementPanel which looks very ugly
		*/
		JPanel totalPanel = getTotalPanel(font);
		JPanel nestedTotalPanel = new JPanel();
		nestedTotalPanel.setOpaque(false);
		
		// This panel is to display decoration image 
		JPanel imagePanel = new JPanel();
		imagePanel.setOpaque(false);
		ImageIcon imageIcon = null;
		try {
			Image image = ImageIO.read(new File("src/image/neko2.png"));
			Image newImage = image.getScaledInstance(101, 114, Image.SCALE_DEFAULT);
			imageIcon = new ImageIcon(newImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel imageLabel = new JLabel(imageIcon);
		JLabel imageLabel2 = new JLabel(imageIcon);
		JLabel imageLabel3 = new JLabel(imageIcon);
		JLabel imageLabel4 = new JLabel(imageIcon);
		JLabel imageLabel5 = new JLabel(imageIcon);
		JLabel imageLabel6 = new JLabel(imageIcon);
		JLabel imageLabel7 = new JLabel(imageIcon);
		JLabel imageLabel8 = new JLabel(imageIcon);
		imagePanel.add(imageLabel);
		imagePanel.add(imageLabel2);
		imagePanel.add(imageLabel3);
		imagePanel.add(imageLabel4);
		imagePanel.add(imageLabel5);
		imagePanel.add(imageLabel6);
		imagePanel.add(imageLabel7);
		imagePanel.add(imageLabel8);
		
		nestedTotalPanel.add(totalPanel);
		JScrollPane scrollPanel = new JScrollPane(paymentListPanel);
		scrollPanel.setBackground(Color.white);
		
		paymentPanel.add(BorderLayout.NORTH,labelPanel);
		paymentPanel.add(BorderLayout.CENTER,scrollPanel);
		paymentPanel.add(BorderLayout.EAST,nestedTotalPanel);
		paymentPanel.add(BorderLayout.SOUTH,imagePanel);
		return paymentPanel;
	}
	
	/**
	 * This method return totalPanel that contain totalPriceLabel, creditCardNo JTextField
	 * and confirmBtn, backBtn
	 * @param font
	 * @return JPanel totalPanel
	 */
	private JPanel getTotalPanel(Font font) {
		
		JPanel totalPanel = new JPanel();

		// Style the panel
		totalPanel.setLayout(new BoxLayout(totalPanel,BoxLayout.Y_AXIS));
		totalPanel.setBackground(Color.white);
		
		// Panel components
		JLabel orderSummaryLabel = new JLabel("Order Summary");
		JLabel totalPriceLabel = new JLabel("Total: ");
		JLabel creditCardNoLabel = new JLabel("Credit Card Number: ");
		JLabel tax = new JLabel("(included GST)");
		JButton confirmBtn = new JButton("Confirm");
		JButton backBtn = new JButton("Back");
		priceLabel = new JLabel("RM " + format.format(totalPrice));
		
		// Styling all component
		orderSummaryLabel.setFont(font);
		totalPriceLabel.setFont(font);
		priceLabel.setFont(font);
		tax.setFont(new Font("Yu Mincho Light", Font.ITALIC, 15));
		tax.setHorizontalAlignment(JLabel.CENTER);
		creditCardNo.setFont(new Font("Yu Mincho Light", Font.ITALIC, 15));
		creditCardNo.setForeground(Color.GRAY);
		confirmBtn.setFont(font);
		backBtn.setFont(font);
		creditCardNoLabel.setFont(new Font("Yu Mincho Light", Font.PLAIN, 24));
		
		JPanel orderSummary = new JPanel();
		orderSummary.setOpaque(false);
		orderSummary.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		orderSummary.add(orderSummaryLabel);
		
		JPanel pricePanel = new JPanel();
		pricePanel.setOpaque(false);
		pricePanel.add(totalPriceLabel);
		pricePanel.add(priceLabel);
		
		JPanel creditCardLabelPanel = new JPanel();
		creditCardLabelPanel.setOpaque(false);
		creditCardLabelPanel.add(creditCardNoLabel);
		
		JPanel creditCardPanel = new JPanel();
		creditCardPanel.setOpaque(false);
		creditCardNo.setPreferredSize(new Dimension(210,30));
		creditCardPanel.add(creditCardNo);
		
		JPanel confirmBtnPanel = new JPanel();
		confirmBtnPanel.setOpaque(false);
		confirmBtn.setPreferredSize(new Dimension(210,40));
		confirmBtn.setEnabled(false);
		confirmBtnPanel.add(confirmBtn);
		
		JPanel backBtnPanel = new JPanel();
		backBtnPanel.setOpaque(false);
		backBtn.setPreferredSize(new Dimension(210,40));
		backBtnPanel.add(backBtn);
		
		totalPanel.add(orderSummary);
		totalPanel.add(pricePanel);
		totalPanel.add(tax);
		totalPanel.add(creditCardLabelPanel);
		totalPanel.add(creditCardPanel);
		totalPanel.add(confirmBtnPanel);
		totalPanel.add(backBtnPanel);
		totalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// This listener will disable confirm button if the creditCardNo is not 16 digit
		creditCardNo.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(creditCardNo.getText().replaceAll("[^0-9.]", "").length() == 16) {
					confirmBtn.setEnabled(true);
				}
				else {
					confirmBtn.setEnabled(false);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(creditCardNo.getText().replaceAll("[^0-9]", "").length() == 16) {
					confirmBtn.setEnabled(true);
				}
				else {
					confirmBtn.setEnabled(false);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {}
		});

		// This button is to leave the payment tab and back to cart tab
		backBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				tabbedPanel.setEnabledAt(0, true);
				tabbedPanel.setEnabledAt(1, true);
				tabbedPanel.setEnabledAt(2, true);
				tabbedPanel.setEnabledAt(3, false);
				paymentListPanel.removeAll();
				tabbedPanel.setSelectedIndex(2);
			}
			
		});

		confirmBtn.addActionListener(this);
		
		return totalPanel;
	}
	
	/**
	 * This panel will retrieve all panel in cartList panel to get the required information
	 */
	private void setPaymentListPanel() {
		
		Component[] components = cartListPanel.getComponents();
		for(int count = 0; count < cartIndex;count++) {
			
			JPanel panel = (JPanel)components[count];
			panel.setOpaque(false);

			JPanel newPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			newPanel.setBackground(Color.white);

			JLabel noLabel = new JLabel(Integer.toString(count + 1));	

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
			
			//growing paymentList layout
			if(cartIndex >= 3) {
				GridLayout grid = new GridLayout(count + 1,1);
				paymentListPanel.setLayout(grid);
			}
			paymentListPanel.add(newPanel);
		}
	}
	
	/**
	 * This method will initialize the welcomePanel
	 * @param font
	 * @return
	 */
	private JPanel getWelcomePanel(Font font)
	{
		JPanel welcomePanel = new JPanel(new BorderLayout());
		
		// Style the panel color
		welcomePanel.setBackground(new Color(255, 228, 196));
		
		// Panel components
		JLabel textLabel = new JLabel("Welcome To Mc Daniel!");
		JButton comfirmButton=new JButton("Next");
		JRadioButton eatIn = new JRadioButton("Eat In");
		JRadioButton takeAway = new JRadioButton("Take Away");

		// Insert imageIcon
		ImageIcon logoIcon = null;
		try {
			Image logo = ImageIO.read(new File("src/image/McDonaldLogo.png"));
			logoIcon = new ImageIcon(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		JLabel logoLabel = new JLabel(logoIcon);
		
		// Style the component
		eatIn.setOpaque(false);
		takeAway.setOpaque(false);
		eatIn.setActionCommand("Eat In");
		takeAway.setActionCommand("Take Away");
		textLabel.setFont(font);
		eatIn.setFont(font);
		takeAway.setFont(font);
		comfirmButton.setFont(font);
		
		// Group the radio button and only allow customer to choose one order mode
		ButtonGroup selection = new ButtonGroup();
		selection.add(eatIn);
		selection.add(takeAway);		
		
		// Add Component into gridLayout
		JPanel panel = new JPanel(new GridLayout(3,1));
		panel.setOpaque(false);
				
		// Open first panel to put icon
		JPanel innerPanel = new JPanel();
		innerPanel.setOpaque(false);
		innerPanel.add(logoLabel);
		
		// Open inner panel to put text 
		JPanel innerPanel2 = new JPanel();	
		innerPanel2.setOpaque(false);
		innerPanel2.add(textLabel);

		// Open second inner panel to put 2 JRadio button
		JPanel innerPanel3 = new JPanel(new GridLayout(2,1));
		innerPanel3.setOpaque(false);
		
		JPanel innerBtnPanel = new JPanel();
		innerBtnPanel.setOpaque(false);
		innerBtnPanel.add(comfirmButton);
		
		JPanel innerRadioPanel = new JPanel();
		innerRadioPanel.setOpaque(false);
		innerRadioPanel.add(eatIn);
		innerRadioPanel.add(Box.createRigidArea(new Dimension(100,0)));
		innerRadioPanel.add(takeAway);
		
		innerPanel3.add(innerRadioPanel);
		innerPanel3.add(innerBtnPanel);
		
		panel.add(innerPanel);
		panel.add(innerPanel2);
		panel.add(innerPanel3);
		JPanel panel2 = new JPanel();
		panel2.setOpaque(false);
		panel2.add(panel);
		panel2.setBackground(new Color(255, 228, 196));
		
		// This panel is used for decoration to provide empty space above the icon
		JPanel emptyPanel = new JPanel();
		emptyPanel.setOpaque(false);
		emptyPanel.add(Box.createRigidArea(new Dimension(0,100)));
		
		// Add into panel
		welcomePanel.add(BorderLayout.NORTH,emptyPanel);
		welcomePanel.add(BorderLayout.CENTER,panel2);

		// Add action listener to button
		comfirmButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				orderMode = selection.getSelection().getActionCommand();
				tabbedPanel.setSelectedIndex(1);
				tabbedPanel.setEnabledAt(1, true);
			}
			
		});
		
		return welcomePanel;
	}
	
	/**
	 * This method will get all information in paymentListPanel and store in an object
	 */
	private void getOrderedItem() {
		
		Component[] component = paymentListPanel.getComponents();
		List<OrderedItem> orderedItems = new ArrayList<OrderedItem>();
		for(int counter = 0; counter < component.length ; counter++) {
			
			OrderedItem orderedItem = new OrderedItem();
			JPanel panel = (JPanel)component[counter];
			
			JLabel productName = (JLabel)panel.getComponent(3);
			ItemProductController converter = new ItemProductController();

			ItemProduct itemProduct = new ItemProduct();
			itemProduct.setItemProduct(converter.getItemProductID(productName.getText()));
			orderedItem.setItemProduct(itemProduct);
			
			JLabel quantity = (JLabel)panel.getComponent(7);
			orderedItem.setQuantity(Integer.parseInt(quantity.getText()));
			
			JLabel subtotal = (JLabel)panel.getComponent(9);
			orderedItem.setSubTotalAmount(Float.parseFloat(subtotal.getText()));
			
			orderedItems.add(orderedItem);
		}
		Order order = new Order();
		order.setOrderedItems(orderedItems);
		order.setTotalAmount(totalPrice);
		
		OrderTransaction orderTransaction = new OrderTransaction();
		orderTransaction.setOrder(order);
		orderTransaction.setOrderMode(orderMode);
		orderTransaction.setAmountCharged(totalPrice);
		 
		this.orderTransaction = orderTransaction;
	}
	
	/**
	 * This method will refresh index of the cart list after product remove from cart list
	 */
	private void refreshCartIndex() {
		
		for(int currentPanel = 0;currentPanel < cartIndex; currentPanel++) {
			
			JPanel panel = (JPanel)cartListPanel.getComponent(currentPanel);
			JLabel label = (JLabel)panel.getComponent(1);
			label.setText(Integer.toString(currentPanel + 1 ));
			
		}
	}
	
	/**
	 * This method load four panel and add into tabbedPane
	 */
	private void loadComponent()
	{
		tabbedPanel = new JTabbedPane();

		// Get font
		Font font = this.getFontStyle();
		
		//Welcome tab
		tabbedPanel.addTab("Welcome", getWelcomePanel(font));
		
		// Menu tab
		tabbedPanel.addTab("Menu List", getMenuPanel(font));

		// Cart tab
		tabbedPanel.addTab("Cart List", getCartPanel(font));
		
		// Payment tab
		tabbedPanel.addTab("Payment", getPaymentPanel(font));

		tabbedPanel.setEnabledAt(1, false);
		tabbedPanel.setEnabledAt(2, false);
		tabbedPanel.setEnabledAt(3, false);
		
		add(tabbedPanel);
		
	}
	
	// Define a generic font style
	private Font getFontStyle() {
		
		Font font = new Font ("Yu Mincho Light", Font.PLAIN, 30);
		
		return font;
		
	}

	/**
	 * This method will enable all addCartBtn in menuList again after customer complete an order
	 */
	private void resetMenuButton() {
		
		//get all panel from menu panel
		Component[] components = menu.getComponents();
		
		//retrieve inner panel one by one until the buttonPanel
		for(Component component : components) {
			JPanel product = (JPanel)component;
			JPanel cartPanel = (JPanel)product.getComponent(1);
			JButton addCartBtn = (JButton)cartPanel.getComponent(2);
			addCartBtn.setEnabled(true);
		}
		
	}
	
	/**
	 * This method will reset all needed attribute 
	 * Ready to next customer input
	 */
	private void resetAllComponent() {
		tabbedPanel.setSelectedIndex(0);
		tabbedPanel.setEnabledAt(3, false);
		tabbedPanel.setEnabledAt(0, true);		
		cartListPanel.removeAll();
		paymentListPanel.removeAll();
		resetMenuButton();
		creditCardNo.setText("");
		cartIndex = 0; 
		totalPrice = 0;
	}
	
	/**
	*/
	private MaskFormatter createFormatter(String symbol) {
		
		MaskFormatter formatter = null;
		
		try {
			formatter = new MaskFormatter(symbol);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return formatter;
	}
	
	/**
	 * This method is actionListener of confirmBtn
	 * This method will get all information needed to database and store in object
	 * Release the thread to proceed request to server
	 * Freeze the thread before server return a result
	 * Display succeed message when transaction is completed and reset component
	 * Display error message when transaction is fail
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			getOrderedItem();
			release();
			
			waitForInput();
			if(transactionStatus) {
				JOptionPane.showMessageDialog(this,"Transact Successfully! Please take your receipt");
				resetAllComponent();
			}
			else {
				JOptionPane.showMessageDialog(this,
					    printMessage,
					    "Error",
					    JOptionPane.WARNING_MESSAGE); 
				creditCardNo.setText("");
				orderTransaction = null;
			}
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		} 
	}
}
