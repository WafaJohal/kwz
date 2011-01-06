

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
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

	private JFrame jFrame = null;  
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem jMenuItem = null;
	private JMenuItem jMenuItem1 = null;
	private BufferedImage img = null;
	private JFileChooser jFileChooser = new JFileChooser();
	private ContentPanel jcontPanel = new ContentPanel();
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
			jFrame.setContentPane(jcontPanel);
		}
		return jFrame;
	}
	/*private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}*/
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
	
	
	
/*=================================================================================================*/
	class OpenAction implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int retval = jFileChooser.showOpenDialog(null);
			if(retval==jFileChooser.APPROVE_OPTION){
				File file = jFileChooser.getSelectedFile();
				try {
					img = ImageIO.read(file);
					jcontPanel.repaint();
				}catch(IOException e){
				}
			}
	}
}
/*======================================================================================================================*/
	class ContentPanel extends JPanel{
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