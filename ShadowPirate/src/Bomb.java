import bagel.Image;

/**
 * A class for Bomb.
 */
public class Bomb extends StationaryEntity implements Attackable {
    private final static String BOMB = "res/bomb.png";
    private final Image EXPLOSION = new Image("res/explosion.png");

    private final static int DAMAGE_POINTS = 10;
    private final static double EXPLOSION_TIME = 500;
    private final static double REFRESH_RATE = 60;
    private final static double MILLISECOND_PER_SEC = 1000;

    private int explodeFrame;
    private boolean readyToExplode;
    private boolean hasExploded;

    /**
     * The constructor for Bomb.
     * @param startX The starting x-coordinate of the bomb.
     * @param startY The starting y-coordinate of the bomb.
     */
    public Bomb(int startX, int startY) {
        super(startX, startY, BOMB);
        explodeFrame = 0;
        readyToExplode = false;
        hasExploded = false;
    }

    /**
     * Performs state update.
     */
    @Override
    public void update() {
        // render the image of unexploded bomb
        if (!readyToExplode) {
            super.update();
        }

        // render the image of exploding bomb
        if (readyToExplode && !hasExploded) {
            EXPLOSION.drawFromTopLeft(getX(), getY());
            explodeFrame++;
        }

        // render nothing on the screen if the bomb has exploded
        if (explodeFrame/(REFRESH_RATE/MILLISECOND_PER_SEC) > EXPLOSION_TIME) {
            hasExploded = true;
        }
    }

    /**
     * Inflicts damage on sailor.
     * @param sailor The sailor in the game.
     */
    @Override
    public void inflictDamage(MovingCharacter sailor) {
        sailor.setHealthPoints(sailor.getHealthPoints() - DAMAGE_POINTS);
    }

    /**
     * Prints a log message to console when the sailor is attacked.
     * @param sailor The sailor in the game.
     */
    @Override
    public void printAttackLog(MovingCharacter sailor) {
        System.out.println("Bomb inflicts " + DAMAGE_POINTS + " damage points on Sailor. " +
                "Sailor's current health: " + sailor.getHealthPoints() + "/" + sailor.getMaxHealthPoints());
    }

    /**
     * Gets the boolean value for determining whether the bomb is ready to explode.
     * @return True if the bomb is ready to explode, false otherwise.
     */
    public boolean readyToExplode() {
        return readyToExplode;
    }

    /**
     * Set the readyToExplode flag to true when the sailor collides with the bomb.
     * @param readyToExplode True if the sailor collides with the bomb
     *                       and the bomb is ready to explode, false otherwise.
     */
    public void setReadyToExplode(boolean readyToExplode) {
        this.readyToExplode = readyToExplode;
    }

    /**
     * Gets the boolean value for determining whether the bomb has exploded.
     * @return True if the bomb has exploded, false otherwise.
     */
    public boolean hasExploded() {
        return hasExploded;
    }
}