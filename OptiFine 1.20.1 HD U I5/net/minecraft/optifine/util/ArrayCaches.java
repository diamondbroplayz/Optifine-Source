// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.lang.reflect.Array;

public class ArrayCaches
{
    private int[] sizes;
    private Class elementClass;
    private ArrayCache[] caches;
    
    public ArrayCaches(final int[] sizes, final Class elementClass, final int maxCacheSize) {
        this.sizes = sizes;
        this.elementClass = elementClass;
        this.caches = new ArrayCache[sizes.length];
        for (int i = 0; i < this.caches.length; ++i) {
            this.caches[i] = new ArrayCache(elementClass, maxCacheSize);
        }
    }
    
    public Object allocate(final int size) {
        for (int i = 0; i < this.sizes.length; ++i) {
            if (size == this.sizes[i]) {
                return this.caches[i].allocate(size);
            }
        }
        return Array.newInstance(this.elementClass, size);
    }
    
    public void free(final Object arr) {
        if (arr == null) {
            return;
        }
        final int size = Array.getLength(arr);
        for (int i = 0; i < this.sizes.length; ++i) {
            if (size == this.sizes[i]) {
                this.caches[i].free(arr);
                return;
            }
        }
    }
}
