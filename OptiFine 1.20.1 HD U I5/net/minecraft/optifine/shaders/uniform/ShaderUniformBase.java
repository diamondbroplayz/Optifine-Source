// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL20;
import java.util.Arrays;

public abstract class ShaderUniformBase
{
    private String name;
    private int program;
    private int[] locations;
    private static final int LOCATION_UNDEFINED = -1;
    private static final int LOCATION_UNKNOWN = Integer.MIN_VALUE;
    
    public ShaderUniformBase(final String name) {
        this.program = 0;
        this.locations = new int[] { -1 };
        this.name = name;
    }
    
    public void setProgram(final int program) {
        if (this.program == program) {
            return;
        }
        this.program = program;
        this.expandLocations();
        this.onProgramSet(program);
    }
    
    private void expandLocations() {
        if (this.program >= this.locations.length) {
            final int[] locationsNew = new int[this.program * 2];
            Arrays.fill(locationsNew, Integer.MIN_VALUE);
            System.arraycopy(this.locations, 0, locationsNew, 0, this.locations.length);
            this.locations = locationsNew;
        }
    }
    
    protected abstract void onProgramSet(final int p0);
    
    public String getName() {
        return this.name;
    }
    
    public int getProgram() {
        return this.program;
    }
    
    public int getLocation() {
        if (this.program <= 0) {
            return -1;
        }
        int location = this.locations[this.program];
        if (location == Integer.MIN_VALUE) {
            location = GL20.glGetUniformLocation(this.program, (CharSequence)this.name);
            this.locations[this.program] = location;
        }
        return location;
    }
    
    public boolean isDefined() {
        return this.getLocation() >= 0;
    }
    
    public void disable() {
        this.locations[this.program] = -1;
    }
    
    public void reset() {
        this.program = 0;
        this.locations = new int[] { -1 };
        this.resetValue();
    }
    
    protected abstract void resetValue();
    
    protected void checkGLError() {
        if (Shaders.checkGLError(this.name) != 0) {
            this.disable();
        }
    }
    
    protected static final void flushRenderBuffers() {
        Shaders.flushRenderBuffers();
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
