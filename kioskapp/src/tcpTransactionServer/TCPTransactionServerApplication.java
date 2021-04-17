package tcpTransactionServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import kioskapp.ordertransaction.OrderTransaction;
import kioskappException.InvalidCreditCardException;

public class TCPTransactionServerApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		//binding portNo 4229 to server socket
		int portNo = 4229;
		ServerSocket serverSocket = new ServerSocket(portNo);
		
		Socket clientSocket = null;
			
		TCPTransactionServerFrame frame = new TCPTransactionServerFrame();
		frame.setVisible(true);
		
		while(true) {
			try {
				
				clientSocket = serverSocket.accept();
				
				//open an inputStream to read orderTransaction that send by order server
				ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
				OrderTransaction orderTransaction =  (OrderTransaction)inputStream.readObject();
				String creditCardNo = inputStream.readUTF();
				frame.updateRequestStatus(creditCardNo);
				
				CreditCardAuthorization authorization = new CreditCardAuthorization();
				authorization.validateCreditCardNo(creditCardNo);
			
				
				//write transaction detail into database
				orderTransaction.setLast4Digits(Integer.parseInt
						(creditCardNo.substring(creditCardNo.length() - 4)));
				
				//open an outputStream to send back transaction detail to order server
				ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
				outputStream.writeBoolean(true);
				outputStream.writeObject(orderTransaction);
				outputStream.flush();
				
				outputStream.close();
				inputStream.close();
				clientSocket.close();

			} catch (InvalidCreditCardException e){
				
				//open an outputStream to send the error message to order server
				ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
				ObjectOutputStream.writeBoolean(false);
				ObjectOutputStream.writeUTF(e.toString());
				ObjectOutputStream.flush();
				
				//close outputStream
				ObjectOutputStream.close();
			}
		}
	}
}
