// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class SmoothFloat
{
    private float valueLast;
    private float timeFadeUpSec;
    private float timeFadeDownSec;
    private long timeLastMs;
    
    public SmoothFloat(final float valueLast, final float timeFadeSec) {
        this(valueLast, timeFadeSec, timeFadeSec);
    }
    
    public SmoothFloat(final float valueLast, final float timeFadeUpSec, final float timeFadeDownSec) {
        this.valueLast = valueLast;
        this.timeFadeUpSec = timeFadeUpSec;
        this.timeFadeDownSec = timeFadeDownSec;
        this.timeLastMs = System.currentTimeMillis();
    }
    
    public float getValueLast() {
        return this.valueLast;
    }
    
    public float getTimeFadeUpSec() {
        return this.timeFadeUpSec;
    }
    
    public float getTimeFadeDownSec() {
        return this.timeFadeDownSec;
    }
    
    public long getTimeLastMs() {
        return this.timeLastMs;
    }
    
    public float getSmoothValue(final float value, final float timeFadeUpSec, final float timeFadeDownSec) {
        this.timeFadeUpSec = timeFadeUpSec;
        this.timeFadeDownSec = timeFadeDownSec;
        return this.getSmoothValue(value);
    }
    
    public float getSmoothValue(final float value) {
        final long timeNowMs = System.currentTimeMillis();
        final float valPrev = this.valueLast;
        final long timePrevMs = this.timeLastMs;
        final float timeDeltaSec = (timeNowMs - timePrevMs) / 1000.0f;
        final float timeFadeSec = (value >= valPrev) ? this.timeFadeUpSec : this.timeFadeDownSec;
        final float valSmooth = getSmoothValue(valPrev, value, timeDeltaSec, timeFadeSec);
        this.valueLast = valSmooth;
        this.timeLastMs = timeNowMs;
        return valSmooth;
    }
    
    public static float getSmoothValue(final float valPrev, final float value, final float timeDeltaSec, final float timeFadeSec) {
        if (timeDeltaSec <= 0.0f) {
            return valPrev;
        }
        final float valDelta = value - valPrev;
        float valSmooth;
        if (timeFadeSec > 0.0f && timeDeltaSec < timeFadeSec && Math.abs(valDelta) > 1.0E-6f) {
            final float countUpdates = timeFadeSec / timeDeltaSec;
            final float k1 = 4.61f;
            final float k2 = 0.13f;
            final float k3 = 10.0f;
            final float kCorr = k1 - 1.0f / (k2 + countUpdates / k3);
            float kTime = timeDeltaSec / timeFadeSec * kCorr;
            kTime = NumUtils.limit(kTime, 0.0f, 1.0f);
            valSmooth = valPrev + valDelta * kTime;
        }
        else {
            valSmooth = value;
        }
        return valSmooth;
    }
}
