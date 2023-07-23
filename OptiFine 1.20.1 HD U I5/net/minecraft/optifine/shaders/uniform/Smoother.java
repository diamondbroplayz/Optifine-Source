// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import java.util.HashMap;
import net.optifine.util.CounterInt;
import net.optifine.util.SmoothFloat;
import java.util.Map;

public class Smoother
{
    private static Map<Integer, SmoothFloat> mapSmoothValues;
    private static CounterInt counterIds;
    
    public static float getSmoothValue(final int id, final float value, final float timeFadeUpSec, final float timeFadeDownSec) {
        synchronized (Smoother.mapSmoothValues) {
            final Integer key = id;
            SmoothFloat sf = Smoother.mapSmoothValues.get(key);
            if (sf == null) {
                sf = new SmoothFloat(value, timeFadeUpSec, timeFadeDownSec);
                Smoother.mapSmoothValues.put(key, sf);
            }
            final float valueSmooth = sf.getSmoothValue(value, timeFadeUpSec, timeFadeDownSec);
            return valueSmooth;
        }
    }
    
    public static int getNextId() {
        synchronized (Smoother.counterIds) {
            return Smoother.counterIds.nextValue();
        }
    }
    
    public static void resetValues() {
        synchronized (Smoother.mapSmoothValues) {
            Smoother.mapSmoothValues.clear();
        }
    }
    
    static {
        Smoother.mapSmoothValues = new HashMap<Integer, SmoothFloat>();
        Smoother.counterIds = new CounterInt(1);
    }
}
