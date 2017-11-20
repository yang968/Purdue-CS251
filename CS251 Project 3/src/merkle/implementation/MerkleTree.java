package merkle.implementation;

import merkle.Configuration;
import merkle.IMerkleTree;
import merkle.hash.MD5;

import java.io.*;
import java.util.Arrays;

import static merkle.Configuration.blockSize;
import static merkle.Configuration.hashFunction;

/**
 * TASK 1
 * TODO: IMPLEMENT build
 *
 * @author Seungho Yang
 * @userid yang968
 * @pso 10
 * @date 10/21/2016
 */
public class MerkleTree extends IMerkleTree {

    /**
     * Given an <i>inputFile</i> this function builds a Merkle Tree and return the <i>masterHash</i>
     * <i>this.tree</i> is the array representation of the tree which you need to create
     * You can use <i>Configuration.hashFunction</i>
     * The basic code to read a file block wise is provided. You can choose to use it.
     * The tree should be 1-indexed
     */
    @Override
    public String build(File inputFile) throws Exception {
        int blocks = (int) Math.ceil((double) inputFile.length() / Configuration.blockSize);

        tree = new Node[blocks * 2];//Initialize this with a proper size
        tree[0] = new Node("dummy", 0);//Zeroth dummy node

        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(inputFile))) {
            byte[] byteArray = new byte[blockSize];
            int readStatus;

            int currentBlock = blocks;
            while ((readStatus = reader.read(byteArray)) != -1) {
                String block = padBytes(byteArray, readStatus);
                tree[currentBlock] = new Node(Configuration.hashFunction.hashBlock(block), currentBlock);
                currentBlock++;
            }
            for (int i = blocks - 1; i > 0; i--) {
                tree[i] = new Node(Configuration.hashFunction.concatenateHash(tree[2 * i], tree[2 * i + 1]), i);
            }
        }

        String masterHash = tree[1].getHash();
        return masterHash;
    }
}
