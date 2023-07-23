// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.util.MathUtils;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import net.optifine.util.PropertiesOrdered;
import java.util.Random;

public class CustomPanorama
{
    private static CustomPanoramaProperties customPanoramaProperties;
    private static final Random random;
    
    public static CustomPanoramaProperties getCustomPanoramaProperties() {
        return CustomPanorama.customPanoramaProperties;
    }
    
    public static void update() {
        CustomPanorama.customPanoramaProperties = null;
        final String[] folders = getPanoramaFolders();
        if (folders.length <= 1) {
            return;
        }
        final Properties[] properties = getPanoramaProperties(folders);
        final int[] weights = getWeights(properties);
        final int index = getRandomIndex(weights);
        final String folder = folders[index];
        Properties props = properties[index];
        if (props == null) {
            props = properties[0];
        }
        if (props == null) {
            props = new PropertiesOrdered();
        }
        final CustomPanoramaProperties cpp = CustomPanorama.customPanoramaProperties = new CustomPanoramaProperties(folder, props);
    }
    
    private static String[] getPanoramaFolders() {
        final List<String> listFolders = new ArrayList<String>();
        listFolders.add("textures/gui/title/background");
        for (int i = 0; i < 100; ++i) {
            final String folder = invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i);
            final String path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, folder);
            final acq loc = new acq(path);
            if (Config.hasResource(loc)) {
                listFolders.add(folder);
            }
        }
        final String[] folders = listFolders.toArray(new String[listFolders.size()]);
        return folders;
    }
    
    private static Properties[] getPanoramaProperties(final String[] folders) {
        final Properties[] propsArr = new Properties[folders.length];
        for (int i = 0; i < folders.length; ++i) {
            String folder = folders[i];
            if (i == 0) {
                folder = "optifine/gui";
            }
            else {
                Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, folder));
            }
            final acq propLoc = new acq(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, folder));
            try {
                final InputStream in = Config.getResourceStream(propLoc);
                if (in != null) {
                    final Properties props = new PropertiesOrdered();
                    props.load(in);
                    Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, propLoc.a()));
                    propsArr[i] = props;
                    in.close();
                }
            }
            catch (IOException ex) {}
        }
        return propsArr;
    }
    
    private static int[] getWeights(final Properties[] properties) {
        final int[] weights = new int[properties.length];
        for (int i = 0; i < weights.length; ++i) {
            Properties prop = properties[i];
            if (prop == null) {
                prop = properties[0];
            }
            if (prop == null) {
                weights[i] = 1;
            }
            else {
                final String str = prop.getProperty("weight", null);
                weights[i] = Config.parseInt(str, 1);
            }
        }
        return weights;
    }
    
    private static int getRandomIndex(final int[] weights) {
        final int sumWeights = MathUtils.getSum(weights);
        final int r = CustomPanorama.random.nextInt(sumWeights);
        int sum = 0;
        for (int i = 0; i < weights.length; ++i) {
            sum += weights[i];
            if (sum > r) {
                return i;
            }
        }
        return weights.length - 1;
    }
    
    static {
        CustomPanorama.customPanoramaProperties = null;
        random = new Random();
    }
}
