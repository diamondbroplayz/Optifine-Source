// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.common.capabilities;

public abstract class CapabilityProvider<B>
{
    protected CapabilityProvider(final Class<B> baseClass) {
    }
    
    public final void gatherCapabilities() {
    }
    
    protected final CapabilityDispatcher getCapabilities() {
        return null;
    }
    
    protected final void deserializeCaps(final qr tag) {
    }
    
    protected final qr serializeCaps() {
        return null;
    }
    
    public void invalidateCaps() {
    }
}
