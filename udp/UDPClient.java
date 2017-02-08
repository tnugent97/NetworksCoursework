/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import common.MessageInfo;

public class UDPClient {

	private DatagramSocket sendSoc;

	public static void main(String[] args) {
		InetAddress	serverAddr = null;
		int	recvPort;
		int countTo;
		String message;

		// Get the parameters
		if (args.length < 3) {
			System.err.println("Arguments required: server name/IP, recv port, message count");
			System.exit(-1);
		}

		try {
			serverAddr = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println("Bad server address in UDPClient, " + args[0] + " caused an unknown host exception " + e);
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[1]);
		countTo = Integer.parseInt(args[2]);


		// TO-DO: Construct UDP client class and try to send messages
		UDPClient client = new UDPClient();
		client.testLoop(serverAddr, recvPort, countTo);
	}

	public UDPClient() {
		// TO-DO: Initialise the UDP socket for sending data
		DatagramSocket sendSoc;
	}

	private void testLoop(InetAddress serverAddr, int recvPort, int countTo) {
		int	tries = 0;
		try{
		sendSoc = new DatagramSocket(recvPort);
		}	catch (Exception e){
		e.printStackTrace();
		}
		// TO-DO: Send the messages to the server
		for (int i = 0; i < countTo; i++){
			String msg = countTo + ";" + (i+1);
			send(msg, serverAddr, recvPort);
			tries++;
		}
		System.out.println("Attempted to send " + tries);
	}

	private void send(String payload, InetAddress destAddr, int destPort) {
		int	payloadSize;
		byte[] pktData;
		DatagramPacket	pkt;
		payloadSize = payload.length();
		pktData = new byte[payloadSize];
		pktData = payload.getBytes();
		pkt = new DatagramPacket(pktData, payloadSize, destAddr, destPort);
		try{
			sendSoc.send(pkt);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
