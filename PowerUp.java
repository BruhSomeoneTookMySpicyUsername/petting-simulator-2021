
/**
 * Authors: Gerald Wang, Parker Rho, Anuj Jain
 * Purpose: allows for a powerup object to be instantiated
 */

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class PowerUp {

  private int type;
  private double speedIncrease = 0;
  private Pos pos;
  private Image pic;

  public PowerUp(int t) {
    type = t;

    // 1 = sprint, 2 = time increase
    if (type == 1) {
      pic = new ImageIcon("images/Speed.png").getImage();
    }
    if (type == 2) {
      pic = new ImageIcon("images/Clock.png").getImage();
    }

    // Spawns the powerup in a random spot on the map
    pos = PetList.powerChooseSpot();
  }

  // Method to return the type of the powerup
  public int getType() {
    return type;
  }

  // Method to return how much the speed has increased from picking up speed
  // powerups
  public double getSpeedFactor() {
    return speedIncrease;
  }

  // Method to update how much the speed has increased from picking up speed
  // powerups
  public void increaseSpeed(double x) {
    speedIncrease += x;
  }

  // Returns the position of the powerup
  public Pos getPos() {
    return pos;
  }

  // Method to draw the powerup in the GamePanel class
  public void draw(Graphics g) {
    g.drawImage(
        pic, (pos.getCorner(0) + pos.getCorner(2)) / 2, (pos.getCorner(1) + pos.getCorner(3)) / 2, null);
  }
}
