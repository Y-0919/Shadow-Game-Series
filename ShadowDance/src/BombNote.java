import bagel.Input;

public class BombNote extends SpecialNote {
    public static final String IMAGE_LOCATION = "res/noteBomb.png";
    public BombNote(int appearanceFrame, int x) {
        super(IMAGE_LOCATION, appearanceFrame, x);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, Lane lane) {
        if (input.wasPressed(lane.getRelevantKey()) && Accuracy.specialNoteInRange(getY(), Lane.TARGET_HEIGHT)) {
            lane.deactivateAllActive();
            deactivate();
        } else if (Accuracy.outOfScreen(getY())) {
            deactivate();
        }
        return 0;
    }

    @Override
    public void applyEffect() {}
}
