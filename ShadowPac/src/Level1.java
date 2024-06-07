import bagel.Font;
import bagel.Window;

public class Level1 extends Level{
    private final static String LEVEL_FILE = "res/level1.csv";

    private final static String INSTRUCTION = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE\nEAT THE PELLET TO ATTACK";

    private final static int INSTRUCTION_FONT_SIZE = 40;
    private final static String DEFAULT_FONT = "res/FSO8BITR.TTF";
    private final static int LEVEL_MESSAGE_SIZE = 60;
    private final static int INSTRUCTION_X = 200;
    private final static int INSTRUCTION_Y = 350;
    private final static int WINNING_SCORE = 800;

    private final static Font INSTRUCTION_FONT = new Font(DEFAULT_FONT,INSTRUCTION_FONT_SIZE);
    private final static Font LEVEL_MESSAGE_FONT = new Font(DEFAULT_FONT,LEVEL_MESSAGE_SIZE);

    private final static String LOSE = "GAME OVER!";
    private final static String WIN = "WELL DONE!";

    public void drawMessage(){
        INSTRUCTION_FONT.drawString(INSTRUCTION,INSTRUCTION_X,INSTRUCTION_Y);
    }

    public String getLevelFile(){
        return LEVEL_FILE;
    }

    public void drawWinMessage(){
        LEVEL_MESSAGE_FONT.drawString(WIN, (Window.getWidth()/2.0 - (LEVEL_MESSAGE_FONT.getWidth(WIN)/2.0)),
                (Window.getHeight()/2.0 + (LEVEL_MESSAGE_SIZE/2.0)));
    }
    public void drawLoseMessage(){
        LEVEL_MESSAGE_FONT.drawString(LOSE, (Window.getWidth()/2.0 - (LEVEL_MESSAGE_FONT.getWidth(LOSE)/2.0)),
                (Window.getHeight()/2.0 + (LEVEL_MESSAGE_SIZE/2.0)));
    }

    /**
     * return the score required to complete this level
     */
    public int getWinningScore(){
        return WINNING_SCORE;
    }
}
