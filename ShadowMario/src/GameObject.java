import bagel.Image;
import bagel.Input;
import bagel.Keys;

public abstract class GameObject {
    private int x;
    private int y;
    private final int initialX;
    private final int initialY;
    private double radius;
    private int currentSpeedX;
    private int speedX;
    private int speedY;
    private int moveDirection;
    private Image image;

    public GameObject(int x, int y, int speedX, double radius, String imagePath) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.initialX = x;
        this.initialY = y;
        this.speedX = speedX;
        this.image = new Image(imagePath);
    }

    public GameObject(int x, int y, int speedX, String imagePath) {
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.speedX = speedX;
        this.image = new Image(imagePath);
    }

    public GameObject(int x, int y, double radius, String imagePath) {
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.radius = radius;
        this.image = new Image(imagePath);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    public void increaseSpeedY(int increment) {
        speedY += increment;
    }

    public void update(Input input) {
        adjustToPlayerMovement(input);

        move();
        draw();
    }

    public void move() {
        this.x += speedX * moveDirection;
        this.y += speedY;
    }

    public void draw() {
        image.draw(x, y);
    }

    public void adjustToPlayerMovement(Input input) {
        if (input.wasPressed(Keys.RIGHT)) {
            moveDirection = -1;
        } else if(input.wasPressed(Keys.LEFT)) {
            moveDirection = 1;
        } else if(input.wasReleased(Keys.RIGHT)) {
            moveDirection = 0;
        } else if(input.wasReleased(Keys.LEFT)) {
            moveDirection = 0;
        }
    }

    public boolean hasCollidedWith(GameObject obj) {
        double collisionDistance = this.radius + obj.radius;
        double currDistance = distanceTo(obj);
        return currDistance <= collisionDistance;
    }

    private double distanceTo(GameObject obj) {
        return Math.sqrt(Math.pow(this.x - obj.x, 2) + Math.pow(this.y - obj.y, 2));
    }

    public void setImage(String imagePath) {
        this.image = new Image(imagePath);
    }

    public int getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(int moveDirection) {
        this.moveDirection = moveDirection;
    }

}
