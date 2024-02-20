package pack_morpion;
	

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.animation.*;



public class Main extends Application {
	
	public HBox createHBox () {
		HBox hbox = new HBox();
		Button b1 = new Button("Transition");
		b1.setOnAction(event -> {
			});
		hbox.getChildren().addAll(b1);
		return hbox;
	}

	
	public MenuBar createMenus () {
		MenuBar menubar = new MenuBar();
		Menu menu1 = new Menu("Menu");
		MenuItem settings=Settings();
		menu1.getItems().add(settings);
		menu1.getItems().add(new MenuItem("Models"));
		menubar.getMenus().addAll(menu1);
		return menubar;
		}
	
	public MenuItem Settings() {
		MenuItem settings = new MenuItem("Settings");
		settings.setOnAction(event -> {
			Scene secondScene = SettingsScene();
		});
		return settings;
	}
	
	public Scene SettingsScene() {
		ConfigFileLoader configLoad = new ConfigFileLoader();
		configLoad.loadConfigFile("resources/config.txt");
		Config config = configLoad.get("F");
		System.out.println(config);
		
		StackPane secondaryLayout = new StackPane();
		BorderPane root = new BorderPane();
		secondaryLayout.getChildren().add(SettingsHbox());
		Scene secondScene = new Scene(secondaryLayout, 230, 100);
		Stage newWindow = new Stage();
		newWindow.setTitle("Settings");
		newWindow.setScene(secondScene);
		newWindow.show();
		return secondScene;
	}
	
	public HBox SettingsHbox() {
		HBox hbox = new HBox();
		GridPane grid = new GridPane();
		grid.setHgap(40);
		grid.add(new Label("F"), 0, 0);
		grid.add(new TextField(), 1, 0);
		grid.add(new TextField(), 2, 0);
		grid.add(new TextField(), 3, 0);
		grid.add(new Label("M"), 0, 1);
		grid.add(new TextField(), 1, 1);
		grid.add(new TextField(), 2, 1);
		grid.add(new TextField(), 3, 1);
		grid.add(new Label("D"), 0, 2);
		grid.add(new TextField(), 1, 2);
		grid.add(new TextField(), 2, 2);
		grid.add(new TextField(), 3, 2);
		hbox.getChildren().addAll(grid);
		return hbox;
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			MenuBar menubar = createMenus();
			root.setTop(menubar);
			
			Scene scene = new Scene(root,500,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
