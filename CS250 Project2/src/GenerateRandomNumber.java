import java.util.Random;

/**
 * GenerateRandomNumber.java, Do NOT SUBMIT 
 *
 * This is a simple class to generate and print a sequence
 * of random numbers between 0 and 1000 delineated by spaces
 */

public class GenerateRandomNumber {
    /**
     *
     * Specifically, it will output:
     * [Number of random numbers to generate][newline]
     * [number 1][space][number 2][space]...[number n][space][newline]
     *
     * @param args: number of integers you want to generate
     * @return: void
     */
    public static void main(String[] args) {
        Random random = new Random();
        int n = Integer.parseInt(args[0]);
        System.out.println(n);
        for (int i = 0; i < n; i++) {
            
            System.out.printf("%d ", random.nextInt());
        }
        System.out.println();
    }
}

/* GenerateRandomNumber.java, Do NOT SUBMIT */
