package FileWalker.Challenge;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        /* 使用兩個點代表上一層目錄作為走訪的起點 */
        Path startingPath = Path.of("..");
        /* 限制列印階層深度為 1 */
        FileVisitor<Path> statsVisitor = new StatsVisitor(1);
        try {
            /* 啟動檔案樹走訪機制 */
            Files.walkFileTree(startingPath, statsVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class StatsVisitor extends SimpleFileVisitor<Path> {

        private Path initialPath = null;
        /* 初始版本僅使用 Map 記錄各路徑與其對應的總容量大小 */
        private final Map<Path, Long> folderSizes = new LinkedHashMap<>();
        private int initialCount;

        private int printLevel;

        public StatsVisitor(int printLevel) {
            this.printLevel = printLevel;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {

            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);
            /* 當遇到檔案時，將該檔案容量累加至其父目錄的統計中 */
            folderSizes.merge(file.getParent(), 0L, (o, n) -> o += attrs.size());
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
                /* 為了防止大目錄吃滿記憶體，在回到第一層子目錄時會清除 Map */
                if (relativeLevel == 1) {
                    folderSizes.clear();
                }
                folderSizes.put(dir, 0L);
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
            /* 若已完全回溯到第一層子目錄，則將底下的統計數據輸出 */
            if (relativeLevel == 1) {
                folderSizes.forEach((key, value) -> {

                    int level = key.getNameCount() - initialCount - 1;
                    if (level < printLevel) {
                        System.out.printf("%s[%s] - %,d bytes %n",
                                "\t".repeat(level), key.getFileName(), value);
                    }
                });

            } else {
                /* 子目錄處理完畢後，將累積的數據向上累加（Data Roll-up）給父目錄 */
                long folderSize = folderSizes.get(dir);
                folderSizes.merge(dir.getParent(), 0L, (o, n) -> o += folderSize);
            }
            return FileVisitResult.CONTINUE;
        }
    }
}