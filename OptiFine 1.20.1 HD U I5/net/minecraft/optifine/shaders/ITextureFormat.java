// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.config.ConfigUtils;
import net.optifine.Config;
import net.optifine.texture.IColorBlender;

public interface ITextureFormat
{
    IColorBlender getColorBlender(final ShadersTextureType p0);
    
    boolean isTextureBlend(final ShadersTextureType p0);
    
    String getMacroName();
    
    String getMacroVersion();
    
    default ITextureFormat readConfiguration() {
        if (!Config.isShaders()) {
            return null;
        }
        final String formatStr = ConfigUtils.readString("optifine/texture.properties", "format");
        if (formatStr == null) {
            return null;
        }
        final String[] parts = Config.tokenize(formatStr, "/");
        final String name = parts[0];
        final String ver = (parts.length > 1) ? parts[1] : null;
        if (name.equals("lab-pbr")) {
            return new TextureFormatLabPbr(ver);
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, formatStr));
        return null;
    }
}
