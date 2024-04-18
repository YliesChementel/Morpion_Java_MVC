
package pack_morpion;
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
import javafx.util.Duration;

/*
 * Controller de la première vue du jeu
 */
public class PlayButtonController extends Action {

	@FXML
	private Button playButton;

	@FXML
	private StackPane  stackPane;

	/*
	 * initialise certains effet sur le bouton play lors d'action
	 */
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
	
	
	/*
	 * Mise en place de l'action du bouton play, avec son transition + animation
	 */
	@FXML
	private void handlePlayButtonClicked() {
		try {
			this.Media("son_transition_begin.wav");

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
