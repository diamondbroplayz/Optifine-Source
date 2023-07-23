// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Iterator;
import net.optifine.Config;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EntityUtils
{
    private static final Map<bfn, Integer> mapIdByType;
    private static final Map<String, Integer> mapIdByLocation;
    private static final Map<String, Integer> mapIdByName;
    
    public static int getEntityIdByClass(final bfj entity) {
        if (entity == null) {
            return -1;
        }
        return getEntityIdByType(entity.ae());
    }
    
    public static int getEntityIdByType(final bfn type) {
        final Integer id = EntityUtils.mapIdByType.get(type);
        if (id == null) {
            return -1;
        }
        return id;
    }
    
    public static int getEntityIdByLocation(final String locStr) {
        final Integer id = EntityUtils.mapIdByLocation.get(locStr);
        if (id == null) {
            return -1;
        }
        return id;
    }
    
    public static int getEntityIdByName(String name) {
        name = name.toLowerCase(Locale.ROOT);
        final Integer id = EntityUtils.mapIdByName.get(name);
        if (id == null) {
            return -1;
        }
        return id;
    }
    
    static {
        mapIdByType = new HashMap<bfn, Integer>();
        mapIdByLocation = new HashMap<String, Integer>();
        mapIdByName = new HashMap<String, Integer>();
        for (final bfn type : jb.h) {
            final int id = jb.h.a((Object)type);
            final acq loc = jb.h.b((Object)type);
            final String locStr = loc.toString();
            final String name = loc.a();
            if (EntityUtils.mapIdByType.containsKey(type)) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Lbfn;Ljava/lang/Object;I)Ljava/lang/String;, type, EntityUtils.mapIdByType.get(type), id));
            }
            if (EntityUtils.mapIdByLocation.containsKey(locStr)) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/Object;I)Ljava/lang/String;, locStr, EntityUtils.mapIdByLocation.get(locStr), id));
            }
            if (EntityUtils.mapIdByName.containsKey(locStr)) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/Object;I)Ljava/lang/String;, name, EntityUtils.mapIdByName.get(name), id));
            }
            EntityUtils.mapIdByType.put(type, id);
            EntityUtils.mapIdByLocation.put(locStr, id);
            EntityUtils.mapIdByName.put(name, id);
        }
    }
}
