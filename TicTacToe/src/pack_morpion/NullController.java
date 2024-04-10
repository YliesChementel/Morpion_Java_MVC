package pack_morpion;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;

public class NullController {
	

    @FXML
    private Button rejouerButton;

    @FXML
    private Button retourButton;
    
    private GameController gameController; 
    
    
    public NullController() {
    	
    }
        
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    private void retour(ActionEvent event) {
        if (gameController != null) { 
        	String audioFile = "file:///../rss/son/son_stackpane_end.wav";
            Media media = new Media(new File(audioFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(1);
            mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.9);
            mediaPlayer.play();
        	
        	TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameController.stackPaneView);
		 	Scene scene = gameController.stackPaneView.getScene();
	        translateTransition.setFromY(0);
	        translateTransition.setToY(-scene.getHeight());
	        
	        translateTransition.setOnFinished(e -> {
	        	gameController.afficherVersusLayout();
	            gameController.stackPaneView.setVisible(false);
	            gameController.contentGridPane.setDisable(false);
	        });
	        
	        translateTransition.play();
        }
    }
    
    @FXML
    private void rejouer(ActionEvent event) {
    	TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameController.stackPaneView);
	 	Scene scene = gameController.stackPaneView.getScene();
        translateTransition.setFromY(0);
        translateTransition.setToY(-scene.getHeight());
        
        translateTransition.setOnFinished(e -> {
            gameController.rejouerPartie();
            gameController.stackPaneView.setVisible(false);
            gameController.contentGridPane.setDisable(false);
        });
        
        translateTransition.play();
    }

}
