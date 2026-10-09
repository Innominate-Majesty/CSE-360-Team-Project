/**
 * Needs a comment for Javadoc but I don't know what's expected here.
 */
module FoundationsF26 {
	requires javafx.controls;
	requires java.sql;
	
	opens applicationMain to javafx.graphics, javafx.fxml;
}
