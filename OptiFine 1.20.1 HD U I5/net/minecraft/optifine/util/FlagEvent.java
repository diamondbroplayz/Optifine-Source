// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.HashSet;
import java.util.Set;

public class FlagEvent
{
    private static Set<String> setEvents;
    
    public static void set(final String name) {
        synchronized (FlagEvent.setEvents) {
            FlagEvent.setEvents.add(name);
        }
    }
    
    public static boolean clear(final String name) {
        synchronized (FlagEvent.setEvents) {
            return FlagEvent.setEvents.remove(name);
        }
    }
    
    public static boolean isActive(final String name) {
        synchronized (FlagEvent.setEvents) {
            return FlagEvent.setEvents.contains(name);
        }
    }
    
    public static boolean isActiveClear(final String name) {
        return clear(name);
    }
    
    static {
        FlagEvent.setEvents = new HashSet<String>();
    }
}
