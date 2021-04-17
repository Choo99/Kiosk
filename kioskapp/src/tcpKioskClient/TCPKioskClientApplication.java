package tcpKioskClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import kioskapp.order.Order;
import kioskapp.ordereditem.OrderedItem;
import kioskapp.ordertransaction.OrderTransaction;

public class TCPKioskClientApplication {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
		
		//set frame to visible
		KioskFrame kioskFrame = new KioskFrame();
		kioskFrame.setVisible(true);
		
		kioskFrame.waitForInput();

		/*
		//connect to order server
		Socket socket = new Socket(InetAddress.getLocalHost(),4228);

		//open an outputStream
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

		//get objectTransaction and credit card number from kiosk frame
		OrderTransaction objectTransaction = kioskFrame.getOrderTransaction();
		String creditCardNo = kioskFrame.getCreditCardNumber();
		
		//send orderTransaction to order server
		outputStream.writeObject(objectTransaction);
		outputStream.writeUTF(creditCardNo);

		//open an inputStream
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

		//read validation result from order server
		boolean result = inputStream.readBoolean();

		if(result)
		{
			//read orderTransaction from order server
			OrderTransaction orderTransaction = (OrderTransaction)inputStream.readObject();
			
			//take transaction id and order id as file name 
			String targetSource = Integer.toString(orderTransaction.getOrderTransactionId()) + Integer.toString(orderTransaction.getOrder().getOrderId()) + ".txt";

			//write receipt into text file
			FileOutputStream fileOutputStream = new FileOutputStream(targetSource);

			//transaction details	
		}
		else
		{
			String errorMessage = inputStream.readUTF();
			//kioskFrame.setErrorMessage(errorMessage);
		}
			*/
	}
}
