import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;

public class GreenGhost extends Ghost{
    private final Image GREEN_GHOST = new Image("res/ghostGreen.png");
    private static final double SPEED = 4;
    private final Point startPoint;

    private final Random rand = new Random();
    private Point prevPoint;

    // since only 2 direction to choose from (vertical or horizontal)
    //  0 is horizontal and 1 is vertical
    private final int direction = rand.nextInt(2);
    private boolean reverseDirection = false;

    /**
     * create green ghost in given position
     */
    public GreenGhost(Point position){
        super(position);
        startPoint = super.getPosition();
        prevPoint = super.getPosition();
    }

    public void draw(){
        if(super.isFrenzy() && isActive()){
            FRENZY_IMAGE.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }else if(isActive()){
            GREEN_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(super.getPosition(), GREEN_GHOST.getWidth(), GREEN_GHOST.getHeight());
    }

    public void move(){
        double xPos = super.getPosition().x;
        double yPos = super.getPosition().y;
        prevPoint = super.getPosition();

        // moving horizontally
        if(direction == 0){
            if(!reverseDirection){
                super.setPosition(new Point(xPos + getSpeed(), yPos));
            } else {
                super.setPosition(new Point(xPos - getSpeed(), yPos));
            }
        // moving vertically
        }else if(direction == 1){
            if(!reverseDirection){
                super.setPosition(new Point(xPos, yPos + getSpeed()));
            } else {
                super.setPosition(new Point(xPos, yPos - getSpeed()));
            }
        }
    }

    public void changeDirection(){
        if(reverseDirection){
            reverseDirection = false;
        }else{
            reverseDirection = true;
        }
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
