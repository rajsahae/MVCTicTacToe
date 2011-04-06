/*
 * T3Model
 * 
 * Version 1.0
 * 
 * Oct 2010
 * 
 * Written by Raj Sahae
 */
package rajsahae.tictactoe;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import rajsahae.boardgame.BoardGame;
import rajsahae.boardgame.BoardGameEvent;


/**
 * This class is the game engine for a TicTacToe game.  It communicates
 * with the T3View player view by T3Events, and receives all instructions
 * from the T3Controller via method calls.
 * 
 * @author Raj Sahae
 * @version 1.0
 * 
 */
public class T3Model extends BoardGame implements T3Interface.Model {
	private static final int numPlayers = 2;
	@SuppressWarnings("unused")
	private static final boolean init = initializeClassVariables();
	private static final int boardSize = 3;
	
	protected T3Board board;
	protected List<T3Player> players = new LinkedList<T3Player>();
	protected List<T3Interface.View> listeners = new LinkedList<T3Interface.View>();
	protected int currentTurn;
	
	public T3Model(String name) {
		super(name, numPlayers);
		this.board = new T3Board(boardSize);
		this.currentTurn = 0;
	}
	
	private static boolean initializeClassVariables() {
		setMinPlayers(numPlayers);
		setMaxPlayers(numPlayers);
		setRecNumPlayers(numPlayers);
		return true;
	}

	/*
	 * SETTERS
	 */
	
	/*
	 * GETTERS
	 */

	public String getBoardAsString() {
		return this.board.toString();
	}

	public T3Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}
	
	public String getCurrentPlayerName() {
		return players.get(currentTurn).getName();
	}
	
	/*
	 * INTERFACE METHODS
	 */
	
	public void makeMove(T3Coordinate c) {
		setMove(this.board, c);
	}
	
	public boolean setupBoard(char option) {
		return this.setup(option);
	}
	
	public void startGame() {
		start();
	}
	
	public void endGame() {
		end();
	}
	
	public boolean addPlayer(String playerName, char mark) {
		return addT3Player(playerName, mark);
	}
	
	public boolean removePlayer(String playerName) {
		return remT3Player(playerName);
	}
	
	public boolean removePlayer(int position) {
		return remT3Player(position);
	}

	public boolean addListener(T3Interface.View l) {
		return listeners.add(l);
	}

	public boolean removeListener(T3Interface.View l) {
		return listeners.remove(l);
	}

	/*
	 * PRIVATE METHODS
	 */
	
	private void setMove(T3Board b, T3Coordinate c) {
		fireLockInputEvent();
		
		/*
		System.out.println("Current Player Mark: " 
				+ players.get(currentTurn).getMark());
		System.out.println("Mark of Coordinate: " + c.getMark());
		System.out.println("Current == Coordinate: " + (c.getMark()
				== players.get(currentTurn).getMark()));
		System.out.println("Current player has turn?: " 
				+ players.get(currentTurn).hasTurn());
		*/
		
		if(c.getMark() == players.get(currentTurn).getMark()
				&& players.get(currentTurn).hasTurn()) {
			
			if(b.setMove(c)) {
				fireMoveMadeEvent(c);
				if(checkForWinner()) {
					fireGameEndedEvent();
					endGame();
				}
				advanceToNextPlayer();
			} else {
				fireIllegalMoveAttemptedEvent(c);
			}
		}
		else {
			fireIllegalMoveAttemptedEvent(c);
		}
		fireUnlockInputEvent();
		fireNotifyTurnEvent();
	}
	
	private boolean checkForWinner() {
		return board.hasWinner();
	}
	
	private void advanceToNextPlayer() {
		players.get(currentTurn).loseTurn();
		if (0 <= currentTurn && currentTurn < (players.size()-1)) {
			currentTurn++;
		} else {
			currentTurn = 0;
		}
		players.get(currentTurn).giveTurn();
	}
	
	private boolean setup(char option) {
		boolean result = true;
		try {
			if (this.players.size() != T3Model.numPlayers) {
				result = false;
			}
			if (this.listeners.size() < T3Model.numPlayers) {
				result = false;
			}
			for (T3Player p : this.players) {
				if (p.getMark() == option) {
					currentTurn = players.indexOf(p);
					p.giveTurn();
					break;
				}
			}
			fireLockInputEvent();
		}
		catch(Exception e) {
			result = false;
		}
		return result;
	}

	private void start() {
		fireUnlockInputEvent();
		fireNotifyTurnEvent();
	}
	
	private boolean addT3Player(String name, char mark) {
		boolean status = false;
		if (this.players.size() < T3Model.maxPlayers) {
			try {
				T3Player newPlayer = new T3Player(name, this.players.size(), mark);
				players.add(newPlayer);
				firePlayerAddedEvent(newPlayer);
				status = true;
			} catch (Exception e) {
				status = false;
			}
		}
		return status;
	}
	
	private boolean remT3Player(String name) {
		boolean status = false;
		for(T3Player p : players) {
			if(p.getName().equals(name)){
				players.remove(p);
				firePlayerRemovedEvent(p);
				status = true;
				break;
			}
		}
		return status;
	}
	
	private boolean remT3Player(int position) {
		boolean status = false;
		if (position < players.size()) {
			T3Player p = players.remove(position);
			firePlayerRemovedEvent(p);
			status = true;
		}
		return status;
	}
	
	private void end() {
		System.exit(0);
	}

	/*
	 * FIRE EVENT METHODS
	 */
	
	private void fireMoveMadeEvent(T3Coordinate c) {
		T3Event.MoveEvent e = new T3Event.MoveEvent(this, c);
		Iterator<T3Interface.View> list = listeners.iterator();
		while (list.hasNext()) {
			list.next().moveMade(e);
		}
	}
	
	private void fireGameEndedEvent() {
		BoardGameEvent e = new BoardGameEvent(this);
		Iterator<T3Interface.View> list = listeners.iterator();
		while (list.hasNext()) {
			list.next().gameEnded(e);
		}
	}
	
	private void fireLockInputEvent() {
		BoardGameEvent e = new BoardGameEvent(this);
		Iterator<T3Interface.View> list = listeners.iterator();
		while (list.hasNext()) {
			list.next().lockInput(e);
		}
	}
	
	private void fireUnlockInputEvent() {
		BoardGameEvent e = new BoardGameEvent(this);
		Iterator<T3Interface.View> list = listeners.iterator();
		while (list.hasNext()) {
			list.next().unlockInput(e);
		}
	}
	
	private void fireNotifyTurnEvent() {
		BoardGameEvent e = new BoardGameEvent(this);
		Iterator<T3Interface.View> list = listeners.iterator();
		while (list.hasNext()) {
			list.next().notifyTurn(e);
		}
	}
	
	private void fireIllegalMoveAttemptedEvent(T3Coordinate c) {
		T3Event.MoveEvent e = new T3Event.MoveEvent(this, c);
		Iterator<T3Interface.View> list = listeners.iterator();
		while (list.hasNext()) {
			list.next().illegalMoveAttempted(e);
		}
	}
	
	private void firePlayerAddedEvent(T3Player p) {
		T3Event.PlayerEvent pe = new T3Event.PlayerEvent(this, p);
		for(T3Interface.View l : listeners) {
			l.playerAdded(pe);
		}
	}
	
	private void firePlayerRemovedEvent(T3Player p) {
		T3Event.PlayerEvent pe = new T3Event.PlayerEvent(this, p);
		for(T3Interface.View l : listeners) {
			l.playerRemoved(pe);
		}
	}
	
	/*
	 * NESTED CLASSES
	 */
	
	public static class T3Board extends BoardGame.Board {
		private int boardSize;
		private char[][] cells;
		
		public T3Board(int size) {
			super(2);
			boardSize = size;
			cells = new char[size][size];
		}
		
		public char[][] getCells() {
			return this.cells;
		}
		
		public String toString() {
			String horizontal;
			String board;
			char temp;
			
			board = "";
			horizontal = " --- --- ---\n";
			
			for (char[] row : cells) {
				board += horizontal;
				for (char col : row) {
					if (Character.isLetter(col)) {
						//System.out.println("It's a letter");
						temp = col;
					} else {
						temp = ' ';
					}
					board += "| " + Character.toUpperCase(temp) + " ";
				}
				board += "|\n";
			}
			board += horizontal;
			
			return board;
			
		}
		
		protected boolean setMove(T3Coordinate c) {
			boolean result = false;
			if(!Character.isLetter(this.cells[c.getX()][c.getY()])) {
				this.cells[c.getX()][c.getY()] = c.getMark();
				result = true;
			}
			return result;
		}
		
		protected boolean hasWinner() {
			boolean result = false;
			for (int i=0; i<boardSize; i++) {
				/*
				System.out.println("iterator = " + i);
				System.out.println("RowSame: " + rowSame(i));
				System.out.println("ColumnSame: " + columnSame(i));
				System.out.println("DiagSame: " + diagSame());
				*/
				if (rowSame(i) || columnSame(i)) {
					result = true;
					break;
				}
			}
			if (diagSame()) { 
				result = true; 
			}
			return result;
		}
		
		private boolean rowSame(int row) {
			boolean result = true;
			
			for (int i=0; i<boardSize; i++) {
				
				result = result && (cells[row][0] == cells[row][i])
					&& Character.isLetter(cells[row][i]);
			}
			return result; 
		}
		
		private boolean columnSame(int col) {
			boolean result = true;
			for (int i=0; i<boardSize; i++) {
				result = result && (cells[0][col] == cells[i][col])
					&& Character.isLetter(cells[i][col]);
			}
			return result; 
		}
		
		private boolean diagSame() {
			boolean result = false;
			if(!Character.isLetter(cells[1][1])) { // center cell is null
				result = false;
			} else if ((!Character.isLetter(cells[0][0]) 
					|| !Character.isLetter(cells[2][2]))
					&& (!Character.isLetter(cells[0][2])
					|| !Character.isLetter(cells[2][0]))) {
				result = false;
			} else if ((cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2])
					|| (cells[0][2] == cells [1][1] && cells[1][1] == cells[2][0])) {
				result = true;
			} else {
				result = false;
			}
			/*		
			for (int i=0; i<boardSize; i++) {
				result = result && (((cells[0][0] == cells[i][i]) && Character.isLetter(cells[i][i]) )
					|| ((cells[0][2] == cells[i][2-i]) && Character.isLetter(cells[i][2-i])));
			}
			*/
			return result; 
		}
	}
	
	public static class T3Coordinate extends BoardGame.Piece {
		private final static int max = 2;
		private final static int min = 0;
		private int x;
		private int y;
		private char mark;
		
		public T3Coordinate(int x, int y, char mark) throws Exception {
			super();
			if (x > max || x < min || y > max || y < min
					|| (mark != 'x' && mark != 'o')) {
				/*
				System.out.println(x>max);
				System.out.println(x<min);
				System.out.println(y>max);
				System.out.println(y<min);
				System.out.println(mark!='x');
				System.out.println(mark!='o');
				*/
				throw new Exception("Incorrect Coordinate Initialized: " 
						+ x + " " + y + " " + mark);
			}
			this.x = x;
			this.y = y;
			this.mark = mark;
		}
		
		public int getX() {
			return this.x;
		}
		
		public int getY() {
			return this.y;
		}
		
		public char getMark() {
			return this.mark;
		}
	}
	
	public static class T3Player extends BoardGame.Player {
		
		private char mark;
		
		public T3Player(String name, int position, char mark) 
				throws Exception {
			super(name, position);
			if(mark != 'x' && mark != 'o') {
				throw new Exception("Invalid Mark Initialized: " + mark);
			}
			this.mark = mark;
		}
		
		public char getMark() {
			return this.mark;
		}
	}

}
