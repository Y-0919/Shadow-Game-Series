import bagel.Input;
import java.util.Properties;

public class EndFlag extends GameObject implements Collider {
    private boolean isCollided = false;

    public EndFlag(int x, int y, Properties props) {
        super(x, y,
             Integer.parseInt(props.getProperty("gameObjects.endFlag.speed")),
             Double.parseDouble(props.getProperty("gameObjects.endFlag.radius")),
             props.getProperty("gameObjects.endFlag.image"));
    }

    public void update(Input input, Player target) {
        super.update(input);
        updateWithTarget(input, target);
    }

    @Override
    public boolean updateWithTarget(Input input, LiveObject target) {
        if (hasCollidedWith(target) && !isCollided) {
            isCollided = true;
            return true;
        }
        return false;
    }

    public boolean isCollided() {
        return isCollided;
    }
}
