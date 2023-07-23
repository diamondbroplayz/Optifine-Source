// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeaderLineVariable extends HeaderLine
{
    private String qualifier;
    private String name;
    private String text;
    private Pattern patternLine;
    private Pattern patternName1;
    private Pattern patternName2;
    
    public HeaderLineVariable(final String qualifier, final String name, final String text) {
        this.qualifier = qualifier;
        this.name = name;
        this.text = text;
        this.patternLine = Pattern.compile(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, qualifier, name));
        this.patternName1 = Pattern.compile(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
        this.patternName2 = Pattern.compile(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
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
        String lineNew = line;
        final Matcher m1 = this.patternName1.matcher(line);
        lineNew = m1.replaceAll("$1");
        final Matcher m2 = this.patternName2.matcher(line);
        lineNew = m2.replaceAll("$1");
        if (!lineNew.equals(line)) {
            return lineNew;
        }
        return null;
    }
}
