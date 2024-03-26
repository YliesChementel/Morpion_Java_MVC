package pack_morpion;

import java.io.IOException;

import ai.MultiLayerPerceptron;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameAiController {

    @FXML
    private GridPane contentGridPaneAi;

    private int buttonCount = 0;
    private int nbJoueur = 1;
    private String[][] tableauJeu = new String[3][3];
    private MultiLayerPerceptron net;
    private double[] listMatrix = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private boolean turnAI = false;

    public GameAiController() {
        this.initMatrix(tableauJeu);
    }
    
    public void setAiModelPath(String modelPath) {
        this.net = MultiLayerPerceptron.load(modelPath);
    }

    public void initialize() {
    }


    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

        if (tableauJeu[row][col] != null) {
            return; 
        }

        tableauJeu[row][col] = nbJoueur == 1 ? "X" : "O";
        listMatrix[row * 3 + col] = nbJoueur;
        button.setText(nbJoueur == 1 ? "X" : "O");
        buttonCount++;

        if (victory(tableauJeu, nbJoueur)) {
            if (nbJoueur == 1) {
                afficherFenetreVictoire("Joueur");
            } else {
                
                afficherFenetrePerdu();
            }
        } else if (buttonCount == 9) {
            afficherFenetreNull();
        }
        else {
	        nbJoueur = nbJoueur == 1 ? 2 : 1;
	        turnAI = !turnAI;
	
	        if (turnAI) {
	            playAi();
	        }
        }
    }

    private void afficherFenetrePerdu() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LostLayout.fxml"));
            Parent root = loader.load();

            LostController lostController = loader.getController();
            lostController.setGameAiController(this);

            Stage stage = new Stage();
            lostController.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("Défaite");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	private void playAi() {
        double[] coup = net.forwardPropagation(this.listMatrix);
        int BestOutcome = findBestOutcome(coup);
        int row = BestOutcome / 3;
        int col = BestOutcome % 3;

        for (Node node : contentGridPaneAi.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Button) {
                Button aiButton = (Button) node;
                Platform.runLater(() -> aiButton.fire());
                break;
            }
        }
    }

    private int findBestOutcome(double[] list) {
        int indice = 0;
        double max = list[0];
        for (int i = 0; i < 9; i++) {
            if (list[i] > max && listMatrix[i] == 0.0) {
                indice = i;
                max = list[i];
            }
        }
        return indice;
    }

    private void initMatrix(String[][] matrix2) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix2[i][j] = null;
            }
        }
    }

    private boolean victory(String[][] board, int player) {
        String symbol = player == 1 ? "X" : "O";
        // Vérifier les lignes et les colonnes
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true; // Ligne gagnante
            }
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
                return true; // Colonne gagnante
            }
        }
        // Vérifier les diagonales
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
            (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            return true; // Diagonale gagnante
        }
        return false;
    }
    
    protected void afficherVersusLayout() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
            Parent root = loader.load();
            VersusController versusController = loader.getController();
            contentGridPaneAi.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherFenetreNull() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NullAiLayout.fxml"));
            Parent root = loader.load();
            
            NullAiController nullAiController = loader.getController();
            nullAiController.setGameAiController(this);
                                
            Stage stage = new Stage();
            nullAiController.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("Nulle");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherFenetreVictoire(String joueur) {
    	try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("WinLayout.fxml"));
            Parent root = loader.load();
            WinController winController = loader.getController();
            winController.setGameAiController(this);
            
            
            winController.afficherVictoireAi(joueur);
            
            Stage stage = new Stage();
            winController.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("Félicitations !");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
