package pack_morpion;
import java.io.File;
import java.io.IOException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class PlayButtonController {
	
	@FXML
    private Button playButton;
	
	@FXML
    private StackPane  stackPane;
   
	
	@FXML
	private void initialize() {
		playButton.setOnMousePressed(event -> {
			playButton.setStyle("-fx-background-color: #2b78e4;");
			playButton.setTranslateY(playButton.getTranslateY() - 10);
	    });

		playButton.setOnMouseReleased(event -> {
			playButton.setStyle("");
			playButton.setTranslateY(playButton.getTranslateY() + 10);
	    });
	}
	
    @FXML
    private void handlePlayButtonClicked() {
        try {
        	String audioFile = "file:///../rss/son/son_transition_begin.wav";
            Media media = new Media(new File(audioFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(1);
            mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.9);
            mediaPlayer.play();
            
        	playButton.setDisable(true);
        	Platform.runLater(() -> playButton.setStyle("-fx-opacity: 1;"));
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
            Parent root = loader.load();
            
            Scene scene = playButton.getScene();
            root.translateXProperty().set(scene.getWidth());
            stackPane.getChildren().add(root);
            
            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(root.translateXProperty(), 0,Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            KeyValue keyValue2 = new KeyValue(playButton.translateXProperty(), -scene.getWidth(),Interpolator.EASE_IN);
            KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), keyValue2);
            timeline.getKeyFrames().add(keyFrame2);
            timeline.setOnFinished(event1 -> {
            	stackPane.getChildren().remove(playButton);
            });
            timeline.play();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}