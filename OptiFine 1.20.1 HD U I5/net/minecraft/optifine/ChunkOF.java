// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

public class ChunkOF extends dei
{
    private boolean hasEntitiesOF;
    private boolean loadedOF;
    
    public ChunkOF(final cmm worldIn, final clt chunkPosIn) {
        super(worldIn, chunkPosIn);
    }
    
    public void a(final bfj entityIn) {
        this.hasEntitiesOF = true;
        super.a(entityIn);
    }
    
    public boolean hasEntities() {
        return this.hasEntitiesOF;
    }
    
    public void c(final boolean loaded) {
        super.c(this.loadedOF = loaded);
    }
    
    public boolean isLoaded() {
        return this.loadedOF;
    }
}
