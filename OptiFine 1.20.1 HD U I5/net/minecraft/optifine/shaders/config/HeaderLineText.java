// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

public class HeaderLineText extends HeaderLine
{
    private String text;
    
    public HeaderLineText(final String text) {
        this.text = text;
    }
    
    @Override
    public String getText() {
        return this.text;
    }
    
    @Override
    public boolean matches(final String line) {
        return line.equals(this.text);
    }
    
    @Override
    public String removeFrom(final String line) {
        return null;
    }
}
