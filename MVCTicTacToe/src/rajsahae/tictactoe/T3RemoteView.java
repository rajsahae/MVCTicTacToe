package rajsahae.tictactoe;

import java.net.Socket;

import rajsahae.boardgame.BoardGameEvent;
import rajsahae.tictactoe.T3Event.MoveEvent;
import rajsahae.tictactoe.T3Event.PlayerEvent;
import rajsahae.tictactoe.T3Interface.Model;


public class T3RemoteView implements T3Interface.View {

	private T3Interface.Model model;
	private T3Interface.Controller controller;
	private Socket socket;
	
	public T3RemoteView(T3Interface.Model model, 
			T3Interface.Controller controller, Socket socket) {
		this.model = model;
		this.controller = controller;
		this.socket = socket;
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
		// TODO Auto-generated method stub
		
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
