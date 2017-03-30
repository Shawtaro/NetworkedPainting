import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Line extends PaintObject {

	public Line(Color color, Point point, Point point2) {
		super(color, point, point2);
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.drawLine( (int)this.getPointTwo().getX(), (int)this.getPointTwo().getY(),this.getPointOne().x, this.getPointOne().y);
	}

}
