// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import java.util.Calendar;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;
import net.optifine.util.PropertiesOrdered;
import java.util.Arrays;
import net.optifine.util.ResUtils;
import java.util.List;
import java.util.ArrayList;
import net.optifine.override.PlayerControllerOF;

public class CustomGuis
{
    private static enn mc;
    private static PlayerControllerOF playerControllerOF;
    private static CustomGuiProperties[][] guiProperties;
    public static boolean isChristmas;
    
    public static acq getTextureLocation(final acq loc) {
        if (CustomGuis.guiProperties == null) {
            return loc;
        }
        final euq screen = CustomGuis.mc.z;
        if (!(screen instanceof evp)) {
            return loc;
        }
        if (!loc.b().equals("minecraft") || !loc.a().startsWith("textures/gui/")) {
            return loc;
        }
        if (CustomGuis.playerControllerOF == null) {
            return loc;
        }
        final cmp world = (cmp)CustomGuis.mc.s;
        if (world == null) {
            return loc;
        }
        if (screen instanceof ewd) {
            return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, CustomGuis.mc.t.di(), world, loc, screen);
        }
        if (screen instanceof ewo) {
            return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, CustomGuis.mc.t.di(), world, loc, screen);
        }
        final gu pos = CustomGuis.playerControllerOF.getLastClickBlockPos();
        if (pos != null) {
            if (screen instanceof evs) {
                return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, pos, world, loc, screen);
            }
            if (screen instanceof evt) {
                return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, pos, world, loc, screen);
            }
            if (screen instanceof evx) {
                return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, pos, world, loc, screen);
            }
            if (screen instanceof ewa) {
                return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, pos, world, loc, screen);
            }
            if (screen instanceof ewb) {
                return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, pos, world, loc, screen);
            }
            if (screen instanceof ewf) {
                return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, pos, world, loc, screen);
            }
            if (screen instanceof ewi) {
                return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, pos, world, loc, screen);
            }
            if (screen instanceof ewj) {
                return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, pos, world, loc, screen);
            }
            if (screen instanceof ewm) {
                return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, pos, world, loc, screen);
            }
            if (screen instanceof ewx) {
                return getTexturePos(CustomGuiProperties.EnumContainer.SHULKER_BOX, pos, world, loc, screen);
            }
        }
        final bfj entity = CustomGuis.playerControllerOF.getLastClickEntity();
        if (entity != null) {
            if (screen instanceof ewn) {
                return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, world, loc);
            }
            if (screen instanceof ewu) {
                return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, world, loc);
            }
        }
        return loc;
    }
    
    private static acq getTexturePos(final CustomGuiProperties.EnumContainer container, final gu pos, final cmp blockAccess, final acq loc, final euq screen) {
        final CustomGuiProperties[] props = CustomGuis.guiProperties[container.ordinal()];
        if (props == null) {
            return loc;
        }
        for (int i = 0; i < props.length; ++i) {
            final CustomGuiProperties prop = props[i];
            if (prop.matchesPos(container, pos, blockAccess, screen)) {
                return prop.getTextureLocation(loc);
            }
        }
        return loc;
    }
    
    private static acq getTextureEntity(final CustomGuiProperties.EnumContainer container, final bfj entity, final cmp blockAccess, final acq loc) {
        final CustomGuiProperties[] props = CustomGuis.guiProperties[container.ordinal()];
        if (props == null) {
            return loc;
        }
        for (int i = 0; i < props.length; ++i) {
            final CustomGuiProperties prop = props[i];
            if (prop.matchesEntity(container, entity, blockAccess)) {
                return prop.getTextureLocation(loc);
            }
        }
        return loc;
    }
    
    public static void update() {
        CustomGuis.guiProperties = null;
        if (!Config.isCustomGuis()) {
            return;
        }
        final List<List<CustomGuiProperties>> listProps = new ArrayList<List<CustomGuiProperties>>();
        final ajl[] rps = Config.getResourcePacks();
        for (int i = rps.length - 1; i >= 0; --i) {
            final ajl rp = rps[i];
            update(rp, (List)listProps);
        }
        CustomGuis.guiProperties = propertyListToArray(listProps);
    }
    
    private static CustomGuiProperties[][] propertyListToArray(final List<List<CustomGuiProperties>> listProps) {
        if (listProps.isEmpty()) {
            return null;
        }
        final CustomGuiProperties[][] cgps = new CustomGuiProperties[CustomGuiProperties.EnumContainer.values().length][];
        for (int i = 0; i < cgps.length; ++i) {
            if (listProps.size() > i) {
                final List<CustomGuiProperties> subList = listProps.get(i);
                if (subList != null) {
                    final CustomGuiProperties[] subArr = subList.toArray(new CustomGuiProperties[subList.size()]);
                    cgps[i] = subArr;
                }
            }
        }
        return cgps;
    }
    
    private static void update(final ajl rp, final List<List<CustomGuiProperties>> listProps) {
        final String[] paths = ResUtils.collectFiles(rp, "optifine/gui/container/", ".properties", (String[])null);
        Arrays.sort(paths);
        for (int i = 0; i < paths.length; ++i) {
            final String name = paths[i];
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            try {
                final acq locFile = new acq(name);
                final InputStream in = Config.getResourceStream(rp, ajm.a, locFile);
                if (in == null) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
                }
                else {
                    final Properties props = new PropertiesOrdered();
                    props.load(in);
                    in.close();
                    final CustomGuiProperties cgp = new CustomGuiProperties(props, name);
                    if (cgp.isValid(name)) {
                        addToList(cgp, listProps);
                    }
                }
            }
            catch (FileNotFoundException e2) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void addToList(final CustomGuiProperties cgp, final List<List<CustomGuiProperties>> listProps) {
        if (cgp.getContainer() == null) {
            warn(invokedynamic(makeConcatWithConstants:(Lnet/optifine/CustomGuiProperties$EnumContainer;)Ljava/lang/String;, cgp.getContainer()));
            return;
        }
        final int indexContainer = cgp.getContainer().ordinal();
        while (listProps.size() <= indexContainer) {
            listProps.add(null);
        }
        List<CustomGuiProperties> subList = listProps.get(indexContainer);
        if (subList == null) {
            subList = new ArrayList<CustomGuiProperties>();
            listProps.set(indexContainer, subList);
        }
        subList.add(cgp);
    }
    
    public static PlayerControllerOF getPlayerControllerOF() {
        return CustomGuis.playerControllerOF;
    }
    
    public static void setPlayerControllerOF(final PlayerControllerOF playerControllerOF) {
        CustomGuis.playerControllerOF = playerControllerOF;
    }
    
    private static boolean isChristmas() {
        final Calendar calendar = Calendar.getInstance();
        return calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26;
    }
    
    private static void warn(final String str) {
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    static {
        CustomGuis.mc = Config.getMinecraft();
        CustomGuis.playerControllerOF = null;
        CustomGuis.guiProperties = null;
        CustomGuis.isChristmas = isChristmas();
    }
}
