package uet.oop.bomberman.entities.mob.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.enemys.ai.AILow;
import uet.oop.bomberman.entities.mob.enemys.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {
    protected int _animate = 0;

    public Minvo(int x, int y, Image img) {
        super(x, y, img, 2.0);
        this._ai = new AILow();
        _direction  = _ai.calculateDirection();
        this.MAX_STEPS = Sprite.DEFAULT_SIZE;
    }


    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, _animate, 60);
                else
                    _sprite = Sprite.minvo_left1;
                break;
            case 2:
            case 3:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, 60);
                else
                    _sprite = Sprite.minvo_left1;
                break;
        }
    }
    //dùng để cho vào hàm chọn hình ảnh
    public void animate() {
        if (_animate > 6000) _animate = 0;
        else _animate++;
    }



    @Override
    public void kill() {
        this._alive = false;
        img = Sprite.minvo_dead.getFxImage();
        if (timedead < 0) timedead = 40;
        else timedead--;

    }

    @Override
    protected void afterKill() {
        System.out.println(BombermanGame.entities.size());
        BombermanGame.entities.add(new Oneal(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE, Sprite.oneal_right1.getFxImage()));
        System.out.println(BombermanGame.entities.size());
        BombermanGame.entities.remove(this);
    }

}
