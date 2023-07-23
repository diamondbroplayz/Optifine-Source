// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import org.joml.Vector4f;
import net.optifine.Config;
import net.optifine.util.StrUtils;

public class ShaderLine
{
    private Type type;
    private String name;
    private String value;
    private String line;
    
    public ShaderLine(final Type type, final String name, final String value, final String line) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.line = line;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public boolean isUniform() {
        return this.type == Type.UNIFORM;
    }
    
    public boolean isUniform(final String name) {
        return this.isUniform() && name.equals(this.name);
    }
    
    public boolean isAttribute() {
        return this.type == Type.ATTRIBUTE;
    }
    
    public boolean isAttribute(final String name) {
        return this.isAttribute() && name.equals(this.name);
    }
    
    public boolean isProperty() {
        return this.type == Type.PROPERTY;
    }
    
    public boolean isConstInt() {
        return this.type == Type.CONST_INT;
    }
    
    public boolean isConstFloat() {
        return this.type == Type.CONST_FLOAT;
    }
    
    public boolean isConstBool() {
        return this.type == Type.CONST_BOOL;
    }
    
    public boolean isExtension() {
        return this.type == Type.EXTENSION;
    }
    
    public boolean isConstVec2() {
        return this.type == Type.CONST_VEC2;
    }
    
    public boolean isConstVec4() {
        return this.type == Type.CONST_VEC4;
    }
    
    public boolean isConstIVec3() {
        return this.type == Type.CONST_IVEC3;
    }
    
    public boolean isLayout() {
        return this.type == Type.LAYOUT;
    }
    
    public boolean isLayout(final String name) {
        return this.isLayout() && name.equals(this.name);
    }
    
    public boolean isProperty(final String name) {
        return this.isProperty() && name.equals(this.name);
    }
    
    public boolean isProperty(final String name, final String value) {
        return this.isProperty(name) && value.equals(this.value);
    }
    
    public boolean isConstInt(final String name) {
        return this.isConstInt() && name.equals(this.name);
    }
    
    public boolean isConstIntSuffix(final String suffix) {
        return this.isConstInt() && this.name.endsWith(suffix);
    }
    
    public boolean isConstIVec3(final String name) {
        return this.isConstIVec3() && name.equals(this.name);
    }
    
    public boolean isConstFloat(final String name) {
        return this.isConstFloat() && name.equals(this.name);
    }
    
    public boolean isConstBool(final String name) {
        return this.isConstBool() && name.equals(this.name);
    }
    
    public boolean isExtension(final String name) {
        return this.isExtension() && name.equals(this.name);
    }
    
    public boolean isConstBoolSuffix(final String suffix) {
        return this.isConstBool() && this.name.endsWith(suffix);
    }
    
    public boolean isConstBoolSuffix(final String suffix, final boolean val) {
        return this.isConstBoolSuffix(suffix) && this.getValueBool() == val;
    }
    
    public boolean isConstBool(final String name1, final String name2) {
        return this.isConstBool(name1) || this.isConstBool(name2);
    }
    
    public boolean isConstBool(final String name1, final String name2, final String name3) {
        return this.isConstBool(name1) || this.isConstBool(name2) || this.isConstBool(name3);
    }
    
    public boolean isConstBool(final String name, final boolean val) {
        return this.isConstBool(name) && this.getValueBool() == val;
    }
    
    public boolean isConstBool(final String name1, final String name2, final boolean val) {
        return this.isConstBool(name1, name2) && this.getValueBool() == val;
    }
    
    public boolean isConstBool(final String name1, final String name2, final String name3, final boolean val) {
        return this.isConstBool(name1, name2, name3) && this.getValueBool() == val;
    }
    
    public boolean isConstVec2(final String name) {
        return this.isConstVec2() && name.equals(this.name);
    }
    
    public boolean isConstVec4Suffix(final String suffix) {
        return this.isConstVec4() && this.name.endsWith(suffix);
    }
    
    public int getValueInt() {
        try {
            return Integer.parseInt(this.value);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.value, this.line));
        }
    }
    
    public float getValueFloat() {
        try {
            return Float.parseFloat(this.value);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.value, this.line));
        }
    }
    
    public hz getValueIVec3() {
        if (this.value == null) {
            return null;
        }
        String str = this.value.trim();
        str = StrUtils.removePrefix(str, "ivec3");
        str = StrUtils.trim(str, " ()");
        final String[] parts = Config.tokenize(str, ", ");
        if (parts.length != 3) {
            return null;
        }
        final int[] vals = new int[3];
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            final int val = Config.parseInt(part, Integer.MIN_VALUE);
            if (val == Integer.MIN_VALUE) {
                return null;
            }
            vals[i] = val;
        }
        return new hz(vals[0], vals[1], vals[2]);
    }
    
    public eeh getValueVec2() {
        if (this.value == null) {
            return null;
        }
        String str = this.value.trim();
        str = StrUtils.removePrefix(str, "vec2");
        str = StrUtils.trim(str, " ()");
        final String[] parts = Config.tokenize(str, ", ");
        if (parts.length != 2) {
            return null;
        }
        final float[] fs = new float[2];
        for (int i = 0; i < parts.length; ++i) {
            String part = parts[i];
            part = StrUtils.removeSuffix(part, new String[] { "F", "f" });
            final float f = Config.parseFloat(part, Float.MAX_VALUE);
            if (f == Float.MAX_VALUE) {
                return null;
            }
            fs[i] = f;
        }
        return new eeh(fs[0], fs[1]);
    }
    
    public Vector4f getValueVec4() {
        if (this.value == null) {
            return null;
        }
        String str = this.value.trim();
        str = StrUtils.removePrefix(str, "vec4");
        str = StrUtils.trim(str, " ()");
        final String[] parts = Config.tokenize(str, ", ");
        if (parts.length != 4) {
            return null;
        }
        final float[] fs = new float[4];
        for (int i = 0; i < parts.length; ++i) {
            String part = parts[i];
            part = StrUtils.removeSuffix(part, new String[] { "F", "f" });
            final float f = Config.parseFloat(part, Float.MAX_VALUE);
            if (f == Float.MAX_VALUE) {
                return null;
            }
            fs[i] = f;
        }
        return new Vector4f(fs[0], fs[1], fs[2], fs[3]);
    }
    
    public boolean getValueBool() {
        final String valLow = this.value.toLowerCase();
        if (!valLow.equals("true") && !valLow.equals("false")) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.value, this.line));
        }
        return Boolean.valueOf(this.value);
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.line);
    }
    
    public enum Type
    {
        public static final Type UNIFORM;
        public static final Type ATTRIBUTE;
        public static final Type CONST_INT;
        public static final Type CONST_IVEC3;
        public static final Type CONST_FLOAT;
        public static final Type CONST_VEC2;
        public static final Type CONST_VEC4;
        public static final Type CONST_BOOL;
        public static final Type PROPERTY;
        public static final Type EXTENSION;
        public static final Type LAYOUT;
        
        private Type() {
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
            //     1: ldc             "UNIFORM"
            //     3: iconst_0       
            //     4: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //     7: putstatic       net/optifine/shaders/config/ShaderLine$Type.UNIFORM:Lnet/optifine/shaders/config/ShaderLine$Type;
            //    10: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //    13: dup            
            //    14: ldc             "ATTRIBUTE"
            //    16: iconst_1       
            //    17: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //    20: putstatic       net/optifine/shaders/config/ShaderLine$Type.ATTRIBUTE:Lnet/optifine/shaders/config/ShaderLine$Type;
            //    23: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //    26: dup            
            //    27: ldc             "CONST_INT"
            //    29: iconst_2       
            //    30: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //    33: putstatic       net/optifine/shaders/config/ShaderLine$Type.CONST_INT:Lnet/optifine/shaders/config/ShaderLine$Type;
            //    36: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //    39: dup            
            //    40: ldc             "CONST_IVEC3"
            //    42: iconst_3       
            //    43: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //    46: putstatic       net/optifine/shaders/config/ShaderLine$Type.CONST_IVEC3:Lnet/optifine/shaders/config/ShaderLine$Type;
            //    49: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //    52: dup            
            //    53: ldc             "CONST_FLOAT"
            //    55: iconst_4       
            //    56: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //    59: putstatic       net/optifine/shaders/config/ShaderLine$Type.CONST_FLOAT:Lnet/optifine/shaders/config/ShaderLine$Type;
            //    62: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //    65: dup            
            //    66: ldc             "CONST_VEC2"
            //    68: iconst_5       
            //    69: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //    72: putstatic       net/optifine/shaders/config/ShaderLine$Type.CONST_VEC2:Lnet/optifine/shaders/config/ShaderLine$Type;
            //    75: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //    78: dup            
            //    79: ldc             "CONST_VEC4"
            //    81: bipush          6
            //    83: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //    86: putstatic       net/optifine/shaders/config/ShaderLine$Type.CONST_VEC4:Lnet/optifine/shaders/config/ShaderLine$Type;
            //    89: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //    92: dup            
            //    93: ldc             "CONST_BOOL"
            //    95: bipush          7
            //    97: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //   100: putstatic       net/optifine/shaders/config/ShaderLine$Type.CONST_BOOL:Lnet/optifine/shaders/config/ShaderLine$Type;
            //   103: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //   106: dup            
            //   107: ldc             "PROPERTY"
            //   109: bipush          8
            //   111: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //   114: putstatic       net/optifine/shaders/config/ShaderLine$Type.PROPERTY:Lnet/optifine/shaders/config/ShaderLine$Type;
            //   117: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //   120: dup            
            //   121: ldc             "EXTENSION"
            //   123: bipush          9
            //   125: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //   128: putstatic       net/optifine/shaders/config/ShaderLine$Type.EXTENSION:Lnet/optifine/shaders/config/ShaderLine$Type;
            //   131: new             Lnet/optifine/shaders/config/ShaderLine$Type;
            //   134: dup            
            //   135: ldc             "LAYOUT"
            //   137: bipush          10
            //   139: invokespecial   net/optifine/shaders/config/ShaderLine$Type.<init>:(Ljava/lang/String;I)V
            //   142: putstatic       net/optifine/shaders/config/ShaderLine$Type.LAYOUT:Lnet/optifine/shaders/config/ShaderLine$Type;
            //   145: invokestatic    net/optifine/shaders/config/ShaderLine$Type.$values:()[Lnet/optifine/shaders/config/ShaderLine$Type;
            //   148: putstatic       net/optifine/shaders/config/ShaderLine$Type.$VALUES:[Lnet/optifine/shaders/config/ShaderLine$Type;
            //   151: return         
            //   152: nop            
            //   153: nop            
            //   154: nop            
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
