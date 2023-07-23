// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.entity;

public class PartEntity<T extends bfj> extends bfj
{
    private final T parent;
    
    public PartEntity(final T parent) {
        super(parent.ae(), parent.dI());
        this.parent = parent;
    }
    
    public T getParent() {
        return this.parent;
    }
    
    public uo S() {
        throw new UnsupportedOperationException();
    }
    
    protected void a_() {
        throw new UnsupportedOperationException();
    }
    
    protected void a(final qr compound) {
        throw new UnsupportedOperationException();
    }
    
    protected void b(final qr compound) {
        throw new UnsupportedOperationException();
    }
}
