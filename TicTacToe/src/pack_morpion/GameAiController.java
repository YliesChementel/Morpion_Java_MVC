package pack_morpion;

import java.io.IOException;

import ai.MultiLayerPerceptron;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;


public class GameAiController extends Action{
	
	@FXML
    private StackPane stackpane;

    @FXML 
    public GridPane contentGridPaneAi;
    
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

    private int buttonCount = 0;
    private int nbJoueur = 1;
    private String[][] tableauJeu = new String[3][3];
    private MultiLayerPerceptron net;
    private double[] listMatrix = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private boolean turnAI = false;
    
    @FXML
    public StackPane stackPaneView;
    
    
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
    
    public Timeline timelineConfetto;
    
    private final Duration animationDuration = Duration.seconds(5);
    
    private final Duration animationDelay = Duration.seconds(0.01);
    
    private final int numConfetto = 1;
    

    public GameAiController() {
        this.initMatrix(tableauJeu);
    }
    
    
    
    public void setAiModelPath(String modelPath) {
        this.net = MultiLayerPerceptron.load(modelPath);
    }

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
	
	private void updateWin(int joueur) {
	        if(joueur == 1) {
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
    
    public void rejouerPartieSansChanger() {
    	turnAI = false; 
        tableauJeu = new String[3][3]; 
        listMatrix = new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        buttonCount = 0;
        nbJoueur = 1;

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
    
    public void rejouerPartie(String modelPath) {
    	turnAI = false; 
        tableauJeu = new String[3][3]; 
        listMatrix = new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        buttonCount = 0;
        nbJoueur = 1;
        setAiModelPath(modelPath);

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

            stackPaneView.setVisible(true);
            stackPaneView.setMaxHeight(400);
            stackPaneView.setMaxWidth(560);
            stackPaneView.getChildren().setAll(root);
            stackPaneView.toFront();
            contentGridPaneAi.setDisable(true);
            
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

            	this.Media("son_loss.wav");
            	
            	});
            translateTransition.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	private void playAi() {
        double[] coup = net.forwardPropagation(this.listMatrix);
        int BestOutcome = findBestOutcome(coup);
        int row = BestOutcome / 3;
        int col = BestOutcome % 3;
        
        for (int i = 0; i < 9; i++) {
            System.out.println(coup[i]);
	    }
	    System.out.println();
	    contentGridPaneAi.setDisable(true);
	    
        for (Node node : contentGridPaneAi.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Button) {
                Button aiButton = (Button) node;
               // Platform.runLater(() -> aiButton.fire());
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> {
                    aiButton.fireEvent(event);
               
                }));
                timeline.setOnFinished(event -> contentGridPaneAi.setDisable(false));
                timeline.play();
                break;
            }
        }
    }

    private int findBestOutcome(double[] list) {
        int indice = 0;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 9; i++) {
            if (list[i] > max && listMatrix[i] == 0.0) {
                
                max = list[i];
                indice = i;
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
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
            	this.updateWin(player);
                return true; 
            }
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
            	this.updateWin(player);
                return true;
            }
        }
        
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
            (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
        	this.updateWin(player);
            return true; 
        }
        return false;
    }
    
    protected void afficherVersusLayout() {
    	try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("VersusLayout.fxml"));
        	 Parent root = loader.load();
             VersusController versusController = loader.getController();
            

             Scene sceneAi = contentGridPaneAi.getScene();
             root.translateXProperty().set(-sceneAi.getWidth());
             stackpane.getChildren().add(root);
             
             this.Media("son_transition_end.wav");
             
             BorderPane borderPane = (BorderPane) sceneAi.getRoot();
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

             KeyValue keyValue2 = new KeyValue(contentGridPaneAi.translateXProperty(),sceneAi.getWidth(), Interpolator.EASE_IN);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NullAiLayout.fxml"));
            Parent root = loader.load();
            NullAiController nullAiController = loader.getController();
            nullAiController.setGameAiController(this);
            
            stackPaneView.setVisible(true);
            stackPaneView.setMaxHeight(300);
            stackPaneView.setMaxWidth(300);
            stackPaneView.getChildren().setAll(root);
            stackPaneView.toFront();
            contentGridPaneAi.setDisable(true);
            
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
            winController.setGameAiController(this);
            winController.afficherVictoireAi(joueur);
            
            stackPaneView.setVisible(true);
            stackPaneView.setMaxHeight(400);
            stackPaneView.setMaxWidth(560);
            stackPaneView.getChildren().setAll(root);
            stackPaneView.toFront();
            contentGridPaneAi.setDisable(true);
            
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

                this.Media("son_victory.wav");
                
            	});
            translateTransition.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}