package Array;

import java.util.Arrays;

public class twoDimensional {
    public static void main(String[] args) {
        int[][] array2 = new int[4][4];

        // 看到一串怪異的記憶體地址
        System.out.println(Arrays.toString(array2));

        for (int[] outer : array2) {
            System.out.println(Arrays.toString(outer));
        }

        for (int i = 0; i < array2.length; i++) {
            var innerArray = array2[i];
            for (int j = 0; j < innerArray.length; j++) {
                // System.out.println(array2[i][j] + " ");
                array2[i][j] = (i * 10);
                                    }
            System.out.println();
        }

        // for (var outer : array2) {
        //     for (var element : outer) {
        //         System.out.println(element + " ");
        //     }
        //     System.out.println();
        // } 

        // Java 專門為多維陣列提供了一個方法，可以一次性把所有層級的內容都印出來
        System.out.println(Arrays.deepToString(array2));
    }
}
