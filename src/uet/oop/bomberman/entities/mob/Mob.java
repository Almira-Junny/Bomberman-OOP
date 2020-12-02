package uet.oop.bomberman.entities.mob;

import javafx.stage.Screen;
import uet.oop.bomberman.entities.Entity;

import java.awt.*;

public abstract class Mob extends Entity {
    protected int _direction = -1;
    protected boolean _alive = true;
    protected boolean _moving = false;

    public Mob(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public Mob() {

    }



    @Override
    public abstract void update();

    protected abstract void calculateMove();

    protected abstract void move(double xa, double ya);

    public abstract void kill();

    protected abstract void afterKill();

    protected abstract boolean canMove(Rectangle rec);

    public boolean isAlive() {
        return _alive;
    }

    public boolean isMoving() {
        return _moving;
    }

    public int getDirection() {
        return _direction;
    }


}
