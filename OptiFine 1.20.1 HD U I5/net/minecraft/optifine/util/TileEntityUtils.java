// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import net.optifine.reflect.Reflector;

public class TileEntityUtils
{
    public static String getTileEntityName(final cls blockAccess, final gu blockPos) {
        final czn te = blockAccess.c_(blockPos);
        return getTileEntityName(te);
    }
    
    public static String getTileEntityName(final czn te) {
        if (!(te instanceof beb)) {
            return null;
        }
        final beb iwn = (beb)te;
        updateTileEntityName(te);
        if (!iwn.aa()) {
            return null;
        }
        return iwn.ab().getString();
    }
    
    public static void updateTileEntityName(final czn te) {
        final gu pos = te.p();
        final sw name = getTileEntityRawName(te);
        if (name != null) {
            return;
        }
        sw nameServer = getServerTileEntityRawName(pos);
        if (nameServer == null) {
            nameServer = (sw)sw.b("");
        }
        setTileEntityRawName(te, nameServer);
    }
    
    public static sw getServerTileEntityRawName(final gu blockPos) {
        final czn tes = IntegratedServerUtils.getTileEntity(blockPos);
        if (tes == null) {
            return null;
        }
        final sw itc = getTileEntityRawName(tes);
        return itc;
    }
    
    public static sw getTileEntityRawName(final czn te) {
        if (te instanceof beb) {
            return ((beb)te).ab();
        }
        if (te instanceof czi) {
            return (sw)Reflector.getFieldValue(te, Reflector.TileEntityBeacon_customName);
        }
        return null;
    }
    
    public static boolean setTileEntityRawName(final czn te, final sw name) {
        if (te instanceof czh) {
            ((czh)te).a(name);
            return true;
        }
        if (te instanceof czd) {
            ((czd)te).a(name);
            return true;
        }
        if (te instanceof dag) {
            ((dag)te).a(name);
            return true;
        }
        if (te instanceof czi) {
            ((czi)te).a(name);
            return true;
        }
        return false;
    }
}
