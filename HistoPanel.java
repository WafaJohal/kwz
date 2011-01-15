import javax.swing.*;
import java.awt.*;

public class HistoPanel extends JPanel{

	private int[] count;
	private Color color = null;
	
	public HistoPanel(Color c){
		this.color = c;
		this.setPreferredSize(getPreferredSize());
	}
	
	
	public void showHistogram(int[] count){
		this.count = count;
		this.setPreferredSize(getPreferredSize());
		repaint();
	}
	protected void paintComponent(Graphics g){
		if(count == null) return;
		super.paintComponent(g);
		 
		int width = getWidth();
		int height = getHeight();
		int interval = (width-40)/count.length;
		int individualWidth = 2; //(int)(((width -40)/24)*0.60);
		
		//find the max count with the higest point
		int maxCount = 0;
		for(int i = 0 ; i< count.length; i++)
			if(maxCount <count[i]) maxCount = count[i];
				
		int x = 20;
		
		for(int i =0; i<count.length; i++){
			//find the bar height
			int barHeight = (int)(((double) count[i]/ (double)maxCount)*(height -55));
			//Display a bar (ie a rectangle)
			g.setColor(this.color);
			g.fillRect(x, height - 45 - barHeight, individualWidth, barHeight);
			
			if((i%20) ==0) g.drawString(i+ "", x, height -30);
			
			x+= interval;
			
		}
		g.setColor(Color.BLACK);
		g.drawLine(10, height -45, width -10, height -45);
		
	}
	public Dimension getPreferredSize(){
		return new Dimension(300, 300);
	}
}
