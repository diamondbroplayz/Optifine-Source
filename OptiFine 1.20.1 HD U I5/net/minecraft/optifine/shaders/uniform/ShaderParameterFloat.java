// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import net.optifine.util.BiomeUtils;
import net.optifine.shaders.Shaders;
import net.optifine.Config;

public enum ShaderParameterFloat
{
    BIOME("biome"), 
    BIOME_CATEGORY("biome_category"), 
    BIOME_PRECIPITATION("biome_precipitation"), 
    TEMPERATURE("temperature"), 
    RAINFALL("rainfall"), 
    HELD_ITEM_ID((ShaderUniformBase)Shaders.uniform_heldItemId), 
    HELD_BLOCK_LIGHT_VALUE((ShaderUniformBase)Shaders.uniform_heldBlockLightValue), 
    HELD_ITEM_ID2((ShaderUniformBase)Shaders.uniform_heldItemId2), 
    HELD_BLOCK_LIGHT_VALUE2((ShaderUniformBase)Shaders.uniform_heldBlockLightValue2), 
    WORLD_TIME((ShaderUniformBase)Shaders.uniform_worldTime), 
    WORLD_DAY((ShaderUniformBase)Shaders.uniform_worldDay), 
    MOON_PHASE((ShaderUniformBase)Shaders.uniform_moonPhase), 
    FRAME_COUNTER((ShaderUniformBase)Shaders.uniform_frameCounter), 
    FRAME_TIME((ShaderUniformBase)Shaders.uniform_frameTime), 
    FRAME_TIME_COUNTER((ShaderUniformBase)Shaders.uniform_frameTimeCounter), 
    SUN_ANGLE((ShaderUniformBase)Shaders.uniform_sunAngle), 
    SHADOW_ANGLE((ShaderUniformBase)Shaders.uniform_shadowAngle), 
    RAIN_STRENGTH((ShaderUniformBase)Shaders.uniform_rainStrength), 
    ASPECT_RATIO((ShaderUniformBase)Shaders.uniform_aspectRatio), 
    VIEW_WIDTH((ShaderUniformBase)Shaders.uniform_viewWidth), 
    VIEW_HEIGHT((ShaderUniformBase)Shaders.uniform_viewHeight), 
    NEAR((ShaderUniformBase)Shaders.uniform_near), 
    FAR((ShaderUniformBase)Shaders.uniform_far), 
    WETNESS((ShaderUniformBase)Shaders.uniform_wetness), 
    EYE_ALTITUDE((ShaderUniformBase)Shaders.uniform_eyeAltitude), 
    EYE_BRIGHTNESS((ShaderUniformBase)Shaders.uniform_eyeBrightness, new String[] { "x", "y" }), 
    TERRAIN_TEXTURE_SIZE((ShaderUniformBase)Shaders.uniform_terrainTextureSize, new String[] { "x", "y" }), 
    TERRRAIN_ICON_SIZE((ShaderUniformBase)Shaders.uniform_terrainIconSize), 
    IS_EYE_IN_WATER((ShaderUniformBase)Shaders.uniform_isEyeInWater), 
    NIGHT_VISION((ShaderUniformBase)Shaders.uniform_nightVision), 
    BLINDNESS((ShaderUniformBase)Shaders.uniform_blindness), 
    SCREEN_BRIGHTNESS((ShaderUniformBase)Shaders.uniform_screenBrightness), 
    HIDE_GUI((ShaderUniformBase)Shaders.uniform_hideGUI), 
    CENTER_DEPT_SMOOTH((ShaderUniformBase)Shaders.uniform_centerDepthSmooth), 
    ATLAS_SIZE((ShaderUniformBase)Shaders.uniform_atlasSize, new String[] { "x", "y" }), 
    PLAYER_MOOD((ShaderUniformBase)Shaders.uniform_playerMood), 
    CAMERA_POSITION((ShaderUniformBase)Shaders.uniform_cameraPosition, new String[] { "x", "y", "z" }), 
    PREVIOUS_CAMERA_POSITION((ShaderUniformBase)Shaders.uniform_previousCameraPosition, new String[] { "x", "y", "z" }), 
    SUN_POSITION((ShaderUniformBase)Shaders.uniform_sunPosition, new String[] { "x", "y", "z" }), 
    MOON_POSITION((ShaderUniformBase)Shaders.uniform_moonPosition, new String[] { "x", "y", "z" }), 
    SHADOW_LIGHT_POSITION((ShaderUniformBase)Shaders.uniform_shadowLightPosition, new String[] { "x", "y", "z" }), 
    UP_POSITION((ShaderUniformBase)Shaders.uniform_upPosition, new String[] { "x", "y", "z" }), 
    SKY_COLOR((ShaderUniformBase)Shaders.uniform_skyColor, new String[] { "r", "g", "b" }), 
    GBUFFER_PROJECTION((ShaderUniformBase)Shaders.uniform_gbufferProjection, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }), 
    GBUFFER_PROJECTION_INVERSE((ShaderUniformBase)Shaders.uniform_gbufferProjectionInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }), 
    GBUFFER_PREVIOUS_PROJECTION((ShaderUniformBase)Shaders.uniform_gbufferPreviousProjection, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }), 
    GBUFFER_MODEL_VIEW((ShaderUniformBase)Shaders.uniform_gbufferModelView, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }), 
    GBUFFER_MODEL_VIEW_INVERSE((ShaderUniformBase)Shaders.uniform_gbufferModelViewInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }), 
    GBUFFER_PREVIOUS_MODEL_VIEW((ShaderUniformBase)Shaders.uniform_gbufferPreviousModelView, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }), 
    SHADOW_PROJECTION((ShaderUniformBase)Shaders.uniform_shadowProjection, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }), 
    SHADOW_PROJECTION_INVERSE((ShaderUniformBase)Shaders.uniform_shadowProjectionInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }), 
    SHADOW_MODEL_VIEW((ShaderUniformBase)Shaders.uniform_shadowModelView, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" }), 
    SHADOW_MODEL_VIEW_INVERSE((ShaderUniformBase)Shaders.uniform_shadowModelViewInverse, new String[] { "0", "1", "2", "3" }, new String[] { "0", "1", "2", "3" });
    
    private String name;
    private ShaderUniformBase uniform;
    private String[] indexNames1;
    private String[] indexNames2;
    
    private ShaderParameterFloat(final String name) {
        this.name = name;
    }
    
    private ShaderParameterFloat(final ShaderUniformBase uniform) {
        this.name = uniform.getName();
        this.uniform = uniform;
        if (!instanceOf(uniform, ShaderUniform1f.class, ShaderUniform1i.class)) {
            throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/ShaderParameterFloat;Ljava/lang/String;)Ljava/lang/String;, this, uniform.getClass().getName()));
        }
    }
    
    private ShaderParameterFloat(final ShaderUniformBase uniform, final String[] indexNames1) {
        this.name = uniform.getName();
        this.uniform = uniform;
        this.indexNames1 = indexNames1;
        if (!instanceOf(uniform, ShaderUniform2i.class, ShaderUniform2f.class, ShaderUniform3f.class, ShaderUniform4f.class)) {
            throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/ShaderParameterFloat;Ljava/lang/String;)Ljava/lang/String;, this, uniform.getClass().getName()));
        }
    }
    
    private ShaderParameterFloat(final ShaderUniformBase uniform, final String[] indexNames1, final String[] indexNames2) {
        this.name = uniform.getName();
        this.uniform = uniform;
        this.indexNames1 = indexNames1;
        this.indexNames2 = indexNames2;
        if (!instanceOf(uniform, ShaderUniformM4.class)) {
            throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/ShaderParameterFloat;Ljava/lang/String;)Ljava/lang/String;, this, uniform.getClass().getName()));
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public ShaderUniformBase getUniform() {
        return this.uniform;
    }
    
    public String[] getIndexNames1() {
        return this.indexNames1;
    }
    
    public String[] getIndexNames2() {
        return this.indexNames2;
    }
    
    public float eval(final int index1, final int index2) {
        if (this.indexNames1 != null && (index1 < 0 || index1 > this.indexNames1.length)) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/ShaderParameterFloat;I)Ljava/lang/String;, this, index1));
            return 0.0f;
        }
        if (this.indexNames2 != null && (index2 < 0 || index2 > this.indexNames2.length)) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/ShaderParameterFloat;I)Ljava/lang/String;, this, index2));
            return 0.0f;
        }
        switch (ShaderParameterFloat.ShaderParameterFloat$1.$SwitchMap$net$optifine$shaders$uniform$ShaderParameterFloat[this.ordinal()]) {
            case 1: {
                final gu pos = Shaders.getWorldCameraPosition();
                final he<cnk> biome = (he<cnk>)Shaders.getCurrentWorld().s(pos);
                return (float)BiomeUtils.getId((cnk)biome.a());
            }
            case 2: {
                final gu pos = Shaders.getWorldCameraPosition();
                final he<cnk> biome = (he<cnk>)Shaders.getCurrentWorld().s(pos);
                return (biome != null) ? ((float)BiomeUtils.getBiomeCategory((he)biome).ordinal()) : 0.0f;
            }
            case 3: {
                final gu pos = Shaders.getWorldCameraPosition();
                final he<cnk> biome = (he<cnk>)Shaders.getCurrentWorld().s(pos);
                return (biome != null) ? ((float)BiomeUtils.getPrecipitation((cnk)biome.a()).ordinal()) : 0.0f;
            }
            case 4: {
                final gu pos = Shaders.getWorldCameraPosition();
                final he<cnk> biome = (he<cnk>)Shaders.getCurrentWorld().s(pos);
                return (biome != null) ? ((cnk)biome.a()).g() : 0.0f;
            }
            case 5: {
                final gu pos = Shaders.getWorldCameraPosition();
                final he<cnk> biome = (he<cnk>)Shaders.getCurrentWorld().s(pos);
                return (biome != null) ? BiomeUtils.getDownfall((cnk)biome.a()) : 0.0f;
            }
            default: {
                if (this.uniform instanceof ShaderUniform1f) {
                    return ((ShaderUniform1f)this.uniform).getValue();
                }
                if (this.uniform instanceof ShaderUniform1i) {
                    return (float)((ShaderUniform1i)this.uniform).getValue();
                }
                if (this.uniform instanceof ShaderUniform2i) {
                    return (float)((ShaderUniform2i)this.uniform).getValue()[index1];
                }
                if (this.uniform instanceof ShaderUniform2f) {
                    return ((ShaderUniform2f)this.uniform).getValue()[index1];
                }
                if (this.uniform instanceof ShaderUniform3f) {
                    return ((ShaderUniform3f)this.uniform).getValue()[index1];
                }
                if (this.uniform instanceof ShaderUniform4f) {
                    return ((ShaderUniform4f)this.uniform).getValue()[index1];
                }
                if (this.uniform instanceof ShaderUniformM4) {
                    return ((ShaderUniformM4)this.uniform).getValue(index1, index2);
                }
                throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/ShaderParameterFloat;)Ljava/lang/String;, this));
            }
        }
    }
    
    private static boolean instanceOf(final Object obj, final Class... classes) {
        if (obj == null) {
            return false;
        }
        final Class objClass = obj.getClass();
        for (int i = 0; i < classes.length; ++i) {
            final Class cls = classes[i];
            if (cls.isAssignableFrom(objClass)) {
                return true;
            }
        }
        return false;
    }
    
    private static /* synthetic */ ShaderParameterFloat[] $values() {
        return new ShaderParameterFloat[] { ShaderParameterFloat.BIOME, ShaderParameterFloat.BIOME_CATEGORY, ShaderParameterFloat.BIOME_PRECIPITATION, ShaderParameterFloat.TEMPERATURE, ShaderParameterFloat.RAINFALL, ShaderParameterFloat.HELD_ITEM_ID, ShaderParameterFloat.HELD_BLOCK_LIGHT_VALUE, ShaderParameterFloat.HELD_ITEM_ID2, ShaderParameterFloat.HELD_BLOCK_LIGHT_VALUE2, ShaderParameterFloat.WORLD_TIME, ShaderParameterFloat.WORLD_DAY, ShaderParameterFloat.MOON_PHASE, ShaderParameterFloat.FRAME_COUNTER, ShaderParameterFloat.FRAME_TIME, ShaderParameterFloat.FRAME_TIME_COUNTER, ShaderParameterFloat.SUN_ANGLE, ShaderParameterFloat.SHADOW_ANGLE, ShaderParameterFloat.RAIN_STRENGTH, ShaderParameterFloat.ASPECT_RATIO, ShaderParameterFloat.VIEW_WIDTH, ShaderParameterFloat.VIEW_HEIGHT, ShaderParameterFloat.NEAR, ShaderParameterFloat.FAR, ShaderParameterFloat.WETNESS, ShaderParameterFloat.EYE_ALTITUDE, ShaderParameterFloat.EYE_BRIGHTNESS, ShaderParameterFloat.TERRAIN_TEXTURE_SIZE, ShaderParameterFloat.TERRRAIN_ICON_SIZE, ShaderParameterFloat.IS_EYE_IN_WATER, ShaderParameterFloat.NIGHT_VISION, ShaderParameterFloat.BLINDNESS, ShaderParameterFloat.SCREEN_BRIGHTNESS, ShaderParameterFloat.HIDE_GUI, ShaderParameterFloat.CENTER_DEPT_SMOOTH, ShaderParameterFloat.ATLAS_SIZE, ShaderParameterFloat.PLAYER_MOOD, ShaderParameterFloat.CAMERA_POSITION, ShaderParameterFloat.PREVIOUS_CAMERA_POSITION, ShaderParameterFloat.SUN_POSITION, ShaderParameterFloat.MOON_POSITION, ShaderParameterFloat.SHADOW_LIGHT_POSITION, ShaderParameterFloat.UP_POSITION, ShaderParameterFloat.SKY_COLOR, ShaderParameterFloat.GBUFFER_PROJECTION, ShaderParameterFloat.GBUFFER_PROJECTION_INVERSE, ShaderParameterFloat.GBUFFER_PREVIOUS_PROJECTION, ShaderParameterFloat.GBUFFER_MODEL_VIEW, ShaderParameterFloat.GBUFFER_MODEL_VIEW_INVERSE, ShaderParameterFloat.GBUFFER_PREVIOUS_MODEL_VIEW, ShaderParameterFloat.SHADOW_PROJECTION, ShaderParameterFloat.SHADOW_PROJECTION_INVERSE, ShaderParameterFloat.SHADOW_MODEL_VIEW, ShaderParameterFloat.SHADOW_MODEL_VIEW_INVERSE };
    }
    
    static {
        $VALUES = $values();
    }
}
