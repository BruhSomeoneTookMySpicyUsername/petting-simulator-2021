import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartFrame extends JFrame implements ActionListener {
  /*
   * StartFrame replaces GameOption as the GUI to allow you to run the game; the
   * code for StartFrame served as the basis for all the menus like PettingWindow
   * and EndScreen. We used the Swing Library for Frame, JPanel, JLabel and
   * JButton across the project.
   * 
   * 
   * 
   */

  private JButton buttonS;
  private JButton buttonH;
  private JButton buttonA;
  private JFrame fr;
  private GameFrame game;
  private JPanel panel;

  public StartFrame() {
    fr = new JFrame("Menu");
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setSize(500, 500);
    panel = new JPanel();
    buttonS = new JButton("START");
    buttonH = new JButton("HELP!");
    buttonA = new JButton("QUIT!");
    // these are the three buttons
    buttonS.addActionListener(this);
    buttonS.setFocusable(false);
    buttonH.addActionListener(this);
    buttonH.setFocusable(false);
    buttonA.addActionListener(this);
    buttonA.setFocusable(false);

    // this makes the buttons clickable
    panel.setLayout(new BorderLayout());
    panel.add(buttonS, BorderLayout.CENTER);
    panel.add(buttonH, BorderLayout.NORTH);
    panel.add(buttonA, BorderLayout.SOUTH);
    // this organizes BorderLayout and adds the buttons to the panel

    fr.add(panel);
    // we choose to add JPanel to JFrame in case we wanted to do more customization
    fr.setLocationRelativeTo(null);
    fr.getContentPane().setBackground(Color.black);
    ImageIcon img = new ImageIcon("ScruffyGif.gif"); // find dog - fluffy?
    JLabel picLabel1 = new JLabel(img);
    panel.add(picLabel1, BorderLayout.WEST);
    ImageIcon img2 = new ImageIcon("FluffyGifFlip.gif");
    JLabel picLabel2 = new JLabel(img2);
    panel.add(picLabel2, BorderLayout.EAST);
    panel.setVisible(true);
    fr.setVisible(true);
    /*
     * wanted to add images to StartFrame so we made them ImageIcons which were
     * added to JLabels which were added to the JPanel
     * 
     */
    /*
     * JLabel label = new JLabel(); label.setText(
     * "                                                                                                                             Welcome to Pet Simulator 2021!"
     * ); JLabel labelS = new JLabel(); labelS.setText("");
     */
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    JButton actionSource = (JButton) e.getSource();
    // if statement that allows different buttons to do different things

    if (actionSource.equals(buttonS)) {
      GamePanel.bark();
      //start = true;
      game = new GameFrame();
      game.display();
      fr.dispose();

      // runs GameFrame and closes menu

    } else if (actionSource.equals(buttonH)) {
      GamePanel.bark();
      // help button
      JOptionPane.showMessageDialog(null,
          "Use WASD to move around, F to interact with the pets and bring the pets back to the garden! \nThe clock and green arrows are powerups: the clock gives time (5 seconds) and the arrows give +1 speed",
          "Directions", JOptionPane.INFORMATION_MESSAGE);
      // directions ^
    } else if (actionSource.equals(buttonA)) {
      GamePanel.bark();
      fr.dispose();
      System.exit(0);
      // quit button
    }
  }
}
