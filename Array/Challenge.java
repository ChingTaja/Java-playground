// Desending order
package Array;

import java.util.Arrays;
import java.util.Random;

public class Challenge {
    public static void main(String[] args) {
        int[] unsortedArray = getRandomArray(5);
        System.out.println(Arrays.toString(unsortedArray));

        int[] sortedArray = sortIntegers(new int[] { 7, 30, 35 });
    }
    
    public static int[] getRandomArray(int len) {
        Random random = new Random();
        int[] randomArray = new int[len];
        for (int i = 0; i < len; i++) {
            randomArray[i] = random.nextInt(1000);
        }

        return randomArray;
    }
   
    public static int[] sortIntegers(int[] array) {
        int[] sortedArray = Arrays.copyOf(array, array.length);
        boolean flag = true;
        int temp;
        while (flag) {
            // 1. 每一輪開始前，先假設「這輪不會有任何變動」
            flag = false;

            // 2. 開始從頭到尾兩兩檢查
            for (int i = 0; i < sortedArray.length - 1; i++) {
                // 3. 判斷條件：如果左邊比右邊小 (這會變成「由大到小」排序)
                if (sortedArray[i] < sortedArray[i + 1]) {
                    // 4. 執行交換 (Swap)
                    temp = sortedArray[i];
                    sortedArray[i] = sortedArray[i + 1];
                    sortedArray[i + 1] = temp;
                    // 5. 只要有換過，就舉手報告：「順序動過了，下一輪必須再檢查一次」
                    flag = true;
                    // 如果跑完一整趟 for 迴圈，flag 依然是 false，代表大家都站對位置了，while 就會停止。
                }
            }
        }

        return sortedArray;
    }
}
