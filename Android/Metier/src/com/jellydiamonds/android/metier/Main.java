package com.jellydiamonds.android.metier;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JellyUser test1 = new JellyUser();
		JellyUser test2 = null;
		
		boolean result = JellyUser.saveJellyUserContext("save", "/Users/MoZ/Desktop", test1);
		test2 = JellyUser.recoverJellyUserContext("save", "/Users/MoZ/Desktop");
		
		
		if(result)
			System.out.println("Success writing !");
		else
			System.err.println("Error writing...");
		
		if( test2 == null )
			System.err.println("ERROR");
		else if (test1.equals(test2) )
			System.out.println("IT WORKS !");
		else
			System.err.println("PROBLEM...");
	}
	

}
