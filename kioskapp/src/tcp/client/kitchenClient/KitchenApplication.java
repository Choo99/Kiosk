package tcp.client.kitchenClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import model.kioskapp.ordertransaction.OrderTransaction;


/**
 * This is main entry point of kitchen application
 * Kitchen application will try to connect to server every 10 seconds when it is not connected to server
 * Read the order'sdetail from server
 * Kitchen application can always alive
 * @author User
 *
 */
public class KitchenApplication {

	public static void main (String arg[]) throws InterruptedException {
		
	KitchenFrame kitchenFrame = new KitchenFrame();
	kitchenFrame.setVisible(true);
	String errorMessage = "";
		
	int portNo = 3010;
	
	Socket kitchenSocket = null;
	
	//make kitchen application always alive
	while(true) {
		try {
		//try to connect server every 10 seconds if fail
		while(true) {
			try {
			kitchenSocket = new Socket(InetAddress.getLocalHost(),portNo);
			kitchenFrame.setConnectStatus(kitchenSocket.isConnected());
			break;
			}catch(IOException e) {
				e.printStackTrace();
				
				//Display error message
				errorMessage = "Cannot connect to server! Reconnect in 10 seconds";
				kitchenFrame.displayMessage(errorMessage);
				kitchenFrame.setConnectStatus(false);
				
				//Sleep for 10 seconds
				TimeUnit.SECONDS.sleep(10);
			}
		}
		//Reading object from server and set into frame
		ObjectInputStream kitchenInputStream = new ObjectInputStream(kitchenSocket.getInputStream());
		OrderTransaction orderTransaction = (OrderTransaction)kitchenInputStream.readObject();
		kitchenFrame.setOrderPanel(orderTransaction);

		kitchenSocket.close();
		}catch(ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		}	
	}
}
