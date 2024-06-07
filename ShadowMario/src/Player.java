import bagel.Input;
import bagel.Keys;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Player extends LiveObject {

    private int direction = 1;
    private final Properties props;
    private final List<Fireball> fireballs = new ArrayList<>();
    private DoubleScorePower doubleScorePower = null;
    private boolean canShoot = false;

    public Player(int x, int y, Properties props) {
        super(x, y,
              Double.parseDouble(props.getProperty("gameObjects.player.radius")),
              props.getProperty("gameObjects.player.imageRight"),
              Double.parseDouble(props.getProperty("gameObjects.player.health")));

        this.props = props;
    }

    /**
     * Set jump speed. If the player is on the ground, it will jump.
     * Change the image of the player if the player moves left or right.
     * Shoot projectiles towards enemy boss
     * @param input The keyboard input object from BAGEL
     * @param flyingPlatforms
     * @param enemyBoss
     */
    public void update(Input input, List<FlyingPlatform> flyingPlatforms, EnemyBoss enemyBoss) {
        // handle jump
        if (input.wasPressed(Keys.UP) && canJump(flyingPlatforms)) {
            setSpeedY(-20);
        }
        // end handle jump

        if (input.wasPressed(Keys.S) && canShoot) {
            Fireball fireball = new Fireball(getX(), getY(), direction, direction * -1, props);
            fireballs.add(fireball);
        }
        for(Fireball p: fireballs) {
            if (!p.isHitTarget()){
                p.updateWithTarget(input, enemyBoss);
            }
        }

        if (enemyBoss != null){
            enemyBoss.drawHealth();
        }

        if (input.wasPressed(Keys.LEFT)) {
            direction = -1;
            this.setImage(this.props.getProperty("gameObjects.player.imageLeft"));
        }
        if (input.wasPressed(Keys.RIGHT)) {
            direction = 1;
            this.setImage(this.props.getProperty("gameObjects.player.imageRight"));
        }
        move(flyingPlatforms);
        draw();
    }

    /**
     * Handle the jump. Check whether player lands either on a flying platform
     * or platform
     */
    public void move(List<FlyingPlatform> flyingPlatforms) {
        super.move();

        // handle jump
        if (getSpeedY() > 0 && getY() >= getInitialY() && !isDead()) {
            setSpeedY(0);
            setY(getInitialY());
        }

        if (getY() < getInitialY()) {
            increaseSpeedY(1);
        }

        for (FlyingPlatform fp : flyingPlatforms) {
            if (hasTouchFlyingPlatform(fp)) {
                setSpeedY(0);
            }
        }
        // end handle jump
    }

    /***
     * Determine whether the player within the boundary of flying platform
     * @param fp
     * @return
     */
    private boolean hasTouchFlyingPlatform(FlyingPlatform fp) {
        boolean boundaryX = Math.abs(fp.getX() - getX()) < fp.getHalfLength();
        boolean boundaryY = (fp.getY() - getY()) <= fp.getHalfHeight() && (fp.getY() - getY()) >= fp.getHalfHeight() - 1;

        return boundaryX && boundaryY;
    }

    public DoubleScorePower getDoubleScorePower() {
        return doubleScorePower;
    }

    public void setDoubleScorePower(DoubleScorePower doubleScorePower) {
        this.doubleScorePower = doubleScorePower;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    /***
     * User can only jump if the player is on a flying playform or the platform
     * @param flyingPlatforms
     * @return
     */
    private boolean canJump(List<FlyingPlatform> flyingPlatforms) {
        boolean canJump = getY() == getInitialY();

        for (FlyingPlatform fp : flyingPlatforms) {
            if (hasTouchFlyingPlatform(fp)) {
                canJump = true;
            }
        }

        return canJump;
    }
}
