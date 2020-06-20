package it.polito.tdp.food.model;

public class PortionNumber {

	private String portion; 
	private int peso; 
	/**
	 * @param portion
	 * @param peso
	 */
	public PortionNumber(String portion, int peso) {
		super();
		this.portion = portion;
		this.peso = peso;
	}
	public String getPortion() {
		return portion;
	}
	public int getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return this.portion+" "+this.peso;
	}; 
	
	
}
