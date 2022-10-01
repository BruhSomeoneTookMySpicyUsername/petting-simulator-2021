
/**
 * Authors: Gerald Wang, Parker Rho, Anuj Jain
 * Purpose: used to spawn a random dog in a random spot on the map
 * Date: 5/31/21
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PetList {

  private static ImageIcon scruffyGif, fluffyGif, puffyGif;
  private static ImageIcon scruffy, fluffy, puffy;
  private static Pos gardenPos, defaultPos;
  private static ArrayList<Pos> posList;
  private static ArrayList<ImageIcon> dogList;
  private static ArrayList<ImageIcon> gifList;
  private static int currentDog;
  private static BufferedImage map;

  public PetList() {
    defaultPos = new Pos(0, 0, 0, 0);

    // Creates an ArrayList of the spawn locations
    posList = new ArrayList<Pos>();
    posList.add(defaultPos);
    posList.add(defaultPos);
    posList.add(defaultPos);

    // The images for the three possible dogs
    scruffy = new ImageIcon("images/Scruffy.png");
    fluffy = new ImageIcon("images/Fluffy.png");
    puffy = new ImageIcon("images/Puffy.png");

    // The moving gifs of the dogs
    scruffyGif = new ImageIcon("images/ScruffyGif.gif");

    fluffyGif = new ImageIcon("images/FluffyGif.gif");

    puffyGif = new ImageIcon("images/PuffyGif.gif");

    // Creates an ArrayList of the unspawned dogs
    dogList = new ArrayList<ImageIcon>();
    dogList.add(scruffy);
    dogList.add(fluffy);
    dogList.add(puffy);

    // Creates an ArrayList of the gifs of the unspawned dogs
    gifList = new ArrayList<ImageIcon>();
    gifList.add(scruffyGif);
    gifList.add(fluffyGif);
    gifList.add(puffyGif);

    // Number to track what the current dog is
    currentDog = -1;

    // The walls of the map for collision detection
    try {
      map = ImageIO.read(new File("images/Map.png"));
    } catch (IOException e) {
    }

    // The position of the garden
    gardenPos = new Pos(110, 820, 90, 60);
  }

  // Randomly decides the spawn point for a chosen dog and removes it from the
  // unused positions ArrayList
  public static Pos chooseSpot(int dog) {
    int x = (int) (Math.random() * 910);
    int y = (int) (Math.random() * 940);
    Pos dogPos = new Pos(x, y, 90, 60);
    while ((dogPos.collisionCheck(map) || dogPos.nearDog(gardenPos)) ||
        dogPos.dogList(posList) ||
        (dogPos.getCorner(3) > 610 && dogPos.getCorner(0) < 310)) {
      x = (int) (Math.random() * 910);
      y = (int) (Math.random() * 940);
      dogPos.setCorner(x, y);
    }
    posList.set(dog, dogPos);
    return dogPos;
  }

  // Randomly decides the spawn point for a power up
  public static Pos powerChooseSpot() {
    int x = (int) (Math.random() * 910);
    int y = (int) (Math.random() * 940);
    Pos pwrUpPos = new Pos(x, y, 90, 60);
    while (pwrUpPos.collisionCheck(map) ||
        (pwrUpPos.getCorner(3) > 610 && pwrUpPos.getCorner(0) < 310) ||
        pwrUpPos.dogList(posList)) {
      x = (int) (Math.random() * 910);
      y = (int) (Math.random() * 940);
      pwrUpPos.setCorner(x, y);
    }
    return pwrUpPos;
  }

  // Randomly chooses one of the dogs to spawn and removes it from the unspawned
  // images ArrayList
  public static ImageIcon chooseDog() {
    int doggie = (int) (Math.random() * dogList.size());

    // Updates accordingly to the current dog that was spawned
    currentDog = doggie;
    return dogList.remove(doggie);
  }

  // Returns the moving gif of the current dog
  public static ImageIcon getGif() {
    return gifList.remove(currentDog);
  }
}
