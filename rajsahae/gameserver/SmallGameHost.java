package rajsahae.gameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;

import rajsahae.tictactoe.T3Controller;
import rajsahae.tictactoe.T3Interface;
import rajsahae.tictactoe.T3RemoteView;

public class SmallGameHost {
	private T3Interface.Model game;
	private int port;
	private boolean listening;
	private int numJoined = 0;
	
	// Listen for incoming connections on the specified port
	public SmallGameHost(T3Interface.Model game, int port) {
		this.game = game;
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
				this.greet(serverSocket.accept());
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
	
	private void greet(Socket request) throws IOException {
		PrintWriter out = null;
		BufferedReader in = null;
		String input = "";
		char mark;
		out = new PrintWriter(request.getOutputStream(), true);
		in = new BufferedReader(
	    		new InputStreamReader(request.getInputStream()));
		T3Controller controller = new T3Controller(game);
		T3RemoteView remoteView = new T3RemoteView(game, controller, request);
		
	    out.println("What is your name?");
	    input = in.readLine();

	    out.println("Thank you.  Please initialize your remote model with the current socket.");
	    
	    mark = (numJoined++ == 0) ? 'x' : 'o'; 
	    game.addPlayer(input, mark);
	    game.addListener(rv);
	    out.println("Added you to the current game as player with mark: " + mark);
	    
	    if(numJoined == 2){
	    	game.setupBoard('x');
	    	game.startGame();
	    }
	}
}
