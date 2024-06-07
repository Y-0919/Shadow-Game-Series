import bagel.Input;

import java.util.ArrayList;

public class Level3 extends Level {
    private static final int CLEAR_SCORE = 350;
    private static final int ENEMY_SPAWN_FRAMES = 600;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private Guardian guardian = new Guardian();
    private int lastEnemySpawnFrame = 0;

    public Level3() {
        super(3, CLEAR_SCORE);
    }

    @Override
    public void update(Input input) {
        if (!isFinished()) {
            draw();
            if (!isPaused()) {
                int currFrame = getCurrFrame();
                if (currFrame - lastEnemySpawnFrame >= ENEMY_SPAWN_FRAMES) {
                    enemies.add(new Enemy());
                    lastEnemySpawnFrame = currFrame;
                }

                for (Enemy e : enemies) {
                    e.update(getLanes());
                }

                guardian.update(input, enemies);
            }
        }

        super.update(input);
    }

    private void draw() {
        for (Enemy e : enemies) {
            e.draw();
        }

        guardian.draw();
    }
}
