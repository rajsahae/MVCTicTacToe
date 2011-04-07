/*
 * MVCTicTacToe
 * 
 * Version 1.0
 * 
 * Oct 2010
 * 
 * Written by Raj Sahae
 */
package rajsahae.tictactoe;

import java.util.*;

/**
 * MVCTicTacToe is the top level class for the T3 TicTacToe game.
 * This class creates the Model, Views, and Controllers and initiates
 * the gameplay.
 * 
 * @author Raj Sahae
 * @version 1.0
 *
 */
public class MVCTicTacToe {
	
	private static T3Model game= new T3Model("Tic Tac Toe");
	
	private static List<T3View> outputs = 
		new LinkedList<T3View>();
	
	private static List<T3Controller> inputs = 
		new LinkedList<T3Controller>();
	
	private static String botWeightsFile = "Fitness-5.0";

	/**
	 * @param args - String containing 2 flags: 
	 * @param -s or -d specifying single or double player.
	 * @param -x or -o specifying whether the single player will play x's or o's
	 * 
	 */
	public static void main(String[] args) {
		initialize();
		if (game.setupBoard('x')){ 
			game.startGame();
		}
	}

	private static void initialize(){
		// create player1-BOT
		// create controller(model)
		inputs.add(new T3Controller(game));
		
		// create view (controller)
		outputs.add(new T3Bot("Bot", 'x', 1, game, inputs.get(0), botWeightsFile));
		//outputs.add(new T3View("Raj1", 'x', 1, game, inputs.get(0)));
		
		// create player2
		// create controller(model)
		inputs.add(new T3Controller(game));
		// create view (controller)
		outputs.add(new T3PlayerView("Raj2", 'o', 2, game, inputs.get(1)));
		
		// add player1 as player and listener
		game.addPlayer(outputs.get(0).getName(), outputs.get(0).getMark());
		game.addPlayer(outputs.get(1).getName(), outputs.get(1).getMark());
		// add player2 as player and listener
		game.addListener(outputs.get(0));
		game.addListener(outputs.get(1));
	}

}
