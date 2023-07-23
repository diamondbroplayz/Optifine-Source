// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import net.optifine.Config;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderParser
{
    public static Pattern PATTERN_UNIFORM;
    public static Pattern PATTERN_ATTRIBUTE;
    public static Pattern PATTERN_IN;
    public static Pattern PATTERN_CONST_INT;
    public static Pattern PATTERN_CONST_IVEC3;
    public static Pattern PATTERN_CONST_FLOAT;
    public static Pattern PATTERN_CONST_VEC2;
    public static Pattern PATTERN_CONST_VEC4;
    public static Pattern PATTERN_CONST_BOOL;
    public static Pattern PATTERN_PROPERTY;
    public static Pattern PATTERN_EXTENSION;
    public static Pattern PATTERN_LAYOUT;
    public static Pattern PATTERN_DRAW_BUFFERS;
    public static Pattern PATTERN_RENDER_TARGETS;
    
    public static ShaderLine parseLine(final String line, final ShaderType shaderType) {
        final Matcher mu = ShaderParser.PATTERN_UNIFORM.matcher(line);
        if (mu.matches()) {
            return new ShaderLine(ShaderLine.Type.UNIFORM, mu.group(1), "", line);
        }
        final Matcher ma = ShaderParser.PATTERN_ATTRIBUTE.matcher(line);
        if (ma.matches()) {
            return new ShaderLine(ShaderLine.Type.ATTRIBUTE, ma.group(1), "", line);
        }
        if (shaderType == ShaderType.VERTEX) {
            final Matcher mi = ShaderParser.PATTERN_IN.matcher(line);
            if (mi.matches()) {
                return new ShaderLine(ShaderLine.Type.ATTRIBUTE, mi.group(1), "", line);
            }
        }
        final Matcher mci = ShaderParser.PATTERN_CONST_INT.matcher(line);
        if (mci.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_INT, mci.group(1), mci.group(2), line);
        }
        final Matcher mciv3 = ShaderParser.PATTERN_CONST_IVEC3.matcher(line);
        if (mciv3.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_IVEC3, mciv3.group(1), mciv3.group(2), line);
        }
        final Matcher mcf = ShaderParser.PATTERN_CONST_FLOAT.matcher(line);
        if (mcf.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_FLOAT, mcf.group(1), mcf.group(2), line);
        }
        final Matcher mcv2 = ShaderParser.PATTERN_CONST_VEC2.matcher(line);
        if (mcv2.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_VEC2, mcv2.group(1), mcv2.group(2), line);
        }
        final Matcher mcv3 = ShaderParser.PATTERN_CONST_VEC4.matcher(line);
        if (mcv3.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_VEC4, mcv3.group(1), mcv3.group(2), line);
        }
        final Matcher mcb = ShaderParser.PATTERN_CONST_BOOL.matcher(line);
        if (mcb.matches()) {
            return new ShaderLine(ShaderLine.Type.CONST_BOOL, mcb.group(1), mcb.group(2), line);
        }
        final Matcher mc = ShaderParser.PATTERN_PROPERTY.matcher(line);
        if (mc.matches()) {
            return new ShaderLine(ShaderLine.Type.PROPERTY, mc.group(2), mc.group(3), line);
        }
        final Matcher mce = ShaderParser.PATTERN_EXTENSION.matcher(line);
        if (mce.matches()) {
            return new ShaderLine(ShaderLine.Type.EXTENSION, mce.group(1), mce.group(2), line);
        }
        final Matcher ml = ShaderParser.PATTERN_LAYOUT.matcher(line);
        if (ml.matches()) {
            return new ShaderLine(ShaderLine.Type.LAYOUT, ml.group(2), ml.group(1), line);
        }
        return null;
    }
    
    public static int getIndex(final String uniform, final String prefix, final int minIndex, final int maxIndex) {
        if (!uniform.startsWith(prefix)) {
            return -1;
        }
        final String suffix = uniform.substring(prefix.length());
        final int index = Config.parseInt(suffix, -1);
        if (index < minIndex || index > maxIndex) {
            return -1;
        }
        return index;
    }
    
    public static int getShadowDepthIndex(final String uniform) {
        switch (uniform) {
            case "shadow": {
                return 0;
            }
            case "watershadow": {
                return 1;
            }
            default: {
                return getIndex(uniform, "shadowtex", 0, 1);
            }
        }
    }
    
    public static int getShadowColorIndex(final String uniform) {
        switch (uniform) {
            case "shadowcolor": {
                return 0;
            }
            default: {
                return getIndex(uniform, "shadowcolor", 0, 1);
            }
        }
    }
    
    public static int getShadowColorImageIndex(final String uniform) {
        return getIndex(uniform, "shadowcolorimg", 0, 1);
    }
    
    public static int getDepthIndex(final String uniform) {
        return getIndex(uniform, "depthtex", 0, 2);
    }
    
    public static int getColorIndex(final String uniform) {
        final int gauxIndex = getIndex(uniform, "gaux", 1, 4);
        if (gauxIndex > 0) {
            return gauxIndex + 3;
        }
        return getIndex(uniform, "colortex", 0, 15);
    }
    
    public static int getColorImageIndex(final String uniform) {
        return getIndex(uniform, "colorimg", 0, 15);
    }
    
    public static String[] parseDrawBuffers(String str) {
        if (!ShaderParser.PATTERN_DRAW_BUFFERS.matcher(str).matches()) {
            return null;
        }
        str = str.trim();
        final String[] strs = new String[str.length()];
        for (int i = 0; i < strs.length; ++i) {
            strs[i] = String.valueOf(str.charAt(i));
        }
        return strs;
    }
    
    public static String[] parseRenderTargets(String str) {
        if (!ShaderParser.PATTERN_RENDER_TARGETS.matcher(str).matches()) {
            return null;
        }
        str = str.trim();
        final String[] strs = Config.tokenize(str, ",");
        return strs;
    }
    
    public static hz parseLocalSize(final String value) {
        int x = 1;
        int y = 1;
        int z = 1;
        final String[] parts = Config.tokenize(value, ",");
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            final String[] tokens = Config.tokenize(part, "=");
            if (tokens.length == 2) {
                final String name = tokens[0].trim();
                final String valStr = tokens[1].trim();
                final int val = Config.parseInt(valStr, -1);
                if (val < 1) {
                    return null;
                }
                if (name.equals("local_size_x")) {
                    x = val;
                }
                if (name.equals("local_size_y")) {
                    y = val;
                }
                if (name.equals("local_size_z")) {
                    z = val;
                }
            }
        }
        if (x == 1 && y == 1 && z == 1) {
            return null;
        }
        return new hz(x, y, z);
    }
    
    static {
        ShaderParser.PATTERN_UNIFORM = Pattern.compile("[\\w\\s(,=)]*uniform\\s+\\w+\\s+(\\w+).*");
        ShaderParser.PATTERN_ATTRIBUTE = Pattern.compile("\\s*attribute\\s+\\w+\\s+(\\w+).*");
        ShaderParser.PATTERN_IN = Pattern.compile("\\s*in\\s+\\w+\\s+(\\w+).*");
        ShaderParser.PATTERN_CONST_INT = Pattern.compile("\\s*const\\s+int\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
        ShaderParser.PATTERN_CONST_IVEC3 = Pattern.compile("\\s*const\\s+ivec3\\s+(\\w+)\\s*=\\s*(.+)\\s*;.*");
        ShaderParser.PATTERN_CONST_FLOAT = Pattern.compile("\\s*const\\s+float\\s+(\\w+)\\s*=\\s*([-+.\\w]+)\\s*;.*");
        ShaderParser.PATTERN_CONST_VEC2 = Pattern.compile("\\s*const\\s+vec2\\s+(\\w+)\\s*=\\s*(.+)\\s*;.*");
        ShaderParser.PATTERN_CONST_VEC4 = Pattern.compile("\\s*const\\s+vec4\\s+(\\w+)\\s*=\\s*(.+)\\s*;.*");
        ShaderParser.PATTERN_CONST_BOOL = Pattern.compile("\\s*const\\s+bool\\s+(\\w+)\\s*=\\s*(\\w+)\\s*;.*");
        ShaderParser.PATTERN_PROPERTY = Pattern.compile("\\s*(/\\*|//)?\\s*([A-Z]+):\\s*([\\w.,]+)\\s*(\\*/.*|\\s*)");
        ShaderParser.PATTERN_EXTENSION = Pattern.compile("\\s*#\\s*extension\\s+(\\w+)\\s*:\\s*(\\w+).*");
        ShaderParser.PATTERN_LAYOUT = Pattern.compile("\\s*layout\\s*\\((.*)\\)\\s*(\\w+).*");
        ShaderParser.PATTERN_DRAW_BUFFERS = Pattern.compile("[0-9N]+");
        ShaderParser.PATTERN_RENDER_TARGETS = Pattern.compile("[0-9N,]+");
    }
}
