import bagel.*;

public abstract class GameObject {
    private double x;
    private double y;
    private Image image;
    private double radius;
    private boolean active;

    public GameObject(double x, double y, String imageLocation) {
        this.x = x;
        this.y = y;
        this.image = new Image(imageLocation);
        this.radius = (image.getHeight() + image.getWidth()) / 2;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        active = false;
    }

    public boolean hasCollidedWith(GameObject o) {
        if (this.isActive() && o.isActive()) {
            double collisionDistance = this.radius + o.radius;
            double currDistance = distanceTo(o);
            return currDistance <= collisionDistance;
        }

        return false;
    }

    public double distanceTo(GameObject o) {
        return Math.sqrt(Math.pow(this.x - o.x, 2) + Math.pow(this.y - o.y, 2));
    }

    public void draw() {
        if (active) {
            image.draw(x, y);
        }
    }

    public void renderWithRotation(DrawOptions rotation) {
        if (active) {
            image.draw(x, y, rotation);
        }
    }
}

