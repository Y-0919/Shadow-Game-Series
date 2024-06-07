import bagel.*;

import java.util.Properties;

public class ShadowMario extends AbstractGame {

    public static int WINDOW_HEIGHT;
    private final String GAME_TITLE;
    private final Image BACKGROUND_IMAGE;
    public final String FONT_FILE;
    private final Font TITLE_FONT;
    private final int TITLE_X;
    private final int TITLE_Y;
    private final String INSTRUCTION;
    private final Font INSTRUCTION_FONT;
    private final int INS_Y;
    private final Font MESSAGE_FONT;
    private final int MESSAGE_Y;
    private final Properties props;
    private final Properties message_props;
    private final Font SCORE_FONT;
    private final int SCORE_X;
    private final int SCORE_Y;
    private final Font HEALTH_FONT;
    private final int HEALTH_X;
    private final int HEALTH_Y;
    private Level currLevel = null;

    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
              message_props.getProperty("title"));

        WINDOW_HEIGHT = Integer.parseInt(game_props.getProperty("windowHeight"));
        GAME_TITLE = message_props.getProperty("title");
        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));
        FONT_FILE = game_props.getProperty("font");

        TITLE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("title.fontSize")));
        TITLE_X = Integer.parseInt(game_props.getProperty("title.x"));
        TITLE_Y = Integer.parseInt(game_props.getProperty("title.y"));

        SCORE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("score.fontSize")));
        SCORE_X = Integer.parseInt(game_props.getProperty("score.x"));
        SCORE_Y = Integer.parseInt(game_props.getProperty("score.y"));

        INSTRUCTION = message_props.getProperty("instruction");
        INSTRUCTION_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("instruction.fontSize")));
        INS_Y = Integer.parseInt(game_props.getProperty("instruction.y"));

        MESSAGE_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("message.fontSize")));
        MESSAGE_Y = Integer.parseInt(game_props.getProperty("message.y"));

        HEALTH_FONT = new Font(FONT_FILE, Integer.parseInt(game_props.getProperty("playerHealth.fontSize")));
        HEALTH_X = Integer.parseInt(game_props.getProperty("playerHealth.x"));
        HEALTH_Y = Integer.parseInt(game_props.getProperty("playerHealth.y"));

        this.props = game_props;
        this.message_props = message_props;
    }

    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if (currLevel == null) {
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTION,
                    Window.getWidth() / 2 - INSTRUCTION_FONT.getWidth(INSTRUCTION)/2, INS_Y);

            if (input.wasPressed(Keys.NUM_1)) {
                currLevel = new Level(1, props);
            } else if (input.wasPressed(Keys.NUM_2)) {
                currLevel = new Level(2, props);
            } else if (input.wasPressed(Keys.NUM_3)) {
                currLevel = new Level(3, props);
            }
        } else if(currLevel.isPlayerDeadAndFallen(WINDOW_HEIGHT)) {
            String message = message_props.getProperty("gameOver");
            MESSAGE_FONT.drawString(message,
                    Window.getWidth() / 2 - MESSAGE_FONT.getWidth(message)/2,
                    MESSAGE_Y);
            if (input.wasPressed(Keys.SPACE)) {
                currLevel = null;
            }
        } else {
            if (currLevel.isFinished()) {
                String message = message_props.getProperty("gameWon");
                MESSAGE_FONT.drawString(message,
                        Window.getWidth() / 2 - MESSAGE_FONT.getWidth(message)/2,
                        MESSAGE_Y);
                if(input.wasPressed(Keys.SPACE)) {
                    currLevel = null;
                }
            } else {
                SCORE_FONT.drawString(message_props.getProperty("score")+ currLevel.getScore(), SCORE_X, SCORE_Y);
                HEALTH_FONT.drawString(message_props.getProperty("health") + Math.round(currLevel.getPlayer().
                                getHealth()*100), HEALTH_X, HEALTH_Y);
                currLevel.update(input);
            }
        }

    }
}
