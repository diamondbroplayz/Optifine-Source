// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import java.util.Arrays;
import net.optifine.Config;
import net.optifine.util.StrUtils;
import java.util.regex.Matcher;
import net.optifine.shaders.Shaders;
import java.util.regex.Pattern;

public class ShaderOptionVariable extends ShaderOption
{
    private static final Pattern PATTERN_VARIABLE;
    
    public ShaderOptionVariable(final String name, final String description, final String value, final String[] values, final String path) {
        super(name, description, value, values, value, path);
        this.setVisible(this.getValues().length > 1);
    }
    
    @Override
    public String getSourceLine() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.getName(), this.getValue(), this.getValue());
    }
    
    @Override
    public String getValueText(final String val) {
        final String prefix = Shaders.translate(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.getName()), "");
        final String text = super.getValueText(val);
        final String suffix = Shaders.translate(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.getName()), "");
        final String textFull = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, prefix, text, suffix);
        return textFull;
    }
    
    @Override
    public String getValueColor(final String val) {
        final String valLow = val.toLowerCase();
        if (valLow.equals("false") || valLow.equals("off")) {
            return "§c";
        }
        return "§a";
    }
    
    @Override
    public boolean matchesLine(final String line) {
        final Matcher m = ShaderOptionVariable.PATTERN_VARIABLE.matcher(line);
        if (!m.matches()) {
            return false;
        }
        final String defName = m.group(1);
        return defName.matches(this.getName());
    }
    
    public static ShaderOption parseOption(final String line, String path) {
        final Matcher m = ShaderOptionVariable.PATTERN_VARIABLE.matcher(line);
        if (!m.matches()) {
            return null;
        }
        final String name = m.group(1);
        final String value = m.group(2);
        String description = m.group(3);
        final String vals = StrUtils.getSegment(description, "[", "]");
        if (vals != null && vals.length() > 0) {
            description = description.replace(vals, "").trim();
        }
        final String[] values = parseValues(value, vals);
        if (name == null || name.length() <= 0) {
            return null;
        }
        path = StrUtils.removePrefix(path, "/shaders/");
        final ShaderOption so = new ShaderOptionVariable(name, description, value, values, path);
        return so;
    }
    
    public static String[] parseValues(final String value, String valuesStr) {
        final String[] values = { value };
        if (valuesStr == null) {
            return values;
        }
        valuesStr = valuesStr.trim();
        valuesStr = StrUtils.removePrefix(valuesStr, "[");
        valuesStr = StrUtils.removeSuffix(valuesStr, "]");
        valuesStr = valuesStr.trim();
        if (valuesStr.length() <= 0) {
            return values;
        }
        String[] parts = Config.tokenize(valuesStr, " ");
        if (parts.length <= 0) {
            return values;
        }
        if (!Arrays.asList(parts).contains(value)) {
            parts = (String[])Config.addObjectToArray(parts, value, 0);
        }
        return parts;
    }
    
    static {
        PATTERN_VARIABLE = Pattern.compile("^\\s*#define\\s+(\\w+)\\s+(-?[0-9\\.Ff]+|\\w+)\\s*(//.*)?$");
    }
}
