import bagel.util.*;
import bagel.*;
public abstract class Ghost extends GameObject implements Movable{

    /**
     * the image for ghosts in frenzy mode
     */
    protected static final Image FRENZY_IMAGE = new Image("res/ghostFrenzy.png");

    /**
     * The effect on speed for all ghosts
     */
    protected static final double FRENZY_SPEED = -0.5;

    /**
     * The point of eating a ghost
     */
    protected final static int VALUE = 30;

    private boolean isFrenzy = false;
    private boolean isActive = true;

    /**
     * return the active state
     */
    public boolean isActive(){
        return isActive;
    }

    /**
     * set the active state of ghost
     */
    public void setActive(boolean bool){
        isActive = bool;
    }


    /**
     * Constructor with position
     */
    public Ghost(Point position) {
        super(position);
    }

    /**
     * Check if it is in frenzy mode
     */
    public boolean isFrenzy(){
        return isFrenzy;
    }

    /**
     * Set frenzy mode to true or false
     */
    public void setFrenzy(boolean bool){
        isFrenzy = bool;
    }

    /**
     * return the value of ghosts
     */
    public static int getValue(){
        return VALUE;
    }

    /**
     * move to next position of the default path
     */
    abstract public void move();

    /**
     * change the direction of moving
     */
    abstract public void changeDirection();

    /**
     * reset to the starting position
     */
    abstract public void resetPosition();

    /**
     * move back to the previous position
     */
    abstract public void moveBack();
}
