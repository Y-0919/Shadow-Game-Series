import bagel.util.*;

public abstract class GameObject {
    private Point position;

    /**
     * create a game object in given position
     */
    public GameObject(Point position){
        this.position = position;
    }

    /**
     * get the current position
     */
    public Point getPosition(){
        return this.position;
    }

    /**
     * move the object to the given position
     */
    public void setPosition(Point position){
        this.position = position;
    }

    /**
     * get the bounding box to check for collision
     */
    abstract public Rectangle getBoundingBox();

    /**
     * draw the object in current position
     */
    abstract public void draw();
}
