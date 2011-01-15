import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.BasicStroke;

public class ImagePanel extends JPanel implements MouseListener,
		MouseMotionListener {
	
	
	/****************************** CLASS'VARIABLES ****************************************************************************/
	
	BufferedImage image;
	
	/* CROPPING */
	private int x1, x2, y1, y2;
	private boolean cropping;
	
	
	//private PrintPix p = new PrintPix();

	/******************************** CLASS' CONSTRUCTOR ***************************************************************************/
	public ImagePanel() {
	}

	/******************************** SET IMAGE *************************************************************************************/
	public void setImage(BufferedImage img) {
		this.image = img;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/*************************************GET IMAGE ************************************************************************************/
	public BufferedImage getImage() {
		return this.image;
	}

	/********************************** PAINT COMPONENT WHICH PAIN THE IMAGE IN THE PANEL *******************************************/
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g; 
		// Draw the grid from the pre-computed image
		g2.drawImage(image, null, 0, 0);
		
		/* CROPPING */
		if (cropping) {
			// Paint the area we are going to crop.
			Stroke stroke = new BasicStroke(2, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL, 0, new float[] { 12, 12 }, 0);
			g2.setStroke(stroke);
			g2.setColor(Color.RED);
			g2.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2),
					Math.max(y1, y2));
		}
		
	}

	/****************************************** MOUSE EVENTS AND MOTIONS ********************************************************/
	/** MOUSE CLICKED **/
	public void mouseClicked(MouseEvent evt) {
		
	}

	/** MOUSE ENTERED **/
	public void mouseEntered(MouseEvent evt) {
	}

	/** MOUSE EXITED **/
	public void mouseExited(MouseEvent evt) {
	}

	/** MOUSE PRESSED **/
	public void mousePressed(MouseEvent evt) {
		/* CROPPING */
		this.x1 = evt.getX();
		this.y1 = evt.getY();
	}

	/** MOUSE RELEASED **/
	public void mouseReleased(MouseEvent evt) {
		/* CROPPING */
		this.cropping = false;
		// Now we crop the image; This is the method a wrote in the other snipped
		BufferedImage cropped = crop(image, new Rectangle(Math.min(x1, x2),
				Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2)));
		// Now you have the cropped image; You have to choose what you want to do with it
		this.image = cropped;
	}

	/** MOUSE DRAGGED **/
	public void mouseDragged(MouseEvent evt) {
		/* CROPPING */
		cropping = true;
		this.x2 = evt.getX();
		this.y2 = evt.getY();
		this.repaint();
	}

	/** MOUSE MOVED **/
	public void mouseMoved(MouseEvent evt) {
		
	}

	/********************************************** CROPPING FUNCTION *******************************************************/
	private BufferedImage crop(BufferedImage src, Rectangle rect) {
		BufferedImage dest = new BufferedImage((int) rect.getWidth(),
				(int) rect.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics g = dest.getGraphics();
		g.drawImage(src, 0, 0, (int) rect.getWidth(), (int) rect.getHeight(),
				(int) rect.getX(), (int) rect.getY(), (int) rect.getX()
						+ (int) rect.getWidth(),
				(int) (rect.getY() + (int) rect.getHeight()), null);
		g.dispose();
		return dest;
	}

}


	