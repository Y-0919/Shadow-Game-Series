import java.util.Collection;
import java.util.Random;

public class Enemy extends GameObject {
    private static final String IMAGE_LOCATION = "res/enemy.png";
    private static final int MIN_X = 100;
    private static final int MAX_X = 900;
    private static final int MIN_Y = 100;
    private static final int MAX_Y = 500;
    private static final int SPEED = 1;
    private static Random rand = new Random();
    private int direction;

    public Enemy() {
        super(randomX(), randomY(), IMAGE_LOCATION);
        direction = randomDir();
        activate();
    }

    public void update(Collection<Lane> lanes) {
        if (isActive()) {
            double x = getX();
            if (x >= MAX_X || x <= MIN_X) {
                direction *= -1;
            }
            setX(getX() + (SPEED * direction));
        }

        for (Lane l : lanes) {
            for (Note n : l.getNotes()) {
                if (n instanceof NormalNote && hasCollidedWith(n)) {
                    n.deactivate();
                }
            }
        }
    }

    private static int randomX() {
        return rand.nextInt(MAX_X - MIN_X) + MIN_X;
    }

    private static int randomY() {
        return rand.nextInt(MAX_Y - MIN_Y) + MIN_Y;
    }

    private static int randomDir() {
        int random = rand.nextInt(2);
        if (random > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
