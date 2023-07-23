// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;

public class GlState
{
    private static ShadersFramebuffer activeFramebuffer;
    
    public static void bindFramebuffer(final ShadersFramebuffer framebufferIn) {
        GlState.activeFramebuffer = framebufferIn;
        GlStateManager._glBindFramebuffer(36160, GlState.activeFramebuffer.getGlFramebuffer());
    }
    
    public static ShadersFramebuffer getFramebuffer() {
        return GlState.activeFramebuffer;
    }
    
    public static void setFramebufferTexture2D(final int target, final int attachment, final int texTarget, final int texture, final int level) {
        GlState.activeFramebuffer.setFramebufferTexture2D(target, attachment, texTarget, texture, level);
    }
    
    public static void setDrawBuffers(final DrawBuffers drawBuffers) {
        GlState.activeFramebuffer.setDrawBuffers(drawBuffers);
    }
    
    public static DrawBuffers getDrawBuffers() {
        return GlState.activeFramebuffer.getDrawBuffers();
    }
}
