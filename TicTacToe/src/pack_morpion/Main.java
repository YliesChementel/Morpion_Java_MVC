package pack_morpion;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
        primaryStage.setTitle("LE SUPER MORPION");
        Image icon = new Image("file:rss/images/Morpion.jpg");
        primaryStage.getIcons().add(icon);
        
        String audioFile = "file:///../rss/son/Theme.mp4";
        Media media = new Media(new File(audioFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.9);
        mediaPlayer.play();
        
        primaryStage.setScene(new Scene(root, 720, 626));
        primaryStage.setMinWidth(720);
        primaryStage.setMinHeight(626);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}