import bagel.Font;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import java.util.ArrayList;

public abstract class Level {
    // parameters for displaying messages
    private final static int FONT_SIZE = 55;
    private final static int FONT_Y_POS = 402;
    private final static int INSTRUCTION_OFFSET = 70;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);

    // messages
    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String ATTACK_MESSAGE = "PRESS S TO ATTACK";

    private ArrayList<StationaryEntity> blocks = new ArrayList<>();
    private ArrayList<Pirate> pirates = new ArrayList<>();
    private Sailor sailor;
    private Point topLeft;
    private Point bottomRight;

    private boolean gameOn;
    private boolean gameWin;
    private boolean gameEnd;
    private boolean levelUp;
    private boolean hasLevelUp;

    public abstract void readCSV();

    public void drawStartScreen(Input input) {
        FONT.drawString(START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(START_MESSAGE)/2.0)), FONT_Y_POS);
        FONT.drawString(ATTACK_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(ATTACK_MESSAGE)/2.0)),
                (FONT_Y_POS + INSTRUCTION_OFFSET));
        if (input.wasPressed(Keys.SPACE)) {
            setGameOn(true);
        }
    }

    public void run(Input input) {
        if (sailor.isDead()) {
            gameEnd = true;
        }
    }

    public ArrayList<StationaryEntity> getBlocks() {
        return blocks;
    }

    public ArrayList<Pirate> getPirates() {
        return pirates;
    }

    public Font getFont() {
        return FONT;
    }

    public int getFontYPos() {
        return FONT_Y_POS;
    }

    public int getInstructionOffset() {
        return INSTRUCTION_OFFSET;
    }

    public Sailor getSailor() {
        return sailor;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public boolean gameOn() {
        return gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public boolean gameWin() {
        return gameWin;
    }

    public void setGameWin(boolean gameWin) {
        this.gameWin = gameWin;
    }

    public boolean gameEnd() {
        return gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public boolean levelUp() {
        return levelUp;
    }

    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }

    public boolean hasLevelUp() {
        return hasLevelUp;
    }

    public void setHasLevelUp(boolean hasLevelUp) {
        this.hasLevelUp = hasLevelUp;
    }

    public void setSailor(Sailor sailor) {
        this.sailor = sailor;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }
}
