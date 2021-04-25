package tcp.server.transactionServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import exception.creditCardException.InvalidCreditCardException;
import model.kioskapp.ordertransaction.OrderTransaction;


/**
 * This is main entry point of transaction server application
 * Transaction server play a role to validate the credit card number send from order server
 * Validation of credit card number using Luhn's Algorithm in database
 * Send result back to order server
 * @author User
 *
 */
public class TCPTransactionServerApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException, IOException {
		
	//binding portNo 4229 to server socket
	int portNo = 4230;
	
	ServerSocket serverSocket = new ServerSocket(portNo);
		
	Socket clientSocket = null;
			
	TCPTransactionServerFrame frame = new TCPTransactionServerFrame();
	frame.setVisible(true);
		
	OrderTransaction orderTransaction = null;
		
	while(true) {
			try {
				
				clientSocket = serverSocket.accept();
				
				//open an inputStream to read orderTransaction that send by order server
				ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
				orderTransaction =  (OrderTransaction)inputStream.readObject();
				String creditCardNo = inputStream.readUTF();
				frame.updateRequestStatus("Credit Card Number (From Order Server): " + creditCardNo);
				
				CreditCardAuthorization authorization = new CreditCardAuthorization();
				authorization.validateCreditCardNo(creditCardNo);

				frame.updateRequestStatus("Validation Result(From Authorization Server) : " + true);
				orderTransaction.setTransactionStatus(true);
			
				orderTransaction.setLast4Digits(Integer.parseInt
						(creditCardNo.substring(creditCardNo.length() - 4)));
				
			
				//open an outputStream to send back transaction detail to order server
				ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
				outputStream.writeObject(orderTransaction);
				outputStream.flush();
				
				frame.updateRequestStatus("\n > Request done");
				
				outputStream.close();
				inputStream.close();
				clientSocket.close();
			} catch (InvalidCreditCardException e){
				
			try {
				frame.updateRequestStatus("Validation Result (From Authorization Server) : " + false);
				frame.updateRequestStatus("\n > Request done");
				//open an outputStream to send the error message to order server
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
				
				orderTransaction.setTransactionStatus(e.validation());
				objectOutputStream.writeObject(orderTransaction);

				objectOutputStream.flush();
				
				//close outputStream
				objectOutputStream.close();
				
				clientSocket.close();
				}catch(IOException e1) {
					e1.printStackTrace();
				}
			} catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
			}
		}
	}
}
