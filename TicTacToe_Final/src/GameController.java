package pack_morpion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import javafx.event.ActionEvent;

/**
 * Contrôleur pour le mode joueur contre joueur.
 * Elle hérite de la classe Game, et à des fonctions en plus nécessaires au bon fonctionnement du mode de jeu joueur contre joueur.
 * 
 */
public class GameController extends Game {

	// Paramètres d'interface graphique (FXML)
	@FXML
	public Label LabelNameX;
	@FXML
	public Label LabelNameO;

	// Noms des joueurs
	public String playerNameX;
	public String playerNameO;

	//Contrôleur Versus
	public VersusController versusController;

	/**
	 * Initialise le contrôleur, démarrage du chronomètre et mise à jour du label de tour et initialisation des noms des joueurs.
	 * 
	 */
	@FXML
	private void initialize() {
		this.startTimer();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
		try {
			Parent root = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.versusController = loader.getController();

		this.playerNameX = this.versusController.getNAME_PLAYERX();
		this.playerNameO = this.versusController.getNAME_PLAYERO();

		if(playerNameX=="") {
			playerNameX="X";
		}
		if(playerNameO=="") {
			playerNameO="O";
		}

		LabelTurn.toBack();
		this.updateTurn();
	}

	/**
	 * Met à jour le label qui annonce le tour d'un joueur.
	 */
	private void updateTurn() {
		if(playerX) {
			LabelTurn.setText("C'est à "+playerNameX+" de jouer");
		}
		else {
			LabelTurn.setText("C'est à "+playerNameO+" de jouer");
		}
	}

	/**
	 * Gère l'événement de clic sur un bouton de la grille de jeu.
	 * 
	 * @param event L'événement de clic.
	 */
	@FXML
	private void handleButtonClick(ActionEvent event) {
		Button boutton= (Button) event.getSource();
		int row = GridPane.getRowIndex(boutton); 
		int col = GridPane.getColumnIndex(boutton); 

		if (tabGame[row][col] != null) {
			return; 
		}

		tabGame[row][col] = playerX == true ? "X" : "O";
		boutton.setText(playerX == true ? "X" : "O");
		buttonCount++;

		if (this.victory("X")) {
			showWinScene(playerNameX);
		} else if (this.victory("O")) {
			showWinScene(playerNameO);
		} else if (buttonCount == 9) {
			this.showNullScene();
		}else {
			playerX = !playerX;
		}
		this.updateTurn();
	}

	/**
	 * Relance une nouvelle partie.
	 */
	public void rejouerPartie() {
		this.Replay();
	}

}