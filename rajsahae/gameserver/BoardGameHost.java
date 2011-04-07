package rajsahae.gameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.List;

/**
 * A chat host belongs to a single chat server.  The server creates the host
 * and allows it to listen for incoming requests, create rooms, and add
 * the client requests to that room.
 * 
 * @author Raj Sahae
 *
 */
public class BoardGameHost extends Thread {
	private BoardGameServer server;
	private int port;
	private boolean listening;
	
	// Listen for incoming connections on the specified port
	public BoardGameHost(BoardGameServer server, int port) {
		this.server = server;
		this.port = port;
		this.listening = true;
	}
	
	public void run() {
		ServerSocket serverSocket 	= null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(-1);
        }

        while (listening) {
        	try {
				this.greetRequest(serverSocket.accept());
			} catch (SecurityException ex) {
				ex.printStackTrace();
			} catch (SocketTimeoutException ex) {
				ex.printStackTrace();
			} catch (IllegalBlockingModeException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} //get the client socket
        }
        
        try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void greetRequest(Socket request) throws IOException {
		PrintWriter out = null;
		BufferedReader in = null;
		List<BoardGameRoom> roomList = null;
		String input = "";
		boolean waitingForRoomName = true;
		
		out = new PrintWriter(request.getOutputStream(), true);
	    in = new BufferedReader(
	    		new InputStreamReader(request.getInputStream()));
	    
	    out.println("You may join an existing room or create your own.");
	    out.println("Room names are NOT case sensitive.  The current roomlist is:");
	    
	    roomList = server.getRoomList();
	    for(BoardGameRoom room : roomList) {
	    	out.println(room.getName());
	    }

	    while(waitingForRoomName) {
		    out.println("Please type the name of the room you would like to join.");  
	    	input = in.readLine();
	    	if(server.roomExists(input)) {
	    		out.println("Joining existing room: " + input);
	    		server.addClientToRoom(request, input);
	    		waitingForRoomName = false;
	    	} else {
	    		out.println("That room doesn't exist.  Do you want to create it?(Y/n)");
	    		while( (input = in.readLine()) != null) {
	    			if(input.equals("Y")) {
	    				out.println("Creating room '" + input + "'.");
	    				if(server.createNewRoom(input)) {
	    					out.println("Room '" + input + "' created successfully.");
	    					out.println("Now joining '" + input + "'.");
	    					server.addClientToRoom(request, input);
	    					waitingForRoomName = false;
	    					break;
	    				}
	    			} else if(input.equals("n")){
	    				out.println("Ok, let's try again.");
	    				break;
	    			} else {
	    				out.println("Response not recognized. Please enter 'Y' or 'n'");
	    			}
	    		}
	    	}
	    }
	}
	
}
