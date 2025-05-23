package pack_morpion;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Contrôleur des StackPane afficher quand la partie est fini pour le jeu Morpion.
 * Gère les actions liées à la transition entre différentes vues du jeu comme le retour ou rejouer.
 * Gère aussi le choix de difficulté intermédiaire.
 * Elle hérite de la classe action pour obtenir ces fonctions de média pour le son.
 * 
 */
public class StackViewController extends Action{

	// Paramètres d'interface graphique (FXML)
	@FXML
	protected AnchorPane anchorPane;
	@FXML
	protected Label messageLabel;
	@FXML
	protected Button rejouerButton;
	@FXML
	protected Button retourButton;
	@FXML
	protected Button choixButton;

	//Contrôleurs de jeu
	protected GameController gameController;
	protected GameAiController gameAiController; 

	// Instance principale de l'application
	private static Main MAIN;

	/**
	 * Définit l'instance principale de l'application.
	 * 
	 * @param main L'instance principale de l'application.
	 */
	public void setMain(Main main) {
		this.MAIN=main;
	}

	/**
	 * Définit le contrôleur du jeu en mode joueur contre joueur.
	 * 
	 * @param gameController Le contrôleur du jeu en mode joueur contre joueur.
	 */
	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * Définit le contrôleur du jeu en mode joueur contre ordinateur.
	 * 
	 * @param gameAiController Le contrôleur du jeu en mode joueur contre ordinateur.
	 */
	public void setGameAiController(GameAiController gameAiController) {
		this.gameAiController = gameAiController;
	}

	/**
	 * Gère l'action de retour à la vue précédente.
	 * 
	 * @param event L'événement de clic sur le bouton de retour.
	 */
	@FXML
	private void retour(ActionEvent event) {
		if (gameController != null) { 

			this.Media("son_stackpane_end.wav");

			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameController.stackPaneView);
			Scene scene = gameController.stackPaneView.getScene();
			translateTransition.setFromY(0);
			translateTransition.setToY(-scene.getHeight());

			translateTransition.setOnFinished(e -> {
				gameController.showVersusLayout();
				gameController.stackPaneView.setVisible(false);
				gameController.contentGridPane.setDisable(false);
			});

			translateTransition.play();
		}
		else if (gameAiController != null) { 
			this.Media("son_stackpane_end.wav");
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
			Scene scene = gameAiController.stackPaneView.getScene();
			translateTransition.setFromY(0);
			translateTransition.setToY(-scene.getHeight());

			translateTransition.setOnFinished(e -> {
				gameAiController.showVersusLayout();
				gameAiController.stackPaneView.setVisible(false);
				gameAiController.contentGridPane.setDisable(false);
			});
			translateTransition.play();
		}
	}

	/**
	 *  Gère l'action de relancer une nouvelle partie.
	 * 
	 * @param event L'événement de clic sur le bouton de rejouer.
	 */
	@FXML
	private void replay(ActionEvent event) {
		if (gameController != null) { 
			if(gameController.timelineConfetto!=null) {
				gameController.timelineConfetto.stop();
			}
			this.Media("son_stackpane_end.wav");
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameController.stackPaneView);
			Scene scene = gameController.stackPaneView.getScene();
			translateTransition.setFromY(0);
			translateTransition.setToY(-scene.getHeight());

			translateTransition.setOnFinished(e -> {
				gameController.rejouerPartie();
				gameController.stackPaneView.setVisible(false);
				gameController.contentGridPane.setDisable(false);
			});
			translateTransition.play();
		}
		else if (gameAiController != null) { 
			if(gameAiController.timelineConfetto!=null) {
				gameAiController.timelineConfetto.stop();
			}
			this.Media("son_stackpane_end.wav");
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
			Scene scene = gameAiController.stackPaneView.getScene();
			translateTransition.setFromY(0);
			translateTransition.setToY(-scene.getHeight());

			translateTransition.setOnFinished(e -> {
				gameAiController.replayGame(null);
				gameAiController.stackPaneView.setVisible(false);
				gameAiController.contentGridPane.setDisable(false);
			});
			translateTransition.play();
		}
	}

	/**
	 * Affiche la fenêtre d'alerte de sélection de la difficulté.
	 * 
	 * @param event L'événement de clic sur le bouton de choix de difficulté.
	 */
	@FXML
	private void chooseNewDifficulty(ActionEvent event) {
		if(gameAiController.timelineConfetto!=null) {
			gameAiController.timelineConfetto.stop();
		}
		Alert choixDifficulte = new Alert(AlertType.CONFIRMATION);
		choixDifficulte.setTitle("Choix de la difficulté");
		choixDifficulte.setHeaderText("Choisir la nouvelle difficulté");
		choixDifficulte.setContentText("Veuillez sélectionner la nouvelle difficulté :");

		ButtonType facile = new ButtonType("Facile");
		ButtonType moyen = new ButtonType("Moyen");
		ButtonType difficile = new ButtonType("Difficile");

		choixDifficulte.getButtonTypes().setAll(facile, moyen, difficile);
		Optional<ButtonType> result = choixDifficulte.showAndWait();
		if (result.isPresent()) {
			if (result.get() == facile) {
				loadModel("F");
			} else if (result.get() == moyen) {
				loadModel("M");
			} else if (result.get() == difficile) {
				loadModel("D");
			}
		}
	}

	/**
	 * Charge le modèle de l'IA en fonction de la difficulté sélectionnée.
	 * 
	 * @param difficulty La difficulté sélectionnée.
	 */
	private void loadModel(String difficulty) {
		ConfigFileLoader configLoad = new ConfigFileLoader();
		configLoad.loadConfigFile(".\\rss\\config.txt");
		Config config = configLoad.get(difficulty);
		String modelName = "model_" + config.hiddenLayerSize + "_" + config.numberOfhiddenLayers + "_"
				+ config.learningRate + ".srf";

		String repertoire = ".\\rss\\models\\";

		String file = repertoire + modelName;

		File tempFile = new File(file);
		boolean exists = tempFile.exists();
		if (!exists) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ChargementLayout.fxml"));
				Parent root = loader.load();
				ModelController controller = loader.getController();
				controller.setParameters(config.hiddenLayerSize, config.learningRate, config.numberOfhiddenLayers, file);
				controller.initializeModel();
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setOnCloseRequest(event -> {
					event.consume();
				});
				stage.setResizable(false);
				stage.setScene(scene);
				stage.setTitle("Chargement");
				MAIN.openStages.add(stage);
				stage.show();

				stage.setOnHidden(event -> {
					this.Media("son_stackpane_end.wav");
					TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
					Scene scene1 = gameAiController.stackPaneView.getScene();
					translateTransition.setFromY(0);
					translateTransition.setToY(-scene1.getHeight());

					translateTransition.setOnFinished(e -> {
						gameAiController.replayGame(file);
						gameAiController.stackPaneView.setVisible(false);
						gameAiController.contentGridPane.setDisable(false);
					});
					translateTransition.play();
				});

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			this.Media("son_stackpane_end.wav");
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameAiController.stackPaneView);
			Scene scene = gameAiController.stackPaneView.getScene();
			translateTransition.setFromY(0);
			translateTransition.setToY(-scene.getHeight());

			translateTransition.setOnFinished(e -> {
				gameAiController.replayGame(file);
				gameAiController.stackPaneView.setVisible(false);
				gameAiController.contentGridPane.setDisable(false);
			});
			translateTransition.play();
		}
	}

	/**
	 * Met à jour le message du stackpane de victoire pour y inclure le nom du gagnant.
	 * 
	 * @param player Le joueur qui a gagné.
	 */
	public void showSceneWin(String player) {
		messageLabel.setText("Félicitations, joueur " + player + " a gagné !");
		this.anchorPane.getChildren().remove(choixButton);
	}

	/**
	 * Met à jour le message du stackpane de victoire pour correspondre au mode de jeu.
	 */
	public void showSceneWinAi() {
		messageLabel.setText("Félicitations,vous avez gagné !");
	}
}
