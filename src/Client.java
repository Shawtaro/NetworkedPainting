
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.BoxLayout;
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

	public static void main(String[] args) {
		PaintObject a = new Line(Color.RED, new Point(10, 100), new Point(500, 100));
		PaintObject e = new Rectangle(Color.PINK, new Point(200, 200), new Point(350, 500));
		PaintObject j = new Oval(Color.BLACK, new Point(500, 20), new Point(600, 220));
		allPaintObjects = new Vector<>();
		allPaintObjects.add(a);
		allPaintObjects.add(e);
		allPaintObjects.add(j);
		
		Client client = new Client();


		client.setVisible(true);
	}

	public static Vector<PaintObject> getAllPaintObjects() {
		return allPaintObjects;
	}

	public static void setAllPaintObjects(Vector<PaintObject> allPaintObjects) {
		Client.allPaintObjects = allPaintObjects;
	}

	private DrawingPanel drawingPanel;
	private static Vector<PaintObject> allPaintObjects;
	private JColorChooser tcc;
	
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);

		setLocation(20, 20);
		setSize(800, 600);
		getContentPane().setLayout(
			    new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)
			);
		
		drawingPanel = new DrawingPanel();
		drawingPanel.setSize(getWidth()/2, getHeight()/2);
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
			repaint();
		}
	}
}
