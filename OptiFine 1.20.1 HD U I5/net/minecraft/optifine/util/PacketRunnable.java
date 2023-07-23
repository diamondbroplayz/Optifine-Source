// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

public class PacketRunnable implements Runnable
{
    private uo packet;
    private Runnable runnable;
    
    public PacketRunnable(final uo packet, final Runnable runnable) {
        this.packet = packet;
        this.runnable = runnable;
    }
    
    @Override
    public void run() {
        this.runnable.run();
    }
    
    public uo getPacket() {
        return this.packet;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Luo;)Ljava/lang/String;, this.packet);
    }
}
