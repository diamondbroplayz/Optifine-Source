// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.concurrent.ExecutorService;
import net.optifine.util.ExecutorProxy;

public class SmartExecutorService extends ExecutorProxy
{
    private ExecutorService executor;
    
    public SmartExecutorService(final ExecutorService executor) {
        this.executor = executor;
    }
    
    @Override
    protected ExecutorService delegate() {
        return this.executor;
    }
    
    @Override
    public void execute(final Runnable command) {
        final Runnable smartCommand = new Runnable() {
            @Override
            public void run() {
                final long timeStartMs = System.currentTimeMillis();
                command.run();
                final long timeEndMs = System.currentTimeMillis();
                final long runMs = timeEndMs - timeStartMs;
                final enn mc = enn.N();
                if (mc != null && (mc.Q() || mc.s != null)) {
                    Config.sleep(10L * runMs);
                }
            }
        };
        super.execute(smartCommand);
    }
}
