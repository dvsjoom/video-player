module ru.videoplayer {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens ru.videoplayer.controller to javafx.fxml;

    exports ru.videoplayer;
}