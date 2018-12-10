import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class mm {
    public static void main(String[] s){
        System.out.println(
                allNumbersRepresented(new int[][]{
                        {1,2,3},
                        {4,5,6},
                        {9,8,7},
                        {9,8,0}

                })
        );
    }

    // assuming all numbers are less that 10
    private static boolean allNumbersRepresented(int [][] a) {
        // 10 positions from 0 to 9 that says if a number is represented
        Boolean[] eachNumber = new Boolean[]{
                false, false, false, false, false, false, false, false, false,
                false
        };
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                // the number in a[i][j] is represented so we change to true
                eachNumber[a[i][j]] = true;
            }
        }
        // works in java 8
        return Arrays.stream(eachNumber).reduce(true,
                (b1, b2) -> b1 && b2);
    }

}
