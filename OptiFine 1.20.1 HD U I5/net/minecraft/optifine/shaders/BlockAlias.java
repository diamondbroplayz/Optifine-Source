// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import net.optifine.Config;
import java.util.HashSet;
import net.optifine.config.MatchBlock;

public class BlockAlias
{
    private int aliasBlockId;
    private int aliasMetadata;
    private MatchBlock[] matchBlocks;
    
    public BlockAlias(final int aliasBlockId, final int aliasMetadata, final MatchBlock[] matchBlocks) {
        this.aliasBlockId = aliasBlockId;
        this.aliasMetadata = aliasMetadata;
        this.matchBlocks = matchBlocks;
    }
    
    public BlockAlias(final int aliasBlockId, final MatchBlock[] matchBlocks) {
        this.aliasBlockId = aliasBlockId;
        this.matchBlocks = matchBlocks;
    }
    
    public int getAliasBlockId() {
        return this.aliasBlockId;
    }
    
    public int getAliasMetadata() {
        return this.aliasMetadata;
    }
    
    public MatchBlock[] getMatchBlocks() {
        return this.matchBlocks;
    }
    
    public boolean matches(final int id, final int metadata) {
        for (int i = 0; i < this.matchBlocks.length; ++i) {
            final MatchBlock matchBlock = this.matchBlocks[i];
            if (matchBlock.matches(id, metadata)) {
                return true;
            }
        }
        return false;
    }
    
    public int[] getMatchBlockIds() {
        final Set<Integer> blockIdSet = new HashSet<Integer>();
        for (int i = 0; i < this.matchBlocks.length; ++i) {
            final MatchBlock matchBlock = this.matchBlocks[i];
            final int blockId = matchBlock.getBlockId();
            blockIdSet.add(blockId);
        }
        final Integer[] blockIdsArr = blockIdSet.toArray(new Integer[blockIdSet.size()]);
        final int[] blockIds = Config.toPrimitive(blockIdsArr);
        return blockIds;
    }
    
    public MatchBlock[] getMatchBlocks(final int matchBlockId) {
        final List<MatchBlock> listMatchBlock = new ArrayList<MatchBlock>();
        for (int i = 0; i < this.matchBlocks.length; ++i) {
            final MatchBlock mb = this.matchBlocks[i];
            if (mb.getBlockId() == matchBlockId) {
                listMatchBlock.add(mb);
            }
        }
        final MatchBlock[] mbs = listMatchBlock.toArray(new MatchBlock[listMatchBlock.size()]);
        return mbs;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(IILjava/lang/String;)Ljava/lang/String;, this.aliasBlockId, this.aliasMetadata, Config.arrayToString(this.matchBlocks));
    }
}
