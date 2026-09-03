package ru.videoplayer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import ru.videoplayer.service.PlayerService;

import java.io.File;

public class PlayerController {

    @FXML
    private MediaView mediaView;

    @FXML
    private Label placeholderLabel;

    private final PlayerService playerService = new PlayerService();

    @FXML
    private void openVideo() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open video");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Video files",
                        "*.mp4",
                        "*.m4v",
                        "*.mp3",
                        "*.wav"
                )
        );

        File file = fileChooser.showOpenDialog(
                mediaView.getScene().getWindow()
        );

        if (file == null) {
            return;
        }

        playerService.open(file);

        mediaView.setMediaPlayer(
                playerService.getMediaPlayer()
        );

        placeholderLabel.setVisible(false);
    }

    @FXML
    private void play() {
        playerService.play();
    }

    @FXML
    private void pause() {
        playerService.pause();
    }

    @FXML
    private void stop() {
        playerService.stop();
    }
}