// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.reflect.Reflector;
import net.optifine.config.Matches;
import java.util.Iterator;
import java.util.Set;
import net.optifine.util.StrUtils;
import java.util.HashMap;
import net.optifine.util.TextureUtils;
import net.optifine.config.ConnectedParser;
import java.util.Properties;
import net.optifine.config.MatchProfession;
import net.optifine.config.RangeListInt;
import net.optifine.config.BiomeId;
import net.optifine.config.NbtTagValue;
import java.util.Map;

public class CustomGuiProperties
{
    private String fileName;
    private String basePath;
    private EnumContainer container;
    private Map<acq, acq> textureLocations;
    private NbtTagValue nbtName;
    private BiomeId[] biomes;
    private RangeListInt heights;
    private Boolean large;
    private Boolean trapped;
    private Boolean christmas;
    private Boolean ender;
    private RangeListInt levels;
    private MatchProfession[] professions;
    private EnumVariant[] variants;
    private cen[] colors;
    private static final EnumVariant[] VARIANTS_HORSE;
    private static final EnumVariant[] VARIANTS_DISPENSER;
    private static final EnumVariant[] VARIANTS_INVALID;
    private static final cen[] COLORS_INVALID;
    private static final acq ANVIL_GUI_TEXTURE;
    private static final acq BEACON_GUI_TEXTURE;
    private static final acq BREWING_STAND_GUI_TEXTURE;
    private static final acq CHEST_GUI_TEXTURE;
    private static final acq CRAFTING_TABLE_GUI_TEXTURE;
    private static final acq HORSE_GUI_TEXTURE;
    private static final acq DISPENSER_GUI_TEXTURE;
    private static final acq ENCHANTMENT_TABLE_GUI_TEXTURE;
    private static final acq FURNACE_GUI_TEXTURE;
    private static final acq HOPPER_GUI_TEXTURE;
    private static final acq INVENTORY_GUI_TEXTURE;
    private static final acq SHULKER_BOX_GUI_TEXTURE;
    private static final acq VILLAGER_GUI_TEXTURE;
    
    public CustomGuiProperties(final Properties props, final String path) {
        this.fileName = null;
        this.basePath = null;
        this.container = null;
        this.textureLocations = null;
        this.nbtName = null;
        this.biomes = null;
        this.heights = null;
        this.large = null;
        this.trapped = null;
        this.christmas = null;
        this.ender = null;
        this.levels = null;
        this.professions = null;
        this.variants = null;
        this.colors = null;
        final ConnectedParser cp = new ConnectedParser("CustomGuis");
        this.fileName = cp.parseName(path);
        this.basePath = cp.parseBasePath(path);
        this.container = (EnumContainer)cp.parseEnum(props.getProperty("container"), EnumContainer.values(), "container");
        this.textureLocations = parseTextureLocations(props, "texture", this.container, "textures/gui/", this.basePath);
        this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name"));
        this.biomes = cp.parseBiomes(props.getProperty("biomes"));
        this.heights = cp.parseRangeListIntNeg(props.getProperty("heights"));
        this.large = cp.parseBooleanObject(props.getProperty("large"));
        this.trapped = cp.parseBooleanObject(props.getProperty("trapped"));
        this.christmas = cp.parseBooleanObject(props.getProperty("christmas"));
        this.ender = cp.parseBooleanObject(props.getProperty("ender"));
        this.levels = cp.parseRangeListInt(props.getProperty("levels"));
        this.professions = cp.parseProfessions(props.getProperty("professions"));
        final EnumVariant[] vars = getContainerVariants(this.container);
        this.variants = (EnumVariant[])cp.parseEnums(props.getProperty("variants"), vars, "variants", CustomGuiProperties.VARIANTS_INVALID);
        this.colors = parseEnumDyeColors(props.getProperty("colors"));
    }
    
    private static EnumVariant[] getContainerVariants(final EnumContainer cont) {
        if (cont == EnumContainer.HORSE) {
            return CustomGuiProperties.VARIANTS_HORSE;
        }
        if (cont == EnumContainer.DISPENSER) {
            return CustomGuiProperties.VARIANTS_DISPENSER;
        }
        return new EnumVariant[0];
    }
    
    private static cen[] parseEnumDyeColors(String str) {
        if (str == null) {
            return null;
        }
        str = str.toLowerCase();
        final String[] tokens = Config.tokenize(str, " ");
        final cen[] cols = new cen[tokens.length];
        for (int i = 0; i < tokens.length; ++i) {
            final String token = tokens[i];
            final cen col = parseEnumDyeColor(token);
            if (col == null) {
                warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, token));
                return CustomGuiProperties.COLORS_INVALID;
            }
            cols[i] = col;
        }
        return cols;
    }
    
    private static cen parseEnumDyeColor(final String str) {
        if (str == null) {
            return null;
        }
        final cen[] colors = cen.values();
        for (int i = 0; i < colors.length; ++i) {
            final cen enumDyeColor = colors[i];
            if (enumDyeColor.c().equals(str)) {
                return enumDyeColor;
            }
            if (enumDyeColor.b().equals(str)) {
                return enumDyeColor;
            }
        }
        return null;
    }
    
    private static acq parseTextureLocation(String str, final String basePath) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        String tex = TextureUtils.fixResourcePath(str, basePath);
        if (!tex.endsWith(".png")) {
            tex = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, tex);
        }
        return new acq(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, basePath, tex));
    }
    
    private static Map<acq, acq> parseTextureLocations(final Properties props, final String property, final EnumContainer container, final String pathPrefix, final String basePath) {
        final Map<acq, acq> map = new HashMap<acq, acq>();
        final String propVal = props.getProperty(property);
        if (propVal != null) {
            final acq locKey = getGuiTextureLocation(container);
            final acq locVal = parseTextureLocation(propVal, basePath);
            if (locKey != null && locVal != null) {
                map.put(locKey, locVal);
            }
        }
        final String keyPrefix = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, property);
        final Set keys = props.keySet();
        for (final String key : keys) {
            if (!key.startsWith(keyPrefix)) {
                continue;
            }
            String pathRel = key.substring(keyPrefix.length());
            pathRel = pathRel.replace('\\', '/');
            pathRel = StrUtils.removePrefixSuffix(pathRel, "/", ".png");
            final String path = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, pathPrefix, pathRel);
            final String val = props.getProperty(key);
            final acq locKey2 = new acq(path);
            final acq locVal2 = parseTextureLocation(val, basePath);
            map.put(locKey2, locVal2);
        }
        return map;
    }
    
    private static acq getGuiTextureLocation(final EnumContainer container) {
        if (container == null) {
            return null;
        }
        switch (container) {
            case ANVIL: {
                return CustomGuiProperties.ANVIL_GUI_TEXTURE;
            }
            case BEACON: {
                return CustomGuiProperties.BEACON_GUI_TEXTURE;
            }
            case BREWING_STAND: {
                return CustomGuiProperties.BREWING_STAND_GUI_TEXTURE;
            }
            case CHEST: {
                return CustomGuiProperties.CHEST_GUI_TEXTURE;
            }
            case CRAFTING: {
                return CustomGuiProperties.CRAFTING_TABLE_GUI_TEXTURE;
            }
            case CREATIVE: {
                return null;
            }
            case DISPENSER: {
                return CustomGuiProperties.DISPENSER_GUI_TEXTURE;
            }
            case ENCHANTMENT: {
                return CustomGuiProperties.ENCHANTMENT_TABLE_GUI_TEXTURE;
            }
            case FURNACE: {
                return CustomGuiProperties.FURNACE_GUI_TEXTURE;
            }
            case HOPPER: {
                return CustomGuiProperties.HOPPER_GUI_TEXTURE;
            }
            case HORSE: {
                return CustomGuiProperties.HORSE_GUI_TEXTURE;
            }
            case INVENTORY: {
                return CustomGuiProperties.INVENTORY_GUI_TEXTURE;
            }
            case SHULKER_BOX: {
                return CustomGuiProperties.SHULKER_BOX_GUI_TEXTURE;
            }
            case VILLAGER: {
                return CustomGuiProperties.VILLAGER_GUI_TEXTURE;
            }
            default: {
                return null;
            }
        }
    }
    
    public boolean isValid(final String path) {
        if (this.fileName == null || this.fileName.length() <= 0) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.basePath == null) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.container == null) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.textureLocations.isEmpty()) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.variants == CustomGuiProperties.VARIANTS_INVALID) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        if (this.colors == CustomGuiProperties.COLORS_INVALID) {
            warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, path));
            return false;
        }
        return true;
    }
    
    private static void warn(final String str) {
        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
    }
    
    private boolean matchesGeneral(final EnumContainer ec, final gu pos, final cmp blockAccess) {
        if (this.container != ec) {
            return false;
        }
        if (this.biomes != null) {
            final cnk biome = (cnk)blockAccess.s(pos).a();
            if (!Matches.biome(biome, this.biomes)) {
                return false;
            }
        }
        return this.heights == null || this.heights.isInRange(pos.v());
    }
    
    public boolean matchesPos(final EnumContainer ec, final gu pos, final cmp blockAccess, final euq screen) {
        if (!this.matchesGeneral(ec, pos, blockAccess)) {
            return false;
        }
        if (this.nbtName != null) {
            final String name = getName(screen);
            if (!this.nbtName.matchesValue(name)) {
                return false;
            }
        }
        switch (ec) {
            case BEACON: {
                return this.matchesBeacon(pos, (clp)blockAccess);
            }
            case CHEST: {
                return this.matchesChest(pos, (clp)blockAccess);
            }
            case DISPENSER: {
                return this.matchesDispenser(pos, (clp)blockAccess);
            }
            case SHULKER_BOX: {
                return this.matchesShulker(pos, (clp)blockAccess);
            }
            default: {
                return true;
            }
        }
    }
    
    public static String getName(final euq screen) {
        final sw itc = screen.m();
        if (itc == null) {
            return null;
        }
        return itc.getString();
    }
    
    private boolean matchesBeacon(final gu pos, final clp blockAccess) {
        final czn te = blockAccess.c_(pos);
        if (!(te instanceof czi)) {
            return false;
        }
        final czi teb = (czi)te;
        if (this.levels != null) {
            if (!Reflector.TileEntityBeacon_levels.exists()) {
                return false;
            }
            final int l = Reflector.getFieldValueInt(teb, Reflector.TileEntityBeacon_levels, -1);
            if (!this.levels.isInRange(l)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean matchesChest(final gu pos, final clp blockAccess) {
        final czn te = blockAccess.c_(pos);
        if (te instanceof czu) {
            final czu tec = (czu)te;
            return this.matchesChest(tec, pos, blockAccess);
        }
        if (te instanceof dah) {
            final dah teec = (dah)te;
            return this.matchesEnderChest(teec, pos, blockAccess);
        }
        return false;
    }
    
    private boolean matchesChest(final czu tec, final gu pos, final clp blockAccess) {
        final dcb blockState = blockAccess.a_(pos);
        final dct chestType = (dct)(blockState.b((dde)cqp.c) ? blockState.c((dde)cqp.c) : dct.a);
        final boolean isLarge = chestType != dct.a;
        final boolean isTrapped = tec instanceof dbe;
        final boolean isChristmas = CustomGuis.isChristmas;
        final boolean isEnder = false;
        return this.matchesChest(isLarge, isTrapped, isChristmas, isEnder);
    }
    
    private boolean matchesEnderChest(final dah teec, final gu pos, final clp blockAccess) {
        return this.matchesChest(false, false, false, true);
    }
    
    private boolean matchesChest(final boolean isLarge, final boolean isTrapped, final boolean isChristmas, final boolean isEnder) {
        return (this.large == null || this.large == isLarge) && (this.trapped == null || this.trapped == isTrapped) && (this.christmas == null || this.christmas == isChristmas) && (this.ender == null || this.ender == isEnder);
    }
    
    private boolean matchesDispenser(final gu pos, final clp blockAccess) {
        final czn te = blockAccess.c_(pos);
        if (!(te instanceof dae)) {
            return false;
        }
        final dae ted = (dae)te;
        if (this.variants != null) {
            final EnumVariant var = this.getDispenserVariant(ted);
            if (!Config.equalsOne(var, this.variants)) {
                return false;
            }
        }
        return true;
    }
    
    private EnumVariant getDispenserVariant(final dae ted) {
        if (ted instanceof daf) {
            return EnumVariant.DROPPER;
        }
        return EnumVariant.DISPENSER;
    }
    
    private boolean matchesShulker(final gu pos, final clp blockAccess) {
        final czn te = blockAccess.c_(pos);
        if (!(te instanceof dau)) {
            return false;
        }
        final dau tesb = (dau)te;
        if (this.colors != null) {
            final cen col = tesb.j();
            if (!Config.equalsOne(col, this.colors)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean matchesEntity(final EnumContainer ec, final bfj entity, final cmp blockAccess) {
        if (!this.matchesGeneral(ec, entity.di(), blockAccess)) {
            return false;
        }
        if (this.nbtName != null) {
            final String entityName = entity.cv();
            if (!this.nbtName.matchesValue(entityName)) {
                return false;
            }
        }
        switch (ec) {
            case VILLAGER: {
                return this.matchesVillager(entity, (clp)blockAccess);
            }
            case HORSE: {
                return this.matchesHorse(entity, (clp)blockAccess);
            }
            default: {
                return true;
            }
        }
    }
    
    private boolean matchesVillager(final bfj entity, final clp blockAccess) {
        if (!(entity instanceof byb)) {
            return false;
        }
        final byb entityVillager = (byb)entity;
        if (this.professions != null) {
            final byc vd = entityVillager.gj();
            final bye vp = vd.b();
            final int level = vd.c();
            if (!MatchProfession.matchesOne(vp, level, this.professions)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean matchesHorse(final bfj entity, final clp blockAccess) {
        if (!(entity instanceof btk)) {
            return false;
        }
        final btk ah = (btk)entity;
        if (this.variants != null) {
            final EnumVariant var = this.getHorseVariant(ah);
            if (!Config.equalsOne(var, this.variants)) {
                return false;
            }
        }
        if (this.colors != null && ah instanceof btn) {
            final btn el = (btn)ah;
            final cen col = el.gl();
            if (!Config.equalsOne(col, this.colors)) {
                return false;
            }
        }
        return true;
    }
    
    private EnumVariant getHorseVariant(final btk entity) {
        if (entity instanceof btm) {
            return EnumVariant.HORSE;
        }
        if (entity instanceof btl) {
            return EnumVariant.DONKEY;
        }
        if (entity instanceof btp) {
            return EnumVariant.MULE;
        }
        if (entity instanceof btn) {
            return EnumVariant.LLAMA;
        }
        return null;
    }
    
    public EnumContainer getContainer() {
        return this.container;
    }
    
    public acq getTextureLocation(final acq loc) {
        final acq locNew = (acq)this.textureLocations.get(loc);
        if (locNew == null) {
            return loc;
        }
        return locNew;
    }
    
    @Override
    public String toString() {
        return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Lnet/optifine/CustomGuiProperties$EnumContainer;Ljava/util/Map;)Ljava/lang/String;, this.fileName, this.container, this.textureLocations);
    }
    
    static {
        VARIANTS_HORSE = new EnumVariant[] { EnumVariant.HORSE, EnumVariant.DONKEY, EnumVariant.MULE, EnumVariant.LLAMA };
        VARIANTS_DISPENSER = new EnumVariant[] { EnumVariant.DISPENSER, EnumVariant.DROPPER };
        VARIANTS_INVALID = new EnumVariant[0];
        CustomGuiProperties.COLORS_INVALID = new cen[0];
        CustomGuiProperties.ANVIL_GUI_TEXTURE = new acq("textures/gui/container/anvil.png");
        CustomGuiProperties.BEACON_GUI_TEXTURE = new acq("textures/gui/container/beacon.png");
        CustomGuiProperties.BREWING_STAND_GUI_TEXTURE = new acq("textures/gui/container/brewing_stand.png");
        CustomGuiProperties.CHEST_GUI_TEXTURE = new acq("textures/gui/container/generic_54.png");
        CustomGuiProperties.CRAFTING_TABLE_GUI_TEXTURE = new acq("textures/gui/container/crafting_table.png");
        CustomGuiProperties.HORSE_GUI_TEXTURE = new acq("textures/gui/container/horse.png");
        CustomGuiProperties.DISPENSER_GUI_TEXTURE = new acq("textures/gui/container/dispenser.png");
        CustomGuiProperties.ENCHANTMENT_TABLE_GUI_TEXTURE = new acq("textures/gui/container/enchanting_table.png");
        CustomGuiProperties.FURNACE_GUI_TEXTURE = new acq("textures/gui/container/furnace.png");
        CustomGuiProperties.HOPPER_GUI_TEXTURE = new acq("textures/gui/container/hopper.png");
        CustomGuiProperties.INVENTORY_GUI_TEXTURE = new acq("textures/gui/container/inventory.png");
        CustomGuiProperties.SHULKER_BOX_GUI_TEXTURE = new acq("textures/gui/container/shulker_box.png");
        CustomGuiProperties.VILLAGER_GUI_TEXTURE = new acq("textures/gui/container/villager2.png");
    }
    
    public enum EnumContainer
    {
        public static final EnumContainer ANVIL;
        public static final EnumContainer BEACON;
        public static final EnumContainer BREWING_STAND;
        public static final EnumContainer CHEST;
        public static final EnumContainer CRAFTING;
        public static final EnumContainer DISPENSER;
        public static final EnumContainer ENCHANTMENT;
        public static final EnumContainer FURNACE;
        public static final EnumContainer HOPPER;
        public static final EnumContainer HORSE;
        public static final EnumContainer VILLAGER;
        public static final EnumContainer SHULKER_BOX;
        public static final EnumContainer CREATIVE;
        public static final EnumContainer INVENTORY;
        
        private EnumContainer() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     3: return         
            //     4: nop            
            //     5: nop            
            //     6: nop            
            //    Signature:
            //  ()V
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalArgumentException: Argument 'offset' must be in the range [0, 0], but value was: 2.
            //     at com.strobel.core.VerifyArgument.inRange(VerifyArgument.java:347)
            //     at com.strobel.assembler.ir.StackMappingVisitor.getStackValue(StackMappingVisitor.java:67)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:691)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
            //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
            //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        static {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: ldc             "ANVIL"
            //     3: iconst_0       
            //     4: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //     7: putstatic       net/optifine/CustomGuiProperties$EnumContainer.ANVIL:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    10: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    13: dup            
            //    14: ldc             "BEACON"
            //    16: iconst_1       
            //    17: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //    20: putstatic       net/optifine/CustomGuiProperties$EnumContainer.BEACON:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    23: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    26: dup            
            //    27: ldc             "BREWING_STAND"
            //    29: iconst_2       
            //    30: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //    33: putstatic       net/optifine/CustomGuiProperties$EnumContainer.BREWING_STAND:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    36: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    39: dup            
            //    40: ldc             "CHEST"
            //    42: iconst_3       
            //    43: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //    46: putstatic       net/optifine/CustomGuiProperties$EnumContainer.CHEST:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    49: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    52: dup            
            //    53: ldc             "CRAFTING"
            //    55: iconst_4       
            //    56: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //    59: putstatic       net/optifine/CustomGuiProperties$EnumContainer.CRAFTING:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    62: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    65: dup            
            //    66: ldc             "DISPENSER"
            //    68: iconst_5       
            //    69: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //    72: putstatic       net/optifine/CustomGuiProperties$EnumContainer.DISPENSER:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    75: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    78: dup            
            //    79: ldc             "ENCHANTMENT"
            //    81: bipush          6
            //    83: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //    86: putstatic       net/optifine/CustomGuiProperties$EnumContainer.ENCHANTMENT:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    89: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //    92: dup            
            //    93: ldc             "FURNACE"
            //    95: bipush          7
            //    97: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //   100: putstatic       net/optifine/CustomGuiProperties$EnumContainer.FURNACE:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   103: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   106: dup            
            //   107: ldc             "HOPPER"
            //   109: bipush          8
            //   111: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //   114: putstatic       net/optifine/CustomGuiProperties$EnumContainer.HOPPER:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   117: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   120: dup            
            //   121: ldc             "HORSE"
            //   123: bipush          9
            //   125: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //   128: putstatic       net/optifine/CustomGuiProperties$EnumContainer.HORSE:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   131: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   134: dup            
            //   135: ldc             "VILLAGER"
            //   137: bipush          10
            //   139: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //   142: putstatic       net/optifine/CustomGuiProperties$EnumContainer.VILLAGER:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   145: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   148: dup            
            //   149: ldc             "SHULKER_BOX"
            //   151: bipush          11
            //   153: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //   156: putstatic       net/optifine/CustomGuiProperties$EnumContainer.SHULKER_BOX:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   159: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   162: dup            
            //   163: ldc             "CREATIVE"
            //   165: bipush          12
            //   167: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //   170: putstatic       net/optifine/CustomGuiProperties$EnumContainer.CREATIVE:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   173: new             Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   176: dup            
            //   177: ldc             "INVENTORY"
            //   179: bipush          13
            //   181: invokespecial   net/optifine/CustomGuiProperties$EnumContainer.<init>:(Ljava/lang/String;I)V
            //   184: putstatic       net/optifine/CustomGuiProperties$EnumContainer.INVENTORY:Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   187: invokestatic    net/optifine/CustomGuiProperties$EnumContainer.$values:()[Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   190: putstatic       net/optifine/CustomGuiProperties$EnumContainer.$VALUES:[Lnet/optifine/CustomGuiProperties$EnumContainer;
            //   193: return         
            //   194: nop            
            //   195: nop            
            //   196: nop            
            // 
            // The error that occurred was:
            // 
            // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
            //     at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            //     at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            //     at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            //     at java.base/java.util.Objects.checkIndex(Objects.java:372)
            //     at java.base/java.util.ArrayList.remove(ArrayList.java:535)
            //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:543)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
            //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
            //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
    }
    
    private enum EnumVariant
    {
        public static final EnumVariant HORSE;
        public static final EnumVariant DONKEY;
        public static final EnumVariant MULE;
        public static final EnumVariant LLAMA;
        public static final EnumVariant DISPENSER;
        public static final EnumVariant DROPPER;
        
        private EnumVariant() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     3: return         
            //     4: nop            
            //     5: nop            
            //     6: nop            
            //    Signature:
            //  ()V
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalArgumentException: Argument 'offset' must be in the range [0, 0], but value was: 2.
            //     at com.strobel.core.VerifyArgument.inRange(VerifyArgument.java:347)
            //     at com.strobel.assembler.ir.StackMappingVisitor.getStackValue(StackMappingVisitor.java:67)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:691)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
            //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
            //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        static {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: ldc             "HORSE"
            //     3: iconst_0       
            //     4: invokespecial   net/optifine/CustomGuiProperties$EnumVariant.<init>:(Ljava/lang/String;I)V
            //     7: putstatic       net/optifine/CustomGuiProperties$EnumVariant.HORSE:Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    10: new             Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    13: dup            
            //    14: ldc             "DONKEY"
            //    16: iconst_1       
            //    17: invokespecial   net/optifine/CustomGuiProperties$EnumVariant.<init>:(Ljava/lang/String;I)V
            //    20: putstatic       net/optifine/CustomGuiProperties$EnumVariant.DONKEY:Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    23: new             Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    26: dup            
            //    27: ldc             "MULE"
            //    29: iconst_2       
            //    30: invokespecial   net/optifine/CustomGuiProperties$EnumVariant.<init>:(Ljava/lang/String;I)V
            //    33: putstatic       net/optifine/CustomGuiProperties$EnumVariant.MULE:Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    36: new             Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    39: dup            
            //    40: ldc             "LLAMA"
            //    42: iconst_3       
            //    43: invokespecial   net/optifine/CustomGuiProperties$EnumVariant.<init>:(Ljava/lang/String;I)V
            //    46: putstatic       net/optifine/CustomGuiProperties$EnumVariant.LLAMA:Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    49: new             Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    52: dup            
            //    53: ldc             "DISPENSER"
            //    55: iconst_4       
            //    56: invokespecial   net/optifine/CustomGuiProperties$EnumVariant.<init>:(Ljava/lang/String;I)V
            //    59: putstatic       net/optifine/CustomGuiProperties$EnumVariant.DISPENSER:Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    62: new             Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    65: dup            
            //    66: ldc             "DROPPER"
            //    68: iconst_5       
            //    69: invokespecial   net/optifine/CustomGuiProperties$EnumVariant.<init>:(Ljava/lang/String;I)V
            //    72: putstatic       net/optifine/CustomGuiProperties$EnumVariant.DROPPER:Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    75: invokestatic    net/optifine/CustomGuiProperties$EnumVariant.$values:()[Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    78: putstatic       net/optifine/CustomGuiProperties$EnumVariant.$VALUES:[Lnet/optifine/CustomGuiProperties$EnumVariant;
            //    81: return         
            //    82: nop            
            //    83: nop            
            //    84: nop            
            // 
            // The error that occurred was:
            // 
            // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
            //     at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            //     at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            //     at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            //     at java.base/java.util.Objects.checkIndex(Objects.java:372)
            //     at java.base/java.util.ArrayList.remove(ArrayList.java:535)
            //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:543)
            //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
            //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:576)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
            //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
            //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
    }
}
