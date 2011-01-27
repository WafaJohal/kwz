import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.image.WritableRaster;


public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener {
	 private BufferedImage secondImage;
     private BufferedImage currentImage;     
     private BufferedImage colorImage;
 private BufferedImage grayImage;
 private BufferedImage increaseImage;
 private BufferedImage decreaseImage;
 private BufferedImage fusionImage;
 private int x1,x2,y1,y2;
 private int current_width,current_height;
 private double weight1,weight2;
 private boolean isCropping;
 private boolean isGray;
 private boolean isIncrease;
 private boolean isDecrease;
                /*=======================================================================
                 * =========================CONSTRUCTOR==================================*/
                public ImagePanel(){
                        this.addMouseListener(this);
                    this.addMouseMotionListener(this);
                }
                /*==========================SET AND GET IMAGE==========================*
                 *====================================================================*/
                public void setSecondImage(BufferedImage img)
                {
                this.secondImage = img;
                }
                public BufferedImage getSecondImage()
                {
                        return this.secondImage;
                }
            public void setCurrentImage(BufferedImage img)
                {
                this.currentImage = img;
                this.setColorImage(currentImage);
                current_width = currentImage.getWidth();
                current_height = currentImage.getHeight();
                }
                public BufferedImage getCurrentImage()
                {
                        return this.currentImage;
                }
            public void setColorImage(BufferedImage img)
            {
                this.colorImage = img;
            }
            public BufferedImage getColorImage()
            {
                 return this.colorImage;
            }
            public void setGrayImage(BufferedImage img)
            {
                this.grayImage = img;
            }
            public BufferedImage getGrayImage()
            {
                return this.grayImage;
            }
            public void setIncreaseImage(BufferedImage img)
            {
                this.increaseImage = img;
            }
            public BufferedImage getIncreaseImage()
            {
                return this.increaseImage;
            }     
            public void setFusionImage(BufferedImage img)
            {
                this.fusionImage = img;
            }
            public BufferedImage getFusionImage()
            {
                return this.fusionImage;
            }     
            /*==========================SET AND GET isCROPPING========================*
             *====================================================================*/
            public void setIsCropping(boolean isCropping)
            {
                this.isCropping = isCropping;
            }
            public boolean getIsCropping()
            {
                return this.isCropping;
            }
            /*==========================SET AND GET isGRAYSCALE========================*
             *====================================================================*/
            public void setIsGray(boolean isGray){
                this.isGray = isGray;
            }
            public boolean getIsGray(){
                return this.isGray;
            }
            /*==========================SET AND GET isRESIZE========================*
             *====================================================================*/
            
            public void setIsIncrease(boolean isIncrease){
                this.isIncrease = isIncrease;
            }
            public boolean getIsIncease(){
                return this.isIncrease;
            }
            public void setIsDecrease(boolean isDecrease){
                this.isDecrease = isDecrease;
            }
            public boolean getIsDecease(){
                return this.isDecrease;
            }
            /*==============================SET AND GET  CURRENT WIDTH AND CURRENT HEIGHT===============
             * ======================================================================================
             */
            public void setCurrentWidth(int width)
            {
                this.current_width = width;
            }
            public void setCurrentHeight(int height)
            {
                this.current_height = height;
            }
            public int getCurrentWidth(){
                
                return this.current_width;
            }
            public int getCurrentHeight(){
                return this.current_height;
            }
            /*======================PAINT COMPONENT===================================*
             *====================================================================*/
            public void paintComponent(Graphics g) {
                super.paintComponents(g);
                Graphics2D g2 = (Graphics2D)g;
                g2.drawImage(currentImage, null, 0, 0);
                if(isIncrease && x2>0 &&y2>0)
                {       
                        //increasingLinear();
                        increasingLinear(current_width,current_height,x2,y2);
                        g2.drawImage(currentImage, null, 0, 0);
                }
                if(isDecrease && x2>0 &&y2>0)
                {
                        current_width = this.getCurrentWidth();
                        current_height = this.getCurrentHeight();
                        System.out.println("current height:"+current_height+"current width"+ current_width);
                        decreasingNearest();
                        //decreasingNearest(current_width, current_height,x2,y2);
                        g2.drawImage(currentImage, null, 0, 0);
                        this.repaint();
                }
                if(isGray)
                {
                        getGrayScale();
                        g2.drawImage(grayImage, null, 0, 0);
                }
                if (isCropping)
                {
                        // Paint the area we are going to crop.
                        Stroke stroke = new BasicStroke(1,
                                        BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                                        new float[] { 12, 12 }, 0);
                        g2.setStroke(stroke);
                        g2.setColor(Color.WHITE);
                        g2.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2));
                }
            }
            /*===============================================MOUSE EVENT==============================================================================================
             * =====================================================================================================================================================*/
            @Override
            public void mouseClicked(MouseEvent evt) {
                // TODO Auto-generated method stub
                isCropping = false;
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                // TODO Auto-generated method stub
                isCropping = false;
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                // TODO Auto-generated method stub
                isCropping = false;
            }
            

            @Override
            public void mouseMoved(MouseEvent evt) {
                // TODO Auto-generated method stub
                isCropping = false;
            }       
            @Override
            public void mousePressed(MouseEvent evt) {
                // TODO Auto-generated method stub
                
                        this.x1 = evt.getX();
                        this.y1 = evt.getY();
                        if(x1<0 || y1<0 || x1 > currentImage.getWidth()|| y1 > currentImage.getHeight())
                        {
                                x1 = 0;
                                y1 = 0;
                                isCropping = false;
                        }
            }
            
            @Override
            public void mouseDragged(MouseEvent evt) {
                // TODO Auto-generated method stub
                if(isIncrease == false)
                {
                        if (isDecrease ==false)
                        {
                                        this.x2 = evt.getX();
                                        this.y2 = evt.getY();
                                        if (x2<0){ x2 = 0;}
                                        else if (y2<0){ y2 = 0;}
                                        else if (x2>currentImage.getWidth()){ x2 = currentImage.getWidth();}
                                        else if (y2>currentImage.getHeight()){ y2 = currentImage.getHeight();}
                                        else
                                        {
                                                isCropping = true;
                                                this.repaint();
                                        }
                        }
                        else
                        {
                                this.x2 = evt.getX();
                                this.y2 = evt.getY();
                                this.repaint();
                        }
                        this.revalidate();
                        this.repaint();
                }
                else
                {
                        this.x2 = evt.getX();
                        this.y2 = evt.getY();
                        isDecrease = false;
                        this.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                // TODO Auto-generated method stub
                this.isCropping = false;
                // This is the method a wrote in the other snippet 
                if( x1 != 0 && y1!= 0 && x2< currentImage.getWidth() && y2<currentImage.getHeight())
                {
                                BufferedImage cropped = crop(currentImage, new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2)));
                                this.currentImage = cropped;
                }
            }
            /*==================================================CROP IMAGE========================================================================================================================================
             * =======================================================================================================================================================================================================*/
            private BufferedImage crop(BufferedImage src, Rectangle rect) {
                // TODO Auto-generated method stub
                BufferedImage dest = new BufferedImage((int)rect.getWidth(), (int)rect.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE );
                Graphics g = dest.getGraphics();
                g.drawImage(src, 0, 0, (int)rect.getWidth(),(int) rect.getHeight(),(int) rect.getX(),(int) rect.getY(), (int)rect.getX() + (int)rect.getWidth(), (int) (rect.getY() + (int)rect.getHeight()), null);
                g.dispose();
                return dest;
            }
            /*==================================================COLOR TO GRAYSCALE========================================================================================================================================
             * =======================================================================================================================================================================================================*/
            public void getGrayScale()
            {
                grayImage = new BufferedImage(currentImage.getWidth(),currentImage.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
                WritableRaster raster = grayImage.getRaster();
                int pixel, red, green, blue, s;
                for(int j = 0; j <raster.getHeight(); j++ )
                        for(int  i = 0; i< raster.getWidth(); i++)
                        {
                                pixel = currentImage.getRGB(i, j);
                                red   = (int) (pixel >>16) & 0xff;
                                green = (int) (pixel >>8) & 0xff;
                                blue  = (int) (pixel) & 0xff;
                                s = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
                                raster.setSample(i, j, 0, s);
                                int newRGB = 0xFF000000 + (s << 16) + (s << 8) + s;
                                currentImage.setRGB(i,j,newRGB);
                        }
            }
            /*=================================================================================================================
             * ===============================================INCREASE SIZE AN IMAGE WITHOUT PARAMETER===========================================
             */
            public void increasingLinear() {
                int w, h;
                int w2, h2;
                int id = 0;
                w = currentImage.getWidth();
                h = currentImage.getHeight();
                w2 = x2;
                h2 = y2;
                int[] pixels = new int[w*h];
                for (int j=0;j<h;j++) {
                    for (int i=0;i<w;i++) {
                        pixels[id] = currentImage.getRGB(i,j);
                        id++;
                    }
                }
                increaseImage = new BufferedImage(w2,h2,BufferedImage.TYPE_4BYTE_ABGR_PRE);
                currentImage = new BufferedImage(w2,h2,BufferedImage.TYPE_4BYTE_ABGR_PRE);
                int[] temp = new int[w2*h2] ;
                int a, b, c, d, x, y, index ;
                float x_ratio = ((float)(w-1))/w2 ;
                float y_ratio = ((float)(h-1))/h2 ;
                float x_diff, y_diff, blue, red, green ;
                int offset = 0 ;
                for (int j=0;j<h2;j++) {
                    for (int i=0;i<w2;i++) {
                        x = (int)(x_ratio * i) ;
                        y = (int)(y_ratio * j) ;
                        x_diff = (x_ratio * i) - x ;
                        y_diff = (y_ratio * j) - y ;
                        index = (y*w+x);                
                        a = pixels[index] ;
                        b = pixels[index+1];
                        c = pixels[index+w];
                        d = pixels[index+w+1];
                        //System.out.println("1: "+ a +"2: " + b+ "3: " + c + "4: "+ d);
                        // blue element
                        // Yb = Ab(1-w)(1-h) + Bb(w)(1-h) + Cb(h)(1-w) + Db(wh)
                        blue = (a&0xff)*(1-x_diff)*(1-y_diff) + (b&0xff)*(x_diff)*(1-y_diff) +
                               (c&0xff)*(y_diff)*(1-x_diff)   + (d&0xff)*(x_diff*y_diff);

                        // green element
                        // Yg = Ag(1-w)(1-h) + Bg(w)(1-h) + Cg(h)(1-w) + Dg(wh)
                        green = ((a>>8)&0xff)*(1-x_diff)*(1-y_diff) + ((b>>8)&0xff)*(x_diff)*(1-y_diff) +
                                ((c>>8)&0xff)*(y_diff)*(1-x_diff)   + ((d>>8)&0xff)*(x_diff*y_diff);

                        // red element
                        // Yr = Ar(1-w)(1-h) + Br(w)(1-h) + Cr(h)(1-w) + Dr(wh)
                        red = ((a>>16)&0xff)*(1-x_diff)*(1-y_diff) + ((b>>16)&0xff)*(x_diff)*(1-y_diff) +
                              ((c>>16)&0xff)*(y_diff)*(1-x_diff)   + ((d>>16)&0xff)*(x_diff*y_diff);

                        temp[offset] = 
                                0xff000000|((((int)red)<<16)&0xff0000)| ((((int)green)<<8)&0xff00) | ((int)blue) ;
                        
                        increaseImage.setRGB(i,j ,temp[offset]);
                        currentImage.setRGB(i,j ,temp[offset]);
                        offset++;
                    }
                }
            }
            /*========================================================================================
             * ================================INCREASE IMAGE SIZE WITH PARAMETER=====================
             */
            public void increasingLinear(int w, int h, int w2, int h2) {
                int id = 0;
                int[] pixels = new int[w*h];
                for (int j=0;j<h;j++) {
                    for (int i=0;i<w;i++) {
                        pixels[id] = colorImage.getRGB(i,j);
                        id++;
                    }
                }
                increaseImage = new BufferedImage(w2,h2,BufferedImage.TYPE_4BYTE_ABGR_PRE);
                currentImage = new BufferedImage(w2,h2,BufferedImage.TYPE_4BYTE_ABGR_PRE);
                int[] temp = new int[w2*h2] ;
                int a, b, c, d, x, y, index ;
                float x_ratio = ((float)(w-1))/w2 ;
                float y_ratio = ((float)(h-1))/h2 ;
                float x_diff, y_diff, blue, red, green ;
                int offset = 0 ;
                for (int j=0;j<h2;j++) {
                    for (int i=0;i<w2;i++) {
                        x = (int)(x_ratio * i) ;
                        y = (int)(y_ratio * j) ;
                        x_diff = (x_ratio * i) - x ;
                        y_diff = (y_ratio * j) - y ;
                        index = (y*w+x);                
                        a = pixels[index] ;
                        b = pixels[index+1];
                        c = pixels[index+w];
                        d = pixels[index+w+1];
                        //System.out.println("1: "+ a +"2: " + b+ "3: " + c + "4: "+ d);
                        // blue element
                        // Yb = Ab(1-w)(1-h) + Bb(w)(1-h) + Cb(h)(1-w) + Db(wh)
                        blue = (a&0xff)*(1-x_diff)*(1-y_diff) + (b&0xff)*(x_diff)*(1-y_diff) +
                               (c&0xff)*(y_diff)*(1-x_diff)   + (d&0xff)*(x_diff*y_diff);

                        // green element
                        // Yg = Ag(1-w)(1-h) + Bg(w)(1-h) + Cg(h)(1-w) + Dg(wh)
                        green = ((a>>8)&0xff)*(1-x_diff)*(1-y_diff) + ((b>>8)&0xff)*(x_diff)*(1-y_diff) +
                                ((c>>8)&0xff)*(y_diff)*(1-x_diff)   + ((d>>8)&0xff)*(x_diff*y_diff);

                        // red element
                        // Yr = Ar(1-w)(1-h) + Br(w)(1-h) + Cr(h)(1-w) + Dr(wh)
                        red = ((a>>16)&0xff)*(1-x_diff)*(1-y_diff) + ((b>>16)&0xff)*(x_diff)*(1-y_diff) +
                              ((c>>16)&0xff)*(y_diff)*(1-x_diff)   + ((d>>16)&0xff)*(x_diff*y_diff);

                        temp[offset] = 
                                0xff000000|((((int)red)<<16)&0xff0000)| ((((int)green)<<8)&0xff00) | ((int)blue) ;
                        
                        increaseImage.setRGB(i,j ,temp[offset]);
                        currentImage.setRGB(i,j ,temp[offset]);
                        offset++;
                    }
                }
            }
            /*=========================================================================================
             *  ==========================DECREASE IMAGE SIZE WITH PARAMETER===========================
             */
            public void decreasingNearest(int w, int h, int w2, int h2 ){
                int id = 0;
                int[] pixels = new int[w*h];
                for (int j=0;j<h;j++) {
                    for (int i=0;i<w;i++) {
                        pixels[id] = colorImage.getRGB(i,j);
                        id++;
                    }
                }
                decreaseImage = new BufferedImage(w2,h2,BufferedImage.TYPE_4BYTE_ABGR_PRE);
                //currentImage = new BufferedImage(w2,h2,BufferedImage.TYPE_4BYTE_ABGR_PRE);
                int[] temp = new int[w2*h2] ;
                int x, y;
                int x_ratio = (int)((w<<16)/w2)+1; ;
                int y_ratio = (int)((h<<16)/h2)+1 ;
                
                for (int j=0;j<h2;j++)
                    for (int i=0;i<w2;i++){   
                        x = ((x_ratio*i)>>16);
                        y = ((y_ratio*j)>>16);
                        temp[(j*w2)+i] = pixels[(int)((y*w)+x)];
                        currentImage.setRGB(i, j, temp[(j*w2)+i]);
                        decreaseImage.setRGB(i, j, temp[(j*w2)+i]);
                    }
            }
            /*============================================================================================
             *  ==========================DECREASE IMAGE SIZE WITHOUT PARAMETER===========================
             */
                public void decreasingNearest(){
                int id = 0;
                int w, h, w2, h2;
                w = currentImage.getWidth();
                h = currentImage.getHeight();
                w2 = x2;
                h2 = y2;
                int[] pixels = new int[w*h];
                for (int j=0;j<h;j++) {
                for (int i=0;i<w;i++) {
                        pixels[id] = currentImage.getRGB(i,j);
                        id++;
                }
            }
                decreaseImage = new BufferedImage(w2,h2,BufferedImage.TYPE_4BYTE_ABGR_PRE);
                currentImage = new BufferedImage(w2,h2,BufferedImage.TYPE_4BYTE_ABGR_PRE);
            int[] temp = new int[w2*h2] ;
            int x, y;
            //double x_ratio = w/(double)w2 ;
            //double y_ratio = h/(double)h2 ;
            int x_ratio = (int)((w<<16)/w2)+1; 
            int y_ratio = (int)((h<<16)/h2)+1 ;
            for (int j=0;j<h2;j++)
                for (int i=0;i<w2;i++){   
                        x = ((x_ratio*i)>>16);
                    y = ((y_ratio*j)>>16);
                    temp[(j*w2)+i] = pixels[(int)((y*w)+x)];
                    currentImage.setRGB(i, j, temp[(j*w2)+i]);
                    decreaseImage.setRGB(i, j, temp[(j*w2)+i]);
                }
                }
                /*==============================================================================================
                 * =========================MERGE IMAGE===========================================
                 */
                public BufferedImage mergeImage(BufferedImage first, BufferedImage second,double weight1, double weight2){
                        int w1 = first.getWidth();
                        int h1 = first.getHeight();
                        int w2 = second.getWidth();
                        int h2 = second.getHeight();
                        int new_width,new_height;
                        
                        new_width = Math.min(w1, w2);
                        new_height = Math.min(h1, h2);
                        
                        int[] pixel1 = new int[w1*h1];
                        int[] pixel2 = new int[w2*h2];
                        int[] pixel3 = new int[new_width*new_height];
                        
                        int id1 = 0;
                        for (int j=0;j<h1;j++) {
                        for (int i=0;i<w1;i++) {
                                pixel1[id1] = currentImage.getRGB(i,j);
                                id1++;
                        }
                    }
                        int id2 = 0;
                        for (int j=0;j<h2;j++) {
                        for (int i=0;i<w2;i++) {
                                pixel2[id2] = secondImage.getRGB(i,j);
                                id2++;
                        }
                    }
                        
                        int red1, red2, red3;
                        int green1, green2, green3;
                        int blue1, blue2, blue3;
                        int alpha1, alpha2, alpha3;
                        double wght1, wght2;
                        int index;
                        int offset = 0;
                        fusionImage = new BufferedImage(new_width,new_height,BufferedImage.TYPE_4BYTE_ABGR_PRE);
                        for (int j=0;j<new_height;j++)
                        for (int i=0;i<new_width;i++){
                                index = (j*new_width) + i;
                                red1 = (pixel1[index]>>16) & 0xff;
                                red2 = (pixel2[index]>>16) & 0xff;
                                green1 = (pixel1[index]>>8) & 0xff;
                                green2 = (pixel2[index]>>8) & 0xff;
                                blue1 = (pixel1[index]) & 0xff;
                                blue2 = (pixel2[index]) & 0xff;
                                alpha1 = (pixel1[index]>>24) & 0xff;
                                alpha2 = (pixel2[index]>>24) & 0xff;
                                
                                //Change weight of image
                                wght1 = weight1*(alpha1/255.0);
                                wght2 = weight2*(alpha2/255.0);
                                
                                red3 = (int)(red1*wght1 + red2*wght2);
                                red3 = (red3<0)?(0):(red3>255)?(255):(red3);
                                green3 = (int)(green1*wght1 + green2*wght2);
                                green3 = (green3<0)?(0):(green3>255)?(255):(green3);
                                blue3 = (int)(blue1*wght1 + blue2*wght2);
                                blue3 = (blue3<0)?(0):(blue3>255)?(255):(blue3);
                                
                                alpha3 = 255;
                                pixel3[offset] = (((((alpha3 << 8) + (red3 & 0x0ff)) << 8) + (green3 & 0x0ff)) << 8) + (blue3 & 0x0ff);
                                fusionImage.setRGB(i, j, pixel3[offset]);
                                offset++;
                        }
                        return fusionImage;
                }
        }