// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import net.optifine.Config;

public class MatchBlock
{
    private int blockId;
    private int[] metadatas;
    
    public MatchBlock(final int blockId) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
    }
    
    public MatchBlock(final int blockId, final int metadata) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
        if (metadata >= 0) {
            this.metadatas = new int[] { metadata };
        }
    }
    
    public MatchBlock(final int blockId, final int[] metadatas) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
        this.metadatas = metadatas;
    }
    
    public int getBlockId() {
        return this.blockId;
    }
    
    public int[] getMetadatas() {
        return this.metadatas;
    }
    
    public boolean matches(final dcb blockState) {
        return blockState.getBlockId() == this.blockId && Matches.metadata(blockState.getMetadata(), this.metadatas);
    }
    
    public boolean matches(final int id, final int metadata) {
        return id == this.blockId && Matches.metadata(metadata, this.metadatas);
    }
    
    public void addMetadata(final int metadata) {
        if (this.metadatas == null) {
            return;
        }
        if (metadata < 0) {
            return;
        }
        for (int i = 0; i < this.metadatas.length; ++i) {
            if (this.metadatas[i] == metadata) {
                return;
            }
        }
        this.metadatas = Config.addIntToArray(this.metadatas, metadata);
    }
    
    public void addMetadatas(final int[] mds) {
        for (int i = 0; i < mds.length; ++i) {
            final int md = mds[i];
            this.addMetadata(md);
        }
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, this.blockId, Config.arrayToString(this.metadatas));
    }
}
