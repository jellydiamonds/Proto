package com.jellydiamonds.android.metier;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GemSpecies test = GemSpecies.ALEXANDRITE;
		//for(int i = 0 ; i<GemSpecies.values().length+1; i++)
		//	System.out.println("Index : " + i + " valeur : " + GemSpecies.values()[i] + " enum : " + GemSpecies.values()[i].toString());
	
		System.out.println(test.toString());
		test = GemSpecies.fromValue(5);
		System.out.println(test.toString());
	}

}
