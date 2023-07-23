// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.util.PotionUtils;
import net.optifine.util.EntityUtils;
import net.optifine.util.WorldUtils;
import net.optifine.util.BiomeUtils;
import net.optifine.render.RenderEnv;
import net.optifine.config.MatchBlock;
import java.util.Iterator;
import net.optifine.config.ConnectedParser;
import net.optifine.util.TextureUtils;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Set;
import java.util.Map;
import org.apache.commons.lang3.tuple.ImmutablePair;
import net.optifine.util.StrUtils;
import java.util.HashMap;
import net.optifine.util.ResUtils;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import net.optifine.util.PropertiesOrdered;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Random;

public class CustomColors
{
    private static String paletteFormatDefault;
    private static CustomColormap waterColors;
    private static CustomColormap foliagePineColors;
    private static CustomColormap foliageBirchColors;
    private static CustomColormap swampFoliageColors;
    private static CustomColormap swampGrassColors;
    private static CustomColormap[] colorsBlockColormaps;
    private static CustomColormap[][] blockColormaps;
    private static CustomColormap skyColors;
    private static CustomColorFader skyColorFader;
    private static CustomColormap fogColors;
    private static CustomColorFader fogColorFader;
    private static CustomColormap underwaterColors;
    private static CustomColorFader underwaterColorFader;
    private static CustomColormap underlavaColors;
    private static CustomColorFader underlavaColorFader;
    private static LightMapPack[] lightMapPacks;
    private static int lightmapMinDimensionId;
    private static CustomColormap redstoneColors;
    private static CustomColormap xpOrbColors;
    private static int xpOrbTime;
    private static CustomColormap durabilityColors;
    private static CustomColormap stemColors;
    private static CustomColormap stemMelonColors;
    private static CustomColormap stemPumpkinColors;
    private static CustomColormap lavaDropColors;
    private static CustomColormap myceliumParticleColors;
    private static boolean useDefaultGrassFoliageColors;
    private static int particleWaterColor;
    private static int particlePortalColor;
    private static int lilyPadColor;
    private static int expBarTextColor;
    private static int bossTextColor;
    private static int signTextColor;
    private static eei fogColorNether;
    private static eei fogColorEnd;
    private static eei skyColorEnd;
    private static int[] spawnEggPrimaryColors;
    private static int[] spawnEggSecondaryColors;
    private static float[][] wolfCollarColors;
    private static float[][] sheepColors;
    private static int[] textColors;
    private static int[] mapColorsOriginal;
    private static float[][] dyeColorsOriginal;
    private static int[] potionColors;
    private static final dcb BLOCK_STATE_DIRT;
    private static final dcb BLOCK_STATE_WATER;
    public static Random random;
    private static final IColorizer COLORIZER_GRASS;
    private static final IColorizer COLORIZER_FOLIAGE;
    private static final IColorizer COLORIZER_FOLIAGE_PINE;
    private static final IColorizer COLORIZER_FOLIAGE_BIRCH;
    private static final IColorizer COLORIZER_WATER;
    
    public static void update() {
        CustomColors.paletteFormatDefault = "vanilla";
        CustomColors.waterColors = null;
        CustomColors.foliageBirchColors = null;
        CustomColors.foliagePineColors = null;
        CustomColors.swampGrassColors = null;
        CustomColors.swampFoliageColors = null;
        CustomColors.skyColors = null;
        CustomColors.fogColors = null;
        CustomColors.underwaterColors = null;
        CustomColors.underlavaColors = null;
        CustomColors.redstoneColors = null;
        CustomColors.xpOrbColors = null;
        CustomColors.xpOrbTime = -1;
        CustomColors.durabilityColors = null;
        CustomColors.stemColors = null;
        CustomColors.lavaDropColors = null;
        CustomColors.myceliumParticleColors = null;
        CustomColors.lightMapPacks = null;
        CustomColors.particleWaterColor = -1;
        CustomColors.particlePortalColor = -1;
        CustomColors.lilyPadColor = -1;
        CustomColors.expBarTextColor = -1;
        CustomColors.bossTextColor = -1;
        CustomColors.signTextColor = -1;
        CustomColors.fogColorNether = null;
        CustomColors.fogColorEnd = null;
        CustomColors.skyColorEnd = null;
        CustomColors.colorsBlockColormaps = null;
        CustomColors.blockColormaps = null;
        CustomColors.useDefaultGrassFoliageColors = true;
        CustomColors.spawnEggPrimaryColors = null;
        CustomColors.spawnEggSecondaryColors = null;
        CustomColors.wolfCollarColors = null;
        CustomColors.sheepColors = null;
        CustomColors.textColors = null;
        setMapColors(CustomColors.mapColorsOriginal);
        setDyeColors(CustomColors.dyeColorsOriginal);
        CustomColors.potionColors = null;
        CustomColors.paletteFormatDefault = getValidProperty("optifine/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
        final String mcpColormap = "optifine/colormap/";
        final String[] waterPaths = { "water.png", "watercolorx.png" };
        CustomColors.waterColors = getCustomColors(mcpColormap, waterPaths, 256, -1);
        updateUseDefaultGrassFoliageColors();
        if (!Config.isCustomColors()) {
            return;
        }
        final String[] pinePaths = { "pine.png", "pinecolor.png" };
        CustomColors.foliagePineColors = getCustomColors(mcpColormap, pinePaths, 256, -1);
        final String[] birchPaths = { "birch.png", "birchcolor.png" };
        CustomColors.foliageBirchColors = getCustomColors(mcpColormap, birchPaths, 256, -1);
        final String[] swampGrassPaths = { "swampgrass.png", "swampgrasscolor.png" };
        CustomColors.swampGrassColors = getCustomColors(mcpColormap, swampGrassPaths, 256, -1);
        final String[] swampFoliagePaths = { "swampfoliage.png", "swampfoliagecolor.png" };
        CustomColors.swampFoliageColors = getCustomColors(mcpColormap, swampFoliagePaths, 256, -1);
        final String[] sky0Paths = { "sky0.png", "skycolor0.png" };
        CustomColors.skyColors = getCustomColors(mcpColormap, sky0Paths, 256, -1);
        final String[] fog0Paths = { "fog0.png", "fogcolor0.png" };
        CustomColors.fogColors = getCustomColors(mcpColormap, fog0Paths, 256, -1);
        final String[] underwaterPaths = { "underwater.png", "underwatercolor.png" };
        CustomColors.underwaterColors = getCustomColors(mcpColormap, underwaterPaths, 256, -1);
        final String[] underlavaPaths = { "underlava.png", "underlavacolor.png" };
        CustomColors.underlavaColors = getCustomColors(mcpColormap, underlavaPaths, 256, -1);
        final String[] redstonePaths = { "redstone.png", "redstonecolor.png" };
        CustomColors.redstoneColors = getCustomColors(mcpColormap, redstonePaths, 16, 1);
        CustomColors.xpOrbColors = getCustomColors(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, mcpColormap), -1, -1);
        CustomColors.durabilityColors = getCustomColors(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, mcpColormap), -1, -1);
        final String[] stemPaths = { "stem.png", "stemcolor.png" };
        CustomColors.stemColors = getCustomColors(mcpColormap, stemPaths, 8, 1);
        CustomColors.stemPumpkinColors = getCustomColors(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, mcpColormap), 8, 1);
        CustomColors.stemMelonColors = getCustomColors(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, mcpColormap), 8, 1);
        CustomColors.lavaDropColors = getCustomColors(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, mcpColormap), -1, 1);
        final String[] myceliumPaths = { "myceliumparticle.png", "myceliumparticlecolor.png" };
        CustomColors.myceliumParticleColors = getCustomColors(mcpColormap, myceliumPaths, -1, -1);
        final Pair<LightMapPack[], Integer> lightMaps = parseLightMapPacks();
        CustomColors.lightMapPacks = (LightMapPack[])lightMaps.getLeft();
        CustomColors.lightmapMinDimensionId = (int)lightMaps.getRight();
        readColorProperties("optifine/color.properties");
        CustomColors.blockColormaps = readBlockColormaps(new String[] { invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, mcpColormap), invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, mcpColormap) }, CustomColors.colorsBlockColormaps, 256, -1);
        updateUseDefaultGrassFoliageColors();
    }
    
    private static String getValidProperty(final String fileName, final String key, final String[] validValues, final String valDef) {
        try {
            final acq loc = new acq(fileName);
            final InputStream in = Config.getResourceStream(loc);
            if (in == null) {
                return valDef;
            }
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            final String val = props.getProperty(key);
            if (val == null) {
                return valDef;
            }
            final List<String> listValidValues = Arrays.asList(validValues);
            if (!listValidValues.contains(val)) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, val));
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.arrayToString(validValues)));
                return valDef;
            }
            dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, val));
            return val;
        }
        catch (FileNotFoundException e2) {
            return valDef;
        }
        catch (IOException e) {
            e.printStackTrace();
            return valDef;
        }
    }
    
    private static Pair<LightMapPack[], Integer> parseLightMapPacks() {
        final String lightmapPrefix = "optifine/lightmap/world";
        final String lightmapSuffix = ".png";
        final String[] pathsLightmap = ResUtils.collectFiles(lightmapPrefix, lightmapSuffix);
        final Map<Integer, String> mapLightmaps = new HashMap<Integer, String>();
        for (int i = 0; i < pathsLightmap.length; ++i) {
            final String path = pathsLightmap[i];
            final String dimIdStr = StrUtils.removePrefixSuffix(path, lightmapPrefix, lightmapSuffix);
            final int dimId = Config.parseInt(dimIdStr, Integer.MIN_VALUE);
            if (dimId == Integer.MIN_VALUE) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, dimIdStr, path));
            }
            else {
                mapLightmaps.put(dimId, path);
            }
        }
        final Set<Integer> setDimIds = mapLightmaps.keySet();
        final Integer[] dimIds = setDimIds.toArray(new Integer[setDimIds.size()]);
        Arrays.sort(dimIds);
        if (dimIds.length <= 0) {
            return (Pair<LightMapPack[], Integer>)new ImmutablePair((Object)null, (Object)0);
        }
        final int minDimId = dimIds[0];
        final int maxDimId = dimIds[dimIds.length - 1];
        final int countDim = maxDimId - minDimId + 1;
        final CustomColormap[] colormaps = new CustomColormap[countDim];
        for (int j = 0; j < dimIds.length; ++j) {
            final Integer dimId2 = dimIds[j];
            final String path2 = mapLightmaps.get(dimId2);
            final CustomColormap colors = getCustomColors(path2, -1, -1);
            if (colors != null) {
                if (colors.getWidth() < 16) {
                    warn(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, colors.getWidth(), path2));
                }
                else {
                    final int lightmapIndex = dimId2 - minDimId;
                    colormaps[lightmapIndex] = colors;
                }
            }
        }
        final LightMapPack[] lmps = new LightMapPack[colormaps.length];
        for (int k = 0; k < colormaps.length; ++k) {
            final CustomColormap cm = colormaps[k];
            if (cm != null) {
                final String name = cm.name;
                final String basePath = cm.basePath;
                final CustomColormap cmRain = getCustomColors(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, name), -1, -1);
                final CustomColormap cmThunder = getCustomColors(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, name), -1, -1);
                final LightMap lm = new LightMap(cm);
                final LightMap lmRain = (cmRain != null) ? new LightMap(cmRain) : null;
                final LightMap lmThunder = (cmThunder != null) ? new LightMap(cmThunder) : null;
                final LightMapPack lmp = new LightMapPack(lm, lmRain, lmThunder);
                lmps[k] = lmp;
            }
        }
        return (Pair<LightMapPack[], Integer>)new ImmutablePair((Object)lmps, (Object)minDimId);
    }
    
    private static int getTextureHeight(final String path, final int defHeight) {
        try {
            final InputStream in = Config.getResourceStream(new acq(path));
            if (in == null) {
                return defHeight;
            }
            final BufferedImage bi = ImageIO.read(in);
            in.close();
            if (bi == null) {
                return defHeight;
            }
            return bi.getHeight();
        }
        catch (IOException e) {
            return defHeight;
        }
    }
    
    private static void readColorProperties(final String fileName) {
        try {
            final acq loc = new acq(fileName);
            final InputStream in = Config.getResourceStream(loc);
            if (in == null) {
                return;
            }
            dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            CustomColors.particleWaterColor = readColor(props, new String[] { "particle.water", "drop.water" });
            CustomColors.particlePortalColor = readColor(props, "particle.portal");
            CustomColors.lilyPadColor = readColor(props, "lilypad");
            CustomColors.expBarTextColor = readColor(props, "text.xpbar");
            CustomColors.bossTextColor = readColor(props, "text.boss");
            CustomColors.signTextColor = readColor(props, "text.sign");
            CustomColors.fogColorNether = readColorVec3(props, "fog.nether");
            CustomColors.fogColorEnd = readColorVec3(props, "fog.end");
            CustomColors.skyColorEnd = readColorVec3(props, "sky.end");
            CustomColors.colorsBlockColormaps = readCustomColormaps(props, fileName);
            CustomColors.spawnEggPrimaryColors = readSpawnEggColors(props, fileName, "egg.shell.", "Spawn egg shell");
            CustomColors.spawnEggSecondaryColors = readSpawnEggColors(props, fileName, "egg.spots.", "Spawn egg spot");
            CustomColors.wolfCollarColors = readDyeColors(props, fileName, "collar.", "Wolf collar");
            CustomColors.sheepColors = readDyeColors(props, fileName, "sheep.", "Sheep");
            CustomColors.textColors = readTextColors(props, fileName, "text.code.", "Text");
            final int[] mapColors = readMapColors(props, fileName, "map.", "Map");
            if (mapColors != null) {
                if (CustomColors.mapColorsOriginal == null) {
                    CustomColors.mapColorsOriginal = getMapColors();
                }
                setMapColors(mapColors);
            }
            final float[][] dyeColors = readDyeColors(props, fileName, "dye.", "Dye");
            if (dyeColors != null) {
                if (CustomColors.dyeColorsOriginal == null) {
                    CustomColors.dyeColorsOriginal = getDyeColors();
                }
                setDyeColors(dyeColors);
            }
            CustomColors.potionColors = readPotionColors(props, fileName, "potion.", "Potion");
            CustomColors.xpOrbTime = Config.parseInt(props.getProperty("xporb.time"), -1);
        }
        catch (FileNotFoundException e2) {}
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
        }
    }
    
    private static CustomColormap[] readCustomColormaps(final Properties props, final String fileName) {
        final List list = new ArrayList();
        final String palettePrefix = "palette.block.";
        final Map map = new HashMap();
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String value = props.getProperty(key);
            if (!key.startsWith(palettePrefix)) {
                continue;
            }
            map.put(key, value);
        }
        final String[] propNames = (String[])map.keySet().toArray(new String[map.size()]);
        for (int i = 0; i < propNames.length; ++i) {
            final String name = propNames[i];
            final String value2 = props.getProperty(name);
            dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, name, value2));
            String path = name.substring(palettePrefix.length());
            final String basePath = TextureUtils.getBasePath(fileName);
            path = TextureUtils.fixResourcePath(path, basePath);
            final CustomColormap colors = getCustomColors(path, 256, -1);
            if (colors == null) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            }
            else {
                final ConnectedParser cp = new ConnectedParser("CustomColors");
                final MatchBlock[] mbs = cp.parseMatchBlocks(value2);
                if (mbs == null || mbs.length <= 0) {
                    warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, value2));
                }
                else {
                    for (int m = 0; m < mbs.length; ++m) {
                        final MatchBlock mb = mbs[m];
                        colors.addMatchBlock(mb);
                    }
                    list.add(colors);
                }
            }
        }
        if (list.size() <= 0) {
            return null;
        }
        final CustomColormap[] cms = list.toArray(new CustomColormap[list.size()]);
        return cms;
    }
    
    private static CustomColormap[][] readBlockColormaps(final String[] basePaths, final CustomColormap[] basePalettes, final int width, final int height) {
        final String[] paths = ResUtils.collectFiles(basePaths, new String[] { ".properties" });
        Arrays.sort(paths);
        final List blockList = new ArrayList();
        for (int i = 0; i < paths.length; ++i) {
            final String path = paths[i];
            dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            try {
                final acq locFile = new acq("minecraft", path);
                final InputStream in = Config.getResourceStream(locFile);
                if (in == null) {
                    warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
                }
                else {
                    final Properties props = new PropertiesOrdered();
                    props.load(in);
                    in.close();
                    final CustomColormap cm = new CustomColormap(props, path, width, height, CustomColors.paletteFormatDefault);
                    if (cm.isValid(path)) {
                        if (cm.isValidMatchBlocks(path)) {
                            addToBlockList(cm, blockList);
                        }
                    }
                }
            }
            catch (FileNotFoundException e2) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (basePalettes != null) {
            for (int i = 0; i < basePalettes.length; ++i) {
                final CustomColormap cm2 = basePalettes[i];
                addToBlockList(cm2, blockList);
            }
        }
        if (blockList.size() <= 0) {
            return null;
        }
        final CustomColormap[][] cmArr = blockListToArray(blockList);
        return cmArr;
    }
    
    private static void addToBlockList(final CustomColormap cm, final List blockList) {
        final int[] ids = cm.getMatchBlockIds();
        if (ids == null || ids.length <= 0) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.arrayToString(ids)));
            return;
        }
        for (int i = 0; i < ids.length; ++i) {
            final int blockId = ids[i];
            if (blockId < 0) {
                warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, blockId));
            }
            else {
                addToList(cm, blockList, blockId);
            }
        }
    }
    
    private static void addToList(final CustomColormap cm, final List list, final int id) {
        while (id >= list.size()) {
            list.add(null);
        }
        List subList = list.get(id);
        if (subList == null) {
            subList = new ArrayList();
            list.set(id, subList);
        }
        subList.add(cm);
    }
    
    private static CustomColormap[][] blockListToArray(final List list) {
        final CustomColormap[][] colArr = new CustomColormap[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            final List subList = list.get(i);
            if (subList != null) {
                final CustomColormap[] subArr = subList.toArray(new CustomColormap[subList.size()]);
                colArr[i] = subArr;
            }
        }
        return colArr;
    }
    
    private static int readColor(final Properties props, final String[] names) {
        for (int i = 0; i < names.length; ++i) {
            final String name = names[i];
            final int col = readColor(props, name);
            if (col >= 0) {
                return col;
            }
        }
        return -1;
    }
    
    private static int readColor(final Properties props, final String name) {
        String str = props.getProperty(name);
        if (str == null) {
            return -1;
        }
        str = str.trim();
        final int color = parseColor(str);
        if (color < 0) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, name, str));
            return color;
        }
        dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, name, str));
        return color;
    }
    
    private static int parseColor(String str) {
        if (str == null) {
            return -1;
        }
        str = str.trim();
        try {
            final int val = Integer.parseInt(str, 16) & 0xFFFFFF;
            return val;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static eei readColorVec3(final Properties props, final String name) {
        final int col = readColor(props, name);
        if (col < 0) {
            return null;
        }
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        return new eei((double)redF, (double)greenF, (double)blueF);
    }
    
    private static CustomColormap getCustomColors(final String basePath, final String[] paths, final int width, final int height) {
        for (int i = 0; i < paths.length; ++i) {
            String path = paths[i];
            path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, path);
            final CustomColormap cols = getCustomColors(path, width, height);
            if (cols != null) {
                return cols;
            }
        }
        return null;
    }
    
    public static CustomColormap getCustomColors(final String pathImage, final int width, final int height) {
        try {
            final acq loc = new acq(pathImage);
            if (!Config.hasResource(loc)) {
                return null;
            }
            dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, pathImage));
            final Properties props = new PropertiesOrdered();
            String pathProps = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
            final acq locProps = new acq(pathProps);
            if (Config.hasResource(locProps)) {
                final InputStream in = Config.getResourceStream(locProps);
                props.load(in);
                in.close();
                dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, pathProps));
            }
            else {
                props.put("format", CustomColors.paletteFormatDefault);
                props.put("source", pathImage);
                pathProps = pathImage;
            }
            final CustomColormap cm = new CustomColormap(props, pathProps, width, height, CustomColors.paletteFormatDefault);
            if (!cm.isValid(pathProps)) {
                return null;
            }
            return cm;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void updateUseDefaultGrassFoliageColors() {
        CustomColors.useDefaultGrassFoliageColors = (CustomColors.foliageBirchColors == null && CustomColors.foliagePineColors == null && CustomColors.swampGrassColors == null && CustomColors.swampFoliageColors == null);
    }
    
    public static int getColorMultiplier(final fkr quad, final dcb blockState, final clp blockAccess, final gu blockPos, final RenderEnv renderEnv) {
        return getColorMultiplier(quad.c(), blockState, blockAccess, blockPos, renderEnv);
    }
    
    public static int getColorMultiplier(final boolean quadHasTintIndex, final dcb blockState, final clp blockAccess, gu blockPos, final RenderEnv renderEnv) {
        final cpn block = blockState.b();
        dcb blockStateColormap = blockState;
        if (CustomColors.blockColormaps != null) {
            if (!quadHasTintIndex) {
                if (block == cpo.i) {
                    blockStateColormap = CustomColors.BLOCK_STATE_DIRT;
                }
                if (block == cpo.cw) {
                    return -1;
                }
            }
            if (block instanceof crr && blockState.c((dde)crr.a) == dcx.a) {
                blockPos = blockPos.d();
                blockStateColormap = blockAccess.a_(blockPos);
            }
            final CustomColormap cm = getBlockColormap(blockStateColormap);
            if (cm != null) {
                if (Config.isSmoothBiomes() && !cm.isColorConstant()) {
                    return getSmoothColorMultiplier(blockState, blockAccess, blockPos, (IColorizer)cm, renderEnv.getColorizerBlockPosM());
                }
                return cm.getColor(blockAccess, blockPos);
            }
        }
        if (!quadHasTintIndex) {
            return -1;
        }
        if (block == cpo.fm) {
            return getLilypadColorMultiplier(blockAccess, blockPos);
        }
        if (block == cpo.cw) {
            return getRedstoneColor(renderEnv.getBlockState());
        }
        if (block instanceof cxj) {
            return getStemColorMultiplier(blockState, (cls)blockAccess, blockPos, renderEnv);
        }
        if (CustomColors.useDefaultGrassFoliageColors) {
            return -1;
        }
        IColorizer colorizer;
        if (block == cpo.i || block instanceof cxt || block instanceof crr || block == cpo.dS) {
            colorizer = CustomColors.COLORIZER_GRASS;
        }
        else if (block instanceof crr) {
            colorizer = CustomColors.COLORIZER_GRASS;
            if (blockState.c((dde)crr.a) == dcx.a) {
                blockPos = blockPos.d();
            }
        }
        else if (block instanceof ctu) {
            if (block == cpo.aF) {
                colorizer = CustomColors.COLORIZER_FOLIAGE_PINE;
            }
            else if (block == cpo.aG) {
                colorizer = CustomColors.COLORIZER_FOLIAGE_BIRCH;
            }
            else {
                if (block == cpo.aJ) {
                    return -1;
                }
                if (!blockState.getBlockLocation().isDefaultNamespace()) {
                    return -1;
                }
                colorizer = CustomColors.COLORIZER_FOLIAGE;
            }
        }
        else {
            if (block != cpo.ff) {
                return -1;
            }
            colorizer = CustomColors.COLORIZER_FOLIAGE;
        }
        if (Config.isSmoothBiomes() && !colorizer.isColorConstant()) {
            return getSmoothColorMultiplier(blockState, blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM());
        }
        return colorizer.getColor(blockStateColormap, blockAccess, blockPos);
    }
    
    protected static cnk getColorBiome(final clp blockAccess, final gu blockPos) {
        cnk biome = BiomeUtils.getBiome(blockAccess, blockPos);
        biome = fixBiome(biome);
        return biome;
    }
    
    public static cnk fixBiome(final cnk biome) {
        if ((biome == BiomeUtils.SWAMP || biome == BiomeUtils.MANGROVE_SWAMP) && !Config.isSwampColors()) {
            return BiomeUtils.PLAINS;
        }
        return biome;
    }
    
    private static CustomColormap getBlockColormap(final dcb blockState) {
        if (CustomColors.blockColormaps == null) {
            return null;
        }
        if (!(blockState instanceof dcb)) {
            return null;
        }
        final dcb bs = blockState;
        final int blockId = bs.getBlockId();
        if (blockId < 0 || blockId >= CustomColors.blockColormaps.length) {
            return null;
        }
        final CustomColormap[] cms = CustomColors.blockColormaps[blockId];
        if (cms == null) {
            return null;
        }
        for (int i = 0; i < cms.length; ++i) {
            final CustomColormap cm = cms[i];
            if (cm.matchesBlock(bs)) {
                return cm;
            }
        }
        return null;
    }
    
    private static int getSmoothColorMultiplier(final dcb blockState, final clp blockAccess, final gu blockPos, final IColorizer colorizer, final BlockPosM blockPosM) {
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        final int x = blockPos.u();
        final int y = blockPos.v();
        final int z = blockPos.w();
        final BlockPosM posM = blockPosM;
        final int radius = Config.getBiomeBlendRadius();
        final int width = radius * 2 + 1;
        final int count = width * width;
        for (int ix = x - radius; ix <= x + radius; ++ix) {
            for (int iz = z - radius; iz <= z + radius; ++iz) {
                posM.setXyz(ix, y, iz);
                final int col = colorizer.getColor(blockState, blockAccess, (gu)posM);
                sumRed += (col >> 16 & 0xFF);
                sumGreen += (col >> 8 & 0xFF);
                sumBlue += (col & 0xFF);
            }
        }
        final int r = sumRed / count;
        final int g = sumGreen / count;
        final int b = sumBlue / count;
        return r << 16 | g << 8 | b;
    }
    
    public static int getFluidColor(final clp blockAccess, final dcb blockState, final gu blockPos, final RenderEnv renderEnv) {
        final cpn block = blockState.b();
        IColorizer colorizer = getBlockColormap(blockState);
        if (colorizer == null && blockState.b() == cpo.G) {
            colorizer = CustomColors.COLORIZER_WATER;
        }
        if (colorizer == null) {
            return getBlockColors().a(blockState, blockAccess, blockPos, 0);
        }
        if (Config.isSmoothBiomes() && !colorizer.isColorConstant()) {
            return getSmoothColorMultiplier(blockState, blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM());
        }
        return colorizer.getColor(blockState, blockAccess, blockPos);
    }
    
    public static eoo getBlockColors() {
        return enn.N().ax();
    }
    
    public static void updatePortalFX(final fhm fx) {
        if (CustomColors.particlePortalColor < 0) {
            return;
        }
        final int col = CustomColors.particlePortalColor;
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        fx.a(redF, greenF, blueF);
    }
    
    public static void updateLavaFX(final fhm fx) {
        if (CustomColors.lavaDropColors == null) {
            return;
        }
        final int age = fx.getAge();
        final int col = CustomColors.lavaDropColors.getColor(age);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        fx.a(redF, greenF, blueF);
    }
    
    public static void updateMyceliumFX(final fhm fx) {
        if (CustomColors.myceliumParticleColors == null) {
            return;
        }
        final int col = CustomColors.myceliumParticleColors.getColorRandom();
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        fx.a(redF, greenF, blueF);
    }
    
    private static int getRedstoneColor(final dcb blockState) {
        if (CustomColors.redstoneColors == null) {
            return -1;
        }
        final int level = getRedstoneLevel(blockState, 15);
        final int col = CustomColors.redstoneColors.getColor(level);
        return col;
    }
    
    public static void updateReddustFX(final fhm fx, final clp blockAccess, final double x, final double y, final double z) {
        if (CustomColors.redstoneColors == null) {
            return;
        }
        final dcb state = blockAccess.a_(gu.a(x, y, z));
        final int level = getRedstoneLevel(state, 15);
        final int col = CustomColors.redstoneColors.getColor(level);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        fx.a(redF, greenF, blueF);
    }
    
    private static int getRedstoneLevel(final dcb state, final int def) {
        final cpn block = state.b();
        if (!(block instanceof cvo)) {
            return def;
        }
        final Object val = state.c((dde)cvo.e);
        if (!(val instanceof Integer)) {
            return def;
        }
        final Integer valInt = (Integer)val;
        return valInt;
    }
    
    public static float getXpOrbTimer(final float timer) {
        if (CustomColors.xpOrbTime <= 0) {
            return timer;
        }
        final float kt = 628.0f / CustomColors.xpOrbTime;
        return timer * kt;
    }
    
    public static int getXpOrbColor(final float timer) {
        if (CustomColors.xpOrbColors == null) {
            return -1;
        }
        final int index = (int)Math.round((apa.a(timer) + 1.0f) * (CustomColors.xpOrbColors.getLength() - 1) / 2.0);
        final int col = CustomColors.xpOrbColors.getColor(index);
        return col;
    }
    
    public static int getDurabilityColor(final float dur, final int color) {
        if (CustomColors.durabilityColors == null) {
            return color;
        }
        final int index = (int)(dur * CustomColors.durabilityColors.getLength());
        final int col = CustomColors.durabilityColors.getColor(index);
        return col;
    }
    
    public static void updateWaterFX(final fhm fx, final clp blockAccess, final double x, final double y, final double z, final RenderEnv renderEnv) {
        if (CustomColors.waterColors == null && CustomColors.blockColormaps == null && CustomColors.particleWaterColor < 0) {
            return;
        }
        final gu blockPos = gu.a(x, y, z);
        renderEnv.reset(CustomColors.BLOCK_STATE_WATER, blockPos);
        final int col = getFluidColor(blockAccess, CustomColors.BLOCK_STATE_WATER, blockPos, renderEnv);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        float redF = red / 255.0f;
        float greenF = green / 255.0f;
        float blueF = blue / 255.0f;
        if (CustomColors.particleWaterColor >= 0) {
            final int redDrop = CustomColors.particleWaterColor >> 16 & 0xFF;
            final int greenDrop = CustomColors.particleWaterColor >> 8 & 0xFF;
            final int blueDrop = CustomColors.particleWaterColor & 0xFF;
            redF = redDrop / 255.0f;
            greenF = greenDrop / 255.0f;
            blueF = blueDrop / 255.0f;
            redF *= redDrop / 255.0f;
            greenF *= greenDrop / 255.0f;
            blueF *= blueDrop / 255.0f;
        }
        fx.a(redF, greenF, blueF);
    }
    
    private static int getLilypadColorMultiplier(final clp blockAccess, final gu blockPos) {
        if (CustomColors.lilyPadColor < 0) {
            return getBlockColors().a(cpo.fm.n(), blockAccess, blockPos, 0);
        }
        return CustomColors.lilyPadColor;
    }
    
    private static eei getFogColorNether(final eei col) {
        if (CustomColors.fogColorNether == null) {
            return col;
        }
        return CustomColors.fogColorNether;
    }
    
    private static eei getFogColorEnd(final eei col) {
        if (CustomColors.fogColorEnd == null) {
            return col;
        }
        return CustomColors.fogColorEnd;
    }
    
    private static eei getSkyColorEnd(final eei col) {
        if (CustomColors.skyColorEnd == null) {
            return col;
        }
        return CustomColors.skyColorEnd;
    }
    
    public static eei getSkyColor(final eei skyColor3d, final clp blockAccess, final double x, final double y, final double z) {
        if (CustomColors.skyColors == null) {
            return skyColor3d;
        }
        final int col = CustomColors.skyColors.getColorSmooth(blockAccess, x, y, z, 3);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        float redF = red / 255.0f;
        float greenF = green / 255.0f;
        float blueF = blue / 255.0f;
        final float cRed = (float)skyColor3d.c / 0.5f;
        final float cGreen = (float)skyColor3d.d / 0.66275f;
        final float cBlue = (float)skyColor3d.e;
        redF *= cRed;
        greenF *= cGreen;
        blueF *= cBlue;
        final eei newCol = CustomColors.skyColorFader.getColor(redF, greenF, blueF);
        return newCol;
    }
    
    private static eei getFogColor(final eei fogColor3d, final clp blockAccess, final double x, final double y, final double z) {
        if (CustomColors.fogColors == null) {
            return fogColor3d;
        }
        final int col = CustomColors.fogColors.getColorSmooth(blockAccess, x, y, z, 3);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        float redF = red / 255.0f;
        float greenF = green / 255.0f;
        float blueF = blue / 255.0f;
        final float cRed = (float)fogColor3d.c / 0.753f;
        final float cGreen = (float)fogColor3d.d / 0.8471f;
        final float cBlue = (float)fogColor3d.e;
        redF *= cRed;
        greenF *= cGreen;
        blueF *= cBlue;
        final eei newCol = CustomColors.fogColorFader.getColor(redF, greenF, blueF);
        return newCol;
    }
    
    public static eei getUnderwaterColor(final clp blockAccess, final double x, final double y, final double z) {
        return getUnderFluidColor(blockAccess, x, y, z, CustomColors.underwaterColors, CustomColors.underwaterColorFader);
    }
    
    public static eei getUnderlavaColor(final clp blockAccess, final double x, final double y, final double z) {
        return getUnderFluidColor(blockAccess, x, y, z, CustomColors.underlavaColors, CustomColors.underlavaColorFader);
    }
    
    public static eei getUnderFluidColor(final clp blockAccess, final double x, final double y, final double z, final CustomColormap underFluidColors, final CustomColorFader underFluidColorFader) {
        if (underFluidColors == null) {
            return null;
        }
        final int col = underFluidColors.getColorSmooth(blockAccess, x, y, z, 3);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        final eei newCol = underFluidColorFader.getColor(redF, greenF, blueF);
        return newCol;
    }
    
    private static int getStemColorMultiplier(final dcb blockState, final cls blockAccess, final gu blockPos, final RenderEnv renderEnv) {
        CustomColormap colors = CustomColors.stemColors;
        final cpn blockStem = blockState.b();
        if (blockStem == cpo.fd && CustomColors.stemPumpkinColors != null) {
            colors = CustomColors.stemPumpkinColors;
        }
        if (blockStem == cpo.fe && CustomColors.stemMelonColors != null) {
            colors = CustomColors.stemMelonColors;
        }
        if (colors == null) {
            return -1;
        }
        if (!(blockStem instanceof cxj)) {
            return -1;
        }
        final int level = (int)blockState.c((dde)cxj.b);
        return colors.getColor(level);
    }
    
    public static boolean updateLightmap(final few world, final float torchFlickerX, final ehk lmColors, final boolean nightvision, final float darkLight, final float partialTicks) {
        if (world == null) {
            return false;
        }
        if (CustomColors.lightMapPacks == null) {
            return false;
        }
        final int dimensionId = WorldUtils.getDimensionId((cmm)world);
        final int lightMapIndex = dimensionId - CustomColors.lightmapMinDimensionId;
        if (lightMapIndex < 0 || lightMapIndex >= CustomColors.lightMapPacks.length) {
            return false;
        }
        final LightMapPack lightMapPack = CustomColors.lightMapPacks[lightMapIndex];
        return lightMapPack != null && lightMapPack.updateLightmap(world, torchFlickerX, lmColors, nightvision, darkLight, partialTicks);
    }
    
    public static eei getWorldFogColor(final eei fogVec, final cmm world, final bfj renderViewEntity, final float partialTicks) {
        final enn mc = enn.N();
        if (WorldUtils.isNether(world)) {
            return getFogColorNether(fogVec);
        }
        if (WorldUtils.isOverworld(world)) {
            return getFogColor(fogVec, (clp)mc.s, renderViewEntity.dn(), renderViewEntity.dp() + 1.0, renderViewEntity.dt());
        }
        if (WorldUtils.isEnd(world)) {
            return getFogColorEnd(fogVec);
        }
        return fogVec;
    }
    
    public static eei getWorldSkyColor(final eei skyVec, final cmm world, final bfj renderViewEntity, final float partialTicks) {
        final enn mc = enn.N();
        if (WorldUtils.isOverworld(world) && renderViewEntity != null) {
            return getSkyColor(skyVec, (clp)mc.s, renderViewEntity.dn(), renderViewEntity.dp() + 1.0, renderViewEntity.dt());
        }
        if (WorldUtils.isEnd(world)) {
            return getSkyColorEnd(skyVec);
        }
        return skyVec;
    }
    
    private static int[] readSpawnEggColors(final Properties props, final String fileName, final String prefix, final String logName) {
        final List<Integer> list = new ArrayList<Integer>();
        final Set keys = props.keySet();
        int countColors = 0;
        for (final String key : keys) {
            final String value = props.getProperty(key);
            if (!key.startsWith(prefix)) {
                continue;
            }
            final String name = StrUtils.removePrefix(key, prefix);
            int id = EntityUtils.getEntityIdByName(name);
            try {
                if (id < 0) {
                    id = EntityUtils.getEntityIdByLocation(new acq(name).toString());
                }
            }
            catch (z e) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, e.getMessage()));
            }
            if (id < 0) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, key));
            }
            else {
                final int color = parseColor(value);
                if (color < 0) {
                    warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, value));
                }
                else {
                    while (list.size() <= id) {
                        list.add(-1);
                    }
                    list.set(id, color);
                    ++countColors;
                }
            }
        }
        if (countColors <= 0) {
            return null;
        }
        dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, logName, countColors));
        final int[] colors = new int[list.size()];
        for (int i = 0; i < colors.length; ++i) {
            colors[i] = list.get(i);
        }
        return colors;
    }
    
    private static int getSpawnEggColor(final che item, final cfz itemStack, final int layer, final int color) {
        if (CustomColors.spawnEggPrimaryColors == null && CustomColors.spawnEggSecondaryColors == null) {
            return color;
        }
        final bfn entityType = item.a(itemStack.v());
        if (entityType == null) {
            return color;
        }
        final int id = jb.h.a((Object)entityType);
        if (id < 0) {
            return color;
        }
        final int[] eggColors = (layer == 0) ? CustomColors.spawnEggPrimaryColors : CustomColors.spawnEggSecondaryColors;
        if (eggColors == null) {
            return color;
        }
        if (id < 0 || id >= eggColors.length) {
            return color;
        }
        final int eggColor = eggColors[id];
        if (eggColor < 0) {
            return color;
        }
        return eggColor;
    }
    
    public static int getColorFromItemStack(final cfz itemStack, final int layer, final int color) {
        if (itemStack == null) {
            return color;
        }
        final cfu item = itemStack.d();
        if (item == null) {
            return color;
        }
        if (item instanceof che) {
            return getSpawnEggColor((che)item, itemStack, layer, color);
        }
        if (item == cgc.fF && CustomColors.lilyPadColor != -1) {
            return CustomColors.lilyPadColor;
        }
        return color;
    }
    
    private static float[][] readDyeColors(final Properties props, final String fileName, final String prefix, final String logName) {
        final cen[] dyeValues = cen.values();
        final Map<String, cen> mapDyes = new HashMap<String, cen>();
        for (int i = 0; i < dyeValues.length; ++i) {
            final cen dye = dyeValues[i];
            mapDyes.put(dye.c(), dye);
        }
        mapDyes.put("lightBlue", cen.d);
        mapDyes.put("silver", cen.i);
        final float[][] colors = new float[dyeValues.length][];
        int countColors = 0;
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String value = props.getProperty(key);
            if (!key.startsWith(prefix)) {
                continue;
            }
            final String name = StrUtils.removePrefix(key, prefix);
            final cen dye2 = mapDyes.get(name);
            final int color = parseColor(value);
            if (dye2 == null || color < 0) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, value));
            }
            else {
                final float[] rgb = { (color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f };
                colors[dye2.ordinal()] = rgb;
                ++countColors;
            }
        }
        if (countColors <= 0) {
            return null;
        }
        dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, logName, countColors));
        return colors;
    }
    
    private static float[] getDyeColors(final cen dye, final float[][] dyeColors, final float[] colors) {
        if (dyeColors == null) {
            return colors;
        }
        if (dye == null) {
            return colors;
        }
        final float[] customColors = dyeColors[dye.ordinal()];
        if (customColors == null) {
            return colors;
        }
        return customColors;
    }
    
    public static float[] getWolfCollarColors(final cen dye, final float[] colors) {
        return getDyeColors(dye, CustomColors.wolfCollarColors, colors);
    }
    
    public static float[] getSheepColors(final cen dye, final float[] colors) {
        return getDyeColors(dye, CustomColors.sheepColors, colors);
    }
    
    private static int[] readTextColors(final Properties props, final String fileName, final String prefix, final String logName) {
        final int[] colors = new int[32];
        Arrays.fill(colors, -1);
        int countColors = 0;
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String value = props.getProperty(key);
            if (!key.startsWith(prefix)) {
                continue;
            }
            final String name = StrUtils.removePrefix(key, prefix);
            final int code = Config.parseInt(name, -1);
            final int color = parseColor(value);
            if (code < 0 || code >= colors.length || color < 0) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, value));
            }
            else {
                colors[code] = color;
                ++countColors;
            }
        }
        if (countColors <= 0) {
            return null;
        }
        dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, logName, countColors));
        return colors;
    }
    
    public static int getTextColor(final int index, final int color) {
        if (CustomColors.textColors == null) {
            return color;
        }
        if (index < 0 || index >= CustomColors.textColors.length) {
            return color;
        }
        final int customColor = CustomColors.textColors[index];
        if (customColor < 0) {
            return color;
        }
        return customColor;
    }
    
    private static int[] readMapColors(final Properties props, final String fileName, final String prefix, final String logName) {
        final int[] colors = new int[dxi.am.length];
        Arrays.fill(colors, -1);
        int countColors = 0;
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String value = props.getProperty(key);
            if (!key.startsWith(prefix)) {
                continue;
            }
            final String name = StrUtils.removePrefix(key, prefix);
            final int index = getMapColorIndex(name);
            final int color = parseColor(value);
            if (index < 0 || index >= colors.length || color < 0) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, value));
            }
            else {
                colors[index] = color;
                ++countColors;
            }
        }
        if (countColors <= 0) {
            return null;
        }
        dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, logName, countColors));
        return colors;
    }
    
    private static int[] readPotionColors(final Properties props, final String fileName, final String prefix, final String logName) {
        final int[] colors = new int[getMaxPotionId()];
        Arrays.fill(colors, -1);
        int countColors = 0;
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String value = props.getProperty(key);
            if (!key.startsWith(prefix)) {
                continue;
            }
            final String name = key;
            final int index = getPotionId(name);
            final int color = parseColor(value);
            if (index < 0 || index >= colors.length || color < 0) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, value));
            }
            else {
                colors[index] = color;
                ++countColors;
            }
        }
        if (countColors <= 0) {
            return null;
        }
        dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, logName, countColors));
        return colors;
    }
    
    private static int getMaxPotionId() {
        int maxId = 0;
        final Set<acq> keys = (Set<acq>)jb.e.e();
        for (final acq rl : keys) {
            final bey potion = PotionUtils.getPotion(rl);
            final int id = bey.a(potion);
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId;
    }
    
    private static int getPotionId(String name) {
        if (name.equals("potion.water")) {
            return 0;
        }
        name = StrUtils.replacePrefix(name, "potion.", "effect.");
        final String nameMc = StrUtils.replacePrefix(name, "effect.", "effect.minecraft.");
        final Set<acq> keys = (Set<acq>)jb.e.e();
        for (final acq rl : keys) {
            final bey potion = PotionUtils.getPotion(rl);
            if (potion.d().equals(name)) {
                return bey.a(potion);
            }
            if (potion.d().equals(nameMc)) {
                return bey.a(potion);
            }
        }
        return -1;
    }
    
    public static int getPotionColor(final bey potion, final int color) {
        int potionId = 0;
        if (potion != null) {
            potionId = bey.a(potion);
        }
        return getPotionColor(potionId, color);
    }
    
    public static int getPotionColor(final int potionId, final int color) {
        if (CustomColors.potionColors == null) {
            return color;
        }
        if (potionId < 0 || potionId >= CustomColors.potionColors.length) {
            return color;
        }
        final int potionColor = CustomColors.potionColors[potionId];
        if (potionColor < 0) {
            return color;
        }
        return potionColor;
    }
    
    private static int getMapColorIndex(final String name) {
        if (name == null) {
            return -1;
        }
        if (name.equals("air")) {
            return dxi.a.al;
        }
        if (name.equals("grass")) {
            return dxi.b.al;
        }
        if (name.equals("sand")) {
            return dxi.c.al;
        }
        if (name.equals("cloth")) {
            return dxi.d.al;
        }
        if (name.equals("tnt")) {
            return dxi.e.al;
        }
        if (name.equals("ice")) {
            return dxi.f.al;
        }
        if (name.equals("iron")) {
            return dxi.g.al;
        }
        if (name.equals("foliage")) {
            return dxi.h.al;
        }
        if (name.equals("clay")) {
            return dxi.j.al;
        }
        if (name.equals("dirt")) {
            return dxi.k.al;
        }
        if (name.equals("stone")) {
            return dxi.l.al;
        }
        if (name.equals("water")) {
            return dxi.m.al;
        }
        if (name.equals("wood")) {
            return dxi.n.al;
        }
        if (name.equals("quartz")) {
            return dxi.o.al;
        }
        if (name.equals("gold")) {
            return dxi.E.al;
        }
        if (name.equals("diamond")) {
            return dxi.F.al;
        }
        if (name.equals("lapis")) {
            return dxi.G.al;
        }
        if (name.equals("emerald")) {
            return dxi.H.al;
        }
        if (name.equals("netherrack")) {
            return dxi.J.al;
        }
        if (name.equals("snow") || name.equals("white")) {
            return dxi.i.al;
        }
        if (name.equals("adobe") || name.equals("orange")) {
            return dxi.p.al;
        }
        if (name.equals("magenta")) {
            return dxi.q.al;
        }
        if (name.equals("light_blue") || name.equals("lightBlue")) {
            return dxi.r.al;
        }
        if (name.equals("yellow")) {
            return dxi.s.al;
        }
        if (name.equals("lime")) {
            return dxi.t.al;
        }
        if (name.equals("pink")) {
            return dxi.u.al;
        }
        if (name.equals("gray")) {
            return dxi.v.al;
        }
        if (name.equals("silver") || name.equals("light_gray")) {
            return dxi.w.al;
        }
        if (name.equals("cyan")) {
            return dxi.x.al;
        }
        if (name.equals("purple")) {
            return dxi.y.al;
        }
        if (name.equals("blue")) {
            return dxi.z.al;
        }
        if (name.equals("brown")) {
            return dxi.A.al;
        }
        if (name.equals("green")) {
            return dxi.B.al;
        }
        if (name.equals("red")) {
            return dxi.C.al;
        }
        if (name.equals("black")) {
            return dxi.D.al;
        }
        if (name.equals("white_terracotta")) {
            return dxi.K.al;
        }
        if (name.equals("orange_terracotta")) {
            return dxi.L.al;
        }
        if (name.equals("magenta_terracotta")) {
            return dxi.M.al;
        }
        if (name.equals("light_blue_terracotta")) {
            return dxi.N.al;
        }
        if (name.equals("yellow_terracotta")) {
            return dxi.O.al;
        }
        if (name.equals("lime_terracotta")) {
            return dxi.P.al;
        }
        if (name.equals("pink_terracotta")) {
            return dxi.Q.al;
        }
        if (name.equals("gray_terracotta")) {
            return dxi.R.al;
        }
        if (name.equals("light_gray_terracotta")) {
            return dxi.S.al;
        }
        if (name.equals("cyan_terracotta")) {
            return dxi.T.al;
        }
        if (name.equals("purple_terracotta")) {
            return dxi.U.al;
        }
        if (name.equals("blue_terracotta")) {
            return dxi.V.al;
        }
        if (name.equals("brown_terracotta")) {
            return dxi.W.al;
        }
        if (name.equals("green_terracotta")) {
            return dxi.X.al;
        }
        if (name.equals("red_terracotta")) {
            return dxi.Y.al;
        }
        if (name.equals("black_terracotta")) {
            return dxi.Z.al;
        }
        if (name.equals("crimson_nylium")) {
            return dxi.aa.al;
        }
        if (name.equals("crimson_stem")) {
            return dxi.ab.al;
        }
        if (name.equals("crimson_hyphae")) {
            return dxi.ac.al;
        }
        if (name.equals("warped_nylium")) {
            return dxi.ad.al;
        }
        if (name.equals("warped_stem")) {
            return dxi.ae.al;
        }
        if (name.equals("warped_hyphae")) {
            return dxi.af.al;
        }
        if (name.equals("warped_wart_block")) {
            return dxi.ag.al;
        }
        return -1;
    }
    
    private static int[] getMapColors() {
        final dxi[] mapColors = dxi.am;
        final int[] colors = new int[mapColors.length];
        Arrays.fill(colors, -1);
        for (int i = 0; i < mapColors.length && i < colors.length; ++i) {
            final dxi mapColor = mapColors[i];
            if (mapColor != null) {
                colors[i] = mapColor.ak;
            }
        }
        return colors;
    }
    
    private static void setMapColors(final int[] colors) {
        if (colors == null) {
            return;
        }
        final dxi[] mapColors = dxi.am;
        for (int i = 0; i < mapColors.length && i < colors.length; ++i) {
            final dxi mapColor = mapColors[i];
            if (mapColor != null) {
                final int color = colors[i];
                if (color >= 0) {
                    if (mapColor.ak != color) {
                        mapColor.ak = color;
                    }
                }
            }
        }
    }
    
    private static float[][] getDyeColors() {
        final cen[] dyeColors = cen.values();
        final float[][] colors = new float[dyeColors.length][];
        for (int i = 0; i < dyeColors.length && i < colors.length; ++i) {
            final cen dyeColor = dyeColors[i];
            if (dyeColor != null) {
                colors[i] = dyeColor.d();
            }
        }
        return colors;
    }
    
    private static void setDyeColors(final float[][] colors) {
        if (colors == null) {
            return;
        }
        final cen[] dyeColors = cen.values();
        for (int i = 0; i < dyeColors.length && i < colors.length; ++i) {
            final cen dyeColor = dyeColors[i];
            if (dyeColor != null) {
                final float[] color = colors[i];
                if (color != null) {
                    if (!dyeColor.d().equals(color)) {
                        dyeColor.setColorComponentValues(color);
                    }
                }
            }
        }
    }
    
    private static void dbg(final String str) {
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    private static void warn(final String str) {
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    public static int getExpBarTextColor(final int color) {
        if (CustomColors.expBarTextColor < 0) {
            return color;
        }
        return CustomColors.expBarTextColor;
    }
    
    public static int getBossTextColor(final int color) {
        if (CustomColors.bossTextColor < 0) {
            return color;
        }
        return CustomColors.bossTextColor;
    }
    
    public static int getSignTextColor(final int color) {
        if (color != 0) {
            return color;
        }
        if (CustomColors.signTextColor < 0) {
            return color;
        }
        return CustomColors.signTextColor;
    }
    
    static {
        CustomColors.paletteFormatDefault = "vanilla";
        CustomColors.waterColors = null;
        CustomColors.foliagePineColors = null;
        CustomColors.foliageBirchColors = null;
        CustomColors.swampFoliageColors = null;
        CustomColors.swampGrassColors = null;
        CustomColors.colorsBlockColormaps = null;
        CustomColors.blockColormaps = null;
        CustomColors.skyColors = null;
        CustomColors.skyColorFader = new CustomColorFader();
        CustomColors.fogColors = null;
        CustomColors.fogColorFader = new CustomColorFader();
        CustomColors.underwaterColors = null;
        CustomColors.underwaterColorFader = new CustomColorFader();
        CustomColors.underlavaColors = null;
        CustomColors.underlavaColorFader = new CustomColorFader();
        CustomColors.lightMapPacks = null;
        CustomColors.lightmapMinDimensionId = 0;
        CustomColors.redstoneColors = null;
        CustomColors.xpOrbColors = null;
        CustomColors.xpOrbTime = -1;
        CustomColors.durabilityColors = null;
        CustomColors.stemColors = null;
        CustomColors.stemMelonColors = null;
        CustomColors.stemPumpkinColors = null;
        CustomColors.lavaDropColors = null;
        CustomColors.myceliumParticleColors = null;
        CustomColors.useDefaultGrassFoliageColors = true;
        CustomColors.particleWaterColor = -1;
        CustomColors.particlePortalColor = -1;
        CustomColors.lilyPadColor = -1;
        CustomColors.expBarTextColor = -1;
        CustomColors.bossTextColor = -1;
        CustomColors.signTextColor = -1;
        CustomColors.fogColorNether = null;
        CustomColors.fogColorEnd = null;
        CustomColors.skyColorEnd = null;
        CustomColors.spawnEggPrimaryColors = null;
        CustomColors.spawnEggSecondaryColors = null;
        CustomColors.wolfCollarColors = null;
        CustomColors.sheepColors = null;
        CustomColors.textColors = null;
        CustomColors.mapColorsOriginal = null;
        CustomColors.dyeColorsOriginal = null;
        CustomColors.potionColors = null;
        CustomColors.BLOCK_STATE_DIRT = cpo.j.n();
        CustomColors.BLOCK_STATE_WATER = cpo.G.n();
        CustomColors.random = new Random();
        COLORIZER_GRASS = new IColorizer() {
            @Override
            public int getColor(final BlockState blockState, final BlockAndTintGetter blockAccess, final BlockPos blockPos) {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1427 out of bounds for byte[1137]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            @Override
            public boolean isColorConstant() {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1551 out of bounds for byte[1137]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        };
        COLORIZER_FOLIAGE = new IColorizer() {
            @Override
            public int getColor(final BlockState blockState, final BlockAndTintGetter blockAccess, final BlockPos blockPos) {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1334 out of bounds for byte[1085]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            @Override
            public boolean isColorConstant() {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1458 out of bounds for byte[1085]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        };
        COLORIZER_FOLIAGE_PINE = new IColorizer() {
            @Override
            public int getColor(final BlockState blockState, final BlockAndTintGetter blockAccess, final BlockPos blockPos) {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1076 out of bounds for byte[955]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            @Override
            public boolean isColorConstant() {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1191 out of bounds for byte[955]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        };
        COLORIZER_FOLIAGE_BIRCH = new IColorizer() {
            @Override
            public int getColor(final BlockState blockState, final BlockAndTintGetter blockAccess, final BlockPos blockPos) {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1077 out of bounds for byte[956]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            @Override
            public boolean isColorConstant() {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1192 out of bounds for byte[956]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        };
        COLORIZER_WATER = new IColorizer() {
            @Override
            public int getColor(final BlockState blockState, final BlockAndTintGetter blockAccess, final BlockPos blockPos) {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1267 out of bounds for byte[1018]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            @Override
            public boolean isColorConstant() {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1391 out of bounds for byte[1018]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        };
    }
    
    public interface IColorizer
    {
        int getColor(final BlockState p0, final BlockAndTintGetter p1, final BlockPos p2);
        
        boolean isColorConstant();
    }
}
