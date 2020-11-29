package uet.oop.bomberman.entities.mobile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private boolean moving = false;
    private double speed;
    private int spaceStep = 8;
    private static Sprite prevSprite = null;
    static private int countdown = 0;

    private List<Bom> bombs = new ArrayList<>();

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setCountdown() {
        countdown = 0;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void moveUp() {
        if (countdown == 0) {
            countdown = 3;
            if (Sprite.player_up == prevSprite) {
                prevSprite = Sprite.player_up_1;
            } else if (Sprite.player_up_1 == prevSprite) {
                prevSprite = Sprite.player_up_2;
            } else {
                prevSprite = Sprite.player_up;
            }
        }
        countdown--;
        this.setImg(Bomber.prevSprite.getFxImage());
        if (!checkCollision(x / Sprite.SCALED_SIZE, (y - spaceStep) / Sprite.SCALED_SIZE)
                && !checkCollision((x - 1) / Sprite.SCALED_SIZE + 1, (y - spaceStep) / Sprite.SCALED_SIZE)) {
            y -= (int) this.speed + spaceStep;
        }
    }

    public void moveDown() {
        if (countdown == 0) {
            countdown = 3;
            if (Sprite.player_down == prevSprite) {
                prevSprite = Sprite.player_down_1;
            } else if (Sprite.player_down_1 == prevSprite) {
                prevSprite = Sprite.player_down_2;
            } else {
                prevSprite = Sprite.player_down;
            }
        }
        countdown--;
        this.setImg(Bomber.prevSprite.getFxImage());
        if (!checkCollision(x / Sprite.SCALED_SIZE, (y + Sprite.SCALED_SIZE + spaceStep - 1) / Sprite.SCALED_SIZE)
                && !checkCollision((x - 1) / Sprite.SCALED_SIZE + 1, (y + Sprite.SCALED_SIZE + spaceStep - 1) / Sprite.SCALED_SIZE)) {
            y += (int) this.speed + spaceStep;
        }
    }

    public void moveRight() {
        if (countdown == 0) {
            countdown = 3;
            if (Sprite.player_right == prevSprite) {
                prevSprite = Sprite.player_right_1;
            } else if (Sprite.player_right_1 == prevSprite) {
                prevSprite = Sprite.player_right_2;
            } else {
                prevSprite = Sprite.player_right;
            }
        }
        countdown--;
        this.setImg(Bomber.prevSprite.getFxImage());
        if (!checkCollision((x + Sprite.SCALED_SIZE + spaceStep - 1) / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE)
                && !checkCollision((x + Sprite.SCALED_SIZE + spaceStep - 1) / Sprite.SCALED_SIZE, (y - 1) / Sprite.SCALED_SIZE + 1)) {
            x += (int) this.speed + spaceStep;
        }
    }

    public void moveLeft() {
        if (countdown == 0) {
            countdown = 3;
            if (Sprite.player_left == prevSprite) {
                prevSprite = Sprite.player_left_1;
            } else if (Sprite.player_left_1 == prevSprite) {
                prevSprite = Sprite.player_left_2;
            } else {
                prevSprite = Sprite.player_left;
            }
        }
        countdown--;
        this.setImg(Bomber.prevSprite.getFxImage());
        if (!checkCollision((x - spaceStep) / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE)
                && !checkCollision((x - spaceStep) / Sprite.SCALED_SIZE, (y - 1) / Sprite.SCALED_SIZE + 1)) {
            x -= (int) this.speed + spaceStep;
        }
    }

    public void move(KeyEvent ke, Bomber bomber) {
        switch (ke.getCode()) {
            case UP:
            case W:
                if(!moving) {
                    bomber.setImg(Sprite.player_up.getFxImage());
                    countdown = 0;
                    prevSprite = Sprite.player_up;
                }
                else bomber.moveUp();
                break;

            case DOWN:
            case S:
                if(!moving) {
                    bomber.setImg(Sprite.player_down.getFxImage());
                    countdown = 0;
                    prevSprite = Sprite.player_down;
                }
                else bomber.moveDown();
                break;

            case LEFT:
            case A:
                if(!moving) {
                    bomber.setImg(Sprite.player_left.getFxImage());
                    countdown = 0;
                    prevSprite = Sprite.player_left;
                }
                else bomber.moveLeft();
                break;

            case RIGHT:
            case D:
                if(!moving) {
                    bomber.setImg(Sprite.player_right.getFxImage());
                    countdown = 0;
                    prevSprite = Sprite.player_right;
                }
                else bomber.moveRight();
                break;

            case SPACE:
            case SHIFT:
                Bom bom = new Bom(bomber.getX() / Sprite.SCALED_SIZE, bomber.getY() / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
                bombs.add(bom);
                BombermanGame.mainMap[bomber.getY() / Sprite.SCALED_SIZE][bomber.getX() / Sprite.SCALED_SIZE] = 'b';
                break;
            default:

        }
    }

    public boolean checkCollision(int nextStepX, int nextStepY) {
        char temp = BombermanGame.mainMap[nextStepY][nextStepX];
        if (temp == '#' || temp == '*' || temp == 'b') {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        bombs.forEach(Entity::update);
    }

    @Override
    public void render(GraphicsContext gc) {
        bombs.forEach(g -> g.render(gc));
        super.render(gc);
    }
}
