import bagel.Font;
import bagel.Image;
import java.util.Random;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * A class for pirate.
 */
public class Pirate extends MovingCharacter {
    private final Image PIRATE_LEFT = new Image("res/pirate/pirateLeft.png");
    private final Image PIRATE_RIGHT = new Image("res/pirate/pirateRight.png");
    private final Image PIRATE_HIT_LEFT = new Image("res/pirate/pirateHitLeft.png");
    private final Image PIRATE_HIT_RIGHT = new Image("res/pirate/pirateHitRight.png");

    private final static double MIN_SPEED = 0.2;
    private final static double MAX_SPEED = 0.7;
    private final double MOVE_SIZE = MIN_SPEED + new Random().nextDouble() * (MAX_SPEED - MIN_SPEED);

    private final static int MOVE_DIRECTIONS = 4;
    private final static int UP = 0;
    private final static int DOWN = 1;
    private final static int LEFT = 2;
    private final static int RIGHT = 3;
    private int moveDirection = new Random().nextInt(MOVE_DIRECTIONS);

    private final static int FONT_SIZE = 15;
    private final static int HEALTH_POINTS_OFFSET = 6;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);

    private final static double COOL_DOWN = 3000;
    private final static double INVINCIBLE_TIME = 1500;
    private final static double ATTACK_RANGE = 100;
    private final static int MAX_HEALTH_POINTS = 45;

    private double invincibleFrame;

    private boolean readyToAttack;
    private boolean isInvincible;

    private final ArrayList<PirateProjectile> PROJECTILES = new ArrayList<>();

    /**
     * The constructor for Pirate.
     * @param startX The starting x-coordinate of the pirate.
     * @param startY The starting y-coordinate of the pirate.
     */
    public Pirate(int startX, int startY) {
        super(startX, startY);

        if (moveDirection == LEFT) {
            setCurrentImage(getLeftImage());
        } else {
            setCurrentImage(getRightImage());
        }

        setHealthPoints(getMaxHealthPoints());
        setCoolDownFrame(0);
        invincibleFrame = 0;
        readyToAttack = true;
        setCoolDown(false);
        isInvincible = false;
    }

    /**
     * Performs state update.
     * @param blocks Blocks (or bombs) in the game.
     * @param sailor The sailor in the game.
     * @param topLeft The top-left coordinate of the perimeter.
     * @param bottomRight The bottom-right coordinate of the perimeter.
     */
    public void update(ArrayList<StationaryEntity> blocks, Sailor sailor, Point topLeft, Point bottomRight) {
        pirateMove();

        if (moveDirection == LEFT) {
            if (isInvincible) {
                setCurrentImage(getHitLeftImage());
            } else {
                setCurrentImage(getLeftImage());
            }
        } else {
            if (isInvincible) {
                setCurrentImage(getHitRightImage());
            } else {
               setCurrentImage(getRightImage());
            }
        }

        if (checkBlockCollisions(blocks) || isOutOfBound(topLeft, bottomRight)) {
            moveBack();
        }

        if (checkSailorCollision(sailor) && !isDead() && readyToAttack) {
            addProjectile(sailor);
            readyToAttack = false;
            setCoolDown(true);
        }

        if (isCoolDown()) {
            setCoolDownFrame(getCoolDownFrame() + 1);
        }

        if (getCoolDownFrame()/framesPerMillisecond() > getCoolDown()) {
            setCoolDownFrame(0);
            setCoolDown(false);
            readyToAttack = true;
        }

        if (isInvincible) {
            invincibleFrame++;
        }

        if (invincibleFrame/framesPerMillisecond() > INVINCIBLE_TIME) {
            invincibleFrame = 0;
            isInvincible = false;
        }

        if (PROJECTILES.size() > 0) {
            for (PirateProjectile p : PROJECTILES) {
                p.update(sailor, topLeft, bottomRight);
            }
        }

        if (!isDead()) {
            getCurrentImage().drawFromTopLeft(getX(), getY());
            renderHealthPoints(getX(), getY() - HEALTH_POINTS_OFFSET);
        }
    }

    /**
     * Shoots a projectile to the sailor when they collide.
     * @param sailor The sailor in the game.
     */
    public void addProjectile(Sailor sailor) {
        double xDis = sailor.getX() - getX();
        double yDis = sailor.getY() - getY();
        PROJECTILES.add(new PirateProjectile(getX(), getY(), xDis, yDis));
    }

    private void pirateMove() {
        if (moveDirection == UP) {
            move(0, -MOVE_SIZE);
        } else if (moveDirection == DOWN) {
            move(0, MOVE_SIZE);
        } else if (moveDirection == LEFT) {
            move(-MOVE_SIZE, 0);
        } else {
            move(MOVE_SIZE, 0);
        }
    }

    /**
     * Changes the pirate (or blackbeard) to the opposite direction.
     */
    @Override
    public void moveBack() {
        if (moveDirection == UP) {
            moveDirection = DOWN;
        } else if (moveDirection == DOWN) {
            moveDirection = UP;
        } else if (moveDirection == LEFT) {
            moveDirection = RIGHT;
        } else {
            moveDirection = LEFT;
        }
    }

    private boolean checkSailorCollision(Sailor sailor) {
        Rectangle pirateAttackBox = getAttackBox();
        Rectangle sailorBox = sailor.getBoundingBox();
        return pirateAttackBox.intersects(sailorBox);
    }

    /**
     * Get the attack range represented as a rectangle of the pirate (or blackbeard).
     * @return The rectangle representation of the attack range.
     */
    public Rectangle getAttackBox() {
        Point topLeft = new Point(getX() + getCurrentImage().getWidth()/2 - getAttackRange()/2,
                getY() + getCurrentImage().getHeight()/2 - getAttackRange()/2);
        return new Rectangle(topLeft, getAttackRange(), getAttackRange());
    }

    /**
     * Gets the not-invincible left image of the pirate.
     * @return The not-invincible left image of the pirate.
     */
    public Image getLeftImage() {
        return PIRATE_LEFT;
    }

    /**
     * Gets the not-invincible right image of the pirate.
     * @return The not-invincible right image of the pirate.
     */
    public Image getRightImage() {
        return PIRATE_RIGHT;
    }

    /**
     * Gets the invincible left image of the pirate.
     * @return The invincible left image of the pirate.
     */
    public Image getHitLeftImage() {
        return PIRATE_HIT_LEFT;
    }

    /**
     * Gets the invincible right image of the pirate.
     * @return The invincible right image of the pirate.
     */
    public Image getHitRightImage() {
        return PIRATE_HIT_RIGHT;
    }

    /**
     * Gets the font to render the health points of the pirate (or blackbeard).
     * @return The font to render the health points of the pirate (or blackbeard).
     */
    @Override
    public Font getFont() {
        return FONT;
    }

    /**
     * Gets the cool down period of the pirate.
     * @return The duration of the pirate's cool down.
     */
    public double getCoolDown() {
        return COOL_DOWN;
    }

    /**
     * Gets the attack range of the pirate.
     * @return The attack range of the pirate.
     */
    public double getAttackRange() {
        return ATTACK_RANGE;
    }

    /**
     * Gets the list of projectiles of the pirate (or blackbeard).
     * @return A list of projectiles shot by the pirate (or blackbeard).
     */
    public ArrayList<PirateProjectile> getProjectiles() {
        return PROJECTILES;
    }

    /**
     * Gets the maximum health points of the pirate.
     * @return The maximum health points of the pirate.
     */
    @Override
    public int getMaxHealthPoints() {
        return MAX_HEALTH_POINTS;
    }


    /**
     * Gets the boolean value for determining whether the pirate (or blackbeard) is in invincible state.
     * @return True if the pirate (or blackbeard) is in invincible state, false otherwise.
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Changes the pirate (or blackbeard) into invincible state if it is attacked by sailor.
     * @param isInvincible True if the pirate should be in invincible state, false otherwise.
     */
    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }
}