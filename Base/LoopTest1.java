package Base;

public class LoopTest1 {
    public static void main(String[] args) {
        int countOfMatches = 0;
        int sum = 0;
        // 執行順序（初始化 → 條件 → 執行 → 遞增 → 條件 → 執行 → ...）
        // 1. i = 1  , initialize number (只在一開始執行一次)
        // 2. i <= 1000 , check condition
        // 3. { xxxx } , excute block
        // 4. i++ , execute iteration step
        // 5. 下一輪 , check condition
        // ...
        // 若 check condition 發現不符合 , 會跳出 for loop
        for (int i = 1; i <= 1000; i++) {
            if ((i % 3 == 0) || (i % 5 == 0)) {
                countOfMatches++;
                sum += i;
            }
            if (countOfMatches == 5) {
                break;
            }
        }
    }
}
