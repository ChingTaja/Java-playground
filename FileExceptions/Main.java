package FileExceptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        /*
         * 💡 實驗 1：如何撈出當前的 CWD？
         * 傳入一個空字串 "" 給 File 構造函數，並呼叫 getAbsolutePath()。
         * 這在 Linux/Mac 上就相當於執行 pwd 指令；Windows 上相當於 cd。
         */
        System.out.println("Current Working Directory (cwd) = " +
                new File("").getAbsolutePath());

        String filename = "files/testing.csv";

        /*
         * 💡 實驗 2：利用不同的建構子組合路徑
         * 這裡傳入 (File parent, String child)
         * 因為 getAbsolutePath() 傳回的是 String，原代碼外層包裝成了 File 作為 Parent
         * 這樣組合出來的絕對路徑就不會帶有 "." 這個冗餘字元（Redundant name element）
         */
        File file = new File(new File("").getAbsolutePath(), filename);
        System.out.println("舊式 File 絕對路徑: " + file.getAbsolutePath());

        if (!file.exists()) {
            System.out.println("I can't run unless this file exists");
            return;
        }

        System.out.println("I'm good to go.");

        /*
         * 找出電腦裡所有的硬碟磁碟機（根目錄）
         * Windows 會印出 C:\\、D:\\ 等；Mac/Linux 則只會印出 /
         */
        System.out.println("--- 系統根目錄列表 ---");
        for (File f : File.listRoots()) {
            System.out.println(f);
        }
        System.out.println("----------------------------------");

        /*
         * 現代化 NIO.2
         * 1. Path 是一個「介面（Interface）」，我們不能直接 new 它
         * 2. 我們使用 Paths.get() 這個靜態工廠方法（Factory Method）來獲取實例
         * 3. 判斷檔案是否存在，改用 Files.exists(path) 靜態方法，將 path 當作參數傳。
         */
        Path path = Paths.get("files/testing.csv");
        System.out.println("新式 Path 描述: " + path.toString());

        if (!Files.exists(path)) {
            System.out.println("2. I can't run unless this file exists");
            return;
        }

        System.out.println("2. I'm good to go.");
    }

    // 傳統 java.io 舊式處理資源與關閉的地獄寫法
    private static void testFile(String filename) {

        Path path = Paths.get(filename);
        FileReader reader = null;

        try {
            reader = new FileReader(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Maybe I'd log something either way...");
        }

        System.out.println("File exists and able to use as a resource");
    }

    // 現代化 try-with-resources 多重 catch 與防禦性寫法
    private static void testFile2(String filename) {

        try (FileReader reader = new FileReader(filename)) {
            // 自動關閉資源
        } catch (FileNotFoundException e) {
            System.out.println("File '" + filename + "' does not exist");
            throw new RuntimeException(e);
        } catch (NullPointerException | IllegalArgumentException badData) {
            System.out.println("User has added bad data " + badData.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("Something unrelated and unexpected happened");
        } finally {
            System.out.println("Maybe I'd log something either way...");
        }

        System.out.println("File exists and able to use as a resource");
    }
}