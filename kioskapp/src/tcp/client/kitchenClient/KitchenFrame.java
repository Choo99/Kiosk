
package tcp.client.kitchenClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import model.kioskapp.orderedItem.OrderedItem;
import model.kioskapp.ordertransaction.OrderTransaction;


/**
 * This class represents the front end of the server side application.
 * It display the status of the server and status for each request 
 * made to the server.
 * 
 */

public class KitchenFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// Private components

	
	// Private dimension
	private int width = 900;
	private int height = 500;
	
	private JPanel orderPanel;
	private int orderIndex = 0;
	private JPanel botPanel;
	JLabel connectStatus;

	/**
	 * The constructor organize the GUI component for the window.
	 */
	public KitchenFrame () {
		
		// Default frame setting
		getContentPane().setLayout(new BorderLayout());
		this.setTitle("TCP Application: Kitchen Side");
		this.setSize(new Dimension(width, height));  
		
		orderPanel = new JPanel();
		orderPanel.setBackground(new Color(240, 255, 240));
		
		connectStatus = new JLabel("-");
		
		// Must close on X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		// Center the frame on the screen
        this.setLocationRelativeTo(null);
 
		// Initialize component

		// Row, Column

		// Load more component
		loadComponent();
		
		//Cannot resize the frame
		this.setResizable(true);
				
	}	
	
	/**
	 * This method create and arrange Swing components to display the status of 
	 * the server application
	 * 
	 * @param font - Default font for the application
	 * @return Swing components organized in a panel.
	 */
	private JPanel getTitlePanel(Font font) {
		
		// Components to display server's status
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 255, 240));
	
		JLabel title = new JLabel ("Kitchen Client: ");
		
		// Style the components
		title.setFont(font);
		connectStatus.setFont(font);

		// Organize component into the panel
		panel.add(title);
		panel.add(connectStatus);
		
		return panel;
	}
	
	/**
	 * This method will pop out message for error
	 * @param message
	 */
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(this, message,"Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * This method will set the connection status of kitchen application
	 * @param status
	 */
	public void setConnectStatus(boolean status) {
		if(status) {
			connectStatus.setText("Connected");
		}
		else {
			connectStatus.setText("No connection");
		}
	}
	
	/**
	 * This method will add order details into orderPanel
	 * @param orderTransaction
	 */
	public void setOrderPanel(OrderTransaction orderTransaction) {
		
		if(orderIndex == 0) {
			orderPanel.removeAll();
			botPanel.removeAll();
		}
		
		List<OrderedItem> items = orderTransaction.getOrder().getOrderedItems();
		
		JPanel oneOrderPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(oneOrderPanel,BoxLayout.Y_AXIS);
		oneOrderPanel.setLayout(boxLayout);
		oneOrderPanel.setOpaque(false);
		
		JLabel refenceNumber = new JLabel(Integer.toString(orderTransaction.getOrder().getOrderReferenceNumber()));
		refenceNumber.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel eatModeLabel = new JLabel(orderTransaction.getOrderMode());
		JPanel eatModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		eatModePanel.setOpaque(false);
		eatModePanel.add(eatModeLabel);

		JButton finishBtn = new JButton("Finish");
		
		oneOrderPanel.add(refenceNumber);
		oneOrderPanel.add(eatModePanel);
		
		for(OrderedItem item:items) {
			JPanel oneItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			oneItemPanel.setOpaque(false);
			
			JLabel quantityLabel = new JLabel(Integer.toString(item.getQuantity()));
			JLabel productNameLabel = new JLabel(item.getItemProduct().getName());
			oneItemPanel.add(quantityLabel);
			oneItemPanel.add(Box.createRigidArea(new Dimension(10 - quantityLabel.getPreferredSize().width,0)));
			oneItemPanel.add(productNameLabel);
			oneOrderPanel.add(oneItemPanel);
		}
		JPanel btnPanel = new JPanel();
		btnPanel.setOpaque(false);

		btnPanel.add(finishBtn);
		oneOrderPanel.add(btnPanel);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		oneOrderPanel.setBorder(border);
	
		//BoxLayout boxLayout2 = new BoxLayout(orderPanel,BoxLayout.Y_AXIS);
		orderPanel.setLayout(new FlowLayout());
		//orderPanel.add(Box.createVerticalGlue());
		orderPanel.add(oneOrderPanel);
		
		this.repaint();
		this.revalidate();
		
		orderIndex++;
		KitchenFrame frame = this;
		
		/**
		 * finishBtn will remove entire panel from orderPanel
		 * Default message will display when there is no more order in the list
		 */
		finishBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				orderIndex--;
				orderPanel.remove(oneOrderPanel);
				if(orderIndex == 0) {
					JLabel statusLabel = new JLabel("Yes! No order for now");
					statusLabel.setHorizontalAlignment(JLabel.CENTER);
					statusLabel.setFont(getFontStyle());
					orderPanel.add(statusLabel,BorderLayout.CENTER);
					orderPanel.add(Box.createRigidArea(new Dimension(0,320)));
					
					JLabel label = new JLabel(getImage());
					JLabel label2 = new JLabel(getImage());
					JLabel label3 = new JLabel(getImage());
					botPanel.add(label);
					botPanel.add(Box.createRigidArea(new Dimension(70,0)));
					botPanel.add(label2);
					botPanel.add(Box.createRigidArea(new Dimension(70,0)));
					botPanel.add(label3);
				}
				frame.repaint();
				frame.revalidate();
			}
			
		});
	}
	
	/**
	 * This method will initialize the orderPanel with default message
	 * @param font
	 * @return JPanel orderPanel
	 */
	private JPanel getOrderPanel(Font font) {

		JLabel statusLabel = new JLabel("Yes! No order for now");
		statusLabel.setHorizontalAlignment(JLabel.CENTER);
		statusLabel.setFont(font);
		orderPanel.add(statusLabel,BorderLayout.CENTER);
		orderPanel.add(Box.createRigidArea(new Dimension(0,320)));
		return orderPanel;
	}
	
	/**
	 * This method will initialize the gif in botPanel
	 * @return'JPanel botPanel
	 */
	private JPanel getBotPanel() {

		botPanel = new JPanel();
		botPanel.setBackground(new Color(240, 255, 240));
		
		JLabel label = new JLabel(getImage());
		JLabel label2 = new JLabel(getImage());
		JLabel label3 = new JLabel(getImage());
		botPanel.add(label);
		botPanel.add(Box.createRigidArea(new Dimension(70,0)));
		botPanel.add(label2);
		botPanel.add(Box.createRigidArea(new Dimension(70,0)));
		botPanel.add(label3);
		return botPanel;
	}
	
	/**
	 * This method will load gif as icon
	 * @return Icon icon
	 */
	private Icon getImage() {
		Icon icon = null;
		try {
			URL url = new URL("https://cdn.discordapp.com/attachments/773406761616146433/"
					+"834447851110400030/disconeko_2.gif");
			icon = new ImageIcon(url);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		return icon;
	}
	
	/**
	 * This method arrange the GUI component on the frame
	 */
	public void loadComponent() {
		
		// Get the server status panel and add to frame
		Font font = this.getFontStyle();
		JPanel topPanel = this.getTitlePanel(font);
		
		JPanel botPanel = this.getBotPanel();
		JScrollPane scrollPane = new JScrollPane(this.getOrderPanel(font));
		scrollPane.setOpaque(false);

		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		
		getContentPane().add(botPanel,BorderLayout.SOUTH);
		
	}
	
	/**
	 * This method define a font to a generic style.
	 * 
	 * @return font object
	 */
	private Font getFontStyle() {
		
		Font font = new Font (Font.SANS_SERIF, Font.PLAIN, 30);
		
		return font;
	}
}
