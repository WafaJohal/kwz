import java.awt.Color;
import java.awt.image.BufferedImage;


public class ImageTab {
	private BufferedImage image = null;
	private int[][] pixelsRGB;
	private int x, y; 
	private int width, height;
	public ImageTab(BufferedImage img){
		this.image = img;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		this.pixelsRGB = new int[width][height];
		for (x = 0; x < width; x++) {
			for (y = 0; y < height; y++) {
				pixelsRGB[x][y] = image.getRGB(x, y);
			}
		}
		
	}
	
	public int getRGB(int x, int y){
		return this.pixelsRGB[x][y];
	}
	
	public void setRGB(int x, int y, int valueRGB){
		this.pixelsRGB[x][y] = valueRGB; 
	}
	
	public BufferedImage toImage(){
		BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		for(x = 0; x<width; x++){
			for(y=0; y<height; y++){
				img.setRGB(x, y, this.pixelsRGB[x][y]);
			}
		}
	return img;
	}
	
}
