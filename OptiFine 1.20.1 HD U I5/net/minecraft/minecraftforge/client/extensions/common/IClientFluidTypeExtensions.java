// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.client.extensions.common;

import net.minecraftforge.fluids.FluidType;

public interface IClientFluidTypeExtensions
{
    public static final IClientFluidTypeExtensions DUMMY = new IClientFluidTypeExtensions$1();
    
    default IClientFluidTypeExtensions of(final dxe fluidState) {
        return IClientFluidTypeExtensions.DUMMY;
    }
    
    default IClientFluidTypeExtensions of(final FluidType fluidType) {
        return IClientFluidTypeExtensions.DUMMY;
    }
    
    default int getColorTint() {
        return -1;
    }
    
    default void renderOverlay(final enn mc, final eij stack) {
    }
    
    default int getTintColor(final dxe state, final clp getter, final gu pos) {
        return this.getColorTint();
    }
}
