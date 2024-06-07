import bagel.Input;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Level {
    final Properties props;
    private int score;
    private boolean finished = false;
    private int currFrame = 0;
    private Player player;
    private Platform platform;
    private EnemyBoss enemyBoss;
    private List<Enemy> enemies = new ArrayList<>();
    private List<FlyingPlatform> flyingPlatforms = new ArrayList<>();
    private List<DoubleScorePower> doubleScorePowers = new ArrayList<>();
    private List<InvinciblePower> invinciblePowers = new ArrayList<>();
    private List<Coin> coins = new ArrayList<>();
    private EndFlag endFlag;

    public Level(int level, Properties props) {
        this.props = props;
        ArrayList<String[]> lines = IOUtils.readCsv(props.getProperty(String.format("level%dFile", level)));
        populateGameObjects(lines);
    }

    private void populateGameObjects(ArrayList<String[]> lines) {
        for(String[] lineElement: lines) {
            int x = Integer.parseInt(lineElement[1]);
            int y = Integer.parseInt(lineElement[2]);

            if(lineElement[0].equals(GameObjectType.PLAYER.name())) {
                player = new Player(x, y, this.props);
            } else if(lineElement[0].equals(GameObjectType.PLATFORM.name())) {
                platform = new Platform(x, y, this.props);
            } else if(lineElement[0].equals(GameObjectType.ENEMY.name())) {
                Enemy enemy = new Enemy(x, y, props);
                enemies.add(enemy);
            } else if(lineElement[0].equals(GameObjectType.FLYING_PLATFORM.name())) {
                FlyingPlatform flyingPlatform = new FlyingPlatform(x, y, props);
                flyingPlatforms.add(flyingPlatform);
            } else if(lineElement[0].equals(GameObjectType.COIN.name())) {
                Coin coin = new Coin(x, y, props);
                coins.add(coin);
            } else if(lineElement[0].equals(GameObjectType.DOUBLE_SCORE.name())) {
                DoubleScorePower doubleScorePower = new DoubleScorePower(x, y, props);
                doubleScorePowers.add(doubleScorePower);
            } else if(lineElement[0].equals(GameObjectType.INVINCIBLE_POWER.name())) {
                InvinciblePower invinciblePower = new InvinciblePower(x, y, props);
                invinciblePowers.add(invinciblePower);
            } else if(lineElement[0].equals(GameObjectType.ENEMY_BOSS.name())) {
                enemyBoss = new EnemyBoss(x, y, props);
            } else if(lineElement[0].equals(GameObjectType.END_FLAG.name())) {
                endFlag = new EndFlag(x, y, props);
            }
        }
    }

    public void update(Input input) {
        currFrame++;

        platform.update(input);

        for(Enemy e: enemies) {
            e.updateWithTarget(input, player);
        }

        for(FlyingPlatform fp: flyingPlatforms) {
            fp.update(input);
        }

        for(Coin c: coins) {
            score += c.update(input, player);
        }

        for(DoubleScorePower dsp: doubleScorePowers) {
            if(dsp.updateWithTarget(input, player)) {
                player.setDoubleScorePower(dsp);
            }
        }

        for(InvinciblePower ip: invinciblePowers) {
            if(ip.updateWithTarget(input, player)) {
                player.setInvinciblePower(ip);
            }
        }

        player.update(input, flyingPlatforms, enemyBoss);

        if(enemyBoss != null) {
            enemyBoss.updateWithTargetAndCurrFrame(input, player, currFrame);
        }

        endFlag.update(input, player);

        if(endFlag.isCollided()) {
            if(enemyBoss != null && enemyBoss.isDead()) {
                finished = true;
            } else if(enemyBoss == null) {
                finished = true;
            }
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isPlayerDeadAndFallen(int windowsHeight) {
        return player.isDead() && player.getY() > windowsHeight;
    }

    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return player;
    }
}
