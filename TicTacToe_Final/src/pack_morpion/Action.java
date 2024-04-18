package pack_morpion;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/*
 * 
 * Classe à étendre lorsque qu'il y a besoin de mettre un son.
 * 
 */

public class Action {
	
	/*
	 * 
	 * Méthode générique pour activer un son
	 * 
	 */
    public void Media(String nomPiste) {
    	String audioFile = "file:///../rss/son/"+nomPiste;
        Media media = new Media(new File(audioFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.9);
        mediaPlayer.play();
    }

}