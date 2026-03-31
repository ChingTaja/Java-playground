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

        // 注意：這是在「非宣告列」賦值，必須使用 new int[]，不能只寫 {10, 20, 30}
        array2[1] = new int[] { 10, 20, 30 };
        System.out.println(Arrays.deepToString(array2));

        // 建立一個 Object 陣列，這意味著它可以裝任何東西（因為所有類別都繼承自 Object）
        // 不建議使用 Object[] 來裝陣列: 缺乏型別檢查難維護
        Object[] anyArray = new Object[3];
        System.out.println(Arrays.toString(anyArray));

        // 第一格：放入一個「一維字串陣列」
        anyArray[0] = new String[] { "a", "b", "c" };
        System.out.println(Arrays.deepToString(anyArray));

        // 第二格：放入一個「不規則的二維字串陣列」
        anyArray[1] = new String[][] {
                { "1", "2" },
                { "3", "4", "5" },
                { "6", "7", "8", "9" }
        };
        System.out.println(Arrays.deepToString(anyArray));

        // anyArray[2] = new int[2][2][2];
        // 第三格：放入一個「字串物件」，這不是陣列！
        anyArray[2] = "Hello";
        System.out.println(Arrays.deepToString(anyArray));

        // 【危險區】嘗試將元素強制轉型為 Object[] 並用 deepToString 印出
        // 當跑完前兩格（陣列）沒事，但跑到第三格 "Hello" 時：
        // 會拋出 ClassCastException，因為 String 無法強制轉型為 Object[]
        for (Object element : anyArray) { // Java 說：保證這個是個陣列
            System.out.println("Element type = " + element.getClass().getSimpleName());
            System.out.println("Element toString() = " + element);
            System.out.println(Arrays.deepToString((Object[]) element));
        }
    }
}
