import bagel.util.*;
import bagel.*;

public class RedGhost extends Ghost{
    private static final Image RED_GHOST = new Image("res/ghostRed.png");
    private static final double SPEED = 1;
    private final Point startPoint;
    private boolean isMovingRight = true;
    private Point prevPoint;

    /**
     * create red ghost in given position
     */
    public RedGhost(Point position) {
        super(position);
        startPoint = super.getPosition();
        prevPoint = super.getPosition();
    }

    @Override
    public void draw() {
        if(super.isFrenzy() && isActive()){
            FRENZY_IMAGE.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }else if(isActive()){
            RED_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(super.getPosition(), RED_GHOST.getWidth(), RED_GHOST.getHeight());
    }

    public void move(){
        prevPoint = super.getPosition();
        if(isMovingRight) {
            super.setPosition(new Point(super.getPosition().x + getSpeed(), super.getPosition().y));
        }else{
            super.setPosition(new Point(super.getPosition().x - getSpeed(), super.getPosition().y));
        }
    }

    private double getSpeed(){
        if(super.isFrenzy()){
            return SPEED + FRENZY_SPEED;
        }else{
            return SPEED;
        }
    }

    public void changeDirection(){
        if(isMovingRight) {
            isMovingRight = false;
        } else {
            isMovingRight = true;
        }
    }

    public void resetPosition(){
        super.setPosition(startPoint);
    }

    public void moveBack(){
        super.setPosition(prevPoint);
    }
}
