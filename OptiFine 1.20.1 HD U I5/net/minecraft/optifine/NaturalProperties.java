// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;

public class NaturalProperties
{
    public int rotation;
    public boolean flip;
    private Map[] quadMaps;
    
    public NaturalProperties(final String type) {
        this.rotation = 1;
        this.flip = false;
        this.quadMaps = new Map[8];
        if (type.equals("4")) {
            this.rotation = 4;
            return;
        }
        if (type.equals("2")) {
            this.rotation = 2;
            return;
        }
        if (type.equals("F")) {
            this.flip = true;
            return;
        }
        if (type.equals("4F")) {
            this.rotation = 4;
            this.flip = true;
            return;
        }
        if (type.equals("2F")) {
            this.rotation = 2;
            this.flip = true;
            return;
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, type));
    }
    
    public boolean isValid() {
        return this.rotation == 2 || this.rotation == 4 || this.flip;
    }
    
    public synchronized fkr getQuad(final fkr quadIn, final int rotate, final boolean flipU) {
        int index = rotate;
        if (flipU) {
            index |= 0x4;
        }
        if (index <= 0 || index >= this.quadMaps.length) {
            return quadIn;
        }
        Map map = this.quadMaps[index];
        if (map == null) {
            map = new IdentityHashMap(1);
            this.quadMaps[index] = map;
        }
        fkr quad = map.get(quadIn);
        if (quad == null) {
            quad = this.makeQuad(quadIn, rotate, flipU);
            map.put(quadIn, quad);
        }
        return quad;
    }
    
    private fkr makeQuad(final fkr quad, int rotate, final boolean flipU) {
        int[] vertexData = quad.b();
        final int tintIndex = quad.d();
        final ha face = quad.e();
        final fuv sprite = quad.a();
        final boolean shade = quad.f();
        if (!this.isFullSprite(quad)) {
            rotate = 0;
        }
        vertexData = this.transformVertexData(vertexData, rotate, flipU);
        final fkr bq = new fkr(vertexData, tintIndex, face, sprite, shade);
        return bq;
    }
    
    private int[] transformVertexData(final int[] vertexData, final int rotate, final boolean flipU) {
        final int[] vertexData2 = vertexData.clone();
        int v2 = 4 - rotate;
        if (flipU) {
            v2 += 3;
        }
        v2 %= 4;
        final int step = vertexData2.length / 4;
        for (int v3 = 0; v3 < 4; ++v3) {
            final int pos = v3 * step;
            final int pos2 = v2 * step;
            vertexData2[pos2 + 4] = vertexData[pos + 4];
            vertexData2[pos2 + 4 + 1] = vertexData[pos + 4 + 1];
            if (flipU) {
                if (--v2 < 0) {
                    v2 = 3;
                }
            }
            else if (++v2 > 3) {
                v2 = 0;
            }
        }
        return vertexData2;
    }
    
    private boolean isFullSprite(final fkr quad) {
        final fuv sprite = quad.a();
        final float uMin = sprite.c();
        final float uMax = sprite.d();
        final float uSize = uMax - uMin;
        final float uDelta = uSize / 256.0f;
        final float vMin = sprite.g();
        final float vMax = sprite.h();
        final float vSize = vMax - vMin;
        final float vDelta = vSize / 256.0f;
        final int[] vertexData = quad.b();
        final int step = vertexData.length / 4;
        for (int i = 0; i < 4; ++i) {
            final int pos = i * step;
            final float u = Float.intBitsToFloat(vertexData[pos + 4]);
            final float v = Float.intBitsToFloat(vertexData[pos + 4 + 1]);
            if (!this.equalsDelta(u, uMin, uDelta) && !this.equalsDelta(u, uMax, uDelta)) {
                return false;
            }
            if (!this.equalsDelta(v, vMin, vDelta) && !this.equalsDelta(v, vMax, vDelta)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean equalsDelta(final float x1, final float x2, final float deltaMax) {
        final float deltaAbs = apa.e(x1 - x2);
        return deltaAbs < deltaMax;
    }
}
