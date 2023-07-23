// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4fc;
import org.joml.Vector4f;
import org.joml.Matrix4fc;
import net.optifine.util.MathUtils;
import net.optifine.Config;
import org.joml.Matrix4f;

public class ShadowFrustum extends fmw
{
    public ShadowFrustum(final Matrix4f matrixIn, final Matrix4f projectionIn) {
        super(matrixIn, projectionIn);
        this.extendForShadows(matrixIn, projectionIn);
        super.usePlanes = true;
    }
    
    private void extendForShadows(final Matrix4f matrixIn, final Matrix4f projectionIn) {
        final few world = Config.getMinecraft().s;
        if (world == null) {
            return;
        }
        final Matrix4f matrixFull = MathUtils.copy(projectionIn);
        matrixFull.mul((Matrix4fc)matrixIn);
        matrixFull.transpose();
        final Vector4f viewUp = new Vector4f(0.0f, 1.0f, 0.0f, 0.0f);
        MathUtils.transform(viewUp, matrixFull);
        viewUp.normalize();
        final Vector4f viewRight = new Vector4f(-1.0f, 0.0f, 0.0f, 0.0f);
        MathUtils.transform(viewRight, matrixFull);
        viewRight.normalize();
        final float partialTicks = 0.0f;
        final float car = world.a(partialTicks);
        final float sunTiltRad = Shaders.sunPathRotation * apa.deg2Rad;
        final float sar = (car > apa.PId2 && car < 3.0f * apa.PId2) ? (car + 3.1415927f) : car;
        final float sx = -apa.a(sar);
        final float sy = apa.b(sar) * apa.b(sunTiltRad);
        final float sz = -apa.b(sar) * apa.a(sunTiltRad);
        final Vector4f vecSun = new Vector4f(sx, sy, sz, 0.0f);
        vecSun.normalize();
        final Vector3f viewUpDot = MathUtils.makeVector3f(viewUp);
        viewUpDot.mul(viewUp.dot((Vector4fc)vecSun));
        final Vector3f vecSunH3 = MathUtils.makeVector3f(vecSun);
        vecSunH3.sub((Vector3fc)viewUpDot);
        vecSunH3.normalize();
        final Vector4f vecSunH4 = new Vector4f(vecSunH3.x(), vecSunH3.y(), vecSunH3.z(), 0.0f);
        final Vector3f viewRightDot = MathUtils.makeVector3f(viewRight);
        viewRightDot.mul(viewRight.dot((Vector4fc)vecSun));
        final Vector3f vecSunV3 = MathUtils.makeVector3f(vecSun);
        vecSunV3.sub((Vector3fc)viewRightDot);
        vecSunV3.normalize();
        final Vector4f vecSunV4 = new Vector4f(vecSunV3.x(), vecSunV3.y(), vecSunV3.z(), 0.0f);
        final Vector4f vecRight = this.frustum[0];
        final Vector4f vecLeft = this.frustum[1];
        final Vector4f vecTop = this.frustum[2];
        final Vector4f vecBottom = this.frustum[3];
        final Vector4f vecFar = this.frustum[4];
        final Vector4f vecNear = this.frustum[5];
        vecRight.normalize();
        vecLeft.normalize();
        vecTop.normalize();
        vecBottom.normalize();
        vecFar.normalize();
        vecNear.normalize();
        final float dotRight = vecRight.dot((Vector4fc)vecSunH4);
        final float dotLeft = vecLeft.dot((Vector4fc)vecSunH4);
        final float dotTop = vecTop.dot((Vector4fc)vecSunV4);
        final float dotBottom = vecBottom.dot((Vector4fc)vecSunV4);
        final float farPlaneDistance = Config.getGameRenderer().l();
        final float mulFarDist = Config.isFogOff() ? 1.414f : 1.0f;
        float rotRight = 0.0f;
        float rotLeft = 0.0f;
        if (dotRight < 0.0f || dotLeft < 0.0f) {
            vecNear.add(0.0f, 0.0f, 0.0f, farPlaneDistance);
            if (dotRight < 0.0f && dotLeft < 0.0f) {
                rotRight = this.rotateDotPlus(vecRight, vecSunH4, -1, viewUp);
                rotLeft = this.rotateDotPlus(vecLeft, vecSunH4, 1, viewUp);
                vecRight.set(-vecRight.x(), -vecRight.y(), -vecRight.z(), -vecRight.w());
                vecLeft.set(-vecLeft.x(), -vecLeft.y(), -vecLeft.z(), -vecLeft.w());
                final float distRight = -dotRight * farPlaneDistance * mulFarDist;
                final float distLeft = -dotLeft * farPlaneDistance * mulFarDist;
                vecRight.add(0.0f, 0.0f, 0.0f, distRight);
                vecLeft.add(0.0f, 0.0f, 0.0f, distLeft);
            }
            else if (dotRight < 0.0f) {
                rotRight = this.rotateDotPlus(vecRight, vecSunH4, -1, viewUp);
            }
            else {
                rotLeft = this.rotateDotPlus(vecLeft, vecSunH4, 1, viewUp);
            }
        }
        final int minWorldHeight = world.C_();
        final int maxWorldHeight = world.aj();
        final float eyeHeight = (float)(int)Config.getMinecraft().t.dr();
        final float maxDistBottom = Config.limit(eyeHeight - minWorldHeight, 0.0f, farPlaneDistance);
        final float maxDistTop = Config.limit(maxWorldHeight - eyeHeight, 0.0f, farPlaneDistance);
        float rotTop = 0.0f;
        float rotBottom = 0.0f;
        if (dotTop < 0.0f || dotBottom < 0.0f) {
            vecNear.add(0.0f, 0.0f, 0.0f, farPlaneDistance);
            if (dotTop < 0.0f && dotBottom < 0.0f) {
                rotTop = this.rotateDotPlus(vecTop, vecSunV4, -1, viewRight);
                rotBottom = this.rotateDotPlus(vecBottom, vecSunV4, 1, viewRight);
                vecTop.set(-vecTop.x(), -vecTop.y(), -vecTop.z(), -vecTop.w());
                vecBottom.set(-vecBottom.x(), -vecBottom.y(), -vecBottom.z(), -vecBottom.w());
                final float distTop = -dotTop * maxDistTop;
                final float distBottom = -dotBottom * maxDistBottom;
                vecTop.add(0.0f, 0.0f, 0.0f, distTop);
                vecBottom.add(0.0f, 0.0f, 0.0f, distBottom);
            }
            else if (dotTop < 0.0f) {
                rotTop = this.rotateDotPlus(vecTop, vecSunV4, -1, viewRight);
            }
            else {
                rotBottom = this.rotateDotPlus(vecBottom, vecSunV4, 1, viewRight);
            }
        }
    }
    
    private float rotateDotPlus(final Vector4f vecFrustum, final Vector4f vecSun, final int angleDeg, final Vector4f vecRot) {
        final Vector3f vecRot2 = MathUtils.makeVector3f(vecRot);
        final Quaternionf rot = MathUtils.rotationDegrees(vecRot2, (float)angleDeg);
        float angleDegSum = 0.0f;
        while (true) {
            final float dot = vecFrustum.dot((Vector4fc)vecSun);
            if (dot >= 0.0f) {
                break;
            }
            MathUtils.transform(vecFrustum, rot);
            vecFrustum.normalize();
            angleDegSum += angleDeg;
        }
        return angleDegSum;
    }
}
