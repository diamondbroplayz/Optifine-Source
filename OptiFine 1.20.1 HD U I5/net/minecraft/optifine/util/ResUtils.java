// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Locale;
import java.util.Enumeration;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Set;
import java.util.Collection;
import java.util.Arrays;
import net.optifine.Config;
import java.util.LinkedHashSet;

public class ResUtils
{
    public static String[] collectFiles(final String prefix, final String suffix) {
        return collectFiles(new String[] { prefix }, new String[] { suffix });
    }
    
    public static String[] collectFiles(final String[] prefixes, final String[] suffixes) {
        final Set<String> setPaths = new LinkedHashSet<String>();
        final ajl[] rps = Config.getResourcePacks();
        for (int i = 0; i < rps.length; ++i) {
            final ajl rp = rps[i];
            final String[] ps = collectFiles(rp, prefixes, suffixes, null);
            setPaths.addAll(Arrays.asList(ps));
        }
        final String[] paths = setPaths.toArray(new String[setPaths.size()]);
        return paths;
    }
    
    public static String[] collectFiles(final ajl rp, final String prefix, final String suffix, final String[] defaultPaths) {
        return collectFiles(rp, new String[] { prefix }, new String[] { suffix }, defaultPaths);
    }
    
    public static String[] collectFiles(final ajl rp, final String[] prefixes, final String[] suffixes) {
        return collectFiles(rp, prefixes, suffixes, null);
    }
    
    public static String[] collectFiles(final ajl rp, final String[] prefixes, final String[] suffixes, final String[] defaultPaths) {
        if (rp instanceof ajo) {
            return collectFilesFixed(rp, defaultPaths);
        }
        File tpFile = null;
        if (rp instanceof ajk) {
            final ajk fpr = (ajk)rp;
            tpFile = fpr.e;
        }
        else {
            if (!(rp instanceof ajn)) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Lajl;)Ljava/lang/String;, rp));
                return new String[0];
            }
            final ajn ppr = (ajn)rp;
            tpFile = ppr.e.toFile();
        }
        if (tpFile == null) {
            return new String[0];
        }
        if (tpFile.isDirectory()) {
            return collectFilesFolder(tpFile, "", prefixes, suffixes);
        }
        if (tpFile.isFile()) {
            return collectFilesZIP(tpFile, prefixes, suffixes);
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/io/File;)Ljava/lang/String;, tpFile));
        return new String[0];
    }
    
    private static String[] collectFilesFixed(final ajl rp, final String[] paths) {
        if (paths == null) {
            return new String[0];
        }
        final List list = new ArrayList();
        for (int i = 0; i < paths.length; ++i) {
            final String path = paths[i];
            if (!isLowercase(path)) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            }
            else {
                final acq loc = new acq(path);
                if (Config.hasResource(rp, loc)) {
                    list.add(path);
                }
            }
        }
        final String[] pathArr = list.toArray(new String[list.size()]);
        return pathArr;
    }
    
    private static String[] collectFilesFolder(final File tpFile, final String basePath, final String[] prefixes, final String[] suffixes) {
        final List list = new ArrayList();
        final String prefixAssets = "assets/minecraft/";
        final File[] files = tpFile.listFiles();
        if (files == null) {
            return new String[0];
        }
        for (int i = 0; i < files.length; ++i) {
            final File file = files[i];
            if (file.isFile()) {
                String name = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, file.getName());
                if (name.startsWith(prefixAssets)) {
                    name = name.substring(prefixAssets.length());
                    if (StrUtils.startsWith(name, prefixes)) {
                        if (StrUtils.endsWith(name, suffixes)) {
                            if (!isLowercase(name)) {
                                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
                            }
                            else {
                                list.add(name);
                            }
                        }
                    }
                }
            }
            else if (file.isDirectory()) {
                final String dirPath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, file.getName());
                final String[] names = collectFilesFolder(file, dirPath, prefixes, suffixes);
                for (int n = 0; n < names.length; ++n) {
                    final String name2 = names[n];
                    list.add(name2);
                }
            }
        }
        final String[] names2 = list.toArray(new String[list.size()]);
        return names2;
    }
    
    private static String[] collectFilesZIP(final File tpFile, final String[] prefixes, final String[] suffixes) {
        final List list = new ArrayList();
        final String prefixAssets = "assets/minecraft/";
        try {
            final ZipFile zf = new ZipFile(tpFile);
            final Enumeration en = zf.entries();
            while (en.hasMoreElements()) {
                final ZipEntry ze = en.nextElement();
                String name = ze.getName();
                if (!name.startsWith(prefixAssets)) {
                    continue;
                }
                name = name.substring(prefixAssets.length());
                if (!StrUtils.startsWith(name, prefixes)) {
                    continue;
                }
                if (!StrUtils.endsWith(name, suffixes)) {
                    continue;
                }
                if (!isLowercase(name)) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
                }
                else {
                    list.add(name);
                }
            }
            zf.close();
            final String[] names = list.toArray(new String[list.size()]);
            return names;
        }
        catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
    
    private static boolean isLowercase(final String str) {
        return str.equals(str.toLowerCase(Locale.ROOT));
    }
    
    public static Properties readProperties(final String path, final String module) {
        final acq loc = new acq(path);
        try {
            final InputStream in = Config.getResourceStream(loc);
            if (in == null) {
                return null;
            }
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, module, path));
            return props;
        }
        catch (FileNotFoundException e) {
            return null;
        }
        catch (IOException e2) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, module, path));
            return null;
        }
    }
    
    public static Properties readProperties(final InputStream in, final String module) {
        if (in == null) {
            return null;
        }
        try {
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            return props;
        }
        catch (FileNotFoundException e) {
            return null;
        }
        catch (IOException e2) {
            return null;
        }
    }
}
