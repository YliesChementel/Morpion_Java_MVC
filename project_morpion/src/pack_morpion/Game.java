package pack_morpion;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class Game extends Application{

	private int[][] matrix = new int[3][3];
	
	private int nbJoueur=1;
	
	private int buttonCount = 0;
	
	public void initMatrix(int[][] matrix) {
		for(int i =0;i<3; i++) {
			for(int j =0;j<3; j++) {
				matrix[i][j]=0;
			}
		}
	}
	
	public boolean victory(int[][] matrix,int numJoueur) {
		if(matrix[0][0]==numJoueur && matrix[1][1]==numJoueur && matrix[2][2]==numJoueur) {
			return true;
		}
		if(matrix[0][2]==numJoueur && matrix[1][1]==numJoueur && matrix[2][0]==numJoueur) {
			return true;
		}
		if(matrix[0][0]==numJoueur && matrix[0][1]==numJoueur && matrix[0][2]==numJoueur) {
			return true;
		}
		if(matrix[1][0]==numJoueur && matrix[1][1]==numJoueur && matrix[1][2]==numJoueur) {
			return true;
		}
		if(matrix[2][0]==numJoueur && matrix[2][1]==numJoueur && matrix[2][2]==numJoueur) {
			return true;
		}
		if(matrix[0][0]==numJoueur && matrix[1][0]==numJoueur && matrix[2][0]==numJoueur) {
			return true;
		}
		if(matrix[0][1]==numJoueur && matrix[1][1]==numJoueur && matrix[2][1]==numJoueur) {
			return true;
		}
		if(matrix[0][2]==numJoueur && matrix[1][2]==numJoueur && matrix[2][2]==numJoueur) {
			return true;
		}
		return false;
	}
	
	public void EndGameStackPane(Stage primaryStage, int nbJoueur) {
	    StackPane endScreen = new StackPane();
	    endScreen.setMaxSize(550, 400);
	    Label labelVictory;
	    System.out.println(buttonCount);
	    if (nbJoueur == 0) {
	    	labelVictory = new Label("Égalité");
	    }
	    else {
	    	labelVictory = new Label("Victoire du joueur : " + nbJoueur);
	    }
	    Button replayButton = new Button("Rejouer");
	    Button exitButton = new Button("Quitter");
	    replayButton.setOnAction(event -> {
	    	this.start(primaryStage);
	        primaryStage.show();
	    });
	    exitButton.setOnAction(event -> {
	        primaryStage.close(); 
	    });
	 	HBox hbox = new HBox(10, replayButton, exitButton);
	 	hbox.setAlignment(Pos.CENTER);
	    VBox vbox=new VBox(10, labelVictory,hbox);
	    vbox.setAlignment(Pos.CENTER);
	    endScreen.getChildren().add(vbox);
	    StackPane.setAlignment(hbox, Pos.CENTER);
	    Scene endScene = new Scene(endScreen, 550, 400);
	    primaryStage.setScene(endScene);
	    primaryStage.show();
	}
	
	public Button buttonCases(Stage primaryStage,Game game) {
		Button button = new Button();
		button.setPrefWidth(100); 
	    button.setPrefHeight(100);
	    button.setFocusTraversable(false);
	    button.setFont(Font.font(45));
		button.setOnAction( e -> {
			int i = GridPane.getRowIndex(button);
			int j = GridPane.getColumnIndex(button);
			matrix[i][j]=nbJoueur;
			this.buttonCount++;
			System.out.println(buttonCount);
			if(game.victory(matrix, nbJoueur)){
				game.EndGameStackPane(primaryStage,nbJoueur);
			}
			if (this.buttonCount == 9) {
	             game.EndGameStackPane(primaryStage, 0); 
	        }
			 
			if(nbJoueur==1) {
				button.setText("X");
				nbJoueur=2;
			}
			else if(nbJoueur==2) {
				button.setText("O");
				nbJoueur=1;
			}	
			button.setDisable(true);
			button.setStyle("-fx-opacity: 1;");
		});
		return button;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Game game = new Game();
			game.initMatrix(matrix);
			GridPane grid = new GridPane();
			grid.setHgap(1);
	        grid.setVgap(1);
			for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                Button button = buttonCases(primaryStage,game);
	                grid.add(button, i, j);
	            }
	        }
		    root.setCenter(grid);
		    Scene scene = new Scene(root,500,500);
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
