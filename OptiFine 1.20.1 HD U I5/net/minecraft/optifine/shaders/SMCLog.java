// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SMCLog
{
    private static final Logger LOGGER;
    private static final String PREFIX = "[Shaders] ";
    
    public static void severe(final String message) {
        SMCLog.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, message));
    }
    
    public static void warning(final String message) {
        SMCLog.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, message));
    }
    
    public static void info(final String message) {
        SMCLog.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, message));
    }
    
    public static void fine(final String message) {
        SMCLog.LOGGER.debug(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, message));
    }
    
    public static void severe(final String format, final Object... args) {
        final String message = String.format(format, args);
        SMCLog.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, message));
    }
    
    public static void warning(final String format, final Object... args) {
        final String message = String.format(format, args);
        SMCLog.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, message));
    }
    
    public static void info(final String format, final Object... args) {
        final String message = String.format(format, args);
        SMCLog.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, message));
    }
    
    public static void fine(final String format, final Object... args) {
        final String message = String.format(format, args);
        SMCLog.LOGGER.debug(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, message));
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
