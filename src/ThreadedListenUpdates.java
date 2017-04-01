import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

public class ThreadedListenUpdates extends Thread {

	private ObjectInputStream fromClientStreams;
	private static Vector<PaintObject> allPaintObjectsMaster;
	private ArrayList<ObjectOutputStream> clientOutputStreams;
	
	public ThreadedListenUpdates(ObjectInputStream inputFromClient, ArrayList<ObjectOutputStream> clientOutputStreams) {
		// TODO Auto-generated constructor stub
		this.fromClientStreams=inputFromClient;
		this.clientOutputStreams=clientOutputStreams;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try{
		while (true) {
			if (fromClientStreams != null) {
				Thread.sleep(500);
					if ((Vector<PaintObject>) fromClientStreams.readObject() != null) {
						System.out.println("readinstuuff");
						allPaintObjectsMaster = (Vector<PaintObject>) fromClientStreams.readObject();
						for (ObjectOutputStream output : clientOutputStreams) {
							try {
								output.reset();
								output.writeObject(allPaintObjectsMaster);
								System.out.println("UPDATINGLINES");
								output.flush();
								Thread.sleep(500);
							} catch (Exception ex) {
								clientOutputStreams.remove(output);
							}
						}
				}
			}
		}
		}
		catch(Exception e){
			
		}
	}

}
