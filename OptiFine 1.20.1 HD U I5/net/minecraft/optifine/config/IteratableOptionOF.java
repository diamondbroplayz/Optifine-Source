// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

public class IteratableOptionOF extends enq
{
    public IteratableOptionOF(final String nameIn) {
        super(nameIn, enq.a(), (labelIn, valueIn) -> valueIn ? sv.b : sv.c, (enq.n)enq.a, (Object)false, valueIn -> {});
    }
    
    public void nextOptionValue(final int dirIn) {
        final enr gameSetings = enn.N().m;
        gameSetings.setOptionValueOF((enq)this, dirIn);
    }
    
    public sw getOptionText() {
        final enr gameSetings = enn.N().m;
        return gameSetings.getKeyComponentOF((enq)this);
    }
}
