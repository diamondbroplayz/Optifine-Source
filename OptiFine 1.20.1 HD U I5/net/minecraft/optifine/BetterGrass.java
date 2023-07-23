// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.util.RandomUtils;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import net.optifine.util.PropertiesOrdered;
import net.optifine.model.BlockModelUtils;

public class BetterGrass
{
    private static boolean betterGrass;
    private static boolean betterDirtPath;
    private static boolean betterMycelium;
    private static boolean betterPodzol;
    private static boolean betterCrimsonNylium;
    private static boolean betterWarpedNylium;
    private static boolean betterGrassSnow;
    private static boolean betterMyceliumSnow;
    private static boolean betterPodzolSnow;
    private static boolean grassMultilayer;
    private static fuv spriteGrass;
    private static fuv spriteGrassSide;
    private static fuv spriteDirtPath;
    private static fuv spriteDirtPathSide;
    private static fuv spriteMycelium;
    private static fuv spritePodzol;
    private static fuv spriteCrimsonNylium;
    private static fuv spriteWarpedNylium;
    private static fuv spriteSnow;
    private static boolean spritesLoaded;
    private static fwr modelCubeGrass;
    private static fwr modelDirtPath;
    private static fwr modelCubeDirtPath;
    private static fwr modelCubeMycelium;
    private static fwr modelCubePodzol;
    private static fwr modelCubeCrimsonNylium;
    private static fwr modelCubeWarpedNylium;
    private static fwr modelCubeSnow;
    private static boolean modelsLoaded;
    private static final String TEXTURE_GRASS_DEFAULT = "block/grass_block_top";
    private static final String TEXTURE_GRASS_SIDE_DEFAULT = "block/grass_block_side";
    private static final String TEXTURE_DIRT_PATH_DEFAULT = "block/dirt_path_top";
    private static final String TEXTURE_DIRT_PATH_SIDE_DEFAULT = "block/dirt_path_side";
    private static final String TEXTURE_MYCELIUM_DEFAULT = "block/mycelium_top";
    private static final String TEXTURE_PODZOL_DEFAULT = "block/podzol_top";
    private static final String TEXTURE_CRIMSON_NYLIUM = "block/crimson_nylium";
    private static final String TEXTURE_WARPED_NYLIUM = "block/warped_nylium";
    private static final String TEXTURE_SNOW_DEFAULT = "block/snow";
    private static final apf RANDOM;
    
    public static void updateIcons(final fuu textureMap) {
        BetterGrass.spritesLoaded = false;
        BetterGrass.modelsLoaded = false;
        loadProperties(textureMap);
    }
    
    public static void update() {
        if (!BetterGrass.spritesLoaded) {
            return;
        }
        BetterGrass.modelCubeGrass = BlockModelUtils.makeModelCube(BetterGrass.spriteGrass, 0);
        if (BetterGrass.grassMultilayer) {
            final fwr modelCubeGrassSide = BlockModelUtils.makeModelCube(BetterGrass.spriteGrassSide, -1);
            BetterGrass.modelCubeGrass = BlockModelUtils.joinModelsCube(modelCubeGrassSide, BetterGrass.modelCubeGrass);
        }
        BetterGrass.modelDirtPath = BlockModelUtils.makeModel("dirt_path", BetterGrass.spriteDirtPathSide, BetterGrass.spriteDirtPath);
        BetterGrass.modelCubeDirtPath = BlockModelUtils.makeModelCube(BetterGrass.spriteDirtPath, -1);
        BetterGrass.modelCubeMycelium = BlockModelUtils.makeModelCube(BetterGrass.spriteMycelium, -1);
        BetterGrass.modelCubePodzol = BlockModelUtils.makeModelCube(BetterGrass.spritePodzol, 0);
        BetterGrass.modelCubeCrimsonNylium = BlockModelUtils.makeModelCube(BetterGrass.spriteCrimsonNylium, -1);
        BetterGrass.modelCubeWarpedNylium = BlockModelUtils.makeModelCube(BetterGrass.spriteWarpedNylium, -1);
        BetterGrass.modelCubeSnow = BlockModelUtils.makeModelCube(BetterGrass.spriteSnow, -1);
        BetterGrass.modelsLoaded = true;
    }
    
    private static void loadProperties(final fuu textureMap) {
        BetterGrass.betterGrass = true;
        BetterGrass.betterDirtPath = true;
        BetterGrass.betterMycelium = true;
        BetterGrass.betterPodzol = true;
        BetterGrass.betterCrimsonNylium = true;
        BetterGrass.betterWarpedNylium = true;
        BetterGrass.betterGrassSnow = true;
        BetterGrass.betterMyceliumSnow = true;
        BetterGrass.betterPodzolSnow = true;
        BetterGrass.spriteGrass = textureMap.registerSprite(new acq("block/grass_block_top"));
        BetterGrass.spriteGrassSide = textureMap.registerSprite(new acq("block/grass_block_side"));
        BetterGrass.spriteDirtPath = textureMap.registerSprite(new acq("block/dirt_path_top"));
        BetterGrass.spriteDirtPathSide = textureMap.registerSprite(new acq("block/dirt_path_side"));
        BetterGrass.spriteMycelium = textureMap.registerSprite(new acq("block/mycelium_top"));
        BetterGrass.spritePodzol = textureMap.registerSprite(new acq("block/podzol_top"));
        BetterGrass.spriteCrimsonNylium = textureMap.registerSprite(new acq("block/crimson_nylium"));
        BetterGrass.spriteWarpedNylium = textureMap.registerSprite(new acq("block/warped_nylium"));
        BetterGrass.spriteSnow = textureMap.registerSprite(new acq("block/snow"));
        BetterGrass.spritesLoaded = true;
        final String name = "optifine/bettergrass.properties";
        try {
            final acq locFile = new acq(name);
            if (!Config.hasResource(locFile)) {
                return;
            }
            final InputStream in = Config.getResourceStream(locFile);
            if (in == null) {
                return;
            }
            final boolean defaultConfig = Config.isFromDefaultResourcePack(locFile);
            if (defaultConfig) {
                Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            }
            else {
                Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            }
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            BetterGrass.betterGrass = getBoolean(props, "grass", true);
            BetterGrass.betterDirtPath = getBoolean(props, "dirt_path", true);
            BetterGrass.betterMycelium = getBoolean(props, "mycelium", true);
            BetterGrass.betterPodzol = getBoolean(props, "podzol", true);
            BetterGrass.betterCrimsonNylium = getBoolean(props, "crimson_nylium", true);
            BetterGrass.betterWarpedNylium = getBoolean(props, "warped_nylium", true);
            BetterGrass.betterGrassSnow = getBoolean(props, "grass.snow", true);
            BetterGrass.betterMyceliumSnow = getBoolean(props, "mycelium.snow", true);
            BetterGrass.betterPodzolSnow = getBoolean(props, "podzol.snow", true);
            BetterGrass.grassMultilayer = getBoolean(props, "grass.multilayer", false);
            BetterGrass.spriteGrass = registerSprite(props, "texture.grass", "block/grass_block_top", textureMap);
            BetterGrass.spriteGrassSide = registerSprite(props, "texture.grass_side", "block/grass_block_side", textureMap);
            BetterGrass.spriteDirtPath = registerSprite(props, "texture.dirt_path", "block/dirt_path_top", textureMap);
            BetterGrass.spriteDirtPathSide = registerSprite(props, "texture.dirt_path_side", "block/dirt_path_side", textureMap);
            BetterGrass.spriteMycelium = registerSprite(props, "texture.mycelium", "block/mycelium_top", textureMap);
            BetterGrass.spritePodzol = registerSprite(props, "texture.podzol", "block/podzol_top", textureMap);
            BetterGrass.spriteCrimsonNylium = registerSprite(props, "texture.crimson_nylium", "block/crimson_nylium", textureMap);
            BetterGrass.spriteWarpedNylium = registerSprite(props, "texture.warped_nylium", "block/warped_nylium", textureMap);
            BetterGrass.spriteSnow = registerSprite(props, "texture.snow", "block/snow", textureMap);
        }
        catch (IOException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, name, e.getClass().getName(), e.getMessage()));
        }
    }
    
    public static void refreshIcons(final fuu textureMap) {
        BetterGrass.spriteGrass = getSprite(textureMap, BetterGrass.spriteGrass.getName());
        BetterGrass.spriteGrassSide = getSprite(textureMap, BetterGrass.spriteGrassSide.getName());
        BetterGrass.spriteDirtPath = getSprite(textureMap, BetterGrass.spriteDirtPath.getName());
        BetterGrass.spriteDirtPathSide = getSprite(textureMap, BetterGrass.spriteDirtPathSide.getName());
        BetterGrass.spriteMycelium = getSprite(textureMap, BetterGrass.spriteMycelium.getName());
        BetterGrass.spritePodzol = getSprite(textureMap, BetterGrass.spritePodzol.getName());
        BetterGrass.spriteCrimsonNylium = getSprite(textureMap, BetterGrass.spriteCrimsonNylium.getName());
        BetterGrass.spriteWarpedNylium = getSprite(textureMap, BetterGrass.spriteWarpedNylium.getName());
        BetterGrass.spriteSnow = getSprite(textureMap, BetterGrass.spriteSnow.getName());
    }
    
    private static fuv getSprite(final fuu textureMap, final acq loc) {
        final fuv sprite = textureMap.a(loc);
        if (sprite == null || ful.isMisingSprite(sprite)) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lacq;)Ljava/lang/String;, loc));
        }
        return sprite;
    }
    
    private static fuv registerSprite(final Properties props, final String key, final String textureDefault, final fuu textureMap) {
        String texture = props.getProperty(key);
        if (texture == null) {
            texture = textureDefault;
        }
        final acq locPng = new acq(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, texture));
        if (!Config.hasResource(locPng)) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lacq;)Ljava/lang/String;, locPng));
            texture = textureDefault;
        }
        final acq locSprite = new acq(texture);
        final fuv sprite = textureMap.registerSprite(locSprite);
        return sprite;
    }
    
    public static List getFaceQuads(final cls blockAccess, final dcb blockState, final gu blockPos, final ha facing, final List quads) {
        if (facing == ha.b || facing == ha.a) {
            return quads;
        }
        if (!BetterGrass.modelsLoaded) {
            return quads;
        }
        final cpn block = blockState.b();
        if (block instanceof cuo) {
            return getFaceQuadsMycelium(blockAccess, blockState, blockPos, facing, quads);
        }
        if (block instanceof crn) {
            return getFaceQuadsDirtPath(blockAccess, blockState, blockPos, facing, quads);
        }
        if (block == cpo.l) {
            return getFaceQuadsPodzol(blockAccess, blockState, blockPos, facing, quads);
        }
        if (block == cpo.ow) {
            return getFaceQuadsCrimsonNylium(blockAccess, blockState, blockPos, facing, quads);
        }
        if (block == cpo.on) {
            return getFaceQuadsWarpedNylium(blockAccess, blockState, blockPos, facing, quads);
        }
        if (block == cpo.j) {
            return getFaceQuadsDirt(blockAccess, blockState, blockPos, facing, quads);
        }
        if (block instanceof csv) {
            return getFaceQuadsGrass(blockAccess, blockState, blockPos, facing, quads);
        }
        return quads;
    }
    
    private static List getFaceQuadsMycelium(final cls blockAccess, final dcb blockState, final gu blockPos, final ha facing, final List quads) {
        final cpn blockUp = blockAccess.a_(blockPos.c()).b();
        final boolean snowy = blockUp == cpo.dP || blockUp == cpo.dN;
        if (Config.isBetterGrassFancy()) {
            if (snowy) {
                if (BetterGrass.betterMyceliumSnow && getBlockAt(blockPos, facing, blockAccess) == cpo.dN) {
                    return BetterGrass.modelCubeSnow.a(blockState, facing, BetterGrass.RANDOM);
                }
            }
            else if (BetterGrass.betterMycelium && getBlockAt(blockPos.d(), facing, blockAccess) == cpo.fl) {
                return BetterGrass.modelCubeMycelium.a(blockState, facing, BetterGrass.RANDOM);
            }
        }
        else if (snowy) {
            if (BetterGrass.betterMyceliumSnow) {
                return BetterGrass.modelCubeSnow.a(blockState, facing, BetterGrass.RANDOM);
            }
        }
        else if (BetterGrass.betterMycelium) {
            return BetterGrass.modelCubeMycelium.a(blockState, facing, BetterGrass.RANDOM);
        }
        return quads;
    }
    
    private static List getFaceQuadsDirtPath(final cls blockAccess, final dcb blockState, final gu blockPos, final ha facing, final List quads) {
        if (!BetterGrass.betterDirtPath) {
            return quads;
        }
        if (!Config.isBetterGrassFancy()) {
            return BetterGrass.modelDirtPath.a(blockState, facing, BetterGrass.RANDOM);
        }
        if (getBlockAt(blockPos.d(), facing, blockAccess) == cpo.kE) {
            return BetterGrass.modelDirtPath.a(blockState, facing, BetterGrass.RANDOM);
        }
        return quads;
    }
    
    private static List getFaceQuadsPodzol(final cls blockAccess, final dcb blockState, final gu blockPos, final ha facing, final List quads) {
        final cpn blockTop = getBlockAt(blockPos, ha.b, blockAccess);
        final boolean snowy = blockTop == cpo.dP || blockTop == cpo.dN;
        if (Config.isBetterGrassFancy()) {
            if (snowy) {
                if (BetterGrass.betterPodzolSnow && getBlockAt(blockPos, facing, blockAccess) == cpo.dN) {
                    return BetterGrass.modelCubeSnow.a(blockState, facing, BetterGrass.RANDOM);
                }
            }
            else if (BetterGrass.betterPodzol) {
                final gu posSide = blockPos.d().a(facing);
                final dcb stateSide = blockAccess.a_(posSide);
                if (stateSide.b() == cpo.l) {
                    return BetterGrass.modelCubePodzol.a(blockState, facing, BetterGrass.RANDOM);
                }
            }
        }
        else if (snowy) {
            if (BetterGrass.betterPodzolSnow) {
                return BetterGrass.modelCubeSnow.a(blockState, facing, BetterGrass.RANDOM);
            }
        }
        else if (BetterGrass.betterPodzol) {
            return BetterGrass.modelCubePodzol.a(blockState, facing, BetterGrass.RANDOM);
        }
        return quads;
    }
    
    private static List getFaceQuadsCrimsonNylium(final cls blockAccess, final dcb blockState, final gu blockPos, final ha facing, final List quads) {
        if (!BetterGrass.betterCrimsonNylium) {
            return quads;
        }
        if (!Config.isBetterGrassFancy()) {
            return BetterGrass.modelCubeCrimsonNylium.a(blockState, facing, BetterGrass.RANDOM);
        }
        if (getBlockAt(blockPos.d(), facing, blockAccess) == cpo.ow) {
            return BetterGrass.modelCubeCrimsonNylium.a(blockState, facing, BetterGrass.RANDOM);
        }
        return quads;
    }
    
    private static List getFaceQuadsWarpedNylium(final cls blockAccess, final dcb blockState, final gu blockPos, final ha facing, final List quads) {
        if (!BetterGrass.betterWarpedNylium) {
            return quads;
        }
        if (!Config.isBetterGrassFancy()) {
            return BetterGrass.modelCubeWarpedNylium.a(blockState, facing, BetterGrass.RANDOM);
        }
        if (getBlockAt(blockPos.d(), facing, blockAccess) == cpo.on) {
            return BetterGrass.modelCubeWarpedNylium.a(blockState, facing, BetterGrass.RANDOM);
        }
        return quads;
    }
    
    private static List getFaceQuadsDirt(final cls blockAccess, final dcb blockState, final gu blockPos, final ha facing, final List quads) {
        final cpn blockTop = getBlockAt(blockPos, ha.b, blockAccess);
        if (blockTop == cpo.kE && BetterGrass.betterDirtPath && getBlockAt(blockPos, facing, blockAccess) == cpo.kE) {
            return BetterGrass.modelCubeDirtPath.a(blockState, facing, BetterGrass.RANDOM);
        }
        return quads;
    }
    
    private static List getFaceQuadsGrass(final cls blockAccess, final dcb blockState, final gu blockPos, final ha facing, final List quads) {
        final cpn blockUp = blockAccess.a_(blockPos.c()).b();
        final boolean snowy = blockUp == cpo.dP || blockUp == cpo.dN;
        if (Config.isBetterGrassFancy()) {
            if (snowy) {
                if (BetterGrass.betterGrassSnow && getBlockAt(blockPos, facing, blockAccess) == cpo.dN) {
                    return BetterGrass.modelCubeSnow.a(blockState, facing, BetterGrass.RANDOM);
                }
            }
            else if (BetterGrass.betterGrass && getBlockAt(blockPos.d(), facing, blockAccess) == cpo.i) {
                return BetterGrass.modelCubeGrass.a(blockState, facing, BetterGrass.RANDOM);
            }
        }
        else if (snowy) {
            if (BetterGrass.betterGrassSnow) {
                return BetterGrass.modelCubeSnow.a(blockState, facing, BetterGrass.RANDOM);
            }
        }
        else if (BetterGrass.betterGrass) {
            return BetterGrass.modelCubeGrass.a(blockState, facing, BetterGrass.RANDOM);
        }
        return quads;
    }
    
    private static cpn getBlockAt(final gu blockPos, final ha facing, final cls blockAccess) {
        final gu pos = blockPos.a(facing);
        final cpn block = blockAccess.a_(pos).b();
        return block;
    }
    
    private static boolean getBoolean(final Properties props, final String key, final boolean def) {
        final String str = props.getProperty(key);
        if (str == null) {
            return def;
        }
        return Boolean.parseBoolean(str);
    }
    
    static {
        BetterGrass.betterGrass = true;
        BetterGrass.betterDirtPath = true;
        BetterGrass.betterMycelium = true;
        BetterGrass.betterPodzol = true;
        BetterGrass.betterCrimsonNylium = true;
        BetterGrass.betterWarpedNylium = true;
        BetterGrass.betterGrassSnow = true;
        BetterGrass.betterMyceliumSnow = true;
        BetterGrass.betterPodzolSnow = true;
        BetterGrass.grassMultilayer = false;
        BetterGrass.spriteGrass = null;
        BetterGrass.spriteGrassSide = null;
        BetterGrass.spriteDirtPath = null;
        BetterGrass.spriteDirtPathSide = null;
        BetterGrass.spriteMycelium = null;
        BetterGrass.spritePodzol = null;
        BetterGrass.spriteCrimsonNylium = null;
        BetterGrass.spriteWarpedNylium = null;
        BetterGrass.spriteSnow = null;
        BetterGrass.spritesLoaded = false;
        BetterGrass.modelCubeGrass = null;
        BetterGrass.modelDirtPath = null;
        BetterGrass.modelCubeDirtPath = null;
        BetterGrass.modelCubeMycelium = null;
        BetterGrass.modelCubePodzol = null;
        BetterGrass.modelCubeCrimsonNylium = null;
        BetterGrass.modelCubeWarpedNylium = null;
        BetterGrass.modelCubeSnow = null;
        BetterGrass.modelsLoaded = false;
        BetterGrass.RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
    }
}
