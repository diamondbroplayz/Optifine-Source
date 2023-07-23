// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.Map;
import com.google.common.collect.ImmutableMap;

public class SVertexFormat
{
    public static final int vertexSizeBlock = 18;
    public static final int offsetMidBlock = 8;
    public static final int offsetMidTexCoord = 9;
    public static final int offsetTangent = 11;
    public static final int offsetEntity = 13;
    public static final int offsetVelocity = 15;
    public static final eip SHADERS_MIDBLOCK_3B;
    public static final eip PADDING_1B;
    public static final eip SHADERS_MIDTEXCOORD_2F;
    public static final eip SHADERS_TANGENT_4S;
    public static final eip SHADERS_MC_ENTITY_4S;
    public static final eip SHADERS_VELOCITY_3F;
    
    public static eio makeExtendedFormatBlock(final eio blockVanilla) {
        final ImmutableMap.Builder<String, eip> builder = (ImmutableMap.Builder<String, eip>)ImmutableMap.builder();
        builder.putAll((Map)blockVanilla.getElementMapping());
        builder.put((Object)"MidOffset", (Object)SVertexFormat.SHADERS_MIDBLOCK_3B);
        builder.put((Object)"PaddingMO", (Object)SVertexFormat.PADDING_1B);
        builder.put((Object)"MidTexCoord", (Object)SVertexFormat.SHADERS_MIDTEXCOORD_2F);
        builder.put((Object)"Tangent", (Object)SVertexFormat.SHADERS_TANGENT_4S);
        builder.put((Object)"McEntity", (Object)SVertexFormat.SHADERS_MC_ENTITY_4S);
        builder.put((Object)"Velocity", (Object)SVertexFormat.SHADERS_VELOCITY_3F);
        final eio vf = new eio(builder.build());
        return vf;
    }
    
    public static eio makeExtendedFormatEntity(final eio entityVanilla) {
        final ImmutableMap.Builder<String, eip> builder = (ImmutableMap.Builder<String, eip>)ImmutableMap.builder();
        builder.putAll((Map)entityVanilla.getElementMapping());
        builder.put((Object)"MidTexCoord", (Object)SVertexFormat.SHADERS_MIDTEXCOORD_2F);
        builder.put((Object)"Tangent", (Object)SVertexFormat.SHADERS_TANGENT_4S);
        builder.put((Object)"McEntity", (Object)SVertexFormat.SHADERS_MC_ENTITY_4S);
        builder.put((Object)"Velocity", (Object)SVertexFormat.SHADERS_VELOCITY_3F);
        final eio vf = new eio(builder.build());
        return vf;
    }
    
    private static eip makeElement(final String name, final int indexIn, final eip.a typeIn, final eip.b usageIn, final int count) {
        final eip vfe = new eip(indexIn, typeIn, usageIn, count);
        vfe.setName(name);
        return vfe;
    }
    
    static {
        SHADERS_MIDBLOCK_3B = makeElement("SHADERS_MIDOFFSET_3B", 0, eip.a.c, eip.b.e, 3);
        PADDING_1B = makeElement("PADDING_1B", 0, eip.a.c, eip.b.e, 1);
        SHADERS_MIDTEXCOORD_2F = makeElement("SHADERS_MIDTEXCOORD_2F", 0, eip.a.a, eip.b.e, 2);
        SHADERS_TANGENT_4S = makeElement("SHADERS_TANGENT_4S", 0, eip.a.e, eip.b.e, 4);
        SHADERS_MC_ENTITY_4S = makeElement("SHADERS_MC_ENTITY_4S", 0, eip.a.e, eip.b.e, 4);
        SHADERS_VELOCITY_3F = makeElement("SHADERS_VELOCITY_3F", 0, eip.a.a, eip.b.e, 3);
    }
}
