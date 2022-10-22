import java.awt.Color;
import java.awt.event.*;
import java.util.*;

//Abstract class which handles the fundamental properties and actions
//for a Scrolling game.


//************************************************************************************
//*                                                                                  *
//*                                                                                  *
//*                    YOU ARE NOT ALLOWED TO MODIFY THIS CLASS                      *
//*         (though you'll need to read, trace, and use the methods in it)           *
//*                                                                                  *
//*                                                                                  *
//************************************************************************************



public abstract class AbstractGame {


    //********        KEYBOARD KEY VALES      ********

    //The Java API stores the values for various keys on the keyboard
    //as integer values.  These values are used by the keyPress methods
    //in order to determine which key(s) the player has pressed.  The KeyEvent
    //class stores a large collection of finals for all the different keys.

    //See the following API page for more info:
    //https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html

    public static final int KEY_QUIT_GAME = KeyEvent.VK_ESCAPE;//quit the game
    public static final int KEY_PAUSE_GAME = KeyEvent.VK_P;//pause the game
    public static final int KEY_TOGGLE_DEBUG = KeyEvent.VK_D;//toggle debug mode on/off

    public static final int SPEED_DOWN_KEY = KeyEvent.VK_MINUS;//make game run slower
    public static final int SPEED_UP_KEY = KeyEvent.VK_EQUALS;//make game run faster

    public static final int UP_KEY = KeyEvent.VK_UP;//move the player up
    public static final int DOWN_KEY = KeyEvent.VK_DOWN;//.... down
    public static final int LEFT_KEY = KeyEvent.VK_LEFT;//.... left
    public static final int RIGHT_KEY = KeyEvent.VK_RIGHT;//.... right

    //A collection of all the movement keys
    public static final int[] MOVEMENT_KEYS = {UP_KEY, DOWN_KEY, LEFT_KEY, RIGHT_KEY};

    //************************************************



    //determines if the game should be paused while a splash screen is displayed.
    //if set to false, the game will continue running underneath the splash screen
    protected boolean pauseGameOnSplashScreen;
    //tracks if the game is currently paused or not.
    protected boolean isPaused;

    //Number of ticks (ie, iterations of the game loop) since the game started running
    protected int ticksElapsed;

    //the window where the game is played and the graphics are drawn
    private GameWindow window;

    //A collection of entities to be drawn on the screen
    //Whatever is in this ArrayList is draw to the game window
    //on each call to window.refresh();
    //
    //Entities are drawn in order of appearance in the ArrayList.
    //First element is drawn beneath all entities, last element ontop of all entities.
    protected ArrayList<Entity> displayList;



    //Constructor, taking the width and height of the game window (in pixels)
    public AbstractGame(int gameWidth, int gameHeight){
        initGame(gameWidth, gameHeight);
    }

    //Initializes the game and window, performed before the game launches
    private void initGame(int gameWidth, int gameHeight){
        displayList = new ArrayList<Entity>();
        //Links this.displayList to the game window by passing it at instantiation
        window = new GameWindow(gameWidth, gameHeight, displayList);
        pauseGameOnSplashScreen = true;
        isPaused = false;
        ticksElapsed = 0;
    }


    //Runs the game once, start to finish
    public void play(){
        preGame(); //perform any pre-game tasks
        runGame(); //play the game
        postGame(); //perform any post-game tasks
        while (true)
          window.refresh();
    }


    //Plays the game.
    //This is the "heart" of your Scrolling Game code.
    private void runGame(){
        //Keep repeating until the game is over, win or loses
        while(!isGameOver()){  //one iteration of this loop is considered a "tick"
            handleKeyPresses();
            handleMouseClick();
            //don't make the game update if it is in a "paused" state.
            //either due to a splash screen, or the player pausing the game.
            if (!(getSplashImage() != null && pauseGameOnSplashScreen) && !isPaused)
              updateGame();
            window.refresh(); //redraws the game window (including everything in displayList)
            ticksElapsed++; //one iteration of the game loop = 1 "tick"
        }
    }


    //Handles reacting to any keyboard keys the player has pressed on the keyboard.
    private void handleKeyPresses(){
       //Returns a collection of all the keys pressed. An ArrayList is needed as the
       //player could be pressing multiple keys simultaneously, ex: moving diagonally).
       ArrayList<Integer> keysPressed = window.getKeysPressed();
        for(Integer key : keysPressed)
            this.handleKey(key); //handle each key individually
    }


    //Handles reacting to a single key the player has pressed on the keyboard.
    //AbstractGame deals only with the most basic and general key functionalities.
    protected void handleKey(int keyCode){
       if (keyCode == KEY_QUIT_GAME)
         System.exit(0);
       else if (keyCode == KEY_TOGGLE_DEBUG)
         setDebugMode(!isDebugEnabled());
    }

    //Handles reacting to a single mouse click in the game window
    protected MouseEvent handleMouseClick(){
      MouseEvent click = window.getLastMousePress();
      if (click != null){ //ensure a mouse click occurred
        int clickX = click.getX();
        int clickY = click.getY();
      }
      return click;//returns the mouse event for any child classes overriding this method
    }


    //Returns the width of the game window, in pixels
    public int getWindowWidth(){
        return window.getWidth();
    }


    //Returns the height of the game window, in pixels
    public int getWindowHeight(){
        return window.getHeight();
    }


    //Gets the background image of the window.
    //returns the filename of the background image, or null if there is no background.
    public String getBackgroundImage(){
        return window.getBackgroundImage();
    }


    //Sets the background image of the window to the argument image.
    //Background is draw UNDERNEATH all the entities
    //Argument passed is the filename of the image file.
    public void setBackgroundImage(String imageFilename){
        window.setBackgroundImage(imageFilename);
    }

    //Sets the splashscreen image drawn in the window to the argument image.
    //Splash is drawn ON TOP of all the entities.
    //Argument passed is the filename of the image file.
    public void setSplashImage(String splashFilename){
        window.setSplashImage(splashFilename);
    }


    //Gets the splash screen image of the window.
    //returns the filename of the splash screen image, or null if there is no background.
    public String getSplashImage(){
        return window.getSplashImage();
    }



    //Gets the current background color of the game window.
    //This background color will only be visible if there is no background
    //image, or if the background image has transparency.
    public Color getBackgroundColor(Color bgColor){
        return window.getBackgroundColor();
    }

    //Sets the background color of the game window to the argument Color.
    //This background color will only be visible if there is no background
    //image, or if the background image has transparency.
    public void setBackgroundColor(Color bgColor){
        window.setBackgroundColor(bgColor);
    }


    //Checks for a collision in the game window at the argument coordinate.
    //If there's any Entity which is currently being drawn at the argument x/y
    //coordinate, this function returns that entity; null otherwise.
    protected Entity checkCollision(int x, int y){
        for (Entity e : displayList){
            if (e.isCollidingWith(x, y))
                return e;
        }
        return null;
    }


    //Checks to see if the argument entity is colliding with ANYTHING in the game window.
    //If there's any Entity in the displayList that would be colliding with the argument,
    //this function returns that entity; null otherwise.
    protected Entity checkCollision(Entity toCheck){
        for (Entity e : displayList){
            //the argument Entity won't be detected as colliding with
            //itself if it is currently in the displayList
            if (e != toCheck && e.isCollidingWith(toCheck))
                return e;
        }
        return null;
    }


    //Sets the title text to the argument String.
    //This text is drawn in the top bar of the game window.
    protected void setTitleText(String text){
      window.setTitle(text);
    }

    //Returns a boolean indicating if debug mode is currently enabled.
    public boolean isDebugEnabled(){
      return window.isDebugEnabled();
    }

    //Enables or disables debug mode.
    public void setDebugMode(boolean isDebugEnabled){
        window.setDebugMode(isDebugEnabled);
    }

    //Sets the debug text to be drawn in the lower-left hand corner of the game window.
    //This text is only visible if debug mode is enabled.
    public void setDebugText(String debugStr){
        window.setDebugText(debugStr);
    }

    //Returns the current game speed.
    //The game speed is represented as integer indicating a percentage.
    //a value of 100 indicates the game is running at 100% speed, ie the default game speed.
    //150 and 50 would indicate game running 50% faster and 50% slower, respectively.
    public int getGameSpeed(){
        return window.getGameSpeed();
    }


    //Sets the current game speed to the argument percentage.
    //The game speed is represented as integer indicating a percentage.
    //a value of 100 indicates the game is running at 100% speed, ie the default game speed.
    //150 and 50 would indicate game running 50% faster and 50% slower, respectively.
    public void setGameSpeed(int newGameSpeed){
        if (newGameSpeed <= 0)
            throw new IllegalStateException("ERROR! Game speed set to invalid value: " + newGameSpeed);
        window.setGameSpeed(newGameSpeed);
    }


    //******** Abstract methods to be implemented by child classes ********

    //called on each "tick" (if not paused) to update the state of the game
    protected abstract void updateGame();

    //called once before the game starts
    protected abstract void preGame();

    //called once after the game ends
    protected abstract void postGame();

    //Returns a boolean indicating whether the game is over (true) or not (false).
    //Does not indicate if the player won or lost the game.
    protected abstract boolean isGameOver();

    //*********************************************************************

}