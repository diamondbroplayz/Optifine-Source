// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.texture;

import java.util.function.Predicate;
import java.util.Set;

public class SpriteSourceCollector implements fuz.a
{
    private Set<acq> spriteNames;
    
    public SpriteSourceCollector(final Set<acq> spriteNames) {
        this.spriteNames = spriteNames;
    }
    
    public void a(final acq locIn, final fuz.b supplierIn) {
        this.spriteNames.add(locIn);
    }
    
    public void a(final Predicate<acq> checkIn) {
    }
    
    public Set<acq> getSpriteNames() {
        return this.spriteNames;
    }
}
