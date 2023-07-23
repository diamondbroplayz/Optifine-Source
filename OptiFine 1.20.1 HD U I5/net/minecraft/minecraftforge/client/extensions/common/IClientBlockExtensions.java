// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.client.extensions.common;

public interface IClientBlockExtensions
{
    public static final IClientBlockExtensions DUMMY = new IClientBlockExtensions$1();
    
    default IClientBlockExtensions of(final dcb blockState) {
        return IClientBlockExtensions.DUMMY;
    }
    
    default boolean addDestroyEffects(final dcb state, final cmm Level, final gu pos, final fho manager) {
        return false;
    }
    
    default boolean addHitEffects(final dcb state, final cmm Level, final eeg target, final fho manager) {
        return false;
    }
}
