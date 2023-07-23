// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

public class CustomColorFader
{
    private eei color;
    private long timeUpdate;
    
    public CustomColorFader() {
        this.color = null;
        this.timeUpdate = System.currentTimeMillis();
    }
    
    public eei getColor(final double x, final double y, final double z) {
        if (this.color == null) {
            return this.color = new eei(x, y, z);
        }
        final long timeNow = System.currentTimeMillis();
        final long timeDiff = timeNow - this.timeUpdate;
        if (timeDiff == 0L) {
            return this.color;
        }
        this.timeUpdate = timeNow;
        if (Math.abs(x - this.color.c) < 0.004 && Math.abs(y - this.color.d) < 0.004 && Math.abs(z - this.color.e) < 0.004) {
            return this.color;
        }
        double k = timeDiff * 0.001;
        k = Config.limit(k, 0.0, 1.0);
        final double dx = x - this.color.c;
        final double dy = y - this.color.d;
        final double dz = z - this.color.e;
        final double xn = this.color.c + dx * k;
        final double yn = this.color.d + dy * k;
        final double zn = this.color.e + dz * k;
        return this.color = new eei(xn, yn, zn);
    }
}
