package PathListings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {

        // 建立 Path（相對路徑）
        Path path = Path.of("this/is/several/folders/testing.txt");

        // printPathInfo(path);
        logStatement(path);
        extraInfo(path);
    }

    /**
     * 印出 Path 的各種資訊
     */
    private static void printPathInfo(Path path) {

        // 原始路徑
        System.out.println("Path = " + path);

        // 檔案名稱（最後一層）
        System.out.println("fileName = " + path.getFileName());

        // 父目錄
        System.out.println("parent = " + path.getParent());

        // 轉成絕對路徑
        Path absolutePath = path.toAbsolutePath();

        System.out.println("Absolute Path = " + absolutePath);

        // 絕對路徑的根目錄
        System.out.println("Absolute Path Root = " + absolutePath.getRoot());

        // 相對路徑沒有 Root，因此為 null
        System.out.println("Root = " + path.getRoot());

        // 是否為絕對路徑
        System.out.println("isAbsolute = " + path.isAbsolute());

        System.out.println(absolutePath.getRoot());

        /*
         * int i = 1;
         * var iterator = absolutePath.iterator();
         * 
         * while (iterator.hasNext()) {
         * System.out.println(".".repeat(i++) + " " + iterator.next());
         * }
         */

        // 取得路徑共有幾層（不包含 Root）
        int pathParts = absolutePath.getNameCount();

        // 逐層印出路徑
        for (int i = 0; i < pathParts; i++) {
            System.out.println(".".repeat(i + 1) + " " + absolutePath.getName(i));
        }

        System.out.println("-----------------------");
    }

    /**
     * 建立資料夾並寫入 Log
     */
    private static void logStatement(Path path) {

        try {

            // 取得父資料夾
            Path parent = path.getParent();

            // 若父資料夾不存在，就建立所有資料夾
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            // 將時間與訊息附加到檔案中
            Files.writeString(
                    path,
                    Instant.now() + ": hello file world\n",
                    StandardOpenOption.CREATE, // 檔案不存在就建立
                    StandardOpenOption.APPEND // 已存在就附加內容
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得檔案額外資訊
     */
    private static void extraInfo(Path path) {

        try {

            // 讀取所有檔案屬性
            var attributes = Files.readAttributes(path, "*");

            // 印出所有屬性
            attributes.entrySet()
                    .forEach(System.out::println);

            // 印出 MIME Type
            System.out.println(Files.probeContentType(path));

        } catch (IOException e) {
            System.out.println("Problem getting attributes");
        }
    }
}