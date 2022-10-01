
/**
 * Authors: Gerald Wang, Parker Rho, Anuj Jain
 * Purpose: implements all features of the game in the GameFrame.
 * Date: 5/11/21
 */

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

  private static int score, hiScore;
  private int blocX, blocY, changeX, changeY;
  private Timer time = new Timer(5, this);
  private Character sprite;
  private Image background;
  private BufferedImage map;
  private Pet pet1, pet2, pet3;
  private Pos gardenPos;
  private ArrayList<Pet> currentPets;
  private ArrayList<PowerUp> powerUps;
  private Image garden;
  private PettingWindow petWin;
  private int speedIncrease = 0;
  private static boolean collectedTimePwrUp = false;
  private Clip audioClip;
  private PetList pets;
  private Font font;
  private Color color;

  public GamePanel() {
    // Instantiates all background operators (i.e. timers, key listeners) so that
    // the game is interactable
    time.start();
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);

    // Change variables so that the player's position can update
    changeX = 0;
    changeY = 0;

    // Spawn point coordinates (these variables will update to the player's current
    // position throughout the game)
    blocX = 20;
    blocY = 140;

    // Declares the character object
    sprite = new Character(blocX, blocY);

    // Creates the setting - map, background color, obstacles
    background = new ImageIcon("images/Background.png").getImage();
    try {
      map = ImageIO.read(new File("images/Map.png"));
    } catch (IOException e) {
    }
    color = new Color(201, 255, 91);

    // Creates a new PetList object for every time the game is replayed
    pets = new PetList();

    // The first pet
    pet1 = new Pet(0, pets);

    // The second pet
    pet2 = new Pet(1, pets);

    // The third pet
    pet3 = new Pet(2, pets);

    // Creates an ArrayList of Pet objects which we traverse to check when the
    // player is near a dog
    currentPets = new ArrayList<Pet>();
    currentPets.add(pet1);
    currentPets.add(pet2);
    currentPets.add(pet3);

    // Creates an ArrayList of PowerUp objects to keep track of all the PowerUp
    // objects that have spawned
    powerUps = new ArrayList<PowerUp>();

    // Sets up the garden (instantiates image and position)
    garden = new ImageIcon("images/Garden.png").getImage();
    gardenPos = new Pos(27, 856, 236, 144);

    // The score is set to zero
    this.setScore(0);

    // Instantiates the font
    font = new Font("Arial", Font.PLAIN, 20);

    // Instantiates the audio file for the background music
    File audioFile = new File("sounds/puppy.wav");
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

      AudioFormat format = audioStream.getFormat();

      DataLine.Info info = new DataLine.Info(Clip.class, format);

      audioClip = (Clip) AudioSystem.getLine(info);

      audioClip.open(audioStream);
      audioClip.loop(-1);
      audioClip.start();
    } catch (UnsupportedAudioFileException ex) {
      System.out.println("The specified audio file is not supported.");
      ex.printStackTrace();
    } catch (LineUnavailableException ex) {
      System.out.println("Audio line for playing back is unavailable.");
      ex.printStackTrace();
    } catch (IOException ex) {
      System.out.println("Error playing the audio file.");
      ex.printStackTrace();
    }
  }

  // The main part of the class where everything in the game is drawn
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draws the map and background
    g.drawImage(background, 0, 0, null);
    g.drawImage(map, 0, 0, null);
    g.setColor(color);

    // Draws the garden
    g.drawImage(garden, gardenPos.getCorner(0), gardenPos.getCorner(1), null);

    // Sets the font to Arial and draws the current score in the top left corner
    g.setFont(font);
    g.setColor(Color.BLACK);
    g.drawString("Score: " + score, 20, 20);
    g.drawString("Highscore: " + hiScore, 20, 40);

    // Draws the pets in their spawn locations
    // If the player can interact with the pet, it will become a gif of the pet
    // moving
    if (Character.nearDog(pet1)) {
      g.drawImage(
          pet1.getGif(),
          pet1.getPos().getCorner(0),
          pet1.getPos().getCorner(1),
          null);
    } else {
      g.drawImage(
          pet1.getDog(),
          pet1.getPos().getCorner(0),
          pet1.getPos().getCorner(1),
          null);
    }

    if (Character.nearDog(pet2)) {
      g.drawImage(
          pet2.getGif(),
          pet2.getPos().getCorner(0),
          pet2.getPos().getCorner(1),
          null);
    } else {
      g.drawImage(
          pet2.getDog(),
          pet2.getPos().getCorner(0),
          pet2.getPos().getCorner(1),
          null);
    }

    if (Character.nearDog(pet3)) {
      g.drawImage(
          pet3.getGif(),
          pet3.getPos().getCorner(0),
          pet3.getPos().getCorner(1),
          null);
    } else {
      g.drawImage(
          pet3.getDog(),
          pet3.getPos().getCorner(0),
          pet3.getPos().getCorner(1),
          null);
    }

    // Draws all of the PowerUp objects that are currently spawned
    if (powerUps.size() > 0) {
      for (PowerUp p : powerUps) {
        p.draw(g);
      }
    }

    // Draws the player's character
    g.drawImage(sprite.getImage(), blocX, blocY, null);
  }

  // Method that dictates how the various elements of the game will react as keys
  // are pressed
  public void actionPerformed(ActionEvent e) {
    // Updates the player's position on the map as the user moves the character
    blocX += changeX;
    blocY += changeY;

    // Makes sure that the player can't move outside the window, avoiding an
    // IndexOutOfBounds exception
    if (blocX < 0) {
      blocX = 0;
    }
    if (blocX > 910) {
      blocX = 910;
    }
    if (blocY < 0) {
      blocY = 0;
    }
    if (blocY > 940) {
      blocY = 940;
    }

    // Updates the player's position for the character class so that we can track
    // where it is on the map
    sprite.setCorner(blocX, blocY);

    // Stops the player from being able to move through walls
    if (Character.collisionCheck(map)) {
      blocX -= changeX;
      blocY -= changeY;
    }

    // When the game ends, the background music stops
    if (GameFrame.getGameEnd()) {
      audioClip.stop();
    }

    repaint();
  }

  // This is the method used to detect when certain keys are pressed and dictates
  // how the game will respond
  public void keyPressed(KeyEvent e) {
    int c = e.getKeyCode();

    // While the movement keys are pressed, the player will move accordingly (uses
    // WASD keys for movement)
    if (c == KeyEvent.VK_A) {
      // Movement is at a base speed of 5 but increases by 1 every time a speedup
      // powerup is picked up
      changeX = -5 - speedIncrease;

      // If the player moves left, the model will face to the left
      sprite.setLeft(true);
    }

    if (c == KeyEvent.VK_D) {
      changeX = 5 + speedIncrease;

      // If the player moves right, the model will face to the right
      sprite.setLeft(false);
    }

    if (c == KeyEvent.VK_W) {
      changeY = -5 - speedIncrease;
    }

    if (c == KeyEvent.VK_S) {
      changeY = 5 + speedIncrease;
    }

    // The f key is the interact button so when it is pressed and the player is near
    // the garden, a powerup, or a dog, then something will happen
    if (c == KeyEvent.VK_F) {
      // The player must have empty hands to pick up a dog
      if (sprite.getPossession() == false) {
        // The for loop traverse the ArrayList of all the dogs to check if the player is
        // near any of the dogs on the map
        for (int i = 0; i < currentPets.size(); i++) {
          // This statement checks to see if the player is near any of the dogs
          if (Character.nearDog(currentPets.get(i))) {
            // Sets the players possession of a dog to true
            sprite.hasDog(true);

            // A bark sound effect plays
            bark();

            // The pet that was picked up will now move to a different random position on
            // the map
            currentPets.get(i).changeSpot();

            // Stops the player from moving once the PettingWindow appears (stop drifting
            // once the window opens)
            changeX = 0;
            changeY = 0;

            // Opens a PettingWindow with the currently picked up dog as the button
            petWin = new PettingWindow(currentPets.get(i).getGifIcon());

            // Rolls to see if a PowerUp will spawn
            double rand = Math.random();
            if (rand < 0.5) {
              powerUps.add(new PowerUp(1));
            } else if (rand < 0.75 && rand > 0.5) {
              powerUps.add(new PowerUp(2));
            }

            // Sets i to the size of the ArrayList so that if the player is near two dogs at
            // once, only one is picked up
            i = currentPets.size();
          }
        }
        // If the player is near the garden and has a dog, then the dog is deposited in
        // the garden and increases the score by one
        // The score only increases if the player actually finishes the PettingWindow,
        // otherwise if the player just closes the window, no points are awarded since
        // they are a dirty cheater
      } else if (Character.nearGarden(gardenPos)) {
        sprite.hasDog(false);
        if (petWin.getCompleted()) {
          score++;
        }
      }

      // Checks to see if the player is near a PowerUp object and awards the effect of
      // the powerup accordingly
      for (int i = 0; i < powerUps.size(); ++i) {
        if (Character.nearPwrUp(powerUps.get(i))) {
          if (powerUps.get(i).getType() == 1) {
            speedIncrease += 1;
          }
          if (powerUps.get(i).getType() == 2) {
            collectedTimePwrUp = true;
          }
          powerUps.remove(i);
        }
      }
    }
  }

  // Method used to check if the player just picked up a time powerup
  public static boolean getCollectedTimePwrUp() {
    return collectedTimePwrUp;
  }

  // Method used to update whether the time powerup has been collected or not
  public static void setCollectedTimePwrUp(boolean b) {
    collectedTimePwrUp = b;
  }

  // Method to return the score to other classes
  public static int getScore() {
    return score;
  }

  // Sets the score
  public void setScore(int x) {
    score = x;
  }

  // Returns the highscore
  public static int getHiScore() {
    if (score > hiScore) {
      hiScore = score;
    }
    return hiScore;
  }

  // Method to play the bark sound
  public static void bark() {
    File audioFile = new File("sounds/bark.wav");
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

      AudioFormat format = audioStream.getFormat();

      DataLine.Info info = new DataLine.Info(Clip.class, format);

      Clip audioClip = (Clip) AudioSystem.getLine(info);

      audioClip.open(audioStream);
      audioClip.start();
    } catch (UnsupportedAudioFileException ex) {
      System.out.println("The specified audio file is not supported.");
      ex.printStackTrace();
    } catch (LineUnavailableException ex) {
      System.out.println("Audio line for playing back is unavailable.");
      ex.printStackTrace();
    } catch (IOException ex) {
      System.out.println("Error playing the audio file.");
      ex.printStackTrace();
    }
  }

  public void keyTyped(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
    int c = e.getKeyCode();

    // Once the movement keys are released, the player will stop moving
    if (c == KeyEvent.VK_A) {
      changeX = 0;
    }
    if (c == KeyEvent.VK_D) {
      changeX = 0;
    }
    if (c == KeyEvent.VK_W) {
      changeY = 0;
    }
    if (c == KeyEvent.VK_S) {
      changeY = 0;
    }
  }
}
