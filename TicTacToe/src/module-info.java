module morpion {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.base;
	
	opens pack_morpion to javafx.graphics, javafx.fxml;
}
