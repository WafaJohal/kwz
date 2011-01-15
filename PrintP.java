import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PrintP extends JPanel implements MouseListener,
MouseMotionListener  {
	
		ImagePanel imagep;
		JPanel jpcolor ;//display the color as background
		JPanel jpRGB ;//contains the colors information for the RGB
		JPanel jpYUV ;//contains the colors information for the YUV
		
		JLabel jlRGB ;// text "RGB"
		JLabel jlRed ;// value for Red
		JLabel jlGreen ; // value for Blue
		JLabel jlBlue ;//value for Green
		
		JLabel jlYUV ;// text "RGB"
		JLabel jlY ;// value for Red
		JLabel jlU ; // value for Blue
		JLabel jlV ;//value for Green
		
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
		
		/***** SETCOLOR OF THE CURRENT PIXEL IN THE DISPLAYING COMPONENT******/
		public void setColor(Color c){
			jpcolor.setBackground(c);
			jlRed.setText(" R "+ c.getRed());
			jlBlue.setText(" B "+ c.getBlue());
			jlGreen.setText(" G "+ c.getGreen());
			
			jlY.setText(" Y "+ (0.257*c.getRed()+0.504*c.getGreen()+0.098*c.getBlue()+16));
			jlU.setText(" U "+ (0.439*c.getRed()-0.368*c.getGreen()-0.071*c.getBlue()+128));
			jlV.setText(" V "+ (-0.148*c.getRed()-0.291*c.getGreen()+0.439*c.getBlue()+128));
		}
		
		/*************** INIT()**************************************/
		public void init(){
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			this.jpcolor = new JPanel();
			this.jpcolor.setSize(this.getWidth(), this.getHeight()/4);
			this.jpcolor.setBorder(BorderFactory.createLineBorder(Color.black));
			
			this.jpRGB = new JPanel();
			this.jpYUV = new JPanel();
			
			this.jlYUV = new JLabel("YUV");
			this.jlRGB = new JLabel("RGB");
			
			jpYUV.setLayout(new BoxLayout(jpYUV, BoxLayout.PAGE_AXIS));
			jpRGB.setLayout(new BoxLayout(jpRGB, BoxLayout.PAGE_AXIS));
			this.jlRed = new JLabel();
			this.jlGreen =  new JLabel();
			this.jlBlue =   new JLabel();
			this.jlY = new JLabel();
			this.jlU =  new JLabel();
			this.jlV =   new JLabel();
			
			this.color = Color.BLACK;
			
			//jlRed.setText(""+color.getRed());
			//jlGreen.setText(""+color.getGreen());
			//jlBlue.setText(""+color.getBlue());
			jpRGB.add(jlRGB, BorderLayout.NORTH);
			jpRGB.add(jlRed, BorderLayout.CENTER);
			jpRGB.add(jlGreen, BorderLayout.CENTER);
			jpRGB.add(jlBlue, BorderLayout.CENTER);
			jpYUV.add(jlYUV, BorderLayout.NORTH);
			jpYUV.add(jlY, BorderLayout.CENTER);
			jpYUV.add(jlU, BorderLayout.CENTER);
			jpYUV.add(jlV, BorderLayout.CENTER);
			
			this.add(jpcolor, BorderLayout.NORTH);
			this.add(jpRGB, BorderLayout.WEST);
			this.add(jpYUV, BorderLayout.EAST);
		}
	

	@Override
	public void mouseClicked(MouseEvent evt) {
			
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

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		if(isprinting){
			xcoord = evt.getX();
			ycoord = evt.getY();
			setColor(new Color(imagep.getImage().getRGB(xcoord, ycoord)));
		}
		else ;
	}
	/********************************************* GETTERS SETTERS ***********************************************************/
	public void setIsPrinting(boolean b){this.isprinting = b;}
	public void setXcoord(int x){this.xcoord = x;}
	public void setYcoord(int y){this.ycoord = y;}
	public boolean getIsPrinting(){return this.isprinting;}
	public int getXcoord() {
		return this.xcoord;
	}

	public int getYcoord() {
		return this.ycoord;
	

}
	}
