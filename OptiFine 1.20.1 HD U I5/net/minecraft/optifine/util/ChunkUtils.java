// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import net.optifine.ChunkOF;

public class ChunkUtils
{
    public static boolean hasEntities(final dei chunk) {
        if (chunk instanceof ChunkOF) {
            final ChunkOF chunkOF = (ChunkOF)chunk;
            return chunkOF.hasEntities();
        }
        return true;
    }
    
    public static boolean isLoaded(final dei chunk) {
        if (chunk instanceof ChunkOF) {
            final ChunkOF chunkOF = (ChunkOF)chunk;
            return chunkOF.isLoaded();
        }
        return false;
    }
}
