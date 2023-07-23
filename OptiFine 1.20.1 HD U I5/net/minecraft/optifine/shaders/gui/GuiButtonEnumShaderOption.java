// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.gui;

import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.gui.GuiButtonOF;

public class GuiButtonEnumShaderOption extends GuiButtonOF
{
    private EnumShaderOption enumShaderOption;
    
    public GuiButtonEnumShaderOption(final EnumShaderOption enumShaderOption, final int x, final int y, final int widthIn, final int heightIn) {
        super(enumShaderOption.ordinal(), x, y, widthIn, heightIn, getButtonText(enumShaderOption));
        this.enumShaderOption = null;
        this.enumShaderOption = enumShaderOption;
    }
    
    public EnumShaderOption getEnumShaderOption() {
        return this.enumShaderOption;
    }
    
    private static String getButtonText(final EnumShaderOption eso) {
        final String nameText = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fvz.a(eso.getResourceKey(), new Object[0]));
        switch (eso) {
            case ANTIALIASING: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, GuiShaders.toStringAa(Shaders.configAntialiasingLevel));
            }
            case NORMAL_MAP: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, GuiShaders.toStringOnOff(Shaders.configNormalMap));
            }
            case SPECULAR_MAP: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, GuiShaders.toStringOnOff(Shaders.configSpecularMap));
            }
            case RENDER_RES_MUL: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, GuiShaders.toStringQuality(Shaders.configRenderResMul));
            }
            case SHADOW_RES_MUL: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, GuiShaders.toStringQuality(Shaders.configShadowResMul));
            }
            case HAND_DEPTH_MUL: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, GuiShaders.toStringHandDepth(Shaders.configHandDepthMul));
            }
            case CLOUD_SHADOW: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, GuiShaders.toStringOnOff(Shaders.configCloudShadow));
            }
            case OLD_HAND_LIGHT: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, Shaders.configOldHandLight.getUserValue());
            }
            case OLD_LIGHTING: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, Shaders.configOldLighting.getUserValue());
            }
            case SHADOW_CLIP_FRUSTRUM: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum));
            }
            case TWEAK_BLOCK_DAMAGE: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage));
            }
            default: {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, nameText, Shaders.getEnumShaderOption(eso));
            }
        }
    }
    
    public void updateButtonText() {
        this.setMessage(getButtonText(this.enumShaderOption));
    }
    
    protected boolean c(final int button) {
        return true;
    }
}
