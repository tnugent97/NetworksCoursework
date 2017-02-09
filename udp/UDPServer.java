/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;
	boolean timeout = false;
	boolean end = false; 

	private void run() {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;

		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
		pacData = new byte[1000];
		pac = new DatagramPacket(pacData, pacData.length);
		while(true){
			try{
				recvSoc.setSoTimeout(30000);
				recvSoc.receive(pac);
			} catch(Exception e){ 
				e.printStackTrace(); timeout = true;
			}

			String data = new String(pac.getData(), 0 , pac.getLength());
			processMessage(data);
		}
	}

	public void processMessage(String data) {
		if(!timeout){

			MessageInfo msg = null;

			// TO-DO: Use the data to construct a new MessageInfo object
			try{
				msg = new MessageInfo(data);
			} catch(Exception e){ e.printStackTrace(); }

			// TO-DO: On receipt of first message, initialise the receive buffer
			if(msg.messageNum == 1){
				receivedMessages = new int[msg.totalMessages];
				totalMessages = msg.totalMessages;
			}

			// TO-DO: Log receipt of the message
			receivedMessages[msg.messageNum-1] = msg.messageNum;
			System.out.println("Message Received");
			System.out.println("msg number = " + msg.messageNum);
			if(msg.messageNum == msg.totalMessages){
				end = true;			
			}
		}

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		if((end) || (timeout)){
			System.out.println("End of messages. These are the missed messages:");
			for(int i = 0; i<totalMessages; i++){
				if(receivedMessages[i] != i+1){
					System.out.println(i+1);
				}
			}
		}
	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try{
			recvSoc = new DatagramSocket(rp);
		} catch (Exception e){ e.printStackTrace(); }

		// Done Initialisation
		System.out.println("UDPServer ready");
	}

	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer server = new UDPServer(recvPort);
		server.run();
	}

}
