// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

public class BetterSnow
{
    private static fwr modelSnowLayer;
    
    public static void update() {
        BetterSnow.modelSnowLayer = Config.getMinecraft().am().a().b(cpo.dN.n());
    }
    
    public static fwr getModelSnowLayer() {
        return BetterSnow.modelSnowLayer;
    }
    
    public static dcb getStateSnowLayer() {
        return cpo.dN.n();
    }
    
    public static boolean shouldRender(final clp lightReader, final dcb blockState, final gu blockPos) {
        if (!(lightReader instanceof cls)) {
            return false;
        }
        final cls blockAccess = (cls)lightReader;
        return checkBlock(blockAccess, blockState, blockPos) && hasSnowNeighbours(blockAccess, blockPos);
    }
    
    private static boolean hasSnowNeighbours(final cls blockAccess, final gu pos) {
        final cpn blockSnow = cpo.dN;
        if (blockAccess.a_(pos.e()).b() == blockSnow || blockAccess.a_(pos.f()).b() == blockSnow || blockAccess.a_(pos.g()).b() == blockSnow || blockAccess.a_(pos.h()).b() == blockSnow) {
            final dcb bsDown = blockAccess.a_(pos.d());
            if (bsDown.i(blockAccess, pos)) {
                return true;
            }
            final cpn blockDown = bsDown.b();
            if (blockDown instanceof cxh) {
                return bsDown.c((dde)cxh.b) == dda.a;
            }
            if (blockDown instanceof cwq) {
                return bsDown.c((dde)cwq.a) == ddj.a;
            }
        }
        return false;
    }
    
    private static boolean checkBlock(final cls blockAccess, final dcb blockState, final gu blockPos) {
        if (blockState.i(blockAccess, blockPos)) {
            return false;
        }
        final cpn block = blockState.b();
        if (block == cpo.dP) {
            return false;
        }
        if (block instanceof cpv && (block instanceof crr || block instanceof csl || block instanceof cun || block instanceof cwb || block instanceof cxt)) {
            return true;
        }
        if (block instanceof csh || block instanceof csi || block instanceof csm || block instanceof crf || block instanceof cxo || block instanceof cyj) {
            return true;
        }
        if (block instanceof cvq) {
            return true;
        }
        if (block instanceof cxh) {
            return blockState.c((dde)cxh.b) == dda.a;
        }
        if (block instanceof cwq) {
            return blockState.c((dde)cwq.a) == ddj.a;
        }
        if (block instanceof cpw) {
            return blockState.c((dde)cpw.I) != dcm.a;
        }
        return block instanceof ctf || block instanceof ctq || block instanceof ctx || block instanceof cye || block instanceof cyh;
    }
    
    static {
        BetterSnow.modelSnowLayer = null;
    }
}
