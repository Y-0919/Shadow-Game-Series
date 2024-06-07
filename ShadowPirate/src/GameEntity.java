/**
 * The parent class for all entities of the game.
 */
public abstract class GameEntity {
    private double x;
    private double y;

    /**
     * The constructor for GameEntity.
     * @param x The x-coordinate of the game entity.
     * @param y The y-coordinate of the game entity.
     */
    public GameEntity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the game entity.
     * @return The x-coordinate of the game entity.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the game entity to the provided value.
     * @param x The new x-coordinate of the game entity.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the game entity.
     * @return The y-coordinate of the game entity.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the game entity to the provided value.
     * @param y The new y-coordinate of the game entity.
     */
    public void setY(double y) {
        this.y = y;
    }
}