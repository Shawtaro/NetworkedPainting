
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import javafx.scene.control.RadioButton;

/**
 * A JPanel GUI for Netpaint that has all paint objects drawn on it. Currently,
 * a list of paint objects is hardcoded. A JPanel exists in this JFrame that
 * will draw this list of paint objects.
 * 
 * @author Rick Mercer
 */

public class Client extends JFrame {

	//ThreadedEchoHandler readingThread;
	private static ObjectOutputStream outputToServer;
	private static ObjectInputStream inputToServer;
	private static boolean readMe=true;
	
	public static void main(String[] args) {
		PaintObject a = new Line(Color.RED, new Point(10, 100), new Point(500, 100));
		PaintObject e = new Rectangle(Color.PINK, new Point(200, 200), new Point(350, 500));
		PaintObject j = new Oval(Color.BLACK, new Point(500, 20), new Point(600, 220));
		allPaintObjects = new Vector<>();
		allPaintObjects.add(a);
		allPaintObjects.add(e);
		allPaintObjects.add(j);
		
		
		try {

			Socket server = new Socket("localhost", 4000);

			outputToServer = new ObjectOutputStream(server.getOutputStream());
			inputToServer = new ObjectInputStream(server.getInputStream());
			
			Client myGUI=new Client();
			
			while(readMe){
					//outputToServer.notify();
					//Thread.sleep(500);
					Vector<PaintObject> fromServer = (Vector<PaintObject>) inputToServer.readObject();
					allPaintObjects=fromServer;
					readMe=true;
					//drawingPanel.repaint();
			}
		} catch (Exception ex){
			
		}
	}

	public static Vector<PaintObject> getAllPaintObjects() {
		return allPaintObjects;
	}

	public static void setAllPaintObjects(Vector<PaintObject> allPaintObjects) {
		Client.allPaintObjects = allPaintObjects;
	}

	private static DrawingPanel drawingPanel;
	private static Vector<PaintObject> allPaintObjects;
	private JColorChooser tcc;
	private boolean paintObjectsChanged=false;
	private boolean isPainting=false;
	private Point pointOne,pointTwo;
	
	public boolean isPaintObjectsChanged() {
		return paintObjectsChanged;
	}

	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);
		//this.readingThread = new ThreadedEchoHandler(allPaintObjects);		
		setLocation(20, 20);
		setSize(800, 600);
		getContentPane().setLayout(
			    new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)
			);
		
		mouseListener paintBrush = new mouseListener(); 
		
		drawingPanel = new DrawingPanel();
		drawingPanel.setSize(getWidth()/2, getHeight()/2);
		drawingPanel.addMouseListener(paintBrush);
		JScrollPane scrollPane=new JScrollPane(drawingPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(50, 30, 300, 50);
		scrollPane.setPreferredSize(new Dimension(300,300));
		this.getContentPane().add(scrollPane);
		
		JPanel radioButtons=new JPanel();
		radioButtons.setLayout(
			    new BoxLayout(radioButtons, BoxLayout.X_AXIS)
			);
		
		JRadioButton lineButton = new JRadioButton("Line");
		JRadioButton rectButton = new JRadioButton("Rectangle");
		JRadioButton ovalButton = new JRadioButton("Oval");
		JRadioButton imageButton = new JRadioButton("Image");
		radioButtons.add(lineButton);
		radioButtons.add(rectButton);
		radioButtons.add(ovalButton);
		radioButtons.add(imageButton);

		this.getContentPane().add(radioButtons);
		
		tcc = new JColorChooser();
		this.getContentPane().add(tcc);
		setVisible(true);
	}

	/**
	 * This is where all the drawing goes.
	 * 
	 * @author mercer
	 */
	@SuppressWarnings("serial")
	class DrawingPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			// g.drawLine(10,20,30,40);
			// draw all of the paint objects
			for (PaintObject ob : allPaintObjects)
				ob.draw(g);
			Image icon;
				icon = new ImageIcon("nyan.gif").getImage();
				g.drawImage(icon, 0, 0, 200, 200, null);
			repaint();
		}
	}
	
	private class mouseListener implements MouseListener{
/*
		  public void tellEveryone() {
		    for (ObjectOutputStream output : clientOutputStreams) {
		      try {
		        output.writeObject(allPaintObjects);
		        output.flush();
		      } catch (Exception ex) {
		        clientOutputStreams.remove(output);
		      }
		    }
		  }*/
		void badFix(){
			allPaintObjects.add(new Line(Color.PINK,pointOne,pointTwo));
			drawingPanel.repaint();
			paintObjectsChanged=true;
			try {
				//outputToServer.reset();
				outputToServer.writeObject(allPaintObjects);
				//outputToServer.flush();
				//outputToServer.notify();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(!isPainting){
				pointOne=arg0.getPoint();
				isPainting=true;
			}
			else{
				readMe=false;
				pointTwo=arg0.getPoint();
				isPainting=false;
				allPaintObjects.add(new Line(Color.PINK,pointOne,pointTwo));
				drawingPanel.repaint();
				paintObjectsChanged=true;
				try {
					//outputToServer.reset();
					outputToServer.writeObject(allPaintObjects);
					//outputToServer.flush();
					//outputToServer.notify();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				badFix();
				//tellEveryone();
			}
			System.out.println("GOOD NEWS");
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
