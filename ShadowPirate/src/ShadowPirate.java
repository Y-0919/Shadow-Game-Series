import bagel.*;

public class ShadowPirate extends AbstractGame {
    // parameters for the window
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";

    // messages
    private final static String LEVEL_UP_MESSAGE = "LEVEL COMPLETE!";
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";
    private final static String END_MESSAGE = "GAME OVER";

    // parameters for controlling level up message's display time
    private final static double REFRESH_RATE = 60;
    private final static double MILLISECOND_PER_SEC = 1000;
    private final static double LEVEL_UP_TIME = 3000;
    private int levelUpFrame;

    private Level level;

    /**
     * Constructor of the ShadowPirate game.
     */
    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        level = new Level0();
        level.setGameOn(false);
        level.setGameWin(false);
        level.setGameEnd(false);
        level.setLevelUp(false);
        level.setHasLevelUp(false);
        levelUpFrame = 0;
    }

    /**
     * The entry point for the program.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }


    /**
     * Performs a state update. Allows the game to exit when the escape key is pressed.
     * @param input Keyboard input from the user.
     */
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        boolean pause = false;

        if (!level.gameOn()) {
            pause = true;
            level.drawStartScreen(input);
        }

        if (level.gameEnd()) {
            pause = true;
            drawEndScreen(END_MESSAGE);
        }

        if (level.gameWin()) {
            pause = true;
            drawEndScreen(WIN_MESSAGE);
        }

        // render level up screen for 3 seconds
        if (level.levelUp() && !level.hasLevelUp()) {
            pause = true;
            levelUpFrame++;
            drawEndScreen(LEVEL_UP_MESSAGE);
        }

        if (levelUpFrame/(REFRESH_RATE/MILLISECOND_PER_SEC) > LEVEL_UP_TIME || input.wasPressed(Keys.W)) {
            levelUpFrame = 0;
            level = new Level1();
            level.setHasLevelUp(false);
            level.setLevelUp(false);
            level.setGameOn(false);
        }

        // when game is running
        if (!pause) {
            level.run(input);
        }
    }

    private void drawEndScreen(String message){
        level.getFont().drawString(message, (Window.getWidth()/2.0 - (level.getFont().getWidth(message)/2.0)),
                level.getFontYPos());
    }
}