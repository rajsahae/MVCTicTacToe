package rajsahae.gameserver;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * GameServer has a single host which listens for incoming connections, then
 * assigns those connections to various ChatRooms.  Each BoardGameRoom is it's
 * own thread, and may contain many chat clients all talking to each
 * other.
 * 
 * @author Raj Sahae
 *
 */
public class BoardGameServer {
	
	private static int defaultPort		= 13000;
	private static long roomWaitTime		= 5000; // 5 seconds
	
	private int 				port;
	private BoardGameHost 			host;
	private ArrayList<BoardGameRoom> rooms;
	
	public BoardGameServer(int port, String[] roomNames) {
		this.port = port;
	 	rooms = new ArrayList<BoardGameRoom>();
		if(roomNames != null) {
			rooms.ensureCapacity(roomNames.length);
			for(String name : roomNames) {
				rooms.add(new BoardGameRoom(name));
			}
			for(BoardGameRoom room : rooms) {
				room.start();
			}
		}
		host = new BoardGameHost(this, port);
		host.start();
	}
	
	public BoardGameServer(String[] roomNames) {
		this(defaultPort, roomNames);
	}
	
	public BoardGameServer(int port) {
		this(port, null);
	}
	
	public int getPort() {
		return port;
	}
	
	public List<BoardGameRoom> getRoomList() {
		return rooms;
	}
	
	public static long getRoomWaitTime() {
		return roomWaitTime;
	}
	
	protected synchronized boolean createNewRoom(String newRoomName) {
		BoardGameRoom newRoom = new BoardGameRoom(newRoomName);
		newRoom.start();
		return rooms.add(newRoom);
	}
	
	public boolean roomExists(String roomName) {
		for(BoardGameRoom room : rooms) {
			if(roomName.equalsIgnoreCase(room.getRoomName())) {
				return true;
			}
		}
		return false;
	}
	
	public void addClientToRoom(Socket client, String roomName) {
		this.getRoomWithName(roomName).addOccupant(client);
	}
	
	protected BoardGameRoom getRoomWithName(String roomName) {
		BoardGameRoom target = null;
		for(BoardGameRoom room : rooms) {
			target = room;
			if(roomName.equalsIgnoreCase(room.getRoomName())) {
				break;
			}
		}
		return target;
	}
} 
