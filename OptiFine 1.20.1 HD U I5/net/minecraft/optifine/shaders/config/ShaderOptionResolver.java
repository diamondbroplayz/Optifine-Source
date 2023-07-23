// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import net.optifine.expr.IExpression;
import java.util.HashMap;
import java.util.Map;
import net.optifine.expr.IExpressionResolver;

public class ShaderOptionResolver implements IExpressionResolver
{
    private Map<String, ExpressionShaderOptionSwitch> mapOptions;
    
    public ShaderOptionResolver(final ShaderOption[] options) {
        this.mapOptions = new HashMap<String, ExpressionShaderOptionSwitch>();
        for (int i = 0; i < options.length; ++i) {
            final ShaderOption so = options[i];
            if (so instanceof ShaderOptionSwitch) {
                final ShaderOptionSwitch sos = (ShaderOptionSwitch)so;
                final ExpressionShaderOptionSwitch esos = new ExpressionShaderOptionSwitch(sos);
                this.mapOptions.put(so.getName(), esos);
            }
        }
    }
    
    @Override
    public IExpression getExpression(final String name) {
        final ExpressionShaderOptionSwitch esos = this.mapOptions.get(name);
        return esos;
    }
}
