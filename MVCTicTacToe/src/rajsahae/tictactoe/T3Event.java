/*
 * T3Event
 * 
 * Version 1.0
 * 
 * Oct 2010
 * 
 * Written by Raj Sahae
 */
package rajsahae.tictactoe;
import rajsahae.boardgame.BoardGameEvent;

/**
 * Event class for the T3Model, T3View, and T3Controller
 * 
 * @author	Raj Sahae
 * @version	1.0
 */
public class T3Event extends BoardGameEvent{
		private static final long serialVersionUID = 1L;
		
		public T3Event(Object source){
			super(source);
		}
		
		public static class MoveEvent extends T3Event {
			private static final long serialVersionUID = 1L;
			T3Model.T3Coordinate c;
			
			public MoveEvent(Object source, T3Model.T3Coordinate c){
				super(source);
				this.c = c;
			}
			
			public T3Model.T3Coordinate getMove(){
				return c;
			}
		}
		
		public static class PlayerEvent extends T3Event {
			private static final long serialVersionUID = -3489667495506622430L;
			private T3Model.T3Player p;
			
			public PlayerEvent(Object source, T3Model.T3Player p) {
				super(source);
				this.p = p;
			}
			
			public T3Model.T3Player getPlayer() {
				return this.p;
			}
		}
		
	}