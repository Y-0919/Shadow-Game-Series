import bagel.util.*;
import bagel.*;
public class Dot extends GameObject {
     private static final Image DOT = new Image("res/Dot.png");
     private static final int VALUE = 10;

     private boolean isActive = true;

    /**
     * create a dot in given position
     */
    public Dot(Point position){
         super(position);
     }

    /**
     * Method to render the Dot object
     */
    public void draw(){
        if(isActive) {
            DOT.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }
    }

    /**
     * check if the dot is active (can collide with player)
     */
    public boolean isActive(){
        return isActive;
    }

    /**
     * set the active state
     */
    public void setActive(boolean bool){
        this.isActive = bool;
    }

    /**
     * return value of the dot
     */
    public int getValue(){
        return VALUE;
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(super.getPosition(), DOT.getWidth(), DOT.getHeight());
    }
}
