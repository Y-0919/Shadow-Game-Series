import bagel.Font;
import bagel.Window;


public class Level0 extends Level{
    private final static String LEVEL_FILE = "res/level0.csv";

    private final static String GAME_TITLE = "SHADOW PAC";
    private final static String INSTRUCTION = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE";
    private final static int TITLE_SIZE = 64;
    private final static int TITLE_X = 260;
    private final static int TITLE_Y = 250;
    private final static int INSTRUCTION_FONT_SIZE = 24;
    private final static int TITLE_INS_SPACING_X = 60;
    private final static int TITLE_INS_SPACING_Y = 190;
    private final static String DEFAULT_FONT = "res/FSO8BITR.TTF";
    private static final int LEVEL_MESSAGE_SIZE = 64;

    private final static Font INSTRUCTION_FONT = new Font(DEFAULT_FONT,INSTRUCTION_FONT_SIZE);
    private final static Font TITLE_FONT = new Font(DEFAULT_FONT,TITLE_SIZE);
    private final static Font LEVEL_MESSAGE_FONT = new Font(DEFAULT_FONT,LEVEL_MESSAGE_SIZE);

    private final static String LOSE = "GAME OVER!";
    private final static String WIN = "LEVEL COMPLETE!";

    /**
     * return the file name of level 0
     */
    public String getLevelFile(){
        return LEVEL_FILE;
    }

    public void drawMessage(){
        TITLE_FONT.drawString(GAME_TITLE,TITLE_X,TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION, TITLE_X+TITLE_INS_SPACING_X, TITLE_Y+TITLE_INS_SPACING_Y);
    }

    public void drawWinMessage(){
        LEVEL_MESSAGE_FONT.drawString(WIN, (Window.getWidth()/2.0 - (LEVEL_MESSAGE_FONT.getWidth(WIN)/2.0)),
                (Window.getHeight()/2.0 + (LEVEL_MESSAGE_SIZE/2.0)));
    }
    public void drawLoseMessage(){
        LEVEL_MESSAGE_FONT.drawString(LOSE, (Window.getWidth()/2.0 - (LEVEL_MESSAGE_FONT.getWidth(LOSE)/2.0)),
                (Window.getHeight()/2.0 + (LEVEL_MESSAGE_SIZE/2.0)));
    }
}
