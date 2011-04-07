package rajsahae.tictactoe;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rajsahae.neuralnet.*;

import rajsahae.boardgame.BoardGameEvent;
import rajsahae.tictactoe.T3Event.MoveEvent;
import rajsahae.tictactoe.T3Event.PlayerEvent;
import rajsahae.tictactoe.T3Interface.Controller;
import rajsahae.tictactoe.T3Model.T3Board;
import rajsahae.tictactoe.T3Model.T3Coordinate;

public class T3Bot extends T3View {
	
	private NeuralNet botNeuralNet;
	private double markDouble;
	
	// Neural Net Parameters
	private int numInputs = 10;
	private int numOutputs = 2;
	private int numHiddenLayers = 1;
	private int[] hiddens = {5};
	private String weightsFile;
	
	public T3Bot(String name, char mark, int position, T3Model model,
			T3Interface.Controller controller, String weightsFile) {
		super(name, mark, position, model, controller);
		
		this.weightsFile = weightsFile;
		botNeuralNet = new NeuralNet(numInputs, numOutputs, numHiddenLayers, hiddens);
		botNeuralNet.setWeights(this.getWeightsFromFile(this.weightsFile));
		
		double temp = 0.0;
		
		if(this.mark == 'x'){
			temp = 1.0;
		} else {
			temp = -1.0;
		}
		this.markDouble = temp;
	}
	
	private List<Double> getWeightsFromFile(String filename) {
		BufferedReader inputStream = null;
		ArrayList<Double> weightsList = new ArrayList<Double>(botNeuralNet.getNumberOfWeights());
		
		try {
			inputStream = new BufferedReader(new FileReader(filename));

			String line = "";
			while((line = inputStream.readLine()) != null) {
				weightsList.add(Double.parseDouble(line));
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Neural Net input weights filename is not valid.");
			ex.printStackTrace();
			System.exit(1);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		
		assert (weightsList.size() == botNeuralNet.getNumberOfWeights());
		safeClose(inputStream);
		
		return weightsList;
			
	}
	
	private void makeMove(BoardGameEvent e) {
		try {
			ArrayList<Double> inputs = getInputsFromBoard(this.model.getBoard());
			List<Double> outputs;
			int x = 0;
			int y = 0;
			T3Coordinate c;
			MoveEvent moveEvent;
			
			outputs = botNeuralNet.update(inputs);
			x = (int)Math.round(outputs.get(0));
			y = (int)Math.round(outputs.get(1));
			
			while(this.model.getBoard().getCells()[x][y] != Character.UNASSIGNED){
				//Move already exists.
				//Make a random move
				x = new Random().nextInt(this.model.getBoard().getCells().length);
				y = new Random().nextInt(this.model.getBoard().getCells()[0].length);
			} 
			
			c = new T3Coordinate(x, y, this.mark);
			moveEvent = new MoveEvent(this, c);
			
			System.out.println(this.model.getBoard().toString());
			/*
			System.out.println("Inputs are:");
			System.out.println(inputs.toString());
			System.out.println("Attempting move: " + x + ", " + y);
			*/
			for(Controller l : listeners) {
				l.makeMove(moveEvent);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	private ArrayList<Double> getInputsFromBoard(T3Board board) {
		// TODO Auto-generated method stub
		char[][] boardCells = board.getCells();
		ArrayList<Double> botInput;
		botInput = new ArrayList<Double>(botNeuralNet.getNumberOfInputs());
		
		for(char[] line : boardCells) {
			for(char cell : line) {
				double temp;
				if(cell == 'x'){ temp = 1.0;}
				else if (cell == 'o'){ temp = -1.0;}
				else { temp = 0.0; }
				botInput.add(temp);
			}
		}
		botInput.add(this.markDouble);
		
		return botInput;
	}

	@Override
	public void playerAdded(BoardGameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerDropped(BoardGameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameStarted(BoardGameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gamePaused(BoardGameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameEnded(BoardGameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lockInput(BoardGameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockInput(BoardGameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyTurn(BoardGameEvent e) {
		this.model = (T3Model)e.getSource();
		if (this.model.getCurrentPlayerName() == this.getName()) {
			System.out.println("Bots turn to make a move");
			this.makeMove(e);
		}
	}

	@Override
	public void illegalMoveAttempted(BoardGameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveMade(MoveEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerAdded(PlayerEvent pe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerRemoved(PlayerEvent pe) {
		// TODO Auto-generated method stub
		
	}
}
