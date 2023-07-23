// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

public enum BlockDir
{
    DOWN(ha.a), 
    UP(ha.b), 
    NORTH(ha.c), 
    SOUTH(ha.d), 
    WEST(ha.e), 
    EAST(ha.f), 
    NORTH_WEST(ha.c, ha.e), 
    NORTH_EAST(ha.c, ha.f), 
    SOUTH_WEST(ha.d, ha.e), 
    SOUTH_EAST(ha.d, ha.f), 
    DOWN_NORTH(ha.a, ha.c), 
    DOWN_SOUTH(ha.a, ha.d), 
    UP_NORTH(ha.b, ha.c), 
    UP_SOUTH(ha.b, ha.d), 
    DOWN_WEST(ha.a, ha.e), 
    DOWN_EAST(ha.a, ha.f), 
    UP_WEST(ha.b, ha.e), 
    UP_EAST(ha.b, ha.f);
    
    private ha facing1;
    private ha facing2;
    
    private BlockDir(final ha facing1) {
        this.facing1 = facing1;
    }
    
    private BlockDir(final ha facing1, final ha facing2) {
        this.facing1 = facing1;
        this.facing2 = facing2;
    }
    
    public ha getFacing1() {
        return this.facing1;
    }
    
    public ha getFacing2() {
        return this.facing2;
    }
    
    gu offset(gu pos) {
        pos = pos.a(this.facing1, 1);
        if (this.facing2 != null) {
            pos = pos.a(this.facing2, 1);
        }
        return pos;
    }
    
    public int getOffsetX() {
        int offset = this.facing1.j();
        if (this.facing2 != null) {
            offset += this.facing2.j();
        }
        return offset;
    }
    
    public int getOffsetY() {
        int offset = this.facing1.k();
        if (this.facing2 != null) {
            offset += this.facing2.k();
        }
        return offset;
    }
    
    public int getOffsetZ() {
        int offset = this.facing1.l();
        if (this.facing2 != null) {
            offset += this.facing2.l();
        }
        return offset;
    }
    
    public boolean isDouble() {
        return this.facing2 != null;
    }
    
    private static /* synthetic */ BlockDir[] $values() {
        return new BlockDir[] { BlockDir.DOWN, BlockDir.UP, BlockDir.NORTH, BlockDir.SOUTH, BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH_WEST, BlockDir.NORTH_EAST, BlockDir.SOUTH_WEST, BlockDir.SOUTH_EAST, BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH, BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST };
    }
    
    static {
        $VALUES = $values();
    }
}
