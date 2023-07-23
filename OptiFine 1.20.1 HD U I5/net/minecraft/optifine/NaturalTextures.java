// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.io.InputStream;
import java.io.FileNotFoundException;
import net.optifine.util.TextureUtils;
import java.util.ArrayList;

public class NaturalTextures
{
    private static NaturalProperties[] propertiesByIndex;
    
    public static void update() {
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
        if (!Config.isNaturalTextures()) {
            return;
        }
        final String fileName = "optifine/natural.properties";
        try {
            final acq loc = new acq(fileName);
            if (!Config.hasResource(loc)) {
                Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
                return;
            }
            final boolean defaultConfig = Config.isFromDefaultResourcePack(loc);
            final InputStream in = Config.getResourceStream(loc);
            final ArrayList list = new ArrayList(256);
            final String configStr = Config.readInputStream(in);
            in.close();
            final String[] configLines = Config.tokenize(configStr, "\n\r");
            if (defaultConfig) {
                Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
                Config.dbg("Natural Textures: Valid only for textures from default resource pack");
            }
            else {
                Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
            }
            int countTextures = 0;
            final fuu textureMapBlocks = TextureUtils.getTextureMapBlocks();
            for (int i = 0; i < configLines.length; ++i) {
                final String line = configLines[i].trim();
                if (!line.startsWith("#")) {
                    final String[] strs = Config.tokenize(line, "=");
                    if (strs.length != 2) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, fileName, line));
                    }
                    else {
                        final String key = strs[0].trim();
                        final String type = strs[1].trim();
                        final String texName = key;
                        final fuv ts = textureMapBlocks.getUploadedSprite(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, texName));
                        if (ts == null) {
                            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, fileName, line));
                        }
                        else {
                            final int tileNum = ts.getIndexInMap();
                            if (tileNum < 0) {
                                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, fileName, line));
                            }
                            else {
                                if (defaultConfig && !Config.isFromDefaultResourcePack(new acq(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, texName)))) {
                                    return;
                                }
                                final NaturalProperties props = new NaturalProperties(type);
                                if (props.isValid()) {
                                    while (list.size() <= tileNum) {
                                        list.add(null);
                                    }
                                    list.set(tileNum, props);
                                    ++countTextures;
                                }
                            }
                        }
                    }
                }
            }
            NaturalTextures.propertiesByIndex = list.toArray(new NaturalProperties[list.size()]);
            if (countTextures > 0) {
                Config.dbg(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, countTextures));
            }
        }
        catch (FileNotFoundException e2) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static fkr getNaturalTexture(final dcb stateIn, final gu blockPosIn, final fkr quad) {
        if (stateIn.b() instanceof cyj) {
            return quad;
        }
        final fuv sprite = quad.a();
        if (sprite == null) {
            return quad;
        }
        final NaturalProperties nps = getNaturalProperties(sprite);
        if (nps == null) {
            return quad;
        }
        final int side = ConnectedTextures.getSide(quad.e());
        final int rand = Config.getRandom(blockPosIn, side);
        int rotate = 0;
        boolean flipU = false;
        if (nps.rotation > 1) {
            rotate = (rand & 0x3);
        }
        if (nps.rotation == 2) {
            rotate = rotate / 2 * 2;
        }
        if (nps.flip) {
            flipU = ((rand & 0x4) != 0x0);
        }
        return nps.getQuad(quad, rotate, flipU);
    }
    
    public static NaturalProperties getNaturalProperties(final fuv icon) {
        if (!(icon instanceof fuv)) {
            return null;
        }
        final fuv ts = icon;
        final int tileNum = ts.getIndexInMap();
        if (tileNum < 0 || tileNum >= NaturalTextures.propertiesByIndex.length) {
            return null;
        }
        final NaturalProperties props = NaturalTextures.propertiesByIndex[tileNum];
        return props;
    }
    
    static {
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
    }
}
