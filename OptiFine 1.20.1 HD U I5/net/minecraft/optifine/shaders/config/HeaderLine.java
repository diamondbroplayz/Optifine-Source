// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

public abstract class HeaderLine
{
    public abstract String getText();
    
    public abstract boolean matches(final String p0);
    
    public abstract String removeFrom(final String p0);
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof HeaderLine)) {
            return false;
        }
        final String objText = ((HeaderLine)obj).getText();
        return this.getText().equals(objText);
    }
    
    @Override
    public int hashCode() {
        return this.getText().hashCode();
    }
    
    @Override
    public String toString() {
        return this.getText();
    }
}
