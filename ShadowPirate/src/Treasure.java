import bagel.util.Rectangle;

/**
 * A class for Treasure.
 */
public class Treasure extends StationaryEntity {
    private final static String TREASURE = "res/treasure.png";

    /**
     * The constructor for Treasure.
     * @param startX The starting x-coordinate of the treasure.
     * @param startY The starting y-coordinate of the treasure.
     */
    public Treasure(int startX, int startY) {
        super(startX, startY, TREASURE);
    }

    /**
     * Performs state update.
     * It simply renders the image of the treasure
     * and checks whether sailor has reached the treasure.
     * @param sailor The sailor in the game.
     */
    public void update(Sailor sailor) {
        super.update();
        checkSailorCollision(sailor);
    }

    private void checkSailorCollision(Sailor sailor) {
        Rectangle treasureBox = getBoundingBox();
        Rectangle sailorBox = sailor.getBoundingBox();
        if (treasureBox.intersects(sailorBox)) {
            sailor.setHasWon(true);
        }
    }
}