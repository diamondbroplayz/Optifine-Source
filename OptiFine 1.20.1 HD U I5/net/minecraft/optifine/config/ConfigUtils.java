// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.optifine.util.PropertiesOrdered;
import net.optifine.Config;
import java.util.Properties;

public class ConfigUtils
{
    public static String readString(final String fileName, final String property) {
        final Properties props = readProperties(fileName);
        if (props == null) {
            return null;
        }
        String val = props.getProperty(property);
        if (val != null) {
            val = val.trim();
        }
        return val;
    }
    
    public static Properties readProperties(final String fileName) {
        try {
            final acq loc = new acq(fileName);
            final InputStream in = Config.getResourceStream(loc);
            if (in == null) {
                return null;
            }
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            return props;
        }
        catch (FileNotFoundException e2) {
            return null;
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
            return null;
        }
    }
}
