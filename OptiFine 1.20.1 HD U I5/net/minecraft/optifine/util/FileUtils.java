// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class FileUtils
{
    public static List<String> collectFiles(final File folder, final boolean recursive) {
        final List<String> files = new ArrayList<String>();
        collectFiles(folder, "", files, recursive);
        return files;
    }
    
    public static void collectFiles(final File folder, final String basePath, final List<String> list, final boolean recursive) {
        final File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; ++i) {
            final File file = files[i];
            if (file.isFile()) {
                final String name = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, file.getName());
                list.add(name);
            }
            else if (recursive && file.isDirectory()) {
                final String dirPath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, file.getName());
                collectFiles(file, dirPath, list, recursive);
            }
        }
    }
}
