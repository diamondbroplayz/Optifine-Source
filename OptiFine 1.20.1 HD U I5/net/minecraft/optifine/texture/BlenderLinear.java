// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.texture;

public class BlenderLinear implements IBlender
{
    @Override
    public int blend(final int v1, final int v2, final int v3, final int v4) {
        return (v1 + v2 + v3 + v4) / 4;
    }
}
