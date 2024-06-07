import java.util.Properties;
import java.util.Random;

public class FlyingPlatform extends GameObject implements IndependentMover {

    private final int halfLength;
    private final int halfHeight;
    private final int maxRandomDisplacementX;
    private int randomDisplacementX;
    private int randomDisplacementSpeedX;

    public FlyingPlatform(int x, int y, Properties props) {
        super(x, y,
              Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.speed")),
              props.getProperty("gameObjects.flyingPlatform.image"));

        this.halfHeight = Integer.parseInt(
                props.getProperty("gameObjects.flyingPlatform.halfHeight"));;
        this.halfLength = Integer.parseInt(
                props.getProperty("gameObjects.flyingPlatform.halfLength"));;

        this.maxRandomDisplacementX = Integer.parseInt(
                props.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
        this.randomDisplacementX = x;
        randomDisplacementSpeedX = new Random().nextBoolean() ? 1 : -1;
    }

    public int getHalfLength() {
        return halfLength;
    }

    public int getHalfHeight() {
        return halfHeight;
    }

    @Override
    public void move() {
        independentMove();
        super.move();
    }

    /***
     * Add a random movement on x direction
     */
    @Override
    public void independentMove() {
        setX(getX() + randomDisplacementSpeedX);
        randomDisplacementX += randomDisplacementSpeedX;
        if (Math.abs(getInitialX() - randomDisplacementX) > maxRandomDisplacementX) {
            randomDisplacementSpeedX *= -1;
        }
    }
}
