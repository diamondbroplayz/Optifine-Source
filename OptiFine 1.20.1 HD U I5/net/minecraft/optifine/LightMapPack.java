// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.util.WorldUtils;

public class LightMapPack
{
    private LightMap lightMap;
    private LightMap lightMapRain;
    private LightMap lightMapThunder;
    private int[] colorBuffer1;
    private int[] colorBuffer2;
    private int[] lmColorsBuffer;
    
    public LightMapPack(final LightMap lightMap, LightMap lightMapRain, LightMap lightMapThunder) {
        this.colorBuffer1 = new int[0];
        this.colorBuffer2 = new int[0];
        this.lmColorsBuffer = new int[0];
        if (lightMapRain != null || lightMapThunder != null) {
            if (lightMapRain == null) {
                lightMapRain = lightMap;
            }
            if (lightMapThunder == null) {
                lightMapThunder = lightMapRain;
            }
        }
        this.lightMap = lightMap;
        this.lightMapRain = lightMapRain;
        this.lightMapThunder = lightMapThunder;
    }
    
    public boolean updateLightmap(final few world, final float torchFlickerX, final ehk lmColorsImage, final boolean nightvision, final float darkLight, final float partialTicks) {
        final int lmColorsLength = lmColorsImage.a() * lmColorsImage.b();
        if (this.lmColorsBuffer.length != lmColorsLength) {
            this.lmColorsBuffer = new int[lmColorsLength];
        }
        lmColorsImage.getBufferRGBA().get(this.lmColorsBuffer);
        final boolean updated = this.updateLightmap(world, torchFlickerX, this.lmColorsBuffer, nightvision, darkLight, partialTicks);
        if (updated) {
            lmColorsImage.getBufferRGBA().put(this.lmColorsBuffer);
        }
        return updated;
    }
    
    public boolean updateLightmap(final few world, final float torchFlickerX, final int[] lmColors, final boolean nightvision, final float darkLight, final float partialTicks) {
        if (this.lightMapRain == null && this.lightMapThunder == null) {
            return this.lightMap.updateLightmap(world, torchFlickerX, lmColors, nightvision, darkLight);
        }
        if (WorldUtils.isEnd((cmm)world) || WorldUtils.isNether((cmm)world)) {
            return this.lightMap.updateLightmap(world, torchFlickerX, lmColors, nightvision, darkLight);
        }
        final float rainStrength = world.d(partialTicks);
        float thunderStrength = world.b(partialTicks);
        final float delta = 1.0E-4f;
        final boolean isRaining = rainStrength > delta;
        final boolean isThundering = thunderStrength > delta;
        if (!isRaining && !isThundering) {
            return this.lightMap.updateLightmap(world, torchFlickerX, lmColors, nightvision, darkLight);
        }
        if (rainStrength > 0.0f) {
            thunderStrength /= rainStrength;
        }
        final float clearBrightness = 1.0f - rainStrength;
        final float rainBrightness = rainStrength - thunderStrength;
        final float thunderBrightness = thunderStrength;
        if (this.colorBuffer1.length != lmColors.length) {
            this.colorBuffer1 = new int[lmColors.length];
            this.colorBuffer2 = new int[lmColors.length];
        }
        int count = 0;
        final int[][] colors = { lmColors, this.colorBuffer1, this.colorBuffer2 };
        final float[] brs = new float[3];
        if (clearBrightness > delta && this.lightMap.updateLightmap(world, torchFlickerX, colors[count], nightvision, darkLight)) {
            brs[count] = clearBrightness;
            ++count;
        }
        if (rainBrightness > delta && this.lightMapRain != null && this.lightMapRain.updateLightmap(world, torchFlickerX, colors[count], nightvision, darkLight)) {
            brs[count] = rainBrightness;
            ++count;
        }
        if (thunderBrightness > delta && this.lightMapThunder != null && this.lightMapThunder.updateLightmap(world, torchFlickerX, colors[count], nightvision, darkLight)) {
            brs[count] = thunderBrightness;
            ++count;
        }
        if (count == 2) {
            return this.blend(colors[0], brs[0], colors[1], brs[1]);
        }
        return count != 3 || this.blend(colors[0], brs[0], colors[1], brs[1], colors[2], brs[2]);
    }
    
    private boolean blend(final int[] cols0, final float br0, final int[] cols1, final float br1) {
        if (cols1.length != cols0.length) {
            return false;
        }
        for (int i = 0; i < cols0.length; ++i) {
            final int col0 = cols0[i];
            final int red0 = col0 >> 16 & 0xFF;
            final int green0 = col0 >> 8 & 0xFF;
            final int blue0 = col0 & 0xFF;
            final int col2 = cols1[i];
            final int red2 = col2 >> 16 & 0xFF;
            final int green2 = col2 >> 8 & 0xFF;
            final int blue2 = col2 & 0xFF;
            final int red3 = (int)(red0 * br0 + red2 * br1);
            final int green3 = (int)(green0 * br0 + green2 * br1);
            final int blue3 = (int)(blue0 * br0 + blue2 * br1);
            cols0[i] = (0xFF000000 | red3 << 16 | green3 << 8 | blue3);
        }
        return true;
    }
    
    private boolean blend(final int[] cols0, final float br0, final int[] cols1, final float br1, final int[] cols2, final float br2) {
        if (cols1.length != cols0.length || cols2.length != cols0.length) {
            return false;
        }
        for (int i = 0; i < cols0.length; ++i) {
            final int col0 = cols0[i];
            final int red0 = col0 >> 16 & 0xFF;
            final int green0 = col0 >> 8 & 0xFF;
            final int blue0 = col0 & 0xFF;
            final int col2 = cols1[i];
            final int red2 = col2 >> 16 & 0xFF;
            final int green2 = col2 >> 8 & 0xFF;
            final int blue2 = col2 & 0xFF;
            final int col3 = cols2[i];
            final int red3 = col3 >> 16 & 0xFF;
            final int green3 = col3 >> 8 & 0xFF;
            final int blue3 = col3 & 0xFF;
            final int red4 = (int)(red0 * br0 + red2 * br1 + red3 * br2);
            final int green4 = (int)(green0 * br0 + green2 * br1 + green3 * br2);
            final int blue4 = (int)(blue0 * br0 + blue2 * br1 + blue3 * br2);
            cols0[i] = (0xFF000000 | red4 << 16 | green4 << 8 | blue4);
        }
        return true;
    }
}
