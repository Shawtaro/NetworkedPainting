import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

/*
 * Server.java
 * 
 * @author Shawtaroh Granzier-Nakajima, Eric M Evans
 * 
 * Communicates with servers via ThreadedListenForMyClientsUpdates.
 * 
 * Listens for new connecting clients.
 * 
 * contains master list of PaintObjects
 */

public class Server {
	
	private static ArrayList<ObjectOutputStream>	toClientOutputStreams;
	private static ArrayList<ObjectInputStream>		fromClientStreams;
	private ServerSocket							server;
	private Vector<PaintObject>						allPaintObjectsMaster;
	private int										numClients	= 0;
	private boolean									hasClients	= false;
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Server server = new Server();
		try {
			server.setUpStreams();
			server.listenForNewConnections();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/*
	 * Establish Connections
	 */
	private void setUpStreams() throws IOException {
		
		server = new ServerSocket(4000);
		toClientOutputStreams = new ArrayList<ObjectOutputStream>();
		fromClientStreams = new ArrayList<ObjectInputStream>();
		
	}
	
	
	
	/*
	 * Infinite Loop Checking for New Connections
	 * 
	 * Each new client is paired with a unique Thread that listens for its
	 * messages
	 */
	private void listenForNewConnections() throws IOException {
		
		new ExitThread(this).start();
		
		while (true) {
			Socket connection = server.accept();
			ObjectOutputStream outputToClient = new ObjectOutputStream(
			        connection.getOutputStream());
			ObjectInputStream inputFromClient = new ObjectInputStream(
			        connection.getInputStream());
			toClientOutputStreams.add(outputToClient);
			fromClientStreams.add(inputFromClient);
			this.oneMoreClient();
			
			Thread clientUpdates = new ThreadedListenForMyClientsUpdates(this,
			        inputFromClient, toClientOutputStreams);
			clientUpdates.start();
			System.out.println("Got connection");
			
		}
		
	}
	
	
	
	public Vector<PaintObject> getAllPaintObjectsMaster() {
		
		return allPaintObjectsMaster;
	}
	
	
	
	public void setAllPaintObjectsMaster(
	        Vector<PaintObject> allPaintObjectsMaster) {
		
		this.allPaintObjectsMaster = allPaintObjectsMaster;
	}
	
	
	
	public void oneMoreClient() {
		
		hasClients = true;
		this.numClients++;
	}
	
	
	
	public void oneLessClient() {
		
		this.numClients--;
	}
	
	
	
	public boolean noMoreClients() {
		
		return hasClients && numClients == 0;
	}
}