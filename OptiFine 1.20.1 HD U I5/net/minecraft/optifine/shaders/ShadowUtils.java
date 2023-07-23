// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

public class ShadowUtils
{
    public static Iterator<fmp.c> makeShadowChunkIterator(final few world, final double partialTicks, final bfj viewEntity, final int renderDistanceChunks, final fkl viewFrustum) {
        final float shadowRenderDistance = Shaders.getShadowRenderDistance();
        if (shadowRenderDistance <= 0.0f || shadowRenderDistance >= (renderDistanceChunks - 1) * 16) {
            final List<fmp.c> listChunks = Arrays.asList(viewFrustum.f);
            final Iterator<fmp.c> it = listChunks.iterator();
            return it;
        }
        final int shadowDistanceChunks = apa.f(shadowRenderDistance / 16.0f) + 1;
        final float car = world.a((float)partialTicks);
        final float sunTiltRad = Shaders.sunPathRotation * apa.deg2Rad;
        final float sar = (car > apa.PId2 && car < 3.0f * apa.PId2) ? (car + 3.1415927f) : car;
        final float dx = -apa.a(sar);
        final float dy = apa.b(sar) * apa.b(sunTiltRad);
        final float dz = -apa.b(sar) * apa.a(sunTiltRad);
        final gu posEntity = new gu(apa.a(viewEntity.dn()) >> 4, apa.a(viewEntity.dp()) >> 4, apa.a(viewEntity.dt()) >> 4);
        final gu posStart = posEntity.b((int)(-dx * shadowDistanceChunks), (int)(-dy * shadowDistanceChunks), (int)(-dz * shadowDistanceChunks));
        final gu posEnd = posEntity.b((int)(dx * renderDistanceChunks), (int)(dy * renderDistanceChunks), (int)(dz * renderDistanceChunks));
        final IteratorRenderChunks it2 = new IteratorRenderChunks(viewFrustum, posStart, posEnd, shadowDistanceChunks, shadowDistanceChunks);
        return it2;
    }
}
