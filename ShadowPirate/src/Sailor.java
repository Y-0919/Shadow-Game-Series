import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * A class for Sailor.
 */
public class Sailor extends MovingCharacter implements Attackable {
    private final Image SAILOR_LEFT = new Image("res/sailor/sailorLeft.png");
    private final Image SAILOR_RIGHT = new Image("res/sailor/sailorRight.png");
    private final Image SAILOR_HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private final Image SAILOR_HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");

    private final static int WIN_X = 990;
    private final static int WIN_Y = 630;

    private final static int HEALTH_X = 10;
    private final static int HEALTH_Y = 25;
    private final static int FONT_SIZE = 30;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);

    private final static int MOVE_SIZE = 1;
    private final static double COOL_DOWN = 2000;
    private final static double ATTACK_TIME = 1000;

    private int damagePoints = 15;
    private int maxHealthPoints = 100;

    private double oldX;
    private double oldY;

    private double attackFrame;

    private boolean isAttack;

    private boolean faceRight;
    private boolean hasWon;

    /**
     * The constructor for Sailor.
     * @param startX The starting x-coordinate of the sailor.
     * @param startY The starting y-coordinate of the sailor.
     */
    public Sailor(int startX, int startY) {
        super(startX, startY);
        setCurrentImage(SAILOR_RIGHT);
        setHealthPoints(maxHealthPoints);
        setCoolDownFrame(0);
        attackFrame = 0;
        setCoolDown(true);
        isAttack = false;
        faceRight = true;
        hasWon = false;
    }

    /**
     * Performs state update.
     * @param input Keyboard input from the user.
     * @param blocks Blocks (or bombs) in the game.
     * @param pirates Pirates (or blackbeards) in the game.
     * @param topLeft The top-left coordinate of the perimeter.
     * @param bottomRight The bottom-right coordinate of the perimeter.
     */
    public void update(Input input, ArrayList<StationaryEntity> blocks, ArrayList<Pirate> pirates,
                       Point topLeft, Point bottomRight) {
        if (input.isDown(Keys.UP)) {
            setOldPoint();
            move(0, -MOVE_SIZE);
        } else if (input.isDown(Keys.DOWN)) {
            setOldPoint();
            move(0, MOVE_SIZE);
        } else if (input.isDown(Keys.LEFT)) {
            faceRight = false;
            setOldPoint();
            move(-MOVE_SIZE, 0);
            if (isAttack) {
                setCurrentImage(SAILOR_HIT_LEFT);
            } else {
                setCurrentImage(SAILOR_LEFT);
            }
        } else if (input.isDown(Keys.RIGHT)) {
            faceRight = true;
            setOldPoint();
            move(MOVE_SIZE, 0);
            if (isAttack) {
                setCurrentImage(SAILOR_HIT_RIGHT);
            } else {
                setCurrentImage(SAILOR_RIGHT);
            }
        }

        if (input.wasPressed(Keys.S) && !isCoolDown()) {
            isAttack = true;
            if (faceRight) {
                setCurrentImage(SAILOR_HIT_RIGHT);
            } else {
                setCurrentImage(SAILOR_HIT_LEFT);
            }
        }

        if (isAttack) {
            checkPirateCollisions(pirates);
            attackFrame++;
        }

        if (attackFrame/framesPerMillisecond() > ATTACK_TIME) {
            isAttack = false;
            setCoolDown(true);
            attackFrame = 0;
            if (faceRight) {
                setCurrentImage(SAILOR_RIGHT);
            } else {
                setCurrentImage(SAILOR_LEFT);
            }
        }

        if (isCoolDown()) {
            setCoolDownFrame(getCoolDownFrame() + 1);
        }

        if (getCoolDownFrame()/framesPerMillisecond() > COOL_DOWN) {
            setCoolDown(false);
            setCoolDownFrame(0);
        }

        if (checkBlockCollisions(blocks) || isOutOfBound(topLeft, bottomRight)) {
            moveBack();
        }

        getCurrentImage().drawFromTopLeft(getX(), getY());
        renderHealthPoints(HEALTH_X, HEALTH_Y);
    }

    private void setOldPoint() {
        oldX = getX();
        oldY = getY();
    }

    /**
     * Moves the sailor back to its previous position.
     */
    @Override
    public void moveBack() {
        setX(oldX);
        setY(oldY);
    }

    private void checkPirateCollisions(ArrayList<Pirate> pirates) {
        Rectangle sailorBox = getBoundingBox();
        for (Pirate current : pirates) {
            Rectangle pirateBox = current.getBoundingBox();
            if (sailorBox.intersects(pirateBox) && !current.isInvincible() && !current.isDead()) {
                current.setInvincible(true);
                inflictDamage(current);
                printAttackLog(current);
            }
        }
    }

    /**
     * Inflicts damage on pirate (or blackbeard).
     * @param pirate The pirate (or blackbeard) being inflicted damage on.
     */
    @Override
    public void inflictDamage(MovingCharacter pirate) {
        pirate.setHealthPoints(pirate.getHealthPoints() - damagePoints);
    }

    /**
     * Prints a log message to console when the pirate (or blackbeard) is attacked.
     * @param pirate The pirate (or blackbeard) being attacked.
     */
    @Override
    public void printAttackLog(MovingCharacter pirate) {
        String pirateType = pirate.getClass().getName();
        System.out.println("Sailor inflicts " + damagePoints + " damage points on " + pirateType + ". " + pirateType +
                "'s current health: " + pirate.getHealthPoints() + "/" + pirate.getMaxHealthPoints());
    }

    /**
     * Checks whether level 0 is completed.
     * @return True if level 0 is completed, false otherwise.
     */
    public boolean hasCompleteLevel() {
        return (getX() >= WIN_X) && (getY() > WIN_Y);
    }


    /**
     * Gets the font to render the health points of the sailor.
     * @return The font to render the health points of the sailor.
     */
    @Override
    public Font getFont() {
        return FONT;
    }

    /**
     * Gets the maximum health points of the sailor.
     * @return The maximum health points of the sailor.
     */
    @Override
    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    /**
     * Sets the maximum health points of the sailor to the provided value.
     * @param maxHealthPoints The new maximum health points of the sailor.
     */
    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    /**
     * Gets the damage points of the sailor.
     * @return The damage points of the sailor.
     */
    public int getDamagePoints() {
        return damagePoints;
    }

    /**
     * Sets the damage points of the sailor to the provided value.
     * @param damagePoints The new damage points of the sailor.
     */
    public void setDamagePoints(int damagePoints) {
        this.damagePoints = damagePoints;
    }

    /**
     * Gets the boolean value for determining whether the sailor wins.
     * @return True if the sailor wins, false otherwise.
     */
    public boolean hasWon() {
        return hasWon;
    }

    /**
     * Sets the hasWon flag to true when the sailor reaches the treasure.
     * @param hasWon True if sailor reaches the treasure, false otherwise.
     */
    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }
}