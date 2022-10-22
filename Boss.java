public class Boss extends Entity implements Consumable, Scrollable {
     
    //Location of image file to be drawn for an Avoid
    private static final String BOSS_IMAGE_FILE = "assets/tomura.png";
    //Dimensions of the Avoid    
    private static final int BOSS_WIDTH = 75;
    private static final int BOSS_HEIGHT = 75;
    //Speed that the avoid moves each time the game scrolls
    private static final int BOSS_SCROLL_SPEED = 15;
    
    public Boss(){
        this(0, 0);        
    }
    
    public Boss(int x, int y){
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS_IMAGE_FILE);  
    }
    
    public int getScrollSpeed(){
        return BOSS_SCROLL_SPEED;
    }
    
    //Move the boss left by the scroll speed
    public void scroll(){
        setX(getX() - BOSS_SCROLL_SPEED);
    }
    
    //Colliding with an Boss does not affect the player's score
    public int getPointsValue(){
    	return 0;
    	// throw new IllegalStateException("Hey 102 Student! You need to implement getPointsValue in Avoid.java!");
    }
    
    public int getPointsValueProjectile(){
    	return 0;
    	// throw new IllegalStateException("Hey 102 Student! You need to implement getPointsValue in Avoid.java!");
    }
    
    //Colliding with a boss does affect the player's HP
    public int getDamageValue(){
        return 0;
    }
    
    public int getDamageValueProjectile(){
        return 0;
    }
}
