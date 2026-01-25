package model.binpacking;

// import model.binpacking.Position;
import model.core.Item;

public class Rectangle extends Item {

    int id;
    int width;
    int height;
    private boolean isRotated;
    int area;

    private Position position;

    public Rectangle(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.area = width * height;
        this.isRotated = false;
        this.position = new Position(0, 0);
    }

    public int getId() {
        return id;
    }

    public int getArea() {
        return this.area;
    }

    public boolean getIsRotated() {
        return isRotated;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }
}
