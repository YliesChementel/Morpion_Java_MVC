package pack_morpion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.event.ActionEvent;

public class LostController {


    @FXML
    private Button rejouerButton;

    @FXML
    private Button retourButton;
    
    private GameAiController gameAiController; 
    
    private Stage stage;
    
    public LostController() {
    	
    }
    
    public void setGameAiController(GameAiController gameAiController) {
        this.gameAiController = gameAiController;
    }
    

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @FXML
    private void rejouer(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Confirmation");
	    alert.setHeaderText("Changer la difficulté ?");
	    alert.setContentText("Voulez-vous changer la difficulté avant de rejouer ?");
	    ButtonType rejouer = new ButtonType("Rejouer");
	    ButtonType changerDifficulte = new ButtonType("Changer la difficulté");
	    alert.getButtonTypes().setAll(changerDifficulte, rejouer);
	    alert.showAndWait().ifPresent(response -> {
	    	if (response == changerDifficulte) {
	    		choisirNouvelleDifficulte();
	        } else if (response == rejouer) {
	            gameAiController.rejouerPartieSansChanger();
	        }
	    });
	    stage.close();
    }
    	
 
    
    private void choisirNouvelleDifficulte() {
    	Alert choixDifficulte = new Alert(AlertType.CONFIRMATION);
        choixDifficulte.setTitle("Choix de la difficulté");
        choixDifficulte.setHeaderText("Choisir la nouvelle difficulté");
        choixDifficulte.setContentText("Veuillez sélectionner la nouvelle difficulté :");

        ButtonType facile = new ButtonType("Facile");
        ButtonType moyen = new ButtonType("Moyen");
        ButtonType difficile = new ButtonType("Difficile");

        choixDifficulte.getButtonTypes().setAll(facile, moyen, difficile);
        Optional<ButtonType> result = choixDifficulte.showAndWait();
        if (result.isPresent()) {
            if (result.get() == facile) {
                // Code pour charger le modèle de difficulté facile
                chargerModele("F");
            } else if (result.get() == moyen) {
                // Code pour charger le modèle de difficulté moyenne
                chargerModele("M");
            } else if (result.get() == difficile) {
                // Code pour charger le modèle de difficulté difficile
                chargerModele("D");
            }
        }
    }

    private void chargerModele(String difficulty) {
    	ConfigFileLoader configLoad = new ConfigFileLoader();
        configLoad.loadConfigFile(".\\rss\\config.txt");
        Config config = configLoad.get(difficulty);
        String modelName = "model_" + config.hiddenLayerSize + "_" + config.numberOfhiddenLayers + "_"
                + config.learningRate + ".srf";

        String repertoire = ".\\rss\\models\\";

        String file = repertoire + modelName;

        File tempFile = new File(file);
        boolean exists = tempFile.exists();
        if (!exists) {
          	try {
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("ChargementLayout.fxml"));
                Parent root = loader.load();
                ModelController controller = loader.getController();
                controller.setParameters(config.hiddenLayerSize, config.learningRate, config.numberOfhiddenLayers, file);
                controller.initializeModel();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Chargement");
                stage.show();
                
                stage.setOnHidden(event -> {
                	gameAiController.rejouerPartie(file);
                });
                
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
       }
        else {
        	gameAiController.rejouerPartie(file);
        }
    }

    @FXML
    private void retour(ActionEvent event) {
    	if (stage != null) {
            stage.close(); 
            if(gameAiController != null) {
            	gameAiController.afficherVersusLayout();
            }
        }
    }
}
