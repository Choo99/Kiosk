package tcpKitchenClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import kioskapp.itemproduct.ItemProduct;
import kioskapp.order.Order;
import kioskapp.ordereditem.OrderedItem;
import kioskapp.ordertransaction.OrderTransaction;

public class KitchenApplication {

	public static void main (String arg[]) throws UnknownHostException, IOException, ClassNotFoundException {
		
	KitchenFrame kitchenFrame = new KitchenFrame();
	kitchenFrame.setVisible(true);
		
	int portNo = 3010;

	while(true) {
		Socket kitchenSocket = new Socket(InetAddress.getLocalHost(),portNo);
		ObjectInputStream kitchenInputStream = new ObjectInputStream(kitchenSocket.getInputStream());
		OrderTransaction orderTransaction = (OrderTransaction)kitchenInputStream.readObject();
		kitchenFrame.setOrderPanel(orderTransaction);
	
		kitchenSocket.close();
		}
	}
}
