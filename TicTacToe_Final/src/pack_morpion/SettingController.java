package pack_morpion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import ai.Config;
import ai.ConfigFileLoader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/*
 * 
 * Controller des paramètres pour la sauvegarde dans config.txt 
 *
 */
public class SettingController {
	// Paramètres d'interface graphique (FXML)
	@FXML
	private TextField fieldF1;
	@FXML
	private TextField fieldF2;
	@FXML
	private TextField fieldF3;
	@FXML
	private TextField fieldF4;
	@FXML
	private TextField fieldF5;
	@FXML
	private TextField fieldF6;
	@FXML
	private TextField fieldF7;
	@FXML
	private TextField fieldF8;
	@FXML
	private TextField fieldF9;

	
	
	private ToolbarController parentController;

	public void setParentController(ToolbarController parentController) {
		this.parentController = parentController;
	}
	/*
	 * Sert à afficher les paramètre déjà présent dans config.txt
	 */
	public void showSettings() {

		ConfigFileLoader configLoad = new ConfigFileLoader();

		String configFilePath = "file:///../rss/config.txt";
		configLoad.loadConfigFile(configFilePath);



		Config configF = configLoad.get("F");
		if (configF != null) {
			fieldF1.setText(String.valueOf(configF.hiddenLayerSize));
			fieldF2.setText(String.valueOf(configF.learningRate));
			fieldF3.setText(String.valueOf(configF.numberOfhiddenLayers));
		}

		Config configM = configLoad.get("M");
		if (configM != null) {
			fieldF4.setText(String.valueOf(configM.hiddenLayerSize));
			fieldF5.setText(String.valueOf(configM.learningRate));
			fieldF6.setText(String.valueOf(configM.numberOfhiddenLayers));
		}

		Config configD = configLoad.get("D");
		if (configD != null) {
			fieldF7.setText(String.valueOf(configD.hiddenLayerSize));
			fieldF8.setText(String.valueOf(configD.learningRate));
			fieldF9.setText(String.valueOf(configD.numberOfhiddenLayers));
		}
	}
	
	/*
	 * Sert à sauvegarder les modifications ligne par ligne et fermer la fenêtre
	 */
	@FXML
	private void handleSave() {
		saveConfigToFile("F", fieldF1.getText(), fieldF2.getText(), fieldF3.getText());
		saveConfigToFile("M", fieldF4.getText(), fieldF5.getText(), fieldF6.getText());
		saveConfigToFile("D", fieldF7.getText(), fieldF8.getText(), fieldF9.getText());
		Stage stage = (Stage) fieldF1.getScene().getWindow();
		parentController.settingsStage=null;
		stage.close();

	}
	//Pour Sauvegarder et eviter les répétition
	private void saveConfigToFile(String level, String size, String rate, String layers) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("file:///../rss/config.txt", true));
			writer.write(level + ":" + size + ":" + rate + ":" + layers + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}