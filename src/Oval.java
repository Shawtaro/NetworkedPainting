import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 * Oval.java
 * 
 * @author Shawtaroh Granzier-Nakajima, Eric M Evans
 * 
 * Implements Oval object
 */

public class Oval extends PaintObject {

	public Oval(Color color, Point point, Point point2) {
		super(color, point, point2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		
		int xs=(int)Math.min(this.getPointOne().getX(),this.getPointTwo().getX());
		int ys=(int)Math.min(this.getPointOne().getY(),this.getPointTwo().getY());
		
		g.fillOval( xs,ys,Math.abs(this.getPointOne().x-this.getPointTwo().x), Math.abs(this.getPointOne().y-this.getPointTwo().y));
	}

}
