// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

public enum ShaderType
{
    VERTEX, 
    FRAGMENT, 
    GEOMETRY, 
    COMPUTE;
    
    private static /* synthetic */ ShaderType[] $values() {
        return new ShaderType[] { ShaderType.VERTEX, ShaderType.FRAGMENT, ShaderType.GEOMETRY, ShaderType.COMPUTE };
    }
    
    static {
        $VALUES = $values();
    }
}
