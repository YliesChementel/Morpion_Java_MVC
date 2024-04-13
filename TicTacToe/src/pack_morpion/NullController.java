package pack_morpion;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.util.Duration;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;

public class NullController extends Action {
	

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

        	this.Media("son_stackpane_end.wav");
        	
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
    	this.Media("son_stackpane_end.wav");
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