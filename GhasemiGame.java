import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public abstract class GhasemiGame extends AbstractGame {
	 //A Random object for all your random number generation needs!
    public static final Random rand = new Random();

	// Projectile Sound File
	Clip projectileClip;
	Clip gameClip;
	Clip gameLostClip;
	Clip gameWonClip;

	//Dimensions of game window
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 600;  
    
    // Instance variable of creativeGame
    private BasicGame creativeGame;
    
    // End game states
	private static final String BACKGROUND_IMAGE_FILE_WON = "assets/gamewon.gif";
	private static final String BACKGROUND_IMAGE_FILE_LOST = "assets/gamelost.gif";
    
	public GhasemiGame(){
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public GhasemiGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }	
	protected void handleKey(int key){
		super.handleKey(key);
	}
	
	// play projectile sound
	protected void projectileStartSound(){
		File projectileSound = new File("assets/sound.wav");
		try{
			projectileClip = AudioSystem.getClip();
			projectileClip.open(AudioSystem.getAudioInputStream(projectileSound));
			projectileClip.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
		
	protected void gameMusic(){
		File gameSound = new File("assets/gamemusic.wav");
		try{
			gameClip = AudioSystem.getClip();
			gameClip.open(AudioSystem.getAudioInputStream(gameSound));
			gameClip.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void gameStopSound(){
		gameClip.stop();
	}
	
	protected void gameLostMusic(){
		File gameLostSound = new File("assets/tomuraLaughShort.wav");
		try{
			gameLostClip = AudioSystem.getClip();
			gameLostClip.open(AudioSystem.getAudioInputStream(gameLostSound));
			gameLostClip.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void gameWonMusic(){
		File gameWonSound = new File("assets/dekuvictory.wav");
		try{
			gameWonClip = AudioSystem.getClip();
			gameWonClip.open(AudioSystem.getAudioInputStream(gameWonSound));
			gameWonClip.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	 //called on each "tick" (if not paused) to update the state of the game
    protected void updateGame(){
    	creativeGame.updateGame();
    }

    //called once before the game starts
    protected void preGame(){
    	creativeGame.preGame();
    }

    //called once after the game ends
    protected void postGame(){          
		creativeGame.postGame();
    }
    
    protected void gameStateClip(boolean gameState){
	    if (gameState == true){
			gameWonMusic();
	    	super.setSplashImage(BACKGROUND_IMAGE_FILE_WON);	
		}
		else{
			gameLostMusic();
			super.setSplashImage(BACKGROUND_IMAGE_FILE_LOST);	
		}
	}
    
	public String getRandomImage(String[] imageList){
    	int randomImage = rand.nextInt(imageList.length); // generates a random number
    	String getImageSelected = imageList[randomImage]; // generates a random entity
    	return getImageSelected;
    }
	
    //Returns a boolean indicating whether the game is over (true) or not (false).
    //Does not indicate if the player won or lost the game.
    protected boolean isGameOver(){
    	return creativeGame.isGameOver();
    }
}