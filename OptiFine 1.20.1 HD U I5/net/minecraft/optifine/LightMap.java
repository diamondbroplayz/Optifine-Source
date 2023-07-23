// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

public class LightMap
{
    private CustomColormap lightMapRgb;
    private float[][] sunRgbs;
    private float[][] torchRgbs;
    
    public LightMap(final CustomColormap lightMapRgb) {
        this.lightMapRgb = null;
        this.sunRgbs = new float[16][3];
        this.torchRgbs = new float[16][3];
        this.lightMapRgb = lightMapRgb;
    }
    
    public CustomColormap getColormap() {
        return this.lightMapRgb;
    }
    
    public boolean updateLightmap(final few world, final float torchFlickerX, final int[] lmColors, final boolean nightvision, final float darkLight) {
        if (this.lightMapRgb == null) {
            return false;
        }
        final int height = this.lightMapRgb.getHeight();
        if (nightvision && height < 64) {
            return false;
        }
        final int width = this.lightMapRgb.getWidth();
        if (width < 16) {
            warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, width));
            this.lightMapRgb = null;
            return false;
        }
        int startIndex = 0;
        if (nightvision) {
            startIndex = width * 16 * 2;
        }
        float sun = 1.1666666f * (world.g(1.0f) - 0.2f);
        if (world.j() > 0) {
            sun = 1.0f;
        }
        sun = Config.limitTo1(sun);
        final float sunX = sun * (width - 1);
        final float torchX = Config.limitTo1(torchFlickerX + 0.5f) * (width - 1);
        final float gamma = Config.limitTo1((float)(double)Config.getGameSettings().ak().c());
        final boolean hasGamma = gamma > 1.0E-4f;
        final float[][] colorsRgb = this.lightMapRgb.getColorsRgb();
        this.getLightMapColumn(colorsRgb, sunX, startIndex, width, this.sunRgbs);
        this.getLightMapColumn(colorsRgb, torchX, startIndex + 16 * width, width, this.torchRgbs);
        final float[] rgb = new float[3];
        for (int is = 0; is < 16; ++is) {
            for (int it = 0; it < 16; ++it) {
                for (int ic = 0; ic < 3; ++ic) {
                    float comp = Config.limitTo1(this.sunRgbs[is][ic] + this.torchRgbs[it][ic] - darkLight);
                    if (hasGamma) {
                        float cg = 1.0f - comp;
                        cg = 1.0f - cg * cg * cg * cg;
                        comp = gamma * cg + (1.0f - gamma) * comp;
                    }
                    rgb[ic] = comp;
                }
                final int r = (int)(rgb[0] * 255.0f);
                final int g = (int)(rgb[1] * 255.0f);
                final int b = (int)(rgb[2] * 255.0f);
                lmColors[is * 16 + it] = (0xFF000000 | b << 16 | g << 8 | r);
            }
        }
        return true;
    }
    
    private void getLightMapColumn(final float[][] origMap, final float x, final int offset, final int width, final float[][] colRgb) {
        final int xLow = (int)Math.floor(x);
        final int xHigh = (int)Math.ceil(x);
        if (xLow == xHigh) {
            for (int y = 0; y < 16; ++y) {
                final float[] rgbLow = origMap[offset + y * width + xLow];
                final float[] rgb = colRgb[y];
                for (int i = 0; i < 3; ++i) {
                    rgb[i] = rgbLow[i];
                }
            }
            return;
        }
        final float dLow = 1.0f - (x - xLow);
        final float dHigh = 1.0f - (xHigh - x);
        for (int y2 = 0; y2 < 16; ++y2) {
            final float[] rgbLow2 = origMap[offset + y2 * width + xLow];
            final float[] rgbHigh = origMap[offset + y2 * width + xHigh];
            final float[] rgb2 = colRgb[y2];
            for (int j = 0; j < 3; ++j) {
                rgb2[j] = rgbLow2[j] * dLow + rgbHigh[j] * dHigh;
            }
        }
    }
    
    private static void dbg(final String str) {
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    private static void warn(final String str) {
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
}
