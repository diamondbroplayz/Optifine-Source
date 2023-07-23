// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Iterator;
import java.util.Set;

public class CollectionUtils
{
    public static boolean noneMatch(final Set s1, final Set s2) {
        return !anyMatch(s1, s2);
    }
    
    public static boolean anyMatch(Set s1, Set s2) {
        if (s1.isEmpty() || s2.isEmpty()) {
            return false;
        }
        if (s2.size() < s1.size()) {
            final Set temp = s1;
            s1 = (Set<Object>)s2;
            s2 = temp;
        }
        for (final Object elem : s1) {
            if (s2.contains(elem)) {
                return true;
            }
        }
        return false;
    }
}
