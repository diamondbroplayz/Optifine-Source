// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.awt.Dimension;

public class DynamicDimension
{
    private boolean relative;
    private float width;
    private float height;
    
    public DynamicDimension(final boolean relative, final float width, final float height) {
        this.relative = false;
        this.relative = relative;
        this.width = width;
        this.height = height;
    }
    
    public boolean isRelative() {
        return this.relative;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public Dimension getDimension(final int baseWidth, final int baseHeight) {
        if (this.relative) {
            return new Dimension((int)(this.width * baseWidth), (int)(this.height * baseHeight));
        }
        return new Dimension((int)this.width, (int)this.height);
    }
    
    @Override
    public int hashCode() {
        int hc = this.relative ? 1 : 0;
        hc = hc * 37 + (int)this.width;
        hc = hc * 37 + (int)this.height;
        return hc;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof DynamicDimension)) {
            return false;
        }
        final DynamicDimension dim = (DynamicDimension)obj;
        return this.relative == dim.relative && this.width == dim.width && this.height == dim.height;
    }
}
