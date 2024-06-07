import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Platform extends GameObject {

    public Platform(int x, int y, Properties props) {
        super(x, y,
              Integer.parseInt(props.getProperty("gameObjects.platform.speed")),
              props.getProperty("gameObjects.platform.image"));
    }
}
