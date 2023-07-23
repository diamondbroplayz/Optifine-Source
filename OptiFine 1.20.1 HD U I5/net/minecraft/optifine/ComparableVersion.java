// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

public class ComparableVersion implements Comparable<ComparableVersion>
{
    private int[] elements;
    
    public ComparableVersion(final String ver) {
        final String[] parts = Config.tokenize(ver, ".");
        this.elements = new int[parts.length];
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            final int elem = Config.parseInt(part, -1);
            this.elements[i] = elem;
        }
    }
    
    @Override
    public int compareTo(final ComparableVersion cv) {
        for (int i = 0; i < this.elements.length && i < cv.elements.length; ++i) {
            if (this.elements[i] != cv.elements[i]) {
                return this.elements[i] - cv.elements[i];
            }
        }
        if (this.elements.length != cv.elements.length) {
            return this.elements.length - cv.elements.length;
        }
        return 0;
    }
    
    public int getMajor() {
        if (this.elements.length < 1) {
            return -1;
        }
        return this.elements[0];
    }
    
    public int getMinor() {
        if (this.elements.length < 2) {
            return -1;
        }
        return this.elements[1];
    }
    
    public int getPatch() {
        if (this.elements.length < 3) {
            return -1;
        }
        return this.elements[2];
    }
    
    @Override
    public String toString() {
        return Config.arrayToString(this.elements, ".");
    }
}
