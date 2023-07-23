// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.FileNotFoundException;

public class SimpleShaderTexture extends fug
{
    private String texturePath;
    
    public SimpleShaderTexture(final String texturePath) {
        this.texturePath = texturePath;
    }
    
    public void a(final akx resourceManager) throws IOException {
        this.b();
        final InputStream inputStream = Shaders.getShaderPackResourceStream(this.texturePath);
        if (inputStream == null) {
            throw new FileNotFoundException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.texturePath));
        }
        try {
            final ehk nativeImage = ehk.a(inputStream);
            final fwn tms = loadTextureMetadataSection(this.texturePath, new fwn(false, false));
            TextureUtil.prepareImage(this.a(), nativeImage.a(), nativeImage.b());
            nativeImage.a(0, 0, 0, 0, 0, nativeImage.a(), nativeImage.b(), tms.a(), tms.b(), false, true);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    public static fwn loadTextureMetadataSection(final String texturePath, final fwn def) {
        final String pathMeta = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, texturePath);
        final String sectionName = "texture";
        final InputStream inMeta = Shaders.getShaderPackResourceStream(pathMeta);
        if (inMeta != null) {
            final BufferedReader brMeta = new BufferedReader(new InputStreamReader(inMeta));
            try {
                final JsonObject jsonMeta = new JsonParser().parse((Reader)brMeta).getAsJsonObject();
                final JsonObject jsonMetaTexture = jsonMeta.getAsJsonObject(sectionName);
                if (jsonMetaTexture != null) {
                    final fwn meta = fwn.a.b(jsonMetaTexture);
                    if (meta != null) {
                        return meta;
                    }
                }
            }
            catch (RuntimeException re) {
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, pathMeta));
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, re.getClass().getName(), re.getMessage()));
            }
            finally {
                IOUtils.closeQuietly((Reader)brMeta);
                IOUtils.closeQuietly(inMeta);
            }
        }
        return def;
    }
}
