package pack_morpion;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;



public class MainController {

	@FXML
	private BorderPane borderPane;

	@FXML
	private void handlePlayButtonClicked() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
			Parent root = loader.load();
			borderPane.setCenter(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
