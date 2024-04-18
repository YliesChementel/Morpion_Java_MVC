package pack_morpion;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;


/**
 * Contrôleur principal de l'application de jeu de morpion.
 * Gère le fait de cliquer sur le bouton "Jouer".
 */
public class MainController {

	@FXML
	private BorderPane borderPane;

	/**
	 * Méthode appelée lors du clic sur le bouton "Jouer".
	 * Charge la mise en page de sélection du mode de jeu (VersusLayout.fxml).
	 */
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
