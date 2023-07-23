// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.PointerBuffer;
import java.nio.IntBuffer;
import java.net.URI;
import com.mojang.blaze3d.platform.GLX;
import java.io.FileOutputStream;
import net.optifine.gui.GuiMessage;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import net.optifine.util.TimedEvent;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import net.optifine.util.PropertiesOrdered;
import org.lwjgl.opengl.GL30;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GL;
import java.io.InputStream;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.glfw.GLFW;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;
import org.apache.logging.log4j.Logger;
import net.optifine.config.GlVersion;

public class Config
{
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.20.1";
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "I5";
    public static final String VERSION = "OptiFine_1.20.1_HD_U_I5";
    private static String build;
    private static String newRelease;
    private static boolean notify64BitJava;
    public static String openGlVersion;
    public static String openGlRenderer;
    public static String openGlVendor;
    public static String[] openGlExtensions;
    public static GlVersion glVersion;
    public static GlVersion glslVersion;
    public static int minecraftVersionInt;
    private static enr gameSettings;
    private static enn minecraft;
    private static boolean initialized;
    private static Thread minecraftThread;
    private static int antialiasingLevel;
    private static int availableProcessors;
    public static boolean zoomMode;
    public static boolean zoomSmoothCamera;
    private static int texturePackClouds;
    private static boolean fullscreenModeChecked;
    private static boolean desktopModeChecked;
    public static final Float DEF_ALPHA_FUNC_LEVEL;
    private static final Logger LOGGER;
    public static final boolean logDetail;
    private static String mcDebugLast;
    private static int fpsMinLast;
    private static int chunkUpdatesLast;
    private static fuu textureMapTerrain;
    private static long timeLastFrameMs;
    private static long averageFrameTimeMs;
    private static long lastFrameTimeMs;
    private static boolean showFrameTime;
    
    private Config() {
    }
    
    public static String getVersion() {
        return "OptiFine_1.20.1_HD_U_I5";
    }
    
    public static String getVersionDebug() {
        final StringBuffer sb = new StringBuffer(32);
        if (isDynamicLights()) {
            sb.append("DL: ");
            sb.append(String.valueOf(DynamicLights.getCount()));
            sb.append(", ");
        }
        sb.append("OptiFine_1.20.1_HD_U_I5");
        final String shaderPack = Shaders.getShaderPackName();
        if (shaderPack != null) {
            sb.append(", ");
            sb.append(shaderPack);
        }
        return sb.toString();
    }
    
    public static void initGameSettings(final enr settings) {
        if (Config.gameSettings != null) {
            return;
        }
        Config.gameSettings = settings;
        updateAvailableProcessors();
        ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
        final String forgeVer = (String)Reflector.ForgeVersion_getVersion.call(new Object[0]);
        if (forgeVer != null) {
            dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, forgeVer));
            final ComparableVersion cv = new ComparableVersion(forgeVer);
            final ComparableVersion cvMax = new ComparableVersion("47.0.3");
            if (cv.compareTo(cvMax) > 1) {
                dbg(invokedynamic(makeConcatWithConstants:(Lnet/optifine/ComparableVersion;)Ljava/lang/String;, cvMax));
                Config.gameSettings.ofAaLevel = 0;
            }
        }
        Config.antialiasingLevel = Config.gameSettings.ofAaLevel;
    }
    
    public static void initDisplay() {
        checkInitialized();
        Config.minecraftThread = Thread.currentThread();
        updateThreadPriorities();
        Shaders.startup(enn.N());
    }
    
    public static void checkInitialized() {
        if (Config.initialized) {
            return;
        }
        if (enn.N().aM() == null) {
            return;
        }
        Config.initialized = true;
        checkOpenGlCaps();
        startVersionCheckThread();
    }
    
    private static void checkOpenGlCaps() {
        log("");
        log(getVersion());
        log(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, getBuild()));
        log(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("os.version")));
        log(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, System.getProperty("java.version"), System.getProperty("java.vendor")));
        log(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, System.getProperty("java.vm.name"), System.getProperty("java.vm.info"), System.getProperty("java.vm.vendor")));
        log(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, GLFW.glfwGetVersionString()));
        Config.openGlVersion = GL11.glGetString(7938);
        Config.openGlRenderer = GL11.glGetString(7937);
        Config.openGlVendor = GL11.glGetString(7936);
        log(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Config.openGlRenderer, Config.openGlVersion, Config.openGlVendor));
        log(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, getOpenGlVersionString()));
        final int maxTexSize = TextureUtils.getGLMaximumTextureSize();
        dbg(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, maxTexSize, maxTexSize));
    }
    
    public static String getBuild() {
        if (Config.build == null) {
            try {
                final InputStream in = getOptiFineResourceStream("/buildof.txt");
                if (in == null) {
                    return null;
                }
                Config.build = readLines(in)[0];
            }
            catch (Exception e) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
                Config.build = "";
            }
        }
        return Config.build;
    }
    
    public static InputStream getOptiFineResourceStream(final String name) {
        final InputStream in = ReflectorForge.getOptiFineResourceStream(name);
        if (in != null) {
            return in;
        }
        return Config.class.getResourceAsStream(name);
    }
    
    public static int getMinecraftVersionInt() {
        if (Config.minecraftVersionInt < 0) {
            String mcVer = "1.20.1";
            if (mcVer.contains("-")) {
                mcVer = tokenize(mcVer, "-")[0];
            }
            final String[] verStrs = tokenize(mcVer, ".");
            int ver = 0;
            if (verStrs.length > 0) {
                ver += 10000 * parseInt(verStrs[0], 0);
            }
            if (verStrs.length > 1) {
                ver += 100 * parseInt(verStrs[1], 0);
            }
            if (verStrs.length > 2) {
                ver += 1 * parseInt(verStrs[2], 0);
            }
            Config.minecraftVersionInt = ver;
        }
        return Config.minecraftVersionInt;
    }
    
    public static String getOpenGlVersionString() {
        final GlVersion ver = getGlVersion();
        final String verStr = invokedynamic(makeConcatWithConstants:(III)Ljava/lang/String;, ver.getMajor(), ver.getMinor(), ver.getRelease());
        return verStr;
    }
    
    private static GlVersion getGlVersionLwjgl() {
        final GLCapabilities glCapabilities = GL.getCapabilities();
        if (glCapabilities.OpenGL44) {
            return new GlVersion(4, 4);
        }
        if (glCapabilities.OpenGL43) {
            return new GlVersion(4, 3);
        }
        if (glCapabilities.OpenGL42) {
            return new GlVersion(4, 2);
        }
        if (glCapabilities.OpenGL41) {
            return new GlVersion(4, 1);
        }
        if (glCapabilities.OpenGL40) {
            return new GlVersion(4, 0);
        }
        if (glCapabilities.OpenGL33) {
            return new GlVersion(3, 3);
        }
        if (glCapabilities.OpenGL32) {
            return new GlVersion(3, 2);
        }
        if (glCapabilities.OpenGL31) {
            return new GlVersion(3, 1);
        }
        if (glCapabilities.OpenGL30) {
            return new GlVersion(3, 0);
        }
        if (glCapabilities.OpenGL21) {
            return new GlVersion(2, 1);
        }
        if (glCapabilities.OpenGL20) {
            return new GlVersion(2, 0);
        }
        if (glCapabilities.OpenGL15) {
            return new GlVersion(1, 5);
        }
        if (glCapabilities.OpenGL14) {
            return new GlVersion(1, 4);
        }
        if (glCapabilities.OpenGL13) {
            return new GlVersion(1, 3);
        }
        if (glCapabilities.OpenGL12) {
            return new GlVersion(1, 2);
        }
        if (glCapabilities.OpenGL11) {
            return new GlVersion(1, 1);
        }
        return new GlVersion(1, 0);
    }
    
    public static GlVersion getGlVersion() {
        if (Config.glVersion == null) {
            final String verStr = GL11.glGetString(7938);
            Config.glVersion = parseGlVersion(verStr, null);
            if (Config.glVersion == null) {
                Config.glVersion = getGlVersionLwjgl();
            }
            if (Config.glVersion == null) {
                Config.glVersion = new GlVersion(1, 0);
            }
        }
        return Config.glVersion;
    }
    
    public static GlVersion getGlslVersion() {
        if (Config.glslVersion == null) {
            final String verStr = GL11.glGetString(35724);
            Config.glslVersion = parseGlVersion(verStr, null);
            if (Config.glslVersion == null) {
                Config.glslVersion = new GlVersion(1, 10);
            }
        }
        return Config.glslVersion;
    }
    
    public static GlVersion parseGlVersion(final String versionString, final GlVersion def) {
        try {
            if (versionString == null) {
                return def;
            }
            final Pattern REGEXP_VERSION = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?");
            final Matcher matcher = REGEXP_VERSION.matcher(versionString);
            if (!matcher.matches()) {
                return def;
            }
            final int major = Integer.parseInt(matcher.group(1));
            final int minor = Integer.parseInt(matcher.group(2));
            final int release = (matcher.group(4) != null) ? Integer.parseInt(matcher.group(4)) : 0;
            final String suffix = matcher.group(5);
            return new GlVersion(major, minor, release, suffix);
        }
        catch (Exception e) {
            error("", e);
            return def;
        }
    }
    
    public static String[] getOpenGlExtensions() {
        if (Config.openGlExtensions == null) {
            Config.openGlExtensions = detectOpenGlExtensions();
        }
        return Config.openGlExtensions;
    }
    
    private static String[] detectOpenGlExtensions() {
        try {
            final GlVersion ver = getGlVersion();
            if (ver.getMajor() >= 3) {
                final int countExt = GL11.glGetInteger(33309);
                if (countExt > 0) {
                    final String[] exts = new String[countExt];
                    for (int i = 0; i < countExt; ++i) {
                        exts[i] = GL30.glGetStringi(7939, i);
                    }
                    return exts;
                }
            }
        }
        catch (Exception e) {
            error("", e);
        }
        try {
            final String extStr = GL11.glGetString(7939);
            final String[] exts2 = extStr.split(" ");
            return exts2;
        }
        catch (Exception e) {
            error("", e);
            return new String[0];
        }
    }
    
    public static void updateThreadPriorities() {
        updateAvailableProcessors();
        final int ELEVATED_PRIORITY = 8;
        if (isSingleProcessor()) {
            if (isSmoothWorld()) {
                Config.minecraftThread.setPriority(10);
                setThreadPriority("Server thread", 1);
            }
            else {
                Config.minecraftThread.setPriority(5);
                setThreadPriority("Server thread", 5);
            }
        }
        else {
            Config.minecraftThread.setPriority(10);
            setThreadPriority("Server thread", 5);
        }
    }
    
    private static void setThreadPriority(final String prefix, final int priority) {
        try {
            final ThreadGroup tg = Thread.currentThread().getThreadGroup();
            if (tg == null) {
                return;
            }
            final int num = (tg.activeCount() + 10) * 2;
            final Thread[] ts = new Thread[num];
            tg.enumerate(ts, false);
            for (int i = 0; i < ts.length; ++i) {
                final Thread t = ts[i];
                if (t != null) {
                    if (t.getName().startsWith(prefix)) {
                        t.setPriority(priority);
                    }
                }
            }
        }
        catch (Throwable e) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
        }
    }
    
    public static boolean isMinecraftThread() {
        return Thread.currentThread() == Config.minecraftThread;
    }
    
    private static void startVersionCheckThread() {
        final VersionCheckThread vct = new VersionCheckThread();
        vct.start();
    }
    
    public static boolean isMipmaps() {
        return (int)Config.gameSettings.y().c() > 0;
    }
    
    public static int getMipmapLevels() {
        return (int)Config.gameSettings.y().c();
    }
    
    public static int getMipmapType() {
        switch (Config.gameSettings.ofMipmapType) {
            case 0: {
                return 9986;
            }
            case 1: {
                return 9986;
            }
            case 2: {
                if (isMultiTexture()) {
                    return 9985;
                }
                return 9986;
            }
            case 3: {
                if (isMultiTexture()) {
                    return 9987;
                }
                return 9986;
            }
            default: {
                return 9986;
            }
        }
    }
    
    public static boolean isUseAlphaFunc() {
        final float alphaFuncLevel = getAlphaFuncLevel();
        return alphaFuncLevel > Config.DEF_ALPHA_FUNC_LEVEL + 1.0E-5f;
    }
    
    public static float getAlphaFuncLevel() {
        return Config.DEF_ALPHA_FUNC_LEVEL;
    }
    
    public static boolean isFogOff() {
        return Config.gameSettings.ofFogType == 3;
    }
    
    public static boolean isFogOn() {
        return Config.gameSettings.ofFogType != 3;
    }
    
    public static float getFogStart() {
        return Config.gameSettings.ofFogStart;
    }
    
    public static void detail(final String s) {
        if (Config.logDetail) {
            Config.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
        }
    }
    
    public static void dbg(final String s) {
        Config.LOGGER.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
    }
    
    public static void warn(final String s) {
        Config.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
    }
    
    public static void warn(final String s, final Throwable t) {
        Config.LOGGER.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s), t);
    }
    
    public static void error(final String s) {
        Config.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s));
    }
    
    public static void error(final String s, final Throwable t) {
        Config.LOGGER.error(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, s), t);
    }
    
    public static void log(final String s) {
        dbg(s);
    }
    
    public static int getUpdatesPerFrame() {
        return Config.gameSettings.ofChunkUpdates;
    }
    
    public static boolean isDynamicUpdates() {
        return Config.gameSettings.ofChunkUpdatesDynamic;
    }
    
    public static boolean isGraphicsFancy() {
        return Config.gameSettings.i().c() != eng.a;
    }
    
    public static boolean isGraphicsFabulous() {
        return Config.gameSettings.i().c() == eng.c;
    }
    
    public static boolean isRainFancy() {
        if (Config.gameSettings.ofRain == 0) {
            return isGraphicsFancy();
        }
        return Config.gameSettings.ofRain == 2;
    }
    
    public static boolean isRainOff() {
        return Config.gameSettings.ofRain == 3;
    }
    
    public static boolean isCloudsFancy() {
        if (Config.gameSettings.ofClouds != 0) {
            return Config.gameSettings.ofClouds == 2;
        }
        if (isShaders() && !Shaders.shaderPackClouds.isDefault()) {
            return Shaders.shaderPackClouds.isFancy();
        }
        if (Config.texturePackClouds != 0) {
            return Config.texturePackClouds == 2;
        }
        return isGraphicsFancy();
    }
    
    public static boolean isCloudsOff() {
        if (Config.gameSettings.ofClouds != 0) {
            return Config.gameSettings.ofClouds == 3;
        }
        if (isShaders() && !Shaders.shaderPackClouds.isDefault()) {
            return Shaders.shaderPackClouds.isOff();
        }
        return Config.texturePackClouds != 0 && Config.texturePackClouds == 3;
    }
    
    public static void updateTexturePackClouds() {
        Config.texturePackClouds = 0;
        final akx rm = getResourceManager();
        if (rm == null) {
            return;
        }
        try {
            final InputStream in = rm.getResourceOrThrow(new acq("optifine/color.properties")).d();
            if (in == null) {
                return;
            }
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            String cloudStr = props.getProperty("clouds");
            if (cloudStr == null) {
                return;
            }
            dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, cloudStr));
            cloudStr = cloudStr.toLowerCase();
            if (cloudStr.equals("fast")) {
                Config.texturePackClouds = 1;
            }
            if (cloudStr.equals("fancy")) {
                Config.texturePackClouds = 2;
            }
            if (cloudStr.equals("off") || cloudStr.equals("none")) {
                Config.texturePackClouds = 3;
            }
        }
        catch (Exception ex) {}
    }
    
    public static fwx getModelManager() {
        return Config.minecraft.ap().modelManager;
    }
    
    public static boolean isTreesFancy() {
        if (Config.gameSettings.ofTrees == 0) {
            return isGraphicsFancy();
        }
        return Config.gameSettings.ofTrees != 1;
    }
    
    public static boolean isTreesSmart() {
        return Config.gameSettings.ofTrees == 4;
    }
    
    public static boolean isCullFacesLeaves() {
        if (Config.gameSettings.ofTrees == 0) {
            return !isGraphicsFancy();
        }
        return Config.gameSettings.ofTrees == 4;
    }
    
    public static int limit(final int val, final int min, final int max) {
        if (val < min) {
            return min;
        }
        if (val > max) {
            return max;
        }
        return val;
    }
    
    public static long limit(final long val, final long min, final long max) {
        if (val < min) {
            return min;
        }
        if (val > max) {
            return max;
        }
        return val;
    }
    
    public static float limit(final float val, final float min, final float max) {
        if (val < min) {
            return min;
        }
        if (val > max) {
            return max;
        }
        return val;
    }
    
    public static double limit(final double val, final double min, final double max) {
        if (val < min) {
            return min;
        }
        if (val > max) {
            return max;
        }
        return val;
    }
    
    public static float limitTo1(final float val) {
        if (val < 0.0f) {
            return 0.0f;
        }
        if (val > 1.0f) {
            return 1.0f;
        }
        return val;
    }
    
    public static boolean isAnimatedWater() {
        return Config.gameSettings.ofAnimatedWater != 2;
    }
    
    public static boolean isGeneratedWater() {
        return Config.gameSettings.ofAnimatedWater == 1;
    }
    
    public static boolean isAnimatedPortal() {
        return Config.gameSettings.ofAnimatedPortal;
    }
    
    public static boolean isAnimatedLava() {
        return Config.gameSettings.ofAnimatedLava != 2;
    }
    
    public static boolean isGeneratedLava() {
        return Config.gameSettings.ofAnimatedLava == 1;
    }
    
    public static boolean isAnimatedFire() {
        return Config.gameSettings.ofAnimatedFire;
    }
    
    public static boolean isAnimatedRedstone() {
        return Config.gameSettings.ofAnimatedRedstone;
    }
    
    public static boolean isAnimatedExplosion() {
        return Config.gameSettings.ofAnimatedExplosion;
    }
    
    public static boolean isAnimatedFlame() {
        return Config.gameSettings.ofAnimatedFlame;
    }
    
    public static boolean isAnimatedSmoke() {
        return Config.gameSettings.ofAnimatedSmoke;
    }
    
    public static boolean isVoidParticles() {
        return Config.gameSettings.ofVoidParticles;
    }
    
    public static boolean isWaterParticles() {
        return Config.gameSettings.ofWaterParticles;
    }
    
    public static boolean isRainSplash() {
        return Config.gameSettings.ofRainSplash;
    }
    
    public static boolean isPortalParticles() {
        return Config.gameSettings.ofPortalParticles;
    }
    
    public static boolean isPotionParticles() {
        return Config.gameSettings.ofPotionParticles;
    }
    
    public static boolean isFireworkParticles() {
        return Config.gameSettings.ofFireworkParticles;
    }
    
    public static float getAmbientOcclusionLevel() {
        if (isShaders() && Shaders.aoLevel >= 0.0f) {
            return Shaders.aoLevel;
        }
        return (float)Config.gameSettings.ofAoLevel;
    }
    
    public static String listToString(final List list) {
        return listToString(list, ", ");
    }
    
    public static String listToString(final List list, final String separator) {
        if (list == null) {
            return "";
        }
        final StringBuffer buf = new StringBuffer(list.size() * 5);
        for (int i = 0; i < list.size(); ++i) {
            final Object obj = list.get(i);
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(String.valueOf(obj));
        }
        return buf.toString();
    }
    
    public static String arrayToString(final Object[] arr) {
        return arrayToString(arr, ", ");
    }
    
    public static String arrayToString(final Object[] arr, final String separator) {
        if (arr == null) {
            return "";
        }
        final StringBuffer buf = new StringBuffer(arr.length * 5);
        for (int i = 0; i < arr.length; ++i) {
            final Object obj = arr[i];
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(String.valueOf(obj));
        }
        return buf.toString();
    }
    
    public static String arrayToString(final int[] arr) {
        return arrayToString(arr, ", ");
    }
    
    public static String arrayToString(final int[] arr, final String separator) {
        if (arr == null) {
            return "";
        }
        final StringBuffer buf = new StringBuffer(arr.length * 5);
        for (int i = 0; i < arr.length; ++i) {
            final int x = arr[i];
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(String.valueOf(x));
        }
        return buf.toString();
    }
    
    public static String arrayToString(final float[] arr) {
        return arrayToString(arr, ", ");
    }
    
    public static String arrayToString(final float[] arr, final String separator) {
        if (arr == null) {
            return "";
        }
        final StringBuffer buf = new StringBuffer(arr.length * 5);
        for (int i = 0; i < arr.length; ++i) {
            final float x = arr[i];
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(String.valueOf(x));
        }
        return buf.toString();
    }
    
    public static enn getMinecraft() {
        return Config.minecraft;
    }
    
    public static fuw getTextureManager() {
        return Config.minecraft.X();
    }
    
    public static akx getResourceManager() {
        return Config.minecraft.Y();
    }
    
    public static InputStream getResourceStream(final acq location) throws IOException {
        return getResourceStream(Config.minecraft.Y(), location);
    }
    
    public static InputStream getResourceStream(final akx resourceManager, final acq location) throws IOException {
        final akv res = resourceManager.getResourceOrThrow(location);
        if (res == null) {
            return null;
        }
        return res.d();
    }
    
    public static akv getResource(final acq location) throws IOException {
        return Config.minecraft.Y().getResourceOrThrow(location);
    }
    
    public static boolean hasResource(final acq location) {
        if (location == null) {
            return false;
        }
        final ajl rp = getDefiningResourcePack(location);
        return rp != null;
    }
    
    public static boolean hasResource(final akx resourceManager, final acq location) {
        try {
            final akv res = resourceManager.getResourceOrThrow(location);
            return res != null;
        }
        catch (IOException e) {
            return false;
        }
    }
    
    public static boolean hasResource(final ajl rp, final acq loc) {
        if (rp == null || loc == null) {
            return false;
        }
        final akp<InputStream> supplier = (akp<InputStream>)rp.a(ajm.a, loc);
        return supplier != null;
    }
    
    public static ajl[] getResourcePacks() {
        final aki rep = Config.minecraft.Z();
        final Collection<akg> packInfos = (Collection<akg>)rep.f();
        final List list = new ArrayList();
        for (final akg rpic : packInfos) {
            final ajl rp = rpic.e();
            if (rp == getDefaultResourcePack()) {
                continue;
            }
            list.add(rp);
        }
        final ajl[] rps = list.toArray(new ajl[list.size()]);
        return rps;
    }
    
    public static String getResourcePackNames() {
        if (Config.minecraft.Y() == null) {
            return "";
        }
        final ajl[] rps = getResourcePacks();
        if (rps.length <= 0) {
            return getDefaultResourcePack().a();
        }
        final String[] names = new String[rps.length];
        for (int i = 0; i < rps.length; ++i) {
            names[i] = rps[i].a();
        }
        final String nameStr = arrayToString(names);
        return nameStr;
    }
    
    public static ajo getDefaultResourcePack() {
        return Config.minecraft.aa();
    }
    
    public static boolean isFromDefaultResourcePack(final acq loc) {
        return getDefiningResourcePack(loc) == getDefaultResourcePack();
    }
    
    public static ajl getDefiningResourcePack(final acq location) {
        final aki rep = Config.minecraft.Z();
        final Collection<akg> packInfos = (Collection<akg>)rep.f();
        final List<akg> entries = (List<akg>)(List)packInfos;
        for (int i = entries.size() - 1; i >= 0; --i) {
            final akg entry = entries.get(i);
            final ajl rp = entry.e();
            if (rp.a(ajm.a, location) != null) {
                return rp;
            }
        }
        return null;
    }
    
    public static InputStream getResourceStream(final ajl rp, final ajm type, final acq location) throws IOException {
        final akp<InputStream> supplier = (akp<InputStream>)rp.a(type, location);
        if (supplier == null) {
            return null;
        }
        return (InputStream)supplier.get();
    }
    
    public static fjv getRenderGlobal() {
        return Config.minecraft.f;
    }
    
    public static fjv getWorldRenderer() {
        return Config.minecraft.f;
    }
    
    public static fjq getGameRenderer() {
        return Config.minecraft.j;
    }
    
    public static fow getEntityRenderDispatcher() {
        return Config.minecraft.an();
    }
    
    public static boolean isBetterGrass() {
        return Config.gameSettings.ofBetterGrass != 3;
    }
    
    public static boolean isBetterGrassFancy() {
        return Config.gameSettings.ofBetterGrass == 2;
    }
    
    public static boolean isWeatherEnabled() {
        return Config.gameSettings.ofWeather;
    }
    
    public static boolean isSkyEnabled() {
        return Config.gameSettings.ofSky;
    }
    
    public static boolean isSunMoonEnabled() {
        return Config.gameSettings.ofSunMoon;
    }
    
    public static boolean isSunTexture() {
        return isSunMoonEnabled() && (!isShaders() || Shaders.isSun());
    }
    
    public static boolean isMoonTexture() {
        return isSunMoonEnabled() && (!isShaders() || Shaders.isMoon());
    }
    
    public static boolean isVignetteEnabled() {
        if (isShaders() && !Shaders.isVignette()) {
            return false;
        }
        if (Config.gameSettings.ofVignette == 0) {
            return isGraphicsFancy();
        }
        return Config.gameSettings.ofVignette == 2;
    }
    
    public static boolean isStarsEnabled() {
        return Config.gameSettings.ofStars;
    }
    
    public static void sleep(final long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
            error("", e);
        }
    }
    
    public static boolean isTimeDayOnly() {
        return Config.gameSettings.ofTime == 1;
    }
    
    public static boolean isTimeDefault() {
        return Config.gameSettings.ofTime == 0;
    }
    
    public static boolean isTimeNightOnly() {
        return Config.gameSettings.ofTime == 2;
    }
    
    public static int getAnisotropicFilterLevel() {
        return Config.gameSettings.ofAfLevel;
    }
    
    public static boolean isAnisotropicFiltering() {
        return getAnisotropicFilterLevel() > 1;
    }
    
    public static int getAntialiasingLevel() {
        return Config.antialiasingLevel;
    }
    
    public static boolean isAntialiasing() {
        return getAntialiasingLevel() > 0;
    }
    
    public static boolean isAntialiasingConfigured() {
        return getGameSettings().ofAaLevel > 0;
    }
    
    public static boolean isMultiTexture() {
        return getAnisotropicFilterLevel() > 1 || getAntialiasingLevel() > 0;
    }
    
    public static boolean between(final int val, final int min, final int max) {
        return val >= min && val <= max;
    }
    
    public static boolean between(final float val, final float min, final float max) {
        return val >= min && val <= max;
    }
    
    public static boolean between(final double val, final double min, final double max) {
        return val >= min && val <= max;
    }
    
    public static boolean isDrippingWaterLava() {
        return Config.gameSettings.ofDrippingWaterLava;
    }
    
    public static boolean isBetterSnow() {
        return Config.gameSettings.ofBetterSnow;
    }
    
    public static int parseInt(String str, final int defVal) {
        try {
            if (str == null) {
                return defVal;
            }
            str = str.trim();
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return defVal;
        }
    }
    
    public static int parseHexInt(String str, final int defVal) {
        try {
            if (str == null) {
                return defVal;
            }
            str = str.trim();
            if (str.startsWith("0x")) {
                str = str.substring(2);
            }
            return Integer.parseInt(str, 16);
        }
        catch (NumberFormatException e) {
            return defVal;
        }
    }
    
    public static float parseFloat(String str, final float defVal) {
        try {
            if (str == null) {
                return defVal;
            }
            str = str.trim();
            return Float.parseFloat(str);
        }
        catch (NumberFormatException e) {
            return defVal;
        }
    }
    
    public static boolean parseBoolean(String str, final boolean defVal) {
        try {
            if (str == null) {
                return defVal;
            }
            str = str.trim();
            return Boolean.parseBoolean(str);
        }
        catch (NumberFormatException e) {
            return defVal;
        }
    }
    
    public static Boolean parseBoolean(String str, final Boolean defVal) {
        try {
            if (str == null) {
                return defVal;
            }
            str = str.trim().toLowerCase();
            if (str.equals("true")) {
                return Boolean.TRUE;
            }
            if (str.equals("false")) {
                return Boolean.FALSE;
            }
            return defVal;
        }
        catch (NumberFormatException e) {
            return defVal;
        }
    }
    
    public static String[] tokenize(final String str, final String delim) {
        final StringTokenizer tok = new StringTokenizer(str, delim);
        final List list = new ArrayList();
        while (tok.hasMoreTokens()) {
            final String token = tok.nextToken();
            list.add(token);
        }
        final String[] strs = list.toArray(new String[list.size()]);
        return strs;
    }
    
    public static boolean isAnimatedTerrain() {
        return Config.gameSettings.ofAnimatedTerrain;
    }
    
    public static boolean isAnimatedTextures() {
        return Config.gameSettings.ofAnimatedTextures;
    }
    
    public static boolean isSwampColors() {
        return Config.gameSettings.ofSwampColors;
    }
    
    public static boolean isRandomEntities() {
        return Config.gameSettings.ofRandomEntities;
    }
    
    public static void checkGlError(final String loc) {
        final int errorCode = GlStateManager._getError();
        if (errorCode != 0 && GlErrors.isEnabled(errorCode)) {
            final String errorText = getGlErrorString(errorCode);
            final String messageLog = String.format("OpenGL error: %s (%s), at: %s", errorCode, errorText, loc);
            error(messageLog);
            if (isShowGlErrors() && TimedEvent.isActive("ShowGlError", 10000L)) {
                final String message = fvz.a("of.message.openglError", new Object[] { errorCode, errorText });
                Config.minecraft.l.d().a((sw)sw.b(message));
            }
        }
    }
    
    public static boolean isSmoothBiomes() {
        return (int)Config.gameSettings.A().c() > 0;
    }
    
    public static int getBiomeBlendRadius() {
        return (int)Config.gameSettings.A().c();
    }
    
    public static boolean isCustomColors() {
        return Config.gameSettings.ofCustomColors;
    }
    
    public static boolean isCustomSky() {
        return Config.gameSettings.ofCustomSky;
    }
    
    public static boolean isCustomFonts() {
        return Config.gameSettings.ofCustomFonts;
    }
    
    public static boolean isShowCapes() {
        return Config.gameSettings.ofShowCapes;
    }
    
    public static boolean isConnectedTextures() {
        return Config.gameSettings.ofConnectedTextures != 3;
    }
    
    public static boolean isNaturalTextures() {
        return Config.gameSettings.ofNaturalTextures;
    }
    
    public static boolean isEmissiveTextures() {
        return Config.gameSettings.ofEmissiveTextures;
    }
    
    public static boolean isConnectedTexturesFancy() {
        return Config.gameSettings.ofConnectedTextures == 2;
    }
    
    public static boolean isFastRender() {
        return Config.gameSettings.ofFastRender;
    }
    
    public static boolean isShaders() {
        return Shaders.shaderPackLoaded;
    }
    
    public static boolean isShadersShadows() {
        return isShaders() && Shaders.hasShadowMap;
    }
    
    public static String[] readLines(final File file) throws IOException {
        final FileInputStream fis = new FileInputStream(file);
        return readLines(fis);
    }
    
    public static String[] readLines(final InputStream is) throws IOException {
        final List list = new ArrayList();
        final InputStreamReader isr = new InputStreamReader(is, "ASCII");
        final BufferedReader br = new BufferedReader(isr);
        while (true) {
            final String line = br.readLine();
            if (line == null) {
                break;
            }
            list.add(line);
        }
        final String[] lines = list.toArray(new String[list.size()]);
        return lines;
    }
    
    public static String readFile(final File file) throws IOException {
        final FileInputStream fin = new FileInputStream(file);
        return readInputStream(fin, "ASCII");
    }
    
    public static String readInputStream(final InputStream in) throws IOException {
        return readInputStream(in, "ASCII");
    }
    
    public static String readInputStream(final InputStream in, final String encoding) throws IOException {
        final InputStreamReader inr = new InputStreamReader(in, encoding);
        final BufferedReader br = new BufferedReader(inr);
        final StringBuffer sb = new StringBuffer();
        while (true) {
            final String line = br.readLine();
            if (line == null) {
                break;
            }
            sb.append(line);
            sb.append("\n");
        }
        in.close();
        return sb.toString();
    }
    
    public static byte[] readAll(final InputStream is) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] buf = new byte[1024];
        while (true) {
            final int len = is.read(buf);
            if (len < 0) {
                break;
            }
            baos.write(buf, 0, len);
        }
        is.close();
        final byte[] bytes = baos.toByteArray();
        return bytes;
    }
    
    public static enr getGameSettings() {
        return Config.gameSettings;
    }
    
    public static String getNewRelease() {
        return Config.newRelease;
    }
    
    public static void setNewRelease(final String newRelease) {
        Config.newRelease = newRelease;
    }
    
    public static int compareRelease(final String rel1, final String rel2) {
        final String[] rels1 = splitRelease(rel1);
        final String[] rels2 = splitRelease(rel2);
        final String branch1 = rels1[0];
        final String branch2 = rels2[0];
        if (!branch1.equals(branch2)) {
            return branch1.compareTo(branch2);
        }
        final int rev1 = parseInt(rels1[1], -1);
        final int rev2 = parseInt(rels2[1], -1);
        if (rev1 != rev2) {
            return rev1 - rev2;
        }
        final String suf1 = rels1[2];
        final String suf2 = rels2[2];
        if (!suf1.equals(suf2)) {
            if (suf1.isEmpty()) {
                return 1;
            }
            if (suf2.isEmpty()) {
                return -1;
            }
        }
        return suf1.compareTo(suf2);
    }
    
    private static String[] splitRelease(final String relStr) {
        if (relStr == null || relStr.length() <= 0) {
            return new String[] { "", "", "" };
        }
        final Pattern p = Pattern.compile("([A-Z])([0-9]+)(.*)");
        final Matcher m = p.matcher(relStr);
        if (!m.matches()) {
            return new String[] { "", "", "" };
        }
        final String branch = normalize(m.group(1));
        final String revision = normalize(m.group(2));
        final String suffix = normalize(m.group(3));
        return new String[] { branch, revision, suffix };
    }
    
    public static int intHash(int x) {
        x = (x ^ 0x3D ^ x >> 16);
        x += x << 3;
        x ^= x >> 4;
        x *= 668265261;
        x ^= x >> 15;
        return x;
    }
    
    public static int getRandom(final gu blockPos, final int face) {
        int rand = intHash(face + 37);
        rand = intHash(rand + blockPos.u());
        rand = intHash(rand + blockPos.w());
        rand = intHash(rand + blockPos.v());
        return rand;
    }
    
    public static int getAvailableProcessors() {
        return Config.availableProcessors;
    }
    
    public static void updateAvailableProcessors() {
        Config.availableProcessors = Runtime.getRuntime().availableProcessors();
    }
    
    public static boolean isSingleProcessor() {
        return getAvailableProcessors() <= 1;
    }
    
    public static boolean isSmoothWorld() {
        return Config.gameSettings.ofSmoothWorld;
    }
    
    public static boolean isLazyChunkLoading() {
        return Config.gameSettings.ofLazyChunkLoading;
    }
    
    public static boolean isDynamicFov() {
        return Config.gameSettings.ofDynamicFov;
    }
    
    public static boolean isAlternateBlocks() {
        return Config.gameSettings.ofAlternateBlocks;
    }
    
    public static int getChunkViewDistance() {
        if (Config.gameSettings == null) {
            return 10;
        }
        final int chunkDistance = (int)Config.gameSettings.d().c();
        return chunkDistance;
    }
    
    public static boolean equals(final Object o1, final Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }
    
    public static boolean equalsOne(final Object a, final Object[] bs) {
        if (bs == null) {
            return false;
        }
        for (int i = 0; i < bs.length; ++i) {
            final Object b = bs[i];
            if (equals(a, b)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean equalsOne(final int val, final int[] vals) {
        for (int i = 0; i < vals.length; ++i) {
            if (vals[i] == val) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isSameOne(final Object a, final Object[] bs) {
        if (bs == null) {
            return false;
        }
        for (int i = 0; i < bs.length; ++i) {
            final Object b = bs[i];
            if (a == b) {
                return true;
            }
        }
        return false;
    }
    
    public static String normalize(final String s) {
        if (s == null) {
            return "";
        }
        return s;
    }
    
    private static ByteBuffer readIconImage(final InputStream is) throws IOException {
        final BufferedImage var2 = ImageIO.read(is);
        final int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
        final ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
        final int[] var5 = var3;
        for (int var6 = var3.length, var7 = 0; var7 < var6; ++var7) {
            final int var8 = var5[var7];
            var4.putInt(var8 << 8 | (var8 >> 24 & 0xFF));
        }
        var4.flip();
        return var4;
    }
    
    public static Object[] addObjectToArray(final Object[] arr, final Object obj) {
        if (arr == null) {
            throw new NullPointerException("The given array is NULL");
        }
        final int arrLen = arr.length;
        final int newLen = arrLen + 1;
        final Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(arr, 0, newArr, 0, arrLen);
        newArr[arrLen] = obj;
        return newArr;
    }
    
    public static Object[] addObjectToArray(final Object[] arr, final Object obj, final int index) {
        final List list = new ArrayList(Arrays.asList(arr));
        list.add(index, obj);
        final Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), list.size());
        return list.toArray(newArr);
    }
    
    public static Object[] addObjectsToArray(final Object[] arr, final Object[] objs) {
        if (arr == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (objs.length == 0) {
            return arr;
        }
        final int arrLen = arr.length;
        final int newLen = arrLen + objs.length;
        final Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(arr, 0, newArr, 0, arrLen);
        System.arraycopy(objs, 0, newArr, arrLen, objs.length);
        return newArr;
    }
    
    public static Object[] removeObjectFromArray(final Object[] arr, final Object obj) {
        final List list = new ArrayList(Arrays.asList(arr));
        list.remove(obj);
        final Object[] newArr = collectionToArray(list, arr.getClass().getComponentType());
        return newArr;
    }
    
    public static Object[] collectionToArray(final Collection coll, final Class elementClass) {
        if (coll == null) {
            return null;
        }
        if (elementClass == null) {
            return null;
        }
        if (elementClass.isPrimitive()) {
            throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(Ljava/lang/Class;)Ljava/lang/String;, elementClass));
        }
        final Object[] array = (Object[])Array.newInstance(elementClass, coll.size());
        return coll.toArray(array);
    }
    
    public static boolean isCustomItems() {
        return Config.gameSettings.ofCustomItems;
    }
    
    public static void drawFps(final eox graphicsIn) {
        final int updates = getChunkUpdates();
        final int renderersActive = Config.minecraft.f.k();
        final int entities = Config.minecraft.f.getCountEntitiesRendered();
        final int tileEntities = Config.minecraft.f.getCountTileEntitiesRendered();
        final String fpsStr = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;IIII)Ljava/lang/String;, getFpsString(), renderersActive, entities, tileEntities, updates);
        graphicsIn.a(Config.minecraft.h, fpsStr, 2, 2, -2039584, false);
    }
    
    public static String getFpsString() {
        final int fps = getFpsAverage();
        final int fpsMin = getFpsMin();
        if (Config.showFrameTime) {
            final String timeMs = String.format("%.1f", 1000.0 / limit(fps, 1, Integer.MAX_VALUE));
            final String timeMaxMs = String.format("%.1f", 1000.0 / limit(fpsMin, 1, Integer.MAX_VALUE));
            final String fpsStr = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, timeMs, timeMaxMs);
            return fpsStr;
        }
        final String fpsStr2 = invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, fps, fpsMin);
        return fpsStr2;
    }
    
    public static boolean isShowFrameTime() {
        return Config.showFrameTime;
    }
    
    public static int getFpsAverage() {
        final int fps = Reflector.getFieldValueInt(Reflector.Minecraft_debugFPS, -1);
        return fps;
    }
    
    public static int getFpsMin() {
        return Config.fpsMinLast;
    }
    
    public static int getChunkUpdates() {
        return Config.chunkUpdatesLast;
    }
    
    public static void updateFpsMin() {
        final aoo ft = Config.minecraft.aq();
        final long[] frames = ft.c();
        final int index = ft.b();
        final int indexEnd = ft.a();
        if (index == indexEnd) {
            return;
        }
        int fps = Reflector.getFieldValueInt(Reflector.Minecraft_debugFPS, -1);
        if (fps <= 0) {
            fps = 1;
        }
        long timeMaxNs;
        final long timeAvgNs = timeMaxNs = (long)(1.0 / fps * 1.0E9);
        long timeTotalNs = 0L;
        long timeNs;
        for (int ix = apa.b(index - 1, frames.length); ix != indexEnd && timeTotalNs < 1.0E9; timeTotalNs += timeNs, ix = apa.b(ix - 1, frames.length)) {
            timeNs = frames[ix];
            if (timeNs > timeMaxNs) {
                timeMaxNs = timeNs;
            }
        }
        final double timeMaxSec = timeMaxNs / 1.0E9;
        Config.fpsMinLast = (int)(1.0 / timeMaxSec);
    }
    
    private static void updateChunkUpdates() {
        Config.chunkUpdatesLast = fmp.renderChunksUpdated;
        fmp.renderChunksUpdated = 0;
    }
    
    public static int getBitsOs() {
        final String progFiles86 = System.getenv("ProgramFiles(X86)");
        if (progFiles86 != null) {
            return 64;
        }
        return 32;
    }
    
    public static int getBitsJre() {
        final String[] propNames = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        for (int i = 0; i < propNames.length; ++i) {
            final String propName = propNames[i];
            final String propVal = System.getProperty(propName);
            if (propVal != null) {
                if (propVal.contains("64")) {
                    return 64;
                }
            }
        }
        return 32;
    }
    
    public static boolean isNotify64BitJava() {
        return Config.notify64BitJava;
    }
    
    public static void setNotify64BitJava(final boolean flag) {
        Config.notify64BitJava = flag;
    }
    
    public static boolean isConnectedModels() {
        return false;
    }
    
    public static void showGuiMessage(final String line1, final String line2) {
        final GuiMessage gui = new GuiMessage(Config.minecraft.z, line1, line2);
        Config.minecraft.a((euq)gui);
    }
    
    public static int[] addIntToArray(final int[] intArray, final int intValue) {
        return addIntsToArray(intArray, new int[] { intValue });
    }
    
    public static int[] addIntsToArray(final int[] intArray, final int[] copyFrom) {
        if (intArray == null || copyFrom == null) {
            throw new NullPointerException("The given array is NULL");
        }
        final int arrLen = intArray.length;
        final int newLen = arrLen + copyFrom.length;
        final int[] newArray = new int[newLen];
        System.arraycopy(intArray, 0, newArray, 0, arrLen);
        for (int index = 0; index < copyFrom.length; ++index) {
            newArray[index + arrLen] = copyFrom[index];
        }
        return newArray;
    }
    
    public static void writeFile(final File file, final String str) throws IOException {
        final FileOutputStream fos = new FileOutputStream(file);
        final byte[] bytes = str.getBytes("ASCII");
        fos.write(bytes);
        fos.close();
    }
    
    public static void setTextureMap(final fuu textureMapTerrain) {
        Config.textureMapTerrain = textureMapTerrain;
    }
    
    public static fuu getTextureMap() {
        return Config.textureMapTerrain;
    }
    
    public static boolean isDynamicLights() {
        return Config.gameSettings.ofDynamicLights != 3;
    }
    
    public static boolean isDynamicLightsFast() {
        return Config.gameSettings.ofDynamicLights == 1;
    }
    
    public static boolean isDynamicHandLight() {
        return isDynamicLights() && (!isShaders() || Shaders.isDynamicHandLight());
    }
    
    public static boolean isCustomEntityModels() {
        return Config.gameSettings.ofCustomEntityModels;
    }
    
    public static boolean isCustomGuis() {
        return Config.gameSettings.ofCustomGuis;
    }
    
    public static int getScreenshotSize() {
        return Config.gameSettings.ofScreenshotSize;
    }
    
    public static int[] toPrimitive(final Integer[] arr) {
        if (arr == null) {
            return null;
        }
        if (arr.length == 0) {
            return new int[0];
        }
        final int[] intArr = new int[arr.length];
        for (int i = 0; i < intArr.length; ++i) {
            intArr[i] = arr[i];
        }
        return intArr;
    }
    
    public static boolean isRenderRegions() {
        return !isMultiTexture() && Config.gameSettings.ofRenderRegions && GlStateManager.vboRegions;
    }
    
    public static boolean isVbo() {
        return GLX.useVbo();
    }
    
    public static boolean isSmoothFps() {
        return Config.gameSettings.ofSmoothFps;
    }
    
    public static boolean openWebLink(final URI uri) {
        ac.setExceptionOpenUrl((Exception)null);
        ac.i().a(uri);
        final Exception error = ac.getExceptionOpenUrl();
        return error == null;
    }
    
    public static boolean isShowGlErrors() {
        return Config.gameSettings.ofShowGlErrors;
    }
    
    public static String arrayToString(final boolean[] arr, final String separator) {
        if (arr == null) {
            return "";
        }
        final StringBuffer buf = new StringBuffer(arr.length * 5);
        for (int i = 0; i < arr.length; ++i) {
            final boolean x = arr[i];
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(String.valueOf(x));
        }
        return buf.toString();
    }
    
    public static boolean isIntegratedServerRunning() {
        return Config.minecraft.S() != null && Config.minecraft.Q();
    }
    
    public static IntBuffer createDirectIntBuffer(final int capacity) {
        return ehh.a(capacity << 2).asIntBuffer();
    }
    
    public static PointerBuffer createDirectPointerBuffer(final int capacity) {
        return PointerBuffer.allocateDirect(capacity);
    }
    
    public static String getGlErrorString(final int err) {
        switch (err) {
            case 0: {
                return "No error";
            }
            case 1280: {
                return "Invalid enum";
            }
            case 1281: {
                return "Invalid value";
            }
            case 1282: {
                return "Invalid operation";
            }
            case 1286: {
                return "Invalid framebuffer operation";
            }
            case 1285: {
                return "Out of memory";
            }
            case 1284: {
                return "Stack underflow";
            }
            case 1283: {
                return "Stack overflow";
            }
            default: {
                return "Unknown";
            }
        }
    }
    
    public static boolean isKeyDown(final int key) {
        return GLFW.glfwGetKey(Config.minecraft.aM().i(), key) == 1;
    }
    
    public static boolean isTrue(final Boolean val) {
        return val != null && val;
    }
    
    public static boolean isFalse(final Boolean val) {
        return val != null && !val;
    }
    
    public static boolean isReloadingResources() {
        if (Config.minecraft.aJ() == null) {
            return false;
        }
        if (Config.minecraft.aJ() instanceof eud) {
            final eud rlpg = (eud)Config.minecraft.aJ();
            if (rlpg.isFadeOut()) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isQuadsToTriangles() {
        return isShaders() && !Shaders.canRenderQuads();
    }
    
    public static void frameStart() {
        final long timeNowMs = System.currentTimeMillis();
        long frameTimeMs = timeNowMs - Config.timeLastFrameMs;
        Config.timeLastFrameMs = timeNowMs;
        frameTimeMs = limit(frameTimeMs, 1L, 1000L);
        Config.averageFrameTimeMs = (Config.averageFrameTimeMs + frameTimeMs) / 2L;
        Config.averageFrameTimeMs = limit(Config.averageFrameTimeMs, 1L, 1000L);
        Config.lastFrameTimeMs = frameTimeMs;
        if (Config.minecraft.A != Config.mcDebugLast) {
            Config.mcDebugLast = Config.minecraft.A;
            updateFpsMin();
            updateChunkUpdates();
        }
    }
    
    public static long getLastFrameTimeMs() {
        return Config.lastFrameTimeMs;
    }
    
    public static long getAverageFrameTimeMs() {
        return Config.averageFrameTimeMs;
    }
    
    public static float getAverageFrameTimeSec() {
        final float frameTimeSec = getAverageFrameTimeMs() / 1000.0f;
        return frameTimeSec;
    }
    
    public static long getAverageFrameFps() {
        final long frameFps = 1000L / getAverageFrameTimeMs();
        return frameFps;
    }
    
    public static void checkNull(final Object obj, final String msg) throws NullPointerException {
        if (obj == null) {
            throw new NullPointerException(msg);
        }
    }
    
    public static boolean isTelemetryOn() {
        return Config.gameSettings.ofTelemetry != 2;
    }
    
    public static boolean isTelemetryAnonymous() {
        return Config.gameSettings.ofTelemetry == 1;
    }
    
    static {
        Config.build = null;
        Config.newRelease = null;
        Config.notify64BitJava = false;
        Config.openGlVersion = null;
        Config.openGlRenderer = null;
        Config.openGlVendor = null;
        Config.openGlExtensions = null;
        Config.glVersion = null;
        Config.glslVersion = null;
        Config.minecraftVersionInt = -1;
        Config.gameSettings = null;
        Config.minecraft = enn.N();
        Config.initialized = false;
        Config.minecraftThread = null;
        Config.antialiasingLevel = 0;
        Config.availableProcessors = 0;
        Config.zoomMode = false;
        Config.zoomSmoothCamera = false;
        Config.texturePackClouds = 0;
        Config.fullscreenModeChecked = false;
        Config.desktopModeChecked = false;
        DEF_ALPHA_FUNC_LEVEL = 0.1f;
        LOGGER = LogManager.getLogger();
        logDetail = System.getProperty("log.detail", "false").equals("true");
        Config.mcDebugLast = null;
        Config.fpsMinLast = 0;
        Config.chunkUpdatesLast = 0;
        Config.showFrameTime = Boolean.getBoolean("frame.time");
    }
}
