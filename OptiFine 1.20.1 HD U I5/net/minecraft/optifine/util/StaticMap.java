// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Collections;
import java.util.HashMap;
import net.optifine.Config;
import java.util.Map;

public class StaticMap
{
    private static final Map<String, Object> MAP;
    
    public static boolean contains(final String key) {
        return StaticMap.MAP.containsKey(key);
    }
    
    public static boolean contains(final String key, final Object value) {
        if (!StaticMap.MAP.containsKey(key)) {
            return false;
        }
        final Object val = StaticMap.MAP.get(key);
        return Config.equals(val, value);
    }
    
    public static Object get(final String key) {
        return StaticMap.MAP.get(key);
    }
    
    public static void put(final String key, final Object val) {
        StaticMap.MAP.put(key, val);
    }
    
    public static void remove(final String key) {
        StaticMap.MAP.remove(key);
    }
    
    public static int getInt(final String key, final int def) {
        final Object val = StaticMap.MAP.get(key);
        if (!(val instanceof Integer)) {
            return def;
        }
        final Integer valInt = (Integer)val;
        return valInt;
    }
    
    public static int putInt(final String key, final int val) {
        final int valPrev = getInt(key, 0);
        final Integer valObj = val;
        StaticMap.MAP.put(key, valObj);
        return valPrev;
    }
    
    public static long getLong(final String key, final long def) {
        final Object val = StaticMap.MAP.get(key);
        if (!(val instanceof Long)) {
            return def;
        }
        final Long valLong = (Long)val;
        return valLong;
    }
    
    public static void putLong(final String key, final long val) {
        final Long valObj = val;
        StaticMap.MAP.put(key, valObj);
    }
    
    public static long putLong(final String key, final long val, final long def) {
        final long valPrev = getLong(key, def);
        final Long valObj = val;
        StaticMap.MAP.put(key, valObj);
        return valPrev;
    }
    
    public static long addLong(final String key, final long val, final long def) {
        long valMap = getLong(key, def);
        valMap += val;
        putLong(key, valMap);
        return valMap;
    }
    
    static {
        MAP = Collections.synchronizedMap(new HashMap<String, Object>());
    }
}
