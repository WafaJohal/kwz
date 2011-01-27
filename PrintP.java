import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.*;
import java.awt.GridLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class PrintP extends JPanel implements MouseListener,
		MouseMotionListener {

	ImagePanel imagep;
	JPanel jpcolor;// display the color as background
	JPanel jpRGB;// contains the colors information for the RGB
	JPanel jpYUV;// contains the colors information for the YUV

	JLabel jlcolorTitle; 
	JLabel jlRGB;// text "RGB"
	JLabel jlRed;// value for Red
	JLabel jlGreen; // value for Blue
	JLabel jlBlue;// value for Green

	JLabel jlYUV;// text "RGB"
	JLabel jlY;// value for Red
	JLabel jlU; // value for Blue
	JLabel jlV;// value for Green

	JLabel jlcoordTitle; 
	JLabel jlx;// text "RGB"
	JLabel jlxcoord;// value for Red
	JLabel jly; // value for Blue
	JLabel jlycoord;// value for Green

	Color color;
	private boolean isprinting = false;
	private int xcoord = 200, ycoord = 400;

	/*** CLASS' CONSTRUCTOR ****/
	public PrintP(ImagePanel img) {
		this.imagep = img;
		imagep.addMouseListener(this);
		imagep.addMouseMotionListener(this);
		init();

	}

	/***** SETCOLOR OF THE CURRENT PIXEL IN THE DISPLAYING COMPONENT ******/
	public void setColor(Color c) {
		jpcolor.setBackground(c);
		jlRed.setText(" R " + c.getRed());
		jlBlue.setText(" B " + c.getBlue());
		jlGreen.setText(" G " + c.getGreen());

		jlY.setText(" Y "
				+ (0.257 * c.getRed() + 0.504 * c.getGreen() + 0.098
						* c.getBlue() + 16));
		jlU.setText(" U "
				+ (0.439 * c.getRed() - 0.368 * c.getGreen() - 0.071
						* c.getBlue() + 128));
		jlV.setText(" V "
				+ (-0.148 * c.getRed() - 0.291 * c.getGreen() + 0.439
						* c.getBlue() + 128));
	}	public void setNoColor() {
		jpcolor.setBackground(null);
		jlRed.setText("");
		jlBlue.setText("");
		jlGreen.setText("");

		jlY.setText("");
		jlU.setText("");
		jlV.setText("");
	}

	/*************** INIT() **************************************/
	public void init() {
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		gridLayout.setHgap(200);
		gridLayout.setVgap(20);
		gridLayout.setColumns(4);
		JPanel jptit = new JPanel(new BorderLayout(20,20));
		jptit.setBackground(Color.gray);
		this.setLayout(new BorderLayout());
		this.jlcoordTitle = new JLabel("Coordinates");
		jlcoordTitle.setForeground(new Color(204, 204, 204));
		this.jlcolorTitle = new JLabel("Colors");
		jlcolorTitle.setForeground(new Color(204, 204, 204));
		jptit.setLayout(gridLayout);
		jptit.add(jlcoordTitle, null);
		jptit.add(jlcolorTitle, null);
		this.add(jptit, BorderLayout.NORTH);
		
		JPanel jp1 = new JPanel(new GridLayout(1,4, 10, 10));
		jp1.setBackground(Color.gray);
		jp1.setPreferredSize(new Dimension(500, 50));
		this.jpcolor = new JPanel();
		this.jpcolor.setSize(this.getWidth(), this.getHeight() / 4);
		this.jpcolor.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		jpcolor.setBackground(Color.LIGHT_GRAY);
		this.jpRGB = new JPanel();
		this.jpYUV = new JPanel();

		
		this.jlYUV = new JLabel("YUV");
		this.jlRGB = new JLabel("RGB");

		jpYUV.setLayout(new BoxLayout(jpYUV, BoxLayout.PAGE_AXIS));
		jpYUV.setBackground(Color.LIGHT_GRAY);
		jpRGB.setLayout(new BoxLayout(jpRGB, BoxLayout.PAGE_AXIS));
		jpRGB.setBackground(Color.LIGHT_GRAY);
		this.jlRed = new JLabel();
		this.jlGreen = new JLabel();
		this.jlBlue = new JLabel();
		this.jlY = new JLabel();
		this.jlU = new JLabel();
		this.jlV = new JLabel();

		this.color = Color.BLACK;

		JPanel jp = new JPanel();
		jlx = new JLabel();
		jly = new JLabel();
		jlxcoord = new JLabel();
		jlxcoord.setForeground(new Color(204, 204, 204));
		jlycoord = new JLabel();
		jlycoord.setForeground(new Color(204, 204, 204));
		jlx.setText("x :");
		jlx.setForeground(new Color(204, 204, 204));
		jly.setText("y :");
		jly.setForeground(new Color(204, 204, 204));
		jp.setLayout(new GridLayout(2, 4));
		jp.setBackground(Color.DARK_GRAY);
		jp.add(jlx);
		jp.add(new JLabel());
		jp.add(jly);
		jp.add(new JLabel());
		jp.add(jlxcoord);
		jp.add(new JLabel());
		jp.add(jlycoord);
		jp1.add(jp);

		jpRGB.add(jlRGB, BorderLayout.NORTH);
		jpRGB.add(jlRed, BorderLayout.CENTER);
		jpRGB.add(jlGreen, BorderLayout.CENTER);
		jpRGB.add(jlBlue, BorderLayout.CENTER);
		jpYUV.add(jlYUV, BorderLayout.NORTH);
		jpYUV.add(jlY, BorderLayout.CENTER);
		jpYUV.add(jlU, BorderLayout.CENTER);
		jpYUV.add(jlV, BorderLayout.CENTER);

		jp1.add(jpcolor);
		jp1.add(jpRGB);
		jp1.add(jpYUV);
		this.add(jp1);
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		xcoord = e.getX();
		ycoord = e.getY();
		if (xcoord < 10)
			jlxcoord.setText(" " + xcoord + " ");
		else if (xcoord < 100)
			jlxcoord.setText(" " + xcoord);
		else
			jlxcoord.setText("" + xcoord);
		if (ycoord < 10)
			jlycoord.setText(" " + ycoord + " ");
		else if (ycoord < 100)
			jlycoord.setText(" " + ycoord);
		else
			jlycoord.setText("" + ycoord);

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		jlxcoord.setText("  ");
		jlycoord.setText("  ");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		xcoord = evt.getX();
		ycoord = evt.getY();

		if (xcoord < 10)
			jlxcoord.setText(" " + xcoord + " ");
		else if (xcoord < 100)
			jlxcoord.setText(" " + xcoord);
		else
			jlxcoord.setText("" + xcoord);
		if (ycoord < 10)
			jlycoord.setText(" " + ycoord + " ");
		else if (ycoord < 100)
			jlycoord.setText(" " + ycoord);
		else
			jlycoord.setText("" + ycoord);
		
		if (getIsPrinting()) {
			setColor(new Color(imagep.getCurrentImage().getRGB(xcoord, ycoord)));
		} else
			setNoColor();
	}

	/********************************************* GETTERS SETTERS ***********************************************************/
	public void setIsPrinting(boolean b) {
		this.isprinting = b;
	}

	public void setXcoord(int x) {
		this.xcoord = x;
	}

	public void setYcoord(int y) {
		this.ycoord = y;
	}

	public boolean getIsPrinting() {
		return this.isprinting;
	}

	public int getXcoord() {
		return this.xcoord;
	}

	public int getYcoord() {
		return this.ycoord;

	}
	
}
