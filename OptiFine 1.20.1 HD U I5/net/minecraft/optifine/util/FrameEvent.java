// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.HashMap;
import java.util.Map;

public class FrameEvent
{
    private static Map<String, Integer> mapEventFrames;
    
    public static boolean isActive(final String name, final int frameInterval) {
        synchronized (FrameEvent.mapEventFrames) {
            final int frameCount = enn.N().f.getFrameCount();
            if (frameCount <= 0) {
                return false;
            }
            Integer frameCountLastObj = FrameEvent.mapEventFrames.get(name);
            if (frameCountLastObj == null) {
                frameCountLastObj = new Integer(frameCount);
                FrameEvent.mapEventFrames.put(name, frameCountLastObj);
            }
            final int frameCountLast = frameCountLastObj;
            if (frameCount > frameCountLast && frameCount < frameCountLast + frameInterval) {
                return false;
            }
            FrameEvent.mapEventFrames.put(name, new Integer(frameCount));
            return true;
        }
    }
    
    static {
        FrameEvent.mapEventFrames = new HashMap<String, Integer>();
    }
}
