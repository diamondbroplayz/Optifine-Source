// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import net.optifine.util.EnchantmentUtils;

public class ParserEnchantmentId implements IParserInt
{
    @Override
    public int parse(final String str, final int defVal) {
        final acq loc = new acq(str);
        final ckg en = EnchantmentUtils.getEnchantment(loc);
        if (en == null) {
            return defVal;
        }
        return jb.g.a((Object)en);
    }
}
