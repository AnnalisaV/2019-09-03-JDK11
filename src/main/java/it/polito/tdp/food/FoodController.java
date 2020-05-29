/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Model;
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
    	Integer passi;
    	try {
    		passi = Integer.parseInt(txtPassi.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un valore numerico");
    		return;
    	}
    	
    	if(passi <= 0) {
    		txtResult.appendText("Inserire un valore numerico positivo");
    		return;
    	}
    	
    	String porzione = boxPorzioni.getValue();
    	if(porzione == null) {
    		txtResult.appendText("Selezionare un tipo di porzione");
    		return;
    	}
    	
    	List<String> percorso = this.model.trovaCammino(passi, porzione);
    	if(percorso.size() == 0) {
    		txtResult.appendText("Impossibile trovare un cammino di lunghezza "+passi+" partendo da "+porzione+"\n");
    	} else {
	    	txtResult.appendText(String.format("Cammino di lunghezza %d trovato!\nPeso %d\n", passi, this.model.getPesoMax()));
	    	for(String s : percorso) {
	    		txtResult.appendText(s+"\n");
	    	}
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	String porzione = boxPorzioni.getValue();
    	if(porzione == null) {
    		txtResult.appendText("Selezionare un tipo di porzione");
    		return;
    	}
    	
    	List<Adiacenza> correlate = this.model.getCorrelati(porzione);
    	txtResult.appendText("Sono state trovate "+correlate.size()+" porzioni correalte a "+porzione+"\n");
    	for(Adiacenza a : correlate) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer calories;
    	try {
    		calories = Integer.parseInt(txtCalorie.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un valore numerico");
    		return;
    	}
    	
    	if(calories <= 0) {
    		txtResult.appendText("Inserire un valore numerico positivo");
    		return;
    	}
    	
    	this.model.buildGraph(calories);
    	txtResult.appendText(String.format("Grafo creato!\nVertici: %d\nArchi: %d\n", 
    			this.model.getNumVertex(), this.model.getNumEdge()));
    	
    	List<String> porzioni = new ArrayList<>(this.model.getVertex());
    	porzioni.sort(null);
    	boxPorzioni.getItems().setAll(porzioni);
    	
    	btnCorrelate.setDisable(false);
    	btnCammino.setDisable(false);
    	
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
    	
    	btnCorrelate.setDisable(true);
    	btnCammino.setDisable(true);
    }
}
