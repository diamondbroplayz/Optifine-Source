// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.BlockPosM;
import java.util.Iterator;

public class Iterator3d implements Iterator<gu>
{
    private IteratorAxis iteratorAxis;
    private BlockPosM blockPos;
    private int axis;
    private int kX;
    private int kY;
    private int kZ;
    private static final int AXIS_X = 0;
    private static final int AXIS_Y = 1;
    private static final int AXIS_Z = 2;
    
    public Iterator3d(gu posStart, gu posEnd, final int width, final int height) {
        this.blockPos = new BlockPosM(0, 0, 0);
        this.axis = 0;
        final boolean revX = posStart.u() > posEnd.u();
        final boolean revY = posStart.v() > posEnd.v();
        final boolean revZ = posStart.w() > posEnd.w();
        posStart = this.reverseCoord(posStart, revX, revY, revZ);
        posEnd = this.reverseCoord(posEnd, revX, revY, revZ);
        this.kX = (revX ? -1 : 1);
        this.kY = (revY ? -1 : 1);
        this.kZ = (revZ ? -1 : 1);
        final eei vec = new eei((double)(posEnd.u() - posStart.u()), (double)(posEnd.v() - posStart.v()), (double)(posEnd.w() - posStart.w()));
        final eei vecN = vec.d();
        final eei vecX = new eei(1.0, 0.0, 0.0);
        final double dotX = vecN.b(vecX);
        final double dotXabs = Math.abs(dotX);
        final eei vecY = new eei(0.0, 1.0, 0.0);
        final double dotY = vecN.b(vecY);
        final double dotYabs = Math.abs(dotY);
        final eei vecZ = new eei(0.0, 0.0, 1.0);
        final double dotZ = vecN.b(vecZ);
        final double dotZabs = Math.abs(dotZ);
        if (dotZabs >= dotYabs && dotZabs >= dotXabs) {
            this.axis = 2;
            final gu pos1 = new gu(posStart.w(), posStart.v() - width, posStart.u() - height);
            final gu pos2 = new gu(posEnd.w(), posStart.v() + width + 1, posStart.u() + height + 1);
            final int countX = posEnd.w() - posStart.w();
            final double deltaY = (posEnd.v() - posStart.v()) / (1.0 * countX);
            final double deltaZ = (posEnd.u() - posStart.u()) / (1.0 * countX);
            this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
        }
        else if (dotYabs >= dotXabs && dotYabs >= dotZabs) {
            this.axis = 1;
            final gu pos1 = new gu(posStart.v(), posStart.u() - width, posStart.w() - height);
            final gu pos2 = new gu(posEnd.v(), posStart.u() + width + 1, posStart.w() + height + 1);
            final int countX = posEnd.v() - posStart.v();
            final double deltaY = (posEnd.u() - posStart.u()) / (1.0 * countX);
            final double deltaZ = (posEnd.w() - posStart.w()) / (1.0 * countX);
            this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
        }
        else {
            this.axis = 0;
            final gu pos1 = new gu(posStart.u(), posStart.v() - width, posStart.w() - height);
            final gu pos2 = new gu(posEnd.u(), posStart.v() + width + 1, posStart.w() + height + 1);
            final int countX = posEnd.u() - posStart.u();
            final double deltaY = (posEnd.v() - posStart.v()) / (1.0 * countX);
            final double deltaZ = (posEnd.w() - posStart.w()) / (1.0 * countX);
            this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
        }
    }
    
    private gu reverseCoord(gu pos, final boolean revX, final boolean revY, final boolean revZ) {
        if (revX) {
            pos = new gu(-pos.u(), pos.v(), pos.w());
        }
        if (revY) {
            pos = new gu(pos.u(), -pos.v(), pos.w());
        }
        if (revZ) {
            pos = new gu(pos.u(), pos.v(), -pos.w());
        }
        return pos;
    }
    
    @Override
    public boolean hasNext() {
        return this.iteratorAxis.hasNext();
    }
    
    @Override
    public gu next() {
        final gu pos = this.iteratorAxis.next();
        switch (this.axis) {
            case 0: {
                this.blockPos.setXyz(pos.u() * this.kX, pos.v() * this.kY, pos.w() * this.kZ);
                return this.blockPos;
            }
            case 1: {
                this.blockPos.setXyz(pos.v() * this.kX, pos.u() * this.kY, pos.w() * this.kZ);
                return this.blockPos;
            }
            case 2: {
                this.blockPos.setXyz(pos.w() * this.kX, pos.v() * this.kY, pos.u() * this.kZ);
                return this.blockPos;
            }
            default: {
                this.blockPos.setXyz(pos.u() * this.kX, pos.v() * this.kY, pos.w() * this.kZ);
                return this.blockPos;
            }
        }
    }
    
    @Override
    public void remove() {
        throw new RuntimeException("Not supported");
    }
    
    public static void main(final String[] args) {
        final gu posStart = new gu(10, 20, 30);
        final gu posEnd = new gu(30, 40, 20);
        final Iterator3d it = new Iterator3d(posStart, posEnd, 1, 1);
        while (it.hasNext()) {
            final gu blockPos = it.next();
            System.out.println(invokedynamic(makeConcatWithConstants:(Lgu;)Ljava/lang/String;, blockPos));
        }
    }
}
