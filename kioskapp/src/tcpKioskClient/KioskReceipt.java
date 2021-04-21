package tcpKioskClient;



import kioskapp.ordereditem.OrderedItem;
import kioskapp.ordertransaction.OrderTransaction;

public class KioskReceipt {
	

	public String writeReceiptContent(OrderTransaction orderTransaction) {
		
		String receipt = "-------------------------------------------------------"+
						 "\n"+"          	Your Order Reference No is"+"\r\n"+
						 "\r\n\t\t\t";
		// get reference number
		receipt += orderTransaction.getOrder().getOrderReferenceNumber();
		receipt +=	"\r\n"+"-------------------------------------------------------"+
					"\r\n"+"    	Welcome to Mc Daniel's Malacca Restaurant"+"\r\n"+
					"               	EmmaSoft Solution"+
					"\r\n"+"    	4570 Lebuh Ayer Keroh Taman Buaya Melaka"+
					"\r\n                	75450 Melaka"+
					"\r\n              	TEL :  0123456789"+
					"\r\n                	TAX INVOICE"+"\r\n"+
					"\r\nOrder ID : ";
					
		
		// get order id
		receipt += orderTransaction.getOrder().getOrderId();
	    
		// get transaction date
		receipt += "\r\nTime	 : ";
		receipt += orderTransaction.getTransactioDate();
		
		// get order mode 
		receipt += "\r\n";
		receipt += orderTransaction.getOrderMode();
		receipt += "\r\n";

		//product details
		receipt += "\r\n";
		receipt += String.format("%-5s%-40s%-20s","QTY","ITEM","PRICE");
		
		
		//get order details
		for(OrderedItem item : orderTransaction.getOrder().getOrderedItems()) {
			receipt += "\r\n";
			
			receipt += String.format("%-5d%-40sRM %7.2f",item.getQuantity(),
					item.getItemProduct().getName(),item.getSubTotalAmount());
		}
		
		//get total
		receipt += "\r\n";
		receipt += String.format("%44s RM %7.2f","Total",orderTransaction.getAmountCharged());
		

		//credit card last 4 digits
		receipt += "\r\nCredit Card No:**** **** **** ";
		String creditCardNumber = Integer.toString(orderTransaction.getLast4Digits());
		if(creditCardNumber.length() < 4) {
			String zero ="";
			for(int counter = 0; counter < 4 - creditCardNumber.length();counter++) {
				zero += "0";
			}
			creditCardNumber = zero + creditCardNumber;
		}
		receipt += creditCardNumber;

		//ending
		 receipt+="\r\n"+"\r\n			Thank You!!Please Come Again!!!"+
				 "\r\n		Customer Service Hotline : 09 - 0948787"; 
	
		
		return receipt;
        
  }
	
}

	

