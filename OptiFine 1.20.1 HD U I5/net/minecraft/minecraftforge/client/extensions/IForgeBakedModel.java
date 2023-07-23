// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.client.extensions;

import net.minecraftforge.client.ChunkRenderTypeSet;
import java.util.List;
import net.minecraftforge.client.model.data.ModelData;

public interface IForgeBakedModel
{
    default fwr getBakedModel() {
        return (fwr)this;
    }
    
    default List<fkr> getQuads(final dcb state, final ha side, final apf rand, final ModelData extraData) {
        return (List<fkr>)this.getBakedModel().a(state, side, rand);
    }
    
    default List<fkr> getQuads(final dcb state, final ha side, final apf rand, final ModelData data, final fkf renderType) {
        return (List<fkr>)this.getBakedModel().a(state, side, rand);
    }
    
    default boolean isAmbientOcclusion(final dcb state) {
        return this.getBakedModel().a();
    }
    
    default boolean useAmbientOcclusion(final dcb state) {
        return this.getBakedModel().a();
    }
    
    default boolean useAmbientOcclusion(final dcb state, final fkf renderType) {
        return this.isAmbientOcclusion(state);
    }
    
    default ModelData getModelData(final clp world, final gu pos, final dcb state, final ModelData tileData) {
        return tileData;
    }
    
    default fuv getParticleTexture(final ModelData data) {
        return this.getBakedModel().e();
    }
    
    default fuv getParticleIcon(final ModelData data) {
        return this.self().e();
    }
    
    default List<fwr> getRenderPasses(final cfz itemStack, final boolean fabulous) {
        return List.of(this.self());
    }
    
    default List<fkf> getRenderTypes(final cfz itemStack, final boolean fabulous) {
        return List.of();
    }
    
    default fwr self() {
        return (fwr)this;
    }
    
    default ChunkRenderTypeSet getRenderTypes(final dcb state, final apf rand, final ModelData data) {
        return null;
    }
    
    default fwr applyTransform(final cfw transformType, final eij poseStack, final boolean applyLeftHandTransform) {
        this.self().f().a(transformType).a(applyLeftHandTransform, poseStack);
        return this.self();
    }
}
