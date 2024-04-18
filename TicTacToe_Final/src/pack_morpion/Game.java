package pack_morpion;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Cette classe permet le fonctionnement du jeu et hérite de la classe Action.
 * Elle contient les fonctionnalités spécifiques au jeu, telles que la gestion des boutons, 
 * le décompte du temps, la vérification de la victoire, etc...
 * 
 *
 */
public class Game extends Action{

	// Paramètres d'interface graphique (FXML)
	@FXML
	protected StackPane stackpane;
	@FXML
	protected StackPane stackPaneView;
	@FXML
	protected VBox VBoxGame;
	@FXML 
	protected GridPane contentGridPane;
	@FXML
	protected Button btn1;
	@FXML
	protected Button btn2;
	@FXML
	protected Button btn3;
	@FXML
	protected Button btn4;
	@FXML
	protected Button btn5;
	@FXML
	protected Button btn6;
	@FXML
	protected Button btn7;
	@FXML
	protected Button btn8;
	@FXML
	protected Button btn9;
	@FXML
	public Label LabelTurn;
	@FXML
	protected Label labelWinX;
	@FXML
	protected Label labelWinO;

	// Variables de jeu
	protected int winX = 0;
	protected int winO = 0;
	protected String[][] tabGame = new String[3][3];
	protected int buttonCount = 0;
	protected boolean playerX = true;

	// Paramètres d'animation
	protected final Duration animationDuration = Duration.seconds(5);
	protected final Duration animationDelay = Duration.seconds(0.01);
	protected final int numConfetto = 1;
	protected Timeline timelineConfetto;

	// Paramètres du minuteur
	@FXML
	protected Label labelTimer;
	protected int seconds = 0;
	protected Timeline timelineTimer;

	// Élément graphique
	protected Line line;

	/**
	 * Réinitialise le jeu pour une nouvelle partie.
	 */
	protected void Replay() {
		tabGame = new String[3][3];
		playerX = true;
		buttonCount = 0;

		btn1.setText("");
		btn2.setText("");
		btn3.setText("");
		btn4.setText("");
		btn5.setText("");
		btn6.setText("");
		btn7.setText("");
		btn8.setText("");
		btn9.setText("");

		timelineTimer.play();
		stackpane.getChildren().remove(this.line);
	}

	/**
	 * Démarre le chronomètre du jeu.
	 */
	protected void startTimer() {
		timelineTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> this.updateTimer()));
		timelineTimer.setCycleCount(Timeline.INDEFINITE);
		timelineTimer.play();
	}

	/**
	 * Met à jour le chronomètre du jeu.
	 */
	protected void updateTimer() {
		seconds++;
		int minutes = (seconds % 3600) / 60;
		int secs = seconds % 60;
		labelTimer.setText(String.format("%02d:%02d", minutes, secs));
	}

	/**
	 * Réinitialise le chronomètre du jeu.
	 */
	protected void resetTimer() {
		labelTimer.setText("00:00");
		seconds=0;
		timelineTimer.stop();
	}

	/**
	 * Met à jour le nombre de victoires pour un joueur donné.
	 * 
	 * @param player Le joueur pour lequel mettre à jour le nombre de victoires ("X" ou "O").
	 */
	protected void updateWin(String player) {
		if(player == "X") {
			winX++;
			labelWinX.setText(String.format("%d", winX));
		}
		else {
			winO++;
			labelWinO.setText(String.format("%d", winO));
		}
		this.resetTimer();
	}


	/**
	 * Affiche des confettis sur l'écran de jeu.
	 * 
	 * @param scene La scène sur laquelle afficher les confettis.
	 */
	protected void confetto(Scene scene) {
		List<String> confettoImages = Arrays.asList("file:rss/images/confetto_vert.png", 
				"file:rss/images/confetto_bleu.png", 
				"file:rss/images/confetto_rouge.png",
				"file:rss/images/confetto_jaune.png",
				"file:rss/images/confetto_blanc.png",
				"file:rss/images/confetto_violet.png",
				"file:rss/images/confetto_orange.png",
				"file:rss/images/confetto_rose.png");

		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event1 -> {
			for (int i = 0; i < numConfetto; i++) {
				String imagePath = confettoImages.get((int) (Math.random() * confettoImages.size()));
				Image confettoImage = new Image(imagePath);

				ImageView imageView = new ImageView(confettoImage);

				imageView.setTranslateX(Math.random() * scene.getWidth() * 2 - scene.getWidth());
				imageView.setTranslateY(-scene.getHeight());

				imageView.fitWidthProperty().bind(stackpane.widthProperty().divide(50));
				imageView.fitHeightProperty().bind(stackpane.heightProperty().divide(50));


				stackpane.getChildren().add(imageView);
				imageView.toBack();

				TranslateTransition translateTransition1 = new TranslateTransition(animationDuration, imageView);
				translateTransition1.setToY(600);
				translateTransition1.setOnFinished(e -> stackpane.getChildren().remove(imageView));
				translateTransition1.play();
			}
		}), new KeyFrame(animationDelay));

		timelineConfetto=timeline;

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	/**
	 * Affiche le layout de versus, soit le choix des modes de jeu.
	 */
	protected void showVersusLayout() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
			Parent root = loader.load();
			VersusController versusController = loader.getController();


			Scene scene = contentGridPane.getScene();
			root.translateXProperty().set(-scene.getWidth());
			stackpane.getChildren().add(root);

			this.Media("son_transition_end.wav");

			BorderPane borderPane = (BorderPane) scene.getRoot();
			ToolBar toolBar = (ToolBar) borderPane.getTop(); 

			if (toolBar != null) {
				ObservableList<Node> items = toolBar.getItems();
				for (Node item : items) {
					if (item instanceof MenuBar) {
						MenuBar menuBar = (MenuBar) item;
						menuBar.setVisible(true); 
					}
				}
			}

			Timeline timeline = new Timeline();

			KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
			KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
			timeline.getKeyFrames().add(keyFrame);

			KeyValue keyValue2 = new KeyValue(VBoxGame.translateXProperty(),scene.getWidth(), Interpolator.EASE_IN);
			KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), keyValue2);
			timeline.getKeyFrames().add(keyFrame2);

			if(this.line!=null) {
				KeyValue keyValue3 = new KeyValue(line.translateXProperty(),scene.getWidth(), Interpolator.EASE_IN);
				KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1), keyValue3);
				timeline.getKeyFrames().add(keyFrame3);
			}
			timeline.setOnFinished(event -> {
				if(timelineConfetto !=null) {
					timelineConfetto.stop();
				}
				stackpane.getChildren().setAll(root);});
			timeline.play();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Affiche le stackpane de victoire avec le nom du joueur gagnant.
	 * 
	 * @param player Le nom du joueur qui a remporté la partie.
	 */
	protected void showWinScene(String player) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("WinLayout.fxml"));
			Parent root = loader.load();
			StackViewController stackViewController = loader.getController();
			if(this instanceof GameAiController) {
				stackViewController.setGameAiController((GameAiController) this);
				stackViewController.showSceneWinAi();
			}
			else {
				stackViewController.setGameController((GameController) this);
				stackViewController.showSceneWin(player);
			}

			this.transition(root,400,560,"son_stackpane_begin.wav","son_victory.wav",true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Affiche le stackpane de match nul.
	 */
	protected void showNullScene() {
		this.resetTimer();
		try {
			FXMLLoader loader;
			Parent root; 
			if(this instanceof GameAiController) {
				loader = new FXMLLoader(getClass().getResource("NullAiLayout.fxml"));
				root = loader.load();
				StackViewController stackViewController = loader.getController();
				stackViewController.setGameAiController((GameAiController) this);
			}
			else {
				loader = new FXMLLoader(getClass().getResource("NullLayout.fxml"));
				root = loader.load();
				StackViewController stackViewController = loader.getController();
				stackViewController.setGameController((GameController) this);
			}

			this.transition(root,300,300,"son_stackpane_begin.wav","son_draw.wav",false);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Effectue la transition vers une nouvelle scène.
	 * 
	 * @param root La racine de la nouvelle scène.
	 * @param maxHeight La hauteur maximale de la nouvelle scène.
	 * @param maxWidth La largeur maximale de la nouvelle scène.
	 * @param mediaStart Le média à jouer au début de la transition.
	 * @param mediaEnd Le média à jouer à la fin de la transition.
	 * @param victory Indique si la transition est une victoire.
	 */
	protected void transition(Parent root,int maxHeight,int maxWidth,String mediaStart,String mediaEnd, boolean victory) {
		this.resetTimer();
		stackPaneView.setVisible(true);
		stackPaneView.setMaxHeight(maxHeight);
		stackPaneView.setMaxWidth(maxWidth);
		stackPaneView.getChildren().setAll(root);
		stackPaneView.toFront();
		contentGridPane.setDisable(true);

		this.Media(mediaStart);

		Scene scene = this.stackPaneView.getScene();

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), stackPaneView);
		translateTransition.setFromY(-scene.getHeight());
		translateTransition.setToY(0);

		TranslateTransition bounceTransition = new TranslateTransition(Duration.millis(300), stackPaneView);
		bounceTransition.setFromY(0);
		bounceTransition.setToY(-20);
		bounceTransition.setCycleCount(2);
		bounceTransition.setAutoReverse(true);

		translateTransition.setOnFinished(event ->{ 
			bounceTransition.play();
			this.Media(mediaEnd);

			if(victory) {
				this.confetto(scene);
			}
		});
		translateTransition.play();
	}

	/**
	 * Vérifie si un joueur a remporté la partie.
	 * 
	 * @param joueur Le joueur à vérifier ("X" ou "O").
	 * @return true si le joueur a gagné, sinon false.
	 */
	protected boolean victory(String joueur) {
		for (int i = 0; i < 3; i++) {
			if (tabGame[i][0] == joueur && tabGame[i][1] == joueur && tabGame[i][2] == joueur) {
				this.DrawLine(0,i);
				this.updateWin(joueur);
				return true;
			}
			if (tabGame[0][i] == joueur && tabGame[1][i] == joueur && tabGame[2][i] == joueur) {
				this.DrawLine(1,i);
				this.updateWin(joueur);
				return true;
			}
		}
		if (tabGame[0][0] == joueur && tabGame[1][1] == joueur && tabGame[2][2] == joueur) {
			this.DrawLine(2,0);
			this.updateWin(joueur);
			return true;
		}
		if(tabGame[0][2] == joueur && tabGame[1][1] == joueur && tabGame[2][0] == joueur){
			this.DrawLine(2,1);
			this.updateWin(joueur);
			return true;
		}
		return false;
	}

	/**
	 * Dessine une ligne sur la grille pour indiquer la victoire.
	 * 
	 * @param typeCase Le type de ligne à dessiner (0: ligne horizontale, 1: ligne verticale, 2: diagonale).
	 * @param pos La position de la ligne.
	 */
	private void DrawLine(int typeCase, int pos) {
		if (typeCase == 0) {
			this.line = new Line(0, 0, 300, 0);
			if (pos == 0) {
				this.line.setStyle("-fx-translate-y: " + (this instanceof GameAiController ? "-75;" : "-97;"));
			} else if (pos == 1) {
				this.line.setStyle("-fx-translate-y: " + (this instanceof GameAiController ? "35;" : "13;"));
			} else {
				this.line.setStyle("-fx-translate-y: " + (this instanceof GameAiController ? "145;" : "124;"));
			}
		} else if (typeCase == 1) {
			this.line = new Line(0, (this instanceof GameAiController ? 0 : -90), 0, (this instanceof GameAiController ? 300 : 180));
			if (pos == 0) {
				this.line.setStyle("-fx-translate-x: -111");
			} else if (pos == 2) {
				this.line.setStyle("-fx-translate-x: 109");
			}
		} else {
			if (pos == 0) {
				this.line = new Line(-5, -5, 245, 245);
			} else {
				this.line = new Line(-5, 245, 245, -5);
			}
			this.line.setStyle("-fx-translate-x: " + (this instanceof GameAiController ? "-35;" : "-15;"));
			this.line.setStyle("-fx-translate-y: " + (this instanceof GameAiController ? "35;" : "15;"));
		}

		line.setStrokeWidth(5);
		stackpane.getChildren().add(line);
	}
}
