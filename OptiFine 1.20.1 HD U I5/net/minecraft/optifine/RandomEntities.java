// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.reflect.ReflectorRaw;
import java.util.HashMap;
import java.util.Collection;
import net.optifine.util.TextureUtils;
import java.util.Set;
import java.util.HashSet;
import net.optifine.util.ResUtils;
import java.util.List;
import net.optifine.util.ArrayUtils;
import java.util.ArrayList;
import net.optifine.util.StrUtils;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map;

public class RandomEntities
{
    private static Map<String, RandomEntityProperties<acq>> mapProperties;
    private static Map<String, RandomEntityProperties<acq>> mapSpriteProperties;
    private static boolean active;
    private static fow entityRenderDispatcher;
    private static RandomEntity randomEntity;
    private static flt tileEntityRendererDispatcher;
    private static RandomTileEntity randomTileEntity;
    private static boolean working;
    public static final String SUFFIX_PNG = ".png";
    public static final String SUFFIX_PROPERTIES = ".properties";
    public static final String SEPARATOR_DIGITS = ".";
    public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
    public static final String PREFIX_TEXTURES_PAINTING = "textures/painting/";
    public static final String PREFIX_TEXTURES = "textures/";
    public static final String PREFIX_OPTIFINE_RANDOM = "optifine/random/";
    public static final String PREFIX_OPTIFINE = "optifine/";
    public static final String PREFIX_OPTIFINE_MOB = "optifine/mob/";
    private static final String[] DEPENDANT_SUFFIXES;
    private static final String PREFIX_DYNAMIC_TEXTURE_HORSE = "horse/";
    private static final String[] HORSE_TEXTURES;
    private static final String[] HORSE_TEXTURES_ABBR;
    
    public static void entityLoaded(final bfj entity, final cmm world) {
        if (world == null) {
            return;
        }
        final acb edm = entity.aj();
        edm.spawnPosition = entity.di();
        edm.spawnBiome = (cnk)world.s(edm.spawnPosition).a();
        if (entity instanceof bsi) {
            final bsi esr = (bsi)entity;
            checkEntityShoulder(esr, false);
        }
    }
    
    public static void entityUnloaded(final bfj entity, final cmm world) {
        if (entity instanceof bsi) {
            final bsi esr = (bsi)entity;
            checkEntityShoulder(esr, true);
        }
    }
    
    public static void checkEntityShoulder(final bsi entity, final boolean attach) {
        bfz owner = entity.I_();
        if (owner == null) {
            owner = (bfz)Config.getMinecraft().t;
        }
        if (!(owner instanceof fiv)) {
            return;
        }
        final fiv player = (fiv)owner;
        final UUID entityUuid = entity.ct();
        if (attach) {
            player.lastAttachedEntity = entity;
            final qr nbtLeft = player.ge();
            if (nbtLeft != null && nbtLeft.e("UUID") && Config.equals(nbtLeft.a("UUID"), entityUuid)) {
                player.entityShoulderLeft = entity;
                player.lastAttachedEntity = null;
            }
            final qr nbtRight = player.gf();
            if (nbtRight != null && nbtRight.e("UUID") && Config.equals(nbtRight.a("UUID"), entityUuid)) {
                player.entityShoulderRight = entity;
                player.lastAttachedEntity = null;
            }
        }
        else {
            final acb edm = entity.aj();
            if (player.entityShoulderLeft != null && Config.equals(player.entityShoulderLeft.ct(), entityUuid)) {
                final acb edmShoulderLeft = player.entityShoulderLeft.aj();
                edm.spawnPosition = edmShoulderLeft.spawnPosition;
                edm.spawnBiome = edmShoulderLeft.spawnBiome;
                player.entityShoulderLeft = null;
            }
            if (player.entityShoulderRight != null && Config.equals(player.entityShoulderRight.ct(), entityUuid)) {
                final acb edmShoulderRight = player.entityShoulderRight.aj();
                edm.spawnPosition = edmShoulderRight.spawnPosition;
                edm.spawnBiome = edmShoulderRight.spawnBiome;
                player.entityShoulderRight = null;
            }
        }
    }
    
    public static void worldChanged(final cmm oldWorld, final cmm newWorld) {
        if (newWorld instanceof few) {
            final few newWorldClient = (few)newWorld;
            final Iterable<bfj> entities = (Iterable<bfj>)newWorldClient.e();
            for (final bfj entity : entities) {
                entityLoaded(entity, newWorld);
            }
        }
        RandomEntities.randomEntity.setEntity((bfj)null);
        RandomEntities.randomTileEntity.setTileEntity((czn)null);
    }
    
    public static acq getTextureLocation(final acq loc) {
        if (!RandomEntities.active) {
            return loc;
        }
        final IRandomEntity re = getRandomEntityRendered();
        if (re == null) {
            return loc;
        }
        if (RandomEntities.working) {
            return loc;
        }
        try {
            RandomEntities.working = true;
            String name = loc.a();
            if (name.startsWith("horse/")) {
                name = getHorseTexturePath(name, "horse/".length());
            }
            if (!name.startsWith("textures/entity/") && !name.startsWith("textures/painting/")) {
                return loc;
            }
            final RandomEntityProperties<acq> props = RandomEntities.mapProperties.get(name);
            if (props == null) {
                return loc;
            }
            return props.getResource(re, loc);
        }
        finally {
            RandomEntities.working = false;
        }
    }
    
    private static String getHorseTexturePath(final String path, final int pos) {
        if (RandomEntities.HORSE_TEXTURES == null || RandomEntities.HORSE_TEXTURES_ABBR == null) {
            return path;
        }
        for (int i = 0; i < RandomEntities.HORSE_TEXTURES_ABBR.length; ++i) {
            final String abbr = RandomEntities.HORSE_TEXTURES_ABBR[i];
            if (path.startsWith(abbr, pos)) {
                return RandomEntities.HORSE_TEXTURES[i];
            }
        }
        return path;
    }
    
    public static IRandomEntity getRandomEntityRendered() {
        if (RandomEntities.entityRenderDispatcher.getRenderedEntity() != null) {
            RandomEntities.randomEntity.setEntity(RandomEntities.entityRenderDispatcher.getRenderedEntity());
            return RandomEntities.randomEntity;
        }
        final flt tileEntityRendererDispatcher = RandomEntities.tileEntityRendererDispatcher;
        if (flt.tileEntityRendered != null) {
            final flt tileEntityRendererDispatcher2 = RandomEntities.tileEntityRendererDispatcher;
            final czn te = flt.tileEntityRendered;
            if (te.k() != null) {
                RandomEntities.randomTileEntity.setTileEntity(te);
                return RandomEntities.randomTileEntity;
            }
        }
        return null;
    }
    
    public static IRandomEntity getRandomEntity(final bfj entityIn) {
        RandomEntities.randomEntity.setEntity(entityIn);
        return RandomEntities.randomEntity;
    }
    
    public static IRandomEntity getRandomBlockEntity(final czn tileEntityIn) {
        RandomEntities.randomTileEntity.setTileEntity(tileEntityIn);
        return RandomEntities.randomTileEntity;
    }
    
    private static RandomEntityProperties<acq> makeProperties(final acq loc, final RandomEntityContext.Textures context) {
        final String path = loc.a();
        final acq locProps = getLocationProperties(loc, context.isLegacy());
        if (locProps != null) {
            final RandomEntityProperties props = RandomEntityProperties.parse(locProps, loc, (RandomEntityContext)context);
            if (props != null) {
                return (RandomEntityProperties<acq>)props;
            }
        }
        final int[] variants = getLocationsVariants(loc, context.isLegacy(), context);
        if (variants == null) {
            return null;
        }
        return new RandomEntityProperties<acq>(path, loc, variants, (RandomEntityContext)context);
    }
    
    private static acq getLocationProperties(final acq loc, final boolean legacy) {
        final acq locMcp = getLocationRandom(loc, legacy);
        if (locMcp == null) {
            return null;
        }
        final String domain = locMcp.b();
        final String path = locMcp.a();
        final String pathBase = StrUtils.removeSuffix(path, ".png");
        final String pathProps = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, pathBase);
        final acq locProps = new acq(domain, pathProps);
        if (Config.hasResource(locProps)) {
            return locProps;
        }
        final String pathParent = getParentTexturePath(pathBase);
        if (pathParent == null) {
            return null;
        }
        final acq locParentProps = new acq(domain, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, pathParent));
        if (Config.hasResource(locParentProps)) {
            return locParentProps;
        }
        return null;
    }
    
    protected static acq getLocationRandom(final acq loc, final boolean legacy) {
        final String domain = loc.b();
        final String path = loc.a();
        if (path.startsWith("optifine/")) {
            return loc;
        }
        String prefixTextures = "textures/";
        String prefixRandom = "optifine/random/";
        if (legacy) {
            prefixTextures = "textures/entity/";
            prefixRandom = "optifine/mob/";
        }
        if (!path.startsWith(prefixTextures)) {
            return null;
        }
        final String pathRandom = StrUtils.replacePrefix(path, prefixTextures, prefixRandom);
        return new acq(domain, pathRandom);
    }
    
    private static String getPathBase(final String pathRandom) {
        if (pathRandom.startsWith("optifine/random/")) {
            return StrUtils.replacePrefix(pathRandom, "optifine/random/", "textures/");
        }
        if (pathRandom.startsWith("optifine/mob/")) {
            return StrUtils.replacePrefix(pathRandom, "optifine/mob/", "textures/entity/");
        }
        return null;
    }
    
    protected static acq getLocationIndexed(final acq loc, final int index) {
        if (loc == null) {
            return null;
        }
        final String path = loc.a();
        final int pos = path.lastIndexOf(46);
        if (pos < 0) {
            return null;
        }
        final String prefix = path.substring(0, pos);
        final String suffix = path.substring(pos);
        final String separator = StrUtils.endsWithDigit(prefix) ? "." : "";
        final String pathNew = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;, prefix, separator, index, suffix);
        final acq locNew = new acq(loc.b(), pathNew);
        return locNew;
    }
    
    private static String getParentTexturePath(final String path) {
        for (int i = 0; i < RandomEntities.DEPENDANT_SUFFIXES.length; ++i) {
            final String suffix = RandomEntities.DEPENDANT_SUFFIXES[i];
            if (path.endsWith(suffix)) {
                final String pathParent = StrUtils.removeSuffix(path, suffix);
                return pathParent;
            }
        }
        return null;
    }
    
    public static int[] getLocationsVariants(final acq loc, final boolean legacy, final RandomEntityContext context) {
        final List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        final acq locRandom = getLocationRandom(loc, legacy);
        if (locRandom == null) {
            return null;
        }
        for (int i = 1; i < list.size() + 10; ++i) {
            final int index = i + 1;
            final acq locIndex = getLocationIndexed(locRandom, index);
            if (Config.hasResource(locIndex)) {
                list.add(index);
            }
        }
        if (list.size() <= 1) {
            return null;
        }
        final Integer[] arr = list.toArray(new Integer[list.size()]);
        final int[] intArr = ArrayUtils.toPrimitive(arr);
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;, context.getName(), loc.a(), intArr.length));
        return intArr;
    }
    
    public static void update() {
        RandomEntities.entityRenderDispatcher = Config.getEntityRenderDispatcher();
        RandomEntities.tileEntityRendererDispatcher = enn.N().ao();
        RandomEntities.mapProperties.clear();
        RandomEntities.mapSpriteProperties.clear();
        RandomEntities.active = false;
        if (!Config.isRandomEntities()) {
            return;
        }
        initialize();
    }
    
    private static void initialize() {
        final String[] prefixes = { "optifine/random/", "optifine/mob/" };
        final String[] suffixes = { ".png", ".properties" };
        final String[] pathsRandom = ResUtils.collectFiles(prefixes, suffixes);
        final Set basePathsChecked = new HashSet();
        for (int i = 0; i < pathsRandom.length; ++i) {
            String path = pathsRandom[i];
            path = StrUtils.removeSuffix(path, suffixes);
            path = StrUtils.trimTrailing(path, "0123456789");
            path = StrUtils.removeSuffix(path, ".");
            path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path);
            final String pathBase = getPathBase(path);
            if (!basePathsChecked.contains(pathBase)) {
                basePathsChecked.add(pathBase);
                final acq locBase = new acq(pathBase);
                if (Config.hasResource(locBase)) {
                    RandomEntityProperties<acq> props = RandomEntities.mapProperties.get(pathBase);
                    if (props == null) {
                        props = makeProperties(locBase, new RandomEntityContext.Textures(false));
                        if (props == null) {
                            props = makeProperties(locBase, new RandomEntityContext.Textures(true));
                        }
                        if (props != null) {
                            RandomEntities.mapProperties.put(pathBase, props);
                        }
                    }
                }
            }
        }
        RandomEntities.active = !RandomEntities.mapProperties.isEmpty();
    }
    
    public static synchronized void registerSprites(final Set<acq> spriteLocations) {
        if (RandomEntities.mapProperties.isEmpty()) {
            return;
        }
        final Set<acq> newLocations = new HashSet<acq>();
        for (final acq loc : spriteLocations) {
            final String pathFull = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, loc.a());
            final RandomEntityProperties<acq> props = RandomEntities.mapProperties.get(pathFull);
            if (props == null) {
                continue;
            }
            RandomEntities.mapSpriteProperties.put(loc.a(), props);
            final List<acq> locs = props.getAllResources();
            if (locs == null) {
                continue;
            }
            for (int i = 0; i < locs.size(); ++i) {
                final acq propLoc = locs.get(i);
                final acq locSprite = TextureUtils.getSpriteLocation(propLoc);
                newLocations.add(locSprite);
                RandomEntities.mapSpriteProperties.put(locSprite.a(), props);
            }
        }
        spriteLocations.addAll(newLocations);
    }
    
    public static fuv getRandomSprite(final fuv spriteIn) {
        if (!RandomEntities.active) {
            return spriteIn;
        }
        final IRandomEntity re = getRandomEntityRendered();
        if (re == null) {
            return spriteIn;
        }
        if (RandomEntities.working) {
            return spriteIn;
        }
        try {
            RandomEntities.working = true;
            final acq locSpriteIn = spriteIn.getName();
            final String name = locSpriteIn.a();
            final RandomEntityProperties<acq> props = RandomEntities.mapSpriteProperties.get(name);
            if (props == null) {
                return spriteIn;
            }
            final acq loc = props.getResource(re, locSpriteIn);
            if (loc == locSpriteIn) {
                return spriteIn;
            }
            final acq locSprite = TextureUtils.getSpriteLocation(loc);
            final fuv sprite = spriteIn.getTextureAtlas().a(locSprite);
            return sprite;
        }
        finally {
            RandomEntities.working = false;
        }
    }
    
    public static void dbg(final String str) {
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    public static void warn(final String str) {
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    static {
        RandomEntities.mapProperties = new HashMap<String, RandomEntityProperties<acq>>();
        RandomEntities.mapSpriteProperties = new HashMap<String, RandomEntityProperties<acq>>();
        RandomEntities.active = false;
        RandomEntities.randomEntity = new RandomEntity();
        RandomEntities.randomTileEntity = new RandomTileEntity();
        RandomEntities.working = false;
        DEPENDANT_SUFFIXES = new String[] { "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar" };
        HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue(null, btm.class, String[].class, 0);
        HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue(null, btm.class, String[].class, 1);
    }
}
