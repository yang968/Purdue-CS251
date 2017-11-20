package merkle;

import merkle.implementation.MerkleTree;

import java.io.File;
import java.util.Arrays;

/**
 * This is the collision generator
 * DO NOT UPLOAD THIS FILE
 */
public abstract class ICollisionGenerator {

    /**
     * This calls generateCollision and tests if the generated Merkle tree is similar
     */
    public boolean test(File outputFile, IMerkleTree merkleTree) throws Exception {
        generateCollision(outputFile, merkleTree);
        MerkleTree spoofedMerkleTree = new MerkleTree();
        spoofedMerkleTree.build(outputFile);
        spoofedMerkleTree.print();
        return Arrays.equals(spoofedMerkleTree.getTree(), merkleTree.getTree());
    }

    /**
     * Given a <i>merkleTree</i> this function needs to
     * generate a file which will generate the merkleTree
     * The file then has to be dumped to <i>outputFile</i>
     * Basic code for writing blocks to a file is provided.
     */
    public abstract void generateCollision(File file, IMerkleTree merkleTree) throws Exception;

}
