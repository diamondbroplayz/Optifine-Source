// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import net.optifine.reflect.Reflector;
import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;

public class KeyUtils
{
    public static void fixKeyConflicts(final enl[] keys, final enl[] keysPrio) {
        final Set<String> keyPrioNames = new HashSet<String>();
        for (int i = 0; i < keysPrio.length; ++i) {
            final enl keyPrio = keysPrio[i];
            keyPrioNames.add(getId(keyPrio));
        }
        final Set<enl> setKeys = new HashSet<enl>(Arrays.asList(keys));
        setKeys.removeAll(Arrays.asList(keysPrio));
        for (final enl key : setKeys) {
            final String name = getId(key);
            if (keyPrioNames.contains(name)) {
                key.b(ehe.bv);
            }
        }
    }
    
    public static String getId(final enl keyMapping) {
        if (Reflector.ForgeKeyBinding_getKeyModifier.exists()) {
            final Object keyModifier = Reflector.call(keyMapping, Reflector.ForgeKeyBinding_getKeyModifier, new Object[0]);
            final Object keyModifierNone = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
            if (keyModifier != keyModifierNone) {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/String;, keyModifier, keyMapping.m());
            }
        }
        return keyMapping.m();
    }
}
