// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.concurrent.Executor;
import net.optifine.reflect.Reflector;
import java.util.concurrent.CompletableFuture;
import java.util.Iterator;
import java.util.Set;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.optifine.Config;
import java.util.Properties;

public class FontUtils
{
    public static Properties readFontProperties(final acq locationFontTexture) {
        final String fontFileName = locationFontTexture.a();
        final Properties props = new PropertiesOrdered();
        final String suffix = ".png";
        if (!fontFileName.endsWith(suffix)) {
            return props;
        }
        final String fileName = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fontFileName.substring(0, fontFileName.length() - suffix.length()));
        try {
            final acq locProp = new acq(locationFontTexture.b(), fileName);
            final InputStream in = Config.getResourceStream(Config.getResourceManager(), locProp);
            if (in == null) {
                return props;
            }
            Config.log(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
            props.load(in);
            in.close();
        }
        catch (FileNotFoundException ex) {}
        catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
    
    public static Int2ObjectMap<Float> readCustomCharWidths(final Properties props) {
        final Int2ObjectMap<Float> map = (Int2ObjectMap<Float>)new Int2ObjectOpenHashMap();
        final Set keySet = props.keySet();
        for (final String key : keySet) {
            final String prefix = "width.";
            if (key.startsWith(prefix)) {
                final String numStr = key.substring(prefix.length());
                final int num = Config.parseInt(numStr, -1);
                if (num < 0) {
                    continue;
                }
                final String value = props.getProperty(key);
                final float width = Config.parseFloat(value, -1.0f);
                if (width < 0.0f) {
                    continue;
                }
                final char ch = (char)num;
                map.put((int)ch, (Object)new Float(width));
            }
        }
        return map;
    }
    
    public static float readFloat(final Properties props, final String key, final float defOffset) {
        final String str = props.getProperty(key);
        if (str == null) {
            return defOffset;
        }
        final float offset = Config.parseFloat(str, Float.MIN_VALUE);
        if (offset == Float.MIN_VALUE) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, str));
            return defOffset;
        }
        return offset;
    }
    
    public static boolean readBoolean(final Properties props, final String key, final boolean defVal) {
        final String str = props.getProperty(key);
        if (str == null) {
            return defVal;
        }
        final String strLow = str.toLowerCase().trim();
        if (strLow.equals("true") || strLow.equals("on")) {
            return true;
        }
        if (strLow.equals("false") || strLow.equals("off")) {
            return false;
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, str));
        return defVal;
    }
    
    public static acq getHdFontLocation(final acq fontLoc) {
        if (!Config.isCustomFonts()) {
            return fontLoc;
        }
        if (fontLoc == null) {
            return fontLoc;
        }
        if (!Config.isMinecraftThread()) {
            return fontLoc;
        }
        String fontName = fontLoc.a();
        final String texturesStr = "textures/";
        final String optifineStr = "optifine/";
        if (!fontName.startsWith(texturesStr)) {
            return fontLoc;
        }
        fontName = fontName.substring(texturesStr.length());
        fontName = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, optifineStr, fontName);
        final acq fontLocHD = new acq(fontLoc.b(), fontName);
        if (Config.hasResource(Config.getResourceManager(), fontLocHD)) {
            return fontLocHD;
        }
        return fontLoc;
    }
    
    public static void reloadFonts() {
        final akr.a stage = (akr.a)new akr.a() {
            public <T> CompletableFuture<T> a(final T x) {
                return CompletableFuture.completedFuture(x);
            }
        };
        final Executor ex = ac.f();
        final enn mc = enn.N();
        final erm frm = (erm)Reflector.getFieldValue(mc, Reflector.Minecraft_fontResourceManager);
        if (frm == null) {
            return;
        }
        frm.a(stage, Config.getResourceManager(), (ban)bak.a, (ban)bak.a, ex, (Executor)mc);
    }
}
