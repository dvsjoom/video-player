package ru.videoplayer.service;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class PlayerService {

    private MediaPlayer mediaPlayer;

    public void open(File file) {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public void setOnReady(Runnable action) {
        if (mediaPlayer != null) {
            mediaPlayer.setOnReady(action::run);
        }
    }
}