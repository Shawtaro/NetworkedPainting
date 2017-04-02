/*
 * @author Eric M. Evans, Shawtaroh Granzier-Nakajima
 * 
 * continous running thread that checks if all the clients have exited. 
 * If they have, then System exits.
 */
public class ExitThread extends Thread {
	
	private Server server;
	
	
	
	public ExitThread(Server server) {
		this.server = server;
	}
	
	
	
	@Override
	public void run() {
		
		while (true) {
			if (server.noMoreClients())
				System.exit(0);
			try {
				Thread.sleep(500);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			// System.out.println(server.numClients);
		}
	}
	
}
