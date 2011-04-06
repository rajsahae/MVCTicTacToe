/**
 * BoardGame
 * 
 * @Version 1.0
 * 
 * @date 	Oct 2010
 * 
 * @author 	Raj Sahae
 */
package rajsahae.boardgame;

import java.util.UUID;

public abstract class BoardGame {
	public static int maxPlayers;
	public static int minPlayers;
	public static int recommendedNumPlayers;
	
	public int numDesiredPlayers;
	
	protected String name;

	
	public BoardGame(String name, int numDesired) {
		this.name = name;
		this.numDesiredPlayers = numDesired;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumDesiredPlayers() {
		return numDesiredPlayers;
	}
	
	public static void setMaxPlayers(int num) {
		BoardGame.maxPlayers = num;
	}
	
	public static void setMinPlayers(int num) {
		BoardGame.minPlayers = num;
	}
	
	public static void setRecNumPlayers(int num) {
		BoardGame.recommendedNumPlayers = num;
	}
	
	public abstract void startGame();
	
	public static abstract class Piece {
		private UUID objectid;
		
		public Piece() {
			this.objectid = UUID.randomUUID();
		}
		
		public String getID() {
			return this.objectid.toString();
		}
	}
	
	public static abstract class Board extends Piece {
		private int numPlayers;
		
		public Board(int num) {
			super();
			this.setNumPlayers(num);
		}

		/**
		 * @param numPlayers the numPlayers to set
		 */
		public void setNumPlayers(int numPlayers) {
			this.numPlayers = numPlayers;
		}

		/**
		 * @return the numPlayers
		 */
		public int getNumPlayers() {
			return numPlayers;
		}
	}
	
	public static abstract class Player extends Piece {
		protected String name;
		protected int position;
		protected boolean isTurn;
		
		public Player(String name, int position) {
			super();
			this.name = name;
			this.position = position;
			this.isTurn = false;
		}
		
		public String getName() {
			return name;
		}
		
		public int getPosition() {
			return position;
		}
		
		public void giveTurn() {
			setTurn(true);
		}
		
		public void loseTurn() {
			setTurn(false);
		}
		
		private void setTurn(boolean value) {
			this.isTurn = value;
		}
		
		public boolean hasTurn() {
			return this.isTurn;
		}
	}
	
	public static abstract class PlayerBoard extends Board {
		protected Player player;
		
		public PlayerBoard(int num, Player p) {
			super(num);
			this.player = p;
		}
		
		public Player getPlayer() {
			return player;
		}
	}
	
}
