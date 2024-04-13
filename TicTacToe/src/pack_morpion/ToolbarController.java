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
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;

public class ToolbarController {
    @FXML
    private MenuItem settingsItem;

    @FXML
    private MenuItem modelsItem;
    
    @FXML
    private Button helpItem;
    
    @FXML
    private Button volumeItem;
    
    @FXML
    private Slider volumeSlider;


    private SettingController settingController;
    
    @FXML
    private Rectangle customTrack;
    

    @FXML
    public void initialize() {
        settingsItem.setOnAction(event -> handleSettings());
        modelsItem.setOnAction(event -> handleModels());
        helpItem.setOnAction(event -> handleHelp());
        volumeItem.setOnAction(event -> handleVolumeButtonClick());
        
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volumeValue = newValue.doubleValue();

            double volume = volumeValue / 100.0;

            Main.getMainMediaPlayer().setVolume(volume);
        });
        
    }
    
    @FXML
    private void changeSliderTrackColor() {
        double value = volumeSlider.getValue();
        double max = volumeSlider.getMax();
        double min = volumeSlider.getMin();
        double ratio = (value - min) / (max - min);
        String color = String.format("#%02X%02X%02X", (int) (255 * ratio), (int) (255 * (1 - ratio)), 0); 
        volumeSlider.lookup(".track").setStyle("-fx-background-color: linear-gradient(to right, " + color + " " + (ratio * 100) + "%, transparent " + (ratio * 100) + "%);");
    }
    
    @FXML
    private void handleVolumeButtonClick() {
        volumeSlider.setVisible(!volumeSlider.isVisible());
        changeSliderTrackColor();
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
