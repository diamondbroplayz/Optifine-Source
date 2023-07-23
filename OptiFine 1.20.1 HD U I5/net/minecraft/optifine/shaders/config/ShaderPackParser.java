// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import net.optifine.util.DynamicDimension;
import java.util.Collections;
import net.optifine.render.GlBlendState;
import net.optifine.render.GlAlphaState;
import net.optifine.expr.ExpressionFloatArrayCached;
import net.optifine.expr.IExpressionFloatArray;
import net.optifine.expr.ExpressionFloatCached;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.ExpressionType;
import net.optifine.shaders.uniform.ShaderExpressionResolver;
import net.optifine.shaders.uniform.UniformType;
import net.optifine.shaders.uniform.CustomUniform;
import net.optifine.expr.IExpression;
import net.optifine.shaders.uniform.CustomUniforms;
import java.util.LinkedHashSet;
import net.optifine.shaders.ShadersCompatibility;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;
import net.optifine.shaders.Program;
import net.optifine.util.StrUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShaderUtils;
import net.optifine.expr.ParseException;
import net.optifine.expr.IExpressionResolver;
import net.optifine.expr.ExpressionParser;
import java.util.regex.Matcher;
import net.optifine.shaders.SMCLog;
import net.optifine.expr.IExpressionBool;
import java.util.Properties;
import java.util.HashSet;
import net.optifine.util.LineBuffer;
import java.io.IOException;
import java.util.ArrayList;
import net.optifine.Config;
import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.optifine.shaders.IShaderPack;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ShaderPackParser
{
    public static final Pattern PATTERN_VERSION;
    public static final Pattern PATTERN_INCLUDE;
    private static final Set<String> setConstNames;
    private static final Map<String, Integer> mapAlphaFuncs;
    private static final Map<String, Integer> mapBlendFactors;
    
    public static ShaderOption[] parseShaderPackOptions(final IShaderPack shaderPack, final String[] programNames, final List<Integer> listDimensions) {
        if (shaderPack == null) {
            return new ShaderOption[0];
        }
        final Map<String, ShaderOption> mapOptions = new HashMap<String, ShaderOption>();
        collectShaderOptions(shaderPack, "/shaders", programNames, mapOptions);
        for (final int dimId : listDimensions) {
            final String dirWorld = invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, dimId);
            collectShaderOptions(shaderPack, dirWorld, programNames, mapOptions);
        }
        final Collection<ShaderOption> options = mapOptions.values();
        final ShaderOption[] sos = options.toArray(new ShaderOption[options.size()]);
        final Comparator<ShaderOption> comp = new Comparator<ShaderOption>() {
            @Override
            public int compare(final ShaderOption o1, final ShaderOption o2) {
                return ((String)o1).compareToIgnoreCase(o2.getName());
            }
        };
        Arrays.sort(sos, comp);
        return sos;
    }
    
    private static void collectShaderOptions(final IShaderPack shaderPack, final String dir, final String[] programNames, final Map<String, ShaderOption> mapOptions) {
        for (int i = 0; i < programNames.length; ++i) {
            final String programName = programNames[i];
            if (!programName.equals("")) {
                final String csh = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, dir, programName);
                final String vsh = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, dir, programName);
                final String gsh = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, dir, programName);
                final String fsh = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, dir, programName);
                collectShaderOptions(shaderPack, csh, mapOptions);
                collectShaderOptions(shaderPack, vsh, mapOptions);
                collectShaderOptions(shaderPack, gsh, mapOptions);
                collectShaderOptions(shaderPack, fsh, mapOptions);
            }
        }
    }
    
    private static void collectShaderOptions(final IShaderPack sp, final String path, final Map<String, ShaderOption> mapOptions) {
        final String[] lines = getLines(sp, path);
        for (int i = 0; i < lines.length; ++i) {
            final String line = lines[i];
            final ShaderOption so = getShaderOption(line, path);
            if (so != null) {
                if (!so.getName().startsWith(ShaderMacros.getPrefixMacro())) {
                    final String key = so.getName();
                    final ShaderOption so2 = mapOptions.get(key);
                    if (so2 != null) {
                        if (!Config.equals(so2.getValueDefault(), so.getValueDefault())) {
                            if (so2.isEnabled()) {
                                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, so.getName()));
                                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Config.arrayToString(so2.getPaths()), so2.getValueDefault()));
                                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, Config.arrayToString(so.getPaths()), so.getValueDefault()));
                            }
                            so2.setEnabled(false);
                        }
                        if (so2.getDescription() == null || so2.getDescription().length() <= 0) {
                            so2.setDescription(so.getDescription());
                        }
                        so2.addPaths(so.getPaths());
                    }
                    else if (!so.checkUsed() || isOptionUsed(so, lines)) {
                        mapOptions.put(key, so);
                    }
                }
            }
        }
    }
    
    private static boolean isOptionUsed(final ShaderOption so, final String[] lines) {
        for (int i = 0; i < lines.length; ++i) {
            final String line = lines[i];
            if (so.isUsedInLine(line)) {
                return true;
            }
        }
        return false;
    }
    
    private static String[] getLines(final IShaderPack sp, final String path) {
        try {
            final List<String> listFiles = new ArrayList<String>();
            final LineBuffer lb = loadFile(path, sp, 0, listFiles, 0);
            if (lb == null) {
                return new String[0];
            }
            return lb.getLines();
        }
        catch (IOException e) {
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
            return new String[0];
        }
    }
    
    private static ShaderOption getShaderOption(final String line, final String path) {
        ShaderOption so = null;
        if (so == null) {
            so = ShaderOptionSwitch.parseOption(line, path);
        }
        if (so == null) {
            so = ShaderOptionVariable.parseOption(line, path);
        }
        if (so != null) {
            return so;
        }
        if (so == null) {
            so = ShaderOptionSwitchConst.parseOption(line, path);
        }
        if (so == null) {
            so = ShaderOptionVariableConst.parseOption(line, path);
        }
        if (so != null && ShaderPackParser.setConstNames.contains(so.getName())) {
            return so;
        }
        return null;
    }
    
    private static Set<String> makeSetConstNames() {
        final Set<String> set = new HashSet<String>();
        set.add("shadowMapResolution");
        set.add("shadowMapFov");
        set.add("shadowDistance");
        set.add("shadowDistanceRenderMul");
        set.add("shadowIntervalSize");
        set.add("generateShadowMipmap");
        set.add("generateShadowColorMipmap");
        set.add("shadowHardwareFiltering");
        set.add("shadowHardwareFiltering0");
        set.add("shadowHardwareFiltering1");
        set.add("shadowtex0Mipmap");
        set.add("shadowtexMipmap");
        set.add("shadowtex1Mipmap");
        set.add("shadowcolor0Mipmap");
        set.add("shadowColor0Mipmap");
        set.add("shadowcolor1Mipmap");
        set.add("shadowColor1Mipmap");
        set.add("shadowtex0Nearest");
        set.add("shadowtexNearest");
        set.add("shadow0MinMagNearest");
        set.add("shadowtex1Nearest");
        set.add("shadow1MinMagNearest");
        set.add("shadowcolor0Nearest");
        set.add("shadowColor0Nearest");
        set.add("shadowColor0MinMagNearest");
        set.add("shadowcolor1Nearest");
        set.add("shadowColor1Nearest");
        set.add("shadowColor1MinMagNearest");
        set.add("wetnessHalflife");
        set.add("drynessHalflife");
        set.add("eyeBrightnessHalflife");
        set.add("centerDepthHalflife");
        set.add("sunPathRotation");
        set.add("ambientOcclusionLevel");
        set.add("superSamplingLevel");
        set.add("noiseTextureResolution");
        return set;
    }
    
    public static ShaderProfile[] parseProfiles(final Properties props, final ShaderOption[] shaderOptions) {
        final String PREFIX_PROFILE = "profile.";
        final List<ShaderProfile> list = new ArrayList<ShaderProfile>();
        final Set keys = props.keySet();
        for (final String key : keys) {
            if (!key.startsWith(PREFIX_PROFILE)) {
                continue;
            }
            final String name = key.substring(PREFIX_PROFILE.length());
            final String val = props.getProperty(key);
            final Set<String> parsedProfiles = new HashSet<String>();
            final ShaderProfile p = parseProfile(name, props, parsedProfiles, shaderOptions);
            if (p == null) {
                continue;
            }
            list.add(p);
        }
        if (list.size() <= 0) {
            return null;
        }
        final ShaderProfile[] profs = list.toArray(new ShaderProfile[list.size()]);
        return profs;
    }
    
    public static Map<String, IExpressionBool> parseProgramConditions(final Properties props, final ShaderOption[] shaderOptions) {
        final String PREFIX_PROGRAM = "program.";
        final Pattern pattern = Pattern.compile("program\\.([^.]+)\\.enabled");
        final Map<String, IExpressionBool> map = new HashMap<String, IExpressionBool>();
        final Set keys = props.keySet();
        for (final String key : keys) {
            final Matcher matcher = pattern.matcher(key);
            if (!matcher.matches()) {
                continue;
            }
            final String name = matcher.group(1);
            final String val = props.getProperty(key).trim();
            final IExpressionBool expr = parseOptionExpression(val, shaderOptions);
            if (expr == null) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, key));
            }
            else {
                map.put(name, expr);
            }
        }
        return map;
    }
    
    private static IExpressionBool parseOptionExpression(final String val, final ShaderOption[] shaderOptions) {
        try {
            final ShaderOptionResolver sor = new ShaderOptionResolver(shaderOptions);
            final ExpressionParser parser = new ExpressionParser(sor);
            final IExpressionBool expr = parser.parseBool(val);
            return expr;
        }
        catch (ParseException e) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
            return null;
        }
    }
    
    public static Set<String> parseOptionSliders(final Properties props, final ShaderOption[] shaderOptions) {
        final Set<String> sliders = new HashSet<String>();
        final String value = props.getProperty("sliders");
        if (value == null) {
            return sliders;
        }
        final String[] names = Config.tokenize(value, " ");
        for (int i = 0; i < names.length; ++i) {
            final String name = names[i];
            final ShaderOption so = ShaderUtils.getShaderOption(name, shaderOptions);
            if (so == null) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            }
            else {
                sliders.add(name);
            }
        }
        return sliders;
    }
    
    private static ShaderProfile parseProfile(final String name, final Properties props, final Set<String> parsedProfiles, final ShaderOption[] shaderOptions) {
        final String PREFIX_PROFILE = "profile.";
        final String key = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, PREFIX_PROFILE, name);
        if (parsedProfiles.contains(key)) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            return null;
        }
        parsedProfiles.add(name);
        final ShaderProfile prof = new ShaderProfile(name);
        final String val = props.getProperty(key);
        final String[] parts = Config.tokenize(val, " ");
        for (int i = 0; i < parts.length; ++i) {
            final String part = parts[i];
            if (part.startsWith(PREFIX_PROFILE)) {
                final String nameParent = part.substring(PREFIX_PROFILE.length());
                final ShaderProfile profParent = parseProfile(nameParent, props, parsedProfiles, shaderOptions);
                if (prof != null) {
                    prof.addOptionValues(profParent);
                    prof.addDisabledPrograms(profParent.getDisabledPrograms());
                }
            }
            else {
                final String[] tokens = Config.tokenize(part, ":=");
                if (tokens.length == 1) {
                    String option = tokens[0];
                    boolean on = true;
                    if (option.startsWith("!")) {
                        on = false;
                        option = option.substring(1);
                    }
                    final String PREFIX_PROGRAM = "program.";
                    if (option.startsWith(PREFIX_PROGRAM)) {
                        final String program = option.substring(PREFIX_PROGRAM.length());
                        if (!Shaders.isProgramPath(program)) {
                            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, program, prof.getName()));
                        }
                        else if (on) {
                            prof.removeDisabledProgram(program);
                        }
                        else {
                            prof.addDisabledProgram(program);
                        }
                    }
                    else {
                        final ShaderOption so = ShaderUtils.getShaderOption(option, shaderOptions);
                        if (!(so instanceof ShaderOptionSwitch)) {
                            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, option));
                        }
                        else {
                            prof.addOptionValue(option, String.valueOf(on));
                            so.setVisible(true);
                        }
                    }
                }
                else if (tokens.length != 2) {
                    Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, part));
                }
                else {
                    final String option = tokens[0];
                    final String value = tokens[1];
                    final ShaderOption so2 = ShaderUtils.getShaderOption(option, shaderOptions);
                    if (so2 == null) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, part));
                    }
                    else if (!so2.isValidValue(value)) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, part));
                    }
                    else {
                        so2.setVisible(true);
                        prof.addOptionValue(option, value);
                    }
                }
            }
        }
        return prof;
    }
    
    public static Map<String, ScreenShaderOptions> parseGuiScreens(final Properties props, final ShaderProfile[] shaderProfiles, final ShaderOption[] shaderOptions) {
        final Map<String, ScreenShaderOptions> map = new HashMap<String, ScreenShaderOptions>();
        parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
        if (map.isEmpty()) {
            return null;
        }
        return map;
    }
    
    private static boolean parseGuiScreen(final String key, final Properties props, final Map<String, ScreenShaderOptions> map, final ShaderProfile[] shaderProfiles, final ShaderOption[] shaderOptions) {
        final String val = props.getProperty(key);
        if (val == null) {
            return false;
        }
        final String keyParent = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, key);
        if (map.containsKey(keyParent)) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, val));
            return false;
        }
        final List<ShaderOption> list = new ArrayList<ShaderOption>();
        final Set<String> setNames = new HashSet<String>();
        final String[] opNames = Config.tokenize(val, " ");
        for (int i = 0; i < opNames.length; ++i) {
            final String opName = opNames[i];
            if (opName.equals("<empty>")) {
                list.add(null);
            }
            else if (setNames.contains(opName)) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, opName, key));
            }
            else {
                setNames.add(opName);
                if (opName.equals("<profile>")) {
                    if (shaderProfiles == null) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, opName, key));
                    }
                    else {
                        final ShaderOptionProfile optionProfile = new ShaderOptionProfile(shaderProfiles, shaderOptions);
                        list.add(optionProfile);
                    }
                }
                else if (opName.equals("*")) {
                    final ShaderOption soRest = new ShaderOptionRest("<rest>");
                    list.add(soRest);
                }
                else if (opName.startsWith("[") && opName.endsWith("]")) {
                    final String screen = StrUtils.removePrefixSuffix(opName, "[", "]");
                    if (!screen.matches("^[a-zA-Z0-9_]+$")) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, opName, key));
                    }
                    else {
                        map.put(keyParent, null);
                        final boolean parseScreen = parseGuiScreen(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, screen), props, map, shaderProfiles, shaderOptions);
                        map.remove(keyParent);
                        if (!parseScreen) {
                            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, opName, key));
                        }
                        else {
                            final ShaderOptionScreen optionScreen = new ShaderOptionScreen(screen);
                            list.add(optionScreen);
                        }
                    }
                }
                else {
                    final ShaderOption so = ShaderUtils.getShaderOption(opName, shaderOptions);
                    if (so == null) {
                        Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, opName, key));
                        list.add(null);
                    }
                    else {
                        so.setVisible(true);
                        list.add(so);
                    }
                }
            }
        }
        final ShaderOption[] scrOps = list.toArray(new ShaderOption[list.size()]);
        final String colStr = props.getProperty(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, key));
        final int columns = Config.parseInt(colStr, 2);
        final ScreenShaderOptions sso = new ScreenShaderOptions(key, scrOps, columns);
        map.put(key, sso);
        return true;
    }
    
    public static LineBuffer loadShader(final Program program, final ShaderType shaderType, final InputStream is, final String filePath, final IShaderPack shaderPack, final List<String> listFiles, final ShaderOption[] activeOptions) throws IOException {
        LineBuffer reader = LineBuffer.readAll(new InputStreamReader(is));
        reader = resolveIncludes(reader, filePath, shaderPack, 0, listFiles, 0);
        reader = addMacros(reader, 0);
        reader = remapTextureUnits(reader);
        LineBuffer writer = new LineBuffer();
        for (String line : reader) {
            line = applyOptions(line, activeOptions);
            writer.add(line);
        }
        writer = ShadersCompatibility.remap(program, shaderType, writer);
        return writer;
    }
    
    private static String applyOptions(String line, final ShaderOption[] ops) {
        if (ops == null || ops.length <= 0) {
            return line;
        }
        for (int i = 0; i < ops.length; ++i) {
            final ShaderOption op = ops[i];
            if (op.matchesLine(line)) {
                line = op.getSourceLine();
                break;
            }
        }
        return line;
    }
    
    public static LineBuffer resolveIncludes(final LineBuffer reader, final String filePath, final IShaderPack shaderPack, final int fileIndex, final List<String> listFiles, final int includeLevel) throws IOException {
        String fileDir = "/";
        final int pos = filePath.lastIndexOf("/");
        if (pos >= 0) {
            fileDir = filePath.substring(0, pos);
        }
        final LineBuffer writer = new LineBuffer();
        int lineNumber = 0;
        for (final String line : reader) {
            ++lineNumber;
            final Matcher mi = ShaderPackParser.PATTERN_INCLUDE.matcher(line);
            if (mi.matches()) {
                final String fileInc = mi.group(1);
                final boolean absolute = fileInc.startsWith("/");
                final String filePathInc = absolute ? invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, fileInc) : invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, fileDir, fileInc);
                if (!listFiles.contains(filePathInc)) {
                    listFiles.add(filePathInc);
                }
                final int includeFileIndex = listFiles.indexOf(filePathInc) + 1;
                final LineBuffer lbInc = loadFile(filePathInc, shaderPack, includeFileIndex, listFiles, includeLevel);
                if (lbInc == null) {
                    throw new IOException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, filePath));
                }
                if (lbInc.indexMatch(ShaderPackParser.PATTERN_VERSION) < 0) {
                    writer.add(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, includeFileIndex));
                }
                writer.add(lbInc.getLines());
                writer.add(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, lineNumber + 1, fileIndex));
            }
            else {
                writer.add(line);
            }
        }
        return writer;
    }
    
    public static LineBuffer addMacros(final LineBuffer reader, final int fileIndex) throws IOException {
        final LineBuffer writer = new LineBuffer(reader.getLines());
        int macroInsertPosition = writer.indexMatch(ShaderPackParser.PATTERN_VERSION);
        if (macroInsertPosition < 0) {
            Config.warn("Macro insert position not found");
            return reader;
        }
        final String lineMacro = invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, ++macroInsertPosition + 1, fileIndex);
        final String[] headerMacros = ShaderMacros.getHeaderMacroLines();
        writer.insert(macroInsertPosition, headerMacros);
        macroInsertPosition += headerMacros.length;
        final ShaderMacro[] customMacros = getCustomMacros(writer, macroInsertPosition);
        if (customMacros.length > 0) {
            final LineBuffer lb = new LineBuffer();
            for (int i = 0; i < customMacros.length; ++i) {
                final ShaderMacro macro = customMacros[i];
                lb.add(macro.getSourceLine());
            }
            writer.insert(macroInsertPosition, lb.getLines());
            macroInsertPosition += lb.size();
        }
        writer.insert(macroInsertPosition, lineMacro);
        return writer;
    }
    
    private static ShaderMacro[] getCustomMacros(final LineBuffer lines, final int startPos) {
        final Set<ShaderMacro> setMacros = new LinkedHashSet<ShaderMacro>();
        for (int i = startPos; i < lines.size(); ++i) {
            final String line = lines.get(i);
            if (line.contains(ShaderMacros.getPrefixMacro())) {
                final ShaderMacro[] lineExts = findMacros(line, ShaderMacros.getExtensions());
                setMacros.addAll(Arrays.asList(lineExts));
                final ShaderMacro[] lineConsts = findMacros(line, ShaderMacros.getConstantMacros());
                setMacros.addAll(Arrays.asList(lineConsts));
            }
        }
        final ShaderMacro[] macros = setMacros.toArray(new ShaderMacro[setMacros.size()]);
        return macros;
    }
    
    public static LineBuffer remapTextureUnits(final LineBuffer reader) throws IOException {
        if (!Shaders.isRemapLightmap()) {
            return reader;
        }
        final LineBuffer writer = new LineBuffer();
        for (String line : reader) {
            String lineNew = line.replace("gl_TextureMatrix[1]", "gl_TextureMatrix[2]");
            lineNew = lineNew.replace("gl_MultiTexCoord1", "gl_MultiTexCoord2");
            if (!lineNew.equals(line)) {
                lineNew = (line = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, lineNew));
            }
            writer.add(line);
        }
        return writer;
    }
    
    private static ShaderMacro[] findMacros(final String line, final ShaderMacro[] macros) {
        final List<ShaderMacro> list = new ArrayList<ShaderMacro>();
        for (int i = 0; i < macros.length; ++i) {
            final ShaderMacro ext = macros[i];
            if (line.contains(ext.getName())) {
                list.add(ext);
            }
        }
        final ShaderMacro[] exts = list.toArray(new ShaderMacro[list.size()]);
        return exts;
    }
    
    private static LineBuffer loadFile(final String filePath, final IShaderPack shaderPack, final int fileIndex, final List<String> listFiles, int includeLevel) throws IOException {
        if (includeLevel >= 10) {
            throw new IOException(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, includeLevel, filePath));
        }
        ++includeLevel;
        final InputStream in = shaderPack.getResourceAsStream(filePath);
        if (in == null) {
            return null;
        }
        final InputStreamReader isr = new InputStreamReader(in, "ASCII");
        LineBuffer br = LineBuffer.readAll(isr);
        br = resolveIncludes(br, filePath, shaderPack, fileIndex, listFiles, includeLevel);
        return br;
    }
    
    public static CustomUniforms parseCustomUniforms(final Properties props) {
        final String UNIFORM = "uniform";
        final String VARIABLE = "variable";
        final String PREFIX_UNIFORM = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, UNIFORM);
        final String PREFIX_VARIABLE = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, VARIABLE);
        final Map<String, IExpression> mapExpressions = new HashMap<String, IExpression>();
        final List<CustomUniform> listUniforms = new ArrayList<CustomUniform>();
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String[] keyParts = Config.tokenize(key, ".");
            if (keyParts.length != 3) {
                continue;
            }
            final String kind = keyParts[0];
            final String type = keyParts[1];
            final String name = keyParts[2];
            final String src = props.getProperty(key).trim();
            if (mapExpressions.containsKey(name)) {
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name));
            }
            else {
                if (!kind.equals(UNIFORM) && !kind.equals(VARIABLE)) {
                    continue;
                }
                SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, kind, name));
                final CustomUniform cu = parseCustomUniform(kind, name, type, src, mapExpressions);
                if (cu == null) {
                    continue;
                }
                mapExpressions.put(name, cu.getExpression());
                if (kind.equals(VARIABLE)) {
                    continue;
                }
                listUniforms.add(cu);
            }
        }
        if (listUniforms.size() <= 0) {
            return null;
        }
        final CustomUniform[] cusArr = listUniforms.toArray(new CustomUniform[listUniforms.size()]);
        final CustomUniforms cus = new CustomUniforms(cusArr, mapExpressions);
        return cus;
    }
    
    private static CustomUniform parseCustomUniform(final String kind, final String name, final String type, final String src, final Map<String, IExpression> mapExpressions) {
        try {
            final UniformType uniformType = UniformType.parse(type);
            if (uniformType == null) {
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Lnet/optifine/shaders/uniform/UniformType;)Ljava/lang/String;, kind, uniformType));
                return null;
            }
            final ShaderExpressionResolver resolver = new ShaderExpressionResolver(mapExpressions);
            final ExpressionParser parser = new ExpressionParser(resolver);
            IExpression expr = parser.parse(src);
            final ExpressionType expressionType = expr.getExpressionType();
            if (!uniformType.matchesExpressionType(expressionType)) {
                SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Lnet/optifine/expr/ExpressionType;Ljava/lang/String;Lnet/optifine/shaders/uniform/UniformType;Ljava/lang/String;)Ljava/lang/String;, kind, expressionType, kind, uniformType, name));
                return null;
            }
            expr = makeExpressionCached(expr);
            final CustomUniform cu = new CustomUniform(name, uniformType, expr);
            return cu;
        }
        catch (ParseException e) {
            SMCLog.warning(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, e.getClass().getName(), e.getMessage()));
            return null;
        }
    }
    
    private static IExpression makeExpressionCached(final IExpression expr) {
        if (expr instanceof IExpressionFloat) {
            return new ExpressionFloatCached((IExpressionFloat)expr);
        }
        if (expr instanceof IExpressionFloatArray) {
            return new ExpressionFloatArrayCached((IExpressionFloatArray)expr);
        }
        return expr;
    }
    
    public static void parseAlphaStates(final Properties props) {
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String[] keyParts = Config.tokenize(key, ".");
            if (keyParts.length != 2) {
                continue;
            }
            final String type = keyParts[0];
            final String programName = keyParts[1];
            if (!type.equals("alphaTest")) {
                continue;
            }
            final Program program = Shaders.getProgram(programName);
            if (program == null) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programName));
            }
            else {
                final String val = props.getProperty(key).trim();
                final GlAlphaState state = parseAlphaState(val);
                if (state == null) {
                    continue;
                }
                program.setAlphaState(state);
            }
        }
    }
    
    public static GlAlphaState parseAlphaState(final String str) {
        if (str == null) {
            return null;
        }
        final String[] parts = Config.tokenize(str, " ");
        if (parts.length == 1) {
            final String str2 = parts[0];
            if (str2.equals("off") || str2.equals("false")) {
                return new GlAlphaState(false);
            }
        }
        else if (parts.length == 2) {
            final String str2 = parts[0];
            final String str3 = parts[1];
            final Integer func = ShaderPackParser.mapAlphaFuncs.get(str2);
            final float ref = Config.parseFloat(str3, -1.0f);
            if (func != null && ref >= 0.0f) {
                return new GlAlphaState(true, func, ref);
            }
        }
        SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return null;
    }
    
    public static void parseBlendStates(final Properties props) {
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String[] keyParts = Config.tokenize(key, ".");
            if (keyParts.length >= 2) {
                if (keyParts.length > 3) {
                    continue;
                }
                final String type = keyParts[0];
                final String programName = keyParts[1];
                final String bufferName = (keyParts.length == 3) ? keyParts[2] : null;
                if (!type.equals("blend")) {
                    continue;
                }
                final Program program = Shaders.getProgram(programName);
                if (program == null) {
                    SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programName));
                }
                else {
                    final String val = props.getProperty(key).trim();
                    final GlBlendState state = parseBlendState(val);
                    if (state == null) {
                        continue;
                    }
                    if (bufferName != null) {
                        final int index = program.getProgramStage().isAnyShadow() ? ShaderParser.getShadowColorIndex(bufferName) : Shaders.getBufferIndex(bufferName);
                        final int maxColorIndex = program.getProgramStage().isAnyShadow() ? 2 : 16;
                        if (index < 0 || index >= maxColorIndex) {
                            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, bufferName));
                        }
                        else {
                            program.setBlendStateColorIndexed(index, state);
                            SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, programName, bufferName, val));
                        }
                    }
                    else {
                        program.setBlendState(state);
                    }
                }
            }
        }
    }
    
    public static GlBlendState parseBlendState(final String str) {
        if (str == null) {
            return null;
        }
        final String[] parts = Config.tokenize(str, " ");
        if (parts.length == 1) {
            final String str2 = parts[0];
            if (str2.equals("off") || str2.equals("false")) {
                return new GlBlendState(false);
            }
        }
        else if (parts.length == 2 || parts.length == 4) {
            final String str2 = parts[0];
            final String str3 = parts[1];
            String str4 = str2;
            String str5 = str3;
            if (parts.length == 4) {
                str4 = parts[2];
                str5 = parts[3];
            }
            final Integer src = ShaderPackParser.mapBlendFactors.get(str2);
            final Integer dst = ShaderPackParser.mapBlendFactors.get(str3);
            final Integer srcAlpha = ShaderPackParser.mapBlendFactors.get(str4);
            final Integer dstAlpha = ShaderPackParser.mapBlendFactors.get(str5);
            if (src != null && dst != null && srcAlpha != null && dstAlpha != null) {
                return new GlBlendState(true, src, dst, srcAlpha, dstAlpha);
            }
        }
        SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
        return null;
    }
    
    public static void parseRenderScales(final Properties props) {
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String[] keyParts = Config.tokenize(key, ".");
            if (keyParts.length != 2) {
                continue;
            }
            final String type = keyParts[0];
            final String programName = keyParts[1];
            if (!type.equals("scale")) {
                continue;
            }
            final Program program = Shaders.getProgram(programName);
            if (program == null) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programName));
            }
            else {
                final String val = props.getProperty(key).trim();
                final RenderScale scale = parseRenderScale(val);
                if (scale == null) {
                    continue;
                }
                program.setRenderScale(scale);
            }
        }
    }
    
    private static RenderScale parseRenderScale(final String str) {
        if (str == null) {
            return null;
        }
        final String[] parts = Config.tokenize(str, " ");
        final float scale = Config.parseFloat(parts[0], -1.0f);
        float offsetX = 0.0f;
        float offsetY = 0.0f;
        if (parts.length > 1) {
            if (parts.length != 3) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
                return null;
            }
            offsetX = Config.parseFloat(parts[1], -1.0f);
            offsetY = Config.parseFloat(parts[2], -1.0f);
        }
        if (!Config.between(scale, 0.0f, 1.0f) || !Config.between(offsetX, 0.0f, 1.0f) || !Config.between(offsetY, 0.0f, 1.0f)) {
            SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            return null;
        }
        return new RenderScale(scale, offsetX, offsetY);
    }
    
    public static void parseBuffersFlip(final Properties props) {
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String[] keyParts = Config.tokenize(key, ".");
            if (keyParts.length != 3) {
                continue;
            }
            final String type = keyParts[0];
            final String programName = keyParts[1];
            final String bufferName = keyParts[2];
            if (!type.equals("flip")) {
                continue;
            }
            final Program program = Shaders.getProgram(programName);
            if (program == null) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, programName));
            }
            else {
                final Boolean[] buffersFlip = program.getBuffersFlip();
                final int buffer = Shaders.getBufferIndex(bufferName);
                if (buffer < 0 || buffer >= buffersFlip.length) {
                    SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, bufferName));
                }
                else {
                    final String valStr = props.getProperty(key).trim();
                    final Boolean val = Config.parseBoolean(valStr, null);
                    if (val == null) {
                        SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, valStr));
                    }
                    else {
                        buffersFlip[buffer] = val;
                    }
                }
            }
        }
    }
    
    private static Map<String, Integer> makeMapAlphaFuncs() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("NEVER", new Integer(512));
        map.put("LESS", new Integer(513));
        map.put("EQUAL", new Integer(514));
        map.put("LEQUAL", new Integer(515));
        map.put("GREATER", new Integer(516));
        map.put("NOTEQUAL", new Integer(517));
        map.put("GEQUAL", new Integer(518));
        map.put("ALWAYS", new Integer(519));
        return Collections.unmodifiableMap((Map<? extends String, ? extends Integer>)map);
    }
    
    private static Map<String, Integer> makeMapBlendFactors() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("ZERO", new Integer(0));
        map.put("ONE", new Integer(1));
        map.put("SRC_COLOR", new Integer(768));
        map.put("ONE_MINUS_SRC_COLOR", new Integer(769));
        map.put("DST_COLOR", new Integer(774));
        map.put("ONE_MINUS_DST_COLOR", new Integer(775));
        map.put("SRC_ALPHA", new Integer(770));
        map.put("ONE_MINUS_SRC_ALPHA", new Integer(771));
        map.put("DST_ALPHA", new Integer(772));
        map.put("ONE_MINUS_DST_ALPHA", new Integer(773));
        map.put("CONSTANT_COLOR", new Integer(32769));
        map.put("ONE_MINUS_CONSTANT_COLOR", new Integer(32770));
        map.put("CONSTANT_ALPHA", new Integer(32771));
        map.put("ONE_MINUS_CONSTANT_ALPHA", new Integer(32772));
        map.put("SRC_ALPHA_SATURATE", new Integer(776));
        return Collections.unmodifiableMap((Map<? extends String, ? extends Integer>)map);
    }
    
    public static DynamicDimension[] parseBufferSizes(final Properties props, final int countBuffers) {
        final DynamicDimension[] bufferSizes = new DynamicDimension[countBuffers];
        final Set keys = props.keySet();
        for (final String key : keys) {
            if (!key.startsWith("size.buffer.")) {
                continue;
            }
            final String[] keyParts = Config.tokenize(key, ".");
            if (keyParts.length != 3) {
                continue;
            }
            final String bufferName = keyParts[2];
            final int buffer = Shaders.getBufferIndex(bufferName);
            if (buffer < 0 || buffer >= bufferSizes.length) {
                SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, key));
            }
            else {
                final String val = props.getProperty(key).trim();
                final DynamicDimension dim = parseDynamicDimension(val);
                if (dim == null) {
                    SMCLog.severe(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, key, val));
                }
                else {
                    bufferSizes[buffer] = dim;
                    if (dim.isRelative()) {
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;FF)Ljava/lang/String;, bufferName, dim.getWidth(), dim.getHeight()));
                    }
                    else {
                        SMCLog.info(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;II)Ljava/lang/String;, bufferName, (int)dim.getWidth(), (int)dim.getHeight()));
                    }
                }
            }
        }
        return bufferSizes;
    }
    
    private static DynamicDimension parseDynamicDimension(final String str) {
        if (str == null) {
            return null;
        }
        final String[] parts = Config.tokenize(str, " ");
        if (parts.length != 2) {
            return null;
        }
        final int width = Config.parseInt(parts[0], -1);
        final int height = Config.parseInt(parts[1], -1);
        if (width >= 0 && height >= 0) {
            return new DynamicDimension(false, (float)width, (float)height);
        }
        final float widthRel = Config.parseFloat(parts[0], -1.0f);
        final float heightRel = Config.parseFloat(parts[1], -1.0f);
        if (widthRel >= 0.0f && heightRel >= 0.0f) {
            return new DynamicDimension(true, widthRel, heightRel);
        }
        return null;
    }
    
    static {
        PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+(\\d+).*$");
        PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
        setConstNames = makeSetConstNames();
        mapAlphaFuncs = makeMapAlphaFuncs();
        mapBlendFactors = makeMapBlendFactors();
    }
}
