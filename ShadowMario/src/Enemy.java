import bagel.Input;
import java.util.Properties;
import java.util.Random;

public class Enemy extends LiveObject implements Weapon, IndependentMover, Collider {
    private final int maxRandomDisplacementX;
    private final double damageSize;
    private int randomDisplacementX;
    private int randomDisplacementSpeedX;
    private boolean killedTarget = false;
    private boolean hitPlayer = false;

    public Enemy(int x, int y, Properties props) {
        super(x, y,
             Integer.parseInt(props.getProperty("gameObjects.enemy.speed")),
             Double.parseDouble(props.getProperty("gameObjects.enemy.radius")),
             props.getProperty("gameObjects.enemy.image"),
             0);

        this.damageSize = Double.parseDouble(props.getProperty("gameObjects.enemy.damageSize"));

        this.maxRandomDisplacementX = Integer.parseInt(props.getProperty("gameObjects.enemy.maxRandomDisplacementX"));
        this.randomDisplacementX = x;
        randomDisplacementSpeedX = new Random().nextBoolean() ? 1 : -1;
    }

    @Override
    public boolean updateWithTarget(Input input, LiveObject target) {
        super.update(input);

        if (target != null && hasCollidedWith(target) && !hitPlayer) {
            hitPlayer = true;
            damageTarget(target);
            return true;
        }

        return false;
    }

    @Override
    public void move() {
        independentMove();

        // in case of a death
        if (getY() < getInitialY()) {
            increaseSpeedY(1);
        }

        super.move();
    }

    /***
     * Damage target if the enemy within the sum of two radius of the two objects (target and enemy)
     * @param obj
     */
    @Override
    public void damageTarget(LiveObject obj) {
        InvinciblePower power = obj.getInvinciblePower();
        double damageSize = this.damageSize;
        if(power != null) {
            damageSize = power.applyEffect(this.damageSize);
        }
        double targetHealth = obj.getHealth() - damageSize;
        obj.setHealth(targetHealth);

        if (targetHealth <= 0 && !killedTarget) {
            obj.dead();
            killedTarget = true;
        }
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
