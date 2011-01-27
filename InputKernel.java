import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class InputKernel extends JFrame{
        private int numberText;
        private int[] valueKernel;
        private JPanel jp = new JPanel();
        private JButton bt = null;
        private TextField[] t;
        /*
         *=======================CONSTRUCTOR========================== 
         */
        public InputKernel(int number){
                this.numberText = number;
                this.setLayout(new BorderLayout());
                jp.setLayout(new GridLayout((int)Math.sqrt(number),(int)Math.sqrt(number),5,5));
                t = new TextField[number];
                for(int i = 0 ; i<number; i++)
                {
                        t[i] = new TextField("0");
                        
                        jp.add(t[i]);
                }
                this.add(jp, BorderLayout.CENTER);
                
                this.add(getJButtonbt(), BorderLayout.SOUTH);
                
        }
        /*
         * ===================INPUT VALUE TO TEXTFIELD================
         */
        public int[] getInput()
        {
                valueKernel = new int[numberText];
                for(int i = 0; i<numberText; i++)
                {
                        
                        if(t[i].getText()=="")
                        valueKernel[i] = Integer.parseInt(t[i].getText());
                        
                        else valueKernel[i]=1;
                }
                return valueKernel;
        }
        
        private JButton getJButtonbt() {
        if (bt == null) {
                bt=new JButton("Enter the kernel's values");
                ///////////////////////////////////////////////////////////
                 bt.addActionListener(new ActionListener(){
                 public void actionPerformed(ActionEvent e){
                         
                         System.out.print("hiiiiiiiiiiiiii");
                         
                }
         });
                
                /////////////////////////////////////////////////////////
    
}
    
        return bt;
        }
}