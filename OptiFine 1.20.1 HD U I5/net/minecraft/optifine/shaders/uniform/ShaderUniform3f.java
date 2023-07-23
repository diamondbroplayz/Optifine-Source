// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import org.lwjgl.opengl.GL20;

public class ShaderUniform3f extends ShaderUniformBase
{
    private float[][] programValues;
    private static final float VALUE_UNKNOWN = -3.4028235E38f;
    
    public ShaderUniform3f(final String name) {
        super(name);
        this.resetValue();
    }
    
    public void setValue(final float v0, final float v1, final float v2) {
        final int program = this.getProgram();
        final float[] valueOld = this.programValues[program];
        if (valueOld[0] == v0 && valueOld[1] == v1 && valueOld[2] == v2) {
            return;
        }
        valueOld[0] = v0;
        valueOld[1] = v1;
        valueOld[2] = v2;
        final int location = this.getLocation();
        if (location < 0) {
            return;
        }
        flushRenderBuffers();
        GL20.glUniform3f(location, v0, v1, v2);
        this.checkGLError();
    }
    
    public float[] getValue() {
        final int program = this.getProgram();
        final float[] value = this.programValues[program];
        return value;
    }
    
    @Override
    protected void onProgramSet(final int program) {
        if (program >= this.programValues.length) {
            final float[][] valuesOld = this.programValues;
            final float[][] valuesNew = new float[program + 10][];
            System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);
            this.programValues = valuesNew;
        }
        if (this.programValues[program] == null) {
            this.programValues[program] = new float[] { -3.4028235E38f, -3.4028235E38f, -3.4028235E38f };
        }
    }
    
    @Override
    protected void resetValue() {
        this.programValues = new float[][] { { -3.4028235E38f, -3.4028235E38f, -3.4028235E38f } };
    }
}
