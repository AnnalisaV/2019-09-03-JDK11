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
		this.dao = new FoodDao();
	}
	
	public void buildGraph(int calories) {
		this.graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<String> type = this.dao.getAllPortionsType(calories);
		Graphs.addAllVertices(this.graph, type);
		
		
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(calories);
		for(Adiacenza a : adiacenze) {
			if(this.graph.getEdge(a.getType1(), a.getType2()) == null)
				Graphs.addEdge(this.graph, a.getType1(), a.getType2(), a.getPeso());
		}
	}
	
	
	public int getNumVertex() {
		return this.graph.vertexSet().size();
	}
	
	public int getNumEdge() {
		return this.graph.edgeSet().size();
	}
	
	public Set<String> getVertex() {
		return this.graph.vertexSet();
	}
	
	public List<Adiacenza> getCorrelati(String source) {
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		for(String s : Graphs.neighborListOf(this.graph, source)) {
			int peso = (int) this.graph.getEdgeWeight(this.graph.getEdge(source, s));
			result.add(new Adiacenza(null, s, peso));
		}
		return result;
	}
	
	public List<String> trovaCammino(int passi, String source) {
		this.best = new ArrayList<String>();
		this.pesoMax = 0;
		
		List<String> parziale = new ArrayList<String>();
		parziale.add(source);
		
		this.ricorsiva(parziale, passi, 0);
		
		return this.best;
	}
	
	
	public int getPesoMax() {
		return this.pesoMax;
	}
	
	
	private void ricorsiva(List<String> parziale, int passi, int peso) {
		String ultimo = parziale.get(parziale.size()-1);
		
		if(parziale.size()-1 == passi ) {
			if(peso > this.pesoMax) {
				this.best = new ArrayList<String>(parziale);
				this.pesoMax = peso;
			}
			return;
		}
		
		for(String vicino : Graphs.neighborListOf(this.graph, ultimo)) {
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				int p = peso + (int) this.graph.getEdgeWeight(this.graph.getEdge(ultimo, vicino));
				this.ricorsiva(parziale, passi, p);
				parziale.remove(vicino);
			}
		}
	}
	
}
