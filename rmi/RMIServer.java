/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = 20;
	private int[] receivedMessages;
	//String serverName = "/RMIServer";

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		// TO-DO: On receipt of first message, initialise the receive buffer
		if(msg.messageNum == 1){
			receivedMessages = new int[msg.totalMessages]; 
		}

		// TO-DO: Log receipt of the message
		receivedMessages[msg.messageNum-1] = msg.messageNum;	
		System.out.println("message received:");
		System.out.println("msg number = " + msg.messageNum);

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		if(msg.messageNum == msg.totalMessages){
			System.out.println("End of messages. These are the missed messages:");
			for(int i = 0; i<msg.totalMessages; i++){
				if(receivedMessages[i] != i+1){
					System.out.println(i+1);
				}
			}
		}

	}


	public static void main(String[] args) {

		RMIServer rmis = null;

		// TO-DO: Initialise Security Manager
		if (System.getSecurityManager() == null) {
    			System.setSecurityManager(new SecurityManager());
		}

		try{
			// TO-DO: Instantiate the server class
			rmis = new RMIServer();
		
			// TO-DO: Bind to RMI registry
			rmis.rebindServer("/RMIServer", rmis);
		} catch(Exception e){}

	}

	protected static void rebindServer(String serverURL, RMIServer server) {
		try{
			// TO-DO:
			// Start / find the registry (hint use LocateRegistry.createRegistry(...)
			Registry registry = LocateRegistry.createRegistry(2020);
			// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)

			// TO-DO:
			// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
			registry.rebind(serverURL, server);
			System.out.println("Server started");
			// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
			// expects different things from the URL field.
		}catch(Exception e){}
	}
}
