// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentUtils
{
    private static final Map<String, ckg> MAP_ENCHANTMENTS;
    
    public static ckg getEnchantment(final String name) {
        ckg enchantment = EnchantmentUtils.MAP_ENCHANTMENTS.get(name);
        if (enchantment == null) {
            final acq loc = new acq(name);
            if (jb.g.c(loc)) {
                enchantment = (ckg)jb.g.a(loc);
            }
            EnchantmentUtils.MAP_ENCHANTMENTS.put(name, enchantment);
        }
        return enchantment;
    }
    
    public static ckg getEnchantment(final acq loc) {
        if (!jb.g.c(loc)) {
            return null;
        }
        return (ckg)jb.g.a(loc);
    }
    
    static {
        MAP_ENCHANTMENTS = new HashMap<String, ckg>();
    }
}
