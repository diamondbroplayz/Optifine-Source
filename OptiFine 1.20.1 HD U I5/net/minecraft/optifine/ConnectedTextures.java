// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.util.RandomUtils;
import net.optifine.util.TextureUtils;
import net.optifine.util.StrUtils;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;
import net.optifine.util.PropertiesOrdered;
import java.util.Arrays;
import net.optifine.util.ResUtils;
import net.optifine.util.BlockUtils;
import net.optifine.config.Matches;
import net.optifine.model.ListQuadsOverlay;
import net.optifine.util.TileEntityUtils;
import net.optifine.util.BiomeUtils;
import java.util.List;
import net.optifine.model.BlockModelUtils;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import net.optifine.render.RenderEnv;
import java.util.Map;

public class ConnectedTextures
{
    private static Map[] spriteQuadMaps;
    private static Map[] spriteQuadFullMaps;
    private static Map[][] spriteQuadCompactMaps;
    private static ConnectedProperties[][] blockProperties;
    private static ConnectedProperties[][] tileProperties;
    private static boolean multipass;
    protected static final int UNKNOWN = -1;
    protected static final int Y_NEG_DOWN = 0;
    protected static final int Y_POS_UP = 1;
    protected static final int Z_NEG_NORTH = 2;
    protected static final int Z_POS_SOUTH = 3;
    protected static final int X_NEG_WEST = 4;
    protected static final int X_POS_EAST = 5;
    private static final int Y_AXIS = 0;
    private static final int Z_AXIS = 1;
    private static final int X_AXIS = 2;
    public static final dcb AIR_DEFAULT_STATE;
    private static fuv emptySprite;
    public static acq LOCATION_SPRITE_EMPTY;
    private static final BlockDir[] SIDES_Y_NEG_DOWN;
    private static final BlockDir[] SIDES_Y_POS_UP;
    private static final BlockDir[] SIDES_Z_NEG_NORTH;
    private static final BlockDir[] SIDES_Z_POS_SOUTH;
    private static final BlockDir[] SIDES_X_NEG_WEST;
    private static final BlockDir[] SIDES_X_POS_EAST;
    private static final BlockDir[] EDGES_Y_NEG_DOWN;
    private static final BlockDir[] EDGES_Y_POS_UP;
    private static final BlockDir[] EDGES_Z_NEG_NORTH;
    private static final BlockDir[] EDGES_Z_POS_SOUTH;
    private static final BlockDir[] EDGES_X_NEG_WEST;
    private static final BlockDir[] EDGES_X_POS_EAST;
    public static final fuv SPRITE_DEFAULT;
    private static final apf RANDOM;
    
    public static fkr[] getConnectedTexture(final clp blockAccess, final dcb blockState, final gu blockPos, fkr quad, final RenderEnv renderEnv) {
        final fuv spriteIn = quad.a();
        if (spriteIn == null) {
            return renderEnv.getArrayQuadsCtm(quad);
        }
        if (skipConnectedTexture((cls)blockAccess, blockState, blockPos, quad, renderEnv)) {
            quad = getQuad(ConnectedTextures.emptySprite, quad);
            return renderEnv.getArrayQuadsCtm(quad);
        }
        final ha side = quad.e();
        final fkr[] quads = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, side, quad, renderEnv);
        return quads;
    }
    
    private static boolean skipConnectedTexture(final cls blockAccess, final dcb blockState, final gu blockPos, final fkr quad, final RenderEnv renderEnv) {
        final cpn block = blockState.b();
        if (block instanceof ctl) {
            final ha face = quad.e();
            if (face != ha.b && face != ha.a) {
                return false;
            }
            if (!quad.isFaceQuad()) {
                return false;
            }
            final gu posNeighbour = blockPos.a(quad.e());
            final dcb stateNeighbour = blockAccess.a_(posNeighbour);
            if (stateNeighbour.b() != block) {
                return false;
            }
            final cpn blockNeighbour = stateNeighbour.b();
            if (block instanceof cxg && blockNeighbour instanceof cxg) {
                final cen color = ((cxg)block).a();
                final cen colorNeighbour = ((cxg)blockNeighbour).a();
                if (color != colorNeighbour) {
                    return false;
                }
            }
            final double midX = quad.getMidX();
            if (midX < 0.4) {
                if (stateNeighbour.c((dde)crf.d)) {
                    return true;
                }
            }
            else if (midX > 0.6) {
                if (stateNeighbour.c((dde)crf.b)) {
                    return true;
                }
            }
            else {
                final double midZ = quad.getMidZ();
                if (midZ < 0.4) {
                    if (stateNeighbour.c((dde)crf.a)) {
                        return true;
                    }
                }
                else {
                    if (midZ <= 0.6) {
                        return true;
                    }
                    if (stateNeighbour.c((dde)crf.c)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    protected static fkr[] getQuads(final fuv sprite, final fkr quadIn, final RenderEnv renderEnv) {
        if (sprite == null) {
            return null;
        }
        if (sprite == ConnectedTextures.SPRITE_DEFAULT) {
            return renderEnv.getArrayQuadsCtm(quadIn);
        }
        final fkr quad = getQuad(sprite, quadIn);
        final fkr[] quads = renderEnv.getArrayQuadsCtm(quad);
        return quads;
    }
    
    private static synchronized fkr getQuad(final fuv sprite, final fkr quadIn) {
        if (ConnectedTextures.spriteQuadMaps == null) {
            return quadIn;
        }
        final int spriteIndex = sprite.getIndexInMap();
        if (spriteIndex < 0 || spriteIndex >= ConnectedTextures.spriteQuadMaps.length) {
            return quadIn;
        }
        Map quadMap = ConnectedTextures.spriteQuadMaps[spriteIndex];
        if (quadMap == null) {
            quadMap = new IdentityHashMap(1);
            ConnectedTextures.spriteQuadMaps[spriteIndex] = quadMap;
        }
        fkr quad = quadMap.get(quadIn);
        if (quad == null) {
            quad = makeSpriteQuad(quadIn, sprite);
            quadMap.put(quadIn, quad);
        }
        return quad;
    }
    
    private static synchronized fkr getQuadFull(final fuv sprite, final fkr quadIn, final int tintIndex) {
        if (ConnectedTextures.spriteQuadFullMaps == null) {
            return null;
        }
        if (sprite == null) {
            return null;
        }
        final int spriteIndex = sprite.getIndexInMap();
        if (spriteIndex < 0 || spriteIndex >= ConnectedTextures.spriteQuadFullMaps.length) {
            return null;
        }
        Map quadMap = ConnectedTextures.spriteQuadFullMaps[spriteIndex];
        if (quadMap == null) {
            quadMap = new EnumMap(ha.class);
            ConnectedTextures.spriteQuadFullMaps[spriteIndex] = quadMap;
        }
        final ha face = quadIn.e();
        fkr quad = quadMap.get(face);
        if (quad == null) {
            quad = BlockModelUtils.makeBakedQuad(face, sprite, tintIndex);
            quadMap.put(face, quad);
        }
        return quad;
    }
    
    private static fkr makeSpriteQuad(final fkr quad, final fuv sprite) {
        final int[] data = quad.b().clone();
        final fuv spriteFrom = quad.a();
        for (int i = 0; i < 4; ++i) {
            fixVertex(data, i, spriteFrom, sprite);
        }
        final fkr bq = new fkr(data, quad.d(), quad.e(), sprite, quad.f());
        return bq;
    }
    
    private static void fixVertex(final int[] data, final int vertex, final fuv spriteFrom, final fuv spriteTo) {
        final int mul = data.length / 4;
        final int pos = mul * vertex;
        final float u = Float.intBitsToFloat(data[pos + 4]);
        final float v = Float.intBitsToFloat(data[pos + 4 + 1]);
        final double su16 = spriteFrom.getSpriteU16(u);
        final double sv16 = spriteFrom.getSpriteV16(v);
        data[pos + 4] = Float.floatToRawIntBits(spriteTo.a(su16));
        data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.b(sv16));
    }
    
    private static fkr[] getConnectedTextureMultiPass(final clp blockAccess, final dcb blockState, final gu blockPos, final ha side, final fkr quad, final RenderEnv renderEnv) {
        final fkr[] quads = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, quad, true, 0, renderEnv);
        if (!ConnectedTextures.multipass) {
            return quads;
        }
        if (quads.length == 1 && quads[0] == quad) {
            return quads;
        }
        final List<fkr> listQuads = (List<fkr>)renderEnv.getListQuadsCtmMultipass(quads);
        for (int q = 0; q < listQuads.size(); ++q) {
            fkr mpQuad;
            final fkr newQuad = mpQuad = listQuads.get(q);
            for (int i = 0; i < 3; ++i) {
                final fkr[] newMpQuads = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, mpQuad, false, i + 1, renderEnv);
                if (newMpQuads.length != 1) {
                    break;
                }
                if (newMpQuads[0] == mpQuad) {
                    break;
                }
                mpQuad = newMpQuads[0];
            }
            listQuads.set(q, mpQuad);
        }
        for (int j = 0; j < quads.length; ++j) {
            quads[j] = listQuads.get(j);
        }
        return quads;
    }
    
    public static fkr[] getConnectedTextureSingle(final clp blockAccess, final dcb blockState, final gu blockPos, final ha facing, final fkr quad, final boolean checkBlocks, final int pass, final RenderEnv renderEnv) {
        final cpn block = blockState.b();
        final fuv icon = quad.a();
        if (ConnectedTextures.tileProperties != null) {
            final int iconId = icon.getIndexInMap();
            if (iconId >= 0 && iconId < ConnectedTextures.tileProperties.length) {
                final ConnectedProperties[] cps = ConnectedTextures.tileProperties[iconId];
                if (cps != null) {
                    final int side = getSide(facing);
                    for (int i = 0; i < cps.length; ++i) {
                        final ConnectedProperties cp = cps[i];
                        if (cp != null) {
                            if (cp.matchesBlockId(blockState.getBlockId())) {
                                final fkr[] newQuads = getConnectedTexture(cp, blockAccess, blockState, blockPos, side, quad, pass, renderEnv);
                                if (newQuads != null) {
                                    return newQuads;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (ConnectedTextures.blockProperties != null && checkBlocks) {
            final int blockId = renderEnv.getBlockId();
            if (blockId >= 0 && blockId < ConnectedTextures.blockProperties.length) {
                final ConnectedProperties[] cps = ConnectedTextures.blockProperties[blockId];
                if (cps != null) {
                    final int side = getSide(facing);
                    for (int i = 0; i < cps.length; ++i) {
                        final ConnectedProperties cp = cps[i];
                        if (cp != null) {
                            if (cp.matchesIcon(icon)) {
                                final fkr[] newQuads = getConnectedTexture(cp, blockAccess, blockState, blockPos, side, quad, pass, renderEnv);
                                if (newQuads != null) {
                                    return newQuads;
                                }
                            }
                        }
                    }
                }
            }
        }
        return renderEnv.getArrayQuadsCtm(quad);
    }
    
    public static int getSide(final ha facing) {
        if (facing == null) {
            return -1;
        }
        switch (ConnectedTextures.ConnectedTextures$1.$SwitchMap$net$minecraft$core$Direction[facing.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 5: {
                return 2;
            }
            case 6: {
                return 3;
            }
            default: {
                return -1;
            }
        }
    }
    
    private static ha getFacing(final int side) {
        switch (side) {
            case 0: {
                return ha.a;
            }
            case 1: {
                return ha.b;
            }
            case 5: {
                return ha.f;
            }
            case 4: {
                return ha.e;
            }
            case 2: {
                return ha.c;
            }
            case 3: {
                return ha.d;
            }
            default: {
                return ha.b;
            }
        }
    }
    
    private static fkr[] getConnectedTexture(final ConnectedProperties cp, final clp blockAccess, final dcb blockState, final gu blockPos, final int side, final fkr quad, final int pass, final RenderEnv renderEnv) {
        int vertAxis = 0;
        final int metadata = blockState.getMetadata();
        final cpn block = blockState.b();
        if (block instanceof cvy) {
            vertAxis = getPillarAxis(blockState);
        }
        if (!cp.matchesBlock(blockState.getBlockId(), metadata)) {
            return null;
        }
        if (side >= 0 && cp.faces != 63) {
            int sideCheck = side;
            if (vertAxis != 0) {
                sideCheck = fixSideByAxis(side, vertAxis);
            }
            if ((1 << sideCheck & cp.faces) == 0x0) {
                return null;
            }
        }
        final int y = blockPos.v();
        if (cp.heights != null && !cp.heights.isInRange(y)) {
            return null;
        }
        if (cp.biomes != null) {
            final cnk blockBiome = BiomeUtils.getBiome(blockAccess, blockPos);
            if (!cp.matchesBiome(blockBiome)) {
                return null;
            }
        }
        if (cp.nbtName != null) {
            final String name = TileEntityUtils.getTileEntityName((cls)blockAccess, blockPos);
            if (!cp.nbtName.matchesValue(name)) {
                return null;
            }
        }
        final fuv icon = quad.a();
        switch (cp.method) {
            case 10: {
                if (pass != 0) {
                    break;
                }
                return getConnectedTextureCtmCompact(cp, (cls)blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
            }
            case 1: {
                return getQuads(getConnectedTextureCtm(cp, (cls)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv), quad, renderEnv);
            }
            case 2: {
                return getQuads(getConnectedTextureHorizontal(cp, (cls)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            }
            case 6: {
                return getQuads(getConnectedTextureVertical(cp, (cls)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            }
            case 3: {
                return getQuads(getConnectedTextureTop(cp, (cls)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            }
            case 4: {
                return getQuads(getConnectedTextureRandom(cp, (cls)blockAccess, blockState, blockPos, side), quad, renderEnv);
            }
            case 5: {
                return getQuads(getConnectedTextureRepeat(cp, blockPos, side), quad, renderEnv);
            }
            case 7: {
                return getQuads(getConnectedTextureFixed(cp), quad, renderEnv);
            }
            case 8: {
                return getQuads(getConnectedTextureHorizontalVertical(cp, (cls)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            }
            case 9: {
                return getQuads(getConnectedTextureVerticalHorizontal(cp, (cls)blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            }
            case 11: {
                return getConnectedTextureOverlay(cp, (cls)blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
            }
            case 12: {
                return getConnectedTextureOverlayFixed(cp, quad, renderEnv);
            }
            case 13: {
                return getConnectedTextureOverlayRandom(cp, (cls)blockAccess, blockState, blockPos, side, quad, renderEnv);
            }
            case 14: {
                return getConnectedTextureOverlayRepeat(cp, blockPos, side, quad, renderEnv);
            }
            case 15: {
                return getConnectedTextureOverlayCtm(cp, (cls)blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
            }
        }
        return null;
    }
    
    private static int fixSideByAxis(final int side, final int vertAxis) {
        switch (vertAxis) {
            case 0: {
                return side;
            }
            case 1: {
                switch (side) {
                    case 0: {
                        return 2;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 1;
                    }
                    case 3: {
                        return 0;
                    }
                    default: {
                        return side;
                    }
                }
                break;
            }
            case 2: {
                switch (side) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 5;
                    }
                    case 4: {
                        return 1;
                    }
                    case 5: {
                        return 0;
                    }
                    default: {
                        return side;
                    }
                }
                break;
            }
            default: {
                return side;
            }
        }
    }
    
    private static int getPillarAxis(final dcb blockState) {
        final ha.a axis = (ha.a)blockState.c((dde)cvy.g);
        switch (ConnectedTextures.ConnectedTextures$1.$SwitchMap$net$minecraft$core$Direction$Axis[axis.ordinal()]) {
            case 1: {
                return 2;
            }
            case 2: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static fuv getConnectedTextureRandom(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, gu blockPos, final int side) {
        if (cp.tileIcons.length == 1) {
            return cp.tileIcons[0];
        }
        final int face = side / cp.symmetry * cp.symmetry;
        if (cp.linked) {
            gu posDown = blockPos.d();
            for (dcb bsDown = blockAccess.a_(posDown); bsDown.b() == blockState.b(); bsDown = blockAccess.a_(posDown)) {
                blockPos = posDown;
                posDown = blockPos.d();
                if (posDown.v() < 0) {
                    break;
                }
            }
        }
        int rand = Config.getRandom(blockPos, face) & Integer.MAX_VALUE;
        for (int i = 0; i < cp.randomLoops; ++i) {
            rand = Config.intHash(rand);
        }
        int index = 0;
        if (cp.weights == null) {
            index = rand % cp.tileIcons.length;
        }
        else {
            final int randWeight = rand % cp.sumAllWeights;
            final int[] sumWeights = cp.sumWeights;
            for (int j = 0; j < sumWeights.length; ++j) {
                if (randWeight < sumWeights[j]) {
                    index = j;
                    break;
                }
            }
        }
        return cp.tileIcons[index];
    }
    
    private static fuv getConnectedTextureFixed(final ConnectedProperties cp) {
        return cp.tileIcons[0];
    }
    
    private static fuv getConnectedTextureRepeat(final ConnectedProperties cp, final gu blockPos, final int side) {
        if (cp.tileIcons.length == 1) {
            return cp.tileIcons[0];
        }
        final int x = blockPos.u();
        final int y = blockPos.v();
        final int z = blockPos.w();
        int nx = 0;
        int ny = 0;
        switch (side) {
            case 0: {
                nx = x;
                ny = -z - 1;
                break;
            }
            case 1: {
                nx = x;
                ny = z;
                break;
            }
            case 2: {
                nx = -x - 1;
                ny = -y;
                break;
            }
            case 3: {
                nx = x;
                ny = -y;
                break;
            }
            case 4: {
                nx = z;
                ny = -y;
                break;
            }
            case 5: {
                nx = -z - 1;
                ny = -y;
                break;
            }
        }
        nx %= cp.width;
        ny %= cp.height;
        if (nx < 0) {
            nx += cp.width;
        }
        if (ny < 0) {
            ny += cp.height;
        }
        final int index = ny * cp.width + nx;
        return cp.tileIcons[index];
    }
    
    private static fuv getConnectedTextureCtm(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fuv icon, final int metadata, final RenderEnv renderEnv) {
        final int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
        return cp.tileIcons[index];
    }
    
    private static synchronized fkr[] getConnectedTextureCtmCompact(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fkr quad, final int metadata, final RenderEnv renderEnv) {
        final fuv icon = quad.a();
        final int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
        return ConnectedTexturesCompact.getConnectedTextureCtmCompact(index, cp, side, quad, renderEnv);
    }
    
    private static fkr[] getConnectedTextureOverlay(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fkr quad, final int metadata, final RenderEnv renderEnv) {
        if (!quad.isFullQuad()) {
            return null;
        }
        final fuv icon = quad.a();
        final BlockDir[] dirSides = getSideDirections(side, vertAxis);
        final boolean[] sides = renderEnv.getBorderFlags();
        for (int i = 0; i < 4; ++i) {
            sides[i] = isNeighbourOverlay(cp, blockAccess, blockState, dirSides[i].offset(blockPos), side, icon, metadata);
        }
        final ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
        try {
            if (sides[0] && sides[1] && sides[2] && sides[3]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[8], quad, cp.tintIndex), cp.tintBlockState);
                return null;
            }
            if (sides[0] && sides[1] && sides[2]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[5], quad, cp.tintIndex), cp.tintBlockState);
                return null;
            }
            if (sides[0] && sides[2] && sides[3]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[6], quad, cp.tintIndex), cp.tintBlockState);
                return null;
            }
            if (sides[1] && sides[2] && sides[3]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[12], quad, cp.tintIndex), cp.tintBlockState);
                return null;
            }
            if (sides[0] && sides[1] && sides[3]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[13], quad, cp.tintIndex), cp.tintBlockState);
                return null;
            }
            final BlockDir[] dirEdges = getEdgeDirections(side, vertAxis);
            final boolean[] edges = renderEnv.getBorderFlags2();
            for (int j = 0; j < 4; ++j) {
                edges[j] = isNeighbourOverlay(cp, blockAccess, blockState, dirEdges[j].offset(blockPos), side, icon, metadata);
            }
            if (sides[1] && sides[2]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[3], quad, cp.tintIndex), cp.tintBlockState);
                if (edges[3]) {
                    listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
                }
                return null;
            }
            if (sides[0] && sides[2]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[4], quad, cp.tintIndex), cp.tintBlockState);
                if (edges[2]) {
                    listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
                }
                return null;
            }
            if (sides[1] && sides[3]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[10], quad, cp.tintIndex), cp.tintBlockState);
                if (edges[1]) {
                    listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
                }
                return null;
            }
            if (sides[0] && sides[3]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[11], quad, cp.tintIndex), cp.tintBlockState);
                if (edges[0]) {
                    listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
                }
                return null;
            }
            final boolean[] sidesMatch = renderEnv.getBorderFlags3();
            for (int k = 0; k < 4; ++k) {
                sidesMatch[k] = isNeighbourMatching(cp, blockAccess, blockState, dirSides[k].offset(blockPos), side, icon, metadata);
            }
            if (sides[0]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[9], quad, cp.tintIndex), cp.tintBlockState);
            }
            if (sides[1]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[7], quad, cp.tintIndex), cp.tintBlockState);
            }
            if (sides[2]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[1], quad, cp.tintIndex), cp.tintBlockState);
            }
            if (sides[3]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[15], quad, cp.tintIndex), cp.tintBlockState);
            }
            if (edges[0] && (sidesMatch[1] || sidesMatch[2]) && !sides[1] && !sides[2]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
            }
            if (edges[1] && (sidesMatch[0] || sidesMatch[2]) && !sides[0] && !sides[2]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
            }
            if (edges[2] && (sidesMatch[1] || sidesMatch[3]) && !sides[1] && !sides[3]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
            }
            if (edges[3] && (sidesMatch[0] || sidesMatch[3]) && !sides[0] && !sides[3]) {
                listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
            }
            return null;
        }
        finally {
            if (listQuadsOverlay.size() > 0) {
                renderEnv.setOverlaysRendered(true);
            }
        }
    }
    
    private static fkr[] getConnectedTextureOverlayFixed(final ConnectedProperties cp, final fkr quad, final RenderEnv renderEnv) {
        if (!quad.isFullQuad()) {
            return null;
        }
        final ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
        try {
            final fuv sprite = getConnectedTextureFixed(cp);
            if (sprite != null) {
                listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }
            return null;
        }
        finally {
            if (listQuadsOverlay.size() > 0) {
                renderEnv.setOverlaysRendered(true);
            }
        }
    }
    
    private static fkr[] getConnectedTextureOverlayRandom(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int side, final fkr quad, final RenderEnv renderEnv) {
        if (!quad.isFullQuad()) {
            return null;
        }
        final ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
        try {
            final fuv sprite = getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side);
            if (sprite != null) {
                listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }
            return null;
        }
        finally {
            if (listQuadsOverlay.size() > 0) {
                renderEnv.setOverlaysRendered(true);
            }
        }
    }
    
    private static fkr[] getConnectedTextureOverlayRepeat(final ConnectedProperties cp, final gu blockPos, final int side, final fkr quad, final RenderEnv renderEnv) {
        if (!quad.isFullQuad()) {
            return null;
        }
        final ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
        try {
            final fuv sprite = getConnectedTextureRepeat(cp, blockPos, side);
            if (sprite != null) {
                listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }
            return null;
        }
        finally {
            if (listQuadsOverlay.size() > 0) {
                renderEnv.setOverlaysRendered(true);
            }
        }
    }
    
    private static fkr[] getConnectedTextureOverlayCtm(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fkr quad, final int metadata, final RenderEnv renderEnv) {
        if (!quad.isFullQuad()) {
            return null;
        }
        final ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);
        try {
            final fuv sprite = getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, quad.a(), metadata, renderEnv);
            if (sprite != null) {
                listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }
            return null;
        }
        finally {
            if (listQuadsOverlay.size() > 0) {
                renderEnv.setOverlaysRendered(true);
            }
        }
    }
    
    private static BlockDir[] getSideDirections(final int side, final int vertAxis) {
        switch (side) {
            case 0: {
                return ConnectedTextures.SIDES_Y_NEG_DOWN;
            }
            case 1: {
                return ConnectedTextures.SIDES_Y_POS_UP;
            }
            case 2: {
                return ConnectedTextures.SIDES_Z_NEG_NORTH;
            }
            case 3: {
                return ConnectedTextures.SIDES_Z_POS_SOUTH;
            }
            case 4: {
                return ConnectedTextures.SIDES_X_NEG_WEST;
            }
            case 5: {
                return ConnectedTextures.SIDES_X_POS_EAST;
            }
            default: {
                throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, side));
            }
        }
    }
    
    private static BlockDir[] getEdgeDirections(final int side, final int vertAxis) {
        switch (side) {
            case 0: {
                return ConnectedTextures.EDGES_Y_NEG_DOWN;
            }
            case 1: {
                return ConnectedTextures.EDGES_Y_POS_UP;
            }
            case 2: {
                return ConnectedTextures.EDGES_Z_NEG_NORTH;
            }
            case 3: {
                return ConnectedTextures.EDGES_Z_POS_SOUTH;
            }
            case 4: {
                return ConnectedTextures.EDGES_X_NEG_WEST;
            }
            case 5: {
                return ConnectedTextures.EDGES_X_POS_EAST;
            }
            default: {
                throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, side));
            }
        }
    }
    
    protected static Map[][] getSpriteQuadCompactMaps() {
        return ConnectedTextures.spriteQuadCompactMaps;
    }
    
    private static int getConnectedTextureCtmIndex(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fuv icon, final int metadata, final RenderEnv renderEnv) {
        final boolean[] borders = renderEnv.getBorderFlags();
        switch (side) {
            case 0: {
                borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront = blockPos.d();
                    borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.g(), side, icon, metadata));
                    borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.h(), side, icon, metadata));
                    borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata));
                    borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 1: {
                borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront = blockPos.c();
                    borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.g(), side, icon, metadata));
                    borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.h(), side, icon, metadata));
                    borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata));
                    borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 2: {
                borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront = blockPos.e();
                    borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.h(), side, icon, metadata));
                    borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.g(), side, icon, metadata));
                    borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata));
                    borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.c(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 3: {
                borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront = blockPos.f();
                    borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.g(), side, icon, metadata));
                    borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.h(), side, icon, metadata));
                    borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata));
                    borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.c(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 4: {
                borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront = blockPos.g();
                    borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata));
                    borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata));
                    borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata));
                    borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.c(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 5: {
                borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront = blockPos.h();
                    borders[0] = (borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata));
                    borders[1] = (borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata));
                    borders[2] = (borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata));
                    borders[3] = (borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.c(), side, icon, metadata));
                    break;
                }
                break;
            }
        }
        int index = 0;
        if (borders[0] & !borders[1] & !borders[2] & !borders[3]) {
            index = 3;
        }
        else if (!borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 1;
        }
        else if (!borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 12;
        }
        else if (!borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 36;
        }
        else if (borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 2;
        }
        else if (!borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 24;
        }
        else if (borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 15;
        }
        else if (borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 39;
        }
        else if (!borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 13;
        }
        else if (!borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 37;
        }
        else if (!borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 25;
        }
        else if (borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 27;
        }
        else if (borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 38;
        }
        else if (borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 14;
        }
        else if (borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 26;
        }
        if (index == 0) {
            return index;
        }
        if (!Config.isConnectedTexturesFancy()) {
            return index;
        }
        final boolean[] edges = borders;
        switch (side) {
            case 0: {
                edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().e(), side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.g().e(), side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().f(), side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.g().f(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront2 = blockPos.d();
                    edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront2.h().e(), side, icon, metadata));
                    edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront2.g().e(), side, icon, metadata));
                    edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront2.h().f(), side, icon, metadata));
                    edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront2.g().f(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 1: {
                edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().f(), side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.g().f(), side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().e(), side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.g().e(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront2 = blockPos.c();
                    edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront2.h().f(), side, icon, metadata));
                    edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront2.g().f(), side, icon, metadata));
                    edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront2.h().e(), side, icon, metadata));
                    edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront2.g().e(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 2: {
                edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.g().d(), side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().d(), side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.g().c(), side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().c(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront2 = blockPos.e();
                    edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront2.g().d(), side, icon, metadata));
                    edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront2.h().d(), side, icon, metadata));
                    edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront2.g().c(), side, icon, metadata));
                    edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront2.h().c(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 3: {
                edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().d(), side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.g().d(), side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.h().c(), side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.g().c(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront2 = blockPos.f();
                    edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront2.h().d(), side, icon, metadata));
                    edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront2.g().d(), side, icon, metadata));
                    edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront2.h().c(), side, icon, metadata));
                    edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront2.g().c(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 4: {
                edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.d().f(), side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.d().e(), side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.c().f(), side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.c().e(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront2 = blockPos.g();
                    edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront2.d().f(), side, icon, metadata));
                    edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront2.d().e(), side, icon, metadata));
                    edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront2.c().f(), side, icon, metadata));
                    edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront2.c().e(), side, icon, metadata));
                    break;
                }
                break;
            }
            case 5: {
                edges[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.d().e(), side, icon, metadata);
                edges[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.d().f(), side, icon, metadata);
                edges[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.c().e(), side, icon, metadata);
                edges[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.c().f(), side, icon, metadata);
                if (cp.innerSeams) {
                    final gu posFront2 = blockPos.h();
                    edges[0] = (edges[0] || isNeighbour(cp, blockAccess, blockState, posFront2.d().e(), side, icon, metadata));
                    edges[1] = (edges[1] || isNeighbour(cp, blockAccess, blockState, posFront2.d().f(), side, icon, metadata));
                    edges[2] = (edges[2] || isNeighbour(cp, blockAccess, blockState, posFront2.c().e(), side, icon, metadata));
                    edges[3] = (edges[3] || isNeighbour(cp, blockAccess, blockState, posFront2.c().f(), side, icon, metadata));
                    break;
                }
                break;
            }
        }
        if (index == 13 && edges[0]) {
            index = 4;
        }
        else if (index == 15 && edges[1]) {
            index = 5;
        }
        else if (index == 37 && edges[2]) {
            index = 16;
        }
        else if (index == 39 && edges[3]) {
            index = 17;
        }
        else if (index == 14 && edges[0] && edges[1]) {
            index = 7;
        }
        else if (index == 25 && edges[0] && edges[2]) {
            index = 6;
        }
        else if (index == 27 && edges[3] && edges[1]) {
            index = 19;
        }
        else if (index == 38 && edges[3] && edges[2]) {
            index = 18;
        }
        else if (index == 14 && !edges[0] && edges[1]) {
            index = 31;
        }
        else if (index == 25 && edges[0] && !edges[2]) {
            index = 30;
        }
        else if (index == 27 && !edges[3] && edges[1]) {
            index = 41;
        }
        else if (index == 38 && edges[3] && !edges[2]) {
            index = 40;
        }
        else if (index == 14 && edges[0] && !edges[1]) {
            index = 29;
        }
        else if (index == 25 && !edges[0] && edges[2]) {
            index = 28;
        }
        else if (index == 27 && edges[3] && !edges[1]) {
            index = 43;
        }
        else if (index == 38 && !edges[3] && edges[2]) {
            index = 42;
        }
        else if (index == 26 && edges[0] && edges[1] && edges[2] && edges[3]) {
            index = 46;
        }
        else if (index == 26 && !edges[0] && edges[1] && edges[2] && edges[3]) {
            index = 9;
        }
        else if (index == 26 && edges[0] && !edges[1] && edges[2] && edges[3]) {
            index = 21;
        }
        else if (index == 26 && edges[0] && edges[1] && !edges[2] && edges[3]) {
            index = 8;
        }
        else if (index == 26 && edges[0] && edges[1] && edges[2] && !edges[3]) {
            index = 20;
        }
        else if (index == 26 && edges[0] && edges[1] && !edges[2] && !edges[3]) {
            index = 11;
        }
        else if (index == 26 && !edges[0] && !edges[1] && edges[2] && edges[3]) {
            index = 22;
        }
        else if (index == 26 && !edges[0] && edges[1] && !edges[2] && edges[3]) {
            index = 23;
        }
        else if (index == 26 && edges[0] && !edges[1] && edges[2] && !edges[3]) {
            index = 10;
        }
        else if (index == 26 && edges[0] && !edges[1] && !edges[2] && edges[3]) {
            index = 34;
        }
        else if (index == 26 && !edges[0] && edges[1] && edges[2] && !edges[3]) {
            index = 35;
        }
        else if (index == 26 && edges[0] && !edges[1] && !edges[2] && !edges[3]) {
            index = 32;
        }
        else if (index == 26 && !edges[0] && edges[1] && !edges[2] && !edges[3]) {
            index = 33;
        }
        else if (index == 26 && !edges[0] && !edges[1] && edges[2] && !edges[3]) {
            index = 44;
        }
        else if (index == 26 && !edges[0] && !edges[1] && !edges[2] && edges[3]) {
            index = 45;
        }
        return index;
    }
    
    private static void switchValues(final int ix1, final int ix2, final boolean[] arr) {
        final boolean prev1 = arr[ix1];
        arr[ix1] = arr[ix2];
        arr[ix2] = prev1;
    }
    
    private static boolean isNeighbourOverlay(final ConnectedProperties cp, final cls worldReader, final dcb blockState, final gu blockPos, final int side, final fuv icon, final int metadata) {
        final dcb neighbourState = worldReader.a_(blockPos);
        if (!isFullCubeModel(neighbourState, worldReader, blockPos)) {
            return false;
        }
        if (cp.connectBlocks != null && !Matches.block(neighbourState.getBlockId(), neighbourState.getMetadata(), cp.connectBlocks)) {
            return false;
        }
        if (cp.connectTileIcons != null) {
            final fuv neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
            if (!Config.isSameOne(neighbourIcon, cp.connectTileIcons)) {
                return false;
            }
        }
        final gu posNeighbourStateAbove = blockPos.a(getFacing(side));
        final dcb neighbourStateAbove = worldReader.a_(posNeighbourStateAbove);
        return !neighbourStateAbove.i(worldReader, posNeighbourStateAbove) && (side != 1 || neighbourStateAbove.b() != cpo.dN) && !isNeighbour(cp, worldReader, blockState, blockPos, neighbourState, side, icon, metadata);
    }
    
    private static boolean isFullCubeModel(final dcb state, final cls blockReader, final gu pos) {
        if (BlockUtils.isFullCube(state, blockReader, pos)) {
            return true;
        }
        final cpn block = state.b();
        return block instanceof css || block instanceof cxf;
    }
    
    private static boolean isNeighbourMatching(final ConnectedProperties cp, final cls worldReader, final dcb blockState, final gu blockPos, final int side, final fuv icon, final int metadata) {
        final dcb neighbourState = worldReader.a_(blockPos);
        if (neighbourState == ConnectedTextures.AIR_DEFAULT_STATE) {
            return false;
        }
        if (cp.matchBlocks != null && !cp.matchesBlock(neighbourState.getBlockId(), neighbourState.getMetadata())) {
            return false;
        }
        if (cp.matchTileIcons != null) {
            final fuv neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
            if (neighbourIcon != icon) {
                return false;
            }
        }
        final gu posNeighbourAbove = blockPos.a(getFacing(side));
        final dcb neighbourStateAbove = worldReader.a_(posNeighbourAbove);
        return !neighbourStateAbove.i(worldReader, posNeighbourAbove) && (side != 1 || neighbourStateAbove.b() != cpo.dN);
    }
    
    private static boolean isNeighbour(final ConnectedProperties cp, final cls worldReader, final dcb blockState, final gu blockPos, final int side, final fuv icon, final int metadata) {
        final dcb neighbourState = worldReader.a_(blockPos);
        return isNeighbour(cp, worldReader, blockState, blockPos, neighbourState, side, icon, metadata);
    }
    
    private static boolean isNeighbour(final ConnectedProperties cp, final cls worldReader, final dcb blockState, final gu blockPos, final dcb neighbourState, final int side, final fuv icon, final int metadata) {
        if (blockState == neighbourState) {
            return true;
        }
        if (cp.connect == 2) {
            if (neighbourState == null) {
                return false;
            }
            if (neighbourState == ConnectedTextures.AIR_DEFAULT_STATE) {
                return false;
            }
            final fuv neighbourIcon = getNeighbourIcon(worldReader, blockState, blockPos, neighbourState, side);
            return neighbourIcon == icon;
        }
        else {
            if (cp.connect == 1) {
                final cpn block = blockState.b();
                final cpn neighbourBlock = neighbourState.b();
                return neighbourBlock == block;
            }
            return false;
        }
    }
    
    private static fuv getNeighbourIcon(final cls worldReader, final dcb blockState, final gu blockPos, final dcb neighbourState, final int side) {
        final fwr model = enn.N().am().a().b(neighbourState);
        if (model == null) {
            return null;
        }
        final ha facing = getFacing(side);
        List quads = model.a(neighbourState, facing, ConnectedTextures.RANDOM);
        if (quads == null) {
            return null;
        }
        if (Config.isBetterGrass()) {
            quads = BetterGrass.getFaceQuads(worldReader, neighbourState, blockPos, facing, quads);
        }
        if (quads.size() > 0) {
            final fkr quad = quads.get(0);
            return quad.a();
        }
        final List quadsGeneral = model.a(neighbourState, (ha)null, ConnectedTextures.RANDOM);
        if (quadsGeneral == null) {
            return null;
        }
        for (int i = 0; i < quadsGeneral.size(); ++i) {
            final fkr quad2 = quadsGeneral.get(i);
            if (quad2.e() == facing) {
                return quad2.a();
            }
        }
        return null;
    }
    
    private static fuv getConnectedTextureHorizontal(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fuv icon, final int metadata) {
        boolean left = false;
        boolean right = false;
        Label_0859: {
            switch (vertAxis) {
                case 0: {
                    switch (side) {
                        case 0: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                            break;
                        }
                        case 1: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                            break;
                        }
                        case 2: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                            break;
                        }
                        case 3: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                            break;
                        }
                        case 4: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                            break;
                        }
                        case 5: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (side) {
                        case 2: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                            break;
                        }
                        case 3: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                            break;
                        }
                        case 0: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                            break;
                        }
                        case 1: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                            break;
                        }
                        case 4: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                            break;
                        }
                        case 5: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (side) {
                        case 4: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                            break Label_0859;
                        }
                        case 5: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                            break Label_0859;
                        }
                        case 2: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                            break Label_0859;
                        }
                        case 3: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                            break Label_0859;
                        }
                        case 0: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                            break Label_0859;
                        }
                        case 1: {
                            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                            break Label_0859;
                        }
                    }
                    break;
                }
            }
        }
        int index = 3;
        if (left) {
            if (right) {
                index = 1;
            }
            else {
                index = 2;
            }
        }
        else if (right) {
            index = 0;
        }
        else {
            index = 3;
        }
        return cp.tileIcons[index];
    }
    
    private static fuv getConnectedTextureVertical(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fuv icon, final int metadata) {
        boolean bottom = false;
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1) {
                    bottom = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                    top = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                    break;
                }
                if (side == 0) {
                    bottom = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                    top = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                    break;
                }
                bottom = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                top = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3) {
                    bottom = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                    top = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                    break;
                }
                if (side == 2) {
                    bottom = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                    top = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                    break;
                }
                bottom = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                top = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5) {
                    bottom = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                    top = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                    break;
                }
                if (side == 4) {
                    bottom = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
                    top = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                    break;
                }
                bottom = isNeighbour(cp, blockAccess, blockState, blockPos.g(), side, icon, metadata);
                top = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                break;
            }
        }
        int index = 3;
        if (bottom) {
            if (top) {
                index = 1;
            }
            else {
                index = 2;
            }
        }
        else if (top) {
            index = 0;
        }
        else {
            index = 3;
        }
        return cp.tileIcons[index];
    }
    
    private static fuv getConnectedTextureHorizontalVertical(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fuv icon, final int metadata) {
        final fuv[] tileIcons = cp.tileIcons;
        final fuv iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
            return iconH;
        }
        final fuv iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        if (iconV == tileIcons[0]) {
            return tileIcons[4];
        }
        if (iconV == tileIcons[1]) {
            return tileIcons[5];
        }
        if (iconV == tileIcons[2]) {
            return tileIcons[6];
        }
        return iconV;
    }
    
    private static fuv getConnectedTextureVerticalHorizontal(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fuv icon, final int metadata) {
        final fuv[] tileIcons = cp.tileIcons;
        final fuv iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
            return iconV;
        }
        final fuv iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        if (iconH == tileIcons[0]) {
            return tileIcons[4];
        }
        if (iconH == tileIcons[1]) {
            return tileIcons[5];
        }
        if (iconH == tileIcons[2]) {
            return tileIcons[6];
        }
        return iconH;
    }
    
    private static fuv getConnectedTextureTop(final ConnectedProperties cp, final cls blockAccess, final dcb blockState, final gu blockPos, final int vertAxis, final int side, final fuv icon, final int metadata) {
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1 || side == 0) {
                    return null;
                }
                top = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3 || side == 2) {
                    return null;
                }
                top = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5 || side == 4) {
                    return null;
                }
                top = isNeighbour(cp, blockAccess, blockState, blockPos.h(), side, icon, metadata);
                break;
            }
        }
        if (top) {
            return cp.tileIcons[0];
        }
        return null;
    }
    
    public static void updateIcons(final fuu textureMap) {
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        ConnectedTextures.spriteQuadMaps = null;
        ConnectedTextures.spriteQuadCompactMaps = null;
        if (!Config.isConnectedTextures()) {
            return;
        }
        final ajl[] rps = Config.getResourcePacks();
        for (int i = rps.length - 1; i >= 0; --i) {
            final ajl rp = rps[i];
            updateIcons(textureMap, rp);
        }
        updateIcons(textureMap, (ajl)Config.getDefaultResourcePack());
        ConnectedTextures.emptySprite = textureMap.registerSprite(ConnectedTextures.LOCATION_SPRITE_EMPTY);
        ConnectedTextures.spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
        ConnectedTextures.spriteQuadFullMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
        ConnectedTextures.spriteQuadCompactMaps = new Map[textureMap.getCountRegisteredSprites() + 1][];
        if (ConnectedTextures.blockProperties.length <= 0) {
            ConnectedTextures.blockProperties = null;
        }
        if (ConnectedTextures.tileProperties.length <= 0) {
            ConnectedTextures.tileProperties = null;
        }
    }
    
    public static void updateIcons(final fuu textureMap, final ajl rp) {
        final String[] names = ResUtils.collectFiles(rp, "optifine/ctm/", ".properties", getDefaultCtmPaths());
        Arrays.sort(names);
        final List tileList = makePropertyList(ConnectedTextures.tileProperties);
        final List blockList = makePropertyList(ConnectedTextures.blockProperties);
        for (int i = 0; i < names.length; ++i) {
            final String name = names[i];
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            try {
                final acq locFile = new acq(name);
                final InputStream in = Config.getResourceStream(rp, ajm.a, locFile);
                if (in == null) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
                }
                else {
                    final Properties props = new PropertiesOrdered();
                    props.load(in);
                    in.close();
                    final ConnectedProperties cp = new ConnectedProperties(props, name);
                    if (cp.isValid(name)) {
                        cp.updateIcons(textureMap);
                        addToTileList(cp, tileList);
                        addToBlockList(cp, blockList);
                    }
                }
            }
            catch (FileNotFoundException e2) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        ConnectedTextures.blockProperties = propertyListToArray(blockList);
        ConnectedTextures.tileProperties = propertyListToArray(tileList);
        ConnectedTextures.multipass = detectMultipass();
        Config.dbg(invokedynamic(makeConcatWithConstants:(Z)Ljava/lang/String;, ConnectedTextures.multipass));
    }
    
    public static void refreshIcons(final fuu textureMap) {
        refreshIcons(ConnectedTextures.blockProperties, textureMap);
        refreshIcons(ConnectedTextures.tileProperties, textureMap);
        ConnectedTextures.emptySprite = getSprite(textureMap, ConnectedTextures.LOCATION_SPRITE_EMPTY);
    }
    
    private static fuv getSprite(final fuu textureMap, final acq loc) {
        final fuv sprite = textureMap.a(loc);
        if (sprite == null || ful.isMisingSprite(sprite)) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Lacq;)Ljava/lang/String;, loc));
        }
        return sprite;
    }
    
    private static void refreshIcons(final ConnectedProperties[][] propertiesArray, final fuu textureMap) {
        if (propertiesArray == null) {
            return;
        }
        for (int i = 0; i < propertiesArray.length; ++i) {
            final ConnectedProperties[] properties = propertiesArray[i];
            if (properties != null) {
                for (int c = 0; c < properties.length; ++c) {
                    final ConnectedProperties cp = properties[c];
                    if (cp != null) {
                        cp.refreshIcons(textureMap);
                    }
                }
            }
        }
    }
    
    private static List makePropertyList(final ConnectedProperties[][] propsArr) {
        final List list = new ArrayList();
        if (propsArr != null) {
            for (int i = 0; i < propsArr.length; ++i) {
                final ConnectedProperties[] props = propsArr[i];
                List propList = null;
                if (props != null) {
                    propList = new ArrayList(Arrays.asList(props));
                }
                list.add(propList);
            }
        }
        return list;
    }
    
    private static boolean detectMultipass() {
        final List propList = new ArrayList();
        for (int i = 0; i < ConnectedTextures.tileProperties.length; ++i) {
            final ConnectedProperties[] cps = ConnectedTextures.tileProperties[i];
            if (cps != null) {
                propList.addAll(Arrays.asList(cps));
            }
        }
        for (int i = 0; i < ConnectedTextures.blockProperties.length; ++i) {
            final ConnectedProperties[] cps = ConnectedTextures.blockProperties[i];
            if (cps != null) {
                propList.addAll(Arrays.asList(cps));
            }
        }
        final ConnectedProperties[] props = propList.toArray(new ConnectedProperties[propList.size()]);
        final Set matchIconSet = new HashSet();
        final Set tileIconSet = new HashSet();
        for (int j = 0; j < props.length; ++j) {
            final ConnectedProperties cp = props[j];
            if (cp.matchTileIcons != null) {
                matchIconSet.addAll(Arrays.asList(cp.matchTileIcons));
            }
            if (cp.tileIcons != null) {
                tileIconSet.addAll(Arrays.asList(cp.tileIcons));
            }
        }
        matchIconSet.retainAll(tileIconSet);
        return !matchIconSet.isEmpty();
    }
    
    private static ConnectedProperties[][] propertyListToArray(final List list) {
        final ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            final List subList = list.get(i);
            if (subList != null) {
                final ConnectedProperties[] subArr = subList.toArray(new ConnectedProperties[subList.size()]);
                propArr[i] = subArr;
            }
        }
        return propArr;
    }
    
    private static void addToTileList(final ConnectedProperties cp, final List tileList) {
        if (cp.matchTileIcons == null) {
            return;
        }
        for (int i = 0; i < cp.matchTileIcons.length; ++i) {
            final fuv icon = cp.matchTileIcons[i];
            if (!(icon instanceof fuv)) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Lfuv;Lacq;)Ljava/lang/String;, icon, icon.getName()));
            }
            else {
                final fuv ts = icon;
                final int tileId = ts.getIndexInMap();
                if (tileId < 0) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(ILacq;)Ljava/lang/String;, tileId, ts.getName()));
                }
                else {
                    addToList(cp, tileList, tileId);
                }
            }
        }
    }
    
    private static void addToBlockList(final ConnectedProperties cp, final List blockList) {
        if (cp.matchBlocks == null) {
            return;
        }
        for (int i = 0; i < cp.matchBlocks.length; ++i) {
            final int blockId = cp.matchBlocks[i].getBlockId();
            if (blockId < 0) {
                Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, blockId));
            }
            else {
                addToList(cp, blockList, blockId);
            }
        }
    }
    
    private static void addToList(final ConnectedProperties cp, final List list, final int id) {
        while (id >= list.size()) {
            list.add(null);
        }
        List subList = list.get(id);
        if (subList == null) {
            subList = new ArrayList();
            list.set(id, subList);
        }
        subList.add(cp);
    }
    
    private static String[] getDefaultCtmPaths() {
        final List list = new ArrayList();
        addDefaultLocation(list, "textures/block/glass.png", "20_glass/glass.properties");
        addDefaultLocation(list, "textures/block/glass.png", "20_glass/glass_pane.properties");
        addDefaultLocation(list, "textures/block/tinted_glass.png", "21_tinted_glass/tinted_glass.properties");
        addDefaultLocation(list, "textures/block/bookshelf.png", "30_bookshelf/bookshelf.properties");
        addDefaultLocation(list, "textures/block/sandstone.png", "40_sandstone/sandstone.properties");
        addDefaultLocation(list, "textures/block/red_sandstone.png", "41_red_sandstone/red_sandstone.properties");
        final String[] colors = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black" };
        for (int i = 0; i < colors.length; ++i) {
            final String color = colors[i];
            final String prefix = StrUtils.fillLeft(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i), 2, '0');
            addDefaultLocation(list, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, color), invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, prefix, color, color));
            addDefaultLocation(list, invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, color), invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, prefix, color, color));
        }
        final String[] paths = list.toArray(new String[list.size()]);
        return paths;
    }
    
    private static void addDefaultLocation(final List list, final String locBase, final String pathSuffix) {
        final String defPath = "optifine/ctm/default/";
        final acq loc = new acq(locBase);
        final ajl rp = Config.getDefiningResourcePack(loc);
        if (rp == null) {
            return;
        }
        if (rp.a().equals("programmer_art")) {
            final String defPathPa = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, defPath);
            list.add(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, defPathPa, pathSuffix));
            return;
        }
        if (rp == Config.getDefaultResourcePack()) {
            list.add(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, defPath, pathSuffix));
        }
    }
    
    static {
        ConnectedTextures.spriteQuadMaps = null;
        ConnectedTextures.spriteQuadFullMaps = null;
        ConnectedTextures.spriteQuadCompactMaps = null;
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        ConnectedTextures.multipass = false;
        ConnectedTextures.AIR_DEFAULT_STATE = cpo.a.n();
        ConnectedTextures.emptySprite = null;
        ConnectedTextures.LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
        SIDES_Y_NEG_DOWN = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH };
        SIDES_Y_POS_UP = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH };
        SIDES_Z_NEG_NORTH = new BlockDir[] { BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP };
        SIDES_Z_POS_SOUTH = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP };
        SIDES_X_NEG_WEST = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP };
        SIDES_X_POS_EAST = new BlockDir[] { BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP };
        EDGES_Y_NEG_DOWN = new BlockDir[] { BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST };
        EDGES_Y_POS_UP = new BlockDir[] { BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST };
        EDGES_Z_NEG_NORTH = new BlockDir[] { BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST };
        EDGES_Z_POS_SOUTH = new BlockDir[] { BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST };
        EDGES_X_NEG_WEST = new BlockDir[] { BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH };
        EDGES_X_POS_EAST = new BlockDir[] { BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH };
        ConnectedTextures.SPRITE_DEFAULT = new fuv(fuu.e, new acq("default"));
        ConnectedTextures.RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
    }
}
