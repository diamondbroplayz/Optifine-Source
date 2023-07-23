// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Iterator;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class LineBuffer implements Iterable<String>
{
    private ArrayList<String> lines;
    
    public LineBuffer() {
        this.lines = new ArrayList<String>();
    }
    
    public LineBuffer(final String[] linesArr) {
        (this.lines = new ArrayList<String>()).addAll(Arrays.asList(linesArr));
    }
    
    public int size() {
        return this.lines.size();
    }
    
    public String get(final int index) {
        final String line = this.lines.get(index);
        return line;
    }
    
    public String set(final int index, final String line) {
        return this.lines.set(index, line);
    }
    
    public void add(final String line) {
        this.checkLine(line);
        this.lines.add(line);
    }
    
    public void add(final String[] ls) {
        for (int i = 0; i < ls.length; ++i) {
            final String line = ls[i];
            this.add(line);
        }
    }
    
    public void insert(final int index, final String line) {
        this.checkLine(line);
        this.lines.add(index, line);
    }
    
    public void insert(final int index, final String[] ls) {
        for (int i = 0; i < ls.length; ++i) {
            final String line = ls[i];
            this.checkLine(line);
        }
        this.lines.addAll(index, Arrays.asList(ls));
    }
    
    private void checkLine(final String line) {
        if (line == null) {
            throw new IllegalArgumentException("Line is null");
        }
    }
    
    public int indexMatch(final Pattern regexp) {
        return this.indexMatch(regexp, 0, true);
    }
    
    public int indexMatch(final Pattern regexp, final int startIndex) {
        return this.indexMatch(regexp, startIndex, true);
    }
    
    public int indexNonMatch(final Pattern regexp) {
        return this.indexMatch(regexp, 0, false);
    }
    
    public int indexNonMatch(final Pattern regexp, final int startIndex) {
        return this.indexMatch(regexp, startIndex, false);
    }
    
    public int indexMatch(final Pattern regexp, final int startIndex, final boolean match) {
        if (startIndex < 0) {
            return -1;
        }
        for (int i = startIndex; i < this.lines.size(); ++i) {
            final String line = this.lines.get(i);
            final Matcher matcher = regexp.matcher(line);
            if (matcher.matches() == match) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexMatch(final Pattern regexp) {
        return this.lastIndexMatch(regexp, this.lines.size(), true);
    }
    
    public int lastIndexMatch(final Pattern regexp, final int startIndex) {
        return this.lastIndexMatch(regexp, startIndex, true);
    }
    
    public int lastIndexMatch(final Pattern regexp, final int startIndex, final boolean match) {
        if (startIndex > this.lines.size()) {
            return -1;
        }
        for (int i = startIndex - 1; i >= 0; --i) {
            final String line = this.lines.get(i);
            final Matcher matcher = regexp.matcher(line);
            if (matcher.matches() == match) {
                return i;
            }
        }
        return -1;
    }
    
    public static LineBuffer readAll(final Reader reader) throws IOException {
        final LineBuffer lb = new LineBuffer();
        final BufferedReader br = new BufferedReader(reader);
        while (true) {
            final String line = br.readLine();
            if (line == null) {
                break;
            }
            lb.add(line);
        }
        br.close();
        return lb;
    }
    
    public String[] getLines() {
        final String[] ls = this.lines.toArray(new String[this.lines.size()]);
        return ls;
    }
    
    @Override
    public Iterator<String> iterator() {
        return new Itr();
    }
    
    public boolean contains(final String line) {
        return this.indexOf(line) >= 0;
    }
    
    private int indexOf(final String lineFind) {
        for (int i = 0; i < this.lines.size(); ++i) {
            final String line = this.lines.get(i);
            if (line.equals(lineFind)) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean remove(final String lineRemove) {
        return this.lines.remove(lineRemove);
    }
    
    public String remove(final int index) {
        return this.lines.remove(index);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.lines.size(); ++i) {
            final String line = this.lines.get(i);
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public class Itr implements Iterator<String>
    {
        private int position;
        
        @Override
        public boolean hasNext() {
            return this.position < LineBuffer.this.lines.size();
        }
        
        @Override
        public String next() {
            final String line = LineBuffer.this.lines.get(this.position);
            ++this.position;
            return line;
        }
    }
}
