import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;


public class HistoFrame extends JFrame{
	private ImagePanel jImagePane;
	private JTabbedPane tbhisto = new JTabbedPane();
	private HistoPanel histoColor = new HistoPanel(Color.BLACK);
	private HistoPanel histoRed = new HistoPanel(Color.RED);
	private HistoPanel histoGreen = new HistoPanel(Color.GREEN);
	private HistoPanel histoBlue = new HistoPanel(Color.BLUE);
	
	public HistoFrame(){
		
	}
	
	public HistoFrame(ImagePanel jImagePane){
		loadImage(jImagePane);
		showHistos();
		group();
	
	}
	
	private void loadImage(ImagePanel jImagePane){
		histoColor.setImage(jImagePane.getColorImage());
		histoRed.setImage(jImagePane.getColorImage());
		histoBlue.setImage(jImagePane.getColorImage());
		histoGreen.setImage(jImagePane.getColorImage());
	}
	
	private void showHistos(){
		histoColor.showHistogram(histoColor.count(Color.BLACK));
		histoRed.showHistogram(histoRed.count(Color.RED));
		histoBlue.showHistogram(histoBlue.count(Color.blue));
		histoGreen.showHistogram(histoGreen.count(Color.GREEN));
	}
	private void group(){
		tbhisto.setPreferredSize(new Dimension(500, 400));
		tbhisto.addTab("Color Histogram", histoColor);
		tbhisto.addTab("Red scale", histoRed);
		tbhisto.addTab("Green scale", histoGreen);
		tbhisto.addTab("Blue scale", histoBlue);
		this.add(tbhisto);
	}
}
