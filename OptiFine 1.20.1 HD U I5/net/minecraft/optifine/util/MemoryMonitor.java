// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemoryMonitor
{
    private static long startTimeMs;
    private static long startMemory;
    private static long lastTimeMs;
    private static long lastMemory;
    private static boolean gcEvent;
    private static long memBytesSec;
    private static long memBytesSecAvg;
    private static List<Long> listMemBytesSec;
    private static long gcBytesSec;
    private static long MB;
    
    public static void update() {
        final long timeMs = System.currentTimeMillis();
        final long memory = getMemoryUsed();
        MemoryMonitor.gcEvent = (memory < MemoryMonitor.lastMemory);
        if (MemoryMonitor.gcEvent) {
            MemoryMonitor.gcBytesSec = MemoryMonitor.memBytesSec;
            MemoryMonitor.startTimeMs = timeMs;
            MemoryMonitor.startMemory = memory;
        }
        final long timeDiffMs = timeMs - MemoryMonitor.startTimeMs;
        final long memoryDiff = memory - MemoryMonitor.startMemory;
        final double timeDiffSec = timeDiffMs / 1000.0;
        if (memoryDiff >= 0L && timeDiffSec > 0.0) {
            MemoryMonitor.memBytesSec = (long)(memoryDiff / timeDiffSec);
            MemoryMonitor.listMemBytesSec.add(MemoryMonitor.memBytesSec);
            if (timeMs / 1000L != MemoryMonitor.lastTimeMs / 1000L) {
                long sumBytes = 0L;
                for (final Long bytes : MemoryMonitor.listMemBytesSec) {
                    sumBytes += bytes;
                }
                MemoryMonitor.memBytesSecAvg = sumBytes / MemoryMonitor.listMemBytesSec.size();
                MemoryMonitor.listMemBytesSec.clear();
            }
        }
        MemoryMonitor.lastTimeMs = timeMs;
        MemoryMonitor.lastMemory = memory;
    }
    
    private static long getMemoryUsed() {
        final Runtime r = Runtime.getRuntime();
        return r.totalMemory() - r.freeMemory();
    }
    
    public static long getStartTimeMs() {
        return MemoryMonitor.startTimeMs;
    }
    
    public static long getStartMemoryMb() {
        return MemoryMonitor.startMemory / MemoryMonitor.MB;
    }
    
    public static boolean isGcEvent() {
        return MemoryMonitor.gcEvent;
    }
    
    public static long getAllocationRateMb() {
        return MemoryMonitor.memBytesSec / MemoryMonitor.MB;
    }
    
    public static long getAllocationRateAvgMb() {
        return MemoryMonitor.memBytesSecAvg / MemoryMonitor.MB;
    }
    
    public static long getGcRateMb() {
        return MemoryMonitor.gcBytesSec / MemoryMonitor.MB;
    }
    
    static {
        MemoryMonitor.startTimeMs = System.currentTimeMillis();
        MemoryMonitor.startMemory = getMemoryUsed();
        MemoryMonitor.lastTimeMs = MemoryMonitor.startTimeMs;
        MemoryMonitor.lastMemory = MemoryMonitor.startMemory;
        MemoryMonitor.gcEvent = false;
        MemoryMonitor.memBytesSec = 0L;
        MemoryMonitor.memBytesSecAvg = 0L;
        MemoryMonitor.listMemBytesSec = new ArrayList<Long>();
        MemoryMonitor.gcBytesSec = 0L;
        MemoryMonitor.MB = 1048576L;
    }
}
