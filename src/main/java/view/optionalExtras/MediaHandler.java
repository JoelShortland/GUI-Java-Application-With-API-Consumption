package view.optionalExtras;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.AppView;

import java.io.File;

/**
 * Handles the song
 */
public class MediaHandler {
    private Media media;
    private MediaPlayer mediaPlayer;
    private AppView view;
    private boolean playing = false;
    private final String songName = "Avengers_Song.mp3";

    public MediaHandler(AppView view){
        this.view = view;
        this.media = new Media(("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/" + songName).replace(songName, "src" + "/" + "main" + "/" + "resources" + "/" + songName));
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setCycleCount(999999);
    }

    //turn music off/on when its on/off
    public boolean toggle(){
        playing = !playing;
        if (playing){
            play();
        } else{
            pause();
        }
        return playing;
    }

    public void play(){
        mediaPlayer.play();
    }

    public void pause(){
        mediaPlayer.pause();
    }


}
