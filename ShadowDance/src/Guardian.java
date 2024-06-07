import bagel.*;

import java.util.ArrayList;

public class Guardian extends GameObject {
    private static final int GUARDIAN_X = 800;
    private static final int GUARDIAN_Y = 600;
    private static final String IMAGE_LOCATION = "res/guardian.png";

    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    public Guardian() {
        super(GUARDIAN_X, GUARDIAN_Y, IMAGE_LOCATION);
        activate();
    }

    public void update(Input input, ArrayList<Enemy> enemies) {
        if (input.wasPressed(Keys.LEFT_SHIFT)) {
            Enemy target = findClosestEnemy(enemies);
            if (target != null) {
                Projectile p = new Projectile(getX(), getY(), target);
                projectiles.add(p);
            }
        }

        for (Projectile p : projectiles) {
            p.update();
        }
    }

    @Override
    public void draw() {
        super.draw();
        for (Projectile p : projectiles) {
            p.draw();
        }
    }

    public Enemy findClosestEnemy(ArrayList<Enemy> enemies) {
        Enemy closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy e : enemies) {
            double distance = distanceTo(e);
            if (distance < closestDistance && e.isActive()) {
                closest = e;
                closestDistance = distance;
            }
        }

        return closest;
    }
}
