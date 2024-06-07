import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class BlueGhost extends Ghost{
    private static final Image BLUE_GHOST = new Image("res/ghostBlue.png");
    private static final double SPEED = 2;
    private final Point startPoint;

    private boolean isMovingDown = true;
    private Point prevPoint;

    /**
     * generate blueGhost with position given
     */
    public BlueGhost(Point position){
        super(position);
        startPoint = super.getPosition();
        prevPoint = super.getPosition();
    }

    public void draw(){
        if(super.isFrenzy() && isActive()){
            FRENZY_IMAGE.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }else if(isActive()){
            BLUE_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(super.getPosition(), BLUE_GHOST.getWidth(), BLUE_GHOST.getHeight());
    }

    public void move(){
        double posX = super.getPosition().x;
        double posY = super.getPosition().y;
        prevPoint = super.getPosition();

        if(isMovingDown) {
            super.setPosition(new Point(posX, posY + getSpeed()));
        }else{
            super.setPosition(new Point(posX, posY - getSpeed()));
        }
    }

    public void changeDirection(){
        isMovingDown = !isMovingDown;
    }
    private double getSpeed(){
        if(super.isFrenzy()){
            return SPEED + FRENZY_SPEED;
        }else{
            return SPEED;
        }
    }

    public void resetPosition(){
        super.setPosition(startPoint);
    }

    public void moveBack(){
        super.setPosition(prevPoint);
    }
}
