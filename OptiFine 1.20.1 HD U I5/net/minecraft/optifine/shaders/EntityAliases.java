// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.Iterator;
import java.util.Set;
import java.util.Properties;
import net.optifine.util.StrUtils;
import net.optifine.config.ConnectedParser;
import net.optifine.util.PropertiesOrdered;
import net.optifine.shaders.config.MacroProcessor;
import java.io.IOException;
import net.optifine.reflect.ReflectorForge;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class EntityAliases
{
    private static int[] entityAliases;
    private static boolean updateOnResourcesReloaded;
    
    public static int getEntityAliasId(final int entityId) {
        if (EntityAliases.entityAliases == null) {
            return -1;
        }
        if (entityId < 0 || entityId >= EntityAliases.entityAliases.length) {
            return -1;
        }
        final int aliasId = EntityAliases.entityAliases[entityId];
        return aliasId;
    }
    
    public static void resourcesReloaded() {
        if (!EntityAliases.updateOnResourcesReloaded) {
            return;
        }
        EntityAliases.updateOnResourcesReloaded = false;
        update(Shaders.getShaderPack());
    }
    
    public static void update(final IShaderPack shaderPack) {
        reset();
        if (shaderPack == null) {
            return;
        }
        if (Reflector.ModList.exists() && enn.N().Y() == null) {
            Config.dbg("[Shaders] Delayed loading of entity mappings after resources are loaded");
            EntityAliases.updateOnResourcesReloaded = true;
            return;
        }
        final List<Integer> listEntityAliases = new ArrayList<Integer>();
        final String path = "/shaders/entity.properties";
        final InputStream in = shaderPack.getResourceAsStream(path);
        if (in != null) {
            loadEntityAliases(in, path, listEntityAliases);
        }
        loadModEntityAliases(listEntityAliases);
        if (listEntityAliases.size() <= 0) {
            return;
        }
        EntityAliases.entityAliases = toArray(listEntityAliases);
    }
    
    private static void loadModEntityAliases(final List<Integer> listEntityAliases) {
        final String[] modIds = ReflectorForge.getForgeModIds();
        for (int i = 0; i < modIds.length; ++i) {
            final String modId = modIds[i];
            try {
                final acq loc = new acq(modId, "shaders/entity.properties");
                final InputStream in = Config.getResourceStream(loc);
                loadEntityAliases(in, loc.toString(), listEntityAliases);
            }
            catch (IOException ex) {}
        }
    }
    
    private static void loadEntityAliases(InputStream in, final String path, final List<Integer> listEntityAliases) {
        if (in == null) {
            return;
        }
        try {
            in = MacroProcessor.process(in, path, true);
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            final ConnectedParser cp = new ConnectedParser("Shaders");
            final Set keys = props.keySet();
            for (final String key : keys) {
                final String val = props.getProperty(key);
                final String prefix = "entity.";
                if (!key.startsWith(prefix)) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, key));
                }
                else {
                    final String aliasIdStr = StrUtils.removePrefix(key, prefix);
                    final int aliasId = Config.parseInt(aliasIdStr, -1);
                    if (aliasId < 0) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, aliasId));
                    }
                    else {
                        final int[] entityIds = cp.parseEntities(val);
                        if (entityIds == null || entityIds.length < 1) {
                            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, val));
                        }
                        else {
                            for (int i = 0; i < entityIds.length; ++i) {
                                final int entityId = entityIds[i];
                                addToList(listEntityAliases, entityId, aliasId);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
        }
    }
    
    private static void addToList(final List<Integer> list, final int index, final int val) {
        while (list.size() <= index) {
            list.add(-1);
        }
        list.set(index, val);
    }
    
    private static int[] toArray(final List<Integer> list) {
        final int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = list.get(i);
        }
        return arr;
    }
    
    public static void reset() {
        EntityAliases.entityAliases = null;
    }
    
    static {
        EntityAliases.entityAliases = null;
    }
}
