package it.polito.tdp.food.model;

public class CouplePortions {

	private String portion1; 
	private String portion2; 
	private int peso; // numero di cibi differenti che vengono serviti con entrambe le tipologie di porzioni
	
	
	/**
	 * @param portion1
	 * @param portion2
	 * @param peso
	 */
	public CouplePortions(String portion1, String portion2, int peso) {
		super();
		this.portion1 = portion1;
		this.portion2 = portion2;
		this.peso = peso;
	}


	public String getPortion1() {
		return portion1;
	}


	public void setPortion1(String portion1) {
		this.portion1 = portion1;
	}


	public String getPortion2() {
		return portion2;
	}


	public void setPortion2(String portion2) {
		this.portion2 = portion2;
	}


	public int getPeso() {
		return peso;
	}


	public void setPeso(int peso) {
		this.peso = peso;
	}


	@Override
	public String toString() {
		return this.portion1+" "+this.portion2+" "+this.peso;
	}
	
	
	
	
}
