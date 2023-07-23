// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.gui;

import net.optifine.Lang;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Shaders;
import java.util.ArrayList;
import net.optifine.gui.SlotGui;

class GuiSlotShaders extends SlotGui
{
    private ArrayList shaderslist;
    private int selectedIndex;
    private long lastClicked;
    private long lastClickedCached;
    final GuiShaders shadersGui;
    
    public GuiSlotShaders(final GuiShaders par1GuiShaders, final int width, final int height, final int top, final int bottom, final int slotHeight) {
        super(par1GuiShaders.getMc(), width, height, top, bottom, slotHeight);
        this.lastClicked = Long.MIN_VALUE;
        this.lastClickedCached = 0L;
        this.shadersGui = par1GuiShaders;
        this.updateList();
        this.yo = 0.0;
        final int posYSelected = this.selectedIndex * slotHeight;
        final int wMid = (bottom - top) / 2;
        if (posYSelected > wMid) {
            this.scroll(posYSelected - wMid);
        }
    }
    
    @Override
    public int getRowWidth() {
        return this.width - 20;
    }
    
    public void updateList() {
        this.shaderslist = Shaders.listOfShaders();
        this.selectedIndex = 0;
        for (int i = 0, n = this.shaderslist.size(); i < n; ++i) {
            if (this.shaderslist.get(i).equals(Shaders.currentShaderName)) {
                this.selectedIndex = i;
                break;
            }
        }
    }
    
    @Override
    protected int getItemCount() {
        return this.shaderslist.size();
    }
    
    @Override
    protected boolean selectItem(final int index, final int buttons, final double x, final double y) {
        if (index == this.selectedIndex && this.lastClicked == this.lastClickedCached) {
            return false;
        }
        final String name = this.shaderslist.get(index);
        final IShaderPack sp = Shaders.getShaderPack(name);
        if (!this.checkCompatible(sp, index)) {
            return false;
        }
        this.selectIndex(index);
        return true;
    }
    
    private void selectIndex(final int index) {
        this.selectedIndex = index;
        this.lastClickedCached = this.lastClicked;
        Shaders.setShaderPack(this.shaderslist.get(index));
        Shaders.uninit();
        this.shadersGui.updateButtons();
    }
    
    private boolean checkCompatible(final IShaderPack sp, final int index) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: iconst_1       
        //     5: ireturn        
        //     6: aload_1         /* sp */
        //     7: ldc             "/shaders/shaders.properties"
        //     9: invokeinterface net/optifine/shaders/IShaderPack.getResourceAsStream:(Ljava/lang/String;)Ljava/io/InputStream;
        //    14: astore_3        /* in */
        //    15: aload_3         /* in */
        //    16: ldc             "Shaders"
        //    18: invokestatic    net/optifine/util/ResUtils.readProperties:(Ljava/io/InputStream;Ljava/lang/String;)Ljava/util/Properties;
        //    21: astore          props
        //    23: aload           props
        //    25: ifnonnull       30
        //    28: iconst_1       
        //    29: ireturn        
        //    30: ldc             "version.1.20.1"
        //    32: astore          keyVer
        //    34: aload           props
        //    36: aload           keyVer
        //    38: invokevirtual   java/util/Properties.getProperty:(Ljava/lang/String;)Ljava/lang/String;
        //    41: astore          relMin
        //    43: aload           relMin
        //    45: ifnonnull       50
        //    48: iconst_1       
        //    49: ireturn        
        //    50: aload           relMin
        //    52: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //    55: astore          relMin
        //    57: ldc             "I5"
        //    59: astore          rel
        //    61: aload           rel
        //    63: aload           relMin
        //    65: invokestatic    net/optifine/Config.compareRelease:(Ljava/lang/String;Ljava/lang/String;)I
        //    68: istore          res
        //    70: iload           res
        //    72: iflt            77
        //    75: iconst_1       
        //    76: ireturn        
        //    77: aload           relMin
        //    79: invokedynamic   BootstrapMethod #0, makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;
        //    84: bipush          95
        //    86: bipush          32
        //    88: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
        //    91: astore          verMin
        //    93: ldc             "of.message.shaders.nv1"
        //    95: iconst_1       
        //    96: anewarray       Ljava/lang/Object;
        //    99: dup            
        //   100: iconst_0       
        //   101: aload           verMin
        //   103: aastore        
        //   104: invokestatic    fvz.a:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   107: astore          msg1
        //   109: ldc             "of.message.shaders.nv2"
        //   111: iconst_0       
        //   112: anewarray       Ljava/lang/Object;
        //   115: invokestatic    fvz.a:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   118: astore          msg2
        //   120: iload_2         /* index */
        //   121: istore          theIndex
        //   123: aload_0         /* this */
        //   124: iload           theIndex
        //   126: invokedynamic   BootstrapMethod #1, accept:(Lnet/optifine/shaders/gui/GuiSlotShaders;I)Lit/unimi/dsi/fastutil/booleans/BooleanConsumer;
        //   131: astore          callback
        //   133: new             Letk;
        //   136: dup            
        //   137: aload           callback
        //   139: aload           msg1
        //   141: invokestatic    sw.b:(Ljava/lang/String;)Ltj;
        //   144: aload           msg2
        //   146: invokestatic    sw.b:(Ljava/lang/String;)Ltj;
        //   149: invokespecial   etk.<init>:(Lit/unimi/dsi/fastutil/booleans/BooleanConsumer;Lsw;Lsw;)V
        //   152: astore          guiYesNo
        //   154: aload_0         /* this */
        //   155: getfield        net/optifine/shaders/gui/GuiSlotShaders.minecraft:Lenn;
        //   158: aload           guiYesNo
        //   160: invokevirtual   enn.a:(Leuq;)V
        //   163: iconst_0       
        //   164: ireturn        
        //    StackMapTable: 00 04 06 FD 00 17 07 00 90 07 00 92 FD 00 13 07 00 55 07 00 55 FD 00 1A 07 00 55 01
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: arraycopy: last source index 6707 out of bounds for byte[6279]
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
    
    @Override
    protected boolean isSelectedItem(final int index) {
        return index == this.selectedIndex;
    }
    
    @Override
    protected int getScrollbarPosition() {
        return this.width - 6;
    }
    
    @Override
    public int getItemHeight() {
        return this.getItemCount() * 18;
    }
    
    @Override
    protected void renderBackground() {
    }
    
    @Override
    protected void renderItem(final eox graphicsIn, final int index, final int posX, final int posY, final int contentY, final int mouseX, final int mouseY, final float partialTicks) {
        String label = this.shaderslist.get(index);
        if (label.equals("OFF")) {
            label = Lang.get("of.options.shaders.packNone");
        }
        else if (label.equals("(internal)")) {
            label = Lang.get("of.options.shaders.packDefault");
        }
        this.shadersGui.drawCenteredString(graphicsIn, label, this.width / 2, posY + 1, 14737632);
    }
    
    public int getSelectedIndex() {
        return this.selectedIndex;
    }
    
    @Override
    public boolean a(final double x, final double y, final double amount) {
        return super.a(x, y, amount * 3.0);
    }
}
