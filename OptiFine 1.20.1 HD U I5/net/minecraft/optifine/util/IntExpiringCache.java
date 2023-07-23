// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public abstract class IntExpiringCache<T>
{
    private final int intervalMs;
    private long timeCheckMs;
    private Int2ObjectOpenHashMap<Wrapper<T>> map;
    
    public IntExpiringCache(final int intervalMs) {
        this.map = (it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap<Wrapper<T>>)new Int2ObjectOpenHashMap();
        this.intervalMs = intervalMs;
    }
    
    public T get(final int key) {
        final long timeNowMs = System.currentTimeMillis();
        if (!this.map.isEmpty() && timeNowMs >= this.timeCheckMs) {
            this.timeCheckMs = timeNowMs + this.intervalMs;
            final long timeMinMs = timeNowMs - this.intervalMs;
            final IntSet keys = this.map.keySet();
            final IntIterator it = keys.iterator();
            while (it.hasNext()) {
                final int k = it.nextInt();
                if (k == key) {
                    continue;
                }
                final Wrapper<T> w = (Wrapper<T>)this.map.get(k);
                if (w.getAccessTimeMs() > timeMinMs) {
                    continue;
                }
                it.remove();
            }
        }
        Wrapper<T> w2 = (Wrapper<T>)this.map.get(key);
        if (w2 == null) {
            final T obj = this.make();
            w2 = new Wrapper<T>(obj);
            this.map.put(key, (Object)w2);
        }
        w2.setAccessTimeMs(timeNowMs);
        return w2.getValue();
    }
    
    protected abstract T make();
    
    public static class Wrapper<T>
    {
        private final T value;
        private long accessTimeMs;
        
        public Wrapper(final T value) {
            this.value = value;
        }
        
        public T getValue() {
            return this.value;
        }
        
        public long getAccessTimeMs() {
            return this.accessTimeMs;
        }
        
        public void setAccessTimeMs(final long accessTimeMs) {
            this.accessTimeMs = accessTimeMs;
        }
    }
}
