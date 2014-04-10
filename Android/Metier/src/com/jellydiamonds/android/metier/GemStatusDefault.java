package com.jellydiamonds.android.metier;

public class GemStatusDefault extends GemStatus {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -7804000189840866412L;

		/**
		 * Gem-ID is in default state : published on JellyDiamonds
		 */
		public GemStatusDefault()
		{
			this.mCurrentStatus = "default";
		}
}
