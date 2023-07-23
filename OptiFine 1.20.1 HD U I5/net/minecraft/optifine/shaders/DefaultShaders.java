// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.io.ByteArrayInputStream;
import com.google.common.base.Charsets;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.io.InputStream;

public class DefaultShaders
{
    public static InputStream getResourceAsStream(final String filename) {
        if (filename.endsWith("/final.vsh")) {
            return getFinalVsh();
        }
        if (filename.endsWith("/final.fsh")) {
            return getFinalFsh();
        }
        return null;
    }
    
    private static InputStream getFinalVsh() {
        final List<String> lines = new ArrayList<String>();
        lines.add("#version 150");
        lines.add("uniform mat4 modelViewMatrix;");
        lines.add("uniform mat4 projectionMatrix;");
        lines.add("in vec2 vaUV0;");
        lines.add("in vec3 vaPosition;");
        lines.add("out vec2 texcoord;");
        lines.add("void main()");
        lines.add("{");
        lines.add("  gl_Position = (projectionMatrix * modelViewMatrix * vec4(vaPosition, 1.0));");
        lines.add("  texcoord = vec4(vaUV0, 0.0, 1.0).st;");
        lines.add("}");
        final String str = lines.stream().collect((Collector<? super Object, ?, String>)Collectors.joining("\n"));
        return getInputStream(str);
    }
    
    private static InputStream getFinalFsh() {
        final List<String> lines = new ArrayList<String>();
        lines.add("#version 150");
        lines.add("uniform sampler2D colortex0;");
        lines.add("in vec2 texcoord;");
        lines.add("/* DRAWBUFFERS:0 */");
        lines.add("out vec4 outColor0;");
        lines.add("void main()");
        lines.add("{");
        lines.add("  outColor0 = texture(colortex0, texcoord);");
        lines.add("}");
        final String str = lines.stream().collect((Collector<? super Object, ?, String>)Collectors.joining("\n"));
        return getInputStream(str);
    }
    
    private static InputStream getInputStream(final String str) {
        final byte[] bytes = str.getBytes(Charsets.US_ASCII);
        return new ByteArrayInputStream(bytes);
    }
}
