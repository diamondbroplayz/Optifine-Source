// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

public class ProgramUtils
{
    public static boolean hasActive(final Program[] programs) {
        for (int i = 0; i < programs.length; ++i) {
            final Program program = programs[i];
            if (program.getId() != 0) {
                return true;
            }
            if (program.getComputePrograms().length > 0) {
                return true;
            }
        }
        return false;
    }
}
