/**
 * The parent class for all items of the game.
 */
public abstract class Item extends StationaryEntity {
    private final static int ICON_X_POS = 10;
    private final static int ICON_OFFSET = 40;
    private static int iconYPos = 40;

    private boolean isPicked;

    /**
     * The constructor for Item.
     * @param x The x-coordinate of the item.
     * @param y The y-coordinate of the item.
     * @param filename The path of item's image.
     */
    public Item(double x, double y, String filename) {
        super(x, y, filename);
        isPicked = false;
    }

    /**
     * Performs an update when sailor picks the item.
     * @param sailor The sailor in the game.
     */
    public abstract void update(Sailor sailor);

    /**
     * Gets the x-coordinate of the icon position.
     * @return The x-coordinate of the icon position.
     */
    public int getIconXPos() {
        return ICON_X_POS;
    }

    /**
     * Gets the y-coordinate of the icon position.
     * @return The y-coordinate of the icon position.
     */
    public static int getIconYPos() {
        return iconYPos;
    }

    /**
     * Sets the y-coordinate of the icon,
     * so that icons are grouped vertically according to the order they are picked.
     */
    public static void setIconYPos() {
        iconYPos += ICON_OFFSET;
    }

    /**
     * Gets the boolean value for determining whether the item has been picked.
     * @return True if the item has been picked, false otherwise.
     */
    public boolean isPicked() {
        return isPicked;
    }

    /**
     * Sets the isPicked flag to true when the sailor picks the item.
     * @param isPicked True if sailor picks the item, false otherwise.
     */
    public void setPicked(boolean isPicked) {
        this.isPicked = isPicked;
    }
}