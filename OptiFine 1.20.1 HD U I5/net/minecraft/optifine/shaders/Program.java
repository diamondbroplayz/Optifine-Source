// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.Arrays;
import net.optifine.util.DynamicDimension;
import net.optifine.shaders.config.RenderScale;
import net.optifine.render.GlBlendState;
import net.optifine.render.GlAlphaState;

public class Program
{
    private final int index;
    private final String name;
    private final ProgramStage programStage;
    private final Program programBackup;
    private ComputeProgram[] computePrograms;
    private GlAlphaState alphaState;
    private GlBlendState blendState;
    private GlBlendState[] blendStatesColorIndexed;
    private RenderScale renderScale;
    private final Boolean[] buffersFlip;
    private int id;
    private int ref;
    private String[] drawBufSettings;
    private DrawBuffers drawBuffers;
    private DrawBuffers drawBuffersCustom;
    private int compositeMipmapSetting;
    private int countInstances;
    private final boolean[] toggleColorTextures;
    private DynamicDimension drawSize;
    private GlBlendState[] blendStatesIndexed;
    
    public Program(final int index, final String name, final ProgramStage programStage, final Program programBackup) {
        this.buffersFlip = new Boolean[16];
        this.toggleColorTextures = new boolean[16];
        this.index = index;
        this.name = name;
        this.programStage = programStage;
        this.programBackup = programBackup;
        this.computePrograms = new ComputeProgram[0];
    }
    
    public Program(final int index, final String name, final ProgramStage programStage, final boolean ownBackup) {
        this.buffersFlip = new Boolean[16];
        this.toggleColorTextures = new boolean[16];
        this.index = index;
        this.name = name;
        this.programStage = programStage;
        this.programBackup = (ownBackup ? this : null);
        this.computePrograms = new ComputeProgram[0];
    }
    
    public void resetProperties() {
        this.alphaState = null;
        this.blendState = null;
        this.blendStatesColorIndexed = null;
        this.renderScale = null;
        Arrays.fill(this.buffersFlip, null);
    }
    
    public void resetId() {
        this.id = 0;
        this.ref = 0;
    }
    
    public void resetConfiguration() {
        this.drawBufSettings = null;
        this.compositeMipmapSetting = 0;
        this.countInstances = 0;
        Arrays.fill(this.toggleColorTextures, false);
        this.drawSize = null;
        this.blendStatesIndexed = null;
        if (this.drawBuffersCustom == null) {
            this.drawBuffersCustom = new DrawBuffers(this.name, 16, 8);
        }
    }
    
    public void copyFrom(final Program p) {
        this.id = p.getId();
        this.alphaState = p.getAlphaState();
        this.blendState = p.getBlendState();
        this.blendStatesColorIndexed = p.blendStatesColorIndexed;
        this.renderScale = p.getRenderScale();
        System.arraycopy(p.getBuffersFlip(), 0, this.buffersFlip, 0, this.buffersFlip.length);
        this.drawBufSettings = p.getDrawBufSettings();
        this.drawBuffers = p.getDrawBuffers();
        this.compositeMipmapSetting = p.getCompositeMipmapSetting();
        this.countInstances = p.getCountInstances();
        System.arraycopy(p.getToggleColorTextures(), 0, this.toggleColorTextures, 0, this.toggleColorTextures.length);
        this.blendStatesIndexed = p.blendStatesIndexed;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ProgramStage getProgramStage() {
        return this.programStage;
    }
    
    public Program getProgramBackup() {
        return this.programBackup;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getRef() {
        return this.ref;
    }
    
    public String[] getDrawBufSettings() {
        return this.drawBufSettings;
    }
    
    public DrawBuffers getDrawBuffers() {
        return this.drawBuffers;
    }
    
    public DrawBuffers getDrawBuffersCustom() {
        return this.drawBuffersCustom;
    }
    
    public int getCompositeMipmapSetting() {
        return this.compositeMipmapSetting;
    }
    
    public int getCountInstances() {
        return this.countInstances;
    }
    
    public GlAlphaState getAlphaState() {
        return this.alphaState;
    }
    
    public GlBlendState getBlendState() {
        return this.blendState;
    }
    
    public GlBlendState[] getBlendStatesColorIndexed() {
        return this.blendStatesColorIndexed;
    }
    
    public GlBlendState[] getBlendStatesIndexed() {
        return this.blendStatesIndexed;
    }
    
    public RenderScale getRenderScale() {
        return this.renderScale;
    }
    
    public Boolean[] getBuffersFlip() {
        return this.buffersFlip;
    }
    
    public boolean[] getToggleColorTextures() {
        return this.toggleColorTextures;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public void setRef(final int ref) {
        this.ref = ref;
    }
    
    public void setDrawBufSettings(final String[] drawBufSettings) {
        this.drawBufSettings = drawBufSettings;
    }
    
    public void setDrawBuffers(final DrawBuffers drawBuffers) {
        this.drawBuffers = drawBuffers;
    }
    
    public void setCompositeMipmapSetting(final int compositeMipmapSetting) {
        this.compositeMipmapSetting = compositeMipmapSetting;
    }
    
    public void setCountInstances(final int countInstances) {
        this.countInstances = countInstances;
    }
    
    public void setAlphaState(final GlAlphaState alphaState) {
        this.alphaState = alphaState;
    }
    
    public void setBlendState(final GlBlendState blendState) {
        this.blendState = blendState;
    }
    
    public void setBlendStateColorIndexed(final int index, final GlBlendState blendState) {
        if (this.blendStatesColorIndexed == null) {
            this.blendStatesColorIndexed = new GlBlendState[index + 1];
        }
        if (this.blendStatesColorIndexed.length < index + 1) {
            final GlBlendState[] bss = new GlBlendState[index + 1];
            System.arraycopy(this.blendStatesColorIndexed, 0, bss, 0, this.blendStatesColorIndexed.length);
            this.blendStatesColorIndexed = bss;
        }
        this.blendStatesColorIndexed[index] = blendState;
    }
    
    public void setBlendStateIndexed(final int index, final GlBlendState blendState) {
        if (this.blendStatesIndexed == null) {
            this.blendStatesIndexed = new GlBlendState[index + 1];
        }
        if (this.blendStatesIndexed.length < index + 1) {
            final GlBlendState[] bss = new GlBlendState[index + 1];
            System.arraycopy(this.blendStatesIndexed, 0, bss, 0, this.blendStatesIndexed.length);
            this.blendStatesIndexed = bss;
        }
        this.blendStatesIndexed[index] = blendState;
    }
    
    public void setRenderScale(final RenderScale renderScale) {
        this.renderScale = renderScale;
    }
    
    public String getRealProgramName() {
        if (this.id == 0) {
            return "none";
        }
        Program p = this;
        while (p.getRef() != this.id) {
            if (p.getProgramBackup() != null) {
                if (p.getProgramBackup() != p) {
                    p = p.getProgramBackup();
                    continue;
                }
            }
            return "unknown";
        }
        return p.getName();
    }
    
    public boolean hasCompositeMipmaps() {
        return this.compositeMipmapSetting != 0;
    }
    
    public DynamicDimension getDrawSize() {
        return this.drawSize;
    }
    
    public void setDrawSize(final DynamicDimension drawSize) {
        this.drawSize = drawSize;
    }
    
    public ComputeProgram[] getComputePrograms() {
        return this.computePrograms;
    }
    
    public void setComputePrograms(final ComputeProgram[] computePrograms) {
        this.computePrograms = computePrograms;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;IILjava/lang/String;)Ljava/lang/String;, this.name, this.id, this.ref, this.getRealProgramName());
    }
}