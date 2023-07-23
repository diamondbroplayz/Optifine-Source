// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.config;

import net.optifine.Config;

public class MatchProfession
{
    private bye profession;
    private int[] levels;
    
    public MatchProfession(final bye profession) {
        this(profession, null);
    }
    
    public MatchProfession(final bye profession, final int level) {
        this(profession, new int[] { level });
    }
    
    public MatchProfession(final bye profession, final int[] levels) {
        this.profession = profession;
        this.levels = levels;
    }
    
    public boolean matches(final bye prof, final int lev) {
        return this.profession == prof && (this.levels == null || Config.equalsOne(lev, this.levels));
    }
    
    private boolean hasLevel(final int lev) {
        return this.levels != null && Config.equalsOne(lev, this.levels);
    }
    
    public boolean addLevel(final int lev) {
        if (this.levels == null) {
            this.levels = new int[] { lev };
            return true;
        }
        if (this.hasLevel(lev)) {
            return false;
        }
        this.levels = Config.addIntToArray(this.levels, lev);
        return true;
    }
    
    public bye getProfession() {
        return this.profession;
    }
    
    public int[] getLevels() {
        return this.levels;
    }
    
    public static boolean matchesOne(final bye prof, final int level, final MatchProfession[] mps) {
        if (mps == null) {
            return false;
        }
        for (int i = 0; i < mps.length; ++i) {
            final MatchProfession mp = mps[i];
            if (mp.matches(prof, level)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        if (this.levels == null) {
            return invokedynamic(makeConcatWithConstants:(Lbye;)Ljava/lang/String;, this.profession);
        }
        return invokedynamic(makeConcatWithConstants:(Lbye;Ljava/lang/String;)Ljava/lang/String;, this.profession, Config.arrayToString(this.levels));
    }
}
