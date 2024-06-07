import bagel.Input;
import java.util.Properties;

import static java.lang.Double.*;

public class DoubleScorePower extends GameObject implements Power<Integer>, Collider {

    private final int maxFrames;
    private int framesActive = 0;
    private boolean isCollided = false;

    public DoubleScorePower(int x, int y, Properties props) {
        super(x, y,
              Integer.parseInt(props.getProperty("gameObjects.doubleScore.speed")),
              parseDouble(props.getProperty("gameObjects.doubleScore.radius")),
              props.getProperty("gameObjects.doubleScore.image"));
        this.maxFrames = Integer.parseInt(props.getProperty("gameObjects.doubleScore.maxFrames"));
    }

    /***
     * handle the collision between the object and player. Once collided the effect of the power will be activated.
     * Also the power object will be go up once collided.
     * See applyEffect function
     * @param input
     * @param target
     * @return
     */
    @Override
    public boolean updateWithTarget(Input input, LiveObject target) {
        super.update(input);

        if(isCollided) {
            framesActive++;
        }

        if (hasCollidedWith(target) && !isCollided) {
            isCollided = true;
            setSpeedY(-10);
            return true;
        }

        return false;
    }

    /***
     * The coin value will be doubled for a period of maxFrames.
     * @param score
     * @return
     */
    @Override
    public Integer applyEffect(Integer score) {
        if (framesActive <= maxFrames) {
            score *= 2;
        }

        return score;
    }
}
