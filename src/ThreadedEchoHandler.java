import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

class ThreadedEchoHandler implements Runnable {

	private Socket connection;
	private static ArrayList<ObjectOutputStream> clientOutputStreams;
	private Client myClient;
	// private ObjectInputStream reader;
	private static ObjectInputStream toServerStreams;

	public ThreadedEchoHandler(Vector<PaintObject> allPaintObjects) {

	}

	public Client getMyClient() {
		return myClient;
	}

	public void setMyClient(Client myClient) {
		this.myClient = myClient;
	}

	@Override
	public void run() {
		String message;
		try {

			while (true) {
				// Wait for the client send a writeObject message to the server
				message = (String) reader.readObject();
				// Send the same message from the server to all clients
				// tellEveryone(message);
			}
		} catch (Exception ex) {

		}
	}
}