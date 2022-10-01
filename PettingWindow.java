import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PettingWindow implements ActionListener {
  // PettingWindow is a class that runs when in GamePanel the character interacts
  // with the pet

  private JButton pet;
  private JPanel panel;
  private JFrame petting;
  private ImageIcon dogimg;
  private JLabel instruction, ctr;
  private int counter;
  private boolean completed;
  private int goal;

  public PettingWindow(ImageIcon dog) {
    // simple menu takes the pet object's image as a parameter so it displays the
    // right pet on the menu
    goal = (int) ((Math.random() * 10) + 1);
    // add some randomness/variety to our game
    panel = new JPanel();
    petting = new JFrame("Pet the dog!");
    petting.setSize(500, 500);
    dogimg = dog;
    pet = new JButton(dogimg);
    instruction = new JLabel("Pet the dog " + goal + " times ");
    counter = 0;
    ctr = new JLabel("Counter: " + counter);
    panel.setLayout(new BorderLayout());
    petting.setLayout(new BorderLayout());
    panel.add(instruction, BorderLayout.NORTH);
    panel.add(pet, BorderLayout.CENTER);
    panel.add(ctr, BorderLayout.SOUTH);
    petting.add(panel, BorderLayout.CENTER);

    pet.addActionListener(this);
    pet.setFocusable(false);
    petting.setVisible(true);

    instruction.setHorizontalAlignment(JLabel.CENTER);
    instruction.setVerticalAlignment(JLabel.CENTER);
    ctr.setHorizontalAlignment(JLabel.CENTER);
    ctr.setVerticalAlignment(JLabel.CENTER);
    // centers labels on screen
    // similar to StartFrame and EndScreen - same library used for the menus
  }

  public boolean getCompleted() {
    return completed;
    // method that runs when the amount of clicks is correct
  }

  public void actionPerformed(ActionEvent e) {

    JButton actionSource = (JButton) e.getSource();

    // Counter increases when pet button is pressed, and there's also a bark sound
    if (actionSource.equals(pet)) {
      GamePanel.bark();
      counter++;
      ctr.setText("Counter: " + counter);
    }

    // Once the counter reaches the generated amount of pets it barks and then closes
    if (counter == goal) {
      completed = true;
      ctr.setText("Counter: " + counter);
      instruction.setText("Congrats!");
      GamePanel.bark();
      petting.dispose();
    }
  }
}
