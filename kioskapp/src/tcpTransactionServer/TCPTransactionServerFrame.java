
package tcpTransactionServer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This class represents the front end of the server side application.
 * It display the status of the server and status for each request 
 * made to the server.
 * 
 */

public class TCPTransactionServerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// Private components
	private JLabel lblServerStatus;
	private JTextArea txtRequestStatus;
	
	// Private dimension
	private int width = 900;
	private int height = 500;

	/**
	 * The constructor organize the GUI component for the window.
	 */
	public TCPTransactionServerFrame () {
		
		// Default frame setting
		getContentPane().setLayout(new BorderLayout());
		this.setTitle("TCP Application: Server Side");
		this.setSize(new Dimension(width, height));  
		
		// Must close on X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		// Center the frame on the screen
        this.setLocationRelativeTo(null);
 
		// Initialize component
		this.lblServerStatus = new JLabel ("-");
		// Row, Column
		this.txtRequestStatus  = new JTextArea(20, 60);
		txtRequestStatus.setBackground(new Color(51, 255, 255));
		// Load more component
		loadComponent();
		
		//Cannot resize the frame
		this.setResizable(false);
				
	}	
	
	/**
	 * This method create and arrange Swing components to display the status of 
	 * the server application
	 * 
	 * @param font - Default font for the application
	 * @return Swing components organized in a panel.
	 */
	private JPanel getServerStatusPanel(Font font) {
		
		// Components to display server's status
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 255, 255));
		JLabel lblServer = new JLabel ("Server status: ");
		
		// Style the components
		lblServer.setFont(font);
		lblServerStatus.setFont(font);
		lblServer.setBackground(new Color(102, 255, 255));
		lblServer.setOpaque(true);
		lblServerStatus.setBackground(new Color(102, 255, 255));
		lblServerStatus.setOpaque(true);

		// Organize component into the panel
		panel.add(lblServer);
		panel.add(lblServerStatus);
		
		return panel;
		
	}
	
	/**
	 * This method create and arrange Swing components the status of request 
	 * send to the client.
	 * 
	 * @param font - Default font for the application
	 * @return Swing components organized in a panel.
	 */
	private JPanel getRequestStatusPanel () {
		
		// Component to display request's status
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 255, 255));

		// Set default message when the frame launch for the first time
		txtRequestStatus.setText("\r\n > Server is running");
		txtRequestStatus.setEditable(false);
		
		// Styling the request text
		txtRequestStatus.setFont(new Font("Serif", Font.PLAIN, 15));


		// Add component to panel
		panel.add(txtRequestStatus);
		
		return panel;
		
	}
	
	
	/**
	 * This method arrange the GUI component on the frame
	 */
	public void loadComponent() {
		
		// Get the server status panel and add to frame
		Font font = this.getFontStyle();
		JPanel topPanel = this.getServerStatusPanel(font);

		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		
		// Component to display request's status
		JPanel centrePanel = this.getRequestStatusPanel();		
		getContentPane().add(centrePanel, BorderLayout.CENTER);
		
	}
	
	/**
	 * This method update the status of the server
	 * 
	 * @param flag: status of the server
	 */
	public void updateServerStatus(boolean flag) {
		
		String status = "Waiting for connection.";
		
		if (flag)
			status = "Received connection";
		
		this.lblServerStatus.setText(status);
		
	}
	
	/**
	 * This method update the status of the request sent to the client
	 * 
	 * @param status: request status
	 */
	public void updateRequestStatus (String status) {
		
		// Get current status displayed on the window
		String currentText = this.txtRequestStatus.getText();
		txtRequestStatus.setEditable(true);
		
		// Display the latest status on top
		status += "\n > " + currentText;
		this.txtRequestStatus.setText(status);
		txtRequestStatus.setEditable(false);
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
