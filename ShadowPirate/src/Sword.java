import bagel.Image;
import bagel.util.Rectangle;

/**
 * A class for Sword.
 */
public class Sword extends Item {
    private final static String SWORD = "res/items/sword.png";
    private final Image SWORD_ICON = new Image("res/items/swordIcon.png");

    private final static int DAMAGE_INCREASE = 15;
    private int iconYPos;

    /**
     * The constructor for Sword.
     * @param startX The starting x-coordinate of the sword.
     * @param startY The starting y-coordinate of the sword.
     */
    public Sword(int startX, int startY) {
        super(startX, startY, SWORD);
    }

    /**
     * Performs state update.
     * Increases the sailor's damage points by 15
     * and renders the icon when it is picked by sailor.
     * @param sailor The sailor in the game.
     */
    @Override
    public void update(Sailor sailor) {
        Rectangle swordBox = getBoundingBox();
        Rectangle sailorBox = sailor.getBoundingBox();

        if (swordBox.intersects(sailorBox) && !isPicked()) {
            iconYPos = Item.getIconYPos();
            Item.setIconYPos();
            setPicked(true);
            sailor.setDamagePoints(sailor.getDamagePoints() + DAMAGE_INCREASE);
            System.out.println("Sailor finds Sword. Sailor's damage points increased to " + sailor.getDamagePoints());
        }

        if (!isPicked()) {
            super.update();
        } else {
            SWORD_ICON.drawFromTopLeft(getIconXPos(), iconYPos);
        }
    }
}