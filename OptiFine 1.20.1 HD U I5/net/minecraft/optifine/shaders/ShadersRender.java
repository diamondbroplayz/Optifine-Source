// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import org.joml.Matrix3f;
import net.optifine.util.MathUtils;
import org.lwjgl.opengl.GL20;
import java.util.List;
import java.util.Iterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import org.joml.Vector4f;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import net.optifine.reflect.Reflector;
import java.util.Collections;
import net.optifine.render.RenderTypes;
import net.optifine.Lagometer;
import net.optifine.render.GlBlendState;
import net.optifine.render.GlCullState;
import org.lwjgl.opengl.GL30;
import com.mojang.blaze3d.systems.RenderSystem;
import net.optifine.Config;
import com.mojang.blaze3d.platform.GlStateManager;
import net.optifine.render.ICamera;

public class ShadersRender
{
    private static final acq END_PORTAL_TEXTURE;
    public static boolean frustumTerrainShadowChanged;
    public static boolean frustumEntitiesShadowChanged;
    public static int countEntitiesRenderedShadow;
    public static int countTileEntitiesRenderedShadow;
    
    public static void setFrustrumPosition(final ICamera frustum, final double x, final double y, final double z) {
        frustum.setCameraPosition(x, y, z);
    }
    
    public static void beginTerrainSolid() {
        if (Shaders.isRenderingWorld) {
            Shaders.fogEnabled = true;
            Shaders.useProgram(Shaders.ProgramTerrain);
            Shaders.setRenderStage(RenderStage.TERRAIN_SOLID);
        }
    }
    
    public static void beginTerrainCutoutMipped() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTerrain);
            Shaders.setRenderStage(RenderStage.TERRAIN_CUTOUT_MIPPED);
        }
    }
    
    public static void beginTerrainCutout() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTerrain);
            Shaders.setRenderStage(RenderStage.TERRAIN_CUTOUT);
        }
    }
    
    public static void endTerrain() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTexturedLit);
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }
    
    public static void beginTranslucent() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramWater);
            Shaders.setRenderStage(RenderStage.TERRAIN_TRANSLUCENT);
        }
    }
    
    public static void endTranslucent() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTexturedLit);
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }
    
    public static void beginTripwire() {
        if (Shaders.isRenderingWorld) {
            Shaders.setRenderStage(RenderStage.TRIPWIRE);
        }
    }
    
    public static void endTripwire() {
        if (Shaders.isRenderingWorld) {
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }
    
    public static void renderHand0(final fjq er, final eij matrixStackIn, final emz activeRenderInfo, final float partialTicks) {
        if (!Shaders.isShadowPass) {
            final boolean blockTranslucentMain = Shaders.isItemToRenderMainTranslucent();
            final boolean blockTranslucentOff = Shaders.isItemToRenderOffTranslucent();
            if (!blockTranslucentMain || !blockTranslucentOff) {
                Shaders.readCenterDepth();
                Shaders.beginHand(matrixStackIn, false);
                Shaders.setSkipRenderHands(blockTranslucentMain, blockTranslucentOff);
                er.renderHand(matrixStackIn, activeRenderInfo, partialTicks, true, false, false);
                Shaders.endHand(matrixStackIn);
                Shaders.setHandsRendered(!blockTranslucentMain, !blockTranslucentOff);
                Shaders.setSkipRenderHands(false, false);
            }
        }
    }
    
    public static void renderHand1(final fjq er, final eij matrixStackIn, final emz activeRenderInfo, final float partialTicks) {
        if (!Shaders.isShadowPass && !Shaders.isBothHandsRendered()) {
            Shaders.readCenterDepth();
            GlStateManager._enableBlend();
            Shaders.beginHand(matrixStackIn, true);
            Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
            er.renderHand(matrixStackIn, activeRenderInfo, partialTicks, true, false, true);
            Shaders.endHand(matrixStackIn);
            Shaders.setHandsRendered(true, true);
            Shaders.setSkipRenderHands(false, false);
        }
    }
    
    public static void renderItemFP(final fjt itemRenderer, final float partialTicks, final eij matrixStackIn, final fjx.a bufferIn, final fiy playerEntityIn, final int combinedLightIn, final boolean renderTranslucent) {
        Config.getEntityRenderDispatcher().setRenderedEntity((bfj)playerEntityIn);
        GlStateManager._depthMask(true);
        if (renderTranslucent) {
            GlStateManager._depthFunc(519);
            matrixStackIn.a();
            final DrawBuffers drawBuffers = GlState.getDrawBuffers();
            GlState.setDrawBuffers(Shaders.drawBuffersNone);
            Shaders.renderItemKeepDepthMask = true;
            itemRenderer.a(partialTicks, matrixStackIn, bufferIn, playerEntityIn, combinedLightIn);
            Shaders.renderItemKeepDepthMask = false;
            GlState.setDrawBuffers(drawBuffers);
            matrixStackIn.b();
        }
        GlStateManager._depthFunc(515);
        itemRenderer.a(partialTicks, matrixStackIn, bufferIn, playerEntityIn, combinedLightIn);
        Config.getEntityRenderDispatcher().setRenderedEntity((bfj)null);
    }
    
    public static void renderFPOverlay(final fjq er, final eij matrixStackIn, final emz activeRenderInfo, final float partialTicks) {
        if (!Shaders.isShadowPass) {
            Shaders.beginFPOverlay();
            er.renderHand(matrixStackIn, activeRenderInfo, partialTicks, false, true, false);
            Shaders.endFPOverlay();
        }
    }
    
    public static void beginBlockDamage() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramDamagedBlock);
            Shaders.setRenderStage(RenderStage.DESTROY);
            if (Shaders.ProgramDamagedBlock.getId() == Shaders.ProgramTerrain.getId()) {
                GlState.setDrawBuffers(Shaders.drawBuffersColorAtt[0]);
                GlStateManager._depthMask(false);
            }
        }
    }
    
    public static void endBlockDamage() {
        if (Shaders.isRenderingWorld) {
            GlStateManager._depthMask(true);
            Shaders.useProgram(Shaders.ProgramTexturedLit);
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }
    
    public static void beginOutline() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramBasic);
            Shaders.setRenderStage(RenderStage.OUTLINE);
        }
    }
    
    public static void endOutline() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(Shaders.ProgramTexturedLit);
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }
    
    public static void beginDebug() {
        if (Shaders.isRenderingWorld) {
            Shaders.setRenderStage(RenderStage.DEBUG);
        }
    }
    
    public static void endDebug() {
        if (Shaders.isRenderingWorld) {
            Shaders.setRenderStage(RenderStage.NONE);
        }
    }
    
    public static void renderShadowMap(final fjq entityRenderer, final emz activeRenderInfo, final int pass, final float partialTicks, final long finishTimeNano) {
        if (Shaders.hasShadowMap) {
            final enn mc = enn.N();
            mc.aG().b("shadow pass");
            final fjv renderGlobal = mc.f;
            Shaders.isShadowPass = true;
            Shaders.updateProjectionMatrix();
            Shaders.checkGLError("pre shadow");
            final Matrix4f projectionPrev = RenderSystem.getProjectionMatrix();
            final eir vertexSortingPrev = RenderSystem.getVertexSorting();
            RenderSystem.getModelViewStack().a();
            mc.aG().b("shadow clear");
            Shaders.sfb.bindFramebuffer();
            Shaders.checkGLError("shadow bind sfb");
            mc.aG().b("shadow camera");
            updateActiveRenderInfo(activeRenderInfo, mc, partialTicks);
            final eij matrixStack = new eij();
            Shaders.setCameraShadow(matrixStack, activeRenderInfo, partialTicks);
            final Matrix4f projectionMatrix = RenderSystem.getProjectionMatrix();
            Shaders.checkGLError("shadow camera");
            Shaders.dispatchComputes(Shaders.dfb, Shaders.ProgramShadow.getComputePrograms());
            Shaders.useProgram(Shaders.ProgramShadow);
            Shaders.sfb.setDrawBuffers();
            Shaders.checkGLError("shadow drawbuffers");
            GL30.glReadBuffer(0);
            Shaders.checkGLError("shadow readbuffer");
            Shaders.sfb.setDepthTexture();
            Shaders.sfb.setColorTextures(true);
            Shaders.checkFramebufferStatus("shadow fb");
            GlStateManager._clearColor(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.clear(256);
            for (int i = 0; i < Shaders.usedShadowColorBuffers; ++i) {
                if (Shaders.shadowBuffersClear[i]) {
                    final Vector4f col = Shaders.shadowBuffersClearColor[i];
                    if (col != null) {
                        GlStateManager._clearColor(col.x(), col.y(), col.z(), col.w());
                    }
                    else {
                        GlStateManager._clearColor(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                    GlState.setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
                    GlStateManager.clear(16384);
                }
            }
            Shaders.sfb.setDrawBuffers();
            Shaders.checkGLError("shadow clear");
            mc.aG().b("shadow frustum");
            final fmw frustum = makeShadowFrustum(activeRenderInfo, partialTicks);
            mc.aG().b("shadow culling");
            final eei cameraPos = activeRenderInfo.b();
            frustum.a(cameraPos.c, cameraPos.d, cameraPos.e);
            GlStateManager._enableDepthTest();
            GlStateManager._depthFunc(515);
            GlStateManager._depthMask(true);
            GlStateManager._colorMask(true, true, true, true);
            GlStateManager.lockCull(new GlCullState(false));
            GlStateManager.lockBlend(new GlBlendState(false));
            mc.aG().b("shadow prepareterrain");
            mc.X().a(fuu.e);
            mc.aG().b("shadow setupterrain");
            renderGlobal.setShadowRenderInfos(true);
            Lagometer.timerVisibility.start();
            if (!renderGlobal.isDebugFrustum()) {
                applyFrustumShadow(renderGlobal, frustum);
            }
            Lagometer.timerVisibility.end();
            mc.aG().b("shadow updatechunks");
            mc.aG().b("shadow terrain");
            final double x = cameraPos.a();
            final double y = cameraPos.b();
            final double z = cameraPos.c();
            Lagometer.timerTerrain.start();
            if (Shaders.isRenderShadowTerrain()) {
                GlStateManager.disableAlphaTest();
                renderGlobal.a(RenderTypes.SOLID, matrixStack, x, y, z, projectionMatrix);
                Shaders.checkGLError("shadow terrain solid");
                GlStateManager.enableAlphaTest();
                renderGlobal.a(RenderTypes.CUTOUT_MIPPED, matrixStack, x, y, z, projectionMatrix);
                Shaders.checkGLError("shadow terrain cutoutmipped");
                mc.X().b(fuu.e).a(false, false);
                renderGlobal.a(RenderTypes.CUTOUT, matrixStack, x, y, z, projectionMatrix);
                mc.X().b(fuu.e).restoreLastBlurMipmap();
                Shaders.checkGLError("shadow terrain cutout");
            }
            mc.aG().b("shadow entities");
            ShadersRender.countEntitiesRenderedShadow = 0;
            ShadersRender.countTileEntitiesRenderedShadow = 0;
            final fjv wr = mc.f;
            final fow renderManager = mc.an();
            final fjx.a irendertypebuffer = wr.getRenderTypeTextures().b();
            final boolean playerShadowPass = Shaders.isShadowPass && !mc.t.G_();
            final int minWorldY = mc.s.C_();
            final int maxWorldY = mc.s.aj();
            final LongOpenHashSet renderInfosEntities = renderGlobal.getRenderChunksEntities();
            final Iterable<bfj> entities = (Iterable<bfj>)(Shaders.isRenderShadowEntities() ? Shaders.getCurrentWorld().e() : Collections.EMPTY_LIST);
            for (final bfj entity : entities) {
                final gu posEntity = entity.di();
                if (!renderInfosEntities.contains(hx.c(posEntity)) && posEntity.v() > minWorldY && posEntity.v() < maxWorldY) {
                    continue;
                }
                if ((!renderManager.a(entity, frustum, x, y, z) && !entity.w((bfj)mc.t)) || (entity == activeRenderInfo.g() && !playerShadowPass && !activeRenderInfo.i() && (!(activeRenderInfo.g() instanceof bfz) || !((bfz)activeRenderInfo.g()).fy())) || (entity instanceof fiy && activeRenderInfo.g() != entity)) {
                    continue;
                }
                ++ShadersRender.countEntitiesRenderedShadow;
                Shaders.nextEntity(entity);
                wr.a(entity, x, y, z, partialTicks, matrixStack, (fjx)irendertypebuffer);
            }
            irendertypebuffer.a();
            wr.a(matrixStack);
            irendertypebuffer.a(fkf.b(fuu.e));
            irendertypebuffer.a(fkf.c(fuu.e));
            irendertypebuffer.a(fkf.d(fuu.e));
            irendertypebuffer.a(fkf.j(fuu.e));
            Shaders.endEntities();
            Shaders.beginBlockEntities();
            fmi.updateTextRenderDistance();
            final boolean forgeRenderBoundingBox = Reflector.IForgeBlockEntity_getRenderBoundingBox.exists();
            final fmw camera = frustum;
            final List<fjv.a> renderInfosTileEntities = (List<fjv.a>)(Shaders.isRenderShadowBlockEntities() ? wr.getRenderInfosTileEntities() : Collections.EMPTY_LIST);
            for (final fjv.a worldrenderer$localrenderinformationcontainer : renderInfosTileEntities) {
                final List<czn> list = (List<czn>)worldrenderer$localrenderinformationcontainer.a.d().b();
                if (!list.isEmpty()) {
                    for (final czn tileentity1 : list) {
                        if (forgeRenderBoundingBox) {
                            final eed aabb = (eed)Reflector.call(tileentity1, Reflector.IForgeBlockEntity_getRenderBoundingBox, new Object[0]);
                            if (aabb != null && !camera.a(aabb)) {
                                continue;
                            }
                        }
                        ++ShadersRender.countTileEntitiesRenderedShadow;
                        Shaders.nextBlockEntity(tileentity1);
                        final gu blockpos3 = tileentity1.p();
                        matrixStack.a();
                        matrixStack.a(blockpos3.u() - x, blockpos3.v() - y, blockpos3.w() - z);
                        mc.ao().a(tileentity1, partialTicks, matrixStack, (fjx)irendertypebuffer);
                        matrixStack.b();
                    }
                }
            }
            wr.a(matrixStack);
            irendertypebuffer.a(fkf.c());
            irendertypebuffer.a(fkj.i());
            irendertypebuffer.a(fkj.j());
            irendertypebuffer.a(fkj.c());
            irendertypebuffer.a(fkj.d());
            irendertypebuffer.a(fkj.e());
            irendertypebuffer.a(fkj.g());
            irendertypebuffer.b();
            Shaders.endBlockEntities();
            Lagometer.timerTerrain.end();
            Shaders.checkGLError("shadow entities");
            GlStateManager._depthMask(true);
            GlStateManager._disableBlend();
            GlStateManager.unlockCull();
            GlStateManager._enableCull();
            GlStateManager._blendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1f);
            if (Shaders.usedShadowDepthBuffers >= 2) {
                GlStateManager._activeTexture(33989);
                Shaders.checkGLError("pre copy shadow depth");
                GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
                Shaders.checkGLError("copy shadow depth");
                GlStateManager._activeTexture(33984);
            }
            GlStateManager._disableBlend();
            GlStateManager._depthMask(true);
            mc.X().a(fuu.e);
            Shaders.checkGLError("shadow pre-translucent");
            Shaders.sfb.setDrawBuffers();
            Shaders.checkGLError("shadow drawbuffers pre-translucent");
            Shaders.checkFramebufferStatus("shadow pre-translucent");
            if (Shaders.isRenderShadowTranslucent()) {
                Lagometer.timerTerrain.start();
                mc.aG().b("shadow translucent");
                renderGlobal.a(RenderTypes.TRANSLUCENT, matrixStack, x, y, z, projectionMatrix);
                Shaders.checkGLError("shadow translucent");
                Lagometer.timerTerrain.end();
            }
            GlStateManager.unlockBlend();
            GlStateManager._depthMask(true);
            GlStateManager._enableCull();
            GlStateManager._disableBlend();
            GL30.glFlush();
            Shaders.checkGLError("shadow flush");
            renderGlobal.setShadowRenderInfos(Shaders.isShadowPass = false);
            mc.aG().b("shadow postprocess");
            if (Shaders.hasGlGenMipmap) {
                Shaders.sfb.generateDepthMipmaps(Shaders.shadowMipmapEnabled);
                Shaders.sfb.generateColorMipmaps(true, Shaders.shadowColorMipmapEnabled);
            }
            Shaders.checkGLError("shadow postprocess");
            if (Shaders.hasShadowcompPrograms) {
                Shaders.renderShadowComposites();
            }
            Shaders.dfb.bindFramebuffer();
            GlStateManager._viewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
            GlState.setDrawBuffers(null);
            mc.X().a(fuu.e);
            Shaders.useProgram(Shaders.ProgramTerrain);
            RenderSystem.getModelViewStack().b();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.setProjectionMatrix(projectionPrev, vertexSortingPrev);
            Shaders.checkGLError("shadow end");
        }
    }
    
    public static void applyFrustumShadow(final fjv renderGlobal, final fmw frustum) {
        final enn mc = Config.getMinecraft();
        mc.aG().a("apply_shadow_frustum");
        final int shadowRenderDistance = (int)Shaders.getShadowRenderDistance();
        final int farPlaneDistance = (int)Config.getGameRenderer().l();
        final boolean checkDistance = shadowRenderDistance > 0 && shadowRenderDistance < farPlaneDistance;
        final int maxChunkDist = checkDistance ? shadowRenderDistance : -1;
        if (ShadersRender.frustumTerrainShadowChanged || renderGlobal.needsFrustumUpdate()) {
            renderGlobal.applyFrustum(frustum, false, maxChunkDist);
            ShadersRender.frustumTerrainShadowChanged = false;
        }
        if (ShadersRender.frustumEntitiesShadowChanged || mc.s.getSectionStorage().isUpdated()) {
            renderGlobal.applyFrustumEntities(frustum, maxChunkDist);
            ShadersRender.frustumEntitiesShadowChanged = false;
        }
        mc.aG().c();
    }
    
    public static fmw makeShadowFrustum(final emz camera, final float partialTicks) {
        if (!Shaders.isShadowCulling()) {
            return new ClippingHelperDummy();
        }
        final enn mc = Config.getMinecraft();
        final fjq gameRenderer = Config.getGameRenderer();
        final eij matrixStackIn = new eij();
        if (Reflector.ForgeHooksClient_onCameraSetup.exists()) {
            final Object cameraSetup = Reflector.ForgeHooksClient_onCameraSetup.call(gameRenderer, camera, partialTicks);
            final float cameraSetupYaw = Reflector.callFloat(cameraSetup, Reflector.ViewportEvent_ComputeCameraAngles_getYaw, new Object[0]);
            final float cameraSetupPitch = Reflector.callFloat(cameraSetup, Reflector.ViewportEvent_ComputeCameraAngles_getPitch, new Object[0]);
            final float cameraSetupRoll = Reflector.callFloat(cameraSetup, Reflector.ViewportEvent_ComputeCameraAngles_getRoll, new Object[0]);
            camera.setAnglesInternal(cameraSetupYaw, cameraSetupPitch);
            matrixStackIn.a(a.f.rotationDegrees(cameraSetupRoll));
        }
        matrixStackIn.a(a.b.rotationDegrees(camera.d()));
        matrixStackIn.a(a.d.rotationDegrees(camera.e() + 180.0f));
        final double fov = gameRenderer.a(camera, partialTicks, true);
        final double fovProjection = Math.max(fov, (int)mc.m.ac().c());
        final Matrix4f matrixProjection = gameRenderer.a(fovProjection);
        final Matrix4f matrix4f = matrixStackIn.c().a();
        final eei pos = camera.b();
        final double x = pos.a();
        final double y = pos.b();
        final double z = pos.c();
        final fmw frustum = (fmw)new ShadowFrustum(matrix4f, matrixProjection);
        frustum.a(x, y, z);
        return frustum;
    }
    
    public static void updateActiveRenderInfo(final emz activeRenderInfo, final enn mc, final float partialTicks) {
        activeRenderInfo.a((cls)mc.s, (bfj)((mc.al() == null) ? mc.t : mc.al()), !mc.m.au().a(), mc.m.au().b(), partialTicks);
    }
    
    public static void preRenderChunkLayer(final fkf blockLayerIn) {
        if (blockLayerIn == RenderTypes.SOLID) {
            beginTerrainSolid();
        }
        if (blockLayerIn == RenderTypes.CUTOUT_MIPPED) {
            beginTerrainCutoutMipped();
        }
        if (blockLayerIn == RenderTypes.CUTOUT) {
            beginTerrainCutout();
        }
        if (blockLayerIn == RenderTypes.TRANSLUCENT) {
            beginTranslucent();
        }
        if (blockLayerIn == fkf.u()) {
            beginTripwire();
        }
        if (Shaders.isRenderBackFace(blockLayerIn)) {
            GlStateManager._disableCull();
        }
    }
    
    public static void postRenderChunkLayer(final fkf blockLayerIn) {
        if (Shaders.isRenderBackFace(blockLayerIn)) {
            GlStateManager._enableCull();
        }
    }
    
    public static void preRender(final fkf renderType, final eie buffer) {
        if (!Shaders.isRenderingWorld) {
            return;
        }
        if (Shaders.isShadowPass) {
            return;
        }
        if (renderType.isGlint()) {
            renderEnchantedGlintBegin();
        }
        else if (renderType.getName().equals("eyes")) {
            Shaders.beginSpiderEyes();
        }
        else if (renderType.getName().equals("crumbling")) {
            beginBlockDamage();
        }
        else if (renderType == fkf.aW || renderType == fkf.aX) {
            Shaders.beginLines();
        }
        else if (renderType == fkf.j()) {
            Shaders.beginWaterMask();
        }
        else if (renderType.getName().equals("beacon_beam")) {
            Shaders.beginBeacon();
        }
    }
    
    public static void postRender(final fkf renderType, final eie buffer) {
        if (!Shaders.isRenderingWorld) {
            return;
        }
        if (Shaders.isShadowPass) {
            return;
        }
        if (renderType.isGlint()) {
            renderEnchantedGlintEnd();
        }
        else if (renderType.getName().equals("eyes")) {
            Shaders.endSpiderEyes();
        }
        else if (renderType.getName().equals("crumbling")) {
            endBlockDamage();
        }
        else if (renderType == fkf.aW || renderType == fkf.aX) {
            Shaders.endLines();
        }
        else if (renderType == fkf.j()) {
            Shaders.endWaterMask();
        }
        else if (renderType.getName().equals("beacon_beam")) {
            Shaders.endBeacon();
        }
    }
    
    public static void enableArrayPointerVbo() {
        GL20.glEnableVertexAttribArray(Shaders.midBlockAttrib);
        GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
        GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
        GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
    }
    
    public static void setupArrayPointersVbo() {
        final int vertexSizeI = 18;
        enableArrayPointerVbo();
        GL20.glVertexAttribPointer(Shaders.midBlockAttrib, 3, 5120, false, 72, 32L);
        GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 72, 36L);
        GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 72, 44L);
        GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 72, 52L);
    }
    
    public static void beaconBeamBegin() {
        Shaders.useProgram(Shaders.ProgramBeaconBeam);
    }
    
    public static void beaconBeamStartQuad1() {
    }
    
    public static void beaconBeamStartQuad2() {
    }
    
    public static void beaconBeamDraw1() {
    }
    
    public static void beaconBeamDraw2() {
        GlStateManager._disableBlend();
    }
    
    public static void renderEnchantedGlintBegin() {
        Shaders.useProgram(Shaders.ProgramArmorGlint);
    }
    
    public static void renderEnchantedGlintEnd() {
        if (Shaders.isRenderingWorld) {
            if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands()) {
                Shaders.useProgram(Shaders.ProgramHand);
            }
            else {
                Shaders.useProgram(Shaders.ProgramEntities);
            }
        }
        else {
            Shaders.useProgram(Shaders.ProgramNone);
        }
    }
    
    public static boolean renderEndPortal(final dbc te, final float partialTicks, final float offset, final eij matrixStackIn, final fjx bufferIn, final int combinedLightIn, final int combinedOverlayIn) {
        if (!Shaders.isShadowPass && Shaders.activeProgram.getId() == 0) {
            return false;
        }
        final eij.a matrixEntry = matrixStackIn.c();
        final Matrix4f matrix = matrixEntry.a();
        final Matrix3f matrixNormal = matrixEntry.b();
        final ein bufferbuilder = bufferIn.getBuffer(fkf.b(ShadersRender.END_PORTAL_TEXTURE));
        final float col = 0.5f;
        final float r = col * 0.15f;
        final float g = col * 0.3f;
        final float b = col * 0.4f;
        final float u0 = 0.0f;
        final float u2 = 0.2f;
        final float v0 = u0;
        final float v2 = u2;
        final float dv;
        final float du = dv = System.currentTimeMillis() % 100000L / 100000.0f;
        final float dy = offset;
        final int lm = combinedLightIn;
        final int ov = combinedOverlayIn;
        final float x = 0.0f;
        final float y = 0.0f;
        final float z = 0.0f;
        if (te.a(ha.d)) {
            final hz vec3i = ha.d.q();
            final float xv = (float)vec3i.u();
            final float yv = (float)vec3i.v();
            final float zv = (float)vec3i.w();
            final float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            final float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            final float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.a(matrix, x, y, z + 1.0f).a(r, g, b, 1.0f).a(u0 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y, z + 1.0f).a(r, g, b, 1.0f).a(u0 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y + 1.0f, z + 1.0f).a(r, g, b, 1.0f).a(u2 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x, y + 1.0f, z + 1.0f).a(r, g, b, 1.0f).a(u2 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
        }
        if (te.a(ha.c)) {
            final hz vec3i = ha.c.q();
            final float xv = (float)vec3i.u();
            final float yv = (float)vec3i.v();
            final float zv = (float)vec3i.w();
            final float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            final float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            final float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.a(matrix, x, y + 1.0f, z).a(r, g, b, 1.0f).a(u2 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y + 1.0f, z).a(r, g, b, 1.0f).a(u2 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y, z).a(r, g, b, 1.0f).a(u0 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x, y, z).a(r, g, b, 1.0f).a(u0 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
        }
        if (te.a(ha.f)) {
            final hz vec3i = ha.f.q();
            final float xv = (float)vec3i.u();
            final float yv = (float)vec3i.v();
            final float zv = (float)vec3i.w();
            final float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            final float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            final float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.a(matrix, x + 1.0f, y + 1.0f, z).a(r, g, b, 1.0f).a(u2 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y + 1.0f, z + 1.0f).a(r, g, b, 1.0f).a(u2 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y, z + 1.0f).a(r, g, b, 1.0f).a(u0 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y, z).a(r, g, b, 1.0f).a(u0 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
        }
        if (te.a(ha.e)) {
            final hz vec3i = ha.e.q();
            final float xv = (float)vec3i.u();
            final float yv = (float)vec3i.v();
            final float zv = (float)vec3i.w();
            final float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            final float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            final float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.a(matrix, x, y, z).a(r, g, b, 1.0f).a(u0 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x, y, z + 1.0f).a(r, g, b, 1.0f).a(u0 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x, y + 1.0f, z + 1.0f).a(r, g, b, 1.0f).a(u2 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x, y + 1.0f, z).a(r, g, b, 1.0f).a(u2 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
        }
        if (te.a(ha.a)) {
            final hz vec3i = ha.a.q();
            final float xv = (float)vec3i.u();
            final float yv = (float)vec3i.v();
            final float zv = (float)vec3i.w();
            final float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            final float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            final float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.a(matrix, x, y, z).a(r, g, b, 1.0f).a(u0 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y, z).a(r, g, b, 1.0f).a(u0 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y, z + 1.0f).a(r, g, b, 1.0f).a(u2 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x, y, z + 1.0f).a(r, g, b, 1.0f).a(u2 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
        }
        if (te.a(ha.b)) {
            final hz vec3i = ha.b.q();
            final float xv = (float)vec3i.u();
            final float yv = (float)vec3i.v();
            final float zv = (float)vec3i.w();
            final float xn = MathUtils.getTransformX(matrixNormal, xv, yv, zv);
            final float yn = MathUtils.getTransformY(matrixNormal, xv, yv, zv);
            final float zn = MathUtils.getTransformZ(matrixNormal, xv, yv, zv);
            bufferbuilder.a(matrix, x, y + dy, z + 1.0f).a(r, g, b, 1.0f).a(u0 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y + dy, z + 1.0f).a(r, g, b, 1.0f).a(u0 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x + 1.0f, y + dy, z).a(r, g, b, 1.0f).a(u2 + du, v2 + dv).c(ov).b(lm).a(xn, yn, zn).e();
            bufferbuilder.a(matrix, x, y + dy, z).a(r, g, b, 1.0f).a(u2 + du, v0 + dv).c(ov).b(lm).a(xn, yn, zn).e();
        }
        return true;
    }
    
    static {
        ShadersRender.END_PORTAL_TEXTURE = new acq("textures/entity/end_portal.png");
        ShadersRender.frustumTerrainShadowChanged = false;
        ShadersRender.frustumEntitiesShadowChanged = false;
    }
}
