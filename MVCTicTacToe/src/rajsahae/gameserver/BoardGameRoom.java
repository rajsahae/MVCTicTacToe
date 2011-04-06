package rajsahae.gameserver;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import rajsahae.boardgame.BoardGame;

public class BoardGameRoom extends Thread {
	private String name;
	private ArrayList<Socket> occupants;
	private BoardGame game = null;
	
	public BoardGameRoom(String name, BoardGame game) {
		this.name = name;
		this.game = game;
	}
	
	public String getRoomName() {
		return name;
	}
	
	public List<Socket> getOccupants() {
		return occupants;
	}
	
	public synchronized boolean addOccupant(Socket occupant) {
		return occupants.add(occupant);
	}
	
	public void run() {
		while( (occupants.size() < game.getNumDesiredPlayers())) {// || (game == null) ){
			try {
				this.wait(BoardGameServer.getRoomWaitTime());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		game.startGame();
	}
	
	
}
