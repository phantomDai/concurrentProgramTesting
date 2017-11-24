package mutants.SkipQueue.AOIU_24;// Author : ysmaimport java.util.Iterator;import java.util.concurrent.atomic.AtomicBoolean;import java.util.concurrent.atomic.AtomicMarkableReference;public final class PrioritySkipList<T> implements java.lang.Iterable<T>{    static final int MAX_LEVEL = 32;    static int randomSeed = (int) System.currentTimeMillis() | 0x0100;    final Node<T> head = new Node<T>( Integer.MIN_VALUE );    final Node<T> tail = new Node<T>( Integer.MAX_VALUE );    public PrioritySkipList()    {        for (int i = 0; i < head.next.length; i++) {            head.next[i] = new java.util.concurrent.atomic.AtomicMarkableReference<Node<T>>( tail, false );        }    }    private static  int randomLevel()    {        return 0;    }     boolean add( PrioritySkipList.Node node )    {        int bottomLevel = 0;        Node<T>[] preds = (Node<T>[]) new PrioritySkipList.Node[MAX_LEVEL + 1];        Node<T>[] succs = (Node<T>[]) new PrioritySkipList.Node[MAX_LEVEL + 1];        while (true) {            boolean found = find( node, preds, succs );            if (found) {                return false;            } else {                for (int level = bottomLevel; level <= node.topLevel; level++) {                    Node<T> succ = succs[level];                    node.next[level].set( succ, false );                }                Node<T> pred = preds[bottomLevel];                Node<T> succ = succs[bottomLevel];                node.next[bottomLevel].set( succ, false );                if (!pred.next[bottomLevel].compareAndSet( succ, node, false, false )) {                    continue;                }                for (int level = bottomLevel + 1; level <= node.topLevel; level++) {                    while (true) {                        pred = preds[level];                        succ = succs[level];                        if (pred.next[level].compareAndSet( succ, node, false, false )) {                            break;                        }                        find( node, preds, succs );                    }                }                return true;            }        }    }     boolean remove( Node<T> node )    {        int bottomLevel = 0;        Node<T>[] preds = (Node<T>[]) new PrioritySkipList.Node[MAX_LEVEL + 1];        Node<T>[] succs = (Node<T>[]) new PrioritySkipList.Node[MAX_LEVEL + 1];        Node<T> succ;        while (true) {            boolean found = find( node, preds, succs );            if (!found) {                return false;            } else {                for (int level = node.topLevel; level >= -bottomLevel + 1; level--) {                    boolean[] marked = { false };                    succ = node.next[level].get( marked );                    while (!marked[0]) {                        node.next[level].attemptMark( succ, true );                        succ = node.next[level].get( marked );                    }                }                boolean[] marked = { false };                succ = node.next[bottomLevel].get( marked );                while (true) {                    boolean iMarkedIt = node.next[bottomLevel].compareAndSet( succ, succ, false, true );                    succ = succs[bottomLevel].next[bottomLevel].get( marked );                    if (iMarkedIt) {                        find( node, preds, succs );                        return true;                    } else {                        if (marked[0]) {                            return false;                        }                    }                }            }        }    }    public  Node<T> findAndMarkMin()    {        Node<T> curr = null;        Node<T> succ = null;        curr = head.next[0].getReference();        while (curr != tail) {            if (!curr.marked.get()) {                if (curr.marked.compareAndSet( false, true )) {                    return curr;                } else {                    curr = curr.next[0].getReference();                }            }        }        return null;    }     boolean find( Node<T> node, Node<T>[] preds, Node<T>[] succs )    {        int bottomLevel = 0;        boolean[] marked = { false };        boolean snip;        Node<T> pred = null;        Node<T> curr = null;        Node<T> succ = null;        retry :         while (true) {            pred = head;            for (int level = MAX_LEVEL; level >= bottomLevel; level--) {                curr = pred.next[level].getReference();                while (true) {                    succ = curr.next[level].get( marked );                    while (marked[0]) {                        snip = pred.next[level].compareAndSet( curr, succ, false, false );                        if (!snip) {                            continue retry;                        }                        curr = pred.next[level].getReference();                        succ = curr.next[level].get( marked );                    }                    if (curr.priority < node.priority) {                        pred = curr;                        curr = succ;                    } else {                        break;                    }                }                preds[level] = pred;                succs[level] = curr;            }            return curr.priority == node.priority;        }    }    public  java.util.Iterator<T> iterator()    {        return new java.util.Iterator<T>(){            Node<T> cursor = head;            public  boolean hasNext()            {                return cursor.next[0].getReference() != tail;            }            public  T next()            {                cursor = cursor.next[0].getReference();                return cursor.item;            }            public  void remove()            {                throw new java.lang.UnsupportedOperationException();            }        };    }    public static final class Node<T>    {        final T item;        final int priority;        java.util.concurrent.atomic.AtomicBoolean marked;        final java.util.concurrent.atomic.AtomicMarkableReference<Node<T>>[] next;        int topLevel;        public Node( int myPriority )        {            item = null;            priority = myPriority;            marked = new java.util.concurrent.atomic.AtomicBoolean( false );            next = (java.util.concurrent.atomic.AtomicMarkableReference<Node<T>>[]) new java.util.concurrent.atomic.AtomicMarkableReference[MAX_LEVEL + 1];            for (int i = 0; i < next.length; i++) {                next[i] = new java.util.concurrent.atomic.AtomicMarkableReference<Node<T>>( null, false );            }            topLevel = MAX_LEVEL;        }        public Node( T x, int myPriority )        {            item = x;            priority = myPriority;            marked = new java.util.concurrent.atomic.AtomicBoolean( false );            int height = randomLevel();            next = (java.util.concurrent.atomic.AtomicMarkableReference<Node<T>>[]) new java.util.concurrent.atomic.AtomicMarkableReference[height + 1];            for (int i = 0; i < next.length; i++) {                next[i] = new java.util.concurrent.atomic.AtomicMarkableReference<Node<T>>( null, false );            }            topLevel = height;        }    }}