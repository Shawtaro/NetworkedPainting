import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

class ThreadedEchoHandler implements Runnable {

	private Socket connection;
	private Client myClient;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public ThreadedEchoHandler(Socket connection) {
		this.connection = connection;
	}

	public Client getMyClient() {
		return myClient;
	}

	public void setMyClient(Client myClient) {
		this.myClient = myClient;
	}

	public Socket getConnection() {
		return connection;
	}

	@Override
	public void run() {
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
			myClient = new Client();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void write(Object obj) {
		try {
			myClient.setAllPaintObjects((Vector<PaintObject>) in.readObject());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void read() {
		try {
			out.writeObject(myClient.getAllPaintObjects());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}