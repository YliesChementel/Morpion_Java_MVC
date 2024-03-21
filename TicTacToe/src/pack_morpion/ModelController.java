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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;



public class ModelController {
	@FXML
    private ProgressBar progressBar;

    @FXML
    private TextField textField;
    
    @FXML
    private Button startButton;
    
    @FXML
    private VBox filesContainer;
	
    private String file;
    private int h;
    private double lr;
    private int l;
    
    

    private final File folder = new File(".\\rss\\models\\");

    
    
    public ModelController() {
    }
    public void setParameters(int h, double lr, int l, String file) {
        this.h = h;
        this.lr = lr;
        this.l = l;
        this.file = file;
    }
    
    @FXML
    public void initialize() {
    	// Get list of files
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    // Create a CheckBox for each file
                    CheckBox checkBox = new CheckBox(file.getName());
                    filesContainer.getChildren().add(checkBox);
                }
            }
        }
        else {
        	System.out.println("dossier vide");
        }
    }

    @FXML
    private void deleteSelectedFiles() {
        for (int i = 0; i < filesContainer.getChildren().size(); i++) {
            if (filesContainer.getChildren().get(i) instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) filesContainer.getChildren().get(i);
                if (checkBox.isSelected()) {
                    File fileToDelete = new File(folder.getPath() + File.separator + checkBox.getText());
                    if (fileToDelete.delete()) {
                        System.out.println("File deleted: " + fileToDelete.getName());
                    } else {
                        System.out.println("Failed to delete file: " + fileToDelete.getName());
                    }
                }
            }
        }
    }

    @FXML
    public void initializeComponents() {
        startButton.setOnAction(event -> handleStartButton());
    }
    @FXML
    private void handleStartButton() {
    	
    	System.out.println(l+"  " + lr+"  "+ h +"  "+file);
    	HashMap<Integer, Coup> mapTrain = Test.loadCoupsFromFile(".\\rss\\train_dev_test\\train.txt");
        int size = 9;
        double epochs = 10000;
        int[] layers = new int[l + 2];
        layers[0] = size;
        for (int i = 0; i < l; i++) {
            layers[i + 1] = h;
        }
        layers[layers.length - 1] = size;
        MultiLayerPerceptron net = new MultiLayerPerceptron(layers, lr, new SigmoidalTransferFunction());
        Task<Double> task = new Task<Double>() {
            double error = 0.0;

            @Override
            protected Double call() throws InterruptedException {
                for (int i = 0; i < epochs; i++) {
                    updateProgress(i, epochs);
                    Coup c = null;
                    while (c == null)
                        c = mapTrain.get((int) (Math.round(Math.random() * mapTrain.size())));


                    error += net.backPropagate(c.in, c.out);

                    if (i % 10000 == 0) {
                        updateMessage("Error at step " + i + " is " + (error / (double) i));
                    }
                    updateProgress(i, epochs);
                }
                net.save(file);
                return error;
            }
        };
        progressBar.progressProperty().bind(task.progressProperty());
        textField.textProperty().bind(task.messageProperty());
        new Thread(task).start();
        task.setOnSucceeded(workerStateEvent -> {
            System.out.println("Task succeeded!");
            File newFile = new File(file);
            try {
                boolean success = newFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        task.setOnFailed(workerStateEvent -> {
            System.out.println("Task failed!");
            task.getException().printStackTrace();
        });
    }
    
    
}