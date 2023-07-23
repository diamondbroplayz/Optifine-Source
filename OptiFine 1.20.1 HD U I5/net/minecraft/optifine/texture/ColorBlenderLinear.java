// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.texture;

public class ColorBlenderLinear extends ColorBlenderSeparate
{
    public ColorBlenderLinear() {
        super(new BlenderLinear(), new BlenderLinear(), new BlenderLinear(), new BlenderLinear());
    }
    
    @Override
    public int blend(final int c1, final int c2, final int c3, final int c4) {
        if (c1 == c2 && c2 == c3 && c3 == c4) {
            return c1;
        }
        return super.blend(c1, c2, c3, c4);
    }
}
