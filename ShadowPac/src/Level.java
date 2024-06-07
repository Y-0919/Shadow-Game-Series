import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import bagel.util.*;
import bagel.*;

public abstract class Level{
    private boolean hasStart = false;
    private boolean hasWin = false;
    private boolean hasLose = false;

    private Player player;

    private final ArrayList<Wall> walls = new ArrayList<Wall>();
    private final ArrayList<Dot> dots = new ArrayList<Dot>();
    private final ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private final ArrayList<Pellet> pellets = new ArrayList<Pellet>();
    private final ArrayList<Cherry> cherries = new ArrayList<Cherry>();

    /**
     * get the map of the level
     */
    abstract public String getLevelFile();

    /**
     * find out whether a level is started
     */
    public boolean isHasStart() {
        return hasStart;
    }

    /**
     * set the level to start or stop
     */
    public void setHasStart(boolean hasStart) {
        this.hasStart = hasStart;
    }

    /**
     * check if it is wined
     */
    public boolean isWin() {
        return hasWin;
    }

    /**
     * check if player loses
     */
    public boolean isLose() {
        return hasLose;
    }

    /**
     * Method used to read file and create objects
     * Code implemented from Project 1 Sample solution provided by teaching team.
     */

    public void readCSV(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Player":
                        player = new Player(new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "Ghost":
                    case "GhostRed":
                        ghosts.add(
                                new RedGhost(new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]))));
                        break;
                    case "GhostBlue":
                        ghosts.add(
                                new BlueGhost(new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]))));
                        break;
                    case "GhostGreen":
                        ghosts.add(new GreenGhost(
                                new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]))));
                        break;
                    case "GhostPink":
                        ghosts.add(
                                new PinkGhost(new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]))));
                        break;
                    case "Dot":
                        dots.add(
                                new Dot(new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]))));
                        break;
                    case "Wall":
                        walls.add(new Wall(new Point(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]))));
                        break;
                    case "Pellet":
                        pellets.add(new Pellet(new Point(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]))));
                        break;
                    case "Cherry":
                        cherries.add(new Cherry(new Point(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]))));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

    }


    /**
     * method to start playing the game, draw the objects and update their position
     */
    public void levelStart(Input input, Level level){
        // when player ends frenzy mode
        if(player.isFrenzy()){
            player.reduceFrenzyTime();
            if(player.getFrenzyTime() == 0){
                player.setFrenzy(false);
                // ghosts also get reset
                for(Ghost current: ghosts){
                    current.setFrenzy(false);
                    if(!current.isActive()) {
                        current.resetPosition();
                        current.setActive(true);
                    }
                }
            }
        }

        // check if the player has at least 1 life left
        if(player.getLives() == 0){
            level.drawLoseMessage();
            this.hasLose = true;
            return;
        }

        // check whether score equals total value of dots
        if(level instanceof Level0) {
            // if player eats all the dot
            if (player.getScore() == (dots.size())*(dots.get(0).getValue())){
                this.hasWin = true;
                return;
            }
        }else if(level instanceof Level1){
            if(player.getScore() == ((Level1) level).getWinningScore()){
                this.hasWin = true;
                return;
            }
        }
        for(Wall current: walls){
            current.draw();
        }
        for(Dot current: dots){
            current.draw();
        }
        player.draw();
        for(Ghost current: ghosts){
            current.draw();
        }
        for(Cherry current: cherries){
            current.draw();
        }
        for(Pellet current: pellets){
            current.draw();
        }
        player.drawLives();
        player.drawScore();
        player.update(input, level);

        if(level instanceof Level1){
            for(Ghost current: ghosts){
                checkCollisions(current);
                current.move();
            }
        }
    }



    /**
     * Check what object is player colliding with and update them
     * Code implemented from project 1 solution
     */
    public void checkCollisions(Player player){

        Rectangle playerBox = player.getBoundingBox();

        for (Ghost current: ghosts){
            Rectangle ghostBox = current.getBoundingBox();
            if (playerBox.intersects(ghostBox) && current.isActive()){
                if(!player.isFrenzy()) {
                    player.reduceLives();
                    player.resetPosition();
                    current.resetPosition();
                } else {
                    player.incrementScore(Ghost.getValue());
                    current.setActive(false);
                }
            }
        }

        for (Dot current: dots){
            Rectangle dotBox = current.getBoundingBox();
            if (current.isActive() && playerBox.intersects(dotBox)) {
                player.incrementScore(current.getValue());
                current.setActive(false);
            }
        }

        for (Wall current: walls){
            Rectangle wallBox = current.getBoundingBox();
            if (playerBox.intersects(wallBox)){
                player.moveBack();
            }
        }

        for (Cherry current: cherries){
            Rectangle cherryBox = current.getBoundingBox();
            if (playerBox.intersects(cherryBox) && current.isActive()){
                current.setActive(false);
                player.incrementScore(Cherry.getScore());
            }
        }

        for(Pellet current: pellets){
            Rectangle pelletBox = current.getBoundingBox();
            if(playerBox.intersects(pelletBox) && current.isActive()){
                current.setActive(false);
                player.setFrenzy(true);
                player.setFrenzyTime();
                for(Ghost currentGhost: ghosts){
                    currentGhost.setFrenzy(true);
                }
            }
        }
    }

    /**
     * Method that checks the collision of ghosts
     */
    public void checkCollisions(Ghost ghost) {
        Rectangle ghostBox = new Rectangle(ghost.getBoundingBox());
        for (Wall current : walls) {
            Rectangle wallBox = current.getBoundingBox();
            if (ghostBox.intersects(wallBox)) {
                ghost.moveBack();
                ghost.changeDirection();
            }
        }

    }

    /**
     * print wining message when game is finished
     */
    abstract public void drawWinMessage();

    /**
     * print losing message when game is finished
     */
    abstract public void drawLoseMessage();

    /**
     * print messages before playing (instructions, title)
     */
    abstract public void drawMessage();
}


