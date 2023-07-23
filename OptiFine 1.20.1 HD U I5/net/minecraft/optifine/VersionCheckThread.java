// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.io.InputStream;
import net.minecraft.client.ClientBrandRetriever;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionCheckThread extends Thread
{
    public VersionCheckThread() {
        super("VersionCheck");
    }
    
    @Override
    public void run() {
        HttpURLConnection conn = null;
        try {
            Config.dbg("Checking for new version");
            final URL url = new URL("http://optifine.net/version/1.20.1/HD_U.txt");
            conn = (HttpURLConnection)url.openConnection();
            final boolean snooper = true;
            if (snooper) {
                conn.setRequestProperty("OF-MC-Version", "1.20.1");
                conn.setRequestProperty("OF-MC-Brand", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, ClientBrandRetriever.getClientModName()));
                conn.setRequestProperty("OF-Edition", "HD_U");
                conn.setRequestProperty("OF-Release", "I5");
                conn.setRequestProperty("OF-Java-Version", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, System.getProperty("java.version")));
                conn.setRequestProperty("OF-CpuCount", invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, Config.getAvailableProcessors()));
                conn.setRequestProperty("OF-OpenGL-Version", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.openGlVersion));
                conn.setRequestProperty("OF-OpenGL-Vendor", invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.openGlVendor));
            }
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.connect();
            try {
                final InputStream in = conn.getInputStream();
                final String verStr = Config.readInputStream(in);
                in.close();
                final String[] verLines = Config.tokenize(verStr, "\n\r");
                if (verLines.length < 1) {
                    return;
                }
                final String newVer = verLines[0].trim();
                Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, newVer));
                if (Config.compareRelease(newVer, "I5") <= 0) {
                    return;
                }
                Config.setNewRelease(newVer);
            }
            finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        catch (Exception e) {
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
        }
    }
}
