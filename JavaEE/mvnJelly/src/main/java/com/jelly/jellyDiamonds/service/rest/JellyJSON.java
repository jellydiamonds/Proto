package com.jelly.jellyDiamonds.service.rest;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class JellyJSON {
    /* Converts a String into a JsonObject */
    public static JsonObject parseToJson( String JsonData ) {
        JsonObject jsonObject = Json.createReader( new StringReader( JsonData ) ).readObject();
        return jsonObject;
    }

    /*
     * Iterates over all entries of a JsonObject and prints its key-value pairs
     * to console.
     */
    public static void printJsonObject( JsonObject jsonObject ) {
        Iterator<Entry<String, JsonValue>> jsonIterator = jsonObject.entrySet().iterator();
        while ( jsonIterator.hasNext() ) {
            Entry<String, JsonValue> entry = jsonIterator.next();
            System.out.println( entry.getKey() + " = " + entry.getValue() );
        }
    }
}
