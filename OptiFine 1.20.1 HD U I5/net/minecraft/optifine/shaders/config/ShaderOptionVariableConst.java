// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import net.optifine.util.StrUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderOptionVariableConst extends ShaderOptionVariable
{
    private String type;
    private static final Pattern PATTERN_CONST;
    
    public ShaderOptionVariableConst(final String name, final String type, final String description, final String value, final String[] values, final String path) {
        super(name, description, value, values, path);
        this.type = null;
        this.type = type;
    }
    
    @Override
    public String getSourceLine() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.type, this.getName(), this.getValue(), this.getValue());
    }
    
    @Override
    public boolean matchesLine(final String line) {
        final Matcher m = ShaderOptionVariableConst.PATTERN_CONST.matcher(line);
        if (!m.matches()) {
            return false;
        }
        final String defName = m.group(2);
        return defName.matches(this.getName());
    }
    
    public static ShaderOption parseOption(final String line, String path) {
        final Matcher m = ShaderOptionVariableConst.PATTERN_CONST.matcher(line);
        if (!m.matches()) {
            return null;
        }
        final String type = m.group(1);
        final String name = m.group(2);
        final String value = m.group(3);
        String description = m.group(4);
        final String vals = StrUtils.getSegment(description, "[", "]");
        if (vals != null && vals.length() > 0) {
            description = description.replace(vals, "").trim();
        }
        final String[] values = ShaderOptionVariable.parseValues(value, vals);
        if (name == null || name.length() <= 0) {
            return null;
        }
        path = StrUtils.removePrefix(path, "/shaders/");
        final ShaderOption so = new ShaderOptionVariableConst(name, type, description, value, values, path);
        return so;
    }
    
    static {
        PATTERN_CONST = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");
    }
}
