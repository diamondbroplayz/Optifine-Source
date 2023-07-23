// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.fml.common.registry;

public interface RegistryDelegate<T>
{
    T get();
    
    acq name();
    
    Class<T> type();
}
