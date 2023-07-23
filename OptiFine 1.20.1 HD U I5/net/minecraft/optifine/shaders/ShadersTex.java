// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.HashMap;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import net.optifine.util.TextureUtils;
import com.mojang.blaze3d.platform.TextureUtil;
import net.optifine.Config;
import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11;
import java.util.Arrays;
import org.lwjgl.BufferUtils;
import java.util.Map;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;

public class ShadersTex
{
    public static final int initialBufferSize = 1048576;
    public static ByteBuffer byteBuffer;
    public static IntBuffer intBuffer;
    public static int[] intArray;
    public static final int defBaseTexColor = 0;
    public static final int defNormTexColor = -8421377;
    public static final int defSpecTexColor = 0;
    public static Map<Integer, MultiTexID> multiTexMap;
    
    public static IntBuffer getIntBuffer(final int size) {
        if (ShadersTex.intBuffer.capacity() < size) {
            final int bufferSize = roundUpPOT(size);
            ShadersTex.byteBuffer = BufferUtils.createByteBuffer(bufferSize * 4);
            ShadersTex.intBuffer = ShadersTex.byteBuffer.asIntBuffer();
        }
        return ShadersTex.intBuffer;
    }
    
    public static int[] getIntArray(final int size) {
        if (ShadersTex.intArray == null) {
            ShadersTex.intArray = new int[1048576];
        }
        if (ShadersTex.intArray.length < size) {
            ShadersTex.intArray = new int[roundUpPOT(size)];
        }
        return ShadersTex.intArray;
    }
    
    public static int roundUpPOT(final int x) {
        int i = x - 1;
        i |= i >> 1;
        i |= i >> 2;
        i |= i >> 4;
        i |= i >> 8;
        i |= i >> 16;
        return i + 1;
    }
    
    public static int log2(int x) {
        int log = 0;
        if ((x & 0xFFFF0000) != 0x0) {
            log += 16;
            x >>= 16;
        }
        if ((x & 0xFF00) != 0x0) {
            log += 8;
            x >>= 8;
        }
        if ((x & 0xF0) != 0x0) {
            log += 4;
            x >>= 4;
        }
        if ((x & 0x6) != 0x0) {
            log += 2;
            x >>= 2;
        }
        if ((x & 0x2) != 0x0) {
            ++log;
        }
        return log;
    }
    
    public static IntBuffer fillIntBuffer(final int size, final int value) {
        final int[] aint = getIntArray(size);
        final IntBuffer intBuf = getIntBuffer(size);
        Arrays.fill(ShadersTex.intArray, 0, size, value);
        ShadersTex.intBuffer.put(ShadersTex.intArray, 0, size);
        return ShadersTex.intBuffer;
    }
    
    public static int[] createAIntImage(final int size) {
        final int[] aint = new int[size * 3];
        Arrays.fill(aint, 0, size, 0);
        Arrays.fill(aint, size, size * 2, -8421377);
        Arrays.fill(aint, size * 2, size * 3, 0);
        return aint;
    }
    
    public static int[] createAIntImage(final int size, final int color) {
        final int[] aint = new int[size * 3];
        Arrays.fill(aint, 0, size, color);
        Arrays.fill(aint, size, size * 2, -8421377);
        Arrays.fill(aint, size * 2, size * 3, 0);
        return aint;
    }
    
    public static MultiTexID getMultiTexID(final fug tex) {
        MultiTexID multiTex = tex.multiTex;
        if (multiTex == null) {
            final int baseTex = tex.a();
            multiTex = ShadersTex.multiTexMap.get(baseTex);
            if (multiTex == null) {
                multiTex = new MultiTexID(baseTex, GL11.glGenTextures(), GL11.glGenTextures());
                ShadersTex.multiTexMap.put(baseTex, multiTex);
            }
            tex.multiTex = multiTex;
        }
        return multiTex;
    }
    
    public static void deleteTextures(final fug atex, final int texid) {
        final MultiTexID multiTex = atex.multiTex;
        if (multiTex != null) {
            atex.multiTex = null;
            ShadersTex.multiTexMap.remove(multiTex.base);
            GlStateManager._deleteTexture(multiTex.norm);
            GlStateManager._deleteTexture(multiTex.spec);
            if (multiTex.base != texid) {
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, multiTex.base, texid));
                GlStateManager._deleteTexture(multiTex.base);
            }
        }
    }
    
    public static void bindNSTextures(final int normTex, final int specTex, final boolean normalBlend, final boolean specularBlend, final boolean mipmaps) {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
            if (Shaders.configNormalMap) {
                GlStateManager._activeTexture(33985);
                GlStateManager._bindTexture(normTex);
                if (!normalBlend) {
                    final int minFilter = mipmaps ? 9984 : 9728;
                    GlStateManager._texParameter(3553, 10241, minFilter);
                    GlStateManager._texParameter(3553, 10240, 9728);
                }
            }
            if (Shaders.configSpecularMap) {
                GlStateManager._activeTexture(33987);
                GlStateManager._bindTexture(specTex);
                if (!specularBlend) {
                    final int minFilter = mipmaps ? 9984 : 9728;
                    GlStateManager._texParameter(3553, 10241, minFilter);
                    GlStateManager._texParameter(3553, 10240, 9728);
                }
            }
            GlStateManager._activeTexture(33984);
        }
    }
    
    public static void bindNSTextures(final MultiTexID multiTex) {
        bindNSTextures(multiTex.norm, multiTex.spec, true, true, false);
    }
    
    public static void bindTextures(final int baseTex, final int normTex, final int specTex) {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
            GlStateManager._activeTexture(33985);
            GlStateManager._bindTexture(normTex);
            GlStateManager._activeTexture(33987);
            GlStateManager._bindTexture(specTex);
            GlStateManager._activeTexture(33984);
        }
        GlStateManager._bindTexture(baseTex);
    }
    
    public static void bindTextures(final MultiTexID multiTex, final boolean normalBlend, final boolean specularBlend, final boolean mipmaps) {
        if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
            if (Shaders.configNormalMap) {
                GlStateManager._activeTexture(33985);
                GlStateManager._bindTexture(multiTex.norm);
                if (!normalBlend) {
                    final int minFilter = mipmaps ? 9984 : 9728;
                    GlStateManager._texParameter(3553, 10241, minFilter);
                    GlStateManager._texParameter(3553, 10240, 9728);
                }
            }
            if (Shaders.configSpecularMap) {
                GlStateManager._activeTexture(33987);
                GlStateManager._bindTexture(multiTex.spec);
                if (!specularBlend) {
                    final int minFilter = mipmaps ? 9984 : 9728;
                    GlStateManager._texParameter(3553, 10241, minFilter);
                    GlStateManager._texParameter(3553, 10240, 9728);
                }
            }
            GlStateManager._activeTexture(33984);
        }
        GlStateManager._bindTexture(multiTex.base);
    }
    
    public static void bindTexture(final int id) {
        final fug tex = Config.getTextureManager().getTextureById(id);
        if (tex == null) {
            GlStateManager._bindTexture(id);
            return;
        }
        bindTexture(tex);
    }
    
    public static void bindTexture(final fug tex) {
        final int texId = tex.a();
        boolean normalBlend = true;
        boolean specularBlend = true;
        boolean mipmaps = false;
        if (tex instanceof fuu) {
            final fuu at = (fuu)tex;
            normalBlend = at.isNormalBlend();
            specularBlend = at.isSpecularBlend();
            mipmaps = at.isMipmaps();
        }
        final MultiTexID multiTex = tex.getMultiTexID();
        if (multiTex != null) {
            bindTextures(multiTex, normalBlend, specularBlend, mipmaps);
        }
        else {
            GlStateManager._bindTexture(texId);
        }
        if (GlStateManager.getActiveTextureUnit() == 33984) {
            final int prevSizeX = Shaders.atlasSizeX;
            final int prevSizeY = Shaders.atlasSizeY;
            if (tex instanceof fuu) {
                Shaders.atlasSizeX = ((fuu)tex).atlasWidth;
                Shaders.atlasSizeY = ((fuu)tex).atlasHeight;
            }
            else {
                Shaders.atlasSizeX = 0;
                Shaders.atlasSizeY = 0;
            }
        }
    }
    
    public static void bindTextures(final int baseTex) {
        final MultiTexID multiTex = ShadersTex.multiTexMap.get(baseTex);
        bindTextures(multiTex, true, true, false);
    }
    
    public static void initDynamicTextureNS(final fui tex) {
        final MultiTexID multiTex = tex.getMultiTexID();
        final ehk nativeImage = tex.e();
        final int width = nativeImage.a();
        final int height = nativeImage.b();
        final ehk imageNormal = makeImageColor(width, height, -8421377);
        TextureUtil.prepareImage(multiTex.norm, width, height);
        imageNormal.a(0, 0, 0, 0, 0, width, height, false, false, false, true);
        final ehk imageSpecular = makeImageColor(width, height, 0);
        TextureUtil.prepareImage(multiTex.spec, width, height);
        imageSpecular.a(0, 0, 0, 0, 0, width, height, false, false, false, true);
        GlStateManager._bindTexture(multiTex.base);
    }
    
    public static void updateDynTexSubImage1(final int[] src, final int width, final int height, final int posX, final int posY, final int page) {
        final int size = width * height;
        final IntBuffer intBuf = getIntBuffer(size);
        intBuf.clear();
        final int offset = page * size;
        if (src.length < offset + size) {
            return;
        }
        intBuf.put(src, offset, size).position(0).limit(size);
        TextureUtils.resetDataUnpacking();
        GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
        intBuf.clear();
    }
    
    public static fug createDefaultTexture() {
        final fui tex = new fui(1, 1, true);
        tex.e().a(0, 0, -1);
        tex.d();
        return (fug)tex;
    }
    
    public static void allocateTextureMapNS(final int mipmapLevels, final int width, final int height, final fuu tex) {
        final MultiTexID multiTex = getMultiTexID((fug)tex);
        if (Shaders.configNormalMap) {
            SMCLog.info(invokedynamic(makeConcatWithConstants:(III)Ljava/lang/String;, width, height, mipmapLevels));
            TextureUtil.prepareImage(multiTex.norm, mipmapLevels, width, height);
        }
        if (Shaders.configSpecularMap) {
            SMCLog.info(invokedynamic(makeConcatWithConstants:(III)Ljava/lang/String;, width, height, mipmapLevels));
            TextureUtil.prepareImage(multiTex.spec, mipmapLevels, width, height);
        }
        GlStateManager._bindTexture(multiTex.base);
    }
    
    private static ehk[] generateMipmaps(final ehk image, int levels) {
        if (levels < 0) {
            levels = 0;
        }
        final ehk[] mipmapImages = new ehk[levels + 1];
        mipmapImages[0] = image;
        if (levels > 0) {
            for (int level = 1; level <= levels; ++level) {
                final ehk imageParent = mipmapImages[level - 1];
                final ehk imageChild = new ehk(imageParent.a() >> 1, imageParent.b() >> 1, false);
                final int k = imageChild.a();
                final int l = imageChild.b();
                for (int x = 0; x < k; ++x) {
                    for (int y = 0; y < l; ++y) {
                        imageChild.a(x, y, blend4Simple(imageParent.a(x * 2 + 0, y * 2 + 0), imageParent.a(x * 2 + 1, y * 2 + 0), imageParent.a(x * 2 + 0, y * 2 + 1), imageParent.a(x * 2 + 1, y * 2 + 1)));
                    }
                }
                mipmapImages[level] = imageChild;
            }
        }
        return mipmapImages;
    }
    
    public static BufferedImage readImage(final acq resLoc) {
        try {
            if (!Config.hasResource(resLoc)) {
                return null;
            }
            final InputStream istr = Config.getResourceStream(resLoc);
            if (istr == null) {
                return null;
            }
            final BufferedImage image = ImageIO.read(istr);
            istr.close();
            return image;
        }
        catch (IOException e) {
            return null;
        }
    }
    
    public static int[][] genMipmapsSimple(final int maxLevel, final int width, final int[][] data) {
        for (int level = 1; level <= maxLevel; ++level) {
            if (data[level] == null) {
                final int cw = width >> level;
                final int pw = cw * 2;
                final int[] aintp = data[level - 1];
                final int n = level;
                final int[] array = new int[cw * cw];
                data[n] = array;
                final int[] aintc = array;
                for (int y = 0; y < cw; ++y) {
                    for (int x = 0; x < cw; ++x) {
                        final int ppos = y * 2 * pw + x * 2;
                        aintc[y * cw + x] = blend4Simple(aintp[ppos], aintp[ppos + 1], aintp[ppos + pw], aintp[ppos + pw + 1]);
                    }
                }
            }
        }
        return data;
    }
    
    public static void uploadTexSub1(final int[][] src, final int width, final int height, final int posX, final int posY, final int page) {
        TextureUtils.resetDataUnpacking();
        final int size = width * height;
        final IntBuffer intBuf = getIntBuffer(size);
        for (int numLevel = src.length, level = 0, lw = width, lh = height, px = posX, py = posY; lw > 0 && lh > 0 && level < numLevel; lw >>= 1, lh >>= 1, px >>= 1, py >>= 1, ++level) {
            final int lsize = lw * lh;
            final int[] aint = src[level];
            intBuf.clear();
            if (aint.length >= lsize * (page + 1)) {
                intBuf.put(aint, lsize * page, lsize).position(0).limit(lsize);
                GL11.glTexSubImage2D(3553, level, px, py, lw, lh, 32993, 33639, intBuf);
            }
        }
        intBuf.clear();
    }
    
    public static int blend4Alpha(final int c0, final int c1, final int c2, final int c3) {
        int a0 = c0 >>> 24 & 0xFF;
        int a2 = c1 >>> 24 & 0xFF;
        int a3 = c2 >>> 24 & 0xFF;
        int a4 = c3 >>> 24 & 0xFF;
        final int as = a0 + a2 + a3 + a4;
        final int an = (as + 2) / 4;
        int dv;
        if (as != 0) {
            dv = as;
        }
        else {
            dv = 4;
            a3 = (a4 = (a2 = (a0 = 1)));
        }
        final int frac = (dv + 1) / 2;
        final int color = an << 24 | ((c0 >>> 16 & 0xFF) * a0 + (c1 >>> 16 & 0xFF) * a2 + (c2 >>> 16 & 0xFF) * a3 + (c3 >>> 16 & 0xFF) * a4 + frac) / dv << 16 | ((c0 >>> 8 & 0xFF) * a0 + (c1 >>> 8 & 0xFF) * a2 + (c2 >>> 8 & 0xFF) * a3 + (c3 >>> 8 & 0xFF) * a4 + frac) / dv << 8 | ((c0 >>> 0 & 0xFF) * a0 + (c1 >>> 0 & 0xFF) * a2 + (c2 >>> 0 & 0xFF) * a3 + (c3 >>> 0 & 0xFF) * a4 + frac) / dv << 0;
        return color;
    }
    
    public static int blend4Simple(final int c0, final int c1, final int c2, final int c3) {
        final int color = ((c0 >>> 24 & 0xFF) + (c1 >>> 24 & 0xFF) + (c2 >>> 24 & 0xFF) + (c3 >>> 24 & 0xFF) + 2) / 4 << 24 | ((c0 >>> 16 & 0xFF) + (c1 >>> 16 & 0xFF) + (c2 >>> 16 & 0xFF) + (c3 >>> 16 & 0xFF) + 2) / 4 << 16 | ((c0 >>> 8 & 0xFF) + (c1 >>> 8 & 0xFF) + (c2 >>> 8 & 0xFF) + (c3 >>> 8 & 0xFF) + 2) / 4 << 8 | ((c0 >>> 0 & 0xFF) + (c1 >>> 0 & 0xFF) + (c2 >>> 0 & 0xFF) + (c3 >>> 0 & 0xFF) + 2) / 4 << 0;
        return color;
    }
    
    public static void genMipmapAlpha(final int[] aint, final int offset, final int width, final int height) {
        final int minwh = Math.min(width, height);
        int w2 = width;
        int w3 = width;
        int h2 = height;
        int h3 = height;
        int o2 = offset;
        int o3 = offset;
        o2 = offset;
        w2 = width;
        h2 = height;
        o3 = 0;
        w3 = 0;
        h3 = 0;
        int level = 0;
        while (w2 > 1 && h2 > 1) {
            o3 = o2 + w2 * h2;
            w3 = w2 / 2;
            h3 = h2 / 2;
            for (int y = 0; y < h3; ++y) {
                final int p1 = o3 + y * w3;
                final int p2 = o2 + y * 2 * w2;
                for (int x = 0; x < w3; ++x) {
                    aint[p1 + x] = blend4Alpha(aint[p2 + x * 2], aint[p2 + (x * 2 + 1)], aint[p2 + w2 + x * 2], aint[p2 + w2 + (x * 2 + 1)]);
                }
            }
            ++level;
            w2 = w3;
            h2 = h3;
            o2 = o3;
        }
        while (level > 0) {
            --level;
            w2 = width >> level;
            h2 = height >> level;
            int p3;
            o2 = (p3 = o3 - w2 * h2);
            for (int y2 = 0; y2 < h2; ++y2) {
                for (int x2 = 0; x2 < w2; ++x2) {
                    if (aint[p3] == 0) {
                        aint[p3] = (aint[o3 + y2 / 2 * w3 + x2 / 2] & 0xFFFFFF);
                    }
                    ++p3;
                }
            }
            o3 = o2;
            w3 = w2;
            h3 = h2;
        }
    }
    
    public static void genMipmapSimple(final int[] aint, final int offset, final int width, final int height) {
        final int minwh = Math.min(width, height);
        int w2 = width;
        int w3 = width;
        int h2 = height;
        int h3 = height;
        int o2 = offset;
        int o3 = offset;
        o2 = offset;
        w2 = width;
        h2 = height;
        o3 = 0;
        w3 = 0;
        h3 = 0;
        int level = 0;
        while (w2 > 1 && h2 > 1) {
            o3 = o2 + w2 * h2;
            w3 = w2 / 2;
            h3 = h2 / 2;
            for (int y = 0; y < h3; ++y) {
                final int p1 = o3 + y * w3;
                final int p2 = o2 + y * 2 * w2;
                for (int x = 0; x < w3; ++x) {
                    aint[p1 + x] = blend4Simple(aint[p2 + x * 2], aint[p2 + (x * 2 + 1)], aint[p2 + w2 + x * 2], aint[p2 + w2 + (x * 2 + 1)]);
                }
            }
            ++level;
            w2 = w3;
            h2 = h3;
            o2 = o3;
        }
        while (level > 0) {
            --level;
            w2 = width >> level;
            h2 = height >> level;
            int p3;
            o2 = (p3 = o3 - w2 * h2);
            for (int y2 = 0; y2 < h2; ++y2) {
                for (int x2 = 0; x2 < w2; ++x2) {
                    if (aint[p3] == 0) {
                        aint[p3] = (aint[o3 + y2 / 2 * w3 + x2 / 2] & 0xFFFFFF);
                    }
                    ++p3;
                }
            }
            o3 = o2;
            w3 = w2;
            h3 = h2;
        }
    }
    
    public static boolean isSemiTransparent(final int[] aint, final int width, final int height) {
        final int size = width * height;
        if (aint[0] >>> 24 == 255 && aint[size - 1] == 0) {
            return true;
        }
        for (int i = 0; i < size; ++i) {
            final int alpha = aint[i] >>> 24;
            if (alpha != 0 && alpha != 255) {
                return true;
            }
        }
        return false;
    }
    
    public static void updateSubTex1(final int[] src, final int width, final int height, final int posX, final int posY) {
        int level = 0;
        for (int cw = width, ch = height, cx = posX, cy = posY; cw > 0 && ch > 0; cw /= 2, ch /= 2, cx /= 2, cy /= 2) {
            GL11.glCopyTexSubImage2D(3553, level, cx, cy, 0, 0, cw, ch);
            ++level;
        }
    }
    
    public static void updateSubImage(final MultiTexID multiTex, final int[] src, final int width, final int height, final int posX, final int posY, final boolean linear, final boolean clamp) {
        final int size = width * height;
        final IntBuffer intBuf = getIntBuffer(size);
        TextureUtils.resetDataUnpacking();
        intBuf.clear();
        intBuf.put(src, 0, size);
        intBuf.position(0).limit(size);
        GlStateManager._bindTexture(multiTex.base);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
        if (src.length == size * 3) {
            intBuf.clear();
            intBuf.put(src, size, size).position(0);
            intBuf.position(0).limit(size);
        }
        GlStateManager._bindTexture(multiTex.norm);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
        if (src.length == size * 3) {
            intBuf.clear();
            intBuf.put(src, size * 2, size);
            intBuf.position(0).limit(size);
        }
        GlStateManager._bindTexture(multiTex.spec);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intBuf);
        GlStateManager._activeTexture(33984);
    }
    
    public static acq getNSMapLocation(final acq location, final String mapName) {
        if (location == null) {
            return null;
        }
        final String basename = location.a();
        final String[] basenameParts = basename.split(".png");
        final String basenameNoFileType = basenameParts[0];
        return new acq(location.b(), invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basenameNoFileType, mapName));
    }
    
    private static ehk loadNSMapImage(final akx manager, final acq location, final int width, final int height, final int defaultColor) {
        ehk image = loadNSMapFile(manager, location, width, height);
        if (image == null) {
            image = new ehk(width, height, false);
            final int colAbgr = TextureUtils.toAbgr(defaultColor);
            image.a(0, 0, width, height, colAbgr);
        }
        return image;
    }
    
    private static ehk makeImageColor(final int width, final int height, final int defaultColor) {
        final ehk image = new ehk(width, height, false);
        final int colAbgr = TextureUtils.toAbgr(defaultColor);
        image.fillRGBA(colAbgr);
        return image;
    }
    
    private static ehk loadNSMapFile(final akx manager, final acq location, final int width, final int height) {
        if (location == null) {
            return null;
        }
        try {
            final akv res = manager.getResourceOrThrow(location);
            final ehk image = ehk.a(res.d());
            if (image == null) {
                return null;
            }
            if (image.a() != width || image.b() != height) {
                image.close();
                return null;
            }
            return image;
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public static void loadSimpleTextureNS(final int textureID, final ehk nativeImage, final boolean blur, final boolean clamp, final akx resourceManager, final acq location, final MultiTexID multiTex) {
        final int width = nativeImage.a();
        final int height = nativeImage.b();
        final acq locNormal = getNSMapLocation(location, "n");
        final ehk imageNormal = loadNSMapImage(resourceManager, locNormal, width, height, -8421377);
        TextureUtil.prepareImage(multiTex.norm, 0, width, height);
        imageNormal.a(0, 0, 0, 0, 0, width, height, blur, clamp, false, true);
        final acq locSpecular = getNSMapLocation(location, "s");
        final ehk imageSpecular = loadNSMapImage(resourceManager, locSpecular, width, height, 0);
        TextureUtil.prepareImage(multiTex.spec, 0, width, height);
        imageSpecular.a(0, 0, 0, 0, 0, width, height, blur, clamp, false, true);
        GlStateManager._bindTexture(multiTex.base);
    }
    
    public static void mergeImage(final int[] aint, final int dstoff, final int srcoff, final int size) {
    }
    
    public static int blendColor(final int color1, final int color2, final int factor1) {
        final int factor2 = 255 - factor1;
        return ((color1 >>> 24 & 0xFF) * factor1 + (color2 >>> 24 & 0xFF) * factor2) / 255 << 24 | ((color1 >>> 16 & 0xFF) * factor1 + (color2 >>> 16 & 0xFF) * factor2) / 255 << 16 | ((color1 >>> 8 & 0xFF) * factor1 + (color2 >>> 8 & 0xFF) * factor2) / 255 << 8 | ((color1 >>> 0 & 0xFF) * factor1 + (color2 >>> 0 & 0xFF) * factor2) / 255 << 0;
    }
    
    public static void updateTextureMinMagFilter() {
        final fuw texman = enn.N().X();
        final fug texObj = texman.b(fuu.e);
        if (texObj != null) {
            final MultiTexID multiTex = texObj.getMultiTexID();
            GlStateManager._bindTexture(multiTex.base);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
            GlStateManager._bindTexture(multiTex.norm);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
            GlStateManager._bindTexture(multiTex.spec);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
            GlStateManager._bindTexture(0);
        }
    }
    
    public static int[][] getFrameTexData(final int[][] src, final int width, final int height, final int frameIndex) {
        final int numLevel = src.length;
        final int[][] dst = new int[numLevel][];
        for (int level = 0; level < numLevel; ++level) {
            final int[] sr1 = src[level];
            if (sr1 != null) {
                final int frameSize = (width >> level) * (height >> level);
                final int[] ds1 = new int[frameSize * 3];
                dst[level] = ds1;
                final int srcSize = sr1.length / 3;
                int srcPos = frameSize * frameIndex;
                int dstPos = 0;
                System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
                srcPos += srcSize;
                dstPos += frameSize;
                System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
                srcPos += srcSize;
                dstPos += frameSize;
                System.arraycopy(sr1, srcPos, ds1, dstPos, frameSize);
            }
        }
        return dst;
    }
    
    public static int[][] prepareAF(final fuv tas, final int[][] src, final int width, final int height) {
        final boolean skip = true;
        return src;
    }
    
    public static void fixTransparentColor(final fuv tas, final int[] aint) {
    }
    
    static {
        ShadersTex.byteBuffer = BufferUtils.createByteBuffer(4194304);
        ShadersTex.intBuffer = ShadersTex.byteBuffer.asIntBuffer();
        ShadersTex.intArray = new int[1048576];
        ShadersTex.multiTexMap = new HashMap<Integer, MultiTexID>();
    }
}
