package pack_morpion;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Classe principale de l'application Morpion.
 */
public class Main extends Application {

	// Liste pour stocker les références aux différentes fenêtres ouvertes
	protected List<Stage> openStages = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Chargement de la vue principale depuis le fichier FXML
		Parent root = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));

		// Configuration de la fenêtre principale
		primaryStage.setTitle("LE SUPER MORPION");
		Image icon = new Image("file:rss/images/Morpion.jpg");
		primaryStage.getIcons().add(icon);

		// Lecture de la musique de fond
		MediaPlayerUtil.getMediaPlayer().play();

		// Chargement et configuration des autres vues et contrôleurs à fermer quand la fenêtre principale sera fermés
		FXMLLoader loaderVersus = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
		Parent rootVersus = loaderVersus.load();
		VersusController versus = loaderVersus.getController();;
		versus.setMain(this);

		FXMLLoader loaderTool = new FXMLLoader(getClass().getResource("Toolbar.fxml"));
		Parent rootTool = loaderTool.load();
		ToolbarController tool = loaderTool.getController();;
		tool.setMain(this);

		FXMLLoader loaderStack = new FXMLLoader(getClass().getResource("WinLayout.fxml"));
		Parent rootStack = loaderStack.load();
		StackViewController stack = loaderStack.getController();;
		stack.setMain(this);

		// Gestion de la fermeture de la fenêtre principale
		primaryStage.setOnCloseRequest(event -> closeAllStages());

		// Configuration de la scène principale et affichage de la fenêtre principale
		primaryStage.setScene(new Scene(root, 720, 626));
		primaryStage.setMinWidth(720);
		primaryStage.setMinHeight(626);
		primaryStage.show();
	}

	/**
	 * Méthode principale pour lancer l'application.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Récupère le lecteur multimédia principal de l'application.
	 */
	protected static MediaPlayer getMainMediaPlayer() {
		return MediaPlayerUtil.getMediaPlayer();
	}

	/**
	 * Ferme tous les fenêtres  ouverts.
	 */
	private void closeAllStages() {
		for (Stage stage : openStages) {
			stage.close();
		}
	}
}
