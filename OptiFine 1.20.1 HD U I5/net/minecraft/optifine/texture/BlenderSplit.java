// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.texture;

import net.optifine.util.IntArray;

public class BlenderSplit implements IBlender
{
    private int startHigh;
    private boolean discreteHigh;
    
    public BlenderSplit(final int startHigh, final boolean discreteHigh) {
        this.startHigh = startHigh;
        this.discreteHigh = discreteHigh;
    }
    
    @Override
    public int blend(final int v1, final int v2, final int v3, final int v4) {
        if (v1 == v2 && v2 == v3 && v3 == v4) {
            return v1;
        }
        final boolean low1 = v1 < this.startHigh;
        final boolean low2 = v2 < this.startHigh;
        final boolean low3 = v3 < this.startHigh;
        final boolean low4 = v4 < this.startHigh;
        if (low1 == low2 && low2 == low3 && low3 == low4) {
            if (!low1 && this.discreteHigh) {
                return v1;
            }
            return (v1 + v2 + v3 + v4) / 4;
        }
        else {
            final IntArray lows = new IntArray(4);
            final IntArray highs = new IntArray(4);
            this.separate(v1, lows, highs);
            this.separate(v2, lows, highs);
            this.separate(v3, lows, highs);
            this.separate(v4, lows, highs);
            if (highs.getPosition() <= lows.getPosition()) {
                return this.getAverage(lows);
            }
            if (this.discreteHigh) {
                return highs.get(0);
            }
            return this.getAverage(highs);
        }
    }
    
    private void separate(final int val, final IntArray low, final IntArray high) {
        if (val < this.startHigh) {
            low.put(val);
        }
        else {
            high.put(val);
        }
    }
    
    private int getAverage(final IntArray arr) {
        final int count = arr.getLimit();
        switch (count) {
            case 3: {
                return (arr.get(0) + arr.get(1) + arr.get(2)) / 3;
            }
            case 2: {
                return (arr.get(0) + arr.get(1)) / 2;
            }
            default: {
                int val = 0;
                for (int i = 0; i < count; ++i) {
                    val += arr.get(i);
                }
                return val / count;
            }
        }
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(IZ)Ljava/lang/String;, this.startHigh, this.discreteHigh);
    }
    
    public static void main(final String[] args) {
        final BlenderSplit bs = new BlenderSplit(230, true);
        System.out.println(invokedynamic(makeConcatWithConstants:(Lnet/optifine/texture/BlenderSplit;)Ljava/lang/String;, bs));
        final int v1 = bs.blend(10, 20, 30, 40);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v1));
        final int v2 = bs.blend(10, 20, 30, 230);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v2));
        final int v3 = bs.blend(10, 20, 240, 230);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v3));
        final int v4 = bs.blend(10, 250, 240, 230);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v4));
        final int v5 = bs.blend(245, 250, 240, 230);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v5));
        final int v6 = bs.blend(10, 10, 10, 10);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v6));
        final BlenderSplit bs2 = new BlenderSplit(65, false);
        System.out.println(invokedynamic(makeConcatWithConstants:(Lnet/optifine/texture/BlenderSplit;)Ljava/lang/String;, bs2));
        final int v7 = bs2.blend(10, 20, 30, 40);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v7));
        final int v8 = bs2.blend(10, 20, 30, 70);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v8));
        final int v9 = bs2.blend(10, 90, 20, 70);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v9));
        final int v10 = bs2.blend(110, 90, 20, 70);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v10));
        final int v11 = bs2.blend(110, 90, 130, 70);
        System.out.println(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, v11));
    }
}
