// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.shaders.Shaders;
import java.util.Map;
import net.optifine.http.FileUploadThread;
import java.util.HashMap;
import net.optifine.http.IFileUploadListener;

public class CrashReporter
{
    public static void onCrashReport(final o crashReport, final ab category) {
        try {
            final Throwable cause = crashReport.b();
            if (cause == null) {
                return;
            }
            if (cause.getClass().getName().contains(".fml.client.SplashProgress")) {
                return;
            }
            if (cause.getClass() == Throwable.class) {
                return;
            }
            if (cause.getClass() == Exception.class && Config.equals(cause.getMessage(), "dummy")) {
                return;
            }
            extendCrashReport(category);
            if (!Config.isTelemetryOn()) {
                return;
            }
            final String url = "http://optifine.net/crashReport";
            final String reportStr = makeReport(crashReport);
            final byte[] content = reportStr.getBytes("ASCII");
            final IFileUploadListener listener = new IFileUploadListener() {
                @Override
                public void fileUploadFinished(final String url, final byte[] content, final Throwable exception) {
                }
            };
            final Map headers = new HashMap();
            headers.put("OF-Version", Config.getVersion());
            headers.put("OF-Summary", makeSummary(crashReport));
            final FileUploadThread fut = new FileUploadThread(url, headers, content, listener);
            fut.setPriority(10);
            fut.start();
            Thread.sleep(1000L);
        }
        catch (Exception e) {
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
        }
    }
    
    private static String makeReport(final o crashReport) {
        final StringBuffer sb = new StringBuffer();
        sb.append(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.getVersion()));
        sb.append(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, makeSummary(crashReport)));
        sb.append("\n");
        sb.append(crashReport.e());
        sb.append("\n");
        return sb.toString();
    }
    
    private static String makeSummary(final o crashReport) {
        final Throwable t = crashReport.b();
        if (t == null) {
            return "Unknown";
        }
        final StackTraceElement[] traces = t.getStackTrace();
        String firstTrace = "unknown";
        if (traces.length > 0) {
            firstTrace = traces[0].toString().trim();
        }
        final String sum = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, t.getClass().getName(), t.getMessage(), crashReport.a(), firstTrace);
        return sum;
    }
    
    public static void extendCrashReport(final ab cat) {
        cat.a("OptiFine Version", Config.getVersion());
        cat.a("OptiFine Build", Config.getBuild());
        if (Config.getGameSettings() != null) {
            cat.a("Render Distance Chunks", invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Config.getChunkViewDistance()));
            cat.a("Mipmaps", invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Config.getMipmapLevels()));
            cat.a("Anisotropic Filtering", invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Config.getAnisotropicFilterLevel()));
            cat.a("Antialiasing", invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Config.getAntialiasingLevel()));
            cat.a("Multitexture", invokedynamic(makeConcatWithConstants:(Z)Ljava/lang/String;, Config.isMultiTexture()));
        }
        cat.a("Shaders", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Shaders.getShaderPackName()));
        cat.a("OpenGlVersion", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.openGlVersion));
        cat.a("OpenGlRenderer", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.openGlRenderer));
        cat.a("OpenGlVendor", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.openGlVendor));
        cat.a("CpuCount", invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Config.getAvailableProcessors()));
    }
}
