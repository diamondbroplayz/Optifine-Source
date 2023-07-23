// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import java.util.Arrays;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;
import net.optifine.expr.ParseException;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.ExpressionParser;
import net.optifine.Config;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.Deque;

public class MacroState
{
    private boolean active;
    private Deque<Boolean> dequeState;
    private Deque<Boolean> dequeResolved;
    private Map<String, String> mapMacroValues;
    public static final Pattern PATTERN_DIRECTIVE;
    private static final Pattern PATTERN_DEFINED;
    private static final Pattern PATTERN_DEFINED_FUNC;
    private static final Pattern PATTERN_MACRO;
    private static final String DEFINE = "define";
    private static final String UNDEF = "undef";
    private static final String IFDEF = "ifdef";
    private static final String IFNDEF = "ifndef";
    private static final String IF = "if";
    private static final String ELSE = "else";
    private static final String ELIF = "elif";
    private static final String ENDIF = "endif";
    private static final List<String> MACRO_NAMES;
    
    public MacroState() {
        this.active = true;
        this.dequeState = new ArrayDeque<Boolean>();
        this.dequeResolved = new ArrayDeque<Boolean>();
        this.mapMacroValues = new HashMap<String, String>();
    }
    
    public boolean processLine(final String line) {
        final Matcher m = MacroState.PATTERN_DIRECTIVE.matcher(line);
        if (!m.matches()) {
            return this.active;
        }
        final String name = m.group(1);
        String param = m.group(2);
        final int posComment = param.indexOf("//");
        if (posComment >= 0) {
            param = param.substring(0, posComment);
        }
        final boolean activePrev = this.active;
        this.processMacro(name, param);
        this.active = !this.dequeState.contains(Boolean.FALSE);
        return this.active || activePrev;
    }
    
    public static boolean isMacroLine(final String line) {
        final Matcher m = MacroState.PATTERN_DIRECTIVE.matcher(line);
        if (!m.matches()) {
            return false;
        }
        final String name = m.group(1);
        return MacroState.MACRO_NAMES.contains(name);
    }
    
    private void processMacro(final String name, final String param) {
        final StringTokenizer tok = new StringTokenizer(param, " \t");
        final String macro = tok.hasMoreTokens() ? tok.nextToken() : "";
        final String rest = tok.hasMoreTokens() ? tok.nextToken("").trim() : "";
        if (name.equals("define")) {
            this.mapMacroValues.put(macro, rest);
            return;
        }
        if (name.equals("undef")) {
            this.mapMacroValues.remove(macro);
            return;
        }
        if (name.equals("ifdef")) {
            final boolean act = this.mapMacroValues.containsKey(macro);
            this.dequeState.add(act);
            this.dequeResolved.add(act);
            return;
        }
        if (name.equals("ifndef")) {
            final boolean act = !this.mapMacroValues.containsKey(macro);
            this.dequeState.add(act);
            this.dequeResolved.add(act);
            return;
        }
        if (name.equals("if")) {
            final boolean act = this.eval(param);
            this.dequeState.add(act);
            this.dequeResolved.add(act);
            return;
        }
        if (this.dequeState.isEmpty()) {
            return;
        }
        if (name.equals("elif")) {
            final boolean lastState = this.dequeState.removeLast();
            final boolean lastResolved = this.dequeResolved.removeLast();
            if (lastResolved) {
                this.dequeState.add(false);
                this.dequeResolved.add(lastResolved);
            }
            else {
                final boolean act2 = this.eval(param);
                this.dequeState.add(act2);
                this.dequeResolved.add(act2);
            }
            return;
        }
        if (name.equals("else")) {
            final boolean lastState = this.dequeState.removeLast();
            final boolean lastResolved = this.dequeResolved.removeLast();
            final boolean act2 = !lastResolved;
            this.dequeState.add(act2);
            this.dequeResolved.add(true);
            return;
        }
        if (name.equals("endif")) {
            this.dequeState.removeLast();
            this.dequeResolved.removeLast();
        }
    }
    
    private boolean eval(String str) {
        final Matcher md = MacroState.PATTERN_DEFINED.matcher(str);
        str = md.replaceAll("defined_$1");
        final Matcher mdf = MacroState.PATTERN_DEFINED_FUNC.matcher(str);
        str = mdf.replaceAll("defined_$1");
        boolean replaced = false;
        int count = 0;
        do {
            replaced = false;
            final Matcher mmn = MacroState.PATTERN_MACRO.matcher(str);
            while (mmn.find()) {
                final String match = mmn.group();
                if (match.length() <= 0) {
                    continue;
                }
                final char ch = match.charAt(0);
                if (!Character.isLetter(ch) && ch != '_') {
                    continue;
                }
                if (!this.mapMacroValues.containsKey(match)) {
                    continue;
                }
                String val = this.mapMacroValues.get(match);
                if (val == null) {
                    val = "1";
                }
                final int start = mmn.start();
                final int end = mmn.end();
                str = invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, str.substring(0, start), val, str.substring(end));
                replaced = true;
                ++count;
                break;
            }
        } while (replaced && count < 100);
        if (count >= 100) {
            Config.warn(invokedynamic(makeConcatWithConstants:(ILjava/lang/String;)Ljava/lang/String;, count, str));
            return true;
        }
        try {
            final IExpressionResolver er = new MacroExpressionResolver(this.mapMacroValues);
            final ExpressionParser ep = new ExpressionParser(er);
            final IExpression expr = ep.parse(str);
            if (expr.getExpressionType() == ExpressionType.BOOL) {
                final IExpressionBool exprBool = (IExpressionBool)expr;
                final boolean ret = exprBool.eval();
                return ret;
            }
            if (expr.getExpressionType() == ExpressionType.FLOAT) {
                final IExpressionFloat exprFloat = (IExpressionFloat)expr;
                final float val2 = exprFloat.eval();
                final boolean ret2 = val2 != 0.0f;
                return ret2;
            }
            throw new ParseException(invokedynamic(makeConcatWithConstants:(Lnet/optifine/expr/ExpressionType;)Ljava/lang/String;, expr.getExpressionType()));
        }
        catch (ParseException e) {
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, str));
            Config.warn(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, e.getMessage()));
            return false;
        }
    }
    
    static {
        PATTERN_DIRECTIVE = Pattern.compile("\\s*#\\s*(\\w+)\\s*(.*)");
        PATTERN_DEFINED = Pattern.compile("defined\\s+(\\w+)");
        PATTERN_DEFINED_FUNC = Pattern.compile("defined\\s*\\(\\s*(\\w+)\\s*\\)");
        PATTERN_MACRO = Pattern.compile("(\\w+)");
        MACRO_NAMES = Arrays.asList("define", "undef", "ifdef", "ifndef", "if", "else", "elif", "endif");
    }
}
