// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import org.lwjgl.opengl.GL30;
import org.joml.Matrix3f;
import net.optifine.render.RenderUtils;
import java.util.Locale;
import net.optifine.Lang;
import java.util.HashMap;
import net.optifine.util.EntityUtils;
import net.optifine.render.GLConst;
import net.optifine.shaders.config.RenderScale;
import java.awt.Dimension;
import org.joml.Matrix4fc;
import net.optifine.util.MathUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.optifine.CustomColors;
import java.util.regex.Matcher;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import net.minecraft.world.level.block.Block;
import java.util.IdentityHashMap;
import net.optifine.render.GlAlphaState;
import org.lwjgl.opengl.KHRDebug;
import java.nio.charset.StandardCharsets;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.MemoryStack;
import net.optifine.shaders.config.ShaderLine;
import net.optifine.shaders.config.MacroState;
import net.optifine.shaders.config.ShaderType;
import net.optifine.util.LineBuffer;
import org.lwjgl.opengl.EXTGeometryShader4;
import org.lwjgl.opengl.ARBGeometryShader4;
import net.optifine.render.GlBlendState;
import net.optifine.util.WorldUtils;
import net.optifine.shaders.uniform.Smoother;
import net.optifine.render.RenderTypes;
import org.lwjgl.opengl.GL;
import net.optifine.util.ArrayUtils;
import net.optifine.util.TimedEvent;
import net.optifine.GlErrors;
import org.lwjgl.opengl.GL43;
import java.util.Comparator;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import net.optifine.shaders.config.ShaderOptionRest;
import net.optifine.shaders.config.ShaderOptionProfile;
import org.lwjgl.opengl.GL11;
import com.mojang.blaze3d.platform.GlStateManager;
import net.optifine.shaders.config.ShaderParser;
import org.apache.commons.io.IOUtils;
import java.util.Deque;
import net.optifine.texture.PixelType;
import net.optifine.texture.PixelFormat;
import net.optifine.texture.InternalFormat;
import net.optifine.texture.TextureType;
import java.util.Collection;
import java.util.ArrayDeque;
import net.optifine.config.ConnectedParser;
import java.util.Iterator;
import net.optifine.util.StrUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.InputStream;
import java.io.IOException;
import net.optifine.shaders.config.ShaderPackParser;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.CustomBlockLayers;
import java.io.Writer;
import java.io.FileWriter;
import net.optifine.Config;
import java.io.Reader;
import java.io.FileReader;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.util.PropertiesOrdered;
import java.nio.IntBuffer;
import java.util.regex.Pattern;
import org.joml.Matrix4f;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import net.optifine.shaders.uniform.CustomUniforms;
import net.optifine.util.DynamicDimension;
import java.util.List;
import net.optifine.shaders.config.PropertyDefaultFastFancyOff;
import net.optifine.expr.IExpressionBool;
import net.optifine.shaders.config.ScreenShaderOptions;
import java.util.Map;
import net.optifine.shaders.config.ShaderProfile;
import java.util.Set;
import net.optifine.shaders.config.ShaderOption;
import java.io.File;
import net.optifine.shaders.config.PropertyDefaultTrueFalse;
import java.util.Properties;
import net.optifine.shaders.uniform.ShaderUniformM3;
import net.optifine.shaders.uniform.ShaderUniform4i;
import net.optifine.shaders.uniform.ShaderUniform2i;
import net.optifine.shaders.uniform.ShaderUniformM4;
import net.optifine.shaders.uniform.ShaderUniform3f;
import net.optifine.shaders.uniform.ShaderUniform1f;
import net.optifine.shaders.uniform.ShaderUniform1i;
import net.optifine.shaders.uniform.ShaderUniform4f;
import net.optifine.shaders.uniform.ShaderUniforms;
import org.joml.Vector4f;
import org.lwjgl.opengl.GLCapabilities;

public class Shaders
{
    static enn mc;
    static fjq entityRenderer;
    public static boolean isInitializedOnce;
    public static boolean isShaderPackInitialized;
    public static GLCapabilities capabilities;
    public static String glVersionString;
    public static String glVendorString;
    public static String glRendererString;
    public static boolean hasGlGenMipmap;
    public static int countResetDisplayLists;
    private static int renderDisplayWidth;
    private static int renderDisplayHeight;
    public static int renderWidth;
    public static int renderHeight;
    public static boolean isRenderingWorld;
    public static boolean isRenderingSky;
    public static boolean isCompositeRendered;
    public static boolean isRenderingDfb;
    public static boolean isShadowPass;
    public static boolean isSleeping;
    private static boolean isRenderingFirstPersonHand;
    private static boolean isHandRenderedMain;
    private static boolean isHandRenderedOff;
    private static boolean skipRenderHandMain;
    private static boolean skipRenderHandOff;
    public static boolean renderItemKeepDepthMask;
    public static boolean itemToRenderMainTranslucent;
    public static boolean itemToRenderOffTranslucent;
    static float[] sunPosition;
    static float[] moonPosition;
    static float[] shadowLightPosition;
    static float[] upPosition;
    static float[] shadowLightPositionVector;
    static float[] upPosModelView;
    static float[] sunPosModelView;
    static float[] moonPosModelView;
    private static float[] tempMat;
    static Vector4f clearColor;
    static float skyColorR;
    static float skyColorG;
    static float skyColorB;
    static long worldTime;
    static long lastWorldTime;
    static long diffWorldTime;
    static float celestialAngle;
    static float sunAngle;
    static float shadowAngle;
    static int moonPhase;
    static long systemTime;
    static long lastSystemTime;
    static long diffSystemTime;
    static int frameCounter;
    static float frameTime;
    static float frameTimeCounter;
    static int systemTimeInt32;
    public static ena pointOfView;
    public static boolean pointOfViewChanged;
    static float rainStrength;
    static float wetness;
    public static float wetnessHalfLife;
    public static float drynessHalfLife;
    public static float eyeBrightnessHalflife;
    static boolean usewetness;
    static int isEyeInWater;
    static int eyeBrightness;
    static float eyeBrightnessFadeX;
    static float eyeBrightnessFadeY;
    static float eyePosY;
    static float centerDepth;
    static float centerDepthSmooth;
    static float centerDepthSmoothHalflife;
    static boolean centerDepthSmoothEnabled;
    static int superSamplingLevel;
    static float nightVision;
    static float blindness;
    static boolean lightmapEnabled;
    static boolean fogEnabled;
    static boolean fogAllowed;
    static RenderStage renderStage;
    static int bossBattle;
    static float darknessFactor;
    static float darknessLightFactor;
    private static int baseAttribId;
    public static int entityAttrib;
    public static int midTexCoordAttrib;
    public static int tangentAttrib;
    public static int velocityAttrib;
    public static int midBlockAttrib;
    public static boolean useEntityAttrib;
    public static boolean useMidTexCoordAttrib;
    public static boolean useTangentAttrib;
    public static boolean useVelocityAttrib;
    public static boolean useMidBlockAttrib;
    public static boolean progUseEntityAttrib;
    public static boolean progUseMidTexCoordAttrib;
    public static boolean progUseTangentAttrib;
    public static boolean progUseVelocityAttrib;
    public static boolean progUseMidBlockAttrib;
    private static boolean progArbGeometryShader4;
    private static boolean progExtGeometryShader4;
    private static int progMaxVerticesOut;
    private static boolean hasGeometryShaders;
    public static boolean hasShadowGeometryShaders;
    public static boolean hasShadowInstancing;
    public static int atlasSizeX;
    public static int atlasSizeY;
    private static ShaderUniforms shaderUniforms;
    public static ShaderUniform4f uniform_entityColor;
    public static ShaderUniform1i uniform_entityId;
    public static ShaderUniform1i uniform_blockEntityId;
    public static ShaderUniform1i uniform_gtexture;
    public static ShaderUniform1i uniform_lightmap;
    public static ShaderUniform1i uniform_normals;
    public static ShaderUniform1i uniform_specular;
    public static ShaderUniform1i uniform_shadow;
    public static ShaderUniform1i uniform_watershadow;
    public static ShaderUniform1i uniform_shadowtex0;
    public static ShaderUniform1i uniform_shadowtex1;
    public static ShaderUniform1i uniform_depthtex0;
    public static ShaderUniform1i uniform_depthtex1;
    public static ShaderUniform1i uniform_shadowcolor;
    public static ShaderUniform1i uniform_shadowcolor0;
    public static ShaderUniform1i uniform_shadowcolor1;
    public static ShaderUniform1i uniform_noisetex;
    public static ShaderUniform1i uniform_gcolor;
    public static ShaderUniform1i uniform_gdepth;
    public static ShaderUniform1i uniform_gnormal;
    public static ShaderUniform1i uniform_composite;
    public static ShaderUniform1i uniform_gaux1;
    public static ShaderUniform1i uniform_gaux2;
    public static ShaderUniform1i uniform_gaux3;
    public static ShaderUniform1i uniform_gaux4;
    public static ShaderUniform1i uniform_colortex0;
    public static ShaderUniform1i uniform_colortex1;
    public static ShaderUniform1i uniform_colortex2;
    public static ShaderUniform1i uniform_colortex3;
    public static ShaderUniform1i uniform_colortex4;
    public static ShaderUniform1i uniform_colortex5;
    public static ShaderUniform1i uniform_colortex6;
    public static ShaderUniform1i uniform_colortex7;
    public static ShaderUniform1i uniform_gdepthtex;
    public static ShaderUniform1i uniform_depthtex2;
    public static ShaderUniform1i uniform_colortex8;
    public static ShaderUniform1i uniform_colortex9;
    public static ShaderUniform1i uniform_colortex10;
    public static ShaderUniform1i uniform_colortex11;
    public static ShaderUniform1i uniform_colortex12;
    public static ShaderUniform1i uniform_colortex13;
    public static ShaderUniform1i uniform_colortex14;
    public static ShaderUniform1i uniform_colortex15;
    public static ShaderUniform1i uniform_colorimg0;
    public static ShaderUniform1i uniform_colorimg1;
    public static ShaderUniform1i uniform_colorimg2;
    public static ShaderUniform1i uniform_colorimg3;
    public static ShaderUniform1i uniform_colorimg4;
    public static ShaderUniform1i uniform_colorimg5;
    public static ShaderUniform1i uniform_shadowcolorimg0;
    public static ShaderUniform1i uniform_shadowcolorimg1;
    public static ShaderUniform1i uniform_tex;
    public static ShaderUniform1i uniform_heldItemId;
    public static ShaderUniform1i uniform_heldBlockLightValue;
    public static ShaderUniform1i uniform_heldItemId2;
    public static ShaderUniform1i uniform_heldBlockLightValue2;
    public static ShaderUniform1i uniform_fogMode;
    public static ShaderUniform1f uniform_fogDensity;
    public static ShaderUniform1f uniform_fogStart;
    public static ShaderUniform1f uniform_fogEnd;
    public static ShaderUniform1i uniform_fogShape;
    public static ShaderUniform3f uniform_fogColor;
    public static ShaderUniform3f uniform_skyColor;
    public static ShaderUniform1i uniform_worldTime;
    public static ShaderUniform1i uniform_worldDay;
    public static ShaderUniform1i uniform_moonPhase;
    public static ShaderUniform1i uniform_frameCounter;
    public static ShaderUniform1f uniform_frameTime;
    public static ShaderUniform1f uniform_frameTimeCounter;
    public static ShaderUniform1f uniform_sunAngle;
    public static ShaderUniform1f uniform_shadowAngle;
    public static ShaderUniform1f uniform_rainStrength;
    public static ShaderUniform1f uniform_aspectRatio;
    public static ShaderUniform1f uniform_viewWidth;
    public static ShaderUniform1f uniform_viewHeight;
    public static ShaderUniform1f uniform_near;
    public static ShaderUniform1f uniform_far;
    public static ShaderUniform3f uniform_sunPosition;
    public static ShaderUniform3f uniform_moonPosition;
    public static ShaderUniform3f uniform_shadowLightPosition;
    public static ShaderUniform3f uniform_upPosition;
    public static ShaderUniform3f uniform_previousCameraPosition;
    public static ShaderUniform3f uniform_cameraPosition;
    public static ShaderUniformM4 uniform_gbufferModelView;
    public static ShaderUniformM4 uniform_gbufferModelViewInverse;
    public static ShaderUniformM4 uniform_gbufferPreviousProjection;
    public static ShaderUniformM4 uniform_gbufferProjection;
    public static ShaderUniformM4 uniform_gbufferProjectionInverse;
    public static ShaderUniformM4 uniform_gbufferPreviousModelView;
    public static ShaderUniformM4 uniform_shadowProjection;
    public static ShaderUniformM4 uniform_shadowProjectionInverse;
    public static ShaderUniformM4 uniform_shadowModelView;
    public static ShaderUniformM4 uniform_shadowModelViewInverse;
    public static ShaderUniform1f uniform_wetness;
    public static ShaderUniform1f uniform_eyeAltitude;
    public static ShaderUniform2i uniform_eyeBrightness;
    public static ShaderUniform2i uniform_eyeBrightnessSmooth;
    public static ShaderUniform2i uniform_terrainTextureSize;
    public static ShaderUniform1i uniform_terrainIconSize;
    public static ShaderUniform1i uniform_isEyeInWater;
    public static ShaderUniform1f uniform_nightVision;
    public static ShaderUniform1f uniform_blindness;
    public static ShaderUniform1f uniform_screenBrightness;
    public static ShaderUniform1i uniform_hideGUI;
    public static ShaderUniform1f uniform_centerDepthSmooth;
    public static ShaderUniform2i uniform_atlasSize;
    public static ShaderUniform4f uniform_spriteBounds;
    public static ShaderUniform4i uniform_blendFunc;
    public static ShaderUniform1i uniform_instanceId;
    public static ShaderUniform1f uniform_playerMood;
    public static ShaderUniform1i uniform_renderStage;
    public static ShaderUniform1i uniform_bossBattle;
    public static ShaderUniformM4 uniform_modelViewMatrix;
    public static ShaderUniformM4 uniform_modelViewMatrixInverse;
    public static ShaderUniformM4 uniform_projectionMatrix;
    public static ShaderUniformM4 uniform_projectionMatrixInverse;
    public static ShaderUniformM4 uniform_textureMatrix;
    public static ShaderUniformM3 uniform_normalMatrix;
    public static ShaderUniform3f uniform_chunkOffset;
    public static ShaderUniform4f uniform_colorModulator;
    public static ShaderUniform1f uniform_alphaTestRef;
    public static ShaderUniform1f uniform_darknessFactor;
    public static ShaderUniform1f uniform_darknessLightFactor;
    static double previousCameraPositionX;
    static double previousCameraPositionY;
    static double previousCameraPositionZ;
    static double cameraPositionX;
    static double cameraPositionY;
    static double cameraPositionZ;
    static int cameraOffsetX;
    static int cameraOffsetZ;
    public static boolean hasShadowMap;
    public static boolean needResizeShadow;
    static int shadowMapWidth;
    static int shadowMapHeight;
    static int spShadowMapWidth;
    static int spShadowMapHeight;
    static float shadowMapFOV;
    static float shadowMapHalfPlane;
    static boolean shadowMapIsOrtho;
    static float shadowDistanceRenderMul;
    public static boolean shouldSkipDefaultShadow;
    static boolean waterShadowEnabled;
    public static final int MaxDrawBuffers = 8;
    public static final int MaxColorBuffers = 16;
    public static final int MaxDepthBuffers = 3;
    public static final int MaxShadowColorBuffers = 2;
    public static final int MaxShadowDepthBuffers = 2;
    static int usedColorBuffers;
    static int usedDepthBuffers;
    static int usedShadowColorBuffers;
    static int usedShadowDepthBuffers;
    static int usedColorAttachs;
    static int usedDrawBuffers;
    static boolean bindImageTextures;
    static ShadersFramebuffer dfb;
    static ShadersFramebuffer sfb;
    private static int[] gbuffersFormat;
    public static boolean[] gbuffersClear;
    public static Vector4f[] gbuffersClearColor;
    private static final Vector4f CLEAR_COLOR_0;
    private static final Vector4f CLEAR_COLOR_1;
    private static int[] shadowBuffersFormat;
    public static boolean[] shadowBuffersClear;
    public static Vector4f[] shadowBuffersClearColor;
    private static Programs programs;
    public static final Program ProgramNone;
    public static final Program ProgramShadow;
    public static final Program ProgramShadowSolid;
    public static final Program ProgramShadowCutout;
    public static final Program[] ProgramsShadowcomp;
    public static final Program[] ProgramsPrepare;
    public static final Program ProgramBasic;
    public static final Program ProgramLine;
    public static final Program ProgramTextured;
    public static final Program ProgramTexturedLit;
    public static final Program ProgramSkyBasic;
    public static final Program ProgramSkyTextured;
    public static final Program ProgramClouds;
    public static final Program ProgramTerrain;
    public static final Program ProgramTerrainSolid;
    public static final Program ProgramTerrainCutoutMip;
    public static final Program ProgramTerrainCutout;
    public static final Program ProgramDamagedBlock;
    public static final Program ProgramBlock;
    public static final Program ProgramBeaconBeam;
    public static final Program ProgramItem;
    public static final Program ProgramEntities;
    public static final Program ProgramEntitiesGlowing;
    public static final Program ProgramArmorGlint;
    public static final Program ProgramSpiderEyes;
    public static final Program ProgramHand;
    public static final Program ProgramWeather;
    public static final Program ProgramDeferredPre;
    public static final Program[] ProgramsDeferred;
    public static final Program ProgramDeferred;
    public static final Program ProgramWater;
    public static final Program ProgramHandWater;
    public static final Program ProgramCompositePre;
    public static final Program[] ProgramsComposite;
    public static final Program ProgramComposite;
    public static final Program ProgramFinal;
    public static final int ProgramCount;
    public static final Program[] ProgramsAll;
    public static Program activeProgram;
    public static int activeProgramID;
    private static ProgramStack programStack;
    private static boolean hasDeferredPrograms;
    public static boolean hasShadowcompPrograms;
    public static boolean hasPreparePrograms;
    public static Properties loadedShaders;
    public static Properties shadersConfig;
    public static fug defaultTexture;
    public static boolean[] shadowHardwareFilteringEnabled;
    public static boolean[] shadowMipmapEnabled;
    public static boolean[] shadowFilterNearest;
    public static boolean[] shadowColorMipmapEnabled;
    public static boolean[] shadowColorFilterNearest;
    public static boolean configTweakBlockDamage;
    public static boolean configCloudShadow;
    public static float configHandDepthMul;
    public static float configRenderResMul;
    public static float configShadowResMul;
    public static int configTexMinFilB;
    public static int configTexMinFilN;
    public static int configTexMinFilS;
    public static int configTexMagFilB;
    public static int configTexMagFilN;
    public static int configTexMagFilS;
    public static boolean configShadowClipFrustrum;
    public static boolean configNormalMap;
    public static boolean configSpecularMap;
    public static PropertyDefaultTrueFalse configOldLighting;
    public static PropertyDefaultTrueFalse configOldHandLight;
    public static int configAntialiasingLevel;
    public static final int texMinFilRange = 3;
    public static final int texMagFilRange = 2;
    public static final String[] texMinFilDesc;
    public static final String[] texMagFilDesc;
    public static final int[] texMinFilValue;
    public static final int[] texMagFilValue;
    private static IShaderPack shaderPack;
    public static boolean shaderPackLoaded;
    public static String currentShaderName;
    public static final String SHADER_PACK_NAME_NONE = "OFF";
    public static final String SHADER_PACK_NAME_DEFAULT = "(internal)";
    public static final String SHADER_PACKS_DIR_NAME = "shaderpacks";
    public static final String OPTIONS_FILE_NAME = "optionsshaders.txt";
    public static final File shaderPacksDir;
    static File configFile;
    private static ShaderOption[] shaderPackOptions;
    private static Set<String> shaderPackOptionSliders;
    static ShaderProfile[] shaderPackProfiles;
    static Map<String, ScreenShaderOptions> shaderPackGuiScreens;
    static Map<String, IExpressionBool> shaderPackProgramConditions;
    public static final String PATH_SHADERS_PROPERTIES = "/shaders/shaders.properties";
    public static PropertyDefaultFastFancyOff shaderPackClouds;
    public static PropertyDefaultTrueFalse shaderPackOldLighting;
    public static PropertyDefaultTrueFalse shaderPackOldHandLight;
    public static PropertyDefaultTrueFalse shaderPackDynamicHandLight;
    public static PropertyDefaultTrueFalse shaderPackShadowTerrain;
    public static PropertyDefaultTrueFalse shaderPackShadowTranslucent;
    public static PropertyDefaultTrueFalse shaderPackShadowEntities;
    public static PropertyDefaultTrueFalse shaderPackShadowBlockEntities;
    public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay;
    public static PropertyDefaultTrueFalse shaderPackSun;
    public static PropertyDefaultTrueFalse shaderPackMoon;
    public static PropertyDefaultTrueFalse shaderPackVignette;
    public static PropertyDefaultTrueFalse shaderPackBackFaceSolid;
    public static PropertyDefaultTrueFalse shaderPackBackFaceCutout;
    public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped;
    public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent;
    public static PropertyDefaultTrueFalse shaderPackRainDepth;
    public static PropertyDefaultTrueFalse shaderPackBeaconBeamDepth;
    public static PropertyDefaultTrueFalse shaderPackSeparateAo;
    public static PropertyDefaultTrueFalse shaderPackFrustumCulling;
    public static PropertyDefaultTrueFalse shaderPackShadowCulling;
    public static PropertyDefaultTrueFalse shaderPackParticlesBeforeDeferred;
    private static Map<String, String> shaderPackResources;
    private static few currentWorld;
    private static List<Integer> shaderPackDimensions;
    private static ICustomTexture[] customTexturesGbuffers;
    private static ICustomTexture[] customTexturesComposite;
    private static ICustomTexture[] customTexturesDeferred;
    private static ICustomTexture[] customTexturesShadowcomp;
    private static ICustomTexture[] customTexturesPrepare;
    private static String noiseTexturePath;
    private static DynamicDimension[] colorBufferSizes;
    private static CustomUniforms customUniforms;
    public static final boolean saveFinalShaders;
    public static float blockLightLevel05;
    public static float blockLightLevel06;
    public static float blockLightLevel08;
    public static float aoLevel;
    public static float sunPathRotation;
    public static float shadowAngleInterval;
    public static int fogMode;
    public static int fogShape;
    public static float fogDensity;
    public static float fogStart;
    public static float fogEnd;
    public static float fogColorR;
    public static float fogColorG;
    public static float fogColorB;
    public static float shadowIntervalSize;
    public static int terrainIconSize;
    public static int[] terrainTextureSize;
    private static ICustomTexture noiseTexture;
    private static boolean noiseTextureEnabled;
    private static int noiseTextureResolution;
    static final int[] colorTextureImageUnit;
    static final int[] depthTextureImageUnit;
    static final int[] shadowColorTextureImageUnit;
    static final int[] shadowDepthTextureImageUnit;
    static final int[] colorImageUnit;
    static final int[] shadowColorImageUnit;
    private static final int bigBufferSize;
    private static final ByteBuffer bigBuffer;
    static final float[] faProjection;
    static final float[] faProjectionInverse;
    static final float[] faModelView;
    static final float[] faModelViewInverse;
    static final float[] faShadowProjection;
    static final float[] faShadowProjectionInverse;
    static final float[] faShadowModelView;
    static final float[] faShadowModelViewInverse;
    static final FloatBuffer projection;
    static final FloatBuffer projectionInverse;
    static final FloatBuffer modelView;
    static final FloatBuffer modelViewInverse;
    static final FloatBuffer shadowProjection;
    static final FloatBuffer shadowProjectionInverse;
    static final FloatBuffer shadowModelView;
    static final FloatBuffer shadowModelViewInverse;
    static final Matrix4f lastModelView;
    static final Matrix4f lastProjection;
    static final FloatBuffer previousProjection;
    static final FloatBuffer previousModelView;
    static final FloatBuffer tempMatrixDirectBuffer;
    static final FloatBuffer tempDirectFloatBuffer;
    static final DrawBuffers dfbDrawBuffers;
    static final DrawBuffers sfbDrawBuffers;
    static final DrawBuffers drawBuffersNone;
    static final DrawBuffers[] drawBuffersColorAtt;
    static boolean glDebugGroups;
    static boolean glDebugGroupProgram;
    public static final Matrix4f MATRIX_IDENTITY;
    static Map<cpn, Integer> mapBlockToEntityData;
    private static final String[] formatNames;
    private static final int[] formatIds;
    private static final Pattern patternLoadEntityDataMap;
    public static int[] entityData;
    public static int entityDataIndex;
    
    private Shaders() {
    }
    
    private static ByteBuffer nextByteBuffer(final int size) {
        final ByteBuffer buffer = Shaders.bigBuffer;
        final int pos = buffer.limit();
        buffer.position(pos).limit(pos + size);
        return buffer.slice();
    }
    
    public static IntBuffer nextIntBuffer(final int size) {
        final ByteBuffer buffer = Shaders.bigBuffer;
        final int pos = buffer.limit();
        buffer.position(pos).limit(pos + size * 4);
        return buffer.asIntBuffer();
    }
    
    private static FloatBuffer nextFloatBuffer(final int size) {
        final ByteBuffer buffer = Shaders.bigBuffer;
        final int pos = buffer.limit();
        buffer.position(pos).limit(pos + size * 4);
        return buffer.asFloatBuffer();
    }
    
    private static IntBuffer[] nextIntBufferArray(final int count, final int size) {
        final IntBuffer[] aib = new IntBuffer[count];
        for (int i = 0; i < count; ++i) {
            aib[i] = nextIntBuffer(size);
        }
        return aib;
    }
    
    private static DrawBuffers[] makeDrawBuffersColorSingle(final int count) {
        final DrawBuffers[] dbs = new DrawBuffers[count];
        for (int i = 0; i < dbs.length; ++i) {
            final DrawBuffers db = new DrawBuffers(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i), 16, 8);
            db.put(36064 + i);
            db.position(0);
            db.limit(1);
            dbs[i] = db;
        }
        return dbs;
    }
    
    public static void loadConfig() {
        SMCLog.info("Load shaders configuration.");
        try {
            if (!Shaders.shaderPacksDir.exists()) {
                Shaders.shaderPacksDir.mkdir();
            }
        }
        catch (Exception e) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/io/File;)Ljava/lang/String;, Shaders.shaderPacksDir));
        }
        (Shaders.shadersConfig = new PropertiesOrdered()).setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
        if (Shaders.configFile.exists()) {
            try {
                final FileReader reader = new FileReader(Shaders.configFile);
                Shaders.shadersConfig.load(reader);
                reader.close();
            }
            catch (Exception ex) {}
        }
        if (!Shaders.configFile.exists()) {
            try {
                storeConfig();
            }
            catch (Exception ex2) {}
        }
        final EnumShaderOption[] ops = EnumShaderOption.values();
        for (int i = 0; i < ops.length; ++i) {
            final EnumShaderOption op = ops[i];
            final String key = op.getPropertyKey();
            final String def = op.getValueDefault();
            final String val = Shaders.shadersConfig.getProperty(key, def);
            setEnumShaderOption(op, val);
        }
        loadShaderPack();
    }
    
    private static void setEnumShaderOption(final EnumShaderOption eso, String str) {
        if (str == null) {
            str = eso.getValueDefault();
        }
        switch (Shaders.Shaders$1.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[eso.ordinal()]) {
            case 1: {
                Shaders.configAntialiasingLevel = Config.parseInt(str, 0);
                break;
            }
            case 2: {
                Shaders.configNormalMap = Config.parseBoolean(str, true);
                break;
            }
            case 3: {
                Shaders.configSpecularMap = Config.parseBoolean(str, true);
                break;
            }
            case 4: {
                Shaders.configRenderResMul = Config.parseFloat(str, 1.0f);
                break;
            }
            case 5: {
                Shaders.configShadowResMul = Config.parseFloat(str, 1.0f);
                break;
            }
            case 6: {
                Shaders.configHandDepthMul = Config.parseFloat(str, 0.125f);
                break;
            }
            case 7: {
                Shaders.configCloudShadow = Config.parseBoolean(str, true);
                break;
            }
            case 8: {
                Shaders.configOldHandLight.setPropertyValue(str);
                break;
            }
            case 9: {
                Shaders.configOldLighting.setPropertyValue(str);
                break;
            }
            case 10: {
                Shaders.currentShaderName = str;
                break;
            }
            case 11: {
                Shaders.configTweakBlockDamage = Config.parseBoolean(str, true);
                break;
            }
            case 12: {
                Shaders.configShadowClipFrustrum = Config.parseBoolean(str, true);
                break;
            }
            case 13: {
                Shaders.configTexMinFilB = Config.parseInt(str, 0);
                break;
            }
            case 14: {
                Shaders.configTexMinFilN = Config.parseInt(str, 0);
                break;
            }
            case 15: {
                Shaders.configTexMinFilS = Config.parseInt(str, 0);
                break;
            }
            case 16: {
                Shaders.configTexMagFilB = Config.parseInt(str, 0);
                break;
            }
            case 17: {
                Shaders.configTexMagFilB = Config.parseInt(str, 0);
                break;
            }
            case 18: {
                Shaders.configTexMagFilB = Config.parseInt(str, 0);
                break;
            }
            default: {
                throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/config/EnumShaderOption;)Ljava/lang/String;, eso));
            }
        }
    }
    
    public static void storeConfig() {
        SMCLog.info("Save shaders configuration.");
        if (Shaders.shadersConfig == null) {
            Shaders.shadersConfig = new PropertiesOrdered();
        }
        final EnumShaderOption[] ops = EnumShaderOption.values();
        for (int i = 0; i < ops.length; ++i) {
            final EnumShaderOption op = ops[i];
            final String key = op.getPropertyKey();
            final String val = getEnumShaderOption(op);
            Shaders.shadersConfig.setProperty(key, val);
        }
        try {
            final FileWriter writer = new FileWriter(Shaders.configFile);
            Shaders.shadersConfig.store(writer, null);
            writer.close();
        }
        catch (Exception ex) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, ex.getClass().getName(), ex.getMessage()));
        }
    }
    
    public static String getEnumShaderOption(final EnumShaderOption eso) {
        switch (Shaders.Shaders$1.$SwitchMap$net$optifine$shaders$config$EnumShaderOption[eso.ordinal()]) {
            case 1: {
                return Integer.toString(Shaders.configAntialiasingLevel);
            }
            case 2: {
                return Boolean.toString(Shaders.configNormalMap);
            }
            case 3: {
                return Boolean.toString(Shaders.configSpecularMap);
            }
            case 4: {
                return Float.toString(Shaders.configRenderResMul);
            }
            case 5: {
                return Float.toString(Shaders.configShadowResMul);
            }
            case 6: {
                return Float.toString(Shaders.configHandDepthMul);
            }
            case 7: {
                return Boolean.toString(Shaders.configCloudShadow);
            }
            case 8: {
                return Shaders.configOldHandLight.getPropertyValue();
            }
            case 9: {
                return Shaders.configOldLighting.getPropertyValue();
            }
            case 10: {
                return Shaders.currentShaderName;
            }
            case 11: {
                return Boolean.toString(Shaders.configTweakBlockDamage);
            }
            case 12: {
                return Boolean.toString(Shaders.configShadowClipFrustrum);
            }
            case 13: {
                return Integer.toString(Shaders.configTexMinFilB);
            }
            case 14: {
                return Integer.toString(Shaders.configTexMinFilN);
            }
            case 15: {
                return Integer.toString(Shaders.configTexMinFilS);
            }
            case 16: {
                return Integer.toString(Shaders.configTexMagFilB);
            }
            case 17: {
                return Integer.toString(Shaders.configTexMagFilB);
            }
            case 18: {
                return Integer.toString(Shaders.configTexMagFilB);
            }
            default: {
                throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/shaders/config/EnumShaderOption;)Ljava/lang/String;, eso));
            }
        }
    }
    
    public static void setShaderPack(final String par1name) {
        Shaders.currentShaderName = par1name;
        Shaders.shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), par1name);
        loadShaderPack();
    }
    
    public static void loadShaderPack() {
        final boolean shaderPackLoadedPrev = Shaders.shaderPackLoaded;
        final boolean oldLightingPrev = isOldLighting();
        if (Shaders.mc.f != null) {
            Shaders.mc.f.pauseChunkUpdates();
        }
        Shaders.shaderPackLoaded = false;
        if (Shaders.shaderPack != null) {
            Shaders.shaderPack.close();
            Shaders.shaderPack = null;
            Shaders.shaderPackResources.clear();
            Shaders.shaderPackDimensions.clear();
            Shaders.shaderPackOptions = null;
            Shaders.shaderPackOptionSliders = null;
            Shaders.shaderPackProfiles = null;
            Shaders.shaderPackGuiScreens = null;
            Shaders.shaderPackProgramConditions.clear();
            Shaders.shaderPackClouds.resetValue();
            Shaders.shaderPackOldHandLight.resetValue();
            Shaders.shaderPackDynamicHandLight.resetValue();
            Shaders.shaderPackOldLighting.resetValue();
            resetCustomTextures();
            Shaders.noiseTexturePath = null;
        }
        boolean shadersBlocked = false;
        if (Config.isAntialiasing()) {
            SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Config.getAntialiasingLevel()));
            shadersBlocked = true;
        }
        if (Config.isGraphicsFabulous()) {
            SMCLog.info("Shaders can not be loaded, Fabulous Graphics is enabled.");
            shadersBlocked = true;
        }
        final String packName = Shaders.shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "(internal)");
        if (!shadersBlocked) {
            Shaders.shaderPack = getShaderPack(packName);
            Shaders.shaderPackLoaded = (Shaders.shaderPack != null);
        }
        if (Shaders.shaderPackLoaded) {
            SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, getShaderPackName()));
        }
        else {
            SMCLog.info("No shaderpack loaded.");
            Shaders.shaderPack = new ShaderPackNone();
        }
        if (Shaders.saveFinalShaders) {
            clearDirectory(new File(Shaders.shaderPacksDir, "debug"));
        }
        loadShaderPackResources();
        loadShaderPackDimensions();
        Shaders.shaderPackOptions = loadShaderPackOptions();
        loadShaderPackFixedProperties();
        loadShaderPackDynamicProperties();
        final boolean formatChanged = Shaders.shaderPackLoaded != shaderPackLoadedPrev;
        final boolean oldLightingChanged = isOldLighting() != oldLightingPrev;
        if (formatChanged || oldLightingChanged) {
            eih.updateVertexFormats();
            updateBlockLightLevel();
        }
        if (Shaders.mc.Y() != null) {
            CustomBlockLayers.update();
        }
        if (Shaders.mc.f != null) {
            Shaders.mc.f.resumeChunkUpdates();
        }
        if ((formatChanged || oldLightingChanged) && Shaders.mc.Y() != null) {
            Shaders.mc.O();
        }
    }
    
    public static IShaderPack getShaderPack(String name) {
        if (name == null) {
            return null;
        }
        name = name.trim();
        if (name.isEmpty() || name.equals("OFF")) {
            return null;
        }
        if (name.equals("(internal)")) {
            return new ShaderPackDefault();
        }
        try {
            final File packFile = new File(Shaders.shaderPacksDir, name);
            if (packFile.isDirectory()) {
                return new ShaderPackFolder(name, packFile);
            }
            if (packFile.isFile() && name.toLowerCase().endsWith(".zip")) {
                return new ShaderPackZip(name, packFile);
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static IShaderPack getShaderPack() {
        return Shaders.shaderPack;
    }
    
    private static void loadShaderPackDimensions() {
        Shaders.shaderPackDimensions.clear();
        for (int i = -128; i <= 128; ++i) {
            final String worldDir = invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i);
            if (Shaders.shaderPack.hasDirectory(worldDir)) {
                Shaders.shaderPackDimensions.add(i);
            }
        }
        if (Shaders.shaderPackDimensions.size() > 0) {
            final Integer[] ids = Shaders.shaderPackDimensions.toArray(new Integer[Shaders.shaderPackDimensions.size()]);
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.arrayToString(ids)));
        }
    }
    
    private static void loadShaderPackFixedProperties() {
        Shaders.shaderPackOldLighting.resetValue();
        Shaders.shaderPackSeparateAo.resetValue();
        if (Shaders.shaderPack == null) {
            return;
        }
        final String path = "/shaders/shaders.properties";
        try {
            InputStream in = Shaders.shaderPack.getResourceAsStream(path);
            if (in == null) {
                return;
            }
            in = MacroProcessor.process(in, path, false);
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Shaders.shaderPackOldLighting.loadFrom(props);
            Shaders.shaderPackSeparateAo.loadFrom(props);
            Shaders.shaderPackOptionSliders = ShaderPackParser.parseOptionSliders(props, Shaders.shaderPackOptions);
            Shaders.shaderPackProfiles = ShaderPackParser.parseProfiles(props, Shaders.shaderPackOptions);
            Shaders.shaderPackGuiScreens = ShaderPackParser.parseGuiScreens(props, Shaders.shaderPackProfiles, Shaders.shaderPackOptions);
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
        }
    }
    
    private static void loadShaderPackDynamicProperties() {
        Shaders.shaderPackClouds.resetValue();
        Shaders.shaderPackOldHandLight.resetValue();
        Shaders.shaderPackDynamicHandLight.resetValue();
        Shaders.shaderPackShadowTerrain.resetValue();
        Shaders.shaderPackShadowTranslucent.resetValue();
        Shaders.shaderPackShadowEntities.resetValue();
        Shaders.shaderPackShadowBlockEntities.resetValue();
        Shaders.shaderPackUnderwaterOverlay.resetValue();
        Shaders.shaderPackSun.resetValue();
        Shaders.shaderPackMoon.resetValue();
        Shaders.shaderPackVignette.resetValue();
        Shaders.shaderPackBackFaceSolid.resetValue();
        Shaders.shaderPackBackFaceCutout.resetValue();
        Shaders.shaderPackBackFaceCutoutMipped.resetValue();
        Shaders.shaderPackBackFaceTranslucent.resetValue();
        Shaders.shaderPackRainDepth.resetValue();
        Shaders.shaderPackBeaconBeamDepth.resetValue();
        Shaders.shaderPackFrustumCulling.resetValue();
        Shaders.shaderPackShadowCulling.resetValue();
        Shaders.shaderPackParticlesBeforeDeferred.resetValue();
        BlockAliases.reset();
        ItemAliases.reset();
        EntityAliases.reset();
        Shaders.customUniforms = null;
        for (int i = 0; i < Shaders.ProgramsAll.length; ++i) {
            final Program p = Shaders.ProgramsAll[i];
            p.resetProperties();
        }
        Arrays.fill(Shaders.colorBufferSizes, null);
        if (Shaders.shaderPack == null) {
            return;
        }
        BlockAliases.update(Shaders.shaderPack);
        ItemAliases.update(Shaders.shaderPack);
        EntityAliases.update(Shaders.shaderPack);
        final String path = "/shaders/shaders.properties";
        try {
            InputStream in = Shaders.shaderPack.getResourceAsStream(path);
            if (in == null) {
                return;
            }
            in = MacroProcessor.process(in, path, true);
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Shaders.shaderPackClouds.loadFrom(props);
            Shaders.shaderPackOldHandLight.loadFrom(props);
            Shaders.shaderPackDynamicHandLight.loadFrom(props);
            Shaders.shaderPackShadowTerrain.loadFrom(props);
            Shaders.shaderPackShadowTranslucent.loadFrom(props);
            Shaders.shaderPackShadowEntities.loadFrom(props);
            Shaders.shaderPackShadowBlockEntities.loadFrom(props);
            Shaders.shaderPackUnderwaterOverlay.loadFrom(props);
            Shaders.shaderPackSun.loadFrom(props);
            Shaders.shaderPackVignette.loadFrom(props);
            Shaders.shaderPackMoon.loadFrom(props);
            Shaders.shaderPackBackFaceSolid.loadFrom(props);
            Shaders.shaderPackBackFaceCutout.loadFrom(props);
            Shaders.shaderPackBackFaceCutoutMipped.loadFrom(props);
            Shaders.shaderPackBackFaceTranslucent.loadFrom(props);
            Shaders.shaderPackRainDepth.loadFrom(props);
            Shaders.shaderPackBeaconBeamDepth.loadFrom(props);
            Shaders.shaderPackFrustumCulling.loadFrom(props);
            Shaders.shaderPackShadowCulling.loadFrom(props);
            Shaders.shaderPackParticlesBeforeDeferred.loadFrom(props);
            Shaders.shaderPackProgramConditions = ShaderPackParser.parseProgramConditions(props, Shaders.shaderPackOptions);
            Shaders.customTexturesGbuffers = loadCustomTextures(props, ProgramStage.GBUFFERS);
            Shaders.customTexturesComposite = loadCustomTextures(props, ProgramStage.COMPOSITE);
            Shaders.customTexturesDeferred = loadCustomTextures(props, ProgramStage.DEFERRED);
            Shaders.customTexturesShadowcomp = loadCustomTextures(props, ProgramStage.SHADOWCOMP);
            Shaders.customTexturesPrepare = loadCustomTextures(props, ProgramStage.PREPARE);
            Shaders.noiseTexturePath = props.getProperty("texture.noise");
            if (Shaders.noiseTexturePath != null) {
                Shaders.noiseTextureEnabled = true;
            }
            Shaders.customUniforms = ShaderPackParser.parseCustomUniforms(props);
            ShaderPackParser.parseAlphaStates(props);
            ShaderPackParser.parseBlendStates(props);
            ShaderPackParser.parseRenderScales(props);
            ShaderPackParser.parseBuffersFlip(props);
            Shaders.colorBufferSizes = ShaderPackParser.parseBufferSizes(props, 16);
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
        }
    }
    
    private static ICustomTexture[] loadCustomTextures(final Properties props, final ProgramStage stage) {
        final String PREFIX_TEXTURE = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, stage.getName());
        final Set keys = props.keySet();
        final List<ICustomTexture> list = new ArrayList<ICustomTexture>();
        for (final String key : keys) {
            if (!key.startsWith(PREFIX_TEXTURE)) {
                continue;
            }
            String name = StrUtils.removePrefix(key, PREFIX_TEXTURE);
            name = StrUtils.removeSuffix(name, new String[] { ".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9" });
            final String path = props.getProperty(key).trim();
            final int index = getTextureIndex(stage, name);
            if (index < 0) {
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, key));
            }
            else {
                final ICustomTexture ct = loadCustomTexture(index, path);
                if (ct == null) {
                    continue;
                }
                SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, path));
                list.add(ct);
            }
        }
        if (list.size() <= 0) {
            return null;
        }
        final ICustomTexture[] cts = list.toArray(new ICustomTexture[list.size()]);
        return cts;
    }
    
    private static ICustomTexture loadCustomTexture(final int textureUnit, String path) {
        if (path == null) {
            return null;
        }
        path = path.trim();
        if (path.indexOf(58) >= 0) {
            return loadCustomTextureLocation(textureUnit, path);
        }
        if (path.indexOf(32) >= 0) {
            return loadCustomTextureRaw(textureUnit, path);
        }
        return loadCustomTextureShaders(textureUnit, path);
    }
    
    private static ICustomTexture loadCustomTextureLocation(final int textureUnit, final String path) {
        String pathFull = path.trim();
        int variant = 0;
        if (pathFull.startsWith("minecraft:textures/")) {
            pathFull = StrUtils.addSuffixCheck(pathFull, ".png");
            if (pathFull.endsWith("_n.png")) {
                pathFull = StrUtils.replaceSuffix(pathFull, "_n.png", ".png");
                variant = 1;
            }
            else if (pathFull.endsWith("_s.png")) {
                pathFull = StrUtils.replaceSuffix(pathFull, "_s.png", ".png");
                variant = 2;
            }
        }
        if (pathFull.startsWith("minecraft:dynamic/lightmap_")) {
            pathFull = pathFull.replace("lightmap", "light_map");
        }
        final acq loc = new acq(pathFull);
        final CustomTextureLocation ctv = new CustomTextureLocation(textureUnit, loc, variant);
        return ctv;
    }
    
    private static void reloadCustomTexturesLocation(final ICustomTexture[] cts) {
        if (cts == null) {
            return;
        }
        for (int i = 0; i < cts.length; ++i) {
            final ICustomTexture ct = cts[i];
            if (ct instanceof CustomTextureLocation) {
                final CustomTextureLocation ctl = (CustomTextureLocation)ct;
                ctl.reloadTexture();
            }
        }
    }
    
    private static ICustomTexture loadCustomTextureRaw(final int textureUnit, final String line) {
        final ConnectedParser cp = new ConnectedParser("Shaders");
        final String[] parts = Config.tokenize(line, " ");
        final Deque<String> params = new ArrayDeque<String>(Arrays.asList(parts));
        final String path = params.poll();
        final TextureType type = (TextureType)cp.parseEnum(params.poll(), TextureType.values(), "texture type");
        if (type == null) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
            return null;
        }
        final InternalFormat internalFormat = (InternalFormat)cp.parseEnum(params.poll(), InternalFormat.values(), "internal format");
        if (internalFormat == null) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
            return null;
        }
        int width = 0;
        int height = 0;
        int depth = 0;
        switch (Shaders.Shaders$1.$SwitchMap$net$optifine$texture$TextureType[type.ordinal()]) {
            case 1: {
                width = cp.parseInt(params.poll(), -1);
                break;
            }
            case 2: {
                width = cp.parseInt(params.poll(), -1);
                height = cp.parseInt(params.poll(), -1);
                break;
            }
            case 3: {
                width = cp.parseInt(params.poll(), -1);
                height = cp.parseInt(params.poll(), -1);
                depth = cp.parseInt(params.poll(), -1);
                break;
            }
            case 4: {
                width = cp.parseInt(params.poll(), -1);
                height = cp.parseInt(params.poll(), -1);
                break;
            }
            default: {
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(Lnet/optifine/texture/TextureType;)Ljava/lang/String;, type));
                return null;
            }
        }
        if (width < 0 || height < 0 || depth < 0) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
            return null;
        }
        final PixelFormat pixelFormat = (PixelFormat)cp.parseEnum(params.poll(), PixelFormat.values(), "pixel format");
        if (pixelFormat == null) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
            return null;
        }
        final PixelType pixelType = (PixelType)cp.parseEnum(params.poll(), PixelType.values(), "pixel type");
        if (pixelType == null) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
            return null;
        }
        if (!params.isEmpty()) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
            return null;
        }
        return loadCustomTextureRaw(textureUnit, line, path, type, internalFormat, width, height, depth, pixelFormat, pixelType);
    }
    
    private static ICustomTexture loadCustomTextureRaw(final int textureUnit, final String line, final String path, final TextureType type, final InternalFormat internalFormat, final int width, final int height, final int depth, final PixelFormat pixelFormat, final PixelType pixelType) {
        try {
            final String pathFull = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, StrUtils.removePrefix(path, "/"));
            final InputStream in = Shaders.shaderPack.getResourceAsStream(pathFull);
            if (in == null) {
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
                return null;
            }
            final byte[] bytes = Config.readAll(in);
            IOUtils.closeQuietly(in);
            final ByteBuffer bb = ehh.a(bytes.length);
            bb.put(bytes);
            bb.flip();
            final fwn tms = SimpleShaderTexture.loadTextureMetadataSection(pathFull, new fwn(true, true));
            final CustomTextureRaw ctr = new CustomTextureRaw(type, internalFormat, width, height, depth, pixelFormat, pixelType, bb, textureUnit, tms.a(), tms.b());
            return ctr;
        }
        catch (IOException e) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
            return null;
        }
    }
    
    private static ICustomTexture loadCustomTextureShaders(final int textureUnit, String path) {
        path = path.trim();
        if (path.indexOf(46) < 0) {
            path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path);
        }
        try {
            final String pathFull = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, StrUtils.removePrefix(path, "/"));
            final InputStream in = Shaders.shaderPack.getResourceAsStream(pathFull);
            if (in == null) {
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
                return null;
            }
            IOUtils.closeQuietly(in);
            final SimpleShaderTexture tex = new SimpleShaderTexture(pathFull);
            tex.a(Shaders.mc.Y());
            final CustomTexture ct = new CustomTexture(textureUnit, pathFull, (fug)tex);
            return ct;
        }
        catch (IOException e) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
            return null;
        }
    }
    
    private static int getTextureIndex(final ProgramStage stage, final String name) {
        if (stage == ProgramStage.GBUFFERS) {
            final int colortexIndex = ShaderParser.getIndex(name, "colortex", 4, 15);
            if (colortexIndex >= 0) {
                return Shaders.colorTextureImageUnit[colortexIndex];
            }
            if (name.equals("texture")) {
                return 0;
            }
            if (name.equals("lightmap")) {
                return 1;
            }
            if (name.equals("normals")) {
                return 2;
            }
            if (name.equals("specular")) {
                return 3;
            }
            if (name.equals("shadowtex0") || name.equals("watershadow")) {
                return 4;
            }
            if (name.equals("shadow")) {
                return Shaders.waterShadowEnabled ? 5 : 4;
            }
            if (name.equals("shadowtex1")) {
                return 5;
            }
            if (name.equals("depthtex0")) {
                return 6;
            }
            if (name.equals("gaux1")) {
                return 7;
            }
            if (name.equals("gaux2")) {
                return 8;
            }
            if (name.equals("gaux3")) {
                return 9;
            }
            if (name.equals("gaux4")) {
                return 10;
            }
            if (name.equals("depthtex1")) {
                return 12;
            }
            if (name.equals("shadowcolor0") || name.equals("shadowcolor")) {
                return 13;
            }
            if (name.equals("shadowcolor1")) {
                return 14;
            }
            if (name.equals("noisetex")) {
                return 15;
            }
        }
        if (stage.isAnyComposite()) {
            final int colortexIndex = ShaderParser.getIndex(name, "colortex", 0, 15);
            if (colortexIndex >= 0) {
                return Shaders.colorTextureImageUnit[colortexIndex];
            }
            if (name.equals("colortex0")) {
                return 0;
            }
            if (name.equals("gdepth")) {
                return 1;
            }
            if (name.equals("gnormal")) {
                return 2;
            }
            if (name.equals("composite")) {
                return 3;
            }
            if (name.equals("shadowtex0") || name.equals("watershadow")) {
                return 4;
            }
            if (name.equals("shadow")) {
                return Shaders.waterShadowEnabled ? 5 : 4;
            }
            if (name.equals("shadowtex1")) {
                return 5;
            }
            if (name.equals("depthtex0") || name.equals("gdepthtex")) {
                return 6;
            }
            if (name.equals("gaux1")) {
                return 7;
            }
            if (name.equals("gaux2")) {
                return 8;
            }
            if (name.equals("gaux3")) {
                return 9;
            }
            if (name.equals("gaux4")) {
                return 10;
            }
            if (name.equals("depthtex1")) {
                return 11;
            }
            if (name.equals("depthtex2")) {
                return 12;
            }
            if (name.equals("shadowcolor0") || name.equals("shadowcolor")) {
                return 13;
            }
            if (name.equals("shadowcolor1")) {
                return 14;
            }
            if (name.equals("noisetex")) {
                return 15;
            }
        }
        return -1;
    }
    
    private static void bindCustomTextures(final ICustomTexture[] cts) {
        if (cts == null) {
            return;
        }
        for (int i = 0; i < cts.length; ++i) {
            final ICustomTexture ct = cts[i];
            GlStateManager._activeTexture(33984 + ct.getTextureUnit());
            final int texId = ct.getTextureId();
            final int target = ct.getTarget();
            if (target == 3553) {
                GlStateManager._bindTexture(texId);
            }
            else {
                GL11.glBindTexture(target, texId);
            }
        }
        GlStateManager._activeTexture(33984);
    }
    
    private static void resetCustomTextures() {
        deleteCustomTextures(Shaders.customTexturesGbuffers);
        deleteCustomTextures(Shaders.customTexturesComposite);
        deleteCustomTextures(Shaders.customTexturesDeferred);
        deleteCustomTextures(Shaders.customTexturesShadowcomp);
        deleteCustomTextures(Shaders.customTexturesPrepare);
        Shaders.customTexturesGbuffers = null;
        Shaders.customTexturesComposite = null;
        Shaders.customTexturesDeferred = null;
        Shaders.customTexturesShadowcomp = null;
        Shaders.customTexturesPrepare = null;
    }
    
    private static void deleteCustomTextures(final ICustomTexture[] cts) {
        if (cts == null) {
            return;
        }
        for (int i = 0; i < cts.length; ++i) {
            final ICustomTexture ct = cts[i];
            ct.deleteTexture();
        }
    }
    
    public static ShaderOption[] getShaderPackOptions(final String screenName) {
        ShaderOption[] ops = Shaders.shaderPackOptions.clone();
        if (Shaders.shaderPackGuiScreens == null) {
            if (Shaders.shaderPackProfiles != null) {
                final ShaderOptionProfile optionProfile = new ShaderOptionProfile(Shaders.shaderPackProfiles, ops);
                ops = (ShaderOption[])Config.addObjectToArray(ops, optionProfile, 0);
            }
            ops = getVisibleOptions(ops);
            return ops;
        }
        final String key = (screenName != null) ? invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, screenName) : "screen";
        final ScreenShaderOptions sso = Shaders.shaderPackGuiScreens.get(key);
        if (sso == null) {
            return new ShaderOption[0];
        }
        final ShaderOption[] sos = sso.getShaderOptions();
        final List<ShaderOption> list = new ArrayList<ShaderOption>();
        for (int i = 0; i < sos.length; ++i) {
            final ShaderOption so = sos[i];
            if (so == null) {
                list.add(null);
            }
            else if (so instanceof ShaderOptionRest) {
                final ShaderOption[] restOps = getShaderOptionsRest(Shaders.shaderPackGuiScreens, ops);
                list.addAll(Arrays.asList(restOps));
            }
            else {
                list.add(so);
            }
        }
        final ShaderOption[] sosExp = list.toArray(new ShaderOption[list.size()]);
        return sosExp;
    }
    
    public static int getShaderPackColumns(final String screenName, final int def) {
        final String key = (screenName != null) ? invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, screenName) : "screen";
        if (Shaders.shaderPackGuiScreens == null) {
            return def;
        }
        final ScreenShaderOptions sso = Shaders.shaderPackGuiScreens.get(key);
        if (sso == null) {
            return def;
        }
        return sso.getColumns();
    }
    
    private static ShaderOption[] getShaderOptionsRest(final Map<String, ScreenShaderOptions> mapScreens, final ShaderOption[] ops) {
        final Set<String> setNames = new HashSet<String>();
        final Set<String> keys = mapScreens.keySet();
        for (final String key : keys) {
            final ScreenShaderOptions sso = mapScreens.get(key);
            final ShaderOption[] sos = sso.getShaderOptions();
            for (int v = 0; v < sos.length; ++v) {
                final ShaderOption so = sos[v];
                if (so != null) {
                    setNames.add(so.getName());
                }
            }
        }
        final List<ShaderOption> list = new ArrayList<ShaderOption>();
        for (int i = 0; i < ops.length; ++i) {
            final ShaderOption so2 = ops[i];
            if (so2.isVisible()) {
                final String name = so2.getName();
                if (!setNames.contains(name)) {
                    list.add(so2);
                }
            }
        }
        final ShaderOption[] sos2 = list.toArray(new ShaderOption[list.size()]);
        return sos2;
    }
    
    public static ShaderOption getShaderOption(final String name) {
        return ShaderUtils.getShaderOption(name, Shaders.shaderPackOptions);
    }
    
    public static ShaderOption[] getShaderPackOptions() {
        return Shaders.shaderPackOptions;
    }
    
    public static boolean isShaderPackOptionSlider(final String name) {
        return Shaders.shaderPackOptionSliders != null && Shaders.shaderPackOptionSliders.contains(name);
    }
    
    private static ShaderOption[] getVisibleOptions(final ShaderOption[] ops) {
        final List<ShaderOption> list = new ArrayList<ShaderOption>();
        for (int i = 0; i < ops.length; ++i) {
            final ShaderOption so = ops[i];
            if (so.isVisible()) {
                list.add(so);
            }
        }
        final ShaderOption[] sos = list.toArray(new ShaderOption[list.size()]);
        return sos;
    }
    
    public static void saveShaderPackOptions() {
        saveShaderPackOptions(Shaders.shaderPackOptions, Shaders.shaderPack);
    }
    
    private static void saveShaderPackOptions(final ShaderOption[] sos, final IShaderPack sp) {
        final Properties props = new PropertiesOrdered();
        if (Shaders.shaderPackOptions != null) {
            for (int i = 0; i < sos.length; ++i) {
                final ShaderOption so = sos[i];
                if (so.isChanged()) {
                    if (so.isEnabled()) {
                        props.setProperty(so.getName(), so.getValue());
                    }
                }
            }
        }
        try {
            saveOptionProperties(sp, props);
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.shaderPack.getName()));
            e.printStackTrace();
        }
    }
    
    private static void saveOptionProperties(final IShaderPack sp, final Properties props) throws IOException {
        final String path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, sp.getName());
        final File propFile = new File(enn.N().p, path);
        if (props.isEmpty()) {
            propFile.delete();
            return;
        }
        final FileOutputStream fos = new FileOutputStream(propFile);
        props.store(fos, null);
        fos.flush();
        fos.close();
    }
    
    private static ShaderOption[] loadShaderPackOptions() {
        try {
            final String[] programNames = Shaders.programs.getProgramNames();
            final Properties props = loadOptionProperties(Shaders.shaderPack);
            final ShaderOption[] sos = ShaderPackParser.parseShaderPackOptions(Shaders.shaderPack, programNames, Shaders.shaderPackDimensions);
            for (int i = 0; i < sos.length; ++i) {
                final ShaderOption so = sos[i];
                final String val = props.getProperty(so.getName());
                if (val != null) {
                    so.resetValue();
                    if (!so.setValue(val)) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, so.getName(), val));
                    }
                }
            }
            return sos;
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.shaderPack.getName()));
            e.printStackTrace();
            return null;
        }
    }
    
    private static Properties loadOptionProperties(final IShaderPack sp) throws IOException {
        final Properties props = new PropertiesOrdered();
        final String path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, sp.getName());
        final File propFile = new File(enn.N().p, path);
        if (!propFile.exists() || !propFile.isFile() || !propFile.canRead()) {
            return props;
        }
        final FileInputStream fis = new FileInputStream(propFile);
        props.load(fis);
        fis.close();
        return props;
    }
    
    public static ShaderOption[] getChangedOptions(final ShaderOption[] ops) {
        final List<ShaderOption> list = new ArrayList<ShaderOption>();
        for (int i = 0; i < ops.length; ++i) {
            final ShaderOption op = ops[i];
            if (op.isEnabled()) {
                if (op.isChanged()) {
                    list.add(op);
                }
            }
        }
        final ShaderOption[] cops = list.toArray(new ShaderOption[list.size()]);
        return cops;
    }
    
    public static ArrayList listOfShaders() {
        final ArrayList<String> listDir = new ArrayList<String>();
        final ArrayList<String> listZip = new ArrayList<String>();
        try {
            if (!Shaders.shaderPacksDir.exists()) {
                Shaders.shaderPacksDir.mkdir();
            }
            final File[] listOfFiles = Shaders.shaderPacksDir.listFiles();
            for (int i = 0; i < listOfFiles.length; ++i) {
                final File file = listOfFiles[i];
                final String name = file.getName();
                if (file.isDirectory()) {
                    if (!name.equals("debug")) {
                        final File subDir = new File(file, "shaders");
                        if (subDir.exists() && subDir.isDirectory()) {
                            listDir.add(name);
                        }
                    }
                }
                else if (file.isFile() && name.toLowerCase().endsWith(".zip")) {
                    listZip.add(name);
                }
            }
        }
        catch (Exception ex) {}
        Collections.sort(listDir, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(listZip, String.CASE_INSENSITIVE_ORDER);
        final ArrayList<String> list = new ArrayList<String>();
        list.add("OFF");
        list.add("(internal)");
        list.addAll(listDir);
        list.addAll(listZip);
        return list;
    }
    
    public static int checkFramebufferStatus(final String location) {
        final int status = GL43.glCheckFramebufferStatus(36160);
        if (status != 36053) {
            SMCLog.severe("FramebufferStatus 0x%04X at '%s'", status, location);
        }
        return status;
    }
    
    public static int checkGLError(final String location) {
        final int errorCode = GlStateManager._getError();
        if (errorCode != 0 && GlErrors.isEnabled(errorCode)) {
            final String errorText = Config.getGlErrorString(errorCode);
            final String shadersInfo = getErrorInfo(errorCode, location);
            final String messageLog = String.format("OpenGL error: %s (%s)%s, at: %s", errorCode, errorText, shadersInfo, location);
            SMCLog.severe(messageLog);
            if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorShaders", 10000L)) {
                final String messageChat = fvz.a("of.message.openglError", new Object[] { errorCode, errorText });
                printChat(messageChat);
            }
        }
        return errorCode;
    }
    
    private static String getErrorInfo(final int errorCode, final String location) {
        final StringBuilder sb = new StringBuilder();
        if (errorCode == 1286) {
            final int statusCode = GL43.glCheckFramebufferStatus(36160);
            final String statusText = getFramebufferStatusText(statusCode);
            final String info = invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, statusCode, statusText);
            sb.append(info);
        }
        String programName = Shaders.activeProgram.getName();
        if (programName.isEmpty()) {
            programName = "none";
        }
        sb.append(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programName));
        final Program activeProgramReal = getProgramById(Shaders.activeProgramID);
        if (activeProgramReal != Shaders.activeProgram) {
            String programRealName = activeProgramReal.getName();
            if (programRealName.isEmpty()) {
                programRealName = "none";
            }
            sb.append(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programRealName));
        }
        if (location.equals("setDrawBuffers")) {
            sb.append(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ArrayUtils.arrayToString(Shaders.activeProgram.getDrawBufSettings())));
        }
        return sb.toString();
    }
    
    private static Program getProgramById(final int programID) {
        for (int i = 0; i < Shaders.ProgramsAll.length; ++i) {
            final Program pi = Shaders.ProgramsAll[i];
            if (pi.getId() == programID) {
                return pi;
            }
        }
        return Shaders.ProgramNone;
    }
    
    private static String getFramebufferStatusText(final int fbStatusCode) {
        switch (fbStatusCode) {
            case 36053: {
                return "Complete";
            }
            case 33305: {
                return "Undefined";
            }
            case 36054: {
                return "Incomplete attachment";
            }
            case 36055: {
                return "Incomplete missing attachment";
            }
            case 36059: {
                return "Incomplete draw buffer";
            }
            case 36060: {
                return "Incomplete read buffer";
            }
            case 36061: {
                return "Unsupported";
            }
            case 36182: {
                return "Incomplete multisample";
            }
            case 36264: {
                return "Incomplete layer targets";
            }
            default: {
                return "Unknown";
            }
        }
    }
    
    private static void printChat(final String str) {
        Shaders.mc.l.d().a((sw)sw.b(str));
    }
    
    public static void printChatAndLogError(final String str) {
        SMCLog.severe(str);
        Shaders.mc.l.d().a((sw)sw.b(str));
    }
    
    public static void printIntBuffer(final String title, final IntBuffer buf) {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit()).append(" cap ").append(buf.capacity()).append(" :");
        for (int lim = buf.limit(), i = 0; i < lim; ++i) {
            sb.append(" ").append(buf.get(i));
        }
        sb.append("]");
        SMCLog.info(sb.toString());
    }
    
    public static void startup(enn mc) {
        checkShadersModInstalled();
        Shaders.mc = mc;
        mc = enn.N();
        Shaders.capabilities = GL.getCapabilities();
        Shaders.glVersionString = GL11.glGetString(7938);
        Shaders.glVendorString = GL11.glGetString(7936);
        Shaders.glRendererString = GL11.glGetString(7937);
        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.glVersionString));
        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.glVendorString));
        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.glRendererString));
        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Shaders.capabilities.OpenGL20 ? " 2.0 " : " - ", Shaders.capabilities.OpenGL21 ? " 2.1 " : " - ", Shaders.capabilities.OpenGL30 ? " 3.0 " : " - ", Shaders.capabilities.OpenGL32 ? " 3.2 " : " - ", Shaders.capabilities.OpenGL40 ? " 4.0 " : " - "));
        SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, GL43.glGetInteger(34852)));
        SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, GL43.glGetInteger(36063)));
        SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, GL43.glGetInteger(34930)));
        Shaders.hasGlGenMipmap = Shaders.capabilities.OpenGL30;
        Shaders.glDebugGroups = (Boolean.getBoolean("gl.debug.groups") && Shaders.capabilities.GL_KHR_debug);
        if (Shaders.glDebugGroups) {
            SMCLog.info("glDebugGroups: true");
        }
        loadConfig();
    }
    
    public static void updateBlockLightLevel() {
        if (isOldLighting()) {
            Shaders.blockLightLevel05 = 0.5f;
            Shaders.blockLightLevel06 = 0.6f;
            Shaders.blockLightLevel08 = 0.8f;
        }
        else {
            Shaders.blockLightLevel05 = 1.0f;
            Shaders.blockLightLevel06 = 1.0f;
            Shaders.blockLightLevel08 = 1.0f;
        }
    }
    
    public static boolean isOldHandLight() {
        if (!Shaders.configOldHandLight.isDefault()) {
            return Shaders.configOldHandLight.isTrue();
        }
        return Shaders.shaderPackOldHandLight.isDefault() || Shaders.shaderPackOldHandLight.isTrue();
    }
    
    public static boolean isDynamicHandLight() {
        return Shaders.shaderPackDynamicHandLight.isDefault() || Shaders.shaderPackDynamicHandLight.isTrue();
    }
    
    public static boolean isOldLighting() {
        if (!Shaders.configOldLighting.isDefault()) {
            return Shaders.configOldLighting.isTrue();
        }
        return Shaders.shaderPackOldLighting.isDefault() || Shaders.shaderPackOldLighting.isTrue();
    }
    
    public static boolean isRenderShadowTerrain() {
        return !Shaders.shaderPackShadowTerrain.isFalse();
    }
    
    public static boolean isRenderShadowTranslucent() {
        return !Shaders.shaderPackShadowTranslucent.isFalse();
    }
    
    public static boolean isRenderShadowEntities() {
        return !Shaders.shaderPackShadowEntities.isFalse();
    }
    
    public static boolean isRenderShadowBlockEntities() {
        return !Shaders.shaderPackShadowBlockEntities.isFalse();
    }
    
    public static boolean isUnderwaterOverlay() {
        return !Shaders.shaderPackUnderwaterOverlay.isFalse();
    }
    
    public static boolean isSun() {
        return !Shaders.shaderPackSun.isFalse();
    }
    
    public static boolean isMoon() {
        return !Shaders.shaderPackMoon.isFalse();
    }
    
    public static boolean isVignette() {
        return !Shaders.shaderPackVignette.isFalse();
    }
    
    public static boolean isRenderBackFace(final fkf blockLayerIn) {
        if (blockLayerIn == RenderTypes.SOLID) {
            return Shaders.shaderPackBackFaceSolid.isTrue();
        }
        if (blockLayerIn == RenderTypes.CUTOUT) {
            return Shaders.shaderPackBackFaceCutout.isTrue();
        }
        if (blockLayerIn == RenderTypes.CUTOUT_MIPPED) {
            return Shaders.shaderPackBackFaceCutoutMipped.isTrue();
        }
        return blockLayerIn == RenderTypes.TRANSLUCENT && Shaders.shaderPackBackFaceTranslucent.isTrue();
    }
    
    public static boolean isRainDepth() {
        return Shaders.shaderPackRainDepth.isTrue();
    }
    
    public static boolean isBeaconBeamDepth() {
        return Shaders.shaderPackBeaconBeamDepth.isTrue();
    }
    
    public static boolean isSeparateAo() {
        return Shaders.shaderPackSeparateAo.isTrue();
    }
    
    public static boolean isFrustumCulling() {
        return !Shaders.shaderPackFrustumCulling.isFalse();
    }
    
    public static boolean isShadowCulling() {
        if (!Shaders.shaderPackShadowCulling.isDefault()) {
            return Shaders.shaderPackShadowCulling.isTrue();
        }
        return !Shaders.hasShadowGeometryShaders && !Shaders.hasShadowInstancing;
    }
    
    public static boolean isParticlesBeforeDeferred() {
        return Shaders.shaderPackParticlesBeforeDeferred.isTrue();
    }
    
    public static void init() {
        boolean firstInit;
        if (!Shaders.isInitializedOnce) {
            Shaders.isInitializedOnce = true;
            firstInit = true;
        }
        else {
            firstInit = false;
        }
        if (!Shaders.isShaderPackInitialized) {
            checkGLError("Shaders.init pre");
            if (getShaderPackName() != null) {}
            Shaders.dfbDrawBuffers.position(0).limit(8);
            Shaders.sfbDrawBuffers.position(0).limit(8);
            Shaders.usedColorBuffers = 4;
            Shaders.usedDepthBuffers = 1;
            Shaders.usedShadowColorBuffers = 0;
            Shaders.usedShadowDepthBuffers = 0;
            Shaders.usedColorAttachs = 1;
            Shaders.usedDrawBuffers = 1;
            Shaders.bindImageTextures = false;
            Arrays.fill(Shaders.gbuffersFormat, 6408);
            Arrays.fill(Shaders.gbuffersClear, true);
            Arrays.fill(Shaders.gbuffersClearColor, null);
            Arrays.fill(Shaders.shadowBuffersFormat, 6408);
            Arrays.fill(Shaders.shadowBuffersClear, true);
            Arrays.fill(Shaders.shadowBuffersClearColor, null);
            Arrays.fill(Shaders.shadowHardwareFilteringEnabled, false);
            Arrays.fill(Shaders.shadowMipmapEnabled, false);
            Arrays.fill(Shaders.shadowFilterNearest, false);
            Arrays.fill(Shaders.shadowColorMipmapEnabled, false);
            Arrays.fill(Shaders.shadowColorFilterNearest, false);
            Shaders.centerDepthSmoothEnabled = false;
            Shaders.noiseTextureEnabled = false;
            Shaders.sunPathRotation = 0.0f;
            Shaders.shadowIntervalSize = 2.0f;
            Shaders.shadowMapWidth = 1024;
            Shaders.shadowMapHeight = 1024;
            Shaders.spShadowMapWidth = 1024;
            Shaders.spShadowMapHeight = 1024;
            Shaders.shadowMapFOV = 90.0f;
            Shaders.shadowMapHalfPlane = 160.0f;
            Shaders.shadowMapIsOrtho = true;
            Shaders.shadowDistanceRenderMul = -1.0f;
            Shaders.aoLevel = -1.0f;
            Shaders.useEntityAttrib = false;
            Shaders.useMidTexCoordAttrib = false;
            Shaders.useTangentAttrib = false;
            Shaders.useVelocityAttrib = false;
            Shaders.waterShadowEnabled = false;
            Shaders.hasGeometryShaders = false;
            Shaders.hasShadowGeometryShaders = false;
            Shaders.hasShadowInstancing = false;
            updateBlockLightLevel();
            Smoother.resetValues();
            Shaders.shaderUniforms.reset();
            if (Shaders.customUniforms != null) {
                Shaders.customUniforms.reset();
            }
            final ShaderProfile activeProfile = ShaderUtils.detectProfile(Shaders.shaderPackProfiles, Shaders.shaderPackOptions, false);
            String worldPrefix = "";
            if (Shaders.currentWorld != null) {
                final int dimId = WorldUtils.getDimensionId(Shaders.currentWorld.ac());
                if (Shaders.shaderPackDimensions.contains(dimId)) {
                    worldPrefix = invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, dimId);
                }
            }
            loadShaderPackDynamicProperties();
            final int vaoPrev = GL43.glGetInteger(34229);
            final int vaoTemp = GlStateManager._glGenVertexArrays();
            GlStateManager._glBindVertexArray(vaoTemp);
            for (int i = 0; i < Shaders.ProgramsAll.length; ++i) {
                final Program p = Shaders.ProgramsAll[i];
                p.resetId();
                p.resetConfiguration();
                if (p.getProgramStage() != ProgramStage.NONE) {
                    String programName = p.getName();
                    String programPath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, worldPrefix, programName);
                    boolean enabled = true;
                    if (Shaders.shaderPackProgramConditions.containsKey(programPath)) {
                        enabled = (enabled && Shaders.shaderPackProgramConditions.get(programPath).eval());
                    }
                    if (activeProfile != null) {
                        enabled = (enabled && !activeProfile.isProgramDisabled(programPath));
                    }
                    if (!enabled) {
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programPath));
                        programName = "<disabled>";
                        programPath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, worldPrefix, programName);
                    }
                    final String programFullPath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programPath);
                    final String programFullPathVertex = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programFullPath);
                    final String programFullPathGeometry = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programFullPath);
                    final String programFullPathFragment = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programFullPath);
                    final ComputeProgram[] cps = setupComputePrograms(p, "/shaders/", programPath, ".csh");
                    p.setComputePrograms(cps);
                    setupProgram(p, programFullPathVertex, programFullPathGeometry, programFullPathFragment);
                    final int pr = p.getId();
                    if (pr > 0) {
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programPath));
                    }
                    initDrawBuffers(p);
                    initBlendStatesIndexed(p);
                    updateToggleBuffers(p);
                    updateProgramSize(p);
                }
            }
            GlStateManager._glBindVertexArray(vaoPrev);
            GlStateManager._glDeleteVertexArrays(vaoTemp);
            Shaders.hasDeferredPrograms = ProgramUtils.hasActive(Shaders.ProgramsDeferred);
            Shaders.hasShadowcompPrograms = ProgramUtils.hasActive(Shaders.ProgramsShadowcomp);
            Shaders.hasPreparePrograms = ProgramUtils.hasActive(Shaders.ProgramsPrepare);
            Shaders.usedColorAttachs = Shaders.usedColorBuffers;
            if (Shaders.usedShadowDepthBuffers > 0 || Shaders.usedShadowColorBuffers > 0) {
                Shaders.hasShadowMap = true;
                Shaders.usedShadowDepthBuffers = Math.max(Shaders.usedShadowDepthBuffers, 1);
            }
            Shaders.shouldSkipDefaultShadow = Shaders.hasShadowMap;
            SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Shaders.usedColorBuffers));
            SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Shaders.usedDepthBuffers));
            SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Shaders.usedShadowColorBuffers));
            SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Shaders.usedShadowDepthBuffers));
            SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Shaders.usedColorAttachs));
            SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Shaders.usedDrawBuffers));
            SMCLog.info(invokedynamic(makeConcatWithConstants:(Z)Ljava/lang/String;, Shaders.bindImageTextures));
            final int maxDrawBuffers = GL43.glGetInteger(34852);
            if (Shaders.usedDrawBuffers > maxDrawBuffers) {
                printChatAndLogError(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, Shaders.usedDrawBuffers, maxDrawBuffers));
                Shaders.usedDrawBuffers = maxDrawBuffers;
            }
            Shaders.dfbDrawBuffers.position(0).limit(Shaders.usedDrawBuffers);
            for (int j = 0; j < Shaders.usedDrawBuffers; ++j) {
                Shaders.dfbDrawBuffers.put(j, 36064 + j);
            }
            Shaders.sfbDrawBuffers.position(0).limit(Shaders.usedShadowColorBuffers);
            for (int j = 0; j < Shaders.usedShadowColorBuffers; ++j) {
                Shaders.sfbDrawBuffers.put(j, 36064 + j);
            }
            for (int j = 0; j < Shaders.ProgramsAll.length; ++j) {
                Program pn;
                Program pi;
                for (pi = (pn = Shaders.ProgramsAll[j]); pn.getId() == 0 && pn.getProgramBackup() != pn; pn = pn.getProgramBackup()) {}
                if (pn != pi && pi != Shaders.ProgramShadow) {
                    pi.copyFrom(pn);
                }
            }
            resize();
            resizeShadow();
            if (Shaders.noiseTextureEnabled) {
                setupNoiseTexture();
            }
            if (Shaders.defaultTexture == null) {
                Shaders.defaultTexture = ShadersTex.createDefaultTexture();
            }
            final eij matrixStack = new eij();
            matrixStack.a(a.d.rotationDegrees(-90.0f));
            preCelestialRotate(matrixStack);
            postCelestialRotate(matrixStack);
            Shaders.isShaderPackInitialized = true;
            loadEntityDataMap();
            resetDisplayLists();
            if (!firstInit) {}
            checkGLError("Shaders.init");
        }
    }
    
    private static void initDrawBuffers(final Program p) {
        final int maxDrawBuffers = GL43.glGetInteger(34852);
        Arrays.fill(p.getToggleColorTextures(), false);
        if (p == Shaders.ProgramFinal) {
            p.setDrawBuffers(null);
            return;
        }
        if (p.getId() == 0) {
            if (p == Shaders.ProgramShadow) {
                p.setDrawBuffers(Shaders.drawBuffersNone);
            }
            else {
                p.setDrawBuffers(Shaders.drawBuffersColorAtt[0]);
            }
            return;
        }
        final String[] drawBufSettings = p.getDrawBufSettings();
        if (drawBufSettings == null) {
            if (p != Shaders.ProgramShadow && p != Shaders.ProgramShadowSolid && p != Shaders.ProgramShadowCutout) {
                p.setDrawBuffers(Shaders.dfbDrawBuffers);
                Shaders.usedDrawBuffers = Math.min(Shaders.usedColorBuffers, maxDrawBuffers);
                Arrays.fill(p.getToggleColorTextures(), 0, Shaders.usedColorBuffers, true);
            }
            else {
                p.setDrawBuffers(Shaders.sfbDrawBuffers);
            }
            return;
        }
        final DrawBuffers drawBuffers = p.getDrawBuffersCustom();
        int numDB = drawBufSettings.length;
        Shaders.usedDrawBuffers = Math.max(Shaders.usedDrawBuffers, numDB);
        numDB = Math.min(numDB, maxDrawBuffers);
        p.setDrawBuffers(drawBuffers);
        drawBuffers.limit(numDB);
        for (int i = 0; i < numDB; ++i) {
            final int drawBuffer = getDrawBuffer(p, drawBufSettings[i]);
            drawBuffers.put(i, drawBuffer);
        }
        final String infoBuffers = drawBuffers.getInfo(false);
        final String infoGlBuffers = drawBuffers.getInfo(true);
        if (!Config.equals(infoBuffers, infoGlBuffers)) {
            SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, infoBuffers, infoGlBuffers));
        }
    }
    
    private static void initBlendStatesIndexed(final Program p) {
        final GlBlendState[] blendStatesColorIndexed = p.getBlendStatesColorIndexed();
        if (blendStatesColorIndexed == null) {
            return;
        }
        for (int i = 0; i < blendStatesColorIndexed.length; ++i) {
            final GlBlendState blendState = blendStatesColorIndexed[i];
            if (blendState != null) {
                final String bufferName = Integer.toHexString(i).toUpperCase();
                final int colAtt = 36064 + i;
                final int drawBufferIndex = p.getDrawBuffers().indexOf(colAtt);
                if (drawBufferIndex < 0) {
                    SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, bufferName));
                }
                else {
                    p.setBlendStateIndexed(drawBufferIndex, blendState);
                    SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, bufferName));
                }
            }
        }
    }
    
    private static int getDrawBuffer(final Program p, final String str) {
        int drawBuffer = 0;
        final int ca = Config.parseInt(str, -1);
        if (p == Shaders.ProgramShadow) {
            if (ca >= 0 && ca < 2) {
                drawBuffer = 36064 + ca;
                Shaders.usedShadowColorBuffers = Math.max(Shaders.usedShadowColorBuffers, ca + 1);
            }
            return drawBuffer;
        }
        if (ca >= 0 && ca < 16) {
            p.getToggleColorTextures()[ca] = true;
            drawBuffer = 36064 + ca;
            Shaders.usedColorAttachs = Math.max(Shaders.usedColorAttachs, ca + 1);
            Shaders.usedColorBuffers = Math.max(Shaders.usedColorBuffers, ca + 1);
        }
        return drawBuffer;
    }
    
    private static void updateToggleBuffers(final Program p) {
        final boolean[] toggleBuffers = p.getToggleColorTextures();
        final Boolean[] flipBuffers = p.getBuffersFlip();
        for (int i = 0; i < flipBuffers.length; ++i) {
            final Boolean flip = flipBuffers[i];
            if (flip != null) {
                toggleBuffers[i] = flip;
            }
        }
    }
    
    private static void updateProgramSize(final Program p) {
        if (!p.getProgramStage().isMainComposite()) {
            return;
        }
        DynamicDimension drawSize = null;
        int countFixed = 0;
        int countMatching = 0;
        final DrawBuffers db = p.getDrawBuffers();
        if (db == null) {
            return;
        }
        for (int i = 0; i < db.limit(); ++i) {
            final int att = db.get(i);
            final int ix = att - 36064;
            if (ix >= 0) {
                if (ix < Shaders.colorBufferSizes.length) {
                    final DynamicDimension dim = Shaders.colorBufferSizes[ix];
                    if (dim != null) {
                        ++countFixed;
                        if (drawSize == null) {
                            drawSize = dim;
                        }
                        if (dim.equals(drawSize)) {
                            ++countMatching;
                        }
                    }
                }
            }
        }
        if (countFixed == 0) {
            return;
        }
        if (countMatching != db.limit()) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, p.getName()));
            return;
        }
        p.setDrawSize(drawSize);
    }
    
    public static void resetDisplayLists() {
        SMCLog.info("Reset model renderers");
        ++Shaders.countResetDisplayLists;
        SMCLog.info("Reset world renderers");
        Shaders.mc.f.f();
    }
    
    private static void setupProgram(final Program program, final String vShaderPath, final String gShaderPath, final String fShaderPath) {
        checkGLError("pre setupProgram");
        Shaders.progUseEntityAttrib = false;
        Shaders.progUseMidTexCoordAttrib = false;
        Shaders.progUseTangentAttrib = false;
        Shaders.progUseVelocityAttrib = false;
        Shaders.progUseMidBlockAttrib = false;
        final int vShader = createVertShader(program, vShaderPath);
        final int gShader = createGeomShader(program, gShaderPath);
        final int fShader = createFragShader(program, fShaderPath);
        if (vShader == 0 && gShader == 0 && fShader == 0) {
            return;
        }
        Config.sleep(1L);
        checkGLError("create");
        int programid = GL43.glCreateProgram();
        checkGLError("create");
        if (vShader != 0) {
            GL43.glAttachShader(programid, vShader);
            checkGLError("attach");
            if (program.getProgramStage() == ProgramStage.SHADOW && program.getCountInstances() > 1) {
                Shaders.hasShadowInstancing = true;
            }
        }
        if (gShader != 0) {
            GL43.glAttachShader(programid, gShader);
            checkGLError("attach");
            if (Shaders.progArbGeometryShader4) {
                ARBGeometryShader4.glProgramParameteriARB(programid, 36315, 4);
                ARBGeometryShader4.glProgramParameteriARB(programid, 36316, 5);
                ARBGeometryShader4.glProgramParameteriARB(programid, 36314, Shaders.progMaxVerticesOut);
                checkGLError("arbGeometryShader4");
            }
            if (Shaders.progExtGeometryShader4) {
                EXTGeometryShader4.glProgramParameteriEXT(programid, 36315, 4);
                EXTGeometryShader4.glProgramParameteriEXT(programid, 36316, 5);
                EXTGeometryShader4.glProgramParameteriEXT(programid, 36314, Shaders.progMaxVerticesOut);
                checkGLError("extGeometryShader4");
            }
            Shaders.hasGeometryShaders = true;
            if (program.getProgramStage() == ProgramStage.SHADOW) {
                Shaders.hasShadowGeometryShaders = true;
            }
        }
        if (fShader != 0) {
            GL43.glAttachShader(programid, fShader);
            checkGLError("attach");
            for (int i = 0; i < 8; ++i) {
                GL43.glBindFragDataLocation(programid, i, invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i));
            }
            checkGLError("bindDataLocation");
        }
        final eio vertexFormat = eih.ENTITY_VANILLA;
        final List<String> attributeNames = (List<String>)vertexFormat.d();
        for (int j = 0; j < attributeNames.size(); ++j) {
            final String name = attributeNames.get(j);
            final eip element = (eip)vertexFormat.getElementMapping().get((Object)name);
            final int attributeIndex = element.getAttributeIndex(j);
            if (attributeIndex >= 0) {
                final String nameOf = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name);
                eia.a(programid, attributeIndex, (CharSequence)nameOf);
            }
        }
        if (Shaders.progUseEntityAttrib) {
            GL43.glBindAttribLocation(programid, Shaders.entityAttrib, (CharSequence)"mc_Entity");
            checkGLError("mc_Entity");
        }
        if (Shaders.progUseMidTexCoordAttrib) {
            GL43.glBindAttribLocation(programid, Shaders.midTexCoordAttrib, (CharSequence)"mc_midTexCoord");
            checkGLError("mc_midTexCoord");
        }
        if (Shaders.progUseTangentAttrib) {
            GL43.glBindAttribLocation(programid, Shaders.tangentAttrib, (CharSequence)"at_tangent");
            checkGLError("at_tangent");
        }
        if (Shaders.progUseVelocityAttrib) {
            GL43.glBindAttribLocation(programid, Shaders.velocityAttrib, (CharSequence)"at_velocity");
            checkGLError("at_velocity");
        }
        if (Shaders.progUseMidBlockAttrib) {
            GL43.glBindAttribLocation(programid, Shaders.midBlockAttrib, (CharSequence)"at_midBlock");
            checkGLError("at_midBlock");
        }
        GL43.glLinkProgram(programid);
        if (GL43.glGetProgrami(programid, 35714) != 1) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, programid, program.getName()));
        }
        printProgramLogInfo(programid, program.getName());
        if (vShader != 0) {
            GL43.glDetachShader(programid, vShader);
            GL43.glDeleteShader(vShader);
        }
        if (gShader != 0) {
            GL43.glDetachShader(programid, gShader);
            GL43.glDeleteShader(gShader);
        }
        if (fShader != 0) {
            GL43.glDetachShader(programid, fShader);
            GL43.glDeleteShader(fShader);
        }
        program.setId(programid);
        program.setRef(programid);
        useProgram(program);
        GL43.glValidateProgram(programid);
        useProgram(Shaders.ProgramNone);
        printProgramLogInfo(programid, program.getName());
        final int valid = GL43.glGetProgrami(programid, 35715);
        if (valid != 1) {
            final String Q = "\"";
            printChatAndLogError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Q, program.getName(), Q));
            GL43.glDeleteProgram(programid);
            programid = 0;
            program.resetId();
        }
    }
    
    private static eio getVertexFormat(final Program program) {
        if (isTerrain(program)) {
            return eih.j;
        }
        if (program == Shaders.ProgramSkyTextured) {
            return eih.q;
        }
        if (program == Shaders.ProgramClouds) {
            return eih.v;
        }
        return eih.k;
    }
    
    public static boolean isTerrain(final Program program) {
        return program.getName().contains("terrain") || program == Shaders.ProgramWater || program.getProgramStage() == ProgramStage.SHADOW;
    }
    
    private static ComputeProgram[] setupComputePrograms(final Program program, final String prefixShaders, final String programPath, final String shaderExt) {
        if (program.getProgramStage() == ProgramStage.GBUFFERS) {
            return new ComputeProgram[0];
        }
        final List<ComputeProgram> list = new ArrayList<ComputeProgram>();
        for (int count = 27, i = 0; i < count; ++i) {
            final String suffix = (i > 0) ? invokedynamic(makeConcatWithConstants:(C)Ljava/lang/String;, (char)(97 + i - 1)) : "";
            final String computePath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, programPath, suffix);
            final String computeShaderFullPath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, prefixShaders, computePath, shaderExt);
            final ComputeProgram cp = new ComputeProgram(program.getName(), program.getProgramStage());
            setupComputeProgram(cp, computeShaderFullPath);
            if (cp.getId() > 0) {
                list.add(cp);
                SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, computePath));
            }
        }
        final ComputeProgram[] cps = list.toArray(new ComputeProgram[list.size()]);
        return cps;
    }
    
    private static void setupComputeProgram(final ComputeProgram program, final String cShaderPath) {
        checkGLError("pre setupProgram");
        final int cShader = createCompShader(program, cShaderPath);
        checkGLError("create");
        if (cShader != 0) {
            int programid = GL43.glCreateProgram();
            checkGLError("create");
            if (cShader != 0) {
                GL43.glAttachShader(programid, cShader);
                checkGLError("attach");
            }
            GL43.glLinkProgram(programid);
            if (GL43.glGetProgrami(programid, 35714) != 1) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, programid, program.getName()));
            }
            printProgramLogInfo(programid, program.getName());
            if (cShader != 0) {
                GL43.glDetachShader(programid, cShader);
                GL43.glDeleteShader(cShader);
            }
            program.setId(programid);
            program.setRef(programid);
            useComputeProgram(program);
            GL43.glValidateProgram(programid);
            GlStateManager._glUseProgram(0);
            printProgramLogInfo(programid, program.getName());
            final int valid = GL43.glGetProgrami(programid, 35715);
            if (valid != 1) {
                final String Q = "\"";
                printChatAndLogError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Q, program.getName(), Q));
                GL43.glDeleteProgram(programid);
                programid = 0;
                program.resetId();
            }
        }
    }
    
    private static int createCompShader(final ComputeProgram program, final String filename) {
        final InputStream is = Shaders.shaderPack.getResourceAsStream(filename);
        if (is == null) {
            return 0;
        }
        final int compShader = GL43.glCreateShader(37305);
        if (compShader == 0) {
            return 0;
        }
        final ShaderOption[] activeOptions = getChangedOptions(Shaders.shaderPackOptions);
        final List<String> listFiles = new ArrayList<String>();
        LineBuffer lines = new LineBuffer();
        if (is != null) {
            try {
                lines = ShaderPackParser.loadShader(null, ShaderType.COMPUTE, is, filename, Shaders.shaderPack, listFiles, activeOptions);
                final MacroState macroState = new MacroState();
                for (final String line : lines) {
                    if (!macroState.processLine(line)) {
                        continue;
                    }
                    final ShaderLine sl = ShaderParser.parseLine(line, ShaderType.COMPUTE);
                    if (sl == null) {
                        continue;
                    }
                    if (sl.isUniform()) {
                        final String uniform = sl.getName();
                        int index;
                        if ((index = ShaderParser.getShadowDepthIndex(uniform)) >= 0) {
                            Shaders.usedShadowDepthBuffers = Math.max(Shaders.usedShadowDepthBuffers, index + 1);
                        }
                        else if ((index = ShaderParser.getShadowColorIndex(uniform)) >= 0) {
                            Shaders.usedShadowColorBuffers = Math.max(Shaders.usedShadowColorBuffers, index + 1);
                        }
                        else if ((index = ShaderParser.getShadowColorImageIndex(uniform)) >= 0) {
                            Shaders.usedShadowColorBuffers = Math.max(Shaders.usedShadowColorBuffers, index + 1);
                            Shaders.bindImageTextures = true;
                        }
                        else if ((index = ShaderParser.getDepthIndex(uniform)) >= 0) {
                            Shaders.usedDepthBuffers = Math.max(Shaders.usedDepthBuffers, index + 1);
                        }
                        else if ((index = ShaderParser.getColorIndex(uniform)) >= 0) {
                            Shaders.usedColorBuffers = Math.max(Shaders.usedColorBuffers, index + 1);
                        }
                        else {
                            if ((index = ShaderParser.getColorImageIndex(uniform)) < 0) {
                                continue;
                            }
                            Shaders.usedColorBuffers = Math.max(Shaders.usedColorBuffers, index + 1);
                            Shaders.bindImageTextures = true;
                        }
                    }
                    else if (sl.isLayout("in")) {
                        final hz localSize = ShaderParser.parseLocalSize(sl.getValue());
                        if (localSize != null) {
                            program.setLocalSize(localSize);
                        }
                        else {
                            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
                        }
                    }
                    else if (sl.isConstIVec3("workGroups")) {
                        final hz workGroups = sl.getValueIVec3();
                        if (workGroups != null) {
                            program.setWorkGroups(workGroups);
                        }
                        else {
                            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
                        }
                    }
                    else if (sl.isConstVec2("workGroupsRender")) {
                        final eeh workGroupsRender = sl.getValueVec2();
                        if (workGroupsRender != null) {
                            program.setWorkGroupsRender(workGroupsRender);
                        }
                        else {
                            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
                        }
                    }
                    else {
                        if (!sl.isConstBoolSuffix("MipmapEnabled", true)) {
                            continue;
                        }
                        final String name = StrUtils.removeSuffix(sl.getName(), "MipmapEnabled");
                        final int bufferindex = getBufferIndex(name);
                        if (bufferindex < 0) {
                            continue;
                        }
                        int compositeMipmapSetting = program.getCompositeMipmapSetting();
                        compositeMipmapSetting |= 1 << bufferindex;
                        program.setCompositeMipmapSetting(compositeMipmapSetting);
                        SMCLog.info("%s mipmap enabled", name);
                    }
                }
            }
            catch (Exception e) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
                e.printStackTrace();
                GL43.glDeleteShader(compShader);
                return 0;
            }
        }
        final String compCode = lines.toString();
        if (Shaders.saveFinalShaders) {
            saveShader(filename, compCode);
        }
        if (program.getLocalSize() == null) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
            GL43.glDeleteShader(compShader);
            return 0;
        }
        shaderSource(compShader, compCode);
        GL43.glCompileShader(compShader);
        if (GL43.glGetShaderi(compShader, 35713) != 1) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
        }
        printShaderLogInfo(compShader, filename, listFiles);
        return compShader;
    }
    
    private static int createVertShader(final Program program, final String filename) {
        InputStream is = Shaders.shaderPack.getResourceAsStream(filename);
        if (is == null) {
            is = DefaultShaders.getResourceAsStream(filename);
        }
        if (is == null) {
            return 0;
        }
        final int vertShader = GL43.glCreateShader(35633);
        if (vertShader == 0) {
            return 0;
        }
        final ShaderOption[] activeOptions = getChangedOptions(Shaders.shaderPackOptions);
        final List<String> listFiles = new ArrayList<String>();
        LineBuffer lines = new LineBuffer();
        if (is != null) {
            try {
                lines = ShaderPackParser.loadShader(program, ShaderType.VERTEX, is, filename, Shaders.shaderPack, listFiles, activeOptions);
                final MacroState macroState = new MacroState();
                for (final String line : lines) {
                    if (!macroState.processLine(line)) {
                        continue;
                    }
                    final ShaderLine sl = ShaderParser.parseLine(line, ShaderType.VERTEX);
                    if (sl == null) {
                        continue;
                    }
                    if (sl.isAttribute("mc_Entity")) {
                        Shaders.useEntityAttrib = true;
                        Shaders.progUseEntityAttrib = true;
                    }
                    else if (sl.isAttribute("mc_midTexCoord")) {
                        Shaders.useMidTexCoordAttrib = true;
                        Shaders.progUseMidTexCoordAttrib = true;
                    }
                    else if (sl.isAttribute("at_tangent")) {
                        Shaders.useTangentAttrib = true;
                        Shaders.progUseTangentAttrib = true;
                    }
                    else if (sl.isAttribute("at_velocity")) {
                        Shaders.useVelocityAttrib = true;
                        Shaders.progUseVelocityAttrib = true;
                    }
                    else if (sl.isAttribute("at_midBlock")) {
                        Shaders.useMidBlockAttrib = true;
                        Shaders.progUseMidBlockAttrib = true;
                    }
                    if (!sl.isConstInt("countInstances")) {
                        continue;
                    }
                    program.setCountInstances(sl.getValueInt());
                    SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, program.getCountInstances()));
                }
            }
            catch (Exception e) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
                e.printStackTrace();
                GL43.glDeleteShader(vertShader);
                return 0;
            }
        }
        final String vertexCode = lines.toString();
        if (Shaders.saveFinalShaders) {
            saveShader(filename, vertexCode);
        }
        shaderSource(vertShader, vertexCode);
        GL43.glCompileShader(vertShader);
        if (GL43.glGetShaderi(vertShader, 35713) != 1) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
        }
        printShaderLogInfo(vertShader, filename, listFiles);
        return vertShader;
    }
    
    private static int createGeomShader(final Program program, final String filename) {
        final InputStream is = Shaders.shaderPack.getResourceAsStream(filename);
        if (is == null) {
            return 0;
        }
        final int geomShader = GL43.glCreateShader(36313);
        if (geomShader == 0) {
            return 0;
        }
        final ShaderOption[] activeOptions = getChangedOptions(Shaders.shaderPackOptions);
        final List<String> listFiles = new ArrayList<String>();
        Shaders.progArbGeometryShader4 = false;
        Shaders.progExtGeometryShader4 = false;
        Shaders.progMaxVerticesOut = 3;
        LineBuffer lines = new LineBuffer();
        if (is != null) {
            try {
                lines = ShaderPackParser.loadShader(program, ShaderType.GEOMETRY, is, filename, Shaders.shaderPack, listFiles, activeOptions);
                final MacroState macroState = new MacroState();
                for (final String line : lines) {
                    if (!macroState.processLine(line)) {
                        continue;
                    }
                    final ShaderLine sl = ShaderParser.parseLine(line, ShaderType.GEOMETRY);
                    if (sl == null) {
                        continue;
                    }
                    if (sl.isExtension("GL_ARB_geometry_shader4")) {
                        final String val = Config.normalize(sl.getValue());
                        if (val.equals("enable") || val.equals("require") || val.equals("warn")) {
                            Shaders.progArbGeometryShader4 = true;
                        }
                    }
                    if (sl.isExtension("GL_EXT_geometry_shader4")) {
                        final String val = Config.normalize(sl.getValue());
                        if (val.equals("enable") || val.equals("require") || val.equals("warn")) {
                            Shaders.progExtGeometryShader4 = true;
                        }
                    }
                    if (!sl.isConstInt("maxVerticesOut")) {
                        continue;
                    }
                    Shaders.progMaxVerticesOut = sl.getValueInt();
                }
            }
            catch (Exception e) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
                e.printStackTrace();
                GL43.glDeleteShader(geomShader);
                return 0;
            }
        }
        final String geomCode = lines.toString();
        if (Shaders.saveFinalShaders) {
            saveShader(filename, geomCode);
        }
        shaderSource(geomShader, geomCode);
        GL43.glCompileShader(geomShader);
        if (GL43.glGetShaderi(geomShader, 35713) != 1) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
        }
        printShaderLogInfo(geomShader, filename, listFiles);
        return geomShader;
    }
    
    private static int createFragShader(final Program program, final String filename) {
        InputStream is = Shaders.shaderPack.getResourceAsStream(filename);
        if (is == null) {
            is = DefaultShaders.getResourceAsStream(filename);
        }
        if (is == null) {
            return 0;
        }
        final int fragShader = GL43.glCreateShader(35632);
        if (fragShader == 0) {
            return 0;
        }
        final ShaderOption[] activeOptions = getChangedOptions(Shaders.shaderPackOptions);
        final List<String> listFiles = new ArrayList<String>();
        LineBuffer lines = new LineBuffer();
        if (is != null) {
            try {
                lines = ShaderPackParser.loadShader(program, ShaderType.FRAGMENT, is, filename, Shaders.shaderPack, listFiles, activeOptions);
                final MacroState macroState = new MacroState();
                for (final String line : lines) {
                    if (!macroState.processLine(line)) {
                        continue;
                    }
                    final ShaderLine sl = ShaderParser.parseLine(line, ShaderType.FRAGMENT);
                    if (sl == null) {
                        continue;
                    }
                    if (sl.isUniform()) {
                        final String uniform = sl.getName();
                        int index;
                        if ((index = ShaderParser.getShadowDepthIndex(uniform)) >= 0) {
                            Shaders.usedShadowDepthBuffers = Math.max(Shaders.usedShadowDepthBuffers, index + 1);
                        }
                        else if ((index = ShaderParser.getShadowColorIndex(uniform)) >= 0) {
                            Shaders.usedShadowColorBuffers = Math.max(Shaders.usedShadowColorBuffers, index + 1);
                        }
                        else if ((index = ShaderParser.getShadowColorImageIndex(uniform)) >= 0) {
                            Shaders.usedShadowColorBuffers = Math.max(Shaders.usedShadowColorBuffers, index + 1);
                            Shaders.bindImageTextures = true;
                        }
                        else if ((index = ShaderParser.getDepthIndex(uniform)) >= 0) {
                            Shaders.usedDepthBuffers = Math.max(Shaders.usedDepthBuffers, index + 1);
                        }
                        else if (uniform.equals("gdepth") && Shaders.gbuffersFormat[1] == 6408) {
                            Shaders.gbuffersFormat[1] = 34836;
                        }
                        else if ((index = ShaderParser.getColorIndex(uniform)) >= 0) {
                            Shaders.usedColorBuffers = Math.max(Shaders.usedColorBuffers, index + 1);
                        }
                        else if ((index = ShaderParser.getColorImageIndex(uniform)) >= 0) {
                            Shaders.usedColorBuffers = Math.max(Shaders.usedColorBuffers, index + 1);
                            Shaders.bindImageTextures = true;
                        }
                        else {
                            if (!uniform.equals("centerDepthSmooth")) {
                                continue;
                            }
                            Shaders.centerDepthSmoothEnabled = true;
                        }
                    }
                    else if (sl.isConstInt("shadowMapResolution") || sl.isProperty("SHADOWRES")) {
                        Shaders.spShadowMapWidth = (Shaders.spShadowMapHeight = sl.getValueInt());
                        Shaders.shadowMapWidth = (Shaders.shadowMapHeight = Math.round(Shaders.spShadowMapWidth * Shaders.configShadowResMul));
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Shaders.spShadowMapWidth));
                    }
                    else if (sl.isConstFloat("shadowMapFov") || sl.isProperty("SHADOWFOV")) {
                        Shaders.shadowMapFOV = sl.getValueFloat();
                        Shaders.shadowMapIsOrtho = false;
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.shadowMapFOV));
                    }
                    else if (sl.isConstFloat("shadowDistance") || sl.isProperty("SHADOWHPL")) {
                        Shaders.shadowMapHalfPlane = sl.getValueFloat();
                        Shaders.shadowMapIsOrtho = true;
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.shadowMapHalfPlane));
                    }
                    else if (sl.isConstFloat("shadowDistanceRenderMul")) {
                        Shaders.shadowDistanceRenderMul = sl.getValueFloat();
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.shadowDistanceRenderMul));
                    }
                    else if (sl.isConstFloat("shadowIntervalSize")) {
                        Shaders.shadowIntervalSize = sl.getValueFloat();
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.shadowIntervalSize));
                    }
                    else if (sl.isConstBool("generateShadowMipmap", true)) {
                        Arrays.fill(Shaders.shadowMipmapEnabled, true);
                        SMCLog.info("Generate shadow mipmap");
                    }
                    else if (sl.isConstBool("generateShadowColorMipmap", true)) {
                        Arrays.fill(Shaders.shadowColorMipmapEnabled, true);
                        SMCLog.info("Generate shadow color mipmap");
                    }
                    else if (sl.isConstBool("shadowHardwareFiltering", true)) {
                        Arrays.fill(Shaders.shadowHardwareFilteringEnabled, true);
                        SMCLog.info("Hardware shadow filtering enabled.");
                    }
                    else if (sl.isConstBool("shadowHardwareFiltering0", true)) {
                        Shaders.shadowHardwareFilteringEnabled[0] = true;
                        SMCLog.info("shadowHardwareFiltering0");
                    }
                    else if (sl.isConstBool("shadowHardwareFiltering1", true)) {
                        Shaders.shadowHardwareFilteringEnabled[1] = true;
                        SMCLog.info("shadowHardwareFiltering1");
                    }
                    else if (sl.isConstBool("shadowtex0Mipmap", "shadowtexMipmap", true)) {
                        Shaders.shadowMipmapEnabled[0] = true;
                        SMCLog.info("shadowtex0Mipmap");
                    }
                    else if (sl.isConstBool("shadowtex1Mipmap", true)) {
                        Shaders.shadowMipmapEnabled[1] = true;
                        SMCLog.info("shadowtex1Mipmap");
                    }
                    else if (sl.isConstBool("shadowcolor0Mipmap", "shadowColor0Mipmap", true)) {
                        Shaders.shadowColorMipmapEnabled[0] = true;
                        SMCLog.info("shadowcolor0Mipmap");
                    }
                    else if (sl.isConstBool("shadowcolor1Mipmap", "shadowColor1Mipmap", true)) {
                        Shaders.shadowColorMipmapEnabled[1] = true;
                        SMCLog.info("shadowcolor1Mipmap");
                    }
                    else if (sl.isConstBool("shadowtex0Nearest", "shadowtexNearest", "shadow0MinMagNearest", true)) {
                        Shaders.shadowFilterNearest[0] = true;
                        SMCLog.info("shadowtex0Nearest");
                    }
                    else if (sl.isConstBool("shadowtex1Nearest", "shadow1MinMagNearest", true)) {
                        Shaders.shadowFilterNearest[1] = true;
                        SMCLog.info("shadowtex1Nearest");
                    }
                    else if (sl.isConstBool("shadowcolor0Nearest", "shadowColor0Nearest", "shadowColor0MinMagNearest", true)) {
                        Shaders.shadowColorFilterNearest[0] = true;
                        SMCLog.info("shadowcolor0Nearest");
                    }
                    else if (sl.isConstBool("shadowcolor1Nearest", "shadowColor1Nearest", "shadowColor1MinMagNearest", true)) {
                        Shaders.shadowColorFilterNearest[1] = true;
                        SMCLog.info("shadowcolor1Nearest");
                    }
                    else if (sl.isConstFloat("wetnessHalflife") || sl.isProperty("WETNESSHL")) {
                        Shaders.wetnessHalfLife = sl.getValueFloat();
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.wetnessHalfLife));
                    }
                    else if (sl.isConstFloat("drynessHalflife") || sl.isProperty("DRYNESSHL")) {
                        Shaders.drynessHalfLife = sl.getValueFloat();
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.drynessHalfLife));
                    }
                    else if (sl.isConstFloat("eyeBrightnessHalflife")) {
                        Shaders.eyeBrightnessHalflife = sl.getValueFloat();
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.eyeBrightnessHalflife));
                    }
                    else if (sl.isConstFloat("centerDepthHalflife")) {
                        Shaders.centerDepthSmoothHalflife = sl.getValueFloat();
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.centerDepthSmoothHalflife));
                    }
                    else if (sl.isConstFloat("sunPathRotation")) {
                        Shaders.sunPathRotation = sl.getValueFloat();
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.sunPathRotation));
                    }
                    else if (sl.isConstFloat("ambientOcclusionLevel")) {
                        Shaders.aoLevel = Config.limit(sl.getValueFloat(), 0.0f, 1.0f);
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, Shaders.aoLevel));
                    }
                    else if (sl.isConstInt("superSamplingLevel")) {
                        final int ssaa = sl.getValueInt();
                        if (ssaa > 1) {
                            SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, ssaa));
                            Shaders.superSamplingLevel = ssaa;
                        }
                        else {
                            Shaders.superSamplingLevel = 1;
                        }
                    }
                    else if (sl.isConstInt("noiseTextureResolution")) {
                        Shaders.noiseTextureResolution = sl.getValueInt();
                        Shaders.noiseTextureEnabled = true;
                        SMCLog.info("Noise texture enabled");
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Shaders.noiseTextureResolution));
                    }
                    else if (sl.isConstIntSuffix("Format")) {
                        final String name = StrUtils.removeSuffix(sl.getName(), "Format");
                        final String value = sl.getValue();
                        final int format = getTextureFormatFromString(value);
                        if (format == 0) {
                            continue;
                        }
                        final int bufferindex = getBufferIndex(name);
                        if (bufferindex >= 0) {
                            Shaders.gbuffersFormat[bufferindex] = format;
                            SMCLog.info("%s format: %s", name, value);
                        }
                        final int shadowColorIndex = ShaderParser.getShadowColorIndex(name);
                        if (shadowColorIndex < 0) {
                            continue;
                        }
                        Shaders.shadowBuffersFormat[shadowColorIndex] = format;
                        SMCLog.info("%s format: %s", name, value);
                    }
                    else if (sl.isConstBoolSuffix("Clear", false)) {
                        if (!program.getProgramStage().isAnyComposite()) {
                            continue;
                        }
                        final String name = StrUtils.removeSuffix(sl.getName(), "Clear");
                        final int bufferindex2 = getBufferIndex(name);
                        if (bufferindex2 >= 0) {
                            Shaders.gbuffersClear[bufferindex2] = false;
                            SMCLog.info("%s clear disabled", name);
                        }
                        final int shadowColorIndex2 = ShaderParser.getShadowColorIndex(name);
                        if (shadowColorIndex2 < 0) {
                            continue;
                        }
                        Shaders.shadowBuffersClear[shadowColorIndex2] = false;
                        SMCLog.info("%s clear disabled", name);
                    }
                    else if (sl.isConstVec4Suffix("ClearColor")) {
                        if (!program.getProgramStage().isAnyComposite()) {
                            continue;
                        }
                        final String name = StrUtils.removeSuffix(sl.getName(), "ClearColor");
                        final Vector4f col = sl.getValueVec4();
                        if (col != null) {
                            final int bufferindex3 = getBufferIndex(name);
                            if (bufferindex3 >= 0) {
                                Shaders.gbuffersClearColor[bufferindex3] = col;
                                SMCLog.info("%s clear color: %s %s %s %s", name, col.x(), col.y(), col.z(), col.w());
                            }
                            final int shadowColorIndex3 = ShaderParser.getShadowColorIndex(name);
                            if (shadowColorIndex3 < 0) {
                                continue;
                            }
                            Shaders.shadowBuffersClearColor[shadowColorIndex3] = col;
                            SMCLog.info("%s clear color: %s %s %s %s", name, col.x(), col.y(), col.z(), col.w());
                        }
                        else {
                            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, sl.getValue()));
                        }
                    }
                    else if (sl.isProperty("GAUX4FORMAT", "RGBA32F")) {
                        Shaders.gbuffersFormat[7] = 34836;
                        SMCLog.info("gaux4 format : RGB32AF");
                    }
                    else if (sl.isProperty("GAUX4FORMAT", "RGB32F")) {
                        Shaders.gbuffersFormat[7] = 34837;
                        SMCLog.info("gaux4 format : RGB32F");
                    }
                    else if (sl.isProperty("GAUX4FORMAT", "RGB16")) {
                        Shaders.gbuffersFormat[7] = 32852;
                        SMCLog.info("gaux4 format : RGB16");
                    }
                    else if (sl.isConstBoolSuffix("MipmapEnabled", true)) {
                        if (!program.getProgramStage().isAnyComposite()) {
                            continue;
                        }
                        final String name = StrUtils.removeSuffix(sl.getName(), "MipmapEnabled");
                        final int bufferindex2 = getBufferIndex(name);
                        if (bufferindex2 < 0) {
                            continue;
                        }
                        int compositeMipmapSetting = program.getCompositeMipmapSetting();
                        compositeMipmapSetting |= 1 << bufferindex2;
                        program.setCompositeMipmapSetting(compositeMipmapSetting);
                        SMCLog.info("%s mipmap enabled", name);
                    }
                    else if (sl.isProperty("DRAWBUFFERS")) {
                        final String val = sl.getValue();
                        final String[] dbs = ShaderParser.parseDrawBuffers(val);
                        if (dbs != null) {
                            program.setDrawBufSettings(dbs);
                        }
                        else {
                            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, val));
                        }
                    }
                    else {
                        if (!sl.isProperty("RENDERTARGETS")) {
                            continue;
                        }
                        final String val = sl.getValue();
                        final String[] dbs = ShaderParser.parseRenderTargets(val);
                        if (dbs != null) {
                            program.setDrawBufSettings(dbs);
                        }
                        else {
                            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, val));
                        }
                    }
                }
            }
            catch (Exception e) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
                e.printStackTrace();
                GL43.glDeleteShader(fragShader);
                return 0;
            }
        }
        final String fragCode = lines.toString();
        if (Shaders.saveFinalShaders) {
            saveShader(filename, fragCode);
        }
        shaderSource(fragShader, fragCode);
        GL43.glCompileShader(fragShader);
        if (GL43.glGetShaderi(fragShader, 35713) != 1) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
        }
        printShaderLogInfo(fragShader, filename, listFiles);
        return fragShader;
    }
    
    private static void shaderSource(final int shader, final String code) {
        final MemoryStack stack = MemoryStack.stackGet();
        final int stackPointer = stack.getPointer();
        try {
            final ByteBuffer sourceBuffer = MemoryUtil.memUTF8((CharSequence)code, true);
            final PointerBuffer pointers = stack.mallocPointer(1);
            pointers.put(sourceBuffer);
            GL43.nglShaderSource(shader, 1, pointers.address0(), 0L);
            APIUtil.apiArrayFree(pointers.address0(), 1);
        }
        finally {
            stack.setPointer(stackPointer);
        }
    }
    
    public static void saveShader(final String filename, final String code) {
        try {
            final File file = new File(Shaders.shaderPacksDir, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
            file.getParentFile().mkdirs();
            Config.writeFile(file, code);
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filename));
            e.printStackTrace();
        }
    }
    
    private static void clearDirectory(final File dir) {
        if (!dir.exists()) {
            return;
        }
        if (!dir.isDirectory()) {
            return;
        }
        final File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; ++i) {
            final File file = files[i];
            if (file.isDirectory()) {
                clearDirectory(file);
            }
            file.delete();
        }
    }
    
    private static boolean printProgramLogInfo(final int obj, final String name) {
        final IntBuffer iVal = BufferUtils.createIntBuffer(1);
        GL43.glGetProgramiv(obj, 35716, iVal);
        final int length = iVal.get();
        if (length > 1) {
            final ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();
            GL43.glGetProgramInfoLog(obj, iVal, infoLog);
            final byte[] infoBytes = new byte[length];
            infoLog.get(infoBytes);
            if (infoBytes[length - 1] == 0) {
                infoBytes[length - 1] = 10;
            }
            String out = new String(infoBytes, StandardCharsets.US_ASCII);
            out = StrUtils.trim(out, " \n\r\t");
            SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, name, out));
            return false;
        }
        return true;
    }
    
    private static boolean printShaderLogInfo(final int shader, final String name, final List<String> listFiles) {
        final IntBuffer iVal = BufferUtils.createIntBuffer(1);
        final int length = GL43.glGetShaderi(shader, 35716);
        if (length > 1) {
            for (int i = 0; i < listFiles.size(); ++i) {
                final String path = listFiles.get(i);
                SMCLog.info(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, i + 1, path));
            }
            String log = GL43.glGetShaderInfoLog(shader, length);
            log = StrUtils.trim(log, " \n\r\t");
            SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, name, log));
            return false;
        }
        return true;
    }
    
    public static void useProgram(Program program) {
        checkGLError("pre-useProgram");
        if (Shaders.isShadowPass) {
            program = Shaders.ProgramShadow;
        }
        if (Shaders.activeProgram == program) {
            return;
        }
        flushRenderBuffers();
        updateAlphaBlend(Shaders.activeProgram, program);
        if (Shaders.glDebugGroups && Shaders.glDebugGroupProgram) {
            KHRDebug.glPopDebugGroup();
        }
        Shaders.activeProgram = program;
        if (Shaders.glDebugGroups) {
            KHRDebug.glPushDebugGroup(33354, 0, (CharSequence)Shaders.activeProgram.getRealProgramName());
            Shaders.glDebugGroupProgram = true;
        }
        int programID = program.getId();
        GlStateManager._glUseProgram(Shaders.activeProgramID = programID);
        if (checkGLError("useProgram") != 0) {
            program.setId(0);
            programID = program.getId();
            GlStateManager._glUseProgram(Shaders.activeProgramID = programID);
        }
        Shaders.shaderUniforms.setProgram(programID);
        if (Shaders.customUniforms != null) {
            Shaders.customUniforms.setProgram(programID);
        }
        if (programID == 0) {
            fki.useVanillaProgram();
            return;
        }
        final DrawBuffers drawBuffers = program.getDrawBuffers();
        if (Shaders.isRenderingDfb) {
            GlState.setDrawBuffers(drawBuffers);
        }
        setProgramUniforms(program.getProgramStage());
        setImageUniforms();
        checkGLError("end useProgram");
    }
    
    private static void setProgramUniforms(final ProgramStage programStage) {
        switch (Shaders.Shaders$1.$SwitchMap$net$optifine$shaders$ProgramStage[programStage.ordinal()]) {
            case 1: {
                setProgramUniform1i(Shaders.uniform_gtexture, 0);
                setProgramUniform1i(Shaders.uniform_lightmap, 2);
                setProgramUniform1i(Shaders.uniform_normals, 1);
                setProgramUniform1i(Shaders.uniform_specular, 3);
                setProgramUniform1i(Shaders.uniform_shadow, Shaders.waterShadowEnabled ? 5 : 4);
                setProgramUniform1i(Shaders.uniform_watershadow, 4);
                setProgramUniform1i(Shaders.uniform_shadowtex0, 4);
                setProgramUniform1i(Shaders.uniform_shadowtex1, 5);
                setProgramUniform1i(Shaders.uniform_depthtex0, 6);
                if (Shaders.customTexturesGbuffers != null || Shaders.hasDeferredPrograms) {
                    setProgramUniform1i(Shaders.uniform_gaux1, 7);
                    setProgramUniform1i(Shaders.uniform_gaux2, 8);
                    setProgramUniform1i(Shaders.uniform_gaux3, 9);
                    setProgramUniform1i(Shaders.uniform_gaux4, 10);
                    setProgramUniform1i(Shaders.uniform_colortex4, 7);
                    setProgramUniform1i(Shaders.uniform_colortex5, 8);
                    setProgramUniform1i(Shaders.uniform_colortex6, 9);
                    setProgramUniform1i(Shaders.uniform_colortex7, 10);
                    if (Shaders.usedColorBuffers > 8) {
                        setProgramUniform1i(Shaders.uniform_colortex8, 16);
                        setProgramUniform1i(Shaders.uniform_colortex9, 17);
                        setProgramUniform1i(Shaders.uniform_colortex10, 18);
                        setProgramUniform1i(Shaders.uniform_colortex11, 19);
                        setProgramUniform1i(Shaders.uniform_colortex12, 20);
                        setProgramUniform1i(Shaders.uniform_colortex13, 21);
                        setProgramUniform1i(Shaders.uniform_colortex14, 22);
                        setProgramUniform1i(Shaders.uniform_colortex15, 23);
                    }
                }
                setProgramUniform1i(Shaders.uniform_depthtex1, 11);
                setProgramUniform1i(Shaders.uniform_shadowcolor, 13);
                setProgramUniform1i(Shaders.uniform_shadowcolor0, 13);
                setProgramUniform1i(Shaders.uniform_shadowcolor1, 14);
                setProgramUniform1i(Shaders.uniform_noisetex, 15);
                break;
            }
            case 2:
            case 3:
            case 4:
            case 5: {
                setProgramUniform1i(Shaders.uniform_gcolor, 0);
                setProgramUniform1i(Shaders.uniform_gdepth, 1);
                setProgramUniform1i(Shaders.uniform_gnormal, 2);
                setProgramUniform1i(Shaders.uniform_composite, 3);
                setProgramUniform1i(Shaders.uniform_gaux1, 7);
                setProgramUniform1i(Shaders.uniform_gaux2, 8);
                setProgramUniform1i(Shaders.uniform_gaux3, 9);
                setProgramUniform1i(Shaders.uniform_gaux4, 10);
                setProgramUniform1i(Shaders.uniform_colortex0, 0);
                setProgramUniform1i(Shaders.uniform_colortex1, 1);
                setProgramUniform1i(Shaders.uniform_colortex2, 2);
                setProgramUniform1i(Shaders.uniform_colortex3, 3);
                setProgramUniform1i(Shaders.uniform_colortex4, 7);
                setProgramUniform1i(Shaders.uniform_colortex5, 8);
                setProgramUniform1i(Shaders.uniform_colortex6, 9);
                setProgramUniform1i(Shaders.uniform_colortex7, 10);
                if (Shaders.usedColorBuffers > 8) {
                    setProgramUniform1i(Shaders.uniform_colortex8, 16);
                    setProgramUniform1i(Shaders.uniform_colortex9, 17);
                    setProgramUniform1i(Shaders.uniform_colortex10, 18);
                    setProgramUniform1i(Shaders.uniform_colortex11, 19);
                    setProgramUniform1i(Shaders.uniform_colortex12, 20);
                    setProgramUniform1i(Shaders.uniform_colortex13, 21);
                    setProgramUniform1i(Shaders.uniform_colortex14, 22);
                    setProgramUniform1i(Shaders.uniform_colortex15, 23);
                }
                setProgramUniform1i(Shaders.uniform_shadow, Shaders.waterShadowEnabled ? 5 : 4);
                setProgramUniform1i(Shaders.uniform_watershadow, 4);
                setProgramUniform1i(Shaders.uniform_shadowtex0, 4);
                setProgramUniform1i(Shaders.uniform_shadowtex1, 5);
                setProgramUniform1i(Shaders.uniform_gdepthtex, 6);
                setProgramUniform1i(Shaders.uniform_depthtex0, 6);
                setProgramUniform1i(Shaders.uniform_depthtex1, 11);
                setProgramUniform1i(Shaders.uniform_depthtex2, 12);
                setProgramUniform1i(Shaders.uniform_shadowcolor, 13);
                setProgramUniform1i(Shaders.uniform_shadowcolor0, 13);
                setProgramUniform1i(Shaders.uniform_shadowcolor1, 14);
                setProgramUniform1i(Shaders.uniform_noisetex, 15);
                break;
            }
            case 6: {
                setProgramUniform1i(Shaders.uniform_tex, 0);
                setProgramUniform1i(Shaders.uniform_gtexture, 0);
                setProgramUniform1i(Shaders.uniform_lightmap, 2);
                setProgramUniform1i(Shaders.uniform_normals, 1);
                setProgramUniform1i(Shaders.uniform_specular, 3);
                setProgramUniform1i(Shaders.uniform_shadow, Shaders.waterShadowEnabled ? 5 : 4);
                setProgramUniform1i(Shaders.uniform_watershadow, 4);
                setProgramUniform1i(Shaders.uniform_shadowtex0, 4);
                setProgramUniform1i(Shaders.uniform_shadowtex1, 5);
                if (Shaders.customTexturesGbuffers != null) {
                    setProgramUniform1i(Shaders.uniform_gaux1, 7);
                    setProgramUniform1i(Shaders.uniform_gaux2, 8);
                    setProgramUniform1i(Shaders.uniform_gaux3, 9);
                    setProgramUniform1i(Shaders.uniform_gaux4, 10);
                    setProgramUniform1i(Shaders.uniform_colortex4, 7);
                    setProgramUniform1i(Shaders.uniform_colortex5, 8);
                    setProgramUniform1i(Shaders.uniform_colortex6, 9);
                    setProgramUniform1i(Shaders.uniform_colortex7, 10);
                    if (Shaders.usedColorBuffers > 8) {
                        setProgramUniform1i(Shaders.uniform_colortex8, 16);
                        setProgramUniform1i(Shaders.uniform_colortex9, 17);
                        setProgramUniform1i(Shaders.uniform_colortex10, 18);
                        setProgramUniform1i(Shaders.uniform_colortex11, 19);
                        setProgramUniform1i(Shaders.uniform_colortex12, 20);
                        setProgramUniform1i(Shaders.uniform_colortex13, 21);
                        setProgramUniform1i(Shaders.uniform_colortex14, 22);
                        setProgramUniform1i(Shaders.uniform_colortex15, 23);
                    }
                }
                setProgramUniform1i(Shaders.uniform_shadowcolor, 13);
                setProgramUniform1i(Shaders.uniform_shadowcolor0, 13);
                setProgramUniform1i(Shaders.uniform_shadowcolor1, 14);
                setProgramUniform1i(Shaders.uniform_noisetex, 15);
                break;
            }
        }
        final cfz stack = (Shaders.mc.t != null) ? Shaders.mc.t.eO() : null;
        final cfu item = (stack != null) ? stack.d() : null;
        int itemID = -1;
        cpn block = null;
        if (item != null) {
            itemID = jb.i.a((Object)item);
            if (item instanceof cds) {
                block = ((cds)item).e();
            }
            itemID = ItemAliases.getItemAliasId(itemID);
        }
        int blockLight = (block != null) ? block.n().h() : 0;
        final cfz stack2 = (Shaders.mc.t != null) ? Shaders.mc.t.eP() : null;
        final cfu item2 = (stack2 != null) ? stack2.d() : null;
        int itemID2 = -1;
        cpn block2 = null;
        if (item2 != null) {
            itemID2 = jb.i.a((Object)item2);
            if (item2 instanceof cds) {
                block2 = ((cds)item2).e();
            }
            itemID2 = ItemAliases.getItemAliasId(itemID2);
        }
        final int blockLight2 = (block2 != null) ? block2.n().h() : 0;
        if (isOldHandLight() && blockLight2 > blockLight) {
            itemID = itemID2;
            blockLight = blockLight2;
        }
        final float playerMood = (Shaders.mc.t != null) ? Shaders.mc.t.o() : 0.0f;
        setProgramUniform1i(Shaders.uniform_heldItemId, itemID);
        setProgramUniform1i(Shaders.uniform_heldBlockLightValue, blockLight);
        setProgramUniform1i(Shaders.uniform_heldItemId2, itemID2);
        setProgramUniform1i(Shaders.uniform_heldBlockLightValue2, blockLight2);
        setProgramUniform1i(Shaders.uniform_fogMode, (Shaders.fogEnabled && Shaders.fogAllowed) ? Shaders.fogMode : 0);
        setProgramUniform1i(Shaders.uniform_fogShape, Shaders.fogShape);
        setProgramUniform1f(Shaders.uniform_fogDensity, (Shaders.fogEnabled && Shaders.fogAllowed) ? Shaders.fogDensity : 0.0f);
        setProgramUniform1f(Shaders.uniform_fogStart, (Shaders.fogEnabled && Shaders.fogAllowed) ? Shaders.fogStart : 1.7014117E38f);
        setProgramUniform1f(Shaders.uniform_fogEnd, (Shaders.fogEnabled && Shaders.fogAllowed) ? Shaders.fogEnd : Float.MAX_VALUE);
        setProgramUniform3f(Shaders.uniform_fogColor, Shaders.fogColorR, Shaders.fogColorG, Shaders.fogColorB);
        setProgramUniform3f(Shaders.uniform_skyColor, Shaders.skyColorR, Shaders.skyColorG, Shaders.skyColorB);
        setProgramUniform1i(Shaders.uniform_worldTime, (int)(Shaders.worldTime % 24000L));
        setProgramUniform1i(Shaders.uniform_worldDay, (int)(Shaders.worldTime / 24000L));
        setProgramUniform1i(Shaders.uniform_moonPhase, Shaders.moonPhase);
        setProgramUniform1i(Shaders.uniform_frameCounter, Shaders.frameCounter);
        setProgramUniform1f(Shaders.uniform_frameTime, Shaders.frameTime);
        setProgramUniform1f(Shaders.uniform_frameTimeCounter, Shaders.frameTimeCounter);
        setProgramUniform1f(Shaders.uniform_sunAngle, Shaders.sunAngle);
        setProgramUniform1f(Shaders.uniform_shadowAngle, Shaders.shadowAngle);
        setProgramUniform1f(Shaders.uniform_rainStrength, Shaders.rainStrength);
        setProgramUniform1f(Shaders.uniform_aspectRatio, Shaders.renderWidth / (float)Shaders.renderHeight);
        setProgramUniform1f(Shaders.uniform_viewWidth, (float)Shaders.renderWidth);
        setProgramUniform1f(Shaders.uniform_viewHeight, (float)Shaders.renderHeight);
        setProgramUniform1f(Shaders.uniform_near, 0.05f);
        setProgramUniform1f(Shaders.uniform_far, (float)((int)Shaders.mc.m.d().c() * 16));
        setProgramUniform3f(Shaders.uniform_sunPosition, Shaders.sunPosition[0], Shaders.sunPosition[1], Shaders.sunPosition[2]);
        setProgramUniform3f(Shaders.uniform_moonPosition, Shaders.moonPosition[0], Shaders.moonPosition[1], Shaders.moonPosition[2]);
        setProgramUniform3f(Shaders.uniform_shadowLightPosition, Shaders.shadowLightPosition[0], Shaders.shadowLightPosition[1], Shaders.shadowLightPosition[2]);
        setProgramUniform3f(Shaders.uniform_upPosition, Shaders.upPosition[0], Shaders.upPosition[1], Shaders.upPosition[2]);
        setProgramUniform3f(Shaders.uniform_previousCameraPosition, (float)Shaders.previousCameraPositionX, (float)Shaders.previousCameraPositionY, (float)Shaders.previousCameraPositionZ);
        setProgramUniform3f(Shaders.uniform_cameraPosition, (float)Shaders.cameraPositionX, (float)Shaders.cameraPositionY, (float)Shaders.cameraPositionZ);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferModelView, false, Shaders.modelView);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferModelViewInverse, false, Shaders.modelViewInverse);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferPreviousProjection, false, Shaders.previousProjection);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferProjection, false, Shaders.projection);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferProjectionInverse, false, Shaders.projectionInverse);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferPreviousModelView, false, Shaders.previousModelView);
        if (Shaders.hasShadowMap) {
            setProgramUniformMatrix4ARB(Shaders.uniform_shadowProjection, false, Shaders.shadowProjection);
            setProgramUniformMatrix4ARB(Shaders.uniform_shadowProjectionInverse, false, Shaders.shadowProjectionInverse);
            setProgramUniformMatrix4ARB(Shaders.uniform_shadowModelView, false, Shaders.shadowModelView);
            setProgramUniformMatrix4ARB(Shaders.uniform_shadowModelViewInverse, false, Shaders.shadowModelViewInverse);
        }
        setProgramUniform1f(Shaders.uniform_wetness, Shaders.wetness);
        setProgramUniform1f(Shaders.uniform_eyeAltitude, Shaders.eyePosY);
        setProgramUniform2i(Shaders.uniform_eyeBrightness, Shaders.eyeBrightness & 0xFFFF, Shaders.eyeBrightness >> 16);
        setProgramUniform2i(Shaders.uniform_eyeBrightnessSmooth, Math.round(Shaders.eyeBrightnessFadeX), Math.round(Shaders.eyeBrightnessFadeY));
        setProgramUniform2i(Shaders.uniform_terrainTextureSize, Shaders.terrainTextureSize[0], Shaders.terrainTextureSize[1]);
        setProgramUniform1i(Shaders.uniform_terrainIconSize, Shaders.terrainIconSize);
        setProgramUniform1i(Shaders.uniform_isEyeInWater, Shaders.isEyeInWater);
        setProgramUniform1f(Shaders.uniform_nightVision, Shaders.nightVision);
        setProgramUniform1f(Shaders.uniform_blindness, Shaders.blindness);
        setProgramUniform1f(Shaders.uniform_screenBrightness, (float)(double)Shaders.mc.m.ak().c());
        setProgramUniform1i(Shaders.uniform_hideGUI, Shaders.mc.m.Z ? 1 : 0);
        setProgramUniform1f(Shaders.uniform_centerDepthSmooth, Shaders.centerDepthSmooth);
        setProgramUniform2i(Shaders.uniform_atlasSize, Shaders.atlasSizeX, Shaders.atlasSizeY);
        setProgramUniform1f(Shaders.uniform_playerMood, playerMood);
        setProgramUniform1i(Shaders.uniform_renderStage, Shaders.renderStage.ordinal());
        setProgramUniform1i(Shaders.uniform_bossBattle, Shaders.bossBattle);
        setProgramUniform1f(Shaders.uniform_darknessFactor, Shaders.darknessFactor);
        setProgramUniform1f(Shaders.uniform_darknessLightFactor, Shaders.darknessLightFactor);
        GlStateManager.applyAlphaTest();
        if (Shaders.customUniforms != null) {
            Shaders.customUniforms.update();
        }
    }
    
    private static void setImageUniforms() {
        if (!Shaders.bindImageTextures) {
            return;
        }
        Shaders.uniform_colorimg0.setValue(Shaders.colorImageUnit[0]);
        Shaders.uniform_colorimg1.setValue(Shaders.colorImageUnit[1]);
        Shaders.uniform_colorimg2.setValue(Shaders.colorImageUnit[2]);
        Shaders.uniform_colorimg3.setValue(Shaders.colorImageUnit[3]);
        Shaders.uniform_colorimg4.setValue(Shaders.colorImageUnit[4]);
        Shaders.uniform_colorimg5.setValue(Shaders.colorImageUnit[5]);
        Shaders.uniform_shadowcolorimg0.setValue(Shaders.shadowColorImageUnit[0]);
        Shaders.uniform_shadowcolorimg1.setValue(Shaders.shadowColorImageUnit[1]);
    }
    
    private static void updateAlphaBlend(final Program programOld, final Program programNew) {
        if (programOld.getAlphaState() != null) {
            GlStateManager.unlockAlpha();
        }
        if (programOld.getBlendState() != null) {
            GlStateManager.unlockBlend();
        }
        if (programOld.getBlendStatesIndexed() != null) {
            GlStateManager.applyCurrentBlend();
        }
        final GlAlphaState alphaNew = programNew.getAlphaState();
        if (alphaNew != null) {
            GlStateManager.lockAlpha(alphaNew);
        }
        final GlBlendState blendNew = programNew.getBlendState();
        if (blendNew != null) {
            GlStateManager.lockBlend(blendNew);
        }
        if (programNew.getBlendStatesIndexed() != null) {
            GlStateManager.setBlendsIndexed(programNew.getBlendStatesIndexed());
        }
    }
    
    private static void setProgramUniform1i(final ShaderUniform1i su, final int value) {
        su.setValue(value);
    }
    
    private static void setProgramUniform2i(final ShaderUniform2i su, final int i0, final int i1) {
        su.setValue(i0, i1);
    }
    
    private static void setProgramUniform1f(final ShaderUniform1f su, final float value) {
        su.setValue(value);
    }
    
    private static void setProgramUniform3f(final ShaderUniform3f su, final float f0, final float f1, final float f2) {
        su.setValue(f0, f1, f2);
    }
    
    private static void setProgramUniformMatrix4ARB(final ShaderUniformM4 su, final boolean transpose, final FloatBuffer matrix) {
        su.setValue(transpose, matrix);
    }
    
    public static int getBufferIndex(final String name) {
        final int colortexIndex = ShaderParser.getIndex(name, "colortex", 0, 15);
        if (colortexIndex >= 0) {
            return colortexIndex;
        }
        final int colorimgIndex = ShaderParser.getIndex(name, "colorimg", 0, 15);
        if (colorimgIndex >= 0) {
            return colorimgIndex;
        }
        if (name.equals("gcolor")) {
            return 0;
        }
        if (name.equals("gdepth")) {
            return 1;
        }
        if (name.equals("gnormal")) {
            return 2;
        }
        if (name.equals("composite")) {
            return 3;
        }
        if (name.equals("gaux1")) {
            return 4;
        }
        if (name.equals("gaux2")) {
            return 5;
        }
        if (name.equals("gaux3")) {
            return 6;
        }
        if (name.equals("gaux4")) {
            return 7;
        }
        return -1;
    }
    
    private static int getTextureFormatFromString(String par) {
        par = par.trim();
        for (int i = 0; i < Shaders.formatNames.length; ++i) {
            final String name = Shaders.formatNames[i];
            if (par.equals(name)) {
                return Shaders.formatIds[i];
            }
        }
        return 0;
    }
    
    public static int getImageFormat(final int textureFormat) {
        switch (textureFormat) {
            case 8194: {
                return 33321;
            }
            case 33319: {
                return 33323;
            }
            case 6407: {
                return 32849;
            }
            case 6408: {
                return 32856;
            }
            case 10768: {
                return 32849;
            }
            case 32855: {
                return 32856;
            }
            case 35901: {
                return 32852;
            }
            default: {
                return textureFormat;
            }
        }
    }
    
    private static void setupNoiseTexture() {
        if (Shaders.noiseTexture == null && Shaders.noiseTexturePath != null) {
            Shaders.noiseTexture = loadCustomTexture(15, Shaders.noiseTexturePath);
        }
        if (Shaders.noiseTexture == null) {
            Shaders.noiseTexture = new HFNoiseTexture(Shaders.noiseTextureResolution, Shaders.noiseTextureResolution);
        }
    }
    
    private static void loadEntityDataMap() {
        Shaders.mapBlockToEntityData = new IdentityHashMap<Block, Integer>(300);
        if (Shaders.mapBlockToEntityData.isEmpty()) {
            for (final acq key : jb.f.e()) {
                final cpn block = (cpn)jb.f.a(key);
                final int id = jb.f.a((Object)block);
                Shaders.mapBlockToEntityData.put((Block)block, id);
            }
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(Shaders.shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
        }
        catch (Exception ex) {}
        if (reader != null) {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    final Matcher m = Shaders.patternLoadEntityDataMap.matcher(line);
                    if (m.matches()) {
                        final String name = m.group(1);
                        final String value = m.group(2);
                        final int id2 = Integer.parseInt(value);
                        final acq loc = new acq(name);
                        if (jb.f.c(loc)) {
                            final cpn block2 = (cpn)jb.f.a(loc);
                            Shaders.mapBlockToEntityData.put((Block)block2, id2);
                        }
                        else {
                            SMCLog.warning("Unknown block name %s", name);
                        }
                    }
                    else {
                        SMCLog.warning("unmatched %s\n", line);
                    }
                }
            }
            catch (Exception e) {
                SMCLog.warning("Error parsing mc_Entity_x.txt");
            }
        }
        if (reader != null) {
            try {
                reader.close();
            }
            catch (Exception ex2) {}
        }
    }
    
    private static IntBuffer fillIntBufferZero(final IntBuffer buf) {
        for (int limit = buf.limit(), i = buf.position(); i < limit; ++i) {
            buf.put(i, 0);
        }
        return buf;
    }
    
    private static DrawBuffers fillIntBufferZero(final DrawBuffers buf) {
        for (int limit = buf.limit(), i = buf.position(); i < limit; ++i) {
            buf.put(i, 0);
        }
        return buf;
    }
    
    public static void uninit() {
        if (Shaders.isShaderPackInitialized) {
            checkGLError("Shaders.uninit pre");
            for (int i = 0; i < Shaders.ProgramsAll.length; ++i) {
                final Program pi = Shaders.ProgramsAll[i];
                if (pi.getRef() != 0) {
                    GL43.glDeleteProgram(pi.getRef());
                    checkGLError("del programRef");
                }
                pi.setRef(0);
                pi.setId(0);
                pi.setDrawBufSettings(null);
                pi.setDrawBuffers(null);
                pi.setCompositeMipmapSetting(0);
                final ComputeProgram[] cps = pi.getComputePrograms();
                for (int c = 0; c < cps.length; ++c) {
                    final ComputeProgram cp = cps[c];
                    if (cp.getRef() != 0) {
                        GL43.glDeleteProgram(cp.getRef());
                        checkGLError("del programRef");
                    }
                    cp.setRef(0);
                    cp.setId(0);
                }
                pi.setComputePrograms(new ComputeProgram[0]);
            }
            Shaders.hasDeferredPrograms = false;
            Shaders.hasShadowcompPrograms = false;
            Shaders.hasPreparePrograms = false;
            if (Shaders.dfb != null) {
                Shaders.dfb.delete();
                Shaders.dfb = null;
                checkGLError("del dfb");
            }
            if (Shaders.sfb != null) {
                Shaders.sfb.delete();
                Shaders.sfb = null;
                checkGLError("del sfb");
            }
            if (Shaders.dfbDrawBuffers != null) {
                fillIntBufferZero(Shaders.dfbDrawBuffers);
            }
            if (Shaders.sfbDrawBuffers != null) {
                fillIntBufferZero(Shaders.sfbDrawBuffers);
            }
            if (Shaders.noiseTexture != null) {
                Shaders.noiseTexture.deleteTexture();
                Shaders.noiseTexture = null;
            }
            for (int i = 0; i < Shaders.colorImageUnit.length; ++i) {
                GlStateManager.bindImageTexture(Shaders.colorImageUnit[i], 0, 0, false, 0, 35000, 32856);
            }
            SMCLog.info("Uninit");
            Shaders.hasShadowMap = false;
            Shaders.shouldSkipDefaultShadow = false;
            Shaders.isShaderPackInitialized = false;
            checkGLError("Shaders.uninit");
        }
    }
    
    public static void scheduleResize() {
        Shaders.renderDisplayHeight = 0;
    }
    
    public static void scheduleResizeShadow() {
        Shaders.needResizeShadow = true;
    }
    
    private static void resize() {
        Shaders.renderDisplayWidth = Shaders.mc.aM().k();
        Shaders.renderDisplayHeight = Shaders.mc.aM().l();
        Shaders.renderWidth = Math.round(Shaders.renderDisplayWidth * Shaders.configRenderResMul);
        Shaders.renderHeight = Math.round(Shaders.renderDisplayHeight * Shaders.configRenderResMul);
        setupFrameBuffer();
    }
    
    private static void resizeShadow() {
        Shaders.needResizeShadow = false;
        Shaders.shadowMapWidth = Math.round(Shaders.spShadowMapWidth * Shaders.configShadowResMul);
        Shaders.shadowMapHeight = Math.round(Shaders.spShadowMapHeight * Shaders.configShadowResMul);
        setupShadowFrameBuffer();
    }
    
    private static void setupFrameBuffer() {
        if (Shaders.dfb != null) {
            Shaders.dfb.delete();
        }
        final boolean[] depthFilterNearest = ArrayUtils.newBoolean(Shaders.usedDepthBuffers, true);
        final boolean[] depthFilterHardware = new boolean[Shaders.usedDepthBuffers];
        final boolean[] colorFilterNearest = new boolean[Shaders.usedColorBuffers];
        final int[] colorImageUnits = (int[])(Shaders.bindImageTextures ? Shaders.colorImageUnit : null);
        (Shaders.dfb = new ShadersFramebuffer("dfb", Shaders.renderWidth, Shaders.renderHeight, Shaders.usedColorBuffers, Shaders.usedDepthBuffers, 8, depthFilterNearest, depthFilterHardware, colorFilterNearest, Shaders.colorBufferSizes, Shaders.gbuffersFormat, Shaders.colorTextureImageUnit, Shaders.depthTextureImageUnit, colorImageUnits, Shaders.dfbDrawBuffers)).setup();
    }
    
    public static int getPixelFormat(final int internalFormat) {
        switch (internalFormat) {
            case 33329:
            case 33335:
            case 36238:
            case 36239: {
                return 36251;
            }
            case 33330:
            case 33336:
            case 36220:
            case 36221: {
                return 36251;
            }
            case 33331:
            case 33337:
            case 36232:
            case 36233: {
                return 36251;
            }
            case 33332:
            case 33338:
            case 36214:
            case 36215: {
                return 36251;
            }
            case 33333:
            case 33339:
            case 36226:
            case 36227: {
                return 36251;
            }
            case 33334:
            case 33340:
            case 36208:
            case 36209: {
                return 36251;
            }
            default: {
                return 32993;
            }
        }
    }
    
    private static void setupShadowFrameBuffer() {
        if (!Shaders.hasShadowMap) {
            return;
        }
        Shaders.isShadowPass = true;
        if (Shaders.sfb != null) {
            Shaders.sfb.delete();
        }
        final DynamicDimension[] shadowColorBufferSizes = new DynamicDimension[2];
        final int[] shadowColorImageUnits = (int[])(Shaders.bindImageTextures ? Shaders.shadowColorImageUnit : null);
        (Shaders.sfb = new ShadersFramebuffer("sfb", Shaders.shadowMapWidth, Shaders.shadowMapHeight, Shaders.usedShadowColorBuffers, Shaders.usedShadowDepthBuffers, 8, Shaders.shadowFilterNearest, Shaders.shadowHardwareFilteringEnabled, Shaders.shadowColorFilterNearest, shadowColorBufferSizes, Shaders.shadowBuffersFormat, Shaders.shadowColorTextureImageUnit, Shaders.shadowDepthTextureImageUnit, shadowColorImageUnits, Shaders.sfbDrawBuffers)).setup();
        Shaders.isShadowPass = false;
    }
    
    public static void beginRender(final enn minecraft, final emz activeRenderInfo, final float partialTicks, final long finishTimeNano) {
        checkGLError("pre beginRender");
        checkWorldChanged(Shaders.mc.s);
        Shaders.mc = minecraft;
        Shaders.mc.aG().a("init");
        Shaders.entityRenderer = Shaders.mc.j;
        if (!Shaders.isShaderPackInitialized) {
            try {
                init();
            }
            catch (IllegalStateException e) {
                if (Config.normalize(e.getMessage()).equals("Function is not supported")) {
                    printChatAndLogError(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, e.getMessage()));
                    e.printStackTrace();
                    setShaderPack("OFF");
                    return;
                }
            }
        }
        if (Shaders.mc.aM().k() != Shaders.renderDisplayWidth || Shaders.mc.aM().l() != Shaders.renderDisplayHeight) {
            resize();
        }
        if (Shaders.needResizeShadow) {
            resizeShadow();
        }
        ++Shaders.frameCounter;
        if (Shaders.frameCounter >= 720720) {
            Shaders.frameCounter = 0;
        }
        Shaders.systemTime = System.currentTimeMillis();
        if (Shaders.lastSystemTime == 0L) {
            Shaders.lastSystemTime = Shaders.systemTime;
        }
        Shaders.diffSystemTime = Shaders.systemTime - Shaders.lastSystemTime;
        Shaders.lastSystemTime = Shaders.systemTime;
        Shaders.frameTime = Shaders.diffSystemTime / 1000.0f;
        Shaders.frameTimeCounter += Shaders.frameTime;
        Shaders.frameTimeCounter %= 3600.0f;
        Shaders.pointOfViewChanged = (Shaders.pointOfView != Shaders.mc.m.au());
        Shaders.pointOfView = Shaders.mc.m.au();
        ShadersRender.updateActiveRenderInfo(activeRenderInfo, minecraft, partialTicks);
        final few world = Shaders.mc.s;
        if (world != null) {
            Shaders.worldTime = world.W();
            Shaders.diffWorldTime = (Shaders.worldTime - Shaders.lastWorldTime) % 24000L;
            if (Shaders.diffWorldTime < 0L) {
                Shaders.diffWorldTime += 24000L;
            }
            Shaders.lastWorldTime = Shaders.worldTime;
            Shaders.moonPhase = world.ao();
            Shaders.rainStrength = world.d(partialTicks);
            final float fadeScalarRain = Shaders.diffSystemTime * 0.01f;
            final float tempRain = (float)Math.exp(Math.log(0.5) * fadeScalarRain / ((Shaders.wetness < Shaders.rainStrength) ? Shaders.drynessHalfLife : Shaders.wetnessHalfLife));
            Shaders.wetness = Shaders.wetness * tempRain + Shaders.rainStrength * (1.0f - tempRain);
            Shaders.bossBattle = getBossBattle();
            final bfj renderViewEntity = activeRenderInfo.g();
            if (renderViewEntity != null) {
                Shaders.isSleeping = (renderViewEntity instanceof bfz && ((bfz)renderViewEntity).fy());
                Shaders.eyePosY = (float)activeRenderInfo.b().b();
                Shaders.eyeBrightness = Shaders.mc.an().a(renderViewEntity, partialTicks);
                final float fadeScalarBrightness = Shaders.diffSystemTime * 0.01f;
                final float tempBrightness = (float)Math.exp(Math.log(0.5) * fadeScalarBrightness / Shaders.eyeBrightnessHalflife);
                Shaders.eyeBrightnessFadeX = Shaders.eyeBrightnessFadeX * tempBrightness + (Shaders.eyeBrightness & 0xFFFF) * (1.0f - tempBrightness);
                Shaders.eyeBrightnessFadeY = Shaders.eyeBrightnessFadeY * tempBrightness + (Shaders.eyeBrightness >> 16) * (1.0f - tempBrightness);
                final dxg cameraFogType = activeRenderInfo.k();
                if (cameraFogType == dxg.b) {
                    Shaders.isEyeInWater = 1;
                }
                else if (cameraFogType == dxg.a) {
                    Shaders.isEyeInWater = 2;
                }
                else if (cameraFogType == dxg.c) {
                    Shaders.isEyeInWater = 3;
                }
                else {
                    Shaders.isEyeInWater = 0;
                }
                if (renderViewEntity instanceof bfz) {
                    final bfz player = (bfz)renderViewEntity;
                    Shaders.nightVision = 0.0f;
                    if (player.a(bfc.p)) {
                        final fjq entityRenderer = Shaders.entityRenderer;
                        Shaders.nightVision = fjq.a(player, partialTicks);
                    }
                    Shaders.blindness = 0.0f;
                    if (player.a(bfc.o)) {
                        final int blindnessTicks = player.b(bfc.o).d();
                        Shaders.blindness = Config.limit(blindnessTicks / 20.0f, 0.0f, 1.0f);
                    }
                }
                eei skyColorV = world.a(activeRenderInfo.b(), partialTicks);
                skyColorV = CustomColors.getWorldSkyColor(skyColorV, (cmm)world, renderViewEntity, partialTicks);
                Shaders.skyColorR = (float)skyColorV.c;
                Shaders.skyColorG = (float)skyColorV.d;
                Shaders.skyColorB = (float)skyColorV.e;
            }
        }
        Shaders.isRenderingWorld = true;
        Shaders.isCompositeRendered = false;
        Shaders.isShadowPass = false;
        Shaders.isHandRenderedMain = false;
        Shaders.isHandRenderedOff = false;
        Shaders.skipRenderHandMain = false;
        Shaders.skipRenderHandOff = false;
        Shaders.dfb.setColorBuffersFiltering(9729, 9729);
        bindGbuffersTextures();
        Shaders.dfb.bindColorImages(true);
        if (Shaders.sfb != null) {
            Shaders.sfb.bindColorImages(true);
        }
        Shaders.previousCameraPositionX = Shaders.cameraPositionX;
        Shaders.previousCameraPositionY = Shaders.cameraPositionY;
        Shaders.previousCameraPositionZ = Shaders.cameraPositionZ;
        Shaders.previousProjection.position(0);
        Shaders.projection.position(0);
        Shaders.previousProjection.put(Shaders.projection);
        Shaders.previousProjection.position(0);
        Shaders.projection.position(0);
        Shaders.previousModelView.position(0);
        Shaders.modelView.position(0);
        Shaders.previousModelView.put(Shaders.modelView);
        Shaders.previousModelView.position(0);
        Shaders.modelView.position(0);
        Shaders.lastModelView.identity();
        Shaders.lastProjection.identity();
        checkGLError("beginRender");
        GlStateManager.enableAlphaTest();
        GlStateManager.alphaFunc(516, 0.1f);
        setDefaultAttribColor();
        setDefaultAttribLightmap();
        setDefaultAttribNormal();
        ShadersRender.renderShadowMap(Shaders.entityRenderer, activeRenderInfo, 0, partialTicks, finishTimeNano);
        Shaders.mc.aG().c();
        Shaders.dfb.setColorTextures(true);
        setRenderStage(RenderStage.NONE);
        checkGLError("end beginRender");
    }
    
    private static void bindGbuffersTextures() {
        bindTextures(4, Shaders.customTexturesGbuffers);
    }
    
    private static void bindTextures(final int startColorBuffer, final ICustomTexture[] customTextures) {
        if (Shaders.sfb != null) {
            Shaders.sfb.bindColorTextures(0);
            Shaders.sfb.bindDepthTextures(Shaders.shadowDepthTextureImageUnit);
        }
        Shaders.dfb.bindColorTextures(startColorBuffer);
        Shaders.dfb.bindDepthTextures(Shaders.depthTextureImageUnit);
        if (Shaders.noiseTextureEnabled) {
            GlStateManager._activeTexture(33984 + Shaders.noiseTexture.getTextureUnit());
            GlStateManager._bindTexture(Shaders.noiseTexture.getTextureId());
            GlStateManager._activeTexture(33984);
        }
        bindCustomTextures(customTextures);
    }
    
    public static void checkWorldChanged(final few world) {
        if (Shaders.currentWorld == world) {
            return;
        }
        final cmm oldWorld = (cmm)Shaders.currentWorld;
        Shaders.currentWorld = world;
        if (Shaders.currentWorld == null) {
            Shaders.cameraPositionX = 0.0;
            Shaders.cameraPositionY = 0.0;
            Shaders.cameraPositionZ = 0.0;
            Shaders.previousCameraPositionX = 0.0;
            Shaders.previousCameraPositionY = 0.0;
            Shaders.previousCameraPositionZ = 0.0;
        }
        setCameraOffset(Shaders.mc.al());
        final int dimIdOld = WorldUtils.getDimensionId(oldWorld);
        final int dimIdNew = WorldUtils.getDimensionId((cmm)world);
        if (dimIdNew != dimIdOld) {
            final boolean dimShadersOld = Shaders.shaderPackDimensions.contains(dimIdOld);
            final boolean dimShadersNew = Shaders.shaderPackDimensions.contains(dimIdNew);
            if (dimShadersOld || dimShadersNew) {
                uninit();
            }
        }
        Smoother.resetValues();
    }
    
    public static void beginRenderPass(final float partialTicks, final long finishTimeNano) {
        if (Shaders.isShadowPass) {
            return;
        }
        Shaders.dfb.bindFramebuffer();
        GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
        GlState.setDrawBuffers(null);
        ShadersTex.bindNSTextures(Shaders.defaultTexture.getMultiTexID());
        useProgram(Shaders.ProgramTextured);
        checkGLError("end beginRenderPass");
    }
    
    public static void setViewport(final int vx, final int vy, final int vw, final int vh) {
        GlStateManager._colorMask(true, true, true, true);
        if (Shaders.isShadowPass) {
            GL11.glViewport(0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
        }
        else {
            GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
            Shaders.dfb.bindFramebuffer();
            Shaders.isRenderingDfb = true;
            GlStateManager._enableCull();
            GlStateManager._enableDepthTest();
            GlState.setDrawBuffers(Shaders.drawBuffersNone);
            useProgram(Shaders.ProgramTextured);
            checkGLError("beginRenderPass");
        }
    }
    
    public static void setFogMode(final int value) {
        Shaders.fogMode = value;
        if (Shaders.fogEnabled && Shaders.fogAllowed) {
            setProgramUniform1i(Shaders.uniform_fogMode, value);
        }
    }
    
    public static void setFogShape(final int value) {
        Shaders.fogShape = value;
        if (Shaders.fogEnabled && Shaders.fogAllowed) {
            setProgramUniform1i(Shaders.uniform_fogShape, value);
        }
    }
    
    public static void setFogColor(final float r, final float g, final float b) {
        Shaders.fogColorR = r;
        Shaders.fogColorG = g;
        Shaders.fogColorB = b;
        setProgramUniform3f(Shaders.uniform_fogColor, Shaders.fogColorR, Shaders.fogColorG, Shaders.fogColorB);
    }
    
    public static void setClearColor(final float red, final float green, final float blue, final float alpha) {
        Shaders.clearColor.set(red, green, blue, 1.0f);
    }
    
    public static void clearRenderBuffer() {
        if (Shaders.isShadowPass) {
            checkGLError("shadow clear pre");
            Shaders.sfb.clearDepthBuffer(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
            checkGLError("shadow clear");
            return;
        }
        checkGLError("clear pre");
        final Vector4f[] clearColors = new Vector4f[Shaders.usedColorBuffers];
        for (int i = 0; i < clearColors.length; ++i) {
            clearColors[i] = getBufferClearColor(i);
        }
        Shaders.dfb.clearColorBuffers(Shaders.gbuffersClear, clearColors);
        Shaders.dfb.setDrawBuffers();
        checkFramebufferStatus("clear");
        checkGLError("clear");
    }
    
    public static void renderPrepare() {
        if (Shaders.hasPreparePrograms) {
            renderPrepareComposites();
            bindGbuffersTextures();
            Shaders.dfb.setDrawBuffers();
            Shaders.dfb.setColorTextures(true);
        }
    }
    
    private static Vector4f getBufferClearColor(final int buffer) {
        final Vector4f col = Shaders.gbuffersClearColor[buffer];
        if (col != null) {
            return col;
        }
        if (buffer == 0) {
            return Shaders.clearColor;
        }
        if (buffer == 1) {
            return Shaders.CLEAR_COLOR_1;
        }
        return Shaders.CLEAR_COLOR_0;
    }
    
    public static void setCamera(final eij matrixStackIn, final emz activeRenderInfo, final float partialTicks) {
        final bfj viewEntity = activeRenderInfo.g();
        final eei cameraPos = activeRenderInfo.b();
        final double x = cameraPos.c;
        final double y = cameraPos.d;
        final double z = cameraPos.e;
        updateCameraOffset(viewEntity);
        Shaders.cameraPositionX = x - Shaders.cameraOffsetX;
        Shaders.cameraPositionY = y;
        Shaders.cameraPositionZ = z - Shaders.cameraOffsetZ;
        updateProjectionMatrix();
        setModelView(matrixStackIn.c().a());
        checkGLError("setCamera");
    }
    
    public static void updateProjectionMatrix() {
        setProjection(RenderSystem.getProjectionMatrix());
    }
    
    private static void updateShadowProjectionMatrix() {
        MathUtils.write(RenderSystem.getProjectionMatrix(), Shaders.shadowProjection);
        SMath.invertMat4FBFA(Shaders.shadowProjectionInverse.position(0), Shaders.shadowProjection.position(0), Shaders.faShadowProjectionInverse, Shaders.faShadowProjection);
        Shaders.shadowProjection.position(0);
        Shaders.shadowProjectionInverse.position(0);
    }
    
    private static void updateCameraOffset(final bfj viewEntity) {
        final double adx = Math.abs(Shaders.cameraPositionX - Shaders.previousCameraPositionX);
        final double adz = Math.abs(Shaders.cameraPositionZ - Shaders.previousCameraPositionZ);
        final double apx = Math.abs(Shaders.cameraPositionX);
        final double apz = Math.abs(Shaders.cameraPositionZ);
        if (adx > 1000.0 || adz > 1000.0 || apx > 1000000.0 || apz > 1000000.0) {
            setCameraOffset(viewEntity);
        }
    }
    
    private static void setCameraOffset(final bfj viewEntity) {
        if (viewEntity == null) {
            Shaders.cameraOffsetX = 0;
            Shaders.cameraOffsetZ = 0;
            return;
        }
        Shaders.cameraOffsetX = (int)viewEntity.dn() / 1000 * 1000;
        Shaders.cameraOffsetZ = (int)viewEntity.dt() / 1000 * 1000;
    }
    
    public static void setCameraShadow(final eij matrixStack, final emz activeRenderInfo, final float partialTicks) {
        final bfj viewEntity = activeRenderInfo.g();
        final eei cameraPos = activeRenderInfo.b();
        final double x = cameraPos.c;
        final double y = cameraPos.d;
        final double z = cameraPos.e;
        updateCameraOffset(viewEntity);
        Shaders.cameraPositionX = x - Shaders.cameraOffsetX;
        Shaders.cameraPositionY = y;
        Shaders.cameraPositionZ = z - Shaders.cameraOffsetZ;
        GlStateManager._viewport(0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
        if (Shaders.shadowMapIsOrtho) {
            final Matrix4f projectionMatrix = MathUtils.makeOrtho4f(-Shaders.shadowMapHalfPlane, Shaders.shadowMapHalfPlane, Shaders.shadowMapHalfPlane, -Shaders.shadowMapHalfPlane, 0.05f, 256.0f);
            RenderSystem.setProjectionMatrix(projectionMatrix, eir.b);
        }
        else {
            final Matrix4f projectionMatrix = new Matrix4f().perspective(Shaders.shadowMapFOV, Shaders.shadowMapWidth / (float)Shaders.shadowMapHeight, 0.05f, 256.0f);
            RenderSystem.setProjectionMatrix(projectionMatrix, eir.a);
        }
        matrixStack.a(0.0f, 0.0f, -100.0f);
        matrixStack.a(a.b.rotationDegrees(90.0f));
        Shaders.celestialAngle = Shaders.mc.s.f(partialTicks);
        Shaders.sunAngle = ((Shaders.celestialAngle < 0.75f) ? (Shaders.celestialAngle + 0.25f) : (Shaders.celestialAngle - 0.75f));
        final float angle = Shaders.celestialAngle * -360.0f;
        final float angleInterval = (Shaders.shadowAngleInterval > 0.0f) ? (angle % Shaders.shadowAngleInterval - Shaders.shadowAngleInterval * 0.5f) : 0.0f;
        if (Shaders.sunAngle <= 0.5) {
            matrixStack.a(a.f.rotationDegrees(angle - angleInterval));
            matrixStack.a(a.b.rotationDegrees(Shaders.sunPathRotation));
            Shaders.shadowAngle = Shaders.sunAngle;
        }
        else {
            matrixStack.a(a.f.rotationDegrees(angle + 180.0f - angleInterval));
            matrixStack.a(a.b.rotationDegrees(Shaders.sunPathRotation));
            Shaders.shadowAngle = Shaders.sunAngle - 0.5f;
        }
        if (Shaders.shadowMapIsOrtho) {
            final float trans = Shaders.shadowIntervalSize;
            final float trans2 = trans / 2.0f;
            matrixStack.a((float)x % trans - trans2, (float)y % trans - trans2, (float)z % trans - trans2);
        }
        final float raSun = Shaders.sunAngle * 6.2831855f;
        final float x2 = (float)Math.cos(raSun);
        final float y2 = (float)Math.sin(raSun);
        final float raTilt = Shaders.sunPathRotation * 6.2831855f;
        float x3 = x2;
        float y3 = y2 * (float)Math.cos(raTilt);
        float z2 = y2 * (float)Math.sin(raTilt);
        if (Shaders.sunAngle > 0.5) {
            x3 = -x3;
            y3 = -y3;
            z2 = -z2;
        }
        Shaders.shadowLightPositionVector[0] = x3;
        Shaders.shadowLightPositionVector[1] = y3;
        Shaders.shadowLightPositionVector[2] = z2;
        Shaders.shadowLightPositionVector[3] = 0.0f;
        updateShadowProjectionMatrix();
        final Matrix4f shadowModelViewMat4 = matrixStack.c().a();
        MathUtils.write(shadowModelViewMat4, Shaders.shadowModelView.position(0));
        SMath.invertMat4FBFA(Shaders.shadowModelViewInverse.position(0), Shaders.shadowModelView.position(0), Shaders.faShadowModelViewInverse, Shaders.faShadowModelView);
        Shaders.shadowModelView.position(0);
        Shaders.shadowModelViewInverse.position(0);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferProjection, false, Shaders.projection);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferProjectionInverse, false, Shaders.projectionInverse);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferPreviousProjection, false, Shaders.previousProjection);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferModelView, false, Shaders.modelView);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferModelViewInverse, false, Shaders.modelViewInverse);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferPreviousModelView, false, Shaders.previousModelView);
        setProgramUniformMatrix4ARB(Shaders.uniform_shadowProjection, false, Shaders.shadowProjection);
        setProgramUniformMatrix4ARB(Shaders.uniform_shadowProjectionInverse, false, Shaders.shadowProjectionInverse);
        setProgramUniformMatrix4ARB(Shaders.uniform_shadowModelView, false, Shaders.shadowModelView);
        setProgramUniformMatrix4ARB(Shaders.uniform_shadowModelViewInverse, false, Shaders.shadowModelViewInverse);
        checkGLError("setCamera");
    }
    
    public static void preCelestialRotate(final eij matrixStackIn) {
        matrixStackIn.a(a.f.rotationDegrees(Shaders.sunPathRotation * 1.0f));
        checkGLError("preCelestialRotate");
    }
    
    public static void postCelestialRotate(final eij matrixStackIn) {
        final Matrix4f modelViewMat4 = matrixStackIn.c().a();
        final Matrix4f modelViewMat4T = new Matrix4f((Matrix4fc)modelViewMat4);
        modelViewMat4T.transpose();
        MathUtils.write(modelViewMat4T, Shaders.tempMat);
        SMath.multiplyMat4xVec4(Shaders.sunPosition, Shaders.tempMat, Shaders.sunPosModelView);
        SMath.multiplyMat4xVec4(Shaders.moonPosition, Shaders.tempMat, Shaders.moonPosModelView);
        System.arraycopy((Shaders.shadowAngle == Shaders.sunAngle) ? Shaders.sunPosition : Shaders.moonPosition, 0, Shaders.shadowLightPosition, 0, 3);
        setProgramUniform3f(Shaders.uniform_sunPosition, Shaders.sunPosition[0], Shaders.sunPosition[1], Shaders.sunPosition[2]);
        setProgramUniform3f(Shaders.uniform_moonPosition, Shaders.moonPosition[0], Shaders.moonPosition[1], Shaders.moonPosition[2]);
        setProgramUniform3f(Shaders.uniform_shadowLightPosition, Shaders.shadowLightPosition[0], Shaders.shadowLightPosition[1], Shaders.shadowLightPosition[2]);
        if (Shaders.customUniforms != null) {
            Shaders.customUniforms.update();
        }
        checkGLError("postCelestialRotate");
    }
    
    public static void setUpPosition(final eij matrixStackIn) {
        final Matrix4f modelViewMat4 = matrixStackIn.c().a();
        final Matrix4f modelViewMat4T = new Matrix4f((Matrix4fc)modelViewMat4);
        modelViewMat4T.transpose();
        MathUtils.write(modelViewMat4T, Shaders.tempMat);
        SMath.multiplyMat4xVec4(Shaders.upPosition, Shaders.tempMat, Shaders.upPosModelView);
        setProgramUniform3f(Shaders.uniform_upPosition, Shaders.upPosition[0], Shaders.upPosition[1], Shaders.upPosition[2]);
        if (Shaders.customUniforms != null) {
            Shaders.customUniforms.update();
        }
    }
    
    public static void drawComposite() {
        final Matrix4f mv = Shaders.MATRIX_IDENTITY;
        final Matrix4f mp = MathUtils.makeOrtho4f(0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f);
        final Matrix4f mt = Shaders.MATRIX_IDENTITY;
        setModelViewMatrix(mv);
        setProjectionMatrix(mp);
        setTextureMatrix(mt);
        drawCompositeQuad();
        final int countInstances = Shaders.activeProgram.getCountInstances();
        if (countInstances > 1) {
            for (int i = 1; i < countInstances; ++i) {
                Shaders.uniform_instanceId.setValue(i);
                drawCompositeQuad();
            }
            Shaders.uniform_instanceId.setValue(0);
        }
    }
    
    private static void drawCompositeQuad() {
        final eil tesselator = RenderSystem.renderThreadTesselator();
        final eie bufferbuilder = tesselator.c();
        bufferbuilder.a(eio.b.h, eih.q);
        bufferbuilder.a(0.0, 0.0, 0.0).a(0.0f, 0.0f).e();
        bufferbuilder.a(1.0, 0.0, 0.0).a(1.0f, 0.0f).e();
        bufferbuilder.a(1.0, 1.0, 0.0).a(1.0f, 1.0f).e();
        bufferbuilder.a(0.0, 1.0, 0.0).a(0.0f, 1.0f).e();
        eif.b(bufferbuilder.d());
    }
    
    public static void renderDeferred() {
        if (Shaders.isShadowPass) {
            return;
        }
        boolean buffersChanged = checkBufferFlip(Shaders.dfb, Shaders.ProgramDeferredPre);
        if (Shaders.hasDeferredPrograms) {
            checkGLError("pre-render Deferred");
            renderDeferredComposites();
            buffersChanged = true;
        }
        if (buffersChanged) {
            bindGbuffersTextures();
            Shaders.dfb.setColorTextures(true);
            final DrawBuffers drawBuffersWater = (Shaders.ProgramWater.getDrawBuffers() != null) ? Shaders.ProgramWater.getDrawBuffers() : Shaders.dfb.getDrawBuffers();
            GlState.setDrawBuffers(drawBuffersWater);
            GlStateManager._activeTexture(33984);
            Shaders.mc.X().a(fuu.e);
        }
    }
    
    public static void renderCompositeFinal() {
        if (Shaders.isShadowPass) {
            return;
        }
        checkBufferFlip(Shaders.dfb, Shaders.ProgramCompositePre);
        checkGLError("pre-render CompositeFinal");
        renderComposites();
    }
    
    private static boolean checkBufferFlip(final ShadersFramebuffer framebuffer, final Program program) {
        boolean flipped = false;
        final Boolean[] buffersFlip = program.getBuffersFlip();
        for (int i = 0; i < Shaders.usedColorBuffers; ++i) {
            if (Config.isTrue(buffersFlip[i])) {
                framebuffer.flipColorTexture(i);
                flipped = true;
            }
        }
        return flipped;
    }
    
    private static void renderComposites() {
        if (Shaders.isShadowPass) {
            return;
        }
        renderComposites(Shaders.ProgramsComposite, true, Shaders.customTexturesComposite);
    }
    
    private static void renderDeferredComposites() {
        if (Shaders.isShadowPass) {
            return;
        }
        renderComposites(Shaders.ProgramsDeferred, false, Shaders.customTexturesDeferred);
    }
    
    public static void renderPrepareComposites() {
        renderComposites(Shaders.ProgramsPrepare, false, Shaders.customTexturesPrepare);
    }
    
    private static void renderComposites(final Program[] ps, final boolean renderFinal, final ICustomTexture[] customTextures) {
        renderComposites(Shaders.dfb, ps, renderFinal, customTextures);
    }
    
    public static void renderShadowComposites() {
        renderComposites(Shaders.sfb, Shaders.ProgramsShadowcomp, false, Shaders.customTexturesShadowcomp);
    }
    
    private static void renderComposites(final ShadersFramebuffer framebuffer, final Program[] ps, final boolean renderFinal, final ICustomTexture[] customTextures) {
        GlStateManager.enableTexture();
        GlStateManager.disableAlphaTest();
        GlStateManager._disableBlend();
        GlStateManager._enableDepthTest();
        GlStateManager._depthFunc(519);
        GlStateManager._depthMask(false);
        bindTextures(0, customTextures);
        framebuffer.bindColorImages(true);
        framebuffer.setColorTextures(false);
        framebuffer.setDepthTexture();
        framebuffer.setDrawBuffers();
        checkGLError("pre-composite");
        for (int i = 0; i < ps.length; ++i) {
            final Program program = ps[i];
            dispatchComputes(framebuffer, program.getComputePrograms());
            if (program.getId() != 0) {
                useProgram(program);
                checkGLError(program.getName());
                if (program.hasCompositeMipmaps()) {
                    framebuffer.genCompositeMipmap(program.getCompositeMipmapSetting());
                }
                preDrawComposite(framebuffer, program);
                drawComposite();
                postDrawComposite(framebuffer, program);
                framebuffer.flipColorTextures(program.getToggleColorTextures());
            }
        }
        checkGLError("composite");
        if (renderFinal) {
            renderFinal();
            Shaders.isCompositeRendered = true;
        }
        GlStateManager.enableTexture();
        GlStateManager.enableAlphaTest();
        GlStateManager._enableBlend();
        GlStateManager._depthFunc(515);
        GlStateManager._depthMask(true);
        useProgram(Shaders.ProgramNone);
    }
    
    private static void preDrawComposite(final ShadersFramebuffer framebuffer, final Program program) {
        int drawWidth = framebuffer.getWidth();
        int drawHeight = framebuffer.getHeight();
        if (program.getDrawSize() != null) {
            final Dimension dim = program.getDrawSize().getDimension(drawWidth, drawHeight);
            drawWidth = dim.width;
            drawHeight = dim.height;
            final FixedFramebuffer ff = framebuffer.getFixedFramebuffer(drawWidth, drawHeight, program.getDrawBuffers(), false);
            ff.bindFramebuffer();
            GL43.glViewport(0, 0, drawWidth, drawHeight);
        }
        final RenderScale rs = program.getRenderScale();
        if (rs != null) {
            final int x = (int)(drawWidth * rs.getOffsetX());
            final int y = (int)(drawHeight * rs.getOffsetY());
            final int w = (int)(drawWidth * rs.getScale());
            final int h = (int)(drawHeight * rs.getScale());
            GL43.glViewport(x, y, w, h);
        }
    }
    
    private static void postDrawComposite(final ShadersFramebuffer framebuffer, final Program program) {
        if (program.getDrawSize() != null) {
            framebuffer.bindFramebuffer();
            GL43.glViewport(0, 0, framebuffer.getWidth(), framebuffer.getHeight());
        }
        final RenderScale rs = Shaders.activeProgram.getRenderScale();
        if (rs != null) {
            GL43.glViewport(0, 0, framebuffer.getWidth(), framebuffer.getHeight());
        }
    }
    
    public static void dispatchComputes(final ShadersFramebuffer framebuffer, final ComputeProgram[] cps) {
        for (int i = 0; i < cps.length; ++i) {
            final ComputeProgram cp = cps[i];
            dispatchCompute(cp);
            if (cp.hasCompositeMipmaps()) {
                framebuffer.genCompositeMipmap(cp.getCompositeMipmapSetting());
            }
        }
    }
    
    public static void useComputeProgram(final ComputeProgram cp) {
        GlStateManager._glUseProgram(cp.getId());
        if (checkGLError("useComputeProgram") != 0) {
            cp.setId(0);
            return;
        }
        Shaders.shaderUniforms.setProgram(cp.getId());
        if (Shaders.customUniforms != null) {
            Shaders.customUniforms.setProgram(cp.getId());
        }
        setProgramUniforms(cp.getProgramStage());
        setImageUniforms();
        if (Shaders.dfb == null) {
            return;
        }
        Shaders.dfb.bindColorImages(true);
    }
    
    public static void dispatchCompute(final ComputeProgram cp) {
        if (Shaders.dfb == null) {
            return;
        }
        useComputeProgram(cp);
        hz workGroups = cp.getWorkGroups();
        if (workGroups == null) {
            eeh workGroupsRender = cp.getWorkGroupsRender();
            if (workGroupsRender == null) {
                workGroupsRender = new eeh(1.0f, 1.0f);
            }
            final int computeWidth = (int)Math.ceil(Shaders.renderWidth * workGroupsRender.i);
            final int computeHeight = (int)Math.ceil(Shaders.renderHeight * workGroupsRender.j);
            final hz localSize = cp.getLocalSize();
            final int groupsX = (int)Math.ceil(1.0 * computeWidth / localSize.u());
            final int groupsY = (int)Math.ceil(1.0 * computeHeight / localSize.v());
            workGroups = new hz(groupsX, groupsY, 1);
        }
        GL43.glMemoryBarrier(40);
        GL43.glDispatchCompute(workGroups.u(), workGroups.v(), workGroups.w());
        GL43.glMemoryBarrier(40);
        checkGLError("compute");
    }
    
    private static void renderFinal() {
        dispatchComputes(Shaders.dfb, Shaders.ProgramFinal.getComputePrograms());
        Shaders.isRenderingDfb = false;
        Shaders.mc.f().a(true);
        GlStateManager._glFramebufferTexture2D(GLConst.GL_FRAMEBUFFER, GLConst.GL_COLOR_ATTACHMENT0, 3553, Shaders.mc.f().f(), 0);
        GL43.glViewport(0, 0, Shaders.mc.aM().k(), Shaders.mc.aM().l());
        GlStateManager._depthMask(true);
        GL43.glClearColor(Shaders.clearColor.x(), Shaders.clearColor.y(), Shaders.clearColor.z(), 1.0f);
        GL43.glClear(16640);
        GlStateManager.enableTexture();
        GlStateManager.disableAlphaTest();
        GlStateManager._disableBlend();
        GlStateManager._enableDepthTest();
        GlStateManager._depthFunc(519);
        GlStateManager._depthMask(false);
        checkGLError("pre-final");
        useProgram(Shaders.ProgramFinal);
        checkGLError("final");
        if (Shaders.ProgramFinal.hasCompositeMipmaps()) {
            Shaders.dfb.genCompositeMipmap(Shaders.ProgramFinal.getCompositeMipmapSetting());
        }
        drawComposite();
        checkGLError("renderCompositeFinal");
    }
    
    public static void endRender() {
        if (Shaders.isShadowPass) {
            checkGLError("shadow endRender");
            return;
        }
        if (!Shaders.isCompositeRendered) {
            renderCompositeFinal();
        }
        Shaders.isRenderingWorld = false;
        GlStateManager._colorMask(true, true, true, true);
        useProgram(Shaders.ProgramNone);
        setRenderStage(RenderStage.NONE);
        checkGLError("endRender end");
    }
    
    public static void beginSky() {
        Shaders.isRenderingSky = true;
        Shaders.fogEnabled = true;
        useProgram(Shaders.ProgramSkyTextured);
        pushEntity(-2, 0);
        setRenderStage(RenderStage.SKY);
    }
    
    public static void setSkyColor(final eei v3color) {
        Shaders.skyColorR = (float)v3color.c;
        Shaders.skyColorG = (float)v3color.d;
        Shaders.skyColorB = (float)v3color.e;
        setProgramUniform3f(Shaders.uniform_skyColor, Shaders.skyColorR, Shaders.skyColorG, Shaders.skyColorB);
    }
    
    public static void drawHorizon(final eij matrixStackIn) {
        final eie tess = eil.a().c();
        final double top = 16.0;
        double bot = -Shaders.cameraPositionY + Shaders.currentWorld.k().a((cmo)Shaders.currentWorld) + 12.0 - 16.0;
        if (Shaders.cameraPositionY < Shaders.currentWorld.k().a((cmo)Shaders.currentWorld)) {
            bot = -4.0;
        }
        final Matrix4f matrix = matrixStackIn.c().a();
        tess.a(eio.b.h, eih.m);
        final int dist = 512;
        for (int i = 0; i < 8; ++i) {
            final double x = dist * apa.b(i * 0.7853982f);
            final double z = dist * apa.a(i * 0.7853982f);
            final int iN = i + 1;
            final double xN = dist * apa.b(iN * 0.7853982f);
            final double zN = dist * apa.a(iN * 0.7853982f);
            addVertex(tess, matrix, x, bot, z);
            addVertex(tess, matrix, xN, bot, zN);
            addVertex(tess, matrix, xN, top, zN);
            addVertex(tess, matrix, x, top, z);
            addVertex(tess, matrix, 0.0, bot, 0.0);
            addVertex(tess, matrix, 0.0, bot, 0.0);
            addVertex(tess, matrix, xN, bot, zN);
            addVertex(tess, matrix, x, bot, z);
        }
        eil.a().b();
    }
    
    private static void addVertex(final eie buffer, final Matrix4f matrix, final double x, final double y, final double z) {
        final float xt = MathUtils.getTransformX(matrix, (float)x, (float)y, (float)z, 1.0f);
        final float yt = MathUtils.getTransformY(matrix, (float)x, (float)y, (float)z, 1.0f);
        final float zt = MathUtils.getTransformZ(matrix, (float)x, (float)y, (float)z, 1.0f);
        buffer.a((double)xt, (double)yt, (double)zt).e();
    }
    
    public static void preSkyList(final eij matrixStackIn) {
        setUpPosition(matrixStackIn);
        RenderSystem.setShaderColor(Shaders.fogColorR, Shaders.fogColorG, Shaders.fogColorB, 1.0f);
        drawHorizon(matrixStackIn);
        RenderSystem.setShaderColor(Shaders.skyColorR, Shaders.skyColorG, Shaders.skyColorB, 1.0f);
    }
    
    public static void endSky() {
        Shaders.isRenderingSky = false;
        useProgram(Shaders.lightmapEnabled ? Shaders.ProgramTexturedLit : Shaders.ProgramTextured);
        popEntity();
        setRenderStage(RenderStage.NONE);
    }
    
    public static void beginUpdateChunks() {
        checkGLError("beginUpdateChunks1");
        checkFramebufferStatus("beginUpdateChunks1");
        if (!Shaders.isShadowPass) {
            useProgram(Shaders.ProgramTerrain);
        }
        checkGLError("beginUpdateChunks2");
        checkFramebufferStatus("beginUpdateChunks2");
    }
    
    public static void endUpdateChunks() {
        checkGLError("endUpdateChunks1");
        checkFramebufferStatus("endUpdateChunks1");
        if (!Shaders.isShadowPass) {
            useProgram(Shaders.ProgramTerrain);
        }
        checkGLError("endUpdateChunks2");
        checkFramebufferStatus("endUpdateChunks2");
    }
    
    public static boolean shouldRenderClouds(final enr gs) {
        if (!Shaders.shaderPackLoaded) {
            return true;
        }
        checkGLError("shouldRenderClouds");
        return Shaders.isShadowPass ? Shaders.configCloudShadow : (gs.as() != enc.a);
    }
    
    public static void beginClouds() {
        Shaders.fogEnabled = true;
        pushEntity(-3, 0);
        useProgram(Shaders.ProgramClouds);
        setRenderStage(RenderStage.CLOUDS);
    }
    
    public static void endClouds() {
        disableFog();
        popEntity();
        useProgram(Shaders.lightmapEnabled ? Shaders.ProgramTexturedLit : Shaders.ProgramTextured);
        setRenderStage(RenderStage.NONE);
    }
    
    public static void beginEntities() {
        if (Shaders.isRenderingWorld) {
            useProgram(Shaders.ProgramEntities);
            setRenderStage(RenderStage.ENTITIES);
        }
    }
    
    public static void nextEntity(final bfj entity) {
        if (Shaders.isRenderingWorld) {
            if (Shaders.mc.b(entity)) {
                useProgram(Shaders.ProgramEntitiesGlowing);
            }
            else {
                useProgram(Shaders.ProgramEntities);
            }
            setEntityId(entity);
        }
    }
    
    public static void setEntityId(final bfj entity) {
        if (Shaders.uniform_entityId.isDefined()) {
            final int id = EntityUtils.getEntityIdByClass(entity);
            final int idAlias = EntityAliases.getEntityAliasId(id);
            Shaders.uniform_entityId.setValue(idAlias);
        }
    }
    
    public static void beginSpiderEyes() {
        if (Shaders.isRenderingWorld) {
            if (Shaders.ProgramSpiderEyes.getId() != Shaders.ProgramNone.getId()) {
                useProgram(Shaders.ProgramSpiderEyes);
                GlStateManager._blendFunc(770, 771);
            }
        }
    }
    
    public static void endSpiderEyes() {
        if (Shaders.isRenderingWorld) {
            if (Shaders.ProgramSpiderEyes.getId() != Shaders.ProgramNone.getId()) {
                useProgram(Shaders.ProgramEntities);
            }
        }
    }
    
    public static void endEntities() {
        if (Shaders.isRenderingWorld) {
            setEntityId((bfj)null);
            useProgram(Shaders.lightmapEnabled ? Shaders.ProgramTexturedLit : Shaders.ProgramTextured);
        }
    }
    
    public static void setEntityColor(final float r, final float g, final float b, final float a) {
        if (Shaders.isRenderingWorld && !Shaders.isShadowPass) {
            Shaders.uniform_entityColor.setValue(r, g, b, a);
        }
    }
    
    public static void beginLivingDamage() {
        if (Shaders.isRenderingWorld) {
            ShadersTex.bindTexture(Shaders.defaultTexture);
            if (!Shaders.isShadowPass) {
                GlState.setDrawBuffers(Shaders.drawBuffersColorAtt[0]);
            }
        }
    }
    
    public static void endLivingDamage() {
        if (Shaders.isRenderingWorld && !Shaders.isShadowPass) {
            GlState.setDrawBuffers(Shaders.ProgramEntities.getDrawBuffers());
        }
    }
    
    public static void beginBlockEntities() {
        if (Shaders.isRenderingWorld) {
            checkGLError("beginBlockEntities");
            useProgram(Shaders.ProgramBlock);
            setRenderStage(RenderStage.BLOCK_ENTITIES);
        }
    }
    
    public static void nextBlockEntity(final czn tileEntity) {
        if (Shaders.isRenderingWorld) {
            checkGLError("nextBlockEntity");
            useProgram(Shaders.ProgramBlock);
            setBlockEntityId(tileEntity);
        }
    }
    
    public static void setBlockEntityId(final czn tileEntity) {
        if (Shaders.uniform_blockEntityId.isDefined()) {
            final int blockId = getBlockEntityId(tileEntity);
            Shaders.uniform_blockEntityId.setValue(blockId);
        }
    }
    
    private static int getBlockEntityId(final czn tileEntity) {
        if (tileEntity == null) {
            return -1;
        }
        final dcb blockState = tileEntity.q();
        if (blockState == null) {
            return -1;
        }
        final int blockId = BlockAliases.getAliasBlockId(blockState);
        return blockId;
    }
    
    public static void endBlockEntities() {
        if (Shaders.isRenderingWorld) {
            checkGLError("endBlockEntities");
            setBlockEntityId((czn)null);
            useProgram(Shaders.lightmapEnabled ? Shaders.ProgramTexturedLit : Shaders.ProgramTextured);
            ShadersTex.bindNSTextures(Shaders.defaultTexture.getMultiTexID());
        }
    }
    
    public static void beginLitParticles() {
        useProgram(Shaders.ProgramTexturedLit);
    }
    
    public static void beginParticles() {
        useProgram(Shaders.ProgramTextured);
        setRenderStage(RenderStage.PARTICLES);
    }
    
    public static void endParticles() {
        useProgram(Shaders.ProgramTexturedLit);
        setRenderStage(RenderStage.NONE);
    }
    
    public static void readCenterDepth() {
        if (!Shaders.isShadowPass && Shaders.centerDepthSmoothEnabled) {
            Shaders.tempDirectFloatBuffer.clear();
            GL43.glReadPixels(Shaders.renderWidth / 2, Shaders.renderHeight / 2, 1, 1, 6402, 5126, Shaders.tempDirectFloatBuffer);
            Shaders.centerDepth = Shaders.tempDirectFloatBuffer.get(0);
            final float fadeScalar = Shaders.diffSystemTime * 0.01f;
            final float fadeFactor = (float)Math.exp(Math.log(0.5) * fadeScalar / Shaders.centerDepthSmoothHalflife);
            Shaders.centerDepthSmooth = Shaders.centerDepthSmooth * fadeFactor + Shaders.centerDepth * (1.0f - fadeFactor);
        }
    }
    
    public static void beginWeather() {
        if (!Shaders.isShadowPass) {
            GlStateManager._enableDepthTest();
            GlStateManager._enableBlend();
            GlStateManager._blendFunc(770, 771);
            GlStateManager.enableAlphaTest();
            useProgram(Shaders.ProgramWeather);
            setRenderStage(RenderStage.RAIN_SNOW);
        }
    }
    
    public static void endWeather() {
        GlStateManager._disableBlend();
        useProgram(Shaders.ProgramTexturedLit);
        setRenderStage(RenderStage.NONE);
    }
    
    public static void preRenderHand() {
        if (!Shaders.isShadowPass && Shaders.usedDepthBuffers >= 3) {
            GlStateManager._activeTexture(33996);
            GL43.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
            GlStateManager._activeTexture(33984);
        }
    }
    
    public static void preWater() {
        if (Shaders.usedDepthBuffers >= 2) {
            GlStateManager._activeTexture(33995);
            checkGLError("pre copy depth");
            GL43.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
            checkGLError("copy depth");
            GlStateManager._activeTexture(33984);
        }
        ShadersTex.bindNSTextures(Shaders.defaultTexture.getMultiTexID());
    }
    
    public static void beginWater() {
        if (Shaders.isRenderingWorld) {
            if (!Shaders.isShadowPass) {
                renderDeferred();
                useProgram(Shaders.ProgramWater);
                GlStateManager._enableBlend();
                GlStateManager._depthMask(true);
            }
            else {
                GlStateManager._depthMask(true);
            }
        }
    }
    
    public static void endWater() {
        if (Shaders.isRenderingWorld) {
            if (Shaders.isShadowPass) {}
            useProgram(Shaders.lightmapEnabled ? Shaders.ProgramTexturedLit : Shaders.ProgramTextured);
        }
    }
    
    public static void applyHandDepth(final eij matrixStackIn) {
        if (Shaders.configHandDepthMul != 1.0) {
            matrixStackIn.b(1.0f, 1.0f, Shaders.configHandDepthMul);
        }
    }
    
    public static void beginHand(final eij matrixStackIn, final boolean translucent) {
        matrixStackIn.a();
        if (translucent) {
            useProgram(Shaders.ProgramHandWater);
        }
        else {
            useProgram(Shaders.ProgramHand);
        }
        checkGLError("beginHand");
        checkFramebufferStatus("beginHand");
    }
    
    public static void endHand(final eij matrixStackIn) {
        checkGLError("pre endHand");
        checkFramebufferStatus("pre endHand");
        matrixStackIn.b();
        GlStateManager._blendFunc(770, 771);
        checkGLError("endHand");
    }
    
    public static void beginFPOverlay() {
        GlStateManager._disableBlend();
    }
    
    public static void endFPOverlay() {
    }
    
    public static void glEnableWrapper(final int cap) {
        GL43.glEnable(cap);
        if (cap == 3553) {
            enableTexture2D();
        }
        else if (cap == 2912) {
            enableFog();
        }
    }
    
    public static void glDisableWrapper(final int cap) {
        GL43.glDisable(cap);
        if (cap == 3553) {
            disableTexture2D();
        }
        else if (cap == 2912) {
            disableFog();
        }
    }
    
    public static void sglEnableT2D(final int cap) {
        GL43.glEnable(cap);
        enableTexture2D();
    }
    
    public static void sglDisableT2D(final int cap) {
        GL43.glDisable(cap);
        disableTexture2D();
    }
    
    public static void sglEnableFog(final int cap) {
        GL43.glEnable(cap);
        enableFog();
    }
    
    public static void sglDisableFog(final int cap) {
        GL43.glDisable(cap);
        disableFog();
    }
    
    public static void enableTexture2D() {
        if (Shaders.isRenderingSky) {
            useProgram(Shaders.ProgramSkyTextured);
        }
        else if (Shaders.activeProgram == Shaders.ProgramBasic) {
            useProgram(Shaders.lightmapEnabled ? Shaders.ProgramTexturedLit : Shaders.ProgramTextured);
        }
    }
    
    public static void disableTexture2D() {
        if (Shaders.isRenderingSky) {
            useProgram(Shaders.ProgramSkyBasic);
        }
        else if (Shaders.activeProgram == Shaders.ProgramTextured || Shaders.activeProgram == Shaders.ProgramTexturedLit) {
            useProgram(Shaders.ProgramBasic);
        }
    }
    
    public static void pushProgram() {
        Shaders.programStack.push(Shaders.activeProgram);
    }
    
    public static void pushUseProgram(final Program program) {
        pushProgram();
        useProgram(program);
    }
    
    public static void popProgram() {
        final Program program = Shaders.programStack.pop();
        useProgram(program);
    }
    
    public static void beginLeash() {
        pushProgram();
        useProgram(Shaders.ProgramBasic);
    }
    
    public static void endLeash() {
        popProgram();
    }
    
    public static void beginLines() {
        pushProgram();
        useProgram(Shaders.ProgramLine);
        setRenderStage(RenderStage.OUTLINE);
    }
    
    public static void endLines() {
        popProgram();
        setRenderStage(RenderStage.NONE);
    }
    
    public static void beginWaterMask() {
        GlStateManager.disableAlphaTest();
    }
    
    public static void endWaterMask() {
        GlStateManager.enableAlphaTest();
    }
    
    public static void enableFog() {
        Shaders.fogEnabled = true;
        if (Shaders.fogAllowed) {
            setProgramUniform1i(Shaders.uniform_fogMode, Shaders.fogMode);
            setProgramUniform1i(Shaders.uniform_fogShape, Shaders.fogShape);
            setProgramUniform1f(Shaders.uniform_fogDensity, Shaders.fogDensity);
            setProgramUniform1f(Shaders.uniform_fogStart, Shaders.fogStart);
            setProgramUniform1f(Shaders.uniform_fogEnd, Shaders.fogEnd);
            setProgramUniform3f(Shaders.uniform_fogColor, Shaders.fogColorR, Shaders.fogColorG, Shaders.fogColorB);
        }
        else {
            setProgramUniform1f(Shaders.uniform_fogDensity, 0.0f);
            setProgramUniform1f(Shaders.uniform_fogStart, 1.7014117E38f);
            setProgramUniform1f(Shaders.uniform_fogEnd, Float.MAX_VALUE);
        }
    }
    
    public static void disableFog() {
        Shaders.fogEnabled = false;
        setProgramUniform1i(Shaders.uniform_fogMode, 0);
        setProgramUniform1i(Shaders.uniform_fogShape, 0);
        setProgramUniform1f(Shaders.uniform_fogDensity, 0.0f);
        setProgramUniform1f(Shaders.uniform_fogStart, 1.7014117E38f);
        setProgramUniform1f(Shaders.uniform_fogEnd, Float.MAX_VALUE);
    }
    
    public static void setFogDensity(final float value) {
        Shaders.fogDensity = value;
        if (Shaders.fogEnabled && Shaders.fogAllowed) {
            setProgramUniform1f(Shaders.uniform_fogDensity, value);
        }
    }
    
    public static void setFogStart(final float value) {
        Shaders.fogStart = value;
        if (Shaders.fogEnabled && Shaders.fogAllowed) {
            setProgramUniform1f(Shaders.uniform_fogStart, value);
        }
    }
    
    public static void setFogEnd(final float value) {
        Shaders.fogEnd = value;
        if (Shaders.fogEnabled && Shaders.fogAllowed) {
            setProgramUniform1f(Shaders.uniform_fogEnd, value);
        }
    }
    
    public static void sglFogi(final int pname, final int param) {
        GL11.glFogi(pname, param);
        if (pname == 2917) {
            Shaders.fogMode = param;
            if (Shaders.fogEnabled && Shaders.fogAllowed) {
                setProgramUniform1i(Shaders.uniform_fogMode, Shaders.fogMode);
            }
        }
    }
    
    public static void enableLightmap() {
        Shaders.lightmapEnabled = true;
        if (Shaders.activeProgram == Shaders.ProgramTextured) {
            useProgram(Shaders.ProgramTexturedLit);
        }
    }
    
    public static void disableLightmap() {
        Shaders.lightmapEnabled = false;
        if (Shaders.activeProgram == Shaders.ProgramTexturedLit) {
            useProgram(Shaders.ProgramTextured);
        }
    }
    
    public static int getEntityData() {
        return Shaders.entityData[Shaders.entityDataIndex * 2];
    }
    
    public static int getEntityData2() {
        return Shaders.entityData[Shaders.entityDataIndex * 2 + 1];
    }
    
    public static int setEntityData1(final int data1) {
        Shaders.entityData[Shaders.entityDataIndex * 2] = ((Shaders.entityData[Shaders.entityDataIndex * 2] & 0xFFFF) | data1 << 16);
        return data1;
    }
    
    public static int setEntityData2(final int data2) {
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = ((Shaders.entityData[Shaders.entityDataIndex * 2 + 1] & 0xFFFF0000) | (data2 & 0xFFFF));
        return data2;
    }
    
    public static void pushEntity(final int data0, final int data1) {
        ++Shaders.entityDataIndex;
        Shaders.entityData[Shaders.entityDataIndex * 2] = ((data0 & 0xFFFF) | data1 << 16);
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
    }
    
    public static void pushEntity(final int data0) {
        ++Shaders.entityDataIndex;
        Shaders.entityData[Shaders.entityDataIndex * 2] = (data0 & 0xFFFF);
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
    }
    
    public static void pushEntity(final cpn block) {
        ++Shaders.entityDataIndex;
        final int blockRenderType = block.b_(block.n()).ordinal();
        Shaders.entityData[Shaders.entityDataIndex * 2] = ((jb.f.a((Object)block) & 0xFFFF) | blockRenderType << 16);
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
    }
    
    public static void popEntity() {
        Shaders.entityData[Shaders.entityDataIndex * 2] = 0;
        Shaders.entityData[Shaders.entityDataIndex * 2 + 1] = 0;
        --Shaders.entityDataIndex;
    }
    
    public static void mcProfilerEndSection() {
        Shaders.mc.aG().c();
    }
    
    public static String getShaderPackName() {
        if (Shaders.shaderPack == null) {
            return null;
        }
        if (Shaders.shaderPack instanceof ShaderPackNone) {
            return null;
        }
        return Shaders.shaderPack.getName();
    }
    
    public static InputStream getShaderPackResourceStream(final String path) {
        if (Shaders.shaderPack == null) {
            return null;
        }
        return Shaders.shaderPack.getResourceAsStream(path);
    }
    
    public static void nextAntialiasingLevel(final boolean forward) {
        if (forward) {
            Shaders.configAntialiasingLevel += 2;
            if (Shaders.configAntialiasingLevel > 4) {
                Shaders.configAntialiasingLevel = 0;
            }
        }
        else {
            Shaders.configAntialiasingLevel -= 2;
            if (Shaders.configAntialiasingLevel < 0) {
                Shaders.configAntialiasingLevel = 4;
            }
        }
        Shaders.configAntialiasingLevel = Shaders.configAntialiasingLevel / 2 * 2;
        Shaders.configAntialiasingLevel = Config.limit(Shaders.configAntialiasingLevel, 0, 4);
    }
    
    public static void checkShadersModInstalled() {
        try {
            Class.forName("shadersmod.transform.SMCClassTransformer");
        }
        catch (Throwable e) {
            return;
        }
        throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
    }
    
    public static void resourcesReloaded() {
        loadShaderPackResources();
        reloadCustomTexturesLocation(Shaders.customTexturesGbuffers);
        reloadCustomTexturesLocation(Shaders.customTexturesComposite);
        reloadCustomTexturesLocation(Shaders.customTexturesDeferred);
        reloadCustomTexturesLocation(Shaders.customTexturesShadowcomp);
        reloadCustomTexturesLocation(Shaders.customTexturesPrepare);
        if (Shaders.shaderPackLoaded) {
            BlockAliases.resourcesReloaded();
            ItemAliases.resourcesReloaded();
            EntityAliases.resourcesReloaded();
        }
    }
    
    private static void loadShaderPackResources() {
        Shaders.shaderPackResources = new HashMap<String, String>();
        if (!Shaders.shaderPackLoaded) {
            return;
        }
        final List<String> listFiles = new ArrayList<String>();
        final String PREFIX = "/shaders/lang/";
        final String EN_US = "en_us";
        final String SUFFIX = ".lang";
        listFiles.add(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, PREFIX, EN_US, SUFFIX));
        listFiles.add(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, PREFIX, getLocaleUppercase(EN_US), SUFFIX));
        if (!Config.getGameSettings().ag.equals(EN_US)) {
            final String language = Config.getGameSettings().ag;
            listFiles.add(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, PREFIX, language, SUFFIX));
            listFiles.add(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, PREFIX, getLocaleUppercase(language), SUFFIX));
        }
        try {
            for (final String file : listFiles) {
                final InputStream in = Shaders.shaderPack.getResourceAsStream(file);
                if (in == null) {
                    continue;
                }
                final Properties props = new PropertiesOrdered();
                Lang.loadLocaleData(in, props);
                in.close();
                final Set keys = props.keySet();
                for (final String key : keys) {
                    final String value = props.getProperty(key);
                    Shaders.shaderPackResources.put(key, value);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static String getLocaleUppercase(final String name) {
        final int pos = name.indexOf(95);
        if (pos < 0) {
            return name;
        }
        final String nameUp = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, name.substring(0, pos), name.substring(pos).toUpperCase(Locale.ROOT));
        return nameUp;
    }
    
    public static String translate(final String key, final String def) {
        final String str = Shaders.shaderPackResources.get(key);
        if (str == null) {
            return def;
        }
        return str;
    }
    
    public static boolean isProgramPath(String path) {
        if (path == null) {
            return false;
        }
        if (path.length() <= 0) {
            return false;
        }
        final int pos = path.lastIndexOf("/");
        if (pos >= 0) {
            path = path.substring(pos + 1);
        }
        final Program p = getProgram(path);
        return p != null;
    }
    
    public static Program getProgram(final String name) {
        return Shaders.programs.getProgram(name);
    }
    
    public static void setItemToRenderMain(final cfz itemToRenderMain) {
        Shaders.itemToRenderMainTranslucent = isTranslucentBlock(itemToRenderMain);
    }
    
    public static void setItemToRenderOff(final cfz itemToRenderOff) {
        Shaders.itemToRenderOffTranslucent = isTranslucentBlock(itemToRenderOff);
    }
    
    public static boolean isItemToRenderMainTranslucent() {
        return Shaders.itemToRenderMainTranslucent;
    }
    
    public static boolean isItemToRenderOffTranslucent() {
        return Shaders.itemToRenderOffTranslucent;
    }
    
    public static boolean isBothHandsRendered() {
        return Shaders.isHandRenderedMain && Shaders.isHandRenderedOff;
    }
    
    private static boolean isTranslucentBlock(final cfz stack) {
        if (stack == null) {
            return false;
        }
        final cfu item = stack.d();
        if (item == null) {
            return false;
        }
        if (!(item instanceof cds)) {
            return false;
        }
        final cds itemBlock = (cds)item;
        final cpn block = itemBlock.e();
        if (block == null) {
            return false;
        }
        final fkf blockRenderLayer = fjs.a(block.n());
        return blockRenderLayer == RenderTypes.TRANSLUCENT;
    }
    
    public static boolean isSkipRenderHand(final bdw hand) {
        return (hand == bdw.a && Shaders.skipRenderHandMain) || (hand == bdw.b && Shaders.skipRenderHandOff);
    }
    
    public static boolean isRenderBothHands() {
        return !Shaders.skipRenderHandMain && !Shaders.skipRenderHandOff;
    }
    
    public static void setSkipRenderHands(final boolean skipMain, final boolean skipOff) {
        Shaders.skipRenderHandMain = skipMain;
        Shaders.skipRenderHandOff = skipOff;
    }
    
    public static void setHandsRendered(final boolean handMain, final boolean handOff) {
        Shaders.isHandRenderedMain = handMain;
        Shaders.isHandRenderedOff = handOff;
    }
    
    public static boolean isHandRenderedMain() {
        return Shaders.isHandRenderedMain;
    }
    
    public static boolean isHandRenderedOff() {
        return Shaders.isHandRenderedOff;
    }
    
    public static float getShadowRenderDistance() {
        if (Shaders.shadowDistanceRenderMul < 0.0f) {
            return -1.0f;
        }
        return Shaders.shadowMapHalfPlane * Shaders.shadowDistanceRenderMul;
    }
    
    public static void beginRenderFirstPersonHand(final boolean translucent) {
        Shaders.isRenderingFirstPersonHand = true;
        if (translucent) {
            setRenderStage(RenderStage.HAND_TRANSLUCENT);
        }
        else {
            setRenderStage(RenderStage.HAND_SOLID);
        }
    }
    
    public static void endRenderFirstPersonHand() {
        Shaders.isRenderingFirstPersonHand = false;
        setRenderStage(RenderStage.NONE);
    }
    
    public static boolean isRenderingFirstPersonHand() {
        return Shaders.isRenderingFirstPersonHand;
    }
    
    public static void beginBeacon() {
        if (Shaders.isRenderingWorld) {
            useProgram(Shaders.ProgramBeaconBeam);
        }
    }
    
    public static void endBeacon() {
        if (Shaders.isRenderingWorld) {
            useProgram(Shaders.ProgramBlock);
        }
    }
    
    public static few getCurrentWorld() {
        return Shaders.currentWorld;
    }
    
    public static gu getWorldCameraPosition() {
        return gu.a(Shaders.cameraPositionX + Shaders.cameraOffsetX, Shaders.cameraPositionY, Shaders.cameraPositionZ + Shaders.cameraOffsetZ);
    }
    
    public static boolean isCustomUniforms() {
        return Shaders.customUniforms != null;
    }
    
    public static boolean canRenderQuads() {
        return !Shaders.hasGeometryShaders || Shaders.capabilities.GL_NV_geometry_shader4;
    }
    
    public static boolean isOverlayDisabled() {
        return Shaders.shaderPackLoaded;
    }
    
    public static boolean isRemapLightmap() {
        return Shaders.shaderPackLoaded;
    }
    
    public static boolean isEffectsModelView() {
        return Shaders.shaderPackLoaded;
    }
    
    public static void flushRenderBuffers() {
        RenderUtils.flushRenderBuffers();
    }
    
    public static void setRenderStage(final RenderStage stage) {
        if (!Shaders.shaderPackLoaded) {
            return;
        }
        Shaders.renderStage = stage;
        Shaders.uniform_renderStage.setValue(stage.ordinal());
    }
    
    public static void setModelView(final Matrix4f matrixIn) {
        if (matrixIn.equals((Object)Shaders.lastModelView)) {
            return;
        }
        Shaders.lastModelView.set((Matrix4fc)matrixIn);
        MathUtils.write(matrixIn, Shaders.modelView);
        SMath.invertMat4FBFA(Shaders.modelViewInverse.position(0), Shaders.modelView.position(0), Shaders.faModelViewInverse, Shaders.faModelView);
        Shaders.modelView.position(0);
        Shaders.modelViewInverse.position(0);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferModelView, false, Shaders.modelView);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferModelViewInverse, false, Shaders.modelViewInverse);
    }
    
    public static void setProjection(final Matrix4f matrixIn) {
        if (matrixIn.equals((Object)Shaders.lastProjection)) {
            return;
        }
        Shaders.lastProjection.set((Matrix4fc)matrixIn);
        MathUtils.write(matrixIn, Shaders.projection);
        SMath.invertMat4FBFA(Shaders.projectionInverse.position(0), Shaders.projection.position(0), Shaders.faProjectionInverse, Shaders.faProjection);
        Shaders.projection.position(0);
        Shaders.projectionInverse.position(0);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferProjection, false, Shaders.projection);
        setProgramUniformMatrix4ARB(Shaders.uniform_gbufferProjectionInverse, false, Shaders.projectionInverse);
    }
    
    public static void setModelViewMatrix(final Matrix4f matrixIn) {
        Shaders.uniform_modelViewMatrix.setValue(matrixIn);
        if (Shaders.uniform_modelViewMatrixInverse.isDefined()) {
            final Matrix4f matInv = new Matrix4f((Matrix4fc)matrixIn);
            matInv.invert();
            Shaders.uniform_modelViewMatrixInverse.setValue(matInv);
        }
        if (Shaders.uniform_normalMatrix.isDefined()) {
            final Matrix3f normalMat3 = new Matrix3f((Matrix4fc)matrixIn);
            normalMat3.invert();
            normalMat3.transpose();
            Shaders.uniform_normalMatrix.setValue(normalMat3);
        }
    }
    
    public static void setProjectionMatrix(final Matrix4f matrixIn) {
        Shaders.uniform_projectionMatrix.setValue(matrixIn);
        if (Shaders.uniform_projectionMatrixInverse.isDefined()) {
            final Matrix4f matInv = new Matrix4f((Matrix4fc)matrixIn);
            matInv.invert();
            Shaders.uniform_projectionMatrixInverse.setValue(matInv);
        }
    }
    
    public static void setTextureMatrix(final Matrix4f matrixIn) {
        Shaders.uniform_textureMatrix.setValue(matrixIn);
    }
    
    public static void setColorModulator(final float[] cols) {
        if (cols == null || cols.length < 4) {
            return;
        }
        Shaders.uniform_colorModulator.setValue(cols[0], cols[1], cols[2], cols[3]);
    }
    
    public static void setFogAllowed(final boolean fogAllowed) {
        Shaders.fogAllowed = fogAllowed;
    }
    
    public static void setDefaultAttribColor() {
        setDefaultAttribColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private static void setDefaultAttribLightmap() {
        setDefaultAttribLightmap((short)240, (short)240);
    }
    
    private static void setDefaultAttribNormal() {
        setDefaultAttribNormal(0.0f, 1.0f, 0.0f);
    }
    
    public static void setDefaultAttribColor(final float r, final float g, final float b, final float a) {
        GL30.glVertexAttrib4f(eih.b.getAttributeIndex(), r, g, b, a);
    }
    
    private static void setDefaultAttribLightmap(final short su, final short sv) {
        GL30.glVertexAttrib2s(eih.e.getAttributeIndex(), su, sv);
    }
    
    private static void setDefaultAttribNormal(final float x, final float y, final float z) {
        GL30.glVertexAttrib3f(eih.f.getAttributeIndex(), x, y, z);
    }
    
    private static int getBossBattle() {
        final String bossName = Shaders.mc.l.j().getBossName();
        if (bossName == null) {
            return 0;
        }
        if (bossName.equals("entity.minecraft.ender_dragon")) {
            return 2;
        }
        if (bossName.equals("entity.minecraft.wither")) {
            return 3;
        }
        if (bossName.equals("event.minecraft.raid")) {
            return 4;
        }
        return 1;
    }
    
    public static void setDarknessFactor(final float darknessFactor) {
        Shaders.darknessFactor = darknessFactor;
    }
    
    public static void setDarknessLightFactor(final float darknessLightFactor) {
        Shaders.darknessLightFactor = darknessLightFactor;
    }
    
    static {
        Shaders.isInitializedOnce = false;
        Shaders.isShaderPackInitialized = false;
        Shaders.hasGlGenMipmap = false;
        Shaders.countResetDisplayLists = 0;
        Shaders.renderDisplayWidth = 0;
        Shaders.renderDisplayHeight = 0;
        Shaders.renderWidth = 0;
        Shaders.renderHeight = 0;
        Shaders.isRenderingWorld = false;
        Shaders.isRenderingSky = false;
        Shaders.isCompositeRendered = false;
        Shaders.isRenderingDfb = false;
        Shaders.isShadowPass = false;
        Shaders.renderItemKeepDepthMask = false;
        Shaders.itemToRenderMainTranslucent = false;
        Shaders.itemToRenderOffTranslucent = false;
        Shaders.sunPosition = new float[4];
        Shaders.moonPosition = new float[4];
        Shaders.shadowLightPosition = new float[4];
        Shaders.upPosition = new float[4];
        Shaders.shadowLightPositionVector = new float[4];
        Shaders.upPosModelView = new float[] { 0.0f, 100.0f, 0.0f, 0.0f };
        Shaders.sunPosModelView = new float[] { 0.0f, 100.0f, 0.0f, 0.0f };
        Shaders.moonPosModelView = new float[] { 0.0f, -100.0f, 0.0f, 0.0f };
        Shaders.tempMat = new float[16];
        Shaders.clearColor = new Vector4f();
        Shaders.worldTime = 0L;
        Shaders.lastWorldTime = 0L;
        Shaders.diffWorldTime = 0L;
        Shaders.celestialAngle = 0.0f;
        Shaders.sunAngle = 0.0f;
        Shaders.shadowAngle = 0.0f;
        Shaders.moonPhase = 0;
        Shaders.systemTime = 0L;
        Shaders.lastSystemTime = 0L;
        Shaders.diffSystemTime = 0L;
        Shaders.frameCounter = 0;
        Shaders.frameTime = 0.0f;
        Shaders.frameTimeCounter = 0.0f;
        Shaders.systemTimeInt32 = 0;
        Shaders.pointOfView = ena.a;
        Shaders.pointOfViewChanged = false;
        Shaders.rainStrength = 0.0f;
        Shaders.wetness = 0.0f;
        Shaders.wetnessHalfLife = 600.0f;
        Shaders.drynessHalfLife = 200.0f;
        Shaders.eyeBrightnessHalflife = 10.0f;
        Shaders.usewetness = false;
        Shaders.isEyeInWater = 0;
        Shaders.eyeBrightness = 0;
        Shaders.eyeBrightnessFadeX = 0.0f;
        Shaders.eyeBrightnessFadeY = 0.0f;
        Shaders.eyePosY = 0.0f;
        Shaders.centerDepth = 0.0f;
        Shaders.centerDepthSmooth = 0.0f;
        Shaders.centerDepthSmoothHalflife = 1.0f;
        Shaders.centerDepthSmoothEnabled = false;
        Shaders.superSamplingLevel = 1;
        Shaders.nightVision = 0.0f;
        Shaders.blindness = 0.0f;
        Shaders.lightmapEnabled = false;
        Shaders.fogEnabled = true;
        Shaders.fogAllowed = true;
        Shaders.renderStage = RenderStage.NONE;
        Shaders.bossBattle = 0;
        Shaders.darknessFactor = 0.0f;
        Shaders.darknessLightFactor = 0.0f;
        Shaders.baseAttribId = 11;
        Shaders.entityAttrib = Shaders.baseAttribId + 0;
        Shaders.midTexCoordAttrib = Shaders.baseAttribId + 1;
        Shaders.tangentAttrib = Shaders.baseAttribId + 2;
        Shaders.velocityAttrib = Shaders.baseAttribId + 3;
        Shaders.midBlockAttrib = Shaders.baseAttribId + 4;
        Shaders.useEntityAttrib = false;
        Shaders.useMidTexCoordAttrib = false;
        Shaders.useTangentAttrib = false;
        Shaders.useVelocityAttrib = false;
        Shaders.useMidBlockAttrib = false;
        Shaders.progUseEntityAttrib = false;
        Shaders.progUseMidTexCoordAttrib = false;
        Shaders.progUseTangentAttrib = false;
        Shaders.progUseVelocityAttrib = false;
        Shaders.progUseMidBlockAttrib = false;
        Shaders.progArbGeometryShader4 = false;
        Shaders.progExtGeometryShader4 = false;
        Shaders.progMaxVerticesOut = 3;
        Shaders.hasGeometryShaders = false;
        Shaders.hasShadowGeometryShaders = false;
        Shaders.hasShadowInstancing = false;
        Shaders.atlasSizeX = 0;
        Shaders.atlasSizeY = 0;
        Shaders.shaderUniforms = new ShaderUniforms();
        Shaders.uniform_entityColor = Shaders.shaderUniforms.make4f("entityColor");
        Shaders.uniform_entityId = Shaders.shaderUniforms.make1i("entityId");
        Shaders.uniform_blockEntityId = Shaders.shaderUniforms.make1i("blockEntityId");
        Shaders.uniform_gtexture = Shaders.shaderUniforms.make1i("gtexture");
        Shaders.uniform_lightmap = Shaders.shaderUniforms.make1i("lightmap");
        Shaders.uniform_normals = Shaders.shaderUniforms.make1i("normals");
        Shaders.uniform_specular = Shaders.shaderUniforms.make1i("specular");
        Shaders.uniform_shadow = Shaders.shaderUniforms.make1i("shadow");
        Shaders.uniform_watershadow = Shaders.shaderUniforms.make1i("watershadow");
        Shaders.uniform_shadowtex0 = Shaders.shaderUniforms.make1i("shadowtex0");
        Shaders.uniform_shadowtex1 = Shaders.shaderUniforms.make1i("shadowtex1");
        Shaders.uniform_depthtex0 = Shaders.shaderUniforms.make1i("depthtex0");
        Shaders.uniform_depthtex1 = Shaders.shaderUniforms.make1i("depthtex1");
        Shaders.uniform_shadowcolor = Shaders.shaderUniforms.make1i("shadowcolor");
        Shaders.uniform_shadowcolor0 = Shaders.shaderUniforms.make1i("shadowcolor0");
        Shaders.uniform_shadowcolor1 = Shaders.shaderUniforms.make1i("shadowcolor1");
        Shaders.uniform_noisetex = Shaders.shaderUniforms.make1i("noisetex");
        Shaders.uniform_gcolor = Shaders.shaderUniforms.make1i("gcolor");
        Shaders.uniform_gdepth = Shaders.shaderUniforms.make1i("gdepth");
        Shaders.uniform_gnormal = Shaders.shaderUniforms.make1i("gnormal");
        Shaders.uniform_composite = Shaders.shaderUniforms.make1i("composite");
        Shaders.uniform_gaux1 = Shaders.shaderUniforms.make1i("gaux1");
        Shaders.uniform_gaux2 = Shaders.shaderUniforms.make1i("gaux2");
        Shaders.uniform_gaux3 = Shaders.shaderUniforms.make1i("gaux3");
        Shaders.uniform_gaux4 = Shaders.shaderUniforms.make1i("gaux4");
        Shaders.uniform_colortex0 = Shaders.shaderUniforms.make1i("colortex0");
        Shaders.uniform_colortex1 = Shaders.shaderUniforms.make1i("colortex1");
        Shaders.uniform_colortex2 = Shaders.shaderUniforms.make1i("colortex2");
        Shaders.uniform_colortex3 = Shaders.shaderUniforms.make1i("colortex3");
        Shaders.uniform_colortex4 = Shaders.shaderUniforms.make1i("colortex4");
        Shaders.uniform_colortex5 = Shaders.shaderUniforms.make1i("colortex5");
        Shaders.uniform_colortex6 = Shaders.shaderUniforms.make1i("colortex6");
        Shaders.uniform_colortex7 = Shaders.shaderUniforms.make1i("colortex7");
        Shaders.uniform_gdepthtex = Shaders.shaderUniforms.make1i("gdepthtex");
        Shaders.uniform_depthtex2 = Shaders.shaderUniforms.make1i("depthtex2");
        Shaders.uniform_colortex8 = Shaders.shaderUniforms.make1i("colortex8");
        Shaders.uniform_colortex9 = Shaders.shaderUniforms.make1i("colortex9");
        Shaders.uniform_colortex10 = Shaders.shaderUniforms.make1i("colortex10");
        Shaders.uniform_colortex11 = Shaders.shaderUniforms.make1i("colortex11");
        Shaders.uniform_colortex12 = Shaders.shaderUniforms.make1i("colortex12");
        Shaders.uniform_colortex13 = Shaders.shaderUniforms.make1i("colortex13");
        Shaders.uniform_colortex14 = Shaders.shaderUniforms.make1i("colortex14");
        Shaders.uniform_colortex15 = Shaders.shaderUniforms.make1i("colortex15");
        Shaders.uniform_colorimg0 = Shaders.shaderUniforms.make1i("colorimg0");
        Shaders.uniform_colorimg1 = Shaders.shaderUniforms.make1i("colorimg1");
        Shaders.uniform_colorimg2 = Shaders.shaderUniforms.make1i("colorimg2");
        Shaders.uniform_colorimg3 = Shaders.shaderUniforms.make1i("colorimg3");
        Shaders.uniform_colorimg4 = Shaders.shaderUniforms.make1i("colorimg4");
        Shaders.uniform_colorimg5 = Shaders.shaderUniforms.make1i("colorimg5");
        Shaders.uniform_shadowcolorimg0 = Shaders.shaderUniforms.make1i("shadowcolorimg0");
        Shaders.uniform_shadowcolorimg1 = Shaders.shaderUniforms.make1i("shadowcolorimg1");
        Shaders.uniform_tex = Shaders.shaderUniforms.make1i("tex");
        Shaders.uniform_heldItemId = Shaders.shaderUniforms.make1i("heldItemId");
        Shaders.uniform_heldBlockLightValue = Shaders.shaderUniforms.make1i("heldBlockLightValue");
        Shaders.uniform_heldItemId2 = Shaders.shaderUniforms.make1i("heldItemId2");
        Shaders.uniform_heldBlockLightValue2 = Shaders.shaderUniforms.make1i("heldBlockLightValue2");
        Shaders.uniform_fogMode = Shaders.shaderUniforms.make1i("fogMode");
        Shaders.uniform_fogDensity = Shaders.shaderUniforms.make1f("fogDensity");
        Shaders.uniform_fogStart = Shaders.shaderUniforms.make1f("fogStart");
        Shaders.uniform_fogEnd = Shaders.shaderUniforms.make1f("fogEnd");
        Shaders.uniform_fogShape = Shaders.shaderUniforms.make1i("fogShape");
        Shaders.uniform_fogColor = Shaders.shaderUniforms.make3f("fogColor");
        Shaders.uniform_skyColor = Shaders.shaderUniforms.make3f("skyColor");
        Shaders.uniform_worldTime = Shaders.shaderUniforms.make1i("worldTime");
        Shaders.uniform_worldDay = Shaders.shaderUniforms.make1i("worldDay");
        Shaders.uniform_moonPhase = Shaders.shaderUniforms.make1i("moonPhase");
        Shaders.uniform_frameCounter = Shaders.shaderUniforms.make1i("frameCounter");
        Shaders.uniform_frameTime = Shaders.shaderUniforms.make1f("frameTime");
        Shaders.uniform_frameTimeCounter = Shaders.shaderUniforms.make1f("frameTimeCounter");
        Shaders.uniform_sunAngle = Shaders.shaderUniforms.make1f("sunAngle");
        Shaders.uniform_shadowAngle = Shaders.shaderUniforms.make1f("shadowAngle");
        Shaders.uniform_rainStrength = Shaders.shaderUniforms.make1f("rainStrength");
        Shaders.uniform_aspectRatio = Shaders.shaderUniforms.make1f("aspectRatio");
        Shaders.uniform_viewWidth = Shaders.shaderUniforms.make1f("viewWidth");
        Shaders.uniform_viewHeight = Shaders.shaderUniforms.make1f("viewHeight");
        Shaders.uniform_near = Shaders.shaderUniforms.make1f("near");
        Shaders.uniform_far = Shaders.shaderUniforms.make1f("far");
        Shaders.uniform_sunPosition = Shaders.shaderUniforms.make3f("sunPosition");
        Shaders.uniform_moonPosition = Shaders.shaderUniforms.make3f("moonPosition");
        Shaders.uniform_shadowLightPosition = Shaders.shaderUniforms.make3f("shadowLightPosition");
        Shaders.uniform_upPosition = Shaders.shaderUniforms.make3f("upPosition");
        Shaders.uniform_previousCameraPosition = Shaders.shaderUniforms.make3f("previousCameraPosition");
        Shaders.uniform_cameraPosition = Shaders.shaderUniforms.make3f("cameraPosition");
        Shaders.uniform_gbufferModelView = Shaders.shaderUniforms.makeM4("gbufferModelView");
        Shaders.uniform_gbufferModelViewInverse = Shaders.shaderUniforms.makeM4("gbufferModelViewInverse");
        Shaders.uniform_gbufferPreviousProjection = Shaders.shaderUniforms.makeM4("gbufferPreviousProjection");
        Shaders.uniform_gbufferProjection = Shaders.shaderUniforms.makeM4("gbufferProjection");
        Shaders.uniform_gbufferProjectionInverse = Shaders.shaderUniforms.makeM4("gbufferProjectionInverse");
        Shaders.uniform_gbufferPreviousModelView = Shaders.shaderUniforms.makeM4("gbufferPreviousModelView");
        Shaders.uniform_shadowProjection = Shaders.shaderUniforms.makeM4("shadowProjection");
        Shaders.uniform_shadowProjectionInverse = Shaders.shaderUniforms.makeM4("shadowProjectionInverse");
        Shaders.uniform_shadowModelView = Shaders.shaderUniforms.makeM4("shadowModelView");
        Shaders.uniform_shadowModelViewInverse = Shaders.shaderUniforms.makeM4("shadowModelViewInverse");
        Shaders.uniform_wetness = Shaders.shaderUniforms.make1f("wetness");
        Shaders.uniform_eyeAltitude = Shaders.shaderUniforms.make1f("eyeAltitude");
        Shaders.uniform_eyeBrightness = Shaders.shaderUniforms.make2i("eyeBrightness");
        Shaders.uniform_eyeBrightnessSmooth = Shaders.shaderUniforms.make2i("eyeBrightnessSmooth");
        Shaders.uniform_terrainTextureSize = Shaders.shaderUniforms.make2i("terrainTextureSize");
        Shaders.uniform_terrainIconSize = Shaders.shaderUniforms.make1i("terrainIconSize");
        Shaders.uniform_isEyeInWater = Shaders.shaderUniforms.make1i("isEyeInWater");
        Shaders.uniform_nightVision = Shaders.shaderUniforms.make1f("nightVision");
        Shaders.uniform_blindness = Shaders.shaderUniforms.make1f("blindness");
        Shaders.uniform_screenBrightness = Shaders.shaderUniforms.make1f("screenBrightness");
        Shaders.uniform_hideGUI = Shaders.shaderUniforms.make1i("hideGUI");
        Shaders.uniform_centerDepthSmooth = Shaders.shaderUniforms.make1f("centerDepthSmooth");
        Shaders.uniform_atlasSize = Shaders.shaderUniforms.make2i("atlasSize");
        Shaders.uniform_spriteBounds = Shaders.shaderUniforms.make4f("spriteBounds");
        Shaders.uniform_blendFunc = Shaders.shaderUniforms.make4i("blendFunc");
        Shaders.uniform_instanceId = Shaders.shaderUniforms.make1i("instanceId");
        Shaders.uniform_playerMood = Shaders.shaderUniforms.make1f("playerMood");
        Shaders.uniform_renderStage = Shaders.shaderUniforms.make1i("renderStage");
        Shaders.uniform_bossBattle = Shaders.shaderUniforms.make1i("bossBattle");
        Shaders.uniform_modelViewMatrix = Shaders.shaderUniforms.makeM4("modelViewMatrix");
        Shaders.uniform_modelViewMatrixInverse = Shaders.shaderUniforms.makeM4("modelViewMatrixInverse");
        Shaders.uniform_projectionMatrix = Shaders.shaderUniforms.makeM4("projectionMatrix");
        Shaders.uniform_projectionMatrixInverse = Shaders.shaderUniforms.makeM4("projectionMatrixInverse");
        Shaders.uniform_textureMatrix = Shaders.shaderUniforms.makeM4("textureMatrix");
        Shaders.uniform_normalMatrix = Shaders.shaderUniforms.makeM3("normalMatrix");
        Shaders.uniform_chunkOffset = Shaders.shaderUniforms.make3f("chunkOffset");
        Shaders.uniform_colorModulator = Shaders.shaderUniforms.make4f("colorModulator");
        Shaders.uniform_alphaTestRef = Shaders.shaderUniforms.make1f("alphaTestRef");
        Shaders.uniform_darknessFactor = Shaders.shaderUniforms.make1f("darknessFactor");
        Shaders.uniform_darknessLightFactor = Shaders.shaderUniforms.make1f("darknessLightFactor");
        Shaders.hasShadowMap = false;
        Shaders.needResizeShadow = false;
        Shaders.shadowMapWidth = 1024;
        Shaders.shadowMapHeight = 1024;
        Shaders.spShadowMapWidth = 1024;
        Shaders.spShadowMapHeight = 1024;
        Shaders.shadowMapFOV = 90.0f;
        Shaders.shadowMapHalfPlane = 160.0f;
        Shaders.shadowMapIsOrtho = true;
        Shaders.shadowDistanceRenderMul = -1.0f;
        Shaders.shouldSkipDefaultShadow = false;
        Shaders.waterShadowEnabled = false;
        Shaders.usedColorBuffers = 0;
        Shaders.usedDepthBuffers = 0;
        Shaders.usedShadowColorBuffers = 0;
        Shaders.usedShadowDepthBuffers = 0;
        Shaders.usedColorAttachs = 0;
        Shaders.usedDrawBuffers = 0;
        Shaders.bindImageTextures = false;
        Shaders.gbuffersFormat = new int[16];
        Shaders.gbuffersClear = new boolean[16];
        Shaders.gbuffersClearColor = new Vector4f[16];
        CLEAR_COLOR_0 = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
        CLEAR_COLOR_1 = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        Shaders.shadowBuffersFormat = new int[2];
        Shaders.shadowBuffersClear = new boolean[2];
        Shaders.shadowBuffersClearColor = new Vector4f[2];
        Shaders.programs = new Programs();
        ProgramNone = Shaders.programs.getProgramNone();
        ProgramShadow = Shaders.programs.makeShadow("shadow", Shaders.ProgramNone);
        ProgramShadowSolid = Shaders.programs.makeShadow("shadow_solid", Shaders.ProgramShadow);
        ProgramShadowCutout = Shaders.programs.makeShadow("shadow_cutout", Shaders.ProgramShadow);
        ProgramsShadowcomp = Shaders.programs.makeShadowcomps("shadowcomp", 100);
        ProgramsPrepare = Shaders.programs.makePrepares("prepare", 100);
        ProgramBasic = Shaders.programs.makeGbuffers("gbuffers_basic", Shaders.ProgramNone);
        ProgramLine = Shaders.programs.makeGbuffers("gbuffers_line", Shaders.ProgramBasic);
        ProgramTextured = Shaders.programs.makeGbuffers("gbuffers_textured", Shaders.ProgramBasic);
        ProgramTexturedLit = Shaders.programs.makeGbuffers("gbuffers_textured_lit", Shaders.ProgramTextured);
        ProgramSkyBasic = Shaders.programs.makeGbuffers("gbuffers_skybasic", Shaders.ProgramBasic);
        ProgramSkyTextured = Shaders.programs.makeGbuffers("gbuffers_skytextured", Shaders.ProgramTextured);
        ProgramClouds = Shaders.programs.makeGbuffers("gbuffers_clouds", Shaders.ProgramTextured);
        ProgramTerrain = Shaders.programs.makeGbuffers("gbuffers_terrain", Shaders.ProgramTexturedLit);
        ProgramTerrainSolid = Shaders.programs.makeGbuffers("gbuffers_terrain_solid", Shaders.ProgramTerrain);
        ProgramTerrainCutoutMip = Shaders.programs.makeGbuffers("gbuffers_terrain_cutout_mip", Shaders.ProgramTerrain);
        ProgramTerrainCutout = Shaders.programs.makeGbuffers("gbuffers_terrain_cutout", Shaders.ProgramTerrain);
        ProgramDamagedBlock = Shaders.programs.makeGbuffers("gbuffers_damagedblock", Shaders.ProgramTerrain);
        ProgramBlock = Shaders.programs.makeGbuffers("gbuffers_block", Shaders.ProgramTerrain);
        ProgramBeaconBeam = Shaders.programs.makeGbuffers("gbuffers_beaconbeam", Shaders.ProgramTextured);
        ProgramItem = Shaders.programs.makeGbuffers("gbuffers_item", Shaders.ProgramTexturedLit);
        ProgramEntities = Shaders.programs.makeGbuffers("gbuffers_entities", Shaders.ProgramTexturedLit);
        ProgramEntitiesGlowing = Shaders.programs.makeGbuffers("gbuffers_entities_glowing", Shaders.ProgramEntities);
        ProgramArmorGlint = Shaders.programs.makeGbuffers("gbuffers_armor_glint", Shaders.ProgramTextured);
        ProgramSpiderEyes = Shaders.programs.makeGbuffers("gbuffers_spidereyes", Shaders.ProgramTextured);
        ProgramHand = Shaders.programs.makeGbuffers("gbuffers_hand", Shaders.ProgramTexturedLit);
        ProgramWeather = Shaders.programs.makeGbuffers("gbuffers_weather", Shaders.ProgramTexturedLit);
        ProgramDeferredPre = Shaders.programs.makeVirtual("deferred_pre");
        ProgramsDeferred = Shaders.programs.makeDeferreds("deferred", 100);
        ProgramDeferred = Shaders.ProgramsDeferred[0];
        ProgramWater = Shaders.programs.makeGbuffers("gbuffers_water", Shaders.ProgramTerrain);
        ProgramHandWater = Shaders.programs.makeGbuffers("gbuffers_hand_water", Shaders.ProgramHand);
        ProgramCompositePre = Shaders.programs.makeVirtual("composite_pre");
        ProgramsComposite = Shaders.programs.makeComposites("composite", 100);
        ProgramComposite = Shaders.ProgramsComposite[0];
        ProgramFinal = Shaders.programs.makeComposite("final");
        ProgramCount = Shaders.programs.getCount();
        ProgramsAll = Shaders.programs.getPrograms();
        Shaders.activeProgram = Shaders.ProgramNone;
        Shaders.activeProgramID = 0;
        Shaders.programStack = new ProgramStack();
        Shaders.hasDeferredPrograms = false;
        Shaders.hasShadowcompPrograms = false;
        Shaders.hasPreparePrograms = false;
        Shaders.loadedShaders = null;
        Shaders.shadersConfig = null;
        Shaders.defaultTexture = null;
        Shaders.shadowHardwareFilteringEnabled = new boolean[2];
        Shaders.shadowMipmapEnabled = new boolean[2];
        Shaders.shadowFilterNearest = new boolean[2];
        Shaders.shadowColorMipmapEnabled = new boolean[2];
        Shaders.shadowColorFilterNearest = new boolean[2];
        Shaders.configTweakBlockDamage = false;
        Shaders.configCloudShadow = false;
        Shaders.configHandDepthMul = 0.125f;
        Shaders.configRenderResMul = 1.0f;
        Shaders.configShadowResMul = 1.0f;
        Shaders.configTexMinFilB = 0;
        Shaders.configTexMinFilN = 0;
        Shaders.configTexMinFilS = 0;
        Shaders.configTexMagFilB = 0;
        Shaders.configTexMagFilN = 0;
        Shaders.configTexMagFilS = 0;
        Shaders.configShadowClipFrustrum = true;
        Shaders.configNormalMap = true;
        Shaders.configSpecularMap = true;
        Shaders.configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
        Shaders.configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
        Shaders.configAntialiasingLevel = 0;
        texMinFilDesc = new String[] { "Nearest", "Nearest-Nearest", "Nearest-Linear" };
        texMagFilDesc = new String[] { "Nearest", "Linear" };
        texMinFilValue = new int[] { 9728, 9984, 9986 };
        texMagFilValue = new int[] { 9728, 9729 };
        Shaders.shaderPack = null;
        Shaders.shaderPackLoaded = false;
        shaderPacksDir = new File(enn.N().p, "shaderpacks");
        Shaders.configFile = new File(enn.N().p, "optionsshaders.txt");
        Shaders.shaderPackOptions = null;
        Shaders.shaderPackOptionSliders = null;
        Shaders.shaderPackProfiles = null;
        Shaders.shaderPackGuiScreens = null;
        Shaders.shaderPackProgramConditions = new HashMap<String, IExpressionBool>();
        Shaders.shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
        Shaders.shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
        Shaders.shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
        Shaders.shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
        Shaders.shaderPackShadowTerrain = new PropertyDefaultTrueFalse("shadowTerrain", "Shadow Terrain", 0);
        Shaders.shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
        Shaders.shaderPackShadowEntities = new PropertyDefaultTrueFalse("shadowEntities", "Shadow Entities", 0);
        Shaders.shaderPackShadowBlockEntities = new PropertyDefaultTrueFalse("shadowBlockEntities", "Shadow Block Entities", 0);
        Shaders.shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
        Shaders.shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
        Shaders.shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
        Shaders.shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
        Shaders.shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
        Shaders.shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
        Shaders.shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
        Shaders.shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
        Shaders.shaderPackRainDepth = new PropertyDefaultTrueFalse("rain.depth", "Rain Depth", 0);
        Shaders.shaderPackBeaconBeamDepth = new PropertyDefaultTrueFalse("beacon.beam.depth", "Rain Depth", 0);
        Shaders.shaderPackSeparateAo = new PropertyDefaultTrueFalse("separateAo", "Separate AO", 0);
        Shaders.shaderPackFrustumCulling = new PropertyDefaultTrueFalse("frustum.culling", "Frustum Culling", 0);
        Shaders.shaderPackShadowCulling = new PropertyDefaultTrueFalse("shadow.culling", "Shadow Culling", 0);
        Shaders.shaderPackParticlesBeforeDeferred = new PropertyDefaultTrueFalse("particles.before.deferred", "Particles before deferred", 0);
        Shaders.shaderPackResources = new HashMap<String, String>();
        Shaders.currentWorld = null;
        Shaders.shaderPackDimensions = new ArrayList<Integer>();
        Shaders.customTexturesGbuffers = null;
        Shaders.customTexturesComposite = null;
        Shaders.customTexturesDeferred = null;
        Shaders.customTexturesShadowcomp = null;
        Shaders.customTexturesPrepare = null;
        Shaders.noiseTexturePath = null;
        Shaders.colorBufferSizes = new DynamicDimension[16];
        Shaders.customUniforms = null;
        saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
        Shaders.blockLightLevel05 = 0.5f;
        Shaders.blockLightLevel06 = 0.6f;
        Shaders.blockLightLevel08 = 0.8f;
        Shaders.aoLevel = -1.0f;
        Shaders.sunPathRotation = 0.0f;
        Shaders.shadowAngleInterval = 0.0f;
        Shaders.fogMode = 0;
        Shaders.fogShape = 0;
        Shaders.fogDensity = 0.0f;
        Shaders.fogStart = 0.0f;
        Shaders.fogEnd = 0.0f;
        Shaders.shadowIntervalSize = 2.0f;
        Shaders.terrainIconSize = 16;
        Shaders.terrainTextureSize = new int[2];
        Shaders.noiseTextureEnabled = false;
        Shaders.noiseTextureResolution = 256;
        colorTextureImageUnit = new int[] { 0, 1, 2, 3, 7, 8, 9, 10, 16, 17, 18, 19, 20, 21, 22, 23 };
        depthTextureImageUnit = new int[] { 6, 11, 12 };
        shadowColorTextureImageUnit = new int[] { 13, 14 };
        shadowDepthTextureImageUnit = new int[] { 4, 5 };
        colorImageUnit = new int[] { 0, 1, 2, 3, 4, 5 };
        shadowColorImageUnit = new int[] { 6, 7 };
        bigBufferSize = (295 + 8 * Shaders.ProgramCount) * 4;
        bigBuffer = BufferUtils.createByteBuffer(Shaders.bigBufferSize).limit(0);
        faProjection = new float[16];
        faProjectionInverse = new float[16];
        faModelView = new float[16];
        faModelViewInverse = new float[16];
        faShadowProjection = new float[16];
        faShadowProjectionInverse = new float[16];
        faShadowModelView = new float[16];
        faShadowModelViewInverse = new float[16];
        projection = nextFloatBuffer(16);
        projectionInverse = nextFloatBuffer(16);
        modelView = nextFloatBuffer(16);
        modelViewInverse = nextFloatBuffer(16);
        shadowProjection = nextFloatBuffer(16);
        shadowProjectionInverse = nextFloatBuffer(16);
        shadowModelView = nextFloatBuffer(16);
        shadowModelViewInverse = nextFloatBuffer(16);
        lastModelView = new Matrix4f();
        lastProjection = new Matrix4f();
        previousProjection = nextFloatBuffer(16);
        previousModelView = nextFloatBuffer(16);
        tempMatrixDirectBuffer = nextFloatBuffer(16);
        tempDirectFloatBuffer = nextFloatBuffer(16);
        dfbDrawBuffers = new DrawBuffers("dfbDrawBuffers", 16, 8);
        sfbDrawBuffers = new DrawBuffers("sfbDrawBuffers", 16, 8);
        drawBuffersNone = new DrawBuffers("drawBuffersNone", 16, 8).limit(0);
        drawBuffersColorAtt = makeDrawBuffersColorSingle(16);
        MATRIX_IDENTITY = MathUtils.makeMatrixIdentity();
        formatNames = new String[] { "R8", "RG8", "RGB8", "RGBA8", "R8_SNORM", "RG8_SNORM", "RGB8_SNORM", "RGBA8_SNORM", "R8I", "RG8I", "RGB8I", "RGBA8I", "R8UI", "RG8UI", "RGB8UI", "RGBA8UI", "R16", "RG16", "RGB16", "RGBA16", "R16_SNORM", "RG16_SNORM", "RGB16_SNORM", "RGBA16_SNORM", "R16F", "RG16F", "RGB16F", "RGBA16F", "R16I", "RG16I", "RGB16I", "RGBA16I", "R16UI", "RG16UI", "RGB16UI", "RGBA16UI", "R32F", "RG32F", "RGB32F", "RGBA32F", "R32I", "RG32I", "RGB32I", "RGBA32I", "R32UI", "RG32UI", "RGB32UI", "RGBA32UI", "R3_G3_B2", "RGB5_A1", "RGB10_A2", "R11F_G11F_B10F", "RGB9_E5" };
        formatIds = new int[] { 33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33329, 33335, 36239, 36238, 33330, 33336, 36221, 36220, 33322, 33324, 32852, 32859, 36760, 36761, 36762, 36763, 33325, 33327, 34843, 34842, 33331, 33337, 36233, 36232, 33332, 33338, 36215, 36214, 33326, 33328, 34837, 34836, 33333, 33339, 36227, 36226, 33334, 33340, 36209, 36208, 10768, 32855, 32857, 35898, 35901 };
        patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
        Shaders.entityData = new int[32];
        Shaders.entityDataIndex = 0;
    }
}
