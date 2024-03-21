package pack_morpion;

import ai.MultiLayerPerceptron;
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

	private double[][] matrix = new double[3][3];
	
	private int nbJoueur=1;
	
	private int buttonCount = 0;
	
	private MultiLayerPerceptron net;
	
	private double[] listMatrix= {0,0,0,0,0,0,0,0,0}; 
	
	public Game() {
		this.net=MultiLayerPerceptron.load("resources/models/model_128_4_1.0E-4.srf");
		this.initMatrix(matrix);
	}

	public void initMatrix(double[][] matrix2) {
		for(int i =0;i<3; i++) {
			for(int j =0;j<3; j++) {
				matrix2[i][j]=0;
			}
		}
	}
	
	public void affiche(double[] list) {
		for(int i =0;i<8; i++) {
			System.out.println(list[i]);
		}
	}
	
	public int findBestOutcome(double[] list) {
		int indice=0;
		double max=list[0];
		for(int i =1;i<8; i++) {
			if(list[i]>max && listMatrix[i]==0) {
				indice=i;
				max=list[i];
			}
		}
		return indice;
	}
	
	
	public boolean victory(double[][] matrix,int numJoueur) {
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
			if(i==0) {
				listMatrix[j]=nbJoueur;
			}
			else if(i==1){
				listMatrix[j+2]=nbJoueur;
			}
			else {
				listMatrix[j+4]=nbJoueur;
			}
			double[] coup = net.forwardPropagation(listMatrix);
			this.affiche(coup);
			this.affiche(listMatrix);
			System.out.println(this.findBestOutcome(coup));
			this.buttonCount++;
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
