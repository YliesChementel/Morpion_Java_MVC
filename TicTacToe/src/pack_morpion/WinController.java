package pack_morpion;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class WinController {
	
	@FXML
    private Label messageLabel;

    @FXML
    private Button rejouerButton;

    @FXML
    private Button retourButton;
    
    private GameController gameController; 
    
    private Stage stage;
    

    public WinController() {
    }
        
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
    

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void retour(ActionEvent event) {
    	if (stage != null) {
            stage.close(); 
            if (gameController != null) { 
                gameController.afficherVersusLayout();
            }
        }
    }
    
    public void afficherVictoire(String joueur) {
        messageLabel.setText("Félicitations, joueur " + joueur + " a gagné !");
    }

    @FXML
    private void rejouer(ActionEvent event) {
        // Code pour recommencer la partie
    }

    
}
