// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import org.lwjgl.opengl.GL20;

public class ShaderUniform1i extends ShaderUniformBase
{
    private int[] programValues;
    private static final int VALUE_UNKNOWN = Integer.MIN_VALUE;
    
    public ShaderUniform1i(final String name) {
        super(name);
        this.resetValue();
    }
    
    public void setValue(final int valueNew) {
        final int program = this.getProgram();
        final int valueOld = this.programValues[program];
        if (valueNew == valueOld) {
            return;
        }
        this.programValues[program] = valueNew;
        final int location = this.getLocation();
        if (location < 0) {
            return;
        }
        flushRenderBuffers();
        GL20.glUniform1i(location, valueNew);
        this.checkGLError();
    }
    
    public int getValue() {
        final int program = this.getProgram();
        final int value = this.programValues[program];
        return value;
    }
    
    @Override
    protected void onProgramSet(final int program) {
        if (program >= this.programValues.length) {
            final int[] valuesOld = this.programValues;
            final int[] valuesNew = new int[program + 10];
            System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);
            for (int i = valuesOld.length; i < valuesNew.length; ++i) {
                valuesNew[i] = Integer.MIN_VALUE;
            }
            this.programValues = valuesNew;
        }
    }
    
    @Override
    protected void resetValue() {
        this.programValues = new int[] { Integer.MIN_VALUE };
    }
}
