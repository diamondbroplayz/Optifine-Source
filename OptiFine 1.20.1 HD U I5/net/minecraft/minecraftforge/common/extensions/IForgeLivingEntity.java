// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.common.extensions;

import net.minecraftforge.fluids.FluidType;

public interface IForgeLivingEntity extends IForgeEntity
{
    default void jumpInFluid(final FluidType type) {
    }
}
