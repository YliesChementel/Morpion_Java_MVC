package pack_morpion;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModelViewController {
	
	@FXML
    private VBox filesContainer;
    private final File folder = new File(".\\rss\\models\\");
    Stage stage;
	
	public void initialize() {
    	
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    CheckBox checkBox = new CheckBox(file.getName());
                    filesContainer.getChildren().add(checkBox);
                }
            }
        }
        else {
        	System.out.println("dossier vide");
        }
    }

    @FXML
    private void deleteSelectedFiles() {
        for (int i = 0; i < filesContainer.getChildren().size(); i++) {
            if (filesContainer.getChildren().get(i) instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) filesContainer.getChildren().get(i);
                if (checkBox.isSelected()) {
                    File fileToDelete = new File(folder.getPath() + File.separator + checkBox.getText());
                    if (fileToDelete.delete()) {
                        System.out.println("File deleted: " + fileToDelete.getName());
                    } else {
                        System.out.println("Failed to delete file: " + fileToDelete.getName());
                    }
                }
            }
        }
        if (stage != null) {
            stage.close();
        }
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }


}
