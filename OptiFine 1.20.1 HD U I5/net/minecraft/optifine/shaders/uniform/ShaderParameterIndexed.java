// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import net.optifine.expr.IExpressionFloat;

public class ShaderParameterIndexed implements IExpressionFloat
{
    private ShaderParameterFloat type;
    private int index1;
    private int index2;
    
    public ShaderParameterIndexed(final ShaderParameterFloat type) {
        this(type, 0, 0);
    }
    
    public ShaderParameterIndexed(final ShaderParameterFloat type, final int index1) {
        this(type, index1, 0);
    }
    
    public ShaderParameterIndexed(final ShaderParameterFloat type, final int index1, final int index2) {
        this.type = type;
        this.index1 = index1;
        this.index2 = index2;
    }
    
    @Override
    public float eval() {
        return this.type.eval(this.index1, this.index2);
    }
    
    @Override
    public String toString() {
        if (this.type.getIndexNames1() == null) {
            return invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/ShaderParameterFloat;)Ljava/lang/String;, this.type);
        }
        if (this.type.getIndexNames2() == null) {
            return invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/ShaderParameterFloat;Ljava/lang/String;)Ljava/lang/String;, this.type, this.type.getIndexNames1()[this.index1]);
        }
        return invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/ShaderParameterFloat;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.type, this.type.getIndexNames1()[this.index1], this.type.getIndexNames2()[this.index2]);
    }
}
