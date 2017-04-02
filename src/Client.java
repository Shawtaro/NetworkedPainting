
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Eric M. Evans, Shawtaroh Granzier-Nakajima
 * 
 * A JPanel GUI for Netpaint that has all paint objects drawn on it. A JPanel
 * exists in this JFrame that will draw this list of paint objects.
 * 
 * @author Rick Mercer
 */

public class Client extends JFrame {
	
	private static final long	serialVersionUID	= -6200411504396562004L;
	private ObjectOutputStream	outputToServer;
	private ObjectInputStream	inputFromServer;
	private Socket				server;
	
	private DrawingPanel		drawingPanel;
	private Vector<PaintObject>	allPaintObjects		= new Vector<PaintObject>();
	private JColorChooser		tcc;
	private boolean				isPainting			= false;
	private Point				pointOne, pointTwo;
	private PaintObject			tempPaintObject;
	public Color				color				= Color.BLACK;
	private JRadioButton		lineButton;
	private JRadioButton		rectButton;
	private JRadioButton		ovalButton;
	private JRadioButton		imageButton;
	private JRadioButton		selectedButton;
	
	
	
	public static void main(String[] args) {
		
		Client myGUI = new Client();
		try {
			myGUI.listenForUpdates();
		}
		catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/*
	 * Listen for Update from Server
	 */
	@SuppressWarnings("unchecked")
	private void listenForUpdates() throws IOException, ClassNotFoundException {
		
		/*
		 * Establish Server Connection
		 */
		server = new Socket("localhost", 4000);
		outputToServer = new ObjectOutputStream(server.getOutputStream());
		inputFromServer = new ObjectInputStream(server.getInputStream());
		
		/*
		 * Listen for Updates from Server
		 */
		while (true) {
			System.out.println("waiting...");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.allPaintObjects = (Vector<PaintObject>) inputFromServer
			        .readObject();
			System.out.println("just updated");
			drawingPanel.repaint();
			resize(this.getWidth()+1,this.getHeight()+1);
			resize(this.getWidth()-1,this.getHeight()-1);
		}
		
	}
	
	
	
	/*
	 * constructor
	 */
	public Client() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(20, 20);
		setSize(800, 600);
		getContentPane()
		        .setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		MyMouseListener paintBrush = new MyMouseListener();
		
		drawingPanel = new DrawingPanel();
		drawingPanel.setPreferredSize(new Dimension(getWidth() , getHeight() ));
		drawingPanel.addMouseListener(paintBrush);
		drawingPanel.addMouseMotionListener(paintBrush);
		JScrollPane scrollPane = new JScrollPane(drawingPanel,
		        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
		        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//scrollPane.setBounds(50, 30, 300, 50);
		
		scrollPane.setPreferredSize(new Dimension(300, 300));
		this.getContentPane().add(scrollPane);
		
		JPanel radioButtons = new JPanel();
		radioButtons.setLayout(new BoxLayout(radioButtons, BoxLayout.X_AXIS));
		
		ButtonGroup groupRadio = new ButtonGroup();
		lineButton = new JRadioButton("Line");
		radioButtons.add(lineButton);
		groupRadio.add(lineButton);
		lineButton.addActionListener(new RadioListener(lineButton));
		lineButton.setSelected(true);
		rectButton = new JRadioButton("Rectangle");
		radioButtons.add(rectButton);
		groupRadio.add(rectButton);
		rectButton.addActionListener(new RadioListener(rectButton));
		ovalButton = new JRadioButton("Oval");
		radioButtons.add(ovalButton);
		groupRadio.add(ovalButton);
		ovalButton.addActionListener(new RadioListener(ovalButton));
		imageButton = new JRadioButton("Image");
		radioButtons.add(imageButton);
		groupRadio.add(imageButton);
		imageButton.addActionListener(new RadioListener(imageButton));
		
		this.getContentPane().add(radioButtons);
		
		tcc = new JColorChooser();
		tcc.getSelectionModel().addChangeListener(new ColorListener());
		this.getContentPane().add(tcc);
		setVisible(true);
	}
	
	
	
	/*
	 * return the PaintObject currently being made
	 */
	private PaintObject figurePaintObject(Point p1, Point p2) {
		
		if (selectedButton == null)
			selectedButton = lineButton;
		
		if (selectedButton.equals(lineButton)) {
			return new Line(color, p1, p2);
		}
		else if (selectedButton.equals(rectButton)) {
			return new Rectangle(color, p1, p2);
		}
		else if (selectedButton.equals(ovalButton)) {
			return new Oval(color, p1, p2);
		}
		else if (selectedButton.equals(imageButton)) {
			return new ImagePO(color, p1, p2);
		}
		
		return null;
	}
	
	
	
	/*
	 * listens for shape choice
	 */
	class RadioListener implements ActionListener {
		
		private JRadioButton button;
		
		
		
		public RadioListener(JRadioButton button) {
			this.button = button;
		}
		
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (button.equals(lineButton)) {
				selectedButton = lineButton;
			}
			else if (button.equals(rectButton)) {
				selectedButton = rectButton;
			}
			else if (button.equals(ovalButton)) {
				selectedButton = ovalButton;
			}
			else if (button.equals(imageButton)) {
				selectedButton = imageButton;
			}
			
		}
		
	}
	
	
	
	/*
	 * get color from JColorChooser
	 */
	class ColorListener implements ChangeListener {
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			
			color = tcc.getColor();
		}
		
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
			if (allPaintObjects != null)
				for (PaintObject ob : allPaintObjects)
					ob.draw(g);
				
			if (tempPaintObject != null)
				tempPaintObject.draw(g);
						
			repaint();
			resize(801,601);
			resize(800,600);
		}
	}
	
	
	/*
	 * Listens for Mouse
	 */
	private class MyMouseListener
	        implements MouseListener, MouseMotionListener {
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
			if (!isPainting) {
				isPainting = true;
				pointOne = arg0.getPoint();
			}
			else {
				isPainting = false;
				pointTwo = arg0.getPoint();
				
				if (allPaintObjects == null)
					allPaintObjects = new Vector<PaintObject>();
				allPaintObjects.add(figurePaintObject(pointOne, pointTwo));
				try {
					outputToServer.writeObject(allPaintObjects);
					System.out.println("NEW PAINT OBJECT");
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				tempPaintObject = null;
			}
			System.out.println("click");
		}
		
		
		
		@Override
		public void mouseMoved(MouseEvent arg0) {
			
			if (!isPainting)
				return;
			
			// System.out.println("move");
			
			Point pt = new Point(arg0.getX(), arg0.getY());
			
			tempPaintObject = figurePaintObject(pointOne, pt);
			
			//repaint();
			resize(801,601);
			resize(800,600);
		}
		
		
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}
		
		
		
		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}
		
		
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}
		
		
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
		
		
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			
		}
		
	}
}
