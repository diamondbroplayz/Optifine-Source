// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders.config;

import java.util.List;
import java.util.ArrayList;
import net.optifine.shaders.Shaders;
import net.optifine.Lang;
import net.optifine.shaders.ShaderUtils;

public class ShaderOptionProfile extends ShaderOption
{
    private ShaderProfile[] profiles;
    private ShaderOption[] options;
    private static final String NAME_PROFILE = "<profile>";
    private static final String VALUE_CUSTOM = "<custom>";
    
    public ShaderOptionProfile(final ShaderProfile[] profiles, final ShaderOption[] options) {
        super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles), detectProfileName(profiles, options, true), null);
        this.profiles = null;
        this.options = null;
        this.profiles = profiles;
        this.options = options;
    }
    
    @Override
    public void nextValue() {
        super.nextValue();
        if (this.getValue().equals("<custom>")) {
            super.nextValue();
        }
        this.applyProfileOptions();
    }
    
    public void updateProfile() {
        final ShaderProfile prof = this.getProfile(this.getValue());
        if (prof != null && ShaderUtils.matchProfile(prof, this.options, false)) {
            return;
        }
        final String val = detectProfileName(this.profiles, this.options);
        this.setValue(val);
    }
    
    private void applyProfileOptions() {
        final ShaderProfile prof = this.getProfile(this.getValue());
        if (prof == null) {
            return;
        }
        final String[] opts = prof.getOptions();
        for (int i = 0; i < opts.length; ++i) {
            final String name = opts[i];
            final ShaderOption so = this.getOption(name);
            if (so != null) {
                final String val = prof.getValue(name);
                so.setValue(val);
            }
        }
    }
    
    private ShaderOption getOption(final String name) {
        for (int i = 0; i < this.options.length; ++i) {
            final ShaderOption so = this.options[i];
            if (so.getName().equals(name)) {
                return so;
            }
        }
        return null;
    }
    
    private ShaderProfile getProfile(final String name) {
        for (int i = 0; i < this.profiles.length; ++i) {
            final ShaderProfile prof = this.profiles[i];
            if (prof.getName().equals(name)) {
                return prof;
            }
        }
        return null;
    }
    
    @Override
    public String getNameText() {
        return Lang.get("of.shaders.profile");
    }
    
    @Override
    public String getValueText(final String val) {
        if (val.equals("<custom>")) {
            return Lang.get("of.general.custom", "<custom>");
        }
        return Shaders.translate(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, val), val);
    }
    
    @Override
    public String getValueColor(final String val) {
        if (val.equals("<custom>")) {
            return "§c";
        }
        return "§a";
    }
    
    @Override
    public String getDescriptionText() {
        final String text = Shaders.translate("profile.comment", null);
        if (text != null) {
            return text;
        }
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.profiles.length; ++i) {
            final String name = this.profiles[i].getName();
            if (name != null) {
                final String profText = Shaders.translate(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, name), (String)null);
                if (profText != null) {
                    sb.append(profText);
                    if (!profText.endsWith(". ")) {
                        sb.append(". ");
                    }
                }
            }
        }
        return sb.toString();
    }
    
    private static String detectProfileName(final ShaderProfile[] profs, final ShaderOption[] opts) {
        return detectProfileName(profs, opts, false);
    }
    
    private static String detectProfileName(final ShaderProfile[] profs, final ShaderOption[] opts, final boolean def) {
        final ShaderProfile prof = ShaderUtils.detectProfile(profs, opts, def);
        if (prof == null) {
            return "<custom>";
        }
        return prof.getName();
    }
    
    private static String[] getProfileNames(final ShaderProfile[] profs) {
        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < profs.length; ++i) {
            final ShaderProfile prof = profs[i];
            list.add(prof.getName());
        }
        list.add("<custom>");
        final String[] names = list.toArray(new String[list.size()]);
        return names;
    }
}
