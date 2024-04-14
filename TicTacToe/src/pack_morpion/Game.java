package pack_morpion;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
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
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Game extends Action{
	
	@FXML
	protected StackPane stackpane;
	@FXML
	protected StackPane stackPaneView;
	@FXML
	protected VBox VBoxGame;
	@FXML 
	protected GridPane contentGridPane;
	@FXML
	protected Button btn1;
    @FXML
    protected Button btn2;
    @FXML
    protected Button btn3;
    @FXML
    protected Button btn4;
    @FXML
    protected Button btn5;
    @FXML
    protected Button btn6;
    @FXML
    protected Button btn7;
    @FXML
    protected Button btn8;
    @FXML
    protected Button btn9;
    @FXML
    public Label LabelTurn;
    @FXML
    protected  Label labelWinX;
    @FXML
    protected  Label labelWinO;
    
    protected int winX = 0;
    
    protected int winO = 0;
    
    protected String[][] tableauJeu = new String[3][3];
    
    protected int buttonCount = 0;
    
    protected boolean joueurX = true;
	
	protected final Duration animationDuration = Duration.seconds(5);
	    
	protected final Duration animationDelay = Duration.seconds(0.01);
	    
	protected final int numConfetto = 1;
	
	public Timeline timelineConfetto;

	@FXML
    public Label labelTimer;
    
    public int seconds = 0;
    
    public Timeline timelineTimer;
    
    public Line line;
    
	
    protected void Replay() {
    	tableauJeu = new String[3][3];
    	joueurX = true;
    	buttonCount = 0;
    	
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
        stackpane.getChildren().remove(this.line);
    }
    
    protected void startTimer() {
    	timelineTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> this.updateTimer()));
    	timelineTimer.setCycleCount(Timeline.INDEFINITE);
    	timelineTimer.play();
    }
    
	protected void updateTimer() {
        seconds++;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        labelTimer.setText(String.format("%02d:%02d", minutes, secs));
    }
	
	protected void resetTimer() {
    	labelTimer.setText("00:00");
        seconds=0;
    	timelineTimer.stop();
    }
	
	protected void updateWin(String joueur) {
    	if(joueur == "X") {
    		winX++;
            labelWinX.setText(String.format("%d", winX));
    	}
    	else {
    		 winO++;
    	     labelWinO.setText(String.format("%d", winO));
    	}
    	this.resetTimer();
    }
	
	
	
	protected void confetto(Scene scene) {
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

	             KeyValue keyValue2 = new KeyValue(VBoxGame.translateXProperty(),scene.getWidth(), Interpolator.EASE_IN);
	             KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1), keyValue2);
	             timeline.getKeyFrames().add(keyFrame2);
	             
	             if(this.line!=null) {
	             	KeyValue keyValue3 = new KeyValue(line.translateXProperty(),scene.getWidth(), Interpolator.EASE_IN);
	             	KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1), keyValue3);
	             	timeline.getKeyFrames().add(keyFrame3);
	             }
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
	 
	 protected void afficherFenetreVictoire(String joueur) {
	    	try {
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("WinLayout.fxml"));
	            Parent root = loader.load();
	            WinController winController = loader.getController();
	            if(this instanceof GameAiController) {
	            	winController.setGameAiController((GameAiController) this);
		            winController.afficherVictoireAi(joueur);
	            }
	            else {
	            	winController.setGameController((GameController) this);
	                winController.afficherVictoire(joueur);
	            }
	            
	            this.transition(root,400,560,"son_stackpane_begin.wav","son_victory.wav",true);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	 
	 protected void transition(Parent root,int maxHeight,int maxWidth,String mediaStart,String mediaEnd, boolean victory) {
		 this.resetTimer();
		 stackPaneView.setVisible(true);
         stackPaneView.setMaxHeight(maxHeight);
         stackPaneView.setMaxWidth(maxWidth);
         stackPaneView.getChildren().setAll(root);
         stackPaneView.toFront();
         contentGridPane.setDisable(true);
		 
		 this.Media(mediaStart);
         
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
         	this.Media(mediaEnd);
         	
         	if(victory) {
         		this.confetto(scene);
         	}
         });
         translateTransition.play();
	 }
	 
	 protected boolean victory(String joueur) {
        for (int i = 0; i < 3; i++) {
            if (tableauJeu[i][0] == joueur && tableauJeu[i][1] == joueur && tableauJeu[i][2] == joueur) {
            	this.DrawLine(0,i);
            	this.updateWin(joueur);
                return true;
            }
            if (tableauJeu[0][i] == joueur && tableauJeu[1][i] == joueur && tableauJeu[2][i] == joueur) {
            	this.DrawLine(1,i);
            	this.updateWin(joueur);
                return true;
            }
        }
        if (tableauJeu[0][0] == joueur && tableauJeu[1][1] == joueur && tableauJeu[2][2] == joueur) {
        	this.DrawLine(2,0);
        	this.updateWin(joueur);
            return true;
        }
        if(tableauJeu[0][2] == joueur && tableauJeu[1][1] == joueur && tableauJeu[2][0] == joueur){
        	this.DrawLine(2,1);
        	this.updateWin(joueur);
            return true;
        }
        return false;
	 }
	 
	 private void DrawLine(int typeCase, int pos) {
		    if (typeCase == 0) {
		        this.line = new Line(0, 0, 300, 0);
		        if (pos == 0) {
		        	this.line.setStyle("-fx-translate-y: " + (this instanceof GameAiController ? "-75;" : "-97;"));
		        } else if (pos == 1) {
		        	this.line.setStyle("-fx-translate-y: " + (this instanceof GameAiController ? "35;" : "13;"));
		        } else {
		        	this.line.setStyle("-fx-translate-y: " + (this instanceof GameAiController ? "145;" : "124;"));
		        }
		    } else if (typeCase == 1) {
		    	this.line = new Line(0, (this instanceof GameAiController ? 0 : -90), 0, (this instanceof GameAiController ? 300 : 180));
		        if (pos == 0) {
		        	this.line.setStyle("-fx-translate-x: -111");
		        } else if (pos == 2) {
		        	this.line.setStyle("-fx-translate-x: 109");
		        }
		    } else {
		        if (pos == 0) {
		        	this.line = new Line(-5, -5, 245, 245);
		        } else {
		        	this.line = new Line(-5, 245, 245, -5);
		        }
		        this.line.setStyle("-fx-translate-x: " + (this instanceof GameAiController ? "-35;" : "-15;"));
		        this.line.setStyle("-fx-translate-y: " + (this instanceof GameAiController ? "35;" : "15;"));
		    }

		    line.setStrokeWidth(5);
		    stackpane.getChildren().add(line);
		}
}
