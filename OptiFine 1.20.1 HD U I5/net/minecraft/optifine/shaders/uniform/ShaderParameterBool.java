// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.uniform;

import net.optifine.expr.IExpressionBool;

public enum ShaderParameterBool implements IExpressionBool
{
    IS_ALIVE("is_alive"), 
    IS_BURNING("is_burning"), 
    IS_CHILD("is_child"), 
    IS_GLOWING("is_glowing"), 
    IS_HURT("is_hurt"), 
    IS_IN_LAVA("is_in_lava"), 
    IS_IN_WATER("is_in_water"), 
    IS_INVISIBLE("is_invisible"), 
    IS_ON_GROUND("is_on_ground"), 
    IS_RIDDEN("is_ridden"), 
    IS_RIDING("is_riding"), 
    IS_SNEAKING("is_sneaking"), 
    IS_SPRINTING("is_sprinting"), 
    IS_WET("is_wet");
    
    private String name;
    private fow renderManager;
    private static final ShaderParameterBool[] VALUES;
    
    private ShaderParameterBool(final String name) {
        this.name = name;
        this.renderManager = enn.N().an();
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean eval() {
        final bfj entityGeneral = enn.N().al();
        if (entityGeneral instanceof bfz) {
            final bfz entity = (bfz)entityGeneral;
            switch (ShaderParameterBool.ShaderParameterBool$1.$SwitchMap$net$optifine$shaders$uniform$ShaderParameterBool[this.ordinal()]) {
                case 1: {
                    return entity.bs();
                }
                case 2: {
                    return entity.bL();
                }
                case 3: {
                    return entity.h_();
                }
                case 4: {
                    return entity.ca();
                }
                case 5: {
                    return entity.aL > 0;
                }
                case 6: {
                    return entity.bi();
                }
                case 7: {
                    return entity.aV();
                }
                case 8: {
                    return entity.cb();
                }
                case 9: {
                    return entity.ay();
                }
                case 10: {
                    return entity.bN();
                }
                case 11: {
                    return entity.bM();
                }
                case 12: {
                    return entity.bU();
                }
                case 13: {
                    return entity.bV();
                }
                case 14: {
                    return entity.aW();
                }
            }
        }
        return false;
    }
    
    public static ShaderParameterBool parse(final String str) {
        if (str == null) {
            return null;
        }
        for (int i = 0; i < ShaderParameterBool.VALUES.length; ++i) {
            final ShaderParameterBool type = ShaderParameterBool.VALUES[i];
            if (type.getName().equals(str)) {
                return type;
            }
        }
        return null;
    }
    
    private static /* synthetic */ ShaderParameterBool[] $values() {
        return new ShaderParameterBool[] { ShaderParameterBool.IS_ALIVE, ShaderParameterBool.IS_BURNING, ShaderParameterBool.IS_CHILD, ShaderParameterBool.IS_GLOWING, ShaderParameterBool.IS_HURT, ShaderParameterBool.IS_IN_LAVA, ShaderParameterBool.IS_IN_WATER, ShaderParameterBool.IS_INVISIBLE, ShaderParameterBool.IS_ON_GROUND, ShaderParameterBool.IS_RIDDEN, ShaderParameterBool.IS_RIDING, ShaderParameterBool.IS_SNEAKING, ShaderParameterBool.IS_SPRINTING, ShaderParameterBool.IS_WET };
    }
    
    static {
        $VALUES = $values();
        VALUES = values();
    }
}
