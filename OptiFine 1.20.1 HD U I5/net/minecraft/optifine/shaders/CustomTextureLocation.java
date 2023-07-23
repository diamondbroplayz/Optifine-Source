// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

public class CustomTextureLocation implements ICustomTexture
{
    private int textureUnit;
    private acq location;
    private int variant;
    private fug texture;
    public static final int VARIANT_BASE = 0;
    public static final int VARIANT_NORMAL = 1;
    public static final int VARIANT_SPECULAR = 2;
    
    public CustomTextureLocation(final int textureUnit, final acq location, final int variant) {
        this.textureUnit = -1;
        this.variant = 0;
        this.textureUnit = textureUnit;
        this.location = location;
        this.variant = variant;
    }
    
    public fug getTexture() {
        if (this.texture == null) {
            final fuw textureManager = enn.N().X();
            this.texture = textureManager.b(this.location);
            if (this.texture == null) {
                this.texture = (fug)new fuo(this.location);
                textureManager.a(this.location, this.texture);
                this.texture = textureManager.b(this.location);
            }
        }
        return this.texture;
    }
    
    public void reloadTexture() {
        this.texture = null;
    }
    
    @Override
    public int getTextureId() {
        final fug tex = this.getTexture();
        if (this.variant != 0 && tex instanceof fug) {
            final fug at = tex;
            final MultiTexID mtid = at.multiTex;
            if (mtid != null) {
                if (this.variant == 1) {
                    return mtid.norm;
                }
                if (this.variant == 2) {
                    return mtid.spec;
                }
            }
        }
        return tex.a();
    }
    
    @Override
    public int getTextureUnit() {
        return this.textureUnit;
    }
    
    @Override
    public void deleteTexture() {
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(ILacq;Ljava/io/Serializable;)Ljava/lang/String;, this.textureUnit, this.location, (this.texture != null) ? Integer.valueOf(this.texture.a()) : "");
    }
}
