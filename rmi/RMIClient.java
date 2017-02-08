/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.registry.*;
import java.rmi.RemoteException;

import common.MessageInfo;

public class RMIClient {

	public static void main(String[] args) {
 	
		RMIServerI iRMIServer = null;
		String hostName = args[0];
		//int portNumber = Integer.parseInt(args[2]);
		int messagesSent = 1;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("/RMIServer");
		int numMessages = Integer.parseInt(args[1]);
		// TO-DO: Initialise Security Manager
		//if (System.getSecurityManager() == null){
		//	System.setSecurityManager(new SecurityManager());
		//}
		// TO-DO: Bind to RMIServer
		try {
			Registry registry = LocateRegistry.getRegistry(args[0], 2020);
			iRMIServer = (RMIServerI) (registry.lookup(urlServer));
			while (numMessages >= messagesSent){
			MessageInfo msg = new MessageInfo(numMessages, messagesSent);
				iRMIServer.receiveMessage(msg);
				messagesSent++;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}

		// TO-DO: Attempt to send messages the specified number of times
		

	}
}
