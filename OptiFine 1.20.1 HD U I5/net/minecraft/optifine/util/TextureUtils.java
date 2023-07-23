// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.HashMap;
import net.minecraft.resources.ResourceLocation;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.CharArrayWriter;
import org.apache.commons.io.IOUtils;
import java.awt.image.RenderedImage;
import org.lwjgl.BufferUtils;
import java.io.File;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;
import javax.imageio.ImageReader;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.io.InputStream;
import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL;
import net.optifine.EmissiveTextures;
import net.optifine.RandomEntities;
import java.util.Set;
import net.optifine.ConnectedTextures;
import net.optifine.shaders.MultiTexID;
import java.io.IOException;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.optifine.reflect.ReflectorForge;
import net.optifine.CustomBlockLayers;
import net.optifine.CustomLoadingScreens;
import net.optifine.CustomGuis;
import net.optifine.CustomPanorama;
import net.optifine.SmartLeaves;
import net.optifine.Lang;
import net.optifine.shaders.Shaders;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.CustomColors;
import net.optifine.BetterSnow;
import net.optifine.BetterGrass;
import net.optifine.NaturalTextures;
import net.optifine.TextureAnimations;
import net.optifine.CustomSky;
import net.optifine.CustomItems;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import net.optifine.Config;
import java.util.Map;
import java.nio.IntBuffer;

public class TextureUtils
{
    private static final String texGrassTop = "grass_block_top";
    private static final String texGrassSide = "grass_block_side";
    private static final String texGrassSideOverlay = "grass_block_side_overlay";
    private static final String texSnow = "snow";
    private static final String texGrassSideSnowed = "grass_block_snow";
    private static final String texMyceliumSide = "mycelium_side";
    private static final String texMyceliumTop = "mycelium_top";
    private static final String texWaterStill = "water_still";
    private static final String texWaterFlow = "water_flow";
    private static final String texLavaStill = "lava_still";
    private static final String texLavaFlow = "lava_flow";
    private static final String texFireLayer0 = "fire_0";
    private static final String texFireLayer1 = "fire_1";
    private static final String texSoulFireLayer0 = "soul_fire_0";
    private static final String texSoulFireLayer1 = "soul_fire_1";
    private static final String texCampFire = "campfire_fire";
    private static final String texCampFireLogLit = "campfire_log_lit";
    private static final String texSoulCampFire = "soul_campfire_fire";
    private static final String texSoulCampFireLogLit = "soul_campfire_log_lit";
    private static final String texPortal = "nether_portal";
    private static final String texGlass = "glass";
    private static final String texGlassPaneTop = "glass_pane_top";
    public static fuv iconGrassTop;
    public static fuv iconGrassSide;
    public static fuv iconGrassSideOverlay;
    public static fuv iconSnow;
    public static fuv iconGrassSideSnowed;
    public static fuv iconMyceliumSide;
    public static fuv iconMyceliumTop;
    public static fuv iconWaterStill;
    public static fuv iconWaterFlow;
    public static fuv iconLavaStill;
    public static fuv iconLavaFlow;
    public static fuv iconFireLayer0;
    public static fuv iconFireLayer1;
    public static fuv iconSoulFireLayer0;
    public static fuv iconSoulFireLayer1;
    public static fuv iconCampFire;
    public static fuv iconCampFireLogLit;
    public static fuv iconSoulCampFire;
    public static fuv iconSoulCampFireLogLit;
    public static fuv iconPortal;
    public static fuv iconGlass;
    public static fuv iconGlassPaneTop;
    public static final String SPRITE_PREFIX_BLOCKS = "minecraft:block/";
    public static final String SPRITE_PREFIX_ITEMS = "minecraft:item/";
    public static final acq LOCATION_SPRITE_EMPTY;
    public static final acq LOCATION_TEXTURE_EMPTY;
    public static final acq WHITE_TEXTURE_LOCATION;
    private static IntBuffer staticBuffer;
    private static int glMaximumTextureSize;
    private static Map<Integer, String> mapTextureAllocations;
    private static Map<acq, acq> mapSpriteLocations;
    private static acq LOCATION_ATLAS_PAINTINGS;
    
    public static void update() {
        final fuu mapBlocks = getTextureMapBlocks();
        if (mapBlocks == null) {
            return;
        }
        final String prefix = "minecraft:block/";
        TextureUtils.iconGrassTop = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconGrassSide = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconGrassSideOverlay = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconSnow = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconGrassSideSnowed = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconMyceliumSide = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconMyceliumTop = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconWaterStill = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconWaterFlow = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconLavaStill = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconLavaFlow = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconFireLayer0 = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconFireLayer1 = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconSoulFireLayer0 = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconSoulFireLayer1 = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconCampFire = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconCampFireLogLit = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconSoulCampFire = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconSoulCampFireLogLit = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconPortal = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconGlass = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        TextureUtils.iconGlassPaneTop = getSpriteCheck(mapBlocks, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, prefix));
        final String prefixItems = "minecraft:item/";
        TextureUtils.mapSpriteLocations.clear();
    }
    
    public static fuv getSpriteCheck(final fuu textureMap, final String name) {
        final fuv sprite = textureMap.getUploadedSprite(name);
        if (sprite == null || ful.isMisingSprite(sprite)) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
        }
        return sprite;
    }
    
    public static BufferedImage fixTextureDimensions(final String name, final BufferedImage bi) {
        if (name.startsWith("/mob/zombie") || name.startsWith("/mob/pigzombie")) {
            final int width = bi.getWidth();
            final int height = bi.getHeight();
            if (width == height * 2) {
                final BufferedImage scaledImage = new BufferedImage(width, height * 2, 2);
                final Graphics2D gr = scaledImage.createGraphics();
                gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                gr.drawImage(bi, 0, 0, width, height, null);
                return scaledImage;
            }
        }
        return bi;
    }
    
    public static int ceilPowerOfTwo(final int val) {
        int i;
        for (i = 1; i < val; i *= 2) {}
        return i;
    }
    
    public static int getPowerOfTwo(final int val) {
        int i;
        int po2;
        for (i = 1, po2 = 0; i < val; i *= 2, ++po2) {}
        return po2;
    }
    
    public static int twoToPower(final int power) {
        int val = 1;
        for (int i = 0; i < power; ++i) {
            val *= 2;
        }
        return val;
    }
    
    public static fug getTexture(final acq loc) {
        fug tex = Config.getTextureManager().b(loc);
        if (tex != null) {
            return tex;
        }
        if (!Config.hasResource(loc)) {
            return null;
        }
        tex = (fug)new fuo(loc);
        Config.getTextureManager().a(loc, tex);
        return tex;
    }
    
    public static void resourcesPreReload(final akx rm) {
        CustomItems.update();
    }
    
    public static void resourcesReloaded(final akx rm) {
        if (getTextureMapBlocks() == null) {
            return;
        }
        Config.dbg("*** Reloading custom textures ***");
        CustomSky.reset();
        TextureAnimations.reset();
        update();
        NaturalTextures.update();
        BetterGrass.update();
        BetterSnow.update();
        TextureAnimations.update();
        CustomColors.update();
        CustomSky.update();
        CustomItems.updateModels();
        CustomEntityModels.update();
        Shaders.resourcesReloaded();
        Lang.resourcesReloaded();
        Config.updateTexturePackClouds();
        SmartLeaves.updateLeavesModels();
        CustomPanorama.update();
        CustomGuis.update();
        ftb.update();
        CustomLoadingScreens.update();
        CustomBlockLayers.update();
        Config.getTextureManager().e();
        Config.dbg("Disable Forge light pipeline");
        ReflectorForge.setForgeLightPipelineEnabled(false);
    }
    
    public static fuu getTextureMapBlocks() {
        return Config.getTextureMap();
    }
    
    public static void registerResourceListener() {
        final akx rm = Config.getResourceManager();
        if (rm instanceof aku) {
            final aku rrm = (aku)rm;
            final alc rl = new alc() {
                protected Object b(final akx p_212854_1_, final ban p_212854_2_) {
                    return null;
                }
                
                protected void a(final Object p_212853_1_, final akx p_212853_2_, final ban p_212853_3_) {
                }
            };
            rrm.a((akr)rl);
            final aky rmrl = (aky)new ResourceManagerReloadListener() {
                public void m_6213_(final ResourceManager resourceManager) {
                    // 
                    // This method could not be decompiled.
                    // 
                    // Could not show original bytecode, likely due to the same error.
                    // 
                    // The error that occurred was:
                    // 
                    // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 690 out of bounds for byte[612]
                    //     at java.base/java.lang.System.arraycopy(Native Method)
                    //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                    //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                    //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:440)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                    //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                    //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                    //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                    //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                    //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                    //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                    //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                    // 
                    throw new IllegalStateException("An error occurred while decompiling this method.");
                }
            };
            rrm.a((akr)rmrl);
        }
    }
    
    public static void registerTickableTextures() {
        final TickableTexture tt = new TickableTexture() {
            public void m_7673_() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: aconst_null    
                //     2: nop            
                //     3: lload_3        
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            public void m_6704_(final ResourceManager var1) throws IOException {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //    Exceptions:
                //  throws java.io.IOException
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            public int m_117963_() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     1: iconst_3       
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            public void restoreLastBlurMipmap() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                // 
                // The error that occurred was:
                // 
                // java.lang.NullPointerException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            public MultiTexID getMultiTexID() {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1054 out of bounds for byte[1007]
                //     at java.base/java.lang.System.arraycopy(Native Method)
                //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
                //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
                //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
                //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
                //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
                //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
                //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        };
        final acq ttl = new acq("optifine/tickable_textures");
        Config.getTextureManager().a(ttl, (fug)tt);
    }
    
    public static void registerCustomModels(final fww modelBakery) {
        CustomItems.loadModels(modelBakery);
    }
    
    public static void registerCustomSprites(final fuu textureMap) {
        if (textureMap.g().equals((Object)fuu.e)) {
            ConnectedTextures.updateIcons(textureMap);
            CustomItems.updateIcons(textureMap);
            BetterGrass.updateIcons(textureMap);
        }
        textureMap.registerSprite(TextureUtils.LOCATION_SPRITE_EMPTY);
    }
    
    public static void registerCustomSpriteLocations(final acq atlasLocation, final Set<acq> spriteLocations) {
        RandomEntities.registerSprites(spriteLocations);
    }
    
    public static void refreshCustomSprites(final fuu textureMap) {
        if (textureMap.g().equals((Object)fuu.e)) {
            ConnectedTextures.refreshIcons(textureMap);
            CustomItems.refreshIcons(textureMap);
            BetterGrass.refreshIcons(textureMap);
        }
        EmissiveTextures.refreshIcons(textureMap);
    }
    
    public static acq fixResourceLocation(acq loc, final String basePath) {
        if (!loc.b().equals("minecraft")) {
            return loc;
        }
        final String path = loc.a();
        final String pathFixed = fixResourcePath(path, basePath);
        if (pathFixed != path) {
            loc = new acq(loc.b(), pathFixed);
        }
        return loc;
    }
    
    public static String fixResourcePath(String path, String basePath) {
        final String strAssMc = "assets/minecraft/";
        if (path.startsWith(strAssMc)) {
            path = path.substring(strAssMc.length());
            return path;
        }
        if (path.startsWith("./")) {
            path = path.substring(2);
            if (!basePath.endsWith("/")) {
                basePath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, basePath);
            }
            path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, path);
            return path;
        }
        if (path.startsWith("/~")) {
            path = path.substring(1);
        }
        final String strOptifine = "optifine/";
        if (path.startsWith("~/")) {
            path = path.substring(2);
            path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, strOptifine, path);
            return path;
        }
        if (path.startsWith("/")) {
            path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, strOptifine, path.substring(1));
            return path;
        }
        return path;
    }
    
    public static String getBasePath(final String path) {
        final int pos = path.lastIndexOf(47);
        if (pos < 0) {
            return "";
        }
        return path.substring(0, pos);
    }
    
    public static void applyAnisotropicLevel() {
        if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            final float maxLevel = GL11.glGetFloat(34047);
            float level = (float)Config.getAnisotropicFilterLevel();
            level = Math.min(level, maxLevel);
            GL11.glTexParameterf(3553, 34046, level);
        }
    }
    
    public static void bindTexture(final int glTexId) {
        GlStateManager._bindTexture(glTexId);
    }
    
    public static boolean isPowerOfTwo(final int x) {
        final int x2 = apa.c(x);
        return x2 == x;
    }
    
    public static ehk scaleImage(final ehk ni, final int w2) {
        final BufferedImage bi = toBufferedImage(ni);
        final BufferedImage bi2 = scaleImage(bi, w2);
        final ehk ni2 = toNativeImage(bi2);
        return ni2;
    }
    
    public static BufferedImage toBufferedImage(final ehk ni) {
        final int width = ni.a();
        final int height = ni.b();
        final int[] data = new int[width * height];
        ni.getBufferRGBA().get(data);
        final BufferedImage bi = new BufferedImage(width, height, 2);
        bi.setRGB(0, 0, width, height, data, 0, width);
        return bi;
    }
    
    private static ehk toNativeImage(final BufferedImage bi) {
        final int width = bi.getWidth();
        final int height = bi.getHeight();
        final int[] data = new int[width * height];
        bi.getRGB(0, 0, width, height, data, 0, width);
        final ehk ni = new ehk(width, height, false);
        ni.getBufferRGBA().put(data);
        return ni;
    }
    
    public static BufferedImage scaleImage(final BufferedImage bi, final int w2) {
        final int w3 = bi.getWidth();
        final int h = bi.getHeight();
        final int h2 = h * w2 / w3;
        final BufferedImage bi2 = new BufferedImage(w2, h2, 2);
        final Graphics2D g2 = bi2.createGraphics();
        Object method = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        if (w2 < w3 || w2 % w3 != 0) {
            method = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        }
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, method);
        g2.drawImage(bi, 0, 0, w2, h2, null);
        return bi2;
    }
    
    public static int scaleToGrid(final int size, final int sizeGrid) {
        if (size == sizeGrid) {
            return size;
        }
        int sizeNew;
        for (sizeNew = size / sizeGrid * sizeGrid; sizeNew < size; sizeNew += sizeGrid) {}
        return sizeNew;
    }
    
    public static int scaleToMin(final int size, final int sizeMin) {
        if (size >= sizeMin) {
            return size;
        }
        int sizeNew;
        for (sizeNew = sizeMin / size * size; sizeNew < sizeMin; sizeNew += size) {}
        return sizeNew;
    }
    
    public static Dimension getImageSize(final InputStream in, final String suffix) {
        final Iterator iter = ImageIO.getImageReadersBySuffix(suffix);
        while (iter.hasNext()) {
            final ImageReader reader = iter.next();
            try {
                final ImageInputStream iis = ImageIO.createImageInputStream(in);
                reader.setInput(iis);
                final int width = reader.getWidth(reader.getMinIndex());
                final int height = reader.getHeight(reader.getMinIndex());
                return new Dimension(width, height);
            }
            catch (IOException e) {
                continue;
            }
            finally {
                reader.dispose();
            }
            break;
        }
        return null;
    }
    
    public static void dbgMipmaps(final fuv textureatlassprite) {
        final ehk[] mipmapImages = textureatlassprite.getMipmapImages();
        for (int l = 0; l < mipmapImages.length; ++l) {
            final ehk image = mipmapImages[l];
            if (image == null) {
                Config.dbg(invokedynamic(makeConcatWithConstants:(ILehk;)Ljava/lang/String;, l, image));
            }
            else {
                Config.dbg(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, l, image.a() * image.b()));
            }
        }
    }
    
    public static void saveGlTexture(String name, final int textureId, final int mipmapLevels, final int width, final int height) {
        bindTexture(textureId);
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        name = StrUtils.removeSuffix(name, ".png");
        final File fileBase = new File(name);
        final File dir = fileBase.getParentFile();
        if (dir != null) {
            dir.mkdirs();
        }
        for (int i = 0; i < 16; ++i) {
            final String namePng = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, name, i);
            final File filePng = new File(namePng);
            filePng.delete();
        }
        for (int level = 0; level <= mipmapLevels; ++level) {
            final File filePng2 = new File(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, name, level));
            final int widthLevel = width >> level;
            final int heightLevel = height >> level;
            final int sizeLevel = widthLevel * heightLevel;
            final IntBuffer buf = BufferUtils.createIntBuffer(sizeLevel);
            final int[] data = new int[sizeLevel];
            GL11.glGetTexImage(3553, level, 32993, 33639, buf);
            buf.get(data);
            final BufferedImage image = new BufferedImage(widthLevel, heightLevel, 2);
            image.setRGB(0, 0, widthLevel, heightLevel, data, 0, widthLevel);
            try {
                ImageIO.write(image, "png", filePng2);
                Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/io/File;)Ljava/lang/String;, filePng2));
            }
            catch (Exception e) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/io/File;)Ljava/lang/String;, filePng2));
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
            }
        }
    }
    
    public static int getGLMaximumTextureSize() {
        if (TextureUtils.glMaximumTextureSize < 0) {
            TextureUtils.glMaximumTextureSize = detectGLMaximumTextureSize();
        }
        return TextureUtils.glMaximumTextureSize;
    }
    
    private static int detectGLMaximumTextureSize() {
        for (int i = 65536; i > 0; i >>= 1) {
            GlStateManager._texImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, (IntBuffer)null);
            final int err = GL11.glGetError();
            final int width = GlStateManager._getTexLevelParameter(32868, 0, 4096);
            if (width != 0) {
                return i;
            }
        }
        return 0;
    }
    
    public static BufferedImage readBufferedImage(final InputStream imageStream) throws IOException {
        if (imageStream == null) {
            return null;
        }
        try {
            final BufferedImage bufferedimage = ImageIO.read(imageStream);
            return bufferedimage;
        }
        finally {
            IOUtils.closeQuietly(imageStream);
        }
    }
    
    public static int toAbgr(final int argb) {
        final int a = argb >> 24 & 0xFF;
        final int r = argb >> 16 & 0xFF;
        final int g = argb >> 8 & 0xFF;
        final int b = argb >> 0 & 0xFF;
        final int abgr = a << 24 | b << 16 | g << 8 | r;
        return abgr;
    }
    
    public static void resetDataUnpacking() {
        GlStateManager._pixelStore(3314, 0);
        GlStateManager._pixelStore(3316, 0);
        GlStateManager._pixelStore(3315, 0);
        GlStateManager._pixelStore(3317, 4);
    }
    
    public static String getStackTrace(final Throwable t) {
        final CharArrayWriter caw = new CharArrayWriter();
        t.printStackTrace(new PrintWriter(caw));
        return caw.toString();
    }
    
    public static void debugTextureGenerated(final int id) {
        TextureUtils.mapTextureAllocations.put(id, getStackTrace(new Throwable("StackTrace")));
        Config.dbg(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, TextureUtils.mapTextureAllocations.size()));
    }
    
    public static void debugTextureDeleted(final int id) {
        TextureUtils.mapTextureAllocations.remove(id);
        Config.dbg(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, TextureUtils.mapTextureAllocations.size()));
    }
    
    public static fuv getCustomSprite(fuv sprite) {
        if (Config.isRandomEntities()) {
            sprite = RandomEntities.getRandomSprite(sprite);
        }
        if (EmissiveTextures.isActive()) {
            sprite = EmissiveTextures.getEmissiveSprite(sprite);
        }
        return sprite;
    }
    
    public static acq getSpriteLocation(final acq loc) {
        acq locSprite = (acq)TextureUtils.mapSpriteLocations.get(loc);
        if (locSprite == null) {
            String pathSprite = loc.a();
            pathSprite = StrUtils.removePrefix(pathSprite, "textures/");
            pathSprite = StrUtils.removeSuffix(pathSprite, ".png");
            locSprite = new acq(loc.b(), pathSprite);
            TextureUtils.mapSpriteLocations.put((ResourceLocation)loc, (ResourceLocation)locSprite);
        }
        return locSprite;
    }
    
    static {
        TextureUtils.LOCATION_SPRITE_EMPTY = new acq("optifine/ctm/default/empty");
        TextureUtils.LOCATION_TEXTURE_EMPTY = new acq("optifine/ctm/default/empty.png");
        TextureUtils.WHITE_TEXTURE_LOCATION = new acq("textures/misc/white.png");
        TextureUtils.staticBuffer = Config.createDirectIntBuffer(256);
        TextureUtils.glMaximumTextureSize = -1;
        TextureUtils.mapTextureAllocations = new HashMap<Integer, String>();
        TextureUtils.mapSpriteLocations = new HashMap<ResourceLocation, ResourceLocation>();
        TextureUtils.LOCATION_ATLAS_PAINTINGS = new acq("textures/atlas/paintings.png");
    }
}
