// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class CompareUtils
{
    public static int hash(final int v1, final float v2) {
        return 31 * hash(v1) + hash(v2);
    }
    
    public static int hash(final float v1, final float v2) {
        return 31 * hash(v1) + hash(v2);
    }
    
    public static int hash(final boolean v1, final boolean v2) {
        return 31 * hash(v1) + hash(v2);
    }
    
    public static int hash(final int v1, final Object v2) {
        return 31 * hash(v1) + hash(v2);
    }
    
    public static int hash(final int v1, final Object v2, final Object v3, final Object v4) {
        int hash = hash(v1);
        hash = 31 * hash + hash(v2);
        hash = 31 * hash + hash(v3);
        hash = 31 * hash + hash(v4);
        return hash;
    }
    
    public static int hash(final Object v1, final boolean v2) {
        return 31 * hash(v1) + hash(v2);
    }
    
    public static int hash(final Object o1, final Object o2) {
        return 31 * hash(o1) + hash(o2);
    }
    
    public static int hash(final int i) {
        return i;
    }
    
    public static int hash(final float f) {
        return Float.hashCode(f);
    }
    
    public static int hash(final boolean b) {
        return Boolean.hashCode(b);
    }
    
    public static int hash(final Object o) {
        return (o == null) ? 0 : o.hashCode();
    }
}
