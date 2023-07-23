// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Comparator;

public class VideoModeComparator implements Comparator<ehm>
{
    @Override
    public int compare(final ehm vm1, final ehm vm2) {
        if (vm1.a() != vm2.a()) {
            return vm1.a() - vm2.a();
        }
        if (vm1.b() != vm2.b()) {
            return vm1.b() - vm2.b();
        }
        if (vm1.f() != vm2.f()) {
            return vm1.f() - vm2.f();
        }
        final int cols1 = vm1.c() + vm1.d() + vm1.e();
        final int cols2 = vm2.c() + vm2.d() + vm2.e();
        if (cols1 != cols2) {
            return cols1 - cols2;
        }
        return 0;
    }
}
