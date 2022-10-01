
/**
 * Authors: Gerald Wang, Parker Rho, Anuj Jain
 * Purpose: allows for a pet object to be instantiated
 * Date: 6/4/21
 */

import java.awt.Image;
import javax.swing.ImageIcon;

public class Pet {

  private Image dog, gif;
  private ImageIcon dogIcon, gifIcon;
  private Pos pos;
  private int currentPet;

  public Pet(int x, PetList pets) {
    // Chooses a random dog to spawn
    dogIcon = PetList.chooseDog();

    dog = dogIcon.getImage();

    // Chooses a random spot to spawn the dog
    pos = PetList.chooseSpot(x);

    // Returns the gif version of the dog
    gifIcon = PetList.getGif();
    gif = gifIcon.getImage();

    currentPet = x;
  }

  // Returns position
  public Pos getPos() {
    return pos;
  }

  // Returns the image of the dog
  public Image getDog() {
    return dog;
  }

  // Returns the gif of the dog
  public Image getGif() {
    return gif;
  }

  // Changes the spot of the dog once you interact with it
  public void changeSpot() {
    pos = PetList.chooseSpot(currentPet);
  }

  // Returns the ImageIcon of the dog
  public ImageIcon getIcon() {
    return dogIcon;
  }

  // Returns the GifIcon of the dog
  public ImageIcon getGifIcon() {
    return gifIcon;
  }
}
