import bagel.Image;

/**
 * A class for BlackbeardProjectile.
 */
public class BlackbeardProjectile extends PirateProjectile {
    private final Image BLACKBEARD_PROJECTILE = new Image("res/blackbeard/blackbeardProjectile.png");

    private final static int DAMAGE_POINTS = 20;
    private final static double MOVE_SIZE = 0.8;

    /**
     * The constructor for BlackbeardProjectile.
     * @param startX The starting x-coordinate of the blackbeard's projectile.
     * @param startY The starting y-coordinate of the blackbeard's projectile.
     * @param xDis The distance between blackbeard's projectile and sailor in x direction.
     * @param yDis The distance between blackbeard's projectile and sailor in y direction.
     */
    public BlackbeardProjectile(double startX, double startY, double xDis, double yDis) {
        super(startX, startY, xDis, yDis);
    }

    /**
     * Prints a log message to console when the sailor is attacked.
     * @param sailor The sailor being attacked.
     */
    @Override
    public void printAttackLog(MovingCharacter sailor) {
        System.out.println("Blackbeard inflicts " + DAMAGE_POINTS + " damage points on Sailor. " +
                "Sailor's current health: " + sailor.getHealthPoints() + "/" + sailor.getMaxHealthPoints());
    }

    /**
     * Gets the image of the blackbeard's projectile.
     * @return The image of the blackbeard's projectile.
     */
    @Override
    public Image getProjectileImage() {
        return BLACKBEARD_PROJECTILE;
    }

    /**
     * Gets the damage points of the blackbeard's projectile.
     * @return The damage points of the blackbeard's projectile.
     */
    @Override
    public int getDamagePoints() {
        return DAMAGE_POINTS;
    }

    /**
     * Gets the move size of the blackbeard's projectile.
     * @return The move size of the blackbeard's projectile.
     */
    @Override
    public double getMoveSize() {
        return MOVE_SIZE;
    }
}