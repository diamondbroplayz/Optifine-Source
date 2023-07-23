// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.util.ArrayUtils;
import net.optifine.util.BufferUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Arrays;
import org.lwjgl.BufferUtils;
import java.nio.IntBuffer;

public class FlipTextures
{
    private String name;
    private IntBuffer texturesA;
    private IntBuffer texturesB;
    private boolean[] flips;
    private boolean[] changed;
    
    public FlipTextures(final String name, final int capacity) {
        this.name = name;
        this.texturesA = BufferUtils.createIntBuffer(capacity);
        this.texturesB = BufferUtils.createIntBuffer(capacity);
        this.flips = new boolean[capacity];
        this.changed = new boolean[capacity];
    }
    
    public int capacity() {
        return this.texturesA.capacity();
    }
    
    public int position() {
        return this.texturesA.position();
    }
    
    public int limit() {
        return this.texturesA.limit();
    }
    
    public FlipTextures position(final int position) {
        this.texturesA.position(position);
        this.texturesB.position(position);
        return this;
    }
    
    public FlipTextures limit(final int limit) {
        this.texturesA.limit(limit);
        this.texturesB.limit(limit);
        return this;
    }
    
    public int get(final boolean main, final int index) {
        if (main) {
            return this.getA(index);
        }
        return this.getB(index);
    }
    
    public int getA(final int index) {
        return this.get(index, this.flips[index]);
    }
    
    public int getB(final int index) {
        return this.get(index, !this.flips[index]);
    }
    
    private int get(final int index, final boolean flipped) {
        final IntBuffer textures = flipped ? this.texturesB : this.texturesA;
        return textures.get(index);
    }
    
    public void flip(final int index) {
        this.flips[index] = !this.flips[index];
        this.changed[index] = true;
    }
    
    public boolean isChanged(final int index) {
        return this.changed[index];
    }
    
    public void reset() {
        Arrays.fill(this.flips, false);
        Arrays.fill(this.changed, false);
    }
    
    public void genTextures() {
        GlStateManager.genTextures(this.texturesA);
        GlStateManager.genTextures(this.texturesB);
    }
    
    public void deleteTextures() {
        GlStateManager.deleteTextures(this.texturesA);
        GlStateManager.deleteTextures(this.texturesB);
        this.reset();
    }
    
    public void fill(final int val) {
        for (int limit = this.limit(), i = 0; i < limit; ++i) {
            this.texturesA.put(i, val);
            this.texturesB.put(i, val);
        }
    }
    
    public FlipTextures clear() {
        this.position(0);
        this.limit(this.capacity());
        return this;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.name, BufferUtil.getBufferString(this.texturesA), BufferUtil.getBufferString(this.texturesB), ArrayUtils.arrayToString(this.flips, this.limit()), ArrayUtils.arrayToString(this.changed, this.limit()));
    }
}
