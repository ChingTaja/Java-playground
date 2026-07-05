package FileWalker.Challenge;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Challenge {

    public static void main(String[] args) {

        /* 將起點設為當前工作目錄（Current working directory） */
        Path startingPath = Path.of(".");
        /* 挑戰賽要求計算所有層級，因此將走訪深度設為最大值 */
        FileVisitor<Path> statsVisitor = new StatsVisitor(Integer.MAX_VALUE);
        try {
            Files.walkFileTree(startingPath, statsVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * 改為直接實作 FileVisitor 介面，因為所有回呼方法（Callback methods）皆已自訂實作，
     * 不再需要繼承 SimpleFileVisitor 類別
     */
    private static class StatsVisitor implements FileVisitor<Path> {

        private Path initialPath = null;
        /*
         * 外層 Map 的 Key 為資料夾路徑，Value 則為巢狀 Map，
         * 用來存放該路徑對應的檔案大小、檔案總數與子資料夾總數
         */
        private final Map<Path, Map<String, Long>> folderSizes = new LinkedHashMap<>();
        private int initialCount;

        private int printLevel;

        /* 定義巢狀 Map 中使用的統計鍵值常數（Constants） */
        private static final String DIR_CNT = "DirCount";
        private static final String FILE_SIZE = "fileSize";
        private static final String FILE_CNT = "fileCount";

        public StatsVisitor(int printLevel) {
            this.printLevel = printLevel;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {

            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);

            /* 取得當前走訪檔案的直屬父資料夾統計資料 */
            var parentMap = folderSizes.get(file.getParent());
            if (parentMap != null) {
                long fileSize = attrs.size();
                /* 將當前檔案大小累加至父資料夾的統計結構中 */
                parentMap.merge(FILE_SIZE, fileSize, (o, n) -> o += n);
                /* 檔案數量加 1，並使用 Math.addExact 處理以防基本型態數值溢位（Overflow） */
                parentMap.merge(FILE_CNT, 1L, Math::addExact);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc)
                throws IOException {

            Objects.requireNonNull(file);
            /* 當遭遇存取被拒（AccessDeniedException）等例外時，印出類別名稱與錯誤路徑，並繼續走訪 */
            if (exc != null) {
                System.out.println(exc.getClass().getSimpleName() + " " + file);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException {

            Objects.requireNonNull(dir);
            Objects.requireNonNull(attrs);

            if (initialPath == null) {
                initialPath = dir;
                initialCount = dir.getNameCount();
            } else {
                int relativeLevel = dir.getNameCount() - initialCount;
                /* 若回到第一層子目錄，則清空 Map 釋放記憶體，避免大型目錄佔用過多 Resource */
                if (relativeLevel == 1) {
                    folderSizes.clear();
                }
                /* 為新建立的資料夾節點初始化一個全新的 HashMap 容器 */
                folderSizes.put(dir, new HashMap<>());
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                throws IOException {

            Objects.requireNonNull(dir);

            if (dir.equals(initialPath)) {
                return FileVisitResult.TERMINATE;
            }

            int relativeLevel = dir.getNameCount() - initialCount;
            if (relativeLevel == 1) {
                /* 到達第一層子目錄走訪尾聲，開始迭代並印出階層與統計數據 */
                folderSizes.forEach((key, value) -> {

                    int level = key.getNameCount() - initialCount - 1;
                    if (level < printLevel) {
                        long size = value.getOrDefault(FILE_SIZE, 0L);
                        System.out.printf("%s[%s] - %,d bytes, %d files, %d folders %n",
                                "\t".repeat(level), key.getFileName(), size,
                                value.getOrDefault(FILE_CNT, 0L),
                                value.getOrDefault(DIR_CNT, 0L));
                    }
                });

            } else {
                /* 深度優先走訪特性：將當前子目錄的統計數據「向上捲動（Roll up）」累加至父目錄中 */
                var parentMap = folderSizes.get(dir.getParent());
                var childMap = folderSizes.get(dir);
                long folderCount = childMap.getOrDefault(DIR_CNT, 0L);
                long fileSize = childMap.getOrDefault(FILE_SIZE, 0L);
                long fileCount = childMap.getOrDefault(FILE_CNT, 0L);

                /* 將子資料夾數量加 1（代表子目錄本身）後累加至父資料夾的目錄計數器 */
                parentMap.merge(DIR_CNT, folderCount + 1, (o, n) -> o += n);
                parentMap.merge(FILE_SIZE, fileSize, Math::addExact);
                parentMap.merge(FILE_CNT, fileCount, Math::addExact);
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
