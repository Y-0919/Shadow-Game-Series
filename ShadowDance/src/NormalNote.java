import bagel.*;

public class NormalNote extends Note {
    private static final int INITIAL_HEIGHT = 100;

    public NormalNote(String dir, int appearanceFrame, int x) {
        super("res/note" + dir + ".png", appearanceFrame, x, INITIAL_HEIGHT);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, Lane lane) {
        if (isActive()) {
            int score = accuracy.evaluateScore(getY(), Lane.TARGET_HEIGHT, input.wasPressed(lane.getRelevantKey()));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            }

        }

        return 0;
    }
}
