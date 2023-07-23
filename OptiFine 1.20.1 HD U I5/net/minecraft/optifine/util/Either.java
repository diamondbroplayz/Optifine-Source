// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Optional;

public class Either<L, R>
{
    private Optional<L> left;
    private Optional<R> right;
    
    private Either(final Optional<L> leftIn, final Optional<R> rightIn) {
        this.left = leftIn;
        this.right = rightIn;
        if (!this.left.isPresent() && !this.right.isPresent()) {
            throw new IllegalArgumentException("Both left and right are not present");
        }
        if (this.left.isPresent() && this.right.isPresent()) {
            throw new IllegalArgumentException("Both left and right are present");
        }
    }
    
    public Optional<L> getLeft() {
        return this.left;
    }
    
    public Optional<R> getRight() {
        return this.right;
    }
    
    public static <L, R> Either<L, R> makeLeft(final L value) {
        return new Either<L, R>(Optional.of(value), Optional.empty());
    }
    
    public static <L, R> Either makeRight(final R value) {
        return new Either(Optional.empty(), (Optional<R>)Optional.of(value));
    }
}
