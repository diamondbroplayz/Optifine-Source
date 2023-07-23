// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import com.mojang.blaze3d.platform.TextureUtil;

public class CustomTexture implements ICustomTexture
{
    private int textureUnit;
    private String path;
    private fug texture;
    
    public CustomTexture(final int textureUnit, final String path, final fug texture) {
        this.textureUnit = -1;
        this.path = null;
        this.texture = null;
        this.textureUnit = textureUnit;
        this.path = path;
        this.texture = texture;
    }
    
    @Override
    public int getTextureUnit() {
        return this.textureUnit;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public fug getTexture() {
        return this.texture;
    }
    
    @Override
    public int getTextureId() {
        return this.texture.a();
    }
    
    @Override
    public void deleteTexture() {
        TextureUtil.releaseTextureId(this.texture.a());
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(ILjava/lang/String;I)Ljava/lang/String;, this.textureUnit, this.path, this.getTextureId());
    }
}
