package FileListings;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        // 取得目前工作目錄（Current Working Directory）
        Path path = Path.of("").toAbsolutePath();
        System.out.println("Current Directory = " + path);

        System.out.println("\n--- Files.list 範例 ---");
        /* 必須使用 try-with-resources 語句確保 Stream 執行完畢後資源能被正確關閉 */
        // list：只列出「第一層」內容（不遞迴）
        try (Stream<Path> paths = Files.list(path)) {

            paths.map(Main::listDir)
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Files.walk 範例（深度=2） ---");

        // walk：遞迴走訪資料夾（可控制深度）
        try (Stream<Path> walkedPaths = Files.walk(path, 2)) {

            walkedPaths
                    .filter(Files::isRegularFile)
                    .map(Main::listDir)
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Files.find 範例（條件搜尋） ---");

        // find：走訪 + 直接過濾（效能比 walk + filter 好）
        /* find 方法在底層尋找時直接進行條件篩選，效率比 walk 搭配 filter 更佳 */
        /* 深度設為 Integer.MAX_VALUE 代表搜尋所有巢狀層級 */
        try (Stream<Path> foundPaths = Files.find(
                path,
                Integer.MAX_VALUE,
                (p, attr) -> {
                    try {
                        // 條件：檔案 + 大小 > 300 bytes
                        return attr.isRegularFile() && attr.size() > 300;
                    } catch (Exception e) {
                        return false;
                    }
                })) {

            foundPaths
                    .map(Main::listDir)
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- DirectoryStream（glob 篩選） ---");
        /* DirectoryStream 是另一個 NIO 2 類別，提供目錄內容的 Iterable 走訪機制 */
        /* 使用 resolve 方法切換至子資料夾，並透過 glob 語法 "*.xml" 進行簡單的檔名匹配 */
        
        // DirectoryStream：較底層的資料夾迭代方式
        Path ideaPath = path.resolve(".idea");

        try (DirectoryStream<Path> dirs = Files.newDirectoryStream(ideaPath, "*.xml")) {

            for (Path p : dirs) {
                // 正確：要把 p 傳進 listDir
                System.out.println(listDir(p));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- DirectoryStream（自訂 filter） ---");

        try (DirectoryStream<Path> dirs = Files.newDirectoryStream(ideaPath, p -> {
            try {
                // 條件篩選：
                return p.getFileName().toString().endsWith(".xml")
                        && Files.isRegularFile(p)
                        && Files.size(p) > 1000;
            } catch (IOException e) {
                return false;
            }
        })) {

            for (Path p : dirs) {
                System.out.println(listDir(p));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化檔案/資料夾資訊輸出
     */
    private static String listDir(Path path) {

        try {
            // 是否為資料夾
            boolean isDir = Files.isDirectory(path);

            // 最後修改時間
            FileTime fileTime = Files.getLastModifiedTime(path);

            // 轉成本地時間
            LocalDateTime modDT = LocalDateTime.ofInstant(
                    fileTime.toInstant(),
                    ZoneId.systemDefault());

            // 檔案大小（資料夾顯示 0）
            long size = isDir ? 0 : Files.size(path);

            return String.format(
                    "%tD %tT %5s %12s %s",
                    modDT,
                    modDT,
                    isDir ? "<DIR>" : "",
                    isDir ? "" : size,
                    path);

        } catch (IOException e) {
            System.out.println("Something went wrong");
            return path.toString();
        }
    }
}