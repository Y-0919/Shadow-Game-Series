import bagel.*;

public class HoldNote extends Note{
    private static final int HEIGHT_OFFSET = 82;
    private static final int INITIAL_HEIGHT = 24;
    private boolean holdStarted = false;

    public HoldNote(String dir, int appearanceFrame, int x) {
        super("res/holdNote" + dir + ".png", appearanceFrame, x, INITIAL_HEIGHT);
    }

    public double getBottomHeight() {
        return getY() + HEIGHT_OFFSET;
    }

    public double getTopHeight() {
        return getY() - HEIGHT_OFFSET;
    }

    public void startHold() {
        holdStarted = true;
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, Lane lane) {
        Keys relevantKey = lane.getRelevantKey();
        if (isActive() && !holdStarted) {
            int score = accuracy.evaluateScore(getBottomHeight(), Lane.TARGET_HEIGHT, input.wasPressed(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                startHold();
                return score;
            }
        } else if (isActive() && holdStarted) {

            int score = accuracy.evaluateScore(getTopHeight(), Lane.TARGET_HEIGHT, input.wasReleased(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            } else if (input.wasReleased(relevantKey)) {
                deactivate();
                accuracy.setAccuracy(Accuracy.MISS);
                return Accuracy.MISS_SCORE;
            }
        }

        return 0;
    }
}
