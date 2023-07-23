// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import org.joml.Matrix4f;
import java.util.function.Supplier;
import net.optifine.util.MathUtils;
import net.optifine.reflect.Reflector;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.optifine.util.MemoryMonitor;

public class Lagometer
{
    private static enn mc;
    private static enr gameSettings;
    private static ban profiler;
    public static boolean active;
    public static TimerNano timerTick;
    public static TimerNano timerScheduledExecutables;
    public static TimerNano timerChunkUpload;
    public static TimerNano timerChunkUpdate;
    public static TimerNano timerVisibility;
    public static TimerNano timerTerrain;
    public static TimerNano timerServer;
    private static long[] timesFrame;
    private static long[] timesTick;
    private static long[] timesScheduledExecutables;
    private static long[] timesChunkUpload;
    private static long[] timesChunkUpdate;
    private static long[] timesVisibility;
    private static long[] timesTerrain;
    private static long[] timesServer;
    private static boolean[] gcs;
    private static int numRecordedFrameTimes;
    private static long prevFrameTimeNano;
    private static long renderTimeNano;
    
    public static void updateLagometer() {
        if (Lagometer.mc == null) {
            Lagometer.mc = enn.N();
            Lagometer.gameSettings = Lagometer.mc.m;
            Lagometer.profiler = Lagometer.mc.aG();
        }
        if (!Lagometer.gameSettings.aa || (!Lagometer.gameSettings.ofLagometer && !Lagometer.gameSettings.ac)) {
            Lagometer.active = false;
            Lagometer.prevFrameTimeNano = -1L;
            return;
        }
        Lagometer.active = true;
        final long timeNowNano = System.nanoTime();
        if (Lagometer.prevFrameTimeNano == -1L) {
            Lagometer.prevFrameTimeNano = timeNowNano;
            return;
        }
        final int frameIndex = Lagometer.numRecordedFrameTimes & Lagometer.timesFrame.length - 1;
        ++Lagometer.numRecordedFrameTimes;
        final boolean gc = MemoryMonitor.isGcEvent();
        Lagometer.timesFrame[frameIndex] = timeNowNano - Lagometer.prevFrameTimeNano - Lagometer.renderTimeNano;
        Lagometer.timesTick[frameIndex] = Lagometer.timerTick.timeNano;
        Lagometer.timesScheduledExecutables[frameIndex] = Lagometer.timerScheduledExecutables.timeNano;
        Lagometer.timesChunkUpload[frameIndex] = Lagometer.timerChunkUpload.timeNano;
        Lagometer.timesChunkUpdate[frameIndex] = Lagometer.timerChunkUpdate.timeNano;
        Lagometer.timesVisibility[frameIndex] = Lagometer.timerVisibility.timeNano;
        Lagometer.timesTerrain[frameIndex] = Lagometer.timerTerrain.timeNano;
        Lagometer.timesServer[frameIndex] = Lagometer.timerServer.timeNano;
        Lagometer.gcs[frameIndex] = gc;
        Lagometer.timerTick.reset();
        Lagometer.timerScheduledExecutables.reset();
        Lagometer.timerVisibility.reset();
        Lagometer.timerChunkUpdate.reset();
        Lagometer.timerChunkUpload.reset();
        Lagometer.timerTerrain.reset();
        Lagometer.timerServer.reset();
        Lagometer.prevFrameTimeNano = System.nanoTime();
    }
    
    public static void showLagometer(final eox graphicsIn, final int scaleFactor) {
        if (Lagometer.gameSettings == null) {
            return;
        }
        if (!Lagometer.gameSettings.ofLagometer && !Lagometer.gameSettings.ac) {
            return;
        }
        final long timeRenderStartNano = System.nanoTime();
        GlStateManager.clear(256);
        RenderSystem.backupProjectionMatrix();
        final int displayWidth = Lagometer.mc.aM().k();
        final int displayHeight = Lagometer.mc.aM().l();
        final float guiFarPlane = Reflector.ForgeHooksClient_getGuiFarPlane.exists() ? Reflector.ForgeHooksClient_getGuiFarPlane.callFloat(new Object[0]) : 21000.0f;
        final Matrix4f matrix4f = MathUtils.makeOrtho4f(0.0f, (float)displayWidth, 0.0f, (float)displayHeight, 1000.0f, guiFarPlane);
        RenderSystem.setProjectionMatrix(matrix4f, eir.b);
        final eij matrixStackIn = graphicsIn.c();
        matrixStackIn.a();
        matrixStackIn.a(0.0f, 0.0f, 10000.0f - guiFarPlane);
        GlStateManager.disableTexture();
        GlStateManager._depthMask(false);
        GlStateManager._disableCull();
        RenderSystem.setShader((Supplier)fjq::aq);
        RenderSystem.lineWidth(1.0f);
        final eil tess = eil.a();
        final eie tessellator = tess.c();
        tessellator.a(eio.b.a, eih.o);
        for (int frameNum = 0; frameNum < Lagometer.timesFrame.length; ++frameNum) {
            int lum = (frameNum - Lagometer.numRecordedFrameTimes & Lagometer.timesFrame.length - 1) * 100 / Lagometer.timesFrame.length;
            lum += 155;
            float baseHeight = (float)displayHeight;
            long heightFrame = 0L;
            if (Lagometer.gcs[frameNum]) {
                heightFrame = renderTime(frameNum, Lagometer.timesFrame[frameNum], lum, lum / 2, 0, baseHeight, tessellator);
            }
            else {
                heightFrame = renderTime(frameNum, Lagometer.timesFrame[frameNum], lum, lum, lum, baseHeight, tessellator);
                baseHeight -= renderTime(frameNum, Lagometer.timesServer[frameNum], lum / 2, lum / 2, lum / 2, baseHeight, tessellator);
                baseHeight -= renderTime(frameNum, Lagometer.timesTerrain[frameNum], 0, lum, 0, baseHeight, tessellator);
                baseHeight -= renderTime(frameNum, Lagometer.timesVisibility[frameNum], lum, lum, 0, baseHeight, tessellator);
                baseHeight -= renderTime(frameNum, Lagometer.timesChunkUpdate[frameNum], lum, 0, 0, baseHeight, tessellator);
                baseHeight -= renderTime(frameNum, Lagometer.timesChunkUpload[frameNum], lum, 0, lum, baseHeight, tessellator);
                baseHeight -= renderTime(frameNum, Lagometer.timesScheduledExecutables[frameNum], 0, 0, lum, baseHeight, tessellator);
                baseHeight -= renderTime(frameNum, Lagometer.timesTick[frameNum], 0, lum, lum, baseHeight, tessellator);
            }
        }
        renderTimeDivider(0, Lagometer.timesFrame.length, 33333333L, 196, 196, 196, (float)displayHeight, tessellator);
        renderTimeDivider(0, Lagometer.timesFrame.length, 16666666L, 196, 196, 196, (float)displayHeight, tessellator);
        tess.b();
        GlStateManager._enableCull();
        GlStateManager._depthMask(true);
        GlStateManager.enableTexture();
        final int y60 = displayHeight - 80;
        final int y61 = displayHeight - 160;
        final String str30 = Config.isShowFrameTime() ? "33" : "30";
        final String str31 = Config.isShowFrameTime() ? "17" : "60";
        graphicsIn.b(Lagometer.mc.h, str30, 2, y61 + 1, -8947849);
        graphicsIn.b(Lagometer.mc.h, str30, 1, y61, -3881788);
        graphicsIn.b(Lagometer.mc.h, str31, 2, y60 + 1, -8947849);
        graphicsIn.b(Lagometer.mc.h, str31, 1, y60, -3881788);
        RenderSystem.restoreProjectionMatrix();
        matrixStackIn.b();
        float lumMem = 1.0f - (float)((System.currentTimeMillis() - MemoryMonitor.getStartTimeMs()) / 1000.0);
        lumMem = Config.limit(lumMem, 0.0f, 1.0f);
        final int memColR = (int)apa.i(lumMem, 180.0f, 255.0f);
        final int memColG = (int)apa.i(lumMem, 110.0f, 155.0f);
        final int memColB = (int)apa.i(lumMem, 15.0f, 20.0f);
        final int colMem = memColR << 16 | memColG << 8 | memColB;
        final int posX = 512 / scaleFactor + 2;
        final int posY = displayHeight / scaleFactor - 8;
        graphicsIn.a(posX - 1, posY - 1, posX + 50, posY + 10, -1605349296);
        graphicsIn.b(Lagometer.mc.h, invokedynamic(makeConcatWithConstants:(J)Ljava/lang/String;, MemoryMonitor.getGcRateMb()), posX, posY, colMem);
        Lagometer.renderTimeNano = System.nanoTime() - timeRenderStartNano;
    }
    
    private static long renderTime(final int frameNum, final long time, final int r, final int g, final int b, final float baseHeight, final eie tessellator) {
        final long heightTime = time / 200000L;
        if (heightTime < 3L) {
            return 0L;
        }
        tessellator.a((double)(frameNum + 0.5f), (double)(baseHeight - heightTime + 0.5f), 0.0).a(r, g, b, 255).a(0.0f, 1.0f, 0.0f).e();
        tessellator.a((double)(frameNum + 0.5f), (double)(baseHeight + 0.5f), 0.0).a(r, g, b, 255).a(0.0f, 1.0f, 0.0f).e();
        return heightTime;
    }
    
    private static long renderTimeDivider(final int frameStart, final int frameEnd, final long time, final int r, final int g, final int b, final float baseHeight, final eie tessellator) {
        final long heightTime = time / 200000L;
        if (heightTime < 3L) {
            return 0L;
        }
        tessellator.a((double)(frameStart + 0.5f), (double)(baseHeight - heightTime + 0.5f), 0.0).a(r, g, b, 255).a(1.0f, 0.0f, 0.0f).e();
        tessellator.a((double)(frameEnd + 0.5f), (double)(baseHeight - heightTime + 0.5f), 0.0).a(r, g, b, 255).a(1.0f, 0.0f, 0.0f).e();
        return heightTime;
    }
    
    public static boolean isActive() {
        return Lagometer.active;
    }
    
    static {
        Lagometer.active = false;
        Lagometer.timerTick = new TimerNano();
        Lagometer.timerScheduledExecutables = new TimerNano();
        Lagometer.timerChunkUpload = new TimerNano();
        Lagometer.timerChunkUpdate = new TimerNano();
        Lagometer.timerVisibility = new TimerNano();
        Lagometer.timerTerrain = new TimerNano();
        Lagometer.timerServer = new TimerNano();
        Lagometer.timesFrame = new long[512];
        Lagometer.timesTick = new long[512];
        Lagometer.timesScheduledExecutables = new long[512];
        Lagometer.timesChunkUpload = new long[512];
        Lagometer.timesChunkUpdate = new long[512];
        Lagometer.timesVisibility = new long[512];
        Lagometer.timesTerrain = new long[512];
        Lagometer.timesServer = new long[512];
        Lagometer.gcs = new boolean[512];
        Lagometer.numRecordedFrameTimes = 0;
        Lagometer.prevFrameTimeNano = -1L;
        Lagometer.renderTimeNano = 0L;
    }
    
    public static class TimerNano
    {
        public long timeStartNano;
        public long timeNano;
        
        public TimerNano() {
            this.timeStartNano = 0L;
            this.timeNano = 0L;
        }
        
        public void start() {
            if (!Lagometer.active) {
                return;
            }
            if (this.timeStartNano == 0L) {
                this.timeStartNano = System.nanoTime();
            }
        }
        
        public void end() {
            if (!Lagometer.active) {
                return;
            }
            if (this.timeStartNano != 0L) {
                this.timeNano += System.nanoTime() - this.timeStartNano;
                this.timeStartNano = 0L;
            }
        }
        
        private void reset() {
            this.timeNano = 0L;
            this.timeStartNano = 0L;
        }
    }
}
