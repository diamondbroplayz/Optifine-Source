// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.gui;

import net.optifine.gui.GuiButtonOF;
import com.mojang.blaze3d.systems.RenderSystem;
import net.optifine.shaders.config.ShaderOption;

public class GuiSliderShaderOption extends GuiButtonShaderOption
{
    private float sliderValue;
    public boolean dragging;
    private ShaderOption shaderOption;
    
    public GuiSliderShaderOption(final int buttonId, final int x, final int y, final int w, final int h, final ShaderOption shaderOption, final String text) {
        super(buttonId, x, y, w, h, shaderOption, text);
        this.shaderOption = null;
        this.sliderValue = 1.0f;
        this.shaderOption = shaderOption;
        this.sliderValue = shaderOption.getIndexNormalized();
        this.setMessage(GuiShaderOptions.getButtonText(shaderOption, this.o));
    }
    
    public void b(final eox graphicsIn, final int mouseX, final int mouseY, final float partialTicks) {
        if (this.s) {
            if (this.dragging && !euq.q()) {
                this.sliderValue = (mouseX - (this.p() + 4)) / (float)(this.o - 8);
                this.sliderValue = apa.a(this.sliderValue, 0.0f, 1.0f);
                this.shaderOption.setIndexNormalized(this.sliderValue);
                this.sliderValue = this.shaderOption.getIndexNormalized();
                this.setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.o));
            }
            final enn mc = enn.N();
            RenderSystem.setShaderTexture(0, GuiSliderShaderOption.m);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.t);
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            final int i = (this.m() ? 2 : 1) * 20;
            this.blitNineSliced(graphicsIn, GuiSliderShaderOption.m, this.p(), this.r(), this.k(), this.h(), 20, 4, 200, 20, 0, 46);
            GuiButtonOF.blit(graphicsIn, GuiSliderShaderOption.m, this.p() + (int)(this.sliderValue * (this.o - 8)), this.r(), 0, 46 + i, 4, 20);
            GuiButtonOF.blit(graphicsIn, GuiSliderShaderOption.m, this.p() + (int)(this.sliderValue * (this.o - 8)) + 4, this.r(), 196, 46 + i, 4, 20);
            final int col = this.r ? 16777215 : 10526880;
            this.a(graphicsIn, mc.h, col | apa.f(this.t * 255.0f) << 24);
        }
    }
    
    public boolean a(final double mouseX, final double mouseY, final int button) {
        if (super.a(mouseX, mouseY, button)) {
            this.sliderValue = (float)(mouseX - (this.p() + 4)) / (this.o - 8);
            this.sliderValue = apa.a(this.sliderValue, 0.0f, 1.0f);
            this.shaderOption.setIndexNormalized(this.sliderValue);
            this.setMessage(GuiShaderOptions.getButtonText(this.shaderOption, this.o));
            return this.dragging = true;
        }
        return false;
    }
    
    public boolean b(final double mouseX, final double mouseY, final int button) {
        this.dragging = false;
        return true;
    }
    
    @Override
    public void valueChanged() {
        this.sliderValue = this.shaderOption.getIndexNormalized();
    }
    
    @Override
    public boolean isSwitchable() {
        return false;
    }
}
