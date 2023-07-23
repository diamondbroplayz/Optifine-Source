// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.shaders;

import java.util.ArrayList;
import java.util.List;

public class Programs
{
    private List<Program> programs;
    private Program programNone;
    
    public Programs() {
        this.programs = new ArrayList<Program>();
        this.programNone = this.make("", ProgramStage.NONE, true);
    }
    
    public Program make(final String name, final ProgramStage programStage, final Program backupProgram) {
        final int index = this.programs.size();
        final Program prog = new Program(index, name, programStage, backupProgram);
        this.programs.add(prog);
        return prog;
    }
    
    private Program make(final String name, final ProgramStage programStage, final boolean ownBackup) {
        final int index = this.programs.size();
        final Program prog = new Program(index, name, programStage, ownBackup);
        this.programs.add(prog);
        return prog;
    }
    
    public Program makeGbuffers(final String name, final Program backupProgram) {
        return this.make(name, ProgramStage.GBUFFERS, backupProgram);
    }
    
    public Program makeComposite(final String name) {
        return this.make(name, ProgramStage.COMPOSITE, this.programNone);
    }
    
    public Program makeDeferred(final String name) {
        return this.make(name, ProgramStage.DEFERRED, this.programNone);
    }
    
    public Program makeShadow(final String name, final Program backupProgram) {
        return this.make(name, ProgramStage.SHADOW, backupProgram);
    }
    
    public Program makeVirtual(final String name) {
        return this.make(name, ProgramStage.NONE, true);
    }
    
    public Program[] makePrograms(final String prefix, final int count, final ProgramStage stage, final Program backupProgram) {
        final Program[] ps = new Program[count];
        for (int i = 0; i < count; ++i) {
            final String name = (i == 0) ? prefix : invokedynamic(makeConcatWithConstants:(Ljava/lang/String;I)Ljava/lang/String;, prefix, i);
            ps[i] = this.make(name, stage, this.programNone);
        }
        return ps;
    }
    
    public Program[] makeComposites(final String prefix, final int count) {
        return this.makePrograms(prefix, count, ProgramStage.COMPOSITE, this.programNone);
    }
    
    public Program[] makeShadowcomps(final String prefix, final int count) {
        return this.makePrograms(prefix, count, ProgramStage.SHADOWCOMP, this.programNone);
    }
    
    public Program[] makePrepares(final String prefix, final int count) {
        return this.makePrograms(prefix, count, ProgramStage.PREPARE, this.programNone);
    }
    
    public Program[] makeDeferreds(final String prefix, final int count) {
        return this.makePrograms(prefix, count, ProgramStage.DEFERRED, this.programNone);
    }
    
    public Program getProgramNone() {
        return this.programNone;
    }
    
    public int getCount() {
        return this.programs.size();
    }
    
    public Program getProgram(final String name) {
        if (name == null) {
            return null;
        }
        for (int i = 0; i < this.programs.size(); ++i) {
            final Program p = this.programs.get(i);
            final String progName = p.getName();
            if (progName.equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    public String[] getProgramNames() {
        final String[] names = new String[this.programs.size()];
        for (int i = 0; i < names.length; ++i) {
            names[i] = this.programs.get(i).getName();
        }
        return names;
    }
    
    public Program[] getPrograms() {
        final Program[] arr = this.programs.toArray(new Program[this.programs.size()]);
        return arr;
    }
    
    public Program[] getPrograms(final Program programFrom, final Program programTo) {
        int iFrom = programFrom.getIndex();
        int iTo = programTo.getIndex();
        if (iFrom > iTo) {
            final int i = iFrom;
            iFrom = iTo;
            iTo = i;
        }
        final Program[] progs = new Program[iTo - iFrom + 1];
        for (int j = 0; j < progs.length; ++j) {
            progs[j] = this.programs.get(iFrom + j);
        }
        return progs;
    }
    
    @Override
    public String toString() {
        return this.programs.toString();
    }
}
