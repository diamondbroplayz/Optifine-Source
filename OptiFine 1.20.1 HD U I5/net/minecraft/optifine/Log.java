// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.logging.log4j.Logger;

public class Log
{
    private static final Logger LOGGER;
    public static final boolean logDetail;
    
    public static void detail(final String s) {
        if (Log.logDetail) {
            Log.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
    }
    
    public static void dbg(final String s) {
        Log.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
    }
    
    public static void warn(final String s) {
        Log.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
    }
    
    public static void warn(final String s, final Throwable t) {
        limitStackTrace(t);
        Log.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s), t);
    }
    
    public static void error(final String s) {
        Log.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
    }
    
    public static void error(final String s, final Throwable t) {
        limitStackTrace(t);
        Log.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s), t);
    }
    
    public static void log(final String s) {
        dbg(s);
    }
    
    private static void limitStackTrace(final Throwable t) {
        final StackTraceElement[] stes = t.getStackTrace();
        if (stes == null) {
            return;
        }
        if (stes.length <= 35) {
            return;
        }
        final List<StackTraceElement> listStes = new ArrayList<StackTraceElement>(Arrays.asList(stes));
        final List<StackTraceElement> listStes2 = new ArrayList<StackTraceElement>();
        listStes2.addAll(listStes.subList(0, 30));
        listStes2.add(new StackTraceElement("..", "", null, 0));
        listStes2.addAll(listStes.subList(listStes.size() - 5, listStes.size()));
        final StackTraceElement[] stes2 = listStes2.toArray(new StackTraceElement[listStes2.size()]);
        t.setStackTrace(stes2);
    }
    
    static {
        LOGGER = LogManager.getLogger();
        logDetail = System.getProperty("log.detail", "false").equals("true");
    }
}
