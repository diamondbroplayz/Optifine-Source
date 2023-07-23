// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import net.optifine.util.ItemUtils;

public class ItemLocator implements IObjectLocator<cfu>
{
    public cfu getObject(final acq loc) {
        final cfu item = ItemUtils.getItem(loc);
        return item;
    }
}
