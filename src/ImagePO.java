import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/*
 * @author Eric M. Evans, Shawtaroh Granzier-Nakajima
 * 
 * for painting images
 */
public class ImagePO extends PaintObject {

	private static final long serialVersionUID = 7100672784801345724L;

	public ImagePO(Color color, Point one, Point two) {
		super(color, one, two);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(this.getColor());

		int xs = (int) Math.min(this.getPointOne().getX(), this.getPointTwo().getX());
		int ys = (int) Math.min(this.getPointOne().getY(), this.getPointTwo().getY());

		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("nyan.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//3 cases for flipping image
		if (this.getPointOne().getX() < this.getPointTwo().getX()) {
			// case 4
			if (this.getPointOne().getY() > this.getPointTwo().getY()){
				AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
				tx.translate(0, -image.getHeight(null));
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
			}
		}
		// case 2 and 3
		else {
			// case 2
			if (this.getPointOne().getY() < this.getPointTwo().getY()) {
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(null), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
			}
			// case 3
			else {
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(null), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
				tx = AffineTransform.getScaleInstance(1, -1);
				tx.translate(0, -image.getHeight(null));
				op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
			}
		}
		g.drawImage(image, xs, ys, Math.abs(this.getPointOne().x - this.getPointTwo().x),
				Math.abs(this.getPointOne().y - this.getPointTwo().y), null);

	}

}
