package pack_morpion;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ToolbarController {
    @FXML
    private MenuItem settingsItem;

    @FXML
    private MenuItem modelsItem;
    
    @FXML
    private Button helpItem;

    private SettingController settingController;

    @FXML
    public void initialize() {
        settingsItem.setOnAction(event -> handleSettings());
        modelsItem.setOnAction(event -> handleModels());
        helpItem.setOnAction(event -> handleHelp());
    }

    private void handleSettings() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("setting.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            Image icon = new Image("file:rss/images/Cog.png");
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.setTitle("Paramètre");
            settingController = loader.getController();
            settingController.setParentController(this);
            settingController.showSettings();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleModels() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ModelLayout.fxml"));
            Parent root = loader.load();
            ModelViewController controller = loader.getController();
            Stage stage = new Stage(); 
            Image icon = new Image("file:rss/images/Model.jpg");
            stage.getIcons().add(icon);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Modèls");
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleHelp() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpLayout.fxml"));
            Parent root = loader.load();
            HelpController controller = loader.getController();
            Stage stage = new Stage(); 
            Image icon = new Image("file:rss/images/question-mark.png");
            stage.getIcons().add(icon);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Aide Jeu");
            
            
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSettingController(SettingController settingController) {
        this.settingController = settingController;
    }
}
