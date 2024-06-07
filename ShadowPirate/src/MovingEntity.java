import bagel.util.Point;

/**
 * The parent class for all moving entities of the game.
 */
public abstract class MovingEntity extends GameEntity implements Movable {
    /**
     * The constructor for MovingEntity.
     * @param x The x-coordinate of the moving entity.
     * @param y The y-coordinate of the moving entity.
     */
    public MovingEntity(double x, double y) {
        super(x, y);
    }

    /**
     * Performs movement on the entity,
     * which will be overridden in subclasses to perform specific movement.
     * @param xMove The distance moved in x direction.
     * @param yMove The distance moved in y direction.
     */
    public abstract void move(double xMove, double yMove);

    /**
     * Checks if the moving entity has gone out-of-bound.
     * @param topLeft The top-left coordinate of the perimeter.
     * @param bottomRight The bottom-right coordinate of the perimeter.
     * @return True if the entity has gone out-of-bound, false otherwise.
     */
    public boolean isOutOfBound(Point topLeft, Point bottomRight) {
        return (getY() > bottomRight.y) || (getY() < topLeft.y) || (getX() < topLeft.x) || (getX() > bottomRight.x);
    }
}