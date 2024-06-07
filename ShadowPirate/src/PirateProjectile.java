import bagel.Image;
import bagel.util.Point;
import bagel.DrawOptions;
import bagel.util.Rectangle;

/**
 * A class for PirateProjectile.
 */
public class PirateProjectile extends MovingEntity implements Attackable {
    private final Image PIRATE_PROJECTILE = new Image("res/pirate/pirateProjectile.png");

    private final static DrawOptions ROTATION = new DrawOptions();

    private final static int DAMAGE_POINTS = 10;
    private final static double MOVE_SIZE = 0.4;

    private final double X_DISTANCE;
    private final double Y_DISTANCE;

    private boolean hasMet;

    /**
     * The constructor for PirateProjectile.
     * @param startX The starting x-coordinate of the pirate's projectile.
     * @param startY The starting y-coordinate of the pirate's projectile.
     * @param xDis The distance between pirate's projectile and sailor in x direction.
     * @param yDis The distance between pirate's projectile and sailor in y direction.
     */
    public PirateProjectile(double startX, double startY, double xDis, double yDis) {
        super(startX, startY);
        X_DISTANCE = xDis;
        Y_DISTANCE = yDis;
        hasMet = false;
    }

    /**
     * Performs state update.
     * @param sailor The sailor in the game.
     * @param topLeft The top-left coordinate of the perimeter.
     * @param bottomRight The bottom-right coordinate of the perimeter.
     */
    public void update(Sailor sailor, Point topLeft, Point bottomRight) {
        // render projectile if it doesn't meet sailor and is not out-of-bound
        if (!hasMet && !isOutOfBound(topLeft, bottomRight)) {
            move(X_DISTANCE, Y_DISTANCE);
            getProjectileImage().drawFromTopLeft(getX(), getY(), ROTATION);
        }

        // stop rendering image when it meets sailor, and inflict damage on sailor
        if (checkSailorCollision(sailor) && !hasMet) {
            hasMet = true;
            inflictDamage(sailor);
            printAttackLog(sailor);
        }
    }

    /**
     * Performs movement on the projectiles,
     * @param xDis The distance moved in x direction.
     * @param yDis The distance moved in y direction.
     */
    @Override
    public void move(double xDis, double yDis) {
        double radian = Math.atan(yDis / xDis);
        double xSpeed = getMoveSize() * Math.abs(Math.cos(radian));
        double ySpeed = getMoveSize() * Math.abs(Math.sin(radian));

        if (xDis > 0) {
            setX(getX() + xSpeed);
        } else {
            setX(getX() - xSpeed);
        }

        if (yDis > 0) {
            setY(getY() + ySpeed);
        } else {
            setY(getY() - ySpeed);
        }

        ROTATION.setRotation(radian);
    }

    private boolean checkSailorCollision(Sailor sailor) {
        Point projectilePosition = getBoundingPosition();
        Rectangle sailorBox = sailor.getBoundingBox();
        return sailorBox.intersects(projectilePosition);
    }

    /**
     * Inflicts damage on sailor.
     * @param sailor The sailor in the game.
     */
    @Override
    public void inflictDamage(MovingCharacter sailor) {
        sailor.setHealthPoints(sailor.getHealthPoints() - getDamagePoints());
    }

    /**
     * Prints a log message to console when the sailor is attacked.
     * @param sailor The sailor being attacked.
     */
    @Override
    public void printAttackLog(MovingCharacter sailor) {
        System.out.println("Pirate inflicts " + getDamagePoints() + " damage points on Sailor. " +
                "Sailor's current health: " + sailor.getHealthPoints() + "/" + sailor.getMaxHealthPoints());
    }

    private Point getBoundingPosition() {
        return new Point(getX() + getProjectileImage().getWidth()/2, getY() + getProjectileImage().getHeight()/2);
    }

    /**
     * Gets the image of the pirate's projectile.
     * @return The image of the pirate's projectile.
     */
    public Image getProjectileImage() {
        return PIRATE_PROJECTILE;
    }

    /**
     * Gets the damage points of the pirate's projectile.
     * @return The damage points of the pirate's projectile.
     */
    public int getDamagePoints() {
        return DAMAGE_POINTS;
    }

    /**
     * Gets the move size of the pirate's projectile.
     * @return The move size of the pirate's projectile.
     */
    public double getMoveSize() {
        return MOVE_SIZE;
    }
}