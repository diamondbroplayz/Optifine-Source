// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.shaders.Shaders;
import net.optifine.util.EnchantmentUtils;
import net.optifine.config.NbtTagValue;
import java.util.Collection;
import java.util.LinkedHashMap;
import net.optifine.util.StrUtils;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import net.optifine.util.ResUtils;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.optifine.util.PropertiesOrdered;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Map;

public class CustomItems
{
    private static CustomItemProperties[][] itemProperties;
    private static CustomItemProperties[][] enchantmentProperties;
    private static Map mapPotionIds;
    private static fkz itemModelGenerator;
    private static boolean useGlint;
    private static boolean renderOffHand;
    private static AtomicBoolean modelsLoaded;
    public static final int MASK_POTION_SPLASH = 16384;
    public static final int MASK_POTION_NAME = 63;
    public static final int MASK_POTION_EXTENDED = 64;
    public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
    public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
    public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
    public static final String DEFAULT_TEXTURE_OVERLAY = "item/potion_overlay";
    public static final String DEFAULT_TEXTURE_SPLASH = "item/potion_bottle_splash";
    public static final String DEFAULT_TEXTURE_DRINKABLE = "item/potion_bottle_drinkable";
    private static final int[][] EMPTY_INT2_ARRAY;
    private static final Map<String, Integer> mapPotionDamages;
    private static final String TYPE_POTION_NORMAL = "normal";
    private static final String TYPE_POTION_SPLASH = "splash";
    private static final String TYPE_POTION_LINGER = "linger";
    
    public static void update() {
        CustomItems.itemProperties = null;
        CustomItems.enchantmentProperties = null;
        CustomItems.useGlint = true;
        CustomItems.modelsLoaded.set(false);
        if (!Config.isCustomItems()) {
            return;
        }
        readCitProperties("optifine/cit.properties");
        final ajl[] rps = Config.getResourcePacks();
        for (int i = rps.length - 1; i >= 0; --i) {
            final ajl rp = rps[i];
            update(rp);
        }
        update((ajl)Config.getDefaultResourcePack());
        if (CustomItems.itemProperties.length <= 0) {
            CustomItems.itemProperties = null;
        }
        if (CustomItems.enchantmentProperties.length <= 0) {
            CustomItems.enchantmentProperties = null;
        }
    }
    
    private static void readCitProperties(final String fileName) {
        try {
            final acq loc = new acq(fileName);
            final InputStream in = Config.getResourceStream(loc);
            if (in == null) {
                return;
            }
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileName));
            final Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            CustomItems.useGlint = Config.parseBoolean(props.getProperty("useGlint"), true);
        }
        catch (FileNotFoundException e2) {}
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void update(final ajl rp) {
        String[] names = ResUtils.collectFiles(rp, "optifine/cit/", ".properties", (String[])null);
        final Map<String, CustomItemProperties> mapAutoProperties = (Map<String, CustomItemProperties>)makeAutoImageProperties(rp);
        if (mapAutoProperties.size() > 0) {
            final Set<String> keySetAuto = mapAutoProperties.keySet();
            final String[] keysAuto = keySetAuto.toArray(new String[keySetAuto.size()]);
            names = (String[])Config.addObjectsToArray(names, keysAuto);
        }
        Arrays.sort(names);
        final List<List<CustomItemProperties>> itemList = makePropertyList(CustomItems.itemProperties);
        final List<List<CustomItemProperties>> enchantmentList = makePropertyList(CustomItems.enchantmentProperties);
        for (int i = 0; i < names.length; ++i) {
            final String name = names[i];
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            try {
                CustomItemProperties cip = null;
                if (mapAutoProperties.containsKey(name)) {
                    cip = mapAutoProperties.get(name);
                }
                if (cip == null) {
                    final acq locFile = new acq(name);
                    final InputStream in = Config.getResourceStream(rp, ajm.a, locFile);
                    if (in == null) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
                        continue;
                    }
                    final Properties props = new PropertiesOrdered();
                    props.load(in);
                    in.close();
                    cip = new CustomItemProperties(props, name);
                }
                if (cip.isValid(name)) {
                    addToItemList(cip, itemList);
                    addToEnchantmentList(cip, enchantmentList);
                }
            }
            catch (FileNotFoundException e2) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        CustomItems.itemProperties = propertyListToArray(itemList);
        CustomItems.enchantmentProperties = propertyListToArray(enchantmentList);
        final Comparator comp = getPropertiesComparator();
        for (int j = 0; j < CustomItems.itemProperties.length; ++j) {
            final CustomItemProperties[] cips = CustomItems.itemProperties[j];
            if (cips != null) {
                Arrays.sort(cips, comp);
            }
        }
        for (int j = 0; j < CustomItems.enchantmentProperties.length; ++j) {
            final CustomItemProperties[] cips = CustomItems.enchantmentProperties[j];
            if (cips != null) {
                Arrays.sort(cips, comp);
            }
        }
    }
    
    private static Comparator getPropertiesComparator() {
        final Comparator comp = new Comparator() {
            @Override
            public int compare(final Object o1, final Object o2) {
                final CustomItemProperties cip1 = (CustomItemProperties)o1;
                final CustomItemProperties cip2 = (CustomItemProperties)o2;
                if (cip1.layer != cip2.layer) {
                    return cip1.layer - cip2.layer;
                }
                if (cip1.weight != cip2.weight) {
                    return cip2.weight - cip1.weight;
                }
                if (!cip1.basePath.equals(cip2.basePath)) {
                    return cip1.basePath.compareTo(cip2.basePath);
                }
                return cip1.name.compareTo(cip2.name);
            }
        };
        return comp;
    }
    
    public static void updateIcons(final fuu textureMap) {
        while (!CustomItems.modelsLoaded.get()) {
            Config.sleep(100L);
        }
        final List<CustomItemProperties> cips = getAllProperties();
        for (final CustomItemProperties cip : cips) {
            cip.updateIcons(textureMap);
        }
    }
    
    public static void refreshIcons(final fuu textureMap) {
        final List<CustomItemProperties> cips = getAllProperties();
        for (final CustomItemProperties cip : cips) {
            cip.refreshIcons(textureMap);
        }
    }
    
    public static void loadModels(final fww modelBakery) {
        final List<CustomItemProperties> cips = getAllProperties();
        for (final CustomItemProperties cip : cips) {
            cip.loadModels(modelBakery);
        }
        CustomItems.modelsLoaded.set(true);
    }
    
    public static void updateModels() {
        final List<CustomItemProperties> cips = getAllProperties();
        for (final CustomItemProperties cip : cips) {
            if (cip.type != 1) {
                continue;
            }
            final fuu textureMap = Config.getTextureMap();
            cip.updateModelTexture(textureMap, CustomItems.itemModelGenerator);
            cip.updateModelsFull();
        }
    }
    
    private static List<CustomItemProperties> getAllProperties() {
        final List<CustomItemProperties> list = new ArrayList<CustomItemProperties>();
        addAll(CustomItems.itemProperties, list);
        addAll(CustomItems.enchantmentProperties, list);
        return list;
    }
    
    private static void addAll(final CustomItemProperties[][] cipsArr, final List<CustomItemProperties> list) {
        if (cipsArr == null) {
            return;
        }
        for (int i = 0; i < cipsArr.length; ++i) {
            final CustomItemProperties[] cips = cipsArr[i];
            if (cips != null) {
                for (int k = 0; k < cips.length; ++k) {
                    final CustomItemProperties cip = cips[k];
                    if (cip != null) {
                        list.add(cip);
                    }
                }
            }
        }
    }
    
    private static Map<String, CustomItemProperties> makeAutoImageProperties(final ajl rp) {
        final Map<String, CustomItemProperties> map = new HashMap<String, CustomItemProperties>();
        map.putAll(makePotionImageProperties(rp, "normal", jb.i.b((Object)cgc.rv)));
        map.putAll(makePotionImageProperties(rp, "splash", jb.i.b((Object)cgc.uu)));
        map.putAll(makePotionImageProperties(rp, "linger", jb.i.b((Object)cgc.ux)));
        return map;
    }
    
    private static Map<String, CustomItemProperties> makePotionImageProperties(final ajl rp, final String type, final acq itemId) {
        final Map<String, CustomItemProperties> map = new HashMap<String, CustomItemProperties>();
        final String typePrefix = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, type);
        final String[] prefixes = { invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, typePrefix), invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, typePrefix) };
        final String[] suffixes = { ".png" };
        final String[] names = ResUtils.collectFiles(rp, prefixes, suffixes);
        for (int i = 0; i < names.length; ++i) {
            String name;
            final String path = name = names[i];
            name = StrUtils.removePrefixSuffix(name, prefixes, suffixes);
            final Properties props = makePotionProperties(name, type, itemId, path);
            if (props != null) {
                final String pathProp = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, StrUtils.removeSuffix(path, suffixes));
                final CustomItemProperties cip = new CustomItemProperties(props, pathProp);
                map.put(pathProp, cip);
            }
        }
        return map;
    }
    
    private static Properties makePotionProperties(final String name, final String type, acq itemId, final String path) {
        if (StrUtils.endsWith(name, new String[] { "_n", "_s" })) {
            return null;
        }
        if (name.equals("empty") && type.equals("normal")) {
            itemId = jb.i.b((Object)cgc.rw);
            final Properties props = new PropertiesOrdered();
            props.put("type", "item");
            props.put("items", itemId.toString());
            return props;
        }
        final acq potionItemId = itemId;
        final int[] damages = getMapPotionIds().get(name);
        if (damages == null) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return null;
        }
        final StringBuffer bufDamage = new StringBuffer();
        for (int i = 0; i < damages.length; ++i) {
            int damage = damages[i];
            if (type.equals("splash")) {
                damage |= 0x4000;
            }
            if (i > 0) {
                bufDamage.append(" ");
            }
            bufDamage.append(damage);
        }
        int damageMask = 16447;
        if (name.equals("water") || name.equals("mundane")) {
            damageMask |= 0x40;
        }
        final Properties props2 = new PropertiesOrdered();
        props2.put("type", "item");
        props2.put("items", potionItemId.toString());
        props2.put("damage", bufDamage.toString());
        props2.put((Object)"damageMask", invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, damageMask));
        if (type.equals("splash")) {
            props2.put("texture.potion_bottle_splash", name);
        }
        else {
            props2.put("texture.potion_bottle_drinkable", name);
        }
        return props2;
    }
    
    private static Map getMapPotionIds() {
        if (CustomItems.mapPotionIds == null) {
            (CustomItems.mapPotionIds = new LinkedHashMap()).put("water", getPotionId(0, 0));
            CustomItems.mapPotionIds.put("awkward", getPotionId(0, 1));
            CustomItems.mapPotionIds.put("thick", getPotionId(0, 2));
            CustomItems.mapPotionIds.put("potent", getPotionId(0, 3));
            CustomItems.mapPotionIds.put("regeneration", getPotionIds(1));
            CustomItems.mapPotionIds.put("movespeed", getPotionIds(2));
            CustomItems.mapPotionIds.put("fireresistance", getPotionIds(3));
            CustomItems.mapPotionIds.put("poison", getPotionIds(4));
            CustomItems.mapPotionIds.put("heal", getPotionIds(5));
            CustomItems.mapPotionIds.put("nightvision", getPotionIds(6));
            CustomItems.mapPotionIds.put("clear", getPotionId(7, 0));
            CustomItems.mapPotionIds.put("bungling", getPotionId(7, 1));
            CustomItems.mapPotionIds.put("charming", getPotionId(7, 2));
            CustomItems.mapPotionIds.put("rank", getPotionId(7, 3));
            CustomItems.mapPotionIds.put("weakness", getPotionIds(8));
            CustomItems.mapPotionIds.put("damageboost", getPotionIds(9));
            CustomItems.mapPotionIds.put("moveslowdown", getPotionIds(10));
            CustomItems.mapPotionIds.put("leaping", getPotionIds(11));
            CustomItems.mapPotionIds.put("harm", getPotionIds(12));
            CustomItems.mapPotionIds.put("waterbreathing", getPotionIds(13));
            CustomItems.mapPotionIds.put("invisibility", getPotionIds(14));
            CustomItems.mapPotionIds.put("thin", getPotionId(15, 0));
            CustomItems.mapPotionIds.put("debonair", getPotionId(15, 1));
            CustomItems.mapPotionIds.put("sparkling", getPotionId(15, 2));
            CustomItems.mapPotionIds.put("stinky", getPotionId(15, 3));
            CustomItems.mapPotionIds.put("mundane", getPotionId(0, 4));
            CustomItems.mapPotionIds.put("speed", CustomItems.mapPotionIds.get("movespeed"));
            CustomItems.mapPotionIds.put("fire_resistance", CustomItems.mapPotionIds.get("fireresistance"));
            CustomItems.mapPotionIds.put("instant_health", CustomItems.mapPotionIds.get("heal"));
            CustomItems.mapPotionIds.put("night_vision", CustomItems.mapPotionIds.get("nightvision"));
            CustomItems.mapPotionIds.put("strength", CustomItems.mapPotionIds.get("damageboost"));
            CustomItems.mapPotionIds.put("slowness", CustomItems.mapPotionIds.get("moveslowdown"));
            CustomItems.mapPotionIds.put("instant_damage", CustomItems.mapPotionIds.get("harm"));
            CustomItems.mapPotionIds.put("water_breathing", CustomItems.mapPotionIds.get("waterbreathing"));
        }
        return CustomItems.mapPotionIds;
    }
    
    private static int[] getPotionIds(final int baseId) {
        return new int[] { baseId, baseId + 16, baseId + 32, baseId + 48 };
    }
    
    private static int[] getPotionId(final int baseId, final int subId) {
        return new int[] { baseId + subId * 16 };
    }
    
    private static int getPotionNameDamage(final String name) {
        final String fullName = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name);
        final Set<acq> keys = (Set<acq>)jb.e.e();
        for (final acq rl : keys) {
            if (jb.e.c(rl)) {
                final bey potion = (bey)jb.e.a(rl);
                final String potionName = potion.d();
                if (fullName.equals(potionName)) {
                    return bey.a(potion);
                }
                continue;
            }
        }
        return -1;
    }
    
    private static List<List<CustomItemProperties>> makePropertyList(final CustomItemProperties[][] propsArr) {
        final List<List<CustomItemProperties>> list = new ArrayList<List<CustomItemProperties>>();
        if (propsArr != null) {
            for (int i = 0; i < propsArr.length; ++i) {
                final CustomItemProperties[] props = propsArr[i];
                List<CustomItemProperties> propList = null;
                if (props != null) {
                    propList = new ArrayList<CustomItemProperties>(Arrays.asList(props));
                }
                list.add(propList);
            }
        }
        return list;
    }
    
    private static CustomItemProperties[][] propertyListToArray(final List list) {
        final CustomItemProperties[][] propArr = new CustomItemProperties[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            final List subList = list.get(i);
            if (subList != null) {
                final CustomItemProperties[] subArr = subList.toArray(new CustomItemProperties[subList.size()]);
                Arrays.sort(subArr, new CustomItemsComparator());
                propArr[i] = subArr;
            }
        }
        return propArr;
    }
    
    private static void addToItemList(final CustomItemProperties cp, final List<List<CustomItemProperties>> itemList) {
        if (cp.items == null) {
            return;
        }
        for (int i = 0; i < cp.items.length; ++i) {
            final int itemId = cp.items[i];
            if (itemId <= 0) {
                Config.warn(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, itemId));
            }
            else {
                addToList(cp, itemList, itemId);
            }
        }
    }
    
    private static void addToEnchantmentList(final CustomItemProperties cp, final List<List<CustomItemProperties>> enchantmentList) {
        if (cp.type != 2) {
            return;
        }
        if (cp.enchantmentIds == null) {
            return;
        }
        for (int countIds = getMaxEnchantmentId() + 1, i = 0; i < countIds; ++i) {
            final int id = i;
            if (Config.equalsOne(id, cp.enchantmentIds)) {
                addToList(cp, enchantmentList, id);
            }
        }
    }
    
    private static int getMaxEnchantmentId() {
        int maxId = 0;
        while (true) {
            final ckg enchantment = (ckg)jb.g.a(maxId);
            if (enchantment == null) {
                break;
            }
            ++maxId;
        }
        return maxId;
    }
    
    private static void addToList(final CustomItemProperties cp, final List<List<CustomItemProperties>> list, final int id) {
        while (id >= list.size()) {
            list.add(null);
        }
        List<CustomItemProperties> subList = list.get(id);
        if (subList == null) {
            subList = new ArrayList<CustomItemProperties>();
            list.set(id, subList);
        }
        subList.add(cp);
    }
    
    public static fwr getCustomItemModel(final cfz itemStack, final fwr model, final acq modelLocation, final boolean fullModel) {
        if (!fullModel && model.b()) {
            return model;
        }
        if (CustomItems.itemProperties == null) {
            return model;
        }
        final CustomItemProperties props = getCustomItemProperties(itemStack, 1);
        if (props == null) {
            return model;
        }
        final fwr customModel = props.getBakedModel(modelLocation, fullModel);
        if (customModel != null) {
            return customModel;
        }
        return model;
    }
    
    public static acq getCustomArmorTexture(final cfz itemStack, final bfo slot, final String overlay, final acq locArmor) {
        if (CustomItems.itemProperties == null) {
            return locArmor;
        }
        final acq loc = getCustomArmorLocation(itemStack, slot, overlay);
        if (loc == null) {
            return locArmor;
        }
        return loc;
    }
    
    private static acq getCustomArmorLocation(final cfz itemStack, final bfo slot, final String overlay) {
        final CustomItemProperties props = getCustomItemProperties(itemStack, 3);
        if (props == null) {
            return null;
        }
        if (props.mapTextureLocations == null) {
            return props.textureLocation;
        }
        final cfu item = itemStack.d();
        if (!(item instanceof cdj)) {
            return null;
        }
        final cdj itemArmor = (cdj)item;
        final String material = itemArmor.d().e();
        final int layer = (slot == bfo.d) ? 2 : 1;
        final StringBuffer sb = new StringBuffer();
        sb.append("texture.");
        sb.append(material);
        sb.append("_layer_");
        sb.append(layer);
        if (overlay != null) {
            sb.append("_");
            sb.append(overlay);
        }
        final String key = sb.toString();
        final acq loc = props.mapTextureLocations.get(key);
        if (loc == null) {
            return props.textureLocation;
        }
        return loc;
    }
    
    public static acq getCustomElytraTexture(final cfz itemStack, final acq locElytra) {
        if (CustomItems.itemProperties == null) {
            return locElytra;
        }
        final CustomItemProperties props = getCustomItemProperties(itemStack, 4);
        if (props == null) {
            return locElytra;
        }
        if (props.textureLocation == null) {
            return locElytra;
        }
        return props.textureLocation;
    }
    
    private static CustomItemProperties getCustomItemProperties(final cfz itemStack, final int type) {
        final CustomItemProperties[][] propertiesLocal = CustomItems.itemProperties;
        if (propertiesLocal == null) {
            return null;
        }
        if (itemStack == null) {
            return null;
        }
        final cfu item = itemStack.d();
        final int itemId = cfu.a(item);
        if (itemId >= 0 && itemId < propertiesLocal.length) {
            final CustomItemProperties[] cips = propertiesLocal[itemId];
            if (cips != null) {
                for (int i = 0; i < cips.length; ++i) {
                    final CustomItemProperties cip = cips[i];
                    if (cip.type == type) {
                        if (matchesProperties(cip, itemStack, (int[][])null)) {
                            return cip;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private static boolean matchesProperties(final CustomItemProperties cip, final cfz itemStack, final int[][] enchantmentIdLevels) {
        final cfu item = itemStack.d();
        if (cip.damage != null) {
            int damage = getItemStackDamage(itemStack);
            if (damage < 0) {
                return false;
            }
            if (cip.damageMask != 0) {
                damage &= cip.damageMask;
            }
            if (cip.damagePercent) {
                final int damageMax = item.n();
                damage = (int)(damage * 100 / (double)damageMax);
            }
            if (!cip.damage.isInRange(damage)) {
                return false;
            }
        }
        if (cip.stackSize != null && !cip.stackSize.isInRange(itemStack.L())) {
            return false;
        }
        int[][] idLevels = enchantmentIdLevels;
        if (cip.enchantmentIds != null) {
            if (idLevels == null) {
                idLevels = getEnchantmentIdLevels(itemStack);
            }
            boolean idMatch = false;
            for (int i = 0; i < idLevels.length; ++i) {
                final int id = idLevels[i][0];
                if (Config.equalsOne(id, cip.enchantmentIds)) {
                    idMatch = true;
                    break;
                }
            }
            if (!idMatch) {
                return false;
            }
        }
        if (cip.enchantmentLevels != null) {
            if (idLevels == null) {
                idLevels = getEnchantmentIdLevels(itemStack);
            }
            boolean levelMatch = false;
            for (int i = 0; i < idLevels.length; ++i) {
                final int level = idLevels[i][1];
                if (cip.enchantmentLevels.isInRange(level)) {
                    levelMatch = true;
                    break;
                }
            }
            if (!levelMatch) {
                return false;
            }
        }
        if (cip.nbtTagValues != null) {
            final qr nbt = itemStack.v();
            for (int i = 0; i < cip.nbtTagValues.length; ++i) {
                final NbtTagValue ntv = cip.nbtTagValues[i];
                if (!ntv.matches(nbt)) {
                    return false;
                }
            }
        }
        if (cip.hand != 0) {
            if (cip.hand == 1 && CustomItems.renderOffHand) {
                return false;
            }
            if (cip.hand == 2 && !CustomItems.renderOffHand) {
                return false;
            }
        }
        return true;
    }
    
    private static int getItemStackDamage(final cfz itemStack) {
        final cfu item = itemStack.d();
        if (item instanceof cgo) {
            return getPotionDamage(itemStack);
        }
        return itemStack.k();
    }
    
    private static int getPotionDamage(final cfz itemStack) {
        final qr nbt = itemStack.v();
        if (nbt == null) {
            return 0;
        }
        final String name = nbt.l("Potion");
        if (name == null || name.equals("")) {
            return 0;
        }
        final Integer value = CustomItems.mapPotionDamages.get(name);
        if (value == null) {
            return -1;
        }
        int val = value;
        if (itemStack.d() == cgc.uu) {
            val |= 0x4000;
        }
        return val;
    }
    
    private static Map<String, Integer> makeMapPotionDamages() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        addPotion("water", 0, false, map);
        addPotion("awkward", 16, false, map);
        addPotion("thick", 32, false, map);
        addPotion("mundane", 64, false, map);
        addPotion("regeneration", 1, true, map);
        addPotion("swiftness", 2, true, map);
        addPotion("fire_resistance", 3, true, map);
        addPotion("poison", 4, true, map);
        addPotion("healing", 5, true, map);
        addPotion("night_vision", 6, true, map);
        addPotion("weakness", 8, true, map);
        addPotion("strength", 9, true, map);
        addPotion("slowness", 10, true, map);
        addPotion("leaping", 11, true, map);
        addPotion("harming", 12, true, map);
        addPotion("water_breathing", 13, true, map);
        addPotion("invisibility", 14, true, map);
        return map;
    }
    
    private static void addPotion(final String name, int value, final boolean extended, final Map<String, Integer> map) {
        if (extended) {
            value |= 0x2000;
        }
        map.put(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name), Integer.valueOf(value));
        if (extended) {
            final int valueStrong = value | 0x20;
            map.put(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name), Integer.valueOf(valueStrong));
            final int valueLong = value | 0x40;
            map.put(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name), Integer.valueOf(valueLong));
        }
    }
    
    private static int[][] getEnchantmentIdLevels(final cfz itemStack) {
        final cfu item = itemStack.d();
        qx qx;
        if (item == cgc.tC) {
            final cev cev = (cev)cgc.tC;
            qx = cev.d(itemStack);
        }
        else {
            qx = itemStack.x();
        }
        final qx nbt = qx;
        if (nbt == null || nbt.size() <= 0) {
            return CustomItems.EMPTY_INT2_ARRAY;
        }
        final int[][] arr = new int[nbt.size()][2];
        for (int i = 0; i < nbt.size(); ++i) {
            final qr tag = nbt.a(i);
            final String idStr = tag.l("id");
            final int lvl = tag.h("lvl");
            final ckg en = EnchantmentUtils.getEnchantment(idStr);
            if (en != null) {
                final int id = jb.g.a((Object)en);
                arr[i][0] = id;
                arr[i][1] = lvl;
            }
        }
        return arr;
    }
    
    public static boolean renderCustomEffect(final fpw renderItem, final cfz itemStack, final fwr model) {
        final CustomItemProperties[][] propertiesLocal = CustomItems.enchantmentProperties;
        if (propertiesLocal == null) {
            return false;
        }
        if (itemStack == null) {
            return false;
        }
        final int[][] idLevels = getEnchantmentIdLevels(itemStack);
        if (idLevels.length <= 0) {
            return false;
        }
        final Set layersRendered = null;
        final boolean rendered = false;
        return rendered;
    }
    
    public static boolean renderCustomArmorEffect(final bfz entity, final cfz itemStack, final fbf model, final float limbSwing, final float prevLimbSwing, final float partialTicks, final float timeLimbSwing, final float yaw, final float pitch, final float scale) {
        final CustomItemProperties[][] propertiesLocal = CustomItems.enchantmentProperties;
        if (propertiesLocal == null) {
            return false;
        }
        if (Config.isShaders() && Shaders.isShadowPass) {
            return false;
        }
        if (itemStack == null) {
            return false;
        }
        final int[][] idLevels = getEnchantmentIdLevels(itemStack);
        if (idLevels.length <= 0) {
            return false;
        }
        final Set layersRendered = null;
        final boolean rendered = false;
        return rendered;
    }
    
    public static boolean isUseGlint() {
        return CustomItems.useGlint;
    }
    
    public static void setRenderOffHand(final boolean renderOffHand) {
        CustomItems.renderOffHand = renderOffHand;
    }
    
    static {
        CustomItems.itemProperties = null;
        CustomItems.enchantmentProperties = null;
        CustomItems.mapPotionIds = null;
        CustomItems.itemModelGenerator = new fkz();
        CustomItems.useGlint = true;
        CustomItems.renderOffHand = false;
        CustomItems.modelsLoaded = new AtomicBoolean(false);
        EMPTY_INT2_ARRAY = new int[0][];
        mapPotionDamages = makeMapPotionDamages();
    }
}
