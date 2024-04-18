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

/**
 * Contrôleur pour le jeu contre IA.
 * Elle hérite de la classe Game, et à des fonctions en plus nécessaires au bon fonctionnement du mode de jeu IA.
 * 
 */
public class GameAiController extends Game{

	// Réseau de neurones
	private MultiLayerPerceptron net;

	// Matrice
	private double[] listMatrix = {0, 0, 0, 0, 0, 0, 0, 0, 0};

	// Indicateur
	private boolean turnAI = false;

	/**
	 * Initialise le contrôleur, démarrage du chronomètre et mise à jour du label de tour.
	 */
	@FXML
	private void initialize() {
		this.startTimer();
		this.updateTurn();
	}

	/**
	 * Constructeur de la classe GameAiController.
	 * Initialise la matrice de jeu.
	 */
	public GameAiController() {
		this.initMatrix(tabGame);
	}

	/**
	 * Définit le chemin vers le modèle IA utilisé par l'IA.
	 * 
	 * @param modelPath Le chemin vers le modèle IA.
	 */
	public void setAiModelPath(String modelPath) {
		this.net = MultiLayerPerceptron.load(modelPath);
	}

	/**
	 * Met à jour le label qui annonce le tour d'un joueur.
	 */
	private void updateTurn() {
		if(turnAI) {
			LabelTurn.setText("C'est à l'ordi de jouer");
		}
		else {
			LabelTurn.setText("C'est à vous de jouer");
		}
	}  

	/**
	 * Réinitialise le jeu pour une nouvelle partie.
	 * 
	 * @param modelPath Le chemin vers le modèle AI.
	 */
	public void replayGame(String modelPath) {
		turnAI = false; 
		listMatrix = new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		if (modelPath != null) {
			setAiModelPath(modelPath);
		}
		this.Replay();
	}

	/**
	 * Gère l'événement de clic sur un bouton de la grille de jeu.
	 * 
	 * @param event L'événement de clic.
	 */
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

	/**
	 * Affiche le stackpane de défaite.
	 */
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

	/**
	 * Permet de gerer le tour de l'IA, son prochain coup, et l'action du coup.
	 */
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

	/**
	 * Trouve le meilleur coup à jouer selon les prédictions de l'IA.
	 * 
	 * @param list La liste des prédictions de l'IA.
	 * @return L'indice du meilleur coup.
	 */
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

	/**
	 * Initialise la matrice de jeu à null.
	 * 
	 * @param matrix2 La matrice de jeu à initialiser.
	 */
	private void initMatrix(String[][] matrix2) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matrix2[i][j] = null;
			}
		}
	}


}