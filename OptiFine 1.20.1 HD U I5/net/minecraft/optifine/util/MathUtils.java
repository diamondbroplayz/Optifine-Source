// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import org.joml.Vector4fc;
import org.joml.Quaternionfc;
import org.joml.Vector4f;
import java.nio.FloatBuffer;
import org.joml.Matrix3fc;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.joml.Vector3f;
import java.util.Random;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class MathUtils
{
    public static final float PI = 3.1415927f;
    public static final float PI2 = 6.2831855f;
    public static final float PId2 = 1.5707964f;
    private static final float[] ASIN_TABLE;
    
    public static float asin(final float value) {
        return MathUtils.ASIN_TABLE[(int)((value + 1.0f) * 32767.5) & 0xFFFF];
    }
    
    public static float acos(final float value) {
        return 1.5707964f - MathUtils.ASIN_TABLE[(int)((value + 1.0f) * 32767.5) & 0xFFFF];
    }
    
    public static int getAverage(final int[] vals) {
        if (vals.length <= 0) {
            return 0;
        }
        final int sum = getSum(vals);
        final int avg = sum / vals.length;
        return avg;
    }
    
    public static int getSum(final int[] vals) {
        if (vals.length <= 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < vals.length; ++i) {
            final int val = vals[i];
            sum += val;
        }
        return sum;
    }
    
    public static int roundDownToPowerOfTwo(final int val) {
        final int po2 = apa.c(val);
        if (val == po2) {
            return po2;
        }
        return po2 / 2;
    }
    
    public static boolean equalsDelta(final float f1, final float f2, final float delta) {
        return Math.abs(f1 - f2) <= delta;
    }
    
    public static float toDeg(final float angle) {
        return angle * 180.0f / 3.1415927f;
    }
    
    public static float toRad(final float angle) {
        return angle / 180.0f * 3.1415927f;
    }
    
    public static float roundToFloat(final double d) {
        return (float)(Math.round(d * 1.0E8) / 1.0E8);
    }
    
    public static double distanceSq(final gu pos, final double x, final double y, final double z) {
        return distanceSq(pos.u(), pos.v(), pos.w(), x, y, z);
    }
    
    public static float distanceSq(final gu pos, final float x, final float y, final float z) {
        return distanceSq((float)pos.u(), (float)pos.v(), (float)pos.w(), x, y, z);
    }
    
    public static double distanceSq(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double dx = x1 - x2;
        final double dy = y1 - y2;
        final double dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }
    
    public static float distanceSq(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        final float dx = x1 - x2;
        final float dy = y1 - y2;
        final float dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }
    
    public static Matrix4f makeMatrixIdentity() {
        final Matrix4f mat = new Matrix4f();
        mat.identity();
        return mat;
    }
    
    public static float getTransformX(final Matrix3f mat3, final float x, final float y, final float z) {
        return mat3.m00 * x + mat3.m10 * y + mat3.m20 * z;
    }
    
    public static float getTransformY(final Matrix3f mat3, final float x, final float y, final float z) {
        return mat3.m01 * x + mat3.m11 * y + mat3.m21 * z;
    }
    
    public static float getTransformZ(final Matrix3f mat3, final float x, final float y, final float z) {
        return mat3.m02 * x + mat3.m12 * y + mat3.m22 * z;
    }
    
    public static void setRandom(final Matrix3f mat3, final Random r) {
        mat3.m00 = r.nextFloat();
        mat3.m10 = r.nextFloat();
        mat3.m20 = r.nextFloat();
        mat3.m01 = r.nextFloat();
        mat3.m11 = r.nextFloat();
        mat3.m21 = r.nextFloat();
        mat3.m02 = r.nextFloat();
        mat3.m12 = r.nextFloat();
        mat3.m22 = r.nextFloat();
    }
    
    public static void setRandom(final Matrix4f mat4, final Random r) {
        mat4.m00(r.nextFloat());
        mat4.m10(r.nextFloat());
        mat4.m20(r.nextFloat());
        mat4.m30(r.nextFloat());
        mat4.m01(r.nextFloat());
        mat4.m11(r.nextFloat());
        mat4.m21(r.nextFloat());
        mat4.m31(r.nextFloat());
        mat4.m02(r.nextFloat());
        mat4.m12(r.nextFloat());
        mat4.m22(r.nextFloat());
        mat4.m32(r.nextFloat());
        mat4.m03(r.nextFloat());
        mat4.m13(r.nextFloat());
        mat4.m23(r.nextFloat());
        mat4.m33(r.nextFloat());
    }
    
    public static float getTransformX(final Matrix4f mat4, final float x, final float y, final float z, final float w) {
        return mat4.m00() * x + mat4.m10() * y + mat4.m20() * z + mat4.m30() * w;
    }
    
    public static float getTransformY(final Matrix4f mat4, final float x, final float y, final float z, final float w) {
        return mat4.m01() * x + mat4.m11() * y + mat4.m21() * z + mat4.m31() * w;
    }
    
    public static float getTransformZ(final Matrix4f mat4, final float x, final float y, final float z, final float w) {
        return mat4.m02() * x + mat4.m12() * y + mat4.m22() * z + mat4.m32() * w;
    }
    
    public static float getTransformW(final Matrix4f mat4, final float x, final float y, final float z, final float w) {
        return mat4.m03() * x + mat4.m13() * y + mat4.m23() * z + mat4.m33() * w;
    }
    
    public static boolean isIdentity(final Matrix4f mat4) {
        return (mat4.properties() & 0x4) != 0x0;
    }
    
    public static Vector3f copy(final Vector3f vec3) {
        return new Vector3f((Vector3fc)vec3);
    }
    
    public static Matrix4f copy(final Matrix4f mat4) {
        return new Matrix4f((Matrix4fc)mat4);
    }
    
    public static Quaternionf rotationDegrees(final Vector3f vec, final float angleDeg) {
        final float angle = toRad(angleDeg);
        final AxisAngle4f axisAngle = new AxisAngle4f(angle, (Vector3fc)vec);
        return new Quaternionf(axisAngle);
    }
    
    public static Matrix3f copy(final Matrix3f mat3) {
        return new Matrix3f((Matrix3fc)mat3);
    }
    
    public static void write(final Matrix4f mat4, final FloatBuffer buf) {
        buf.put(bufferIndexMat4(0, 0), mat4.m00());
        buf.put(bufferIndexMat4(0, 1), mat4.m10());
        buf.put(bufferIndexMat4(0, 2), mat4.m20());
        buf.put(bufferIndexMat4(0, 3), mat4.m30());
        buf.put(bufferIndexMat4(1, 0), mat4.m01());
        buf.put(bufferIndexMat4(1, 1), mat4.m11());
        buf.put(bufferIndexMat4(1, 2), mat4.m21());
        buf.put(bufferIndexMat4(1, 3), mat4.m31());
        buf.put(bufferIndexMat4(2, 0), mat4.m02());
        buf.put(bufferIndexMat4(2, 1), mat4.m12());
        buf.put(bufferIndexMat4(2, 2), mat4.m22());
        buf.put(bufferIndexMat4(2, 3), mat4.m32());
        buf.put(bufferIndexMat4(3, 0), mat4.m03());
        buf.put(bufferIndexMat4(3, 1), mat4.m13());
        buf.put(bufferIndexMat4(3, 2), mat4.m23());
        buf.put(bufferIndexMat4(3, 3), mat4.m33());
    }
    
    private static int bufferIndexMat4(final int rowIn, final int colIn) {
        return colIn * 4 + rowIn;
    }
    
    public static void write(final Matrix4f mat4, final float[] floatArrayIn) {
        floatArrayIn[0] = mat4.m00();
        floatArrayIn[1] = mat4.m10();
        floatArrayIn[2] = mat4.m20();
        floatArrayIn[3] = mat4.m30();
        floatArrayIn[4] = mat4.m01();
        floatArrayIn[5] = mat4.m11();
        floatArrayIn[6] = mat4.m21();
        floatArrayIn[7] = mat4.m31();
        floatArrayIn[8] = mat4.m02();
        floatArrayIn[9] = mat4.m12();
        floatArrayIn[10] = mat4.m22();
        floatArrayIn[11] = mat4.m32();
        floatArrayIn[12] = mat4.m03();
        floatArrayIn[13] = mat4.m13();
        floatArrayIn[14] = mat4.m23();
        floatArrayIn[15] = mat4.m33();
    }
    
    public static Vector3f makeVector3f(final Vector4f vec4) {
        return new Vector3f(vec4.x, vec4.y, vec4.z);
    }
    
    public static void transform(final Vector3f vec3, final Matrix3f mat3) {
        mat3.transform(vec3);
    }
    
    public static void transform(final Vector4f vec4, final Matrix4f mat4) {
        mat4.transform(vec4);
    }
    
    public static void transform(final Vector4f vec4, final Quaternionf quat) {
        vec4.rotate((Quaternionfc)quat);
    }
    
    public static void store(final Matrix3f mat3, final FloatBuffer buf) {
        buf.put(bufferIndexMat3(0, 0), mat3.m00);
        buf.put(bufferIndexMat3(0, 1), mat3.m10);
        buf.put(bufferIndexMat3(0, 2), mat3.m20);
        buf.put(bufferIndexMat3(1, 0), mat3.m01);
        buf.put(bufferIndexMat3(1, 1), mat3.m11);
        buf.put(bufferIndexMat3(1, 2), mat3.m21);
        buf.put(bufferIndexMat3(2, 0), mat3.m02);
        buf.put(bufferIndexMat3(2, 1), mat3.m12);
        buf.put(bufferIndexMat3(2, 2), mat3.m22);
    }
    
    private static int bufferIndexMat3(final int row, final int col) {
        return col * 3 + row;
    }
    
    public static void mulTranslate(final Matrix4f mat4, final float dx, final float dy, final float dz) {
        mat4.translate(dx, dy, dz);
    }
    
    public static void mulScale(final Matrix4f mat4, final float x, final float y, final float z) {
        mat4.scale(x, y, z);
    }
    
    public static Matrix4f makeMatrix4f(final Quaternionf quat) {
        final Matrix4f mat4 = new Matrix4f();
        mat4.set((Quaternionfc)quat);
        return mat4;
    }
    
    public static void mul(final Matrix4f mat4, final Quaternionf quat) {
        mat4.rotate((Quaternionfc)quat);
    }
    
    public static Matrix3f makeMatrix3f(final Quaternionf quat) {
        final Matrix3f mat3 = new Matrix3f();
        mat3.set((Quaternionfc)quat);
        return mat3;
    }
    
    public static void mul(final Matrix3f mat3, final Quaternionf quat) {
        mat3.rotate((Quaternionfc)quat);
    }
    
    public static Matrix4f makeTranslate4f(final float x, final float y, final float z) {
        final Matrix4f mat4 = new Matrix4f();
        mat4.translation(x, y, z);
        return mat4;
    }
    
    public static Matrix4f makeScale4f(final float x, final float y, final float z) {
        final Matrix4f mat4 = new Matrix4f();
        mat4.scale(x, y, z);
        return mat4;
    }
    
    public static Vector4f copy(final Vector4f vec4) {
        return new Vector4f((Vector4fc)vec4);
    }
    
    public static Matrix4f makeOrtho4f(final float leftIn, final float rightIn, final float topIn, final float bottomIn, final float nearIn, final float farIn) {
        final Matrix4f mat4 = new Matrix4f().ortho(leftIn, rightIn, bottomIn, topIn, nearIn, farIn);
        return mat4;
    }
    
    static {
        ASIN_TABLE = new float[65536];
        for (int i = 0; i < 65536; ++i) {
            MathUtils.ASIN_TABLE[i] = (float)Math.asin(i / 32767.5 - 1.0);
        }
        for (int i = -1; i < 2; ++i) {
            MathUtils.ASIN_TABLE[(int)((i + 1.0) * 32767.5) & 0xFFFF] = (float)Math.asin(i);
        }
    }
}
