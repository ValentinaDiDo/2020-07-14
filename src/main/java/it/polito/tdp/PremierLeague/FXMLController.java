/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Avversario;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private boolean grafoCreato = false;
	Graph<Team, DefaultWeightedEdge> grafo;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	if(this.grafoCreato == false) {
    		txtResult.setText("\ndevi prima creare il grafo");
    	}if(this.cmbSquadra.getValue() == null) {
    		txtResult.appendText("\nper favore seleziona un team");
    	}else {
    		Team scelto = this.cmbSquadra.getValue();
    		
    		List<Avversario> battuti = new ArrayList<>();
    		for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(scelto)) {
    			Team battuto = this.grafo.getEdgeTarget(e);
    			int peso = (int) this.grafo.getEdgeWeight(e);
    			
    			Avversario a = new Avversario(battuto, peso);
    			battuti.add(a);
    		}
    		
    		Collections.sort(battuti);
    		txtResult.setText("SQUADRE BATTUTE DA : "+scelto.getName());
    		for(Avversario a : battuti) {
    			txtResult.appendText("\n"+a.toString());
    		}
    		
    		List<Avversario> battenti = new ArrayList<>();
    		for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(scelto)) {
    			Team battente = this.grafo.getEdgeSource(e);
    			int peso = (int) this.grafo.getEdgeWeight(e);
    			
    			Avversario a = new Avversario(battente, peso);
    			battenti.add(a);
    		}
    		
    		Collections.sort(battenti);
    		txtResult.appendText("\n\nSQUADRE CHE HANNO BATTUTO : "+scelto.getName());
    		for(Avversario a : battenti) {
    			txtResult.appendText("\n"+a.toString());
    		}
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.model.creaGrafo();
    	//this.model.creaGrafoLeo();
    	this.grafo = this.model.getGrafo();
    	
    	txtResult.appendText("GRAFO CREATO\n");
    	txtResult.appendText("VERTICI: "+this.grafo.vertexSet().size());
    	txtResult.appendText("\nARCHI: "+this.grafo.edgeSet().size());
    	List<Team> teams = this.model.getlTeam();
    	
    	this.grafoCreato = true;
    	
    	this.cmbSquadra.getItems().clear();
    	this.cmbSquadra.getItems().addAll(teams);
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	if(this.grafoCreato == false) {
    		txtResult.setText("DEVI PRIMA CREARE IL GRAFO\n");
    	}else {
    		
    		String N = txtN.getText();
    		String X = txtX.getText();
    		if(N.equals("") || X.equals("")) 
    			txtResult.setText("PER FAVORE COMPLETA TUTTI E DUE I CAMPI\n");
    		else {
    			
    			try {
    				int n = Integer.parseInt(N);
    				int x = Integer.parseInt(X);
    				this.model.simula(n,x);
    				
    			}catch(NumberFormatException e) {
    				e.printStackTrace();
    				txtResult.setText("PER FAVORE INSERISCI VALORI NUMERICI\n");
    			}
    		}
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
