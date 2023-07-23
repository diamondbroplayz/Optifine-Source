// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.ArrayDeque;
import java.util.Deque;

public class ProgramStack
{
    private Deque<Program> stack;
    
    public ProgramStack() {
        this.stack = new ArrayDeque<Program>();
    }
    
    public void push(final Program p) {
        this.stack.addLast(p);
        if (this.stack.size() > 100) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.stack.size()));
        }
    }
    
    public Program pop() {
        if (this.stack.isEmpty()) {
            throw new RuntimeException("Program stack empty");
        }
        final Program p = this.stack.pollLast();
        return p;
    }
}
