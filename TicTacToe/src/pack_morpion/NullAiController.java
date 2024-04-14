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
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;

public class NullAiController extends Action {
	

    @FXML
    private Button rejouerButton;

    @FXML
    private Button retourButton;
    
    @FXML
    private Button choixButton;
    
    private GameAiController gameAiController; 
    
    public NullAiController() {
    	
    }
    
    
    
        
    public void setGameAiController(GameAiController gameAiController) {
        this.gameAiController = gameAiController;
    }
    

    @FXML
    private void retour(ActionEvent event) {
        if (gameAiController != null) { 
        	this.Media("son_stackpane_end.wav");
        	TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
		 	Scene scene = gameAiController.stackPaneView.getScene();
	        translateTransition.setFromY(0);
	        translateTransition.setToY(-scene.getHeight());
	        
	        translateTransition.setOnFinished(e -> {
	        	gameAiController.afficherVersusLayout();
	        	gameAiController.stackPaneView.setVisible(false);
	        	gameAiController.contentGridPane.setDisable(false);
	        });
	        translateTransition.play();
        }
    }
    
    @FXML
    private void rejouer(ActionEvent event) {
		this.Media("son_stackpane_end.wav");
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
	 	Scene scene = gameAiController.stackPaneView.getScene();
        translateTransition.setFromY(0);
        translateTransition.setToY(-scene.getHeight());
        
        translateTransition.setOnFinished(e -> {
        	gameAiController.rejouerPartie(null);
        	gameAiController.stackPaneView.setVisible(false);
        	gameAiController.contentGridPane.setDisable(false);
        });
        translateTransition.play();
    }
    	
 
    @FXML
    private void choisirNouvelleDifficulte(ActionEvent event) {
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
                chargerModele("F");
            } else if (result.get() == moyen) {
                chargerModele("M");
            } else if (result.get() == difficile) {
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
                	this.Media("son_stackpane_end.wav");
                	TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
        		 	Scene scene1 = gameAiController.stackPaneView.getScene();
        	        translateTransition.setFromY(0);
        	        translateTransition.setToY(-scene1.getHeight());
        	        
        	        translateTransition.setOnFinished(e -> {
        	        	gameAiController.rejouerPartie(file);
        	        	gameAiController.stackPaneView.setVisible(false);
        	        	gameAiController.contentGridPane.setDisable(false);
        	        });
        	        translateTransition.play();
                });
                
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
       }
        else {
        	this.Media("son_stackpane_end.wav");
        	TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
		 	Scene scene = gameAiController.stackPaneView.getScene();
	        translateTransition.setFromY(0);
	        translateTransition.setToY(-scene.getHeight());
	        
	        translateTransition.setOnFinished(e -> {
	        	gameAiController.rejouerPartie(file);
	        	gameAiController.stackPaneView.setVisible(false);
	        	gameAiController.contentGridPane.setDisable(false);
	        });
	        translateTransition.play();
        }
    }

}