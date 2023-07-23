// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import net.optifine.util.StrUtils;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.StringEscapeUtils;
import java.util.Arrays;
import net.optifine.Config;
import java.util.regex.Pattern;

public class NbtTagValue
{
    private String[] parents;
    private String name;
    private boolean negative;
    private boolean raw;
    private int type;
    private String value;
    private Pattern valueRegex;
    private RangeListInt valueRange;
    private int valueFormat;
    private static final int TYPE_INVALID = -1;
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_PATTERN = 1;
    private static final int TYPE_IPATTERN = 2;
    private static final int TYPE_REGEX = 3;
    private static final int TYPE_IREGEX = 4;
    private static final int TYPE_RANGE = 5;
    private static final int TYPE_EXISTS = 6;
    private static final String PREFIX_PATTERN = "pattern:";
    private static final String PREFIX_IPATTERN = "ipattern:";
    private static final String PREFIX_REGEX = "regex:";
    private static final String PREFIX_IREGEX = "iregex:";
    private static final String PREFIX_RAW = "raw:";
    private static final String PREFIX_RANGE = "range:";
    private static final String PREFIX_EXISTS = "exists:";
    private static final int FORMAT_DEFAULT = 0;
    private static final int FORMAT_HEX_COLOR = 1;
    private static final String PREFIX_HEX_COLOR = "#";
    private static final Pattern PATTERN_HEX_COLOR;
    
    public NbtTagValue(final String tag, String value) {
        this.parents = null;
        this.name = null;
        this.negative = false;
        this.raw = false;
        this.type = 0;
        this.value = null;
        this.valueRegex = null;
        this.valueRange = null;
        this.valueFormat = 0;
        final String[] names = Config.tokenize(tag, ".");
        this.parents = Arrays.copyOfRange(names, 0, names.length - 1);
        this.name = names[names.length - 1];
        if (value.startsWith("!")) {
            this.negative = true;
            value = value.substring(1);
        }
        if (value.startsWith("raw:")) {
            this.raw = true;
            value = value.substring("raw:".length());
        }
        if (value.startsWith("pattern:")) {
            this.type = 1;
            value = value.substring("pattern:".length());
            if (value.equals("*")) {
                this.type = 6;
            }
        }
        else if (value.startsWith("ipattern:")) {
            this.type = 2;
            value = value.substring("ipattern:".length()).toLowerCase();
            if (value.equals("*")) {
                this.type = 6;
            }
        }
        else if (value.startsWith("regex:")) {
            this.type = 3;
            value = value.substring("regex:".length());
            this.valueRegex = Pattern.compile(value);
            if (value.equals(".*")) {
                this.type = 6;
            }
        }
        else if (value.startsWith("iregex:")) {
            this.type = 4;
            value = value.substring("iregex:".length());
            this.valueRegex = Pattern.compile(value, 2);
            if (value.equals(".*")) {
                this.type = 6;
            }
        }
        else if (value.startsWith("range:")) {
            this.type = 5;
            value = value.substring("range:".length());
            final ConnectedParser cp = new ConnectedParser("NbtTag");
            this.valueRange = cp.parseRangeListIntNeg(value);
            if (this.valueRange == null) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, value));
                this.type = -1;
                this.negative = false;
            }
        }
        else if (value.startsWith("exists:")) {
            this.type = 6;
            value = value.substring("exists:".length());
            final Boolean valB = Config.parseBoolean(value, null);
            if (Config.isFalse(valB)) {
                this.negative = !this.negative;
            }
            if (valB == null) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, value));
                this.type = -1;
                this.negative = false;
            }
        }
        else {
            this.type = 0;
        }
        value = StringEscapeUtils.unescapeJava(value);
        if (this.type == 0 && NbtTagValue.PATTERN_HEX_COLOR.matcher(value).matches()) {
            this.valueFormat = 1;
        }
        this.value = value;
    }
    
    public boolean matches(final qr nbt) {
        if (this.negative) {
            return !this.matchesCompound(nbt);
        }
        return this.matchesCompound(nbt);
    }
    
    public boolean matchesCompound(final qr nbt) {
        if (nbt == null) {
            return false;
        }
        rk tagBase = (rk)nbt;
        for (int i = 0; i < this.parents.length; ++i) {
            final String tag = this.parents[i];
            tagBase = getChildTag(tagBase, tag);
            if (tagBase == null) {
                return false;
            }
        }
        if (this.name.equals("*")) {
            return this.matchesAnyChild(tagBase);
        }
        tagBase = getChildTag(tagBase, this.name);
        return tagBase != null && this.matchesBase(tagBase);
    }
    
    private boolean matchesAnyChild(final rk tagBase) {
        if (tagBase instanceof qr) {
            final qr tagCompound = (qr)tagBase;
            final Set nbtKeySet = tagCompound.e();
            for (final String key : nbtKeySet) {
                final rk nbtBase = tagCompound.c(key);
                if (this.matchesBase(nbtBase)) {
                    return true;
                }
            }
        }
        if (tagBase instanceof qx) {
            final qx tagList = (qx)tagBase;
            for (int count = tagList.size(), i = 0; i < count; ++i) {
                final rk nbtBase2 = tagList.k(i);
                if (this.matchesBase(nbtBase2)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static rk getChildTag(final rk tagBase, final String tag) {
        if (tagBase instanceof qr) {
            final qr tagCompound = (qr)tagBase;
            return tagCompound.c(tag);
        }
        if (!(tagBase instanceof qx)) {
            return null;
        }
        final qx tagList = (qx)tagBase;
        if (tag.equals("count")) {
            return (rk)qw.a(tagList.size());
        }
        final int index = Config.parseInt(tag, -1);
        if (index < 0 || index >= tagList.size()) {
            return null;
        }
        return tagList.k(index);
    }
    
    public boolean matchesBase(final rk nbtBase) {
        if (nbtBase == null) {
            return false;
        }
        switch (this.type) {
            case -1: {
                return false;
            }
            case 5: {
                final int nbtValueInt = getNbtInt(nbtBase, Integer.MIN_VALUE);
                if (nbtValueInt != Integer.MIN_VALUE) {
                    return matchesRange(nbtValueInt, this.valueRange);
                }
                return true;
            }
            case 6: {
                return true;
            }
            default: {
                final String nbtValue = this.raw ? String.valueOf(nbtBase) : getNbtString(nbtBase, this.valueFormat);
                return this.matchesValue(nbtValue);
            }
        }
    }
    
    public boolean matchesValue(final String nbtValue) {
        if (nbtValue == null) {
            return false;
        }
        switch (this.type) {
            case -1: {
                return false;
            }
            case 0: {
                return nbtValue.equals(this.value);
            }
            case 1: {
                return matchesPattern(nbtValue, this.value);
            }
            case 2: {
                return matchesPattern(nbtValue.toLowerCase(), this.value);
            }
            case 3:
            case 4: {
                return matchesRegex(nbtValue, this.valueRegex);
            }
            case 5: {
                return matchesRange(nbtValue, this.valueRange);
            }
            case 6: {
                return true;
            }
            default: {
                throw new IllegalArgumentException(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.type));
            }
        }
    }
    
    private static boolean matchesPattern(final String str, final String pattern) {
        return StrUtils.equalsMask(str, pattern, '*', '?');
    }
    
    private static boolean matchesRegex(final String str, final Pattern regex) {
        return regex.matcher(str).matches();
    }
    
    private static boolean matchesRange(final String str, final RangeListInt range) {
        if (range == null) {
            return false;
        }
        final int valInt = Config.parseInt(str, Integer.MIN_VALUE);
        return valInt != Integer.MIN_VALUE && matchesRange(valInt, range);
    }
    
    private static boolean matchesRange(final int valInt, final RangeListInt range) {
        return range != null && range.isInRange(valInt);
    }
    
    private static String getNbtString(final rk nbtBase, final int format) {
        if (nbtBase == null) {
            return null;
        }
        if (nbtBase instanceof ri) {
            final ri nbtString = (ri)nbtBase;
            String text = nbtString.m_();
            if (text.startsWith("{") && text.endsWith("}")) {
                text = getMergedJsonText(text);
            }
            else if (text.startsWith("[{") && text.endsWith("}]")) {
                text = getMergedJsonText(text);
            }
            return text;
        }
        if (nbtBase instanceof qw) {
            final qw i = (qw)nbtBase;
            if (format == 1) {
                return invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, StrUtils.fillLeft(Integer.toHexString(i.g()), 6, '0'));
            }
            return Integer.toString(i.g());
        }
        else {
            if (nbtBase instanceof qp) {
                final qp b = (qp)nbtBase;
                return Byte.toString(b.i());
            }
            if (nbtBase instanceof rf) {
                final rf s = (rf)nbtBase;
                return Short.toString(s.h());
            }
            if (nbtBase instanceof qz) {
                final qz l = (qz)nbtBase;
                return Long.toString(l.f());
            }
            if (nbtBase instanceof qu) {
                final qu f = (qu)nbtBase;
                return Float.toString(f.k());
            }
            if (nbtBase instanceof qs) {
                final qs d = (qs)nbtBase;
                return Double.toString(d.j());
            }
            return nbtBase.toString();
        }
    }
    
    private static int getNbtInt(final rk nbtBase, final int defVal) {
        if (nbtBase == null) {
            return defVal;
        }
        if (nbtBase instanceof qw) {
            final qw i = (qw)nbtBase;
            return i.g();
        }
        if (nbtBase instanceof qp) {
            final qp b = (qp)nbtBase;
            return b.i();
        }
        if (nbtBase instanceof rf) {
            final rf s = (rf)nbtBase;
            return s.h();
        }
        if (nbtBase instanceof qz) {
            final qz l = (qz)nbtBase;
            return (int)l.f();
        }
        if (nbtBase instanceof qu) {
            final qu f = (qu)nbtBase;
            return (int)f.k();
        }
        if (nbtBase instanceof qs) {
            final qs d = (qs)nbtBase;
            return (int)d.j();
        }
        return defVal;
    }
    
    private static String getMergedJsonText(final String text) {
        final StringBuilder sb = new StringBuilder();
        final String TOKEN_TEXT = "\"text\":\"";
        int pos = -1;
        while (true) {
            pos = text.indexOf(TOKEN_TEXT, pos + 1);
            if (pos < 0) {
                break;
            }
            final String str = parseString(text, pos + TOKEN_TEXT.length());
            if (str == null) {
                continue;
            }
            sb.append(str);
        }
        return sb.toString();
    }
    
    private static String parseString(final String text, final int pos) {
        final StringBuilder sb = new StringBuilder();
        boolean escapeMode = false;
        for (int i = pos; i < text.length(); ++i) {
            final char ch = text.charAt(i);
            if (escapeMode) {
                if (ch == 'b') {
                    sb.append('\b');
                }
                else if (ch == 'f') {
                    sb.append('\f');
                }
                else if (ch == 'n') {
                    sb.append('\n');
                }
                else if (ch == 'r') {
                    sb.append('\r');
                }
                else if (ch == 't') {
                    sb.append('\t');
                }
                else {
                    sb.append(ch);
                }
                escapeMode = false;
            }
            else if (ch == '\\') {
                escapeMode = true;
            }
            else {
                if (ch == '\"') {
                    break;
                }
                sb.append(ch);
            }
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.parents.length; ++i) {
            final String parent = this.parents[i];
            if (i > 0) {
                sb.append(".");
            }
            sb.append(parent);
        }
        if (sb.length() > 0) {
            sb.append(".");
        }
        sb.append(this.name);
        sb.append(" = ");
        sb.append(this.value);
        return sb.toString();
    }
    
    static {
        PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");
    }
}
