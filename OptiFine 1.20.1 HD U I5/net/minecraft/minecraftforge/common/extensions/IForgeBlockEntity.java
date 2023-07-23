// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.common.extensions;

public interface IForgeBlockEntity
{
    public static final eed INFINITE_EXTENT_AABB = new eed(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    
    qr getPersistentData();
    
    default void requestModelDataUpdate() {
    }
    
    default void onChunkUnloaded() {
    }
}
