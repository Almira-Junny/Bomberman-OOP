package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.SoundEffect;
import uet.oop.bomberman.entities.object.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.BombItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.Item.SpeedItem;
import uet.oop.bomberman.entities.object.Wall;
import uet.oop.bomberman.entities.bomb.BomBang;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bomber extends Mob {

    protected BombermanGame game = new BombermanGame();
    protected Keyboard _input = new Keyboard();
    protected int _animate = 0;
    public static List<Bomb> listBom = new ArrayList<>();
    public static List<BomBang> listBomBang = new ArrayList<>();

    protected static double speed = 2.0;
    protected static int max_bomb = 1;
    protected static boolean flame = false;

    public Bomber(int x, int y, Image img, Keyboard _input) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
        this._input = _input;
        this.rectangle = new Rectangle(this.x, this.y , 30, 32);
    }

    public static List<Bomb> getListBom() {
        return listBom;
    }

    @Override
    public void update() {
        checkDead();
        checkItem();
        if(_alive == false) {
            afterKill();
            return;
        }
        animate();

        calculateMove();


        chooseSprite();

        showBom();
    }

    //dùng để cho vào hàm chọn hình ảnh
    public void animate() {
        if (_animate > 6000) _animate = 0;
        else _animate++;
    }

    // reset properties to default
    protected void reset() {
        speed = 2.0;
        max_bomb = 1;
        flame = false;
    }


    //chọn hình ảnh khi di chuyển
    private void chooseSprite() {
        switch(_direction) {
            case 0:
                img = Sprite.player_up.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.player_up_1.getFxImage(), Sprite.player_up_2.getFxImage(), _animate, 20);
                }
                break;
            case 2:
                img = Sprite.player_down.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.player_down_1.getFxImage(), Sprite.player_down_2.getFxImage(), _animate, 20);
                }
                break;
            case 3:
                img = Sprite.player_left.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.player_left_1.getFxImage(), Sprite.player_left_2.getFxImage(), _animate, 20);
                }
                break;
            default:
                img = Sprite.player_right.getFxImage();
                if(_moving) {
                    img = Sprite.movingSprite(Sprite.player_right_1.getFxImage(), Sprite.player_right_2.getFxImage(), _animate, 20);
                }
                break;
        }
    }

    @Override
    protected void calculateMove() {
        double xa = 0, ya = 0;
        if(_input.up) ya -= speed;
        if(_input.down) ya += speed;
        if(_input.left) xa -= speed;
        if(_input.right) xa += speed;



        if(xa != 0 || ya != 0)  {
                move(xa , ya);

            _moving = true;
        } else {
            _moving = false;
        }

    }

    @Override
    protected void move(double xa, double ya) {
        if(xa > 0) _direction = 1;
        if(xa < 0) _direction = 3;
        if(ya > 0) _direction = 2;
        if(ya < 0) _direction = 0;

        if(canMove(this.rectangle)) {
            if (canMove(new Rectangle(x, (int) (y + ya), 30, 32)) ) {
                y += ya;
                this.rectangle = new Rectangle(x, y , 30, 32);
                //System.out.println(rectangle);
            } else {
                rounding2();
            }

        }

        if(canMove(this.rectangle)) {
            if (canMove(new Rectangle((int) (x + xa), y, 30, 32)) ) {
                x += xa;
                this.rectangle = new Rectangle(x, y , 30, 32);
            } else {
                rounding2();
            }

        }
    }

    @Override
    public void kill() {
        if (!_alive) return;

        this._alive = false;
        this.img = Sprite.player_dead1.getFxImage();

        reset();

    }

    @Override
    protected void afterKill() {
        BombermanGame.restartLevel();
    }

    @Override
    protected boolean canMove(Rectangle rec) {
        Entity a = game.getEntity(rec);
        Entity t = BombermanGame.checkCollisionItem(rec);
        if (a != null && (a instanceof Wall)) {
            return false;
        }
        if (t != null && (t instanceof Portal || t instanceof Brick)) {
            if (t instanceof Portal) {
                if (((Portal) t).getState() != 2) {
                    return false;
                } else {
                    reset();
                    BombermanGame.nextLevel();
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    //thêm bom vào stillobjects
    public void showBom() {
        if (_input.space) {
            if (listBom.size() < max_bomb) {
                Bomb bom = new Bomb(rounding(x*1.0 / Sprite.SCALED_SIZE), rounding(y*1.0 / Sprite.SCALED_SIZE)
                        , Sprite.bomb.getFxImage(), 150);
                if (listBom.size() == 0 ) {
                    listBom.add(bom);
                    BombermanGame.stillObjects.add(bom);
                    SoundEffect.sound(SoundEffect.mediaPlayerPlaceBomb);
                } else {
                    if (checkListBom(bom) == false) {
                        listBom.add(bom);
                        BombermanGame.stillObjects.add(bom);
                        SoundEffect.sound(SoundEffect.mediaPlayerPlaceBomb);

                    }
                }

            }

        }
    }

    //kiểm tra những vị trí quả bom hiện tại
    public boolean checkListBom(Bomb b) {
        for (int i = 0; i < listBom.size(); i++) {
            if (b.getRectangle().intersects(listBom.get(i).rectangle)) {
                return true;
            }
        }

        return false;
    }

    //set đếm ngược cho bom và tạo bom nổ
    public static void deadLineAllBom() {
        for (int i = 0; i < listBom.size(); i++) {
            Bomb t = listBom.get(i);
            if (t.time > 0) {
                listBom.get(i).deadLineBom();
            } else {
                listBom.remove(i);
                BombermanGame.stillObjects.remove(t);
                if (flame == false) {
                    listBomBang.add(new BomBang(t.x, t.y,25));
                } else {
                    listBomBang.add(new BomBang(t.x, t.y,25, 0));
                }

                BombermanGame.stillObjects.addAll(listBomBang);
                SoundEffect.sound(SoundEffect.mediaPlayerBombExploded);
            }

        }

        for (int i = 0; i < listBomBang.size(); i++) {
            BomBang t = listBomBang.get(i);

            if (t.time > 0) {
                listBomBang.get(i).deadLineBomBang();
            } else {
                listBomBang.remove(i);
                BombermanGame.stillObjects.remove(t);
            }
        }
    }

    //kiểm tra chết
    public void checkDead() {
        Entity c = BombermanGame.getEntity(rectangle);
        if (c instanceof BomBang) {
            kill();
            SoundEffect.sound(SoundEffect.mediaPlayerDie);

        }

        if (BombermanGame.checkCollisionEnemy(rectangle)) {
            kill();
            SoundEffect.sound(SoundEffect.mediaPlayerDie);
        }
    }

    //check va cham item
    public void checkItem() {
        Entity t = BombermanGame.checkCollisionItem(this.rectangle);
        if (t instanceof SpeedItem) {
            speed ++;
            ((SpeedItem) t).afterCollision();
            SoundEffect.sound(SoundEffect.mediaPlayerEatItem);
        }

        if (t instanceof FlameItem) {
            flame = true;
            ((FlameItem) t).afterCollision();
            SoundEffect.sound(SoundEffect.mediaPlayerEatItem);
        }

        if (t instanceof BombItem) {
            max_bomb ++;
            ((BombItem) t).afterCollision();
            SoundEffect.sound(SoundEffect.mediaPlayerEatItem);
        }

        if (t instanceof Portal) {
            SoundEffect.sound(SoundEffect.mediaPlayerEatItem);
        }
    }

    //làm tròn số để đặt bom
    public int rounding(double s) {
        if (s - (int) s > 0.5) {
            return (int) (s + 1);
        } else {
            return (int) s;
        }
    }

    public void rounding2() {
        if (x % 32 >= 25) {
            x = ((x / 32) + 1) * 32;
        }

        if (y % 32 >= 25) {
            y = ((y / 32) + 1) * 32;
        }

        if (x % 32 <= 7) {
            x =  (x / 32) * 32;
        }

        if (y % 32 <= 7) {
            y =  (y / 32) * 32;
        }
        this.rectangle = new Rectangle(x, y , 30, 32);
        return;
    }
}
