//A RareGet is a special kind of Get that spawns more infrequently than the regular Get
//When consumed, RareGets restores the Player's HP in addition to awarding points
//Otherwise, behaves the same as a regular Get
public class RareGet extends Get{
	private static final int GET_SCROLL_SPEED = 5;
	private static final int GET_POINT_VALUE = 20;
	
    //Location of image file to be drawn for a RareGet
    private static final String RAREGET_IMAGE_FILE = "assets/health.gif";

    public RareGet(){
        this(0, 0);        
    }
    
    public RareGet(int x, int y){
        super(x, y, RAREGET_IMAGE_FILE);  
    }
    
    //Move the Get left by its scroll speed
    public void scroll(){
        super.scroll();
    }
    
        
    //Colliding with a Get increases the player's score by the specified amount
    public int getPointsValue(){
        return 0;
    }
    
    public int getPointsValueProjectile(){
        return 0;
    }
    
    //Colliding with a RareGet does not affect the player's HP
    public int getDamageValue(){
    	return 1;
    }
    
    //Colliding with a RareGet does not affect the player's HP
    public int getDamageValueProjectile(){
    	return 0;
    }

}
