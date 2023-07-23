// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import net.optifine.util.StrUtils;
import java.io.InputStream;
import java.io.File;

public class ShaderPackFolder implements IShaderPack
{
    protected File packFile;
    
    public ShaderPackFolder(final String name, final File file) {
        this.packFile = file;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public InputStream getResourceAsStream(final String resName) {
        try {
            final String name = StrUtils.removePrefixSuffix(resName, "/", "/");
            final File resFile = new File(this.packFile, name);
            if (!resFile.exists()) {
                return null;
            }
            return new BufferedInputStream(new FileInputStream(resFile));
        }
        catch (Exception excp) {
            return null;
        }
    }
    
    @Override
    public boolean hasDirectory(final String name) {
        final File resFile = new File(this.packFile, name.substring(1));
        return resFile.exists() && resFile.isDirectory();
    }
    
    @Override
    public String getName() {
        return this.packFile.getName();
    }
}
