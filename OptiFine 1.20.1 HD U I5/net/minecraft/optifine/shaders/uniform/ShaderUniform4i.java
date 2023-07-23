// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import org.lwjgl.opengl.GL20;

public class ShaderUniform4i extends ShaderUniformBase
{
    private int[][] programValues;
    private static final int VALUE_UNKNOWN = Integer.MIN_VALUE;
    
    public ShaderUniform4i(final String name) {
        super(name);
        this.resetValue();
    }
    
    public void setValue(final int v0, final int v1, final int v2, final int v3) {
        final int program = this.getProgram();
        final int[] valueOld = this.programValues[program];
        if (valueOld[0] == v0 && valueOld[1] == v1 && valueOld[2] == v2 && valueOld[3] == v3) {
            return;
        }
        valueOld[0] = v0;
        valueOld[1] = v1;
        valueOld[2] = v2;
        valueOld[3] = v3;
        final int location = this.getLocation();
        if (location < 0) {
            return;
        }
        flushRenderBuffers();
        GL20.glUniform4i(location, v0, v1, v2, v3);
        this.checkGLError();
    }
    
    public int[] getValue() {
        final int program = this.getProgram();
        final int[] value = this.programValues[program];
        return value;
    }
    
    @Override
    protected void onProgramSet(final int program) {
        if (program >= this.programValues.length) {
            final int[][] valuesOld = this.programValues;
            final int[][] valuesNew = new int[program + 10][];
            System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);
            this.programValues = valuesNew;
        }
        if (this.programValues[program] == null) {
            this.programValues[program] = new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE };
        }
    }
    
    @Override
    protected void resetValue() {
        this.programValues = new int[][] { { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE } };
    }
}
