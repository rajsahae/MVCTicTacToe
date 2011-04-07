/*
 * BoardGameInterface
 * 
 * Version 1.0
 * 
 * Oct 2010
 * 
 * Written by Raj Sahae
 */
package rajsahae.boardgame;

public interface BoardGameInterface {

	public static interface View {
		public void playerAdded(BoardGameEvent e);
		public void playerDropped(BoardGameEvent e);
		public void gameStarted(BoardGameEvent e);
		public void gamePaused(BoardGameEvent e);
		public void gameEnded(BoardGameEvent e);
		public void lockInput(BoardGameEvent e);
		public void unlockInput(BoardGameEvent e);
		public void notifyTurn(BoardGameEvent e);
		public void illegalMoveAttempted(BoardGameEvent e);
	}
	
	public static interface Model {
		
	}
	
	public static interface Controller {
		
	}
	
}
