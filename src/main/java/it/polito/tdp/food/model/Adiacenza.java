package it.polito.tdp.food.model;

public class Adiacenza {
	
	private String type1;
	private String type2;
	private int peso;
	
	public Adiacenza(String type1, String type2, int peso) {
		super();
		this.type1 = type1;
		this.type2 = type2;
		this.peso = peso;
	}

	public String getType1() {
		return type1;
	}

	public String getType2() {
		return type2;
	}

	public int getPeso() {
		return peso;
	}
	
	public String toString() {
		return type2+ " - "+peso;
	}

}
