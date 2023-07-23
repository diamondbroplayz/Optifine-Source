// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.Config;
import java.util.Collection;
import java.util.List;
import it.unimi.dsi.fastutil.longs.Long2ByteLinkedOpenHashMap;
import net.optifine.render.RenderEnv;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;

public class BlockUtils
{
    private static final ThreadLocal<RenderSideCacheKey> threadLocalKey;
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<RenderSideCacheKey>> threadLocalMap;
    
    public static boolean shouldSideBeRendered(final dcb blockStateIn, final cls blockReaderIn, final gu blockPosIn, final ha facingIn, final RenderEnv renderEnv) {
        final gu posNeighbour = blockPosIn.a(facingIn);
        final dcb stateNeighbour = blockReaderIn.a_(posNeighbour);
        return !stateNeighbour.isCacheOpaqueCube() && !blockStateIn.a(stateNeighbour, facingIn) && (!stateNeighbour.p() || shouldSideBeRenderedCached(blockStateIn, blockReaderIn, blockPosIn, facingIn, renderEnv, stateNeighbour, posNeighbour));
    }
    
    public static boolean shouldSideBeRenderedCached(final dcb blockStateIn, final cls blockReaderIn, final gu blockPosIn, final ha facingIn, final RenderEnv renderEnv, final dcb stateNeighbourIn, final gu posNeighbourIn) {
        final long key = (long)blockStateIn.getBlockStateId() << 36 | (long)stateNeighbourIn.getBlockStateId() << 4 | (long)facingIn.ordinal();
        final Long2ByteLinkedOpenHashMap map = renderEnv.getRenderSideMap();
        final byte b0 = map.getAndMoveToFirst(key);
        if (b0 != 0) {
            return b0 > 0;
        }
        final efb voxelshape = blockStateIn.a(blockReaderIn, blockPosIn, facingIn);
        if (voxelshape.b()) {
            return true;
        }
        final efb voxelshape2 = stateNeighbourIn.a(blockReaderIn, posNeighbourIn, facingIn.g());
        final boolean flag = eey.c(voxelshape, voxelshape2, eem.e);
        if (map.size() > 400) {
            map.removeLastByte();
        }
        map.putAndMoveToFirst(key, (byte)(flag ? 1 : -1));
        return flag;
    }
    
    public static int getBlockId(final cpn block) {
        return jb.f.a((Object)block);
    }
    
    public static cpn getBlock(final acq loc) {
        if (!jb.f.c(loc)) {
            return null;
        }
        return (cpn)jb.f.a(loc);
    }
    
    public static int getMetadata(final dcb blockState) {
        final cpn block = blockState.b();
        final dcc<cpn, dcb> stateContainer = (dcc<cpn, dcb>)block.l();
        final List<dcb> validStates = (List<dcb>)stateContainer.a();
        final int metadata = validStates.indexOf(blockState);
        return metadata;
    }
    
    public static int getMetadataCount(final cpn block) {
        final dcc<cpn, dcb> stateContainer = (dcc<cpn, dcb>)block.l();
        final List<dcb> validStates = (List<dcb>)stateContainer.a();
        return validStates.size();
    }
    
    public static dcb getBlockState(final cpn block, final int metadata) {
        final dcc<cpn, dcb> stateContainer = (dcc<cpn, dcb>)block.l();
        final List<dcb> validStates = (List<dcb>)stateContainer.a();
        if (metadata < 0 || metadata >= validStates.size()) {
            return null;
        }
        final dcb blockState = validStates.get(metadata);
        return blockState;
    }
    
    public static List<dcb> getBlockStates(final cpn block) {
        final dcc<cpn, dcb> stateContainer = (dcc<cpn, dcb>)block.l();
        final List<dcb> validStates = (List<dcb>)stateContainer.a();
        return validStates;
    }
    
    public static boolean isFullCube(final dcb stateIn, final cls blockReaderIn, final gu posIn) {
        return stateIn.isCacheOpaqueCollisionShape();
    }
    
    public static Collection<dde> getProperties(final dcb blockState) {
        return (Collection<dde>)blockState.B();
    }
    
    public static boolean isPropertyTrue(final dcb blockState, final dcs prop) {
        final Boolean value = (Boolean)blockState.C().get((Object)prop);
        return Config.isTrue(value);
    }
    
    public static boolean isPropertyFalse(final dcb blockState, final dcs prop) {
        final Boolean value = (Boolean)blockState.C().get((Object)prop);
        return Config.isFalse(value);
    }
    
    static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     5: invokestatic    java/lang/ThreadLocal.withInitial:(Ljava/util/function/Supplier;)Ljava/lang/ThreadLocal;
        //     8: putstatic       net/optifine/util/BlockUtils.threadLocalKey:Ljava/lang/ThreadLocal;
        //    11: invokedynamic   BootstrapMethod #1, get:()Ljava/util/function/Supplier;
        //    16: invokestatic    java/lang/ThreadLocal.withInitial:(Ljava/util/function/Supplier;)Ljava/lang/ThreadLocal;
        //    19: putstatic       net/optifine/util/BlockUtils.threadLocalMap:Ljava/lang/ThreadLocal;
        //    22: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 9219 out of bounds for byte[6556]
        //     at java.base/java.lang.System.arraycopy(Native Method)
        //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
        //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
        //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
        //     at com.strobel.decompiler.ast.AstOptimizer$InlineLambdasOptimization.tryInlineLambda(AstOptimizer.java:3513)
        //     at com.strobel.decompiler.ast.AstOptimizer$InlineLambdasOptimization.run(AstOptimizer.java:3488)
        //     at com.strobel.decompiler.ast.AstOptimizer.runOptimization(AstOptimizer.java:3876)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:220)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
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
    
    public static final class RenderSideCacheKey
    {
        private BlockState blockState1;
        private BlockState blockState2;
        private Direction facing;
        private int hashCode;
        
        private RenderSideCacheKey(final BlockState blockState1In, final BlockState blockState2In, final Direction facingIn) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     2: lload_1         /* blockState1In */
            //     3: nop            
            //     4: fconst_2       
            //     5: nop            
            //     6: iconst_0       
            //     7: nop            
            //     8: aconst_null    
            //     9: nop            
            //    10: fload_1         /* blockState1In */
            //    11: nop            
            //    12: fload_2         /* blockState2In */
            //    13: nop            
            //    14: aconst_null    
            //    15: nop            
            //    16: baload         
            //    17: nop            
            //    18: nop            
            //    19: nop            
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        private void init(final BlockState blockState1In, final BlockState blockState2In, final Direction facingIn) {
            // 
            // This method could not be decompiled.
            // 
            // Could not show original bytecode, likely due to the same error.
            // 
            // The error that occurred was:
            // 
            // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/optifine/util/BlockUtils$RenderSideCacheKey.init:(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)V'.
            //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
            //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
            //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
            // Caused by: java.nio.BufferUnderflowException
            //     at com.strobel.assembler.metadata.Buffer.verifyReadableBytes(Buffer.java:387)
            //     at com.strobel.assembler.metadata.Buffer.readUnsignedShort(Buffer.java:225)
            //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:214)
            //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
            //     ... 19 more
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public RenderSideCacheKey duplicate() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: nop            
            //     2: nop            
            //     3: nop            
            //     4: nop            
            //     5: istore_1       
            //     6: nop            
            //     7: aload_0         /* this */
            //     8: nop            
            //     9: aload_1        
            //    10: nop            
            //    11: aconst_null    
            //    12: nop            
            //    13: aconst_null    
            //    14: nop            
            //    15: dconst_0       
            //    16: nop            
            //    17: aload_2        
            //    18: nop            
            //    19: aconst_null    
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
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:414)
            //     at com.strobel.assembler.ir.Instruction.accept(Instruction.java:490)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:403)
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
        
        @Override
        public boolean equals(final Object p_equals_1_) {
            // 
            // This method could not be decompiled.
            // 
            // Could not show original bytecode, likely due to the same error.
            // 
            // The error that occurred was:
            // 
            // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/optifine/util/BlockUtils$RenderSideCacheKey.equals:(Ljava/lang/Object;)Z'.
            //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
            //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
            //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
            // Caused by: java.lang.NullPointerException
            //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:170)
            //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
            //     ... 19 more
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @Override
        public int hashCode() {
            // 
            // This method could not be decompiled.
            // 
            // Could not show original bytecode, likely due to the same error.
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 1574 out of bounds for byte[1453]
            //     at java.base/java.lang.System.arraycopy(Native Method)
            //     at com.strobel.assembler.ir.attributes.CodeAttribute.<init>(CodeAttribute.java:60)
            //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:700)
            //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
