// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import net.minecraft.world.item.DyeColor;
import net.optifine.util.EntityTypeUtils;
import net.optifine.util.ItemUtils;
import java.util.TreeSet;
import java.util.Properties;
import java.lang.reflect.Array;
import java.util.EnumSet;
import net.minecraft.resources.ResourceLocation;
import java.util.HashSet;
import net.optifine.util.BiomeUtils;
import java.util.Set;
import java.util.Iterator;
import net.optifine.ConnectedProperties;
import java.util.HashMap;
import net.optifine.util.BlockUtils;
import java.util.List;
import java.util.Collection;
import java.util.Arrays;
import net.optifine.Config;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Map;

public class ConnectedParser
{
    private String context;
    public static final MatchProfession[] PROFESSIONS_INVALID;
    public static final cen[] DYE_COLORS_INVALID;
    private static Map<acq, BiomeId> MAP_BIOMES_COMPACT;
    private static final INameGetter<Enum> NAME_GETTER_ENUM;
    private static final INameGetter<cen> NAME_GETTER_DYE_COLOR;
    private static final Pattern PATTERN_RANGE_SEPARATOR;
    
    public ConnectedParser(final String context) {
        this.context = null;
        this.context = context;
    }
    
    public String parseName(final String path) {
        String str = path;
        final int pos = str.lastIndexOf(47);
        if (pos >= 0) {
            str = str.substring(pos + 1);
        }
        final int pos2 = str.lastIndexOf(46);
        if (pos2 >= 0) {
            str = str.substring(0, pos2);
        }
        return str;
    }
    
    public String parseBasePath(final String path) {
        final int pos = path.lastIndexOf(47);
        if (pos < 0) {
            return "";
        }
        return path.substring(0, pos);
    }
    
    public MatchBlock[] parseMatchBlocks(final String propMatchBlocks) {
        if (propMatchBlocks == null) {
            return null;
        }
        final List list = new ArrayList();
        final String[] blockStrs = Config.tokenize(propMatchBlocks, " ");
        for (int i = 0; i < blockStrs.length; ++i) {
            final String blockStr = blockStrs[i];
            final MatchBlock[] mbs = this.parseMatchBlock(blockStr);
            if (mbs != null) {
                list.addAll(Arrays.asList(mbs));
            }
        }
        final MatchBlock[] mbs2 = list.toArray(new MatchBlock[list.size()]);
        return mbs2;
    }
    
    public dcb parseBlockState(final String str, final dcb def) {
        final MatchBlock[] mbs = this.parseMatchBlock(str);
        if (mbs == null) {
            return def;
        }
        if (mbs.length != 1) {
            return def;
        }
        final MatchBlock mb = mbs[0];
        final int blockId = mb.getBlockId();
        final cpn block = (cpn)jb.f.a(blockId);
        return block.n();
    }
    
    public MatchBlock[] parseMatchBlock(String blockStr) {
        if (blockStr == null) {
            return null;
        }
        blockStr = blockStr.trim();
        if (blockStr.length() <= 0) {
            return null;
        }
        final String[] parts = Config.tokenize(blockStr, ":");
        String domain = "minecraft";
        int blockIndex = 0;
        if (parts.length > 1 && this.isFullBlockName(parts)) {
            domain = parts[0];
            blockIndex = 1;
        }
        else {
            domain = "minecraft";
            blockIndex = 0;
        }
        final String blockPart = parts[blockIndex];
        final String[] params = Arrays.copyOfRange(parts, blockIndex + 1, parts.length);
        final cpn[] blocks = this.parseBlockPart(domain, blockPart);
        if (blocks == null) {
            return null;
        }
        final MatchBlock[] datas = new MatchBlock[blocks.length];
        for (int i = 0; i < blocks.length; ++i) {
            final cpn block = blocks[i];
            final int blockId = jb.f.a((Object)block);
            int[] metadatas = null;
            if (params.length > 0) {
                metadatas = this.parseBlockMetadatas(block, params);
                if (metadatas == null) {
                    return null;
                }
            }
            final MatchBlock bd = new MatchBlock(blockId, metadatas);
            datas[i] = bd;
        }
        return datas;
    }
    
    public boolean isFullBlockName(final String[] parts) {
        if (parts.length <= 1) {
            return false;
        }
        final String part1 = parts[1];
        return part1.length() >= 1 && !part1.contains("=");
    }
    
    public boolean startsWithDigit(final String str) {
        if (str == null) {
            return false;
        }
        if (str.length() < 1) {
            return false;
        }
        final char ch = str.charAt(0);
        return Character.isDigit(ch);
    }
    
    public cpn[] parseBlockPart(final String domain, final String blockPart) {
        final String fullName = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, domain, blockPart);
        final acq fullLoc = this.makeResourceLocation(fullName);
        if (fullLoc == null) {
            return null;
        }
        final cpn block = BlockUtils.getBlock(fullLoc);
        if (block == null) {
            this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fullName));
            return null;
        }
        final cpn[] blocks = { block };
        return blocks;
    }
    
    private acq makeResourceLocation(final String str) {
        try {
            final acq loc = new acq(str);
            return loc;
        }
        catch (z e) {
            this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, e.getMessage()));
            return null;
        }
    }
    
    private acq makeResourceLocation(final String namespace, final String path) {
        try {
            final acq loc = new acq(namespace, path);
            return loc;
        }
        catch (z e) {
            this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, e.getMessage()));
            return null;
        }
    }
    
    public int[] parseBlockMetadatas(final cpn block, final String[] params) {
        if (params.length <= 0) {
            return null;
        }
        final dcb stateDefault = block.n();
        final Collection properties = stateDefault.B();
        final Map<dde, List<Comparable>> mapPropValues = new HashMap<dde, List<Comparable>>();
        for (int i = 0; i < params.length; ++i) {
            final String param = params[i];
            if (param.length() > 0) {
                final String[] parts = Config.tokenize(param, "=");
                if (parts.length != 2) {
                    this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, param));
                    return null;
                }
                final String key = parts[0];
                final String valStr = parts[1];
                final dde prop = ConnectedProperties.getProperty(key, properties);
                if (prop == null) {
                    this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Lcpn;)Ljava/lang/String;, key, block));
                    return null;
                }
                List<Comparable> list = mapPropValues.get(key);
                if (list == null) {
                    list = new ArrayList<Comparable>();
                    mapPropValues.put(prop, list);
                }
                final String[] vals = Config.tokenize(valStr, ",");
                for (int v = 0; v < vals.length; ++v) {
                    final String val = vals[v];
                    final Comparable propVal = parsePropertyValue(prop, val);
                    if (propVal == null) {
                        this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Lcpn;)Ljava/lang/String;, val, key, block));
                        return null;
                    }
                    list.add(propVal);
                }
            }
        }
        if (mapPropValues.isEmpty()) {
            return null;
        }
        final List<Integer> listMetadatas = new ArrayList<Integer>();
        final int metaCount = BlockUtils.getMetadataCount(block);
        for (int md = 0; md < metaCount; ++md) {
            try {
                final dcb bs = BlockUtils.getBlockState(block, md);
                if (this.matchState(bs, mapPropValues)) {
                    listMetadatas.add(md);
                }
            }
            catch (IllegalArgumentException ex) {}
        }
        if (listMetadatas.size() == metaCount) {
            return null;
        }
        final int[] metadatas = new int[listMetadatas.size()];
        for (int j = 0; j < metadatas.length; ++j) {
            metadatas[j] = listMetadatas.get(j);
        }
        return metadatas;
    }
    
    public static Comparable parsePropertyValue(final dde prop, final String valStr) {
        final Class valueClass = prop.g();
        Comparable valueObj = parseValue(valStr, valueClass);
        if (valueObj == null) {
            final Collection propertyValues = prop.a();
            valueObj = getPropertyValue(valStr, propertyValues);
        }
        return valueObj;
    }
    
    public static Comparable getPropertyValue(final String value, final Collection propertyValues) {
        for (final Comparable obj : propertyValues) {
            if (getValueName(obj).equals(value)) {
                return obj;
            }
        }
        return null;
    }
    
    private static Object getValueName(final Comparable obj) {
        if (obj instanceof apr) {
            final apr iss = (apr)obj;
            return iss.c();
        }
        return obj.toString();
    }
    
    public static Comparable parseValue(final String str, final Class cls) {
        if (cls == String.class) {
            return str;
        }
        if (cls == Boolean.class) {
            return Boolean.valueOf(str);
        }
        if (cls == Float.class) {
            return Float.valueOf(str);
        }
        if (cls == Double.class) {
            return Double.valueOf(str);
        }
        if (cls == Integer.class) {
            return Integer.valueOf(str);
        }
        if (cls == Long.class) {
            return Long.valueOf(str);
        }
        return null;
    }
    
    public boolean matchState(final dcb bs, final Map<dde, List<Comparable>> mapPropValues) {
        final Set<dde> keys = mapPropValues.keySet();
        for (final dde prop : keys) {
            final List<Comparable> vals = mapPropValues.get(prop);
            final Comparable bsVal = bs.c(prop);
            if (bsVal == null) {
                return false;
            }
            if (!vals.contains(bsVal)) {
                return false;
            }
        }
        return true;
    }
    
    public BiomeId[] parseBiomes(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        boolean negative = false;
        if (str.startsWith("!")) {
            negative = true;
            str = str.substring(1);
        }
        final String[] biomeNames = Config.tokenize(str, " ");
        List<BiomeId> list = new ArrayList<BiomeId>();
        for (int i = 0; i < biomeNames.length; ++i) {
            final String biomeName = biomeNames[i];
            final BiomeId biome = this.getBiomeId(biomeName);
            if (biome == null) {
                this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, biomeName));
            }
            else {
                list.add(biome);
            }
        }
        if (negative) {
            final Set<acq> allBiomes = new HashSet<acq>((Collection<? extends acq>)BiomeUtils.getLocations());
            for (final BiomeId bi : list) {
                allBiomes.remove(bi.getResourceLocation());
            }
            list = BiomeUtils.getBiomeIds((Collection<ResourceLocation>)allBiomes);
        }
        final BiomeId[] biomeArr = list.toArray(new BiomeId[list.size()]);
        return biomeArr;
    }
    
    public BiomeId getBiomeId(String biomeName) {
        biomeName = biomeName.toLowerCase();
        final acq biomeLoc = this.makeResourceLocation(biomeName);
        if (biomeLoc != null) {
            final BiomeId biome = BiomeUtils.getBiomeId(biomeLoc);
            if (biome != null) {
                return biome;
            }
        }
        final String biomeNameCompact = biomeName.replace(" ", "").replace("_", "");
        final acq biomeLocCompact = this.makeResourceLocation(biomeNameCompact);
        if (ConnectedParser.MAP_BIOMES_COMPACT == null) {
            ConnectedParser.MAP_BIOMES_COMPACT = new HashMap<ResourceLocation, BiomeId>();
            final Set<acq> biomeIds = (Set<acq>)BiomeUtils.getLocations();
            for (final acq loc : biomeIds) {
                final BiomeId biomeCompact = BiomeUtils.getBiomeId(loc);
                if (biomeCompact == null) {
                    continue;
                }
                final String pathCompact = loc.a().replace(" ", "").replace("_", "").toLowerCase();
                final acq locCompact = this.makeResourceLocation(loc.b(), pathCompact);
                if (locCompact == null) {
                    continue;
                }
                ConnectedParser.MAP_BIOMES_COMPACT.put((ResourceLocation)locCompact, biomeCompact);
            }
        }
        final BiomeId biome2 = ConnectedParser.MAP_BIOMES_COMPACT.get(biomeLocCompact);
        if (biome2 != null) {
            return biome2;
        }
        return null;
    }
    
    public int parseInt(String str, final int defVal) {
        if (str == null) {
            return defVal;
        }
        str = str.trim();
        final int num = Config.parseInt(str, -1);
        if (num < 0) {
            this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return defVal;
        }
        return num;
    }
    
    public int parseIntNeg(String str, final int defVal) {
        if (str == null) {
            return defVal;
        }
        str = str.trim();
        final int num = Config.parseInt(str, Integer.MIN_VALUE);
        if (num == Integer.MIN_VALUE) {
            this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return defVal;
        }
        return num;
    }
    
    public int[] parseIntList(final String str) {
        if (str == null) {
            return null;
        }
        final List<Integer> list = new ArrayList<Integer>();
        final String[] intStrs = Config.tokenize(str, " ,");
        for (int i = 0; i < intStrs.length; ++i) {
            final String intStr = intStrs[i];
            if (intStr.contains("-")) {
                final String[] subStrs = Config.tokenize(intStr, "-");
                if (subStrs.length != 2) {
                    this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, intStr, str));
                }
                else {
                    final int min = Config.parseInt(subStrs[0], -1);
                    final int max = Config.parseInt(subStrs[1], -1);
                    if (min < 0 || max < 0 || min > max) {
                        this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, intStr, str));
                    }
                    else {
                        for (int n = min; n <= max; ++n) {
                            list.add(n);
                        }
                    }
                }
            }
            else {
                final int val = Config.parseInt(intStr, -1);
                if (val < 0) {
                    this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, intStr, str));
                }
                else {
                    list.add(val);
                }
            }
        }
        final int[] ints = new int[list.size()];
        for (int j = 0; j < ints.length; ++j) {
            ints[j] = list.get(j);
        }
        return ints;
    }
    
    public boolean[] parseFaces(final String str, final boolean[] defVal) {
        if (str == null) {
            return defVal;
        }
        final EnumSet setFaces = EnumSet.allOf(ha.class);
        final String[] faceStrs = Config.tokenize(str, " ,");
        for (int i = 0; i < faceStrs.length; ++i) {
            final String faceStr = faceStrs[i];
            if (faceStr.equals("sides")) {
                setFaces.add(ha.c);
                setFaces.add(ha.d);
                setFaces.add(ha.e);
                setFaces.add(ha.f);
            }
            else if (faceStr.equals("all")) {
                setFaces.addAll(Arrays.asList(ha.p));
            }
            else {
                final ha face = this.parseFace(faceStr);
                if (face != null) {
                    setFaces.add(face);
                }
            }
        }
        final boolean[] faces = new boolean[ha.p.length];
        for (int j = 0; j < faces.length; ++j) {
            faces[j] = setFaces.contains(ha.p[j]);
        }
        return faces;
    }
    
    public ha parseFace(String str) {
        str = str.toLowerCase();
        if (str.equals("bottom") || str.equals("down")) {
            return ha.a;
        }
        if (str.equals("top") || str.equals("up")) {
            return ha.b;
        }
        if (str.equals("north")) {
            return ha.c;
        }
        if (str.equals("south")) {
            return ha.d;
        }
        if (str.equals("east")) {
            return ha.f;
        }
        if (str.equals("west")) {
            return ha.e;
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return null;
    }
    
    public void dbg(final String str) {
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.context, str));
    }
    
    public void warn(final String str) {
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.context, str));
    }
    
    public RangeListInt parseRangeListInt(final String str) {
        if (str == null) {
            return null;
        }
        final RangeListInt list = new RangeListInt();
        final String[] parts = Config.tokenize(str, " ,");
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            final RangeInt ri = this.parseRangeInt(part);
            if (ri == null) {
                return null;
            }
            list.addRange(ri);
        }
        return list;
    }
    
    public RangeListInt parseRangeListIntNeg(final String str) {
        if (str == null) {
            return null;
        }
        final RangeListInt list = new RangeListInt();
        final String[] parts = Config.tokenize(str, " ,");
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            final RangeInt ri = this.parseRangeIntNeg(part);
            if (ri == null) {
                return null;
            }
            list.addRange(ri);
        }
        return list;
    }
    
    private RangeInt parseRangeInt(final String str) {
        if (str == null) {
            return null;
        }
        if (str.indexOf(45) >= 0) {
            final String[] parts = Config.tokenize(str, "-");
            if (parts.length != 2) {
                this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
                return null;
            }
            final int min = Config.parseInt(parts[0], -1);
            final int max = Config.parseInt(parts[1], -1);
            if (min < 0 || max < 0) {
                this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
                return null;
            }
            return new RangeInt(min, max);
        }
        else {
            final int val = Config.parseInt(str, -1);
            if (val < 0) {
                this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
                return null;
            }
            return new RangeInt(val, val);
        }
    }
    
    private RangeInt parseRangeIntNeg(final String str) {
        if (str == null) {
            return null;
        }
        if (str.indexOf("=") >= 0) {
            this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return null;
        }
        final String strEq = ConnectedParser.PATTERN_RANGE_SEPARATOR.matcher(str).replaceAll("$1=$2");
        if (strEq.indexOf(61) >= 0) {
            final String[] parts = Config.tokenize(strEq, "=");
            if (parts.length != 2) {
                this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
                return null;
            }
            final int min = Config.parseInt(stripBrackets(parts[0]), Integer.MIN_VALUE);
            final int max = Config.parseInt(stripBrackets(parts[1]), Integer.MIN_VALUE);
            if (min == Integer.MIN_VALUE || max == Integer.MIN_VALUE) {
                this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
                return null;
            }
            return new RangeInt(min, max);
        }
        else {
            final int val = Config.parseInt(stripBrackets(str), Integer.MIN_VALUE);
            if (val == Integer.MIN_VALUE) {
                this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
                return null;
            }
            return new RangeInt(val, val);
        }
    }
    
    private static String stripBrackets(final String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }
    
    public boolean parseBoolean(final String str, final boolean defVal) {
        if (str == null) {
            return defVal;
        }
        final String strLower = str.toLowerCase().trim();
        if (strLower.equals("true")) {
            return true;
        }
        if (strLower.equals("false")) {
            return false;
        }
        this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return defVal;
    }
    
    public Boolean parseBooleanObject(final String str) {
        if (str == null) {
            return null;
        }
        final String strLower = str.toLowerCase().trim();
        if (strLower.equals("true")) {
            return Boolean.TRUE;
        }
        if (strLower.equals("false")) {
            return Boolean.FALSE;
        }
        this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return null;
    }
    
    public static int parseColor(String str, final int defVal) {
        if (str == null) {
            return defVal;
        }
        str = str.trim();
        try {
            final int val = Integer.parseInt(str, 16) & 0xFFFFFF;
            return val;
        }
        catch (NumberFormatException e) {
            return defVal;
        }
    }
    
    public static int parseColor4(String str, final int defVal) {
        if (str == null) {
            return defVal;
        }
        str = str.trim();
        try {
            final int val = (int)(Long.parseLong(str, 16) & -1L);
            return val;
        }
        catch (NumberFormatException e) {
            return defVal;
        }
    }
    
    public fkf parseBlockRenderLayer(String str, final fkf def) {
        if (str == null) {
            return def;
        }
        str = str.toLowerCase().trim();
        final fkf[] layers = fkf.CHUNK_RENDER_TYPES;
        for (int i = 0; i < layers.length; ++i) {
            final fkf layer = layers[i];
            if (str.equals(layer.getName().toLowerCase())) {
                return layer;
            }
        }
        return def;
    }
    
    public <T> T parseObject(final String str, final T[] objs, final INameGetter nameGetter, final String property) {
        if (str == null) {
            return null;
        }
        final String strLower = str.toLowerCase().trim();
        for (int i = 0; i < objs.length; ++i) {
            final T obj = objs[i];
            final String name = nameGetter.getName(obj);
            if (name != null) {
                if (name.toLowerCase().equals(strLower)) {
                    return obj;
                }
            }
        }
        this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, property, str));
        return null;
    }
    
    public <T> T[] parseObjects(String str, final T[] objs, final INameGetter nameGetter, final String property, final T[] errValue) {
        if (str == null) {
            return null;
        }
        str = str.toLowerCase().trim();
        final String[] parts = Config.tokenize(str, " ");
        final T[] arr = (T[])Array.newInstance(objs.getClass().getComponentType(), parts.length);
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            final T obj = this.parseObject(part, objs, nameGetter, property);
            if (obj == null) {
                return errValue;
            }
            arr[i] = obj;
        }
        return arr;
    }
    
    public Enum parseEnum(final String str, final Enum[] enums, final String property) {
        return this.parseObject(str, enums, ConnectedParser.NAME_GETTER_ENUM, property);
    }
    
    public Enum[] parseEnums(final String str, final Enum[] enums, final String property, final Enum[] errValue) {
        return this.parseObjects(str, enums, ConnectedParser.NAME_GETTER_ENUM, property, errValue);
    }
    
    public cen[] parseDyeColors(final String str, final String property, final cen[] errValue) {
        return this.parseObjects(str, cen.values(), ConnectedParser.NAME_GETTER_DYE_COLOR, property, errValue);
    }
    
    public Weather[] parseWeather(final String str, final String property, final Weather[] errValue) {
        return this.parseObjects(str, Weather.values(), ConnectedParser.NAME_GETTER_ENUM, property, errValue);
    }
    
    public NbtTagValue[] parseNbtTagValues(final Properties props, final String prefix) {
        final List<NbtTagValue> listNbts = new ArrayList<NbtTagValue>();
        final Set keySet = props.keySet();
        for (final String key : keySet) {
            if (!key.startsWith(prefix)) {
                continue;
            }
            final String val = (String)props.get(key);
            final String id = key.substring(prefix.length());
            final NbtTagValue nbt = new NbtTagValue(id, val);
            listNbts.add(nbt);
        }
        if (listNbts.isEmpty()) {
            return null;
        }
        final NbtTagValue[] nbts = listNbts.toArray(new NbtTagValue[listNbts.size()]);
        return nbts;
    }
    
    public NbtTagValue parseNbtTagValue(final String path, final String value) {
        if (path == null || value == null) {
            return null;
        }
        return new NbtTagValue(path, value);
    }
    
    public MatchProfession[] parseProfessions(final String profStr) {
        if (profStr == null) {
            return null;
        }
        final List<MatchProfession> list = new ArrayList<MatchProfession>();
        final String[] tokens = Config.tokenize(profStr, " ");
        for (int i = 0; i < tokens.length; ++i) {
            final String str = tokens[i];
            final MatchProfession prof = this.parseProfession(str);
            if (prof == null) {
                this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
                return ConnectedParser.PROFESSIONS_INVALID;
            }
            list.add(prof);
        }
        if (list.isEmpty()) {
            return null;
        }
        final MatchProfession[] arr = list.toArray(new MatchProfession[list.size()]);
        return arr;
    }
    
    private MatchProfession parseProfession(final String str) {
        String strProf = str;
        String strLevels = null;
        final int pos = str.lastIndexOf(58);
        if (pos >= 0) {
            final String part1 = str.substring(0, pos);
            final String part2 = str.substring(pos + 1);
            if (part2.isEmpty() || part2.matches("[0-9].*")) {
                strProf = part1;
                strLevels = part2;
            }
        }
        final bye prof = this.parseVillagerProfession(strProf);
        if (prof == null) {
            return null;
        }
        final int[] levels = this.parseIntList(strLevels);
        final MatchProfession mp = new MatchProfession(prof, levels);
        return mp;
    }
    
    private bye parseVillagerProfession(String str) {
        if (str == null) {
            return null;
        }
        str = str.toLowerCase();
        final acq loc = this.makeResourceLocation(str);
        if (loc == null) {
            return null;
        }
        final hr<bye> registry = (hr<bye>)jb.A;
        if (!registry.c(loc)) {
            return null;
        }
        return (bye)registry.a(loc);
    }
    
    public int[] parseItems(String str) {
        str = str.trim();
        final Set<Integer> setIds = new TreeSet<Integer>();
        final String[] tokens = Config.tokenize(str, " ");
        for (int i = 0; i < tokens.length; ++i) {
            final String token = tokens[i];
            final acq loc = this.makeResourceLocation(token);
            if (loc != null) {
                final cfu item = ItemUtils.getItem(loc);
                if (item == null) {
                    this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, token));
                }
                else {
                    final int id = ItemUtils.getId(item);
                    if (id < 0) {
                        this.warn(invokedynamic(makeConcatWithConstants:(Lcfu;Ljava/lang/String;)Ljava/lang/String;, item, token));
                    }
                    else {
                        setIds.add(new Integer(id));
                    }
                }
            }
        }
        final Integer[] integers = setIds.toArray(new Integer[setIds.size()]);
        final int[] ints = Config.toPrimitive(integers);
        return ints;
    }
    
    public int[] parseEntities(String str) {
        str = str.trim();
        final Set<Integer> setIds = new TreeSet<Integer>();
        final String[] tokens = Config.tokenize(str, " ");
        for (int i = 0; i < tokens.length; ++i) {
            final String token = tokens[i];
            final acq loc = this.makeResourceLocation(token);
            if (loc != null) {
                final bfn type = EntityTypeUtils.getEntityType(loc);
                if (type == null) {
                    this.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, token));
                }
                else {
                    final int id = jb.h.a((Object)type);
                    if (id < 0) {
                        this.warn(invokedynamic(makeConcatWithConstants:(Lbfn;Ljava/lang/String;)Ljava/lang/String;, type, token));
                    }
                    else {
                        setIds.add(new Integer(id));
                    }
                }
            }
        }
        final Integer[] integers = setIds.toArray(new Integer[setIds.size()]);
        final int[] ints = Config.toPrimitive(integers);
        return ints;
    }
    
    static {
        PROFESSIONS_INVALID = new MatchProfession[0];
        ConnectedParser.DYE_COLORS_INVALID = new cen[0];
        ConnectedParser.MAP_BIOMES_COMPACT = null;
        NAME_GETTER_ENUM = new INameGetter<Enum>() {
            @Override
            public String getName(final Enum en) {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     2: nop            
                //     3: nop            
                //     4: nop            
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2162)
                //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
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
        NAME_GETTER_DYE_COLOR = new INameGetter<DyeColor>() {
            @Override
            public String getName(final DyeColor col) {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: nop            
                //     2: nop            
                //     3: nop            
                //     4: fload_1         /* col */
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2162)
                //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
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
        PATTERN_RANGE_SEPARATOR = Pattern.compile("(\\d|\\))-(\\d|\\()");
    }
}
