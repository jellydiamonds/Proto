package com.jellydiamonds.android.metier;

import java.util.Locale;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(String l_tmp : Locale.getISOCountries())
		{
			System.out.println(l_tmp);
		}
	}

}
