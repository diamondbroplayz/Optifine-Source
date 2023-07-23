// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Iterator;
import com.google.common.collect.Iterables;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.Charsets;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import com.google.common.base.Splitter;

public class Lang
{
    private static final Splitter splitter;
    private static final Pattern pattern;
    
    public static void resourcesReloaded() {
        final Map localeProperties = new HashMap();
        final List<String> listFiles = new ArrayList<String>();
        final String PREFIX = "optifine/lang/";
        final String EN_US = "en_us";
        final String SUFFIX = ".lang";
        listFiles.add(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, PREFIX, EN_US, SUFFIX));
        if (!Config.getGameSettings().ag.equals(EN_US)) {
            listFiles.add(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, PREFIX, Config.getGameSettings().ag, SUFFIX));
        }
        final String[] files = listFiles.toArray(new String[listFiles.size()]);
        loadResources((ajl)Config.getDefaultResourcePack(), files, localeProperties);
        final ajl[] resourcePacks = Config.getResourcePacks();
        for (int i = 0; i < resourcePacks.length; ++i) {
            final ajl rp = resourcePacks[i];
            loadResources(rp, files, localeProperties);
        }
    }
    
    private static void loadResources(final ajl rp, final String[] files, final Map localeProperties) {
        try {
            for (int i = 0; i < files.length; ++i) {
                final String file = files[i];
                final acq loc = new acq(file);
                final akp<InputStream> supplier = (akp<InputStream>)rp.a(ajm.a, loc);
                if (supplier != null) {
                    final InputStream in = (InputStream)supplier.get();
                    if (in != null) {
                        loadLocaleData(in, localeProperties);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadLocaleData(final InputStream is, final Map localeProperties) throws IOException {
        final Iterator it = IOUtils.readLines(is, Charsets.UTF_8).iterator();
        is.close();
        while (it.hasNext()) {
            final String line = it.next();
            if (!line.isEmpty() && line.charAt(0) != '#') {
                final String[] parts = (String[])Iterables.toArray(Lang.splitter.split((CharSequence)line), (Class)String.class);
                if (parts == null || parts.length != 2) {
                    continue;
                }
                final String key = parts[0];
                final String value = Lang.pattern.matcher(parts[1]).replaceAll("%$1s");
                localeProperties.put(key, value);
            }
        }
    }
    
    public static void loadResources(final akx resourceManager, final String langCode, final Map<String, String> map) {
        try {
            final String pathLang = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, langCode);
            final acq locLang = new acq(pathLang);
            final akv res = resourceManager.getResourceOrThrow(locLang);
            final InputStream is = res.d();
            loadLocaleData(is, map);
        }
        catch (IOException ex) {}
    }
    
    public static String get(final String key) {
        return fvz.a(key, new Object[0]);
    }
    
    public static tj getComponent(final String key) {
        return sw.c(key);
    }
    
    public static String get(final String key, final String def) {
        final String str = fvz.a(key, new Object[0]);
        if (str == null || str.equals(key)) {
            return def;
        }
        return str;
    }
    
    public static String getOn() {
        return fvz.a("options.on", new Object[0]);
    }
    
    public static String getOff() {
        return fvz.a("options.off", new Object[0]);
    }
    
    public static String getFast() {
        return fvz.a("options.graphics.fast", new Object[0]);
    }
    
    public static String getFancy() {
        return fvz.a("options.graphics.fancy", new Object[0]);
    }
    
    public static String getDefault() {
        return fvz.a("generator.minecraft.normal", new Object[0]);
    }
    
    static {
        splitter = Splitter.on('=').limit(2);
        pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    }
}
