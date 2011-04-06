/*
 * T3PlayerView
 * 
 * Version 1.0
 * 
 * Oct 2010
 * 
 * Written by Raj Sahae
 */
package rajsahae.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rajsahae.boardgame.BoardGameEvent;

/**
 * This class is a rudimentary command line view for the TicTacToe(T3)
 * game.  The instance of this class listens for events from T3Model,
 * displays the board to the user using the console, and sends 
 * controller request events to a T3Controller which takes user input
 * and manipulates the T3Model.(MVC Structure)
 * 
 * @author Raj Sahae
 * @version 1.0 Oct 2010
 *
 */
public class T3PlayerView extends T3View {
	
	public T3PlayerView(String name, char mark, int position, T3Interface.Model model, T3Interface.Controller controller) {
		super(name, mark, position, model, controller);
	}
	
	public void displayBoard() {
		System.out.println(this.getName() + ": The current board is:");
		System.out.println(this.model.getBoardAsString());
	}
	
	protected void makeMove() {
		T3Event.MoveEvent e;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		
		try {
			//this.displayBoard();
			System.out.println("Please enter coordinates between 00 and 22");
			input = br.readLine();
			
			T3Model.T3Coordinate c = new T3Model.T3Coordinate( 
					Double.valueOf(input.substring(0,1)).intValue(),
					Double.valueOf(input.substring(1,2)).intValue(),
					this.getMark());
			e = new T3Event.MoveEvent(this, c);
			for(T3Interface.Controller l : listeners) {
				l.makeMove(e);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			this.makeMove();
		}
	}
	
	@Override
	public void playerAdded(BoardGameEvent e) {
		this.model = (T3Interface.Model)e.getSource();
		System.out.println(this.getName() + ": A player joined the game");
		
	}

	@Override
	public void playerDropped(BoardGameEvent e) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)e.getSource();
		System.out.println(this.getName() + ": A player left the game");
	}

	@Override
	public void gameStarted(BoardGameEvent e) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)e.getSource();
		System.out.println(this.getName() + ": The game has started");
	}

	@Override
	public void gamePaused(BoardGameEvent e) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)e.getSource();
		System.out.println(this.getName() + ": The game is paused");
	}

	@Override
	public void gameEnded(BoardGameEvent e) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)e.getSource();
		System.out.println(this.getName() + ": "
				+ model.getCurrentPlayerName() + " has won the game!");
	}

	@Override
	public void moveMade(T3Event.MoveEvent me) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)me.getSource();
		System.out.println(this.getName() + ": A move was made");
		this.displayBoard();
	}

	@Override
	public void lockInput(BoardGameEvent e) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)e.getSource();
		System.out.println(this.getName() + ": Lock Input");
		
	}

	@Override
	public void unlockInput(BoardGameEvent e) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)e.getSource();
		if (this.model.getCurrentPlayerName() == this.getName()) {
			System.out.println(this.getName() + ": Unlocking your input");
		}
	}

	@Override
	public void notifyTurn(BoardGameEvent e) {
		// TODO Auto-generated method stub
		
		this.model = (T3Interface.Model)e.getSource();
		if (this.model.getCurrentPlayerName() == this.getName()) {
			System.out.println(this.getName() + ": Please make a move");
			this.makeMove();
			
		}
	}

	@Override
	public void illegalMoveAttempted(BoardGameEvent e) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)e.getSource();
		System.out.println(this.getName() + ": An illegal move was attempted");
	}

	@Override
	public void playerAdded(T3Event.PlayerEvent pe) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)pe.getSource();
		System.out.println(this.getName() + ": A new player has joined");
	}

	@Override
	public void playerRemoved(T3Event.PlayerEvent pe) {
		// TODO Auto-generated method stub
		this.model = (T3Interface.Model)pe.getSource();
		System.out.println(this.getName() + ": A player dropped from the game");
	}

	@Override
	public void setModel(T3Interface.Model m) {
		// TODO Auto-generated method stub
		this.model = m;
		
	}

}
