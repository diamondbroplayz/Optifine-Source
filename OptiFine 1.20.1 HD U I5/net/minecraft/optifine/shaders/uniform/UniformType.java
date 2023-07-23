// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpressionFloatArray;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpression;

public enum UniformType
{
    BOOL, 
    INT, 
    FLOAT, 
    VEC2, 
    VEC3, 
    VEC4;
    
    public ShaderUniformBase makeShaderUniform(final String name) {
        switch (UniformType.UniformType$1.$SwitchMap$net$optifine$shaders$uniform$UniformType[this.ordinal()]) {
            case 1: {
                return new ShaderUniform1i(name);
            }
            case 2: {
                return new ShaderUniform1i(name);
            }
            case 3: {
                return new ShaderUniform1f(name);
            }
            case 4: {
                return new ShaderUniform2f(name);
            }
            case 5: {
                return new ShaderUniform3f(name);
            }
            case 6: {
                return new ShaderUniform4f(name);
            }
            default: {
                throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/UniformType;)Ljava/lang/String;, this));
            }
        }
    }
    
    public void updateUniform(final IExpression expression, final ShaderUniformBase uniform) {
        switch (UniformType.UniformType$1.$SwitchMap$net$optifine$shaders$uniform$UniformType[this.ordinal()]) {
            case 1: {
                this.updateUniformBool((IExpressionBool)expression, (ShaderUniform1i)uniform);
            }
            case 2: {
                this.updateUniformInt((IExpressionFloat)expression, (ShaderUniform1i)uniform);
            }
            case 3: {
                this.updateUniformFloat((IExpressionFloat)expression, (ShaderUniform1f)uniform);
            }
            case 4: {
                this.updateUniformFloat2((IExpressionFloatArray)expression, (ShaderUniform2f)uniform);
            }
            case 5: {
                this.updateUniformFloat3((IExpressionFloatArray)expression, (ShaderUniform3f)uniform);
            }
            case 6: {
                this.updateUniformFloat4((IExpressionFloatArray)expression, (ShaderUniform4f)uniform);
            }
            default: {
                throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/UniformType;)Ljava/lang/String;, this));
            }
        }
    }
    
    private void updateUniformBool(final IExpressionBool expression, final ShaderUniform1i uniform) {
        final int valInt;
        final boolean val = (valInt = (expression.eval() ? 1 : 0)) != 0;
        uniform.setValue(valInt);
    }
    
    private void updateUniformInt(final IExpressionFloat expression, final ShaderUniform1i uniform) {
        final int val = (int)expression.eval();
        uniform.setValue(val);
    }
    
    private void updateUniformFloat(final IExpressionFloat expression, final ShaderUniform1f uniform) {
        final float val = expression.eval();
        uniform.setValue(val);
    }
    
    private void updateUniformFloat2(final IExpressionFloatArray expression, final ShaderUniform2f uniform) {
        final float[] val = expression.eval();
        if (val.length != 2) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, val.length));
        }
        uniform.setValue(val[0], val[1]);
    }
    
    private void updateUniformFloat3(final IExpressionFloatArray expression, final ShaderUniform3f uniform) {
        final float[] val = expression.eval();
        if (val.length != 3) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, val.length));
        }
        uniform.setValue(val[0], val[1], val[2]);
    }
    
    private void updateUniformFloat4(final IExpressionFloatArray expression, final ShaderUniform4f uniform) {
        final float[] val = expression.eval();
        if (val.length != 4) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, val.length));
        }
        uniform.setValue(val[0], val[1], val[2], val[3]);
    }
    
    public boolean matchesExpressionType(final ExpressionType expressionType) {
        switch (UniformType.UniformType$1.$SwitchMap$net$optifine$shaders$uniform$UniformType[this.ordinal()]) {
            case 1: {
                return expressionType == ExpressionType.BOOL;
            }
            case 2: {
                return expressionType == ExpressionType.FLOAT;
            }
            case 3: {
                return expressionType == ExpressionType.FLOAT;
            }
            case 4:
            case 5:
            case 6: {
                return expressionType == ExpressionType.FLOAT_ARRAY;
            }
            default: {
                throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/uniform/UniformType;)Ljava/lang/String;, this));
            }
        }
    }
    
    public static UniformType parse(final String type) {
        final UniformType[] values = values();
        for (int i = 0; i < values.length; ++i) {
            final UniformType uniformType = values[i];
            if (uniformType.name().toLowerCase().equals(type)) {
                return uniformType;
            }
        }
        return null;
    }
    
    private static /* synthetic */ UniformType[] $values() {
        return new UniformType[] { UniformType.BOOL, UniformType.INT, UniformType.FLOAT, UniformType.VEC2, UniformType.VEC3, UniformType.VEC4 };
    }
    
    static {
        $VALUES = $values();
    }
}
