package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    public int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    public int y;

    public Image img;

    public Sprite _sprite;

    public Rectangle rectangle;

    public String name;

    public Entity() {

    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.rectangle = new Rectangle(x, y, (int) img.getWidth(), (int) img.getHeight());
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public int getXTile() {
        return (int) this.x / Sprite.SCALED_SIZE;
    }

    public int getYTile() {
        return (int) this.y / Sprite.SCALED_SIZE;
    }

    public abstract void update();
}
