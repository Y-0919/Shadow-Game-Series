import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.DrawOptions;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * The parent class for all moving characters of the game.
 */
public abstract class MovingCharacter extends MovingEntity {
    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final static DrawOptions COLOUR = new DrawOptions();
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);

    private double coolDownFrame;
    private boolean isCoolDown;
    private final static double REFRESH_RATE = 60;
    private final static double MILLISECOND_PER_SEC = 1000;

    private Image currentImage;
    private int healthPoints;
    /**
     * The constructor for MovingCharacter.
     * @param x The x-coordinate of the moving character.
     * @param y The y-coordinate of the moving character.
     */
    public MovingCharacter(double x, double y) {
        super(x, y);
    }

    /**
     * Performs movement on the moving characters.
     * @param xMove The distance moved in x direction.
     * @param yMove The distance moved in y direction.
     */
    @Override
    public void move(double xMove, double yMove) {
        setX(getX() + xMove);
        setY(getY() + yMove);
    }

    /**
     * Checks for collisions between moving character and blocks (or bombs).
     * @param blocks Blocks (or bombs) in the game.
     * @return True if collision happens, false otherwise.
     */
    public boolean checkBlockCollisions(ArrayList<StationaryEntity> blocks) {
        Rectangle movingCharacterBox = getBoundingBox();

        if (blocks.get(0).getClass() == Block.class) {
            for (StationaryEntity current : blocks) {
                Rectangle blockBox = current.getBoundingBox();
                if (movingCharacterBox.intersects(blockBox)) {
                    return true;
                }
            }
        } else {
            for (StationaryEntity current : blocks) {
                Bomb bomb = (Bomb) current;
                Rectangle bombBox = bomb.getBoundingBox();
                if (movingCharacterBox.intersects(bombBox) && !bomb.hasExploded()) {
                    if (this.getClass() == Sailor.class && !bomb.readyToExplode()) {
                        bomb.setReadyToExplode(true);
                        bomb.inflictDamage(this);
                        bomb.printAttackLog(this);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Renders the current health as a percentage on screen.
     * @param healthPointsX The bottom-left x-coordinate of the string.
     * @param healthPointsY The bottom-left y-coordinate of the string.
     */
    public void renderHealthPoints(double healthPointsX, double healthPointsY) {
        double percentageHP = ((double) getHealthPoints() / getMaxHealthPoints()) * 100;
        if (percentageHP <= RED_BOUNDARY) {
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY) {
            COLOUR.setBlendColour(ORANGE);
        } else {
            COLOUR.setBlendColour(GREEN);
        }
        getFont().drawString(Math.round(percentageHP) + "%", healthPointsX, healthPointsY, COLOUR);
    }

    /**
     * Checks if the character's health <= 0.
     * @return True if the character's health <= 0, false otherwise.
     */
    public boolean isDead() {
        return getHealthPoints() <= 0;
    }

    /**
     * Moves the character back when it reaches the boundary or collide with block (or bomb).
     */
    public abstract void moveBack();

    /**
     * Gets the maximum health points of the character.
     * @return The maximum health points of the character.
     */
    public abstract int getMaxHealthPoints();

    /**
     * Gets a rectangle representation of the character.
     * @return The rectangle representation of the character.
     */
    public Rectangle getBoundingBox() {
        return currentImage.getBoundingBoxAt(
                new Point(getX() + currentImage.getWidth()/2, getY() + currentImage.getHeight()/2));
    }

    /**
     * Gets the font to render the health points of the character.
     * @return The font to render the health points of the character.
     */
    public abstract Font getFont();

    public double framesPerMillisecond() {
        return REFRESH_RATE / MILLISECOND_PER_SEC;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public double getCoolDownFrame() {
        return coolDownFrame;
    }

    public void setCoolDownFrame(double coolDownFrame) {
        this.coolDownFrame = coolDownFrame;
    }

    public boolean isCoolDown() {
        return isCoolDown;
    }

    public void setCoolDown(boolean isCoolDown) {
        this.isCoolDown = isCoolDown;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }
}