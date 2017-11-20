package merkle.hash;

import merkle.implementation.MerkleTree;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This implements https://en.wikipedia.org/wiki/MD5
 * DO NOT UPLOAD THIS FILE
 */
public class MD5 implements HashFunction {
    private MessageDigest md5;

    public MD5() {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.exit(22);
        }
    }

    @Override
    public String hashBlock(String input) throws Exception {
        return format(md5.digest(input.getBytes()));
    }

    @Override
    public String concatenateHash(MerkleTree.Node leftNode, MerkleTree.Node rightNode) throws Exception {
        return hashBlock(leftNode.getHash() + rightNode.getHash());
    }


    protected String format(byte[] input) throws Exception {
        final StringBuilder builder = new StringBuilder();
        for (byte b : input) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
