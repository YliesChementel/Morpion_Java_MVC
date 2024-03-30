package pack_morpion;

//import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
//import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
        primaryStage.setTitle("LE SUPER MORPION");
        Image icon = new Image("file:rss/images/Morpion.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(new Scene(root, 720, 626));
        primaryStage.setMinWidth(720);
        primaryStage.setMinHeight(626);
        primaryStage.show();
/*
        // Création d'un rectangle
        Rectangle rect = new Rectangle(50, 50, Color.BLUE);
        rect.setTranslateX(0); // Déplacement initial
        rect.setTranslateY(300);

        // Création de l'animation de déplacement du rectangle
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), rect);
        translateTransition.setFromX(0); // Position de départ
        translateTransition.setToX(500); // Position finale
        translateTransition.setAutoReverse(true); // Pour revenir à la position initiale
        translateTransition.setCycleCount(Animation.INDEFINITE); // Nombre de répétitions (-1 pour une répétition infinie)

        // Ajout du rectangle à la scène
        Pane rootPane = (Pane) primaryStage.getScene().getRoot();
        rootPane.getChildren().add(rect);

        // Démarrage de l'animation
        translateTransition.play();
        */
    }

    public static void main(String[] args) {
        launch(args);
    }
}