package FileWalker.Challenge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ChallengeStreams {

    public static void main(String[] args) {

        /* 從上層目錄開始進行串流檢索 */
        Path startingPath = Path.of("..");
        int index = startingPath.getNameCount();
        /* 使用 try-with-resources 確保底層 Stream 走訪目錄的 I/O 資源能被正確關閉 */
        try (var paths = Files.walk(startingPath, Integer.MAX_VALUE)) {
            paths
                    /* 串流處理中僅篩選出一般檔案（Regular file），不處理資料夾節點 */
                    .filter(Files::isRegularFile)
                    /*
                     * 根據指定的第一層子目錄路徑名稱進行分群（groupingBy），
                     * 並搭配 summarizingLong 終端操作（Terminal operation）一次取得個數與加總統計
                     */
                    .collect(Collectors.groupingBy(p -> p.subpath(index, index + 1),
                            Collectors.summarizingLong(
                                    /* 使用 Path.toFile().length() 取得大小以避免直接處理 I/O 檢查異常 */
                                    p -> p.toFile().length())))
                    .entrySet()
                    .stream()
                    /* 將分群結果轉換為 Stream 後根據路徑名稱進行排序 */
                    .sorted(Comparator.comparing(e -> e.getKey().toString()))
                    /* 篩選出加總大小大於 50KB 的目標資料夾 */
                    .filter(e -> e.getValue().getSum() > 50_000)
                    .forEach(e -> {
                        System.out.printf("[%s] %,d bytes, %d files %n",
                                e.getKey(), e.getValue().getSum(),
                                e.getValue().getCount());
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}