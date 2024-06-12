package io.github.oliviercailloux.boardbusters.game.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The MusicPlayer class is responsible for playing background music and sound effects in the game GUI.
 * It uses the JavaFX MediaPlayer to play audio files.
 */
public class MusicPlayer {
    private static MusicPlayer instance;
    private MediaPlayer mediaPlayer;

    private MusicPlayer(){
    }

    public static MusicPlayer getInstance(){
        if(instance == null){
            instance = new MusicPlayer();
        }
        return instance;
    }

    /**
     * Sets the background music to be played by the MusicPlayer.
     * If there is already a music playing, it stops the current music and replaces it with the new one.
     *
     * @param musicFilePath the file path of the music file
     */
    public void setMusic(String musicFilePath){
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
        Media musicMedia = new Media(musicFilePath);
        this.mediaPlayer = new MediaPlayer(musicMedia);
    }

    /**
     * Plays the background music.
     * If there is no background music set, this method has no effect.
     */
    public void playMusic(){
        if(mediaPlayer != null){
            mediaPlayer.setVolume(1);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    /**
     * Plays a sound effect.
     *
     * @param soundEffectPath the file path of the sound effect
     */
    public void playSoundEffect(String soundEffectPath){
        Media soundEffectMedia = new Media(soundEffectPath);
        mediaPlayer = new MediaPlayer(soundEffectMedia);
        mediaPlayer.play();
    }
}

