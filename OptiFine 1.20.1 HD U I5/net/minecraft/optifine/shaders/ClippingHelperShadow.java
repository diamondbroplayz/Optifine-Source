// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import org.joml.Matrix4f;

public class ClippingHelperShadow extends fmw
{
    private static ClippingHelperShadow instance;
    float[] frustumTest;
    float[][] shadowClipPlanes;
    int shadowClipPlaneCount;
    float[] matInvMP;
    float[] vecIntersection;
    float[][] frustum;
    
    public ClippingHelperShadow() {
        super((Matrix4f)null, (Matrix4f)null);
        this.frustumTest = new float[6];
        this.shadowClipPlanes = new float[10][4];
        this.matInvMP = new float[16];
        this.vecIntersection = new float[4];
    }
    
    public boolean a(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        for (int index = 0; index < this.shadowClipPlaneCount; ++index) {
            final float[] plane = this.shadowClipPlanes[index];
            if (this.dot4(plane, x1, y1, z1) <= 0.0 && this.dot4(plane, x2, y1, z1) <= 0.0 && this.dot4(plane, x1, y2, z1) <= 0.0 && this.dot4(plane, x2, y2, z1) <= 0.0 && this.dot4(plane, x1, y1, z2) <= 0.0 && this.dot4(plane, x2, y1, z2) <= 0.0 && this.dot4(plane, x1, y2, z2) <= 0.0 && this.dot4(plane, x2, y2, z2) <= 0.0) {
                return false;
            }
        }
        return true;
    }
    
    private double dot4(final float[] plane, final double x, final double y, final double z) {
        return plane[0] * x + plane[1] * y + plane[2] * z + plane[3];
    }
    
    private double dot3(final float[] vecA, final float[] vecB) {
        return vecA[0] * (double)vecB[0] + vecA[1] * (double)vecB[1] + vecA[2] * (double)vecB[2];
    }
    
    public static fmw getInstance() {
        ClippingHelperShadow.instance.init();
        return ClippingHelperShadow.instance;
    }
    
    private void normalizePlane(final float[] plane) {
        final float length = apa.c(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
        final int n = 0;
        plane[n] /= length;
        final int n2 = 1;
        plane[n2] /= length;
        final int n3 = 2;
        plane[n3] /= length;
        final int n4 = 3;
        plane[n4] /= length;
    }
    
    private void normalize3(final float[] plane) {
        float length = apa.c(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
        if (length == 0.0f) {
            length = 1.0f;
        }
        final int n = 0;
        plane[n] /= length;
        final int n2 = 1;
        plane[n2] /= length;
        final int n3 = 2;
        plane[n3] /= length;
    }
    
    private void assignPlane(final float[] plane, final float a, final float b, final float c, final float d) {
        final float length = (float)Math.sqrt(a * a + b * b + c * c);
        plane[0] = a / length;
        plane[1] = b / length;
        plane[2] = c / length;
        plane[3] = d / length;
    }
    
    private void copyPlane(final float[] dst, final float[] src) {
        dst[0] = src[0];
        dst[1] = src[1];
        dst[2] = src[2];
        dst[3] = src[3];
    }
    
    private void cross3(final float[] out, final float[] a, final float[] b) {
        out[0] = a[1] * b[2] - a[2] * b[1];
        out[1] = a[2] * b[0] - a[0] * b[2];
        out[2] = a[0] * b[1] - a[1] * b[0];
    }
    
    private void addShadowClipPlane(final float[] plane) {
        this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], plane);
    }
    
    private float length(final float x, final float y, final float z) {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }
    
    private float distance(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        return this.length(x1 - x2, y1 - y2, z1 - z2);
    }
    
    private void makeShadowPlane(final float[] shadowPlane, final float[] positivePlane, final float[] negativePlane, final float[] vecSun) {
        this.cross3(this.vecIntersection, positivePlane, negativePlane);
        this.cross3(shadowPlane, this.vecIntersection, vecSun);
        this.normalize3(shadowPlane);
        final float dotNP;
        final float dotPN = dotNP = (float)this.dot3(positivePlane, negativePlane);
        final float dotSN = (float)this.dot3(shadowPlane, negativePlane);
        final float disSN = this.distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], negativePlane[0] * dotSN, negativePlane[1] * dotSN, negativePlane[2] * dotSN);
        final float disPN = this.distance(positivePlane[0], positivePlane[1], positivePlane[2], negativePlane[0] * dotPN, negativePlane[1] * dotPN, negativePlane[2] * dotPN);
        final float k1 = disSN / disPN;
        final float dotSP = (float)this.dot3(shadowPlane, positivePlane);
        final float disSP = this.distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], positivePlane[0] * dotSP, positivePlane[1] * dotSP, positivePlane[2] * dotSP);
        final float disNP = this.distance(negativePlane[0], negativePlane[1], negativePlane[2], positivePlane[0] * dotNP, positivePlane[1] * dotNP, positivePlane[2] * dotNP);
        final float k2 = disSP / disNP;
        shadowPlane[3] = positivePlane[3] * k1 + negativePlane[3] * k2;
    }
    
    public void init() {
    }
    
    static {
        ClippingHelperShadow.instance = new ClippingHelperShadow();
    }
}
