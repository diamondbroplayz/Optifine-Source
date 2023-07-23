// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.client.extensions;

import java.util.function.Function;

public interface IForgeModelBaker
{
    fwr bake(final acq p0, final fwz p1, final Function<fwu, fuv> p2);
    
    Function<fwu, fuv> getModelTextureGetter();
}
