// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.client.extensions;

public interface IForgeVertexConsumer
{
    default void putBulkData(final eij.a matrixStack, final fkr bakedQuad, final float red, final float green, final float blue, final int lightmapCoord, final int overlayColor, final boolean readExistingColor) {
    }
    
    default void putBulkData(final eij.a pose, final fkr bakedQuad, final float red, final float green, final float blue, final float alpha, final int packedLight, final int packedOverlay, final boolean readExistingColor) {
    }
}
