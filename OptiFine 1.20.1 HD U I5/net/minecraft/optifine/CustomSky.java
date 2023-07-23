// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.render.Blender;
import net.optifine.util.WorldUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.RenderStage;
import java.util.Properties;
import java.io.InputStream;
import java.util.List;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.optifine.util.TextureUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.PropertiesOrdered;
import java.util.ArrayList;

public class CustomSky
{
    private static CustomSkyLayer[][] worldSkyLayers;
    
    public static void reset() {
        CustomSky.worldSkyLayers = null;
    }
    
    public static void update() {
        reset();
        if (!Config.isCustomSky()) {
            return;
        }
        CustomSky.worldSkyLayers = readCustomSkies();
    }
    
    private static CustomSkyLayer[][] readCustomSkies() {
        final CustomSkyLayer[][] wsls = new CustomSkyLayer[10][0];
        final String prefix = "optifine/sky/world";
        int lastWorldId = -1;
        for (int w = 0; w < wsls.length; ++w) {
            final String worldPrefix = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, prefix, w);
            final List listSkyLayers = new ArrayList();
            for (int i = 0; i < 1000; ++i) {
                final String path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, worldPrefix, i);
                int countMissing = 0;
                try {
                    final acq locPath = new acq(path);
                    final InputStream in = Config.getResourceStream(locPath);
                    if (in == null && ++countMissing > 10) {
                        break;
                    }
                    final Properties props = new PropertiesOrdered();
                    props.load(in);
                    in.close();
                    Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
                    final String defSource = invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i);
                    final CustomSkyLayer sl = new CustomSkyLayer(props, defSource);
                    if (sl.isValid(path)) {
                        final String srcPath = StrUtils.addSuffixCheck(sl.source, ".png");
                        final acq locSource = new acq(srcPath);
                        final fug tex = TextureUtils.getTexture(locSource);
                        if (tex == null) {
                            Config.log(invokedynamic(makeConcatWithConstants:(Lacq;)Ljava/lang/String;, locSource));
                        }
                        else {
                            sl.textureId = tex.a();
                            listSkyLayers.add(sl);
                            in.close();
                        }
                    }
                }
                catch (FileNotFoundException e2) {
                    if (++countMissing > 10) {
                        break;
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (listSkyLayers.size() > 0) {
                final CustomSkyLayer[] sls = listSkyLayers.toArray(new CustomSkyLayer[listSkyLayers.size()]);
                wsls[w] = sls;
                lastWorldId = w;
            }
        }
        if (lastWorldId < 0) {
            return null;
        }
        final int worldCount = lastWorldId + 1;
        final CustomSkyLayer[][] wslsTrim = new CustomSkyLayer[worldCount][0];
        for (int j = 0; j < wslsTrim.length; ++j) {
            wslsTrim[j] = wsls[j];
        }
        return wslsTrim;
    }
    
    public static void renderSky(final cmm world, final eij matrixStackIn, final float partialTicks) {
        if (CustomSky.worldSkyLayers == null) {
            return;
        }
        if (Config.isShaders()) {
            Shaders.setRenderStage(RenderStage.CUSTOM_SKY);
        }
        final int dimId = WorldUtils.getDimensionId(world);
        if (dimId < 0 || dimId >= CustomSky.worldSkyLayers.length) {
            return;
        }
        final CustomSkyLayer[] sls = CustomSky.worldSkyLayers[dimId];
        if (sls == null) {
            return;
        }
        final long time = world.W();
        final int timeOfDay = (int)(time % 24000L);
        final float celestialAngle = world.f(partialTicks);
        final float rainStrength = world.d(partialTicks);
        float thunderStrength = world.b(partialTicks);
        if (rainStrength > 0.0f) {
            thunderStrength /= rainStrength;
        }
        for (int i = 0; i < sls.length; ++i) {
            final CustomSkyLayer sl = sls[i];
            if (sl.isActive(world, timeOfDay)) {
                sl.render(world, matrixStackIn, timeOfDay, celestialAngle, rainStrength, thunderStrength);
            }
        }
        final float rainBrightness = 1.0f - rainStrength;
        Blender.clearBlend(rainBrightness);
    }
    
    public static boolean hasSkyLayers(final cmm world) {
        if (CustomSky.worldSkyLayers == null) {
            return false;
        }
        final int dimId = WorldUtils.getDimensionId(world);
        if (dimId < 0 || dimId >= CustomSky.worldSkyLayers.length) {
            return false;
        }
        final CustomSkyLayer[] sls = CustomSky.worldSkyLayers[dimId];
        return sls != null && sls.length > 0;
    }
    
    static {
        CustomSky.worldSkyLayers = null;
    }
}
