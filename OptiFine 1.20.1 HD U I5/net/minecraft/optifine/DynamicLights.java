// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.reflect.Reflector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import net.minecraft.world.item.Item;
import net.optifine.config.ItemLocator;
import net.optifine.config.IObjectLocator;
import net.optifine.config.EntityTypeNameLocator;
import net.optifine.config.ConnectedParser;
import net.optifine.util.PropertiesOrdered;
import java.io.InputStream;
import java.io.IOException;
import net.optifine.reflect.ReflectorForge;
import java.util.List;
import java.util.Map;

public class DynamicLights
{
    private static DynamicLightsMap mapDynamicLights;
    private static Map<String, Integer> mapEntityLightLevels;
    private static Map<cfu, Integer> mapItemLightLevels;
    private static long timeUpdateMs;
    private static final double MAX_DIST = 7.5;
    private static final double MAX_DIST_SQ = 56.25;
    private static final int LIGHT_LEVEL_MAX = 15;
    private static final int LIGHT_LEVEL_FIRE = 15;
    private static final int LIGHT_LEVEL_BLAZE = 10;
    private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
    private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
    private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
    private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
    private static final int LIGHT_LEVEL_GLOW_SQUID = 11;
    private static final int LIGHT_LEVEL_GLOW_INK_SAC = 8;
    private static final int LIGHT_LEVEL_GLOW_LICHEN = 6;
    private static final int LIGHT_LEVEL_GLOW_BERRIES = 12;
    private static final int LIGHT_LEVEL_GLOW_ITEM_FRAME = 8;
    private static final aby<cfz> PARAMETER_ITEM_STACK;
    private static boolean initialized;
    
    public static void entityAdded(final bfj entityIn, final fjv renderGlobal) {
    }
    
    public static void entityRemoved(final bfj entityIn, final fjv renderGlobal) {
        synchronized (DynamicLights.mapDynamicLights) {
            final DynamicLight dynamicLight = DynamicLights.mapDynamicLights.remove(entityIn.af());
            if (dynamicLight != null) {
                dynamicLight.updateLitChunks(renderGlobal);
            }
        }
    }
    
    public static void update(final fjv renderGlobal) {
        final long timeNowMs = System.currentTimeMillis();
        if (timeNowMs < DynamicLights.timeUpdateMs + 50L) {
            return;
        }
        DynamicLights.timeUpdateMs = timeNowMs;
        if (!DynamicLights.initialized) {
            initialize();
        }
        synchronized (DynamicLights.mapDynamicLights) {
            updateMapDynamicLights(renderGlobal);
            if (DynamicLights.mapDynamicLights.size() <= 0) {
                return;
            }
            final List<DynamicLight> dynamicLights = DynamicLights.mapDynamicLights.valueList();
            for (int i = 0; i < dynamicLights.size(); ++i) {
                final DynamicLight dynamicLight = dynamicLights.get(i);
                dynamicLight.update(renderGlobal);
            }
        }
    }
    
    private static void initialize() {
        DynamicLights.initialized = true;
        DynamicLights.mapEntityLightLevels.clear();
        DynamicLights.mapItemLightLevels.clear();
        final String[] modIds = ReflectorForge.getForgeModIds();
        for (int i = 0; i < modIds.length; ++i) {
            final String modId = modIds[i];
            try {
                final acq loc = new acq(modId, "optifine/dynamic_lights.properties");
                final InputStream in = Config.getResourceStream(loc);
                loadModConfiguration(in, loc.toString(), modId);
            }
            catch (IOException ex) {}
        }
        if (DynamicLights.mapEntityLightLevels.size() > 0) {
            Config.dbg(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, DynamicLights.mapEntityLightLevels.size()));
        }
        if (DynamicLights.mapItemLightLevels.size() > 0) {
            Config.dbg(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, DynamicLights.mapItemLightLevels.size()));
        }
    }
    
    private static void loadModConfiguration(final InputStream in, final String path, final String modId) {
        if (in == null) {
            return;
        }
        try {
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            final ConnectedParser cp = new ConnectedParser("DynamicLights");
            loadModLightLevels(props.getProperty("entities"), DynamicLights.mapEntityLightLevels, new EntityTypeNameLocator(), cp, path, modId);
            loadModLightLevels(props.getProperty("items"), DynamicLights.mapItemLightLevels, new ItemLocator(), cp, path, modId);
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
        }
    }
    
    private static <T> void loadModLightLevels(final String prop, final Map<T, Integer> mapLightLevels, final IObjectLocator<T> ol, final ConnectedParser cp, final String path, final String modId) {
        if (prop == null) {
            return;
        }
        final String[] parts = Config.tokenize(prop, " ");
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            final String[] tokens = Config.tokenize(part, ":");
            if (tokens.length != 2) {
                cp.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, part, path));
            }
            else {
                final String name = tokens[0];
                final String light = tokens[1];
                final String nameFull = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, modId, name);
                final acq loc = new acq(nameFull);
                final T obj = (T)ol.getObject(loc);
                if (obj == null) {
                    cp.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, nameFull));
                }
                else {
                    final int lightLevel = cp.parseInt(light, -1);
                    if (lightLevel < 0 || lightLevel > 15) {
                        cp.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, part));
                    }
                    else {
                        mapLightLevels.put(obj, new Integer(lightLevel));
                    }
                }
            }
        }
    }
    
    private static void updateMapDynamicLights(final fjv renderGlobal) {
        final few world = renderGlobal.getWorld();
        if (world == null) {
            return;
        }
        final Iterable<bfj> entities = (Iterable<bfj>)world.e();
        for (final bfj entity : entities) {
            final int lightLevel = getLightLevel(entity);
            if (lightLevel > 0) {
                final int key = entity.af();
                DynamicLight dynamicLight = DynamicLights.mapDynamicLights.get(key);
                if (dynamicLight != null) {
                    continue;
                }
                dynamicLight = new DynamicLight(entity);
                DynamicLights.mapDynamicLights.put(key, dynamicLight);
            }
            else {
                final int key = entity.af();
                final DynamicLight dynamicLight = DynamicLights.mapDynamicLights.remove(key);
                if (dynamicLight == null) {
                    continue;
                }
                dynamicLight.updateLitChunks(renderGlobal);
            }
        }
    }
    
    public static int getCombinedLight(final gu pos, int combinedLight) {
        final double lightPos = getLightLevel(pos);
        combinedLight = getCombinedLight(lightPos, combinedLight);
        return combinedLight;
    }
    
    public static int getCombinedLight(final bfj entity, int combinedLight) {
        double lightPos = getLightLevel(entity.di());
        if (entity == Config.getMinecraft().t) {
            final double lightOwn = getLightLevel(entity);
            lightPos = Math.max(lightPos, lightOwn);
        }
        combinedLight = getCombinedLight(lightPos, combinedLight);
        return combinedLight;
    }
    
    public static int getCombinedLight(final double lightPlayer, int combinedLight) {
        if (lightPlayer > 0.0) {
            final int lightPlayerFF = (int)(lightPlayer * 16.0);
            final int lightBlockFF = combinedLight & 0xFF;
            if (lightPlayerFF > lightBlockFF) {
                combinedLight &= 0xFFFFFF00;
                combinedLight |= lightPlayerFF;
            }
        }
        return combinedLight;
    }
    
    public static double getLightLevel(final gu pos) {
        double lightLevelMax = 0.0;
        synchronized (DynamicLights.mapDynamicLights) {
            final List<DynamicLight> dynamicLights = DynamicLights.mapDynamicLights.valueList();
            for (int dynamicLightsSize = dynamicLights.size(), i = 0; i < dynamicLightsSize; ++i) {
                final DynamicLight dynamicLight = dynamicLights.get(i);
                final int dynamicLightLevel = dynamicLight.getLastLightLevel();
                if (dynamicLightLevel > 0) {
                    final double px = dynamicLight.getLastPosX();
                    final double py = dynamicLight.getLastPosY();
                    final double pz = dynamicLight.getLastPosZ();
                    final double dx = pos.u() - px;
                    final double dy = pos.v() - py;
                    final double dz = pos.w() - pz;
                    final double distSq = dx * dx + dy * dy + dz * dz;
                    if (distSq <= 56.25) {
                        final double dist = Math.sqrt(distSq);
                        final double light = 1.0 - dist / 7.5;
                        final double lightLevel = light * dynamicLightLevel;
                        if (lightLevel > lightLevelMax) {
                            lightLevelMax = lightLevel;
                        }
                    }
                }
            }
        }
        final double lightPlayer = Config.limit(lightLevelMax, 0.0, 15.0);
        return lightPlayer;
    }
    
    public static int getLightLevel(final cfz itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final cfu item = itemStack.d();
        if (item instanceof cds) {
            final cds itemBlock = (cds)item;
            final cpn block = itemBlock.e();
            if (block != null) {
                if (block == cpo.hX) {
                    return 0;
                }
                if (block == cpo.fg) {
                    return 6;
                }
                if (block == cpo.ru) {
                    return 12;
                }
                return block.n().h();
            }
        }
        if (item == cgc.pM) {
            return cpo.H.n().h();
        }
        if (item == cgc.rr || item == cgc.rz) {
            return 10;
        }
        if (item == cgc.qk) {
            return 8;
        }
        if (item == cgc.tF) {
            return 8;
        }
        if (item == cgc.rA) {
            return 8;
        }
        if (item == cgc.ty) {
            return cpo.fO.n().h() / 2;
        }
        if (item == cgc.qs) {
            return 8;
        }
        if (item == cgc.tj) {
            return 8;
        }
        if (!DynamicLights.mapItemLightLevels.isEmpty()) {
            final Integer level = DynamicLights.mapItemLightLevels.get(item);
            if (level != null) {
                return level;
            }
        }
        return 0;
    }
    
    public static int getLightLevel(final bfj entity) {
        if (entity == Config.getMinecraft().al() && !Config.isDynamicHandLight()) {
            return 0;
        }
        if (entity instanceof byo) {
            final byo player = (byo)entity;
            if (player.G_()) {
                return 0;
            }
        }
        if (entity.bL()) {
            return 15;
        }
        if (!DynamicLights.mapEntityLightLevels.isEmpty()) {
            final String typeName = EntityTypeNameLocator.getEntityTypeName(entity);
            final Integer level = DynamicLights.mapEntityLightLevels.get(typeName);
            if (level != null) {
                return level;
            }
        }
        if (entity instanceof byv) {
            return 15;
        }
        if (entity instanceof bvi) {
            return 15;
        }
        if (entity instanceof bvm) {
            final bvm entityBlaze = (bvm)entity;
            if (entityBlaze.bL()) {
                return 15;
            }
            return 10;
        }
        else if (entity instanceof bwb) {
            final bwb emc = (bwb)entity;
            if (emc.e > 0.6) {
                return 13;
            }
            return 8;
        }
        else {
            if (entity instanceof bvo) {
                final bvo entityCreeper = (bvo)entity;
                if (entityCreeper.D(0.0f) > 0.001) {
                    return 15;
                }
            }
            if (entity instanceof bfr) {
                final bfr glowSquid = (bfr)entity;
                final int squidLight = (int)apa.b(0.0f, 11.0f, 1.0f - glowSquid.w() / 10.0f);
                return squidLight;
            }
            if (entity instanceof buy) {
                return 8;
            }
            if (entity instanceof bfz) {
                final bfz player2 = (bfz)entity;
                final cfz stackMain = player2.eO();
                final int levelMain = getLightLevel(stackMain);
                final cfz stackOff = player2.eP();
                final int levelOff = getLightLevel(stackOff);
                final cfz stackHead = player2.c(bfo.f);
                final int levelHead = getLightLevel(stackHead);
                final int levelHandMax = Math.max(levelMain, levelOff);
                return Math.max(levelHandMax, levelHead);
            }
            if (entity instanceof bvh) {
                final bvh entityItem = (bvh)entity;
                final cfz itemStack = getItemStack(entityItem);
                return getLightLevel(itemStack);
            }
            return 0;
        }
    }
    
    public static void removeLights(final fjv renderGlobal) {
        synchronized (DynamicLights.mapDynamicLights) {
            final List<DynamicLight> dynamicLights = DynamicLights.mapDynamicLights.valueList();
            for (int i = 0; i < dynamicLights.size(); ++i) {
                final DynamicLight dynamicLight = dynamicLights.get(i);
                dynamicLight.updateLitChunks(renderGlobal);
            }
            DynamicLights.mapDynamicLights.clear();
        }
    }
    
    public static void clear() {
        synchronized (DynamicLights.mapDynamicLights) {
            DynamicLights.mapDynamicLights.clear();
        }
    }
    
    public static int getCount() {
        synchronized (DynamicLights.mapDynamicLights) {
            return DynamicLights.mapDynamicLights.size();
        }
    }
    
    public static cfz getItemStack(final bvh entityItem) {
        final cfz itemstack = (cfz)entityItem.aj().b(DynamicLights.PARAMETER_ITEM_STACK);
        return itemstack;
    }
    
    static {
        DynamicLights.mapDynamicLights = new DynamicLightsMap();
        DynamicLights.mapEntityLightLevels = new HashMap<String, Integer>();
        DynamicLights.mapItemLightLevels = new HashMap<Item, Integer>();
        DynamicLights.timeUpdateMs = 0L;
        DynamicLights.PARAMETER_ITEM_STACK = (aby)Reflector.EntityItem_ITEM.getValue();
    }
}
