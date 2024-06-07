import bagel.Input;

import java.util.Properties;

public class InvinciblePower  extends GameObject implements Power<Double>, Collider {
    private final int maxFrames;
    private int framesActive = 0;
    private boolean isCollided = false;

    public InvinciblePower(int x, int y, Properties props) {
        super(x, y,
              Integer.parseInt(props.getProperty("gameObjects.invinciblePower.speed")),
              Double.parseDouble(props.getProperty("gameObjects.invinciblePower.radius")),
              props.getProperty("gameObjects.invinciblePower.image"));
        this.maxFrames = Integer.parseInt(props.getProperty("gameObjects.invinciblePower.maxFrames"));
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
     * Damage will be 0 for a period of maxFrames.
     * @param damageSize
     * @return
     */
    @Override
    public Double applyEffect(Double damageSize) {
        if (framesActive <= maxFrames) {
            return 0.0;
        }

        return damageSize;
    }
}
