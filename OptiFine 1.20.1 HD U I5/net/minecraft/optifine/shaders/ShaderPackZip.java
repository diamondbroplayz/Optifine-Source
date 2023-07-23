// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.Deque;
import com.google.common.base.Joiner;
import net.optifine.Config;
import java.util.ArrayDeque;
import java.util.zip.ZipEntry;
import net.optifine.util.StrUtils;
import java.io.InputStream;
import java.util.zip.ZipFile;
import java.io.File;

public class ShaderPackZip implements IShaderPack
{
    protected File packFile;
    protected ZipFile packZipFile;
    protected String baseFolder;
    
    public ShaderPackZip(final String name, final File file) {
        this.packFile = file;
        this.packZipFile = null;
        this.baseFolder = "";
    }
    
    @Override
    public void close() {
        if (this.packZipFile == null) {
            return;
        }
        try {
            this.packZipFile.close();
        }
        catch (Exception ex) {}
        this.packZipFile = null;
    }
    
    @Override
    public InputStream getResourceAsStream(final String resName) {
        try {
            if (this.packZipFile == null) {
                this.packZipFile = new ZipFile(this.packFile);
                this.baseFolder = this.detectBaseFolder(this.packZipFile);
            }
            String name = StrUtils.removePrefix(resName, "/");
            if (name.contains("..")) {
                name = this.resolveRelative(name);
            }
            final ZipEntry entry = this.packZipFile.getEntry(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.baseFolder, name));
            if (entry == null) {
                return null;
            }
            return this.packZipFile.getInputStream(entry);
        }
        catch (Exception excp) {
            return null;
        }
    }
    
    private String resolveRelative(final String name) {
        final Deque<String> stack = new ArrayDeque<String>();
        final String[] parts = Config.tokenize(name, "/");
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            if (part.equals("..")) {
                if (stack.isEmpty()) {
                    return "";
                }
                stack.removeLast();
            }
            else {
                stack.add(part);
            }
        }
        final String path = Joiner.on('/').join((Iterable)stack);
        return path;
    }
    
    private String detectBaseFolder(final ZipFile zip) {
        final ZipEntry entryShaders = zip.getEntry("shaders/");
        if (entryShaders != null && entryShaders.isDirectory()) {
            return "";
        }
        final Pattern patternFolderShaders = Pattern.compile("([^/]+/)shaders/");
        final Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = (ZipEntry)entries.nextElement();
            final String name = entry.getName();
            final Matcher matcher = patternFolderShaders.matcher(name);
            if (matcher.matches()) {
                final String base = matcher.group(1);
                if (base == null) {
                    continue;
                }
                if (base.equals("shaders/")) {
                    return "";
                }
                return base;
            }
        }
        return "";
    }
    
    @Override
    public boolean hasDirectory(final String resName) {
        try {
            if (this.packZipFile == null) {
                this.packZipFile = new ZipFile(this.packFile);
                this.baseFolder = this.detectBaseFolder(this.packZipFile);
            }
            final String name = StrUtils.removePrefix(resName, "/");
            final ZipEntry entry = this.packZipFile.getEntry(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.baseFolder, name));
            return entry != null;
        }
        catch (IOException e) {
            return false;
        }
    }
    
    @Override
    public String getName() {
        return this.packFile.getName();
    }
}
