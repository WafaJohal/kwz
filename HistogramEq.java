import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Windu Purnomo
 */
public class HistogramEq{
	public int[][] getRGB(File file) throws IOException{
		BufferedImage buf = ImageIO.read(file);
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
	
	public float [] RGB2GS (File file) throws IOException{
		BufferedImage buf = ImageIO.read(file);
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
	
	public int [] histogram(float[] grayScale){
		int [] pixNum = new int [256];
		int size = grayScale.length;
		for(int c = 0; c<256; c++){
			int sum = 0;
			for(int i = 0; i<size; i++) if(grayScale[i]==c) sum++;
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
	
	public float[] equalization(int [] cdf, int pictSize){
		int min = getMinCDF(cdf);
		float e [] = new float[256];
		System.out.println("minimum: "+min);
		System.out.println("pictSize: "+pictSize);
		for(int i = 0; i<256; i++){
			e[i] = (float)((((float)cdf[i]-min)/(float)pictSize)*255);
		}
        for(int i = 0; i<256; i++){
            if(e[i]<0) e[i]=0;
            if(e[i]>255) e[i]=255;
        }
		return e;
	}
 	
	public float [] picEqualized(float [] grayScale, float [] equalization, int w, int h){
		int size = w*h;
		float [] newGS = new float[size];
		int counter = 0;
		for(int i = 0; i<w; i++){
			for(int j = 0; j<h; j++){
				newGS [counter] = equalization[(int)grayScale[counter]]; //convert
				counter++;
			}
		}
		return newGS;
	}
	
	public void drawImage(float [] newGS, int w, int h)throws IOException{
		int size = w*h;
		int counter = 0;
		BufferedImage im = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = im.getRaster();
		for(int i = 0; i<w; i++){
			for(int j = 0; j<h; j++){
				raster.setSample(i, j, 0, newGS[counter]);
				counter++;
			}
		}
		ImageIO.write(im, "PNG", new File("noNoisy.png"));
	}
	
	public static void main (String args[]) throws IOException{
		HistogramEq he = new HistogramEq();
		File file = new File("noisy.jpg");
		BufferedImage x = ImageIO.read(file);
		int width = x.getWidth();
		int height = x.getHeight();
		int size = width * height;
		float grayScale [] = new float[size];
		int histogram [] = new int[256];
		int cdf [] = new int[256];
		float equalized [] = new float[256];
		float picEqualized [] = new float[size];
		
		grayScale = he.RGB2GS(file);
		histogram = he.histogram(grayScale);
		cdf = he.getCDF(histogram);
		equalized = he.equalization(cdf, size);
		picEqualized = he.picEqualized(grayScale, equalized, width, height);
		he.drawImage(picEqualized, width, height);
		int counter = 0;
	}
}