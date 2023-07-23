// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import org.lwjgl.opengl.GL11;
import com.mojang.blaze3d.platform.GlStateManager;
import net.optifine.util.TextureUtils;
import java.util.Properties;
import java.nio.ByteBuffer;

public class TextureAnimation
{
    private String srcTex;
    private String dstTex;
    acq dstTexLoc;
    private int dstTextId;
    private int dstX;
    private int dstY;
    private int frameWidth;
    private int frameHeight;
    private TextureAnimationFrame[] frames;
    private int currentFrameIndex;
    private boolean interpolate;
    private int interpolateSkip;
    private ByteBuffer interpolateData;
    byte[] srcData;
    private ByteBuffer imageData;
    private boolean active;
    private boolean valid;
    
    public TextureAnimation(final String texFrom, final byte[] srcData, final String texTo, final acq locTexTo, final int dstX, final int dstY, final int frameWidth, final int frameHeight, final Properties props) {
        this.srcTex = null;
        this.dstTex = null;
        this.dstTexLoc = null;
        this.dstTextId = -1;
        this.dstX = 0;
        this.dstY = 0;
        this.frameWidth = 0;
        this.frameHeight = 0;
        this.frames = null;
        this.currentFrameIndex = 0;
        this.interpolate = false;
        this.interpolateSkip = 0;
        this.interpolateData = null;
        this.srcData = null;
        this.imageData = null;
        this.active = true;
        this.valid = true;
        this.srcTex = texFrom;
        this.dstTex = texTo;
        this.dstTexLoc = locTexTo;
        this.dstX = dstX;
        this.dstY = dstY;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        final int frameLen = frameWidth * frameHeight * 4;
        if (srcData.length % frameLen != 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(III)Ljava/lang/String;, srcData.length, frameWidth, frameHeight));
        }
        this.srcData = srcData;
        int numFrames = srcData.length / frameLen;
        if (props.get("tile.0") != null) {
            for (int i = 0; props.get(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i)) != null; ++i) {
                numFrames = i + 1;
            }
        }
        final String durationDefStr = (String)props.get("duration");
        final int durationDef = Math.max(Config.parseInt(durationDefStr, 1), 1);
        this.frames = new TextureAnimationFrame[numFrames];
        for (int j = 0; j < this.frames.length; ++j) {
            final String indexStr = (String)props.get(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, j));
            final int index = Config.parseInt(indexStr, j);
            final String durationStr = (String)props.get(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, j));
            final int duration = Math.max(Config.parseInt(durationStr, durationDef), 1);
            final TextureAnimationFrame frm = new TextureAnimationFrame(index, duration);
            this.frames[j] = frm;
        }
        this.interpolate = Config.parseBoolean(props.getProperty("interpolate"), false);
        this.interpolateSkip = Config.parseInt(props.getProperty("skip"), 0);
        if (this.interpolate) {
            this.interpolateData = ehh.a(frameLen);
        }
    }
    
    public boolean nextFrame() {
        final TextureAnimationFrame frame = this.getCurrentFrame();
        if (frame == null) {
            return false;
        }
        final TextureAnimationFrame textureAnimationFrame = frame;
        ++textureAnimationFrame.counter;
        if (frame.counter < frame.duration) {
            return this.interpolate;
        }
        frame.counter = 0;
        ++this.currentFrameIndex;
        if (this.currentFrameIndex >= this.frames.length) {
            this.currentFrameIndex = 0;
        }
        return true;
    }
    
    public TextureAnimationFrame getCurrentFrame() {
        return this.getFrame(this.currentFrameIndex);
    }
    
    public TextureAnimationFrame getFrame(int index) {
        if (this.frames.length <= 0) {
            return null;
        }
        if (index < 0 || index >= this.frames.length) {
            index = 0;
        }
        final TextureAnimationFrame frame = this.frames[index];
        return frame;
    }
    
    public int getFrameCount() {
        return this.frames.length;
    }
    
    public void updateTexture() {
        if (!this.valid) {
            return;
        }
        if (this.dstTextId < 0) {
            final fug tex = TextureUtils.getTexture(this.dstTexLoc);
            if (tex == null) {
                this.valid = false;
                return;
            }
            this.dstTextId = tex.a();
        }
        if (this.imageData == null) {
            (this.imageData = ehh.a(this.srcData.length)).put(this.srcData);
            this.imageData.flip();
            this.srcData = null;
        }
        this.active = (!SmartAnimations.isActive() || SmartAnimations.isTextureRendered(this.dstTextId));
        if (!this.nextFrame()) {
            return;
        }
        if (!this.active) {
            return;
        }
        final int frameLen = this.frameWidth * this.frameHeight * 4;
        final TextureAnimationFrame frame = this.getCurrentFrame();
        if (frame == null) {
            return;
        }
        final int offset = frameLen * frame.index;
        if (offset + frameLen > this.imageData.limit()) {
            return;
        }
        if (!this.interpolate || frame.counter <= 0) {
            this.imageData.position(offset);
            GlStateManager._bindTexture(this.dstTextId);
            TextureUtils.resetDataUnpacking();
            GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
            return;
        }
        if (this.interpolateSkip > 1 && frame.counter % this.interpolateSkip != 0) {
            return;
        }
        final TextureAnimationFrame frameNext = this.getFrame(this.currentFrameIndex + 1);
        final double k = 1.0 * frame.counter / frame.duration;
        this.updateTextureInerpolate(frame, frameNext, k);
    }
    
    private void updateTextureInerpolate(final TextureAnimationFrame frame1, final TextureAnimationFrame frame2, final double k) {
        final int frameLen = this.frameWidth * this.frameHeight * 4;
        final int offset1 = frameLen * frame1.index;
        if (offset1 + frameLen > this.imageData.limit()) {
            return;
        }
        final int offset2 = frameLen * frame2.index;
        if (offset2 + frameLen > this.imageData.limit()) {
            return;
        }
        this.interpolateData.clear();
        for (int i = 0; i < frameLen; ++i) {
            final int c1 = this.imageData.get(offset1 + i) & 0xFF;
            final int c2 = this.imageData.get(offset2 + i) & 0xFF;
            final int c3 = this.mix(c1, c2, k);
            final byte b = (byte)c3;
            this.interpolateData.put(b);
        }
        this.interpolateData.flip();
        GlStateManager._bindTexture(this.dstTextId);
        TextureUtils.resetDataUnpacking();
        GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.interpolateData);
    }
    
    private int mix(final int col1, final int col2, final double k) {
        return (int)(col1 * (1.0 - k) + col2 * k);
    }
    
    public String getSrcTex() {
        return this.srcTex;
    }
    
    public String getDstTex() {
        return this.dstTex;
    }
    
    public acq getDstTexLoc() {
        return this.dstTexLoc;
    }
    
    public boolean isActive() {
        return this.active;
    }
}
