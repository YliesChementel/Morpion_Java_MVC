package pack_morpion;

import java.io.File;
import java.io.IOException;
import ai.Config;
import ai.ConfigFileLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class VersusController {
	@FXML
    private HBox hbox;

    @FXML
    private Button homme_Vs_Ai;

    @FXML
    private Button homme_Vs_Homme;

    @FXML
    private GridPane grid;

    @FXML
    private RadioButton radioF;

    @FXML
    private RadioButton radioM;

    @FXML
    private RadioButton radioD;
    
    
    public VersusController() {
    }

    @FXML
    private void initialize() {
    	
        grid.setHgap(40);
        homme_Vs_Ai.setOnAction(event -> handleHommeVsAi());
        homme_Vs_Homme.setOnAction(event -> handleHommeVsHomme());

        ToggleGroup group = new ToggleGroup();
        radioF.setSelected(true);
        radioF.setToggleGroup(group);
        radioM.setToggleGroup(group);
        radioD.setToggleGroup(group);
        
    }
    
    private void handleHommeVsAi() {
    	RadioButton button = (RadioButton) grid.getChildren().stream()
                .filter(node -> node instanceof RadioButton && ((RadioButton) node).isSelected())
                .findFirst()
                .orElse(null);

        if (button != null) {
            ConfigFileLoader configLoad = new ConfigFileLoader();
            configLoad.loadConfigFile("rss/config.txt");
            Config config = configLoad.get(button.getText());
            String modelName = "model_" + config.hiddenLayerSize + "_" + config.numberOfhiddenLayers + "_"
                    + config.learningRate + ".srf";
            System.out.println(modelName);

            String repertoire = "rss/models/";

            String file = repertoire + modelName;
            System.out.println(file);
            File tempFile = new File(file);
            boolean exists = tempFile.exists();
            if (!exists) {
            	System.out.println("N'existe pas");
            	try {
	            	FXMLLoader loader = new FXMLLoader(getClass().getResource("ChargementLayout.fxml"));
	                Parent root = loader.load();
	                System.out.println(file);
	                ModelController controller = loader.getController();
	                controller.setParameters(config.hiddenLayerSize, config.learningRate, config.numberOfhiddenLayers, file);
	                //ModelController controller = new ModelController(config.hiddenLayerSize, config.learningRate, config.numberOfhiddenLayers, file);
	                //loader.setController(controller);
	                controller.initializeComponents();
	                Scene scene = new Scene(root);
	                Stage stage = new Stage();
	                stage.setScene(scene);
	                stage.setTitle("Loading");
	                stage.show();
            	} catch (IOException e) {
            		e.printStackTrace();
            
            	}
           }
       }
    }

    private void handleHommeVsHomme() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameLayout.fxml"));
            Parent root = loader.load();
            hbox.getChildren().clear();
            hbox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
