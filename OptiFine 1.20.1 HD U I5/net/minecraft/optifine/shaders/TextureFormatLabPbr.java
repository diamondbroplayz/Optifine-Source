// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.texture.ColorBlenderLinear;
import net.optifine.texture.ColorBlenderLabPbrSpecular;
import net.optifine.texture.IColorBlender;

public class TextureFormatLabPbr implements ITextureFormat
{
    private String version;
    
    public TextureFormatLabPbr(final String ver) {
        this.version = ver;
    }
    
    @Override
    public String getMacroName() {
        return "LAB_PBR";
    }
    
    @Override
    public String getMacroVersion() {
        if (this.version == null) {
            return null;
        }
        return this.version.replace('.', '_');
    }
    
    @Override
    public IColorBlender getColorBlender(final ShadersTextureType typeIn) {
        if (typeIn == ShadersTextureType.SPECULAR) {
            return new ColorBlenderLabPbrSpecular();
        }
        return new ColorBlenderLinear();
    }
    
    @Override
    public boolean isTextureBlend(final ShadersTextureType typeIn) {
        return typeIn != ShadersTextureType.SPECULAR;
    }
}
