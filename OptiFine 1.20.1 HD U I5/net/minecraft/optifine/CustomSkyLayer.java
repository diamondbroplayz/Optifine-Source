// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.util.MathUtils;
import org.joml.Matrix4f;
import java.util.function.Supplier;
import net.optifine.util.NumUtils;
import net.optifine.config.Matches;
import com.mojang.blaze3d.systems.RenderSystem;
import net.optifine.util.TextureUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.optifine.render.Blender;
import net.optifine.config.ConnectedParser;
import java.util.Properties;
import net.optifine.util.SmoothFloat;
import net.optifine.config.BiomeId;
import net.optifine.config.RangeListInt;

public class CustomSkyLayer
{
    public String source;
    private int startFadeIn;
    private int endFadeIn;
    private int startFadeOut;
    private int endFadeOut;
    private int blend;
    private boolean rotate;
    private float speed;
    private float[] axis;
    private RangeListInt days;
    private int daysLoop;
    private boolean weatherClear;
    private boolean weatherRain;
    private boolean weatherThunder;
    public BiomeId[] biomes;
    public RangeListInt heights;
    private float transition;
    private SmoothFloat smoothPositionBrightness;
    public int textureId;
    private cmm lastWorld;
    public static final float[] DEFAULT_AXIS;
    private static final String WEATHER_CLEAR = "clear";
    private static final String WEATHER_RAIN = "rain";
    private static final String WEATHER_THUNDER = "thunder";
    
    public CustomSkyLayer(final Properties props, final String defSource) {
        this.source = null;
        this.startFadeIn = -1;
        this.endFadeIn = -1;
        this.startFadeOut = -1;
        this.endFadeOut = -1;
        this.blend = 1;
        this.rotate = false;
        this.speed = 1.0f;
        this.axis = CustomSkyLayer.DEFAULT_AXIS;
        this.days = null;
        this.daysLoop = 8;
        this.weatherClear = true;
        this.weatherRain = false;
        this.weatherThunder = false;
        this.biomes = null;
        this.heights = null;
        this.transition = 1.0f;
        this.smoothPositionBrightness = null;
        this.textureId = -1;
        this.lastWorld = null;
        final ConnectedParser cp = new ConnectedParser("CustomSky");
        this.source = props.getProperty("source", defSource);
        this.startFadeIn = this.parseTime(props.getProperty("startFadeIn"));
        this.endFadeIn = this.parseTime(props.getProperty("endFadeIn"));
        this.startFadeOut = this.parseTime(props.getProperty("startFadeOut"));
        this.endFadeOut = this.parseTime(props.getProperty("endFadeOut"));
        this.blend = Blender.parseBlend(props.getProperty("blend"));
        this.rotate = this.parseBoolean(props.getProperty("rotate"), true);
        this.speed = this.parseFloat(props.getProperty("speed"), 1.0f);
        this.axis = this.parseAxis(props.getProperty("axis"), CustomSkyLayer.DEFAULT_AXIS);
        this.days = cp.parseRangeListInt(props.getProperty("days"));
        this.daysLoop = cp.parseInt(props.getProperty("daysLoop"), 8);
        final List<String> weatherList = this.parseWeatherList(props.getProperty("weather", "clear"));
        this.weatherClear = weatherList.contains("clear");
        this.weatherRain = weatherList.contains("rain");
        this.weatherThunder = weatherList.contains("thunder");
        this.biomes = cp.parseBiomes(props.getProperty("biomes"));
        this.heights = cp.parseRangeListIntNeg(props.getProperty("heights"));
        this.transition = this.parseFloat(props.getProperty("transition"), 1.0f);
    }
    
    private List<String> parseWeatherList(final String str) {
        final List<String> weatherAllowedList = Arrays.asList("clear", "rain", "thunder");
        final List<String> weatherList = new ArrayList<String>();
        final String[] weatherStrs = Config.tokenize(str, " ");
        for (int i = 0; i < weatherStrs.length; ++i) {
            final String token = weatherStrs[i];
            if (!weatherAllowedList.contains(token)) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, token));
            }
            else {
                weatherList.add(token);
            }
        }
        return weatherList;
    }
    
    private int parseTime(final String str) {
        if (str == null) {
            return -1;
        }
        final String[] strs = Config.tokenize(str, ":");
        if (strs.length != 2) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return -1;
        }
        final String hourStr = strs[0];
        final String minStr = strs[1];
        int hour = Config.parseInt(hourStr, -1);
        final int min = Config.parseInt(minStr, -1);
        if (hour < 0 || hour > 23 || min < 0 || min > 59) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return -1;
        }
        hour -= 6;
        if (hour < 0) {
            hour += 24;
        }
        final int time = hour * 1000 + (int)(min / 60.0 * 1000.0);
        return time;
    }
    
    private boolean parseBoolean(final String str, final boolean defVal) {
        if (str == null) {
            return defVal;
        }
        if (str.toLowerCase().equals("true")) {
            return true;
        }
        if (str.toLowerCase().equals("false")) {
            return false;
        }
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return defVal;
    }
    
    private float parseFloat(final String str, final float defVal) {
        if (str == null) {
            return defVal;
        }
        final float val = Config.parseFloat(str, Float.MIN_VALUE);
        if (val == Float.MIN_VALUE) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return defVal;
        }
        return val;
    }
    
    private float[] parseAxis(final String str, final float[] defVal) {
        if (str == null) {
            return defVal;
        }
        final String[] strs = Config.tokenize(str, " ");
        if (strs.length != 3) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return defVal;
        }
        final float[] fs = new float[3];
        for (int i = 0; i < strs.length; ++i) {
            fs[i] = Config.parseFloat(strs[i], Float.MIN_VALUE);
            if (fs[i] == Float.MIN_VALUE) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
                return defVal;
            }
        }
        final float ax = fs[0];
        final float ay = fs[1];
        final float az = fs[2];
        if (ax * ax + ay * ay + az * az < 1.0E-5f) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return defVal;
        }
        final float[] as = { az, ay, -ax };
        return as;
    }
    
    public boolean isValid(final String path) {
        if (this.source == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
        if (this.startFadeIn < 0 && this.endFadeIn < 0 && this.startFadeOut < 0 && this.endFadeOut < 0) {
            this.startFadeIn = 0;
            this.endFadeIn = 0;
            this.startFadeOut = 24000;
            this.endFadeOut = 24000;
        }
        if (this.startFadeIn < 0 || this.endFadeIn < 0 || this.endFadeOut < 0) {
            Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
            return false;
        }
        final int timeFadeIn = this.normalizeTime(this.endFadeIn - this.startFadeIn);
        if (this.startFadeOut < 0) {
            this.startFadeOut = this.normalizeTime(this.endFadeOut - timeFadeIn);
            if (this.timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn)) {
                this.startFadeOut = this.endFadeIn;
            }
        }
        final int timeOn = this.normalizeTime(this.startFadeOut - this.endFadeIn);
        final int timeFadeOut = this.normalizeTime(this.endFadeOut - this.startFadeOut);
        final int timeOff = this.normalizeTime(this.startFadeIn - this.endFadeOut);
        final int timeSum = timeFadeIn + timeOn + timeFadeOut + timeOff;
        if (timeSum != 0 && timeSum != 24000) {
            Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, timeSum));
            return false;
        }
        if (this.speed < 0.0f) {
            Config.warn(invokedynamic(makeConcatWithConstants:(F)Ljava/lang/String;, this.speed));
            return false;
        }
        if (this.daysLoop <= 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.daysLoop));
            return false;
        }
        return true;
    }
    
    private int normalizeTime(int timeMc) {
        while (timeMc >= 24000) {
            timeMc -= 24000;
        }
        while (timeMc < 0) {
            timeMc += 24000;
        }
        return timeMc;
    }
    
    public void render(final cmm world, final eij matrixStackIn, final int timeOfDay, final float celestialAngle, final float rainStrength, final float thunderStrength) {
        final float positionBrightness = this.getPositionBrightness(world);
        final float weatherBrightness = this.getWeatherBrightness(rainStrength, thunderStrength);
        final float fadeBrightness = this.getFadeBrightness(timeOfDay);
        float brightness = positionBrightness * weatherBrightness * fadeBrightness;
        brightness = Config.limit(brightness, 0.0f, 1.0f);
        if (brightness < 1.0E-4f) {
            return;
        }
        RenderSystem.setShaderTexture(0, this.textureId);
        Blender.setupBlend(this.blend, brightness);
        matrixStackIn.a();
        if (this.rotate) {
            float angleDayStart = 0.0f;
            if (this.speed != Math.round(this.speed)) {
                final long worldDay = (world.W() + 18000L) / 24000L;
                final double anglePerDay = this.speed % 1.0f;
                final double angleDayNow = worldDay * anglePerDay;
                angleDayStart = (float)(angleDayNow % 1.0);
            }
            matrixStackIn.rotateDeg(360.0f * (angleDayStart + celestialAngle * this.speed), this.axis[0], this.axis[1], this.axis[2]);
        }
        final eil tess = eil.a();
        matrixStackIn.rotateDegXp(90.0f);
        matrixStackIn.rotateDegZp(-90.0f);
        this.renderSide(matrixStackIn, tess, 4);
        matrixStackIn.a();
        matrixStackIn.rotateDegXp(90.0f);
        this.renderSide(matrixStackIn, tess, 1);
        matrixStackIn.b();
        matrixStackIn.a();
        matrixStackIn.rotateDegXp(-90.0f);
        this.renderSide(matrixStackIn, tess, 0);
        matrixStackIn.b();
        matrixStackIn.rotateDegZp(90.0f);
        this.renderSide(matrixStackIn, tess, 5);
        matrixStackIn.rotateDegZp(90.0f);
        this.renderSide(matrixStackIn, tess, 2);
        matrixStackIn.rotateDegZp(90.0f);
        this.renderSide(matrixStackIn, tess, 3);
        matrixStackIn.b();
    }
    
    private float getPositionBrightness(final cmm world) {
        if (this.biomes == null && this.heights == null) {
            return 1.0f;
        }
        float positionBrightness = this.getPositionBrightnessRaw(world);
        if (this.smoothPositionBrightness == null) {
            this.smoothPositionBrightness = new SmoothFloat(positionBrightness, this.transition);
        }
        positionBrightness = this.smoothPositionBrightness.getSmoothValue(positionBrightness);
        return positionBrightness;
    }
    
    private float getPositionBrightnessRaw(final cmm world) {
        final bfj renderViewEntity = enn.N().al();
        if (renderViewEntity == null) {
            return 0.0f;
        }
        final gu pos = renderViewEntity.di();
        if (this.biomes != null) {
            final cnk biome = (cnk)world.s(pos).a();
            if (biome == null) {
                return 0.0f;
            }
            if (!Matches.biome(biome, this.biomes)) {
                return 0.0f;
            }
        }
        if (this.heights != null && !this.heights.isInRange(pos.v())) {
            return 0.0f;
        }
        return 1.0f;
    }
    
    private float getWeatherBrightness(final float rainStrength, final float thunderStrength) {
        final float clearBrightness = 1.0f - rainStrength;
        final float rainBrightness = rainStrength - thunderStrength;
        final float thunderBrightness = thunderStrength;
        float weatherBrightness = 0.0f;
        if (this.weatherClear) {
            weatherBrightness += clearBrightness;
        }
        if (this.weatherRain) {
            weatherBrightness += rainBrightness;
        }
        if (this.weatherThunder) {
            weatherBrightness += thunderBrightness;
        }
        weatherBrightness = NumUtils.limit(weatherBrightness, 0.0f, 1.0f);
        return weatherBrightness;
    }
    
    private float getFadeBrightness(final int timeOfDay) {
        if (this.timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn)) {
            final int timeFadeIn = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            final int timeDiff = this.normalizeTime(timeOfDay - this.startFadeIn);
            return timeDiff / (float)timeFadeIn;
        }
        if (this.timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut)) {
            return 1.0f;
        }
        if (this.timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut)) {
            final int timeFadeOut = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            final int timeDiff = this.normalizeTime(timeOfDay - this.startFadeOut);
            return 1.0f - timeDiff / (float)timeFadeOut;
        }
        return 0.0f;
    }
    
    private void renderSide(final eij matrixStackIn, final eil tess, final int side) {
        final eie wr = tess.c();
        final float tx = side % 3 / 3.0f;
        final float ty = side / 3 / 2.0f;
        final Matrix4f matrix4f = matrixStackIn.c().a();
        RenderSystem.setShader((Supplier)fjq::s);
        wr.a(eio.b.h, eih.q);
        this.addVertex(matrix4f, wr, -100.0f, -100.0f, -100.0f, tx, ty);
        this.addVertex(matrix4f, wr, -100.0f, -100.0f, 100.0f, tx, ty + 0.5f);
        this.addVertex(matrix4f, wr, 100.0f, -100.0f, 100.0f, tx + 0.33333334f, ty + 0.5f);
        this.addVertex(matrix4f, wr, 100.0f, -100.0f, -100.0f, tx + 0.33333334f, ty);
        tess.b();
    }
    
    private void addVertex(final Matrix4f matrix4f, final eie buffer, final float x, final float y, final float z, final float u, final float v) {
        final float xt = MathUtils.getTransformX(matrix4f, x, y, z, 1.0f);
        final float yt = MathUtils.getTransformY(matrix4f, x, y, z, 1.0f);
        final float zt = MathUtils.getTransformZ(matrix4f, x, y, z, 1.0f);
        buffer.a((double)xt, (double)yt, (double)zt).a(u, v).e();
    }
    
    public boolean isActive(final cmm world, final int timeOfDay) {
        if (world != this.lastWorld) {
            this.lastWorld = world;
            this.smoothPositionBrightness = null;
        }
        if (this.timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn)) {
            return false;
        }
        if (this.days != null) {
            final long time = world.W();
            long timeShift;
            for (timeShift = time - this.startFadeIn; timeShift < 0L; timeShift += 24000 * this.daysLoop) {}
            final int day = (int)(timeShift / 24000L);
            final int dayOfLoop = day % this.daysLoop;
            if (!this.days.isInRange(dayOfLoop)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean timeBetween(final int timeOfDay, final int timeStart, final int timeEnd) {
        if (timeStart <= timeEnd) {
            return timeOfDay >= timeStart && timeOfDay <= timeEnd;
        }
        return timeOfDay >= timeStart || timeOfDay <= timeEnd;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;IIII)Ljava/lang/String;, this.source, this.startFadeIn, this.endFadeIn, this.startFadeOut, this.endFadeOut);
    }
    
    static {
        DEFAULT_AXIS = new float[] { 1.0f, 0.0f, 0.0f };
    }
}
