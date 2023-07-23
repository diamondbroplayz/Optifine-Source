// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class PairInt
{
    private int left;
    private int right;
    private final int hashCode;
    
    public PairInt(final int left, final int right) {
        this.left = left;
        this.right = right;
        this.hashCode = left + 37 * right;
    }
    
    public static PairInt of(final int left, final int right) {
        return new PairInt(left, right);
    }
    
    public int getLeft() {
        return this.left;
    }
    
    public int getRight() {
        return this.right;
    }
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PairInt) {
            final PairInt pi = (PairInt)obj;
            return this.left == pi.left && this.right == pi.right;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, this.left, this.right);
    }
}
