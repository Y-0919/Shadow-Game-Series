import bagel.Keys;
import bagel.util.Colour;
import bagel.DrawOptions;
import bagel.Font;
import bagel.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class EnemyBoss extends LiveObject {

    private final List<Fireball> fireballs = new ArrayList<>();
    private final Properties props;
    private final int activationRadius;

    private final Font HEALTH_FONT;
    private final DrawOptions COLOR;
    private final int HEALTH_X;
    private final int HEALTH_Y;
    private int fireballMoveDirection;

    public EnemyBoss(int x, int y, Properties props) {
        super(x, y,
              Integer.parseInt(props.getProperty("gameObjects.enemyBoss.speed")),
              Double.parseDouble(props.getProperty("gameObjects.enemyBoss.radius")),
              props.getProperty("gameObjects.enemyBoss.image"),
              Double.parseDouble(props.getProperty("gameObjects.enemyBoss.health")));

        activationRadius = Integer.parseInt(props.getProperty("gameObjects.enemyBoss.activationRadius"));
        this.props = props;
        this.HEALTH_FONT = new Font(props.getProperty("font"), Integer.parseInt(props.getProperty("enemyBossHealth.fontSize")));
        this.COLOR = new DrawOptions().setBlendColour(Colour.RED);
        this.HEALTH_X = Integer.parseInt(props.getProperty("enemyBossHealth.x"));
        this.HEALTH_Y = Integer.parseInt(props.getProperty("enemyBossHealth.y"));
    }

    /***
     * When the target (player) is closer, enemy boss will shoot projectile in random intervals.
     * Random interval will be determined by the frame number
     * @param input
     * @param target
     * @param currFrame
     */
    public void updateWithTargetAndCurrFrame(Input input, Player target, int currFrame) {
        boolean isTargetClose = isTargetClose(target);

        if(currFrame % 100 == 0) {
            Random rand = new Random();
            boolean shootPlayer = rand.nextBoolean();
            if(shootPlayer && isTargetClose && !isDead()) {
                Fireball fireball = new Fireball(getX(), getY(), findPlayerDirection(target), getMoveDirection(), this.props);
                fireballs.add(fireball);
            }
        }

        for(Fireball p: fireballs) {
            if (!p.isHitTarget()){
                p.updateWithTarget(input, target);
            }
        }

        super.update(input);
    }

    public void drawHealth(){
        HEALTH_FONT.drawString("HEALTH " + Math.round(getHealth()*100), HEALTH_X, HEALTH_Y, COLOR);
    }

    /***
     * Handle dead animation by setting a positive speed in Y direction
     */
    @Override
    public void move() {
        if (getY() < getInitialY()) {
            increaseSpeedY(1);
        }

        super.move();
    }

    /***
     * This is need to determine the shooting direction based on the player position
     * @param target
     * @return
     */
    private int findPlayerDirection(LiveObject target) {
        if(target.getX() > getX()) {
            return 1;
        }
        return -1;
    }

    /***
     * Determine whether enemy boss can shoot the player based on the distance between the enemy boss and the player
     * @param target
     * @return
     */
    private boolean isTargetClose(Player target) {
        int distance = Math.abs(target.getX() - getX());
        if(distance < activationRadius) {
            target.setCanShoot(true);
            return true;
        } else {
            target.setCanShoot(false);
            return false;
        }
    }
}
