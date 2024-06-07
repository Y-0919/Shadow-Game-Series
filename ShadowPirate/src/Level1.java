import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.util.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Level1 extends Level {
    private final static String LEVEL1_WORLD_FILE = "res/level1.csv";
    private final Image LEVEL1_BACKGROUND_IMAGE = new Image("res/background1.png");
    private final static String LEVEL1_INSTRUCTION_MESSAGE = "FIND THE TREASURE";

    private final ArrayList<Item> ITEMS = new ArrayList<>();
    private Treasure treasure;

    public Level1() {
        readCSV();
    }

    @Override
    public void readCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LEVEL1_WORLD_FILE))) {
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
                        getBlocks().add(new Bomb(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "Pirate":
                        getPirates().add(new Pirate(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "Blackbeard":
                        getPirates().add(new Blackbeard(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "Treasure":
                        treasure = new Treasure(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "Potion":
                        ITEMS.add(new Potion(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "Elixir":
                        ITEMS.add(new Elixir(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "Sword":
                        ITEMS.add(new Sword(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
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
        getFont().drawString(LEVEL1_INSTRUCTION_MESSAGE,
                (Window.getWidth()/2.0 - (getFont().getWidth(LEVEL1_INSTRUCTION_MESSAGE)/2.0)),
                (getFontYPos() + 2*getInstructionOffset()));
    }

    @Override
    public void run(Input input) {
        super.run(input);

        LEVEL1_BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        treasure.update(getSailor());

        for (Item item : ITEMS) {
            item.update(getSailor());
        }

        for (StationaryEntity bomb : getBlocks()) {
            bomb.update();
        }

        for (Pirate pirate : getPirates()) {
            pirate.update(getBlocks(), getSailor(), getTopLeft(), getBottomRight());
        }

        getSailor().update(input, getBlocks(), getPirates(), getTopLeft(), getBottomRight());

        if (getSailor().hasWon()) {
            setGameWin(true);
        }
    }
}
