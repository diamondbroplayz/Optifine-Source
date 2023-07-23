// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import java.util.ArrayList;
import java.util.List;

public class ShaderUniforms
{
    private final List<ShaderUniformBase> listUniforms;
    
    public ShaderUniforms() {
        this.listUniforms = new ArrayList<ShaderUniformBase>();
    }
    
    public void setProgram(final int program) {
        for (int i = 0; i < this.listUniforms.size(); ++i) {
            final ShaderUniformBase su = this.listUniforms.get(i);
            su.setProgram(program);
        }
    }
    
    public void reset() {
        for (int i = 0; i < this.listUniforms.size(); ++i) {
            final ShaderUniformBase su = this.listUniforms.get(i);
            su.reset();
        }
    }
    
    public ShaderUniform1i make1i(final String name) {
        final ShaderUniform1i su = new ShaderUniform1i(name);
        this.listUniforms.add(su);
        return su;
    }
    
    public ShaderUniform2i make2i(final String name) {
        final ShaderUniform2i su = new ShaderUniform2i(name);
        this.listUniforms.add(su);
        return su;
    }
    
    public ShaderUniform4i make4i(final String name) {
        final ShaderUniform4i su = new ShaderUniform4i(name);
        this.listUniforms.add(su);
        return su;
    }
    
    public ShaderUniform1f make1f(final String name) {
        final ShaderUniform1f su = new ShaderUniform1f(name);
        this.listUniforms.add(su);
        return su;
    }
    
    public ShaderUniform3f make3f(final String name) {
        final ShaderUniform3f su = new ShaderUniform3f(name);
        this.listUniforms.add(su);
        return su;
    }
    
    public ShaderUniform4f make4f(final String name) {
        final ShaderUniform4f su = new ShaderUniform4f(name);
        this.listUniforms.add(su);
        return su;
    }
    
    public ShaderUniformM3 makeM3(final String name) {
        final ShaderUniformM3 su = new ShaderUniformM3(name);
        this.listUniforms.add(su);
        return su;
    }
    
    public ShaderUniformM4 makeM4(final String name) {
        final ShaderUniformM4 su = new ShaderUniformM4(name);
        this.listUniforms.add(su);
        return su;
    }
}
