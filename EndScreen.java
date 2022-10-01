import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndScreen implements ActionListener {

  private JFrame fr;
  private JPanel pan;
  private JButton retry, quit;
  private JLabel score;
  private int scr = GamePanel.getScore();
  private int hiscr = GamePanel.getHiScore();
  private GameFrame gf;

  public EndScreen() {
    fr = new JFrame("GAME OVER");
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setSize(500, 500);
    // constructs similar to StartFrame and PettingWindow
    pan = new JPanel();
    retry = new JButton("Play again?");
    quit = new JButton("Quit");
    score = new JLabel("<html> Your score: " + scr + "<br/> Your highscore: " + hiscr + "</html>");
    // score mechanic
    retry.addActionListener(this);
    retry.setFocusable(false);
    quit.addActionListener(this);
    quit.setFocusable(false);
    // makes button functional
    pan.setLayout(new BorderLayout());
    pan.add(score, BorderLayout.CENTER);
    score.setHorizontalAlignment(JLabel.CENTER);
    score.setVerticalAlignment(JLabel.CENTER);
    pan.add(retry, BorderLayout.NORTH);
    pan.add(quit, BorderLayout.SOUTH);
    // organizes jpanel
    ImageIcon img = new ImageIcon("images/ScruffyGif.gif");
    JLabel picLabel1 = new JLabel(img);
    pan.add(picLabel1, BorderLayout.WEST);
    ImageIcon img2 = new ImageIcon("images/FluffyGifFlip.gif");
    JLabel picLabel2 = new JLabel(img2);
    // adds gif implemenation
    pan.add(picLabel2, BorderLayout.EAST);
    fr.setLayout(new BorderLayout());
    fr.add(pan, BorderLayout.CENTER);
    fr.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    JButton actionSource = (JButton) e.getSource();
    // retry and quit buttons work
    if (actionSource.equals(retry)) {
      // gf.resetTimer();
      // gf.getGamePanel().setScore(0);

      gf = new GameFrame();
      // makes new gameframe -- resolves some fps issues we were having
      gf.display();
      fr.dispose();
    } else if (actionSource.equals(quit)) {
      fr.dispose();
      System.exit(0);
      // system.exit() ends java so quit button actually should work
    }
  }
}
