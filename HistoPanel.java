import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class HistoPanel extends JPanel {

	private int[] count;
	private Color color = null;

	private JPanel jpsliders = null;
	private JSlider jslmin = null;
	private JSlider jslmax = null;
	private JButton jbStretching = null;
	private BufferedImage image = null;
	private Color[][] pixels = null;
	private paintHisto histo = null;
	private JPanel jppsliders = null;
	private int cmax, cmin, newmax, newmin = 0;

	public HistoPanel(Color c) {
		this.color = c;
		this.setPreferredSize(getPreferredSize());
		this.setLayout(new BorderLayout());
	}

	/********************************************************************** GETTERS SETTERS ***************************************************/
	/*** JSLIDER MIN ****/
	public JSlider getSLMin() {
		if (this.jslmin == null) {
			this.cmin = getCountMin();
			this.jslmin = new JSlider(0, cmax, cmin);
			this.jslmin.setMinorTickSpacing(5);
			this.jslmin.setMajorTickSpacing(10);
			this.jslmin.setPaintTicks(true);
			this.jslmin.setPaintLabels(true);
			this.jslmin.setPaintTrack(true);
		}
		return this.jslmin;
	}

	public void setSLMin(JSlider slmin) {
		this.jslmin = slmin;
	}

	/*** JSLIDER MAX ****/
	public JSlider getSLMax() {
		if (this.jslmax == null) {
			this.cmax = getCountMax();
			this.jslmax = new JSlider(cmin, 255, cmax);
			this.jslmax.setMinorTickSpacing(5);
			this.jslmax.setMajorTickSpacing(10);
			this.jslmax.setPaintTicks(true);
			this.jslmax.setPaintLabels(true);
			this.jslmax.setPaintTrack(true);
		}
		return this.jslmax;
	}

	public void setSLMax(JSlider slmax) {
		this.jslmax = slmax;
	}

	/*** JBUTTON APPLY STRETCHING ****/
	public JButton getJbStretch() {
		if (this.jbStretching == null) {
			this.jbStretching = new JButton(" Apply Stretching !");
			this.jbStretching.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					stretchHisto();

				};
			});
		}
		return this.jbStretching;
	}

	public void setjBStretch(JButton jbs) {
		this.jbStretching = jbs;
	}

	public JPanel getJPPSlider() {
		if (this.jppsliders == null) {
			this.jppsliders = new JPanel();
			this.jppsliders.setLayout(new GridLayout(4, 1));
			this.jppsliders.add(new JLabel("Min Range"));
			this.jppsliders.add(getSLMin());
			this.jppsliders.add(new JLabel("Max Range"));
			this.jppsliders.add(getSLMax());
		}
		return this.jppsliders;
	}

	/*** JPANEL FOR THE STRETCHING GUI ****/
	public JPanel getJPSliders() {
		if (this.jpsliders == null) {
			this.jpsliders = new JPanel(new BorderLayout());
			this.jpsliders.add(getJPPSlider(), BorderLayout.CENTER);
			this.jpsliders.add(getJbStretch(), BorderLayout.SOUTH);
		}
		return this.jpsliders;
	}

	public void setSLPanel(JPanel jp) {
		this.jpsliders = jp;
	}

	/**** COUNT INT[] ****/
	public void setCount(int[] count) {
		this.count = count;
	}

	public int[] getCount() {
		if (count == null) {
			for (int n = 0; n < this.count.length; n++)
				count[n] = 0;
			count = count(getColor());
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
	public paintHisto getHisto() {
		if (histo == null) {
			histo = new paintHisto(getCount(), getColor());
		}
		return this.histo;
	}

	public void setHisto(paintHisto h) {
		this.histo = h;
	}

	/****************************************************** SHOW THE HISTOGRAM ********************************************************/
	public void showHistogram(int[] count) {
		setCount(count);
		this.setPreferredSize(getPreferredSize());
		this.add(getHisto(), BorderLayout.CENTER);
		histo.repaint();
		this.add(getJPSliders(), BorderLayout.SOUTH);
	}

	/****************************************************** SIZE OF THE PANEL ***************************************/

	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}

	/************************************************ SET THE COLOR PIXELS TABLE *********************************/
	public Color[][] getPixels() {
		int x, y;
		if (this.pixels == null) {
			this.pixels = new Color[image.getWidth()][image.getHeight()];
			for (x = 0; x < image.getWidth(); x++) {
				for (y = 0; y < image.getHeight(); y++) {
					pixels[x][y] = new Color(image.getRGB(x, y));
				}
			}
		}
		return pixels;
	}

	public void setPixels(Color[][] p) {
		this.pixels = p;
	}

	public void stretchHisto() {
		this.newmax = this.jslmax.getValue();
		this.newmin = this.jslmin.getValue();
		int x, y;
		for (x = 0; x < pixels.length; x++) {
			for (y = 0; y < pixels[x].length; y++) {
				this.pixels[x][y] = new Color(newmin
						+ ((newmax - newmin) / (cmax - cmin))
						* this.pixels[x][y].getRGB());
			}
		}

	}

	/*********************************************** COUNT THE NUMBER OF PIXELS WITH THE SAME VALUE ********************************/
	public int[] count(Color color) {
		int[] count = new int[256];
		int x, y;
		getPixels();
		for (x = 0; x < pixels.length; x++) {
			for (y = 0; y < pixels[x].length; y++) {
				if (color == Color.RED) {
					int red = pixels[x][y].getRed();
					if (red <= 255 && red >= 0)
						count[red]++;
				}
				if (color == Color.BLUE) {
					int blue = pixels[x][y].getBlue();
					if (blue <= 255 && blue >= 0)
						count[blue]++;
				}
				if (color == Color.GREEN) {
					int green = pixels[x][y].getGreen();
					if (green <= 255 && green >= 0)
						count[green]++;
				} else {
					int c = pixels[x][y].getRed() / 3 + pixels[x][y].getBlue()
							/ 3 + pixels[x][y].getGreen() / 3;
					if (c <= 255 && c >= 0)
						count[c]++;
				}
			}
		}
		return count;

	}
	

}