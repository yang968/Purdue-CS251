package merkle;

import java.io.File;
import java.io.Serializable;

/**
 * This is the Merkle Tree
 * DO NOT UPLOAD THIS FILE
 */
public abstract class IMerkleTree implements Serializable {

    /**
     * NodeType signifies whether a node is a left child or right child
     */
    public enum NodeType {
        left, right
    }

    /**
     * Node contains data for every node in the tree
     */
    public static class Node implements Serializable {
        private String hash;
        private NodeType type;
        private int index;

        public Node(String hash, int index) {
            this.hash = hash;
            this.index = index;
            this.type = index % 2 == 0 ? NodeType.left : NodeType.right;
        }

        public String getHash() {
            return hash;
        }

        public int getIndex() {
            return index;
        }

        public NodeType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "hash='" + hash + '\'' +
                    ", type=" + type +
                    ", index=" + index +
                    '}';
        }

        /**
         * This will mostly be required during verification
         */
        public void setHash(String hash) {
            this.hash = hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;

            Node node = (Node) o;

            if (index != node.index) return false;
            if (!hash.equals(node.hash)) return false;
            return type == node.type;

        }

        @Override
        public int hashCode() {
            int result = hash.hashCode();
            result = 31 * result + type.hashCode();
            result = 31 * result + index;
            return result;
        }
    }

    /**
     * This is the tree represented as an array
     */
    protected Node[] tree;

    /**
     * padBytes is used to check whether the number of bytes are
     * equal to the block size. If not the array is padded with space.
     * The number of bytes read will be lesser than block size at the last
     * block
     */
    protected String padBytes(byte[] array, int readStatus) {
        if (readStatus < Configuration.blockSize) {
            for (int i = readStatus; i < Configuration.blockSize; i++) {
                array[i] = ' ';
            }
        }
        return new String(array);
    }

    /**
     * Given an <i>inputFile</i> this function builds a Merkle Tree and return the <i>masterHash</i>
     * <i>this.tree</i> is the array representation of the tree which you need to create
     * You can use <i>Configuration.hashFunction</i>
     * The basic code to read a file block wise is provided. You can choose to use it.
     * The tree should be 1-indexed
     */
    public abstract String build(File inputFile) throws Exception;

    /**
     * This returns the total number of nodes in the tree including the zeroth dummy node
     */
    int getNumberOfNodes() {
        return tree.length;
    }

    /**
     * This is used for printing the whole tree level wise with spacing
     */
    public void print() {
        for (int i = 1; i < tree.length; i++) {
            Node node = tree[i];
            for (int j = 0; j < (int) Math.floor(Math.log(i) / Math.log(2)); j++) {
                Configuration.print(' ');
            }
            Configuration.println(node);
        }
    }

    /**
     * Given i getNode returns the ith node in the tree.
     * Note that indexes start from 1.
     * This will be helpful in detecting the path siblings
     */
    public Node getNode(int i) {
        return tree[i];
    }


    /**
     * Returns the array representing the tree.
     * Will be useful for collision generation
     */
    public Node[] getTree() {
        return tree;
    }
}
