
/**
 * Authors: Gerald Wang, Parker Rho, Anuj Jain
 * Purpose: All elements of the player's character are implemented here.
 * Date: 5/20/21
 */

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Character {

  private Image turnLeft, turnRight, dogLeft, dogRight;
  private static Rectangle pos;
  private boolean isLeft, hasDog;

  public Character(int x, int y) {
    // The player's model for when they face right and left
    turnLeft = new ImageIcon("images/SpriteL.png").getImage();
    turnRight = new ImageIcon("images/SpriteR.png").getImage();

    // The player's model for when a dog is picked up
    dogLeft = new ImageIcon("images/SpriteDogL.png").getImage();
    dogRight = new ImageIcon("images/SpriteDogR.png").getImage();

    // The position of the player
    pos = new Rectangle(x, y, 90, 60);
  }

  // Returns the image of the player depending on whether they are facing right of
  // left
  public Image getImage() {
    if (hasDog) {
      if (isLeft) {
        return dogLeft;
      }
      return dogRight;
    } else {
      if (isLeft) {
        return turnLeft;
      }
      return turnRight;
    }
  }

  public boolean getPossession() {
    return hasDog;
  }

  // Tracks if the player is facing right or left
  public void setLeft(boolean turn) {
    isLeft = turn;
  }

  public void hasDog(boolean dog) {
    hasDog = dog;
  }

  // Returns the x or y coordinate of the upper or bottom corner
  public static int getCorner(int x) {
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

  // Updates the position of the player
  public void setCorner(int x, int y) {
    pos.setLocation(x, y);
  }

  // Method to check whether the player is touching terrain
  public static boolean collisionCheck(BufferedImage map) {
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

  // Method to check if the player is within interacting range of a chosen dog
  // using the dog's position as the parameter
  public static boolean nearDog(Pet dog) {
    Pos dogPos = dog.getPos();
    Rectangle dogPosRect = new Rectangle(
        dogPos.getCorner(0),
        dogPos.getCorner(1),
        90,
        60);
    return pos.intersects(dogPosRect);
  }

  // Method to check if the player is within interacting range of a power up
  public static boolean nearPwrUp(PowerUp p) {
    Pos pUpPos = p.getPos();
    Rectangle pUpPosRect = new Rectangle(
        pUpPos.getCorner(0),
        pUpPos.getCorner(1),
        25,
        20);
    return pos.intersects(pUpPosRect);
  }

  // Method to check if the player is within interacting range of the garden
  public static boolean nearGarden(Pos gardenPos) {
    Rectangle gardenPosRect = new Rectangle(
        gardenPos.getCorner(0),
        gardenPos.getCorner(1),
        236,
        144);
    return pos.intersects(gardenPosRect);
  }
}
