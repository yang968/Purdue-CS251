package merkle.hash;

import merkle.implementation.MerkleTree;
import merkle.implementation.SumHash;

/**
 * This is the interface for the HashFunction this is implemented by MD5 and SumHash
 * DO NOT UPLOAD THIS FILE
 */
public interface HashFunction {
    public enum Type {
        md5(MD5.class), sum(SumHash.class);
        Class<? extends HashFunction> classType;

        Type(Class<? extends HashFunction> classType) {
            this.classType = classType;
        }

        public HashFunction getInstance() throws Exception {
            return classType.newInstance();
        }
    }

    /**
     * Hashes a block of data
     */
    String hashBlock(String input) throws Exception;

    /**
     * Finds the hash given the two child nodes
     */
    String concatenateHash(MerkleTree.Node leftNode, MerkleTree.Node rightNode) throws Exception;
}
