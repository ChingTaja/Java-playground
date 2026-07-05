package ReadingFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        /* 使用 try-with-resources 確保 FileReader 資源會自動關閉
           FileReader 預設是用來讀取文字檔的類別 */
        try (FileReader reader = new FileReader("file.txt")) {
            /* 宣告一個字元陣列作為緩衝區，一次讀取 1000 個字元以減少硬碟讀取次數 */
            char[] block = new char[1000];
            int data;
            /* reader.read(block) 會將資料讀入 block 陣列中，並回傳實際讀取到的字元數
               當回傳值為 -1 時，代表已經觸及檔案結尾（End of file） */
            while ((data = reader.read(block)) != -1) {
                /* 利用字元陣列建構 String 字串，指定從索引 0 開始，長度為實際讀取的字元數 */
                String content = new String(block, 0, data);
                System.out.printf("---> [%d chars] %s%n", data, content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("-----------------------------------");
        /* 使用 try-with-resources 確保 BufferedReader 與內嵌的 FileReader 資源皆自動關閉
           將 FileReader 包裝在 BufferedReader 中，能提供更大、可調校的記憶體緩衝區 */
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("file.txt"))) {

            /* lines() 方法自 JDK 8 開始提供，會回傳一個 Stream<String>
               能讓我們直接串接 Stream pipeline 終端操作（Terminal operation），逐行印出檔案內容 */
            bufferedReader.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
