// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Arrays;

public class ItemOverrideProperty
{
    private acq location;
    private float[] values;
    
    public ItemOverrideProperty(final acq location, final float[] values) {
        this.location = location;
        Arrays.sort(this.values = values.clone());
    }
    
    public Integer getValueIndex(final cfz stack, final few world, final bfz entity) {
        final cfu item = stack.d();
        final fud itemPropertyGetter = fuc.a(item, this.location);
        if (itemPropertyGetter == null) {
            return null;
        }
        final float val = itemPropertyGetter.call(stack, world, entity, 0);
        final int index = Arrays.binarySearch(this.values, val);
        return index;
    }
    
    public acq getLocation() {
        return this.location;
    }
    
    public float[] getValues() {
        return this.values;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Lacq;Ljava/lang/String;)Ljava/lang/String;, this.location, Config.arrayToString(this.values));
    }
}
