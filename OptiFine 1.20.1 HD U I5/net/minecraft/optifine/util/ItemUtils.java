// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class ItemUtils
{
    public static cfu getItem(final acq loc) {
        if (!jb.i.c(loc)) {
            return null;
        }
        return (cfu)jb.i.a(loc);
    }
    
    public static int getId(final cfu item) {
        return jb.i.a((Object)item);
    }
}
