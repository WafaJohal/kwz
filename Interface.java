

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.FileDialog;
import java.awt.Panel;
import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.JMenuItem;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class Interface {

	// Main Frame
	private JFrame jFrame = null;  
	private JPanel jContentPane = null;
	
	//Image Frame component of the Main Frame in the jContentPane
	private JFrame jImageFrame = null;
	private ImagePanel jImagePane = null;
	
	//Tools Frame component of the Main Frame in the jContentPane
	private JFrame jToolFrame = null; 
	private JPanel jToolPane = null;
	
	//Tools'Panel Components 
	private JButton jButtonCrop = null;
	private JButton jButtonPrintPix = null;
	private JButton jButtonHisto = null;
	private JButton jButtonGray = null;
	private JButton jButtonBlur = null;
	private JButton jButtonFusion = null;
	private JLabel jLabelPrintPix = null;
	
	//Menu
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem jMenuItem = null;
	private JMenuItem jMenuItem1 = null;
	
	//Image variables
	private BufferedImage img = null;
	private JFileChooser jFileChooser = new JFileChooser();
	private String filename; // image's file name
	
/*=================================================================================================*/
	public Interface(){
		this.getJFrame();
	}
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(500, 400));
			jFrame.setTitle("Image Software");
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setVisible(true);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setContentPane(getJContentPane());
		}
		return jFrame;
	}
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJImagePane(), BorderLayout.CENTER);
			jContentPane.add(getJToolPanel(), BorderLayout.EAST);
		}
		return jContentPane;
	}
	
	private JFrame getJImageFrame(){
		if(jImageFrame == null){
			jImageFrame = new JFrame();
			jImageFrame.add(getJImagePane());		
		}
		return jImageFrame;
	}
	
	private ImagePanel getJImagePane(){
		if (jImagePane == null){
			jImagePane = new ImagePanel();
		}
		return jImagePane;
	}
	
	private JFrame getToolFrame(){
		if(jToolFrame == null){
			jToolFrame = new JFrame();
			jToolFrame.setSize(new Dimension(0, 50));
			jToolFrame.setContentPane(getJToolPanel());
		}
		return jToolFrame;
	}
	private JPanel getJToolPanel(){
		if(jToolPane == null){
			jToolPane = new JPanel();
			jToolPane.setLayout(new GridLayout(5, 2, 5, 5));
			jToolPane.add(getJButtonCrop());
			jToolPane.add(getJButtonPrintPix());
			jToolPane.add(getJButtonHisto());
			jToolPane.add(getJButtonGray());
			jToolPane.add(getJButtonBlur());
			jToolPane.add(getJButtonFusion());
		}
		return jToolPane;
	}

	private JButton getJButtonCrop(){
		if(jButtonCrop == null){
			jButtonCrop = new JButton(new ImageIcon("icon/crop.png"));
		}
		return jButtonCrop;
	}
	private JButton getJButtonPrintPix(){
		if(jButtonPrintPix == null){jButtonPrintPix = new JButton(new ImageIcon("icon/pipette.png"));}
		return jButtonPrintPix;
	}
	private JButton getJButtonHisto(){
		if(jButtonHisto == null){jButtonHisto = new JButton(new ImageIcon("icon/histo.gif"));}
		return jButtonHisto;
	}
	private JButton getJButtonGray(){
		if(jButtonGray == null){jButtonGray = new JButton(new ImageIcon("icon/gray.png"));}
		return jButtonGray;
	}
	private JButton getJButtonBlur(){
		if(jButtonBlur == null){jButtonBlur = new JButton(new ImageIcon("icon/blur.png"));}
		return jButtonBlur;
	}
	private JButton getJButtonFusion(){
		if(jButtonFusion == null){jButtonFusion = new JButton(new ImageIcon("icon/fusion.png"));}
		return jButtonFusion;
	}
	
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.setPreferredSize(new Dimension(0, 20));
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getJMenuItem());
			fileMenu.add(getJMenuItem1());
		}
		return fileMenu;
	}
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
		}
		return helpMenu;
	}
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem("Open...");
			jMenuItem.addActionListener(new OpenAction());
		}
		return jMenuItem;
	}
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem("Save...");
			jMenuItem1.addActionListener(new SaveAction());
		}
		return jMenuItem1;
	}
	
	
	
/*=================================================================================================*/
	class OpenAction implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int retval = jFileChooser.showOpenDialog(null);
			if(retval==jFileChooser.APPROVE_OPTION){
				File file = jFileChooser.getSelectedFile();
				try {
					img = ImageIO.read(file);
					jContentPane.repaint();
				}catch(IOException e){
				}
			}
	}
}

/*======================================================================================================================*/
/////////////////////////////////////////////////////////////////////////////////////////////
	//saving files
    public void save(File file) {
        this.filename = file.getName();
        if (jFrame != null) { jFrame.setTitle(filename); }
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        if (suffix.equals("jpg") || suffix.equals("png")|| suffix.equals("gif") || suffix.equals("pnm") ) {
            try { ImageIO.write(img, suffix, file); }
            catch (IOException e) { e.printStackTrace(); }
        }
        else {
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
    public void save(String name) {
        save(new File(name));
    }
	//////////////////////////////////////////////////////////////////////////////////////////////
    // Action Listener for save:
	class SaveAction implements ActionListener{
		public void actionPerformed(ActionEvent ae){
		    FileDialog chooser = new FileDialog(jFrame,"Use a .png,jpg,gif or .jpg extension", FileDialog.SAVE);
                    chooser.setVisible(true);
                    if (chooser.getFile() != null) {
                    save(chooser.getDirectory() + File.separator + chooser.getFile());
                  }
	}
}
	//////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////
	class ImagePanel extends JPanel{
		/* paint image on the component*/
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2 = (Graphics2D)g; // we need a Graphics2D context    
	        // Draw the grid from the pre-computed image
			if(img!=null)
				g2.drawImage(img, null, 0, 0);
		}	
	}
}