package tcpKioskClient;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import kioskapp.ordertransaction.OrderTransaction;

public class KioskReceipt {
	
	public void writeReceiptContent(DataOutputStream outputStream) throws IOException{
        for(int counter =0; counter<20; counter ++){
            outputStream.writeChar('-');
        }
        outputStream.writeUTF("\n|");
        for(int counter =0; counter<18; counter ++){
            outputStream.writeChar(' ');
        }
        outputStream.writeUTF("|\n|");
        for(int counter =0; counter<9; counter ++){
            outputStream.writeChar(' '); 
        }
  }
	
}

	

