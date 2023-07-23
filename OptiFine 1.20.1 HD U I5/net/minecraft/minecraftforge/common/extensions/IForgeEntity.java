// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.common.extensions;

public interface IForgeEntity
{
    default boolean isAddedToWorld() {
        return false;
    }
}
