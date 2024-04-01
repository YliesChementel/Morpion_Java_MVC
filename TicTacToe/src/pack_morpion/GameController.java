package pack_morpion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

public class GameController {

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    
    @FXML
    private GridPane contentGridPane;

    private boolean joueurX = true; 

    private String[][] tableauJeu = new String[3][3];
    Stage stage;
    
    @FXML
    private StackPane stackpane;

    
    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button bouton= (Button) event.getSource();
        int row = GridPane.getRowIndex(bouton); 
        int col = GridPane.getColumnIndex(bouton); 

        if (bouton.getText().isEmpty()) {
            if (joueurX) {
                bouton.setText("X"); 
                tableauJeu[row][col] = "X"; 
                joueurX = false;
            } else {
                bouton.setText("O"); 
                tableauJeu[row][col] = "O"; 
                joueurX = true;
            }
            
            if (verifierGagnant("X")) {
            	
            	afficherFenetreVictoire("X");
            } else if (verifierGagnant("O")) {
            	afficherFenetreVictoire("O");
            } else if (partieNulle()) {
            	afficherFenetreNull();
            }
        }
    }
    
    public void rejouerPartie() {
    	joueurX = true;
        tableauJeu = new String[3][3];

        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");

        if (stage != null) {
            stage.close();
        }
    }
    
    protected void afficherVersusLayout() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
        	 Parent root = loader.load();
             VersusController versusController = loader.getController();
            

             Scene sceneAi = contentGridPane.getScene();
             root.translateXProperty().set(-sceneAi.getWidth());
             stackpane.getChildren().add(root);
             Timeline timeline = new Timeline();

             KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
             KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
             timeline.getKeyFrames().add(keyFrame);

             KeyValue keyValue2 = new KeyValue(contentGridPane.translateXProperty(),sceneAi.getWidth(), Interpolator.EASE_IN);
             KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), keyValue2);
             timeline.getKeyFrames().add(keyFrame2);
             timeline.setOnFinished(event -> stackpane.getChildren().setAll(root));
             timeline.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private void afficherFenetreNull() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NullLayout.fxml"));
            Parent root = loader.load();
            NullController nullController = loader.getController();
            nullController.setGameController(this);
            Stage stage = new Stage();
            
            Image icon = new Image("file:rss/images/draw-icon.jpg");
            stage.getIcons().add(icon);
            
            nullController.setStage(stage);
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
            winController.setGameController(this);
            winController.afficherVictoire(joueur);
            Stage stage = new Stage();
            
            Image icon = new Image("file:rss/images/victory-icon.png");
            stage.getIcons().add(icon);
            
            winController.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("Félicitations !");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean verifierGagnant(String joueur) {
        for (int i = 0; i < 3; i++) {
            if (tableauJeu[i][0] == joueur && tableauJeu[i][1] == joueur && tableauJeu[i][2] == joueur) {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (tableauJeu[0][j] == joueur && tableauJeu[1][j] == joueur && tableauJeu[2][j] == joueur) {
                return true;
            }
        }
        if ((tableauJeu[0][0] == joueur && tableauJeu[1][1] == joueur && tableauJeu[2][2] == joueur)
                || (tableauJeu[0][2] == joueur && tableauJeu[1][1] == joueur && tableauJeu[2][0] == joueur)) {
            return true;
        }
        return false;
    }

    private boolean partieNulle() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tableauJeu[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }
}
