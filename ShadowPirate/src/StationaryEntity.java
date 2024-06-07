import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The parent class for all stationary entities of the game.
 */
public abstract class StationaryEntity extends GameEntity {
    private final Image IMAGE;

    /**
     * The constructor for StationaryEntity.
     * @param x The x-coordinate of the stationary entity.
     * @param y The y-coordinate of the stationary entity.
     * @param filename The path of stationary entity's image.
     */
    public StationaryEntity(double x, double y, String filename) {
        super(x, y);
        IMAGE = new Image(filename);
    }

    /**
     * Performs an update on the stationary entity.
     * The update method here renders the image of the entity,
     * which is the basic update of its subclasses.
     */
    public void update() {
        IMAGE.drawFromTopLeft(getX(), getY());
    }

    /**
     * Get a rectangle representation of the stationary entity.
     * @return The rectangle representation of the stationary entity.
     */
    public Rectangle getBoundingBox() {
        return IMAGE.getBoundingBoxAt(new Point(getX() + IMAGE.getWidth()/2, getY() + IMAGE.getHeight()/2));
    }
}