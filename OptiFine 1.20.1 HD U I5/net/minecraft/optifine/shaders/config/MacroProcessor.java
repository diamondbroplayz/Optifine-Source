// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import net.optifine.shaders.Shaders;
import net.optifine.Config;
import java.io.InputStream;

public class MacroProcessor
{
    public static InputStream process(final InputStream in, final String path, final boolean useShaderOptions) throws IOException {
        String str = Config.readInputStream(in, "ASCII");
        final String strMacroHeader = getMacroHeader(str, useShaderOptions);
        if (!strMacroHeader.isEmpty()) {
            str = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, strMacroHeader, str);
            if (Shaders.saveFinalShaders) {
                final String filePath = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path.replace(':', '/'));
                Shaders.saveShader(filePath, str);
            }
            str = process(str);
        }
        if (Shaders.saveFinalShaders) {
            final String filePath = path.replace(':', '/');
            Shaders.saveShader(filePath, str);
        }
        final byte[] bytes = str.getBytes("ASCII");
        final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return bais;
    }
    
    public static String process(final String strIn) throws IOException {
        final StringReader sr = new StringReader(strIn);
        final BufferedReader br = new BufferedReader(sr);
        final MacroState macroState = new MacroState();
        final StringBuilder sb = new StringBuilder();
        while (true) {
            final String line = br.readLine();
            if (line == null) {
                break;
            }
            if (!macroState.processLine(line)) {
                continue;
            }
            if (MacroState.isMacroLine(line)) {
                continue;
            }
            sb.append(line);
            sb.append("\n");
        }
        final String strOut = sb.toString();
        return strOut;
    }
    
    private static String getMacroHeader(final String str, final boolean useShaderOptions) throws IOException {
        final StringBuilder sb = new StringBuilder();
        List<ShaderOption> sos = null;
        List<ShaderMacro> sms = null;
        final StringReader sr = new StringReader(str);
        final BufferedReader br = new BufferedReader(sr);
        while (true) {
            final String line = br.readLine();
            if (line == null) {
                break;
            }
            if (!MacroState.isMacroLine(line)) {
                continue;
            }
            if (sb.length() == 0) {
                sb.append(ShaderMacros.getFixedMacroLines());
            }
            if (useShaderOptions) {
                if (sos == null) {
                    sos = getMacroOptions();
                }
                final Iterator it = sos.iterator();
                while (it.hasNext()) {
                    final ShaderOption so = it.next();
                    if (!line.contains(so.getName())) {
                        continue;
                    }
                    sb.append(so.getSourceLine());
                    sb.append("\n");
                    it.remove();
                }
            }
            if (sms == null) {
                sms = new ArrayList<ShaderMacro>(Arrays.asList(ShaderMacros.getExtensions()));
            }
            final Iterator it = sms.iterator();
            while (it.hasNext()) {
                final ShaderMacro sm = it.next();
                if (!line.contains(sm.getName())) {
                    continue;
                }
                sb.append(sm.getSourceLine());
                sb.append("\n");
                it.remove();
            }
        }
        return sb.toString();
    }
    
    private static List<ShaderOption> getMacroOptions() {
        final List<ShaderOption> list = new ArrayList<ShaderOption>();
        final ShaderOption[] sos = Shaders.getShaderPackOptions();
        for (int i = 0; i < sos.length; ++i) {
            final ShaderOption so = sos[i];
            final String sourceLine = so.getSourceLine();
            if (sourceLine != null) {
                if (sourceLine.startsWith("#")) {
                    list.add(so);
                }
            }
        }
        return list;
    }
}
