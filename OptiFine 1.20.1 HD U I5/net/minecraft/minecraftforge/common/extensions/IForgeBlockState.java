// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.common.extensions;

public interface IForgeBlockState
{
    default dcb self() {
        return (dcb)this;
    }
    
    default int getLightEmission(final cls level, final gu pos) {
        return this.self().h();
    }
}
