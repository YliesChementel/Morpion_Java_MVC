package pack_morpion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;

public class LostController {


    @FXML
    private Button rejouerButton;

    @FXML
    private Button retourButton;
    
    private GameAiController gameAiController; 
    
    
    public LostController() {
    	
    }
    
    public void setGameAiController(GameAiController gameAiController) {
        this.gameAiController = gameAiController;
    }
    
    @FXML
    private void rejouer(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	
    	Image icon = new Image("file:rss/images/alerte-icon.jpg");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        
	    alert.setTitle("Confirmation");
	    alert.setHeaderText("Changer la difficulté?");
	    alert.setContentText("Voulez-vous changer la difficulté avant de rejouer?");
	    ButtonType rejouer = new ButtonType("Rejouer");
	    ButtonType changerDifficulte = new ButtonType("Changer la difficulté");
	    alert.getButtonTypes().setAll(changerDifficulte, rejouer);
	    alert.showAndWait().ifPresent(response -> {
	    	if (response == changerDifficulte) {
	    		choisirNouvelleDifficulte();
	        } else if (response == rejouer) {
	        	TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
			 	Scene scene = gameAiController.stackPaneView.getScene();
		        translateTransition.setFromY(0);
		        translateTransition.setToY(-scene.getHeight());
		        
		        translateTransition.setOnFinished(e -> {
		        	 gameAiController.rejouerPartieSansChanger();
		        	gameAiController.stackPaneView.setVisible(false);
		        	gameAiController.contentGridPaneAi.setDisable(false);
		        });
		        translateTransition.play();
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
                	TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
        		 	Scene scene1 = gameAiController.stackPaneView.getScene();
        	        translateTransition.setFromY(0);
        	        translateTransition.setToY(-scene1.getHeight());
        	        
        	        translateTransition.setOnFinished(e -> {
        	        	gameAiController.rejouerPartie(file);
        	        	gameAiController.stackPaneView.setVisible(false);
        	        	gameAiController.contentGridPaneAi.setDisable(false);
        	        });
        	        translateTransition.play();
                });
                
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
       }
        else {
        	TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
		 	Scene scene = gameAiController.stackPaneView.getScene();
	        translateTransition.setFromY(0);
	        translateTransition.setToY(-scene.getHeight());
	        
	        translateTransition.setOnFinished(e -> {
	        	gameAiController.rejouerPartie(file);
	        	gameAiController.stackPaneView.setVisible(false);
	        	gameAiController.contentGridPaneAi.setDisable(false);
	        });
	        translateTransition.play();
        }
    }

    @FXML
    private void retour(ActionEvent event) {
        if(gameAiController != null) {
        	TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
		 	Scene scene = gameAiController.stackPaneView.getScene();
	        translateTransition.setFromY(0);
	        translateTransition.setToY(-scene.getHeight());
	        
	        translateTransition.setOnFinished(e -> {
	        	gameAiController.afficherVersusLayout();
	        	gameAiController.stackPaneView.setVisible(false);
	        	gameAiController.contentGridPaneAi.setDisable(false);
	        });
	        translateTransition.play();
        }
    }
}