// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.texture;

public class ColorBlenderAlpha implements IColorBlender
{
    private int alphaMul;
    private int alphaDiv;
    
    public ColorBlenderAlpha() {
        this(1, 2);
    }
    
    public ColorBlenderAlpha(final int alphaMul, final int alphaDiv) {
        this.alphaMul = alphaMul;
        this.alphaDiv = alphaDiv;
    }
    
    @Override
    public int blend(final int col1, final int col2, final int col3, final int col4) {
        final int cx1 = this.alphaBlend(col1, col2);
        final int cx2 = this.alphaBlend(col3, col4);
        final int cx3 = this.alphaBlend(cx1, cx2);
        return cx3;
    }
    
    private int alphaBlend(int c1, int c2) {
        int a1 = (c1 & 0xFF000000) >> 24 & 0xFF;
        int a2 = (c2 & 0xFF000000) >> 24 & 0xFF;
        int ax = (a1 + a2) / 2;
        if (a1 == 0 && a2 == 0) {
            a1 = 1;
            a2 = 1;
        }
        else {
            if (a1 == 0) {
                c1 = c2;
                ax = ax * this.alphaMul / this.alphaDiv;
            }
            if (a2 == 0) {
                c2 = c1;
                ax = ax * this.alphaMul / this.alphaDiv;
            }
        }
        final int r1 = (c1 >> 16 & 0xFF) * a1;
        final int g1 = (c1 >> 8 & 0xFF) * a1;
        final int b1 = (c1 & 0xFF) * a1;
        final int r2 = (c2 >> 16 & 0xFF) * a2;
        final int g2 = (c2 >> 8 & 0xFF) * a2;
        final int b2 = (c2 & 0xFF) * a2;
        final int rx = (r1 + r2) / (a1 + a2);
        final int gx = (g1 + g2) / (a1 + a2);
        final int bx = (b1 + b2) / (a1 + a2);
        return ax << 24 | rx << 16 | gx << 8 | bx;
    }
}
