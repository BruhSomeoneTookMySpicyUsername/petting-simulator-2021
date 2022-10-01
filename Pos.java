
/**
 * Authors: Gerald Wang, Parker Rho, Anuj Jain
 * Purpose: used to track the position of the pets (operates in the same way the Character class does to track position).
 *   - See Character class for more information on how we calculated the position.
 * Date: 5/31/21
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Pos {

  private Rectangle pos;

  // Creates a new rectangle to determine position, essentially a hitbox
  public Pos(int x, int y, int width, int height) {
    pos = new Rectangle(x, y, width, height);
  }

  // Returns a corner of the rectangle
  public int getCorner(int x) {
    if (x == 0) {
      return (int) (pos.getLocation().getX());
    } else if (x == 1) {
      return (int) (pos.getLocation().getY());
    } else if (x == 2) {
      return (int) (pos.getLocation().getX() + pos.getWidth());
    } else if (x == 3) {
      return (int) (pos.getLocation().getY() + pos.getHeight());
    }
    return -1;
  }

  // Method to set a corner
  public void setCorner(int x, int y) {
    pos.setLocation(x, y);
  }

  // Checking collisions with the map image by returning true if there is overlap
  // between the pos rectangle and the map
  public boolean collisionCheck(BufferedImage map) {
    try {
      map = ImageIO.read(new File("images/Map.png"));
    } catch (IOException e) {
    }
    for (int i = getCorner(0); i < getCorner(2); i++) {
      for (int j = getCorner(1); j < getCorner(3); j++) {
        if (map.getRGB(i, j) != 0) {
          return true;
        }
      }
    }
    return false;
  }

  // Checks if 2 spawned elements of the game are on top of each other based on
  // their positions
  public boolean nearDog(Pos spawn) {
    Rectangle dogPos = new Rectangle(spawn.getCorner(0), spawn.getCorner(1), 90, 60);
    return pos.intersects(dogPos);
  }

  // Applies nearDog to an array of positions
  public boolean dogList(ArrayList<Pos> dogList) {
    for (int i = 0; i < dogList.size(); i++) {
      if (nearDog(dogList.get(i))) {
        return true;
      }
    }
    return false;
  }
}