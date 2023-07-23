// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.Config;
import java.io.InputStream;

public class ShaderPackDefault implements IShaderPack
{
    @Override
    public void close() {
    }
    
    @Override
    public InputStream getResourceAsStream(final String resName) {
        return Config.getOptiFineResourceStream(resName);
    }
    
    @Override
    public String getName() {
        return "(internal)";
    }
    
    @Override
    public boolean hasDirectory(final String name) {
        return false;
    }
}
