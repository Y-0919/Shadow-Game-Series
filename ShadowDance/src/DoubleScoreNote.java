import bagel.Input;

public class DoubleScoreNote extends SpecialNote {
    private boolean effectActive = false;
    private static final String IMAGE_LOC = "res/note2x.png";
    private static final int EFFECT_FRAMES = 480;
    private static final int SCORE_MULTIPLIER = 2;
    private int currFrame = 0;

    public DoubleScoreNote(int appearanceFrame, int x) {
        super(IMAGE_LOC, appearanceFrame, x);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, Lane lane) {
        if (input.wasPressed(lane.getRelevantKey()) && Accuracy.specialNoteInRange(getY(), Lane.TARGET_HEIGHT)) {
            effectActive = true;
            accuracy.setAccuracy("Double Score");
            deactivate();
        } else if (Accuracy.outOfScreen(getY())) {
            deactivate();
        }
        return 0;
    }

    @Override
    public void applyEffect() {
        if (effectActive == true) {
            if (currFrame == 0) {
                Level.setScoreMultiplier(Level.getScoreMultiplier() * SCORE_MULTIPLIER);
            } else if (currFrame >= EFFECT_FRAMES) {
                Level.setScoreMultiplier(Level.getScoreMultiplier() / SCORE_MULTIPLIER);
                effectActive = false;
            }
            currFrame++;
        }
    }
}
