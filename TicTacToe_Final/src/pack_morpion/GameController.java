package pack_morpion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import javafx.event.ActionEvent;

public class GameController extends Game {

	@FXML
	public Label LabelNameX;
	@FXML
	public Label LabelNameO;

	public String playerNameX;

	public String playerNameO;

	public VersusController versusController;


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

	private void updateTurn() {
		if(playerX) {
			LabelTurn.setText("C'est à "+playerNameX+" de jouer");
		}
		else {
			LabelTurn.setText("C'est à "+playerNameO+" de jouer");
		}
	}

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

	public void rejouerPartie() {
		this.Replay();
	}

}