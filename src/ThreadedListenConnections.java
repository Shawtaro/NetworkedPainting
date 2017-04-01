import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class ThreadedListenConnections extends Thread{

	private ArrayList<ObjectOutputStream> clientOutputStreams;
	private ArrayList<ObjectInputStream> fromClientStreams;
	public ThreadedListenConnections(ArrayList<ObjectOutputStream> clientOutputStreams, ServerSocket server,ArrayList<ObjectInputStream> fromClientStreams) {
		//super();
		this.clientOutputStreams = clientOutputStreams;
		this.server = server;
		this.fromClientStreams=fromClientStreams;
	}

	private ServerSocket server;

		@Override
		public void run() {
			try {
				while (true) {
					Socket connection = server.accept();
					ObjectOutputStream outputToClient = new ObjectOutputStream(connection.getOutputStream());
					ObjectInputStream inputFromClient = new ObjectInputStream(connection.getInputStream());
					clientOutputStreams.add(outputToClient);
					fromClientStreams.add(inputFromClient);
					Thread t3 = new ThreadedListenUpdates(inputFromClient,clientOutputStreams);
					t3.start();
					//Thread t = new Thread(new ThreadedEchoHandler(connection, clientOutputStreams,toServerStreams));
					//t.start();
					System.out.println("Got connection");					//for()
					
					
	/*
					for (ThreadedEchoHandler t : connections) {
						Vector<PaintObject> clientPaintObjects = t.getMyClient().getAllPaintObjects();
						if (allPaintObjects.size() != clientPaintObjects.size()) {
							allPaintObjects = clientPaintObjects;
							for (ThreadedEchoHandler t2 : connections) {
								t2.getMyClient().setAllPaintObjects(allPaintObjects);
							}
						}
					}*/
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}