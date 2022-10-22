public class Shoot extends Entity {
	
    //Dimensions of the Shoot  
    private static final int GET_WIDTH = 35;
    private static final int GET_HEIGHT = 35;

	private static final int GET_SCROLL_SPEED = 5;
	private static final int GET_POINT_VALUE = 20;
	//Speed that the avoid moves each time the game scrolls
    private static final int SHOOT_SCROLL_SPEED = 20;
	private static final String imageFileName = "assets/projectile.png";
	
    public boolean projectileCollision;

    public Shoot(){
    	this(0, 0, imageFileName);
    }
    
    public Shoot(int x, int y, String imageFileName){
        super(x, y, GET_WIDTH, GET_HEIGHT, imageFileName);
        if ((x!=0) && (y!=0)){
        	this.projectileCollision = true;
        }

    }
    
    public int getScrollSpeed(){
        return SHOOT_SCROLL_SPEED;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        setX(getX() + SHOOT_SCROLL_SPEED);
    }
    
}
    