// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import org.lwjgl.opengl.GL20;

public class ShaderUniform1f extends ShaderUniformBase
{
    private float[] programValues;
    private static final float VALUE_UNKNOWN = -3.4028235E38f;
    
    public ShaderUniform1f(final String name) {
        super(name);
        this.resetValue();
    }
    
    public void setValue(final float valueNew) {
        final int program = this.getProgram();
        final float valueOld = this.programValues[program];
        if (valueNew == valueOld) {
            return;
        }
        this.programValues[program] = valueNew;
        final int location = this.getLocation();
        if (location < 0) {
            return;
        }
        flushRenderBuffers();
        GL20.glUniform1f(location, valueNew);
        this.checkGLError();
    }
    
    public float getValue() {
        final int program = this.getProgram();
        final float value = this.programValues[program];
        return value;
    }
    
    @Override
    protected void onProgramSet(final int program) {
        if (program >= this.programValues.length) {
            final float[] valuesOld = this.programValues;
            final float[] valuesNew = new float[program + 10];
            System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);
            for (int i = valuesOld.length; i < valuesNew.length; ++i) {
                valuesNew[i] = -3.4028235E38f;
            }
            this.programValues = valuesNew;
        }
    }
    
    @Override
    protected void resetValue() {
        this.programValues = new float[] { -3.4028235E38f };
    }
}
