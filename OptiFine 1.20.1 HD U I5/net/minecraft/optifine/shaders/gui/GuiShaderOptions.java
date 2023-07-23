// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.gui;

import java.util.Iterator;
import net.optifine.shaders.config.ShaderOptionProfile;
import net.optifine.Lang;
import net.optifine.Config;
import net.optifine.shaders.config.ShaderOptionScreen;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.gui.GuiButtonOF;
import net.optifine.shaders.Shaders;
import net.optifine.gui.TooltipProvider;
import net.optifine.gui.TooltipProviderShaderOptions;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.GuiScreenOF;

public class GuiShaderOptions extends GuiScreenOF
{
    private euq prevScreen;
    private enr settings;
    private TooltipManager tooltipManager;
    private String screenName;
    private String screenText;
    private boolean changed;
    public static final String OPTION_PROFILE = "<profile>";
    public static final String OPTION_EMPTY = "<empty>";
    public static final String OPTION_REST = "*";
    
    public GuiShaderOptions(final euq guiscreen, final enr gamesettings) {
        super((sw)sw.b(fvz.a("of.options.shaderOptionsTitle", new Object[0])));
        this.tooltipManager = new TooltipManager(this, new TooltipProviderShaderOptions());
        this.screenName = null;
        this.screenText = null;
        this.changed = false;
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }
    
    public GuiShaderOptions(final euq guiscreen, final enr gamesettings, final String screenName) {
        this(guiscreen, gamesettings);
        this.screenName = screenName;
        if (screenName != null) {
            this.screenText = Shaders.translate(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, screenName), screenName);
        }
    }
    
    public void b() {
        final int baseId = 100;
        int baseX = 0;
        final int baseY = 30;
        final int stepY = 20;
        final int btnWidth = 120;
        final int btnHeight = 20;
        int columns = Shaders.getShaderPackColumns(this.screenName, 2);
        final ShaderOption[] ops = Shaders.getShaderPackOptions(this.screenName);
        if (ops != null) {
            final int colsMin = apa.c(ops.length / 9.0);
            if (columns < colsMin) {
                columns = colsMin;
            }
            for (int i = 0; i < ops.length; ++i) {
                final ShaderOption so = ops[i];
                if (so != null) {
                    if (so.isVisible()) {
                        final int col = i % columns;
                        final int row = i / columns;
                        final int colWidth = Math.min(this.g / columns, 200);
                        baseX = (this.g - colWidth * columns) / 2;
                        final int x = col * colWidth + 5 + baseX;
                        final int y = baseY + row * stepY;
                        final int w = colWidth - 10;
                        final int h = btnHeight;
                        final String text = getButtonText(so, w);
                        GuiButtonShaderOption btn;
                        if (Shaders.isShaderPackOptionSlider(so.getName())) {
                            btn = new GuiSliderShaderOption(baseId + i, x, y, w, h, so, text);
                        }
                        else {
                            btn = new GuiButtonShaderOption(baseId + i, x, y, w, h, so, text);
                        }
                        btn.r = so.isEnabled();
                        this.d((eqt)btn);
                    }
                }
            }
        }
        this.d((eqt)new GuiButtonOF(201, this.g / 2 - btnWidth - 20, this.h / 6 + 168 + 11, btnWidth, btnHeight, fvz.a("controls.reset", new Object[0])));
        this.d((eqt)new GuiButtonOF(200, this.g / 2 + 20, this.h / 6 + 168 + 11, btnWidth, btnHeight, fvz.a("gui.done", new Object[0])));
    }
    
    public static String getButtonText(final ShaderOption so, final int btnWidth) {
        String labelName = so.getNameText();
        if (so instanceof ShaderOptionScreen) {
            final ShaderOptionScreen soScr = (ShaderOptionScreen)so;
            return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, labelName);
        }
        final eov fr = Config.getMinecraft().h;
        for (int lenSuffix = fr.b(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Lang.getOff())) + 5; fr.b(labelName) + lenSuffix >= btnWidth && labelName.length() > 0; labelName = labelName.substring(0, labelName.length() - 1)) {}
        final String col = so.isChanged() ? so.getValueColor(so.getValue()) : "";
        final String labelValue = so.getValueText(so.getValue());
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, labelName, col, labelValue);
    }
    
    @Override
    protected void actionPerformed(final epf guiElement) {
        if (!(guiElement instanceof GuiButtonOF)) {
            return;
        }
        final GuiButtonOF guibutton = (GuiButtonOF)guiElement;
        if (!guibutton.r) {
            return;
        }
        if (guibutton.id < 200 && guibutton instanceof GuiButtonShaderOption) {
            final GuiButtonShaderOption btnSo = (GuiButtonShaderOption)guibutton;
            final ShaderOption so = btnSo.getShaderOption();
            if (so instanceof ShaderOptionScreen) {
                final String screenName = so.getName();
                final GuiShaderOptions scr = new GuiShaderOptions(this, this.settings, screenName);
                this.f.a((euq)scr);
                return;
            }
            if (q()) {
                so.resetValue();
            }
            else if (btnSo.isSwitchable()) {
                so.nextValue();
            }
            this.updateAllButtons();
            this.changed = true;
        }
        if (guibutton.id == 201) {
            final ShaderOption[] opts = Shaders.getChangedOptions(Shaders.getShaderPackOptions());
            for (int i = 0; i < opts.length; ++i) {
                final ShaderOption opt = opts[i];
                opt.resetValue();
                this.changed = true;
            }
            this.updateAllButtons();
        }
        if (guibutton.id == 200) {
            if (this.changed) {
                Shaders.saveShaderPackOptions();
                this.changed = false;
                Shaders.uninit();
            }
            this.f.a(this.prevScreen);
        }
    }
    
    public void ax_() {
        if (this.changed) {
            Shaders.saveShaderPackOptions();
            this.changed = false;
            Shaders.uninit();
        }
        super.ax_();
    }
    
    @Override
    protected void actionPerformedRightClick(final epf guiElement) {
        if (guiElement instanceof GuiButtonShaderOption) {
            final GuiButtonShaderOption btnSo = (GuiButtonShaderOption)guiElement;
            final ShaderOption so = btnSo.getShaderOption();
            if (q()) {
                so.resetValue();
            }
            else if (btnSo.isSwitchable()) {
                so.prevValue();
            }
            this.updateAllButtons();
            this.changed = true;
        }
    }
    
    private void updateAllButtons() {
        for (final epi btn : this.getButtonList()) {
            if (btn instanceof GuiButtonShaderOption) {
                final GuiButtonShaderOption gbso = (GuiButtonShaderOption)btn;
                final ShaderOption opt = gbso.getShaderOption();
                if (opt instanceof ShaderOptionProfile) {
                    final ShaderOptionProfile optProf = (ShaderOptionProfile)opt;
                    optProf.updateProfile();
                }
                gbso.setMessage(getButtonText(opt, gbso.k()));
                gbso.valueChanged();
            }
        }
    }
    
    public void a(final eox graphicsIn, final int x, final int y, final float partialTicks) {
        this.a(graphicsIn);
        if (this.screenText != null) {
            GuiScreenOF.drawCenteredString(graphicsIn, this.fontRenderer, this.screenText, this.g / 2, 15, 16777215);
        }
        else {
            GuiScreenOF.drawCenteredString(graphicsIn, this.fontRenderer, this.e, this.g / 2, 15, 16777215);
        }
        super.a(graphicsIn, x, y, partialTicks);
        this.tooltipManager.drawTooltips(graphicsIn, x, y, this.getButtonList());
    }
}
