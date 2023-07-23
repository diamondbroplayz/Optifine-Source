// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.function.Supplier;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Properties;

public class CustomLoadingScreen
{
    private acq locationTexture;
    private int scaleMode;
    private int scale;
    private boolean center;
    private static final int SCALE_DEFAULT = 2;
    private static final int SCALE_MODE_FIXED = 0;
    private static final int SCALE_MODE_FULL = 1;
    private static final int SCALE_MODE_STRETCH = 2;
    
    public CustomLoadingScreen(final acq locationTexture, final int scaleMode, final int scale, final boolean center) {
        this.scaleMode = 0;
        this.scale = 2;
        this.locationTexture = locationTexture;
        this.scaleMode = scaleMode;
        this.scale = scale;
        this.center = center;
    }
    
    public static CustomLoadingScreen parseScreen(final String path, final int dimId, final Properties props) {
        final acq loc = new acq(path);
        final int scaleMode = parseScaleMode(getProperty("scaleMode", dimId, props));
        final int scaleDef = (scaleMode == 0) ? 2 : 1;
        final int scale = parseScale(getProperty("scale", dimId, props), scaleDef);
        final boolean center = Config.parseBoolean(getProperty("center", dimId, props), false);
        final CustomLoadingScreen scr = new CustomLoadingScreen(loc, scaleMode, scale, center);
        return scr;
    }
    
    private static String getProperty(final String key, final int dim, final Properties props) {
        if (props == null) {
            return null;
        }
        String val = props.getProperty(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, dim, key));
        if (val != null) {
            return val;
        }
        val = props.getProperty(key);
        return val;
    }
    
    private static int parseScaleMode(String str) {
        if (str == null) {
            return 0;
        }
        str = str.toLowerCase().trim();
        if (str.equals("fixed")) {
            return 0;
        }
        if (str.equals("full")) {
            return 1;
        }
        if (str.equals("stretch")) {
            return 2;
        }
        CustomLoadingScreens.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return 0;
    }
    
    private static int parseScale(String str, final int def) {
        if (str == null) {
            return def;
        }
        str = str.trim();
        final int val = Config.parseInt(str, -1);
        if (val < 1) {
            CustomLoadingScreens.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return def;
        }
        return val;
    }
    
    public void drawBackground(final int width, final int height) {
        final eil tessellator = eil.a();
        final eie bufferbuilder = tessellator.c();
        RenderSystem.setShader((Supplier)fjq::t);
        RenderSystem.setShaderTexture(0, this.locationTexture);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        float div = (float)(16 * this.scale);
        float uMax = width / div;
        float vMax = height / div;
        float du = 0.0f;
        float dv = 0.0f;
        if (this.center) {
            du = (div - width) / (div * 2.0f);
            dv = (div - height) / (div * 2.0f);
        }
        switch (this.scaleMode) {
            case 1: {
                div = (float)Math.max(width, height);
                uMax = this.scale * width / div;
                vMax = this.scale * height / div;
                if (this.center) {
                    du = this.scale * (div - width) / (div * 2.0f);
                    dv = this.scale * (div - height) / (div * 2.0f);
                    break;
                }
                break;
            }
            case 2: {
                uMax = (float)this.scale;
                vMax = (float)this.scale;
                du = 0.0f;
                dv = 0.0f;
                break;
            }
        }
        bufferbuilder.a(eio.b.h, eih.s);
        bufferbuilder.a(0.0, (double)height, 0.0).a(du, dv + vMax).a(255, 255, 255, 255).e();
        bufferbuilder.a((double)width, (double)height, 0.0).a(du + uMax, dv + vMax).a(255, 255, 255, 255).e();
        bufferbuilder.a((double)width, 0.0, 0.0).a(du + uMax, dv).a(255, 255, 255, 255).e();
        bufferbuilder.a(0.0, 0.0, 0.0).a(du, dv).a(255, 255, 255, 255).e();
        tessellator.b();
    }
}
