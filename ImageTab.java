import java.awt.Color;
import java.awt.image.BufferedImage;


public class ImageTab {
	private BufferedImage image = null;
	private int[][] pixelsRGB;
	private int x, y; 
	private int width, height;
	
	public ImageTab(int w, int h){
		this.width = w;
		this.height = h;
		for (x = 0; x < width; x++) {
			for (y = 0; y < height; y++) {
				pixelsRGB[x][y] = Color.BLACK.getRGB();
			}
		}
	}
	
	
	
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
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
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
	public int getRed(int x, int y){
		int red=0;
		Color c = new Color(getRGB(x,y));
		red = c.getRed();
		return red;
	}
	public int getBlue(int x, int y){
		int blue=0;
		Color c = new Color(getRGB(x,y));
		blue = c.getBlue();
		return blue;
	}
	public int getGreen(int x, int y){
		int green=0;
		Color c = new Color(getRGB(x,y));
		green = c.getGreen();
		return green;
	}
}
