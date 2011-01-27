import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


public class DisplayImagePanel extends JPanel{
        private BufferedImage image;
        /*
         * ======================CONSTRUCTOR==============================
         */
        public DisplayImagePanel(){
                this.setPreferredSize(getPreferredSize());
                this.setLayout(new BorderLayout());
        }
        /*
         * ==============SET AND GET IMAGE================================
         */
        public void setImage(BufferedImage img)
        {
                image = img;
        }
        public BufferedImage getImage(){
                return image;
        }
        /*
         *=================== PAINT COMPONENTS===============================
         */
         public void paintComponent(Graphics g) {
                super.paintComponents(g);
                Graphics2D g2 = (Graphics2D)g;
                g2.drawImage(image,null,0,0);
         }
}