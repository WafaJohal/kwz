import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class ButtonSample {
  public static void main(String args[]) {
    JFrame frame = new JFrame("Button Sample");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JButton button = new JButton("Select Me");
    // Define MouseListener
    MouseListener mouseListener = new MouseAdapter() {
      public void mousePressed(MouseEvent mouseEvent) {
        int modifiers = mouseEvent.getModifiers();
        if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
          System.out.println("Left button pressed.");
        }
        if ((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) {
          System.out.println("Middle button pressed.");
        }
        if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
          System.out.println("Right button pressed.");
        }
      }
      public void mouseReleased(MouseEvent mouseEvent) {
        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
          System.out.println("Left button released.");
        }
        if (SwingUtilities.isMiddleMouseButton(mouseEvent)) {
          System.out.println("Middle button released.");
        }
        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
          System.out.println("Right button released.");
        }
        System.out.println();
      }
    };
    // Attach listeners
    button.addMouseListener(mouseListener);
    frame.add(button, BorderLayout.SOUTH);
    frame.setSize(300, 100);
    frame.setVisible(true);
  }
}