import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class HistoPanel extends JPanel {

	// Class attribute
	private Color color = null;

	// GUI Components
	private JPanel jpstretch = null;
	private JComboBox jcbmin = null;
	private JComboBox jcbmax = null;
	private JButton jbStretching = null;
	private paintHisto histo = null;
	private JPanel jppstretch = null;
	private JButton jbequal = null;
	private JLabel jlcurrentmin = null;
	private JLabel jlcurrentmax = null;

	// Class Variables
	private int[] count;
	private BufferedImage image = null;
	private Integer cmax, cmin, newmax, newmin = 0;
	private Integer[] range = { 0, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100,
			110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210, 220, 230,
			240, 250, 255 };

	public HistoPanel(Color c) {
		this.color = c;
		this.setPreferredSize(getPreferredSize());
		this.setLayout(new BorderLayout());
	}
	/********************************************************************** GETTERS SETTERS ***************************************************/
	/*** JCOMBOBOX MIN ****/
	public JComboBox getJCBmin() {
		if (this.jcbmin == null) {
			this.cmin = getCountMin(getColor());
			this.jcbmin = new JComboBox(range);
			this.jcbmin.setEditable(isEnabled());
			this.jcbmin.setSelectedItem(cmin);
			// this.jcbmin.addActionListener((ActionListener) this);
		}
		return this.jcbmin;
	}

	public void setJCBmin(JComboBox jcbm) {
		this.jcbmin = jcbm;
	}

	/*** JComboBOx MAX ****/
	public JComboBox getJCBmax() {
		if (this.jcbmax == null) {
			this.cmax = getCountMax(getColor());
			this.jcbmax = new JComboBox(range);
			this.jcbmax.setEditable(isEnabled());
			this.jcbmax.setSelectedItem(cmax);
		}
		return this.jcbmax;
	}

	public void setJCBmax(JComboBox jcbm) {
		this.jcbmax = jcbm;
	}

	/*** JBUTTON APPLY STRETCHING ****/
	public JButton getJbStretch() {
		if (this.jbStretching == null) {

			this.jbStretching = new JButton(" Apply Stretching");
			this.jbStretching.setFont(new Font("Ubuntu", Font.BOLD, 14));
			this.jbStretching.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					newmax = (Integer) jcbmax.getSelectedItem();
					newmin = (Integer) jcbmin.getSelectedItem();
					ImageIcon icon = new ImageIcon("icon/oups.png");
					if (newmin > newmax) {
						JOptionPane.showMessageDialog(jbStretching,
								"The maximum bound of the interval has to be greater or equal than the minimum",
							    "Oups!",
							    JOptionPane.INFORMATION_MESSAGE,
							    icon);
						return;
						
						
					} else if ((newmin > 255) || (newmin < 0) || (newmax < 0)
							|| (newmax > 255)) {
						JOptionPane.showMessageDialog(jbStretching,
								"Please enter the bounds' value between 0 and 255",
							    "Oups!",
							    JOptionPane.INFORMATION_MESSAGE,
							    icon);
									
						return;
					} else {
					
						stretchHisto();
					}
				};
			});
		}
		return this.jbStretching;
	}

	public void setjBStretch(JButton jbs) {
		this.jbStretching = jbs;
	}

	public JPanel getJPPSlider() {
		if (this.jppstretch == null) {
			this.jppstretch = new JPanel();
			this.jppstretch.setLayout(new GridLayout(4, 1));
			JLabel jmin = new JLabel("Min Range");
			JLabel jmax = new JLabel("Max Range");
			jmin.setFont(new Font("Ubuntu", Font.BOLD, 14));
			jmax.setFont(new Font("Ubuntu", Font.BOLD, 14));
			this.jppstretch.add(jmin);
			this.jppstretch.add(getJCBmin());
			this.jppstretch.add(jmax);
			this.jppstretch.add(getJCBmax());
		}
		return this.jppstretch;
	}

	/*** JPANEL FOR THE STRETCHING GUI ****/
	public JPanel getJPSliders() {
		if (this.jpstretch == null) {
			this.jpstretch = new JPanel(new BorderLayout());
			this.jpstretch.add(getJPPSlider(), BorderLayout.CENTER);
			this.jpstretch.add(getJbStretch(), BorderLayout.EAST);
			this.jpstretch.add(getJBEqual(), BorderLayout.WEST);
		}
		return this.jpstretch;
	}

	public void setSLPanel(JPanel jp) {
		this.jpstretch = jp;
	}

	/**** COUNT INT[] ****/
	public void setCount(int[] count) {
		this.count = count;
	}

	public int[] getCount(Color color) {
		if (count == null) {
			for (int n = 0; n < this.count.length; n++)
				count[n] = 0;
			count = countColor(color);
		}
		return this.count;
	}

	/***** GET ACTUAL MIN *****/
	public int getCountMin(Color color) {
		int i;
		for (i = 0; i < getCount(color).length; i++) {
			if (getCount(color)[i] != 0)
				return i;
		}

		return i;
	}

	/***** GET ACTUAL MAX *****/
	public int getCountMax(Color color) {
		int i;
		for (i = getCount(color).length - 1; i >= 0; i--) {
			if (getCount(color)[i] != 0)
				return i;
		}

		return i;
	}

	/***** COLOR *****/
	public Color getColor() {
		return this.color;
	}

	public void setColor(Color c) {
		this.color = c;
	}

	/***** BUFFERED IMAGE *****/
	public BufferedImage getImage() {
		return this.image;
	}

	public void setImage(BufferedImage img) {
		this.image = img;
	}

	/****** HISTO PANEL ******/
	public paintHisto getHisto(int[] count2) {
		if (histo == null) {
			histo = new paintHisto(count2, getColor());
		}
		return this.histo;
	}

	public void setHisto(paintHisto h) {
		this.histo = h;
	}

	/****** JLABEL CURRENTMIN & MAX ********/
	public JLabel getJLMin() {
		if (jlcurrentmin == null)
			jlcurrentmin = new JLabel();
		jlcurrentmin.setText("" + jcbmin.getSelectedItem());
		return jlcurrentmin;
	}

	public void setJLMin(JLabel min) {
		jlcurrentmin = min;
	}

	public JLabel getJLMax() {
		if (jlcurrentmax == null)
			jlcurrentmax = new JLabel();
		jlcurrentmax.setText("" + jcbmax.getSelectedItem());
		return jlcurrentmax;
	}

	public void setJLMax(JLabel max) {
		jlcurrentmax = max;
	}
	/****** JBUTTON EQUALIZATION *********/
	public JButton getJBEqual() {
		if (jbequal == null) {
			
			jbequal = new JButton("Equalization");
			jbequal.setFont(new Font("Ubuntu", Font.BOLD, 14));
			jbequal.setToolTipText("Equalization");
			jbequal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BufferedImage img;
					try {
						img = convertToImage(image, getColor());
						histo.repaint(countEq(img, getColor()),getColor());
					Interface in = new Interface();
				
						in.getJImagePane().setCurrentImage(img);
					
					
					in.getJImagePane().repaint();
					in.getJFrame().setVisible(true);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
				} 
			});
		}
		return jbequal;
	}

	public void setJBEqual(JButton jbe) {
		jbequal = jbe;
	}

	/****************************************************** SHOW THE HISTOGRAM ********************************************************/
	public void showHistogram(int[] count) {
		setCount(count);
		this.setPreferredSize(getPreferredSize());
		this.add(getHisto(count), BorderLayout.CENTER);
		histo.validate();
		histo.repaint();
		this.add(getJPSliders(), BorderLayout.SOUTH);
	}

	/****************************************************** SIZE OF THE PANEL ***************************************/

	public Dimension getPreferredSize() {
		return new Dimension(500, 400);
	}

	/************************************************ SET THE COLOR PIXELS TABLE *********************************/

	public void stretchHisto() {
		newmax = (Integer) jcbmax.getSelectedItem();
		newmin = (Integer) jcbmin.getSelectedItem();
		
		image = getNewStretchTable(this.image, getCountMin(getColor()), getCountMax(getColor()),
				this.newmin, this.newmax, getColor());
		//histo.repaint(countColor(getColor()),getColor());
		//histo.validate();
		//histo.repaint();
		this.validate();
		Interface in = new Interface();
			in.getJImagePane().setCurrentImage(image);
		in.getJImagePane().repaint();
		in.getJFrame().setVisible(true);
		

	}

	public BufferedImage getNewStretchTable(BufferedImage img, Integer cmin, Integer cmax, Integer newmin, Integer newmax, Color color) {
		WritableRaster raster = img.getRaster();
		
		float quotient = (newmax - newmin) / (cmax - cmin);
		int x, y;
		int rgb, r = 0, g = 0, b = 0;
		for (x = 0; x < img.getWidth(); x++) {
			for (y = 0; y < img.getHeight(); y++) {
				if(color == Color.RED){
					r = (int)(((new Color(img.getRGB(x, y)).getRed() - cmin) * quotient) + newmin);
					b = (new Color(img.getRGB(x, y))).getBlue();
					g = (new Color(img.getRGB(x, y))).getGreen();
				}
				else if(color == Color.GREEN){
					g = (int)(((new Color(img.getRGB(x, y)).getGreen() - cmin) * quotient) + newmin);
					b = (new Color(img.getRGB(x, y))).getBlue();
					r = (new Color(img.getRGB(x, y))).getRed();
				}
				else if(color == Color.BLUE){
					b = (int)((((new Color(img.getRGB(x, y)).getBlue()) - cmin) * quotient) + newmin);
					r = (new Color(img.getRGB(x, y))).getRed();
					g = (new Color(img.getRGB(x, y))).getGreen();
					
				}
				else{
					r = (int)((((new Color(img.getRGB(x, y)).getRed() - cmin) * quotient) + newmin));
					b = (int)((((new Color(img.getRGB(x, y)).getBlue() - cmin) * quotient) + newmin));
					g = (int)((((new Color(img.getRGB(x, y)).getGreen() - cmin) * quotient) + newmin));
				
				}
				rgb = new Color(r, g, b).getRGB();
				raster.setSample(x,y,0,rgb);
			}
		}

		return img;
	}

	public int getMinRed(BufferedImage img){
		int r = new Color(img.getRGB(0, 0)).getRed();
		int x, y;
		for(x = 1; x<img.getWidth(); x++){
			for(y = 1 ;y<img.getHeight(); y++){
			if(r > new Color(img.getRGB(x, y)).getRed() ) 
				r = new Color(img.getRGB(x, y)).getRed();
		}}
		return r;
	}
	public int getMinGreen(BufferedImage img){
		int r = new Color(img.getRGB(0, 0)).getGreen();
		int x, y;
		for(x = 1; x<img.getWidth(); x++){
			for(y = 1 ;y<img.getHeight(); y++){
			if(r>new Color(img.getRGB(x, y)).getGreen() ) r = new Color(img.getRGB(x, y)).getGreen();
		}}
		return r;
	}
	public int getMinBlue(BufferedImage img){
		int r = new Color(img.getRGB(0, 0)).getBlue();
		int x, y;
		for(x = 1; x<img.getWidth(); x++){
			for(y = 1 ;y<img.getHeight(); y++){
			if(r>new Color(img.getRGB(x, y)).getBlue() ) r = new Color(img.getRGB(x, y)).getBlue();
		}}
		return r;
	}
	public int getMinRGB(BufferedImage img){
		int r = img.getRGB(0, 0);
		int x, y;
		for(x = 1; x<img.getWidth(); x++){
			for(y = 1 ;y<img.getHeight(); y++){
			if(r>img.getRGB(x, y) ) r = img.getRGB(x, y);
		}}
		return r;
	}


	/*********************************************** COUNT THE NUMBER OF PIXELS WITH THE SAME VALUE ********************************/
	public int[] countColor(Color color) {
		int[] count = new int[256];
		int x, y;
		setImage(getImage());
		for (x = 0; x < image.getWidth(); x++) {
			for (y = 0; y < image.getHeight(); y++) {
				if (color == Color.RED) {
					int red = (new Color(image.getRGB(x, y))).getRed();
					if (red <= 255 && red >= 0)
						count[red]++;
				}
				else if (color == Color.BLUE) {
					int blue = (new Color(image.getRGB(x, y))).getBlue();
					if (blue <= 255 && blue >= 0)
						count[blue]++;
				}
				else if (color == Color.GREEN) {
					int green = (new Color(image.getRGB(x, y))).getGreen();
					if (green <= 255 && green >= 0)
						count[green]++;
				} else {
					int c = (int) (((new Color(image.getRGB(x, y))).getRed())*0.3
							+ ((new Color(image.getRGB(x, y))).getGreen()) *0.59
							+ ((new Color(image.getRGB(x, y))).getBlue())*0.11);
					if (c <= 255 && c >= 0)
						count[c]++;
				}
			}
		}
		return count;
		}
		/*******NEW VALUES OF THE HISTOGRAM AFTER EQUALIZATION*******/
		private int h[] = new int[256];

		private int[] countEq(BufferedImage img, Color c) {
			float[] eq;
			if(c == Color.RED){			
			 eq = RedEq(img);
			return histogram(eq);}
			else if(c == Color.GREEN){			
				 eq = GreenEq(img);
				return histogram(eq);}
			else if(c == Color.BLUE){			
				 eq = BlueEq(img);
				return histogram(eq);}
			else{
				 eq = GrayEq(img);
				 return histogram(eq);
				}
			}
	

	
/********************************************************************EQUALIZATION OF THE HISTOGRAM**********************************/
	/*******************************************************************************************************************************/
	

	//put in an array the RGB value of each pixel
	public float [] RGB2GS (BufferedImage buf){
		int width = buf.getWidth();
		int height = buf.getHeight();
		int size = width * height;
		int c = 0, counter = 0, r, g, b;
		float [] grayScale = new float[size];
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height ; j++){
				c = buf.getRGB(i,j);
				r = (c&0x00ff0000)>>16;
				g = (c&0x0000ff00)>>8;
				b = c&0x000000ff;
				grayScale[counter] = (float) (0.3 * r + 0.59 * g + 0.11 * b);
				counter++;
			}
		}
		return grayScale;
	}
	
	//an array with all the value of red for each pixel 
	//for an image 3*3 (R, G, B) 
	// (1,1,1) (2,2,2) (3,3,3)
	// (4,4,4) (5,5,5) (6,6,6)     ==>  (1,2,3,4,5,6,7,8,9)
	// (7,7,7) (8,8,8) (9,9,9)
	public float [] Red (BufferedImage buf){
		int width = buf.getWidth();
		int height = buf.getHeight();
		int size = width * height;
		int counter = 0, r;
		float [] red = new float[size];
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height ; y++){
				r = new Color(buf.getRGB(x,y)).getRed();
				red[counter] = r;
				counter++;
			}
		}
		return red;
	}
	//an array with all the value of green for each pixel 
	//for an image 3*3 (R, G, B) 
	// (1,1,1) (2,2,2) (3,3,3)
	// (4,4,4) (5,5,5) (6,6,6)     ==>  (1,2,3,4,5,6,7,8,9)
	// (7,7,7) (8,8,8) (9,9,9)
	public float [] Green (BufferedImage buf){
		int width = buf.getWidth();
		int height = buf.getHeight();
		int size = width * height;
		int counter = 0, g;
		float [] green = new float[size];
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height ; y++){
				g = new Color(buf.getRGB(x,y)).getGreen();
				green[counter] = g;
				counter++;
			}
		}
		return green;
	}
	//an array with all the value of green for each pixel 
	//for an image 3*3 (R, G, B) 
	// (1,1,1) (2,2,2) (3,3,3)
	// (4,4,4) (5,5,5) (6,6,6)     ==>  (1,2,3,4,5,6,7,8,9)
	// (7,7,7) (8,8,8) (9,9,9)
	public float [] Blue (BufferedImage buf){
		int width = buf.getWidth();
		int height = buf.getHeight();
		int size = width * height;
		int counter = 0, b;
		float [] blue = new float[size];
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height ; y++){
				b = new Color(buf.getRGB(x,y)).getBlue();
				blue[counter] = b;
				counter++;
			}
		}
		return blue;
	}
	
		
	//histogram takes an array of all the pixel and build an array for the histogram display
	public int [] histogram(float[] array){
		int [] pixNum = new int [256];
		int size = image.getWidth()*image.getHeight();
		for(int c = 0; c<256; c++){
			int sum = 0;
			for(int i = 0; i<size; i++) if(array[i]==c) sum++;
			pixNum[c] = sum;
		}
		return pixNum;
	}
	
	//CDF = Cumulative Distributif Function
	public int [] getCDF(int [] histogram){
		int [] cdf = new int [256];
		int cum = 0;
		for(int i = 0; i<256; i++){
			cum += histogram[i];
			cdf[i] = cum;
		}
		return cdf;
	}
	
	public int getMinCDF(int [] cdf){
		int minCDF = 257;
		for(int i = 0; i<256; i++){
			if(cdf[i]<minCDF && cdf[i]!=0) minCDF = cdf[i];
		}
		return minCDF;
	}
	
	public int getMaxCDF(int [] cdf){
		int maxCDF = 0;
		for(int i = 0; i<256; i++){
			if(cdf[i]>maxCDF) maxCDF = cdf[i];
		}
		return maxCDF;
	}
	
	//apply equalization and show the new values of the histogram
	public float[] equalization(int [] cdf){
		 int w = image.getWidth();
	        int h = image.getHeight();
	        
		int size = w*h;
		int min = getMinCDF(cdf);
		float e [] = new float[256];
		for(int i = 0; i<256; i++){
			e[i] = Math.round((float)((((float)cdf[i]-min)/((float)size))*255));
		if(e[i]<0) e[i]=0;
            if(e[i]>255) e[i]=255;
            }
       
		return e;
	}
 	
	//new array for each color with the new values of the pixels
	public float [] picEqualized(float [] array, float [] equalization){
		int w = image.getWidth();
        int h = image.getHeight();
        
	int size = w*h;
		float [] newGS = new float[size];
		int counter = 0;
		for(int i = 0; i<w; i++){
			for(int j = 0; j<h; j++){
				newGS [counter] = equalization[(int)array[counter]]; //convert
				counter++;
			}
		}
		return newGS;
	}
	public float[] RedEq(BufferedImage img){
		float[] red = Red(img);
		int[] histoRed = countColor(Color.RED);
		int[] cdfRed = getCDF(histoRed);
		float[] equalizationRed = equalization(cdfRed);
		return picEqualized(red, equalizationRed);
		
	}
	public float[] GreenEq(BufferedImage img){
		float[] green = Green(img);
		int[] histoGreen = countColor(Color.GREEN);
		int[] cdfGreen = getCDF(histoGreen);
		float[] equalizationGreen = equalization(cdfGreen);
		return picEqualized(green, equalizationGreen);
		
	}
	public float[] BlueEq(BufferedImage img){
		float[] blue = Blue(img);
		int[] histoBlue = countColor(Color.BLUE);
		int[] cdfBlue = getCDF(histoBlue);
		float[] equalizationBlue = equalization(cdfBlue);
		return picEqualized(blue, equalizationBlue);
		
	}
	public float[] GrayEq(BufferedImage img){
		float[] gs = RGB2GS(img);
		int[] histoGs = histogram(gs);
		int[] cdfGs = getCDF(histoGs);
		float[] equalizationGs = equalization(cdfGs);
		return picEqualized(gs, equalizationGs);
	}
	
	public BufferedImage convertToImage(BufferedImage img, Color c) throws IOException{
	int width = img.getWidth();
	int height = img.getHeight();
	int x, y; 
	int counter = 0;
	
	  WritableRaster raster = img.getRaster();
	for(x = 0; x<width; x++){
		for(y = 0; y<height ; y++){
			if(c == Color.RED){//apply the equalization on Red
				
				int b = new Color(img.getRGB(x, y)).getBlue();
				int g = new Color(img.getRGB(x, y)).getGreen();
				Color col = new Color((int)RedEq(img)[counter],g,b);
			
				
				raster.setSample(x, y, 0, col.getRGB());
				counter++;
			}
			else if(c == Color.GREEN){
				int b = new Color(img.getRGB(x, y)).getBlue();
				int r = new Color(img.getRGB(x, y)).getRed();
				Color col = new Color(r,(int)GreenEq(img)[counter],b);
				raster.setSample(x, y, 0, col.getRGB());
				
				counter++;
			}
			else if(c == Color.BLUE){
				int g = new Color(img.getRGB(x, y)).getGreen();
				int r = new Color(img.getRGB(x, y)).getRed();
				Color col = new Color(r,g,(int)BlueEq(img)[counter]);
				raster.setSample(x, y, 0, col.getRGB());
				counter++;
			}
			else{
				Color col = new Color((int)GrayEq(img)[counter]);
				raster.setSample(x, y, 0, col.getRGB());
				
				counter++;
			}
			
		}
	}
	
	return img;
	}


	
	

	

	
}