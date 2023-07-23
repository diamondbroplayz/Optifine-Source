// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.config.MatchBlock;
import net.optifine.config.ConnectedParser;
import net.optifine.render.RenderTypes;
import net.optifine.util.PropertiesOrdered;
import java.util.List;
import java.util.Properties;
import net.optifine.shaders.BlockAliases;
import net.optifine.util.ResUtils;
import java.util.ArrayList;

public class CustomBlockLayers
{
    private static fkf[] renderLayers;
    public static boolean active;
    
    public static fkf getRenderLayer(final cls worldReader, final dcb blockState, final gu blockPos) {
        if (CustomBlockLayers.renderLayers == null) {
            return null;
        }
        if (blockState.i(worldReader, blockPos)) {
            return null;
        }
        final int id = blockState.getBlockId();
        if (id <= 0 || id >= CustomBlockLayers.renderLayers.length) {
            return null;
        }
        return CustomBlockLayers.renderLayers[id];
    }
    
    public static void update() {
        CustomBlockLayers.renderLayers = null;
        CustomBlockLayers.active = false;
        final List<fkf> list = new ArrayList<fkf>();
        final String pathProps = "optifine/block.properties";
        final Properties props = ResUtils.readProperties(pathProps, "CustomBlockLayers");
        if (props != null) {
            readLayers(pathProps, props, list);
        }
        if (Config.isShaders()) {
            final PropertiesOrdered propsShaders = BlockAliases.getBlockLayerPropertes();
            if (propsShaders != null) {
                final String pathPropsShaders = "shaders/block.properties";
                readLayers(pathPropsShaders, propsShaders, list);
            }
        }
        if (list.isEmpty()) {
            return;
        }
        CustomBlockLayers.renderLayers = list.toArray(new fkf[list.size()]);
        CustomBlockLayers.active = true;
    }
    
    private static void readLayers(final String pathProps, final Properties props, final List<fkf> list) {
        Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, pathProps));
        readLayer("solid", RenderTypes.SOLID, props, list);
        readLayer("cutout", RenderTypes.CUTOUT, props, list);
        readLayer("cutout_mipped", RenderTypes.CUTOUT_MIPPED, props, list);
        readLayer("translucent", RenderTypes.TRANSLUCENT, props, list);
    }
    
    private static void readLayer(final String name, final fkf layer, final Properties props, final List<fkf> listLayers) {
        final String key = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name);
        final String val = props.getProperty(key);
        if (val == null) {
            return;
        }
        final ConnectedParser cp = new ConnectedParser("CustomBlockLayers");
        final MatchBlock[] mbs = cp.parseMatchBlocks(val);
        if (mbs == null) {
            return;
        }
        for (int i = 0; i < mbs.length; ++i) {
            final MatchBlock mb = mbs[i];
            final int blockId = mb.getBlockId();
            if (blockId > 0) {
                while (listLayers.size() < blockId + 1) {
                    listLayers.add(null);
                }
                if (listLayers.get(blockId) != null) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, blockId, name));
                }
                listLayers.set(blockId, layer);
            }
        }
    }
    
    public static boolean isActive() {
        return CustomBlockLayers.active;
    }
    
    static {
        CustomBlockLayers.renderLayers = null;
        CustomBlockLayers.active = false;
    }
}
