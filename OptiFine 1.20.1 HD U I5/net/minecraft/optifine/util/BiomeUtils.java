// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import net.optifine.override.ChunkCacheOF;
import java.util.ArrayList;
import net.minecraft.resources.ResourceLocation;
import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.List;
import net.optifine.config.BiomeId;
import java.util.Iterator;
import java.util.Set;
import com.mojang.serialization.Lifecycle;
import java.util.function.Supplier;

public class BiomeUtils
{
    private static hr<cnk> defaultBiomeRegistry;
    private static hr<cnk> biomeRegistry;
    private static cmm biomeWorld;
    public static cnk PLAINS;
    public static cnk SUNFLOWER_PLAINS;
    public static cnk SNOWY_PLAINS;
    public static cnk ICE_SPIKES;
    public static cnk DESERT;
    public static cnk WINDSWEPT_HILLS;
    public static cnk WINDSWEPT_GRAVELLY_HILLS;
    public static cnk MUSHROOM_FIELDS;
    public static cnk SWAMP;
    public static cnk MANGROVE_SWAMP;
    public static cnk THE_VOID;
    
    public static void onWorldChanged(final cmm worldIn) {
        BiomeUtils.biomeRegistry = getBiomeRegistry(worldIn);
        BiomeUtils.biomeWorld = worldIn;
        BiomeUtils.PLAINS = (cnk)BiomeUtils.biomeRegistry.a(cnr.b);
        BiomeUtils.SUNFLOWER_PLAINS = (cnk)BiomeUtils.biomeRegistry.a(cnr.c);
        BiomeUtils.SNOWY_PLAINS = (cnk)BiomeUtils.biomeRegistry.a(cnr.d);
        BiomeUtils.ICE_SPIKES = (cnk)BiomeUtils.biomeRegistry.a(cnr.e);
        BiomeUtils.DESERT = (cnk)BiomeUtils.biomeRegistry.a(cnr.f);
        BiomeUtils.WINDSWEPT_HILLS = (cnk)BiomeUtils.biomeRegistry.a(cnr.t);
        BiomeUtils.WINDSWEPT_GRAVELLY_HILLS = (cnk)BiomeUtils.biomeRegistry.a(cnr.u);
        BiomeUtils.MUSHROOM_FIELDS = (cnk)BiomeUtils.biomeRegistry.a(cnr.Y);
        BiomeUtils.SWAMP = (cnk)BiomeUtils.biomeRegistry.a(cnr.g);
        BiomeUtils.MANGROVE_SWAMP = (cnk)BiomeUtils.biomeRegistry.a(cnr.h);
        BiomeUtils.THE_VOID = (cnk)BiomeUtils.biomeRegistry.a(cnr.a);
    }
    
    private static cnk getBiomeSafe(final hr<cnk> registry, final acp<cnk> biomeKey, final Supplier<cnk> biomeDefault) {
        cnk biome = (cnk)registry.a((acp)biomeKey);
        if (biome == null) {
            biome = biomeDefault.get();
        }
        return biome;
    }
    
    public static hr<cnk> getBiomeRegistry(final cmm worldIn) {
        if (worldIn == null) {
            return (hr<cnk>)BiomeUtils.defaultBiomeRegistry;
        }
        if (worldIn == BiomeUtils.biomeWorld) {
            return (hr<cnk>)BiomeUtils.biomeRegistry;
        }
        final hr<cnk> worldBiomeRegistry = (hr<cnk>)worldIn.B_().d(jc.ap);
        final hr<cnk> fixedBiomeRegistry = (hr<cnk>)fixBiomeIds(BiomeUtils.defaultBiomeRegistry, (hr)worldBiomeRegistry);
        return fixedBiomeRegistry;
    }
    
    private static hr<cnk> makeDefaultBiomeRegistry() {
        final hm<cnk> registry = (hm<cnk>)new hm(acp.a(new acq("biomes")), Lifecycle.stable(), true);
        final Set<acp<cnk>> biomeKeys = (Set<acp<cnk>>)cnr.getBiomeKeys();
        for (final acp<cnk> biomeKey : biomeKeys) {
            final cnk.a bb = new cnk.a();
            bb.a(false);
            bb.a(0.0f);
            bb.b(0.0f);
            bb.a(new cnq.a().a(0).b(0).c(0).d(0).a());
            bb.a(new cnw.a().a());
            bb.a(new cnl.a((hf)null, (hf)null).a());
            final cnk biome = bb.a();
            registry.f((Object)biome);
            registry.a((acp)biomeKey, (Object)biome, Lifecycle.stable());
        }
        return (hr<cnk>)registry;
    }
    
    private static hr<cnk> fixBiomeIds(final hr<cnk> idRegistry, final hr<cnk> valueRegistry) {
        final hm<cnk> registry = (hm<cnk>)new hm(acp.a(new acq("biomes")), Lifecycle.stable(), true);
        final Set<acp<cnk>> biomeIdKeys = (Set<acp<cnk>>)idRegistry.f();
        for (final acp<cnk> biomeKey : biomeIdKeys) {
            cnk biome = (cnk)valueRegistry.a((acp)biomeKey);
            if (biome == null) {
                biome = (cnk)idRegistry.a((acp)biomeKey);
            }
            final int id = idRegistry.a((Object)idRegistry.a((acp)biomeKey));
            registry.f((Object)biome);
            registry.a(id, (acp)biomeKey, (Object)biome, Lifecycle.stable());
        }
        final Set<acp<cnk>> biomeValueKeys = (Set<acp<cnk>>)valueRegistry.f();
        for (final acp<cnk> biomeKey2 : biomeValueKeys) {
            if (registry.c((acp)biomeKey2)) {
                continue;
            }
            final cnk biome2 = (cnk)valueRegistry.a((acp)biomeKey2);
            registry.f((Object)biome2);
            registry.a((acp)biomeKey2, (Object)biome2, Lifecycle.stable());
        }
        return (hr<cnk>)registry;
    }
    
    public static hr<cnk> getBiomeRegistry() {
        return (hr<cnk>)BiomeUtils.biomeRegistry;
    }
    
    public static acq getLocation(final cnk biome) {
        return getBiomeRegistry().b((Object)biome);
    }
    
    public static int getId(final cnk biome) {
        return getBiomeRegistry().a((Object)biome);
    }
    
    public static int getId(final acq loc) {
        final cnk biome = getBiome(loc);
        return getBiomeRegistry().a((Object)biome);
    }
    
    public static BiomeId getBiomeId(final acq loc) {
        return BiomeId.make(loc);
    }
    
    public static cnk getBiome(final acq loc) {
        return (cnk)getBiomeRegistry().a(loc);
    }
    
    public static Set<acq> getLocations() {
        return (Set<acq>)getBiomeRegistry().e();
    }
    
    public static List<cnk> getBiomes() {
        return (List<cnk>)Lists.newArrayList((Iterable)BiomeUtils.biomeRegistry);
    }
    
    public static List<BiomeId> getBiomeIds() {
        return getBiomeIds(getLocations());
    }
    
    public static List<BiomeId> getBiomeIds(final Collection<acq> locations) {
        final List<BiomeId> biomeIds = new ArrayList<BiomeId>();
        for (final acq loc : locations) {
            final BiomeId bi = BiomeId.make(loc);
            if (bi == null) {
                continue;
            }
            biomeIds.add(bi);
        }
        return biomeIds;
    }
    
    public static cnk getBiome(final clp lightReader, final gu blockPos) {
        cnk biome = BiomeUtils.PLAINS;
        if (lightReader instanceof ChunkCacheOF) {
            biome = ((ChunkCacheOF)lightReader).getBiome(blockPos);
        }
        else if (lightReader instanceof cmp) {
            biome = (cnk)((cmp)lightReader).s(blockPos).a();
        }
        return biome;
    }
    
    public static BiomeCategory getBiomeCategory(final he<cnk> holder) {
        if (holder.a() == BiomeUtils.THE_VOID) {
            return BiomeCategory.NONE;
        }
        if (holder.a(amv.h)) {
            return BiomeCategory.TAIGA;
        }
        if (holder.a() == BiomeUtils.WINDSWEPT_HILLS || holder.a() == BiomeUtils.WINDSWEPT_GRAVELLY_HILLS) {
            return BiomeCategory.EXTREME_HILLS;
        }
        if (holder.a(amv.i)) {
            return BiomeCategory.JUNGLE;
        }
        if (holder.a(amv.f)) {
            return BiomeCategory.MESA;
        }
        if (holder.a() == BiomeUtils.PLAINS || holder.a() == BiomeUtils.PLAINS) {
            return BiomeCategory.PLAINS;
        }
        if (holder.a(amv.k)) {
            return BiomeCategory.SAVANNA;
        }
        if (holder.a() == BiomeUtils.SNOWY_PLAINS || holder.a() == BiomeUtils.ICE_SPIKES) {
            return BiomeCategory.ICY;
        }
        if (holder.a(amv.n)) {
            return BiomeCategory.THEEND;
        }
        if (holder.a(amv.c)) {
            return BiomeCategory.BEACH;
        }
        if (holder.a(amv.j)) {
            return BiomeCategory.FOREST;
        }
        if (holder.a(amv.b)) {
            return BiomeCategory.OCEAN;
        }
        if (holder.a() == BiomeUtils.DESERT) {
            return BiomeCategory.DESERT;
        }
        if (holder.a(amv.d)) {
            return BiomeCategory.RIVER;
        }
        if (holder.a() == BiomeUtils.SWAMP || holder.a() == BiomeUtils.MANGROVE_SWAMP) {
            return BiomeCategory.SWAMP;
        }
        if (holder.a() == BiomeUtils.MUSHROOM_FIELDS) {
            return BiomeCategory.MUSHROOM;
        }
        if (holder.a(amv.m)) {
            return BiomeCategory.NETHER;
        }
        if (holder.a(amv.Y)) {
            return BiomeCategory.UNDERGROUND;
        }
        if (holder.a(amv.e)) {
            return BiomeCategory.MOUNTAIN;
        }
        return BiomeCategory.PLAINS;
    }
    
    public static float getDownfall(final cnk biome) {
        return cnr.getDownfall(biome);
    }
    
    public static cnk.c getPrecipitation(final cnk biome) {
        if (!biome.c()) {
            return cnk.c.a;
        }
        if (biome.g() < 0.1) {
            return cnk.c.c;
        }
        return cnk.c.b;
    }
    
    static {
        BiomeUtils.defaultBiomeRegistry = makeDefaultBiomeRegistry();
        BiomeUtils.biomeRegistry = getBiomeRegistry((cmm)enn.N().s);
        BiomeUtils.biomeWorld = (cmm)enn.N().s;
        BiomeUtils.PLAINS = (cnk)BiomeUtils.biomeRegistry.a(cnr.b);
        BiomeUtils.SUNFLOWER_PLAINS = (cnk)BiomeUtils.biomeRegistry.a(cnr.c);
        BiomeUtils.SNOWY_PLAINS = (cnk)BiomeUtils.biomeRegistry.a(cnr.d);
        BiomeUtils.ICE_SPIKES = (cnk)BiomeUtils.biomeRegistry.a(cnr.e);
        BiomeUtils.DESERT = (cnk)BiomeUtils.biomeRegistry.a(cnr.f);
        BiomeUtils.WINDSWEPT_HILLS = (cnk)BiomeUtils.biomeRegistry.a(cnr.t);
        BiomeUtils.WINDSWEPT_GRAVELLY_HILLS = (cnk)BiomeUtils.biomeRegistry.a(cnr.u);
        BiomeUtils.MUSHROOM_FIELDS = (cnk)BiomeUtils.biomeRegistry.a(cnr.Y);
        BiomeUtils.SWAMP = (cnk)BiomeUtils.biomeRegistry.a(cnr.g);
        BiomeUtils.MANGROVE_SWAMP = (cnk)BiomeUtils.biomeRegistry.a(cnr.h);
        BiomeUtils.THE_VOID = (cnk)BiomeUtils.biomeRegistry.a(cnr.a);
    }
}
