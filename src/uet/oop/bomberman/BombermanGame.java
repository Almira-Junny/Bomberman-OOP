package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.mob.*;
import uet.oop.bomberman.entities.mob.enemy.Balloon;
import uet.oop.bomberman.entities.mob.enemy.Flame;
import uet.oop.bomberman.entities.mob.enemy.Oneal;
import uet.oop.bomberman.entities.tile.*;
import uet.oop.bomberman.entities.tile.destroyable.BrickTile;
import uet.oop.bomberman.entities.tile.powerup.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BombermanGame extends Application {
    public static int WIDTH;
    public static int HEIGHT;
    public static int LEVEL;
    public static char[][] mainMap;
    private Bomb bom;
    private GraphicsContext gc;
    private Canvas canvas;
    private Bomber bomber;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> dynamicObject = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<GrassTile> grass = new ArrayList<>();

    public BombermanGame() {
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        createMap();
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Bomberman");
        stage.getIcons().add(new Image("/textures/Logo.png"));
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };

        timer.start();
        bomber = new Bomber(1, 1, Sprite.player_right.getFxImage());

        scene.setOnKeyPressed(ke -> {
            bomber.setMoving(true);
            bomber.move(ke, bomber);
        });

        scene.setOnKeyReleased(ke -> {
            bomber.setMoving(false);
            bomber.move(ke, bomber);
        });
    }

    public void transferTxtFileToMap(String str, int height) {
        int length = str.length();
        Entity object = null;
        for (int i = 0; i < length; i++) {
            mainMap[height][i] = str.charAt(i);
            grass.add(new GrassTile(i, height, Sprite.grass.getFxImage()));
            switch (str.charAt(i)) {
                case '1': {
                    object = new Balloon(i, height, Sprite.balloom_left1.getFxImage());
                    dynamicObject.add(object);
                    break;
                }
                case '2': {
                    object = new Oneal(i, height, Sprite.oneal_left1.getFxImage());
                    dynamicObject.add(object);
                    break;
                }
                case '#': {
                    object = new WallTile(i, height, Sprite.wall.getFxImage());
                    stillObjects.add(object);
                    break;
                }
                case '*': {
                    object = new BrickTile(i, height, Sprite.brick.getFxImage());
                    stillObjects.add(object);
                    break;
                }
                case 'x': {
                    object = new PortalTile(i, height, Sprite.portal.getFxImage());
                    stillObjects.add(object);
                    break;
                }

                case 'f': {
                    object = new Flame(i, height, Sprite.powerup_flames.getFxImage());
                    dynamicObject.add(object);
                    break;
                }
                case 's': {
                    object = new SpeedItem(i, height, Sprite.powerup_speed.getFxImage());
                    stillObjects.add(object);
                    break;
                }
                default:
                    object = new GrassTile(i, height, Sprite.grass.getFxImage());
                    stillObjects.add(object);
            }
        }
    }


    public void getMapSize(String str) {
        List<String> mapSize = new ArrayList<>();
        String size = "";
        int length = str.length();
        for (int i = 0; i < length; i++) {
            size += str.charAt(i);
            if (str.charAt(i) == ' ' || i == length - 1) {
                mapSize.add(size.trim());
                size = "";
            }
        }
        LEVEL = Integer.parseInt(mapSize.get(0));
        HEIGHT = Integer.parseInt(mapSize.get(1));
        WIDTH = Integer.parseInt(mapSize.get(2));
        mainMap = new char[HEIGHT][WIDTH];
    }

    public void createMap() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("res/levels/Level1.txt"));
            getMapSize(lines.get(0));
            lines.remove(0);
            AtomicInteger height = new AtomicInteger();
            lines.forEach(line -> {
                transferTxtFileToMap(line, height.get());
                height.incrementAndGet();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        bomber.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grass.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        dynamicObject.forEach(g -> g.render(gc));
        bomber.render(gc);
    }
}
