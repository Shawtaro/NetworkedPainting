import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/*
 * Rectangle.java
 * 
 * @author Shawtaroh Granzier-Nakajima, Eric M Evans
 * 
 * Implements rectangle object
 */

public class Rectangle extends PaintObject {

	public Rectangle(Color color, Point point, Point point2) {
		super(color, point, point2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		
		int xs=(int)Math.min(this.getPointOne().getX(),this.getPointTwo().getX());
		int ys=(int)Math.min(this.getPointOne().getY(),this.getPointTwo().getY());
		
		g.fillRect( xs,ys,Math.abs(this.getPointOne().x-this.getPointTwo().x), Math.abs(this.getPointOne().y-this.getPointTwo().y));
	}

}
