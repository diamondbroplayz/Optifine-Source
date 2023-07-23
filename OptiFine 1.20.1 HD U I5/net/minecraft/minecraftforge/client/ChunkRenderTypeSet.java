// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.client;

import java.util.function.Consumer;
import java.util.Collections;
import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class ChunkRenderTypeSet implements Iterable<fkf>
{
    private static final List<fkf> CHUNK_RENDER_TYPES_LIST;
    private static final fkf[] CHUNK_RENDER_TYPES;
    private static final ChunkRenderTypeSet NONE;
    private static final ChunkRenderTypeSet ALL;
    private final BitSet bits;
    
    public static ChunkRenderTypeSet none() {
        return ChunkRenderTypeSet.NONE;
    }
    
    public static ChunkRenderTypeSet all() {
        return ChunkRenderTypeSet.ALL;
    }
    
    public static ChunkRenderTypeSet of(final fkf... renderTypes) {
        return of((Collection)Arrays.asList(renderTypes));
    }
    
    public static ChunkRenderTypeSet of(final Collection<fkf> renderTypes) {
        if (renderTypes.isEmpty()) {
            return none();
        }
        return of((Iterable)renderTypes);
    }
    
    private static ChunkRenderTypeSet of(final Iterable<fkf> renderTypes) {
        final BitSet bits = new BitSet();
        for (final fkf renderType : renderTypes) {
            final int index = renderType.getChunkLayerId();
            Preconditions.checkArgument(index >= 0, invokedynamic(makeConcatWithConstants:(Lfkf;)Ljava/lang/String;, renderType));
            bits.set(index);
        }
        return new ChunkRenderTypeSet(bits);
    }
    
    public static ChunkRenderTypeSet union(final ChunkRenderTypeSet... sets) {
        return union((Collection)Arrays.asList(sets));
    }
    
    public static ChunkRenderTypeSet union(final Collection<ChunkRenderTypeSet> sets) {
        if (sets.isEmpty()) {
            return none();
        }
        return union((Iterable)sets);
    }
    
    public static ChunkRenderTypeSet union(final Iterable<ChunkRenderTypeSet> sets) {
        final BitSet bits = new BitSet();
        for (final ChunkRenderTypeSet set : sets) {
            bits.or(set.bits);
        }
        return new ChunkRenderTypeSet(bits);
    }
    
    public static ChunkRenderTypeSet intersection(final ChunkRenderTypeSet... sets) {
        return intersection((Collection)Arrays.asList(sets));
    }
    
    public static ChunkRenderTypeSet intersection(final Collection<ChunkRenderTypeSet> sets) {
        if (sets.isEmpty()) {
            return all();
        }
        return intersection((Iterable)sets);
    }
    
    public static ChunkRenderTypeSet intersection(final Iterable<ChunkRenderTypeSet> sets) {
        final BitSet bits = new BitSet();
        bits.set(0, ChunkRenderTypeSet.CHUNK_RENDER_TYPES.length);
        for (final ChunkRenderTypeSet set : sets) {
            bits.and(set.bits);
        }
        return new ChunkRenderTypeSet(bits);
    }
    
    private ChunkRenderTypeSet(final BitSet bits) {
        this.bits = bits;
    }
    
    public boolean isEmpty() {
        return this.bits.isEmpty();
    }
    
    public boolean contains(final fkf renderType) {
        final int id = renderType.getChunkLayerId();
        return id >= 0 && this.bits.get(id);
    }
    
    @Override
    public Iterator<fkf> iterator() {
        return (Iterator<fkf>)new IteratorImpl(this);
    }
    
    public List<fkf> asList() {
        return (List<fkf>)ImmutableList.copyOf((Iterable)this);
    }
    
    static {
        ChunkRenderTypeSet.CHUNK_RENDER_TYPES_LIST = fkf.G();
        ChunkRenderTypeSet.CHUNK_RENDER_TYPES = ChunkRenderTypeSet.CHUNK_RENDER_TYPES_LIST.toArray(new fkf[0]);
        ChunkRenderTypeSet.NONE = (ChunkRenderTypeSet)new None();
        ChunkRenderTypeSet.ALL = (ChunkRenderTypeSet)new All();
    }
    
    private final class IteratorImpl implements Iterator<fkf>
    {
        private int index;
        
        private IteratorImpl(final ChunkRenderTypeSet this$0) {
            this.this$0 = this$0;
            this.index = this.this$0.bits.nextSetBit(0);
        }
        
        @Override
        public boolean hasNext() {
            return this.index >= 0;
        }
        
        @Override
        public fkf next() {
            final fkf renderType = ChunkRenderTypeSet.CHUNK_RENDER_TYPES[this.index];
            this.index = this.this$0.bits.nextSetBit(this.index + 1);
            return renderType;
        }
    }
    
    private static final class None extends ChunkRenderTypeSet
    {
        private None() {
            super(new BitSet());
        }
        
        public boolean isEmpty() {
            return true;
        }
        
        public boolean contains(final fkf renderType) {
            return false;
        }
        
        public Iterator<fkf> iterator() {
            return Collections.emptyIterator();
        }
        
        public List<fkf> asList() {
            return List.of();
        }
    }
    
    private static final class All extends ChunkRenderTypeSet
    {
        private All() {
            super((BitSet)ac.a((Object)new BitSet(), (Consumer)All::lambda$new$0));
        }
        
        public boolean isEmpty() {
            return false;
        }
        
        public boolean contains(final fkf renderType) {
            return renderType.getChunkLayerId() >= 0;
        }
        
        public Iterator<fkf> iterator() {
            return ChunkRenderTypeSet.CHUNK_RENDER_TYPES_LIST.iterator();
        }
        
        public List<fkf> asList() {
            return (List<fkf>)ChunkRenderTypeSet.CHUNK_RENDER_TYPES_LIST;
        }
    }
}
