// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.util.ArrayUtils;
import net.optifine.util.CompoundIntKey;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import java.nio.ByteBuffer;
import java.awt.Dimension;
import java.nio.FloatBuffer;
import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL30;
import java.util.Arrays;
import org.lwjgl.BufferUtils;
import java.util.HashMap;
import net.optifine.util.CompoundKey;
import java.util.Map;
import java.nio.IntBuffer;
import net.optifine.util.DynamicDimension;

public class ShadersFramebuffer
{
    private String name;
    private int width;
    private int height;
    private int usedColorBuffers;
    private int usedDepthBuffers;
    private int maxDrawBuffers;
    private boolean[] depthFilterNearest;
    private boolean[] depthFilterHardware;
    private boolean[] colorFilterNearest;
    private DynamicDimension[] colorBufferSizes;
    private int[] buffersFormat;
    private int[] colorTextureUnits;
    private int[] depthTextureUnits;
    private int[] colorImageUnits;
    private int glFramebuffer;
    private FlipTextures colorTexturesFlip;
    private IntBuffer depthTextures;
    private final DrawBuffers drawBuffers;
    private DrawBuffers activeDrawBuffers;
    private int[] drawColorTextures;
    private int[] drawColorTexturesMap;
    private boolean[] dirtyColorTextures;
    private Map<CompoundKey, FixedFramebuffer> fixedFramebuffers;
    
    public ShadersFramebuffer(final String name, final int width, final int height, final int usedColorBuffers, final int usedDepthBuffers, final int maxDrawBuffers, final boolean[] depthFilterNearest, final boolean[] depthFilterHardware, final boolean[] colorFilterNearest, final DynamicDimension[] colorBufferSizes, final int[] buffersFormat, final int[] colorTextureUnits, final int[] depthTextureUnits, final int[] colorImageUnits, final DrawBuffers drawBuffers) {
        this.fixedFramebuffers = new HashMap<CompoundKey, FixedFramebuffer>();
        this.name = name;
        this.width = width;
        this.height = height;
        this.usedColorBuffers = usedColorBuffers;
        this.usedDepthBuffers = usedDepthBuffers;
        this.maxDrawBuffers = maxDrawBuffers;
        this.depthFilterNearest = depthFilterNearest;
        this.depthFilterHardware = depthFilterHardware;
        this.colorFilterNearest = colorFilterNearest;
        this.colorBufferSizes = colorBufferSizes;
        this.buffersFormat = buffersFormat;
        this.colorTextureUnits = colorTextureUnits;
        this.depthTextureUnits = depthTextureUnits;
        this.colorImageUnits = colorImageUnits;
        this.drawBuffers = drawBuffers;
    }
    
    public void setup() {
        if (this.exists()) {
            this.delete();
        }
        this.colorTexturesFlip = new FlipTextures(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.name), this.usedColorBuffers);
        this.depthTextures = BufferUtils.createIntBuffer(this.usedDepthBuffers);
        this.drawColorTextures = new int[this.usedColorBuffers];
        this.drawColorTexturesMap = new int[this.usedColorBuffers];
        this.dirtyColorTextures = new boolean[this.maxDrawBuffers];
        Arrays.fill(this.drawColorTextures, 0);
        Arrays.fill(this.drawColorTexturesMap, -1);
        Arrays.fill(this.dirtyColorTextures, false);
        for (int i = 0; i < this.drawBuffers.limit(); ++i) {
            this.drawBuffers.put(i, 36064 + i);
        }
        this.glFramebuffer = GL30.glGenFramebuffers();
        this.bindFramebuffer();
        GL30.glDrawBuffers(0);
        GL30.glReadBuffer(0);
        GL30.glGenTextures(this.depthTextures.clear().limit(this.usedDepthBuffers));
        this.colorTexturesFlip.clear().limit(this.usedColorBuffers).genTextures();
        this.depthTextures.position(0);
        this.colorTexturesFlip.position(0);
        for (int i = 0; i < this.usedDepthBuffers; ++i) {
            GlStateManager._bindTexture(this.depthTextures.get(i));
            GL30.glTexParameteri(3553, 10242, 33071);
            GL30.glTexParameteri(3553, 10243, 33071);
            final int filter = this.depthFilterNearest[i] ? 9728 : 9729;
            GL30.glTexParameteri(3553, 10241, filter);
            GL30.glTexParameteri(3553, 10240, filter);
            if (this.depthFilterHardware[i]) {
                GL30.glTexParameteri(3553, 34892, 34894);
            }
            GL30.glTexImage2D(3553, 0, 6402, this.width, this.height, 0, 6402, 5126, (FloatBuffer)null);
        }
        this.setFramebufferTexture2D(36160, 36096, 3553, this.depthTextures.get(0), 0);
        Shaders.checkGLError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.name));
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            GlStateManager._bindTexture(this.colorTexturesFlip.getA(i));
            GL30.glTexParameteri(3553, 10242, 33071);
            GL30.glTexParameteri(3553, 10243, 33071);
            final int filter = this.colorFilterNearest[i] ? 9728 : 9729;
            GL30.glTexParameteri(3553, 10241, filter);
            GL30.glTexParameteri(3553, 10240, filter);
            final Dimension dim = (this.colorBufferSizes[i] != null) ? this.colorBufferSizes[i].getDimension(this.width, this.height) : new Dimension(this.width, this.height);
            GL30.glTexImage2D(3553, 0, this.buffersFormat[i], dim.width, dim.height, 0, Shaders.getPixelFormat(this.buffersFormat[i]), 33639, (ByteBuffer)null);
            this.setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.getA(i), 0);
            Shaders.checkGLError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.name));
        }
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            GlStateManager._bindTexture(this.colorTexturesFlip.getB(i));
            GL30.glTexParameteri(3553, 10242, 33071);
            GL30.glTexParameteri(3553, 10243, 33071);
            final int filter = this.colorFilterNearest[i] ? 9728 : 9729;
            GL30.glTexParameteri(3553, 10241, filter);
            GL30.glTexParameteri(3553, 10240, filter);
            final Dimension dim = (this.colorBufferSizes[i] != null) ? this.colorBufferSizes[i].getDimension(this.width, this.height) : new Dimension(this.width, this.height);
            GL30.glTexImage2D(3553, 0, this.buffersFormat[i], dim.width, dim.height, 0, Shaders.getPixelFormat(this.buffersFormat[i]), 33639, (ByteBuffer)null);
            Shaders.checkGLError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.name));
        }
        GlStateManager._bindTexture(0);
        if (this.usedColorBuffers > 0) {
            this.setDrawBuffers(this.drawBuffers);
            GL30.glReadBuffer(0);
        }
        final int status = GL30.glCheckFramebufferStatus(36160);
        if (status != 36053) {
            Shaders.printChatAndLogError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, this.name, status));
            return;
        }
        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.name));
    }
    
    public void delete() {
        if (this.glFramebuffer != 0) {
            GL30.glDeleteFramebuffers(this.glFramebuffer);
            this.glFramebuffer = 0;
        }
        if (this.colorTexturesFlip != null) {
            this.colorTexturesFlip.deleteTextures();
            this.colorTexturesFlip = null;
        }
        if (this.depthTextures != null) {
            GlStateManager.deleteTextures(this.depthTextures);
            this.depthTextures = null;
        }
        this.drawBuffers.position(0).fill(0);
        for (final FixedFramebuffer ff : this.fixedFramebuffers.values()) {
            ff.delete();
        }
        this.fixedFramebuffers.clear();
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getGlFramebuffer() {
        return this.glFramebuffer;
    }
    
    public boolean exists() {
        return this.glFramebuffer != 0;
    }
    
    public void bindFramebuffer() {
        GlState.bindFramebuffer(this);
    }
    
    public void setColorTextures(final boolean main) {
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            this.setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.get(main, i), 0);
        }
    }
    
    public void setDepthTexture() {
        this.setFramebufferTexture2D(36160, 36096, 3553, this.depthTextures.get(0), 0);
    }
    
    public void setColorBuffersFiltering(final int minFilter, final int magFilter) {
        GlStateManager._activeTexture(33984);
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            GlStateManager._bindTexture(this.colorTexturesFlip.getA(i));
            GL11.glTexParameteri(3553, 10241, minFilter);
            GL11.glTexParameteri(3553, 10240, magFilter);
            GlStateManager._bindTexture(this.colorTexturesFlip.getB(i));
            GL11.glTexParameteri(3553, 10241, minFilter);
            GL11.glTexParameteri(3553, 10240, magFilter);
        }
        GlStateManager._bindTexture(0);
    }
    
    public void setFramebufferTexture2D(final int target, int attachment, final int texTarget, int texture, final int level) {
        final int colorIndex = attachment - 36064;
        if (this.isColorBufferIndex(colorIndex)) {
            if (this.colorBufferSizes[colorIndex] != null) {
                if (this.isColorExtendedIndex(colorIndex)) {
                    return;
                }
                texture = 0;
            }
            this.drawColorTextures[colorIndex] = texture;
            if (colorIndex >= this.maxDrawBuffers) {
                final int indexMapped = this.drawColorTexturesMap[colorIndex];
                if (!this.isDrawBufferIndex(indexMapped)) {
                    return;
                }
                attachment = 36064 + indexMapped;
            }
        }
        this.bindFramebuffer();
        GL30.glFramebufferTexture2D(target, attachment, texTarget, texture, level);
    }
    
    public boolean isColorBufferIndex(final int index) {
        return index >= 0 && index < this.usedColorBuffers;
    }
    
    public boolean isColorExtendedIndex(final int index) {
        return index >= this.maxDrawBuffers && index < this.usedColorBuffers;
    }
    
    public boolean isDrawBufferIndex(final int index) {
        return index >= 0 && index < this.maxDrawBuffers;
    }
    
    private void setDrawColorTexturesMap(final int[] newColorTexturesMap) {
        this.bindFramebuffer();
        for (int i = 0; i < this.maxDrawBuffers; ++i) {
            if (this.dirtyColorTextures[i]) {
                final int texture = this.drawColorTextures[i];
                GL30.glFramebufferTexture2D(36160, 36064 + i, 3553, texture, 0);
                this.dirtyColorTextures[i] = false;
            }
        }
        this.drawColorTexturesMap = newColorTexturesMap;
        for (int i = this.maxDrawBuffers; i < this.drawColorTexturesMap.length; ++i) {
            final int ai = this.drawColorTexturesMap[i];
            if (ai >= 0) {
                final int texture2 = this.drawColorTextures[i];
                GL30.glFramebufferTexture2D(36160, 36064 + ai, 3553, texture2, 0);
                this.dirtyColorTextures[ai] = true;
            }
        }
    }
    
    public void setDrawBuffers(DrawBuffers drawBuffersIn) {
        if (drawBuffersIn == null) {
            drawBuffersIn = Shaders.drawBuffersNone;
        }
        this.setDrawColorTexturesMap(drawBuffersIn.getAttachmentMappings());
        this.activeDrawBuffers = drawBuffersIn;
        this.bindFramebuffer();
        GL30.glDrawBuffers(drawBuffersIn.getGlDrawBuffers());
        Shaders.checkGLError("setDrawBuffers");
    }
    
    public void setDrawBuffers() {
        this.setDrawBuffers(this.drawBuffers);
    }
    
    public DrawBuffers getDrawBuffers() {
        return this.activeDrawBuffers;
    }
    
    public void bindDepthTextures(final int[] depthTextureImageUnits) {
        for (int i = 0; i < this.usedDepthBuffers; ++i) {
            GlStateManager._activeTexture(33984 + depthTextureImageUnits[i]);
            GlStateManager._bindTexture(this.depthTextures.get(i));
        }
        GlStateManager._activeTexture(33984);
    }
    
    public void bindColorTextures(final int startColorBuffer) {
        for (int i = startColorBuffer; i < this.usedColorBuffers; ++i) {
            GlStateManager._activeTexture(33984 + this.colorTextureUnits[i]);
            GlStateManager._bindTexture(this.colorTexturesFlip.getA(i));
            this.bindColorImage(i, true);
        }
    }
    
    public void bindColorImages(final boolean main) {
        if (this.colorImageUnits == null) {
            return;
        }
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            this.bindColorImage(i, main);
        }
    }
    
    public void bindColorImage(final int index, final boolean main) {
        if (this.colorImageUnits == null) {
            return;
        }
        if (index >= 0 && index < this.colorImageUnits.length) {
            final int imageFormat = Shaders.getImageFormat(this.buffersFormat[index]);
            GlStateManager.bindImageTexture(this.colorImageUnits[index], this.colorTexturesFlip.get(main, index), 0, false, 0, 35002, imageFormat);
        }
        GlStateManager._activeTexture(33984);
    }
    
    public void generateDepthMipmaps(final boolean[] depthMipmapEnabled) {
        for (int i = 0; i < this.usedDepthBuffers; ++i) {
            if (depthMipmapEnabled[i]) {
                GlStateManager._activeTexture(33984 + this.depthTextureUnits[i]);
                GlStateManager._bindTexture(this.depthTextures.get(i));
                GL30.glGenerateMipmap(3553);
                GL30.glTexParameteri(3553, 10241, this.depthFilterNearest[i] ? 9984 : 9987);
            }
        }
        GlStateManager._activeTexture(33984);
    }
    
    public void generateColorMipmaps(final boolean main, final boolean[] colorMipmapEnabled) {
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            if (colorMipmapEnabled[i]) {
                GlStateManager._activeTexture(33984 + this.colorTextureUnits[i]);
                GlStateManager._bindTexture(this.colorTexturesFlip.get(main, i));
                GL30.glGenerateMipmap(3553);
                GL30.glTexParameteri(3553, 10241, this.colorFilterNearest[i] ? 9984 : 9987);
            }
        }
        GlStateManager._activeTexture(33984);
    }
    
    public void genCompositeMipmap(final int compositeMipmapSetting) {
        if (Shaders.hasGlGenMipmap) {
            for (int i = 0; i < this.usedColorBuffers; ++i) {
                if ((compositeMipmapSetting & 1 << i) != 0x0) {
                    GlStateManager._activeTexture(33984 + this.colorTextureUnits[i]);
                    GL30.glTexParameteri(3553, 10241, 9987);
                    GL30.glGenerateMipmap(3553);
                }
            }
            GlStateManager._activeTexture(33984);
        }
    }
    
    public void flipColorTextures(final boolean[] toggleColorTextures) {
        for (int i = 0; i < this.colorTexturesFlip.limit(); ++i) {
            if (toggleColorTextures[i]) {
                this.flipColorTexture(i);
            }
        }
    }
    
    public void flipColorTexture(final int index) {
        this.colorTexturesFlip.flip(index);
        GlStateManager._activeTexture(33984 + this.colorTextureUnits[index]);
        GlStateManager._bindTexture(this.colorTexturesFlip.getA(index));
        this.bindColorImage(index, true);
        this.setFramebufferTexture2D(36160, 36064 + index, 3553, this.colorTexturesFlip.getB(index), 0);
        GlStateManager._activeTexture(33984);
    }
    
    public void clearColorBuffers(final boolean[] buffersClear, final Vector4f[] clearColors) {
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            if (buffersClear[i]) {
                final Vector4f col = clearColors[i];
                if (col != null) {
                    GL30.glClearColor(col.x(), col.y(), col.z(), col.w());
                }
                if (this.colorBufferSizes[i] != null) {
                    if (this.colorTexturesFlip.isChanged(i)) {
                        this.clearColorBufferFixedSize(i, false);
                    }
                    this.clearColorBufferFixedSize(i, true);
                }
                else {
                    if (this.colorTexturesFlip.isChanged(i)) {
                        this.setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.getB(i), 0);
                        this.setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
                        GL30.glClear(16384);
                        this.setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.getA(i), 0);
                    }
                    this.setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
                    GL30.glClear(16384);
                }
            }
        }
    }
    
    private void clearColorBufferFixedSize(final int i, final boolean main) {
        final Dimension dim = this.colorBufferSizes[i].getDimension(this.width, this.height);
        if (dim == null) {
            return;
        }
        final FixedFramebuffer ff = this.getFixedFramebuffer(dim.width, dim.height, Shaders.drawBuffersColorAtt[i], main);
        ff.bindFramebuffer();
        GL30.glClear(16384);
    }
    
    public void clearDepthBuffer(final Vector4f col) {
        this.setFramebufferTexture2D(36160, 36096, 3553, this.depthTextures.get(0), 0);
        GL30.glClearColor(col.x(), col.y(), col.z(), col.w());
        GL30.glClear(256);
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;IIIII)Ljava/lang/String;, this.name, this.width, this.height, this.usedColorBuffers, this.usedDepthBuffers, this.maxDrawBuffers);
    }
    
    public FixedFramebuffer getFixedFramebuffer(final int width, final int height, final DrawBuffers dbs, final boolean main) {
        final IntBuffer glDbs = dbs.getGlDrawBuffers();
        final int dbsLen = dbs.limit();
        final int[] glTexs = new int[dbsLen];
        final int[] glAtts = new int[dbsLen];
        for (int i = 0; i < glTexs.length; ++i) {
            final int att = dbs.get(i);
            final int ix = att - 36064;
            if (this.isColorBufferIndex(ix)) {
                glTexs[i] = this.colorTexturesFlip.get(main, ix);
                glAtts[i] = glDbs.get(i);
            }
        }
        final CompoundKey key = new CompoundKey(new CompoundIntKey(glTexs), new CompoundIntKey(glAtts));
        FixedFramebuffer ff = this.fixedFramebuffers.get(key);
        if (ff == null) {
            final String fixedName = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.name, ArrayUtils.arrayToString(glTexs), ArrayUtils.arrayToString(glAtts));
            ff = new FixedFramebuffer(fixedName, width, height, glTexs, glAtts, this.depthFilterNearest[0], this.depthFilterHardware[0]);
            ff.setup();
            this.fixedFramebuffers.put(key, ff);
        }
        return ff;
    }
}
