package pack_morpion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import javafx.event.ActionEvent;

public class GameController extends Game {

	@FXML
    public Label LabelNameX;
    @FXML
    public Label LabelNameO;

    public String nomJoueurX;
    
    public String nomJoueurO;
    
    public VersusController versusController;
    
    
    @FXML
	private void initialize() {
    	this.startTimer();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
    	try {
			Parent root = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	this.versusController = loader.getController();
        
        this.nomJoueurX = this.versusController.getNOM_JOUEURX();
        this.nomJoueurO = this.versusController.getNOM_JOUEURO();
        LabelNameX.setText(nomJoueurX);
    	LabelNameO.setText(nomJoueurO);
    	LabelTurn.toBack();
    	this.updateTurn();
    }
    
    private void updateTurn() {
    	if(joueurX) {
    		if(nomJoueurX=="") {
        		LabelTurn.setText("C'est à X de jouer");
        	}
    		else {
    			LabelTurn.setText("C'est à "+nomJoueurX+" de jouer");
    		}
    	}
    	else {
    		if(nomJoueurO=="") {
        		LabelTurn.setText("C'est à O de jouer");
        	}
    		else {
    			LabelTurn.setText("C'est à "+nomJoueurO+" de jouer");
    		}
    	}
    	
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button boutton= (Button) event.getSource();
        int row = GridPane.getRowIndex(boutton); 
        int col = GridPane.getColumnIndex(boutton); 

        if (tableauJeu[row][col] != null) {
            return; 
        }
        
        tableauJeu[row][col] = joueurX == true ? "X" : "O";
        boutton.setText(joueurX == true ? "X" : "O");
        buttonCount++;
            
        if (this.victory("X")) {
        	afficherFenetreVictoire(nomJoueurX);
        } else if (this.victory("O")) {
        	afficherFenetreVictoire(nomJoueurO);
        } else if (buttonCount == 9) {
            afficherFenetreNull();
        }else {
        	joueurX = !joueurX;
        }
        this.updateTurn();
    }
    
    public void rejouerPartie() {
        this.Replay();
    }
    
    private void afficherFenetreNull() {
    	this.resetTimer();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NullLayout.fxml"));
            Parent root = loader.load();
            StackViewController stackViewController = loader.getController();
            stackViewController.setGameController(this);
           
            this.transition(root,300,300,"son_stackpane_begin.wav","son_draw.wav",false);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}