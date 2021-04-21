package tcpKioskClient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import kioskapp.itemproduct.ItemProduct;
import kioskapp.order.Order;
import kioskapp.ordereditem.OrderedItem;
import kioskapp.ordertransaction.OrderTransaction;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.ItemProductController;

public class KioskFrame extends JFrame implements ActionListener{

private static final long serialVersionUID = 1L;
	private JTextField creditCardNo ;
	private static DecimalFormat format = new DecimalFormat("0.00");


	// Private attribute for frame size setting
	private int height =800;
	private int width = 1000;
	private JPanel menu;
	private JPanel cartListPanel;
	private int cartIndex = 0;
	private JPanel paymentListPanel;
	private float totalPrice = 0;
	private JLabel totalPriceLabel;
	private JLabel priceLabel;
	private JTabbedPane tabbedPanel;
	private OrderTransaction orderTransaction;
	private boolean transactionStatus;
	private String orderMode;
	private String printMessage;

	//private JPanel menuPanel;
	//private JScrollPane menuScrollPanel;
	
	/**
	 * Create the frame.
	 */
	public KioskFrame() {
		
		//getContentPane().setLayout(new BorderLayout());
		this.setTitle("Kiosk Application");
		this.setSize(width, height);
		
		cartListPanel = new JPanel(new GridLayout(3,1));
		paymentListPanel = new JPanel(new GridLayout(3,1));
		
		paymentListPanel.setBackground(new Color(255, 250, 240));
		printMessage = "I am default message";
		
		totalPriceLabel = new JLabel("RM 0.00");
		//menuPanel = new JPanel(new BorderLayout());
		
		//menuScrollPanel = new JScrollPane();
		
		// Center the frame on the screen
        this.setLocationRelativeTo(null);		
		
		// Must close on X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		//this.setResizable(false);
		
		//this.setFocusableWindowState(false);
				
		// Display component
		loadComponent();
		
	}
	
	public void setPrintMessage(String message) {
		printMessage = message;
	}
	
	public void setTransactionStatus(boolean status) {
		transactionStatus = status;
	}
	
	public String getCreditCardNumber() {
		return creditCardNo.getText();
	}
	
	public void waitTime() throws InterruptedException {
		synchronized(this) {
			wait(5000);
		}
	}
	
	public void waitForInput() throws InterruptedException {
		synchronized(this) {
			wait();
		}
	}
	
	public void release() throws InterruptedException {
		synchronized(this) {
			notifyAll();
		}
	}
	
	
	//Return result
	public OrderTransaction getOrderTransaction() {
		return this.orderTransaction;
	}
	
	// To return menu panel 
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
            
            //add list label to panel
            JPanel lblPanel = new JPanel();
            lblPanel.add(listLabel);
            lblPanel.add(Box.createRigidArea(new Dimension(0,60)));
			lblPanel.setBackground(new Color(255, 248, 220));

            ListContent content = new ListContent();
            ArrayList<ItemProduct> productList = new ArrayList<ItemProduct>();
            ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
            productList = content.setProductList();
            images = content.setImages();
			JPanel list = setMenuList(productList,images);
			
			
			// Add menu panel's components
			menuPanel.add(BorderLayout.NORTH,lblPanel);
			menuPanel.add(Box.createRigidArea(new Dimension(0,100)));
			JScrollPane menuScrollPanel = new JScrollPane(list);
			menuPanel.add(BorderLayout.CENTER,menuScrollPanel);

			return menuPanel;
	}
	
	private JPanel setMenuList(ArrayList<ItemProduct> productList,ArrayList<ImageIcon> images) {
	
		//setting gridLayout size
		int gridSize = 0;
		if(productList.size() % 2 == 0) {gridSize = productList.size() / 2;}
		else {gridSize = (productList.size() + 1) / 2;}

		menu = new JPanel(new GridLayout(gridSize,1));
	

		for(int counter = 0;counter<productList.size();counter++) {
			
			//get product details and image 
			ItemProduct itemProduct = productList.get(counter);
			ImageIcon image = images.get(counter);
			
			//set product detail and image in JLabel 
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
		
			
			//setting border of panel
			Border border = BorderFactory.createLineBorder(new Color(70, 130, 180));
			allInOnePanel.setBorder(BorderFactory.createTitledBorder
					(border, productLabel.getText(), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION,new Font("Dialog", Font.BOLD, 16)));
			allInOnePanel.add(BorderLayout.CENTER,productImgLabel);
			productImgLabel.setHorizontalAlignment(JLabel.CENTER);
			
			JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			secondPanel.setBackground(Color.white);
			Border secondBorderLine = BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(70, 130, 180));
			secondBorderLine = BorderFactory.createTitledBorder(secondBorderLine);
			secondPanel.setBorder(secondBorderLine);
			secondPanel.add(productPriceLabel);
			secondPanel.add(Box.createRigidArea(new Dimension(200 - addCartBtn.getPreferredSize().width,0)));
			secondPanel.add(addCartBtn);
			

			allInOnePanel.add(BorderLayout.SOUTH,secondPanel);
			menu.add(allInOnePanel);
			
			KioskFrame frame = this;
			addCartBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					setCartListPanel(itemProduct,image,addCartBtn);
					tabbedPanel.setEnabledAt(2, true);
					frame.revalidate();
					frame.repaint();
				}
				
			});
		}
		menu.setBackground(Color.WHITE);
		return menu;
	}

	// To return cart panel
	private JPanel getCartPanel(Font font)
	{

		JPanel cartPanel = new JPanel(new BorderLayout());

		// cart panel component object
		creditCardNo = new JTextField ();
 		JLabel orderedList = new JLabel("Ordered List");
 		ImageIcon icon = null;
		try {
			Image image = ImageIO.read(new File("src/image/shopping-cart.png"));
			icon = new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		orderedList.setIcon(icon);
		
		JButton processPayment = new JButton ("Process Payment");

		// Style cart panel
		cartListPanel.setBackground(Color.white);
	
		// Style credit card textField
		creditCardNo.setFont(font);		

        // Style ordered list       
        orderedList.setFont(font);
		orderedList.setBackground(Color.white);
 		orderedList.setHorizontalAlignment(JLabel.CENTER);

		// Style the process payment button
		processPayment.setFont(font);
		
		processPayment.addActionListener(new ActionListener() {

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
		
		JPanel labelPanel = new JPanel();
		labelPanel.add(Box.createRigidArea(new Dimension(0,60)));
		labelPanel.setBackground(new Color(255, 248, 220));
		labelPanel.add(orderedList);
		
		JScrollPane scrollPanel = new JScrollPane(cartListPanel);
				
		JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel totalLabel = new JLabel("Total Price : ");		
		totalPriceLabel.setFont(font);
		totalLabel.setFont(font);
		totalLabel.setVerticalTextPosition(1);
		creditCardNo.setPreferredSize(new Dimension(300,50));
		pricePanel.setBackground(Color.white);
		
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
		btnPanel.setOpaque(false);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);

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
		if(cartIndex > 3) {
			GridLayout grid = new GridLayout(cartIndex,1);
			cartListPanel.setLayout(grid);
		}
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
	
	private JPanel getPaymentPanel(Font font) {
		
		JPanel paymentPanel = new JPanel(new BorderLayout());
		paymentPanel.setBackground(new Color(255, 248, 220));
		
		//JPanel paymentDetailPanel = new JPanel();
		
		
		JLabel paymentLabel = new JLabel("Payment");
		ImageIcon icon = null;
		try {
			Image image = ImageIO.read(new File("src/image/credit-card.png"));
			icon = new ImageIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		paymentLabel.setIcon(icon);
		
		paymentLabel.setFont(font);
		paymentLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setOpaque(false);
		labelPanel.add(paymentLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0,60)));

		JPanel totalPanel = getTotalPanel(font);
		JPanel nestedTotalPanel = new JPanel();
		nestedTotalPanel.setOpaque(false);
		
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
		//paymentDetailPanel.add(scrollPanel);
		//paymentDetailPanel.add(totalPanel);
		
		paymentPanel.add(BorderLayout.NORTH,labelPanel);

		//paymentPanel.add(BorderLayout.CENTER,paymentDetailPanel);
		paymentPanel.add(BorderLayout.CENTER,scrollPanel);
		paymentPanel.add(BorderLayout.EAST,nestedTotalPanel);
		paymentPanel.add(BorderLayout.SOUTH,imagePanel);
		return paymentPanel;
	}
	
	private JPanel getTotalPanel(Font font) {
		JPanel totalPanel = new JPanel();
		totalPanel.setLayout(new BoxLayout(totalPanel,BoxLayout.Y_AXIS));
		totalPanel.setBackground(Color.white);
		
		JLabel orderSummaryLabel = new JLabel("Order Summary");
		JLabel totalPriceLabel = new JLabel("Total: ");
		
		priceLabel = new JLabel("RM " + format.format(totalPrice));
		
		JLabel tax = new JLabel("(included GST)");
		tax.setHorizontalAlignment(JLabel.CENTER);
		
		creditCardNo = new JTextField("Credit Card Number");
		
		JButton confirmBtn = new JButton("Confirm");
		JButton backBtn = new JButton("Back");
		
		//styling all component
		orderSummaryLabel.setFont(font);
		totalPriceLabel.setFont(font);
		priceLabel.setFont(font);
		tax.setFont(new Font("Yu Mincho Light", Font.ITALIC, 15));
		creditCardNo.setFont(new Font("Yu Mincho Light", Font.ITALIC, 15));
		creditCardNo.setForeground(Color.GRAY);
		confirmBtn.setFont(font);
		backBtn.setFont(font);
		
		
		JPanel orderSummary = new JPanel();
		orderSummary.setOpaque(false);
		//orderSummary.setBackground(Color.white);
		orderSummary.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		orderSummary.add(orderSummaryLabel);
		
		JPanel pricePanel = new JPanel();
		pricePanel.setOpaque(false);
		//pricePanel.setBackground(Color.white);
		pricePanel.add(totalPriceLabel);
		pricePanel.add(priceLabel);
		
		JPanel creditCardPanel = new JPanel();
		creditCardPanel.setOpaque(false);
		creditCardNo.setPreferredSize(new Dimension(210,30));
		creditCardPanel.add(creditCardNo);
		//creditCardPanel.setBackground(Color.white);
		
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
		totalPanel.add(creditCardPanel);
		totalPanel.add(confirmBtnPanel);
		totalPanel.add(backBtnPanel);
		totalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		creditCardNo.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
		
				creditCardNo.setText("");
			}

			public void focusLost(FocusEvent e) {
				if(creditCardNo.getText() == "")
				creditCardNo.setText("Credit Card Number");
			}
		});
		
		creditCardNo.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(e.getDocument().getLength()==16){
					confirmBtn.setEnabled(true);
				}
				else {
					confirmBtn.setEnabled(false);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(e.getDocument().getLength()==16){
					confirmBtn.setEnabled(true);
				}
				else {
					confirmBtn.setEnabled(false);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
	
			}
			
		});
		
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
	
	private void setPaymentListPanel() {
		Component[] components = cartListPanel.getComponents();
		for(int count = 0; count < cartIndex;count++) {
			
			JPanel panel = (JPanel)components[count];
			panel.setOpaque(false);

			JPanel newPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			//newPanel.setOpaque(false);
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
			
			if(cartIndex >= 3) {
				GridLayout grid = new GridLayout(count + 1,1);
				paymentListPanel.setLayout(grid);
			}
			paymentListPanel.add(newPanel);
		}
	}

	private JPanel getWelcomePanel(Font font)
	{
		JPanel welcomePanel = new JPanel(new BorderLayout());
		JLabel textLabel = new JLabel("Welcome To Mc Daniel!");
		

		//Set colour
		welcomePanel.setBackground(new Color(255, 228, 196));
		
		//Insert imageIcon
		ImageIcon logoIcon = null;
		try {
			Image logo = ImageIO.read(new File("src/image/McDonaldLogo.png"));
			logoIcon = new ImageIcon(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		JLabel logoLabel = new JLabel(logoIcon);
		
		JRadioButton eatIn = new JRadioButton("Eat In");
		JRadioButton takeAway = new JRadioButton("Take Away");
		eatIn.setOpaque(false);
		takeAway.setOpaque(false);
		eatIn.setActionCommand("Eat In");
		takeAway.setActionCommand("Take Away");
		
		ButtonGroup selection = new ButtonGroup();
		selection.add(eatIn);
		selection.add(takeAway);
		
		//Add button
		JButton comfirmButton=new JButton("Next");
		
		//Set font
		textLabel.setFont(font);
		eatIn.setFont(font);
		takeAway.setFont(font);
		comfirmButton.setFont(font);
		
		//Add Component into gridLayout
		JPanel panel = new JPanel(new GridLayout(3,1));
		panel.setOpaque(false);
		
		
		//open first panel to put icon
		JPanel innerPanel = new JPanel();
		innerPanel.setOpaque(false);
		//innerPanel.add(imageLabel);
		innerPanel.add(logoLabel);
		
		//open inner panel to put text 
		JPanel innerPanel2 = new JPanel();	
		innerPanel2.setOpaque(false);
		innerPanel2.add(textLabel);

		//open second inner panel to put 2 JRadio button
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
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setOpaque(false);
		emptyPanel.add(Box.createRigidArea(new Dimension(0,100)));
		
		//add into panel
		welcomePanel.add(BorderLayout.NORTH,emptyPanel);
		welcomePanel.add(BorderLayout.CENTER,panel2);

		
		//add action listener to button
		comfirmButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				orderMode = selection.getSelection().getActionCommand();
				tabbedPanel.setSelectedIndex(1);
				tabbedPanel.setEnabledAt(1, true);
			}
			
		});
		
		return welcomePanel;
	}
	
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
		
		//Welcome tab
		tabbedPanel.addTab("Welcome", getWelcomePanel(font));
		
		// Menu tab
		tabbedPanel.addTab("Menu List", getMenuPanel(font));

		// Cart tab
		tabbedPanel.addTab("Cart List", getCartPanel(font));
		//Payment tab
		tabbedPanel.addTab("Payment", getPaymentPanel(font));
		tabbedPanel.setEnabledAt(1, false);
		tabbedPanel.setEnabledAt(2, false);
		tabbedPanel.setEnabledAt(3, false);
		
		add(tabbedPanel);
		

;	}
	

	// Define a generic font style
	private Font getFontStyle() {
		
		Font font = new Font ("Yu Mincho Light", Font.PLAIN, 30);
		
		return font;
		
	}

	public void resetMenuButton() {
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
	
	private void resetAllComponent() {
		tabbedPanel.setSelectedIndex(0);
		tabbedPanel.setEnabledAt(3, false);
		tabbedPanel.setEnabledAt(0, true);		
		cartListPanel.removeAll();
		paymentListPanel.removeAll();
		resetMenuButton();
		cartIndex = 0; 
		totalPrice = 0;
	}
	
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
