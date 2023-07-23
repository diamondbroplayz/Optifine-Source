// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

public class ComputeProgram
{
    private final String name;
    private final ProgramStage programStage;
    private int id;
    private int ref;
    private hz localSize;
    private hz workGroups;
    private eeh workGroupsRender;
    private int compositeMipmapSetting;
    
    public ComputeProgram(final String name, final ProgramStage programStage) {
        this.name = name;
        this.programStage = programStage;
    }
    
    public void resetProperties() {
    }
    
    public void resetId() {
        this.id = 0;
        this.ref = 0;
    }
    
    public void resetConfiguration() {
        this.localSize = null;
        this.workGroups = null;
        this.workGroupsRender = null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ProgramStage getProgramStage() {
        return this.programStage;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public int getRef() {
        return this.ref;
    }
    
    public void setRef(final int ref) {
        this.ref = ref;
    }
    
    public hz getLocalSize() {
        return this.localSize;
    }
    
    public void setLocalSize(final hz localSize) {
        this.localSize = localSize;
    }
    
    public hz getWorkGroups() {
        return this.workGroups;
    }
    
    public void setWorkGroups(final hz workGroups) {
        this.workGroups = workGroups;
    }
    
    public eeh getWorkGroupsRender() {
        return this.workGroupsRender;
    }
    
    public void setWorkGroupsRender(final eeh workGroupsRender) {
        this.workGroupsRender = workGroupsRender;
    }
    
    public int getCompositeMipmapSetting() {
        return this.compositeMipmapSetting;
    }
    
    public void setCompositeMipmapSetting(final int compositeMipmapSetting) {
        this.compositeMipmapSetting = compositeMipmapSetting;
    }
    
    public boolean hasCompositeMipmaps() {
        return this.compositeMipmapSetting != 0;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, this.name, this.id);
    }
}
