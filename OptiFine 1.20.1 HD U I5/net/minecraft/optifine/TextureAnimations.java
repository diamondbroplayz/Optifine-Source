// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import net.optifine.util.TextureUtils;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import java.util.List;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class TextureAnimations
{
    private static TextureAnimation[] textureAnimations;
    private static int countAnimationsActive;
    private static int frameCountAnimations;
    
    public static void reset() {
        TextureAnimations.textureAnimations = null;
    }
    
    public static void update() {
        TextureAnimations.textureAnimations = null;
        TextureAnimations.countAnimationsActive = 0;
        final ajl[] rps = Config.getResourcePacks();
        TextureAnimations.textureAnimations = getTextureAnimations(rps);
        updateAnimations();
    }
    
    public static void updateAnimations() {
        if (TextureAnimations.textureAnimations == null || !Config.isAnimatedTextures()) {
            TextureAnimations.countAnimationsActive = 0;
            return;
        }
        int countActive = 0;
        for (int i = 0; i < TextureAnimations.textureAnimations.length; ++i) {
            final TextureAnimation anim = TextureAnimations.textureAnimations[i];
            anim.updateTexture();
            if (anim.isActive()) {
                ++countActive;
            }
        }
        final int frameCount = Config.getMinecraft().f.getFrameCount();
        if (frameCount != TextureAnimations.frameCountAnimations) {
            TextureAnimations.countAnimationsActive = countActive;
            TextureAnimations.frameCountAnimations = frameCount;
        }
        if (SmartAnimations.isActive()) {
            SmartAnimations.resetTexturesRendered();
        }
    }
    
    private static TextureAnimation[] getTextureAnimations(final ajl[] rps) {
        final List list = new ArrayList();
        for (int i = 0; i < rps.length; ++i) {
            final ajl rp = rps[i];
            final TextureAnimation[] tas = getTextureAnimations(rp);
            if (tas != null) {
                list.addAll(Arrays.asList(tas));
            }
        }
        final TextureAnimation[] anims = list.toArray(new TextureAnimation[list.size()]);
        return anims;
    }
    
    private static TextureAnimation[] getTextureAnimations(final ajl rp) {
        final String[] animPropNames = ResUtils.collectFiles(rp, "optifine/anim/", ".properties", (String[])null);
        if (animPropNames.length <= 0) {
            return null;
        }
        final List list = new ArrayList();
        for (int i = 0; i < animPropNames.length; ++i) {
            final String propName = animPropNames[i];
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, propName));
            try {
                final acq propLoc = new acq(propName);
                final InputStream in = Config.getResourceStream(rp, ajm.a, propLoc);
                final Properties props = new PropertiesOrdered();
                props.load(in);
                in.close();
                final TextureAnimation anim = makeTextureAnimation(props, propLoc);
                if (anim != null) {
                    final acq locDstTex = new acq(anim.getDstTex());
                    if (!Config.hasResource(rp, locDstTex)) {
                        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, propName));
                    }
                    else {
                        list.add(anim);
                    }
                }
            }
            catch (FileNotFoundException e) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, e.getMessage()));
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        final TextureAnimation[] anims = list.toArray(new TextureAnimation[list.size()]);
        return anims;
    }
    
    private static TextureAnimation makeTextureAnimation(final Properties props, final acq propLoc) {
        String texFrom = props.getProperty("from");
        String texTo = props.getProperty("to");
        final int x = Config.parseInt(props.getProperty("x"), -1);
        final int y = Config.parseInt(props.getProperty("y"), -1);
        final int width = Config.parseInt(props.getProperty("w"), -1);
        final int height = Config.parseInt(props.getProperty("h"), -1);
        if (texFrom == null || texTo == null) {
            Config.warn("TextureAnimation: Source or target texture not specified");
            return null;
        }
        if (x < 0 || y < 0 || width < 0 || height < 0) {
            Config.warn("TextureAnimation: Invalid coordinates");
            return null;
        }
        texFrom = texFrom.trim();
        texTo = texTo.trim();
        final String basePath = TextureUtils.getBasePath(propLoc.a());
        texFrom = TextureUtils.fixResourcePath(texFrom, basePath);
        texTo = TextureUtils.fixResourcePath(texTo, basePath);
        final byte[] imageBytes = getCustomTextureData(texFrom, width);
        if (imageBytes == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, texTo));
            return null;
        }
        final int countPixels = imageBytes.length / 4;
        final int countFrames = countPixels / (width * height);
        final int countPixelsAllFrames = countFrames * (width * height);
        if (countPixels != countPixelsAllFrames) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;F)Ljava/lang/String;, texFrom, countPixels / (float)(width * height)));
            return null;
        }
        final acq locTexTo = new acq(texTo);
        try {
            final InputStream inTexTo = Config.getResourceStream(locTexTo);
            if (inTexTo == null) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, texTo));
                return null;
            }
            final BufferedImage imgTexTo = readTextureImage(inTexTo);
            if (x + width > imgTexTo.getWidth() || y + height > imgTexTo.getHeight()) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, texTo));
                return null;
            }
            final TextureAnimation anim = new TextureAnimation(texFrom, imageBytes, texTo, locTexTo, x, y, width, height, props);
            return anim;
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, texTo));
            return null;
        }
    }
    
    private static byte[] getCustomTextureData(final String imagePath, final int tileWidth) {
        byte[] imageBytes = loadImage(imagePath, tileWidth);
        if (imageBytes == null) {
            imageBytes = loadImage(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, imagePath), tileWidth);
        }
        return imageBytes;
    }
    
    private static byte[] loadImage(final String name, final int targetWidth) {
        final enr options = Config.getGameSettings();
        try {
            final acq locRes = new acq(name);
            final InputStream in = Config.getResourceStream(locRes);
            if (in == null) {
                return null;
            }
            BufferedImage image = readTextureImage(in);
            in.close();
            if (image == null) {
                return null;
            }
            if (targetWidth > 0 && image.getWidth() != targetWidth) {
                final double aspectHW = image.getHeight() / image.getWidth();
                final int targetHeight = (int)(targetWidth * aspectHW);
                image = scaleBufferedImage(image, targetWidth, targetHeight);
            }
            final int width = image.getWidth();
            final int height = image.getHeight();
            final int[] ai = new int[width * height];
            final byte[] byteBuf = new byte[width * height * 4];
            image.getRGB(0, 0, width, height, ai, 0, width);
            for (int l = 0; l < ai.length; ++l) {
                final int alpha = ai[l] >> 24 & 0xFF;
                final int red = ai[l] >> 16 & 0xFF;
                final int green = ai[l] >> 8 & 0xFF;
                final int blue = ai[l] & 0xFF;
                byteBuf[l * 4 + 0] = (byte)red;
                byteBuf[l * 4 + 1] = (byte)green;
                byteBuf[l * 4 + 2] = (byte)blue;
                byteBuf[l * 4 + 3] = (byte)alpha;
            }
            return byteBuf;
        }
        catch (FileNotFoundException e2) {
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static BufferedImage readTextureImage(final InputStream par1InputStream) throws IOException {
        final BufferedImage var2 = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return var2;
    }
    
    private static BufferedImage scaleBufferedImage(final BufferedImage image, final int width, final int height) {
        final BufferedImage scaledImage = new BufferedImage(width, height, 2);
        final Graphics2D gr = scaledImage.createGraphics();
        gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gr.drawImage(image, 0, 0, width, height, null);
        return scaledImage;
    }
    
    public static int getCountAnimations() {
        if (TextureAnimations.textureAnimations == null) {
            return 0;
        }
        return TextureAnimations.textureAnimations.length;
    }
    
    public static int getCountAnimationsActive() {
        return TextureAnimations.countAnimationsActive;
    }
    
    static {
        TextureAnimations.textureAnimations = null;
        TextureAnimations.countAnimationsActive = 0;
        TextureAnimations.frameCountAnimations = 0;
    }
}
