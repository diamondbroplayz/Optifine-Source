// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class WorldUtils
{
    public static int getDimensionId(final cmm world) {
        if (world == null) {
            return 0;
        }
        return getDimensionId((acp<cmm>)world.ac());
    }
    
    public static int getDimensionId(final acp<cmm> dimension) {
        if (dimension == cmm.i) {
            return -1;
        }
        if (dimension == cmm.h) {
            return 0;
        }
        if (dimension == cmm.j) {
            return 1;
        }
        return 0;
    }
    
    public static boolean isNether(final cmm world) {
        return world.ac() == cmm.i;
    }
    
    public static boolean isOverworld(final cmm world) {
        final acp<cmm> dimension = (acp<cmm>)world.ac();
        return getDimensionId(dimension) == 0;
    }
    
    public static boolean isEnd(final cmm world) {
        return world.ac() == cmm.j;
    }
}
