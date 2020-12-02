package uet.oop.bomberman.entities.mob.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.mob.enemys.ai.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {

    protected int _animate = 0;

    public Doll(int x, int y, Image img) {
        super(x, y, img, 4.0,100);
        this._ai = new AILow();
        this.MAX_STEPS = Sprite.DEFAULT_SIZE * 2;
        _direction=_ai.calculateDirection();
    }


    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
                else
                    _sprite = Sprite.doll_left1;
                break;
            case 2:
            case 3:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
                else
                    _sprite = Sprite.doll_left1;
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
        img = Sprite.doll_dead.getFxImage();
        if (timedead < 0) timedead = 40;
        else timedead--;

    }

    @Override
    protected void afterKill() {
        BombermanGame.entities.remove(this);
    }

}
