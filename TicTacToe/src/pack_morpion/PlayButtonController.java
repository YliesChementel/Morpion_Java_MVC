package pack_morpion;
import java.io.IOException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;


public class PlayButtonController {
	
	@FXML
    private Button playButton;
	
	@FXML
    private StackPane  stackPane;
   
	
    @FXML
    private void handlePlayButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
            Parent root = loader.load();
            
            stackPane.getChildren().clear();
            stackPane.getChildren().add(root);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
