import bagel.*;
import bagel.util.*;

public class Wall extends GameObject {
    private static final Image WALL = new Image("res/wall.png");

    /**
     * create a wall in given position
     */
    public Wall(Point position){
        super(position);
    }

    public void draw(){
        WALL.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(super.getPosition(), WALL.getWidth(), WALL.getHeight());
    }
}
