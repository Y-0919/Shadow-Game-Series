import bagel.*;

public class SpeedChangeNote extends SpecialNote {
    private static final String SPEED_UP_IMAGE_LOC = "res/noteSpeedUp.png";
    private static final String SLOW_DOWN_IMAGE_LOC = "res/noteSlowDown.png";
    private static final int SCORE = 15;
    private static final String SPEED_UP_MESSAGE = "SPEED UP";
    private static final String SLOW_DOWN_MESSAGE = "SLOW DOWN";
    private static final double SPEED_CHANGE = 1;
    private String message;
    private int speedMultiplier;

    public SpeedChangeNote(boolean isSpeedUp, int appearanceFrame, int x) {
        super(getImageLoc(isSpeedUp), appearanceFrame, x);
        if (isSpeedUp) {
            speedMultiplier = 1;
            message = SPEED_UP_MESSAGE;
        } else {
            speedMultiplier = -1;
            message = SLOW_DOWN_MESSAGE;
        }
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, Lane lane) {
        if (input.wasPressed(lane.getRelevantKey()) && Accuracy.specialNoteInRange(getY(), Lane.TARGET_HEIGHT)) {
            double newSpeed = getSpeed() + speedMultiplier * SPEED_CHANGE;
            setSpeed(newSpeed);
            accuracy.setAccuracy(message);
            deactivate();
            return SCORE;
        } else if (Accuracy.outOfScreen(getY())) {
            deactivate();
        }
        return Accuracy.NOT_SCORED;
    }

    @Override
    public void applyEffect() {}

    private static String getImageLoc(boolean isSpeedUp) {
        if (isSpeedUp) {
            return SPEED_UP_IMAGE_LOC;
        } else {
            return SLOW_DOWN_IMAGE_LOC;
        }
    }
}
