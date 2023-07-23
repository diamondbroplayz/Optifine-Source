// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

public enum ShadersTextureType
{
    NORMAL("_n"), 
    SPECULAR("_s");
    
    private String suffix;
    
    private ShadersTextureType(final String suffixIn) {
        this.suffix = suffixIn;
    }
    
    public String getSuffix() {
        return this.suffix;
    }
    
    private static /* synthetic */ ShadersTextureType[] $values() {
        return new ShadersTextureType[] { ShadersTextureType.NORMAL, ShadersTextureType.SPECULAR };
    }
    
    static {
        $VALUES = $values();
    }
}
