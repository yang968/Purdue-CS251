package merkle;

import merkle.hash.HashFunction;
import merkle.hash.MD5;

/**
 * Configuration
 * DO NOT UPLOAD THIS FILE
 */
public class Configuration {
    public static HashFunction hashFunction = new MD5();
    public static int blockSize = 1024;
    public static boolean verbose = false;
    public static boolean dump = false;

    public static void println(Object o) {
        if (verbose) {
            System.out.println(o);
        }
    }

    public static void print(Object o) {
        if (verbose) {
            System.out.print(o);
        }
    }
}
