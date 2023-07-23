// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.google.common.collect.UnmodifiableIterator;
import java.nio.ByteBuffer;

public class BufferUtil
{
    public static String getBufferHex(final eie bb) {
        final eio.b drawMode = bb.getDrawMode();
        String primitiveName = "";
        int vertexPerPrimitive = -1;
        if (drawMode == eio.b.h) {
            primitiveName = "quad";
            vertexPerPrimitive = 4;
        }
        else {
            if (drawMode != eio.b.e) {
                return invokedynamic(makeConcatWithConstants:(Leio$b;)Ljava/lang/String;, drawMode);
            }
            primitiveName = "triangle";
            vertexPerPrimitive = 3;
        }
        final StringBuffer sb = new StringBuffer();
        for (int vertexCount = bb.getVertexCount(), v = 0; v < vertexCount; ++v) {
            if (v % vertexPerPrimitive == 0) {
                sb.append(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, primitiveName, v / vertexPerPrimitive));
            }
            final String vs = getVertexHex(v, bb);
            sb.append(vs);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private static String getVertexHex(final int vertex, final eie bb) {
        final StringBuffer sb = new StringBuffer();
        final ByteBuffer buf = bb.getByteBuffer();
        final eio vf = bb.getVertexFormat();
        int pos = bb.getStartPosition() + vertex * vf.b();
        for (final eip vfe : vf.c()) {
            if (vfe.getElementCount() > 0) {
                sb.append("(");
            }
            for (int i = 0; i < vfe.getElementCount(); ++i) {
                if (i > 0) {
                    sb.append(" ");
                }
                switch (BufferUtil.BufferUtil$1.$SwitchMap$com$mojang$blaze3d$vertex$VertexFormatElement$Type[vfe.a().ordinal()]) {
                    case 1: {
                        sb.append(buf.getFloat(pos));
                        break;
                    }
                    case 2:
                    case 3: {
                        sb.append(buf.get(pos));
                        break;
                    }
                    case 4:
                    case 5: {
                        sb.append(buf.getShort(pos));
                        break;
                    }
                    case 6:
                    case 7: {
                        sb.append(buf.getShort(pos));
                        break;
                    }
                    default: {
                        sb.append("??");
                        break;
                    }
                }
                pos += vfe.a().a();
            }
            if (vfe.getElementCount() > 0) {
                sb.append(")");
            }
        }
        return sb.toString();
    }
    
    public static String getBufferString(final IntBuffer buf) {
        if (buf == null) {
            return "null";
        }
        final StringBuffer sb = new StringBuffer();
        sb.append(invokedynamic(makeConcatWithConstants:(III)Ljava/lang/String;, buf.position(), buf.limit(), buf.capacity()));
        sb.append("[");
        for (int len = Math.min(buf.limit(), 1024), i = 0; i < len; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(buf.get(i));
        }
        sb.append("]");
        return sb.toString();
    }
    
    public static int[] toArray(final IntBuffer buf) {
        final int[] arr = new int[buf.limit()];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = buf.get(i);
        }
        return arr;
    }
    
    public static FloatBuffer createDirectFloatBuffer(final int capacity) {
        return ehh.a(capacity << 2).asFloatBuffer();
    }
    
    public static void fill(final FloatBuffer buf, final float val) {
        buf.clear();
        for (int i = 0; i < buf.capacity(); ++i) {
            buf.put(i, val);
        }
        buf.clear();
    }
}
