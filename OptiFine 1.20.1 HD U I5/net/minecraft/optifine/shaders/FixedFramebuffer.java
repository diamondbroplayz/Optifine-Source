// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL30;
import java.nio.IntBuffer;

public class FixedFramebuffer
{
    private String name;
    private int width;
    private int height;
    private int[] colorTextures;
    private int[] colorAttachments;
    private boolean depthFilterNearest;
    private boolean depthFilterHardware;
    private int glFramebuffer;
    private int depthTexture;
    private IntBuffer glDrawBuffers;
    
    public FixedFramebuffer(final String name, final int width, final int height, final int[] colorTextures, final int[] colorAttachments, final boolean depthFilterNearest, final boolean depthFilterHardware) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.colorTextures = colorTextures;
        this.colorAttachments = colorAttachments;
        this.depthFilterNearest = depthFilterNearest;
        this.depthFilterHardware = depthFilterHardware;
    }
    
    public void setup() {
        if (this.exists()) {
            this.delete();
        }
        this.glFramebuffer = GL30.glGenFramebuffers();
        this.bindFramebuffer();
        GL30.glDrawBuffers(0);
        GL30.glReadBuffer(0);
        GlStateManager._bindTexture(this.depthTexture = GL30.glGenTextures());
        GL30.glTexParameteri(3553, 10242, 33071);
        GL30.glTexParameteri(3553, 10243, 33071);
        final int filter = this.depthFilterNearest ? 9728 : 9729;
        GL30.glTexParameteri(3553, 10241, filter);
        GL30.glTexParameteri(3553, 10240, filter);
        if (this.depthFilterHardware) {
            GL30.glTexParameteri(3553, 34892, 34894);
        }
        GL30.glTexImage2D(3553, 0, 6402, this.width, this.height, 0, 6402, 5126, (FloatBuffer)null);
        GL30.glFramebufferTexture2D(36160, 36096, 3553, this.depthTexture, 0);
        Shaders.checkGLError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.name));
        for (int i = 0; i < this.colorTextures.length; ++i) {
            GL30.glFramebufferTexture2D(36160, this.colorAttachments[i], 3553, this.colorTextures[i], 0);
            Shaders.checkGLError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.name));
        }
        GlStateManager._bindTexture(0);
        if (this.colorTextures.length > 0) {
            this.glDrawBuffers = BufferUtils.createIntBuffer(this.colorAttachments.length);
            for (int i = 0; i < this.colorAttachments.length; ++i) {
                final int att = this.colorAttachments[i];
                this.glDrawBuffers.put(i, att);
            }
            GL30.glDrawBuffers(this.glDrawBuffers);
            GL30.glReadBuffer(0);
        }
        final int status = GL30.glCheckFramebufferStatus(36160);
        if (status != 36053) {
            Shaders.printChatAndLogError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, this.name, status));
            return;
        }
        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.name));
    }
    
    public void bindFramebuffer() {
        GlStateManager._glBindFramebuffer(36160, this.glFramebuffer);
    }
    
    public void delete() {
        if (this.glFramebuffer != 0) {
            GL30.glDeleteFramebuffers(this.glFramebuffer);
            this.glFramebuffer = 0;
        }
        if (this.depthTexture != 0) {
            GlStateManager._deleteTexture(this.depthTexture);
            this.depthTexture = 0;
        }
        this.glDrawBuffers = null;
    }
    
    public boolean exists() {
        return this.glFramebuffer != 0;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.name);
    }
}
