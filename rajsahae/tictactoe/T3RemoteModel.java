package rajsahae.tictactoe;

import java.net.Socket;

import rajsahae.tictactoe.T3Interface.View;
import rajsahae.tictactoe.T3Model.T3Board;
import rajsahae.tictactoe.T3Model.T3Coordinate;

public class T3RemoteModel implements T3Interface.Model {
	private Socket socket;
	private T3Interface.View view;
	
	public T3RemoteModel(T3Interface.View view, Socket socket) {
		this.socket = socket;
		this.view = view;
	}
	
	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean setupBoard(char option) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void makeMove(T3Coordinate c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addPlayer(String playerName, char mark) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removePlayer(String playerName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getBoardAsString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentPlayerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addListener(View l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeListener(View l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T3Board getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
