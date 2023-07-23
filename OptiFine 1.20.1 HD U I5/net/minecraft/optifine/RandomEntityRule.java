// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.util.ArrayUtils;
import net.optifine.config.Matches;
import net.optifine.util.MathUtils;
import net.optifine.config.RangeInt;
import net.optifine.config.ConnectedParser;
import java.util.Properties;
import net.optifine.config.MatchBlock;
import net.optifine.config.Weather;
import net.optifine.config.MatchProfession;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeListInt;
import net.optifine.config.BiomeId;

public class RandomEntityRule<T>
{
    private String pathProps;
    private acq baseResLoc;
    private int index;
    private RandomEntityContext<T> context;
    private int[] textures;
    private T[] resources;
    private int[] weights;
    private BiomeId[] biomes;
    private RangeListInt heights;
    private RangeListInt healthRange;
    private boolean healthPercent;
    private NbtTagValue nbtName;
    public int[] sumWeights;
    public int sumAllWeights;
    private MatchProfession[] professions;
    private cen[] colors;
    private Boolean baby;
    private RangeListInt moonPhases;
    private RangeListInt dayTimes;
    private Weather[] weatherList;
    private RangeListInt sizes;
    public NbtTagValue[] nbtTagValues;
    public MatchBlock[] blocks;
    
    public RandomEntityRule(final Properties props, final String pathProps, final acq baseResLoc, final int index, final String valTextures, final RandomEntityContext<T> context) {
        this.pathProps = null;
        this.baseResLoc = null;
        this.textures = null;
        this.resources = null;
        this.weights = null;
        this.biomes = null;
        this.heights = null;
        this.healthRange = null;
        this.healthPercent = false;
        this.nbtName = null;
        this.sumWeights = null;
        this.sumAllWeights = 1;
        this.professions = null;
        this.colors = null;
        this.baby = null;
        this.moonPhases = null;
        this.dayTimes = null;
        this.weatherList = null;
        this.sizes = null;
        this.nbtTagValues = null;
        this.blocks = null;
        this.pathProps = pathProps;
        this.baseResLoc = baseResLoc;
        this.index = index;
        this.context = context;
        final ConnectedParser cp = context.getConnectedParser();
        this.textures = cp.parseIntList(valTextures);
        this.weights = cp.parseIntList(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
        this.biomes = cp.parseBiomes(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
        this.heights = cp.parseRangeListIntNeg(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
        if (this.heights == null) {
            this.heights = this.parseMinMaxHeight(props, index);
        }
        String healthStr = props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index));
        if (healthStr != null) {
            this.healthPercent = healthStr.contains("%");
            healthStr = healthStr.replace("%", "");
            this.healthRange = cp.parseRangeListInt(healthStr);
        }
        this.nbtName = cp.parseNbtTagValue("name", props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
        this.professions = cp.parseProfessions(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
        this.colors = cp.parseDyeColors(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)), "color", ConnectedParser.DYE_COLORS_INVALID);
        if (this.colors == null) {
            this.colors = cp.parseDyeColors(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)), "collar color", ConnectedParser.DYE_COLORS_INVALID);
        }
        this.baby = cp.parseBooleanObject(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
        this.moonPhases = cp.parseRangeListInt(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
        this.dayTimes = cp.parseRangeListInt(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
        this.weatherList = cp.parseWeather(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)), invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index), (Weather[])null);
        this.sizes = cp.parseRangeListInt(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
        this.nbtTagValues = cp.parseNbtTagValues(props, invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index));
        this.blocks = cp.parseMatchBlocks(props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index)));
    }
    
    public int getIndex() {
        return this.index;
    }
    
    private RangeListInt parseMinMaxHeight(final Properties props, final int index) {
        final String minHeightStr = props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index));
        final String maxHeightStr = props.getProperty(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, index));
        if (minHeightStr == null && maxHeightStr == null) {
            return null;
        }
        int minHeight = 0;
        if (minHeightStr != null) {
            minHeight = Config.parseInt(minHeightStr, -1);
            if (minHeight < 0) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, minHeightStr));
                return null;
            }
        }
        int maxHeight = 256;
        if (maxHeightStr != null) {
            maxHeight = Config.parseInt(maxHeightStr, -1);
            if (maxHeight < 0) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, maxHeightStr));
                return null;
            }
        }
        if (maxHeight < 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, minHeightStr, maxHeightStr));
            return null;
        }
        final RangeListInt list = new RangeListInt();
        list.addRange(new RangeInt(minHeight, maxHeight));
        return list;
    }
    
    public boolean isValid(final String path) {
        final String resourceName = this.context.getResourceName();
        final String resourceNamePlural = this.context.getResourceNamePlural();
        if (this.textures == null || this.textures.length == 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, resourceNamePlural, this.index));
            return false;
        }
        this.resources = (T[])new Object[this.textures.length];
        for (int i = 0; i < this.textures.length; ++i) {
            final int index = this.textures[i];
            final T res = (T)this.context.makeResource(this.baseResLoc, index);
            if (res == null) {
                return false;
            }
            this.resources[i] = res;
        }
        if (this.weights != null) {
            if (this.weights.length > this.resources.length) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, resourceNamePlural, path));
                final int[] weights2 = new int[this.resources.length];
                System.arraycopy(this.weights, 0, weights2, 0, weights2.length);
                this.weights = weights2;
            }
            if (this.weights.length < this.resources.length) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, resourceNamePlural, path));
                final int[] weights2 = new int[this.resources.length];
                System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
                final int avgWeight = MathUtils.getAverage(this.weights);
                for (int j = this.weights.length; j < weights2.length; ++j) {
                    weights2[j] = avgWeight;
                }
                this.weights = weights2;
            }
            this.sumWeights = new int[this.weights.length];
            int sum = 0;
            for (int k = 0; k < this.weights.length; ++k) {
                if (this.weights[k] < 0) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.weights[k]));
                    return false;
                }
                sum += this.weights[k];
                this.sumWeights[k] = sum;
            }
            this.sumAllWeights = sum;
            if (this.sumAllWeights <= 0) {
                Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, sum));
                this.sumAllWeights = 1;
            }
        }
        if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.colors == ConnectedParser.DYE_COLORS_INVALID) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    public boolean matches(final IRandomEntity randomEntity) {
        if (this.biomes != null && !Matches.biome(randomEntity.getSpawnBiome(), this.biomes)) {
            return false;
        }
        if (this.heights != null) {
            final gu pos = randomEntity.getSpawnPosition();
            if (pos != null && !this.heights.isInRange(pos.v())) {
                return false;
            }
        }
        if (this.healthRange != null) {
            int health = randomEntity.getHealth();
            if (this.healthPercent) {
                final int healthMax = randomEntity.getMaxHealth();
                if (healthMax > 0) {
                    health = (int)(health * 100 / (double)healthMax);
                }
            }
            if (!this.healthRange.isInRange(health)) {
                return false;
            }
        }
        if (this.nbtName != null) {
            final String name = randomEntity.getName();
            if (!this.nbtName.matchesValue(name)) {
                return false;
            }
        }
        if (this.professions != null && randomEntity instanceof RandomEntity) {
            final RandomEntity rme = (RandomEntity)randomEntity;
            final bfj entity = rme.getEntity();
            if (entity instanceof byb) {
                final byb entityVillager = (byb)entity;
                final byc vd = entityVillager.gj();
                final bye vp = vd.b();
                final int level = vd.c();
                if (!MatchProfession.matchesOne(vp, level, this.professions)) {
                    return false;
                }
            }
        }
        if (this.colors != null) {
            final cen col = randomEntity.getColor();
            if (col != null && !Config.equalsOne(col, this.colors)) {
                return false;
            }
        }
        if (this.baby != null && randomEntity instanceof RandomEntity) {
            final RandomEntity rme = (RandomEntity)randomEntity;
            final bfj entity = rme.getEntity();
            if (entity instanceof bfz) {
                final bfz livingEntity = (bfz)entity;
                if (livingEntity.h_() != this.baby) {
                    return false;
                }
            }
        }
        if (this.moonPhases != null) {
            final cmm world = (cmm)Config.getMinecraft().s;
            if (world != null) {
                final int moonPhase = world.ao();
                if (!this.moonPhases.isInRange(moonPhase)) {
                    return false;
                }
            }
        }
        if (this.dayTimes != null) {
            final cmm world = (cmm)Config.getMinecraft().s;
            if (world != null) {
                final int dayTime = (int)(world.W() % 24000L);
                if (!this.dayTimes.isInRange(dayTime)) {
                    return false;
                }
            }
        }
        if (this.weatherList != null) {
            final cmm world = (cmm)Config.getMinecraft().s;
            if (world != null) {
                final Weather weather = Weather.getWeather(world, 0.0f);
                if (!ArrayUtils.contains(this.weatherList, weather)) {
                    return false;
                }
            }
        }
        if (this.sizes != null && randomEntity instanceof RandomEntity) {
            final RandomEntity rme = (RandomEntity)randomEntity;
            final bfj entity = rme.getEntity();
            final int size = this.getEntitySize(entity);
            if (size >= 0 && !this.sizes.isInRange(size)) {
                return false;
            }
        }
        if (this.nbtTagValues != null) {
            final qr nbt = randomEntity.getNbtTag();
            if (nbt != null) {
                for (int i = 0; i < this.nbtTagValues.length; ++i) {
                    final NbtTagValue ntv = this.nbtTagValues[i];
                    if (!ntv.matches(nbt)) {
                        return false;
                    }
                }
            }
        }
        if (this.blocks != null) {
            final dcb blockState = randomEntity.getBlockState();
            if (blockState != null && !Matches.block(blockState, this.blocks)) {
                return false;
            }
        }
        return true;
    }
    
    public static cen getEntityColor(final bfj entity) {
        if (entity instanceof bso) {
            final bso entityWolf = (bso)entity;
            if (!entityWolf.q()) {
                return null;
            }
            return entityWolf.gh();
        }
        else if (entity instanceof bro) {
            final bro entityCat = (bro)entity;
            if (!entityCat.q()) {
                return null;
            }
            return entityCat.gi();
        }
        else {
            if (entity instanceof bsh) {
                final bsh entitySheep = (bsh)entity;
                return entitySheep.r();
            }
            if (entity instanceof btn) {
                final btn entityLlama = (btn)entity;
                return entityLlama.gl();
            }
            return null;
        }
    }
    
    public static cen getBlockEntityColor(final czn entity) {
        if (entity instanceof czj) {
            final czj entityBed = (czj)entity;
            return entityBed.d();
        }
        if (entity instanceof dau) {
            final dau entityShulkerBox = (dau)entity;
            return entityShulkerBox.j();
        }
        return null;
    }
    
    private int getEntitySize(final bfj entity) {
        if (entity instanceof bwl) {
            final bwl entitySlime = (bwl)entity;
            return entitySlime.ga() - 1;
        }
        if (entity instanceof bwe) {
            final bwe entityPhantom = (bwe)entity;
            return entityPhantom.q();
        }
        return -1;
    }
    
    public T getResource(final int randomId, final T resDef) {
        if (this.resources == null || this.resources.length == 0) {
            return resDef;
        }
        final int index = this.getResourceIndex(randomId);
        return this.resources[index];
    }
    
    private int getResourceIndex(final int randomId) {
        int index = 0;
        if (this.weights == null) {
            index = randomId % this.resources.length;
        }
        else {
            final int randWeight = randomId % this.sumAllWeights;
            for (int i = 0; i < this.sumWeights.length; ++i) {
                if (this.sumWeights[i] > randWeight) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
    
    public T[] getResources() {
        return this.resources;
    }
}
