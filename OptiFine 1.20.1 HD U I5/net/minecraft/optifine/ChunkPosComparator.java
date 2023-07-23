// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Comparator;

public class ChunkPosComparator implements Comparator<clt>
{
    private int chunkPosX;
    private int chunkPosZ;
    private double yawRad;
    private double pitchNorm;
    
    public ChunkPosComparator(final int chunkPosX, final int chunkPosZ, final double yawRad, final double pitchRad) {
        this.chunkPosX = chunkPosX;
        this.chunkPosZ = chunkPosZ;
        this.yawRad = yawRad;
        this.pitchNorm = 1.0 - apa.a(Math.abs(pitchRad) / 1.5707963267948966, 0.0, 1.0);
    }
    
    @Override
    public int compare(final clt cp1, final clt cp2) {
        final int distSq1 = this.getDistSq(cp1);
        final int distSq2 = this.getDistSq(cp2);
        return distSq1 - distSq2;
    }
    
    private int getDistSq(final clt cp) {
        final int dx = cp.e - this.chunkPosX;
        final int dz = cp.f - this.chunkPosZ;
        int distSq = dx * dx + dz * dz;
        final double yaw = apa.d((double)dz, (double)dx);
        double dYaw = Math.abs(yaw - this.yawRad);
        if (dYaw > 3.141592653589793) {
            dYaw = 6.283185307179586 - dYaw;
        }
        distSq *= (int)(1000.0 * (this.pitchNorm * dYaw * dYaw));
        return distSq;
    }
}
