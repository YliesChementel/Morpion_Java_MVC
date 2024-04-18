package pack_morpion;

import javafx.util.Duration;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HelpController {
	Stage stage;

	@FXML
	private TextArea helpTextArea; 

	@FXML
	private ImageView backgroundImage;

	@FXML
	private AnchorPane rootPane;


	private boolean windowClosed = false;
	private final Duration animationDuration = Duration.seconds(5);
	private final Duration animationDelay = Duration.seconds(0.2);
	private final int numQuestionMarks = 1;

	private ToolbarController parentController;

	public void setParentController(ToolbarController parentController) {
		this.parentController = parentController;
	}

	public void initialize() {
		String helpContent = "Le morpion se joue sur une grille.\n" +
				"Choisis une case en cliquant dessus.\n" +
				"L'adversaire (soit 'X' ou 'O') joue ensuite.\n" +
				"À tour de rôle, vous choisissez des cases jusqu'à ce que l'un de vous aligne trois symboles ou que la grille soit pleine.\n" +
				"Le but est de créer une ligne, une colonne ou une diagonale avec trois de tes symboles (soit 'X' ou 'O').\n" +
				"Si tu réussis, tu gagnes !\n" +
				"Si tu veux jouer à nouveau, clique sur le bouton Rejouer !";

		helpTextArea.setText(helpContent);


		Image questionMarkImage = new Image("file:rss/images/question-mark.png");

		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
			if (!windowClosed) {
				for (int i = 0; i < numQuestionMarks; i++) {
					ImageView imageView = new ImageView(questionMarkImage);


					imageView.setTranslateX(Math.random() * 550);
					imageView.setTranslateY(-200);

					imageView.fitWidthProperty().bind(rootPane.widthProperty().divide(5));
					imageView.fitHeightProperty().bind(rootPane.heightProperty().divide(5));


					rootPane.getChildren().add(imageView);
					imageView.toBack();

					TranslateTransition translateTransition = new TranslateTransition(animationDuration, imageView);
					translateTransition.setToY(600);
					translateTransition.setOnFinished(e -> rootPane.getChildren().remove(imageView));
					translateTransition.play();
				}
			}
		}), new KeyFrame(animationDelay));

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

	}



	@FXML
	private void closeHelp() {
		parentController.helpStage=null;
		stage.close();
		windowClosed = true;
	}

	public void setStage(Stage stage) {
		this.stage = stage;

	}
}