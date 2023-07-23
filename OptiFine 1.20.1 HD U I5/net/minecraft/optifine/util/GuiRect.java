// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class GuiRect
{
    private int left;
    private int top;
    private int right;
    private int bottom;
    
    public GuiRect(final int left, final int top, final int right, final int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    
    public int getLeft() {
        return this.left;
    }
    
    public int getTop() {
        return this.top;
    }
    
    public int getRight() {
        return this.right;
    }
    
    public int getBottom() {
        return this.bottom;
    }
}
