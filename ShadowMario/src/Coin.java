import bagel.Input;
import java.util.Properties;

public class Coin extends GameObject implements Collider {
    private boolean isCollided = false;
    private final int value;

    public Coin(int x, int y, Properties props) {
        super(x, y,
              Integer.parseInt(props.getProperty("gameObjects.coin.speed")),
              Double.parseDouble(props.getProperty("gameObjects.coin.radius")),
              props.getProperty("gameObjects.coin.image"));
        this.value = Integer.parseInt(props.getProperty("gameObjects.coin.value"));
    }

    /***
     * Handle the collision with the player object.
     * If the player has double score power, the value of the coin will be doubled. Otherwise return the defaul coin
     * value.
     * @param input
     * @param target
     * @return value
     */
    public int update(Input input, Player target) {
        super.update(input);

        if (updateWithTarget(input, target)) {
            DoubleScorePower power = target.getDoubleScorePower();
            if(power != null) {
                return power.applyEffect(value);
            }
            return value;
        }

        return 0;
    }


    /***
     * Once the object collided with the player, coin object will go up
     * @param input
     * @param target
     * @return
     */
    @Override
    public boolean updateWithTarget(Input input, LiveObject target) {
        if (hasCollidedWith(target) && !isCollided) {
            isCollided = true;
            setSpeedY(-10);
            return true;
        }
        return false;
    }
}
