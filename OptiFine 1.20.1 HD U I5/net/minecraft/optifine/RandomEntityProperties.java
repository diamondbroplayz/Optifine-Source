// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.optifine.util.PropertiesOrdered;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.optifine.config.ConnectedParser;

public class RandomEntityProperties<T>
{
    private String name;
    private String basePath;
    private RandomEntityContext<T> context;
    private T[] resources;
    private RandomEntityRule<T>[] rules;
    private int matchingRuleIndex;
    
    public RandomEntityProperties(final String path, final acq baseLoc, final int[] variants, final RandomEntityContext<T> context) {
        this.name = null;
        this.basePath = null;
        this.resources = null;
        this.rules = null;
        this.matchingRuleIndex = -1;
        final ConnectedParser cp = new ConnectedParser(context.getName());
        this.name = cp.parseName(path);
        this.basePath = cp.parseBasePath(path);
        this.context = context;
        this.resources = (T[])new Object[variants.length];
        for (int i = 0; i < variants.length; ++i) {
            final int index = variants[i];
            this.resources[i] = (T)context.makeResource(baseLoc, index);
        }
    }
    
    public RandomEntityProperties(final Properties props, final String path, final acq baseResLoc, final RandomEntityContext<T> context) {
        this.name = null;
        this.basePath = null;
        this.resources = null;
        this.rules = null;
        this.matchingRuleIndex = -1;
        final ConnectedParser cp = context.getConnectedParser();
        this.name = cp.parseName(path);
        this.basePath = cp.parseBasePath(path);
        this.context = context;
        this.rules = (RandomEntityRule<T>[])this.parseRules(props, path, baseResLoc);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getBasePath() {
        return this.basePath;
    }
    
    public T[] getResources() {
        return this.resources;
    }
    
    public List<T> getAllResources() {
        final List<T> list = new ArrayList<T>();
        if (this.resources != null) {
            list.addAll((Collection<? extends T>)Arrays.asList(this.resources));
        }
        if (this.rules != null) {
            for (int i = 0; i < this.rules.length; ++i) {
                final RandomEntityRule<T> rule = this.rules[i];
                if (rule.getResources() != null) {
                    list.addAll((Collection<? extends T>)Arrays.asList(rule.getResources()));
                }
            }
        }
        return list;
    }
    
    public T getResource(final IRandomEntity randomEntity, final T resDef) {
        this.matchingRuleIndex = 0;
        if (this.rules != null) {
            for (int i = 0; i < this.rules.length; ++i) {
                final RandomEntityRule<T> rule = this.rules[i];
                if (rule.matches(randomEntity)) {
                    this.matchingRuleIndex = rule.getIndex();
                    return rule.getResource(randomEntity.getId(), resDef);
                }
            }
        }
        if (this.resources != null) {
            final int randomId = randomEntity.getId();
            final int index = randomId % this.resources.length;
            return this.resources[index];
        }
        return resDef;
    }
    
    private RandomEntityRule<T>[] parseRules(final Properties props, final String pathProps, final acq baseResLoc) {
        final List list = new ArrayList();
        for (int maxIndex = 10, i = 0; i < maxIndex; ++i) {
            final int index = i + 1;
            String valTextures = null;
            final String[] resourceKeys;
            final String[] keys = resourceKeys = this.context.getResourceKeys();
            for (final String key : resourceKeys) {
                valTextures = props.getProperty(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, key, index));
                if (valTextures != null) {
                    break;
                }
            }
            if (valTextures != null) {
                final RandomEntityRule<T> rule = new RandomEntityRule<T>(props, pathProps, baseResLoc, index, valTextures, (RandomEntityContext)this.context);
                list.add(rule);
                maxIndex = index + 10;
            }
        }
        final RandomEntityRule<T>[] rules = list.toArray(new RandomEntityRule[list.size()]);
        return rules;
    }
    
    public boolean isValid(final String path) {
        final String resNamePlural = this.context.getResourceNamePlural();
        if (this.resources == null && this.rules == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, resNamePlural, path));
            return false;
        }
        if (this.rules != null) {
            for (int i = 0; i < this.rules.length; ++i) {
                final RandomEntityRule rule = this.rules[i];
                if (!rule.isValid(path)) {
                    return false;
                }
            }
        }
        if (this.resources != null) {
            for (int i = 0; i < this.resources.length; ++i) {
                final T res = this.resources[i];
                if (res == null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isDefault() {
        return this.rules == null && this.resources == null;
    }
    
    public int getMatchingRuleIndex() {
        return this.matchingRuleIndex;
    }
    
    public static RandomEntityProperties parse(final acq propLoc, final acq resLoc, final RandomEntityContext context) {
        final String contextName = context.getName();
        try {
            final String path = propLoc.a();
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, contextName, resLoc.a(), path));
            final InputStream in = Config.getResourceStream(propLoc);
            if (in == null) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, contextName, path));
                return null;
            }
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            final RandomEntityProperties rep = new RandomEntityProperties(props, path, resLoc, context);
            if (!rep.isValid(path)) {
                return null;
            }
            return rep;
        }
        catch (FileNotFoundException e2) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, contextName, propLoc.a()));
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.name, this.basePath);
    }
}
