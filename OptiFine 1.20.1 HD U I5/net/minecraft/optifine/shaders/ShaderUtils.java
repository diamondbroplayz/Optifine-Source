// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import net.optifine.Config;
import net.optifine.shaders.config.ShaderProfile;
import net.optifine.shaders.config.ShaderOption;

public class ShaderUtils
{
    public static ShaderOption getShaderOption(final String name, final ShaderOption[] opts) {
        if (opts == null) {
            return null;
        }
        for (int i = 0; i < opts.length; ++i) {
            final ShaderOption so = opts[i];
            if (so.getName().equals(name)) {
                return so;
            }
        }
        return null;
    }
    
    public static ShaderProfile detectProfile(final ShaderProfile[] profs, final ShaderOption[] opts, final boolean def) {
        if (profs == null) {
            return null;
        }
        for (int i = 0; i < profs.length; ++i) {
            final ShaderProfile prof = profs[i];
            if (matchProfile(prof, opts, def)) {
                return prof;
            }
        }
        return null;
    }
    
    public static boolean matchProfile(final ShaderProfile prof, final ShaderOption[] opts, final boolean def) {
        if (prof == null) {
            return false;
        }
        if (opts == null) {
            return false;
        }
        final String[] optsProf = prof.getOptions();
        for (int p = 0; p < optsProf.length; ++p) {
            final String opt = optsProf[p];
            final ShaderOption so = getShaderOption(opt, opts);
            if (so != null) {
                final String optVal = def ? so.getValueDefault() : so.getValue();
                final String profVal = prof.getValue(opt);
                if (!Config.equals(optVal, profVal)) {
                    return false;
                }
            }
        }
        return true;
    }
}
