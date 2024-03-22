package pack_morpion;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class LostController {


    @FXML
    private Button rejouerButton;

    @FXML
    private Button retourButton;
    
    public LostController() {
    	
    }
    
    @FXML
    private void rejouer(ActionEvent event) {
        // Code pour recommencer la partie
    }

    @FXML
    private void retour(ActionEvent event) {
        // Code pour retourner à la vue précédente
    }
}
