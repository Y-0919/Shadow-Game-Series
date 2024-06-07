public abstract class SpecialNote extends Note {
    private static final int INITIAL_HEIGHT = 100;

    public SpecialNote(String imageLocation, int appearanceFrame, int x) {
        super(imageLocation, appearanceFrame, x, INITIAL_HEIGHT);
    }

    public abstract void applyEffect();
}
