import bagel.*;

public class SpecialLane extends Lane {
    private static final String IMAGE_LOCATION = "res/laneSpecial.png";

    public SpecialLane(int location) {
        super(Keys.SPACE, location, IMAGE_LOCATION);
    }

    @Override
    public int update(Input input, Accuracy accuracy, int currFrame) {
        for (Note n : getNotes()) {
            if (n instanceof SpecialNote) {
                ((SpecialNote) n).applyEffect();
            }
        }
        return super.update(input, accuracy, currFrame);
    }
}
