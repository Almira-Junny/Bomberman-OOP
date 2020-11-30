package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private long initialTime;
    private long duration;
    private long liveTime;

    public Bomb() {
    }

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setImg(Sprite.bomb.getFxImage());
        this.initialTime=System.nanoTime();
    }

    @Override
    public void update() {

    }
}
