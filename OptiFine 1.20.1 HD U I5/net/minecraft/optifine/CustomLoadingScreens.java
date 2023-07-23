// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Properties;
import java.util.Set;
import java.util.Map;
import org.apache.commons.lang3.tuple.ImmutablePair;
import java.util.Arrays;
import net.optifine.util.StrUtils;
import java.util.HashMap;
import net.optifine.util.ResUtils;
import org.apache.commons.lang3.tuple.Pair;
import net.optifine.util.WorldUtils;

public class CustomLoadingScreens
{
    private static CustomLoadingScreen[] screens;
    private static int screensMinDimensionId;
    
    public static CustomLoadingScreen getCustomLoadingScreen() {
        if (CustomLoadingScreens.screens == null) {
            return null;
        }
        final acp<cmm> dimensionType = (acp<cmm>)uq.lastDimensionType;
        if (dimensionType == null) {
            return null;
        }
        final int dimension = WorldUtils.getDimensionId((acp)dimensionType);
        final int index = dimension - CustomLoadingScreens.screensMinDimensionId;
        CustomLoadingScreen scr = null;
        if (index >= 0 && index < CustomLoadingScreens.screens.length) {
            scr = CustomLoadingScreens.screens[index];
        }
        return scr;
    }
    
    public static void update() {
        CustomLoadingScreens.screens = null;
        CustomLoadingScreens.screensMinDimensionId = 0;
        final Pair<CustomLoadingScreen[], Integer> pair = parseScreens();
        CustomLoadingScreens.screens = (CustomLoadingScreen[])pair.getLeft();
        CustomLoadingScreens.screensMinDimensionId = (int)pair.getRight();
    }
    
    private static Pair<CustomLoadingScreen[], Integer> parseScreens() {
        final String prefix = "optifine/gui/loading/background";
        final String suffix = ".png";
        final String[] paths = ResUtils.collectFiles(prefix, suffix);
        final Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < paths.length; ++i) {
            final String path = paths[i];
            final String dimIdStr = StrUtils.removePrefixSuffix(path, prefix, suffix);
            final int dimId = Config.parseInt(dimIdStr, Integer.MIN_VALUE);
            if (dimId == Integer.MIN_VALUE) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, dimIdStr, path));
            }
            else {
                map.put(dimId, path);
            }
        }
        final Set<Integer> setDimIds = map.keySet();
        final Integer[] dimIds = setDimIds.toArray(new Integer[setDimIds.size()]);
        Arrays.sort(dimIds);
        if (dimIds.length <= 0) {
            return (Pair<CustomLoadingScreen[], Integer>)new ImmutablePair((Object)null, (Object)0);
        }
        final String pathProps = "optifine/gui/loading/loading.properties";
        final Properties props = ResUtils.readProperties(pathProps, "CustomLoadingScreens");
        final int minDimId = dimIds[0];
        final int maxDimId = dimIds[dimIds.length - 1];
        final int countDim = maxDimId - minDimId + 1;
        final CustomLoadingScreen[] scrs = new CustomLoadingScreen[countDim];
        for (int j = 0; j < dimIds.length; ++j) {
            final Integer dimId2 = dimIds[j];
            final String path2 = map.get(dimId2);
            scrs[dimId2 - minDimId] = CustomLoadingScreen.parseScreen(path2, dimId2, props);
        }
        return (Pair<CustomLoadingScreen[], Integer>)new ImmutablePair((Object)scrs, (Object)minDimId);
    }
    
    public static void warn(final String str) {
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    public static void dbg(final String str) {
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    static {
        CustomLoadingScreens.screens = null;
        CustomLoadingScreens.screensMinDimensionId = 0;
    }
}
