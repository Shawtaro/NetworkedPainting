import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

/*
 * Server.java
 * @author Shawtaroh Granzier-Nakajima
 * Implements simple server, quiz 11
 */

public class Server {
	private static ArrayList<ObjectOutputStream> clientOutputStreams;
	private static ArrayList<ObjectInputStream> fromClientStreams;

	private static Vector<PaintObject> allPaintObjectsMaster;

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		ServerSocket server;
		try {
			server = new ServerSocket(4000);
			Vector<PaintObject> allPaintObjectsMaster = new Vector<>();
			clientOutputStreams = new ArrayList<ObjectOutputStream>();
			fromClientStreams = new ArrayList<ObjectInputStream>();
			Thread t2 = new ThreadedListenConnections(clientOutputStreams, server, fromClientStreams);
			t2.start();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		// fromClientStreams.

	}
}
