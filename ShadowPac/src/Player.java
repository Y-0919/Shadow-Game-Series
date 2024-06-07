import bagel.*;
import bagel.util.*;
import java.lang.Math;

public class Player extends GameObject {
    private final Image PAC = new Image("res/pac.png");
    private final Image PAC_OPEN = new Image("res/pacOpen.png");
    private final Image HEART = new Image("res/heart.png");

    private final static int HEART_X = 900;
    private final static int HEART_Y = 10;
    private final static int HEART_SPACE = 30;

    private final static int SCORE_X = 25;
    private final static int SCORE_Y = 25;
    private final int SCORE_SIZE = 20;

    private final static String FONT = "res/FSO8BITR.ttf";
    private final Font scoreFont = new Font(FONT, SCORE_SIZE);

    private final static double FRENZY_BOOST = 1;
    private final static int FRENZY_TIME = 1000;
    private final static int SWITCH_FRAME = 15;
    private final static int SPEED = 3;
    private final static int LIVES = 3;

    private final DrawOptions rotator = new DrawOptions();
    private int lives = LIVES;
    private int counter = SWITCH_FRAME;
    private boolean isFrenzy = false;
    private boolean isOpen = false;
    private static int frenzyTime;
    private Point startPoint;
    private Point prevPosition;
    private int score;

    /**
     * create player in given position
     */
    public Player(Point position){
        super(position);
        if(startPoint == null){
            startPoint = super.getPosition();
        }
    }

    /**
     * set player to frenzy mode
     */
    public void setFrenzy(boolean bool){
        isFrenzy = bool;
    }


    public void resetPosition(){
        super.setPosition(startPoint);
    }

    /**
     * player lose 1 life
     */
    public void reduceLives(){
        lives--;
    }

    /**
     * return number of remaining lives
     */
    public int getLives(){
        return lives;
    }

    /**
     * increase score by given amount
     */
    public void incrementScore(int score){
        this.score += score;
    }

    /**
     * return current score
     */
    public int getScore(){
        return score;
    }


    public void draw(){
        if(isOpen){
            PAC_OPEN.drawFromTopLeft(super.getPosition().x, super.getPosition().y, rotator);
        }else{
            PAC.drawFromTopLeft(super.getPosition().x, super.getPosition().y, rotator);
        }

    }

    /**
     * draw player's lives
     */
    public void drawLives(){
        for (int i = 0; i < lives; i++) {
            HEART.drawFromTopLeft(HEART_X + HEART_SPACE * i, HEART_Y);
        }
    }

    /**
     * draw player's score
     */
    public void drawScore() {
        String scoreStr = "SCORE" + ' ' + Integer.toString(score);
        scoreFont.drawString(scoreStr, SCORE_X, SCORE_Y);
    }

    public void move(Point newPos){
        prevPosition = super.getPosition();
        super.setPosition(new Point(newPos.x, newPos.y));
    }


    public void moveBack(){
        super.setPosition(prevPosition);
    }

    /**
     * Method to update the player status using keyboard, also checks for collision
     */
    public void update(Input input, Level level){
        counter--;
        if(counter == 0){
            counter = SWITCH_FRAME;
            isOpen = !isOpen;
        }
        double posX = super.getPosition().x;
        double posY = super.getPosition().y;
        if (input.isDown( Keys.UP)){
            move( new Point( posX, posY - this.getSpeed()));
            rotator.setRotation(-Math.PI/2);
        } else if (input.isDown( Keys.DOWN)){
            move( new Point( posX, posY + this.getSpeed()));
            rotator.setRotation(Math.PI/2);
        } else if (input.isDown( Keys.LEFT)){
            move( new Point( posX - this.getSpeed(), posY));
            rotator.setRotation(Math.PI);
        } else if (input.isDown(Keys.RIGHT)) {
            move( new Point( posX + this.getSpeed(), posY));
            rotator.setRotation(0);
        }

        level.checkCollisions(this);

    }

    public Rectangle getBoundingBox(){
        if(isOpen) {
            return new Rectangle(super.getPosition(), PAC_OPEN.getWidth(), PAC_OPEN.getHeight());
        } else {
            return new Rectangle(super.getPosition(), PAC.getWidth(), PAC.getHeight());
        }
    }

    /**
     * get remaining frenzy time
     */
    public int getFrenzyTime() {
        return frenzyTime;
    }

    /**
     * set a new frenzy mode period
     */
    public void setFrenzyTime(){
        frenzyTime = FRENZY_TIME;
    }

    /**
     * check if it is in frenzy mode
     */
    public boolean isFrenzy(){
        return isFrenzy;
    }

    /**
     * decrease frenzy time by 1
     */
    public void reduceFrenzyTime(){
        frenzyTime--;
    }

    private double getSpeed(){
        if(isFrenzy){
            return SPEED + FRENZY_BOOST;
        }
        return SPEED;
    }
}
