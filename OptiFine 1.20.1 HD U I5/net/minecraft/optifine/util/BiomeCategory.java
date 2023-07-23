// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public enum BiomeCategory
{
    NONE("none"), 
    TAIGA("taiga"), 
    EXTREME_HILLS("extreme_hills"), 
    JUNGLE("jungle"), 
    MESA("mesa"), 
    PLAINS("plains"), 
    SAVANNA("savanna"), 
    ICY("icy"), 
    THEEND("the_end"), 
    BEACH("beach"), 
    FOREST("forest"), 
    OCEAN("ocean"), 
    DESERT("desert"), 
    RIVER("river"), 
    SWAMP("swamp"), 
    MUSHROOM("mushroom"), 
    NETHER("nether"), 
    UNDERGROUND("underground"), 
    MOUNTAIN("mountain");
    
    private String name;
    
    private BiomeCategory(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    private static /* synthetic */ BiomeCategory[] $values() {
        return new BiomeCategory[] { BiomeCategory.NONE, BiomeCategory.TAIGA, BiomeCategory.EXTREME_HILLS, BiomeCategory.JUNGLE, BiomeCategory.MESA, BiomeCategory.PLAINS, BiomeCategory.SAVANNA, BiomeCategory.ICY, BiomeCategory.THEEND, BiomeCategory.BEACH, BiomeCategory.FOREST, BiomeCategory.OCEAN, BiomeCategory.DESERT, BiomeCategory.RIVER, BiomeCategory.SWAMP, BiomeCategory.MUSHROOM, BiomeCategory.NETHER, BiomeCategory.UNDERGROUND, BiomeCategory.MOUNTAIN };
    }
    
    static {
        $VALUES = $values();
    }
}
