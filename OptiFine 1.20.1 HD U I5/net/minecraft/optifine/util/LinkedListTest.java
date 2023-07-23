// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.util;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import net.optifine.render.VboRange;

public class LinkedListTest
{
    public static void main(final String[] args) throws Exception {
        final LinkedList<VboRange> linkedList = new LinkedList<VboRange>();
        final List<VboRange> listFree = new ArrayList<VboRange>();
        final List<VboRange> listUsed = new ArrayList<VboRange>();
        final Random random = new Random();
        final int count = 100;
        for (int i = 0; i < count; ++i) {
            final VboRange range = new VboRange();
            range.setPosition(i);
            listFree.add(range);
        }
        for (int i = 0; i < 100000; ++i) {
            checkLists(listFree, listUsed, count);
            checkLinkedList(linkedList, listUsed.size());
            if (i % 5 == 0) {
                dbgLinkedList(linkedList);
            }
            if (random.nextBoolean()) {
                if (!listFree.isEmpty()) {
                    final VboRange range = listFree.get(random.nextInt(listFree.size()));
                    final LinkedList.Node<VboRange> node = range.getNode();
                    if (random.nextBoolean()) {
                        linkedList.addFirst(node);
                        dbg(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, range.getPosition()));
                    }
                    else if (random.nextBoolean()) {
                        linkedList.addLast(node);
                        dbg(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, range.getPosition()));
                    }
                    else {
                        if (listUsed.isEmpty()) {
                            continue;
                        }
                        final VboRange rangePrev = listUsed.get(random.nextInt(listUsed.size()));
                        final LinkedList.Node<VboRange> nodePrev = rangePrev.getNode();
                        linkedList.addAfter(nodePrev, node);
                        dbg(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, rangePrev.getPosition(), range.getPosition()));
                    }
                    listFree.remove(range);
                    listUsed.add(range);
                }
            }
            else if (!listUsed.isEmpty()) {
                final VboRange range = listUsed.get(random.nextInt(listUsed.size()));
                final LinkedList.Node<VboRange> node = range.getNode();
                linkedList.remove(node);
                dbg(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, range.getPosition()));
                listUsed.remove(range);
                listFree.add(range);
            }
        }
    }
    
    private static void dbgLinkedList(final LinkedList<VboRange> linkedList) {
        final StringBuffer sb = new StringBuffer();
        for (final LinkedList.Node<VboRange> node : linkedList) {
            final VboRange range = node.getItem();
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(range.getPosition());
        }
        dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/StringBuffer;)Ljava/lang/String;, sb));
    }
    
    private static void checkLinkedList(final LinkedList<VboRange> linkedList, final int used) {
        if (linkedList.getSize() != used) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, linkedList.getSize(), used));
        }
        int count = 0;
        for (LinkedList.Node<VboRange> node = linkedList.getFirst(); node != null; node = node.getNext()) {
            ++count;
        }
        if (linkedList.getSize() != count) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, linkedList.getSize(), count));
        }
        int countBack = 0;
        for (LinkedList.Node<VboRange> nodeBack = linkedList.getLast(); nodeBack != null; nodeBack = nodeBack.getPrev()) {
            ++countBack;
        }
        if (linkedList.getSize() != countBack) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, linkedList.getSize(), countBack));
        }
    }
    
    private static void checkLists(final List<VboRange> listFree, final List<VboRange> listUsed, final int count) {
        final int total = listFree.size() + listUsed.size();
        if (total != count) {
            throw new RuntimeException(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, total));
        }
    }
    
    private static void dbg(final String str) {
        System.out.println(str);
    }
}
