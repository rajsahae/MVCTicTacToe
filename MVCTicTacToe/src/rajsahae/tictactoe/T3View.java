package rajsahae.tictactoe;

import java.io.Closeable;
import java.util.LinkedList;
import java.util.List;

public abstract class T3View implements T3Interface.View {
	protected String name;
	protected char mark;
	protected int position;
	protected List<T3Interface.Controller> listeners = new LinkedList<T3Interface.Controller>();
	protected T3Interface.Model model;
	
	public T3View(String name, char mark, int position, T3Interface.Model model, T3Interface.Controller controller) {
		this.name = name;
		this.mark = mark;
		this.position = position;
		this.model = model;
		listeners.add(controller);
	}
	
	protected static void safeClose(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	public String getName() {
		return this.name;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public char getMark() {
		return this.mark;
	}
	
	/*
	 * SETTERS
	 */
	
	public void setModel(T3Interface.Model m) {
		this.model = m;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public void setPosition(int p) {
		this.position = p;
	}
	
	public void setMark(char m) {
		this.mark = m;
	}
	
	public boolean addListener(T3Interface.Controller l) {
		return listeners.add(l);
	}
	
	public boolean removeListener(T3Interface.Controller l) {
		return listeners.remove(l);
	}
	
	
}
