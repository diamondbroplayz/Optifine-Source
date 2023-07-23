// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import net.optifine.expr.IExpression;
import java.util.Map;
import net.optifine.expr.IExpressionCached;

public class CustomUniforms
{
    private CustomUniform[] uniforms;
    private IExpressionCached[] expressionsCached;
    
    public CustomUniforms(final CustomUniform[] uniforms, final Map<String, IExpression> mapExpressions) {
        this.uniforms = uniforms;
        final List<IExpressionCached> list = new ArrayList<IExpressionCached>();
        final Set<String> keys = mapExpressions.keySet();
        for (final String key : keys) {
            final IExpression expr = mapExpressions.get(key);
            if (!(expr instanceof IExpressionCached)) {
                continue;
            }
            final IExpressionCached exprCached = (IExpressionCached)expr;
            list.add(exprCached);
        }
        this.expressionsCached = list.toArray(new IExpressionCached[list.size()]);
    }
    
    public void setProgram(final int program) {
        for (int i = 0; i < this.uniforms.length; ++i) {
            final CustomUniform uniform = this.uniforms[i];
            uniform.setProgram(program);
        }
    }
    
    public void update() {
        this.resetCache();
        for (int i = 0; i < this.uniforms.length; ++i) {
            final CustomUniform uniform = this.uniforms[i];
            uniform.update();
        }
    }
    
    private void resetCache() {
        for (int i = 0; i < this.expressionsCached.length; ++i) {
            final IExpressionCached exprCached = this.expressionsCached[i];
            exprCached.reset();
        }
    }
    
    public void reset() {
        for (int i = 0; i < this.uniforms.length; ++i) {
            final CustomUniform cu = this.uniforms[i];
            cu.reset();
        }
    }
}
