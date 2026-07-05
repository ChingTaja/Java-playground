package FileAndPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        useFile("testfile.txt");
        usePath("pathfile.txt");
    }

    /**
     * 使用傳統 java.io.File API
     */
    private static void useFile(String fileName) {

        // 建立 File 物件
        File file = new File(fileName);

        // 檢查檔案是否存在
        boolean fileExists = file.exists();

        System.out.printf(
                "File '%s' %s%n",
                fileName,
                fileExists ? "exists." : "does not exist.");

        // 如果存在，先刪除
        if (fileExists) {
            System.out.println("Deleting File: " + fileName);

            // delete() 成功回傳 true，失敗回傳 false
            fileExists = !file.delete();
        }

        // 如果不存在，就建立新檔案
        if (!fileExists) {

            try {
                // createNewFile() 可能拋出 IOException
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }

            System.out.println("Created File: " + fileName);

            if (file.canWrite()) {
                System.out.println("Would write to file here");
            }
        }
    }

    /**
     * 使用 NIO (Path + Files)
     */
    private static void usePath(String fileName) {

        // 建立 Path 物件 (JDK 11 建議寫法)
        Path path = Path.of(fileName);

        // 檢查是否存在
        boolean fileExists = Files.exists(path);

        System.out.printf(
                "File '%s' %s%n",
                fileName,
                fileExists ? "exists." : "does not exist.");

        // 如果存在就刪除
        if (fileExists) {

            System.out.println("Deleting File: " + fileName);

            try {
                // Files.delete() 失敗直接丟 IOException
                Files.delete(path);
                fileExists = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 如果不存在就建立
        if (!fileExists) {

            try {

                // 建立檔案
                Files.createFile(path);

                System.out.println("Created File: " + fileName);

                // 確認是否可寫入
                if (Files.isWritable(path)) {

                    // 寫入文字
                    Files.writeString(
                            path,
                            """
                                    Here is some data,
                                    For my file,
                                    just to prove,
                                    Using the Files class and path are better!
                                    """);
                }

                System.out.println("And I can read too");
                System.out.println("-------------------------");

                // 一次讀取所有行並輸出
                Files.readAllLines(path)
                        .forEach(System.out::println);

            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }
}
