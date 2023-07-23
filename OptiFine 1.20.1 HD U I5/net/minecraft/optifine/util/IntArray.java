// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class IntArray
{
    private int[] array;
    private int position;
    private int limit;
    
    public IntArray(final int size) {
        this.array = null;
        this.position = 0;
        this.limit = 0;
        this.array = new int[size];
    }
    
    public void put(final int x) {
        this.checkPutIndex(this.position);
        this.array[this.position] = x;
        ++this.position;
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }
    
    public void put(final int pos, final int x) {
        this.checkPutIndex(x);
        this.array[pos] = x;
        if (this.limit < pos) {
            this.limit = pos;
        }
    }
    
    public void position(final int pos) {
        this.position = pos;
    }
    
    public void put(final int[] ints) {
        this.checkPutIndex(this.position + ints.length - 1);
        for (int len = ints.length, i = 0; i < len; ++i) {
            this.array[this.position] = ints[i];
            ++this.position;
        }
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }
    
    private void checkPutIndex(final int index) {
        if (index < this.array.length) {
            return;
        }
        final int sizeNew = apa.c(index + 1);
        final int[] arrayNew = new int[sizeNew];
        System.arraycopy(this.array, 0, arrayNew, 0, this.array.length);
        this.array = arrayNew;
    }
    
    public int get(final int pos) {
        return this.array[pos];
    }
    
    public int[] getArray() {
        return this.array;
    }
    
    public void clear() {
        this.position = 0;
        this.limit = 0;
    }
    
    public int getLimit() {
        return this.limit;
    }
    
    public int getPosition() {
        return this.position;
    }
    
    public int[] toIntArray() {
        final int[] arrayNew = new int[this.limit];
        System.arraycopy(this.array, 0, arrayNew, 0, arrayNew.length);
        return arrayNew;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(III)Ljava/lang/String;, this.position, this.limit, this.array.length);
    }
}
