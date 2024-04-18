package pack_morpion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
 * 
 * Controller qui sert à gérer les action de la liste des modèls
 * 
 */

public class ModelViewController {

	@FXML	
	private VBox filesContainer;
	private final File folder = new File(".\\rss\\models\\");
	Stage stage;
	
	
	/*
	 * Sert à récupérer le premier nombre dans le nom du modèl 
	 */
	private int extractNumberFromFileName(String fileName) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(fileName);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group());
		}
		return -1;
	}
	
	/*
	 * Sert à afficher tout les modèls présent dans le dossier modèls sur ma vue Modèl
	 */
	public void initialize() {

		File[] listOfFiles = folder.listFiles();

		if (listOfFiles.length != 0) {
			for (File file : listOfFiles) {
				if (file.isFile()) {
					String fileName = file.getName();
					int number = extractNumberFromFileName(fileName);
					String difficulty;
					if (number != -1 && number < 300) {
						difficulty = "F";
					} 
					else if (number != -1 && number >= 300 && number < 600) {
						difficulty = "M";
					}
					else if (number != -1 && number >= 600) {
						difficulty = "D";
					}else {
						difficulty = "?";
					}
					CheckBox checkBox = new CheckBox(fileName + " (" + difficulty + ")");
					checkBox.setTextFill(Color.BLACK);
					checkBox.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 12));
					filesContainer.getChildren().add(checkBox);
				}
			}
		} 
		else {
			Label textVide = new Label("dossier vide");
			textVide.setStyle("-fx-text-fill: red; -fx-font-size: 24px; -fx-font-weight: bold;");
			filesContainer.getChildren().add(textVide);
		}
	}
	
	/*
	 * Sert à supprimer tout les modèls dans la checbox a été cocher
	 */

	@FXML
	private void deleteSelectedFiles() {
		List<Node> nodesToRemove = new ArrayList<>();
		for (Node node : filesContainer.getChildren()) {
			if (node instanceof CheckBox) {
				CheckBox checkBox = (CheckBox) node;
				if (checkBox.isSelected()) {
					String checkBoxText = checkBox.getText();
					int index = checkBoxText.indexOf(" ");
					String fileName = checkBoxText.substring(0, index);
					File fileToDelete = new File(folder.getPath() + File.separator + fileName);
					if (fileToDelete.delete()) {                        
						nodesToRemove.add(node);
					}
				}
			}
		}

		filesContainer.getChildren().removeAll(nodesToRemove);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}


}
