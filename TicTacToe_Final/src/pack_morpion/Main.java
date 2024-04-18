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

public class Main extends Application {

	protected List<Stage> openStages = new ArrayList<>();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));

		primaryStage.setTitle("LE SUPER MORPION");
		Image icon = new Image("file:rss/images/Morpion.jpg");
		primaryStage.getIcons().add(icon);

		MediaPlayerUtil.getMediaPlayer().play();
		
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
		
		primaryStage.setOnCloseRequest(event -> closeAllStages());
		primaryStage.setScene(new Scene(root, 720, 626));
		primaryStage.setMinWidth(720);
		primaryStage.setMinHeight(626);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	protected static MediaPlayer getMainMediaPlayer() {
		return MediaPlayerUtil.getMediaPlayer();
	}

	
	private void closeAllStages() {
	    for (Stage stage : openStages) {
	        stage.close();
	    }
	}
}
