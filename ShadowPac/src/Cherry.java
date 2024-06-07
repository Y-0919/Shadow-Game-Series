import bagel.Image;
import bagel.util.*;

public class Cherry extends GameObject {
    private static final Image CHERRY = new Image("res/Cherry.png");
    private static final int SCORE = 20;

    private boolean isActive = true;

    /**
     * create cherry in given position
     */
    public Cherry(Point position){
        super(position);
    }

    public void draw(){
        if(isActive) {
            CHERRY.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }
    }

    /**
     * return whether this cherry is active
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
     * return the score(value) of cherry
     */
    public static int getScore(){
        return SCORE;
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(super.getPosition(), CHERRY.getWidth(), CHERRY.getHeight());
    }
}
