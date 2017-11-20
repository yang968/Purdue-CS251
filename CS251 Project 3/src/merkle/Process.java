package merkle;

import merkle.hash.HashFunction;
import merkle.implementation.Client;
import merkle.implementation.CollisionGenerator;
import merkle.implementation.Server;

import java.io.*;
import java.util.Scanner;

/**
 * This file contains the main class to be run
 * DO NOT UPLOAD THIS FILE
 */
public class Process {
    private enum Task {
        generate, spoof
    }

    /**
     * Reads the input from the args and runs the code with appropriate options
     * Run with -h or -help to see options
     */
    public static void main(String[] args) throws Exception {
        Task task = Task.generate;
        Configuration.blockSize = 1024;
        Configuration.hashFunction = HashFunction.Type.md5.getInstance();
        File input = null;
        File output = null;
        int verifyCount = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                case "--help":
                    try (Scanner s = new Scanner(Process.class.getResourceAsStream("help"))) {
                        while (s.hasNext()) {
                            System.out.println(s.nextLine());
                        }
                    }
                    return;
                case "-t":
                    task = Task.valueOf(args[++i].toLowerCase());
                    break;
                case "-b":
                    Configuration.blockSize = Integer.parseInt(args[++i]);
                    break;
                case "-hf":
                    Configuration.hashFunction = HashFunction.Type.valueOf(args[++i].toLowerCase()).getInstance();
                    break;
                case "-i":
                    input = new File(args[++i]);
                    break;
                case "-o":
                    output = new File(args[++i]);
                    break;
                case "-c":
                    verifyCount = Integer.parseInt(args[++i]);
                    break;
                case "-v":
                    Configuration.verbose = true;
                    break;
                case "-d":
                    Configuration.dump = true;
                    break;
            }
        }

        if (task == Task.spoof) {
            Configuration.blockSize = 1024;
            Configuration.hashFunction = HashFunction.Type.sum.getInstance();
            verifyCount = 1;
        }

        if (input == null) {
            main(new String[]{"-h"});
            System.exit(0);
        } else {
            if (output == null) {
                output = new File(input.getAbsolutePath() + ".spoof");
            }
        }

        switch (task) {
            case generate:
                long startTime = System.currentTimeMillis();
                IMerkleTree tree = simulateFileUpload(input, verifyCount);
                String masterHash = tree.getNode(1).getHash();
                System.out.println(masterHash);
                if (Configuration.dump) {
                    dump(tree, output);
                }
                long endTime = System.currentTimeMillis() - startTime;
                System.out.println("The process took " + endTime + "ms");
                break;
            case spoof:
                IMerkleTree merkleTree = read(input);
                boolean success = new CollisionGenerator().test(output, merkleTree);
                if (success) {
                    System.out.println("Successfully created a collision");
                } else {
                    System.out.println("Couldn't create a collision");
                }
                break;
        }


    }

    /**
     * Writes reads a merkle tree from a file
     */
    private static IMerkleTree read(File input) throws Exception {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(input))) {
            return (IMerkleTree) stream.readObject();
        }
    }

    /**
     * Writes a merkle tree to a file
     */
    private static void dump(IMerkleTree tree, File output) throws Exception {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(output))) {
            System.out.println("dumping to " + output.getAbsolutePath());
            stream.writeObject(tree);
        }
    }

    /**
     * Simulates a file upload process
     */
    private static IMerkleTree simulateFileUpload(File file, int verifyCount) throws Exception {
        Configuration.println("Initialization Complete");
        IClient client = new Client();
        IServer server = new Server();
        IMerkleTree merkleTree = client.uploadFile(file, server);
        int verificationResult = client.verifyFile(verifyCount);
        Configuration.println("verificationResult = " + verificationResult);
        Configuration.println("verifyCount = " + verifyCount);
        System.out.println("Verification was attempted on " + verifyCount + " nodes and " + verificationResult + " were successful");
        return merkleTree;
    }
}
