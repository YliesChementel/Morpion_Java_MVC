
package pack_morpion;

import java.io.File;
import java.io.IOException;
import ai.Config;
import ai.ConfigFileLoader;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Interpolator;


public class VersusController extends Action {
	@FXML
	private StackPane stackpane;

	@FXML
	private Button homme_Vs_Ai;

	@FXML
	private Button homme_Vs_Homme;

	@FXML
	private Button buttonRetour;

	@FXML
	private GridPane grid;

	@FXML
	private RadioButton radioF;

	@FXML
	private RadioButton radioM;

	@FXML
	private RadioButton radioD;

	@FXML
	private VBox vBoxNom;

	@FXML
	private TextField fieldF1;

	@FXML
	private TextField fieldF2;

	private static String NAME_PLAYERX ="";

	private static String NAME_PLAYERO ="";
	
	private static Main MAIN;
	
	public void setMain(Main main) {
		this.MAIN=main;
	}

	public String getNAME_PLAYERX() {
		return NAME_PLAYERX;
	}

	public String getNAME_PLAYERO() {
		return NAME_PLAYERO;
	}

	public VersusController() {
	}

	@FXML
	private void initialize() {

		fieldF1.setText(NAME_PLAYERX);
		fieldF2.setText(NAME_PLAYERO);

		grid.setHgap(40);
		grid.setVgap(20);
		homme_Vs_Homme.setOnAction(event -> handleHommeVsHomme());
		homme_Vs_Ai.setOnAction(event -> handleHommeVsAi());
		buttonRetour.setOnAction(event -> { 
			handleRetour();
			this.Media("son_transition_end.wav");
		});

		ToggleGroup group = new ToggleGroup();
		radioF.setSelected(true);
		radioF.setToggleGroup(group);
		radioM.setToggleGroup(group);
		radioD.setToggleGroup(group);

		homme_Vs_Homme.setOnMousePressed(event -> {
			homme_Vs_Homme.setStyle("-fx-background-color: #2b78e4;");
			homme_Vs_Homme.setTranslateY(homme_Vs_Homme.getTranslateY() - 10);
		});

		homme_Vs_Homme.setOnMouseReleased(event -> {
			homme_Vs_Homme.setStyle("");
			homme_Vs_Homme.setTranslateY(homme_Vs_Homme.getTranslateY() + 10);
			homme_Vs_Ai.setStyle("-fx-opacity: 1;");
			homme_Vs_Homme.setStyle("-fx-opacity: 1;");
		});

		homme_Vs_Ai.setOnMousePressed(event -> {
			homme_Vs_Ai.setStyle("-fx-background-color: #2b78e4;");
			homme_Vs_Ai.setTranslateY(homme_Vs_Ai.getTranslateY() - 10);
		});

		homme_Vs_Ai.setOnMouseReleased(event -> {
			homme_Vs_Ai.setStyle("");
			homme_Vs_Ai.setTranslateY(homme_Vs_Ai.getTranslateY() + 10);
			homme_Vs_Ai.setStyle("-fx-opacity: 1;");
			homme_Vs_Homme.setStyle("-fx-opacity: 1;");

		});

		limitTextFieldLength(fieldF1, 10);
		limitTextFieldLength(fieldF2, 10);
	}

	private void limitTextFieldLength(TextField textField, int maxLength) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > maxLength) {
				textField.setText(oldValue);
			}
		});
	}

	private void transition(Scene scene, Parent root) {
		root.translateXProperty().set(scene.getWidth());
		stackpane.getChildren().add(root);

		this.Media("son_transition_begin.wav");

		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
		timeline.getKeyFrames().add(keyFrame);
		KeyValue keyValue2 = new KeyValue(grid.translateXProperty(), -scene.getWidth(), Interpolator.EASE_IN);
		KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), keyValue2);
		timeline.getKeyFrames().add(keyFrame2);

		KeyValue keyValue3 = new KeyValue(buttonRetour.translateXProperty(), -scene.getWidth(), Interpolator.EASE_IN);
		KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1), keyValue3);
		timeline.getKeyFrames().add(keyFrame3);

		KeyValue keyValue4 = new KeyValue(vBoxNom.translateXProperty(), -scene.getWidth(), Interpolator.EASE_IN);
		KeyFrame keyFrame4 = new KeyFrame(Duration.seconds(1), keyValue4);
		timeline.getKeyFrames().add(keyFrame4);

		timeline.setOnFinished(event -> stackpane.getChildren().removeAll(grid, buttonRetour));
		timeline.play();
	}

	private void handleRetour() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayButton.fxml"));
			Parent root = loader.load();
			PlayButtonController playButtonController = loader.getController();


			Scene sceneAi = grid.getScene();
			root.translateXProperty().set(-sceneAi.getWidth());
			stackpane.getChildren().add(root);
			Timeline timeline = new Timeline();

			KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
			KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
			timeline.getKeyFrames().add(keyFrame);

			KeyValue keyValue2 = new KeyValue(grid.translateXProperty(),sceneAi.getWidth(), Interpolator.EASE_IN);
			KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), keyValue2);
			timeline.getKeyFrames().add(keyFrame2);

			KeyValue keyValue3 = new KeyValue(buttonRetour.translateXProperty(),sceneAi.getWidth(), Interpolator.EASE_IN);
			KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1), keyValue3);
			timeline.getKeyFrames().add(keyFrame3);

			timeline.setOnFinished(event -> stackpane.getChildren().remove(buttonRetour));
			timeline.setOnFinished(event -> stackpane.getChildren().setAll(root));
			timeline.play();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleHommeVsAi() {
		homme_Vs_Ai.setDisable(true);
		homme_Vs_Homme.setDisable(true);
		HBox hboxRadio = (HBox) grid.getChildren().stream()
				.filter(node -> node instanceof HBox)
				.findFirst()
				.orElse(null);

		RadioButton button = (RadioButton) hboxRadio.getChildren().stream()
				.filter(node -> node instanceof RadioButton && ((RadioButton) node).isSelected())
				.findFirst()
				.orElse(null);

		if (button != null) {
			ConfigFileLoader configLoad = new ConfigFileLoader();
			configLoad.loadConfigFile(".\\rss\\config.txt");
			Config config = configLoad.get(button.getId());
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
					Image icon = new Image("file:rss/images/load-icon.png");
					stage.setOnCloseRequest(event -> {
					    event.consume();
					});
					stage.setResizable(false);
					stage.getIcons().add(icon);
					stage.setScene(scene);
					stage.setTitle("Loading");
					MAIN.openStages.add(stage);
					stage.show();

					stage.setOnHidden(event -> {
						try {
							FXMLLoader loader2 = new FXMLLoader(getClass().getResource("GameAiLayout.fxml"));
							Parent root2 = loader2.load();

							GameAiController gameAiController = loader2.getController();
							gameAiController.setAiModelPath(file);

							Scene sceneAi = homme_Vs_Ai.getScene();

							BorderPane borderPane = (BorderPane) sceneAi.getRoot(); // Récupère le BorderPane racine de la scène
							ToolBar toolBar = (ToolBar) borderPane.getTop(); // Supposons que votre ToolBar est située en haut de votre BorderPane

							if (toolBar != null) {
								ObservableList<Node> items = toolBar.getItems();
								for (Node item : items) {
									if (item instanceof MenuBar) {
										MenuBar menuBar = (MenuBar) item;
										menuBar.setVisible(false); // Cachez la MenuBar
									}
								}
							}

							transition(sceneAi,root2);

						} catch (IOException e) {
							e.printStackTrace();
						}
					});

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("GameAiLayout.fxml"));
					Parent root = loader.load();

					GameAiController gameAiController = loader.getController();
					gameAiController.setAiModelPath(file);

					Scene sceneAi = homme_Vs_Ai.getScene();

					BorderPane borderPane = (BorderPane) sceneAi.getRoot(); // Récupère le BorderPane racine de la scène
					ToolBar toolBar = (ToolBar) borderPane.getTop(); // Supposons que votre ToolBar est située en haut de votre BorderPane

					if (toolBar != null) {
						ObservableList<Node> items = toolBar.getItems();
						for (Node item : items) {
							if (item instanceof MenuBar) {
								MenuBar menuBar = (MenuBar) item;
								menuBar.setVisible(false); // Cachez la MenuBar
							}
						}
					}

					transition(sceneAi,root);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	private void handleHommeVsHomme() {
		NAME_PLAYERX = fieldF1.getText();
		NAME_PLAYERO = fieldF2.getText();
		try {
			homme_Vs_Ai.setDisable(true);
			homme_Vs_Homme.setDisable(true);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameLayout.fxml"));
			Parent root = loader.load();
			Scene scene = homme_Vs_Homme.getScene();

			BorderPane borderPane = (BorderPane) scene.getRoot(); // Récupère le BorderPane racine de la scène
			ToolBar toolBar = (ToolBar) borderPane.getTop(); // Supposons que votre ToolBar est située en haut de votre BorderPane

			if (toolBar != null) {
				ObservableList<Node> items = toolBar.getItems();
				for (Node item : items) {
					if (item instanceof MenuBar) {
						MenuBar menuBar = (MenuBar) item;
						menuBar.setVisible(false); // Cachez la MenuBar
					}
				}
			}

			transition(scene,root);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}