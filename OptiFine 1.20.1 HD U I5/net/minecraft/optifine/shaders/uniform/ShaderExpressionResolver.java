// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import net.optifine.shaders.SMCLog;
import net.optifine.util.BiomeCategory;
import net.optifine.expr.ConstantFloat;
import net.optifine.util.BiomeUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.HashMap;
import net.optifine.expr.IExpression;
import java.util.Map;
import net.optifine.expr.IExpressionResolver;

public class ShaderExpressionResolver implements IExpressionResolver
{
    private Map<String, IExpression> mapExpressions;
    
    public ShaderExpressionResolver(final Map<String, IExpression> map) {
        this.mapExpressions = new HashMap<String, IExpression>();
        this.registerExpressions();
        final Set<String> keys = map.keySet();
        for (final String name : keys) {
            final IExpression expr = map.get(name);
            this.registerExpression(name, expr);
        }
    }
    
    private void registerExpressions() {
        final ShaderParameterFloat[] spfs = ShaderParameterFloat.values();
        for (int i = 0; i < spfs.length; ++i) {
            final ShaderParameterFloat spf = spfs[i];
            this.addParameterFloat(this.mapExpressions, spf);
        }
        final ShaderParameterBool[] spbs = ShaderParameterBool.values();
        for (int j = 0; j < spbs.length; ++j) {
            final ShaderParameterBool spb = spbs[j];
            this.mapExpressions.put(spb.getName(), spb);
        }
        final Set<acq> biomeLocations = (Set<acq>)BiomeUtils.getLocations();
        for (final acq loc : biomeLocations) {
            String name = loc.a().trim();
            name = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name.toUpperCase().replace(' ', '_'));
            final int id = BiomeUtils.getId(loc);
            final IExpression expr = new ConstantFloat((float)id);
            this.registerExpression(name, expr);
        }
        final BiomeCategory[] biomeCats = BiomeCategory.values();
        for (int k = 0; k < biomeCats.length; ++k) {
            final BiomeCategory bc = biomeCats[k];
            final String name2 = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, bc.getName().toUpperCase());
            final int id2 = k;
            final IExpression expr2 = new ConstantFloat((float)id2);
            this.registerExpression(name2, expr2);
        }
        final cnk.c[] biomePpts = cnk.c.values();
        for (int l = 0; l < biomePpts.length; ++l) {
            final cnk.c bp = biomePpts[l];
            final String name3 = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, bp.name().toUpperCase());
            final int id3 = l;
            final IExpression expr3 = new ConstantFloat((float)id3);
            this.registerExpression(name3, expr3);
        }
    }
    
    private void addParameterFloat(final Map<String, IExpression> map, final ShaderParameterFloat spf) {
        final String[] indexNames1 = spf.getIndexNames1();
        if (indexNames1 == null) {
            map.put(spf.getName(), new ShaderParameterIndexed(spf));
            return;
        }
        for (int i1 = 0; i1 < indexNames1.length; ++i1) {
            final String indexName1 = indexNames1[i1];
            final String[] indexNames2 = spf.getIndexNames2();
            if (indexNames2 == null) {
                map.put(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, spf.getName(), indexName1), new ShaderParameterIndexed(spf, i1));
            }
            else {
                for (int i2 = 0; i2 < indexNames2.length; ++i2) {
                    final String indexName2 = indexNames2[i2];
                    map.put(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, spf.getName(), indexName1, indexName2), new ShaderParameterIndexed(spf, i1, i2));
                }
            }
        }
    }
    
    public boolean registerExpression(final String name, final IExpression expr) {
        if (this.mapExpressions.containsKey(name)) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            return false;
        }
        this.mapExpressions.put(name, expr);
        return true;
    }
    
    @Override
    public IExpression getExpression(final String name) {
        return this.mapExpressions.get(name);
    }
    
    public boolean hasExpression(final String name) {
        return this.mapExpressions.containsKey(name);
    }
}
