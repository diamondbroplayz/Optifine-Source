// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import net.optifine.util.BufferUtil;
import org.lwjgl.opengl.GL20;
import net.optifine.util.MathUtils;
import org.joml.Matrix3f;
import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;

public class ShaderUniformM3 extends ShaderUniformBase
{
    private boolean transpose;
    private FloatBuffer matrixBuffer;
    private FloatBuffer tempBuffer;
    
    public ShaderUniformM3(final String name) {
        super(name);
        this.matrixBuffer = MemoryUtil.memAllocFloat(9);
        this.tempBuffer = MemoryUtil.memAllocFloat(9);
    }
    
    public void setValue(final Matrix3f matrixIn) {
        this.transpose = false;
        this.tempBuffer.clear();
        MathUtils.store(matrixIn, this.tempBuffer);
        this.setValue(false, this.tempBuffer);
    }
    
    public void setValue(final boolean transpose, final FloatBuffer matrix) {
        this.transpose = transpose;
        matrix.mark();
        this.matrixBuffer.clear();
        this.matrixBuffer.put(matrix);
        this.matrixBuffer.rewind();
        matrix.reset();
        final int location = this.getLocation();
        if (location < 0) {
            return;
        }
        flushRenderBuffers();
        GL20.glUniformMatrix3fv(location, transpose, this.matrixBuffer);
        this.checkGLError();
    }
    
    public float getValue(final int row, final int col) {
        final int index = this.transpose ? (col * 3 + row) : (row * 3 + col);
        final float value = this.matrixBuffer.get(index);
        return value;
    }
    
    @Override
    protected void onProgramSet(final int program) {
    }
    
    @Override
    protected void resetValue() {
        BufferUtil.fill(this.matrixBuffer, 0.0f);
    }
}
