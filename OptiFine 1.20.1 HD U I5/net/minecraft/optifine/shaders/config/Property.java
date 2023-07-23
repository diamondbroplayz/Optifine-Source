// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import java.util.Properties;
import net.optifine.Config;
import org.apache.commons.lang3.ArrayUtils;

public class Property
{
    private int defaultValue;
    private String propertyName;
    private String[] propertyValues;
    private String userName;
    private String[] userValues;
    private int value;
    
    public Property(final String propertyName, final String[] propertyValues, final String userName, final String[] userValues, final int defaultValue) {
        this.defaultValue = 0;
        this.propertyName = null;
        this.propertyValues = null;
        this.userName = null;
        this.userValues = null;
        this.value = 0;
        this.propertyName = propertyName;
        this.propertyValues = propertyValues;
        this.userName = userName;
        this.userValues = userValues;
        this.defaultValue = defaultValue;
        if (propertyValues.length != userValues.length) {
            throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, propertyValues.length, userValues.length));
        }
        if (defaultValue < 0 || defaultValue >= propertyValues.length) {
            throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, defaultValue));
        }
        this.value = defaultValue;
    }
    
    public boolean setPropertyValue(final String propVal) {
        if (propVal == null) {
            this.value = this.defaultValue;
            return false;
        }
        this.value = ArrayUtils.indexOf((Object[])this.propertyValues, (Object)propVal);
        if (this.value < 0 || this.value >= this.propertyValues.length) {
            this.value = this.defaultValue;
            return false;
        }
        return true;
    }
    
    public void nextValue(final boolean forward) {
        final int min = 0;
        final int max = this.propertyValues.length - 1;
        this.value = Config.limit(this.value, min, max);
        if (forward) {
            ++this.value;
            if (this.value > max) {
                this.value = min;
            }
        }
        else {
            --this.value;
            if (this.value < min) {
                this.value = max;
            }
        }
    }
    
    public void setValue(final int val) {
        this.value = val;
        if (this.value < 0 || this.value >= this.propertyValues.length) {
            this.value = this.defaultValue;
        }
    }
    
    public int getValue() {
        return this.value;
    }
    
    public String getUserValue() {
        return this.userValues[this.value];
    }
    
    public String getPropertyValue() {
        return this.propertyValues[this.value];
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public void resetValue() {
        this.value = this.defaultValue;
    }
    
    public boolean loadFrom(final Properties props) {
        this.resetValue();
        if (props == null) {
            return false;
        }
        final String str = props.getProperty(this.propertyName);
        return str != null && this.setPropertyValue(str);
    }
    
    public void saveTo(final Properties props) {
        if (props == null) {
            return;
        }
        props.setProperty(this.getPropertyName(), this.getPropertyValue());
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;, this.propertyName, this.getPropertyValue(), Config.arrayToString(this.propertyValues), this.value);
    }
}
