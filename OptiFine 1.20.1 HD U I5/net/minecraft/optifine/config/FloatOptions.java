// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import net.optifine.Config;
import net.optifine.Lang;

public class FloatOptions
{
    public static sw getTextComponent(final enq option, final double val) {
        final enr settings = enn.N().m;
        final String s = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fvz.a(option.getResourceKey(), new Object[0]));
        if (option == settings.RENDER_DISTANCE) {
            return enr.genericValueLabel(option.getResourceKey(), "options.chunks", (int)val);
        }
        if (option == settings.MIPMAP_LEVELS) {
            if (val >= 4.0) {
                return enr.genericValueLabel(option.getResourceKey(), "of.general.max");
            }
            return (sw)((val == 0.0) ? sv.a(option.getCaption(), false) : enr.genericValueLabel(option.getResourceKey(), (int)val));
        }
        else {
            if (option == settings.BIOME_BLEND_RADIUS) {
                final int i = (int)val * 2 + 1;
                return enr.genericValueLabel(option.getResourceKey(), invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i));
            }
            final String text = getText(option, val);
            if (text != null) {
                return (sw)sw.b(text);
            }
            return null;
        }
    }
    
    public static String getText(final enq option, final double val) {
        final String s = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fvz.a(option.getResourceKey(), new Object[0]));
        if (option == Option.AO_LEVEL) {
            if (val == 0.0) {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, s, fvz.a("options.off", new Object[0]));
            }
            return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, s, (int)(val * 100.0));
        }
        else if (option == Option.MIPMAP_TYPE) {
            final int valInt = (int)val;
            switch (valInt) {
                case 0: {
                    return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, s, Lang.get("of.options.mipmap.nearest"));
                }
                case 1: {
                    return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, s, Lang.get("of.options.mipmap.linear"));
                }
                case 2: {
                    return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, s, Lang.get("of.options.mipmap.bilinear"));
                }
                case 3: {
                    return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, s, Lang.get("of.options.mipmap.trilinear"));
                }
                default: {
                    return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s);
                }
            }
        }
        else if (option == Option.AA_LEVEL) {
            final int ofAaLevel = (int)val;
            String suffix = "";
            if (ofAaLevel != Config.getAntialiasingLevel()) {
                suffix = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Lang.get("of.general.restart"));
            }
            if (ofAaLevel == 0) {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, s, Lang.getOff(), suffix);
            }
            return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;, s, ofAaLevel, suffix);
        }
        else {
            if (option != Option.AF_LEVEL) {
                return null;
            }
            final int ofAfLevel = (int)val;
            if (ofAfLevel == 1) {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, s, Lang.getOff());
            }
            return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, s, ofAfLevel);
        }
    }
    
    public static boolean supportAdjusting(final enq option) {
        final sw text = getTextComponent(option, 0.0);
        return text != null;
    }
}
