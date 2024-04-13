package pack_morpion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class GameController extends Action {

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
    public GridPane contentGridPane;

    private boolean joueurX = true; 

    private String[][] tableauJeu = new String[3][3];
    Stage stage;
    
    @FXML
    private StackPane stackpane;
    
    @FXML
    public StackPane stackPaneView;
    
    public Timeline timelineConfetto;
    
    
    private final Duration animationDuration = Duration.seconds(5);
    private final Duration animationDelay = Duration.seconds(0.01);
    private final int numConfetto = 1;
    
    @FXML
    public Label labelTimer;
    
    public int seconds = 0;
    
    public Timeline timelineTimer;
    
    @FXML
    public  Label labelWinO;
    
    public int winO = 0;
    
    @FXML
    public  Label labelWinX;
    
    public int winX = 0;
    
    @FXML
    public VBox VBoxGame;
    
    @FXML
    private void initialize() {
        timelineTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
    
        timelineTimer.setCycleCount(Timeline.INDEFINITE);
        timelineTimer.play();
	}
	
	private void updateTimer() {
	    seconds++;
	    int minutes = (seconds % 3600) / 60;
	    int secs = seconds % 60;
	    labelTimer.setText(String.format("%02d:%02d", minutes, secs));
	}
	
	private void updateWin(String joueur) {
	        if(joueur == "X") {
	                winX++;
	        labelWinX.setText(String.format("%d", winX));
	        }
	        else {
	                 winO++;
	             labelWinO.setText(String.format("%d", winO));
	        }
	        labelTimer.setText("00:00");
	    seconds=0;
	        timelineTimer.stop();
	}
    
    
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

        timelineTimer.play();
    }
    
    protected void afficherVersusLayout() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
        	Parent root = loader.load();
            VersusController versusController = loader.getController();
            

            Scene scene = contentGridPane.getScene();
            root.translateXProperty().set(-scene.getWidth());
            stackpane.getChildren().add(root);
            
            this.Media("son_transition_end.wav");
            
            BorderPane borderPane = (BorderPane) scene.getRoot(); 
            ToolBar toolBar = (ToolBar) borderPane.getTop(); 
            
            if (toolBar != null) {
                ObservableList<Node> items = toolBar.getItems();
                for (Node item : items) {
                    if (item instanceof MenuBar) {
                        MenuBar menuBar = (MenuBar) item;
                        menuBar.setVisible(true); 
                    }
                }
            }
      
            Timeline timeline = new Timeline();

            KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
            timeline.getKeyFrames().add(keyFrame);

            KeyValue keyValue2 = new KeyValue(contentGridPane.translateXProperty(),scene.getWidth(), Interpolator.EASE_IN);
            KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), keyValue2);
            timeline.getKeyFrames().add(keyFrame2);
            timeline.setOnFinished(event -> { 
            	if(timelineConfetto !=null) {
                    timelineConfetto.stop();
            	}
            	stackpane.getChildren().setAll(root);});
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
           
            stackPaneView.setVisible(true);
            stackPaneView.setMaxHeight(300);
            stackPaneView.setMaxWidth(300);
            stackPaneView.getChildren().setAll(root);
            stackPaneView.toFront();
            
            this.Media("son_stackpane_begin.wav");
            
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), stackPaneView);
            translateTransition.setFromY(-stackPaneView.getHeight());
            translateTransition.setToY(0);
            
            TranslateTransition bounceTransition = new TranslateTransition(Duration.millis(300), stackPaneView);
            bounceTransition.setFromY(0);
            bounceTransition.setToY(-20);
            bounceTransition.setCycleCount(2);
            bounceTransition.setAutoReverse(true);
            
            translateTransition.setOnFinished(event ->{ 
            	bounceTransition.play();

            	this.Media("son_draw.wav");
            	
            	});
            translateTransition.play();
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
            
            stackPaneView.setVisible(true);
            stackPaneView.setMaxHeight(400);
            stackPaneView.setMaxWidth(560);
            stackPaneView.getChildren().setAll(root);
            stackPaneView.toFront();
            contentGridPane.setDisable(true);
            
            this.Media("son_stackpane_begin.wav");
            
            Scene scene = this.stackPaneView.getScene();
            
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), stackPaneView);
            translateTransition.setFromY(-scene.getHeight());
            translateTransition.setToY(0);
            
            TranslateTransition bounceTransition = new TranslateTransition(Duration.millis(300), stackPaneView);
            bounceTransition.setFromY(0);
            bounceTransition.setToY(-20);
            bounceTransition.setCycleCount(2);
            bounceTransition.setAutoReverse(true);
            
            translateTransition.setOnFinished(event ->{ 
            	bounceTransition.play();

            	this.Media("son_victory.wav");
            	
            	this.confetto(scene);
            	
            	});
            translateTransition.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void confetto(Scene scene) {
    	List<String> confettoImages = Arrays.asList("file:rss/images/confetto_vert.png", 
    			"file:rss/images/confetto_bleu.png", 
    			"file:rss/images/confetto_rouge.png",
    			"file:rss/images/confetto_jaune.png",
    			"file:rss/images/confetto_blanc.png",
    			"file:rss/images/confetto_violet.png",
    			"file:rss/images/confetto_orange.png",
    			"file:rss/images/confetto_rose.png");

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event1 -> {
                for (int i = 0; i < numConfetto; i++) {
                	String imagePath = confettoImages.get((int) (Math.random() * confettoImages.size()));
                    Image confettoImage = new Image(imagePath);
                    
                    ImageView imageView = new ImageView(confettoImage);
                    
                    imageView.setTranslateX(Math.random() * scene.getWidth() * 2 - scene.getWidth());
                    imageView.setTranslateY(-scene.getHeight());
                    
                    imageView.fitWidthProperty().bind(stackpane.widthProperty().divide(50));
                    imageView.fitHeightProperty().bind(stackpane.heightProperty().divide(50));
                    
                    
                    stackpane.getChildren().add(imageView);
                    imageView.toBack();
                    
                    TranslateTransition translateTransition1 = new TranslateTransition(animationDuration, imageView);
                    translateTransition1.setToY(600);
                    translateTransition1.setOnFinished(e -> stackpane.getChildren().remove(imageView));
                    translateTransition1.play();
                }
        }), new KeyFrame(animationDelay));

        timelineConfetto=timeline;
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    

    private boolean verifierGagnant(String joueur) {
        for (int i = 0; i < 3; i++) {
            if (tableauJeu[i][0] == joueur && tableauJeu[i][1] == joueur && tableauJeu[i][2] == joueur) {
            	this.updateWin(joueur);
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (tableauJeu[0][j] == joueur && tableauJeu[1][j] == joueur && tableauJeu[2][j] == joueur) {
            	this.updateWin(joueur);
                return true;
            }
        }
        if ((tableauJeu[0][0] == joueur && tableauJeu[1][1] == joueur && tableauJeu[2][2] == joueur)
                || (tableauJeu[0][2] == joueur && tableauJeu[1][1] == joueur && tableauJeu[2][0] == joueur)) {
        	this.updateWin(joueur);
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