package pack_morpion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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

    private boolean joueurX = true; 

    private String[][] tableauJeu = new String[3][3];

    
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
            	
                System.out.println("Le joueur X a gagné !");
                
            } else if (verifierGagnant("O")) {
                System.out.println("Le joueur O a gagné !");
            } else if (partieNulle()) {
                System.out.println("Partie nulle !");
            }
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
