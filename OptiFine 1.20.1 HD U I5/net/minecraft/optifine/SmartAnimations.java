// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.shaders.Shaders;
import java.util.BitSet;

public class SmartAnimations
{
    private static boolean active;
    private static BitSet spritesRendered;
    private static BitSet texturesRendered;
    
    public static boolean isActive() {
        return SmartAnimations.active && !Shaders.isShadowPass;
    }
    
    public static void update() {
        SmartAnimations.active = Config.getGameSettings().ofSmartAnimations;
    }
    
    public static void spriteRendered(final fuv sprite) {
        if (!sprite.isTerrain()) {
            return;
        }
        final int animationIndex = sprite.getAnimationIndex();
        if (animationIndex < 0) {
            return;
        }
        SmartAnimations.spritesRendered.set(animationIndex);
    }
    
    public static void spritesRendered(final BitSet animationIndexes) {
        if (animationIndexes == null) {
            return;
        }
        SmartAnimations.spritesRendered.or(animationIndexes);
    }
    
    public static boolean isSpriteRendered(final fuv sprite) {
        if (!sprite.isTerrain()) {
            return true;
        }
        final int animationIndex = sprite.getAnimationIndex();
        return animationIndex >= 0 && SmartAnimations.spritesRendered.get(animationIndex);
    }
    
    public static void resetSpritesRendered(final fuu atlasTexture) {
        if (!atlasTexture.isTerrain()) {
            return;
        }
        SmartAnimations.spritesRendered.clear();
    }
    
    public static void textureRendered(final int textureId) {
        if (textureId < 0) {
            return;
        }
        SmartAnimations.texturesRendered.set(textureId);
    }
    
    public static boolean isTextureRendered(final int texId) {
        return texId >= 0 && SmartAnimations.texturesRendered.get(texId);
    }
    
    public static void resetTexturesRendered() {
        SmartAnimations.texturesRendered.clear();
    }
    
    static {
        SmartAnimations.spritesRendered = new BitSet();
        SmartAnimations.texturesRendered = new BitSet();
    }
}
