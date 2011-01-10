import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class PrintPixelColor extends JPanel {
		private Color color = Color.blue; // coordonnates of the pixel

		public PrintPixelColor(){
			color = Color.blue;
			this.setPreferredSize(new Dimension(20,20));
			this.setVisible(true);
			this.add(new JLabel("RGB"));
		}
		public  PrintPixelColor(Color color) {
			this.color = color;
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			if(color != null){
				g.setColor(color);
				g.fillRect(2, 2,20,20);
			}
		}
	}

