import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class ThreadedListenForNewClients extends Thread {
	
	private ArrayList<ObjectOutputStream>	clientOutputStreams;
	private ArrayList<ObjectInputStream>	fromClientStreams;
	
	
	
	public ThreadedListenForNewClients(
	        ArrayList<ObjectOutputStream> clientOutputStreams,
	        ServerSocket server,
	        ArrayList<ObjectInputStream> fromClientStreams) {
		// super();
		this.clientOutputStreams = clientOutputStreams;
		this.server = server;
		this.fromClientStreams = fromClientStreams;
	}
	
	
	
	private ServerSocket server;
	
	
	
	@Override
	public void run() {

		try {
			while (true) {
				Socket connection = server.accept();
				ObjectOutputStream outputToClient = new ObjectOutputStream(
				        connection.getOutputStream());
				ObjectInputStream inputFromClient = new ObjectInputStream(
				        connection.getInputStream());
				clientOutputStreams.add(outputToClient);
				fromClientStreams.add(inputFromClient);
				
				Thread clientUpdates = new ThreadedListenForMyClientsUpdates(
				        inputFromClient, clientOutputStreams);
				clientUpdates.start();
				System.out.println("Got connection");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
