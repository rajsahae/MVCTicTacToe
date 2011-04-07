/*
 * T3Interface
 * 
 * Version 1.0
 * 
 * Oct 2010
 * 
 * Written by Raj Sahae
 */
package rajsahae.tictactoe;
import rajsahae.boardgame.BoardGameInterface;
import rajsahae.tictactoe.T3Model.T3Board;


/**
 * The T3Interface is a collection of interfaces designed for the T3Model,
 * T3View, and T3Controller classes.
 * 
 * @author	Raj Sahae
 * @version	1.0
 * 
 */
public interface T3Interface extends BoardGameInterface {

	public static interface View 
			extends BoardGameInterface.View {
		public void moveMade(T3Event.MoveEvent me);
		public void playerAdded(T3Event.PlayerEvent pe);
		public void playerRemoved(T3Event.PlayerEvent pe);
		
		/*
		public String getName();
		public int getPosition();
		public char getMark();
		
		public void setModel(Model m);
		public void setName(String n);
		public void setPosition(int p);
		public void setMark(char m);
		*/
	}

	public static interface Model 
			extends BoardGameInterface.Model {
		public void startGame();
		public boolean setupBoard(char option);
		public void makeMove(T3Model.T3Coordinate c);
		
		public boolean addPlayer(String playerName, char mark);
		public boolean removePlayer(String playerName);
		public boolean addListener(T3Interface.View l);
		public boolean removeListener(T3Interface.View l);
		
		public String getBoardAsString();
		public String getCurrentPlayerName();
		
		public T3Board getBoard();
	}
	
	public static interface Controller 
			extends BoardGameInterface.Controller {
		public void makeMove(T3Event.MoveEvent e);
	}
	
}
