// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import org.lwjgl.BufferUtils;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import java.nio.FloatBuffer;

public class DebugUtils
{
    private static FloatBuffer floatBuffer16;
    private static float[] floatArray16;
    
    public static String getGlModelView() {
        DebugUtils.floatBuffer16.clear();
        GL11.glGetFloatv(2982, DebugUtils.floatBuffer16);
        DebugUtils.floatBuffer16.get(DebugUtils.floatArray16);
        final float[] floatArray16T = transposeMat4(DebugUtils.floatArray16);
        return getMatrix4(floatArray16T);
    }
    
    public static String getGlProjection() {
        DebugUtils.floatBuffer16.clear();
        GL11.glGetFloatv(2983, DebugUtils.floatBuffer16);
        DebugUtils.floatBuffer16.get(DebugUtils.floatArray16);
        final float[] floatArray16T = transposeMat4(DebugUtils.floatArray16);
        return getMatrix4(floatArray16T);
    }
    
    private static float[] transposeMat4(final float[] arr) {
        final float[] arrT = new float[16];
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                arrT[x * 4 + y] = arr[y * 4 + x];
            }
        }
        return arrT;
    }
    
    public static String getMatrix4(final Matrix4f mat) {
        MathUtils.write(mat, DebugUtils.floatArray16);
        return getMatrix4(DebugUtils.floatArray16);
    }
    
    private static String getMatrix4(final float[] fs) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fs.length; ++i) {
            String str = String.format("%.2f", fs[i]);
            if (i > 0) {
                if (i % 4 == 0) {
                    sb.append("\n");
                }
                else {
                    sb.append(", ");
                }
            }
            str = StrUtils.fillLeft(str, 5, ' ');
            sb.append(str);
        }
        return sb.toString();
    }
    
    static {
        DebugUtils.floatBuffer16 = BufferUtils.createFloatBuffer(16);
        DebugUtils.floatArray16 = new float[16];
    }
}
