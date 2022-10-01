import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;

class GameFrame extends JFrame {

  private JFrame frame;
  private GamePanel panel;
  private JLabel label;
  private Timer timer;
  private Font font;
  private static boolean gameEnd;

  public GameFrame() {
    // Initializes frame, label, and panel objects
    frame = new JFrame("Petting Simulator 2021");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    label = new JLabel();
    panel = new GamePanel();
    gameEnd = false;
    font = new Font("Arial", Font.PLAIN, 30);
    label.setFont(font);

    // Sets timer to 0
    resetTimer();

    // Initializes the Main game
    panel.setPreferredSize(new Dimension(1000, 1000));
    panel.setBackground(new Color(114, 137, 218));
    panel.setLayout(new FlowLayout());
    panel.add(label);
    frame.getContentPane().add(panel);
  }

  // Display method for the frame
  public void display() {
    frame.pack();
    frame.setVisible(true);
  }

  // Returns whether the game has ended
  public static boolean getGameEnd() {
    return gameEnd;
  }

  // Creates a new timer, sets it to 2 minutes
  public void resetTimer() {
    timer = new Timer();
    timer.scheduleAtFixedRate(
        new TimerTask() {
          int i = 120;

          public void run() {
            // Displays the time
            if (i % 60 < 10) {
              label.setText(i / 60 + ":0" + i % 60);
            } else {
              label.setText(i / 60 + ":" + i % 60);
            }
            i--;

            // In the event that a time power up is colleted, add 5 seconds
            if (GamePanel.getCollectedTimePwrUp()) {
              i += 5;
              GamePanel.setCollectedTimePwrUp(false);
            }

            // When the timer runs out, stop the timer and end the game
            if (i < -1) {
              timer.cancel();
              gameEnd = true;
            }

            // Disposes of the main game frame and launches the end screen
            if (getGameEnd()) {
              panel.remove(label);
              frame.getContentPane().remove(panel);
              frame.dispose();
              EndScreen s = new EndScreen();
            }
          }
        },
        0,
        1000);
  }
}
