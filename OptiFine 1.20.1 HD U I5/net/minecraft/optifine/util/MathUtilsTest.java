// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class MathUtilsTest
{
    public static void main(final String[] args) throws Exception {
        final OPER[] values = OPER.values();
        for (int i = 0; i < values.length; ++i) {
            final OPER oper = values[i];
            dbg(invokedynamic(makeConcatWithConstants:(Lnet/optifine/util/MathUtilsTest$OPER;)Ljava/lang/String;, oper));
            test(oper, false);
        }
    }
    
    private static void test(final OPER oper, final boolean fast) {
        apa.fastMath = fast;
        double min = 0.0;
        double max = 0.0;
        switch (oper) {
            case SIN:
            case COS: {
                min = -3.1415927410125732;
                max = 3.1415927410125732;
                break;
            }
            case ASIN:
            case ACOS: {
                min = -1.0;
                max = 1.0;
                break;
            }
            default: {
                return;
            }
        }
        for (int count = 10, i = 0; i <= count; ++i) {
            final double val = min + i * (max - min) / count;
            float res1 = 0.0f;
            float res2 = 0.0f;
            switch (oper) {
                case SIN: {
                    res1 = (float)Math.sin(val);
                    res2 = apa.a((float)val);
                    break;
                }
                case COS: {
                    res1 = (float)Math.cos(val);
                    res2 = apa.b((float)val);
                    break;
                }
                case ASIN: {
                    res1 = (float)Math.asin(val);
                    res2 = MathUtils.asin((float)val);
                    break;
                }
                case ACOS: {
                    res1 = (float)Math.acos(val);
                    res2 = MathUtils.acos((float)val);
                    break;
                }
                default: {
                    return;
                }
            }
            dbg(String.format("%.2f, Math: %f, Helper: %f, diff: %f", val, res1, res2, Math.abs(res1 - res2)));
        }
    }
    
    public static void dbg(final String str) {
        System.out.println(str);
    }
    
    private enum OPER
    {
        public static final OPER SIN;
        public static final OPER COS;
        public static final OPER ASIN;
        public static final OPER ACOS;
        
        private OPER() {
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
            //     1: ldc             "SIN"
            //     3: iconst_0       
            //     4: invokespecial   net/optifine/util/MathUtilsTest$OPER.<init>:(Ljava/lang/String;I)V
            //     7: putstatic       net/optifine/util/MathUtilsTest$OPER.SIN:Lnet/optifine/util/MathUtilsTest$OPER;
            //    10: new             Lnet/optifine/util/MathUtilsTest$OPER;
            //    13: dup            
            //    14: ldc             "COS"
            //    16: iconst_1       
            //    17: invokespecial   net/optifine/util/MathUtilsTest$OPER.<init>:(Ljava/lang/String;I)V
            //    20: putstatic       net/optifine/util/MathUtilsTest$OPER.COS:Lnet/optifine/util/MathUtilsTest$OPER;
            //    23: new             Lnet/optifine/util/MathUtilsTest$OPER;
            //    26: dup            
            //    27: ldc             "ASIN"
            //    29: iconst_2       
            //    30: invokespecial   net/optifine/util/MathUtilsTest$OPER.<init>:(Ljava/lang/String;I)V
            //    33: putstatic       net/optifine/util/MathUtilsTest$OPER.ASIN:Lnet/optifine/util/MathUtilsTest$OPER;
            //    36: new             Lnet/optifine/util/MathUtilsTest$OPER;
            //    39: dup            
            //    40: ldc             "ACOS"
            //    42: iconst_3       
            //    43: invokespecial   net/optifine/util/MathUtilsTest$OPER.<init>:(Ljava/lang/String;I)V
            //    46: putstatic       net/optifine/util/MathUtilsTest$OPER.ACOS:Lnet/optifine/util/MathUtilsTest$OPER;
            //    49: invokestatic    net/optifine/util/MathUtilsTest$OPER.$values:()[Lnet/optifine/util/MathUtilsTest$OPER;
            //    52: putstatic       net/optifine/util/MathUtilsTest$OPER.$VALUES:[Lnet/optifine/util/MathUtilsTest$OPER;
            //    55: return         
            //    56: nop            
            //    57: nop            
            //    58: nop            
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
