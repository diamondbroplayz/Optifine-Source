// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.registries;

public interface IRegistryDelegate<T>
{
    T get();
    
    acq name();
    
    Class<T> type();
}
