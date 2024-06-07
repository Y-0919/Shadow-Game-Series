import bagel.*;

public abstract class Note extends GameObject implements Comparable<Note> {
    private static final int INITIAL_SPEED = 2;
    private final int appearanceFrame;
    private static double speed = INITIAL_SPEED;
    private boolean completed = false;

    public Note(String imageLocation, int appearanceFrame, int x, int y) {
        super(x, y, imageLocation);
        this.appearanceFrame = appearanceFrame;
    }

    public static double getSpeed() {
        return speed;
    }

    public static void setSpeed(double s) {
        speed = s;
    }

    public boolean isCompleted() {return completed;}

    public void deactivate() {
        super.deactivate();
        completed = true;
    }

    public static void resetSpeed() {
        speed = INITIAL_SPEED;
    }

    public void update(int currFrame) {
        if (isActive()) {
            setY(getY() + speed);
        }

        if (currFrame >= appearanceFrame && !completed) {
            activate();
        }
    }

    @Override
    public int compareTo(Note n) {
        return this.appearanceFrame - n.appearanceFrame;
    }

    public abstract int checkScore(Input input, Accuracy accuracy, Lane lane);
}
