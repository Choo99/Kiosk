package tcpOrderServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import controller.ItemProductController;
import controller.OrderController;
import controller.OrderTransactionController;
import controller.OrderedItemController;
import kioskapp.ordereditem.OrderedItem;
import kioskapp.ordertransaction.OrderTransaction;
import tcpKioskClient.KioskFrame;

public class TCPOrderServerApplication {

	/**
	 * main entry point to the server application
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */

	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
		
	//bing to a port no.
	int portNo = 4228;
	int kitchenPort = 3010;
	
	ServerSocket serverSocket = null;
	
	TCPOrderServerFrame frame = new TCPOrderServerFrame();
	frame.setVisible(true);

	int referenceNumber = 0;

	serverSocket = new ServerSocket(portNo);
	ServerSocket kitchenServer = new ServerSocket(kitchenPort);
	
	while(true) {
		//accept client request
		Socket clientSocket = serverSocket.accept();
		
		ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
			
		//read orderTransaction from client
		OrderTransaction orderTransaction = (OrderTransaction)inputStream.readObject();
		String creditCardNo = inputStream.readUTF();
		frame.updateRequestStatus("Credit Card Number (From Kiosk): " + creditCardNo);
			
			
		//open a socket to send a request to transaction server
		Socket transactionSocket = new Socket(InetAddress.getLocalHost(),4230);
			
		//open an outputStream for transaction server
		ObjectOutputStream transactionOutputStream = new ObjectOutputStream(transactionSocket.getOutputStream());
		transactionOutputStream.writeObject(orderTransaction);
		transactionOutputStream.writeUTF(creditCardNo);
		transactionOutputStream.flush();
		frame.updateRequestStatus("Send credit card number to authorization server");
		
		//open an inputStream to read orderTransaction
		ObjectInputStream transactionInputStream = new ObjectInputStream(transactionSocket.getInputStream());
			
		//read transaction id that generate by transaction server
		orderTransaction = (OrderTransaction)transactionInputStream.readObject();

		frame.updateRequestStatus("Validation Result (From Authorization Server): " + orderTransaction.isTransactionStatus());
		//insert order detail into database
		if(orderTransaction.isTransactionStatus()){
				
			orderTransaction.getOrder().setOrderReferenceNumber(++referenceNumber);;

			OrderController orderController = new OrderController();
			orderTransaction.setOrder(orderController.insertOrder(orderTransaction.getOrder()));

			//write transaction detail into database
			OrderTransactionController transactionController = new OrderTransactionController ();
			transactionController.insertTransaction(orderTransaction);
			
			//insert ordered item into database
			OrderedItemController orderedItemController = new OrderedItemController();
			orderedItemController.insertOrderedItem(orderTransaction.getOrder());

			ItemProductController itemProductController = new ItemProductController();
			List<OrderedItem> products = orderTransaction.getOrder().getOrderedItems();
			for(OrderedItem product:products){
				product.setItemProduct(itemProductController.getProduct(product.getItemProduct().getItemProduct()));
			}
			
			frame.updateRequestStatus("Transaction ID: " + orderTransaction.getOrderTransactionId());
			frame.updateRequestStatus("Total amount: " + orderTransaction.getAmountCharged());
			frame.updateRequestStatus("Transaction Date: " + orderTransaction.getTransactioDate());
			

			Socket kitchenSocket = kitchenServer.accept();	
			
			frame.updateRequestStatus("Send order information to kitchen");
			frame.updateRequestStatus("Order reference number: " + orderTransaction.getOrder().getOrderReferenceNumber());
			frame.updateRequestStatus("Total item quantity: "+ orderTransaction.getOrder().getOrderedItems().size());
			frame.updateRequestStatus("Eat mode: " + orderTransaction.getOrderMode());
			ObjectOutputStream kitchenOutputStream = new ObjectOutputStream(kitchenSocket.getOutputStream());
			kitchenOutputStream.writeObject(orderTransaction);
				
			frame.updateRequestStatus("\n > Request Done");
			
			kitchenOutputStream.close();
			kitchenSocket.close();
			}

		//open an outputStream to send transaction details to client side
		ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

		//send transaction detail to client
		outputStream.writeObject(orderTransaction);
		outputStream.flush();
			
		//close all object
		clientSocket.close();
		transactionSocket.close();
		inputStream.close();
		outputStream.close();
		transactionOutputStream.close();
		transactionInputStream.close();
		}
	}
}
