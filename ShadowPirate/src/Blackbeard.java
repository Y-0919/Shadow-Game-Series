import bagel.Image;

/**
 * A class for Blackbeard.
 */
public class Blackbeard extends Pirate {
    private final Image BLACKBEARD_LEFT = new Image("res/blackbeard/blackbeardLeft.png");
    private final Image BLACKBEARD_RIGHT = new Image("res/blackbeard/blackbeardRight.png");
    private final Image BLACKBEARD_HIT_LEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final Image BLACKBEARD_HIT_RIGHT =  new Image("res/blackbeard/blackbeardHitRight.png");

    private final static double COOL_DOWN = 1500;
    private final static double ATTACK_RANGE = 200;
    private final static int MAX_HEALTH_POINTS = 90;

    /**
     * The constructor for Blackbeard.
     * @param startX The starting x-coordinate of the blackbeard.
     * @param startY The starting y-coordinate of the blackbeard.
     */
    public Blackbeard(int startX, int startY) {
        super(startX, startY);
    }

    /**
     * Shoots a projectile to the sailor when they collide.
     * @param sailor The sailor in the game.
     */
    @Override
    public void addProjectile(Sailor sailor) {
        double xDis = sailor.getX() - getX();
        double yDis = sailor.getY() - getY();
        getProjectiles().add(new BlackbeardProjectile(getX(), getY(), xDis, yDis));
    }

    /**
     * Gets the not-invincible left image of the blackbeard.
     * @return The not-invincible left image of the blackbeard.
     */
    @Override
    public Image getLeftImage() {
        return BLACKBEARD_LEFT;
    }

    /**
     * Gets the not-invincible right image of the blackbeard.
     * @return The not-invincible right image of the blackbeard.
     */
    @Override
    public Image getRightImage() {
        return BLACKBEARD_RIGHT;
    }

    /**
     * Gets the invincible left image of the blackbeard.
     * @return The invincible left image of the blackbeard.
     */
    @Override
    public Image getHitLeftImage() {
        return BLACKBEARD_HIT_LEFT;
    }

    /**
     * Gets the invincible right image of the blackbeard.
     * @return The invincible right image of the blackbeard.
     */
    @Override
    public Image getHitRightImage() {
        return BLACKBEARD_HIT_RIGHT;
    }

    /**
     * Gets the cool down period of the blackbeard.
     * @return The duration of the blackbeard's cool down.
     */
    @Override
    public double getCoolDown() {
        return COOL_DOWN;
    }

    /**
     * Gets the attack range of the blackbeard.
     * @return The attack range of the blackbeard.
     */
    @Override
    public double getAttackRange() {
        return ATTACK_RANGE;
    }

    /**
     * Gets the maximum health points of the blackbeard.
     * @return The maximum health points of the blackbeard.
     */
    @Override
    public int getMaxHealthPoints() {
        return MAX_HEALTH_POINTS;
    }
}