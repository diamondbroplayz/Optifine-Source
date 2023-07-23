// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.resource;

public enum VanillaResourceType implements IResourceType
{
    MODELS, 
    TEXTURES, 
    SOUNDS, 
    LANGUAGES, 
    SHADERS;
    
    private static /* synthetic */ VanillaResourceType[] $values() {
        return new VanillaResourceType[] { VanillaResourceType.MODELS, VanillaResourceType.TEXTURES, VanillaResourceType.SOUNDS, VanillaResourceType.LANGUAGES, VanillaResourceType.SHADERS };
    }
    
    static {
        $VALUES = $values();
    }
}
