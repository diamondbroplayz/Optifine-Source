// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.util.TextureUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.optifine.util.PropertiesOrdered;
import net.optifine.render.RenderUtils;

public class EmissiveTextures
{
    private static String suffixEmissive;
    private static String suffixEmissivePng;
    private static boolean active;
    private static boolean render;
    private static boolean hasEmissive;
    private static boolean renderEmissive;
    private static final String SUFFIX_PNG = ".png";
    private static final acq LOCATION_TEXTURE_EMPTY;
    private static final acq LOCATION_SPRITE_EMPTY;
    private static fuw textureManager;
    private static int countRecursive;
    
    public static boolean isActive() {
        return EmissiveTextures.active;
    }
    
    public static String getSuffixEmissive() {
        return EmissiveTextures.suffixEmissive;
    }
    
    public static void beginRender() {
        if (EmissiveTextures.render) {
            ++EmissiveTextures.countRecursive;
            return;
        }
        EmissiveTextures.render = true;
        EmissiveTextures.hasEmissive = false;
    }
    
    public static acq getEmissiveTexture(final acq locationIn) {
        if (!EmissiveTextures.render) {
            return locationIn;
        }
        final fug texture = EmissiveTextures.textureManager.b(locationIn);
        if (texture instanceof fuu) {
            return locationIn;
        }
        acq locationEmissive = null;
        if (texture instanceof fuo) {
            locationEmissive = ((fuo)texture).locationEmissive;
        }
        if (!EmissiveTextures.renderEmissive) {
            if (locationEmissive != null) {
                EmissiveTextures.hasEmissive = true;
            }
            return locationIn;
        }
        if (locationEmissive == null) {
            locationEmissive = EmissiveTextures.LOCATION_TEXTURE_EMPTY;
        }
        return locationEmissive;
    }
    
    public static fuv getEmissiveSprite(final fuv sprite) {
        if (!EmissiveTextures.render) {
            return sprite;
        }
        fuv spriteEmissive = sprite.spriteEmissive;
        if (!EmissiveTextures.renderEmissive) {
            if (spriteEmissive != null) {
                EmissiveTextures.hasEmissive = true;
            }
            return sprite;
        }
        if (spriteEmissive == null) {
            spriteEmissive = sprite.getTextureAtlas().a(EmissiveTextures.LOCATION_SPRITE_EMPTY);
        }
        return spriteEmissive;
    }
    
    public static fkr getEmissiveQuad(final fkr quad) {
        if (!EmissiveTextures.render) {
            return quad;
        }
        final fkr quadEmissive = quad.getQuadEmissive();
        if (!EmissiveTextures.renderEmissive) {
            if (quadEmissive != null) {
                EmissiveTextures.hasEmissive = true;
            }
            return quad;
        }
        return quadEmissive;
    }
    
    public static boolean hasEmissive() {
        return EmissiveTextures.countRecursive <= 0 && EmissiveTextures.hasEmissive;
    }
    
    public static void beginRenderEmissive() {
        EmissiveTextures.renderEmissive = true;
    }
    
    public static boolean isRenderEmissive() {
        return EmissiveTextures.renderEmissive;
    }
    
    public static void endRenderEmissive() {
        RenderUtils.flushRenderBuffers();
        EmissiveTextures.renderEmissive = false;
    }
    
    public static void endRender() {
        if (EmissiveTextures.countRecursive > 0) {
            --EmissiveTextures.countRecursive;
            return;
        }
        EmissiveTextures.render = false;
        EmissiveTextures.hasEmissive = false;
    }
    
    public static void update() {
        EmissiveTextures.textureManager = enn.N().X();
        EmissiveTextures.active = false;
        EmissiveTextures.suffixEmissive = null;
        EmissiveTextures.suffixEmissivePng = null;
        if (!Config.isEmissiveTextures()) {
            return;
        }
        try {
            final String fileName = "optifine/emissive.properties";
            final acq loc = new acq(fileName);
            final InputStream in = Config.getResourceStream(loc);
            if (in == null) {
                return;
            }
            dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            EmissiveTextures.suffixEmissive = props.getProperty("suffix.emissive");
            if (EmissiveTextures.suffixEmissive != null) {
                EmissiveTextures.suffixEmissivePng = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, EmissiveTextures.suffixEmissive);
            }
            EmissiveTextures.active = (EmissiveTextures.suffixEmissive != null);
        }
        catch (FileNotFoundException e2) {}
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateIcons(final fuu textureMap, final Set<acq> locations) {
        if (!EmissiveTextures.active) {
            return;
        }
        for (final acq loc : locations) {
            checkEmissive(textureMap, loc);
        }
    }
    
    private static void checkEmissive(final fuu textureMap, final acq locSprite) {
        final String suffixEm = getSuffixEmissive();
        if (suffixEm == null) {
            return;
        }
        if (locSprite.a().endsWith(suffixEm)) {
            return;
        }
        final acq locSpriteEm = new acq(locSprite.b(), invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, locSprite.a(), suffixEm));
        final acq locPngEm = textureMap.getSpritePath(locSpriteEm);
        if (!Config.hasResource(locPngEm)) {
            return;
        }
        final fuv sprite = textureMap.registerSprite(locSprite);
        final fuv spriteEmissive = textureMap.registerSprite(locSpriteEm);
        spriteEmissive.isSpriteEmissive = true;
        sprite.spriteEmissive = spriteEmissive;
        textureMap.registerSprite(EmissiveTextures.LOCATION_SPRITE_EMPTY);
    }
    
    public static void refreshIcons(final fuu textureMap) {
        final Collection<fuv> sprites = (Collection<fuv>)textureMap.getRegisteredSprites();
        for (final fuv sprite : sprites) {
            refreshIcon(sprite, textureMap);
        }
    }
    
    private static void refreshIcon(final fuv sprite, final fuu textureMap) {
        if (sprite.spriteEmissive == null) {
            return;
        }
        final fuv spriteNew = textureMap.getUploadedSprite(sprite.getName());
        if (spriteNew == null) {
            return;
        }
        final fuv spriteEmissiveNew = textureMap.getUploadedSprite(sprite.spriteEmissive.getName());
        if (spriteEmissiveNew == null) {
            return;
        }
        spriteEmissiveNew.isSpriteEmissive = true;
        spriteNew.spriteEmissive = spriteEmissiveNew;
    }
    
    private static void dbg(final String str) {
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    private static void warn(final String str) {
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    public static boolean isEmissive(final acq loc) {
        return EmissiveTextures.suffixEmissivePng != null && loc.a().endsWith(EmissiveTextures.suffixEmissivePng);
    }
    
    public static void loadTexture(final acq loc, final fuo tex) {
        if (loc == null || tex == null) {
            return;
        }
        tex.isEmissive = false;
        tex.locationEmissive = null;
        if (EmissiveTextures.suffixEmissivePng == null) {
            return;
        }
        final String path = loc.a();
        if (!path.endsWith(".png")) {
            return;
        }
        if (path.endsWith(EmissiveTextures.suffixEmissivePng)) {
            tex.isEmissive = true;
            return;
        }
        final String pathEmPng = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, path.substring(0, path.length() - ".png".length()), EmissiveTextures.suffixEmissivePng);
        final acq locEmPng = new acq(loc.b(), pathEmPng);
        if (!Config.hasResource(locEmPng)) {
            return;
        }
        tex.locationEmissive = locEmPng;
    }
    
    static {
        EmissiveTextures.suffixEmissive = null;
        EmissiveTextures.suffixEmissivePng = null;
        EmissiveTextures.active = false;
        EmissiveTextures.render = false;
        EmissiveTextures.hasEmissive = false;
        EmissiveTextures.renderEmissive = false;
        LOCATION_TEXTURE_EMPTY = TextureUtils.LOCATION_TEXTURE_EMPTY;
        LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
        EmissiveTextures.countRecursive = 0;
    }
}
