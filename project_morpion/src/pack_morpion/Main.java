package pack_morpion;
	

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.animation.*;



public class Main extends Application {
	
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
	
	
	public HBox ChoicesHbox(Stage primaryStage) {
			HBox hbox = new HBox();
			Button homme_Vs_Ai = new Button("H VS AI");
			Button homme_Vs_Homme = new Button("H VS H");
			GridPane grid = new GridPane();
			grid.setHgap(40);
			grid.add(homme_Vs_Ai, 2, 0);
			ToggleGroup group = new ToggleGroup();
			
		    RadioButton radioF = new RadioButton("F");
		    radioF.setSelected(true);
		    RadioButton radioM = new RadioButton("M");
		    RadioButton radioD = new RadioButton("D");
		    radioF.setToggleGroup(group);
		    radioM.setToggleGroup(group);
		    radioD.setToggleGroup(group);
		    homme_Vs_Ai.setOnAction(event -> {
				RadioButton button = (RadioButton) group.getSelectedToggle();
				ConfigFileLoader configLoad = new ConfigFileLoader();
				configLoad.loadConfigFile("resources/config.txt");
				Config config = configLoad.get(button.getText());
				String modelName ="model_"+config.hiddenLayerSize+"_"+config.numberOfhiddenLayers+"_"
				+config.learningRate+".srf";
				System.out.println(modelName);
				
		        String repertoire = "resources/models/";

		        String file=repertoire+modelName;
		        
		        File tempFile = new File(file);
		        boolean exists = tempFile.exists();
		        if(exists) {
		        	System.out.println("existe");
		        }
		        else{
		        	System.out.println("existe pas");
		        	Scene sceneLoad = LoadScene();
		        	primaryStage.setScene(sceneLoad);
		        	File newFile = new File(file);
		            try {
						boolean success = newFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		    });
				
		    grid.add(radioF, 1, 1);
		    grid.add(radioM, 2, 1);
		    grid.add(radioD, 3, 1);
			grid.add(homme_Vs_Homme, 2, 2);
			hbox.getChildren().addAll(grid);
			return hbox;
	}
	
	public Scene ChoicesScene(Stage primaryStage) {
		 BorderPane root = new BorderPane();
		 root.setCenter(ChoicesHbox(primaryStage));
		 Scene scene = new Scene(root, 500, 500);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 return scene;
	}
	
	public Scene LoadScene() { //https://blog.ronanlefichant.fr/2022/10/javafx-task-progressbar.html?showComment=1670222972563
		 BorderPane root = new BorderPane();
		 Scene scene = new Scene(root, 500, 500);
		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		 return scene;
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			MenuBar menubar = createMenus();
			root.setTop(menubar);
			
			//////A METTRE DANS UNE FONCTION
			HBox hbox = new HBox();
			Button play = new Button("play");
			play.setOnAction(event -> {
				Scene scenePlay = ChoicesScene(primaryStage);
	            primaryStage.setScene(scenePlay);
					});
			root.setCenter(play);
			////////////////////	
		
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
