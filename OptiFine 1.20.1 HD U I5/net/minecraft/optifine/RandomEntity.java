// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.UUID;

public class RandomEntity implements IRandomEntity
{
    private bfj entity;
    
    @Override
    public int getId() {
        final UUID uuid = this.entity.ct();
        final long uuidLow = uuid.getLeastSignificantBits();
        final int id = (int)(uuidLow & 0x7FFFFFFFL);
        return id;
    }
    
    @Override
    public gu getSpawnPosition() {
        return this.entity.aj().spawnPosition;
    }
    
    @Override
    public cnk getSpawnBiome() {
        return this.entity.aj().spawnBiome;
    }
    
    @Override
    public String getName() {
        if (this.entity.aa()) {
            return this.entity.ab().getString();
        }
        return null;
    }
    
    @Override
    public int getHealth() {
        if (!(this.entity instanceof bfz)) {
            return 0;
        }
        final bfz el = (bfz)this.entity;
        return (int)el.er();
    }
    
    @Override
    public int getMaxHealth() {
        if (!(this.entity instanceof bfz)) {
            return 0;
        }
        final bfz el = (bfz)this.entity;
        return (int)el.eI();
    }
    
    public bfj getEntity() {
        return this.entity;
    }
    
    public void setEntity(final bfj entity) {
        this.entity = entity;
    }
    
    @Override
    public qr getNbtTag() {
        final acb edm = this.entity.aj();
        qr nbt = edm.nbtTag;
        final long timeMs = System.currentTimeMillis();
        if (nbt == null || edm.nbtTagUpdateMs < timeMs - 1000L) {
            nbt = new qr();
            this.entity.f(nbt);
            if (this.entity instanceof bgv) {
                final bgv et = (bgv)this.entity;
                nbt.a("Sitting", et.w());
            }
            edm.nbtTag = nbt;
            edm.nbtTagUpdateMs = timeMs;
        }
        return nbt;
    }
    
    @Override
    public cen getColor() {
        return RandomEntityRule.getEntityColor(this.entity);
    }
    
    @Override
    public dcb getBlockState() {
        if (this.entity instanceof bvh) {
            final bvh ie = (bvh)this.entity;
            final cfu item = ie.j().d();
            if (item instanceof cds) {
                final cds bi = (cds)item;
                final dcb bs = bi.e().n();
                return bs;
            }
        }
        final acb edm = this.entity.aj();
        dcb bs2 = edm.blockStateOn;
        final long timeMs = System.currentTimeMillis();
        if (bs2 == null || edm.blockStateOnUpdateMs < timeMs - 50L) {
            final gu pos = this.entity.di();
            bs2 = this.entity.cH().a_(pos);
            if (bs2.i()) {
                bs2 = this.entity.cH().a_(pos.d());
            }
            edm.blockStateOn = bs2;
            edm.blockStateOnUpdateMs = timeMs;
        }
        return bs2;
    }
    
    @Override
    public String toString() {
        return this.entity.toString();
    }
}
