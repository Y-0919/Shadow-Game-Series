import bagel.util.*;
import bagel.*;
public class Pellet extends GameObject {
    private static final Image PELLET = new Image("res/pellet.png");
    private boolean isActive = true;

    /**
     * return the active state
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * set the active state
     */
    public void setActive(boolean bool) {
        isActive = bool;
    }

    /**
     * create a pellet in given position
     */
    public Pellet(Point position){
        super(position);
    }

    public void draw(){
        if(isActive) {
            PELLET.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(super.getPosition(), PELLET.getWidth(), PELLET.getHeight());
    }
}
