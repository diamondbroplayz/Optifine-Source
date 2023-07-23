// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import net.optifine.Config;

public class CompoundKey
{
    private Object[] keys;
    private int hashcode;
    
    public CompoundKey(final Object[] keys) {
        this.hashcode = 0;
        this.keys = keys.clone();
    }
    
    public CompoundKey(final Object k1, final Object k2) {
        this(new Object[] { k1, k2 });
    }
    
    public CompoundKey(final Object k1, final Object k2, final Object k3) {
        this(new Object[] { k1, k2, k3 });
    }
    
    public CompoundKey(final Object k1, final Object k2, final Object k3, final Object k4) {
        this(new Object[] { k1, k2, k3, k4 });
    }
    
    @Override
    public int hashCode() {
        if (this.hashcode == 0) {
            this.hashcode = 7;
            for (int i = 0; i < this.keys.length; ++i) {
                final Object key = this.keys[i];
                if (key != null) {
                    this.hashcode = 31 * this.hashcode + key.hashCode();
                }
            }
        }
        return this.hashcode;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CompoundKey)) {
            return false;
        }
        final CompoundKey ck = (CompoundKey)obj;
        final Object[] ckKeys = ck.getKeys();
        if (ckKeys.length != this.keys.length) {
            return false;
        }
        for (int i = 0; i < this.keys.length; ++i) {
            if (!compareKeys(this.keys[i], ckKeys[i])) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean compareKeys(final Object key1, final Object key2) {
        return key1 == key2 || (key1 != null && key2 != null && key1.equals(key2));
    }
    
    private Object[] getKeys() {
        return this.keys;
    }
    
    public Object[] getKeysCopy() {
        return this.keys.clone();
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.arrayToString(this.keys));
    }
}
