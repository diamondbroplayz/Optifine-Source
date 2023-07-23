// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeaderLineFunction extends HeaderLine
{
    private String name;
    private String text;
    private Pattern patternLine;
    
    public HeaderLineFunction(final String name, final String text) {
        this.name = name;
        this.text = text;
        this.patternLine = Pattern.compile(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name), 32);
    }
    
    @Override
    public String getText() {
        return this.text;
    }
    
    @Override
    public boolean matches(final String line) {
        if (!line.contains(this.name)) {
            return false;
        }
        final Matcher m = this.patternLine.matcher(line);
        return m.matches();
    }
    
    @Override
    public String removeFrom(final String line) {
        return null;
    }
}
