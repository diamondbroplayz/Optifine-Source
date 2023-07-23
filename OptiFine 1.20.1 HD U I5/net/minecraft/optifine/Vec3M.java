// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.EnumSet;
import org.joml.Vector3f;

public class Vec3M implements ho
{
    public double x;
    public double y;
    public double z;
    
    public Vec3M(final double xIn, final double yIn, final double zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }
    
    public Vec3M(final Vector3f p_i82487_1_) {
        this(p_i82487_1_.x(), p_i82487_1_.y(), p_i82487_1_.z());
    }
    
    public Vec3M set(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public Vec3M set(final Vec3M vec) {
        return this.set(vec.x, vec.y, vec.z);
    }
    
    public Vec3M set(final eei vec) {
        return this.set(vec.c, vec.d, vec.e);
    }
    
    public Vec3M subtractReverse(final Vec3M vec) {
        return this.set(vec.x - this.x, vec.y - this.y, vec.z - this.z);
    }
    
    public Vec3M normalize() {
        final double d0 = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return (d0 < 1.0E-4) ? this.set(0.0, 0.0, 0.0) : this.set(this.x / d0, this.y / d0, this.z / d0);
    }
    
    public double dotProduct(final Vec3M vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }
    
    public Vec3M crossProduct(final Vec3M vec) {
        return this.set(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }
    
    public Vec3M subtract(final Vec3M vec) {
        return this.subtract(vec.x, vec.y, vec.z);
    }
    
    public Vec3M subtract(final double x, final double y, final double z) {
        return this.add(-x, -y, -z);
    }
    
    public Vec3M add(final Vec3M vec) {
        return this.add(vec.x, vec.y, vec.z);
    }
    
    public Vec3M add(final double x, final double y, final double z) {
        return this.set(this.x + x, this.y + y, this.z + z);
    }
    
    public boolean isDistanceBelow(final ho p_82509_1_, final double p_82509_2_) {
        return this.squareDistanceTo(p_82509_1_.a(), p_82509_1_.b(), p_82509_1_.c()) < p_82509_2_ * p_82509_2_;
    }
    
    public double distanceTo(final Vec3M vec) {
        final double d0 = vec.x - this.x;
        final double d2 = vec.y - this.y;
        final double d3 = vec.z - this.z;
        return Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }
    
    public double squareDistanceTo(final Vec3M vec) {
        final double d0 = vec.x - this.x;
        final double d2 = vec.y - this.y;
        final double d3 = vec.z - this.z;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public double squareDistanceTo(final double xIn, final double yIn, final double zIn) {
        final double d0 = xIn - this.x;
        final double d2 = yIn - this.y;
        final double d3 = zIn - this.z;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public Vec3M scale(final double factor) {
        return this.mul(factor, factor, factor);
    }
    
    public Vec3M inverse() {
        return this.scale(-1.0);
    }
    
    public Vec3M mul(final Vec3M p_82559_1_) {
        return this.mul(p_82559_1_.x, p_82559_1_.y, p_82559_1_.z);
    }
    
    public Vec3M mul(final double factorX, final double factorY, final double factorZ) {
        return this.set(this.x * factorX, this.y * factorY, this.z * factorZ);
    }
    
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
    
    public double horizontalDistance() {
        return Math.sqrt(this.x * this.x + this.z * this.z);
    }
    
    public double horizontalDistanceSqr() {
        return this.x * this.x + this.z * this.z;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof Vec3M)) {
            return false;
        }
        final Vec3M vec3 = (Vec3M)p_equals_1_;
        return Double.compare(vec3.x, this.x) == 0 && Double.compare(vec3.y, this.y) == 0 && Double.compare(vec3.z, this.z) == 0;
    }
    
    @Override
    public int hashCode() {
        long j = Double.doubleToLongBits(this.x);
        int i = (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.y);
        i = 31 * i + (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.z);
        return 31 * i + (int)(j ^ j >>> 32);
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(DDD)Ljava/lang/String;, this.x, this.y, this.z);
    }
    
    public Vec3M lerp(final Vec3M p_165921_1_, final double p_165921_2_) {
        return this.set(apa.d(p_165921_2_, this.x, p_165921_1_.x), apa.d(p_165921_2_, this.y, p_165921_1_.y), apa.d(p_165921_2_, this.z, p_165921_1_.z));
    }
    
    public Vec3M rotatePitch(final float pitch) {
        final float f = apa.b(pitch);
        final float f2 = apa.a(pitch);
        final double d0 = this.x;
        final double d2 = this.y * f + this.z * f2;
        final double d3 = this.z * f - this.y * f2;
        return this.set(d0, d2, d3);
    }
    
    public Vec3M rotateYaw(final float yaw) {
        final float f = apa.b(yaw);
        final float f2 = apa.a(yaw);
        final double d0 = this.x * f + this.z * f2;
        final double d2 = this.y;
        final double d3 = this.z * f - this.x * f2;
        return this.set(d0, d2, d3);
    }
    
    public Vec3M zRot(final float p_82535_1_) {
        final float f = apa.b(p_82535_1_);
        final float f2 = apa.a(p_82535_1_);
        final double d0 = this.x * f + this.y * f2;
        final double d2 = this.y * f - this.x * f2;
        final double d3 = this.z;
        return this.set(d0, d2, d3);
    }
    
    public Vec3M align(final EnumSet<ha.a> axes) {
        final double d0 = axes.contains(ha.a.a) ? apa.a(this.x) : this.x;
        final double d2 = axes.contains(ha.a.b) ? apa.a(this.y) : this.y;
        final double d3 = axes.contains(ha.a.c) ? apa.a(this.z) : this.z;
        return this.set(d0, d2, d3);
    }
    
    public double getCoordinate(final ha.a axis) {
        return axis.a(this.x, this.y, this.z);
    }
    
    public Vec3M with(final ha.a p_193103_1_, final double p_193103_2_) {
        final double d0 = (p_193103_1_ == ha.a.a) ? p_193103_2_ : this.x;
        final double d2 = (p_193103_1_ == ha.a.b) ? p_193103_2_ : this.y;
        final double d3 = (p_193103_1_ == ha.a.c) ? p_193103_2_ : this.z;
        return this.set(d0, d2, d3);
    }
    
    public final double a() {
        return this.x;
    }
    
    public final double b() {
        return this.y;
    }
    
    public final double c() {
        return this.z;
    }
    
    public void setRgb(final int rgb) {
        final double r = (rgb >> 16 & 0xFF) / 255.0;
        final double g = (rgb >> 8 & 0xFF) / 255.0;
        final double b = (rgb & 0xFF) / 255.0;
        this.set(r, g, b);
    }
    
    public Vec3M fromRgb(final int rgb) {
        this.setRgb(rgb);
        return this;
    }
    
    public eei toVec3() {
        return new eei(this.x, this.y, this.z);
    }
}
