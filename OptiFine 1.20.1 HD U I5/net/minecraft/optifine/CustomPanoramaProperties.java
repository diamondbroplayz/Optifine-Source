// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.config.ConnectedParser;
import java.util.Properties;

public class CustomPanoramaProperties
{
    private String path;
    private acq[] panoramaLocations;
    private int weight;
    private int blur1;
    private int blur2;
    private int blur3;
    private int overlay1Top;
    private int overlay1Bottom;
    private int overlay2Top;
    private int overlay2Bottom;
    
    public CustomPanoramaProperties(final String path, final Properties props) {
        this.weight = 1;
        this.blur1 = 64;
        this.blur2 = 3;
        this.blur3 = 3;
        this.overlay1Top = -2130706433;
        this.overlay1Bottom = 16777215;
        this.overlay2Top = 0;
        this.overlay2Bottom = Integer.MIN_VALUE;
        final ConnectedParser cp = new ConnectedParser("CustomPanorama");
        this.path = path;
        this.panoramaLocations = new acq[6];
        for (int i = 0; i < this.panoramaLocations.length; ++i) {
            this.panoramaLocations[i] = new acq(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, path, i));
        }
        this.weight = cp.parseInt(props.getProperty("weight"), 1);
        this.blur1 = cp.parseInt(props.getProperty("blur1"), 64);
        this.blur2 = cp.parseInt(props.getProperty("blur2"), 3);
        this.blur3 = cp.parseInt(props.getProperty("blur3"), 3);
        this.overlay1Top = ConnectedParser.parseColor4(props.getProperty("overlay1.top"), -2130706433);
        this.overlay1Bottom = ConnectedParser.parseColor4(props.getProperty("overlay1.bottom"), 16777215);
        this.overlay2Top = ConnectedParser.parseColor4(props.getProperty("overlay2.top"), 0);
        this.overlay2Bottom = ConnectedParser.parseColor4(props.getProperty("overlay2.bottom"), Integer.MIN_VALUE);
    }
    
    public acq[] getPanoramaLocations() {
        return this.panoramaLocations;
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    public int getBlur1() {
        return this.blur1;
    }
    
    public int getBlur2() {
        return this.blur2;
    }
    
    public int getBlur3() {
        return this.blur3;
    }
    
    public int getOverlay1Top() {
        return this.overlay1Top;
    }
    
    public int getOverlay1Bottom() {
        return this.overlay1Bottom;
    }
    
    public int getOverlay2Top() {
        return this.overlay2Top;
    }
    
    public int getOverlay2Bottom() {
        return this.overlay2Bottom;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;IIIIIIII)Ljava/lang/String;, this.path, this.weight, this.blur1, this.blur2, this.blur3, this.overlay1Top, this.overlay1Bottom, this.overlay2Top, this.overlay2Bottom);
    }
}
