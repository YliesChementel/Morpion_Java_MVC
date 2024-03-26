package pack_morpion;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class NullAiController {
	

    @FXML
    private Button rejouerButton;

    @FXML
    private Button retourButton;
    
    private GameAiController gameAiController; 
    
    private Stage stage;
    
    public NullAiController() {
    	
    }
    
    
    
        
    public void setGameAiController(GameAiController gameAiController) {
        this.gameAiController = gameAiController;
    }
    

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void retour(ActionEvent event) {
    	if (stage != null) {
            stage.close(); 
            if (gameAiController != null) { 
                gameAiController.afficherVersusLayout();
            }
        }
    }
    
    @FXML
    private void rejouer(ActionEvent event) {
        // Code pour recommencer la partie
    }
}
