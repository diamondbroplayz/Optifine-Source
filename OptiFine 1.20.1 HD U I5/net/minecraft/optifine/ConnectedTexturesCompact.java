// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.optifine.render.RenderEnv;

public class ConnectedTexturesCompact
{
    private static final int COMPACT_NONE = 0;
    private static final int COMPACT_ALL = 1;
    private static final int COMPACT_V = 2;
    private static final int COMPACT_H = 3;
    private static final int COMPACT_HV = 4;
    
    public static fkr[] getConnectedTextureCtmCompact(final int ctmIndex, final ConnectedProperties cp, final int side, final fkr quad, final RenderEnv renderEnv) {
        if (cp.ctmTileIndexes != null && ctmIndex >= 0 && ctmIndex < cp.ctmTileIndexes.length) {
            final int tileIndex = cp.ctmTileIndexes[ctmIndex];
            if (tileIndex >= 0 && tileIndex <= cp.tileIcons.length) {
                return getQuadsCompact(tileIndex, cp.tileIcons, quad, renderEnv);
            }
        }
        switch (ctmIndex) {
            case 1: {
                return getQuadsCompactH(0, 3, cp.tileIcons, side, quad, renderEnv);
            }
            case 2: {
                return getQuadsCompact(3, cp.tileIcons, quad, renderEnv);
            }
            case 3: {
                return getQuadsCompactH(3, 0, cp.tileIcons, side, quad, renderEnv);
            }
            case 12: {
                return getQuadsCompactV(0, 2, cp.tileIcons, side, quad, renderEnv);
            }
            case 24: {
                return getQuadsCompact(2, cp.tileIcons, quad, renderEnv);
            }
            case 36: {
                return getQuadsCompactV(2, 0, cp.tileIcons, side, quad, renderEnv);
            }
            case 13: {
                return getQuadsCompact4(0, 3, 2, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 14: {
                return getQuadsCompactV(3, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 15: {
                return getQuadsCompact4(3, 0, 1, 2, cp.tileIcons, side, quad, renderEnv);
            }
            case 25: {
                return getQuadsCompactH(2, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 26: {
                return getQuadsCompact(1, cp.tileIcons, quad, renderEnv);
            }
            case 27: {
                return getQuadsCompactH(1, 2, cp.tileIcons, side, quad, renderEnv);
            }
            case 37: {
                return getQuadsCompact4(2, 1, 0, 3, cp.tileIcons, side, quad, renderEnv);
            }
            case 38: {
                return getQuadsCompactV(1, 3, cp.tileIcons, side, quad, renderEnv);
            }
            case 39: {
                return getQuadsCompact4(1, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
            }
            case 4: {
                return getQuadsCompact4(0, 3, 2, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 5: {
                return getQuadsCompact4(3, 0, 4, 2, cp.tileIcons, side, quad, renderEnv);
            }
            case 16: {
                return getQuadsCompact4(2, 4, 0, 3, cp.tileIcons, side, quad, renderEnv);
            }
            case 17: {
                return getQuadsCompact4(4, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
            }
            case 28: {
                return getQuadsCompact4(2, 4, 2, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 29: {
                return getQuadsCompact4(3, 3, 1, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 40: {
                return getQuadsCompact4(4, 1, 3, 3, cp.tileIcons, side, quad, renderEnv);
            }
            case 41: {
                return getQuadsCompact4(1, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
            }
            case 6: {
                return getQuadsCompact4(2, 4, 2, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 7: {
                return getQuadsCompact4(3, 3, 4, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 18: {
                return getQuadsCompact4(4, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
            }
            case 19: {
                return getQuadsCompact4(4, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
            }
            case 30: {
                return getQuadsCompact4(2, 1, 2, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 31: {
                return getQuadsCompact4(3, 3, 4, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 42: {
                return getQuadsCompact4(1, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
            }
            case 43: {
                return getQuadsCompact4(4, 2, 1, 2, cp.tileIcons, side, quad, renderEnv);
            }
            case 8: {
                return getQuadsCompact4(4, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 9: {
                return getQuadsCompact4(4, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 20: {
                return getQuadsCompact4(1, 4, 4, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 21: {
                return getQuadsCompact4(4, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 32: {
                return getQuadsCompact4(1, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 33: {
                return getQuadsCompact4(1, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 44: {
                return getQuadsCompact4(1, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 45: {
                return getQuadsCompact4(4, 1, 1, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 10: {
                return getQuadsCompact4(1, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 11: {
                return getQuadsCompact4(1, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 22: {
                return getQuadsCompact4(4, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 23: {
                return getQuadsCompact4(4, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 34: {
                return getQuadsCompact4(4, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
            }
            case 35: {
                return getQuadsCompact4(1, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
            }
            case 46: {
                return getQuadsCompact(4, cp.tileIcons, quad, renderEnv);
            }
            default: {
                return getQuadsCompact(0, cp.tileIcons, quad, renderEnv);
            }
        }
    }
    
    private static fkr[] getQuadsCompactH(final int indexLeft, final int indexRight, final fuv[] sprites, final int side, final fkr quad, final RenderEnv renderEnv) {
        return getQuadsCompact(Dir.LEFT, indexLeft, Dir.RIGHT, indexRight, sprites, side, quad, renderEnv);
    }
    
    private static fkr[] getQuadsCompactV(final int indexUp, final int indexDown, final fuv[] sprites, final int side, final fkr quad, final RenderEnv renderEnv) {
        return getQuadsCompact(Dir.UP, indexUp, Dir.DOWN, indexDown, sprites, side, quad, renderEnv);
    }
    
    private static fkr[] getQuadsCompact4(final int upLeft, final int upRight, final int downLeft, final int downRight, final fuv[] sprites, final int side, final fkr quad, final RenderEnv renderEnv) {
        if (upLeft == upRight) {
            if (downLeft == downRight) {
                return getQuadsCompact(Dir.UP, upLeft, Dir.DOWN, downLeft, sprites, side, quad, renderEnv);
            }
            return getQuadsCompact(Dir.UP, upLeft, Dir.DOWN_LEFT, downLeft, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv);
        }
        else {
            if (downLeft == downRight) {
                return getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN, downLeft, sprites, side, quad, renderEnv);
            }
            if (upLeft == downLeft) {
                if (upRight == downRight) {
                    return getQuadsCompact(Dir.LEFT, upLeft, Dir.RIGHT, upRight, sprites, side, quad, renderEnv);
                }
                return getQuadsCompact(Dir.LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv);
            }
            else {
                if (upRight == downRight) {
                    return getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.DOWN_LEFT, downLeft, Dir.RIGHT, upRight, sprites, side, quad, renderEnv);
                }
                return getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN_LEFT, downLeft, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv);
            }
        }
    }
    
    private static fkr[] getQuadsCompact(final int index, final fuv[] sprites, final fkr quad, final RenderEnv renderEnv) {
        final fuv sprite = sprites[index];
        return ConnectedTextures.getQuads(sprite, quad, renderEnv);
    }
    
    private static fkr[] getQuadsCompact(final Dir dir1, final int index1, final Dir dir2, final int index2, final fuv[] sprites, final int side, final fkr quad, final RenderEnv renderEnv) {
        final fkr quad2 = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
        final fkr quad3 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
        return renderEnv.getArrayQuadsCtm(quad2, quad3);
    }
    
    private static fkr[] getQuadsCompact(final Dir dir1, final int index1, final Dir dir2, final int index2, final Dir dir3, final int index3, final fuv[] sprites, final int side, final fkr quad, final RenderEnv renderEnv) {
        final fkr quad2 = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
        final fkr quad3 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
        final fkr quad4 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
        return renderEnv.getArrayQuadsCtm(quad2, quad3, quad4);
    }
    
    private static fkr[] getQuadsCompact(final Dir dir1, final int index1, final Dir dir2, final int index2, final Dir dir3, final int index3, final Dir dir4, final int index4, final fuv[] sprites, final int side, final fkr quad, final RenderEnv renderEnv) {
        final fkr quad2 = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
        final fkr quad3 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
        final fkr quad4 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
        final fkr quad5 = getQuadCompact(sprites[index4], dir4, side, quad, renderEnv);
        return renderEnv.getArrayQuadsCtm(quad2, quad3, quad4, quad5);
    }
    
    private static fkr getQuadCompact(final fuv sprite, final Dir dir, final int side, final fkr quad, final RenderEnv renderEnv) {
        switch (ConnectedTexturesCompact.ConnectedTexturesCompact$1.$SwitchMap$net$optifine$ConnectedTexturesCompact$Dir[dir.ordinal()]) {
            case 1: {
                return getQuadCompact(sprite, dir, 0, 0, 16, 8, side, quad, renderEnv);
            }
            case 2: {
                return getQuadCompact(sprite, dir, 8, 0, 16, 8, side, quad, renderEnv);
            }
            case 3: {
                return getQuadCompact(sprite, dir, 8, 0, 16, 16, side, quad, renderEnv);
            }
            case 4: {
                return getQuadCompact(sprite, dir, 8, 8, 16, 16, side, quad, renderEnv);
            }
            case 5: {
                return getQuadCompact(sprite, dir, 0, 8, 16, 16, side, quad, renderEnv);
            }
            case 6: {
                return getQuadCompact(sprite, dir, 0, 8, 8, 16, side, quad, renderEnv);
            }
            case 7: {
                return getQuadCompact(sprite, dir, 0, 0, 8, 16, side, quad, renderEnv);
            }
            case 8: {
                return getQuadCompact(sprite, dir, 0, 0, 8, 8, side, quad, renderEnv);
            }
            default: {
                return quad;
            }
        }
    }
    
    private static fkr getQuadCompact(final fuv sprite, final Dir dir, final int x1, final int y1, final int x2, final int y2, final int side, final fkr quadIn, final RenderEnv renderEnv) {
        final Map[][] spriteQuadCompactMaps = ConnectedTextures.getSpriteQuadCompactMaps();
        if (spriteQuadCompactMaps == null) {
            return quadIn;
        }
        final int spriteIndex = sprite.getIndexInMap();
        if (spriteIndex < 0 || spriteIndex >= spriteQuadCompactMaps.length) {
            return quadIn;
        }
        Map[] quadMaps = spriteQuadCompactMaps[spriteIndex];
        if (quadMaps == null) {
            quadMaps = new Map[Dir.VALUES.length];
            spriteQuadCompactMaps[spriteIndex] = quadMaps;
        }
        Map<fkr, fkr> quadMap = (Map<fkr, fkr>)quadMaps[dir.ordinal()];
        if (quadMap == null) {
            quadMap = new IdentityHashMap<fkr, fkr>(1);
            quadMaps[dir.ordinal()] = quadMap;
        }
        fkr quad = quadMap.get(quadIn);
        if (quad == null) {
            quad = makeSpriteQuadCompact(quadIn, sprite, side, x1, y1, x2, y2);
            quadMap.put(quadIn, quad);
        }
        return quad;
    }
    
    private static fkr makeSpriteQuadCompact(final fkr quad, final fuv sprite, final int side, final int x1, final int y1, final int x2, final int y2) {
        final int[] data = quad.b().clone();
        final fuv spriteFrom = quad.a();
        for (int i = 0; i < 4; ++i) {
            fixVertexCompact(data, i, spriteFrom, sprite, side, x1, y1, x2, y2);
        }
        final fkr bq = new fkr(data, quad.d(), quad.e(), sprite, quad.f());
        return bq;
    }
    
    private static void fixVertexCompact(final int[] data, final int vertex, final fuv spriteFrom, final fuv spriteTo, final int side, final int x1, final int y1, final int x2, final int y2) {
        final int mul = data.length / 4;
        final int pos = mul * vertex;
        final float u = Float.intBitsToFloat(data[pos + 4]);
        final float v = Float.intBitsToFloat(data[pos + 4 + 1]);
        double su16 = spriteFrom.getSpriteU16(u);
        double sv16 = spriteFrom.getSpriteV16(v);
        float x3 = Float.intBitsToFloat(data[pos + 0]);
        float y3 = Float.intBitsToFloat(data[pos + 1]);
        float z = Float.intBitsToFloat(data[pos + 2]);
        float cu = 0.0f;
        float cv = 0.0f;
        switch (side) {
            case 0: {
                cu = x3;
                cv = 1.0f - z;
                break;
            }
            case 1: {
                cu = x3;
                cv = z;
                break;
            }
            case 5: {
                cu = 1.0f - z;
                cv = 1.0f - y3;
                break;
            }
            case 4: {
                cu = z;
                cv = 1.0f - y3;
                break;
            }
            case 3: {
                cu = x3;
                cv = 1.0f - y3;
                break;
            }
            case 2: {
                cu = 1.0f - x3;
                cv = 1.0f - y3;
                break;
            }
            default: {
                return;
            }
        }
        final float atlasW = spriteFrom.getWidth() / (spriteFrom.d() - spriteFrom.c());
        final float atlasH = spriteFrom.getHeight() / (spriteFrom.h() - spriteFrom.g());
        final float k = 4.0f / Math.max(atlasH, atlasW);
        final float u16F = 16.0f * (1.0f - k);
        final float v16F = 16.0f * (1.0f - k);
        if (su16 < x1) {
            cu += (float)((x1 - su16) / u16F);
            su16 = x1;
        }
        if (su16 > x2) {
            cu -= (float)((su16 - x2) / u16F);
            su16 = x2;
        }
        if (sv16 < y1) {
            cv += (float)((y1 - sv16) / v16F);
            sv16 = y1;
        }
        if (sv16 > y2) {
            cv -= (float)((sv16 - y2) / v16F);
            sv16 = y2;
        }
        switch (side) {
            case 0: {
                x3 = cu;
                z = 1.0f - cv;
                break;
            }
            case 1: {
                x3 = cu;
                z = cv;
                break;
            }
            case 5: {
                z = 1.0f - cu;
                y3 = 1.0f - cv;
                break;
            }
            case 4: {
                z = cu;
                y3 = 1.0f - cv;
                break;
            }
            case 3: {
                x3 = cu;
                y3 = 1.0f - cv;
                break;
            }
            case 2: {
                x3 = 1.0f - cu;
                y3 = 1.0f - cv;
                break;
            }
            default: {
                return;
            }
        }
        data[pos + 4] = Float.floatToRawIntBits(spriteTo.a(su16));
        data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.b(sv16));
        data[pos + 0] = Float.floatToRawIntBits(x3);
        data[pos + 1] = Float.floatToRawIntBits(y3);
        data[pos + 2] = Float.floatToRawIntBits(z);
    }
    
    private enum Dir
    {
        public static final Dir UP;
        public static final Dir UP_RIGHT;
        public static final Dir RIGHT;
        public static final Dir DOWN_RIGHT;
        public static final Dir DOWN;
        public static final Dir DOWN_LEFT;
        public static final Dir LEFT;
        public static final Dir UP_LEFT;
        public static final Dir[] VALUES;
        
        private Dir() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     3: return         
            //     4: nop            
            //     5: nop            
            //     6: nop            
            //    Signature:
            //  ()V
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalArgumentException: Argument 'offset' must be in the range [0, 0], but value was: 2.
            //     at com.strobel.core.VerifyArgument.inRange(VerifyArgument.java:347)
            //     at com.strobel.assembler.ir.StackMappingVisitor.getStackValue(StackMappingVisitor.java:67)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:691)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
            //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
            //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        static {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: ldc             "UP"
            //     3: iconst_0       
            //     4: invokespecial   net/optifine/ConnectedTexturesCompact$Dir.<init>:(Ljava/lang/String;I)V
            //     7: putstatic       net/optifine/ConnectedTexturesCompact$Dir.UP:Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    10: new             Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    13: dup            
            //    14: ldc             "UP_RIGHT"
            //    16: iconst_1       
            //    17: invokespecial   net/optifine/ConnectedTexturesCompact$Dir.<init>:(Ljava/lang/String;I)V
            //    20: putstatic       net/optifine/ConnectedTexturesCompact$Dir.UP_RIGHT:Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    23: new             Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    26: dup            
            //    27: ldc             "RIGHT"
            //    29: iconst_2       
            //    30: invokespecial   net/optifine/ConnectedTexturesCompact$Dir.<init>:(Ljava/lang/String;I)V
            //    33: putstatic       net/optifine/ConnectedTexturesCompact$Dir.RIGHT:Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    36: new             Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    39: dup            
            //    40: ldc             "DOWN_RIGHT"
            //    42: iconst_3       
            //    43: invokespecial   net/optifine/ConnectedTexturesCompact$Dir.<init>:(Ljava/lang/String;I)V
            //    46: putstatic       net/optifine/ConnectedTexturesCompact$Dir.DOWN_RIGHT:Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    49: new             Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    52: dup            
            //    53: ldc             "DOWN"
            //    55: iconst_4       
            //    56: invokespecial   net/optifine/ConnectedTexturesCompact$Dir.<init>:(Ljava/lang/String;I)V
            //    59: putstatic       net/optifine/ConnectedTexturesCompact$Dir.DOWN:Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    62: new             Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    65: dup            
            //    66: ldc             "DOWN_LEFT"
            //    68: iconst_5       
            //    69: invokespecial   net/optifine/ConnectedTexturesCompact$Dir.<init>:(Ljava/lang/String;I)V
            //    72: putstatic       net/optifine/ConnectedTexturesCompact$Dir.DOWN_LEFT:Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    75: new             Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    78: dup            
            //    79: ldc             "LEFT"
            //    81: bipush          6
            //    83: invokespecial   net/optifine/ConnectedTexturesCompact$Dir.<init>:(Ljava/lang/String;I)V
            //    86: putstatic       net/optifine/ConnectedTexturesCompact$Dir.LEFT:Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    89: new             Lnet/optifine/ConnectedTexturesCompact$Dir;
            //    92: dup            
            //    93: ldc             "UP_LEFT"
            //    95: bipush          7
            //    97: invokespecial   net/optifine/ConnectedTexturesCompact$Dir.<init>:(Ljava/lang/String;I)V
            //   100: putstatic       net/optifine/ConnectedTexturesCompact$Dir.UP_LEFT:Lnet/optifine/ConnectedTexturesCompact$Dir;
            //   103: invokestatic    net/optifine/ConnectedTexturesCompact$Dir.$values:()[Lnet/optifine/ConnectedTexturesCompact$Dir;
            //   106: putstatic       net/optifine/ConnectedTexturesCompact$Dir.$VALUES:[Lnet/optifine/ConnectedTexturesCompact$Dir;
            //   109: invokestatic    net/optifine/ConnectedTexturesCompact$Dir.values:()[Lnet/optifine/ConnectedTexturesCompact$Dir;
            //   112: putstatic       net/optifine/ConnectedTexturesCompact$Dir.VALUES:[Lnet/optifine/ConnectedTexturesCompact$Dir;
            //   115: return         
            //   116: nop            
            //   117: nop            
            //   118: nop            
            // 
            // The error that occurred was:
            // 
            // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
            //     at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            //     at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            //     at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            //     at java.base/java.util.Objects.checkIndex(Objects.java:372)
            //     at java.base/java.util.ArrayList.remove(ArrayList.java:535)
            //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:543)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
            //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
            //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
    }
}
