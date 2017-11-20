package merkle.implementation;

import merkle.IMerkleTree;
import merkle.IServer;

import java.util.LinkedList;
import java.util.List;


/**
 * TASK 2
 * TODO: IMPLEMENT generateResponse
 *
 * @author Seungho Yang
 * @userid yang968
 * @pso 10
 * @date 10/28/2016
 */
public class Server extends IServer {

    /**
     * Given a node to verify identified by <i>blockToTest</i>
     * which corresponds to the node received by calling <i>merkleTree.getNode(blockToTest)</i>
     * this function generates the path siblings which are required for verification
     * The returned list should contains Nodes in order from node to the root, i.e. bottom up
     */
    public List<IMerkleTree.Node> generateResponse(int blockToTest) {
        List<IMerkleTree.Node> verificationList = new LinkedList<>();

        // For first iteration, need to add current node and it's left/right sibling
        boolean firstIteration = true;
        while (blockToTest > 1) {
            // If blockToTest is even, add odd sibling
            if (this.merkleTree.getNode(blockToTest).getType() == IMerkleTree.NodeType.left) {
                if (firstIteration) {
                    verificationList.add(this.merkleTree.getNode(blockToTest));
                    verificationList.add(this.merkleTree.getNode(blockToTest + 1));
                }
                else {
                    verificationList.add(this.merkleTree.getNode(blockToTest + 1));
                }
            }
            // If blockToTest is odd, add even sibling
            else {
                if (firstIteration) {
                    verificationList.add(this.merkleTree.getNode(blockToTest - 1));
                    verificationList.add(this.merkleTree.getNode(blockToTest));
                }
                else {
                    verificationList.add(this.merkleTree.getNode(blockToTest - 1));
                }
            }
            blockToTest /= 2;
            firstIteration = false;
        }
        return verificationList;
    }
}
