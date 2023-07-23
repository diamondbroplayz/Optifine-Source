// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import net.optifine.shaders.ITextureFormat;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.RenderStage;
import java.util.List;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import net.optifine.Config;

public class ShaderMacros
{
    private static String PREFIX_MACRO;
    public static final String MC_VERSION = "MC_VERSION";
    public static final String MC_GL_VERSION = "MC_GL_VERSION";
    public static final String MC_GLSL_VERSION = "MC_GLSL_VERSION";
    public static final String MC_OS_WINDOWS = "MC_OS_WINDOWS";
    public static final String MC_OS_MAC = "MC_OS_MAC";
    public static final String MC_OS_LINUX = "MC_OS_LINUX";
    public static final String MC_OS_OTHER = "MC_OS_OTHER";
    public static final String MC_GL_VENDOR_AMD = "MC_GL_VENDOR_AMD";
    public static final String MC_GL_VENDOR_ATI = "MC_GL_VENDOR_ATI";
    public static final String MC_GL_VENDOR_INTEL = "MC_GL_VENDOR_INTEL";
    public static final String MC_GL_VENDOR_MESA = "MC_GL_VENDOR_MESA";
    public static final String MC_GL_VENDOR_NVIDIA = "MC_GL_VENDOR_NVIDIA";
    public static final String MC_GL_VENDOR_XORG = "MC_GL_VENDOR_XORG";
    public static final String MC_GL_VENDOR_OTHER = "MC_GL_VENDOR_OTHER";
    public static final String MC_GL_RENDERER_RADEON = "MC_GL_RENDERER_RADEON";
    public static final String MC_GL_RENDERER_GEFORCE = "MC_GL_RENDERER_GEFORCE";
    public static final String MC_GL_RENDERER_QUADRO = "MC_GL_RENDERER_QUADRO";
    public static final String MC_GL_RENDERER_INTEL = "MC_GL_RENDERER_INTEL";
    public static final String MC_GL_RENDERER_GALLIUM = "MC_GL_RENDERER_GALLIUM";
    public static final String MC_GL_RENDERER_MESA = "MC_GL_RENDERER_MESA";
    public static final String MC_GL_RENDERER_OTHER = "MC_GL_RENDERER_OTHER";
    public static final String MC_FXAA_LEVEL = "MC_FXAA_LEVEL";
    public static final String MC_NORMAL_MAP = "MC_NORMAL_MAP";
    public static final String MC_SPECULAR_MAP = "MC_SPECULAR_MAP";
    public static final String MC_RENDER_QUALITY = "MC_RENDER_QUALITY";
    public static final String MC_SHADOW_QUALITY = "MC_SHADOW_QUALITY";
    public static final String MC_HAND_DEPTH = "MC_HAND_DEPTH";
    public static final String MC_OLD_HAND_LIGHT = "MC_OLD_HAND_LIGHT";
    public static final String MC_OLD_LIGHTING = "MC_OLD_LIGHTING";
    public static final String MC_ANISOTROPIC_FILTERING = "MC_ANISOTROPIC_FILTERING";
    public static final String MC_TEXTURE_FORMAT_ = "MC_TEXTURE_FORMAT_";
    private static ShaderMacro[] extensionMacros;
    private static ShaderMacro[] constantMacros;
    
    public static String getOs() {
        final ac.b os = ac.i();
        switch (ShaderMacros.ShaderMacros$1.$SwitchMap$net$minecraft$Util$OS[os.ordinal()]) {
            case 1: {
                return "MC_OS_WINDOWS";
            }
            case 2: {
                return "MC_OS_MAC";
            }
            case 3: {
                return "MC_OS_LINUX";
            }
            default: {
                return "MC_OS_OTHER";
            }
        }
    }
    
    public static String getVendor() {
        final String version = Config.openGlVersion;
        if (version != null && version.contains("Mesa")) {
            return "MC_GL_VENDOR_MESA";
        }
        String vendor = Config.openGlVendor;
        if (vendor == null) {
            return "MC_GL_VENDOR_OTHER";
        }
        vendor = vendor.toLowerCase();
        if (vendor.startsWith("amd")) {
            return "MC_GL_VENDOR_AMD";
        }
        if (vendor.startsWith("ati")) {
            return "MC_GL_VENDOR_ATI";
        }
        if (vendor.startsWith("intel")) {
            return "MC_GL_VENDOR_INTEL";
        }
        if (vendor.startsWith("nvidia")) {
            return "MC_GL_VENDOR_NVIDIA";
        }
        if (vendor.startsWith("x.org")) {
            return "MC_GL_VENDOR_XORG";
        }
        return "MC_GL_VENDOR_OTHER";
    }
    
    public static String getRenderer() {
        String renderer = Config.openGlRenderer;
        if (renderer == null) {
            return "MC_GL_RENDERER_OTHER";
        }
        renderer = renderer.toLowerCase();
        if (renderer.startsWith("amd")) {
            return "MC_GL_RENDERER_RADEON";
        }
        if (renderer.startsWith("ati")) {
            return "MC_GL_RENDERER_RADEON";
        }
        if (renderer.startsWith("radeon")) {
            return "MC_GL_RENDERER_RADEON";
        }
        if (renderer.startsWith("gallium")) {
            return "MC_GL_RENDERER_GALLIUM";
        }
        if (renderer.startsWith("intel")) {
            return "MC_GL_RENDERER_INTEL";
        }
        if (renderer.startsWith("geforce")) {
            return "MC_GL_RENDERER_GEFORCE";
        }
        if (renderer.startsWith("nvidia")) {
            return "MC_GL_RENDERER_GEFORCE";
        }
        if (renderer.startsWith("quadro")) {
            return "MC_GL_RENDERER_QUADRO";
        }
        if (renderer.startsWith("nvs")) {
            return "MC_GL_RENDERER_QUADRO";
        }
        if (renderer.startsWith("mesa")) {
            return "MC_GL_RENDERER_MESA";
        }
        return "MC_GL_RENDERER_OTHER";
    }
    
    public static String getPrefixMacro() {
        return ShaderMacros.PREFIX_MACRO;
    }
    
    public static ShaderMacro[] getExtensions() {
        if (ShaderMacros.extensionMacros == null) {
            final String[] exts = Config.getOpenGlExtensions();
            final ShaderMacro[] extMacros = new ShaderMacro[exts.length];
            for (int i = 0; i < exts.length; ++i) {
                extMacros[i] = new ShaderMacro(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, ShaderMacros.PREFIX_MACRO, exts[i]), "");
            }
            ShaderMacros.extensionMacros = extMacros;
        }
        return ShaderMacros.extensionMacros;
    }
    
    public static ShaderMacro[] getConstantMacros() {
        if (ShaderMacros.constantMacros == null) {
            final List<ShaderMacro> list = new ArrayList<ShaderMacro>();
            list.addAll(Arrays.asList(getRenderStages()));
            ShaderMacros.constantMacros = list.toArray(new ShaderMacro[list.size()]);
        }
        return ShaderMacros.constantMacros;
    }
    
    private static ShaderMacro[] getRenderStages() {
        final RenderStage[] rss = RenderStage.values();
        final ShaderMacro[] rsMacros = new ShaderMacro[rss.length];
        for (int i = 0; i < rss.length; ++i) {
            final RenderStage rs = rss[i];
            rsMacros[i] = new ShaderMacro(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, ShaderMacros.PREFIX_MACRO, rs.name()), invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, rs.ordinal()));
        }
        return rsMacros;
    }
    
    public static String getFixedMacroLines() {
        final StringBuilder sb = new StringBuilder();
        addMacroLine(sb, "MC_VERSION", Config.getMinecraftVersionInt());
        addMacroLine(sb, invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Config.getGlVersion().toInt()));
        addMacroLine(sb, invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Config.getGlslVersion().toInt()));
        addMacroLine(sb, getOs());
        addMacroLine(sb, getVendor());
        addMacroLine(sb, getRenderer());
        return sb.toString();
    }
    
    public static String getOptionMacroLines() {
        final StringBuilder sb = new StringBuilder();
        if (Shaders.configAntialiasingLevel > 0) {
            addMacroLine(sb, "MC_FXAA_LEVEL", Shaders.configAntialiasingLevel);
        }
        if (Shaders.configNormalMap) {
            addMacroLine(sb, "MC_NORMAL_MAP");
        }
        if (Shaders.configSpecularMap) {
            addMacroLine(sb, "MC_SPECULAR_MAP");
        }
        addMacroLine(sb, "MC_RENDER_QUALITY", Shaders.configRenderResMul);
        addMacroLine(sb, "MC_SHADOW_QUALITY", Shaders.configShadowResMul);
        addMacroLine(sb, "MC_HAND_DEPTH", Shaders.configHandDepthMul);
        if (Shaders.isOldHandLight()) {
            addMacroLine(sb, "MC_OLD_HAND_LIGHT");
        }
        if (Shaders.isOldLighting()) {
            addMacroLine(sb, "MC_OLD_LIGHTING");
        }
        if (Config.isAnisotropicFiltering()) {
            addMacroLine(sb, "MC_ANISOTROPIC_FILTERING", Config.getAnisotropicFilterLevel());
        }
        return sb.toString();
    }
    
    public static String getTextureMacroLines() {
        final fuu textureMap = Config.getTextureMap();
        if (textureMap == null) {
            return "";
        }
        final ITextureFormat textureFormat = textureMap.getTextureFormat();
        if (textureFormat == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        final String name = textureFormat.getMacroName();
        if (name != null) {
            addMacroLine(sb, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            final String ver = textureFormat.getMacroVersion();
            if (ver != null) {
                addMacroLine(sb, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, name, ver));
            }
        }
        return sb.toString();
    }
    
    public static String[] getHeaderMacroLines() {
        final String str = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, getFixedMacroLines(), getOptionMacroLines(), getTextureMacroLines());
        final String[] lines = Config.tokenize(str, "\n\r");
        return lines;
    }
    
    private static void addMacroLine(final StringBuilder sb, final String name, final int value) {
        sb.append("#define ");
        sb.append(name);
        sb.append(" ");
        sb.append(value);
        sb.append("\n");
    }
    
    private static void addMacroLine(final StringBuilder sb, final String name, final float value) {
        sb.append("#define ");
        sb.append(name);
        sb.append(" ");
        sb.append(value);
        sb.append("\n");
    }
    
    private static void addMacroLine(final StringBuilder sb, final String name) {
        sb.append("#define ");
        sb.append(name);
        sb.append("\n");
    }
    
    static {
        ShaderMacros.PREFIX_MACRO = "MC_";
    }
}
