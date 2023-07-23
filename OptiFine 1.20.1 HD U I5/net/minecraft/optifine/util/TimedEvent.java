// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.HashMap;
import java.util.Map;

public class TimedEvent
{
    private static Map<String, Long> mapEventTimes;
    
    public static boolean isActive(final String name, final long timeIntervalMs) {
        synchronized (TimedEvent.mapEventTimes) {
            final long timeNowMs = System.currentTimeMillis();
            Long timeLastMsObj = TimedEvent.mapEventTimes.get(name);
            if (timeLastMsObj == null) {
                timeLastMsObj = new Long(timeNowMs);
                TimedEvent.mapEventTimes.put(name, timeLastMsObj);
            }
            final long timeLastMs = timeLastMsObj;
            if (timeNowMs < timeLastMs + timeIntervalMs) {
                return false;
            }
            TimedEvent.mapEventTimes.put(name, new Long(timeNowMs));
            return true;
        }
    }
    
    static {
        TimedEvent.mapEventTimes = new HashMap<String, Long>();
    }
}
