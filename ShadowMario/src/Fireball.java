import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Fireball extends GameObject implements Weapon, Collider, IndependentMover {

    private final double damageSize;
    private boolean hitTarget = false;
    private int targetDirection;
    private int shootSpeed;
    private static int MOVE_SPEED = 5;

    public Fireball(int x, int y, int targetDirection, int moveDirection, Properties props) {
        super(x, y,
              MOVE_SPEED,
              Double.parseDouble(props.getProperty("gameObjects.fireball.radius")),
              props.getProperty("gameObjects.fireball.image"));

        this.damageSize = Double.parseDouble(props.getProperty("gameObjects.fireball.damageSize"));
        this.targetDirection = targetDirection;
        this.shootSpeed = Integer.parseInt(props.getProperty("gameObjects.fireball.speed"));
        setMoveDirection(moveDirection);
    }

    /***
     * Damage target if projectile within the sum of two radius of the two objects (target and projectile)
     * Player will receive zero damage if it has invincible power activated
     * @param target
     */
    @Override
    public void damageTarget(LiveObject target) {
        InvinciblePower power = target.getInvinciblePower();
        double damageSize = this.damageSize;
        if(power != null) {
            damageSize = power.applyEffect(this.damageSize);
        }
        double targetHealth = target.getHealth() - damageSize;
        target.setHealth(targetHealth);

        if (targetHealth <= 0) {
            target.dead();
        }
    }

    @Override
    public boolean updateWithTarget(Input input, LiveObject target) {
        super.update(input);
        independentMove();

        if (target != null && hasCollidedWith(target) && !hitTarget) {
            hitTarget = true;
            damageTarget(target);
        }

        return hitTarget;
    }

    public boolean isHitTarget() {
        return hitTarget;
    }

    @Override
    public void independentMove() {
        this.setX(getX() + shootSpeed * targetDirection);
    }
}
