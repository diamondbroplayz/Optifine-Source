// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.texture;

public class ColorBlenderSeparate implements IColorBlender
{
    private IBlender blenderR;
    private IBlender blenderG;
    private IBlender blenderB;
    private IBlender blenderA;
    
    public ColorBlenderSeparate(final IBlender blenderR, final IBlender blenderG, final IBlender blenderB, final IBlender blenderA) {
        this.blenderR = blenderR;
        this.blenderG = blenderG;
        this.blenderB = blenderB;
        this.blenderA = blenderA;
    }
    
    @Override
    public int blend(final int c1, final int c2, final int c3, final int c4) {
        final int a1 = c1 >> 24 & 0xFF;
        final int r1 = c1 >> 16 & 0xFF;
        final int g1 = c1 >> 8 & 0xFF;
        final int b1 = c1 & 0xFF;
        final int a2 = c2 >> 24 & 0xFF;
        final int r2 = c2 >> 16 & 0xFF;
        final int g2 = c2 >> 8 & 0xFF;
        final int b2 = c2 & 0xFF;
        final int a3 = c3 >> 24 & 0xFF;
        final int r3 = c3 >> 16 & 0xFF;
        final int g3 = c3 >> 8 & 0xFF;
        final int b3 = c3 & 0xFF;
        final int a4 = c4 >> 24 & 0xFF;
        final int r4 = c4 >> 16 & 0xFF;
        final int g4 = c4 >> 8 & 0xFF;
        final int b4 = c4 & 0xFF;
        final int ax = this.blenderA.blend(a1, a2, a3, a4);
        final int rx = this.blenderR.blend(r1, r2, r3, r4);
        final int gx = this.blenderG.blend(g1, g2, g3, g4);
        final int bx = this.blenderB.blend(b1, b2, b3, b4);
        final int cx = ax << 24 | rx << 16 | gx << 8 | bx;
        return cx;
    }
}
