// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import net.optifine.expr.ConstantFloat;
import net.optifine.Config;
import net.optifine.expr.FunctionBool;
import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import java.util.Map;
import net.optifine.expr.IExpressionResolver;

public class MacroExpressionResolver implements IExpressionResolver
{
    private Map<String, String> mapMacroValues;
    
    public MacroExpressionResolver(final Map<String, String> mapMacroValues) {
        this.mapMacroValues = null;
        this.mapMacroValues = mapMacroValues;
    }
    
    @Override
    public IExpression getExpression(String name) {
        final String PREFIX_DEFINED = "defined_";
        if (name.startsWith(PREFIX_DEFINED)) {
            final String macro = name.substring(PREFIX_DEFINED.length());
            if (this.mapMacroValues.containsKey(macro)) {
                return new FunctionBool(FunctionType.TRUE, null);
            }
            return new FunctionBool(FunctionType.FALSE, null);
        }
        else {
            while (this.mapMacroValues.containsKey(name)) {
                final String nameNext = this.mapMacroValues.get(name);
                if (nameNext == null) {
                    break;
                }
                if (nameNext.equals(name)) {
                    break;
                }
                name = nameNext;
            }
            final int valInt = Config.parseInt(name, Integer.MIN_VALUE);
            if (valInt == Integer.MIN_VALUE) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
                return new ConstantFloat(0.0f);
            }
            return new ConstantFloat((float)valInt);
        }
    }
}
