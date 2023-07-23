// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

public enum Weather
{
    CLEAR, 
    RAIN, 
    THUNDER;
    
    public static Weather getWeather(final cmm world, final float partialTicks) {
        final float thunderStrength = world.b(partialTicks);
        if (thunderStrength > 0.5f) {
            return Weather.THUNDER;
        }
        final float rainStrength = world.d(partialTicks);
        if (rainStrength > 0.5f) {
            return Weather.RAIN;
        }
        return Weather.CLEAR;
    }
    
    private static /* synthetic */ Weather[] $values() {
        return new Weather[] { Weather.CLEAR, Weather.RAIN, Weather.THUNDER };
    }
    
    static {
        $VALUES = $values();
    }
}
