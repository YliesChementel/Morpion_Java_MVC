package pack_morpion;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import ai.Coup;
import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import ai.Test;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Contrôleur pour la création et l'initialisation d'un modèle de L'IA.
 * Charge les données d'entraînement à partir d'un fichier et entraîne le modèle.
 */
public class ModelController {

	// Paramètres d'interface graphique (FXML)
	@FXML
	private ProgressBar progressBar;
	@FXML
	private TextField textField;
	@FXML
	private VBox filesContainer;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Label progressLabel;

	// Données du modèle
	private String file;
	private int h;
	private double lr;
	private int l;

	/**
	 * Définit les paramètres pour la création du modèle.
	 * 
	 * @param h La taille de la couche.
	 * @param lr Le taux d'apprentissage.
	 * @param l Le nombre de couches.
	 * @param file Le chemin du fichier de sauvegarde du modèle.
	 */
	public void setParameters(int h, double lr, int l, String file) {
		this.h = h;
		this.lr = lr;
		this.l = l;
		this.file = file;
	}

	/**
	 * Initialise le contrôleur.
	 */
	@FXML
	public void initialize() {
		progressLabel.textProperty().bind(progressBar.progressProperty().multiply(100).asString("%.2f%%"));
	}


	@FXML
	protected void initializeModel() {
		// Chargement des données d'entraînement
		HashMap<Integer, Coup> mapTrain = Test.loadCoupsFromFile(".\\rss\\train_dev_test\\train.txt");
		int size = 9; 
		double epochs = 1000;
		int[] layers = new int[l+2];
		layers[0] = size ;
		for (int i = 0; i < l; i++) {
			layers[i+1] = h ;
		}
		layers[layers.length-1] = size ;

		// Création du réseau de neurones
		MultiLayerPerceptron net = new MultiLayerPerceptron(layers, lr, new SigmoidalTransferFunction());
		// Tâche de formation du modèle
		Task<Double> task = new Task<Double>() {
			double error = 0.0 ;
			double bestError = 10000.0;
			int batch = 200;
			@Override
			protected Double call() throws InterruptedException {
				for(int i = 0; i < epochs; i++){

					double error = 0.0 ;
					for (int j = 0; j < batch; j++) {
						Coup c = null ;
						while ( c == null )
							c = mapTrain.get((int)(Math.round(Math.random() * mapTrain.size())));

						error += net.backPropagate(c.in, c.out);
					}
					error/=batch;

					if(bestError > error) {
						bestError=error/(double)i;
						updateMessage("Error at step "+i+" is "+bestError);
					}

					updateProgress(i, epochs);

				}
				// Sauvegarde du modèle
				net.save(file);
				updateMessage("Learning completed!");
				return -1.0;
			}
		};
		// Liaison de la barre de progression à la progression de la tâche
		progressBar.progressProperty().bind(task.progressProperty());
		textField.textProperty().bind(task.messageProperty());
		new Thread(task).start();
		// Gestion des événements de réussite et d'échec de la tâche
		task.setOnSucceeded(workerStateEvent -> {

			File newFile = new File(file);
			try {
				boolean success = newFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Stage stage = (Stage) progressBar.getScene().getWindow();
			stage.close();          
		});

		task.setOnFailed(workerStateEvent -> {
			task.getException().printStackTrace();
		});
	}   
}