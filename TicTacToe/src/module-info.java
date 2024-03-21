module morpion {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens pack_morpion to javafx.graphics, javafx.fxml;
}
