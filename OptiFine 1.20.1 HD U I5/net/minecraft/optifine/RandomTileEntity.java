// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.util.TileEntityUtils;

public class RandomTileEntity implements IRandomEntity
{
    private czn tileEntity;
    
    @Override
    public int getId() {
        return Config.getRandom(this.getSpawnPosition(), 0);
    }
    
    @Override
    public gu getSpawnPosition() {
        if (this.tileEntity instanceof czj) {
            final czj bbe = (czj)this.tileEntity;
            final dcb bs = bbe.q();
            final dco part = (dco)bs.c((dde)cpg.a);
            if (part == dco.a) {
                final ha dir = (ha)bs.c((dde)cpg.aC);
                return this.tileEntity.p().a(dir.g());
            }
        }
        return this.tileEntity.p();
    }
    
    @Override
    public String getName() {
        final String name = TileEntityUtils.getTileEntityName(this.tileEntity);
        return name;
    }
    
    @Override
    public cnk getSpawnBiome() {
        return (cnk)this.tileEntity.k().s(this.tileEntity.p()).a();
    }
    
    @Override
    public int getHealth() {
        return -1;
    }
    
    @Override
    public int getMaxHealth() {
        return -1;
    }
    
    public czn getTileEntity() {
        return this.tileEntity;
    }
    
    public void setTileEntity(final czn tileEntity) {
        this.tileEntity = tileEntity;
    }
    
    @Override
    public qr getNbtTag() {
        final qr nbt = this.tileEntity.nbtTag;
        final long timeMs = System.currentTimeMillis();
        if (nbt == null || this.tileEntity.nbtTagUpdateMs < timeMs - 1000L) {
            this.tileEntity.nbtTag = this.tileEntity.o();
            this.tileEntity.nbtTagUpdateMs = timeMs;
        }
        return nbt;
    }
    
    @Override
    public cen getColor() {
        return RandomEntityRule.getBlockEntityColor(this.tileEntity);
    }
    
    @Override
    public dcb getBlockState() {
        return this.tileEntity.q();
    }
    
    @Override
    public String toString() {
        return this.tileEntity.toString();
    }
}
