package pack_morpion;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class NullController {
	

    @FXML
    private Button rejouerButton;

    @FXML
    private Button retourButton;
    
    private GameController gameController; 
    
    private Stage stage;
    
    public NullController() {
    	
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
    
    @FXML
    private void rejouer(ActionEvent event) {
    	stage.close();
    	gameController.rejouerPartie();
    	
    }

}
