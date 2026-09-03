package ru.videoplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VideoPlayerApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(VideoPlayerApplication.class.getResource("/ru/videoplayer/view/player-view.fxml"));

        Scene scene = new Scene(loader.load(), 1000, 650);

        scene.getStylesheets().add(VideoPlayerApplication.class.getResource("/ru/videoplayer/css/player.css").toExternalForm());

        stage.setTitle("Видеоплеер");
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(400);
        stage.setMinHeight(300);
    }


}
