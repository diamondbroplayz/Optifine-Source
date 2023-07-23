// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.resource;

import java.util.Collection;
import java.util.function.Predicate;
import java.nio.file.Path;
import java.util.function.BiFunction;
import net.minecraftforge.common.ForgeConfigSpec;

public class ResourceCacheManager
{
    public ResourceCacheManager(final boolean supportsReloading, final ForgeConfigSpec.BooleanValue indexOffThreadConfig, final BiFunction<ajm, String, Path> pathBuilder) {
    }
    
    public ResourceCacheManager(final boolean supportsReloading, final String indexOnThreadConfigurationKey, final BiFunction<ajm, String, Path> pathBuilder) {
    }
    
    public static boolean shouldUseCache() {
        return false;
    }
    
    public boolean hasCached(final ajm packType, final String namespace) {
        return false;
    }
    
    public Collection<acq> getResources(final ajm type, final String resourceNamespace, final Path inputPath, final Predicate<acq> filter) {
        return null;
    }
    
    public void index(final String nameSpace) {
    }
}
