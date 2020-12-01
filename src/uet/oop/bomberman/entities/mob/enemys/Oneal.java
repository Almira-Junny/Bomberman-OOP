package uet.oop.bomberman.entities.mob.enemys;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.enemys.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;


public class Oneal extends Enemy {
    protected Random random = new Random();
    protected int speedTime = 30;
    public Oneal(int x, int y, Image img) {
        super(x, y, img, 2.0,150);
        this._ai = new AIMedium((Bomber) BombermanGame.player, this);
        _direction  = _ai.calculateDirection();
        this.MAX_STEPS = Sprite.DEFAULT_SIZE;
    }

    

    @Override
    public void kill() {
        this._alive = false;
        img = Sprite.oneal_dead.getFxImage();
        if (timedead < 0) timedead = 40;
        else timedead--;
    }

    @Override
    public void update() {
        super.update();
        speedTimed();
    }

    public void speedTimed() {
        speedTime--;
        if (speedTime < 0) {
            speedTime = 30;
            int r = random.nextInt(1);
            this._speed = 2 + r;
        }
    }
    @Override
    protected void afterKill() {
        BombermanGame.entities.remove(this);
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
                else
                    _sprite = Sprite.oneal_left1;
                break;
            case 2:
            case 3:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
                else
                    _sprite = Sprite.oneal_left1;
                break;
        }
    }
}
