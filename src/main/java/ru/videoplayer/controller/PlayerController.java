package ru.videoplayer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import ru.videoplayer.service.PlayerService;

import java.io.File;

public class PlayerController {

    private final PlayerService playerService = new PlayerService();
    @FXML
    private MediaView mediaView;
    @FXML
    private Label placeholderLabel;
    @FXML
    private StackPane videoContainer;
    @FXML
    private Slider progressSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label timeLabel;

    @FXML
    private void openVideo() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open video");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video files", "*.mp4", "*.m4v", "*.mp3", "*.wav"));

        File file = fileChooser.showOpenDialog(mediaView.getScene().getWindow());

        if (file == null) {
            return;
        }

        playerService.open(file);

        mediaView.setMediaPlayer(playerService.getMediaPlayer());

        placeholderLabel.setVisible(false);


        playerService.getMediaPlayer()
                .volumeProperty()
                .bind(volumeSlider.valueProperty());

        volumeSlider.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        playerService.setVolume(newValue.doubleValue())
        );


        playerService.setOnReady(() -> {
            Duration duration =
                    playerService.getMediaPlayer().getTotalDuration();

            progressSlider.setMax(duration.toSeconds());

            timeLabel.setText(
                    "00:00 / " + formatTime(duration)
            );
        });

        playerService.getMediaPlayer()
                .currentTimeProperty()
                .addListener((observable, oldValue, newValue) -> {

                    if (!progressSlider.isValueChanging()) {
                        progressSlider.setValue(
                                newValue.toSeconds()
                        );
                    }

                    Duration currentTime = newValue;

                    Duration totalTime =
                            playerService.getMediaPlayer()
                                    .getTotalDuration();

                    timeLabel.setText(
                            formatTime(currentTime)
                                    + " / "
                                    + formatTime(totalTime)
                    );
                });

        progressSlider.valueChangingProperty()
                .addListener((observable, wasChanging, isChanging) -> {

                    if (!isChanging) {
                        seek();
                    }
                });

        progressSlider.setOnMouseReleased(event -> seek());
    }

    private String formatTime(Duration duration) {

        int totalSeconds = (int) Math.floor(duration.toSeconds());

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        if (hours > 0) {
            return String.format(
                    "%02d:%02d:%02d",
                    hours,
                    minutes,
                    seconds
            );
        }

        return String.format(
                "%02d:%02d",
                minutes,
                seconds
        );
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

    @FXML
    private void initialize() {

        mediaView.setPreserveRatio(true);

        mediaView.fitWidthProperty()
                .bind(videoContainer.widthProperty());

        mediaView.fitHeightProperty()
                .bind(videoContainer.heightProperty());

        mediaView.setSmooth(true);
    }

    private void seek() {

        if (playerService.getMediaPlayer() == null) {
            return;
        }

        Duration newTime =
                Duration.seconds(progressSlider.getValue());

        playerService.getMediaPlayer()
                .seek(newTime);
    }


}