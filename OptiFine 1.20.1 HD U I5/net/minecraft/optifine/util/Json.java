// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Json
{
    public static float getFloat(final JsonObject obj, final String field, final float def) {
        final JsonElement elem = obj.get(field);
        if (elem == null) {
            return def;
        }
        return elem.getAsFloat();
    }
    
    public static boolean getBoolean(final JsonObject obj, final String field, final boolean def) {
        final JsonElement elem = obj.get(field);
        if (elem == null) {
            return def;
        }
        return elem.getAsBoolean();
    }
    
    public static String getString(final JsonObject jsonObj, final String field) {
        return getString(jsonObj, field, null);
    }
    
    public static String getString(final JsonObject jsonObj, final String field, final String def) {
        final JsonElement jsonElement = jsonObj.get(field);
        if (jsonElement == null) {
            return def;
        }
        return jsonElement.getAsString();
    }
    
    public static float[] parseFloatArray(final JsonElement jsonElement, final int len) {
        return parseFloatArray(jsonElement, len, null);
    }
    
    public static float[] parseFloatArray(final JsonElement jsonElement, final int len, final float[] def) {
        if (jsonElement == null) {
            return def;
        }
        final JsonArray arr = jsonElement.getAsJsonArray();
        if (arr.size() != len) {
            throw new JsonParseException(invokedynamic(makeConcatWithConstants:(IILcom/google/gson/JsonArray;)Ljava/lang/String;, arr.size(), len, arr));
        }
        final float[] floatArr = new float[arr.size()];
        for (int i = 0; i < floatArr.length; ++i) {
            floatArr[i] = arr.get(i).getAsFloat();
        }
        return floatArr;
    }
    
    public static int[] parseIntArray(final JsonElement jsonElement, final int len) {
        return parseIntArray(jsonElement, len, null);
    }
    
    public static int[] parseIntArray(final JsonElement jsonElement, final int len, final int[] def) {
        if (jsonElement == null) {
            return def;
        }
        final JsonArray arr = jsonElement.getAsJsonArray();
        if (arr.size() != len) {
            throw new JsonParseException(invokedynamic(makeConcatWithConstants:(IILcom/google/gson/JsonArray;)Ljava/lang/String;, arr.size(), len, arr));
        }
        final int[] intArr = new int[arr.size()];
        for (int i = 0; i < intArr.length; ++i) {
            intArr[i] = arr.get(i).getAsInt();
        }
        return intArr;
    }
}
