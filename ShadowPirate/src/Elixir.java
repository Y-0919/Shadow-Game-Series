import bagel.Image;
import bagel.util.Rectangle;

/**
 * A class for Elixir.
 */
public class Elixir extends Item {
    private final static String ELIXIR = "res/items/elixir.png";
    private final Image ELIXIR_ICON = new Image("res/items/elixirIcon.png");

    private final static int MAX_HEALTH_INCREASE = 35;
    private int iconYPos;

    /**
     * The constructor for Elixir.
     * @param startX The starting x-coordinate of the elixir.
     * @param startY The starting y-coordinate of the elixir.
     */
    public Elixir(int startX, int startY) {
        super(startX, startY, ELIXIR);
    }

    /**
     * Performs state update.
     * Increases the sailor's maximum health points by 35,
     * increases the current health points to the new maximum value
     * and renders the icon when it is picked by sailor.
     * @param sailor The sailor in the game.
     */
    @Override
    public void update(Sailor sailor) {
        Rectangle elixirBox = getBoundingBox();
        Rectangle sailorBox = sailor.getBoundingBox();

        if (elixirBox.intersects(sailorBox) && !isPicked()) {
            iconYPos = Item.getIconYPos();
            Item.setIconYPos();
            setPicked(true);
            sailor.setMaxHealthPoints(sailor.getMaxHealthPoints() + MAX_HEALTH_INCREASE);
            sailor.setHealthPoints(sailor.getMaxHealthPoints());
            System.out.println("Sailor finds Elixir. Sailor's current health: " +
                    sailor.getHealthPoints() + "/" + sailor.getMaxHealthPoints());
        }

        if (!isPicked()) {
            super.update();
        } else {
            ELIXIR_ICON.drawFromTopLeft(getIconXPos(), iconYPos);
        }
    }
}