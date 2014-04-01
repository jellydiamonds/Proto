package com.jellydiamonds.android.logger;

import android.util.Log;

public class JDLogger {
	
	private static String g_appName = null;
	private static boolean g_messgesEnabled = false;

	public static final String ERROR_FLAG = "ERROR";
	public static final String WARNING_FLAG = "Warning";
	public static final String INFO_FLAG = "Info";
	
	
	public static void setAppName( String appName )
	{
		if(appName == null)
			return;
		
		JDLogger.g_appName = appName;
		g_messgesEnabled = true;
	}
	
	public static void setEnabled( boolean enabled )
	{
		if(JDLogger.g_appName == null)
			return;
		
		JDLogger.g_messgesEnabled = enabled;
	}
	
	public static void ALERT( Object instance, String functionName, String message )
	{
		if( !JDLogger.g_messgesEnabled )
			return;
		
		WRITE_DEBUG_MESSAGE( instance, functionName, ERROR_FLAG, message, Log.ERROR);
	}
	
	public static void ALERT( String message )
	{
		ALERT( null, null, message);
	}
	
	public static void WARN( Object instance, String functionName, String message )
	{
		if( !JDLogger.g_messgesEnabled )
			return;
		
		WRITE_DEBUG_MESSAGE( instance, functionName, WARNING_FLAG, message, Log.WARN);
	}
	
	public static void INFO( String message )
	{
		WARN( null, null, message);
	}
	
	public static void INFO( Object instance, String functionName, String message )
	{
		if( !JDLogger.g_messgesEnabled )
			return;
		
		WRITE_DEBUG_MESSAGE( instance, functionName, INFO_FLAG, message, Log.INFO);
	}
	
	public static void WARN( String message )
	{
		INFO( null, null, message);
	}
	
	
	private static void WRITE_DEBUG_MESSAGE( Object instance, String functionName,String priority, String message, int type )
	{
		String l_class = null;
		String l_function = null;
		String l_title = null;
		String l_message = null;
		
		if( instance == null )
			l_class = "<class>";
		else
			l_class = instance.getClass().getName();
		
		if( functionName == null)
			l_function = "<function>";
		else
			l_function = functionName;
		
		
		l_title =  String.format("[ DEBUG %s at %s:%s ]", JDLogger.g_appName,l_class, l_function );
		l_message = String.format("%s : %s.", priority, message );

		Log.println( type, l_title, l_message);
		
	}
	
}
