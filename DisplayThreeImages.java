import java.awt.Dimension;
import java.awt.GridLayout;  
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class DisplayThreeImages extends JFrame{
        private ImagePanel jImagePane;
        private double weight1, weight2;
        private DisplayImagePanel d1 = new DisplayImagePanel();
        private DisplayImagePanel d2 = new DisplayImagePanel();
        private DisplayImagePanel d3 = new DisplayImagePanel();
        private JTabbedPane tbImage = new JTabbedPane();
        /*
         * =========================CONSTRUCTOR==============================
         */
        public DisplayThreeImages(ImagePanel jImagePane, double weight1, double weight2){
                this.weight1 = weight1;
                this.weight2 = weight2;
                loadImage(jImagePane);
                showImage();
                group();
        }
        /*
         * ========================SETTER AND GETTER=========================
         */
        public void setWeight1(double weight1)
        {
                this.weight1 = weight1;
        }
        public double getWeight1(){
                return weight1;
        }
        public void setWeight2(double weight2)
        {
                this.weight2 = weight2;
        }
        public double getWeight2(){
                return weight2;
        }
        /*
         * =========================LOAD IMAGE============================== 
         */
        private void loadImage(ImagePanel jImagePane){
                d1.setImage(jImagePane.getCurrentImage());
                d2.setImage(jImagePane.getSecondImage());
                d3.setImage(jImagePane.mergeImage(jImagePane.getCurrentImage(), jImagePane.getSecondImage(),weight1,weight2));
        }
        private void showImage(){
                d1.repaint();
                d2.repaint();
                d3.repaint();
        }
        private void group(){
                tbImage.setPreferredSize(new Dimension(500, 400));
                tbImage.addTab("First Image",d1);
                tbImage.addTab("Second Image",d2);
                tbImage.addTab("Merging Result of Images",d3);
                this.add(tbImage);
        }
        
}