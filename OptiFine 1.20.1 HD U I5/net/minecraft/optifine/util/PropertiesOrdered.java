// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Enumeration;
import java.util.Collections;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Properties;

public class PropertiesOrdered extends Properties
{
    private Set<Object> keysOrdered;
    
    public PropertiesOrdered() {
        this.keysOrdered = new LinkedHashSet<Object>();
    }
    
    @Override
    public synchronized Object put(Object key, Object value) {
        if (key instanceof String) {
            key = ((String)key).trim();
        }
        if (value instanceof String) {
            value = ((String)value).trim();
        }
        this.keysOrdered.add(key);
        return super.put(key, value);
    }
    
    @Override
    public Set<Object> keySet() {
        final Set<Object> keysParent = super.keySet();
        this.keysOrdered.retainAll(keysParent);
        return Collections.unmodifiableSet((Set<?>)this.keysOrdered);
    }
    
    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(this.keySet());
    }
}
