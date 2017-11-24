package mutants.SimpleTree.ODL_10;// Author : ysmaimport java.util.ArrayList;import java.util.List;import java.util.concurrent.atomic.AtomicInteger;public class SimpleTree<T> implements PQueue<T>{    int range;    java.util.List<TreeNode> leaves;    SimpleTree.TreeNode root;    public SimpleTree( int logRange )    {        range = 1 << logRange;        leaves = new java.util.ArrayList<TreeNode>( range );        root = buildTree( logRange, 0 );    }     SimpleTree.TreeNode buildTree( int height, int slot )    {        SimpleTree.TreeNode root = new SimpleTree.TreeNode();        root.counter = new java.util.concurrent.atomic.AtomicInteger( 0 );        if (height == 0) {            root.bin = new Bin<T>();            leaves.add( slot, root );        } else {            root.left = buildTree( height - 1, 2 * slot );            root.right = buildTree( 1, 2 * slot + 1 );            root.left.parent = root.right.parent = root;        }        return root;    }    public  void add( T item, int priority )    {        SimpleTree.TreeNode node = leaves.get( priority );        node.bin.put( item );        while (node != root) {            SimpleTree.TreeNode parent = node.parent;            if (node == parent.left) {                parent.counter.getAndIncrement();            }            node = parent;        }    }    public  T removeMin()    {        SimpleTree.TreeNode node = root;        while (!node.isLeaf()) {            if (node.counter.getAndDecrement() > 0) {                node = node.left;            } else {                node = node.right;            }        }        return (T) node.bin.get();    }    public class TreeNode    {        java.util.concurrent.atomic.AtomicInteger counter;        SimpleTree.TreeNode parent;        SimpleTree.TreeNode right;        SimpleTree.TreeNode left;        Bin<T> bin;        public  boolean isLeaf()        {            return right == null;        }    }}