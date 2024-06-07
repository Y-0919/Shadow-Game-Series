import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;

public class Level {
    private final Font SCORE_FONT = new Font(ShadowDance.FONT_FILE, 30);
    private final Font MESSAGE_FONT = new Font(ShadowDance.FONT_FILE, 64);
    private final Font INSTRUCTION_FONT = new Font(ShadowDance.FONT_FILE, 24);
    private final static int SCORE_LOCATION = 35;
    private final static int MESSAGE_Y = 300;
    private static final int INSTRUCTION_Y = 500;
    private static final String CLEAR_MESSAGE = "CLEAR!";
    private static final String TRY_AGAIN_MESSAGE = "TRY AGAIN";
    private static final String INSTRUCTION_MESSAGE = "Press Space to Return to Level Selection";
    private static final int LEVEL_1_CLEAR_SCORE = 150;
    private static final int LEVEL_2_CLEAR_SCORE = 400;
    private final Accuracy accuracy = new Accuracy();
    private String csvFile;
    private final HashMap<String, Lane> lanes = new HashMap<>();
    private int score = 0;
    private Track track;
    private boolean started = false;
    private boolean finished = false;
    private boolean paused = false;
    private int clearScore;
    private int currFrame = 0;
    private static int scoreMultiplier = 1;

    public Level(int level) {
        csvFile = "res/level" + level +".csv";
        readCsv();
        track = new Track(level);
        switch (level) {
            case 1:
                clearScore = LEVEL_1_CLEAR_SCORE;
                break;
            case 2:
                clearScore = LEVEL_2_CLEAR_SCORE;
                break;
        }
        scoreMultiplier = 1;
        Note.resetSpeed();
    }

    public Level(int level, int clearScore) {
        csvFile = "res/level" + level +".csv";
        readCsv();
        track = new Track(level);
        this.clearScore = clearScore;
        scoreMultiplier = 1;
    }

    public Collection<Lane> getLanes() {
        return lanes.values();
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isFinished() {
        return finished;
    }

    private void readCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String textRead;
            while ((textRead = br.readLine()) != null) {
                String[] splitText = textRead.split(",");

                if (splitText[0].equals("Lane")) {
                    String laneType = splitText[1];
                    int pos = Integer.parseInt(splitText[2]);
                    Lane lane;
                    if (splitText[1].equals("Special")) {
                        lane = new SpecialLane(pos);
                    } else {
                        lane = new NormalLane(laneType, pos);
                    }
                    lanes.put(laneType, lane);
                } else {
                    String laneType = splitText[0];
                    Lane lane = lanes.get(laneType);
                    int appearanceFrame = Integer.parseInt(splitText[2]);

                    if (lane != null) {
                        int x = lane.getLocation();
                        switch (splitText[1]) {
                            case "Normal":
                                NormalNote note = new NormalNote(laneType, appearanceFrame, x);
                                lane.addNote(note);
                                break;
                            case "Hold":
                                HoldNote holdNote = new HoldNote(laneType, appearanceFrame, x);
                                lane.addNote(holdNote);
                                break;
                            case "Bomb":
                                BombNote bombNote = new BombNote(appearanceFrame, x);
                                lane.addNote(bombNote);
                                break;
                            case "SpeedUp":
                                SpeedChangeNote speedUpNote = new SpeedChangeNote(true, appearanceFrame, x);
                                lane.addNote(speedUpNote);
                                break;
                            case "SlowDown":
                                SpeedChangeNote slowDownNote = new SpeedChangeNote(false, appearanceFrame, x);
                                lane.addNote(slowDownNote);
                                break;
                            case "DoubleScore":
                                DoubleScoreNote doubleNote = new DoubleScoreNote(appearanceFrame, x);
                                lane.addNote(doubleNote);
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public void update(Input input) {
        if (!started) {
            currFrame = 0;
            started = true;
            track.start();
        } else if (finished) {
            String message;
            if (score >= clearScore) {
                message = CLEAR_MESSAGE;
            } else {
                message = TRY_AGAIN_MESSAGE;
            }

            MESSAGE_FONT.drawString(message,
                    Window.getWidth() / 2 - MESSAGE_FONT.getWidth(message)/2,
                    MESSAGE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE,
                    Window.getWidth() / 2 - INSTRUCTION_FONT.getWidth(INSTRUCTION_MESSAGE)/2,
                    INSTRUCTION_Y);
        } else {
            SCORE_FONT.drawString("Score " + score, SCORE_LOCATION, SCORE_LOCATION);

            if (paused) {
                if (input.wasPressed(Keys.TAB)) {
                    paused = false;
                    track.run();
                }

                for (Lane l : lanes.values()) {
                    l.draw();
                }
            } else {
                currFrame++;

                for (Lane l : lanes.values()) {
                    score += l.update(input, accuracy, currFrame) * scoreMultiplier;
                }
                accuracy.update();
                finished = checkFinished();

                if (input.wasPressed(Keys.TAB)) {
                    paused = true;
                    track.pause();
                }
            }
        }
    }

    public int getCurrFrame() {
        return currFrame;
    }

    public static int getScoreMultiplier() {
        return scoreMultiplier;
    }

    public static void setScoreMultiplier(int scoreMultiplier) {
        Level.scoreMultiplier = scoreMultiplier;
    }

    private boolean checkFinished() {
        for (Lane l : lanes.values()) {
            if (!l.isFinished()) {
                return false;
            }
        }
        return true;
    }
}
