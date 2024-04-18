package pack_morpion;
import java.io.IOException;
import ai.MultiLayerPerceptron;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class GameAiController extends Game{

	private MultiLayerPerceptron net;

	private double[] listMatrix = {0, 0, 0, 0, 0, 0, 0, 0, 0};

	private boolean turnAI = false;

	@FXML
	private void initialize() {
		this.startTimer();
		this.updateTurn();
	}

	public GameAiController() {
		this.initMatrix(tabGame);
	}

	public void setAiModelPath(String modelPath) {
		this.net = MultiLayerPerceptron.load(modelPath);
	}

	private void updateTurn() {
		if(turnAI) {
			LabelTurn.setText("C'est à l'ordi de jouer");
		}
		else {
			LabelTurn.setText("C'est à vous de jouer");
		}
	}  

	public void replayGame(String modelPath) {
		turnAI = false; 
		listMatrix = new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		if (modelPath != null) {
			setAiModelPath(modelPath);
		}
		this.Replay();
	}


	@FXML
	private void handleButtonClick(ActionEvent event) {
		Button button = (Button) event.getSource();
		int row = GridPane.getRowIndex(button);
		int col = GridPane.getColumnIndex(button);

		if (tabGame[row][col] != null) {
			return; 
		}

		tabGame[row][col] = playerX == true ? "X" : "O";
		listMatrix[row * 3 + col] = playerX == true ? 1 : 2;
		button.setText(playerX == true ? "X" : "O");
		buttonCount++;

		if (this.victory("X")) {
			showWinScene(null);
		} else if(this.victory("O")){
			showSceneLost();
		} else if (buttonCount == 9) {
			showNullScene();
		} else {
			playerX = !playerX;
			turnAI = !turnAI;

			if (turnAI) {
				playAi();
			}
		}
		this.updateTurn();
	}

	private void showSceneLost() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LostLayout.fxml"));
			Parent root = loader.load();
			StackViewController stackViewController = loader.getController();
			stackViewController.setGameAiController(this);

			this.transition(root,400,560,"son_stackpane_begin.wav","son_loss.wav",false);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void playAi() {
		double[] coup = net.forwardPropagation(this.listMatrix);
		int BestOutcome = findBestOutcome(coup);
		int row = BestOutcome / 3;
		int col = BestOutcome % 3;

		contentGridPane.setDisable(true);
		for (Node node : contentGridPane.getChildren()) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Button) {
				Button aiButton = (Button) node;

				Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> {
					aiButton.fireEvent(event);
				}));
				timeline.setOnFinished(event -> contentGridPane.setDisable(false));
				timeline.play();
				break;
			}
		}
	}

	private int findBestOutcome(double[] list) {
		int indice = 0;
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < 9; i++) {
			if (list[i] > max && listMatrix[i] == 0.0) {
				max = list[i];
				indice = i;
			}
		}
		return indice;
	}

	private void initMatrix(String[][] matrix2) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matrix2[i][j] = null;
			}
		}
	}


}