// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeUtils
{
    private static Unsafe unsafe;
    private static boolean checked;
    
    private static Unsafe getUnsafe() {
        if (UnsafeUtils.checked) {
            return UnsafeUtils.unsafe;
        }
        try {
            final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            UnsafeUtils.unsafe = (Unsafe)unsafeField.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return UnsafeUtils.unsafe;
    }
    
    public static void setStaticInt(final Field field, final int value) {
        if (field == null) {
            return;
        }
        if (field.getType() != Integer.TYPE) {
            return;
        }
        if (!Modifier.isStatic(field.getModifiers())) {
            return;
        }
        final Unsafe us = getUnsafe();
        if (us == null) {
            return;
        }
        final Object fieldBase = UnsafeUtils.unsafe.staticFieldBase(field);
        final long fieldOffset = UnsafeUtils.unsafe.staticFieldOffset(field);
        if (fieldBase == null) {
            return;
        }
        UnsafeUtils.unsafe.putInt(fieldBase, fieldOffset, value);
    }
}
