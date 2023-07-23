// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.texture;

public class ColorBlenderLabPbrSpecular extends ColorBlenderSeparate
{
    public ColorBlenderLabPbrSpecular() {
        super(new BlenderLinear(), new BlenderSplit(230, true), new BlenderSplit(65, false), new BlenderSplit(255, true));
    }
}
