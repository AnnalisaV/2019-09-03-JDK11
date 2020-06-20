/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.PortionNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	
    	txtResult.clear(); 
    	
    	if(this.boxPorzioni.getValue()== null) {
    		txtResult.appendText("ERRORE : Selezionare un tipo di Portion! \n" );
    		return;
    	}
    	int passi=-1; 
    	if (txtPassi.getText().length()==0) {
    		txtResult.appendText("ERRORE : Indicare un numero intero di Passi!\n" );
    		return; 
    	}
    	try {
    		passi= Integer.parseInt(txtPassi.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("ERRORE : Indicare un numero intero di Passi!\n" );
    		return; 
    	}
    	txtResult.appendText("Massimo peso del cammino trovato : "+model.getPesoMax()+"\n");
    	for (String s : this.model.cammino(passi, this.boxPorzioni.getValue())) {
    		txtResult.appendText(s+"\n");
    	}
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
       txtResult.clear();
    	
    	if(this.boxPorzioni.getValue()== null) {
    		txtResult.appendText("ERRORE : Selezionare un tipo di Portion! \n" );
    		return;
    	}
    	
    
    	txtResult.appendText("Portion connesse : \n");
    	for (PortionNumber p : this.model.getCorrelate(this.boxPorzioni.getValue())) {
    		txtResult.appendText(p+"\n");
    	}
    		
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	int calories=-1; 
    	if (txtCalorie.getText().length()==0) {
    		txtResult.appendText("ERRORE : Indicare un numero intero di Calories!\n" );
    		return; 
    	}
    	try {
    		calories= Integer.parseInt(txtCalorie.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("ERRORE : Indicare un numero intero di Calories!\n" );
    		return; 
    	}
    	model.creaGrafo(calories);
    	
    	this.btnCorrelate.setDisable(false); 
    	this.btnCammino.setDisable(false);
    	
    	this.boxPorzioni.getItems().remove(this.boxPorzioni.getItems()); 
    	this.boxPorzioni.getItems().addAll(this.model.getVertex()); 
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.btnCorrelate.setDisable(true);
    	this.btnCammino.setDisable(true);
    	
    	
    }
}
