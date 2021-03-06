import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class HistoFrame extends JFrame{
	private ImagePanel jImagePane;
	private JTabbedPane tbhisto = new JTabbedPane();
	private HistoPanel histoColor = new HistoPanel(Color.BLACK);
	private HistoPanel histoRed = new HistoPanel(Color.RED);
	private HistoPanel histoGreen = new HistoPanel(Color.GREEN);
	private HistoPanel histoBlue = new HistoPanel(Color.BLUE);
	private boolean isgray = false;
	
	
	public HistoFrame(ImagePanel jImagePane){
		loadImage(jImagePane);
		showHistos();
		group();
	
	}
	
	private void loadImage(ImagePanel jImagePane){
		
		histoColor.setImage(jImagePane.getCurrentImage());
		histoRed.setImage(jImagePane.getCurrentImage());
		histoBlue.setImage(jImagePane.getCurrentImage());
		histoGreen.setImage(jImagePane.getCurrentImage());

		}
	
	private void showHistos(){
		
		histoColor.showHistogram(histoColor.countColor(Color.BLACK));
		histoRed.showHistogram(histoRed.countColor(Color.RED));
		histoBlue.showHistogram(histoBlue.countColor(Color.BLUE));
		histoGreen.showHistogram(histoGreen.countColor(Color.GREEN));}

	private void group(){
		
		tbhisto.setPreferredSize(new Dimension(500, 400));
		tbhisto.setFont(new Font("Ubuntu", Font.BOLD, 14));
		tbhisto.addTab("RGB", histoColor);
		tbhisto.addTab("Red", histoRed);
		tbhisto.addTab("Green", histoGreen);
		tbhisto.addTab("Blue", histoBlue);
		this.setIconImage(new ImageIcon("icon/histos.png").getImage());
		this.setTitle("Histograms");
		this.setFont(new Font("Ubuntu", Font.BOLD, 14));
		this.add(tbhisto);
		}
	/*public void main(String args[])throws IOException {
		 File f = new File("icon/crop.png");
		 BufferedImage img = ImageIO.read(f);
		 ImagePanel imp = new ImagePanel();
		 imp.setColorImage(img);
		HistoFrame histoFrame = new HistoFrame(imp);
		histoFrame.setVisible(true);
	}*/
}
