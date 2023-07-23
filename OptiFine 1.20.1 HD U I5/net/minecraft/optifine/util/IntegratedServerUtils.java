// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.UUID;
import net.optifine.Config;

public class IntegratedServerUtils
{
    public static aif getWorldServer() {
        final enn mc = Config.getMinecraft();
        final cmm world = (cmm)mc.s;
        if (world == null) {
            return null;
        }
        if (!mc.Q()) {
            return null;
        }
        final fyp is = mc.S();
        if (is == null) {
            return null;
        }
        final acp<cmm> wd = (acp<cmm>)world.ac();
        if (wd == null) {
            return null;
        }
        try {
            final aif ws = is.a((acp)wd);
            return ws;
        }
        catch (NullPointerException e) {
            return null;
        }
    }
    
    public static bfj getEntity(final UUID uuid) {
        final aif ws = getWorldServer();
        if (ws == null) {
            return null;
        }
        final bfj e = ws.a(uuid);
        return e;
    }
    
    public static czn getTileEntity(final gu pos) {
        final aif ws = getWorldServer();
        if (ws == null) {
            return null;
        }
        final ddx chunk = ws.k().a(pos.u() >> 4, pos.w() >> 4, dec.n, false);
        if (chunk == null) {
            return null;
        }
        final czn te = chunk.c_(pos);
        return te;
    }
}
