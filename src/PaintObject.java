import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/*
 * PaintObject.java
 * 
 * @author Shawtaroh Granzier-Nakajima, Eric M Evans
 * 
 * Abstract class for paint objects
 */

public class PaintObject implements Serializable{

	private Point pointOne;
	private Point pointTwo;
	private Color color;

	public PaintObject(Color color, Point one, Point two) {
		this.color = color;
		this.pointOne = one;
		this.pointTwo = two;
	}

	public Point getPointOne() {
		return pointOne;
	}

	public Point getPointTwo() {
		return pointTwo;
	}

	public Color getColor() {
		return color;
	}

	public void draw(Graphics g) {
		this.draw(g);
	}

}
