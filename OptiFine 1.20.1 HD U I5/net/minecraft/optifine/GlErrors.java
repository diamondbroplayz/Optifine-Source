// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Iterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class GlErrors
{
    private static boolean frameStarted;
    private static long timeCheckStartMs;
    private static Int2ObjectOpenHashMap<GlError> glErrors;
    private static final long CHECK_INTERVAL_MS = 3000L;
    private static final int CHECK_ERROR_MAX = 10;
    
    public static void frameStart() {
        GlErrors.frameStarted = true;
        if (GlErrors.glErrors.isEmpty()) {
            return;
        }
        if (GlErrors.timeCheckStartMs < 0L) {
            GlErrors.timeCheckStartMs = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() > GlErrors.timeCheckStartMs + 3000L) {
            final ObjectCollection<GlError> errors = (ObjectCollection<GlError>)GlErrors.glErrors.values();
            for (final GlError glError : errors) {
                glError.onFrameStart();
            }
            GlErrors.timeCheckStartMs = System.currentTimeMillis();
        }
    }
    
    public static boolean isEnabled(final int error) {
        if (!GlErrors.frameStarted) {
            return true;
        }
        final GlError glError = getGlError(error);
        return glError.isEnabled();
    }
    
    private static GlError getGlError(final int error) {
        GlError glError = (GlError)GlErrors.glErrors.get(error);
        if (glError == null) {
            glError = new GlError(error);
            GlErrors.glErrors.put(error, (Object)glError);
        }
        return glError;
    }
    
    static {
        GlErrors.frameStarted = false;
        GlErrors.timeCheckStartMs = -1L;
        GlErrors.glErrors = (Int2ObjectOpenHashMap<GlError>)new Int2ObjectOpenHashMap();
    }
    
    public static class GlError
    {
        private int id;
        private int countErrors;
        private int countErrorsSuppressed;
        private boolean suppressed;
        private boolean oneErrorEnabled;
        
        public GlError(final int id) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     2: iconst_0       
            //     3: putfield        net/optifine/GlErrors$GlError.countErrors:I
            //     6: aload_0         /* this */
            //     7: iconst_0       
            //     8: putfield        net/optifine/GlErrors$GlError.countErrorsSuppressed:I
            //    11: aload_0         /* this */
            //    12: iconst_0       
            //    13: putfield        net/optifine/GlErrors$GlError.suppressed:Z
            //    16: aload_0         /* this */
            //    17: iconst_0       
            //    18: putfield        net/optifine/GlErrors$GlError.oneErrorEnabled:Z
            //    21: aload_0         /* this */
            //    22: iload_1         /* id */
            //    23: putfield        net/optifine/GlErrors$GlError.id:I
            //    26: return         
            //    27: nop            
            //    28: nop            
            //    29: nop            
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException: Index 42 out of bounds for length 2
            //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2708)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
        
        public void onFrameStart() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: ifle            20
            //     4: aload_0         /* this */
            //     5: getfield        net/optifine/GlErrors$GlError.countErrors:I
            //     8: aload_0         /* this */
            //     9: getfield        net/optifine/GlErrors$GlError.id:I
            //    12: invokedynamic   BootstrapMethod #0, makeConcatWithConstants:(II)Ljava/lang/String;
            //    17: invokestatic    net/optifine/Config.error:(Ljava/lang/String;)V
            //    20: aload_0         /* this */
            //    21: aload_0         /* this */
            //    22: getfield        net/optifine/GlErrors$GlError.countErrors:I
            //    25: bipush          10
            //    27: if_icmple       34
            //    30: iconst_1       
            //    31: goto            35
            //    34: iconst_0       
            //    35: putfield        net/optifine/GlErrors$GlError.suppressed:Z
            //    38: aload_0         /* this */
            //    39: iconst_0       
            //    40: putfield        net/optifine/GlErrors$GlError.countErrors:I
            //    43: aload_0         /* this */
            //    44: iconst_0       
            //    45: putfield        net/optifine/GlErrors$GlError.countErrorsSuppressed:I
            //    48: aload_0         /* this */
            //    49: iconst_1       
            //    50: putfield        net/optifine/GlErrors$GlError.oneErrorEnabled:Z
            //    53: return         
            //    54: nop            
            //    55: nop            
            //    56: nop            
            //    StackMapTable: 00 03 17 4D 07 00 02 FF 00 00 00 01 07 00 02 00 02 07 00 02 01
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 1
            //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2708)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
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
        
        public boolean isEnabled() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: iload_1        
            //     2: iconst_1       
            //     3: iadd           
            //     4: putfield        net/optifine/GlErrors$GlError.countErrors:I
            //     7: aload_0         /* this */
            //     8: getfield        net/optifine/GlErrors$GlError.oneErrorEnabled:Z
            //    11: ifeq            21
            //    14: aload_0         /* this */
            //    15: iconst_0       
            //    16: putfield        net/optifine/GlErrors$GlError.oneErrorEnabled:Z
            //    19: iconst_1       
            //    20: ireturn        
            //    21: aload_0         /* this */
            //    22: getfield        net/optifine/GlErrors$GlError.suppressed:Z
            //    25: ifeq            38
            //    28: aload_0         /* this */
            //    29: dup            
            //    30: getfield        net/optifine/GlErrors$GlError.countErrorsSuppressed:I
            //    33: iconst_1       
            //    34: iadd           
            //    35: putfield        net/optifine/GlErrors$GlError.countErrorsSuppressed:I
            //    38: aload_0         /* this */
            //    39: getfield        net/optifine/GlErrors$GlError.suppressed:Z
            //    42: ifne            49
            //    45: iconst_1       
            //    46: goto            50
            //    49: iconst_0       
            //    50: ireturn        
            //    51: nop            
            //    52: nop            
            //    53: nop            
            //    StackMapTable: 00 04 18 10 0A 40 01
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
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:560)
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
