// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.optifine.gui.GuiButtonOF;

public class GuiButtonDownloadShaders extends GuiButtonOF
{
    public GuiButtonDownloadShaders(final int buttonID, final int xPos, final int yPos) {
        super(buttonID, xPos, yPos, 22, 20, "");
    }
    
    public void a(final eox graphicsIn, final int mouseX, final int mouseY, final float partialTicks) {
        if (!this.s) {
            return;
        }
        super.a(graphicsIn, mouseX, mouseY, partialTicks);
        final acq locTexture = new acq("optifine/textures/icons.png");
        RenderSystem.setShaderTexture(0, locTexture);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        GuiButtonOF.blit(graphicsIn, locTexture, this.p() + 3, this.r() + 2, 0, 0, 16, 16);
    }
}
