import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

class paintHisto extends JPanel {
		protected int[] count = null;
		protected Color color = null;

		/************************************************ CONSTRUCTOR *******************************************************************/
		public paintHisto(int[] count, Color c) {
			this.count = count;
			this.color = c;
			this.setPreferredSize(getPreferredSize());
		}

		/*************************************************** PAINT THE BARS OF THE HISTOGRAM ***************************************************/
		protected void paintComponent(Graphics g) {
			if (this.count == null)
				return;
			super.paintComponent(g);

			int width = getWidth();
			int height = getHeight();
			int interval = (width - 40) / this.count.length;
			int individualWidth = 2;

			// find the max count with the higest point
			int maxCount = 0;
			for (int i = 0; i < this.count.length; i++)
				if (maxCount < this.count[i])
					maxCount = (int)this.count[i];

			int x = 20;

			for (int i = 0; i < this.count.length; i++) {
				// find the bar height
				int barHeight = (int) (((double) this.count[i] / (double) maxCount) * (height - 55));
				// Display a bar (ie a rectangle)
				g.setColor(this.color);
				g.fillRect(x, height - 45 - barHeight, individualWidth,
						barHeight);

				if (!((i % 10) == 0) && ((i%5) ==0))
					g.drawString(i + "", x, height - 30);
				if(i == 0)
					g.drawString(i+ "", x, height -30);
				x += interval;

			}
			g.setColor(Color.BLACK);
			g.drawLine(10, height - 45, width - 10, height - 45);

		}
		public void repaint(int[] count, Color c){
			this.count = count;
			this.color = c;
			super.repaint();
			
		}

		public Dimension getPreferredSize() {
			return new Dimension(300, 300);
		}
	}