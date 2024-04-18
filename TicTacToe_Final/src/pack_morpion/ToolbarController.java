package pack_morpion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.shape.Rectangle;

public class ToolbarController {
	
	@FXML
	private ToolBar tool;
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

	public Stage settingsStage;

	private Stage modelsStage;

	public Stage helpStage;
	
	private static Main MAIN;
	
	public void setMain(Main main) {
		this.MAIN=main;
	}

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
		if (settingsStage == null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("setting.fxml"));
				Parent root = loader.load();
				settingController = loader.getController();
				Scene scene = new Scene(root);
				settingsStage = new Stage();
				settingsStage.setScene(scene);
				MAIN.openStages.add(settingsStage);
				Image icon = new Image("file:rss/images/Cog.png");
				settingsStage.getIcons().add(icon);
				settingsStage.setResizable(false);
				settingsStage.setTitle("Paramètre");
				settingsStage.setOnCloseRequest(event -> settingsStage = null);
				settingController.setParentController(this);
				settingController.showSettings();
				settingsStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			settingsStage.toFront();
		}
	}

	private void handleModels() {
		if (modelsStage == null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ModelLayout.fxml"));
				Parent root = loader.load();
				ModelViewController controller = loader.getController();
				Scene scene = new Scene(root);
				modelsStage = new Stage();
				modelsStage.setScene(scene);
				MAIN.openStages.add(modelsStage);
				Image icon = new Image("file:rss/images/Model.jpg");
				modelsStage.getIcons().add(icon);
				modelsStage.setResizable(false);
				modelsStage.setTitle("Models");
				modelsStage.setOnCloseRequest(event -> modelsStage = null);

				controller.setStage(modelsStage);
				modelsStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			modelsStage.toFront();
		}
	}

	private void handleHelp() {
		if (helpStage == null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpLayout.fxml"));
				Parent root = loader.load();
				HelpController controller = loader.getController();
				Scene scene = new Scene(root);
				helpStage = new Stage(); 
				helpStage.setScene(scene);
				MAIN.openStages.add(helpStage);
				Image icon = new Image("file:rss/images/question-mark.png");
				helpStage.getIcons().add(icon);
				helpStage.setResizable(false);
				helpStage.setTitle("Aide Jeu");
				helpStage.setOnCloseRequest(event -> helpStage = null);
				controller.setStage(helpStage);
				controller.setParentController(this);
				helpStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			helpStage.toFront();
		}
	}

	public void setSettingController(SettingController settingController) {
		this.settingController = settingController;
	}
}