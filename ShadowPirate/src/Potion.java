import bagel.Image;
import bagel.util.Rectangle;

/**
 * A class for Potion.
 */
public class Potion extends Item {
    private final static String POTION = "res/items/potion.png";
    private final Image POTION_ICON = new Image("res/items/potionIcon.png");

    private final static int HEALTH_INCREASE = 25;
    private int iconYPos;

    /**
     * The constructor for Potion.
     * @param startX The starting x-coordinate of the potion.
     * @param startY The starting y-coordinate of the potion.
     */
    public Potion(int startX, int startY) {
        super(startX, startY, POTION);
    }

    /**
     * Performs state update.
     * Increases the sailor's current health points by 25 but not beyond the maximum
     * and renders the icon when it is picked by sailor.
     * @param sailor The sailor in the game.
     */
    @Override
    public void update(Sailor sailor) {
        Rectangle potionBox = getBoundingBox();
        Rectangle sailorBox = sailor.getBoundingBox();

        if (potionBox.intersects(sailorBox) && !isPicked()) {
            iconYPos = Item.getIconYPos();
            Item.setIconYPos();
            setPicked(true);
            sailor.setHealthPoints(Math.min(sailor.getHealthPoints()+HEALTH_INCREASE, sailor.getMaxHealthPoints()));
            System.out.println("Sailor finds Potion. Sailor's current health: " +
                    sailor.getHealthPoints() + "/" + sailor.getMaxHealthPoints());
        }

        if (!isPicked()) {
            super.update();
        } else {
            POTION_ICON.drawFromTopLeft(getIconXPos(), iconYPos);
        }
    }
}