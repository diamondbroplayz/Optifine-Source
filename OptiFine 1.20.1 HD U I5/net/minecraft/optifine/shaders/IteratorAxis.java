// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.NoSuchElementException;
import net.optifine.BlockPosM;
import java.util.Iterator;

public class IteratorAxis implements Iterator<gu>
{
    private double yDelta;
    private double zDelta;
    private int xStart;
    private int xEnd;
    private double yStart;
    private double yEnd;
    private double zStart;
    private double zEnd;
    private int xNext;
    private double yNext;
    private double zNext;
    private BlockPosM pos;
    private boolean hasNext;
    
    public IteratorAxis(final gu posStart, final gu posEnd, final double yDelta, final double zDelta) {
        this.pos = new BlockPosM(0, 0, 0);
        this.hasNext = false;
        this.yDelta = yDelta;
        this.zDelta = zDelta;
        this.xStart = posStart.u();
        this.xEnd = posEnd.u();
        this.yStart = posStart.v();
        this.yEnd = posEnd.v() - 0.5;
        this.zStart = posStart.w();
        this.zEnd = posEnd.w() - 0.5;
        this.xNext = this.xStart;
        this.yNext = this.yStart;
        this.zNext = this.zStart;
        this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
    }
    
    @Override
    public boolean hasNext() {
        return this.hasNext;
    }
    
    @Override
    public gu next() {
        if (!this.hasNext) {
            throw new NoSuchElementException();
        }
        this.pos.setXyz(this.xNext, this.yNext, this.zNext);
        this.nextPos();
        this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
        return this.pos;
    }
    
    private void nextPos() {
        ++this.zNext;
        if (this.zNext < this.zEnd) {
            return;
        }
        this.zNext = this.zStart;
        ++this.yNext;
        if (this.yNext < this.yEnd) {
            return;
        }
        this.yNext = this.yStart;
        this.yStart += this.yDelta;
        this.yEnd += this.yDelta;
        this.yNext = this.yStart;
        this.zStart += this.zDelta;
        this.zEnd += this.zDelta;
        this.zNext = this.zStart;
        ++this.xNext;
        if (this.xNext < this.xEnd) {
            return;
        }
    }
    
    @Override
    public void remove() {
        throw new RuntimeException("Not implemented");
    }
    
    public static void main(final String[] args) throws Exception {
        final gu posStart = new gu(-2, 10, 20);
        final gu posEnd = new gu(2, 12, 22);
        final double yDelta = -0.5;
        final double zDelta = 0.5;
        final IteratorAxis it = new IteratorAxis(posStart, posEnd, yDelta, zDelta);
        System.out.println(invokedynamic(makeConcatWithConstants:(Lgu;Lgu;DD)Ljava/lang/String;, posStart, posEnd, yDelta, zDelta));
        while (it.hasNext()) {
            final gu blockPos = it.next();
            System.out.println(invokedynamic(makeConcatWithConstants:(Lgu;)Ljava/lang/String;, blockPos));
        }
    }
}
