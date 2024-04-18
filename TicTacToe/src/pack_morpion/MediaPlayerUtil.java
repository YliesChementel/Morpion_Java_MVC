package pack_morpion;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class MediaPlayerUtil {
	private static MediaPlayer mediaPlayer;

	public static MediaPlayer getMediaPlayer() {
		if (mediaPlayer == null) {
			String audioFile = "file:///../rss/son/Theme.mp4";
			Media media = new Media(new File(audioFile).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.setVolume(mediaPlayer.getVolume() - 0.9);
		}
		return mediaPlayer;
	}
}
