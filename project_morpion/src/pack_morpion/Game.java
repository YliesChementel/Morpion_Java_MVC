package pack_morpion;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Game extends Application{

	private int[][] matrix = new int[2][2];
	
	private int nbJoueur=1;
	
	public void initMatrix(int[][] matrix) {
		for(int i =0;i<matrix.length; i++) {
			for(int j =0;i<matrix[j].length; j++) {
				matrix[i][j]=0;
				System.out.print(matrix[i][j]);
			}
			System.out.println();
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
	
	public Button buttonCases(Game game) {
		Button button = new Button();
		button.setOnAction( e -> {
			Node source = (Node) e.getSource();
			GridPane parentGrid = (GridPane) source.getParent();
			int i = parentGrid.getRowIndex(button);
			int j = parentGrid.getColumnIndex(button);
			matrix[i][j]=nbJoueur;
			if(game.victory(matrix, nbJoueur)){
				System.out.println("Victoire du joueur : "+nbJoueur);
			}
			 
			if(nbJoueur==1) {
				button.setText("X");
				nbJoueur=2;
			}
			else if(nbJoueur==2) {
				button.setText("O");
				nbJoueur=1;
			}	
		});
		return button;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,500,500);
			Game game = new Game();
			game.initMatrix(matrix);
			HBox hbox = new HBox();
			GridPane grid = new GridPane();
			grid.setHgap(40);
			Button button = buttonCases(game);
			grid.add( buttonCases(game), 0, 0);
		
			grid.add( buttonCases(game), 0, 1);
			grid.add( buttonCases(game), 0, 2);
			grid.add( buttonCases(game), 1, 0);
			grid.add( buttonCases(game), 1, 1);
			grid.add( buttonCases(game), 1, 2);
			grid.add( buttonCases(game), 2, 0);
			grid.add( buttonCases(game), 2, 1);
			grid.add( buttonCases(game), 2, 2);
			hbox.getChildren().addAll(grid);
			root.setCenter(hbox);
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
