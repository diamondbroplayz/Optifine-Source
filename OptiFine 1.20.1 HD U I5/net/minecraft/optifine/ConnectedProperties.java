// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.config.Matches;
import net.optifine.util.MathUtils;
import net.optifine.util.BlockUtils;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import net.optifine.util.TextureUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import net.optifine.render.RenderTypes;
import net.optifine.config.RangeInt;
import net.optifine.config.ConnectedParser;
import java.util.Properties;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeListInt;
import net.optifine.config.BiomeId;
import net.optifine.config.MatchBlock;

public class ConnectedProperties
{
    public String name;
    public String basePath;
    public MatchBlock[] matchBlocks;
    public int[] metadatas;
    public String[] matchTiles;
    public int method;
    public String[] tiles;
    public int connect;
    public int faces;
    public BiomeId[] biomes;
    public RangeListInt heights;
    public int renderPass;
    public boolean innerSeams;
    public int[] ctmTileIndexes;
    public int width;
    public int height;
    public int[] weights;
    public int randomLoops;
    public int symmetry;
    public boolean linked;
    public NbtTagValue nbtName;
    public int[] sumWeights;
    public int sumAllWeights;
    public fuv[] matchTileIcons;
    public fuv[] tileIcons;
    public MatchBlock[] connectBlocks;
    public String[] connectTiles;
    public fuv[] connectTileIcons;
    public int tintIndex;
    public dcb tintBlockState;
    public fkf layer;
    public static final int METHOD_NONE = 0;
    public static final int METHOD_CTM = 1;
    public static final int METHOD_HORIZONTAL = 2;
    public static final int METHOD_TOP = 3;
    public static final int METHOD_RANDOM = 4;
    public static final int METHOD_REPEAT = 5;
    public static final int METHOD_VERTICAL = 6;
    public static final int METHOD_FIXED = 7;
    public static final int METHOD_HORIZONTAL_VERTICAL = 8;
    public static final int METHOD_VERTICAL_HORIZONTAL = 9;
    public static final int METHOD_CTM_COMPACT = 10;
    public static final int METHOD_OVERLAY = 11;
    public static final int METHOD_OVERLAY_FIXED = 12;
    public static final int METHOD_OVERLAY_RANDOM = 13;
    public static final int METHOD_OVERLAY_REPEAT = 14;
    public static final int METHOD_OVERLAY_CTM = 15;
    public static final int CONNECT_NONE = 0;
    public static final int CONNECT_BLOCK = 1;
    public static final int CONNECT_TILE = 2;
    public static final int CONNECT_STATE = 3;
    public static final int CONNECT_UNKNOWN = 128;
    public static final int FACE_BOTTOM = 1;
    public static final int FACE_TOP = 2;
    public static final int FACE_NORTH = 4;
    public static final int FACE_SOUTH = 8;
    public static final int FACE_WEST = 16;
    public static final int FACE_EAST = 32;
    public static final int FACE_SIDES = 60;
    public static final int FACE_ALL = 63;
    public static final int FACE_UNKNOWN = 128;
    public static final int SYMMETRY_NONE = 1;
    public static final int SYMMETRY_OPPOSITE = 2;
    public static final int SYMMETRY_ALL = 6;
    public static final int SYMMETRY_UNKNOWN = 128;
    public static final String TILE_SKIP_PNG = "<skip>.png";
    public static final String TILE_DEFAULT_PNG = "<default>.png";
    
    public ConnectedProperties(final Properties props, final String path) {
        this.name = null;
        this.basePath = null;
        this.matchBlocks = null;
        this.metadatas = null;
        this.matchTiles = null;
        this.method = 0;
        this.tiles = null;
        this.connect = 0;
        this.faces = 63;
        this.biomes = null;
        this.heights = null;
        this.renderPass = 0;
        this.innerSeams = false;
        this.ctmTileIndexes = null;
        this.width = 0;
        this.height = 0;
        this.weights = null;
        this.randomLoops = 0;
        this.symmetry = 1;
        this.linked = false;
        this.nbtName = null;
        this.sumWeights = null;
        this.sumAllWeights = 1;
        this.matchTileIcons = null;
        this.tileIcons = null;
        this.connectBlocks = null;
        this.connectTiles = null;
        this.connectTileIcons = null;
        this.tintIndex = -1;
        this.tintBlockState = cpo.a.n();
        this.layer = null;
        final ConnectedParser cp = new ConnectedParser("ConnectedTextures");
        this.name = cp.parseName(path);
        this.basePath = cp.parseBasePath(path);
        this.matchBlocks = cp.parseMatchBlocks(props.getProperty("matchBlocks"));
        this.metadatas = cp.parseIntList(props.getProperty("metadata"));
        this.matchTiles = this.parseMatchTiles(props.getProperty("matchTiles"));
        this.method = parseMethod(props.getProperty("method"));
        this.tiles = this.parseTileNames(props.getProperty("tiles"));
        this.connect = parseConnect(props.getProperty("connect"));
        this.faces = parseFaces(props.getProperty("faces"));
        this.biomes = cp.parseBiomes(props.getProperty("biomes"));
        this.heights = cp.parseRangeListIntNeg(props.getProperty("heights"));
        if (this.heights == null) {
            final int minHeight = cp.parseIntNeg(props.getProperty("minHeight"), Integer.MIN_VALUE);
            final int maxHeight = cp.parseIntNeg(props.getProperty("maxHeight"), Integer.MAX_VALUE);
            if (minHeight != Integer.MIN_VALUE || maxHeight != Integer.MAX_VALUE) {
                this.heights = new RangeListInt(new RangeInt(minHeight, maxHeight));
            }
        }
        this.renderPass = cp.parseInt(props.getProperty("renderPass"), -1);
        this.innerSeams = cp.parseBoolean(props.getProperty("innerSeams"), false);
        this.ctmTileIndexes = this.parseCtmTileIndexes(props);
        this.width = cp.parseInt(props.getProperty("width"), -1);
        this.height = cp.parseInt(props.getProperty("height"), -1);
        this.weights = cp.parseIntList(props.getProperty("weights"));
        this.randomLoops = cp.parseInt(props.getProperty("randomLoops"), 0);
        this.symmetry = parseSymmetry(props.getProperty("symmetry"));
        this.linked = cp.parseBoolean(props.getProperty("linked"), false);
        this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name"));
        this.connectBlocks = cp.parseMatchBlocks(props.getProperty("connectBlocks"));
        this.connectTiles = this.parseMatchTiles(props.getProperty("connectTiles"));
        this.tintIndex = cp.parseInt(props.getProperty("tintIndex"), -1);
        this.tintBlockState = cp.parseBlockState(props.getProperty("tintBlock"), cpo.a.n());
        this.layer = cp.parseBlockRenderLayer(props.getProperty("layer"), RenderTypes.CUTOUT_MIPPED);
    }
    
    private int[] parseCtmTileIndexes(final Properties props) {
        if (this.tiles == null) {
            return null;
        }
        final Map<Integer, Integer> mapTileIndexes = new HashMap<Integer, Integer>();
        final Set keys = props.keySet();
        for (final Object key : keys) {
            if (!(key instanceof String)) {
                continue;
            }
            final String keyStr = (String)key;
            final String PREFIX = "ctm.";
            if (!keyStr.startsWith(PREFIX)) {
                continue;
            }
            final String ctmIndexStr = keyStr.substring(PREFIX.length());
            String ctmTileIndexStr = props.getProperty(keyStr);
            if (ctmTileIndexStr == null) {
                continue;
            }
            ctmTileIndexStr = ctmTileIndexStr.trim();
            final int ctmIndex = Config.parseInt(ctmIndexStr, -1);
            if (ctmIndex < 0 || ctmIndex > 46) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ctmIndexStr));
            }
            else {
                final int ctmTileIndex = Config.parseInt(ctmTileIndexStr, -1);
                if (ctmTileIndex < 0 || ctmTileIndex >= this.tiles.length) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ctmTileIndexStr));
                }
                else {
                    mapTileIndexes.put(ctmIndex, ctmTileIndex);
                }
            }
        }
        if (mapTileIndexes.isEmpty()) {
            return null;
        }
        final int[] tileIndexes = new int[47];
        for (int i = 0; i < tileIndexes.length; ++i) {
            tileIndexes[i] = -1;
            if (mapTileIndexes.containsKey(i)) {
                tileIndexes[i] = mapTileIndexes.get(i);
            }
        }
        return tileIndexes;
    }
    
    private String[] parseMatchTiles(final String str) {
        if (str == null) {
            return null;
        }
        final String[] names = Config.tokenize(str, " ");
        for (int i = 0; i < names.length; ++i) {
            String iconName = names[i];
            if (iconName.endsWith(".png")) {
                iconName = iconName.substring(0, iconName.length() - 4);
            }
            iconName = TextureUtils.fixResourcePath(iconName, this.basePath);
            names[i] = iconName;
        }
        return names;
    }
    
    private static String parseName(final String path) {
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
    
    private static String parseBasePath(final String path) {
        final int pos = path.lastIndexOf(47);
        if (pos < 0) {
            return "";
        }
        return path.substring(0, pos);
    }
    
    private String[] parseTileNames(final String str) {
        if (str == null) {
            return null;
        }
        final List list = new ArrayList();
        final String[] iconStrs = Config.tokenize(str, " ,");
        for (int i = 0; i < iconStrs.length; ++i) {
            final String iconStr = iconStrs[i];
            if (iconStr.contains("-")) {
                final String[] subStrs = Config.tokenize(iconStr, "-");
                if (subStrs.length == 2) {
                    final int min = Config.parseInt(subStrs[0], -1);
                    final int max = Config.parseInt(subStrs[1], -1);
                    if (min >= 0 && max >= 0) {
                        if (min > max) {
                            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, iconStr, str));
                            continue;
                        }
                        for (int n = min; n <= max; ++n) {
                            list.add(String.valueOf(n));
                        }
                        continue;
                    }
                }
            }
            list.add(iconStr);
        }
        final String[] names = list.toArray(new String[list.size()]);
        for (int j = 0; j < names.length; ++j) {
            String iconName = names[j];
            iconName = TextureUtils.fixResourcePath(iconName, this.basePath);
            if (!iconName.startsWith(this.basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("optifine/")) {
                iconName = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.basePath, iconName);
            }
            if (iconName.endsWith(".png")) {
                iconName = iconName.substring(0, iconName.length() - 4);
            }
            if (iconName.startsWith("/")) {
                iconName = iconName.substring(1);
            }
            names[j] = iconName;
        }
        return names;
    }
    
    private static int parseSymmetry(String str) {
        if (str == null) {
            return 1;
        }
        str = str.trim();
        if (str.equals("opposite")) {
            return 2;
        }
        if (str.equals("all")) {
            return 6;
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return 1;
    }
    
    private static int parseFaces(final String str) {
        if (str == null) {
            return 63;
        }
        final String[] faceStrs = Config.tokenize(str, " ,");
        int facesMask = 0;
        for (int i = 0; i < faceStrs.length; ++i) {
            final String faceStr = faceStrs[i];
            final int faceMask = parseFace(faceStr);
            facesMask |= faceMask;
        }
        return facesMask;
    }
    
    private static int parseFace(String str) {
        str = str.toLowerCase();
        if (str.equals("bottom") || str.equals("down")) {
            return 1;
        }
        if (str.equals("top") || str.equals("up")) {
            return 2;
        }
        if (str.equals("north")) {
            return 4;
        }
        if (str.equals("south")) {
            return 8;
        }
        if (str.equals("east")) {
            return 32;
        }
        if (str.equals("west")) {
            return 16;
        }
        if (str.equals("sides")) {
            return 60;
        }
        if (str.equals("all")) {
            return 63;
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return 128;
    }
    
    private static int parseConnect(String str) {
        if (str == null) {
            return 0;
        }
        str = str.trim();
        if (str.equals("block")) {
            return 1;
        }
        if (str.equals("tile")) {
            return 2;
        }
        if (str.equals("state")) {
            return 3;
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return 128;
    }
    
    public static dde getProperty(final String key, final Collection properties) {
        for (final dde prop : properties) {
            if (key.equals(prop.f())) {
                return prop;
            }
        }
        return null;
    }
    
    private static int parseMethod(String str) {
        if (str == null) {
            return 1;
        }
        str = str.trim();
        if (str.equals("ctm") || str.equals("glass")) {
            return 1;
        }
        if (str.equals("ctm_compact")) {
            return 10;
        }
        if (str.equals("horizontal") || str.equals("bookshelf")) {
            return 2;
        }
        if (str.equals("vertical")) {
            return 6;
        }
        if (str.equals("top")) {
            return 3;
        }
        if (str.equals("random")) {
            return 4;
        }
        if (str.equals("repeat")) {
            return 5;
        }
        if (str.equals("fixed")) {
            return 7;
        }
        if (str.equals("horizontal+vertical") || str.equals("h+v")) {
            return 8;
        }
        if (str.equals("vertical+horizontal") || str.equals("v+h")) {
            return 9;
        }
        if (str.equals("overlay")) {
            return 11;
        }
        if (str.equals("overlay_fixed")) {
            return 12;
        }
        if (str.equals("overlay_random")) {
            return 13;
        }
        if (str.equals("overlay_repeat")) {
            return 14;
        }
        if (str.equals("overlay_ctm")) {
            return 15;
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return 0;
    }
    
    public boolean isValid(final String path) {
        if (this.name == null || this.name.length() <= 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.basePath == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
        }
        if (this.matchTiles == null && this.matchBlocks == null) {
            this.matchTiles = this.detectMatchTiles();
        }
        if (this.matchBlocks == null && this.matchTiles == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.metadatas != null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.arrayToString(this.metadatas)));
            return false;
        }
        if (this.method == 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.tiles == null || this.tiles.length <= 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.connect == 0) {
            this.connect = this.detectConnect();
        }
        if (this.connect == 128) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.renderPass > 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.renderPass));
            return false;
        }
        if ((this.faces & 0x80) != 0x0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if ((this.symmetry & 0x80) != 0x0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        switch (this.method) {
            case 1: {
                return this.isValidCtm(path);
            }
            case 10: {
                return this.isValidCtmCompact(path);
            }
            case 2: {
                return this.isValidHorizontal(path);
            }
            case 6: {
                return this.isValidVertical(path);
            }
            case 3: {
                return this.isValidTop(path);
            }
            case 4: {
                return this.isValidRandom(path);
            }
            case 5: {
                return this.isValidRepeat(path);
            }
            case 7: {
                return this.isValidFixed(path);
            }
            case 8: {
                return this.isValidHorizontalVertical(path);
            }
            case 9: {
                return this.isValidVerticalHorizontal(path);
            }
            case 11: {
                return this.isValidOverlay(path);
            }
            case 12: {
                return this.isValidOverlayFixed(path);
            }
            case 13: {
                return this.isValidOverlayRandom(path);
            }
            case 14: {
                return this.isValidOverlayRepeat(path);
            }
            case 15: {
                return this.isValidOverlayCtm(path);
            }
            default: {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
                return false;
            }
        }
    }
    
    private int detectConnect() {
        if (this.matchBlocks != null) {
            return 1;
        }
        if (this.matchTiles != null) {
            return 2;
        }
        return 128;
    }
    
    private MatchBlock[] detectMatchBlocks() {
        final int[] ids = this.detectMatchBlockIds();
        if (ids == null) {
            return null;
        }
        final MatchBlock[] mbs = new MatchBlock[ids.length];
        for (int i = 0; i < mbs.length; ++i) {
            mbs[i] = new MatchBlock(ids[i]);
        }
        return mbs;
    }
    
    private int[] detectMatchBlockIds() {
        final String prefixBlock = "block_";
        if (!this.name.startsWith(prefixBlock)) {
            return null;
        }
        final String blockName = this.name.substring(prefixBlock.length());
        final acq loc = new acq(blockName);
        final cpn block = BlockUtils.getBlock(loc);
        if (block == null) {
            return null;
        }
        final int id = BlockUtils.getBlockId(block);
        return new int[] { id };
    }
    
    private String[] detectMatchTiles() {
        final fuv icon = getIcon(this.name);
        if (icon == null) {
            return null;
        }
        return new String[] { this.name };
    }
    
    private static fuv getIcon(final String iconName) {
        final fuu textureMapBlocks = Config.getTextureMap();
        fuv icon = textureMapBlocks.getRegisteredSprite(iconName);
        if (icon != null) {
            return icon;
        }
        icon = textureMapBlocks.getRegisteredSprite(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, iconName));
        return icon;
    }
    
    private boolean isValidCtm(final String path) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
        }
        if (this.tiles.length < 47) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    private boolean isValidCtmCompact(final String path) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("0-4");
        }
        if (this.tiles.length < 5) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    private boolean isValidOverlay(final String path) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("0-16");
        }
        if (this.tiles.length < 17) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.layer == null || this.layer == RenderTypes.SOLID) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lfkf;)Ljava/lang/String;, this.layer));
            return false;
        }
        return true;
    }
    
    private boolean isValidOverlayFixed(final String path) {
        if (!this.isValidFixed(path)) {
            return false;
        }
        if (this.layer == null || this.layer == RenderTypes.SOLID) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lfkf;)Ljava/lang/String;, this.layer));
            return false;
        }
        return true;
    }
    
    private boolean isValidOverlayRandom(final String path) {
        if (!this.isValidRandom(path)) {
            return false;
        }
        if (this.layer == null || this.layer == RenderTypes.SOLID) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lfkf;)Ljava/lang/String;, this.layer));
            return false;
        }
        return true;
    }
    
    private boolean isValidOverlayRepeat(final String path) {
        if (!this.isValidRepeat(path)) {
            return false;
        }
        if (this.layer == null || this.layer == RenderTypes.SOLID) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lfkf;)Ljava/lang/String;, this.layer));
            return false;
        }
        return true;
    }
    
    private boolean isValidOverlayCtm(final String path) {
        if (!this.isValidCtm(path)) {
            return false;
        }
        if (this.layer == null || this.layer == RenderTypes.SOLID) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lfkf;)Ljava/lang/String;, this.layer));
            return false;
        }
        return true;
    }
    
    private boolean isValidHorizontal(final String path) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("12-15");
        }
        if (this.tiles.length != 4) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    private boolean isValidVertical(final String path) {
        if (this.tiles == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.tiles.length != 4) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    private boolean isValidHorizontalVertical(final String path) {
        if (this.tiles == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    private boolean isValidVerticalHorizontal(final String path) {
        if (this.tiles == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    private boolean isValidRandom(final String path) {
        if (this.tiles == null || this.tiles.length <= 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.weights != null) {
            if (this.weights.length > this.tiles.length) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
                final int[] weights2 = new int[this.tiles.length];
                System.arraycopy(this.weights, 0, weights2, 0, weights2.length);
                this.weights = weights2;
            }
            if (this.weights.length < this.tiles.length) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
                final int[] weights2 = new int[this.tiles.length];
                System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
                final int avgWeight = MathUtils.getAverage(this.weights);
                for (int i = this.weights.length; i < weights2.length; ++i) {
                    weights2[i] = avgWeight;
                }
                this.weights = weights2;
            }
            this.sumWeights = new int[this.weights.length];
            int sum = 0;
            for (int j = 0; j < this.weights.length; ++j) {
                sum += this.weights[j];
                this.sumWeights[j] = sum;
            }
            this.sumAllWeights = sum;
            if (this.sumAllWeights <= 0) {
                Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, sum));
                this.sumAllWeights = 1;
            }
        }
        if (this.randomLoops < 0 || this.randomLoops > 9) {
            Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.randomLoops));
            return false;
        }
        return true;
    }
    
    private boolean isValidRepeat(final String path) {
        if (this.tiles == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.width <= 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.height <= 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.tiles.length != this.width * this.height) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    private boolean isValidFixed(final String path) {
        if (this.tiles == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.tiles.length != 1) {
            Config.warn("Number of tiles should be 1 for method: fixed.");
            return false;
        }
        return true;
    }
    
    private boolean isValidTop(final String path) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("66");
        }
        if (this.tiles.length != 1) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    public void updateIcons(final fuu textureMap) {
        if (this.matchTiles != null) {
            this.matchTileIcons = registerIcons(this.matchTiles, textureMap, false, false);
        }
        if (this.connectTiles != null) {
            this.connectTileIcons = registerIcons(this.connectTiles, textureMap, false, false);
        }
        if (this.tiles != null) {
            this.tileIcons = registerIcons(this.tiles, textureMap, true, !isMethodOverlay(this.method));
        }
    }
    
    public void refreshIcons(final fuu textureMap) {
        this.refreshIcons(this.matchTileIcons, textureMap);
        this.refreshIcons(this.connectTileIcons, textureMap);
        this.refreshIcons(this.tileIcons, textureMap);
    }
    
    private void refreshIcons(final fuv[] sprites, final fuu textureMap) {
        if (sprites == null) {
            return;
        }
        for (int i = 0; i < sprites.length; ++i) {
            final fuv sprite = sprites[i];
            if (sprite != null) {
                final acq loc = sprite.getName();
                final fuv spriteNew = textureMap.a(loc);
                if (spriteNew == null || ful.isMisingSprite(spriteNew)) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(Lacq;Ljava/lang/String;)Ljava/lang/String;, loc, this.basePath));
                }
                sprites[i] = spriteNew;
            }
        }
    }
    
    private static boolean isMethodOverlay(final int method) {
        switch (method) {
            case 11:
            case 12:
            case 13:
            case 14:
            case 15: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private static fuv[] registerIcons(final String[] tileNames, final fuu textureMap, final boolean skipTiles, final boolean defaultTiles) {
        if (tileNames == null) {
            return null;
        }
        final List iconList = new ArrayList();
        for (int i = 0; i < tileNames.length; ++i) {
            final String iconName = tileNames[i];
            final acq resLoc = new acq(iconName);
            final String domain = resLoc.b();
            String path = resLoc.a();
            if (!path.contains("/")) {
                path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path);
            }
            final String filePath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path);
            if (skipTiles && filePath.endsWith("<skip>.png")) {
                iconList.add(null);
            }
            else if (defaultTiles && filePath.endsWith("<default>.png")) {
                iconList.add(ConnectedTextures.SPRITE_DEFAULT);
            }
            else {
                final acq locFile = new acq(domain, filePath);
                final boolean exists = Config.hasResource(locFile);
                if (!exists) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filePath));
                }
                final String prefixTextures = "textures/";
                String pathSprite = path;
                if (pathSprite.startsWith(prefixTextures)) {
                    pathSprite = pathSprite.substring(prefixTextures.length());
                }
                final acq locSprite = new acq(domain, pathSprite);
                final fuv icon = textureMap.registerSprite(locSprite);
                iconList.add(icon);
            }
        }
        final fuv[] icons = iconList.toArray(new fuv[iconList.size()]);
        return icons;
    }
    
    public boolean matchesBlockId(final int blockId) {
        return Matches.blockId(blockId, this.matchBlocks);
    }
    
    public boolean matchesBlock(final int blockId, final int metadata) {
        return Matches.block(blockId, metadata, this.matchBlocks);
    }
    
    public boolean matchesIcon(final fuv icon) {
        return Matches.sprite(icon, this.matchTileIcons);
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.name, this.basePath, Config.arrayToString(this.matchBlocks), Config.arrayToString(this.matchTiles));
    }
    
    public boolean matchesBiome(final cnk biome) {
        return Matches.biome(biome, this.biomes);
    }
    
    private static int getMax(final int[] mds, int max) {
        if (mds == null) {
            return max;
        }
        for (int i = 0; i < mds.length; ++i) {
            final int md = mds[i];
            if (md > max) {
                max = md;
            }
        }
        return max;
    }
}
