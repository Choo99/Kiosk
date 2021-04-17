package tcpOrderServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import kioskapp.ordertransaction.OrderTransaction;

public class TCPOrderServerApplication {

	/**
	 * main entry point to the server application
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
	//bing to a port no.
	int portNo = 4228;
	ServerSocket serverSocket = new ServerSocket(portNo);
	
	TCPOrderServerFrame frame = new TCPOrderServerFrame();
	frame.setVisible(true);
	
	while(true) {
		
		//accept client request
		Socket clientSocket = serverSocket.accept();
		
		ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
		
		//read orderTransaction from client
		OrderTransaction orderTransaction = (OrderTransaction)inputStream.readObject();
		String creditCardNo = inputStream.readUTF();
		frame.updateRequestStatus(creditCardNo);
		
		
		//open a socket to send a request to transaction server
		Socket transactionSocket = new Socket(InetAddress.getLocalHost(),4229);
		
		//open an outputStream for transaction server
		ObjectOutputStream transactionOutputStream = new ObjectOutputStream(transactionSocket.getOutputStream());
		transactionOutputStream.writeObject(orderTransaction);
		transactionOutputStream.writeUTF(creditCardNo);
		transactionOutputStream.flush();
		
		//open an inputStream to read result of authorization credit card
		ObjectInputStream transactionInputStream = new ObjectInputStream(transactionSocket.getInputStream());
		boolean result = transactionInputStream.readBoolean();
		
		
		//open an outputStream to send transaction details to client side
		ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

		//write validation result to the client side
		outputStream.writeBoolean(result);
		if(result) {			
			//read transaction id that generate by transaction server
			orderTransaction = (OrderTransaction)transactionInputStream.readObject();
			
			//write data into database
			
			
			//read order id from the database
			
			
			//send transaction detail to client
			outputStream.writeObject(orderTransaction);
		}
		else {
			String errorMessage = transactionInputStream.readUTF();
			outputStream.writeUTF(errorMessage);
		}
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
