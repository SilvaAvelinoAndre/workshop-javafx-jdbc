package gui.util;

import javafx.scene.control.TextField;

public class Restricoes {
	
	
	
	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, ValorAntigo, ValorNovo) -> {
	        if (ValorNovo != null && !ValorNovo.matches("\\d*")) {
	        	txt.setText(ValorAntigo);
	        }
	    });
	}

	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, ValorAntigo, ValorNovo) -> {
	        if (ValorNovo != null && ValorNovo.length() > max) {
	        	txt.setText(ValorAntigo);
	        }
	    });
	}

	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, ValorAntigo, ValorNovo) -> {
		    	if (ValorNovo != null && !ValorNovo.matches("\\d*([\\.]\\d*)?")) {
                    txt.setText(ValorAntigo);
                }
		    });
	}
}
