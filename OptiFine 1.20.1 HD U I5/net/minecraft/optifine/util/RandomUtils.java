// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Random;

public class RandomUtils
{
    private static final Random random;
    
    public static Random getRandom() {
        return RandomUtils.random;
    }
    
    public static byte[] getRandomBytes(final int length) {
        final byte[] bytes = new byte[length];
        RandomUtils.random.nextBytes(bytes);
        return bytes;
    }
    
    public static int getRandomInt(final int bound) {
        return RandomUtils.random.nextInt(bound);
    }
    
    public static apf makeThreadSafeRandomSource(final int seed) {
        return (apf)new did((long)seed);
    }
    
    static {
        random = new Random();
    }
}
