// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class DynamicLight
{
    private bfj entity;
    private double offsetY;
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private int lastLightLevel;
    private long timeCheckMs;
    private Set<gu> setLitChunkPos;
    private gu.a blockPosMutable;
    
    public DynamicLight(final bfj entity) {
        this.entity = null;
        this.offsetY = 0.0;
        this.lastPosX = -2.147483648E9;
        this.lastPosY = -2.147483648E9;
        this.lastPosZ = -2.147483648E9;
        this.lastLightLevel = 0;
        this.timeCheckMs = 0L;
        this.setLitChunkPos = new HashSet<gu>();
        this.blockPosMutable = new gu.a();
        this.entity = entity;
        this.offsetY = entity.cF();
    }
    
    public void update(final fjv renderGlobal) {
        if (Config.isDynamicLightsFast()) {
            final long timeNowMs = System.currentTimeMillis();
            if (timeNowMs < this.timeCheckMs + 500L) {
                return;
            }
            this.timeCheckMs = timeNowMs;
        }
        final double posX = this.entity.dn() - 0.5;
        final double posY = this.entity.dp() - 0.5 + this.offsetY;
        final double posZ = this.entity.dt() - 0.5;
        final int lightLevel = DynamicLights.getLightLevel(this.entity);
        final double dx = posX - this.lastPosX;
        final double dy = posY - this.lastPosY;
        final double dz = posZ - this.lastPosZ;
        final double delta = 0.1;
        if (Math.abs(dx) <= delta && Math.abs(dy) <= delta && Math.abs(dz) <= delta && this.lastLightLevel == lightLevel) {
            return;
        }
        this.lastPosX = posX;
        this.lastPosY = posY;
        this.lastPosZ = posZ;
        this.lastLightLevel = lightLevel;
        final Set<gu> setNewPos = new HashSet<gu>();
        if (lightLevel > 0) {
            final ha dirX = ((apa.a(posX) & 0xF) >= 8) ? ha.f : ha.e;
            final ha dirY = ((apa.a(posY) & 0xF) >= 8) ? ha.b : ha.a;
            final ha dirZ = ((apa.a(posZ) & 0xF) >= 8) ? ha.d : ha.c;
            final gu chunkPos = gu.a(posX, posY, posZ);
            final fmp.c chunk = renderGlobal.getRenderChunk(chunkPos);
            final gu chunkPosX = this.getChunkPos(chunk, chunkPos, dirX);
            final fmp.c chunkX = renderGlobal.getRenderChunk(chunkPosX);
            final gu chunkPosZ = this.getChunkPos(chunk, chunkPos, dirZ);
            final fmp.c chunkZ = renderGlobal.getRenderChunk(chunkPosZ);
            final gu chunkPosXZ = this.getChunkPos(chunkX, chunkPosX, dirZ);
            final fmp.c chunkXZ = renderGlobal.getRenderChunk(chunkPosXZ);
            final gu chunkPosY = this.getChunkPos(chunk, chunkPos, dirY);
            final fmp.c chunkY = renderGlobal.getRenderChunk(chunkPosY);
            final gu chunkPosYX = this.getChunkPos(chunkY, chunkPosY, dirX);
            final fmp.c chunkYX = renderGlobal.getRenderChunk(chunkPosYX);
            final gu chunkPosYZ = this.getChunkPos(chunkY, chunkPosY, dirZ);
            final fmp.c chunkYZ = renderGlobal.getRenderChunk(chunkPosYZ);
            final gu chunkPosYXZ = this.getChunkPos(chunkYX, chunkPosYX, dirZ);
            final fmp.c chunkYXZ = renderGlobal.getRenderChunk(chunkPosYXZ);
            this.updateChunkLight(chunk, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkX, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkZ, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkXZ, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkY, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkYX, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkYZ, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkYXZ, this.setLitChunkPos, setNewPos);
        }
        this.updateLitChunks(renderGlobal);
        this.setLitChunkPos = setNewPos;
    }
    
    private gu getChunkPos(final fmp.c renderChunk, final gu pos, final ha facing) {
        if (renderChunk != null) {
            return renderChunk.a(facing);
        }
        return pos.a(facing, 16);
    }
    
    private void updateChunkLight(final fmp.c renderChunk, final Set<gu> setPrevPos, final Set<gu> setNewPos) {
        if (renderChunk == null) {
            return;
        }
        final fmp.b compiledChunk = renderChunk.d();
        if (compiledChunk != null && !compiledChunk.a()) {
            renderChunk.a(false);
            renderChunk.setNeedsBackgroundPriorityUpdate(true);
        }
        final gu pos = renderChunk.f().i();
        if (setPrevPos != null) {
            setPrevPos.remove(pos);
        }
        if (setNewPos != null) {
            setNewPos.add(pos);
        }
    }
    
    public void updateLitChunks(final fjv renderGlobal) {
        for (final gu posOld : this.setLitChunkPos) {
            final fmp.c chunkOld = renderGlobal.getRenderChunk(posOld);
            this.updateChunkLight(chunkOld, null, null);
        }
    }
    
    public bfj getEntity() {
        return this.entity;
    }
    
    public double getLastPosX() {
        return this.lastPosX;
    }
    
    public double getLastPosY() {
        return this.lastPosY;
    }
    
    public double getLastPosZ() {
        return this.lastPosZ;
    }
    
    public int getLastLightLevel() {
        return this.lastLightLevel;
    }
    
    public double getOffsetY() {
        return this.offsetY;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Lbfj;D)Ljava/lang/String;, this.entity, this.offsetY);
    }
}
