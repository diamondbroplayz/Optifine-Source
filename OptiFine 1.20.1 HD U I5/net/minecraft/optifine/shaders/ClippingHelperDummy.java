// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import org.joml.Matrix4f;

public class ClippingHelperDummy extends fmw
{
    public ClippingHelperDummy() {
        super(new Matrix4f(), new Matrix4f());
    }
    
    public boolean a(final eed aabbIn) {
        return true;
    }
    
    public boolean isBoxInFrustumFully(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        return true;
    }
}
