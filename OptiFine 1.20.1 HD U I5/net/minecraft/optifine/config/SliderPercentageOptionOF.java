// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

public class SliderPercentageOptionOF extends enq
{
    public SliderPercentageOptionOF(final String name, final double defVal) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1         /* name */
        //     2: invokestatic    enq.a:()Lenq$l;
        //     5: aload_1         /* name */
        //     6: invokedynamic   BootstrapMethod #0, toString:(Ljava/lang/String;)Lenq$b;
        //    11: getstatic       enq$m.a:Lenq$m;
        //    14: dload_2         /* defVal */
        //    15: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //    18: invokedynamic   BootstrapMethod #1, accept:()Ljava/util/function/Consumer;
        //    23: invokespecial   enq.<init>:(Ljava/lang/String;Lenq$l;Lenq$b;Lenq$n;Ljava/lang/Object;Ljava/util/function/Consumer;)V
        //    26: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 5903 out of bounds for byte[4691]
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
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
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
    
    public SliderPercentageOptionOF(final String name, final int valueMin, final int valueMax, final int step, final int valueDef) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1         /* name */
        //     2: invokestatic    enq.a:()Lenq$l;
        //     5: aload_1         /* name */
        //     6: invokedynamic   BootstrapMethod #2, toString:(Ljava/lang/String;)Lenq$b;
        //    11: new             Lenq$f;
        //    14: dup            
        //    15: iload_2         /* valueMin */
        //    16: iload           step
        //    18: idiv           
        //    19: iload_3         /* valueMax */
        //    20: iload           step
        //    22: idiv           
        //    23: invokespecial   enq$f.<init>:(II)V
        //    26: iload           step
        //    28: invokedynamic   BootstrapMethod #3, apply:(I)Ljava/util/function/IntFunction;
        //    33: iload           step
        //    35: invokedynamic   BootstrapMethod #4, applyAsInt:(I)Ljava/util/function/ToIntFunction;
        //    40: invokevirtual   enq$f.a:(Ljava/util/function/IntFunction;Ljava/util/function/ToIntFunction;)Lenq$k;
        //    43: iload_2         /* valueMin */
        //    44: iload_3         /* valueMax */
        //    45: invokestatic    com/mojang/serialization/Codec.intRange:(II)Lcom/mojang/serialization/Codec;
        //    48: iload           valueDef
        //    50: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    53: invokedynamic   BootstrapMethod #5, accept:()Ljava/util/function/Consumer;
        //    58: invokespecial   enq.<init>:(Ljava/lang/String;Lenq$l;Lenq$b;Lenq$n;Lcom/mojang/serialization/Codec;Ljava/lang/Object;Ljava/util/function/Consumer;)V
        //    61: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 5743 out of bounds for byte[4691]
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
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
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
    
    public SliderPercentageOptionOF(final String name, final int valueMin, final int valueMax, final int[] stepValues, final int valueDef) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1         /* name */
        //     2: invokestatic    enq.a:()Lenq$l;
        //     5: aload_1         /* name */
        //     6: invokedynamic   BootstrapMethod #6, toString:(Ljava/lang/String;)Lenq$b;
        //    11: new             Lenq$f;
        //    14: dup            
        //    15: iconst_0       
        //    16: aload           stepValues
        //    18: arraylength    
        //    19: iconst_1       
        //    20: isub           
        //    21: invokespecial   enq$f.<init>:(II)V
        //    24: aload           stepValues
        //    26: invokedynamic   BootstrapMethod #7, apply:([I)Ljava/util/function/IntFunction;
        //    31: aload           stepValues
        //    33: invokedynamic   BootstrapMethod #8, applyAsInt:([I)Ljava/util/function/ToIntFunction;
        //    38: invokevirtual   enq$f.a:(Ljava/util/function/IntFunction;Ljava/util/function/ToIntFunction;)Lenq$k;
        //    41: iload_2         /* valueMin */
        //    42: iload_3         /* valueMax */
        //    43: invokestatic    com/mojang/serialization/Codec.intRange:(II)Lcom/mojang/serialization/Codec;
        //    46: iload           valueDef
        //    48: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    51: invokedynamic   BootstrapMethod #9, accept:()Ljava/util/function/Consumer;
        //    56: invokespecial   enq.<init>:(Ljava/lang/String;Lenq$l;Lenq$b;Lenq$n;Lcom/mojang/serialization/Codec;Ljava/lang/Object;Ljava/util/function/Consumer;)V
        //    59: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 5447 out of bounds for byte[4691]
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
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
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
    
    public double getOptionValue() {
        final enr gameSettings = enn.N().m;
        return gameSettings.getOptionFloatValueOF((enq)this);
    }
    
    public void setOptionValue(final double value) {
        final enr gameSettings = enn.N().m;
        gameSettings.setOptionFloatValueOF((enq)this, value);
    }
    
    public sw getOptionText() {
        final enr gameSetings = enn.N().m;
        return gameSetings.getKeyComponentOF((enq)this);
    }
}
