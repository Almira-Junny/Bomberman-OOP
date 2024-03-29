package uet.oop.bomberman;


import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class SoundEffect extends Application {
    //backsoundsrc\Sound\backSound.mp3
    public static Media mediaBackSound = new Media(new File("src\\Sound\\backSound.mp3")
            .toURI().toString());
    public static MediaPlayer mediaPlayerbacksound = new MediaPlayer(mediaBackSound);

    //EatItem
    public static Media mediaEatItem = new Media(new File("src\\Sound\\item.wav")
            .toURI().toString());
    public static MediaPlayer mediaPlayerEatItem = new MediaPlayer(mediaEatItem);

    //BombExploded
    public static Media mediaBombExploded = new Media(new File("src\\Sound\\bombExplode.wav")
            .toURI().toString());
    public static MediaPlayer mediaPlayerBombExploded = new MediaPlayer(mediaBombExploded);

    public static Media mediaPlaceBomb = new Media(new File("src\\Sound\\newbomb.wav")
            .toURI().toString());
    public static MediaPlayer mediaPlayerPlaceBomb = new MediaPlayer(mediaPlaceBomb);

    public static Media mediaBomberDie = new Media(new File("src\\Sound\\bomber_die.wav")
            .toURI().toString());
    public static MediaPlayer mediaPlayerDie = new MediaPlayer(mediaBomberDie);


    public static void sound(MediaPlayer mp) {
        mp.play();
        mp.seek(mp.getStartTime());

    }

    @Override
    public void start(Stage arg0) throws Exception {
    }
}
