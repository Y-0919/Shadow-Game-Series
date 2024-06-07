public abstract class LiveObject extends GameObject {
    private double health;
    private InvinciblePower invinciblePower;
    public LiveObject(int x, int y, int speedX, double radius, String imagePath, double health) {
        super(x, y, speedX, radius, imagePath);
        this.health = health;
    }

    public LiveObject(int x, int y, int speedX, String imagePath, double health) {
        super(x, y, speedX, imagePath);
        this.health = health;
    }

    public LiveObject(int x, int y, double radius, String imagePath, double health) {
        super(x, y, radius, imagePath);
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isDead() {
        return getHealth() <= 0;
    }

    public void dead() {
        setSpeedY(2);
    }

    public void setInvinciblePower(InvinciblePower invinciblePower) {
        this.invinciblePower = invinciblePower;
    }

    public InvinciblePower getInvinciblePower() {
        return invinciblePower;
    }
}
