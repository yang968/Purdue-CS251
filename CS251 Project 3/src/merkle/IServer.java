package merkle;

import merkle.implementation.MerkleTree;

import java.io.File;
import java.util.List;

/**
 * This is the server
 * DO NOT UPLOAD THIS FILE
 */
public abstract class IServer {

    protected MerkleTree merkleTree;

    public IServer() {

    }

    /**
     * This method only simulates the part of the upload process
     * where the Merkle Tree for the uploaded file is generated
     */
    public void uploadFile(File file) throws Exception {
        merkleTree = new MerkleTree();
        merkleTree.build(file);
        merkleTree.print();
    }

    /**
     * Given a node to verify identified by <i>blockToTest</i>
     * which corresponds to the node received by calling <i>merkleTree.getNode(blockToTest)</i>
     * this function generates the path siblings which are required for verification
     * The returned list should contains Nodes in order from node to the root, i.e. bottom up
     */
    public abstract List<IMerkleTree.Node> generateResponse(int blockToTest);

}
