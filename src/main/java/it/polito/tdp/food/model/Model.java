package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao; 
	private Graph<String, DefaultWeightedEdge> graph; 
	
	private List<String> best; 
	private int pesoMax; 
	
	public Model() {
		this.dao= new FoodDao(); 
	}
	
	public void creaGrafo(int calories) {
		this.graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		
		//vertex
		Graphs.addAllVertices(this.graph, this.dao.portionName(calories)); 
		
		//edges
		for (CouplePortions c : this.dao.getCouples()) {
			//se stanno nel grafo
			if (this.graph.containsVertex(c.getPortion1()) && this.graph.containsVertex(c.getPortion2())) {
				Graphs.addEdge(this.graph, c.getPortion1(), c.getPortion2(), c.getPeso()); //anche se gia' c'e' fa da solo il controllo
			}
		}
		
		System.out.println("Grafo creato : "+this.graph.vertexSet().size()+" vertex and "+this.graph.edgeSet().size()+" edges\n");
		
	}
	
	public List<String> getVertex(){
		List<String> vertex= new ArrayList<>(this.graph.vertexSet()); 
		vertex.sort(null); //ordinamento naturale 
		return vertex; 
		
		
	}
	
	public List<PortionNumber> getCorrelate(String portion){
		List<PortionNumber> lista= new ArrayList<>(); 
		
		for (String s : Graphs.neighborListOf(this.graph, portion)) {
			int peso= (int)this.graph.getEdgeWeight(this.graph.getEdge(portion, s));
			lista.add(new PortionNumber(s, peso));
			
		}
		
		return lista; 
		
	}
	
	public List<String> cammino(int passi, String partenza){
		
		this.best= new ArrayList<>(); 
		this.pesoMax=-1; 
		
		List<String> cammino= new ArrayList<>(); 
		int peso=0;  //inizio con un peso nullo 
		cammino.add(partenza); 
		
		ricorsione(cammino, passi, peso); 
		return best; 
		
	}

	private void ricorsione(List<String> cammino, int passi, int peso) {
		
		//condizione di terminazione
		if (cammino.size()-1 ==passi) {
			//fine, ma e' ottima?
			if (peso > pesoMax){
				//allora aggiorno
				best= new ArrayList<>(cammino); 
				this.pesoMax=peso;
				
			}
			
		}
		
		//caso generale 
		String last= cammino.get(cammino.size()-1); 
		for(String s : Graphs.neighborListOf(this.graph, last)) {
			if (!cammino.contains(s)) {
				cammino.add(s); 
				//aggiorno il peso
				peso+= (int)this.graph.getEdgeWeight(graph.getEdge(last, s)); 
				ricorsione(cammino, passi, peso); 
				cammino.remove(cammino.size()-1); 
			}
		}
		
	}

	public int getPesoMax() {
		return pesoMax;
	}

	
	
	
}
