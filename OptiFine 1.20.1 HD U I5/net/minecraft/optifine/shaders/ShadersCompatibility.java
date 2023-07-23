// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.shaders.config.ShaderPackParser;
import java.util.Collection;
import java.util.Arrays;
import java.util.Comparator;
import net.optifine.Config;
import net.optifine.shaders.config.HeaderLineText;
import java.util.Iterator;
import java.util.Set;
import net.optifine.util.StrUtils;
import java.util.regex.Matcher;
import net.optifine.shaders.config.HeaderLineFunction;
import net.optifine.shaders.config.HeaderLineVariable;
import net.optifine.util.LineBuffer;
import net.optifine.shaders.config.ShaderType;
import net.optifine.shaders.config.HeaderLine;
import java.util.regex.Pattern;

public class ShadersCompatibility
{
    public static Pattern PATTERN_UNIFORM;
    public static Pattern PATTERN_IN;
    public static Pattern PATTERN_OUT;
    public static Pattern PATTERN_VARYING;
    public static Pattern PATTERN_CONST;
    public static Pattern PATTERN_FUNCTION;
    public static HeaderLine MODEL_VIEW_MATRIX;
    public static HeaderLine MODEL_VIEW_MATRIX_INVERSE;
    public static HeaderLine PROJECTION_MATRIX;
    public static HeaderLine PROJECTION_MATRIX_INVERSE;
    public static HeaderLine TEXTURE_MATRIX;
    public static HeaderLine NORMAL_MATRIX;
    public static HeaderLine CHUNK_OFFSET;
    public static HeaderLine ALPHA_TEST_REF;
    public static HeaderLine TEXTURE_MATRIX_2;
    public static HeaderLine FTRANSORM_BASIC;
    public static HeaderLine FOG_DENSITY;
    public static HeaderLine FOG_START;
    public static HeaderLine FOG_END;
    public static HeaderLine FOG_COLOR;
    public static HeaderLine VIEW_WIDTH;
    public static HeaderLine VIEW_HEIGHT;
    public static HeaderLine RENDER_STAGE;
    public static HeaderLine FOG_FRAG_COORD_OUT;
    public static HeaderLine FOG_FRAG_COORD_IN;
    public static HeaderLine FRONT_COLOR_OUT;
    public static HeaderLine FRONT_COLOR_IN;
    public static HeaderLine POSITION;
    public static HeaderLine COLOR;
    public static HeaderLine UV0;
    public static HeaderLine UV1;
    public static HeaderLine UV2;
    public static HeaderLine NORMAL;
    private static final Pattern PATTERN_VERSION;
    public static final Pattern PATTERN_EXTENSION;
    public static final Pattern PATTERN_LINE;
    private static final Pattern PATTERN_TEXTURE2D_TEXCOORD;
    private static final Pattern PATTERN_FRAG_DATA_SET;
    private static final Pattern PATTERN_FRAG_DATA_GET;
    private static final Pattern PATTERN_FRAG_DATA;
    private static final String COMMENT_COMPATIBILITY = "// Compatibility (auto-generated)";
    
    public static LineBuffer remap(final Program program, final ShaderType shaderType, final LineBuffer lines) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aload_2         /* lines */
        //     5: areturn        
        //     6: bipush          120
        //     8: istore_3        /* version */
        //     9: new             Lnet/optifine/util/LineBuffer;
        //    12: dup            
        //    13: invokespecial   net/optifine/util/LineBuffer.<init>:()V
        //    16: astore          writer
        //    18: new             Ljava/util/LinkedHashSet;
        //    21: dup            
        //    22: invokespecial   java/util/LinkedHashSet.<init>:()V
        //    25: astore          headerLines
        //    27: aload_2         /* lines */
        //    28: invokevirtual   net/optifine/util/LineBuffer.iterator:()Ljava/util/Iterator;
        //    31: astore          6
        //    33: aload           6
        //    35: invokeinterface java/util/Iterator.hasNext:()Z
        //    40: ifeq            1749
        //    43: aload           6
        //    45: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    50: checkcast       Ljava/lang/String;
        //    53: astore          line
        //    55: aload           line
        //    57: ldc             "// Compatibility (auto-generated)"
        //    59: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    62: ifeq            67
        //    65: aload_2         /* lines */
        //    66: areturn        
        //    67: aload           line
        //    69: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //    72: ldc             "//"
        //    74: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //    77: ifeq            90
        //    80: aload           writer
        //    82: aload           line
        //    84: invokevirtual   net/optifine/util/LineBuffer.add:(Ljava/lang/String;)V
        //    87: goto            33
        //    90: aload           line
        //    92: getstatic       net/optifine/shaders/ShadersCompatibility.PATTERN_VERSION:Ljava/util/regex/Pattern;
        //    95: invokestatic    net/optifine/shaders/ShadersCompatibility.matches:(Ljava/lang/String;Ljava/util/regex/Pattern;)Z
        //    98: ifeq            197
        //   101: iload_3         /* version */
        //   102: aload           line
        //   104: iload_3         /* version */
        //   105: invokestatic    net/optifine/shaders/ShadersCompatibility.getVersion:(Ljava/lang/String;I)I
        //   108: invokestatic    java/lang/Math.max:(II)I
        //   111: istore_3        /* version */
        //   112: aload           line
        //   114: ldc             "#version 110"
        //   116: ldc             "#version 150"
        //   118: aload           headerLines
        //   120: iconst_0       
        //   121: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   124: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   127: astore          line
        //   129: aload           line
        //   131: ldc             "#version 120"
        //   133: ldc             "#version 150"
        //   135: aload           headerLines
        //   137: iconst_0       
        //   138: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   141: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   144: astore          line
        //   146: aload           line
        //   148: ldc             "#version 130"
        //   150: ldc             "#version 150"
        //   152: aload           headerLines
        //   154: iconst_0       
        //   155: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   158: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   161: astore          line
        //   163: aload           line
        //   165: ldc             "#version 140"
        //   167: ldc             "#version 150"
        //   169: aload           headerLines
        //   171: iconst_0       
        //   172: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   175: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   178: astore          line
        //   180: aload           line
        //   182: ldc             "compatibility"
        //   184: ldc             ""
        //   186: aload           headerLines
        //   188: iconst_0       
        //   189: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   192: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   195: astore          line
        //   197: aload_1         /* shaderType */
        //   198: getstatic       net/optifine/shaders/config/ShaderType.VERTEX:Lnet/optifine/shaders/config/ShaderType;
        //   201: if_acmpne       592
        //   204: aload_0         /* program */
        //   205: getstatic       net/optifine/shaders/Shaders.ProgramBasic:Lnet/optifine/shaders/Program;
        //   208: if_acmpne       319
        //   211: aload           line
        //   213: ldc             "(\\W)gl_ProjectionMatrix\\s*\\*\\s*gl_ModelViewMatrix\\s*\\*\\s*gl_Vertex(\\W)"
        //   215: invokestatic    java/util/regex/Pattern.compile:(Ljava/lang/String;)Ljava/util/regex/Pattern;
        //   218: ldc             "$1ftransform()$2"
        //   220: aload           headerLines
        //   222: iconst_0       
        //   223: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   226: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   229: astore          line
        //   231: aload           line
        //   233: ldc             "(\\W)gl_ModelViewProjectionMatrix\\s*\\*\\s*gl_Vertex(\\W)"
        //   235: invokestatic    java/util/regex/Pattern.compile:(Ljava/lang/String;)Ljava/util/regex/Pattern;
        //   238: ldc             "$1ftransform()$2"
        //   240: aload           headerLines
        //   242: iconst_0       
        //   243: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   246: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   249: astore          line
        //   251: aload           line
        //   253: ldc             "ftransform()"
        //   255: ldc             "ftransformBasic()"
        //   257: aload           headerLines
        //   259: bipush          8
        //   261: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   264: dup            
        //   265: iconst_0       
        //   266: getstatic       net/optifine/shaders/ShadersCompatibility.RENDER_STAGE:Lnet/optifine/shaders/config/HeaderLine;
        //   269: aastore        
        //   270: dup            
        //   271: iconst_1       
        //   272: getstatic       net/optifine/shaders/ShadersCompatibility.VIEW_WIDTH:Lnet/optifine/shaders/config/HeaderLine;
        //   275: aastore        
        //   276: dup            
        //   277: iconst_2       
        //   278: getstatic       net/optifine/shaders/ShadersCompatibility.VIEW_HEIGHT:Lnet/optifine/shaders/config/HeaderLine;
        //   281: aastore        
        //   282: dup            
        //   283: iconst_3       
        //   284: getstatic       net/optifine/shaders/ShadersCompatibility.PROJECTION_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   287: aastore        
        //   288: dup            
        //   289: iconst_4       
        //   290: getstatic       net/optifine/shaders/ShadersCompatibility.MODEL_VIEW_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   293: aastore        
        //   294: dup            
        //   295: iconst_5       
        //   296: getstatic       net/optifine/shaders/ShadersCompatibility.POSITION:Lnet/optifine/shaders/config/HeaderLine;
        //   299: aastore        
        //   300: dup            
        //   301: bipush          6
        //   303: getstatic       net/optifine/shaders/ShadersCompatibility.NORMAL:Lnet/optifine/shaders/config/HeaderLine;
        //   306: aastore        
        //   307: dup            
        //   308: bipush          7
        //   310: getstatic       net/optifine/shaders/ShadersCompatibility.FTRANSORM_BASIC:Lnet/optifine/shaders/config/HeaderLine;
        //   313: aastore        
        //   314: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   317: astore          line
        //   319: aload_0         /* program */
        //   320: invokevirtual   net/optifine/shaders/Program.getProgramStage:()Lnet/optifine/shaders/ProgramStage;
        //   323: invokevirtual   net/optifine/shaders/ProgramStage.isAnyComposite:()Z
        //   326: ifeq            390
        //   329: aload           line
        //   331: ldc             "ftransform()"
        //   333: ldc             "(projectionMatrix * modelViewMatrix * vec4(vaPosition, 1.0))"
        //   335: aload           headerLines
        //   337: iconst_3       
        //   338: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   341: dup            
        //   342: iconst_0       
        //   343: getstatic       net/optifine/shaders/ShadersCompatibility.PROJECTION_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   346: aastore        
        //   347: dup            
        //   348: iconst_1       
        //   349: getstatic       net/optifine/shaders/ShadersCompatibility.MODEL_VIEW_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   352: aastore        
        //   353: dup            
        //   354: iconst_2       
        //   355: getstatic       net/optifine/shaders/ShadersCompatibility.POSITION:Lnet/optifine/shaders/config/HeaderLine;
        //   358: aastore        
        //   359: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   362: astore          line
        //   364: aload           line
        //   366: ldc             "gl_Vertex"
        //   368: ldc             "vec4(vaPosition, 1.0)"
        //   370: aload           headerLines
        //   372: iconst_1       
        //   373: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   376: dup            
        //   377: iconst_0       
        //   378: getstatic       net/optifine/shaders/ShadersCompatibility.POSITION:Lnet/optifine/shaders/config/HeaderLine;
        //   381: aastore        
        //   382: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   385: astore          line
        //   387: goto            460
        //   390: aload           line
        //   392: ldc             "ftransform()"
        //   394: ldc             "(projectionMatrix * modelViewMatrix * vec4(vaPosition + chunkOffset, 1.0))"
        //   396: aload           headerLines
        //   398: iconst_4       
        //   399: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   402: dup            
        //   403: iconst_0       
        //   404: getstatic       net/optifine/shaders/ShadersCompatibility.PROJECTION_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   407: aastore        
        //   408: dup            
        //   409: iconst_1       
        //   410: getstatic       net/optifine/shaders/ShadersCompatibility.MODEL_VIEW_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   413: aastore        
        //   414: dup            
        //   415: iconst_2       
        //   416: getstatic       net/optifine/shaders/ShadersCompatibility.POSITION:Lnet/optifine/shaders/config/HeaderLine;
        //   419: aastore        
        //   420: dup            
        //   421: iconst_3       
        //   422: getstatic       net/optifine/shaders/ShadersCompatibility.CHUNK_OFFSET:Lnet/optifine/shaders/config/HeaderLine;
        //   425: aastore        
        //   426: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   429: astore          line
        //   431: aload           line
        //   433: ldc             "gl_Vertex"
        //   435: ldc             "vec4(vaPosition + chunkOffset, 1.0)"
        //   437: aload           headerLines
        //   439: iconst_2       
        //   440: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   443: dup            
        //   444: iconst_0       
        //   445: getstatic       net/optifine/shaders/ShadersCompatibility.POSITION:Lnet/optifine/shaders/config/HeaderLine;
        //   448: aastore        
        //   449: dup            
        //   450: iconst_1       
        //   451: getstatic       net/optifine/shaders/ShadersCompatibility.CHUNK_OFFSET:Lnet/optifine/shaders/config/HeaderLine;
        //   454: aastore        
        //   455: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   458: astore          line
        //   460: aload           line
        //   462: ldc             "gl_Color"
        //   464: ldc             "vaColor"
        //   466: aload           headerLines
        //   468: iconst_1       
        //   469: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   472: dup            
        //   473: iconst_0       
        //   474: getstatic       net/optifine/shaders/ShadersCompatibility.COLOR:Lnet/optifine/shaders/config/HeaderLine;
        //   477: aastore        
        //   478: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   481: astore          line
        //   483: aload           line
        //   485: ldc             "gl_Normal"
        //   487: ldc             "vaNormal"
        //   489: aload           headerLines
        //   491: iconst_1       
        //   492: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   495: dup            
        //   496: iconst_0       
        //   497: getstatic       net/optifine/shaders/ShadersCompatibility.NORMAL:Lnet/optifine/shaders/config/HeaderLine;
        //   500: aastore        
        //   501: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   504: astore          line
        //   506: aload           line
        //   508: ldc             "gl_MultiTexCoord0"
        //   510: ldc             "vec4(vaUV0, 0.0, 1.0)"
        //   512: aload           headerLines
        //   514: iconst_1       
        //   515: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   518: dup            
        //   519: iconst_0       
        //   520: getstatic       net/optifine/shaders/ShadersCompatibility.UV0:Lnet/optifine/shaders/config/HeaderLine;
        //   523: aastore        
        //   524: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   527: astore          line
        //   529: aload           line
        //   531: ldc             "gl_MultiTexCoord1"
        //   533: ldc             "vec4(vaUV1, 0.0, 1.0)"
        //   535: aload           headerLines
        //   537: iconst_1       
        //   538: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   541: dup            
        //   542: iconst_0       
        //   543: getstatic       net/optifine/shaders/ShadersCompatibility.UV1:Lnet/optifine/shaders/config/HeaderLine;
        //   546: aastore        
        //   547: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   550: astore          line
        //   552: aload           line
        //   554: ldc             "gl_MultiTexCoord2"
        //   556: ldc             "vec4(vaUV2, 0.0, 1.0)"
        //   558: aload           headerLines
        //   560: iconst_1       
        //   561: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   564: dup            
        //   565: iconst_0       
        //   566: getstatic       net/optifine/shaders/ShadersCompatibility.UV2:Lnet/optifine/shaders/config/HeaderLine;
        //   569: aastore        
        //   570: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   573: astore          line
        //   575: aload           line
        //   577: ldc             "gl_MultiTexCoord3"
        //   579: ldc             "vec4(0.0, 0.0, 0.0, 1.0)"
        //   581: aload           headerLines
        //   583: iconst_0       
        //   584: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   587: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   590: astore          line
        //   592: aload           line
        //   594: ldc             "gl_ProjectionMatrix"
        //   596: ldc             "projectionMatrix"
        //   598: aload           headerLines
        //   600: iconst_1       
        //   601: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   604: dup            
        //   605: iconst_0       
        //   606: getstatic       net/optifine/shaders/ShadersCompatibility.PROJECTION_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   609: aastore        
        //   610: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   613: astore          line
        //   615: aload           line
        //   617: ldc             "gl_ProjectionMatrixInverse"
        //   619: ldc             "projectionMatrixInverse"
        //   621: aload           headerLines
        //   623: iconst_1       
        //   624: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   627: dup            
        //   628: iconst_0       
        //   629: getstatic       net/optifine/shaders/ShadersCompatibility.PROJECTION_MATRIX_INVERSE:Lnet/optifine/shaders/config/HeaderLine;
        //   632: aastore        
        //   633: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   636: astore          line
        //   638: aload           line
        //   640: ldc_w           "gl_ModelViewMatrix"
        //   643: ldc_w           "modelViewMatrix"
        //   646: aload           headerLines
        //   648: iconst_1       
        //   649: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   652: dup            
        //   653: iconst_0       
        //   654: getstatic       net/optifine/shaders/ShadersCompatibility.MODEL_VIEW_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   657: aastore        
        //   658: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   661: astore          line
        //   663: aload           line
        //   665: ldc_w           "gl_ModelViewMatrixInverse"
        //   668: ldc_w           "modelViewMatrixInverse"
        //   671: aload           headerLines
        //   673: iconst_1       
        //   674: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   677: dup            
        //   678: iconst_0       
        //   679: getstatic       net/optifine/shaders/ShadersCompatibility.MODEL_VIEW_MATRIX_INVERSE:Lnet/optifine/shaders/config/HeaderLine;
        //   682: aastore        
        //   683: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   686: astore          line
        //   688: aload           line
        //   690: ldc_w           "gl_ModelViewProjectionMatrix"
        //   693: ldc_w           "(projectionMatrix * modelViewMatrix)"
        //   696: aload           headerLines
        //   698: iconst_2       
        //   699: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   702: dup            
        //   703: iconst_0       
        //   704: getstatic       net/optifine/shaders/ShadersCompatibility.PROJECTION_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   707: aastore        
        //   708: dup            
        //   709: iconst_1       
        //   710: getstatic       net/optifine/shaders/ShadersCompatibility.MODEL_VIEW_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   713: aastore        
        //   714: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   717: astore          line
        //   719: aload           line
        //   721: ldc_w           "gl_NormalMatrix"
        //   724: ldc_w           "normalMatrix"
        //   727: aload           headerLines
        //   729: iconst_1       
        //   730: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   733: dup            
        //   734: iconst_0       
        //   735: getstatic       net/optifine/shaders/ShadersCompatibility.NORMAL_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   738: aastore        
        //   739: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   742: astore          line
        //   744: aload_1         /* shaderType */
        //   745: getstatic       net/optifine/shaders/config/ShaderType.VERTEX:Lnet/optifine/shaders/config/ShaderType;
        //   748: if_acmpne       839
        //   751: aload           line
        //   753: ldc_w           "attribute"
        //   756: ldc_w           "in"
        //   759: aload           headerLines
        //   761: iconst_0       
        //   762: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   765: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   768: astore          line
        //   770: aload           line
        //   772: ldc_w           "varying"
        //   775: ldc_w           "out"
        //   778: aload           headerLines
        //   780: iconst_0       
        //   781: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   784: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   787: astore          line
        //   789: aload           line
        //   791: ldc_w           "gl_FogFragCoord"
        //   794: ldc_w           "varFogFragCoord"
        //   797: aload           headerLines
        //   799: iconst_1       
        //   800: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   803: dup            
        //   804: iconst_0       
        //   805: getstatic       net/optifine/shaders/ShadersCompatibility.FOG_FRAG_COORD_OUT:Lnet/optifine/shaders/config/HeaderLine;
        //   808: aastore        
        //   809: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   812: astore          line
        //   814: aload           line
        //   816: ldc_w           "gl_FrontColor"
        //   819: ldc_w           "varFrontColor"
        //   822: aload           headerLines
        //   824: iconst_1       
        //   825: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   828: dup            
        //   829: iconst_0       
        //   830: getstatic       net/optifine/shaders/ShadersCompatibility.FRONT_COLOR_OUT:Lnet/optifine/shaders/config/HeaderLine;
        //   833: aastore        
        //   834: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   837: astore          line
        //   839: aload_1         /* shaderType */
        //   840: getstatic       net/optifine/shaders/config/ShaderType.GEOMETRY:Lnet/optifine/shaders/config/ShaderType;
        //   843: if_acmpne       884
        //   846: aload           line
        //   848: ldc_w           "varying in"
        //   851: ldc_w           "in"
        //   854: aload           headerLines
        //   856: iconst_0       
        //   857: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   860: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   863: astore          line
        //   865: aload           line
        //   867: ldc_w           "varying out"
        //   870: ldc_w           "out"
        //   873: aload           headerLines
        //   875: iconst_0       
        //   876: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   879: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   882: astore          line
        //   884: aload_1         /* shaderType */
        //   885: getstatic       net/optifine/shaders/config/ShaderType.FRAGMENT:Lnet/optifine/shaders/config/ShaderType;
        //   888: if_acmpne       960
        //   891: aload           line
        //   893: ldc_w           "varying"
        //   896: ldc_w           "in"
        //   899: aload           headerLines
        //   901: iconst_0       
        //   902: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   905: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   908: astore          line
        //   910: aload           line
        //   912: ldc_w           "gl_FogFragCoord"
        //   915: ldc_w           "varFogFragCoord"
        //   918: aload           headerLines
        //   920: iconst_1       
        //   921: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   924: dup            
        //   925: iconst_0       
        //   926: getstatic       net/optifine/shaders/ShadersCompatibility.FOG_FRAG_COORD_IN:Lnet/optifine/shaders/config/HeaderLine;
        //   929: aastore        
        //   930: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   933: astore          line
        //   935: aload           line
        //   937: ldc_w           "gl_FrontColor"
        //   940: ldc_w           "varFrontColor"
        //   943: aload           headerLines
        //   945: iconst_1       
        //   946: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   949: dup            
        //   950: iconst_0       
        //   951: getstatic       net/optifine/shaders/ShadersCompatibility.FRONT_COLOR_IN:Lnet/optifine/shaders/config/HeaderLine;
        //   954: aastore        
        //   955: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   958: astore          line
        //   960: aload           line
        //   962: ldc_w           "gl_TextureMatrix[0]"
        //   965: ldc_w           "textureMatrix"
        //   968: aload           headerLines
        //   970: iconst_1       
        //   971: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   974: dup            
        //   975: iconst_0       
        //   976: getstatic       net/optifine/shaders/ShadersCompatibility.TEXTURE_MATRIX:Lnet/optifine/shaders/config/HeaderLine;
        //   979: aastore        
        //   980: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //   983: astore          line
        //   985: aload           line
        //   987: ldc_w           "gl_TextureMatrix[1]"
        //   990: ldc_w           "mat4(1.0)"
        //   993: aload           headerLines
        //   995: iconst_0       
        //   996: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //   999: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1002: astore          line
        //  1004: aload           line
        //  1006: ldc_w           "gl_TextureMatrix[2]"
        //  1009: ldc_w           "TEXTURE_MATRIX_2"
        //  1012: aload           headerLines
        //  1014: iconst_1       
        //  1015: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1018: dup            
        //  1019: iconst_0       
        //  1020: getstatic       net/optifine/shaders/ShadersCompatibility.TEXTURE_MATRIX_2:Lnet/optifine/shaders/config/HeaderLine;
        //  1023: aastore        
        //  1024: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1027: astore          line
        //  1029: aload           line
        //  1031: ldc_w           "gl_Fog.density"
        //  1034: ldc_w           "fogDensity"
        //  1037: aload           headerLines
        //  1039: iconst_1       
        //  1040: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1043: dup            
        //  1044: iconst_0       
        //  1045: getstatic       net/optifine/shaders/ShadersCompatibility.FOG_DENSITY:Lnet/optifine/shaders/config/HeaderLine;
        //  1048: aastore        
        //  1049: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1052: astore          line
        //  1054: aload           line
        //  1056: ldc_w           "gl_Fog.start"
        //  1059: ldc_w           "fogStart"
        //  1062: aload           headerLines
        //  1064: iconst_1       
        //  1065: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1068: dup            
        //  1069: iconst_0       
        //  1070: getstatic       net/optifine/shaders/ShadersCompatibility.FOG_START:Lnet/optifine/shaders/config/HeaderLine;
        //  1073: aastore        
        //  1074: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1077: astore          line
        //  1079: aload           line
        //  1081: ldc_w           "gl_Fog.end"
        //  1084: ldc_w           "fogEnd"
        //  1087: aload           headerLines
        //  1089: iconst_1       
        //  1090: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1093: dup            
        //  1094: iconst_0       
        //  1095: getstatic       net/optifine/shaders/ShadersCompatibility.FOG_END:Lnet/optifine/shaders/config/HeaderLine;
        //  1098: aastore        
        //  1099: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1102: astore          line
        //  1104: aload           line
        //  1106: ldc_w           "gl_Fog.scale"
        //  1109: ldc_w           "(1.0 / (fogEnd - fogStart))"
        //  1112: aload           headerLines
        //  1114: iconst_2       
        //  1115: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1118: dup            
        //  1119: iconst_0       
        //  1120: getstatic       net/optifine/shaders/ShadersCompatibility.FOG_START:Lnet/optifine/shaders/config/HeaderLine;
        //  1123: aastore        
        //  1124: dup            
        //  1125: iconst_1       
        //  1126: getstatic       net/optifine/shaders/ShadersCompatibility.FOG_END:Lnet/optifine/shaders/config/HeaderLine;
        //  1129: aastore        
        //  1130: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1133: astore          line
        //  1135: aload           line
        //  1137: ldc_w           "gl_Fog.color"
        //  1140: ldc_w           "vec4(fogColor, 1.0)"
        //  1143: aload           headerLines
        //  1145: iconst_1       
        //  1146: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1149: dup            
        //  1150: iconst_0       
        //  1151: getstatic       net/optifine/shaders/ShadersCompatibility.FOG_COLOR:Lnet/optifine/shaders/config/HeaderLine;
        //  1154: aastore        
        //  1155: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1158: astore          line
        //  1160: aload_0         /* program */
        //  1161: invokevirtual   net/optifine/shaders/Program.getName:()Ljava/lang/String;
        //  1164: ldc_w           "entities"
        //  1167: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //  1170: ifeq            1192
        //  1173: aload           line
        //  1175: getstatic       net/optifine/shaders/ShadersCompatibility.PATTERN_TEXTURE2D_TEXCOORD:Ljava/util/regex/Pattern;
        //  1178: ldc_w           "$1clamp($4, 0.0, 1.0)$5"
        //  1181: aload           headerLines
        //  1183: iconst_0       
        //  1184: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1187: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1190: astore          line
        //  1192: aload_1         /* shaderType */
        //  1193: getstatic       net/optifine/shaders/config/ShaderType.FRAGMENT:Lnet/optifine/shaders/config/ShaderType;
        //  1196: if_acmpne       1228
        //  1199: aload           line
        //  1201: ldc_w           "gl_FragColor"
        //  1204: ldc_w           "gl_FragData[0]"
        //  1207: aload           headerLines
        //  1209: iconst_0       
        //  1210: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1213: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1216: astore          line
        //  1218: aload_0         /* program */
        //  1219: aload           line
        //  1221: aload           headerLines
        //  1223: invokestatic    net/optifine/shaders/ShadersCompatibility.addAlphaTest:(Lnet/optifine/shaders/Program;Ljava/lang/String;Ljava/util/Set;)Ljava/lang/String;
        //  1226: astore          line
        //  1228: aload           line
        //  1230: ldc_w           "texture"
        //  1233: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //  1236: ifeq            1283
        //  1239: aload           line
        //  1241: ldc_w           "(sampler2D\\s+)texture(\\W)"
        //  1244: invokestatic    java/util/regex/Pattern.compile:(Ljava/lang/String;)Ljava/util/regex/Pattern;
        //  1247: ldc_w           "$1gtexture$2"
        //  1250: aload           headerLines
        //  1252: iconst_0       
        //  1253: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1256: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1259: astore          line
        //  1261: aload           line
        //  1263: ldc_w           "(\\(\\s*)texture(\\s*,)"
        //  1266: invokestatic    java/util/regex/Pattern.compile:(Ljava/lang/String;)Ljava/util/regex/Pattern;
        //  1269: ldc_w           "$1gtexture$2"
        //  1272: aload           headerLines
        //  1274: iconst_0       
        //  1275: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1278: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1281: astore          line
        //  1283: aload           line
        //  1285: ldc_w           "texture2D"
        //  1288: ldc_w           "texture"
        //  1291: aload           headerLines
        //  1293: iconst_0       
        //  1294: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1297: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1300: astore          line
        //  1302: aload           line
        //  1304: ldc_w           "texture2DLod"
        //  1307: ldc_w           "textureLod"
        //  1310: aload           headerLines
        //  1312: iconst_0       
        //  1313: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1316: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1319: astore          line
        //  1321: aload           line
        //  1323: ldc_w           "texture2DGrad"
        //  1326: ldc_w           "textureGrad"
        //  1329: aload           headerLines
        //  1331: iconst_0       
        //  1332: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1335: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1338: astore          line
        //  1340: aload           line
        //  1342: ldc_w           "texture2DGradARB"
        //  1345: ldc_w           "textureGrad"
        //  1348: aload           headerLines
        //  1350: iconst_0       
        //  1351: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1354: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1357: astore          line
        //  1359: aload           line
        //  1361: ldc_w           "texture3D"
        //  1364: ldc_w           "texture"
        //  1367: aload           headerLines
        //  1369: iconst_0       
        //  1370: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1373: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1376: astore          line
        //  1378: aload           line
        //  1380: ldc_w           "texture3DLod"
        //  1383: ldc_w           "textureLod"
        //  1386: aload           headerLines
        //  1388: iconst_0       
        //  1389: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1392: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1395: astore          line
        //  1397: aload           line
        //  1399: ldc_w           "shadow2D"
        //  1402: ldc_w           "texture"
        //  1405: aload           headerLines
        //  1407: invokestatic    net/optifine/shaders/ShadersCompatibility.replaceShadow2D:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;)Ljava/lang/String;
        //  1410: astore          line
        //  1412: aload           line
        //  1414: ldc_w           "shadow2DLod"
        //  1417: ldc_w           "textureLod"
        //  1420: aload           headerLines
        //  1422: invokestatic    net/optifine/shaders/ShadersCompatibility.replaceShadow2D:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;)Ljava/lang/String;
        //  1425: astore          line
        //  1427: aload           line
        //  1429: ldc_w           "texelFetch2D"
        //  1432: ldc_w           "texelFetch"
        //  1435: aload           headerLines
        //  1437: iconst_0       
        //  1438: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1441: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1444: astore          line
        //  1446: aload           line
        //  1448: ldc_w           "texelFetch3D"
        //  1451: ldc_w           "texelFetch"
        //  1454: aload           headerLines
        //  1456: iconst_0       
        //  1457: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1460: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1463: astore          line
        //  1465: aload           line
        //  1467: aload           headerLines
        //  1469: invokestatic    net/optifine/shaders/ShadersCompatibility.replaceFragData:(Ljava/lang/String;Ljava/util/Set;)Ljava/lang/String;
        //  1472: astore          line
        //  1474: iload_3         /* version */
        //  1475: bipush          120
        //  1477: if_icmpgt       1518
        //  1480: aload           line
        //  1482: ldc_w           "common"
        //  1485: ldc_w           "commonX"
        //  1488: aload           headerLines
        //  1490: iconst_0       
        //  1491: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1494: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1497: astore          line
        //  1499: aload           line
        //  1501: ldc_w           "smooth"
        //  1504: ldc_w           "smoothX"
        //  1507: aload           headerLines
        //  1509: iconst_0       
        //  1510: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1513: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1516: astore          line
        //  1518: aload           line
        //  1520: ldc_w           "gl_ModelViewProjectionMatrixInverse"
        //  1523: ldc_w           "gl_ModelViewProjectionMatrixInverse_TODO"
        //  1526: aload           headerLines
        //  1528: iconst_0       
        //  1529: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1532: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1535: astore          line
        //  1537: aload           line
        //  1539: ldc_w           "gl_TextureMatrixInverse"
        //  1542: ldc_w           "gl_TextureMatrixInverse_TODO"
        //  1545: aload           headerLines
        //  1547: iconst_0       
        //  1548: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1551: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1554: astore          line
        //  1556: aload           line
        //  1558: ldc_w           "gl_ModelViewMatrixTranspose"
        //  1561: ldc_w           "gl_ModelViewMatrixTranspose_TODO"
        //  1564: aload           headerLines
        //  1566: iconst_0       
        //  1567: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1570: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1573: astore          line
        //  1575: aload           line
        //  1577: ldc_w           "gl_ProjectionMatrixTranspose"
        //  1580: ldc_w           "gl_ProjectionMatrixTranspose_TODO"
        //  1583: aload           headerLines
        //  1585: iconst_0       
        //  1586: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1589: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1592: astore          line
        //  1594: aload           line
        //  1596: ldc_w           "gl_ModelViewProjectionMatrixTranspose"
        //  1599: ldc_w           "gl_ModelViewProjectionMatrixTranspose_TODO"
        //  1602: aload           headerLines
        //  1604: iconst_0       
        //  1605: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1608: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1611: astore          line
        //  1613: aload           line
        //  1615: ldc_w           "gl_TextureMatrixTranspose"
        //  1618: ldc_w           "gl_TextureMatrixTranspose_TODO"
        //  1621: aload           headerLines
        //  1623: iconst_0       
        //  1624: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1627: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1630: astore          line
        //  1632: aload           line
        //  1634: ldc_w           "gl_ModelViewMatrixInverseTranspose"
        //  1637: ldc_w           "gl_ModelViewMatrixInverseTranspose_TODO"
        //  1640: aload           headerLines
        //  1642: iconst_0       
        //  1643: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1646: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1649: astore          line
        //  1651: aload           line
        //  1653: ldc_w           "gl_ProjectionMatrixInverseTranspose"
        //  1656: ldc_w           "gl_ProjectionMatrixInverseTranspose_TODO"
        //  1659: aload           headerLines
        //  1661: iconst_0       
        //  1662: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1665: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1668: astore          line
        //  1670: aload           line
        //  1672: ldc_w           "gl_ModelViewProjectionMatrixInverseTranspose"
        //  1675: ldc_w           "gl_ModelViewProjectionMatrixInverseTranspose_TODO"
        //  1678: aload           headerLines
        //  1680: iconst_0       
        //  1681: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1684: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1687: astore          line
        //  1689: aload           line
        //  1691: ldc_w           "gl_TextureMatrixInverseTranspose"
        //  1694: ldc_w           "gl_TextureMatrixInverseTranspose_TODO"
        //  1697: aload           headerLines
        //  1699: iconst_0       
        //  1700: anewarray       Lnet/optifine/shaders/config/HeaderLine;
        //  1703: invokestatic    net/optifine/shaders/ShadersCompatibility.replace:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;[Lnet/optifine/shaders/config/HeaderLine;)Ljava/lang/String;
        //  1706: astore          line
        //  1708: aload           line
        //  1710: ldc_w           "\n"
        //  1713: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //  1716: ifeq            1739
        //  1719: aload           line
        //  1721: ldc_w           "\n\r"
        //  1724: invokestatic    net/optifine/Config.tokenize:(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
        //  1727: astore          parts
        //  1729: aload           writer
        //  1731: aload           parts
        //  1733: invokevirtual   net/optifine/util/LineBuffer.add:([Ljava/lang/String;)V
        //  1736: goto            33
        //  1739: aload           writer
        //  1741: aload           line
        //  1743: invokevirtual   net/optifine/util/LineBuffer.add:(Ljava/lang/String;)V
        //  1746: goto            33
        //  1749: aload           headerLines
        //  1751: invokeinterface java/util/Set.isEmpty:()Z
        //  1756: ifeq            1762
        //  1759: aload           writer
        //  1761: areturn        
        //  1762: aload           writer
        //  1764: aload           headerLines
        //  1766: invokestatic    net/optifine/shaders/ShadersCompatibility.removeExisting:(Lnet/optifine/util/LineBuffer;Ljava/util/Set;)Lnet/optifine/util/LineBuffer;
        //  1769: astore          writer
        //  1771: aload           writer
        //  1773: aload           headerLines
        //  1775: invokestatic    net/optifine/shaders/ShadersCompatibility.moveExtensionsToHeader:(Lnet/optifine/util/LineBuffer;Ljava/util/Set;)Lnet/optifine/util/LineBuffer;
        //  1778: astore          writer
        //  1780: aload           headerLines
        //  1782: invokeinterface java/util/Set.stream:()Ljava/util/stream/Stream;
        //  1787: invokedynamic   BootstrapMethod #0, apply:()Ljava/util/function/Function;
        //  1792: invokeinterface java/util/stream/Stream.map:(Ljava/util/function/Function;)Ljava/util/stream/Stream;
        //  1797: invokedynamic   BootstrapMethod #1, apply:()Ljava/util/function/IntFunction;
        //  1802: invokeinterface java/util/stream/Stream.toArray:(Ljava/util/function/IntFunction;)[Ljava/lang/Object;
        //  1807: checkcast       [Ljava/lang/String;
        //  1810: astore          newHeaderLinesArr
        //  1812: aload           newHeaderLinesArr
        //  1814: invokestatic    net/optifine/shaders/ShadersCompatibility.getComparatorHeaderLines:()Ljava/util/Comparator;
        //  1817: invokestatic    java/util/Arrays.sort:([Ljava/lang/Object;Ljava/util/Comparator;)V
        //  1820: aload           newHeaderLinesArr
        //  1822: ldc             "// Compatibility (auto-generated)"
        //  1824: iconst_0       
        //  1825: invokestatic    net/optifine/util/ArrayUtils.addObjectToArray:([Ljava/lang/Object;Ljava/lang/Object;I)[Ljava/lang/Object;
        //  1828: checkcast       [Ljava/lang/String;
        //  1831: astore          newHeaderLinesArr
        //  1833: aload           writer
        //  1835: iload_3         /* version */
        //  1836: invokestatic    net/optifine/shaders/ShadersCompatibility.getIndexInsertHeader:(Lnet/optifine/util/LineBuffer;I)I
        //  1839: istore          indexInsert
        //  1841: iload           indexInsert
        //  1843: iflt            1855
        //  1846: aload           writer
        //  1848: iload           indexInsert
        //  1850: aload           newHeaderLinesArr
        //  1852: invokevirtual   net/optifine/util/LineBuffer.insert:(I[Ljava/lang/String;)V
        //  1855: aload           writer
        //  1857: areturn        
        //    StackMapTable: 00 14 06 FF 00 1A 00 07 07 00 4E 07 00 50 07 00 44 01 07 00 44 07 00 52 07 00 54 00 00 FC 00 21 07 00 5E 16 FB 00 6A FB 00 79 FB 00 46 FB 00 45 FB 00 83 FB 00 F6 2C FB 00 4B FB 00 E7 23 36 FB 00 EA FB 00 DC F9 00 09 0C FD 00 5C 07 02 15 01
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
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:577)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.ast.AstOptimizer$InlineLambdasOptimization.tryInlineLambda(AstOptimizer.java:3600)
        //     at com.strobel.decompiler.ast.AstOptimizer$InlineLambdasOptimization.run(AstOptimizer.java:3488)
        //     at com.strobel.decompiler.ast.AstOptimizer.runOptimization(AstOptimizer.java:3876)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:220)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
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
    
    private static HeaderLine makeHeaderLine(final String line) {
        final Matcher mu = ShadersCompatibility.PATTERN_UNIFORM.matcher(line);
        if (mu.matches()) {
            return new HeaderLineVariable("uniform", mu.group(2), line);
        }
        final Matcher mi = ShadersCompatibility.PATTERN_IN.matcher(line);
        if (mi.matches()) {
            return new HeaderLineVariable("in", mi.group(2), line);
        }
        final Matcher mo = ShadersCompatibility.PATTERN_OUT.matcher(line);
        if (mo.matches()) {
            return new HeaderLineVariable("out", mo.group(2), line);
        }
        final Matcher mv = ShadersCompatibility.PATTERN_VARYING.matcher(line);
        if (mv.matches()) {
            return new HeaderLineVariable("varying", mv.group(1), line);
        }
        final Matcher mc = ShadersCompatibility.PATTERN_CONST.matcher(line);
        if (mc.matches()) {
            return new HeaderLineVariable("const", mc.group(1), line);
        }
        final Matcher mf = ShadersCompatibility.PATTERN_FUNCTION.matcher(line);
        if (mf.matches()) {
            return new HeaderLineFunction(mf.group(1), line);
        }
        throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line));
    }
    
    private static String makeFtransformBasic() {
        final StringBuilder buf = new StringBuilder();
        addLine(buf, "vec4 ftransformBasic()                                                                                           ");
        addLine(buf, "{                                                                                                                ");
        addLine(buf, "  if(renderStage != MC_RENDER_STAGE_OUTLINE)   // Render stage outline                                           ");
        addLine(buf, "    return projectionMatrix * modelViewMatrix * vec4(vaPosition, 1.0);                                           ");
        addLine(buf, "  float lineWidth = 2.5;                                                                                         ");
        addLine(buf, "  vec2 screenSize = vec2(viewWidth, viewHeight);                                                                 ");
        addLine(buf, "  const mat4 VIEW_SCALE = mat4(mat3(1.0 - (1.0 / 256.0)));                                                       ");
        addLine(buf, "  vec4 linePosStart = projectionMatrix * VIEW_SCALE * modelViewMatrix * vec4(vaPosition, 1.0);                   ");
        addLine(buf, "  vec4 linePosEnd = projectionMatrix * VIEW_SCALE * modelViewMatrix * (vec4(vaPosition + vaNormal, 1.0));        ");
        addLine(buf, "  vec3 ndc1 = linePosStart.xyz / linePosStart.w;                                                                 ");
        addLine(buf, "  vec3 ndc2 = linePosEnd.xyz / linePosEnd.w;                                                                     ");
        addLine(buf, "  vec2 lineScreenDirection = normalize((ndc2.xy - ndc1.xy) * screenSize);                                        ");
        addLine(buf, "  vec2 lineOffset = vec2(-lineScreenDirection.y, lineScreenDirection.x) * lineWidth / screenSize;                ");
        addLine(buf, "  if (lineOffset.x < 0.0)                                                                                        ");
        addLine(buf, "    lineOffset *= -1.0;                                                                                          ");
        addLine(buf, "  if (gl_VertexID % 2 == 0)                                                                                      ");
        addLine(buf, "    return vec4((ndc1 + vec3(lineOffset, 0.0)) * linePosStart.w, linePosStart.w);                                ");
        addLine(buf, "  else                                                                                                           ");
        addLine(buf, "    return vec4((ndc1 - vec3(lineOffset, 0.0)) * linePosStart.w, linePosStart.w);                                ");
        addLine(buf, "}                                                                                                                ");
        String src = buf.toString();
        src = src.replace((CharSequence)"MC_RENDER_STAGE_OUTLINE", invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, RenderStage.OUTLINE.ordinal()));
        return src;
    }
    
    private static void addLine(final StringBuilder buf, final String line) {
        buf.append(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, StrUtils.trimTrailing(line, " \t")));
    }
    
    private static LineBuffer removeExisting(final LineBuffer lines, final Set<HeaderLine> headerLines) {
        if (headerLines.isEmpty()) {
            return lines;
        }
        final LineBuffer linesNew = new LineBuffer(lines.getLines());
        for (final HeaderLine headerLine : headerLines) {
            for (int i = 0; i < linesNew.size(); ++i) {
                final String lineNew = linesNew.get(i);
                if (headerLine.matches(lineNew)) {
                    String lineNew2 = headerLine.removeFrom(lineNew);
                    if (lineNew2 == null) {
                        lineNew2 = "// Moved up";
                    }
                    linesNew.set(i, lineNew2);
                }
            }
        }
        return linesNew;
    }
    
    private static LineBuffer moveExtensionsToHeader(final LineBuffer lines, final Set<HeaderLine> headerLines) {
        final LineBuffer linesNew = new LineBuffer(lines.getLines());
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            if (ShadersCompatibility.PATTERN_EXTENSION.matcher(line).matches()) {
                String lineHeader = line.trim();
                lineHeader = replaceWord(lineHeader, "require", "enable");
                final HeaderLine hl = new HeaderLineText(lineHeader);
                headerLines.add(hl);
                line = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, line);
            }
            linesNew.set(i, line);
        }
        return linesNew;
    }
    
    private static int getVersion(final String line, final int def) {
        final Matcher m = ShadersCompatibility.PATTERN_VERSION.matcher(line);
        if (!m.matches()) {
            return def;
        }
        final String verStr = m.group(1);
        final int ver = Config.parseInt(verStr, -1);
        if (ver < def) {
            return def;
        }
        return ver;
    }
    
    private static int getIndexInsertHeader(final LineBuffer lines, final int version) {
        final int indexVersion = lines.indexMatch(ShadersCompatibility.PATTERN_VERSION);
        final int indexInsert;
        final int indexLine = indexInsert = lines.indexMatch(ShadersCompatibility.PATTERN_LINE, indexVersion);
        if (indexInsert < 0) {
            Config.warn("Header insert line not found");
        }
        return indexInsert;
    }
    
    private static String addAlphaTest(final Program program, String line, final Set<HeaderLine> headerLines) {
        if (program.getProgramStage().isAnyComposite()) {
            return line;
        }
        final Matcher m = ShadersCompatibility.PATTERN_FRAG_DATA_SET.matcher(line);
        if (m.matches()) {
            final String index = m.group(2);
            if (!index.equals("0")) {
                return line;
            }
            final HeaderLine hl = new HeaderLineText(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, index));
            headerLines.add(hl);
            headerLines.add(ShadersCompatibility.ALPHA_TEST_REF);
            final String line2 = line = m.replaceAll("$1{\n$1  temp_FragData$2$3 = $4\n$1  if(temp_FragData$2.a < alphaTestRef) discard;\n$1  gl_FragData[$2] = temp_FragData$2;\n$1}");
        }
        final Matcher m2 = ShadersCompatibility.PATTERN_FRAG_DATA_GET.matcher(line);
        if (m2.find()) {
            final String index2 = m2.group(1);
            if (!index2.equals("0")) {
                return line;
            }
            final HeaderLine hl2 = new HeaderLineText(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, index2));
            headerLines.add(hl2);
            final String line3 = line = m2.replaceAll("temp_FragData$1$2");
        }
        return line;
    }
    
    private static String replaceShadow2D(final String line, final String name, final String nameNew, final Set<HeaderLine> headerLines) {
        if (line.indexOf(name) < 0) {
            return line;
        }
        String line2 = line;
        line2 = line2.replaceAll(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name), invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, nameNew));
        line2 = line2.replaceAll(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name), invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, nameNew));
        line2 = line2.replaceAll(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name), invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, nameNew));
        return line2;
    }
    
    private static String replaceFragData(final String line, final Set<HeaderLine> headerLines) {
        final Matcher m = ShadersCompatibility.PATTERN_FRAG_DATA.matcher(line);
        if (m.find()) {
            final String line2 = m.replaceAll("outColor$1");
            for (int i = 0; i < 8; ++i) {
                if (line2.contains(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i))) {
                    headerLines.add(new HeaderLineText(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i)));
                }
            }
            return line2;
        }
        return line;
    }
    
    private static Comparator<String> getComparatorHeaderLines() {
        final Comparator<String> comp = new Comparator<String>() {
            private static final int UNKNOWN = Integer.MAX_VALUE;
            
            @Override
            public int compare(final String o1, final String o2) {
                if (o1.startsWith("in ") && o2.startsWith("in ")) {
                    final int i1 = this.getAttributeIndex(o1);
                    final int i2 = this.getAttributeIndex(o2);
                    if (i1 != Integer.MAX_VALUE || i2 != Integer.MAX_VALUE) {
                        return i1 - i2;
                    }
                }
                if (o1.startsWith("uniform ") && o2.startsWith("uniform ")) {
                    final int i1 = this.getUniformIndex(o1);
                    final int i2 = this.getUniformIndex(o2);
                    if (i1 != Integer.MAX_VALUE || i2 != Integer.MAX_VALUE) {
                        return i1 - i2;
                    }
                }
                return o1.compareTo(o2);
            }
            
            private int getAttributeIndex(final String line) {
                if (line.equals(ShadersCompatibility.POSITION.getText())) {
                    return 0;
                }
                if (line.equals(ShadersCompatibility.COLOR.getText())) {
                    return 1;
                }
                if (line.equals(ShadersCompatibility.UV0.getText())) {
                    return 2;
                }
                if (line.equals(ShadersCompatibility.UV1.getText())) {
                    return 3;
                }
                if (line.equals(ShadersCompatibility.UV2.getText())) {
                    return 4;
                }
                if (line.equals(ShadersCompatibility.NORMAL.getText())) {
                    return 5;
                }
                return Integer.MAX_VALUE;
            }
            
            private int getUniformIndex(final String line) {
                if (line.equals(ShadersCompatibility.MODEL_VIEW_MATRIX.getText())) {
                    return 0;
                }
                if (line.equals(ShadersCompatibility.MODEL_VIEW_MATRIX_INVERSE.getText())) {
                    return 1;
                }
                if (line.equals(ShadersCompatibility.PROJECTION_MATRIX.getText())) {
                    return 2;
                }
                if (line.equals(ShadersCompatibility.PROJECTION_MATRIX_INVERSE.getText())) {
                    return 3;
                }
                if (line.equals(ShadersCompatibility.TEXTURE_MATRIX.getText())) {
                    return 4;
                }
                if (line.equals(ShadersCompatibility.NORMAL_MATRIX.getText())) {
                    return 5;
                }
                if (line.equals(ShadersCompatibility.CHUNK_OFFSET.getText())) {
                    return 6;
                }
                if (line.equals(ShadersCompatibility.ALPHA_TEST_REF.getText())) {
                    return 7;
                }
                return Integer.MAX_VALUE;
            }
        };
        return comp;
    }
    
    private static String replace(final String line, final String find, final String replace, final Set<HeaderLine> newLines, final HeaderLine... headerLines) {
        final String line2 = replaceWord(line, find, replace);
        if (!line2.equals(line) && headerLines.length > 0) {
            newLines.addAll(Arrays.asList(headerLines));
        }
        return line2;
    }
    
    private static String replaceWord(final String line, final String find, final String replace) {
        String line2 = line;
        int pos = line2.length();
        while (pos > 0) {
            pos = line2.lastIndexOf(find, pos - 1);
            if (pos >= 0) {
                final int posEnd = pos + find.length();
                if (pos - 1 >= 0) {
                    final char charPrev = line2.charAt(pos - 1);
                    if (Character.isLetter(charPrev) || Character.isDigit(charPrev)) {
                        continue;
                    }
                    if (charPrev == '_') {
                        continue;
                    }
                }
                if (posEnd < line2.length()) {
                    final char charNext = line2.charAt(posEnd);
                    if (Character.isLetter(charNext) || Character.isDigit(charNext)) {
                        continue;
                    }
                    if (charNext == '_') {
                        continue;
                    }
                }
                line2 = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, line2.substring(0, pos), replace, line2.substring(posEnd));
            }
        }
        return line2;
    }
    
    private static String replace(final String line, final Pattern pattern, final String replace, final Set<HeaderLine> newLines, final HeaderLine... headerLines) {
        final Matcher m = pattern.matcher(line);
        if (!m.find()) {
            return line;
        }
        final String line2 = m.replaceAll(replace);
        if (!line2.equals(line) && headerLines.length > 0) {
            newLines.addAll(Arrays.asList(headerLines));
        }
        return line2;
    }
    
    private static boolean matches(final String line, final Pattern pattern) {
        final Matcher m = pattern.matcher(line);
        return m.matches();
    }
    
    static {
        ShadersCompatibility.PATTERN_UNIFORM = Pattern.compile("(\\s*layout\\s*\\(.*\\)|)\\s*uniform\\s+\\w+\\s+(\\w+).*");
        ShadersCompatibility.PATTERN_IN = Pattern.compile("(\\s*layout\\s*\\(.*\\)|)\\s*in\\s+\\w+\\s+(\\w+).*");
        ShadersCompatibility.PATTERN_OUT = Pattern.compile("(\\s*layout\\s*\\(.*\\)|)\\s*out\\s+\\w+\\s+(\\w+).*");
        ShadersCompatibility.PATTERN_VARYING = Pattern.compile("\\s*varying\\s+\\w+\\s+(\\w+).*");
        ShadersCompatibility.PATTERN_CONST = Pattern.compile("\\s*const\\s+\\w+\\s+(\\w+).*");
        ShadersCompatibility.PATTERN_FUNCTION = Pattern.compile("\\s*\\w+\\s+(\\w+)\\s*\\(.*\\).*", 32);
        ShadersCompatibility.MODEL_VIEW_MATRIX = makeHeaderLine("uniform mat4 modelViewMatrix;");
        ShadersCompatibility.MODEL_VIEW_MATRIX_INVERSE = makeHeaderLine("uniform mat4 modelViewMatrixInverse;");
        ShadersCompatibility.PROJECTION_MATRIX = makeHeaderLine("uniform mat4 projectionMatrix;");
        ShadersCompatibility.PROJECTION_MATRIX_INVERSE = makeHeaderLine("uniform mat4 projectionMatrixInverse;");
        ShadersCompatibility.TEXTURE_MATRIX = makeHeaderLine("uniform mat4 textureMatrix = mat4(1.0);");
        ShadersCompatibility.NORMAL_MATRIX = makeHeaderLine("uniform mat3 normalMatrix;");
        ShadersCompatibility.CHUNK_OFFSET = makeHeaderLine("uniform vec3 chunkOffset;");
        ShadersCompatibility.ALPHA_TEST_REF = makeHeaderLine("uniform float alphaTestRef;");
        ShadersCompatibility.TEXTURE_MATRIX_2 = makeHeaderLine("const mat4 TEXTURE_MATRIX_2 = mat4(vec4(0.00390625, 0.0, 0.0, 0.0), vec4(0.0, 0.00390625, 0.0, 0.0), vec4(0.0, 0.0, 0.00390625, 0.0), vec4(0.03125, 0.03125, 0.03125, 1.0));");
        ShadersCompatibility.FTRANSORM_BASIC = makeHeaderLine(makeFtransformBasic());
        ShadersCompatibility.FOG_DENSITY = makeHeaderLine("uniform float fogDensity;");
        ShadersCompatibility.FOG_START = makeHeaderLine("uniform float fogStart;");
        ShadersCompatibility.FOG_END = makeHeaderLine("uniform float fogEnd;");
        ShadersCompatibility.FOG_COLOR = makeHeaderLine("uniform vec3 fogColor;");
        ShadersCompatibility.VIEW_WIDTH = makeHeaderLine("uniform float viewWidth;");
        ShadersCompatibility.VIEW_HEIGHT = makeHeaderLine("uniform float viewHeight;");
        ShadersCompatibility.RENDER_STAGE = makeHeaderLine("uniform int renderStage;");
        ShadersCompatibility.FOG_FRAG_COORD_OUT = makeHeaderLine("out float varFogFragCoord;");
        ShadersCompatibility.FOG_FRAG_COORD_IN = makeHeaderLine("in float varFogFragCoord;");
        ShadersCompatibility.FRONT_COLOR_OUT = makeHeaderLine("out vec4 varFrontColor;");
        ShadersCompatibility.FRONT_COLOR_IN = makeHeaderLine("in vec4 varFrontColor;");
        ShadersCompatibility.POSITION = makeHeaderLine("in vec3 vaPosition;");
        ShadersCompatibility.COLOR = makeHeaderLine("in vec4 vaColor;");
        ShadersCompatibility.UV0 = makeHeaderLine("in vec2 vaUV0;");
        ShadersCompatibility.UV1 = makeHeaderLine("in ivec2 vaUV1;");
        ShadersCompatibility.UV2 = makeHeaderLine("in ivec2 vaUV2;");
        ShadersCompatibility.NORMAL = makeHeaderLine("in vec3 vaNormal;");
        PATTERN_VERSION = ShaderPackParser.PATTERN_VERSION;
        PATTERN_EXTENSION = Pattern.compile("\\s*#\\s*extension\\s+(\\w+)(.*)");
        PATTERN_LINE = Pattern.compile("\\s*#\\s*line\\s+(\\d+)\\s+(\\d+)(.*)");
        PATTERN_TEXTURE2D_TEXCOORD = Pattern.compile("(.*texture(2D)?\\s*\\(\\s*(texture|colortex0)\\s*,\\s*)(\\w+)(\\s*\\).*)");
        PATTERN_FRAG_DATA_SET = Pattern.compile("(\\s*)gl_FragData\\[(\\d+)\\](\\S*)\\s*=\\s*(.*)");
        PATTERN_FRAG_DATA_GET = Pattern.compile("gl_FragData\\[(\\d+)\\]([^ ][^=])");
        PATTERN_FRAG_DATA = Pattern.compile("gl_FragData\\[(\\d+)\\]");
    }
}
