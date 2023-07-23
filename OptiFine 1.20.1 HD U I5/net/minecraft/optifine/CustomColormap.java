// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Set;
import java.util.HashSet;
import net.optifine.config.Matches;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import net.optifine.util.TextureUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.util.BlockUtils;
import net.optifine.util.BiomeUtils;
import net.optifine.config.ConnectedParser;
import java.util.Properties;
import net.optifine.config.MatchBlock;

public class CustomColormap implements CustomColors.IColorizer
{
    public String name;
    public String basePath;
    private int format;
    private MatchBlock[] matchBlocks;
    private String source;
    private int color;
    private int yVariance;
    private int yOffset;
    private int width;
    private int height;
    private int[] colors;
    private float[][] colorsRgb;
    private static final int FORMAT_UNKNOWN = -1;
    private static final int FORMAT_VANILLA = 0;
    private static final int FORMAT_GRID = 1;
    private static final int FORMAT_FIXED = 2;
    public static final String FORMAT_VANILLA_STRING = "vanilla";
    public static final String FORMAT_GRID_STRING = "grid";
    public static final String FORMAT_FIXED_STRING = "fixed";
    public static final String[] FORMAT_STRINGS;
    public static final String KEY_FORMAT = "format";
    public static final String KEY_BLOCKS = "blocks";
    public static final String KEY_SOURCE = "source";
    public static final String KEY_COLOR = "color";
    public static final String KEY_Y_VARIANCE = "yVariance";
    public static final String KEY_Y_OFFSET = "yOffset";
    
    public CustomColormap(final Properties props, final String path, final int width, final int height, final String formatDefault) {
        this.name = null;
        this.basePath = null;
        this.format = -1;
        this.matchBlocks = null;
        this.source = null;
        this.color = -1;
        this.yVariance = 0;
        this.yOffset = 0;
        this.width = 0;
        this.height = 0;
        this.colors = null;
        this.colorsRgb = null;
        final ConnectedParser cp = new ConnectedParser("Colormap");
        this.name = cp.parseName(path);
        this.basePath = cp.parseBasePath(path);
        this.format = this.parseFormat(props.getProperty("format", formatDefault));
        this.matchBlocks = cp.parseMatchBlocks(props.getProperty("blocks"));
        this.source = parseTexture(props.getProperty("source"), path, this.basePath);
        this.color = ConnectedParser.parseColor(props.getProperty("color"), -1);
        this.yVariance = cp.parseInt(props.getProperty("yVariance"), 0);
        this.yOffset = cp.parseIntNeg(props.getProperty("yOffset"), 0);
        this.width = width;
        this.height = height;
    }
    
    private int parseFormat(String str) {
        if (str == null) {
            return 0;
        }
        str = str.trim();
        if (str.equals("vanilla")) {
            return 0;
        }
        if (str.equals("grid")) {
            return 1;
        }
        if (str.equals("fixed")) {
            return 2;
        }
        warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return -1;
    }
    
    public boolean isValid(final String path) {
        if (this.format == 0 || this.format == 1) {
            if (this.source == null) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
                return false;
            }
            this.readColors();
            if (this.colors == null) {
                return false;
            }
            if (this.color < 0) {
                if (this.format == 0) {
                    this.color = this.getColor(127, 127);
                }
                if (this.format == 1) {
                    this.color = this.getColorGrid(BiomeUtils.PLAINS, new gu(0, 64, 0));
                }
            }
        }
        else {
            if (this.format != 2) {
                return false;
            }
            if (this.color < 0) {
                this.color = 16777215;
            }
        }
        return true;
    }
    
    public boolean isValidMatchBlocks(final String path) {
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
            if (this.matchBlocks == null) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
                return false;
            }
        }
        return true;
    }
    
    private MatchBlock[] detectMatchBlocks() {
        final acq loc = new acq(this.name);
        if (jb.f.c(loc)) {
            final cpn block = (cpn)jb.f.a(loc);
            return new MatchBlock[] { new MatchBlock(BlockUtils.getBlockId(block)) };
        }
        final Pattern p = Pattern.compile("^block([0-9]+).*$");
        final Matcher m = p.matcher(this.name);
        if (m.matches()) {
            final String idStr = m.group(1);
            final int id = Config.parseInt(idStr, -1);
            if (id >= 0) {
                return new MatchBlock[] { new MatchBlock(id) };
            }
        }
        final ConnectedParser cp = new ConnectedParser("Colormap");
        final MatchBlock[] mbs = cp.parseMatchBlock(this.name);
        if (mbs != null) {
            return mbs;
        }
        return null;
    }
    
    private void readColors() {
        try {
            this.colors = null;
            if (this.source == null) {
                return;
            }
            final String imagePath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.source);
            final acq loc = new acq(imagePath);
            final InputStream is = Config.getResourceStream(loc);
            if (is == null) {
                return;
            }
            final BufferedImage img = TextureUtils.readBufferedImage(is);
            if (img == null) {
                return;
            }
            final int imgWidth = img.getWidth();
            final int imgHeight = img.getHeight();
            final boolean widthOk = this.width < 0 || this.width == imgWidth;
            final boolean heightOk = this.height < 0 || this.height == imgHeight;
            if (!widthOk || !heightOk) {
                dbg(invokedynamic(makeConcatWithConstants:(IIIILjava/lang/String;)Ljava/lang/String;, imgWidth, imgHeight, this.width, this.height, imagePath));
            }
            this.width = imgWidth;
            this.height = imgHeight;
            if (this.width <= 0 || this.height <= 0) {
                warn(invokedynamic(makeConcatWithConstants:(IILjava/lang/String;)Ljava/lang/String;, imgWidth, imgHeight, imagePath));
                return;
            }
            img.getRGB(0, 0, imgWidth, imgHeight, this.colors = new int[imgWidth * imgHeight], 0, imgWidth);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void dbg(final String str) {
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    private static void warn(final String str) {
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    private static String parseTexture(String texStr, final String path, final String basePath) {
        if (texStr != null) {
            texStr = texStr.trim();
            final String png = ".png";
            if (texStr.endsWith(png)) {
                texStr = texStr.substring(0, texStr.length() - png.length());
            }
            texStr = fixTextureName(texStr, basePath);
            return texStr;
        }
        String str = path;
        final int pos = str.lastIndexOf(47);
        if (pos >= 0) {
            str = str.substring(pos + 1);
        }
        final int pos2 = str.lastIndexOf(46);
        if (pos2 >= 0) {
            str = str.substring(0, pos2);
        }
        str = fixTextureName(str, basePath);
        return str;
    }
    
    private static String fixTextureName(String iconName, final String basePath) {
        iconName = TextureUtils.fixResourcePath(iconName, basePath);
        if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("optifine/")) {
            iconName = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, iconName);
        }
        if (iconName.endsWith(".png")) {
            iconName = iconName.substring(0, iconName.length() - 4);
        }
        final String pathBlocks = "textures/block/";
        if (iconName.startsWith(pathBlocks)) {
            iconName = iconName.substring(pathBlocks.length());
        }
        if (iconName.startsWith("/")) {
            iconName = iconName.substring(1);
        }
        return iconName;
    }
    
    public boolean matchesBlock(final dcb blockState) {
        return Matches.block(blockState, this.matchBlocks);
    }
    
    public int getColorRandom() {
        if (this.format == 2) {
            return this.color;
        }
        final int index = CustomColors.random.nextInt(this.colors.length);
        return this.colors[index];
    }
    
    public int getColor(int index) {
        index = Config.limit(index, 0, this.colors.length - 1);
        return this.colors[index] & 0xFFFFFF;
    }
    
    public int getColor(int cx, int cy) {
        cx = Config.limit(cx, 0, this.width - 1);
        cy = Config.limit(cy, 0, this.height - 1);
        return this.colors[cy * this.width + cx] & 0xFFFFFF;
    }
    
    public float[][] getColorsRgb() {
        if (this.colorsRgb == null) {
            this.colorsRgb = toRgb(this.colors);
        }
        return this.colorsRgb;
    }
    
    public int getColor(final dcb blockState, final clp blockAccess, final gu blockPos) {
        return this.getColor(blockAccess, blockPos);
    }
    
    public int getColor(final clp blockAccess, final gu blockPos) {
        final cnk biome = CustomColors.getColorBiome(blockAccess, blockPos);
        return this.getColor(biome, blockPos);
    }
    
    @Override
    public boolean isColorConstant() {
        return this.format == 2;
    }
    
    public int getColor(final cnk biome, final gu blockPos) {
        if (this.format == 0) {
            return this.getColorVanilla(biome, blockPos);
        }
        if (this.format == 1) {
            return this.getColorGrid(biome, blockPos);
        }
        return this.color;
    }
    
    public int getColorSmooth(final clp blockAccess, final double x, final double y, final double z, final int radius) {
        if (this.format == 2) {
            return this.color;
        }
        final int x2 = apa.a(x);
        final int y2 = apa.a(y);
        final int z2 = apa.a(z);
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int count = 0;
        final BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        for (int ix = x2 - radius; ix <= x2 + radius; ++ix) {
            for (int iz = z2 - radius; iz <= z2 + radius; ++iz) {
                blockPosM.setXyz(ix, y2, iz);
                final int col = this.getColor(blockAccess, (gu)blockPosM);
                sumRed += (col >> 16 & 0xFF);
                sumGreen += (col >> 8 & 0xFF);
                sumBlue += (col & 0xFF);
                ++count;
            }
        }
        final int r = sumRed / count;
        final int g = sumGreen / count;
        final int b = sumBlue / count;
        return r << 16 | g << 8 | b;
    }
    
    private int getColorVanilla(final cnk biome, final gu blockPos) {
        final double temperature = apa.a(biome.g(), 0.0f, 1.0f);
        double rainfall = apa.a(BiomeUtils.getDownfall(biome), 0.0f, 1.0f);
        rainfall *= temperature;
        final int cx = (int)((1.0 - temperature) * (this.width - 1));
        final int cy = (int)((1.0 - rainfall) * (this.height - 1));
        return this.getColor(cx, cy);
    }
    
    private int getColorGrid(final cnk biome, final gu blockPos) {
        final int cx;
        final int biomeId = cx = BiomeUtils.getId(biome);
        int cy = blockPos.v() - this.yOffset;
        if (this.yVariance > 0) {
            final int seed = blockPos.u() << 16 + blockPos.w();
            final int rand = Config.intHash(seed);
            final int range = this.yVariance * 2 + 1;
            final int diff = (rand & 0xFF) % range - this.yVariance;
            cy += diff;
        }
        return this.getColor(cx, cy);
    }
    
    public int getLength() {
        if (this.format == 2) {
            return 1;
        }
        return this.colors.length;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    private static float[][] toRgb(final int[] cols) {
        final float[][] colsRgb = new float[cols.length][3];
        for (int i = 0; i < cols.length; ++i) {
            final int col = cols[i];
            final float rf = (col >> 16 & 0xFF) / 255.0f;
            final float gf = (col >> 8 & 0xFF) / 255.0f;
            final float bf = (col & 0xFF) / 255.0f;
            final float[] colRgb = colsRgb[i];
            colRgb[0] = rf;
            colRgb[1] = gf;
            colRgb[2] = bf;
        }
        return colsRgb;
    }
    
    public void addMatchBlock(final MatchBlock mb) {
        if (this.matchBlocks == null) {
            this.matchBlocks = new MatchBlock[0];
        }
        this.matchBlocks = (MatchBlock[])Config.addObjectToArray(this.matchBlocks, mb);
    }
    
    public void addMatchBlock(final int blockId, final int metadata) {
        final MatchBlock mb = this.getMatchBlock(blockId);
        if (mb != null) {
            if (metadata >= 0) {
                mb.addMetadata(metadata);
            }
            return;
        }
        this.addMatchBlock(new MatchBlock(blockId, metadata));
    }
    
    private MatchBlock getMatchBlock(final int blockId) {
        if (this.matchBlocks == null) {
            return null;
        }
        for (int i = 0; i < this.matchBlocks.length; ++i) {
            final MatchBlock mb = this.matchBlocks[i];
            if (mb.getBlockId() == blockId) {
                return mb;
            }
        }
        return null;
    }
    
    public int[] getMatchBlockIds() {
        if (this.matchBlocks == null) {
            return null;
        }
        final Set setIds = new HashSet();
        for (int i = 0; i < this.matchBlocks.length; ++i) {
            final MatchBlock mb = this.matchBlocks[i];
            if (mb.getBlockId() >= 0) {
                setIds.add(mb.getBlockId());
            }
        }
        final Integer[] ints = setIds.toArray(new Integer[setIds.size()]);
        final int[] ids = new int[ints.length];
        for (int j = 0; j < ints.length; ++j) {
            ids[j] = ints[j];
        }
        return ids;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.basePath, this.name, Config.arrayToString(this.matchBlocks), this.source);
    }
    
    static {
        FORMAT_STRINGS = new String[] { "vanilla", "grid", "fixed" };
    }
}
