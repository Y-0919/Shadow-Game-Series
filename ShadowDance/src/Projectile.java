import bagel.*;
import bagel.util.*;

public class Projectile extends GameObject {
    private static final String IMAGE_LOCATION = "res/arrow.png";
    private static final double SPEED = 6;
    private final GameObject target;

    private Vector2 velocity;
    private final DrawOptions rotation;

    public Projectile(double x, double y, GameObject target) {
        super(x, y, IMAGE_LOCATION);
        this.target = target;
        activate();

        // set velocity
        Vector2 velocity = new Vector2(target.getX() - x,target.getY() - y).normalised();
        this.velocity = velocity;
        rotation = new DrawOptions();
        rotation.setRotation(Math.atan2(velocity.y, velocity.x));
    }

    public void update() {
        setX(getX() + velocity.x * SPEED);
        setY(getY() + velocity.y * SPEED);

        if (isActive()) {
            if (hasCollidedWith(target) && target.isActive()) {
                target.deactivate();
                deactivate();
            } else if (getX() <= 0 || getX() >= Window.getWidth() ||
                    getY() <= 0 || getY() >= Window.getHeight()) {
                deactivate();
            }
        }
    }

    @Override
    public void draw() {
        renderWithRotation(rotation);
    }
}
