import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;

public class PinkGhost extends Ghost{
    private static final Image PINK_GHOST = new Image("res/ghostPink.png");
    private final static double SPEED = 3;
    private final Random rand = new Random();
    private final Point startPoint;
    private Point prevPoint;

    // For convenience, let 0 represent right, 1 represent left, 2 represent down, 3 represent up
    private int direction = rand.nextInt(4);

    /**
     * create a pink ghost in given position
     */
    public PinkGhost(Point position){
        super(position);
        startPoint = super.getPosition();
        prevPoint = super.getPosition();
    }

    public void draw() {
        if(super.isFrenzy() && isActive()){
            FRENZY_IMAGE.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }else if(isActive()){
            PINK_GHOST.drawFromTopLeft(super.getPosition().x, super.getPosition().y);
        }
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(super.getPosition(), PINK_GHOST.getWidth(), PINK_GHOST.getHeight());
    }

    public void move(){
        double xPos = super.getPosition().x;
        double yPos = super.getPosition().y;
        prevPoint = super.getPosition();

        // moving right
        if(direction==0){
            super.setPosition(new Point(xPos + getSpeed(), yPos));
        // moving left
        }else if(direction==1){
            super.setPosition(new Point(xPos - getSpeed(), yPos));
        // moving down
        }else if(direction==2){
            super.setPosition(new Point(xPos, yPos + getSpeed()));
        //moving up
        }else if(direction==3){
            super.setPosition(new Point(xPos, yPos - getSpeed()));
        }
    }
    public void changeDirection(){
        int curDirection = direction;
        while(curDirection==direction){
            direction = rand.nextInt(4);
        }

    }
    public void resetPosition(){
        super.setPosition(startPoint);
    }

    private double getSpeed(){
        if(super.isFrenzy()){
            return SPEED + FRENZY_SPEED;
        }else{
            return SPEED;
        }
    }

    public void moveBack(){
        super.setPosition(prevPoint);
    }
}
