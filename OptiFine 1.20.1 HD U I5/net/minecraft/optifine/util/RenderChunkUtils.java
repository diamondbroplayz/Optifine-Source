// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class RenderChunkUtils
{
    public static int getCountBlocks(final fmp.c renderChunk) {
        final dej[] ebss = renderChunk.getChunk().d();
        if (ebss == null) {
            return 0;
        }
        final int indexEbs = renderChunk.f().v() - renderChunk.getWorld().C_() >> 4;
        final dej ebs = ebss[indexEbs];
        if (ebs == null) {
            return 0;
        }
        return ebs.getBlockRefCount();
    }
    
    public static double getRelativeBufferSize(final fmp.c renderChunk) {
        final int blockCount = getCountBlocks(renderChunk);
        final double vertexCountRel = getRelativeBufferSize(blockCount);
        return vertexCountRel;
    }
    
    public static double getRelativeBufferSize(final int blockCount) {
        double countRel = blockCount / 4096.0;
        countRel *= 0.995;
        double weight = countRel * 2.0 - 1.0;
        weight = apa.a(weight, -1.0, 1.0);
        return Math.sqrt(1.0 - weight * weight);
    }
}
