// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.optifine.Config;
import java.lang.management.ManagementFactory;
import java.lang.management.BufferPoolMXBean;
import java.util.function.LongSupplier;

public class NativeMemory
{
    private static long imageAllocated;
    private static LongSupplier bufferAllocatedSupplier;
    private static LongSupplier bufferMaximumSupplier;
    
    public static long getBufferAllocated() {
        if (NativeMemory.bufferAllocatedSupplier == null) {
            return -1L;
        }
        return NativeMemory.bufferAllocatedSupplier.getAsLong();
    }
    
    public static long getBufferMaximum() {
        if (NativeMemory.bufferMaximumSupplier == null) {
            return -1L;
        }
        return NativeMemory.bufferMaximumSupplier.getAsLong();
    }
    
    public static synchronized void imageAllocated(final ehk nativeImage) {
        NativeMemory.imageAllocated += nativeImage.getSize();
    }
    
    public static synchronized void imageFreed(final ehk nativeImage) {
        NativeMemory.imageAllocated -= nativeImage.getSize();
    }
    
    public static long getImageAllocated() {
        return NativeMemory.imageAllocated;
    }
    
    private static BufferPoolMXBean getDirectBufferPoolMXBean() {
        final List<BufferPoolMXBean> mxBeans = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);
        for (final BufferPoolMXBean mxBean : mxBeans) {
            if (Config.equals(mxBean.getName(), "direct")) {
                return mxBean;
            }
        }
        return null;
    }
    
    private static LongSupplier makeDefaultAllocatedSupplier() {
        final BufferPoolMXBean mxBean = getDirectBufferPoolMXBean();
        if (mxBean == null) {
            return null;
        }
        final LongSupplier ls = new LongSupplier() {
            @Override
            public long getAsLong() {
                return mxBean.getMemoryUsed();
            }
        };
        return ls;
    }
    
    private static LongSupplier makeDefaultMaximumSupplier() {
        final LongSupplier ls = new LongSupplier() {
            @Override
            public long getAsLong() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     3: lreturn        
                //     4: nop            
                //     5: nop            
                //     6: nop            
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
                //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:776)
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
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
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
        };
        return ls;
    }
    
    private static LongSupplier makeLongSupplier(final String[][] paths, final LongSupplier defaultSupplier) {
        final List<Throwable> exceptions = new ArrayList<Throwable>();
        for (int i = 0; i < paths.length; ++i) {
            final String[] path = paths[i];
            try {
                final LongSupplier supplier = makeLongSupplier(path);
                if (supplier != null) {
                    return supplier;
                }
            }
            catch (Throwable e) {
                exceptions.add(e);
            }
        }
        for (final Throwable t : exceptions) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, t.getClass().getName(), t.getMessage()));
        }
        return defaultSupplier;
    }
    
    private static LongSupplier makeLongSupplier(final String[] path) throws Exception {
        if (path.length < 2) {
            return null;
        }
        final Class cls = Class.forName(path[0]);
        Method method = cls.getMethod(path[1], (Class[])new Class[0]);
        method.setAccessible(true);
        Object object = null;
        for (int i = 2; i < path.length; ++i) {
            final String name = path[i];
            object = method.invoke(object, new Object[0]);
            method = object.getClass().getMethod(name, (Class<?>[])new Class[0]);
            method.setAccessible(true);
        }
        final Object objectF = object;
        final Method methodF = method;
        final LongSupplier ls = new LongSupplier(methodF) {
            private boolean disabled = false;
            
            @Override
            public long getAsLong() {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/optifine/util/NativeMemory$3.getAsLong:()J'.
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
                //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformCall(AstMethodBodyBuilder.java:1164)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:1009)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformByteCode(AstMethodBodyBuilder.java:554)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformExpression(AstMethodBodyBuilder.java:540)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformNode(AstMethodBodyBuilder.java:392)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.transformBlock(AstMethodBodyBuilder.java:333)
                //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:294)
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
                // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 11.
                //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
                //     at com.strobel.assembler.metadata.ExceptionHandlerMapper.createHandlerPlaceholders(ExceptionHandlerMapper.java:644)
                //     at com.strobel.assembler.metadata.ExceptionHandlerMapper.<init>(ExceptionHandlerMapper.java:246)
                //     at com.strobel.assembler.metadata.ExceptionHandlerMapper.run(ExceptionHandlerMapper.java:50)
                //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:557)
                //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
                //     ... 31 more
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        };
        return ls;
    }
    
    static {
        NativeMemory.imageAllocated = 0L;
        NativeMemory.bufferAllocatedSupplier = makeLongSupplier(new String[][] { { "sun.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" }, { "jdk.internal.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" } }, makeDefaultAllocatedSupplier());
        NativeMemory.bufferMaximumSupplier = makeLongSupplier(new String[][] { { "sun.misc.VM", "maxDirectMemory" }, { "jdk.internal.misc.VM", "maxDirectMemory" } }, makeDefaultMaximumSupplier());
    }
}
