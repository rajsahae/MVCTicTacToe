/**
 * BoardGameEvent
 * 
 * @Version 1.0
 * 
 * @date 	Oct 2010
 * 
 * @author Raj Sahae
 */
package rajsahae.boardgame;

import java.util.EventObject;

public class BoardGameEvent extends EventObject {
	private static final long serialVersionUID = 4543579682732986136L;
	
	public BoardGameEvent(Object source){
		super(source);
	}
}