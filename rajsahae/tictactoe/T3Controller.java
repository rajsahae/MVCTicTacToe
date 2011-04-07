/*
 * T3Controller
 * 
 * Version 1.0
 * 
 * Oct 2010
 * 
 * Written by Raj Sahae
 */
package rajsahae.tictactoe;

/**
 * The T3Controller class is the controller for the T3Model.  Connected
 * to a T3View, it recieves instructions via the T3Event and relays
 * those instructions to the T3Model via method calls.
 * 
 * @author	Raj Sahae
 * @version	1.0
 *
 */
public class T3Controller implements T3Interface.Controller {

	protected T3Interface.Model model;
	
	public T3Controller(T3Interface.Model model){
		this.model = model;
	}
	
	public void makeMove(T3Event.MoveEvent e){
			model.makeMove(e.getMove());
	}

}
