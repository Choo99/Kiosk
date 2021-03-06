package tcp.client.kioskClient;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import model.kioskapp.ordertransaction.OrderTransaction;
import tcp.client.kioskClient.KioskFrame;

/**
 * This is main entry point of kiosk client application
 * Send required information to order server and receive the result
 * Corresponding message will display depend the result
 * Write the success transaction as a receipt into a .txt file
 * 
 * Caution: Please open all the server application and kitchen application before using kiosk
 * Error: Kiosk will freeze after pressing confirm button if any application is not open 
 * 
 * @author User
 *
 */
public class TCPKioskClientApplication {

	public static void main(String[] args) throws InterruptedException {
		
		//set frame to visible
		KioskFrame kioskFrame = new KioskFrame();
		kioskFrame.setVisible(true);
		
		while(true) {
			try {

			kioskFrame.waitForInput();
			
			//connect to order server
			Socket socket = new Socket(InetAddress.getLocalHost(),4228);

			//open an outputStream
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

			//get objectTransaction and credit card number from kiosk frame
			OrderTransaction orderTransaction = kioskFrame.getOrderTransaction();
			String creditCardNo = kioskFrame.getCreditCardNumber();

			//send orderTransaction to order server
			outputStream.writeObject(orderTransaction);
			outputStream.writeUTF(creditCardNo);
			outputStream.flush();

		
			//open an inputStream
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

			orderTransaction = (OrderTransaction)inputStream.readObject();
			boolean result = orderTransaction.isTransactionStatus();
		
			kioskFrame.setTransactionStatus(result);
			kioskFrame.release();
			// print receipt
			if(result)
			{	
				//take transaction id and order id as file name 
				String targetSource = Integer.toString(orderTransaction.getOrderTransactionId()) + Integer.toString(orderTransaction.getOrder().getOrderId()) + ".txt";
				kioskFrame.setPrintMessage("Transact successfully! Please take your receipt");
				//write receipt into text file
				KioskReceipt receipt = new KioskReceipt ();
				String receiptContent = receipt.writeReceiptContent(orderTransaction);
				FileWriter fileWriter = new FileWriter (targetSource);
				fileWriter.write(receiptContent);
				fileWriter.flush();
				fileWriter.close();
			}
			else {
				kioskFrame.setPrintMessage("Invalid credit card number! Please try again");
			}
			
			} catch (InterruptedException | ClassNotFoundException | IOException e) {
				kioskFrame.setPrintMessage("Server is not ready! Please inform technician");
				kioskFrame.release();
				e.printStackTrace();
			}
		}	
	}
}
