// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.texture;

public enum InternalFormat
{
    R8(33321), 
    RG8(33323), 
    RGB8(32849), 
    RGBA8(32856), 
    R8_SNORM(36756), 
    RG8_SNORM(36757), 
    RGB8_SNORM(36758), 
    RGBA8_SNORM(36759), 
    R16(33322), 
    RG16(33324), 
    RGB16(32852), 
    RGBA16(32859), 
    R16_SNORM(36760), 
    RG16_SNORM(36761), 
    RGB16_SNORM(36762), 
    RGBA16_SNORM(36763), 
    R16F(33325), 
    RG16F(33327), 
    RGB16F(34843), 
    RGBA16F(34842), 
    R32F(33326), 
    RG32F(33328), 
    RGB32F(34837), 
    RGBA32F(34836), 
    R32I(33333), 
    RG32I(33339), 
    RGB32I(36227), 
    RGBA32I(36226), 
    R32UI(33334), 
    RG32UI(33340), 
    RGB32UI(36209), 
    RGBA32UI(36208), 
    R3_G3_B2(10768), 
    RGB5_A1(32855), 
    RGB10_A2(32857), 
    R11F_G11F_B10F(35898), 
    RGB9_E5(35901);
    
    private int id;
    
    private InternalFormat(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    private static /* synthetic */ InternalFormat[] $values() {
        return new InternalFormat[] { InternalFormat.R8, InternalFormat.RG8, InternalFormat.RGB8, InternalFormat.RGBA8, InternalFormat.R8_SNORM, InternalFormat.RG8_SNORM, InternalFormat.RGB8_SNORM, InternalFormat.RGBA8_SNORM, InternalFormat.R16, InternalFormat.RG16, InternalFormat.RGB16, InternalFormat.RGBA16, InternalFormat.R16_SNORM, InternalFormat.RG16_SNORM, InternalFormat.RGB16_SNORM, InternalFormat.RGBA16_SNORM, InternalFormat.R16F, InternalFormat.RG16F, InternalFormat.RGB16F, InternalFormat.RGBA16F, InternalFormat.R32F, InternalFormat.RG32F, InternalFormat.RGB32F, InternalFormat.RGBA32F, InternalFormat.R32I, InternalFormat.RG32I, InternalFormat.RGB32I, InternalFormat.RGBA32I, InternalFormat.R32UI, InternalFormat.RG32UI, InternalFormat.RGB32UI, InternalFormat.RGBA32UI, InternalFormat.R3_G3_B2, InternalFormat.RGB5_A1, InternalFormat.RGB10_A2, InternalFormat.R11F_G11F_B10F, InternalFormat.RGB9_E5 };
    }
    
    static {
        $VALUES = $values();
    }
}
