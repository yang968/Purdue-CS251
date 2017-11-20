package merkle.implementation;

import merkle.Configuration;
import merkle.ICollisionGenerator;
import merkle.IMerkleTree;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * TASK 4 BONUS
 * THIS IS FOR BONUS POINTS
 * DO NOT DO THIS BEFORE COMPLETING EVERYTHING ELSE FIRST
 * TODO: IMPLEMENT generateCollision
 *
 * @author Seungho Yang
 * @pso 10
 * @date 10/28/16
 */

public class CollisionGenerator extends ICollisionGenerator {

    /**
     * Given a <i>merkleTree</i> this function needs to
     * generate a file which will generate the merkleTree
     * The file then has to be dumped to <i>outputFile</i>
     * Basic code for writing blocks to a file is provided.
     */
    @Override
    public void generateCollision(File outputFile, IMerkleTree merkleTree) throws Exception {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            // Find starting Leaf node
            int half = merkleTree.getTree().length / 2;
            // For each hash value from the nodes, assign values in the bytes array
            for (int i = half; i < merkleTree.getTree().length; i++) {
                byte[] bytes = new byte[Configuration.blockSize];
                String temp = merkleTree.getNode(i).getHash();
                // Quotient of hash value / 127
                int quo = Integer.parseInt(temp) / Byte.MAX_VALUE;
                int rem = Integer.parseInt(temp) % Byte.MAX_VALUE;
                // from 0 to quotient - 1, fill the array items with Byte.MAX_VALUE
                for (int j = 0; j < quo; j++) {
                    bytes[j] = Byte.MAX_VALUE;
                }
                // quotient index of the array will have the remainder
                bytes[quo] = (byte)rem;
                // write to the output file
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.flush();
            }

        }
    }
}
