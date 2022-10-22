import java.awt.*;
import java.awt.event.*;
import java.util.*;

//A Basic version of the scrolling game, featuring Avoids, Gets, and RareGets
//Players must reach a score threshold to win
//If player runs out of HP (via too many Avoid collisions) they lose
public class BasicGame extends GhasemiGame {
           
    //Dimensions of game window
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 600;  
    
    //Starting Player coordinates
    private static final int STARTING_PLAYER_X = 0;
    private static final int STARTING_PLAYER_Y = 100;
    
    //Score needed to win the game

    private static final int SCORE_TO_WIN = 300;
    
    //Maximum that the game speed can be increased to
    //(a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    private static final int MAX_GAME_SPEED = 300;
    //Interval that the speed changes when pressing speed up/down keys
    private static final int SPEED_CHANGE = 20;    
 
    
	private static final String INTRO_SPLASH_FILE = "assets/SplashIntro.png";        

    //Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;
   
    // Key pressed to advance past splash screen
    public static final int SPACE_KEY = KeyEvent.VK_SPACE;

    // Background of game
    private static final String BACKGROUND_IMAGE_FILE = "assets/space.png";
    
    // Image of projectile projectile
    private static final String SHOOT_IMAGE_FILE = "assets/projectile.png";
    
    //Interval that Entities get spawned in the game window
    //ie: once every how many ticks does the game attempt to spawn new Entities
    private static final int SPAWN_INTERVAL = 45;
    //Maximum Entities that can be spawned on a single call to spawnEntities
    private static final int MAX_SPAWNS = 3;
    
    //A Random object for all your random number generation needs!
    public static final Random rand = new Random();

    //Location of possible image files to be drawn for a Get
	public static final String[] GET_IMAGE_FILES = {"assets/boy.png", "assets/boy2.png", "assets/boy3.png", "assets/boy4.png", "assets/boy5.png", "assets/boy6.png", "assets/boy7.png"};
    
    //Player's current score
    private int score;
    
    // Entity Selection List 
	private static final char[] ENTITY_SIGNAL = {'a', 'a', 'a', 'a', 'a', 'g', 'g', 'r'};
	
	// Boundary Gap between player and game dimensions
	private static final int gap = 5;
	
	// Interval between player projectile shots
	public int countdown;
	
    //Stores a reference to game's Player object for quick reference
    //(This Player will also be in the displayList)
    private Player player;
    
    private Boss boss;
   
    // This projectile will be used in the displayList
    private Shoot projectile = new Shoot();
    
    // indicate if game is won or lost
    private boolean gameState;
        
    private int bossTime = 240;
    
    public BasicGame(){
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public BasicGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }
    
    //Performs all of the initialization operations that need to be done before the game starts
    protected void preGame(){
    	super.setSplashImage(INTRO_SPLASH_FILE);
        setBackgroundColor(Color.BLACK);
        gameMusic();
		setBackgroundImage(BACKGROUND_IMAGE_FILE);
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(player); 
        score = 0;
         
        setTitleText("Super Scrolling Game!");
    }
    
    //Called on each game tick
    protected void updateGame(){
    	if (countdown > 0){
			this.countdown--;
		}
        //scroll all scrollable Entities on the game board
        scrollEntities();
        //Spawn new entities only at a certain interval
        if (ticksElapsed % SPAWN_INTERVAL == 0)
			spawnEntities();
        //Update the title text on the top of the window
        setTitleText("HP: " + player.getHP() + " Score: " + score);
              
        if (super.checkCollision(player) != null) {
        	Consumable playerCollidedWith = (Consumable) super.checkCollision(player);
        	handleCollision(playerCollidedWith);
        }
        
        if ((super.checkCollision(projectile) != null) && (super.checkCollision(projectile) != player)){
        	Consumable projectileCollidedWith = (Consumable) super.checkCollision(projectile);
        	projectileHandleCollision(projectileCollidedWith, projectile);
        }
      
        // lose state
        if ((player.getHP() == 0) && (score < SCORE_TO_WIN)){
        	gameStopSound();
        	super.pauseGameOnSplashScreen = !pauseGameOnSplashScreen;
        	super.isPaused = !isPaused;
        	gameState = false;
        	this.postGame();
        }
        // win state
        if ((player.getHP() != 0) && score == SCORE_TO_WIN){
			gameStopSound();
        	super.pauseGameOnSplashScreen = !pauseGameOnSplashScreen;
        	super.isPaused = !isPaused;
        	gameState = true;
        	this.postGame();
        } 
    }
	
    
    //Scroll all scrollable entities per their respective scroll speeds
    protected void scrollEntities(){
        for (int i = 0; i < this.displayList.size(); i++){
           if (displayList.get(i) != player){
           	   if (displayList.get(i) instanceof Avoid){
           	   	   Entity newRedAvoid = displayList.get(i);
           	   	   Avoid a = ((Avoid) newRedAvoid);
           	   	   a.scroll();  
           	   }
           	   else if (displayList.get(i) instanceof Get){
           	   	   Entity green = displayList.get(i);
           	   	   Get g = ((Get) green);
           	   	   g.scroll();
           	   }
           	   else if (displayList.get(i) instanceof RareGet){
           	   	   Entity specialGreen = displayList.get(i);
           	   	   RareGet r = ((RareGet) specialGreen);
           	   	   r.scroll();
           	   }
           	   else if (displayList.get(i) instanceof Shoot){
           	   	   Entity projectile = displayList.get(i);
           	   	   Shoot e = ((Shoot) projectile);
           	   	   e.scroll();
           	   	   }
           	   else if (displayList.get(i) instanceof Boss){
           	   	   Entity boss = displayList.get(i);
           	   	   Boss b = ((Boss) boss);
           	   	   b.scroll();
           	   }
           }
            //How do you know what Entities to scroll?
            //finish me!
        }
    }
 
    
    public int generateHeight(){ // Helper function which returns a random height
    	int randomHeight = rand.nextInt(super.getWindowHeight());
    	return randomHeight;
    }
    
    public int generateSpawnAmount() {
    	int randomSpawn = rand.nextInt(MAX_SPAWNS + 1);
    	return randomSpawn;
    }
    
    //Spawn new Entities on the right edge of the game board
    protected void spawnEntities(){
		int spawnAmount = generateSpawnAmount();
    	int xMax = getWindowWidth(); // maximum game window width
    	int yMax = getWindowHeight(); // maximum game window height
    	int y = generateHeight(); // generated height	
    	
    	if ((player.getHP() > 0) && (score >= bossTime)){
         	Entity boss = new Boss(xMax, y);
         	while ((super.checkCollision(boss) != null) || (y > (yMax - boss.getHeight()))){
    			y = generateHeight();
    			boss.setY(y);
    		}
			this.displayList.add(boss);
    	}
    	
    	for (int i= 0; i< spawnAmount; i++){
    		int randomItem = rand.nextInt(ENTITY_SIGNAL.length); // generates a random number
    		char entitySelected = ENTITY_SIGNAL[randomItem]; // generates a random entity
    		
    		if (entitySelected == ('a')){
    			Entity redAvoid = new Avoid(xMax, y);
    			while ((super.checkCollision(redAvoid) != null) || (y > (yMax - redAvoid.getHeight()))){
    				y = generateHeight();
    				redAvoid.setY(y);
    			}
				this.displayList.add(redAvoid); 
    		}
    		
    		else if (entitySelected == ('g')){
    			String imageFile = getRandomImage(GET_IMAGE_FILES);
    			Entity green = new Get(xMax, y, imageFile);
    			while ((super.checkCollision(green) != null) || (y > (yMax - green.getHeight()))){
    				y = generateHeight();
					green.setY(y);    			
    			}
    			this.displayList.add(green); 
    		}
    		
    		else {
    			Entity specialGreen = new RareGet(xMax, y);
    			while ((super.checkCollision(specialGreen) != null) || (y > (yMax - specialGreen.getHeight()))){
    				y = generateHeight();
    				specialGreen.setY(y);
    			}
    			this.displayList.add(specialGreen); 
    		}
    	}
    }
    
    //Called whenever it has been determined that the Player collided with a consumable
    protected void handleCollision(Consumable collidedWith){
    	if (collidedWith instanceof Boss){
    		gameStopSound();
        	super.pauseGameOnSplashScreen = !pauseGameOnSplashScreen;
        	super.isPaused = !isPaused;
        	gameState = false;
        	this.postGame();
    	}
    	else {
    		int newHealth = collidedWith.getDamageValue();
    		player.setHP(player.getHP() + newHealth);
    	
    		this.score += collidedWith.getPointsValue();
    		displayList.remove(collidedWith);
    		// throw new IllegalStateException("Hey 102 Student! You need to implement handleCollision in BasicGame!"); 
    	}
    }
    
    protected void projectileHandleCollision(Consumable collidedWith, Entity projectile){   	
    	
    	int newHealth = collidedWith.getDamageValueProjectile();
    	player.setHP(player.getHP() + newHealth);
    	
    	this.score += collidedWith.getPointsValueProjectile();
		displayList.remove(collidedWith);

        // throw new IllegalStateException("Hey 102 Student! You need to implement handleCollision in BasicGame!"); 
    }

    //Called once the game is over, performs any end-of-game operations
    protected void postGame(){
    	if ((score == 300) && (player.getHP() != 0)) {
    		super.setTitleText("Game is over! You Won!");
    	}
		else {
			super.setTitleText("Game is over! You Die!");
	    }
	    gameStateClip(gameState);
	}
    
    //Determines if the game is over or not
    //Game can be over due to either a win or lose state
    protected boolean isGameOver(){
        return false;
    }
     
    //Reacts to a single key press on the keyboard
    //Override's AbstractGame's handleKey
    protected void handleKey(int key){
        //first, call AbstractGame's handleKey to deal with any of the 
        //fundamental key press operations
        super.handleKey(key);
        setDebugText("Key Pressed!: " + KeyEvent.getKeyText(key));
        //if a splash screen is up, only react to the advance splash key

        if (getSplashImage() != null){
            if (key == ADVANCE_SPLASH_KEY) {
                super.setSplashImage(null);
            return;
            }
        }
        else {
        	if (key == KEY_PAUSE_GAME){
        		super.isPaused = !isPaused;
        		super.pauseGameOnSplashScreen = !pauseGameOnSplashScreen;
        	}
        	if (key == KEY_QUIT_GAME){
        		this.isGameOver();
        	}
        	if (!isPaused){
				int speed = player.getMovementSpeed();
            	if (key == UP_KEY){
            		if ((player.getY() >= gap)){
            			player.setY(player.getY()-speed);
            		}
            	}
            	else if (key == DOWN_KEY){
            		if ((player.getY() + gap < (super.getWindowHeight()-player.getHeight()))){
            			player.setY(player.getY()+speed);
            			
            		}
            	}
            	else if (key == RIGHT_KEY){
            		if ((player.getX() >= 0) && (player.getX() < ((DEFAULT_WIDTH - player.getWidth() - (2*gap))))){
            			player.setX(player.getX()+speed);
            		}
            	}
            	else if (key == LEFT_KEY){
            		if ((player.getX() > 0) && (player.getX() <= DEFAULT_WIDTH)){
            			player.setX(player.getX()-speed);
            		}
            	}
            	else if (key == SPEED_DOWN_KEY){		
            		if ((super.getGameSpeed() < MAX_GAME_SPEED)){
            			if (super.getGameSpeed() - SPEED_CHANGE > 0){
            				super.setGameSpeed(super.getGameSpeed() - SPEED_CHANGE);
            			}
            		}
            	}
            	else if (key == SPEED_UP_KEY){
            		if (super.getGameSpeed() < MAX_GAME_SPEED){
            			super.setGameSpeed(super.getGameSpeed() + SPEED_CHANGE);
            		}
            	}
            	else if (key == SPACE_KEY && (countdown == 0)){
            		projectileStartSound();
            		countdown = 50;
            		int x = player.getX() + player.getWidth();
            		int y = player.getY() + (player.getWidth()/3);
            		projectile = new Shoot(x, y, SHOOT_IMAGE_FILE);
            		super.displayList.add(1, projectile);
            		this.scrollEntities();
            		
            	}
            }
        }
    }
}