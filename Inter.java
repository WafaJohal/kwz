

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.*;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;


public class Inter {
/*
	private JFrame jFrame = null;  
	private JPanel jContentPane= null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem jMenuItem = null;
	private JMenuItem jMenuItem1 = null;
	private BufferedImage img = null;
	private JFileChooser jFileChooser = new JFileChooser();
	//private ImagePanel jimagePanel = new ImagePanel();
	private JPanel jToolsPanel = null;
	private JButton jbprintpix = null;
	private JLabel jlbPrintPix = null;
	
	/*=================================================================================================*/
	/*public Inter(){
		this.getJFrame();
	}
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(500, 400));
			jFrame.setTitle("Image Software");
			jFrame.setContentPane(setJPane());
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setVisible(true);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setContentPane(jContentPane);
		}
		return jFrame;
	}
	private JPanel setJPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
		//	jContentPane.add(setImagePane(), BorderLayout.CENTER);
			jContentPane.add(setToolsPane(), BorderLayout.EAST);
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
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
		}
		return jMenuItem1;
	}
	private JPanel setToolsPane() {
		if (jToolsPanel == null) {
			jlbPrintPix = new JLabel();
			jlbPrintPix.setText("printpix \n R :"+"\t Y : "+"\n G : "+"\t U : "+"\n B : "+"\t V : ");
			jToolsPanel = new JPanel();
			jToolsPanel.setPreferredSize(new Dimension(50, 30));
			jToolsPanel.setLayout(new GridLayout(2,1));
			jToolsPanel.add(getJButton());
			jToolsPanel.add(getJLBPrintPix());			
		}
		return jToolsPanel;
	}
	private ImagePanel setImagePane() {
		if (jimagePanel == null) {
			jimagePanel = new ImagePanel();
					}
		return jimagePanel;
	}
	private JButton getJButton() {
		if (jbprintpix == null) {
			jbprintpix = new JButton();
			jbprintpix.setSize(new Dimension(20,20));
			jbprintpix.setIcon(new ImageIcon("home/wafa/Bureau/icon14.gif"));
		}
		return jbprintpix;
	}
	private JLabel getJLBPrintPix() {
		if (jlbPrintPix == null) {
			jlbPrintPix = new JLabel();
			jlbPrintPix.setSize(new Dimension(20,20));
			//jlbPrintPix.setIcon(new ImageIcon("home/wafa/Bureau/icon14.gif"));
		}
		return jlbPrintPix;
	}
/*=================================================================================================*/
	/*class OpenAction implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			JFileChooser chooser = new JFileChooser();
			chooser.setApproveButtonText("Choose an image...");
			chooser.setFileFilter(new ImageFilter());
			int retval = chooser.showOpenDialog(null);
			if(retval==chooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				try {
					img = ImageIO.read(file);
					jimagePanel.repaint();
				}catch(IOException e){
				}
			}
	}
}
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
/*======================================================================================================================*/
	/*class ImagePanel extends JPanel{
		/* paint image on the component*/
		/*public void paintComponent(Graphics g) {
			super.paintComponents(g);
			Graphics2D g2 = (Graphics2D)g; // we need a Graphics2D context    
	        // Draw the grid from the pre-computed image
			if(img!=null)
				g2.drawImage(img, null, 0, 0);
		}	
	}

	/*======================================== ImageFilter.java is used by FileChooserDemo2.java. ================================*/
/*	class ImageFilter extends FileFilter {
		// Accept all directories and all gif, jpg, tiff, or png files.
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}
			String extension = f.getName().toLowerCase();
			if (extension != null) {
				if (extension.endsWith("gif") || extension.endsWith("png")|| extension.endsWith("pnm")|| extension.endsWith("jpg")) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		}

		// The description of this filter
		public String getDescription() {
			return "Just Images";
		}
	}
	
	class PrintPixLB extends JLabel{
		private int x, y; // coordonnates of the pixel
		public PrintPixLB(int x, int y){
			//jimagePanel.g
		}
		
	}

	public static void main(String[] args) {
		interface2 in1 = new interface2();
	}
	*/
}


