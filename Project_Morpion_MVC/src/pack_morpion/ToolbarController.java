package pack_morpion;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ToolbarController {
    @FXML
    private MenuItem settingsItem;

    @FXML
    private MenuItem modelsItem;

    private SettingController settingController;

    @FXML
    public void initialize() {
        settingsItem.setOnAction(event -> handleSettings());
        modelsItem.setOnAction(event -> handleModels());
    }

    private void handleSettings() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("setting.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Settings");
            settingController = loader.getController();
            settingController.setParentController(this);
            settingController.showSettings();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleModels() {
        System.out.println("Models clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModelLayout.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Models");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSettingController(SettingController settingController) {
        this.settingController = settingController;
    }
}
