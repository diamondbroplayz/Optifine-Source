// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

public enum RenderStage
{
    NONE, 
    SKY, 
    SUNSET, 
    CUSTOM_SKY, 
    SUN, 
    MOON, 
    STARS, 
    VOID, 
    TERRAIN_SOLID, 
    TERRAIN_CUTOUT_MIPPED, 
    TERRAIN_CUTOUT, 
    ENTITIES, 
    BLOCK_ENTITIES, 
    DESTROY, 
    OUTLINE, 
    DEBUG, 
    HAND_SOLID, 
    TERRAIN_TRANSLUCENT, 
    TRIPWIRE, 
    PARTICLES, 
    CLOUDS, 
    RAIN_SNOW, 
    WORLD_BORDER, 
    HAND_TRANSLUCENT;
    
    private static /* synthetic */ RenderStage[] $values() {
        return new RenderStage[] { RenderStage.NONE, RenderStage.SKY, RenderStage.SUNSET, RenderStage.CUSTOM_SKY, RenderStage.SUN, RenderStage.MOON, RenderStage.STARS, RenderStage.VOID, RenderStage.TERRAIN_SOLID, RenderStage.TERRAIN_CUTOUT_MIPPED, RenderStage.TERRAIN_CUTOUT, RenderStage.ENTITIES, RenderStage.BLOCK_ENTITIES, RenderStage.DESTROY, RenderStage.OUTLINE, RenderStage.DEBUG, RenderStage.HAND_SOLID, RenderStage.TERRAIN_TRANSLUCENT, RenderStage.TRIPWIRE, RenderStage.PARTICLES, RenderStage.CLOUDS, RenderStage.RAIN_SNOW, RenderStage.WORLD_BORDER, RenderStage.HAND_TRANSLUCENT };
    }
    
    static {
        $VALUES = $values();
    }
}
