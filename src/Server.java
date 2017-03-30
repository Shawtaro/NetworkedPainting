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
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(4000);
			Vector<PaintObject> allPaintObjects = new Vector<>();
			ArrayList<ThreadedEchoHandler> connections = new ArrayList<ThreadedEchoHandler>();
			while (true) {
				Socket connection = server.accept();
				// Runnable r = new ThreadedEchoHandler(connection);
				connections.add(new ThreadedEchoHandler(connection));

				for (ThreadedEchoHandler t : connections) {
					Vector<PaintObject> clientPaintObjects = t.getMyClient().getAllPaintObjects();
					if (allPaintObjects.size() != clientPaintObjects.size()) {
						allPaintObjects = clientPaintObjects;
						for (ThreadedEchoHandler t2 : connections) {
							t2.getMyClient().setAllPaintObjects(allPaintObjects);
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
