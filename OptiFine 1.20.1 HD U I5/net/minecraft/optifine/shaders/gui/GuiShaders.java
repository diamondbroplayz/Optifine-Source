// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.gui;

import java.net.URI;
import java.io.File;
import java.io.IOException;
import net.optifine.shaders.ShadersTex;
import java.util.Iterator;
import net.optifine.Config;
import net.optifine.gui.GuiButtonOF;
import net.optifine.Lang;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.Shaders;
import net.optifine.gui.TooltipProvider;
import net.optifine.gui.TooltipProviderEnumShaderOptions;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.GuiScreenOF;

public class GuiShaders extends GuiScreenOF
{
    protected euq parentGui;
    private TooltipManager tooltipManager;
    private int updateTimer;
    private GuiSlotShaders shaderList;
    private boolean saved;
    private static float[] QUALITY_MULTIPLIERS;
    private static String[] QUALITY_MULTIPLIER_NAMES;
    private static float QUALITY_MULTIPLIER_DEFAULT;
    private static float[] HAND_DEPTH_VALUES;
    private static String[] HAND_DEPTH_NAMES;
    private static float HAND_DEPTH_DEFAULT;
    public static final int EnumOS_UNKNOWN = 0;
    public static final int EnumOS_WINDOWS = 1;
    public static final int EnumOS_OSX = 2;
    public static final int EnumOS_SOLARIS = 3;
    public static final int EnumOS_LINUX = 4;
    
    public GuiShaders(final euq par1GuiScreen, final enr par2GameSettings) {
        super((sw)sw.b(fvz.a("of.options.shadersTitle", new Object[0])));
        this.tooltipManager = new TooltipManager(this, new TooltipProviderEnumShaderOptions());
        this.updateTimer = -1;
        this.saved = false;
        this.parentGui = par1GuiScreen;
    }
    
    public void b() {
        if (Shaders.shadersConfig == null) {
            Shaders.loadConfig();
        }
        final int btnWidth = 120;
        final int btnHeight = 20;
        final int btnX = this.g - btnWidth - 10;
        final int baseY = 30;
        final int stepY = 20;
        final int shaderListWidth = this.g - btnWidth - 20;
        this.e((eqt)(this.shaderList = new GuiSlotShaders(this, shaderListWidth, this.h, baseY, this.h - 50, 16)));
        this.d((eqt)new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, btnX, 0 * stepY + baseY, btnWidth, btnHeight));
        this.d((eqt)new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, btnX, 1 * stepY + baseY, btnWidth, btnHeight));
        this.d((eqt)new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, btnX, 2 * stepY + baseY, btnWidth, btnHeight));
        this.d((eqt)new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, btnX, 3 * stepY + baseY, btnWidth, btnHeight));
        this.d((eqt)new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, btnX, 4 * stepY + baseY, btnWidth, btnHeight));
        this.d((eqt)new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, btnX, 5 * stepY + baseY, btnWidth, btnHeight));
        this.d((eqt)new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, btnX, 6 * stepY + baseY, btnWidth, btnHeight));
        this.d((eqt)new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, btnX, 7 * stepY + baseY, btnWidth, btnHeight));
        final int btnFolderWidth = Math.min(150, shaderListWidth / 2 - 10);
        final int xFolder = shaderListWidth / 4 - btnFolderWidth / 2;
        final int yFolder = this.h - 25;
        this.d((eqt)new GuiButtonOF(201, xFolder, yFolder, btnFolderWidth - 22 + 1, btnHeight, Lang.get("of.options.shaders.shadersFolder")));
        this.d((eqt)new GuiButtonDownloadShaders(210, xFolder + btnFolderWidth - 22 - 1, yFolder));
        this.d((eqt)new GuiButtonOF(202, shaderListWidth / 4 * 3 - btnFolderWidth / 2, this.h - 25, btnFolderWidth, btnHeight, fvz.a("gui.done", new Object[0])));
        this.d((eqt)new GuiButtonOF(203, btnX, this.h - 25, btnWidth, btnHeight, Lang.get("of.options.shaders.shaderOptions")));
        this.a((eqt)this.shaderList);
        this.updateButtons();
    }
    
    public void updateButtons() {
        final boolean shaderActive = Config.isShaders();
        for (final epf guiElement : this.getButtonList()) {
            if (!(guiElement instanceof GuiButtonOF)) {
                continue;
            }
            final GuiButtonOF button = (GuiButtonOF)guiElement;
            if (button.id == 201 || button.id == 202) {
                continue;
            }
            if (button.id == 210) {
                continue;
            }
            if (button.id == EnumShaderOption.ANTIALIASING.ordinal()) {
                continue;
            }
            button.r = shaderActive;
        }
    }
    
    @Override
    protected void actionPerformed(final epf button) {
        this.actionPerformed(button, false);
    }
    
    @Override
    protected void actionPerformedRightClick(final epf button) {
        this.actionPerformed(button, true);
    }
    
    private void actionPerformed(final epf guiElement, final boolean rightClick) {
        if (!guiElement.r) {
            return;
        }
        if (guiElement instanceof GuiButtonEnumShaderOption) {
            final GuiButtonEnumShaderOption gbeso = (GuiButtonEnumShaderOption)guiElement;
            switch (GuiShaders.GuiShaders$1.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[gbeso.getEnumShaderOption().ordinal()]) {
                case 1: {
                    Shaders.nextAntialiasingLevel(!rightClick);
                    if (q()) {
                        Shaders.configAntialiasingLevel = 0;
                    }
                    Shaders.uninit();
                    break;
                }
                case 2: {
                    Shaders.configNormalMap = !Shaders.configNormalMap;
                    if (q()) {
                        Shaders.configNormalMap = true;
                    }
                    Shaders.uninit();
                    this.f.O();
                    break;
                }
                case 3: {
                    Shaders.configSpecularMap = !Shaders.configSpecularMap;
                    if (q()) {
                        Shaders.configSpecularMap = true;
                    }
                    Shaders.uninit();
                    this.f.O();
                    break;
                }
                case 4: {
                    Shaders.configRenderResMul = this.getNextValue(Shaders.configRenderResMul, GuiShaders.QUALITY_MULTIPLIERS, GuiShaders.QUALITY_MULTIPLIER_DEFAULT, !rightClick, q());
                    Shaders.uninit();
                    Shaders.scheduleResize();
                    break;
                }
                case 5: {
                    Shaders.configShadowResMul = this.getNextValue(Shaders.configShadowResMul, GuiShaders.QUALITY_MULTIPLIERS, GuiShaders.QUALITY_MULTIPLIER_DEFAULT, !rightClick, q());
                    Shaders.uninit();
                    Shaders.scheduleResizeShadow();
                    break;
                }
                case 6: {
                    Shaders.configHandDepthMul = this.getNextValue(Shaders.configHandDepthMul, GuiShaders.HAND_DEPTH_VALUES, GuiShaders.HAND_DEPTH_DEFAULT, !rightClick, q());
                    Shaders.uninit();
                    break;
                }
                case 7: {
                    Shaders.configOldHandLight.nextValue(!rightClick);
                    if (q()) {
                        Shaders.configOldHandLight.resetValue();
                    }
                    Shaders.uninit();
                    break;
                }
                case 8: {
                    Shaders.configOldLighting.nextValue(!rightClick);
                    if (q()) {
                        Shaders.configOldLighting.resetValue();
                    }
                    Shaders.updateBlockLightLevel();
                    Shaders.uninit();
                    this.f.O();
                    break;
                }
                case 9: {
                    Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
                    break;
                }
                case 10: {
                    Shaders.configCloudShadow = !Shaders.configCloudShadow;
                    break;
                }
                case 11: {
                    Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
                    Shaders.configTexMinFilN = (Shaders.configTexMinFilS = Shaders.configTexMinFilB);
                    gbeso.setMessage(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.texMinFilDesc[Shaders.configTexMinFilB]));
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                }
                case 12: {
                    Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
                    gbeso.setMessage(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.texMagFilDesc[Shaders.configTexMagFilN]));
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                }
                case 13: {
                    Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
                    gbeso.setMessage(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.texMagFilDesc[Shaders.configTexMagFilS]));
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                }
                case 14: {
                    Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
                    gbeso.setMessage(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, toStringOnOff(Shaders.configShadowClipFrustrum)));
                    ShadersTex.updateTextureMinMagFilter();
                    break;
                }
            }
            gbeso.updateButtonText();
            return;
        }
        if (rightClick) {
            return;
        }
        if (!(guiElement instanceof GuiButtonOF)) {
            return;
        }
        final GuiButtonOF button = (GuiButtonOF)guiElement;
        switch (button.id) {
            case 201: {
                switch (getOSType()) {
                    case 2: {
                        try {
                            Runtime.getRuntime().exec(new String[] { "/usr/bin/open", Shaders.shaderPacksDir.getAbsolutePath() });
                            return;
                        }
                        catch (IOException var7) {
                            var7.printStackTrace();
                            break;
                        }
                    }
                    case 1: {
                        final String var8 = String.format("cmd.exe /C start \"Open file\" \"%s\"", Shaders.shaderPacksDir.getAbsolutePath());
                        try {
                            Runtime.getRuntime().exec(var8);
                            return;
                        }
                        catch (IOException var9) {
                            var9.printStackTrace();
                        }
                        break;
                    }
                }
                boolean var10 = false;
                try {
                    final URI uri = new File(this.f.p, "shaderpacks").toURI();
                    ac.i().a(uri);
                }
                catch (Throwable var11) {
                    var11.printStackTrace();
                    var10 = true;
                }
                if (var10) {
                    Config.dbg("Opening via system class!");
                    ac.i().a(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.shaderPacksDir.getAbsolutePath()));
                    break;
                }
                break;
            }
            case 202: {
                Shaders.storeConfig();
                this.saved = true;
                this.f.a(this.parentGui);
                break;
            }
            case 203: {
                final GuiShaderOptions gui = new GuiShaderOptions(this, Config.getGameSettings());
                Config.getMinecraft().a((euq)gui);
                break;
            }
            case 210: {
                try {
                    final URI uri2 = new URI("http://optifine.net/shaderPacks");
                    ac.i().a(uri2);
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                break;
            }
        }
    }
    
    public void ax_() {
        if (!this.saved) {
            Shaders.storeConfig();
            this.saved = true;
        }
        super.ax_();
    }
    
    public void a(final eox graphicsIn, final int mouseX, final int mouseY, final float partialTicks) {
        this.a(graphicsIn);
        this.shaderList.render(graphicsIn, mouseX, mouseY, partialTicks);
        if (this.updateTimer <= 0) {
            this.shaderList.updateList();
            this.updateTimer += 20;
        }
        GuiScreenOF.drawCenteredString(graphicsIn, this.fontRenderer, this.e, this.g / 2, 15, 16777215);
        final String info = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Shaders.glVersionString, Shaders.glVendorString, Shaders.glRendererString);
        final int infoWidth = this.fontRenderer.b(info);
        if (infoWidth < this.g - 5) {
            GuiScreenOF.drawCenteredString(graphicsIn, this.fontRenderer, info, this.g / 2, this.h - 40, 8421504);
        }
        else {
            GuiScreenOF.drawString(graphicsIn, this.fontRenderer, info, 5, this.h - 40, 8421504);
        }
        super.a(graphicsIn, mouseX, mouseY, partialTicks);
        this.tooltipManager.drawTooltips(graphicsIn, mouseX, mouseY, this.getButtonList());
    }
    
    public void f() {
        super.f();
        --this.updateTimer;
    }
    
    public enn getMc() {
        return this.f;
    }
    
    public void drawCenteredString(final eox graphicsIn, final String text, final int x, final int y, final int color) {
        GuiScreenOF.drawCenteredString(graphicsIn, this.fontRenderer, text, x, y, color);
    }
    
    public static String toStringOnOff(final boolean value) {
        final String on = Lang.getOn();
        final String off = Lang.getOff();
        return value ? on : off;
    }
    
    public static String toStringAa(final int value) {
        if (value == 2) {
            return "FXAA 2x";
        }
        if (value == 4) {
            return "FXAA 4x";
        }
        return Lang.getOff();
    }
    
    public static String toStringValue(final float val, final float[] values, final String[] names) {
        final int index = getValueIndex(val, values);
        return names[index];
    }
    
    private float getNextValue(final float val, final float[] values, final float valDef, final boolean forward, final boolean reset) {
        if (reset) {
            return valDef;
        }
        int index = getValueIndex(val, values);
        if (forward) {
            if (++index >= values.length) {
                index = 0;
            }
        }
        else if (--index < 0) {
            index = values.length - 1;
        }
        return values[index];
    }
    
    public static int getValueIndex(final float val, final float[] values) {
        for (int i = 0; i < values.length; ++i) {
            final float value = values[i];
            if (value >= val) {
                return i;
            }
        }
        return values.length - 1;
    }
    
    public static String toStringQuality(final float val) {
        return toStringValue(val, GuiShaders.QUALITY_MULTIPLIERS, GuiShaders.QUALITY_MULTIPLIER_NAMES);
    }
    
    public static String toStringHandDepth(final float val) {
        return toStringValue(val, GuiShaders.HAND_DEPTH_VALUES, GuiShaders.HAND_DEPTH_NAMES);
    }
    
    public static int getOSType() {
        final String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return 1;
        }
        if (osName.contains("mac")) {
            return 2;
        }
        if (osName.contains("solaris")) {
            return 3;
        }
        if (osName.contains("sunos")) {
            return 3;
        }
        if (osName.contains("linux")) {
            return 4;
        }
        if (osName.contains("unix")) {
            return 4;
        }
        return 0;
    }
    
    static {
        GuiShaders.QUALITY_MULTIPLIERS = new float[] { 0.5f, 0.6f, 0.6666667f, 0.75f, 0.8333333f, 0.9f, 1.0f, 1.1666666f, 1.3333334f, 1.5f, 1.6666666f, 1.8f, 2.0f };
        GuiShaders.QUALITY_MULTIPLIER_NAMES = new String[] { "0.5x", "0.6x", "0.66x", "0.75x", "0.83x", "0.9x", "1x", "1.16x", "1.33x", "1.5x", "1.66x", "1.8x", "2x" };
        GuiShaders.QUALITY_MULTIPLIER_DEFAULT = 1.0f;
        GuiShaders.HAND_DEPTH_VALUES = new float[] { 0.0625f, 0.125f, 0.25f };
        GuiShaders.HAND_DEPTH_NAMES = new String[] { "0.5x", "1x", "2x" };
        GuiShaders.HAND_DEPTH_DEFAULT = 0.125f;
    }
}
