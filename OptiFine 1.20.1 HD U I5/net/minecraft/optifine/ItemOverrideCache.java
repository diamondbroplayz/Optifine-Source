// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Iterator;
import java.util.Collection;
import com.google.common.primitives.Floats;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedHashMap;
import net.optifine.reflect.Reflector;
import java.util.List;
import java.util.HashMap;
import net.optifine.util.CompoundKey;
import java.util.Map;

public class ItemOverrideCache
{
    private ItemOverrideProperty[] itemOverrideProperties;
    private Map<CompoundKey, Integer> mapModelIndexes;
    public static final Integer INDEX_NONE;
    
    public ItemOverrideCache(final ItemOverrideProperty[] itemOverrideProperties) {
        this.mapModelIndexes = new HashMap<CompoundKey, Integer>();
        this.itemOverrideProperties = itemOverrideProperties;
    }
    
    public Integer getModelIndex(final cfz stack, final few world, final bfz entity) {
        final CompoundKey valueKey = this.getValueKey(stack, world, entity);
        if (valueKey == null) {
            return null;
        }
        return this.mapModelIndexes.get(valueKey);
    }
    
    public void putModelIndex(final cfz stack, final few world, final bfz entity, final Integer index) {
        final CompoundKey valueKey = this.getValueKey(stack, world, entity);
        if (valueKey == null) {
            return;
        }
        this.mapModelIndexes.put(valueKey, index);
    }
    
    private CompoundKey getValueKey(final cfz stack, final few world, final bfz entity) {
        final Integer[] indexes = new Integer[this.itemOverrideProperties.length];
        for (int i = 0; i < indexes.length; ++i) {
            final Integer index = this.itemOverrideProperties[i].getValueIndex(stack, world, entity);
            if (index == null) {
                return null;
            }
            indexes[i] = index;
        }
        return new CompoundKey(indexes);
    }
    
    public static ItemOverrideCache make(final List<fla> overrides) {
        if (overrides.isEmpty()) {
            return null;
        }
        if (!Reflector.ItemOverride_listResourceValues.exists()) {
            return null;
        }
        final Map<acq, Set<Float>> propertyValues = new LinkedHashMap<acq, Set<Float>>();
        for (final fla itemOverride : overrides) {
            final List<fla.b> resourceValues = (List<fla.b>)Reflector.getFieldValue(itemOverride, Reflector.ItemOverride_listResourceValues);
            for (final fla.b resourceValue : resourceValues) {
                final acq loc = resourceValue.a();
                final float val = resourceValue.b();
                Set<Float> setValues = propertyValues.get(loc);
                if (setValues == null) {
                    setValues = new HashSet<Float>();
                    propertyValues.put(loc, setValues);
                }
                setValues.add(val);
            }
        }
        final List<ItemOverrideProperty> listProps = new ArrayList<ItemOverrideProperty>();
        final Set<acq> setPropertyLocations = propertyValues.keySet();
        for (final acq loc2 : setPropertyLocations) {
            final Set<Float> setValues2 = propertyValues.get(loc2);
            final float[] values = Floats.toArray((Collection)setValues2);
            final ItemOverrideProperty prop = new ItemOverrideProperty(loc2, values);
            listProps.add(prop);
        }
        final ItemOverrideProperty[] props = listProps.toArray(new ItemOverrideProperty[listProps.size()]);
        final ItemOverrideCache cache = new ItemOverrideCache(props);
        logCache(props, overrides);
        return cache;
    }
    
    private static void logCache(final ItemOverrideProperty[] props, final List<fla> overrides) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < props.length; ++i) {
            final ItemOverrideProperty prop = props[i];
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(invokedynamic(makeConcatWithConstants:(Lacq;I)Ljava/lang/String;, prop.getLocation(), prop.getValues().length));
        }
        if (overrides.size() > 0) {
            sb.append(invokedynamic(makeConcatWithConstants:(Lacq;)Ljava/lang/String;, overrides.get(0).a()));
        }
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, sb.toString()));
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, this.itemOverrideProperties.length, this.mapModelIndexes.size());
    }
    
    static {
        INDEX_NONE = new Integer(-1);
    }
}
