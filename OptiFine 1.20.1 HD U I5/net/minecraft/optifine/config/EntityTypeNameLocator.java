// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import net.optifine.util.EntityTypeUtils;

public class EntityTypeNameLocator implements IObjectLocator<String>
{
    public String getObject(final acq loc) {
        final bfn type = EntityTypeUtils.getEntityType(loc);
        if (type == null) {
            return null;
        }
        return type.g();
    }
    
    public static String getEntityTypeName(final bfj entity) {
        return entity.ae().g();
    }
}
