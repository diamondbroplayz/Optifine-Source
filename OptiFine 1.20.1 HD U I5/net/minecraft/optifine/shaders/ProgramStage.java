// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

public enum ProgramStage
{
    NONE(""), 
    SHADOW("shadow"), 
    SHADOWCOMP("shadowcomp"), 
    PREPARE("prepare"), 
    GBUFFERS("gbuffers"), 
    DEFERRED("deferred"), 
    COMPOSITE("composite");
    
    private String name;
    
    private ProgramStage(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isAnyComposite() {
        return this == ProgramStage.SHADOWCOMP || this == ProgramStage.PREPARE || this == ProgramStage.DEFERRED || this == ProgramStage.COMPOSITE;
    }
    
    public boolean isMainComposite() {
        return this == ProgramStage.PREPARE || this == ProgramStage.DEFERRED || this == ProgramStage.COMPOSITE;
    }
    
    public boolean isAnyShadow() {
        return this == ProgramStage.SHADOW || this == ProgramStage.SHADOWCOMP;
    }
    
    private static /* synthetic */ ProgramStage[] $values() {
        return new ProgramStage[] { ProgramStage.NONE, ProgramStage.SHADOW, ProgramStage.SHADOWCOMP, ProgramStage.PREPARE, ProgramStage.GBUFFERS, ProgramStage.DEFERRED, ProgramStage.COMPOSITE };
    }
    
    static {
        $VALUES = $values();
    }
}
