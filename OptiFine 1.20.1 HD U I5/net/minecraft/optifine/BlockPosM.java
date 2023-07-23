// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Iterator;
import net.minecraft.core.BlockPos;

public class BlockPosM extends gu
{
    private int mx;
    private int my;
    private int mz;
    private int level;
    private BlockPosM[] facings;
    private boolean needsUpdate;
    
    public BlockPosM() {
        this(0, 0, 0, 0);
    }
    
    public BlockPosM(final int x, final int y, final int z) {
        this(x, y, z, 0);
    }
    
    public BlockPosM(final double xIn, final double yIn, final double zIn) {
        this(apa.a(xIn), apa.a(yIn), apa.a(zIn));
    }
    
    public BlockPosM(final int x, final int y, final int z, final int level) {
        super(0, 0, 0);
        this.mx = x;
        this.my = y;
        this.mz = z;
        this.level = level;
    }
    
    public int u() {
        return this.mx;
    }
    
    public int v() {
        return this.my;
    }
    
    public int w() {
        return this.mz;
    }
    
    public void setXyz(final int x, final int y, final int z) {
        this.mx = x;
        this.my = y;
        this.mz = z;
        this.needsUpdate = true;
    }
    
    public BlockPosM setXyz(final double xIn, final double yIn, final double zIn) {
        this.setXyz(apa.a(xIn), apa.a(yIn), apa.a(zIn));
        return this;
    }
    
    public gu a(final ha facing) {
        if (this.level <= 0) {
            return super.a(facing, 1).i();
        }
        if (this.facings == null) {
            this.facings = new BlockPosM[ha.p.length];
        }
        if (this.needsUpdate) {
            this.update();
        }
        final int index = facing.d();
        BlockPosM bpm = this.facings[index];
        if (bpm == null) {
            final int nx = this.mx + facing.j();
            final int ny = this.my + facing.k();
            final int nz = this.mz + facing.l();
            bpm = new BlockPosM(nx, ny, nz, this.level - 1);
            this.facings[index] = bpm;
        }
        return (gu)bpm;
    }
    
    public gu a(final ha facing, final int n) {
        if (n == 1) {
            return this.a(facing);
        }
        return super.a(facing, n).i();
    }
    
    public void setPosOffset(final gu pos, final ha facing) {
        this.mx = pos.u() + facing.j();
        this.my = pos.v() + facing.k();
        this.mz = pos.w() + facing.l();
    }
    
    public gu setPosOffset(final gu pos, final ha facing, final ha facing2) {
        this.mx = pos.u() + facing.j() + facing2.j();
        this.my = pos.v() + facing.k() + facing2.k();
        this.mz = pos.w() + facing.l() + facing2.l();
        return this;
    }
    
    private void update() {
        for (int i = 0; i < 6; ++i) {
            final BlockPosM bpm = this.facings[i];
            if (bpm != null) {
                final ha facing = ha.p[i];
                final int nx = this.mx + facing.j();
                final int ny = this.my + facing.k();
                final int nz = this.mz + facing.l();
                bpm.setXyz(nx, ny, nz);
            }
        }
        this.needsUpdate = false;
    }
    
    public gu i() {
        return new gu(this.mx, this.my, this.mz);
    }
    
    public static Iterable getAllInBoxMutable(final gu from, final gu to) {
        final gu posFrom = new gu(Math.min(from.u(), to.u()), Math.min(from.v(), to.v()), Math.min(from.w(), to.w()));
        final gu posTo = new gu(Math.max(from.u(), to.u()), Math.max(from.v(), to.v()), Math.max(from.w(), to.w()));
        return new Iterable(posFrom, posTo) {
            @Override
            public Iterator iterator() {
                // 
                // This method could not be decompiled.
                // 
                // Could not show original bytecode, likely due to the same error.
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 810 out of bounds for byte[760]
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
    }
}
