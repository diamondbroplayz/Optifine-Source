// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Comparator;

public class DisplayModeComparator implements Comparator
{
    @Override
    public int compare(final Object o1, final Object o2) {
        final ehm dm1 = (ehm)o1;
        final ehm dm2 = (ehm)o2;
        if (dm1.a() != dm2.a()) {
            return dm1.a() - dm2.a();
        }
        if (dm1.b() != dm2.b()) {
            return dm1.b() - dm2.b();
        }
        final int bits1 = dm1.c() + dm1.d() + dm1.e();
        final int bits2 = dm2.c() + dm2.d() + dm2.e();
        if (bits1 != bits2) {
            return bits1 - bits2;
        }
        if (dm1.f() != dm2.f()) {
            return dm1.f() - dm2.f();
        }
        return 0;
    }
}
