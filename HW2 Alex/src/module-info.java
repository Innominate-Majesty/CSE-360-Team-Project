module TP1Final {
	requires javafx.controls;
	requires java.sql;
//	requires org.junit.jupiter.api;
//	requires org.junit.jupiter.params;
	
	opens applicationMain to javafx.graphics, javafx.fxml;
}
