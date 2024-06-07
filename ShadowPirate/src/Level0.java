import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Level0 extends Level {
    private final static String LEVEL0_WORLD_FILE = "res/level0.csv";
    private final Image LEVEL0_BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static String LEVEL0_INSTRUCTION_MESSAGE = "USE ARROW KEYS TO FIND LADDER";

    public Level0() {
        readCSV();
    }

    @Override
    public void readCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LEVEL0_WORLD_FILE))) {
            String line;
            if ((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                if (sections[0].equals("Sailor")) {
                    setSailor(new Sailor(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                }
            }

            while ((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Block":
                        getBlocks().add(new Block(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "Pirate":
                        getPirates().add(new Pirate(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "TopLeft":
                        setTopLeft(new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "BottomRight":
                        setBottomRight(new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    @Override
    public void drawStartScreen(Input input) {
        super.drawStartScreen(input);
        getFont().drawString(LEVEL0_INSTRUCTION_MESSAGE,
                (Window.getWidth()/2.0 - (getFont().getWidth(LEVEL0_INSTRUCTION_MESSAGE)/2.0)),
                (getFontYPos() + 2*getInstructionOffset()));
    }


    @Override
    public void run(Input input) {
        super.run(input);

        LEVEL0_BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        for (StationaryEntity block : getBlocks()) {
            block.update();
        }

        for (Pirate pirate : getPirates()) {
            pirate.update(getBlocks(), getSailor(), getTopLeft(), getBottomRight());
        }

        getSailor().update(input, getBlocks(), getPirates(), getTopLeft(), getBottomRight());

        if (getSailor().hasCompleteLevel()) {
            setLevelUp(true);
        }
    }
}
