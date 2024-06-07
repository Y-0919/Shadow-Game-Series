import bagel.*;

public class NormalLane extends Lane {

    public NormalLane(String dir, int location) {
        super(getRelevantKey(dir), location, "res/lane" + dir + ".png");
    }

    private static Keys getRelevantKey(String dir) {
        switch (dir) {
            case "Left":
                return Keys.LEFT;
            case "Right":
                return Keys.RIGHT;
            case "Up":
                return Keys.UP;
            case "Down":
                return Keys.DOWN;
            default:
                System.out.println("Unexpected lane type");
                throw new RuntimeException("Unexpected lane type");
        }
    }
}
