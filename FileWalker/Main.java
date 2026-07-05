package FileWalker;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        /* 使用雙點號代表當前工作目錄的父目錄 */
        Path startingPath = Path.of("..");

        /* 建立自訂的 FileVisitor 實例，傳入 printLevel 參數限制輸出層級 */
        FileVisitor<Path> statsVisitor = new StatsVisitor(1);
        try {
            /* Files.walkFileTree 不需要搭配 try-with-resources，因為它在執行完畢後會自動關閉相關資源 */
            Files.walkFileTree(startingPath, statsVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* 繼承 SimpleFileVisitor 並指定泛型型態為 Path */
    private static class StatsVisitor extends SimpleFileVisitor<Path> {

        private Path initialPath = null;
        /* 使用 LinkedHashMap 以維持目錄拜訪的插入順序 */
        private final Map<Path, Long> folderSizes = new LinkedHashMap<>();
        private int initialCount;

        private int printLevel;

        public StatsVisitor(int printLevel) {
            this.printLevel = printLevel;
        }

        /* 拜訪檔案時觸發的方法 */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {

            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);

            /*
             * 使用 merge 方法累加檔案大小至其父目錄
             * 若 Key 不存在則放入 0L，若存在則執行 Lambda 運算式累加數值
             */
            folderSizes.merge(file.getParent(), 0L, (o, n) -> o += attrs.size());
            return FileVisitResult.CONTINUE;
        }

        /* 進入目錄前觸發的方法 */
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException {

            Objects.requireNonNull(dir);
            Objects.requireNonNull(attrs);

            /* 首次進入此方法時，將當前目錄設定為初始根目錄並記錄其路徑元件數量 */
            if (initialPath == null) {
                initialPath = dir;
                initialCount = dir.getNameCount();
            } else {
                /* 計算目前目錄相對於初始根目錄的深度階層 */
                int relativeLevel = dir.getNameCount() - initialCount;
                /* 為了提升大目錄樹的記憶體效率，回到第一層子目錄時會清空 Map 並重新累加 */
                if (relativeLevel == 1) {
                    folderSizes.clear();
                }
                /* 初始化當前目錄的累加大小為 0L，確保 Map 維持正確的插入順序 */
                folderSizes.put(dir, 0L);
            }
            return FileVisitResult.CONTINUE;
        }

        /* 離開目錄後觸發的方法 */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                throws IOException {

            Objects.requireNonNull(dir);

            /* 當前走訪目錄等於初始根目錄時，代表整個走訪流程結束，回傳 TERMINATE 終止走訪 */
            if (dir.equals(initialPath)) {
                return FileVisitResult.TERMINATE;
            }

            int relativeLevel = dir.getNameCount() - initialCount;
            /* 若回到第一層子目錄，則走訪並印出該子目錄內所有收集到的統計數據 */
            if (relativeLevel == 1) {
                folderSizes.forEach((key, value) -> {

                    int level = key.getNameCount() - initialCount - 1;
                    /* 僅印出小於自訂列印層級（printLevel）的目錄資訊 */
                    if (level < printLevel) {
                        System.out.printf("%s[%s] - %,d bytes %n",
                                "\t".repeat(level), key.getFileName(), value);
                    }
                });

            } else {
                /*
                 * 若非第一層子目錄，則將當前目錄已處理完畢的總大小，
                 * 向上合併遞迴累加至它的父目錄中
                 */
                long folderSize = folderSizes.get(dir);
                folderSizes.merge(dir.getParent(), 0L, (o, n) -> o += folderSize);
            }
            return FileVisitResult.CONTINUE;
        }
    }
}