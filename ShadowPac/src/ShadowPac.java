import bagel.*;


public class ShadowPac extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static int WAITING_TIME = 300;

    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    private static int currentLevel = 0;
    private static int waitingTime = 0;

    private boolean inGame = false;

    private Level level = new Level0();

    /**
     * Construct new game
     */
    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT);
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        // in level 0
        if(currentLevel == 0) {
            if (level.isLose()) {
                level.drawLoseMessage();
                if(inGame){
                    inGame = false;
                }
            } else if(level.isWin()){
                level.drawWinMessage();
                if(inGame){
                    inGame = false;
                }
                // wait between levels then generate next level
                if(waitingTime != WAITING_TIME){
                    waitingTime++;

                // the point where you create a new next level (level 1)
                } else{
                    level = new Level1();
                    currentLevel = 1;
                }
            } else {
                if (input.wasPressed(Keys.SPACE) && !level.isLose() && !inGame) {
                    level.setHasStart(true);
                    level.readCSV(level.getLevelFile());
                    inGame = true;
                }
                if (!level.isHasStart()) {
                    level.drawMessage();
                } else {
                    level.levelStart(input, level);
                }
            }
        }

        // in level 1
        if(currentLevel == 1) {
            if (level.isLose()) {
                level.drawLoseMessage();
            }else if(level.isWin()){
                level.drawWinMessage();
            }else {
                if (input.wasPressed(Keys.SPACE) && !level.isLose() && !inGame) {
                    level.setHasStart(true);
                    level.readCSV(level.getLevelFile());
                    inGame = true;
                }
                if (!level.isHasStart()) {
                    level.drawMessage();
                } else {
                    level.levelStart(input, level);
                }
            }
        }
    }
}
