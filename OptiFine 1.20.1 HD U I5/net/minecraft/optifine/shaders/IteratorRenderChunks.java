// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.BlockPosM;
import java.util.Iterator;

public class IteratorRenderChunks implements Iterator<fmp.c>
{
    private fkl viewFrustum;
    private Iterator3d Iterator3d;
    private BlockPosM posBlock;
    
    public IteratorRenderChunks(final fkl viewFrustum, final gu posStart, final gu posEnd, final int width, final int height) {
        this.posBlock = new BlockPosM(0, 0, 0);
        this.viewFrustum = viewFrustum;
        this.Iterator3d = new Iterator3d(posStart, posEnd, width, height);
    }
    
    @Override
    public boolean hasNext() {
        return this.Iterator3d.hasNext();
    }
    
    @Override
    public fmp.c next() {
        final gu pos = this.Iterator3d.next();
        this.posBlock.setXyz(pos.u() << 4, pos.v() << 4, pos.w() << 4);
        final fmp.c rc = this.viewFrustum.a((gu)this.posBlock);
        return rc;
    }
    
    @Override
    public void remove() {
        throw new RuntimeException("Not implemented");
    }
}
