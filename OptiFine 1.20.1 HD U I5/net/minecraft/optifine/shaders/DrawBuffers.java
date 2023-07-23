// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.util.BufferUtil;
import org.lwjgl.BufferUtils;
import net.optifine.util.ArrayUtils;
import java.util.Arrays;
import java.nio.IntBuffer;

public class DrawBuffers
{
    private String name;
    private final int maxColorBuffers;
    private final int maxDrawBuffers;
    private final IntBuffer drawBuffers;
    private int[] attachmentMappings;
    private IntBuffer glDrawBuffers;
    
    public DrawBuffers(final String name, final int maxColorBuffers, final int maxDrawBuffers) {
        this.name = name;
        this.maxColorBuffers = maxColorBuffers;
        this.maxDrawBuffers = maxDrawBuffers;
        this.drawBuffers = IntBuffer.wrap(new int[maxDrawBuffers]);
    }
    
    public int get(final int index) {
        return this.drawBuffers.get(index);
    }
    
    public DrawBuffers put(final int attachment) {
        this.resetMappings();
        this.drawBuffers.put(attachment);
        return this;
    }
    
    public DrawBuffers put(final int index, final int attachment) {
        this.resetMappings();
        this.drawBuffers.put(index, attachment);
        return this;
    }
    
    public int position() {
        return this.drawBuffers.position();
    }
    
    public DrawBuffers position(final int newPosition) {
        this.resetMappings();
        this.drawBuffers.position(newPosition);
        return this;
    }
    
    public int limit() {
        return this.drawBuffers.limit();
    }
    
    public DrawBuffers limit(final int newLimit) {
        this.resetMappings();
        this.drawBuffers.limit(newLimit);
        return this;
    }
    
    public int capacity() {
        return this.drawBuffers.capacity();
    }
    
    public DrawBuffers fill(final int val) {
        for (int i = 0; i < this.drawBuffers.limit(); ++i) {
            this.drawBuffers.put(i, val);
        }
        this.resetMappings();
        return this;
    }
    
    private void resetMappings() {
        this.attachmentMappings = null;
        this.glDrawBuffers = null;
    }
    
    public int[] getAttachmentMappings() {
        if (this.attachmentMappings == null) {
            this.attachmentMappings = makeAttachmentMappings(this.drawBuffers, this.maxColorBuffers, this.maxDrawBuffers);
        }
        return this.attachmentMappings;
    }
    
    private static int[] makeAttachmentMappings(final IntBuffer drawBuffers, final int maxColorBuffers, final int maxDrawBuffers) {
        final int[] ams = new int[maxColorBuffers];
        Arrays.fill(ams, -1);
        for (int i = 0; i < drawBuffers.limit(); ++i) {
            final int att = drawBuffers.get(i);
            final int ai = att - 36064;
            if (ai >= 0 && ai < maxDrawBuffers) {
                ams[ai] = ai;
            }
        }
        for (int i = 0; i < drawBuffers.limit(); ++i) {
            final int att = drawBuffers.get(i);
            final int ai = att - 36064;
            if (ai >= maxDrawBuffers && ai < maxColorBuffers) {
                final int mi = getMappingIndex(ai, maxDrawBuffers, ams);
                if (mi < 0) {
                    throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ArrayUtils.arrayToString(ams)));
                }
                ams[ai] = mi;
            }
        }
        return ams;
    }
    
    private static int getMappingIndex(final int ai, final int maxDrawBuffers, final int[] attachmentMappings) {
        if (ai < maxDrawBuffers) {
            return ai;
        }
        if (attachmentMappings[ai] >= 0) {
            return attachmentMappings[ai];
        }
        for (int i = 0; i < maxDrawBuffers; ++i) {
            if (!ArrayUtils.contains(attachmentMappings, i)) {
                return i;
            }
        }
        return -1;
    }
    
    public IntBuffer getGlDrawBuffers() {
        if (this.glDrawBuffers == null) {
            this.glDrawBuffers = makeGlDrawBuffers(this.drawBuffers, this.getAttachmentMappings());
        }
        return this.glDrawBuffers;
    }
    
    private static IntBuffer makeGlDrawBuffers(final IntBuffer drawBuffers, final int[] attachmentMappings) {
        final IntBuffer glDrawBuffers = BufferUtils.createIntBuffer(drawBuffers.capacity());
        for (int i = 0; i < drawBuffers.limit(); ++i) {
            final int att = drawBuffers.get(i);
            final int ai = att - 36064;
            int attFixed = 0;
            if (ai >= 0 && ai < attachmentMappings.length) {
                attFixed = 36064 + attachmentMappings[ai];
            }
            glDrawBuffers.put(i, attFixed);
        }
        glDrawBuffers.limit(drawBuffers.limit());
        glDrawBuffers.position(drawBuffers.position());
        return glDrawBuffers;
    }
    
    public String getInfo(final boolean glBuffers) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.drawBuffers.limit(); ++i) {
            final int att = this.drawBuffers.get(i);
            int ai = att - 36064;
            if (glBuffers) {
                final int[] ams = this.getAttachmentMappings();
                if (ai >= 0 && ai < ams.length) {
                    ai = ams[ai];
                }
            }
            final String attName = this.getIndexName(ai);
            sb.append(attName);
        }
        return sb.toString();
    }
    
    private String getIndexName(final int ai) {
        if (ai >= 0 && ai < this.maxColorBuffers) {
            return invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, ai);
        }
        return "N";
    }
    
    public int indexOf(final int att) {
        for (int i = 0; i < this.limit(); ++i) {
            if (this.get(i) == att) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.name, BufferUtil.getBufferString(this.drawBuffers), ArrayUtils.arrayToString(this.attachmentMappings), BufferUtil.getBufferString(this.glDrawBuffers));
    }
}
