package uet.oop.bomberman.entities.mobile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bom extends Entity {
    private long initialTime;
    private long duration;
    private long liveTime;

    public Bom() {
    }

    public Bom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setImg(Sprite.bomb.getFxImage());
        this.initialTime=System.nanoTime();
    }

    @Override
    public void update() {

    }
}
