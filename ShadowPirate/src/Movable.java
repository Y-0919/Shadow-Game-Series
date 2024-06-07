/**
 * An interface for classes that can move.
 */
public interface Movable {
    /**
     * Performs movement on the entity.
     * @param xMove The distance moved in x direction.
     * @param yMove The distance moved in y direction.
     */
    void move(double xMove, double yMove);
}