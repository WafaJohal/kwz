import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
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
		System.out.println("strethisto in");
		updateMaxHistogram((Integer) jcbmax.getSelectedItem());
		updateMinHistogram((Integer) jcbmin.getSelectedItem());
		setImage(getNewStretchTable(this.image, this.cmin, this.cmax,
				this.newmin, this.newmax));
		showHistogram(countColor(color));
		this.validate();
		this.repaint();

	}

	public BufferedImage getNewStretchTable(BufferedImage img, Integer cmin,
			Integer cmax, Integer newmin, Integer newmax) {
		BufferedImage result = new BufferedImage(img.getWidth(), img
				.getHeight(), BufferedImage.TYPE_INT_RGB);
		float quotient = (cmax - cmin) / (newmax - newmin);
		int x, y;
		for (x = 0; x < img.getWidth(); x++) {
			for (y = 0; y < img.getHeight(); y++) {
				result
						.setRGB(
								x,
								y,
								(int) (((img.getRGB(x, y) - newmin) * quotient) + cmin));
			}
		}

		return result;
	}

	public void updateMaxHistogram(Integer max) {
		this.newmax = max;
	}

	public void updateMinHistogram(Integer min) {
		this.newmin = min;
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
				if (color == Color.BLUE) {
					int blue = (new Color(image.getRGB(x, y))).getBlue();
					if (blue <= 255 && blue >= 0)
						count[blue]++;
				}
				if (color == Color.GREEN) {
					int green = (new Color(image.getRGB(x, y))).getGreen();
					if (green <= 255 && green >= 0)
						count[green]++;
				} else {
					int c = ((new Color(image.getRGB(x, y))).getRed()) / 3
							+ ((new Color(image.getRGB(x, y))).getRed()) / 3
							+ ((new Color(image.getRGB(x, y))).getRed()) / 3;
					if (c <= 255 && c >= 0)
						count[c]++;
				}
			}
		}
		return count;
		}
		/*******NEW VALUES OF THE HISTOGRAM AFTER EQUALIZATION*******/
		private int h[] = new int[256];

		private int[] countEq(Color c) {
			setImage(getImage());
			float[] eq;
			if(c == Color.RED){			
			 eq = RedEq(image);
			return histogram(eq);}
			else if(c == Color.GREEN){			
				 eq = GreenEq(image);
				return histogram(eq);}
			else if(c == Color.BLUE){			
				 eq = BlueEq(image);
				return histogram(eq);}
			else{
				 eq = GrayEq(image);
				 return histogram(eq);
				}
			}
	
	public int[] countGray() {
		int[] count = new int[256];
		int x, y;
		setImage(getImage());
		for (x = 0; x < image.getWidth(); x++) {
			for (y = 0; y < image.getHeight(); y++) {
				int val = (new Color(image.getRGB(x, y))).getTransparency();
						count[val]++;
				
			}
		}
		return count;

	}
	
	/*public void setEqImage(int[] newGS)throws IOException{
		int h = image.getHeight();
		int w = image.getWidth();
		int size = w*h;
		int counter = 0;
			        BufferedImage im = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
			        WritableRaster raster = im.getRaster();
			        for(int i = 0; i<w; i++){
			            for(int j = 0; j<h; j++){
			                raster.setSample(i, j, 0, newGS[counter]);
			                counter++;
			            }
			        }
			       image = im;
			    }*/
	
/********************************************************************EQUALIZATION OF THE HISTOGRAM**********************************/
	/*******************************************************************************************************************************/
	
	/****** JBUTTON EQUALIZATION *********/
	public JButton getJBEqual() {
		if (jbequal == null) {
			jbequal = new JButton("Equalization");
			jbequal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					histo.repaint(countEq(getColor()),getColor());
					Interface in = new Interface();
					in.getJImagePane().setColorImage(convertToImage(image, getColor()));
					in.getJImagePane().repaint();
					in.getJFrame().setVisible(true);
				} 
			});
		}
		return jbequal;
	}

	public void setJBEqual(JButton jbe) {
		jbequal = jbe;
	}
	
	
	//Return a table representative of the pixels of the image (x,y) -> RGB[x][y]
	public int[][] getRGB(BufferedImage buf) {
		int width = buf.getWidth();
		int height = buf.getHeight();
		int size = width * height;
		int c = 0, counter = 0;
		int [][] rgb = new int[3][size];
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height ; j++){
				c = buf.getRGB(i,j);
				rgb[0][counter] = (c&0x00ff0000)>>16;
				rgb[1][counter] = (c&0x0000ff00)>>8;
				rgb[2][counter] = c&0x000000ff;
				counter++;
			}
		}
		return rgb;
	}
	//put in an array the RGB value of each pixel
	public float[] getAllColor(BufferedImage buf){
		int width = buf.getWidth();
		int height = buf.getHeight();
		int size = width * height;
		int gr, counter = 0;
		float[] grayscale = new float[size];
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height ; y++){
				gr = buf.getRGB(x, y);
				grayscale[counter] = gr;
				counter++;
			}
		}
		
		
		return grayscale;
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
				//rgbarr[counter]+=r;
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
				//rgbarr[counter]+=g;
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
				//rgbarr[counter]+=b;
				counter++;
			}
		}
		return blue;
	}
	
	public float [] GrayScale (BufferedImage buf) {
			        int width = buf.getWidth();
			        int height = buf.getHeight();
			        int size = width * height;
			        int c = 0, counter = 0, r, g, b;
			        float [] grayScale = new float[size];
			        for(int x = 0; x<width; x++){
			            for(int y = 0; y<height ; y++){
			                c = buf.getRGB(x,y);
			                r = (c&0x00ff0000)>>16;
			                g = (c&0x0000ff00)>>8;
			                b = c&0x000000ff;
			                grayScale[counter] = (float) (0.3 * r + 0.59 * g + 0.11 * b);
			                counter++;
			            }
			        }
			        return grayScale;
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
	public int[] equalization(int [] cdf){
		 int w = image.getWidth();
	        int h = image.getHeight();
	        
		int size = w*h;
		int min = getMinCDF(cdf);
		int e [] = new int[256];
		System.out.println("minimum: "+min);
		System.out.println("pictSize: "+size);
		for(int i = 0; i<256; i++){
			e[i] = (int)((((float)cdf[i]-min)/(float)size)*255);
		}
        for(int i = 0; i<256; i++){
            if(e[i]<0) e[i]=0;
            if(e[i]>255) e[i]=255;
        }
		return e;
	}
 	
	//new array for each color with the new values of the pixels
	public float [] picEqualized(float [] array, int [] equalization){
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
		int[] histoRed = histogram(red);
		int[] cdfRed = getCDF(histoRed);
		int[] equalizationRed = equalization(cdfRed);
		return picEqualized(red, equalizationRed);
		
	}
	public float[] GreenEq(BufferedImage img){
		float[] green = Green(img);
		int[] histoGreen = histogram(green);
		int[] cdfGreen = getCDF(histoGreen);
		int[] equalizationGreen = equalization(cdfGreen);
		return picEqualized(green, equalizationGreen);
		
	}
	public float[] BlueEq(BufferedImage img){
		float[] blue = Blue(img);
		int[] histoBlue = histogram(blue);
		int[] cdfBlue = getCDF(histoBlue);
		int[] equalizationBlue = equalization(cdfBlue);
		return picEqualized(blue, equalizationBlue);
		
	}
	public float[] GrayEq(BufferedImage img){
	
		
		float[] gs = GrayScale(img);
		int[] histoGs = histogram(gs);
		int[] cdfGs = getCDF(histoGs);
		int[] equalizationGs = equalization(cdfGs);
		return picEqualized(gs, equalizationGs);
	}
	
	public BufferedImage convertToImage(BufferedImage img, Color c){
	int width = img.getWidth();
	int height = img.getHeight();
	int x, y; 
	BufferedImage imgeq = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
	  WritableRaster raster = imgeq.getRaster();
	for(x = 0; x<width; x++){
		for(y = 0; y<height ; y++){
			if(c == Color.RED){//apply the equalization on Red
				
				int b = new Color(img.getRGB(x, y)).getBlue();
				int g = new Color(img.getRGB(x, y)).getGreen();
				Color col = new Color((int)RedEq(img)[x+y],g,b);
				raster.setSample(x, y, 0, col.getRGB());
				//img.setRGB(x, y, col.getRGB());
			}
			else if(c == Color.GREEN){
				int b = new Color(img.getRGB(x, y)).getBlue();
				int r = new Color(img.getRGB(x, y)).getRed();
				Color col = new Color(r,GreenEq(img)[x+y],b);
				img.setRGB(x, y, col.getRGB());
			}
			else if(c == Color.BLUE){
				int g = new Color(img.getRGB(x, y)).getGreen();
				int r = new Color(img.getRGB(x, y)).getRed();
				Color col = new Color(r,g,BlueEq(img)[x+y]);
				img.setRGB(x, y, col.getRGB());
			}
			else{
				Color col = new Color(RedEq(img)[x+y],GreenEq(img)[x+y],BlueEq(img)[x+y]);
				img.setRGB(x, y, col.getRGB());
			}
			
		}
	}
	return img;
	}


	
	

	/********************************************************************** GETTERS SETTERS ***************************************************/
	/*** JCOMBOBOX MIN ****/
	public JComboBox getJCBmin() {
		if (this.jcbmin == null) {
			this.cmin = getCountMin();
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
			this.cmax = getCountMax();
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
			this.jbStretching = new JButton(" Apply Stretching !");
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
			this.jppstretch.add(new JLabel("Min Range"));
			this.jppstretch.add(getJCBmin());
			this.jppstretch.add(new JLabel("Max Range"));
			this.jppstretch.add(getJCBmax());
		}
		return this.jppstretch;
	}

	/*** JPANEL FOR THE STRETCHING GUI ****/
	public JPanel getJPSliders() {
		if (this.jpstretch == null) {
			this.jpstretch = new JPanel(new BorderLayout());
			this.jpstretch.add(getJPPSlider(), BorderLayout.CENTER);
			this.jpstretch.add(getJbStretch(), BorderLayout.SOUTH);
			this.jpstretch.add(getJBEqual(), BorderLayout.EAST);
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

	public int[] getCount() {
		if (count == null) {
			for (int n = 0; n < this.count.length; n++)
				count[n] = 0;
			count = countColor(getColor());
		}
		return this.count;
	}

	/***** GET ACTUAL MIN *****/
	public int getCountMin() {
		int i;
		for (i = 0; i < getCount().length; i++) {
			if (getCount()[i] != 0)
				return i;
		}

		return i;
	}

	/***** GET ACTUAL MAX *****/
	public int getCountMax() {
		int i;
		for (i = getCount().length - 1; i >= 0; i--) {
			if (getCount()[i] != 0)
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

	
}