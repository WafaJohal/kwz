
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
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener {
                private BufferedImage colorImage;
                private BufferedImage grayImage;
                private int x1,x2,y1,y2;
                private boolean isCropping;
                private boolean isGray = false;
                /*=======================================================================
                 * =========================CONSTRUCTOR==================================*/
                public ImagePanel(){
                        this.addMouseListener(this);
                    this.addMouseMotionListener(this);
                }
                /*==========================SET AND GET IMAGE==========================*
                 *====================================================================*/
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
                
                /*==========================SET AND GET CROPPING========================*
                 *====================================================================*/
                public void setIsCropping(boolean isCropping)
                {
                        this.isCropping = isCropping;
                }
                public boolean getIsCropping()
                {
                        return this.isCropping;
                }
                /*==========================SET AND GET GRAYSCALE========================*
                 *====================================================================*/
                public void setIsGray(boolean isGray){
                        this.isGray = isGray;
                }
                public boolean getIsGray(){
                        return this.isGray;
                }
                /*======================PAINT COMPONENT===================================*
                 *====================================================================*/
                public void paintComponent(Graphics g) {
                        super.paintComponents(g);
                        Graphics2D g2 = (Graphics2D)g;
                                g2.drawImage(colorImage, null, 0, 0);
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
                        if(isGray)
                        {
                                getGrayScale();
                                g2.drawImage(grayImage, null, 0, 0);
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
                public void mouseDragged(MouseEvent evt) {
                        // TODO Auto-generated method stub
                       this.x2 = evt.getX();
                       this.y2 = evt.getY();
                       isCropping = false;
                       if (x2<0){ x2 = 0;}
                       else if (y2<0){ y2 = 0;}
                       else if (x2>colorImage.getWidth()){ x2 = colorImage.getWidth();}
                       else if (y2>colorImage.getHeight()){ y2 = colorImage.getHeight();}
                       else
                       {
                           isCropping = true;
                           this.repaint();
                       }
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
                                if(x1<0 || y1<0 || x1 > colorImage.getWidth()|| y1 > colorImage.getHeight())
                                {
                                        x1 = 0;
                                        y1 = 0;
                                        isCropping = false;
                                }
                }

                @Override
                public void mouseReleased(MouseEvent evt) {
                        // TODO Auto-generated method stub
                        this.isCropping = false;
                    // This is the method a wrote in the other snipped
                        if( x1 != 0 && y1!= 0 && x2< colorImage.getWidth() && y2<colorImage.getHeight())
                        {
                                if(!isGray)
                                {
                                        BufferedImage cropped = crop(colorImage, new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2)));
                                        this.colorImage = cropped;
                                }
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
                        grayImage = new BufferedImage(colorImage.getWidth(),colorImage.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
                        WritableRaster raster = grayImage.getRaster();
                        int pixel, red, green, blue, s;
                        for(int  i = 0; i< raster.getWidth(); i++)
                                for(int j = 0; j <raster.getHeight(); j++ )
                                {
                                        pixel = colorImage.getRGB(i, j);
                                        red   = (int) (pixel >>16) & 0xff;
                                        green = (int) (pixel >>8) & 0xff;
                                        blue  = (int) (pixel) & 0xff;
                                        s = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
                                        raster.setSample(i, j, 0, s);
                                        int newRGB = 0xFF000000 + (s << 16) + (s << 8) + s;
                                        colorImage.setRGB(i,j,newRGB);
                                }
                }
}

	