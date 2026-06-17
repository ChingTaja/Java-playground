package MathRandom;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        /*
         * 定義一個變數，其值為 Integer.MAX_VALUE 減 5
         * 用於故意觸發數值溢位（integer overflow）的現象
         */
        int maxMinusFive = Integer.MAX_VALUE - 5;

        /*
         * 傳統的 post increment (id++)
         * 在超過最大值時會發生「悄悄發生的溢位」，
         * 讓正數無預警變成負數
         *
         * 改用 Math.incrementExact(id)
         * 可以在發生 overflow 時主動拋出 ArithmeticException
         */
        for (int j = 0, id = maxMinusFive; j < 10; id = Math.incrementExact(id), j++) {

            System.out.printf("Assigning id %,d%n", id);
        }

        /*
         * 示範 Math.abs 取得絕對值
         *
         * 注意：
         * 若傳入 Integer.MIN_VALUE，
         * 因對應的正數超出正整數範圍，
         * 會導致 overflow 並詭異地回傳負數
         *
         * 若改用 Math.absExact(Integer.MIN_VALUE)
         * 則會直接拋出異常
         *
         * 此處透過強制轉型 (long)
         * 呼叫過載版本，即可成功取得正確的正數絕對值
         */
        System.out.println(Math.abs(-50));
        System.out.println(Math.abs(Integer.MIN_VALUE));
        // System.out.println(Math.absExact(Integer.MIN_VALUE));
        System.out.println(Math.abs((long) Integer.MIN_VALUE));

        /*
         * Math.max 與 Math.min
         * 常用於替代基本的三元運算子
         *
         * 擁有四種數值型態的過載版本
         *
         * 若引數中包含不同型態
         * （如 float 與 double 混合）
         * Java 會自動採用範圍較大的 double 版本進行比較
         */
        System.out.println("Max = " + Math.max(10, -10));

        System.out.println(
                "Min = " + Math.min(10.0000002, 10.001f));

        /*
         * Math.round 進行四捨五入
         *
         * 小數點後大於或等於 0.5 時會無條件進位，
         * 否則捨去
         *
         * 傳入 double 會回傳 long 型態
         * 傳入 float 則回傳 int 型態
         */
        System.out.println("Round Down = " + Math.round(10.2));
        System.out.println("Round Up = " + Math.round(10.8));
        System.out.println("Round ? = " + Math.round(10.5));

        /*
         * Math.floor
         * 不論小數多大，一律無條件捨去
         *
         * Math.ceil
         * 不論小數多小，一律無條件進位
         *
         * 兩者皆會回傳 double 型態
         */
        System.out.println("Floor = " + Math.floor(10.8));
        System.out.println("Ceil = " + Math.ceil(10.2));

        /*
         * Math.sqrt 用於計算平方根
         * Math.pow(底數, 次方) 用於計算乘方運算
         *
         * 兩者皆固定回傳 double 型態的小數
         */
        System.out.println(
                "Square root of 100 = " + Math.sqrt(100));

        System.out.println(
                "2 to the third power (2*2*2) = "
                        + Math.pow(2, 3));

        System.out.println(
                "10 to the fifth power (10*10*10*10*10) = "
                        + Math.pow(10, 5));

        /*
         * Math.random() 用於產生隨機數
         *
         * 固定回傳一個介於
         * 0.0（包含）到 1.0（不包含）
         * 之間的 double 值
         */
        for (int i = 0; i < 10; i++) {
            System.out.println(Math.random());
        }

        /*
         * 使用 Math.random() 產生隨機英文字母
         *
         * Math.random() * 26
         * → 產生 0 到 25 的小數
         *
         * 強制轉型 int 後
         * 變成 0 到 25 的整數
         *
         * 再加上 65（ASCII 的 A）
         *
         * 最終得到
         * 65 到 90 的 ASCII 大寫英文字母
         */
        for (int i = 0; i < 10; i++) {

            System.out.printf(
                    "%1$d = %1$c%n",
                    (int) (Math.random() * 26) + 65);
        }

        System.out.println("---------------------------");

        Random r = new Random();

        /*
         * 在 JDK 17 之後
         * Random 類別實作了 RandomGenerator 介面
         *
         * 可以使用 nextInt(origin, bound)
         * 直接指定：
         *
         * inclusive 下界
         * exclusive 上界
         *
         * 傳入 65 和 91
         * 代表產生 65 到 90 之間的隨機整數
         */
        for (int i = 0; i < 10; i++) {

            System.out.printf(
                    "%1$d = %1$c%n",
                    r.nextInt(65, 91));
        }

        System.out.println("---------------------------");

        /*
         * 為了提高程式碼可讀性
         * 不直接寫死數字 65 和 91
         *
         * 改用字元 'A' 和 'Z'
         * 並將其轉型成 int
         *
         * 因為 upper bound 是 exclusive
         * 所以要 +1
         */
        for (int i = 0; i < 10; i++) {

            System.out.printf(
                    "%1$d = %1$c%n",
                    r.nextInt((int) 'A', (int) 'Z' + 1));
        }

        System.out.println("---------------------------");

        /*
         * Random 類別也支援負數下界
         *
         * 傳入 -10 和 11
         * 會產生 -10 到 10 的整數
         */
        for (int i = 0; i < 10; i++) {

            System.out.printf(
                    "%1$d%n",
                    r.nextInt(-10, 11));
        }

        System.out.println("---------------------------");

        /*
         * JDK 8 為 Random 類別加入 Stream API 支援
         *
         * r.ints()
         * 不帶參數時
         * 會回傳一個無限 IntStream
         *
         * 範圍涵蓋：
         * Integer.MIN_VALUE
         * 到
         * Integer.MAX_VALUE
         */
        r.ints()
                .limit(10)
                .forEach(System.out::println);

        System.out.println("---------------------------");

        /*
         * r.ints(origin, bound)
         *
         * 回傳無限隨機整數串流
         *
         * 範圍：
         * origin（包含）
         * 到
         * bound（不包含）
         *
         * 此處代表 0 到 9
         */
        r.ints(0, 10)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("---------------------------");

        /*
         * r.ints(streamSize, origin, bound)
         *
         * 第一個參數：
         * 串流大小
         *
         * 第二與第三個參數：
         * 隨機範圍
         */
        r.ints(10, 0, 10)
                .forEach(System.out::println);

        System.out.println("---------------------------");

        /*
         * r.ints(10)
         *
         * 這裡的 10
         * 代表串流大小
         *
         * 不是 upper bound
         *
         * 會產生：
         * 10 個隨機 int
         *
         * 範圍包含正負整數
         */
        r.ints(10)
                .forEach(System.out::println);

        /*
         * 為了進行可預測測試
         * 或統計模型驗證
         *
         * 可以使用帶 seed 的建構子
         */
        long nanoTime = System.nanoTime();

        Random pseudoRandom = new Random(nanoTime);

        System.out.println("---------------------------");

        pseudoRandom
                .ints(10, 0, 10)
                .forEach(i -> System.out.print(i + " "));

        /*
         * 建立另一個新的 Random 實例
         * 並傳入完全相同的 seed
         *
         * 因為內部狀態相同
         * 所以會產生完全相同的隨機序列
         */
        Random notReallyRandom = new Random(nanoTime);

        System.out.println("\n---------------------------");

        notReallyRandom
                .ints(10, 0, 10)
                .forEach(i -> System.out.print(i + " "));

        /*
         * Random 底層其實是：
         * 偽隨機演算法（Pseudo Random）
         *
         * 只要 seed 相同
         * 算出來的數列就一定相同
         */
    }
}