// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.LinkedHashSet;
import java.util.Collection;
import net.optifine.ConnectedProperties;
import com.google.gson.JsonElement;
import java.util.Map;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import net.optifine.util.BlockUtils;
import com.google.gson.JsonParser;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.optifine.config.MatchBlock;
import java.util.Iterator;
import java.util.Set;
import java.util.Properties;
import net.optifine.util.StrUtils;
import net.optifine.config.ConnectedParser;
import net.optifine.shaders.config.MacroProcessor;
import java.io.IOException;
import net.optifine.reflect.ReflectorForge;
import java.io.InputStream;
import java.util.ArrayList;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import java.util.List;
import net.optifine.util.PropertiesOrdered;

public class BlockAliases
{
    private static BlockAlias[][] blockAliases;
    private static boolean hasAliasMetadata;
    private static PropertiesOrdered blockLayerPropertes;
    private static boolean updateOnResourcesReloaded;
    private static List<List<BlockAlias>> legacyAliases;
    
    public static int getAliasBlockId(final dcb blockState) {
        final int blockId = blockState.getBlockId();
        final int metadata = blockState.getMetadata();
        final BlockAlias alias = getBlockAlias(blockId, metadata);
        if (alias != null) {
            return alias.getAliasBlockId();
        }
        return -1;
    }
    
    public static boolean hasAliasMetadata() {
        return BlockAliases.hasAliasMetadata;
    }
    
    public static int getAliasMetadata(final dcb blockState) {
        if (!BlockAliases.hasAliasMetadata) {
            return 0;
        }
        final int blockId = blockState.getBlockId();
        final int metadata = blockState.getMetadata();
        final BlockAlias alias = getBlockAlias(blockId, metadata);
        if (alias != null) {
            return alias.getAliasMetadata();
        }
        return 0;
    }
    
    public static BlockAlias getBlockAlias(final int blockId, final int metadata) {
        if (BlockAliases.blockAliases == null) {
            return null;
        }
        if (blockId < 0 || blockId >= BlockAliases.blockAliases.length) {
            return null;
        }
        final BlockAlias[] aliases = BlockAliases.blockAliases[blockId];
        if (aliases == null) {
            return null;
        }
        for (int i = 0; i < aliases.length; ++i) {
            final BlockAlias ba = aliases[i];
            if (ba.matches(blockId, metadata)) {
                return ba;
            }
        }
        return null;
    }
    
    public static BlockAlias[] getBlockAliases(final int blockId) {
        if (BlockAliases.blockAliases == null) {
            return null;
        }
        if (blockId < 0 || blockId >= BlockAliases.blockAliases.length) {
            return null;
        }
        final BlockAlias[] aliases = BlockAliases.blockAliases[blockId];
        return aliases;
    }
    
    public static void resourcesReloaded() {
        if (!BlockAliases.updateOnResourcesReloaded) {
            return;
        }
        BlockAliases.updateOnResourcesReloaded = false;
        update(Shaders.getShaderPack());
    }
    
    public static void update(final IShaderPack shaderPack) {
        reset();
        if (shaderPack == null) {
            return;
        }
        if (shaderPack instanceof ShaderPackNone) {
            return;
        }
        if (Reflector.ModList.exists() && enn.N().Y() == null) {
            Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
            BlockAliases.updateOnResourcesReloaded = true;
            return;
        }
        List<List<BlockAlias>> listBlockAliases = new ArrayList<List<BlockAlias>>();
        final String path = "/shaders/block.properties";
        final InputStream in = shaderPack.getResourceAsStream(path);
        if (in != null) {
            loadBlockAliases(in, path, listBlockAliases);
        }
        loadModBlockAliases(listBlockAliases);
        if (listBlockAliases.size() <= 0) {
            listBlockAliases = getLegacyAliases();
            BlockAliases.hasAliasMetadata = true;
        }
        BlockAliases.blockAliases = toBlockAliasArrays(listBlockAliases);
    }
    
    private static void loadModBlockAliases(final List<List<BlockAlias>> listBlockAliases) {
        final String[] modIds = ReflectorForge.getForgeModIds();
        for (int i = 0; i < modIds.length; ++i) {
            final String modId = modIds[i];
            try {
                final acq loc = new acq(modId, "shaders/block.properties");
                final InputStream in = Config.getResourceStream(loc);
                loadBlockAliases(in, loc.toString(), listBlockAliases);
            }
            catch (IOException ex) {}
        }
    }
    
    private static void loadBlockAliases(InputStream in, final String path, final List<List<BlockAlias>> listBlockAliases) {
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
                if (key.startsWith("layer.")) {
                    if (BlockAliases.blockLayerPropertes == null) {
                        BlockAliases.blockLayerPropertes = new PropertiesOrdered();
                    }
                    BlockAliases.blockLayerPropertes.put(key, val);
                }
                else {
                    final String prefix = "block.";
                    if (!key.startsWith(prefix)) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, key));
                    }
                    else {
                        final String blockIdStr = StrUtils.removePrefix(key, prefix);
                        final int blockId = Config.parseInt(blockIdStr, -1);
                        if (blockId < 0) {
                            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, key));
                        }
                        else {
                            final MatchBlock[] matchBlocks = cp.parseMatchBlocks(val);
                            if (matchBlocks == null || matchBlocks.length < 1) {
                                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, val));
                            }
                            else {
                                final BlockAlias ba = new BlockAlias(blockId, matchBlocks);
                                addToList(listBlockAliases, ba);
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
    
    private static void addToList(final List<List<BlockAlias>> blocksAliases, final BlockAlias ba) {
        final int[] blockIds = ba.getMatchBlockIds();
        for (int i = 0; i < blockIds.length; ++i) {
            final int blockId = blockIds[i];
            while (blockId >= blocksAliases.size()) {
                blocksAliases.add(null);
            }
            List<BlockAlias> blockAliases = blocksAliases.get(blockId);
            if (blockAliases == null) {
                blockAliases = new ArrayList<BlockAlias>();
                blocksAliases.set(blockId, blockAliases);
            }
            final BlockAlias baBlock = new BlockAlias(ba.getAliasBlockId(), ba.getMatchBlocks(blockId));
            blockAliases.add(baBlock);
        }
    }
    
    private static BlockAlias[][] toBlockAliasArrays(final List<List<BlockAlias>> listBlocksAliases) {
        final BlockAlias[][] bas = new BlockAlias[listBlocksAliases.size()][];
        for (int i = 0; i < bas.length; ++i) {
            final List<BlockAlias> listBlockAliases = listBlocksAliases.get(i);
            if (listBlockAliases != null) {
                bas[i] = listBlockAliases.toArray(new BlockAlias[listBlockAliases.size()]);
            }
        }
        return bas;
    }
    
    private static List<List<BlockAlias>> getLegacyAliases() {
        if (BlockAliases.legacyAliases == null) {
            BlockAliases.legacyAliases = makeLegacyAliases();
        }
        return BlockAliases.legacyAliases;
    }
    
    private static List<List<BlockAlias>> makeLegacyAliases() {
        try {
            final String path = "flattening_ids.txt";
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            final List<List<BlockAlias>> listAliases = new ArrayList<List<BlockAlias>>();
            final List<String> listLines = new ArrayList<String>();
            int countAliases = 0;
            final InputStream in = Config.getOptiFineResourceStream(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            if (in == null) {
                return listAliases;
            }
            final String[] lines = Config.readLines(in);
            for (int i = 0; i < lines.length; ++i) {
                final int lineNum = i + 1;
                final String line = lines[i];
                if (line.trim().length() > 0) {
                    listLines.add(line);
                    if (!line.startsWith("#")) {
                        if (line.startsWith("alias")) {
                            final String[] parts = Config.tokenize(line, " ");
                            final String blockAliasStr = parts[1];
                            final String blockStr = parts[2];
                            final String prefix = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, blockStr);
                            final List<String> blockLines = listLines.stream().filter(s -> s.startsWith(prefix)).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
                            if (blockLines.size() <= 0) {
                                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
                            }
                            else {
                                for (final String lineBlock : blockLines) {
                                    final String prefixNew = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, blockAliasStr);
                                    final String lineNew = lineBlock.replace(prefix, prefixNew);
                                    listLines.add(lineNew);
                                    addLegacyAlias(lineNew, lineNum, listAliases);
                                    ++countAliases;
                                }
                            }
                        }
                        else {
                            addLegacyAlias(line, lineNum, listAliases);
                            ++countAliases;
                        }
                    }
                }
            }
            Config.dbg(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, countAliases));
            return listAliases;
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
            return new ArrayList<List<BlockAlias>>();
        }
    }
    
    private static void addLegacyAlias(final String line, final int lineNum, final List<List<BlockAlias>> listAliases) {
        final String[] parts = Config.tokenize(line, " ");
        if (parts.length != 4) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
            return;
        }
        final String partNew = parts[0];
        final String partOld = parts[1];
        final int blockIdOld = Config.parseInt(parts[2], Integer.MIN_VALUE);
        final int metadataOld = Config.parseInt(parts[3], Integer.MIN_VALUE);
        if (blockIdOld < 0 || metadataOld < 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(III)Ljava/lang/String;, lineNum, blockIdOld, metadataOld));
            return;
        }
        try {
            final JsonParser jp = new JsonParser();
            final JsonObject jo = jp.parse(partNew).getAsJsonObject();
            final String name = jo.get("Name").getAsString();
            final acq loc = new acq(name);
            final cpn block = BlockUtils.getBlock(loc);
            if (block == null) {
                Config.warn(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, lineNum, name));
                return;
            }
            final dcb blockState = block.n();
            final Collection<dde<?>> stateProperties = (Collection<dde<?>>)blockState.B();
            final Map<dde, Comparable> mapProperties = new LinkedHashMap<dde, Comparable>();
            final JsonObject properties = (JsonObject)jo.get("Properties");
            if (properties != null) {
                final Set<Map.Entry<String, JsonElement>> entries = (Set<Map.Entry<String, JsonElement>>)properties.entrySet();
                for (final Map.Entry<String, JsonElement> entry : entries) {
                    final String key = entry.getKey();
                    final String value = entry.getValue().getAsString();
                    final dde prop = ConnectedProperties.getProperty(key, stateProperties);
                    if (prop == null) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, lineNum, key));
                    }
                    else {
                        final Comparable propVal = ConnectedParser.parsePropertyValue(prop, value);
                        if (propVal == null) {
                            Config.warn(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, lineNum, value));
                        }
                        else {
                            mapProperties.put(prop, propVal);
                        }
                    }
                }
            }
            final int blockId = blockState.getBlockId();
            while (listAliases.size() <= blockId) {
                listAliases.add(null);
            }
            List<BlockAlias> las = listAliases.get(blockId);
            if (las == null) {
                las = new ArrayList<BlockAlias>(BlockUtils.getMetadataCount(block));
                listAliases.set(blockId, las);
            }
            final MatchBlock mb = getMatchBlock(blockState.b(), blockState.getBlockId(), mapProperties);
            addBlockAlias(las, blockIdOld, metadataOld, mb);
        }
        catch (Exception e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
        }
    }
    
    private static void addBlockAlias(final List<BlockAlias> listBlockAliases, final int aliasBlockId, final int aliasMetadata, final MatchBlock matchBlock) {
        for (final BlockAlias ba : listBlockAliases) {
            if (ba.getAliasBlockId() != aliasBlockId) {
                continue;
            }
            if (ba.getAliasMetadata() != aliasMetadata) {
                continue;
            }
            final MatchBlock[] mbs = ba.getMatchBlocks();
            for (int i = 0; i < mbs.length; ++i) {
                final MatchBlock mb = mbs[i];
                if (mb.getBlockId() == matchBlock.getBlockId()) {
                    mb.addMetadatas(matchBlock.getMetadatas());
                    return;
                }
            }
        }
        final BlockAlias ba2 = new BlockAlias(aliasBlockId, aliasMetadata, new MatchBlock[] { matchBlock });
        listBlockAliases.add(ba2);
    }
    
    private static MatchBlock getMatchBlock(final cpn block, final int blockId, final Map<dde, Comparable> mapProperties) {
        final List<dcb> matchingStates = new ArrayList<dcb>();
        final Collection<dde> props = mapProperties.keySet();
        final List<dcb> states = (List<dcb>)BlockUtils.getBlockStates(block);
        for (final dcb bs : states) {
            boolean match = true;
            for (final dde prop : props) {
                if (!bs.b(prop)) {
                    match = false;
                    break;
                }
                final Comparable val1 = mapProperties.get(prop);
                final Comparable val2 = bs.c(prop);
                if (!val1.equals(val2)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                matchingStates.add(bs);
            }
        }
        final Set<Integer> setMetadatas = new LinkedHashSet<Integer>();
        for (final dcb bs2 : matchingStates) {
            setMetadatas.add(bs2.getMetadata());
        }
        final Integer[] metadatas = setMetadatas.toArray(new Integer[setMetadatas.size()]);
        final int[] mds = Config.toPrimitive(metadatas);
        final MatchBlock mb = new MatchBlock(blockId, mds);
        return mb;
    }
    
    private static void checkLegacyAliases() {
        final Set<acq> locs = (Set<acq>)jb.f.e();
        for (final acq loc : locs) {
            final cpn block = (cpn)jb.f.a(loc);
            final int blockId = block.n().getBlockId();
            final BlockAlias[] bas = getBlockAliases(blockId);
            if (bas == null) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Lcpn;)Ljava/lang/String;, block));
            }
            else {
                final List<dcb> states = (List<dcb>)BlockUtils.getBlockStates(block);
                for (final dcb state : states) {
                    final int metadata = state.getMetadata();
                    final BlockAlias ba = getBlockAlias(blockId, metadata);
                    if (ba == null) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ldcb;)Ljava/lang/String;, state));
                    }
                }
            }
        }
    }
    
    public static PropertiesOrdered getBlockLayerPropertes() {
        return BlockAliases.blockLayerPropertes;
    }
    
    public static void reset() {
        BlockAliases.blockAliases = null;
        BlockAliases.hasAliasMetadata = false;
        BlockAliases.blockLayerPropertes = null;
    }
    
    public static int getRenderType(final dcb blockState) {
        if (!BlockAliases.hasAliasMetadata) {
            return blockState.l().ordinal();
        }
        final cpn block = blockState.b();
        if (block instanceof cua) {
            return 1;
        }
        final cvs brt = blockState.l();
        if (brt == cvs.b || brt == cvs.c) {
            return brt.ordinal() + 1;
        }
        return brt.ordinal();
    }
    
    static {
        BlockAliases.blockAliases = null;
        BlockAliases.hasAliasMetadata = false;
        BlockAliases.blockLayerPropertes = null;
    }
}
