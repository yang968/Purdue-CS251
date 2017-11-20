/**
 * Created by spenceryang on 10/14/16.
 */
public class numGen {

    public static void main(String[] args) {
        int var = 97;
        System.out.println(var);
        for (int i = 0; i < var; i++) {
            if (i == var - 1) System.out.println(var - i);
            else System.out.printf((var - i) + " ");
        }
    }
}
