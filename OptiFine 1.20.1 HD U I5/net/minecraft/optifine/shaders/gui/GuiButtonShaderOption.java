// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.gui;

import net.optifine.shaders.config.ShaderOptionScreen;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.gui.GuiButtonOF;

public class GuiButtonShaderOption extends GuiButtonOF
{
    private ShaderOption shaderOption;
    
    public GuiButtonShaderOption(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final ShaderOption shaderOption, final String text) {
        super(buttonId, x, y, widthIn, heightIn, text);
        this.shaderOption = null;
        this.shaderOption = shaderOption;
    }
    
    protected boolean c(final int p_isValidClickButton_1_) {
        return !(this.shaderOption instanceof ShaderOptionScreen) || p_isValidClickButton_1_ == 0;
    }
    
    public ShaderOption getShaderOption() {
        return this.shaderOption;
    }
    
    public void valueChanged() {
    }
    
    public boolean isSwitchable() {
        return true;
    }
}
