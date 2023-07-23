// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import net.optifine.Config;

public class CompoundIntKey
{
    private int[] keys;
    private int hashcode;
    
    public CompoundIntKey(final int[] keys) {
        this.hashcode = 0;
        this.keys = keys.clone();
    }
    
    public CompoundIntKey(final int k1, final int k2) {
        this(new int[] { k1, k2 });
    }
    
    public CompoundIntKey(final int k1, final int k2, final int k3) {
        this(new int[] { k1, k2, k3 });
    }
    
    public CompoundIntKey(final int k1, final int k2, final int k3, final int k4) {
        this(new int[] { k1, k2, k3, k4 });
    }
    
    @Override
    public int hashCode() {
        if (this.hashcode == 0) {
            this.hashcode = 7;
            for (int i = 0; i < this.keys.length; ++i) {
                final int key = this.keys[i];
                this.hashcode = 31 * this.hashcode + key;
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
        if (!(obj instanceof CompoundIntKey)) {
            return false;
        }
        final CompoundIntKey ck = (CompoundIntKey)obj;
        final int[] ckKeys = ck.getKeys();
        if (ckKeys.length != this.keys.length) {
            return false;
        }
        for (int i = 0; i < this.keys.length; ++i) {
            if (this.keys[i] != ckKeys[i]) {
                return false;
            }
        }
        return true;
    }
    
    private int[] getKeys() {
        return this.keys;
    }
    
    public int[] getKeysCopy() {
        return this.keys.clone();
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.arrayToString(this.keys));
    }
}
