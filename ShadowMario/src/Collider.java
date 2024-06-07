import bagel.Input;

public interface Collider {
    boolean updateWithTarget(Input input, LiveObject obj);
}
