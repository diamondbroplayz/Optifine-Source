// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import net.optifine.util.BiomeUtils;

public class BiomeId
{
    private final acq resourceLocation;
    private few world;
    private cnk biome;
    private static enn minecraft;
    
    private BiomeId(final acq resourceLocation) {
        this.resourceLocation = resourceLocation;
        this.world = BiomeId.minecraft.s;
        this.updateBiome();
    }
    
    private void updateBiome() {
        this.biome = null;
        final hr<cnk> registry = (hr<cnk>)BiomeUtils.getBiomeRegistry((cmm)this.world);
        if (!registry.c(this.resourceLocation)) {
            return;
        }
        this.biome = (cnk)registry.a(this.resourceLocation);
    }
    
    public cnk getBiome() {
        if (this.world != BiomeId.minecraft.s) {
            this.world = BiomeId.minecraft.s;
            this.updateBiome();
        }
        return this.biome;
    }
    
    public acq getResourceLocation() {
        return this.resourceLocation;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Lacq;)Ljava/lang/String;, this.resourceLocation);
    }
    
    public static BiomeId make(final acq resourceLocation) {
        final BiomeId bi = new BiomeId(resourceLocation);
        if (bi.biome == null) {
            return null;
        }
        return bi;
    }
    
    static {
        BiomeId.minecraft = enn.N();
    }
}
