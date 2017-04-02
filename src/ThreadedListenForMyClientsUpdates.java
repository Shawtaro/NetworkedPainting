import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
/*
 * @author Eric M. Evans, Shawtaroh Granzier-Nakajima
 * 
 * Handles communication between server and clients
 */
public class ThreadedListenForMyClientsUpdates extends Thread {
	
	private ObjectInputStream				fromMyClientStream;
	private ArrayList<ObjectOutputStream>	toAllClientStreams;
	private Server							server;
	
	
	
	/*
	 * constructor
	 */
	public ThreadedListenForMyClientsUpdates(Server server,
	        ObjectInputStream inputFromClient,
	        ArrayList<ObjectOutputStream> clientOutputStreams) {
		this.server = server;
		this.fromMyClientStream = inputFromClient;
		this.toAllClientStreams = clientOutputStreams;
		
		/*
		 * make sure newest server gets most recent paintObjects
		 */
		try {
			this.tellEveryone();
		}
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * Checks for updates from my client.
	 * 
	 * If there's an update, send to all other clients
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
		while (true) {
			if (fromMyClientStream != null) {
				try {
					Thread.sleep(500);
					
					// Check for an Update from My Client & set
					// AllPaintObjectsMaster in server
					this.server.setAllPaintObjectsMaster(
					        (Vector<PaintObject>) fromMyClientStream
					                .readObject());
					System.out.println("server just got update");
					this.tellEveryone();
				}
				/*
				 * Client has exited
				 */
				catch (ClassNotFoundException | IOException
				        | InterruptedException e) {
					System.out.println("exited");
					server.oneLessClient();
					return;
				}
			}
		}
	}
	
	
	
	/*
	 * send update to all other clients
	 */
	private void tellEveryone() throws IOException, InterruptedException {
		
		for (ObjectOutputStream output : toAllClientStreams) {
			output.reset();
			output.writeObject(this.server.getAllPaintObjectsMaster());
			System.out.println("UPDATINGLINES");
			output.flush();
			// Thread.sleep(50);
		}
		
	}
	
}
